package io.freezing.ai.bot.impl.texasholdem.simple;

import io.freezing.ai.bot.BotActionRationale;
import io.freezing.ai.bot.PokerBot;
import io.freezing.ai.bot.action.BotAction;
import io.freezing.ai.bot.action.impl.CallAction;
import io.freezing.ai.bot.action.impl.CheckAction;
import io.freezing.ai.bot.action.impl.FoldAction;
import io.freezing.ai.bot.action.impl.RaiseAction;
import io.freezing.ai.domain.*;
import io.freezing.ai.function.CardUtils;
import io.freezing.ai.function.RandomUtils;
import io.freezing.ai.function.TexasHoldEmTableUtils;
import io.freezing.ai.rules.impl.texasholdem.TexasHoldEmRules;
import javafx.util.Pair;

import java.util.Random;

public class SimpleTexasHoldEmPokerBot implements PokerBot {
    private final TexasHoldEmRules rules;
    private final SimpleTexasHoldEmPokerBotConfig config;
    private final Random rnd;

    public SimpleTexasHoldEmPokerBot(TexasHoldEmRules rules, SimpleTexasHoldEmPokerBotConfig config) {
        this.rules = rules;
        this.config = config;
        this.rnd = new Random(config.getSeed());
    }

    @Override
    public BotAction nextAction(PokerState state) {
        Pair<Double, Double> winProbabilityAndCurrentStrength = calculateWinProbabilityAndCurrentStrength(state.getTable(), state.getMyHand(), state.getTotalNumberOfPlayers());
        double winProbability = winProbabilityAndCurrentStrength.getKey();
        double currentHandStrength = winProbabilityAndCurrentStrength.getValue();
        double expectedWin = winProbability * state.getTotalPot() + (winProbability - 1.0) * state.getAmountToCall();
        double optimalBet = state.getMyStack() * state.getMyStack() * winProbability / (state.getMyStack() + state.getTotalPot() - winProbability * state.getMyStack());

        TexasHoldEmRoundState roundState = TexasHoldEmTableUtils.getRoundState(state.getTable());
        boolean isPostFlop = roundState == TexasHoldEmRoundState.TURN || roundState == TexasHoldEmRoundState.RIVER;

        BotActionRationale rationale = new SimpleTexasHoldEmRationale(winProbability, expectedWin, optimalBet, currentHandStrength, state.getAmountToCall(), roundState);

        // TODO: Handle high risk situations, i.e. ones that are potentially losing a lot of money

        if (winProbability > 0.9) return new RaiseAction(state.getMyStack(), rationale);
            // If amount to call is very small on TURN and RIVER, just go for it (assuming there is a reasonable chance of winning)
        else if (isPostFlop && winProbability > 0.15 && currentHandStrength > 0.15 && state.getAmountToCall() < 0.05 * state.getMyStack()) return new CallAction(rationale);
        // In the late state, give more weight to the currentHandStrength, i.e. fold if don't have strong enough hand
        else if (isPostFlop && currentHandStrength < 0.5) return new FoldAction(rationale);
        else if (expectedWin > 0.0 && optimalBet > state.getAmountToCall() + state.getBigBlind()) return new RaiseAction(Math.min(optimalBet, state.getMyStack()), rationale);
        else if (expectedWin > 0.0 && optimalBet > state.getAmountToCall()) return new CallAction(rationale);
        else if (expectedWin > 0.1 * state.getMyStack()) return new CallAction(rationale);
        else if (expectedWin > state.getAmountToCall()) return new CallAction(rationale);
        else if (roundState == TexasHoldEmRoundState.TURN &&
                expectedWin <= 0.0 &&
                state.getAmountToCall() <= 0.2 * state.getMyStack() &&
                currentHandStrength > 0.7) {
            return new CallAction(rationale);
        }
        else if (state.getAmountToCall() == 0) return new CheckAction(rationale);
        // If within the 15% of my stack and I still have good chance of winning
        else if (winProbability > 0.35 && 0.15 * state.getMyStack() > state.getAmountToCall()) return new CallAction(rationale);
        else return new FoldAction(rationale);
    }

    private Pair<Double, Double> calculateWinProbabilityAndCurrentStrength(Table table, Hand hand, int totalNumberOfPlayers) {
        // Initialize here, we want to reuse the same array for performance reasons
        Hand opponents[] = new Hand[totalNumberOfPlayers - 1];

        // Start with finding card codes that are not visible to the bot
        int hiddenCardCodes[] = CardUtils.getHiddenCardCodes(table, hand);
        // Convert them to actual Cards (need them later)
        Card[] hiddenCards = new Card[hiddenCardCodes.length];
        for (int i = 0; i < hiddenCardCodes.length; i++) hiddenCards[i] = CardUtils.getCard(hiddenCardCodes[i]);

        // Monte-Carlo
        int monteCarloIterations = config.getMonteCarloIterations();
        int wins = 0;
        int currentWins = 0;

        while (monteCarloIterations-- > 0) {
            // Shuffle the existing array due to performance reasons (it's not important to keep previous states)
            RandomUtils.shuffleArray(hiddenCards, rnd, 2 * hiddenCardCodes.length * 2);
            assignCardsToOpponents(opponents, hiddenCards);
            Table monteCarloTable = assignCardsToTable(opponents.length * 2, hiddenCards, table);

            // Find winner hand (by reference)
            if (findWinner(monteCarloTable, hand, opponents).getWholeHand().getHand() == hand) wins++;
            if (findWinner(table, hand, opponents).getWholeHand().getHand() == hand) currentWins++;
        }
        return new Pair<>(
                (double)(wins) / (double)(config.getMonteCarloIterations()),
                (double)(currentWins) / (double)(config.getMonteCarloIterations())
        );
    }

    private Table assignCardsToTable(int offset, Card[] hiddenCards, Table table) {
        // Desired total number of cards is 5
        if (table.getCards().length == rules.getMaxNumberOfTableCards()) return table;

        Card[] cards = new Card[rules.getMaxNumberOfTableCards()];
        int storeIdx = 0;
        for (Card c : table.getCards()) cards[storeIdx++] = c;

        // Add remaining cards
        while (storeIdx < cards.length) {
            cards[storeIdx++] = hiddenCards[offset++];
        }

        return new Table(cards);
    }

    private void assignCardsToOpponents(Hand[] opponents, Card[] cards) {
        for (int i = 0; i < opponents.length; i++) {
            // We could create hand mutable, but I don't want to go there. I'll see if it's important later
            opponents[i] = new Hand(new Card[]{
                    cards[2 * i], cards[2 * i + 1]
            });
        }
    }

    private EvaluatedHand findWinner(Table table, Hand hand, Hand[] opponents) {
        EvaluatedHand bestHand = this.rules.getHandEvaluator().evaluate(hand, table);

        for (Hand h : opponents) {
            EvaluatedHand tmpHand = this.rules.getHandEvaluator().evaluate(h, table);
            if (tmpHand.compareTo(bestHand) > 0) {
                bestHand = tmpHand;
            }
        }

        return bestHand;
    }

    @Override
    public TexasHoldEmRules getRules() {
        return rules;
    }

    @Override
    public String getName() {
        return SimpleTexasHoldEmPokerBot.class.getSimpleName();
    }
}

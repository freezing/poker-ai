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
import io.freezing.ai.rules.impl.texasholdem.TexasHoldEmRules;

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
        double winProbability = calculateWinProbability(state.getTable(), state.getMyHand(), state.getTotalNumberOfPlayers());
        double expectedWin = winProbability * state.getTotalPot() + (winProbability - 1.0) * state.getAmountToCall();
        BotActionRationale rationale = new SimpleTexasHoldEmRationale(winProbability, expectedWin);

        if (winProbability > 0.9) return new RaiseAction(state.getMyStack(), rationale);
        else if (winProbability > 0.5) return new RaiseAction(state.getMyStack() / 2, rationale);
        else if (expectedWin > 0.1 * state.getMyStack()) return new CallAction(state.getAmountToCall(), rationale);
        else if (expectedWin > state.getAmountToCall()) return new CallAction(state.getAmountToCall(), rationale);
        else if (state.getAmountToCall() == 0) return new CheckAction(rationale);
        // If within the 15% of my pot and I still have good chance of winning
        else if (winProbability > 0.35 && 0.15 * state.getMyStack() > state.getAmountToCall()) return new CallAction(state.getAmountToCall(), rationale);
        else return new FoldAction(rationale);
    }

    private double calculateWinProbability(Table table, Hand hand, int totalNumberOfPlayers) {
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
        while (monteCarloIterations-- > 0) {
            // Shuffle the existing array due to performance reasons (it's not important to keep previous states)
            RandomUtils.shuffleArray(hiddenCards, rnd, 2 * hiddenCardCodes.length * 2);
            assignCardsToOpponents(opponents, hiddenCards);
            Table monteCarloTable = assignCardsToTable(opponents.length * 2, hiddenCards, table);

            // Find winner hand (by reference)
            if (findWinner(monteCarloTable, hand, opponents).getWholeHand().getHand() == hand) wins++;
        }
        return (double)(wins) / (double)(config.getMonteCarloIterations());
    }

    private Table assignCardsToTable(int offset, Card[] hiddenCards, Table table) {
        // Desired total number of cards is 5
        if (table.getCards().length == rules.getFinalTableLength()) return table;

        Card[] cards = new Card[rules.getFinalTableLength()];
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
    public String getName() {
        return SimpleTexasHoldEmPokerBot.class.getSimpleName();
    }
}

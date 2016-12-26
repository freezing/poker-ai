package io.freezing.ai.bot.impl.texasholdem.simple;

import io.freezing.ai.bot.BotActionRationale;
import io.freezing.ai.bot.PokerBot;
import io.freezing.ai.bot.action.BotAction;
import io.freezing.ai.bot.action.impl.CheckAction;
import io.freezing.ai.bot.action.impl.FoldAction;
import io.freezing.ai.bot.action.impl.RaiseAction;
import io.freezing.ai.domain.*;
import io.freezing.ai.function.CardUtils;
import io.freezing.ai.function.RandomUtils;
import io.freezing.ai.rules.impl.texasholdem.TexasHoldEmRules;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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
        BotActionRationale rationale = new SimpleTexasHoldEmRationale(winProbability);

        if (winProbability > 90) return new RaiseAction(state.getMyStack(), rationale);
        else if (winProbability > 50) return new RaiseAction(state.getMyStack() / 2, rationale);
        else if (state.getAmountToCall() == 0) return new CheckAction(rationale);
        else return new FoldAction(rationale);
    }

    private double calculateWinProbability(Table table, Hand hand, int totalNumberOfPlayers) {
        // Initialize here, we want to reuse the same array for performace reasons
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
            assignCards(opponents, hiddenCards);

            // Find winner hand (by reference)
            if (findWinner(table, hand, opponents).getWholeHand().getHand() == hand) wins++;
        }
        return (double)(wins) / (double)(config.getMonteCarloIterations());
    }

    private void assignCards(Hand[] opponents, Card[] cards) {
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

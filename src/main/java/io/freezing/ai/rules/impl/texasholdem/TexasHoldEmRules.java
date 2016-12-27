package io.freezing.ai.rules.impl.texasholdem;

import io.freezing.ai.rules.HandEvaluator;
import io.freezing.ai.rules.PokerRules;

public class TexasHoldEmRules implements PokerRules {
    // Maximum time in milliseconds that the player has in its time bank
    private final int timeBankMs;

    // Time in milliseconds that is added to the player's time bank each move
    private final int timePerMoveMs;

    // Number of hands that is played within each blind level
    private final int handsPerLevel;

    // Amount of chips that each player starts the game with
    private final int startingStack;

    // Maximum number of players per table
    private final int maxNumberOfPlayers;

    // Ranking logic for Hand and Table
    private final HandEvaluator handEvaluator;

    public TexasHoldEmRules(int maxNumberOfPlayers, int timeBankMs, int timePerMoveMs, int handsPerLevel, int startingStack, TexasHoldEmHandEvaluator handEvaluator) {
        this.maxNumberOfPlayers = maxNumberOfPlayers;
        this.timeBankMs = timeBankMs;
        this.timePerMoveMs = timePerMoveMs;
        this.handsPerLevel = handsPerLevel;
        this.startingStack = startingStack;
        this.handEvaluator = handEvaluator;
    }

    @Override
    public int getMaxNumberOfPlayers() {
        return maxNumberOfPlayers;
    }

    @Override
    public int getTimeBankMs() {
        return timeBankMs;
    }

    @Override
    public int getTimePerMoveMs() {
        return timePerMoveMs;
    }

    @Override
    public int getHandsPerLevel() {
        return handsPerLevel;
    }

    @Override
    public int getStartingStack() {
        return startingStack;
    }

    @Override
    public HandEvaluator getHandEvaluator() {
        return handEvaluator;
    }

    public int getMaxNumberOfTableCards() { return 5; }
}

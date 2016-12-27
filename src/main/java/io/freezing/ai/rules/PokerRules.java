package io.freezing.ai.rules;

public interface PokerRules {
    int getTimeBankMs();
    int getTimePerMoveMs();
    int getHandsPerLevel();
    int getStartingStack();
    int getMaxNumberOfPlayers();
    HandEvaluator getHandEvaluator();
}

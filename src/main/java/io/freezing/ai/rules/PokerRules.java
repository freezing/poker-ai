package io.freezing.ai.rules;

public interface PokerRules {
    int getTimeBankMs();
    int getTimePerMoveMs();
    int getHandsPerLevel();
    int getStartingStack();
    HandEvaluator getHandEvaluator();
}

package io.freezing.ai.bot;

public interface BotActionRationale {
    double getWinProbability();
    double getExpectedWin();
    double getOptimalBet();
    double getCurrentHandStrength();
}

package io.freezing.ai.bot.impl.texasholdem.simple;

import io.freezing.ai.bot.BotActionRationale;

public class SimpleTexasHoldEmRationale implements BotActionRationale {
    private final double winProbability;
    private final double expectedWin;
    private final double optimalBet;
    private final double currentHandStrength;

    public SimpleTexasHoldEmRationale(double winProbability, double expectedWin, double optimalBet, double currentHandStrength) {
        this.winProbability = winProbability;
        this.expectedWin = expectedWin;
        this.optimalBet = optimalBet;
        this.currentHandStrength = currentHandStrength;
    }

    @Override
    public double getWinProbability() {
        return winProbability;
    }

    public double getExpectedWin() {
        return expectedWin;
    }

    public double getOptimalBet() {
        return optimalBet;
    }

    @Override
    public double getCurrentHandStrength() {
        return currentHandStrength;
    }

    @Override
    public String toString() {
        return String.format("SimpleTexasHoldEmRationale(currentHandStrength = %f, optimalBet = %f, winProbability = %f, expectedWin = %f)",
                this.currentHandStrength, this.optimalBet, this.winProbability, this.expectedWin);
    }
}

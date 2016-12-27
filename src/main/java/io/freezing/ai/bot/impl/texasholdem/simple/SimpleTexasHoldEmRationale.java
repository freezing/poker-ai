package io.freezing.ai.bot.impl.texasholdem.simple;

import io.freezing.ai.bot.BotActionRationale;

public class SimpleTexasHoldEmRationale implements BotActionRationale {
    private final double winProbability;
    private final double expectedWin;

    public SimpleTexasHoldEmRationale(double winProbability, double expectedWin) {
        this.winProbability = winProbability;
        this.expectedWin = expectedWin;
    }

    @Override
    public double getWinProbability() {
        return winProbability;
    }

    public double getExpectedWin() {
        return expectedWin;
    }

    @Override
    public String toString() {
        return String.format("SimpleTexasHoldEmRationale(winProbability = %f, expectedWin = %f)", this.winProbability, this.expectedWin);
    }
}

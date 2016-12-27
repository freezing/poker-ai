package io.freezing.ai.bot.impl.texasholdem.simple;

import io.freezing.ai.bot.BotActionRationale;

public class SimpleTexasHoldEmRationale implements BotActionRationale {
    private final double winProbability;
    private final double expectedWin;
    private final double optimalBet;

    public SimpleTexasHoldEmRationale(double winProbability, double expectedWin, double optimalBet) {
        this.winProbability = winProbability;
        this.expectedWin = expectedWin;
        this.optimalBet = optimalBet;
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
    public String toString() {
        return String.format("SimpleTexasHoldEmRationale(optimalBet = %f, winProbability = %f, expectedWin = %f)", this.optimalBet, this.winProbability, this.expectedWin);
    }
}

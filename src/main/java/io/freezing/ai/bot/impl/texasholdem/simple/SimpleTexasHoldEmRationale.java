package io.freezing.ai.bot.impl.texasholdem.simple;

import io.freezing.ai.bot.BotActionRationale;
import io.freezing.ai.domain.TexasHoldEmRoundState;

public class SimpleTexasHoldEmRationale implements BotActionRationale {
    private final double winProbability;
    private final double expectedWin;
    private final double optimalBet;
    private final double currentHandStrength;
    private final double amountToCall;
    private final TexasHoldEmRoundState roundState;

    public SimpleTexasHoldEmRationale(double winProbability, double expectedWin, double optimalBet, double currentHandStrength, double amountToCall, TexasHoldEmRoundState roundState) {
        this.winProbability = winProbability;
        this.expectedWin = expectedWin;
        this.optimalBet = optimalBet;
        this.currentHandStrength = currentHandStrength;
        this.amountToCall = amountToCall;
        this.roundState = roundState;
    }

    @Override
    public double getWinProbability() {
        return winProbability;
    }

    @Override
    public double getExpectedWin() {
        return expectedWin;
    }

    @Override
    public double getOptimalBet() {
        return optimalBet;
    }

    @Override
    public double getAmountToCall() {
        return amountToCall;
    }

    @Override
    public double getCurrentHandStrength() {
        return currentHandStrength;
    }

    public TexasHoldEmRoundState getRoundState() {
        return roundState;
    }

    @Override
    public String toString() {
        return String.format("SimpleTexasHoldEmRationale(currentHandStrength = %f, optimalBet = %f, winProbability = %f, expectedWin = %f, amountToCall = %f, roundState = %s)",
                this.currentHandStrength, this.optimalBet, this.winProbability, this.expectedWin, this.amountToCall, this.roundState);
    }
}

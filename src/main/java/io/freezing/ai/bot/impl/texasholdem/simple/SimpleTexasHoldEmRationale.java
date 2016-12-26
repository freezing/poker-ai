package io.freezing.ai.bot.impl.texasholdem.simple;

import io.freezing.ai.bot.BotActionRationale;

public class SimpleTexasHoldEmRationale implements BotActionRationale {
    private final double winProbability;

    public SimpleTexasHoldEmRationale(double winProbability) {
        this.winProbability = winProbability;
    }

    @Override
    public double getWinProbability() {
        return this.winProbability;
    }
}

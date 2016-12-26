package io.freezing.ai.bot.impl.texasholdem.simple;

public class SimpleTexasHoldEmPokerBotConfig {
    private final int monteCarloIterations;

    public SimpleTexasHoldEmPokerBotConfig(int monteCarloIterations) {
        this.monteCarloIterations = monteCarloIterations;
    }

    public int getMonteCarloIterations() {
        return monteCarloIterations;
    }
}

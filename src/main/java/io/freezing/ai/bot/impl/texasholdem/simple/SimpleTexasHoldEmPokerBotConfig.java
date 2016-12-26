package io.freezing.ai.bot.impl.texasholdem.simple;

public class SimpleTexasHoldEmPokerBotConfig {
    private final int monteCarloIterations;
    private final long seed;

    public SimpleTexasHoldEmPokerBotConfig(int monteCarloIterations, long seed) {
        this.monteCarloIterations = monteCarloIterations;
        this.seed = seed;
    }

    public int getMonteCarloIterations() {
        return monteCarloIterations;
    }

    public long getSeed() {
        return seed;
    }
}

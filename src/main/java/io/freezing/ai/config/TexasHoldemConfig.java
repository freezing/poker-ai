package io.freezing.ai.config;

public class TexasHoldemConfig {
    // Maximum time in milliseconds that the player has in its time bank
    private final int timeBankMs;

    // Time in milliseconds that is added to the player's time bank each move
    private final int timePerMoveMs;

    // Number of hands that is played within each blind level
    private final int handsPerLevel;

    // Amount of chips that each player starts the game with
    private final int startingStack;

    // The name of your bot during this match
    private final String botName;

    public TexasHoldemConfig(int timeBankMs, int timePerMoveMs, int handsPerLevel, int startingStack, String botName) {
        this.timeBankMs = timeBankMs;
        this.timePerMoveMs = timePerMoveMs;
        this.handsPerLevel = handsPerLevel;
        this.startingStack = startingStack;
        this.botName = botName;
    }

    public int getTimeBankMs() {
        return timeBankMs;
    }

    public int getTimePerMoveMs() {
        return timePerMoveMs;
    }

    public int getHandsPerLevel() {
        return handsPerLevel;
    }

    public int getStartingStack() {
        return startingStack;
    }

    public String getBotName() {
        return botName;
    }
}

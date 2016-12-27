package io.freezing.ai.exception.input;

import io.freezing.ai.rules.PokerRules;

public class MaximumPlayersExceededException extends PokerInputException {
    private MaximumPlayersExceededException(String message) {
        super(message);
    }

    public MaximumPlayersExceededException(int inputPlayers, PokerRules rules) {
        this(String.format("Maximum players allowed in: %s is %d, but got: %d",
                rules.getGameName(), rules.getMaxNumberOfPlayers(), inputPlayers));
    }
}

package io.freezing.ai.domain;

public class PokerState {
    // The number of the currently played hand, counting starts at 1
    private final int roundNumber;

    // The current size of the small blind
    private final int smallBlind;

    // The current size of the big blind
    private final int bigBlind;

    // The name of the player that currently has the dealer button (gets the small blind)
    private final String onButton;

    // The cards that are currently on the table
    Table table;

    // Total amount of chips currently in the pot (plus sidepot)
    private final int maxPot;

    // The amount of chips the bot has to put in to call
    private final int amountToCall;

    public PokerState(int roundNumber, int smallBlind, int bigBlind, String onButton, int maxPot, int amountToCall) {
        this.roundNumber = roundNumber;
        this.smallBlind = smallBlind;
        this.bigBlind = bigBlind;
        this.onButton = onButton;
        this.maxPot = maxPot;
        this.amountToCall = amountToCall;
    }

    public int getRoundNumber() {
        return roundNumber;
    }
}

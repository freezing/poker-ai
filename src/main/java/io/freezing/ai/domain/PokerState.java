package io.freezing.ai.domain;

public class PokerState {
    // The number of the currently played hand, counting starts at 1
    private final int roundNumber;

    // The current size of the small blind
    private final int smallBlind;

    // The current size of the big blind
    private final int bigBlind;

    // The cards that are currently on the table
    private final Table table;

    // Total amount of chips currently in the pot (plus sidepot)
    private final int maxPot;

    // The amount of chips the bot has to put in to call
    private final int amountToCall;

    // The amount of chips that I have left in the stack
    private final int myStack;

    // My hand
    private final Hand myHand;

    public PokerState(int roundNumber, int smallBlind, int bigBlind, Table table, int maxPot, int amountToCall, int myStack, Hand myHand) {
        this.roundNumber = roundNumber;
        this.smallBlind = smallBlind;
        this.bigBlind = bigBlind;
        this.table = table;
        this.maxPot = maxPot;
        this.amountToCall = amountToCall;
        this.myStack = myStack;
        this.myHand = myHand;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public int getSmallBlind() {
        return smallBlind;
    }

    public int getBigBlind() {
        return bigBlind;
    }

    public Table getTable() {
        return table;
    }

    public int getMaxPot() {
        return maxPot;
    }

    public int getAmountToCall() {
        return amountToCall;
    }

    public int getMyStack() {
        return myStack;
    }

    public Hand getMyHand() {
        return myHand;
    }
}

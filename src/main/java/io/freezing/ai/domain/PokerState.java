package io.freezing.ai.domain;

public class PokerState {
    // The number of the currently played hand, counting starts at 1
    private final int roundNumber;

    // The total number of players (including bot and opponenets)
    private final int totalNumberOfPlayers;

    // The current size of the small blind
    private final int smallBlind;

    // The current size of the big blind
    private final int bigBlind;

    // The cards that are currently on the table
    private final Table table;

    // Total amount of chips currently in the pot (plus sidepot)
    private final int totalPot;

    // The amount of chips the bot has to put in to call
    private final int amountToCall;

    // The amount of chips that I have left in the stack
    private final int myStack;

    // The distance from the BigBlind
    private final int myPosition;

    // My hand
    private final Hand myHand;

    public PokerState(int roundNumber, int totalNumberOfPlayers, int smallBlind, int bigBlind, Table table, int totalPot, int amountToCall, int myStack, int myPosition, Hand myHand) {
        this.roundNumber = roundNumber;
        this.totalNumberOfPlayers = totalNumberOfPlayers;
        this.smallBlind = smallBlind;
        this.bigBlind = bigBlind;
        this.table = table;
        this.totalPot = totalPot;
        this.amountToCall = amountToCall;
        this.myStack = myStack;
        this.myPosition = myPosition;
        this.myHand = myHand;

        if (totalNumberOfPlayers <= 1) {
            throw new IllegalArgumentException(String.format("Found PokerState with %d total # of players which means the game is over or the input is invalid. TODO: This is to be handled in a better way at some point.", totalNumberOfPlayers));
        }
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public int getTotalNumberOfPlayers() {
        return totalNumberOfPlayers;
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

    public int getTotalPot() {
        return totalPot;
    }

    public int getAmountToCall() {
        return amountToCall;
    }

    public int getMyStack() {
        return myStack;
    }

    public int getMyPosition() {
        return myPosition;
    }

    public Hand getMyHand() {
        return myHand;
    }

    @Override
    public String toString() {
        return "PokerState{" +
                "roundNumber=" + roundNumber +
                ", totalNumberOfPlayers=" + totalNumberOfPlayers +
                ", smallBlind=" + smallBlind +
                ", bigBlind=" + bigBlind +
                ", table=" + table +
                ", totalPot=" + totalPot +
                ", amountToCall=" + amountToCall +
                ", myStack=" + myStack +
                ", myHand=" + myHand +
                '}';
    }
}

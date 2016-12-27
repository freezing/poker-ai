package io.freezing.ai.io.cli.command;

public class SetBlindsCommand implements Command {
    private final int smallBlind;
    private final int bigBlind;

    public SetBlindsCommand(int smallBlind, int bigBlind) {
        this.smallBlind = smallBlind;
        this.bigBlind = bigBlind;
    }

    public int getSmallBlind() {
        return smallBlind;
    }

    public int getBigBlind() {
        return bigBlind;
    }

    @Override
    public String toString() {
        return String.format("SetBlindsCommand(smallBlind = %d, bigBlind = %d)", smallBlind, bigBlind);
    }
}

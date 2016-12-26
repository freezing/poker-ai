package io.freezing.ai.domain;

import java.util.Arrays;

public class WholeHand {
    private final Hand hand;
    private final Table table;

    public WholeHand(Hand hand, Table table) {
        this.hand = hand;
        this.table = table;
    }

    public Hand getHand() {
        return hand;
    }

    public Table getTable() {
        return table;
    }

    @Override
    public String toString() {
        return String.format("WholeHand(Hand = %s, Table = %s)",
                Arrays.deepToString(hand.getCards()),
                Arrays.deepToString(table.getCards()));
    }
}

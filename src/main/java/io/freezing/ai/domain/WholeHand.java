package io.freezing.ai.domain;

public class WholeHand {
    private final Hand hand;
    private final Card[] tablePicks;

    public WholeHand(Hand hand, Card[] tablePicks) {
        this.hand = hand;
        this.tablePicks = tablePicks;
    }

    public Hand getHand() {
        return hand;
    }

    public Card[] getTablePicks() {
        return tablePicks;
    }
}

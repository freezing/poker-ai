package io.freezing.ai.domain;

public class WholeHand {
    private final Card[] handCards;
    private final Card[] tablePicks;

    public WholeHand(Card[] handCards, Card[] tablePicks) {
        this.handCards = handCards;
        this.tablePicks = tablePicks;
    }

    public Card[] getHandCards() {
        return handCards;
    }

    public Card[] getTablePicks() {
        return tablePicks;
    }
}

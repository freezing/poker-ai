package io.freezing.ai.domain;

public class Table {
    private final Card[] cards;

    public Table(Card[] cards) {
        this.cards = cards;
    }

    public Card[] getCards() {
        return cards;
    }
}

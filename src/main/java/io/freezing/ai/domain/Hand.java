package io.freezing.ai.domain;

public class Hand {
    private final Card[] cards;

    public Hand(Card[] cards) {
        this.cards = cards;
    }

    public Card[] getCards() {
        return cards;
    }
}

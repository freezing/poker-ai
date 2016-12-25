package io.freezing.ai.domain;

public class Hand {
    private final Card[] cards;

    public Hand(Card[] cards) {
        this.cards = cards;
    }

    public Card[] getCards() {
        return cards;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Hand = [");
        for (int i = 0; i < cards.length; i++) {
            if (i == cards.length - 1) sb.append(cards[i].toString());
            else sb.append(String.format("%s, ", cards[i].toString()));
        }
        sb.append("]");
        return sb.toString();
    }
}

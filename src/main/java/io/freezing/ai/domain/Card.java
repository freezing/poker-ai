package io.freezing.ai.domain;

public class Card {
    private final CardSuit suit;
    private final CardHeight height;

    public Card(CardSuit suit, CardHeight height) {
        this.suit = suit;
        this.height = height;
    }

    public CardSuit getSuit() {
        return suit;
    }

    public CardHeight getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return String.format("Card(suit = %s, Height = %s)", suit.toString(), height.toString());
    }
}

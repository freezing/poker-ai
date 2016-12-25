package io.freezing.ai.domain;

import java.util.Optional;

public class Opponent {
    private final Optional<Card[]> cards;

    public Opponent(Optional<Card[]> cards) {
        this.cards = cards;
    }

    public Optional<Card[]> getCards() {
        return cards;
    }
}

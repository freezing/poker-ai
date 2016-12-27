package io.freezing.ai.exception.input;

import io.freezing.ai.domain.Card;
import io.freezing.ai.domain.Hand;
import io.freezing.ai.domain.Table;

public class DuplicateCardFoundException extends PokerInputException {
    private DuplicateCardFoundException(String message) {
        super(message);
    }

    public DuplicateCardFoundException(Card duplicateCard, Table table, Hand hand) {
        this(String.format("Found duplicate card: %s, for the given table: %s and hand: %s",
                duplicateCard.toString(), table.toString(), hand.toString()));
    }
}

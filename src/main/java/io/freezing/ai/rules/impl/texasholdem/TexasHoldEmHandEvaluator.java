package io.freezing.ai.rules.impl.texasholdem;

import io.freezing.ai.domain.*;
import io.freezing.ai.function.CardUtils;
import io.freezing.ai.rules.HandEvaluator;

public class TexasHoldEmHandEvaluator implements HandEvaluator {
    @Override
    public EvaluatedHand evaluate(Hand hand, Table table) {
        Card[] cards = CardUtils.merge(table, hand);
        long handBitmask = createBitmask(cards);

        int rank = TexasHoldEmEval.evaluate(handBitmask);
        return new EvaluatedHand(new WholeHand(hand, table), rank);
    }

    private long createBitmask(Card cards[]) {
        // Create bitmask of hand and table
        long handBitmask = 0;

        for (Card c : cards) {
            CardSuit suit = c.getSuit();
            int cardNumber = c.getHeight().ordinal();
            handBitmask |= TexasHoldEmEval.createCardBitmask(cardNumber, suit);
        }
        return handBitmask;
    }
}
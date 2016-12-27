package io.freezing.ai.rules.impl.texasholdem;

import io.freezing.ai.domain.*;
import io.freezing.ai.function.BitUtils;
import io.freezing.ai.function.CardUtils;
import io.freezing.ai.rules.HandEvaluator;

public class TexasHoldEmHandEvaluator implements HandEvaluator {
    @Override
    public EvaluatedHand evaluate(Hand hand, Table table) {
        Card[] cards = CardUtils.merge(table, hand);
        long handBitmask = createBitmask(cards);

        // Make sure that we have 7 different cards in the game
        int setBits = 0;
        for (int i = 0; i < 4; i++) setBits += BitUtils.countSetBits( (int)((handBitmask >> (16 * i)) & 0xFFFF));
        if (setBits != 7) {
            throw new IllegalArgumentException(String.format("Expected 7 different cards, but got: %s", CardUtils.buildString(handBitmask)));
        }

        int rank = TexasHoldEmEval.evaluate(handBitmask);
        return new EvaluatedHand(new WholeHand(hand, table), rank);
    }

    private long createBitmask(Card cards[]) {
        // Create bitmask of hand and table
        long handBitmask = 0;

        for (Card c : cards) {
            CardSuit suit = c.getSuit();
            int cardNumber = CardUtils.getRank(c.getHeight());
            handBitmask |= TexasHoldEmEval.createCardBitmask(cardNumber, suit);
        }
        return handBitmask;
    }
}
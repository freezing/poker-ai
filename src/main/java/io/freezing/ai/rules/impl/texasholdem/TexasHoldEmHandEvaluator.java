package io.freezing.ai.rules.impl.texasholdem;

import io.freezing.ai.domain.*;
import io.freezing.ai.function.CardUtils;
import io.freezing.ai.rules.HandEvaluator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TexasHoldEmHandEvaluator implements HandEvaluator {
    private static final Map<CardSuit, Long> SHIFTS;
    static {
        SHIFTS = new HashMap<>();
        SHIFTS.put(CardSuit.CLUBS, 0L);
        SHIFTS.put(CardSuit.DIAMONDS, 16L);
        SHIFTS.put(CardSuit.HEARTS, 32L);
        SHIFTS.put(CardSuit.SPADES, 48L);
    }

    @Override
    public EvaluatedHand evaluate(Hand hand, Table table) {
        Card[] cards = new Card[hand.getCards().length + table.getCards().length];
        for (int i = 0; i < cards.length; i++) {
            if (i < hand.getCards().length) cards[i] = hand.getCards()[i];
            else cards[i] = table.getCards()[i - hand.getCards().length];
        }
        long handBitmask = createBitmask(cards);
        int rank = TexasHoldEmEval.evaluate(handBitmask);
        return new EvaluatedHand(new WholeHand(hand, table), rank);
    }

    private long createBitmask(Card cards[]) {
        // Create bitmask of hand and table
        long handBitmask = 0;

        for (Card c : cards) {
            CardSuit suit = c.getSuit();
            int cardNumber = CardUtils.getRank(c.getHeight());
            handBitmask |= (1L << cardNumber) << SHIFTS.get(suit);
        }

        System.out.println(Arrays.deepToString(cards));
        for (int i = 0; i < 64; i++) {
            if (((1L << i) & handBitmask) > 0) System.out.println(i);
        }
        System.out.println();
        return handBitmask;
    }
}
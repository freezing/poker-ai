package io.freezing.ai.function;

import io.freezing.ai.domain.*;

import java.util.HashSet;
import java.util.Set;

public class CardUtils {
    public static int[] getHiddenCardCodes(Table table, Hand hand) {
        Card[] all = merge(table, hand);

        // Put known card codes in the Set (so we can easily find them)
        Set<Integer> known = new HashSet<>();
        for (Card card : all) known.add(getCardCode(card));

        // TODO: Refactor so that 52 is not assumption. For now: assume we have 52 cards, which will be true for TexasHoldEm, but we might decide to expand this later
        int unknownCardCodes[] = new int[52 - all.length];
        int storeIdx = 0;
        for (int cardCode = 0; cardCode < 52; cardCode++) {
            if (!known.contains(cardCode)) unknownCardCodes[storeIdx++] = cardCode;
        }
        return unknownCardCodes;
    }

    public static Card[] merge(Table table, Hand hand) {
        Card[] cards = new Card[hand.getCards().length + table.getCards().length];
        for (int i = 0; i < cards.length; i++) {
            if (i < hand.getCards().length) cards[i] = hand.getCards()[i];
            else cards[i] = table.getCards()[i - hand.getCards().length];
        }
        return cards;
    }

    public static int getCardCode(Card card) {
        return card.getSuit().ordinal() * CardHeight.values().length + card.getHeight().ordinal();
    }

    public static Card getCard(int cardCode) {
        return new Card(getSuit(cardCode / CardHeight.values().length), getHeight(cardCode % CardHeight.values().length));
    }

    public static CardSuit getSuit(int suitCode) {
        return CardSuit.values()[suitCode];
    }

    public static CardHeight getHeight(int rank) {
        return CardHeight.values()[rank];
    }

    public static String buildString(long hand) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");

        boolean first = true;
        for (int i = 0; i < 63; i++) {
            if (((1L << i) & hand) > 0) {
                if (!first) {
                    sb.append(", " + i);
                } else {
                    first = false;
                    sb.append(i);
                }
            }
        }
        sb.append("]");
        return sb.toString();
    }
}

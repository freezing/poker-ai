package io.freezing.ai.function;

import io.freezing.ai.domain.Card;
import io.freezing.ai.domain.CardHeight;
import io.freezing.ai.domain.CardSuit;

public class CardUtils {
    public static int getSuiteCode(CardSuit suit) {
        switch (suit) {
            case CLUBS:    return 0;
            case SPADES:   return 1;
            case DIAMONDS: return 2;
            case HEARTS:   return 3;
            default:       throw new IllegalStateException(String.format("Unknown CardSuit found: %s", suit.toString()));
        }
    }

    public static int getRank(CardHeight height) {
        switch (height) {
            case DEUCE: return 0;
            case THREE: return 1;
            case FOUR:  return 2;
            case FIVE:  return 3;
            case SIX:   return 4;
            case SEVEN: return 5;
            case EIGHT: return 6;
            case NINE:  return 7;
            case TEN:   return 8;
            case JACK:  return 9;
            case QUEEN: return 10;
            case KING:  return 11;
            case ACE:   return 12;
            default:    throw new RuntimeException(String.format("Unknown CardHeight: %s", height.toString()));
        }
    }

    public static int getCardCode(Card card) {
        return getSuiteCode(card.getSuit()) * CardHeight.values().length + getRank(card.getHeight());
    }

    public static Card getCard(int cardCode) {
        return new Card(getSuit(cardCode / CardHeight.values().length), getHeight(cardCode % CardHeight.values().length));
    }

    public static CardSuit getSuit(int suitCode) {
        for (CardSuit suit : CardSuit.values()) {
            if (getSuiteCode(suit) == suitCode) {
                return suit;
            }
        }
        throw new IllegalArgumentException(
                String.format("Couldn't find the corresponding CardSuit for the given suitCode: %d", suitCode)
        );
    }

    public static CardHeight getHeight(int rank) {
        for (CardHeight height : CardHeight.values()) {
            if (getRank(height) == rank) {
                return height;
            }
        }
        throw new IllegalArgumentException(
                String.format("Couldn't find the corresponding CardHeight for the given rank: %d", rank)
        );
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

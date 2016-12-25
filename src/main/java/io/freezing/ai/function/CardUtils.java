package io.freezing.ai.function;

import io.freezing.ai.domain.CardHeight;

public class CardUtils {
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
}

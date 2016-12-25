package io.freezing.ai.function;

import io.freezing.ai.domain.Card;
import io.freezing.ai.domain.CardHeight;
import io.freezing.ai.domain.CardSuit;
import io.freezing.ai.exception.parse.ParseException;

public class CardParseUtils {
    public static Card parseCard(String cardAsString) throws ParseException {
        if (cardAsString.length() != 2) {
            throw new ParseException(String.format("Parsing Card from string is expecting a string of length 2, but got: '%s'", cardAsString));
        }

        char suitChar = cardAsString.charAt(0);
        char heightChar = cardAsString.charAt(1);

        CardSuit suit = parseSuit(suitChar);
        CardHeight height = parseHeight(heightChar);
        return new Card(suit, height);
    }

    public static CardSuit parseSuit(char suitChar) throws ParseException {
        switch (suitChar) {
            case 'S': return CardSuit.SPADES;
            case 'H': return CardSuit.HEARTS;
            case 'C': return CardSuit.CLUBS;
            case 'D': return CardSuit.DIAMONDS;
            default : throw new ParseException(String.format("Unknown suit char: %c", suitChar));
        }
    }

    public static CardHeight parseHeight(char heightChar) throws ParseException {
        ParseException parseException = new ParseException(
                String.format("Expected char representing height, which is a character from: {2, 3, 4, 5, 6, 7, 8, 9, T, J, Q, K, A}, but got: %c", heightChar)
        );

        // If it's a number, parse the number and subtract 2.
        // This ensured that DEUCES -> 0, THREE -> 1, ..., NINE -> 7 (which represents CardRank)
        if (Character.isDigit(heightChar)) {
            int rank = Integer.parseInt(heightChar + "") - 2;
            if (rank >= 0 && rank <= 7) return CardUtils.getHeight(rank);
            else throw parseException;
        } else {
            switch (heightChar) {
                case 'T': return CardUtils.getHeight(8);
                case 'J': return CardUtils.getHeight(9);
                case 'Q': return CardUtils.getHeight(10);
                case 'K': return CardUtils.getHeight(11);
                case 'A': return CardUtils.getHeight(12);
                default : throw parseException;
            }
        }
    }
}

package io.freezing.ai.io.parser.impl;

import io.freezing.ai.domain.Card;
import io.freezing.ai.domain.Hand;
import io.freezing.ai.domain.PokerState;
import io.freezing.ai.domain.Table;
import io.freezing.ai.exception.parse.ParseException;
import io.freezing.ai.function.CardParseUtils;
import io.freezing.ai.io.parser.PokerInputParser;

import java.util.logging.Logger;

/**
 * Implementation of the PokerState parser. Format:
 * RoundNumber SmallBlind BigBlind TotalPot AmountToCall MyStack MyHand Table
 * RoundNumber  - Int
 * SmallBlind   - Int
 * BigBlind     - Int
 * TotalPot       - Int
 * AmountToCall - Int
 * MyStack      - Int
 * MyHand       - 2 x Card
 * Table        - 5 x Card
 *
 * Card         - <Suit><Height>
 * Suit         - Char from {S, H, C, D}
 * Height       - Char from {2, 3, 4, 5, 6, 7, 8, 9, T, J, Q, K, A}
 *
 * Example:
 * 1 50 100 250 100 1000 S12 D4 H4 HT DJ CA S7
 */
public class TexasHoldemInputParser implements PokerInputParser {
    private static final Logger logger = Logger.getLogger(TexasHoldemInputParser.class.getName());

    private static final int EXPECTED_TOKENS = 13;

    @Override
    public PokerState parse(String stateString) throws ParseException {
        // Split by whitespace
        String values[]  = stateString.split("\\w");

        if (values.length != 13) {
            throw new ParseException(
                    String.format("Expected %d tokens (split by whitespace), but got: %d in '%s'.", EXPECTED_TOKENS, values.length, stateString)
            );
        }

        logger.fine(String.format("Parsing state from String: %s", stateString));

        int roundNumber  = parseInt(values, 0);
        int smallBlind   = parseInt(values, 1);
        int bigBlind     = parseInt(values, 2);
        int totalPot     = parseInt(values, 3);
        int amountToCall = parseInt(values, 4);
        int myStack      = parseInt(values, 5);
        Hand myHand      = new Hand(parseCards(values, new int[] {6, 7}));
        Table table      = new Table(parseCards(values, new int[] {8, 9, 10, 11, 12}));

        PokerState state = new PokerState(roundNumber, smallBlind, bigBlind, table, totalPot, amountToCall, myStack, myHand);
        logger.info(String.format("Parsed state: %s", state.toString()));
        return state;
    }

    private int parseInt(String values[], int idx) throws ParseException {
        try {
            return Integer.parseInt(values[idx]);
        } catch (NumberFormatException e) {
            throw new ParseException(String.format("Parsing failed at index: %d. Message: %s.", idx, e.getMessage()));
        }
    }

    private Card[] parseCards(String values[], int[] indexes) throws ParseException {
        Card[] cards = new Card[indexes.length];
        for (int i = 0; i < indexes.length; i++) {
            cards[i] = parseCard(values, indexes[i]);
        }
        return cards;
    }

    private Card parseCard(String values[], int idx) throws ParseException {
        try {
            return CardParseUtils.parseCard(values[idx]);
        } catch (ParseException e) {
            throw new ParseException(String.format("Parsing failed at index: %d. Message: %s.", idx, e.getMessage()));
        }
    }
}

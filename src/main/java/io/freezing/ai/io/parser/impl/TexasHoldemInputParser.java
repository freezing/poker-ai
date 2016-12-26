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
 * RoundNumber TotalNumberOfPlayers SmallBlind BigBlind TotalPot AmountToCall MyStack MyHand Table
 *
 * RoundNumber          - Int
 * TotalNumberOfPlayers - Int
 * SmallBlind           - Int
 * BigBlind             - Int
 * TotalPot             - Int
 * AmountToCall         - Int
 * MyStack              - Int
 * MyHand               - 2 x Card
 * Table                - 5 x Card
 *
 * Card         - <Suit><Height>
 * Suit         - Char from {S, H, C, D}
 * Height       - Char from {2, 3, 4, 5, 6, 7, 8, 9, T, J, Q, K, A}
 *
 * Example:
 * 1 5 50 100 250 100 1000 S12 D4 H4 HT DJ CA S7
 */
public class TexasHoldemInputParser implements PokerInputParser {
    private static final Logger logger = Logger.getLogger(TexasHoldemInputParser.class.getName());

    private static final int MIN_EXPECTED_TOKENS = 19;
    private static final int MAX_EXPECTED_TOKENS = 14;

    @Override
    public PokerState parse(String stateString) throws ParseException {
        // Split by whitespace
        String values[]  = stateString.trim().split("\\s");

        if (MIN_EXPECTED_TOKENS <= values.length && values.length <= MAX_EXPECTED_TOKENS) {
            throw new ParseException(
                    String.format("Expected from %d to %d tokens (split by whitespace), but got: %d in '%s'.", MIN_EXPECTED_TOKENS, MAX_EXPECTED_TOKENS, values.length, stateString)
            );
        }

        logger.fine(String.format("Parsing state from String: %s", stateString));

        int nextIdx = 0;

        int roundNumber             = parseInt(values, nextIdx++);
        int totalNumberOfPlayers    = parseInt(values, nextIdx++);
        int smallBlind              = parseInt(values, nextIdx++);
        int bigBlind                = parseInt(values, nextIdx++);
        int totalPot                = parseInt(values, nextIdx++);
        int amountToCall            = parseInt(values, nextIdx++);
        int myStack                 = parseInt(values, nextIdx++);

        Hand myHand                 = new Hand(parseCards(values, new int[] {nextIdx, nextIdx + 1}));
        nextIdx += 2;

        // From here, remaining cards are Table cards; there can be from 0 to 5 cards
        int[] tableIndexes = new int[values.length - nextIdx];
        for (int i = 0; i < tableIndexes.length; i++) tableIndexes[i] = nextIdx + i;
        Table table                       = new Table(parseCards(values, tableIndexes));
        // nextIdx += tableIndexes.length;

        PokerState state = new PokerState(roundNumber, totalNumberOfPlayers, smallBlind, bigBlind, table, totalPot, amountToCall, myStack, myHand);
        logger.info(String.format("Parsed state: %s", state.toString()));
        return state;
    }

    @Override
    public String getFormat() {
        return "<RoundNumber> <TotalNumberOfPlayers> <SmallBlind> <BigBlind> <TotalPot> <AmountToCall> <MyStack> <MyHand> <Table>";
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

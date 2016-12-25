package io.freezing.ai.io.parser.impl;

import io.freezing.ai.domain.PokerState;
import io.freezing.ai.io.parser.PokerInputParser;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Implementation of the PokerState parser. Format:
 * RoundNumber SmallBlind BigBlind MaxPot AmountToCall MyStack MyHand Table
 * RoundNumber  - Int
 * SmallBlind   - Int
 * BigBlind     - Int
 * MaxPot       - Int
 * AmountToCall - Int
 * MyStack      - Int
 * MyHand       - 2 x Card
 * Table        - 5 x Card
 * Card         - <Char><Int>, where first Char represents CardSuit {S, H, C, D}; Second is card height or rank
 */
public class TexasHoldemInputParser implements PokerInputParser {
    @Override
    public PokerState parse(String stateString) {
        throw new NotImplementedException();
    }
}

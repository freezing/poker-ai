package io.freezing.ai.function;

import io.freezing.ai.domain.Table;
import io.freezing.ai.domain.TexasHoldEmRoundState;

public class TexasHoldEmTableUtils {
    public static TexasHoldEmRoundState getRoundState(Table table) {
        if (table.getCards().length == 0) return TexasHoldEmRoundState.PRE_FLOP;
        else if (table.getCards().length == 3) return TexasHoldEmRoundState.FLOP;
        else if (table.getCards().length == 4) return TexasHoldEmRoundState.TURN;
        else if (table.getCards().length == 5) return TexasHoldEmRoundState.RIVER;
        else throw new IllegalArgumentException(String.format("Unknown Texas Hold'em Round State for table of length: %d", table.getCards().length));
    }
}

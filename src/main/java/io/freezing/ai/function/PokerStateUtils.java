package io.freezing.ai.function;

import io.freezing.ai.domain.Card;
import io.freezing.ai.domain.PokerState;
import io.freezing.ai.exception.input.DuplicateCardFoundException;
import io.freezing.ai.exception.input.PokerInputException;
import io.freezing.ai.rules.impl.texasholdem.TexasHoldEmRules;

public class PokerStateUtils {
    public static void validateNoDuplicates(PokerState state) throws PokerInputException {
        Card[] all = CardUtils.merge(state.getTable(), state.getMyHand());

        // TODO: Here too, assumption is 52 total cards, but this will fail if not true anyway so it's fine for now
        boolean[] codesFound = new boolean[52];
        for (Card c : all) {
            int cardCode = CardUtils.getCardCode(c);
            if (codesFound[cardCode]) {
                throw new DuplicateCardFoundException(c, state.getTable(), state.getMyHand());
            } else {
                codesFound[cardCode] = true;
            }
        }
    }

    private static void validateMaxPlayers(PokerState state, int maxAllowedPlayers) {

    }

    public static void validateTexasHoldEmPokerState(PokerState state, TexasHoldEmRules rules) throws PokerInputException {
        validateNoDuplicates(state);
        validateMaxPlayers(state, rules.getMaxNumberOfPlayers());
    }
}

package io.freezing.ai.rules.impl.texasholdem;

import io.freezing.ai.domain.*;
import io.freezing.ai.rules.HandEvaluator;

import java.util.ArrayList;
import java.util.List;

public class TexasHoldEmHandEvaluator implements HandEvaluator {
    private static final int TABLE_PICK_COUNT = 3;

    private final TexasHoldEmWholeHandRanker ranker;

    public TexasHoldEmHandEvaluator() {
        this.ranker = new TexasHoldEmWholeHandRanker();
    }

    @Override
    public EvaluatedHand evaluate(Hand hand, Table table) {
        return findBest(hand, table, 0, new ArrayList<>());
    }

    private EvaluatedHand findBest(Hand hand, Table table, int current, List<Integer> picked) {
        if (picked.size() == TABLE_PICK_COUNT) {
            WholeHand wholeHand = getWholeHand(hand, table, picked);
            WholeHandRank wholeHandRank = this.ranker.calculateRank(wholeHand);
            return new EvaluatedHand(wholeHand, wholeHandRank);
        } else if (current >= table.getCards().length) {
            return null;
        } else {
            // Find best don't pick current
            EvaluatedHand h1 = findBest(hand, table, current + 1, picked);

            // Find best after picking the current
            picked.add(current);
            EvaluatedHand h2 = findBest(hand, table, current + 1, picked);
            picked.remove(picked.size() - 1);

            // Find EvaluatedHand that is higher
            if (h1 == null && h2 == null) return null;
            else if (h1 == null) return h2;
            else if (h2 == null) return h1;
            else if (h1.compareTo(h2) == 1) return h1;
            else return h2;
        }
    }

    private WholeHand getWholeHand(Hand hand, Table table, List<Integer> picked) {
        if (picked.size() != TABLE_PICK_COUNT) {
            throw new IllegalStateException(String.format("This is probably a bug. Picked: %d, but expected: %d.", picked.size(), TABLE_PICK_COUNT));
        }

        Card[] tablePicks = new Card[picked.size()];
        for (int i = 0; i < tablePicks.length; i++) {
            tablePicks[i] = table.getCards()[ picked.get(i) ];
        }
        return new WholeHand(hand, tablePicks);
    }
}

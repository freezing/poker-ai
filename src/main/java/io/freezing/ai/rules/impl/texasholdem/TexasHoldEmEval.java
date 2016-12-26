package io.freezing.ai.rules.impl.texasholdem;

import io.freezing.ai.domain.HandCategory;

import java.util.HashMap;
import java.util.Map;

public class TexasHoldEmEval {
    private static final Map<Long, Integer> CATEGORY_RANK_CODES;
    private static final long STRAIGHT_FLUSH_PATTERNS[];

    static {
        CATEGORY_RANK_CODES = new HashMap<>();
        STRAIGHT_FLUSH_PATTERNS = createStraightFlushPatterns();
    }

    public static int evaluate(long hand) {
        // Try STRAIGHT_FLUSH
        for (long p : STRAIGHT_FLUSH_PATTERNS) {
            if ((p & hand) == p) {
                return getRank(hand, p, HandCategory.STRAIGHT_FLUSH);
            }
        }

        // No pattern found, therefore it's NO_PAIR
        return getRank(hand, 0, HandCategory.NO_PAIR);
    }

    private static int getRank(long hand, long categoryPattern, HandCategory handCategory) {
        int categoryCode = getCategoryCode(handCategory);

        // Evaluation result is 32 bits = 0x0VPPKKKK,
        // where P represent category strength
        // K represents the kicker code.
        // Therefore, encode actual category rank by shifting it by 16
        int categoryRankCode = CATEGORY_RANK_CODES.get(categoryPattern) << 16;

        // Remove cards that determine the category
        long leftovers = hand ^ categoryPattern;

        // Remove suits so that we are only left with numbers.
        // Note that in this state all the numbers are going to be different,
        // otherwise they would have been used in the category pattern.
        // To keep only numbers, basically shift the whole number by (0, 16, 32, 48) and take first 16 bits
        int kickerCode = 0;
        for (int i = 0; i < 4; i++) {
            // Could also use 0x1FFF since it represents 13 bits,
            // but for clarity, lets keep 0xFFFF, which represents the group of 16 bits
            kickerCode |= (leftovers >> (16 * i)) & 0xFFFF;
        }

        return categoryCode | categoryRankCode | kickerCode;
    }

    private static long[] createStraightFlushPatterns() {
        // There are 10 straight flushes per suit
        long patterns[] = new long[40];

        // Special case is straight to the five with 12th bit active and the first 4
        patterns[0] = 0x100F; // 0001000000001111
        // Straight to the five has rank 5
        CATEGORY_RANK_CODES.put(patterns[0], 5);

        long initial = 0x1F;  // 0000000000011111
        for (int i = 1; i < 10; i++) {
            // Shift initial (i - 1) times to get mask for each straight flush
            patterns[i] = initial << (i - 1);
            // It starts with strength 6
            CATEGORY_RANK_CODES.put(patterns[i], 7 - i);
        }

        // Copy the same patterns for each suit (shift 16, 32 and 48 times depending on the suit)
        for (int i = 10; i < 40; i++) {
            int suitIndex = i / 10;
            patterns[i] = initial << (i - 1 + suitIndex * 16);
            int corresponding = CATEGORY_RANK_CODES.get(patterns[i - 10]);
            CATEGORY_RANK_CODES.put(patterns[i], corresponding);
        }

        return patterns;
    }

    /**
     * Evaluation result is 32 bits = 0x0V000000 where V is hex-digit.
     * V represents the category code (which is a value from 0 to 8).
     * There are 6 less-significant hex-digits before V, which is 6 x 4 = 24 binary digits.
     * Therefore, it is important to shift the ordinal by 24.
     */
    private static int getCategoryCode(HandCategory handCategory) {
        return handCategory.ordinal() << 24;
    }
}

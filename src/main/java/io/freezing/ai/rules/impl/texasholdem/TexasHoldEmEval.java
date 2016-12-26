package io.freezing.ai.rules.impl.texasholdem;

import io.freezing.ai.domain.CardSuit;
import io.freezing.ai.domain.HandCategory;

import java.util.*;

public class TexasHoldEmEval {
    private static final Map<Long, Integer> CATEGORY_RANK_CODES;
    private static final long STRAIGHT_FLUSH_PATTERNS[];
    private static final long FOUR_OF_A_KIND_PATTERNS[];
    private static final long FULL_HOUSE_PATTERNS[];
    private static final long THREE_OF_A_KIND_PATTERNS[];
    private static final long PAIR_PATTERNS[];

    private static final Map<CardSuit, Long> SHIFTS;
    static {
        SHIFTS = new HashMap<>();
        SHIFTS.put(CardSuit.CLUBS, 0L);
        SHIFTS.put(CardSuit.DIAMONDS, 16L);
        SHIFTS.put(CardSuit.HEARTS, 32L);
        SHIFTS.put(CardSuit.SPADES, 48L);
    }

    static {
        CATEGORY_RANK_CODES = new HashMap<>();
        STRAIGHT_FLUSH_PATTERNS = createStraightFlushPatterns();
        FOUR_OF_A_KIND_PATTERNS = createFourOfAKindPatterns();
        FULL_HOUSE_PATTERNS = createFullHousePatterns();
        THREE_OF_A_KIND_PATTERNS = createThreeOfAKindPatterns();
        PAIR_PATTERNS = createPairPatterns();
    }

    public static int evaluate(long hand) {
        // Try STRAIGHT_FLUSH
        Optional<Integer> straightFlushOpt = findAndEvaluate(hand, HandCategory.STRAIGHT_FLUSH, STRAIGHT_FLUSH_PATTERNS);
        if (straightFlushOpt.isPresent()) return straightFlushOpt.get();

        // Try FOUR_OF_A_KIND
        Optional<Integer> fourKindOpt = findAndEvaluate(hand, HandCategory.FOUR_OF_A_KIND, FOUR_OF_A_KIND_PATTERNS);
        if (fourKindOpt.isPresent()) return fourKindOpt.get();

        // Try FULL_HOUSE
        Optional<Integer> fullHouseOpt = findAndEvaluate(hand, HandCategory.FULL_HOUSE, FULL_HOUSE_PATTERNS);
        if (fullHouseOpt.isPresent()) return fullHouseOpt.get();

        // Try FLUSH - it's very specific logic, we don't have anything preprocessed
        Optional<Integer> flushOpt = tryFlush(hand);
        if (flushOpt.isPresent()) return flushOpt.get();

        // Try STRAIGHT - it's very specific logic, we don't have anything preprocessed
        Optional<Integer> straightOpt = tryStraight(hand);
        if (straightOpt.isPresent()) return straightOpt.get();

        // Try THREE_OF_A_KIND
        Optional<Integer> threeKindOpt = findAndEvaluate(hand, HandCategory.THREE_OF_A_KIND, THREE_OF_A_KIND_PATTERNS);
        if (threeKindOpt.isPresent()) return threeKindOpt.get();

        

        // Try PAIR
        Optional<Integer> pairOpt = findAndEvaluate(hand, HandCategory.PAIR, PAIR_PATTERNS);
        if (pairOpt.isPresent()) return pairOpt.get();

        // No pattern found, therefore it's NO_PAIR
        return getRank(hand, 0, HandCategory.NO_PAIR);
    }

    private static Optional<Integer> findAndEvaluate(long hand, HandCategory handCategory, long[] patterns) {
        for (long p : patterns) {
            if ((p & hand) == p) {
                return Optional.of(getRank(hand, p, handCategory));
            }
        }
        return Optional.empty();
    }

    private static int getRank(long hand, long categoryPattern, HandCategory handCategory) {
        int categoryCode = getCategoryCode(handCategory);

        // Evaluation result is 32 bits = 0x0V0PPKKK,
        // where P represent category strength
        // K represents the kicker code.
        // Therefore, encode actual category rank by shifting it by 16, except when it's FULL_HOUSE, then shift by 12,
        // because it requires more 3 x P and 2 x K.
        int categoryRankCode = CATEGORY_RANK_CODES.get(categoryPattern) << 12;
        if (handCategory != HandCategory.FULL_HOUSE) categoryRankCode <<= 4;

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

    private static long[] createPairPatterns() {
        List<Long> patterns = new ArrayList<>();
        int suitsLength = CardSuit.values().length;

        for (int cardNum = 0; cardNum < 13; cardNum++) {
            for (int s1 = 0; s1 < suitsLength; s1++) {
                for (int s2 = s1 + 1; s2 < suitsLength; s2++) {
                    long c1 = createCardBitmask(cardNum, CardSuit.values()[s1]);
                    long c2 = createCardBitmask(cardNum, CardSuit.values()[s2]);

                    long pattern = c1 | c2;
                    patterns.add(pattern);
                    CATEGORY_RANK_CODES.put(pattern, cardNum);
                }
            }
        }

        long[] patternsArray = new long[patterns.size()];
        for (int i = 0; i < patternsArray.length; i++) patternsArray[i] = patterns.get(i);
        return patternsArray;
    }

    private static long[] createThreeOfAKindPatterns() {
        List<Long> patterns = new ArrayList<>();
        int suitsLength = CardSuit.values().length;

        for (int cardNum = 0; cardNum < 13; cardNum++) {
            for (int s1 = 0; s1 < suitsLength; s1++) {
                for (int s2 = s1 + 1; s2 < suitsLength; s2++) {
                    for (int s3 = s2 + 1; s3 < suitsLength; s3++) {
                        long c1 = createCardBitmask(cardNum, CardSuit.values()[s1]);
                        long c2 = createCardBitmask(cardNum, CardSuit.values()[s2]);
                        long c3 = createCardBitmask(cardNum, CardSuit.values()[s3]);

                        long pattern = c1 | c2 | c3;
                        // Strength is the cardNum
                        CATEGORY_RANK_CODES.put(pattern, cardNum);
                        patterns.add(pattern);
                    }
                }
            }
        }

        long[] patternsArray = new long[patterns.size()];
        for (int i = 0; i < patterns.size(); i++) patternsArray[i] = patterns.get(i);
        return patternsArray;
    }

    private static Optional<Integer> tryStraight(long hand) {
        int categoryCode = getCategoryCode(HandCategory.STRAIGHT);

        // Fold hand to represent only the numbers
        int handNumbers = 0;
        for (int i = 0; i < 4; i++) {
            handNumbers |= (int)((hand << (i * 16)) & 0xFFFF);
        }

        // Create STRAIGHT patterns and strengths
        int[] patterns = new int[10];
        int[] strengths = new int[patterns.length];
        // Special case is A,2,3,4,5, 12th bit active and the first 4
        patterns[0] = 0x100F; // 0001000000001111
        // Straight to the five has rank 5
        strengths[0] = 5;

        int initial = 0x1F;  // 0000000000011111
        for (int i = 1; i < 10; i++) {
            // Shift initial (i - 1) times to get mask for each straight
            patterns[i] = initial << (i - 1);
            // It starts with strength 6
            strengths[i] = 7 - i;
        }

        // Check if folded hand satisfies any of the patterns
        for (int i = 0; i < patterns.length; i++) {
            if ((handNumbers & patterns[i]) == patterns[i]) {
                // Kicker code is the same as strength
                return Optional.of(categoryCode | strengths[i]);
            }
        }

        return Optional.empty();
    }

    private static Optional<Integer> tryFlush(long hand) {
        int categoryCode = getCategoryCode(HandCategory.FLUSH);

        // Count number of bits in the suit (in 16 bit group). If it's >= 5 then it's FLUSH
        // Strength is the same as kicker code in this case because there are no additional cards,
        // therefore just use what's left of hand
        for (int i = 0; i < 4; i++) {
            int handNumbers = (int)((hand << (i * 16)) & 0xFFFF);
            int ones = countSetBits(handNumbers);
            if (ones >= 5) {
                // Delete ones starting from the least-significant bit, until there are exactly 5 left
                for (int bit = 0; bit < 13 && ones > 5; bit++) {
                    // Remove bit-th bit
                    int removePattern = ~(1 << bit); // All ones except for bit-th bit
                    handNumbers &= removePattern;
                    ones = countSetBits(handNumbers);
                }

                if (ones != 5) throw new IllegalStateException(String.format("Expected that number of ones in hand: %d is 5 but got: %d", handNumbers, ones));

                return Optional.of(categoryCode | handNumbers);
            }
        }

        return Optional.empty();
    }

    private static int countSetBits(int n) {
        n = n - ((n >> 1) & 0x55555555);
        n = (n & 0x33333333) + ((n >> 2) & 0x33333333);
        n = (n + (n >> 4)) & 0x0F0F0F0F;
        n = n + (n >> 8);
        n = n + (n >> 16);
        return n & 0x0000003F;
    }


    private static long[] createFullHousePatterns() {
        // There are 13 * 12 * 6 patterns like this.
        int suitsLength = CardSuit.values().length;

        List<Long> patterns = new ArrayList<>();

        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 13; j++) {
                if (i == j) continue;

                for (int si1 = 0; si1 < suitsLength; si1++) {
                    for (int si2 = 0; si2 < suitsLength; si2++) {
                        if (si1 == si2) continue;
                        for (int si3 = 0; si3 < suitsLength; si3++) {
                            if (si3 == si1 || si3 == si2) continue;
                            // Now create pattern for FULL_HOUSE using card i 3 times, and card j 2 times
                            long c1 = createCardBitmask(i, CardSuit.values()[si1]);
                            long c2 = createCardBitmask(i, CardSuit.values()[si2]);
                            long c3 = createCardBitmask(i, CardSuit.values()[si3]);

                            // Find other 2 cards
                            for (int sj1 = 0; sj1 < suitsLength; sj1++) {
                                for (int sj2 = 0; sj2 < suitsLength; sj2++) {
                                    if (sj1 == sj2) continue;
                                    long c4 = createCardBitmask(j, CardSuit.values()[sj1]);
                                    long c5 = createCardBitmask(j, CardSuit.values()[sj2]);

                                    long pattern = c1 | c2 | c3 | c4 | c5;
                                    int strength = (i << 4) | j;
                                    patterns.add(pattern);
                                    CATEGORY_RANK_CODES.put(pattern, strength);
                                }
                            }
                        }
                    }
                }
            }
        }

        long[] patternsArray = new long[patterns.size()];
        for (int i = 0; i < patterns.size(); i++) patternsArray[i] = patterns.get(i);
        return patternsArray;
    }

    private static long[] createFourOfAKindPatterns() {
        // There are 13 patterns like this.
        // Take a pattern with single card (1 bit in range [0, 12]) and copy it 4 times
        long patterns[] = new long[13];

        for (int i = 0; i < 13; i++) {
            long cardNumber = 1L << i;
            patterns[i] = cardNumber | (cardNumber << 16) | (cardNumber << 32) | (cardNumber << 48);
            // Strength of the pattern is the value of a kind
            CATEGORY_RANK_CODES.put(patterns[i], i);
        }

        return patterns;
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

    public static long createCardBitmask(int cardNumber, CardSuit suit) {
        return (1L << cardNumber) << SHIFTS.get(suit);
    }

    public static void main(String[] args) {
        System.out.println(countSetBits(15)); // 4
        System.out.println(countSetBits(8)); // 1
        System.out.println(countSetBits(2147483150)); // 25
        System.out.println(countSetBits(17920377)); // 12
    }
}

package io.freezing.ai.performance;

import io.freezing.ai.domain.Card;
import io.freezing.ai.domain.EvaluatedHand;
import io.freezing.ai.domain.Hand;
import io.freezing.ai.domain.Table;
import io.freezing.ai.function.CardUtils;
import io.freezing.ai.function.RandomUtils;
import io.freezing.ai.rules.HandEvaluator;
import io.freezing.ai.rules.impl.texasholdem.TexasHoldEmHandEvaluator;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TexasHoldEmEvaluatorSpeedTest {
    static private final HandEvaluator evaluator = new TexasHoldEmHandEvaluator();
    static private final Random rnd = new Random(1453243L);

    static private final Card[] cards = new Card[52];
    static private final Hand hands[] = new Hand[1000];
    static private final Table tables[] = new Table[hands.length];
    static private final int EVALUATIONS = 1000000;

    static {
        for (int i = 0; i < cards.length; i++) cards[i] = CardUtils.getCard(i);

        for (int i = 0; i < hands.length; i++) {
            RandomUtils.shuffleArray(cards, rnd, 2 * cards.length);
            hands[i] = new Hand(new Card[]{ cards[0], cards[1] });
            tables[i] = new Table(new Card[]{ cards[2], cards[3], cards[4], cards[5], cards[6] });
        }
    }

    @Test
    public void speedTestSequential() {
        System.out.println("Performance testing using sequential iteration...");
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < EVALUATIONS; i++) {
            int idx = rnd.nextInt(hands.length);
            EvaluatedHand evalHand = evaluator.evaluate(hands[idx], tables[idx]);
            Assert.assertTrue(evalHand.getRank() > 0);
        }
        long endTime = System.currentTimeMillis();

        long totalTimeMs = endTime - startTime;
        double totalSeconds = totalTimeMs / 1000.0;
        double evaluationsPerSecond = EVALUATIONS / totalSeconds;

        System.out.println(String.format("Total time to evaluate %d hands: %f", EVALUATIONS, totalSeconds));
        System.out.println(String.format("Evaluations per second: %f", evaluationsPerSecond));
        Assert.assertTrue(evaluationsPerSecond > 100000);
    }

    @Test
    public void speedTestParallelStream() {
        System.out.println("Performance testing using parallel stream...");
        long startTime = System.currentTimeMillis();
        List<EvaluatedHand> evalHands = Stream.iterate(0, a -> a + 1).limit(EVALUATIONS).parallel().map(i -> {
            int idx = rnd.nextInt(hands.length);
            return evaluator.evaluate(hands[idx], tables[idx]);
        }).collect(Collectors.toList());
        Assert.assertTrue(evalHands.size() == EVALUATIONS);
        long endTime = System.currentTimeMillis();

        long totalTimeMs = endTime - startTime;
        double totalSeconds = totalTimeMs / 1000.0;
        double evaluationsPerSecond = EVALUATIONS / totalSeconds;

        System.out.println(String.format("Total time to evaluate %d hands: %f", EVALUATIONS, totalSeconds));
        System.out.println(String.format("Evaluations per second: %f", evaluationsPerSecond));
        Assert.assertTrue(evaluationsPerSecond > 300000);
    }
}

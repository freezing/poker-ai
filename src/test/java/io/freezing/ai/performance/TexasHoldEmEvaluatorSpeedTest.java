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

import java.util.Random;

public class TexasHoldEmEvaluatorSpeedTest {
    private HandEvaluator evaluator = new TexasHoldEmHandEvaluator();
    private Random rnd = new Random(1453243L);

    @Test
    public void speedTest() {
        Hand hands[] = new Hand[1000];
        Table tables[] = new Table[hands.length];

        Card[] cards = new Card[52];
        for (int i = 0; i < cards.length; i++) cards[i] = CardUtils.getCard(i);

        for (int i = 0; i < hands.length; i++) {
            RandomUtils.shuffleArray(cards, rnd, 2 * cards.length);
            hands[i] = new Hand(new Card[]{ cards[0], cards[1] });
            tables[i] = new Table(new Card[]{ cards[2], cards[3], cards[4], cards[5], cards[6] });
        }

        final int EVALUATIONS = 1000000;

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
    }
}

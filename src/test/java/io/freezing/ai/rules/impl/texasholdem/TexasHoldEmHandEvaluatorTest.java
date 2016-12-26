package io.freezing.ai.rules.impl.texasholdem;

import io.freezing.ai.domain.Card;
import io.freezing.ai.domain.EvaluatedHand;
import io.freezing.ai.domain.Hand;
import io.freezing.ai.domain.Table;
import io.freezing.ai.exception.parse.ParseException;
import io.freezing.ai.function.CardParseUtils;
import org.junit.Assert;
import org.junit.Test;

public class TexasHoldEmHandEvaluatorTest {
    private TexasHoldEmHandEvaluator evaluator = new TexasHoldEmHandEvaluator();

    @Test
    public void testStraightFlushOrdering() throws ParseException {
        Table table = new Table(new Card[] {
                CardParseUtils.parseCard("H6"),
                CardParseUtils.parseCard("H7"),
                CardParseUtils.parseCard("H8"),
                CardParseUtils.parseCard("H9"),
                CardParseUtils.parseCard("HT")
        });

        Hand hand = new Hand(new Card[] {
                CardParseUtils.parseCard("CA"),
                CardParseUtils.parseCard("DA")
        });

        Hand hand2 = new Hand(new Card[] {
                CardParseUtils.parseCard("HJ"),
                CardParseUtils.parseCard("DQ")
        });

        Hand hand3 = new Hand(new Card[] {
                CardParseUtils.parseCard("HJ"),
                CardParseUtils.parseCard("HQ")
        });

        EvaluatedHand rank = evaluator.evaluate(hand, table);
        EvaluatedHand rank2 = evaluator.evaluate(hand2, table);
        EvaluatedHand rank3 = evaluator.evaluate(hand3, table);

        Assert.assertTrue(rank.getRank() < rank2.getRank());
        Assert.assertTrue(rank2.getRank() < rank3.getRank());
    }

    @Test
    public void testFourOfAKindOrdering() throws ParseException {
        Table table = new Table(new Card[] {
                CardParseUtils.parseCard("SA"), // Ace  of Spades
                CardParseUtils.parseCard("ST"), // Ten  of Spades
                CardParseUtils.parseCard("DA"), // Ace  of Diamonds
                CardParseUtils.parseCard("CA"), // Ace  of Clubs
                CardParseUtils.parseCard("CT")  // Ten  of Clubs
        });

        // Four of a kind (A)
        Hand handA1 = new Hand(new Card[] {
                CardParseUtils.parseCard("HA"), // Ace of Hearts
                CardParseUtils.parseCard("H2")  // Two of Hearts
        });

        Hand handA2 = new Hand(new Card[] {
                CardParseUtils.parseCard("HA"), // Ace   of Hearts
                CardParseUtils.parseCard("H3")  // Three of Hearts
        });

        Hand handA3 = new Hand(new Card[] {
                CardParseUtils.parseCard("HA"), // Ace of Hearts
                CardParseUtils.parseCard("DT")  // Ten of Diamonds
        });

        Hand handA4 = new Hand(new Card[] {
                CardParseUtils.parseCard("HA"), // Ace  of Hearts
                CardParseUtils.parseCard("DK")  // King of Diamonds
        });

        EvaluatedHand evalHandA1 = evaluator.evaluate(handA1, table);
        EvaluatedHand evalHandA2 = evaluator.evaluate(handA2, table);
        EvaluatedHand evalHandA3 = evaluator.evaluate(handA3, table);
        EvaluatedHand evalHandA4 = evaluator.evaluate(handA4, table);

        Assert.assertTrue(evalHandA1.getRank() == evalHandA2.getRank());
        Assert.assertTrue(evalHandA2.getRank() == evalHandA3.getRank());
        Assert.assertTrue(evalHandA3.getRank() < evalHandA4.getRank());

        // Four of a kind (T)
        Hand handT1 = new Hand(new Card[] {
                CardParseUtils.parseCard("DT"),
                CardParseUtils.parseCard("HT")
        });

        EvaluatedHand evalHandT1 = evaluator.evaluate(handT1, table);

        // Make sure that Four of A Kind(T) is less than all Four of a Kind (A)
        Assert.assertTrue(evalHandT1.getRank() < evalHandA1.getRank());
        Assert.assertTrue(evalHandT1.getRank() < evalHandA2.getRank());
        Assert.assertTrue(evalHandT1.getRank() < evalHandA3.getRank());
        Assert.assertTrue(evalHandT1.getRank() < evalHandA4.getRank());
    }
}

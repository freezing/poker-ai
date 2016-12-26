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
                CardParseUtils.parseCard("H4")  // Four of Hearts
        });

        Hand hand1 = new Hand(new Card[] {
                CardParseUtils.parseCard("HA"), // Ace of Hearts
                CardParseUtils.parseCard("H2")  // Two of Hearts
        });

        Hand hand2 = new Hand(new Card[] {
                CardParseUtils.parseCard("HA"), // Ace of Hearts
                CardParseUtils.parseCard("H3")  // Three of Hearts
        });

        Hand hand3 = new Hand(new Card[] {
                CardParseUtils.parseCard("HA"), // Ace of Hearts
                CardParseUtils.parseCard("HT")  // Ten of Hearts
        });

        Hand hand4 = new Hand(new Card[] {
                CardParseUtils.parseCard("HA"), // Ace  of Hearts
                CardParseUtils.parseCard("DK")  // King of Diamonds
        });

        EvaluatedHand evalHand1 = evaluator.evaluate(hand1, table);
        EvaluatedHand evalHand2 = evaluator.evaluate(hand2, table);
        EvaluatedHand evalHand3 = evaluator.evaluate(hand3, table);
        EvaluatedHand evalHand4 = evaluator.evaluate(hand4, table);

        System.out.println(evalHand1);
        System.out.println(evalHand2);
        System.out.println(evalHand3);
        System.out.println(evalHand4);

        Assert.assertTrue(evalHand1.getRank() == evalHand2.getRank());
        Assert.assertTrue(evalHand2.getRank() < evalHand3.getRank());
        Assert.assertTrue(evalHand3.getRank() < evalHand4.getRank());
    }
}

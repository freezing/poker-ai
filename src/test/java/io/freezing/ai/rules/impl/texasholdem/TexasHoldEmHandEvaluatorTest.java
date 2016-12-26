package io.freezing.ai.rules.impl.texasholdem;

import io.freezing.ai.domain.Card;
import io.freezing.ai.domain.EvaluatedHand;
import io.freezing.ai.domain.Hand;
import io.freezing.ai.domain.Table;
import io.freezing.ai.exception.parse.ParseException;
import io.freezing.ai.function.CardParseUtils;
import org.junit.Test;

public class TexasHoldEmHandEvaluatorTest {
    private TexasHoldEmHandEvaluator evaluator = new TexasHoldEmHandEvaluator();

    @Test
    public void testSomething() throws ParseException {
        Hand hand = new Hand(new Card[] {
                CardParseUtils.parseCard("CA"),
                CardParseUtils.parseCard("DA")
        });

        Table table = new Table(new Card[] {
                CardParseUtils.parseCard("H6"),
                CardParseUtils.parseCard("H7"),
                CardParseUtils.parseCard("H8"),
                CardParseUtils.parseCard("H9"),
                CardParseUtils.parseCard("HT")
        });

        Hand hand2 = new Hand(new Card[] {
                CardParseUtils.parseCard("HJ"),
                CardParseUtils.parseCard("DQ")
        });

        Hand hand3 = new Hand(new Card[] {
                CardParseUtils.parseCard("HJ"),
                CardParseUtils.parseCard("HQ")
        });

//        EvaluatedHand rank = evaluator.evaluate(hand, table);
        EvaluatedHand rank2 = evaluator.evaluate(hand2, table);
//        EvaluatedHand rank3 = evaluator.evaluate(hand3, table);

//        System.out.println(rank);
        System.out.println(rank2);
//        System.out.println(rank3);
    }
}

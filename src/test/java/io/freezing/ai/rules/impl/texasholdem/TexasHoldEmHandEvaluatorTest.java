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

    @Test
    public void testFullHouseOrdering() throws ParseException {
        Table table = new Table(new Card[] {
                CardParseUtils.parseCard("SJ"), // Jack of Spades
                CardParseUtils.parseCard("ST"), // Ten  of Spades
                CardParseUtils.parseCard("DA"), // Ace  of Diamonds
                CardParseUtils.parseCard("CA"), // Ace  of Clubs
                CardParseUtils.parseCard("H4")  // Four of Hearts
        });

        // Full House (A-4)
        Hand handA41 = new Hand(new Card[] {
                CardParseUtils.parseCard("HA"), // Ace  of Hearts
                CardParseUtils.parseCard("C4")  // Four of Clubs
        });

        // Full House (A-4)
        Hand handA42 = new Hand(new Card[] {
                CardParseUtils.parseCard("HA"), // Ace  of Hearts
                CardParseUtils.parseCard("S4")  // Four of Spades
        });

        // Full House (A-T)
        Hand handAT = new Hand(new Card[] {
                CardParseUtils.parseCard("HA"), // Ace  of Hearts
                CardParseUtils.parseCard("CT")  // Ten  of Clubs
        });

        // Full House (A-J)
        Hand handAJ = new Hand(new Card[] {
                CardParseUtils.parseCard("HA"), // Ace  of Hearts
                CardParseUtils.parseCard("CJ")  // Jack of Clubs
        });

        EvaluatedHand evalHandA41 = evaluator.evaluate(handA41, table);
        EvaluatedHand evalHandA42 = evaluator.evaluate(handA42, table);
        EvaluatedHand evalHandAT = evaluator.evaluate(handAT, table);
        EvaluatedHand evalHandAJ = evaluator.evaluate(handAJ, table);

        Assert.assertTrue(evalHandA41.getRank() == evalHandA42.getRank());
        Assert.assertTrue(evalHandA41.getRank() < evalHandAT.getRank());
        Assert.assertTrue(evalHandAT.getRank() < evalHandAJ.getRank());
    }

    @Test
    public void testFlushOrdering() throws ParseException {
        Table table = new Table(new Card[] {
                CardParseUtils.parseCard("SJ"), // Jack of Spades
                CardParseUtils.parseCard("ST"), // Ten  of Spades
                CardParseUtils.parseCard("SA"), // Ace  of Spades
                CardParseUtils.parseCard("S4"), // Four of Spades
                CardParseUtils.parseCard("CA")  // Ace  of Clubs
        });

        Hand hand1 = new Hand(new Card[] {
                CardParseUtils.parseCard("HA"), // Ace  of Hearts
                CardParseUtils.parseCard("S2")  // Two of Spades
        });

        Hand hand2 = new Hand(new Card[] {
                CardParseUtils.parseCard("HA"), // Ace   of Hearts
                CardParseUtils.parseCard("S3")  // Three of Spades
        });

        Hand hand3 = new Hand(new Card[] {
                CardParseUtils.parseCard("HA"), // Ace  of Hearts
                CardParseUtils.parseCard("S5")  // Five of Spades
        });

        Hand hand4 = new Hand(new Card[] {
                CardParseUtils.parseCard("S5"), // Five of Hearts
                CardParseUtils.parseCard("S2")  // Two  of Spades
        });

        Hand hand5 = new Hand(new Card[] {
                CardParseUtils.parseCard("HA"), // Ace   of Hearts
                CardParseUtils.parseCard("SQ")  // Queen of Spades
        });

        Hand hand6 = new Hand(new Card[] {
                CardParseUtils.parseCard("HA"), // Ace  of Hearts
                CardParseUtils.parseCard("SK")  // King of Spades
        });

        EvaluatedHand evalHand1 = evaluator.evaluate(hand1, table);
        EvaluatedHand evalHand2 = evaluator.evaluate(hand2, table);
        EvaluatedHand evalHand3 = evaluator.evaluate(hand3, table);
        EvaluatedHand evalHand4 = evaluator.evaluate(hand4, table);
        EvaluatedHand evalHand5 = evaluator.evaluate(hand5, table);
        EvaluatedHand evalHand6 = evaluator.evaluate(hand6, table);

        // Hand4 == Hand3
        // Other are in increasing order
        Assert.assertTrue(evalHand1.getRank() < evalHand2.getRank());
        Assert.assertTrue(evalHand2.getRank() < evalHand3.getRank());
        Assert.assertTrue(evalHand3.getRank() == evalHand4.getRank());
        Assert.assertTrue(evalHand4.getRank() < evalHand5.getRank());
        Assert.assertTrue(evalHand5.getRank() < evalHand6.getRank());
    }

    @Test
    public void testStraightOrdering() throws ParseException {
        Table table = new Table(new Card[] {
                CardParseUtils.parseCard("S2"), // Two   of Spades
                CardParseUtils.parseCard("H5"), // Five  of Hearts
                CardParseUtils.parseCard("S6"), // Six   of Spades
                CardParseUtils.parseCard("D7"), // Seven of Diamonds
                CardParseUtils.parseCard("C9")  // Nine  of Clubs
        });

        Hand hand1 = new Hand(new Card[] {
                CardParseUtils.parseCard("C3"), // Three of Clubs
                CardParseUtils.parseCard("C4")  // Four  of Clubs
        });

        Hand hand2 = new Hand(new Card[] {
                CardParseUtils.parseCard("C4"), // Four  of Clubs
                CardParseUtils.parseCard("D8")  // Eight of Diamonds
        });

        Hand hand3 = new Hand(new Card[] {
                CardParseUtils.parseCard("C8"), // Eight of Clubs
                CardParseUtils.parseCard("DT")  // Ten   of Diamonds
        });

        Hand hand4 = new Hand(new Card[] {
                CardParseUtils.parseCard("S8"), // Eight of Spades
                CardParseUtils.parseCard("CT")  // Ten   of Clubs
        });

        EvaluatedHand evalHand1 = evaluator.evaluate(hand1, table);
        EvaluatedHand evalHand2 = evaluator.evaluate(hand2, table);
        EvaluatedHand evalHand3 = evaluator.evaluate(hand3, table);
        EvaluatedHand evalHand4 = evaluator.evaluate(hand4, table);

        // Check that ordering:
        // Hand4 == Hand3
        // Others are in increasing order
        Assert.assertTrue(evalHand4.getRank() == evalHand3.getRank());

        Assert.assertTrue(evalHand1.getRank() < evalHand2.getRank());
        Assert.assertTrue(evalHand2.getRank() < evalHand3.getRank());


        // Check that Wheel is the weakest (A-2-3-4-5)
        Table wheelTable = new Table(new Card[] {
                CardParseUtils.parseCard("CA"),  // Ace   of Clubs
                CardParseUtils.parseCard("H2"),  // Two   of Hearts
                CardParseUtils.parseCard("D3"),  // Three of Diamonds
                CardParseUtils.parseCard("C4"),  // Four  of Clubs
                CardParseUtils.parseCard("S5")   // Five  of Spades
        });
        Hand wheelHand = new Hand(new Card[] {
                CardParseUtils.parseCard("CT"),  // Ten  of Clubs
                CardParseUtils.parseCard("HJ")   // Jack of Hearts
        });

        EvaluatedHand evalHandWheel = evaluator.evaluate(wheelHand, wheelTable);
        Assert.assertTrue(evalHandWheel.getRank() < evalHand1.getRank());
        Assert.assertTrue(evalHandWheel.getRank() < evalHand2.getRank());
        Assert.assertTrue(evalHandWheel.getRank() < evalHand3.getRank());
        Assert.assertTrue(evalHandWheel.getRank() < evalHand4.getRank());
    }

    @Test
    public void testThreeOfAKindOrdering() throws ParseException {
        Table table = new Table(new Card[] {
                CardParseUtils.parseCard("S2"), // Two   of Spades
                CardParseUtils.parseCard("H5"), // Five  of Hearts
                CardParseUtils.parseCard("S6"), // Six   of Spades
                CardParseUtils.parseCard("DT"), // Ten   of Diamonds
                CardParseUtils.parseCard("C9")  // Nine  of Clubs
        });

        Hand hand1 = new Hand(new Card[] {
                CardParseUtils.parseCard("C2"), // Two of Clubs
                CardParseUtils.parseCard("H2")  // Two of Hearts
        });

        Hand hand2 = new Hand(new Card[] {
                CardParseUtils.parseCard("C5"), // Five of Clubs
                CardParseUtils.parseCard("S5")  // Five of Spades
        });

        Hand hand3 = new Hand(new Card[] {
                CardParseUtils.parseCard("CT"), // Ten of Clubs
                CardParseUtils.parseCard("ST")  // Ten of Spades
        });

        Hand hand4 = new Hand(new Card[] {
                CardParseUtils.parseCard("CT"), // Ten of Clubs
                CardParseUtils.parseCard("HT")  // Ten of Hearts
        });

        EvaluatedHand evalHand1 = evaluator.evaluate(hand1, table);
        EvaluatedHand evalHand2 = evaluator.evaluate(hand2, table);
        EvaluatedHand evalHand3 = evaluator.evaluate(hand3, table);
        EvaluatedHand evalHand4 = evaluator.evaluate(hand4, table);

        // Hand3 == Hand4
        Assert.assertTrue(evalHand1.getRank() < evalHand2.getRank());
        Assert.assertTrue(evalHand2.getRank() < evalHand3.getRank());
        Assert.assertTrue(evalHand3.getRank() == evalHand4.getRank());
    }

    @Test
    public void testTwoPairOrdering() throws ParseException {
        Table table = new Table(new Card[] {
                CardParseUtils.parseCard("S2"), // Two   of Spades
                CardParseUtils.parseCard("H5"), // Five  of Hearts
                CardParseUtils.parseCard("S6"), // Six   of Spades
                CardParseUtils.parseCard("DT"), // Ten   of Diamonds
                CardParseUtils.parseCard("C9")  // Nine  of Clubs
        });

        Hand hand1 = new Hand(new Card[] {
                CardParseUtils.parseCard("C2"), // Two  of Clubs
                CardParseUtils.parseCard("C5")  // Five of Clubs
        });

        Hand hand2 = new Hand(new Card[] {
                CardParseUtils.parseCard("C2"), // Two  of Clubs
                CardParseUtils.parseCard("C6")  // Six  of Clubs
        });

        Hand hand3 = new Hand(new Card[] {
                CardParseUtils.parseCard("C2"), // Two  of Clubs
                CardParseUtils.parseCard("CT")  // Ten  of Clubs
        });

        Hand hand4 = new Hand(new Card[] {
                CardParseUtils.parseCard("CT"), // Ten  of Clubs
                CardParseUtils.parseCard("H9")  // Nine of Hearts
        });

        Hand hand5 = new Hand(new Card[] {
                CardParseUtils.parseCard("ST"), // Ten  of Spades
                CardParseUtils.parseCard("S9")  // Nine of Spades
        });

        EvaluatedHand evalHand1 = evaluator.evaluate(hand1, table);
        EvaluatedHand evalHand2 = evaluator.evaluate(hand2, table);
        EvaluatedHand evalHand3 = evaluator.evaluate(hand3, table);
        EvaluatedHand evalHand4 = evaluator.evaluate(hand4, table);
        EvaluatedHand evalHand5 = evaluator.evaluate(hand5, table);

        Assert.assertTrue(evalHand1.getRank() < evalHand2.getRank());
        Assert.assertTrue(evalHand2.getRank() < evalHand3.getRank());
        Assert.assertTrue(evalHand3.getRank() < evalHand4.getRank());
        Assert.assertTrue(evalHand4.getRank() == evalHand5.getRank());
    }

    @Test
    public void testPairOrdering() throws ParseException {
        Table table = new Table(new Card[] {
                CardParseUtils.parseCard("S2"), // Two   of Spades
                CardParseUtils.parseCard("H5"), // Five  of Hearts
                CardParseUtils.parseCard("S6"), // Six   of Spades
                CardParseUtils.parseCard("DT"), // Ten   of Diamonds
                CardParseUtils.parseCard("C9")  // Nine  of Clubs
        });

        Hand hand1 = new Hand(new Card[] {
                CardParseUtils.parseCard("C2"), // Two  of Clubs
                CardParseUtils.parseCard("CA")  // Ace  of Clubs
        });

        Hand hand2 = new Hand(new Card[] {
                CardParseUtils.parseCard("C5"), // Five of Clubs
                CardParseUtils.parseCard("CA")  // Ace  of Clubs
        });

        Hand hand3 = new Hand(new Card[] {
                CardParseUtils.parseCard("C6"), // Six  of Clubs
                CardParseUtils.parseCard("CK")  // King  of Clubs
        });

        Hand hand4 = new Hand(new Card[] {
                CardParseUtils.parseCard("C6"), // Six  of Clubs
                CardParseUtils.parseCard("CA")  // Ace  of Clubs
        });

        Hand hand5 = new Hand(new Card[] {
                CardParseUtils.parseCard("CT"), // Ten  of Clubs
                CardParseUtils.parseCard("CA")  // Ace  of Clubs
        });

        Hand hand6 = new Hand(new Card[] {
                CardParseUtils.parseCard("DA"), // Ace  of Diamonds
                CardParseUtils.parseCard("CA")  // Ace  of Clubs
        });

        EvaluatedHand evalHand1 = evaluator.evaluate(hand1, table);
        EvaluatedHand evalHand2 = evaluator.evaluate(hand2, table);
        EvaluatedHand evalHand3 = evaluator.evaluate(hand3, table);
        EvaluatedHand evalHand4 = evaluator.evaluate(hand4, table);
        EvaluatedHand evalHand5 = evaluator.evaluate(hand5, table);
        EvaluatedHand evalHand6 = evaluator.evaluate(hand6, table);

        Assert.assertTrue(evalHand1.getRank() < evalHand2.getRank());
        Assert.assertTrue(evalHand2.getRank() < evalHand3.getRank());
        Assert.assertTrue(evalHand3.getRank() < evalHand4.getRank());
        Assert.assertTrue(evalHand4.getRank() < evalHand5.getRank());
        Assert.assertTrue(evalHand5.getRank() < evalHand6.getRank());
    }

    @Test
    public void testNoPairOrdering() throws ParseException {
        Table table = new Table(new Card[] {
                CardParseUtils.parseCard("S2"), // Two   of Spades
                CardParseUtils.parseCard("H5"), // Five  of Hearts
                CardParseUtils.parseCard("SQ"), // Queen of Spades
                CardParseUtils.parseCard("DT"), // Ten   of Diamonds
                CardParseUtils.parseCard("C9")  // Nine  of Clubs
        });

        Hand hand1 = new Hand(new Card[] {
                CardParseUtils.parseCard("C3"), // Three  of Clubs
                CardParseUtils.parseCard("C4")  // Four   of Clubs
        });

        Hand hand2 = new Hand(new Card[] {
                CardParseUtils.parseCard("C3"), // Three  of Clubs
                CardParseUtils.parseCard("C7")  // Seven  of Clubs
        });

        Hand hand3 = new Hand(new Card[] {
                CardParseUtils.parseCard("C7"), // Seven  of Clubs
                CardParseUtils.parseCard("C8")  // Eight  of Clubs
        });

        Hand hand4 = new Hand(new Card[] {
                CardParseUtils.parseCard("C3"), // Three  of Clubs
                CardParseUtils.parseCard("CA")  // Ace    of Clubs
        });

        Hand hand5 = new Hand(new Card[] {
                CardParseUtils.parseCard("C4"), // Three  of Clubs
                CardParseUtils.parseCard("CA")  // Four   of Clubs
        });

        EvaluatedHand evalHand1 = evaluator.evaluate(hand1, table);
        EvaluatedHand evalHand2 = evaluator.evaluate(hand2, table);
        EvaluatedHand evalHand3 = evaluator.evaluate(hand3, table);
        EvaluatedHand evalHand4 = evaluator.evaluate(hand4, table);
        EvaluatedHand evalHand5 = evaluator.evaluate(hand5, table);

        Assert.assertTrue(evalHand1.getRank() < evalHand2.getRank());
        Assert.assertTrue(evalHand2.getRank() < evalHand3.getRank());
        Assert.assertTrue(evalHand3.getRank() < evalHand4.getRank());
        Assert.assertTrue(evalHand4.getRank() == evalHand5.getRank());
    }

    @Test
    public void testCategoryOrdering() throws ParseException {
        EvaluatedHand evalHand1  = evaluate("CA", "CK", "S3", "D4", "H8", "H7", "DT"); // NO_PAIR
        EvaluatedHand evalHand2  = evaluate("CA", "CK", "S3", "DA", "H8", "H7", "DT"); // PAIR
        EvaluatedHand evalHand3  = evaluate("CA", "CK", "SK", "DA", "H8", "H7", "DT"); // TWO_PAIR
        EvaluatedHand evalHand4  = evaluate("CA", "CK", "SK", "D2", "HK", "H7", "DT"); // THREE_OF_A_KIND
        EvaluatedHand evalHand5  = evaluate("CA", "C2", "S3", "D4", "H8", "H5", "DT"); // STRAIGHT WHEEL (to the 5)
        EvaluatedHand evalHand6  = evaluate("C6", "C7", "S8", "DA", "H9", "H7", "DT"); // STRAIGHT
        EvaluatedHand evalHand7  = evaluate("CA", "CK", "C3", "DA", "C8", "C7", "DT"); // FLUSH
        EvaluatedHand evalHand8  = evaluate("CA", "CK", "SK", "DA", "HK", "H7", "DT"); // FULL_HOUSE
        EvaluatedHand evalHand9  = evaluate("C2", "CK", "S2", "D2", "H2", "H7", "DT"); // FOUR_OF_A_KIND
        EvaluatedHand evalHand10 = evaluate("CA", "C2", "C3", "C4", "C5", "H7", "CT"); // STRAIGHT_FLUSH WHEEL (to the 5)
        EvaluatedHand evalHand11 = evaluate("C6", "C7", "C8", "DA", "C9", "H7", "CT"); // STRAIGHT_FLUSH
        EvaluatedHand evalHand12 = evaluate("CA", "CK", "CQ", "CJ", "H8", "H7", "CT"); // ROYAL_FLUSH

        Assert.assertTrue(evalHand1.getRank() < evalHand2.getRank());
        Assert.assertTrue(evalHand2.getRank() < evalHand3.getRank());
        Assert.assertTrue(evalHand3.getRank() < evalHand4.getRank());
        Assert.assertTrue(evalHand4.getRank() < evalHand5.getRank());
        Assert.assertTrue(evalHand5.getRank() < evalHand6.getRank());
        Assert.assertTrue(evalHand6.getRank() < evalHand7.getRank());
        Assert.assertTrue(evalHand7.getRank() < evalHand8.getRank());
        Assert.assertTrue(evalHand8.getRank() < evalHand9.getRank());
        Assert.assertTrue(evalHand9.getRank() < evalHand10.getRank());
        Assert.assertTrue(evalHand10.getRank() < evalHand11.getRank());
        Assert.assertTrue(evalHand11.getRank() < evalHand12.getRank());
    }

    private EvaluatedHand evaluate(String ...cardCodes) throws ParseException {
        if (cardCodes.length != 7) throw new IllegalArgumentException("Expected exactly 7 card codes, but got: " + cardCodes.length);

        return evaluator.evaluate(new Hand(new Card[]{
                CardParseUtils.parseCard(cardCodes[0]),
                CardParseUtils.parseCard(cardCodes[1])
        }), new Table(new Card[] {
                CardParseUtils.parseCard(cardCodes[2]),
                CardParseUtils.parseCard(cardCodes[3]),
                CardParseUtils.parseCard(cardCodes[4]),
                CardParseUtils.parseCard(cardCodes[5]),
                CardParseUtils.parseCard(cardCodes[6])
        }));
    }
}

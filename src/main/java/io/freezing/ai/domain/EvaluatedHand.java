package io.freezing.ai.domain;

public class EvaluatedHand implements Comparable<EvaluatedHand> {
    private final WholeHand wholeHand;
    private final WholeHandRank rank;

    public EvaluatedHand(WholeHand wholeHand, WholeHandRank rank) {
        this.wholeHand = wholeHand;
        this.rank = rank;
    }

    public WholeHand getWholeHand() {
        return wholeHand;
    }

    public WholeHandRank getRank() {
        return rank;
    }


    @Override
    public int compareTo(EvaluatedHand o) {
        return this.rank.compareTo(o.getRank());
    }
}

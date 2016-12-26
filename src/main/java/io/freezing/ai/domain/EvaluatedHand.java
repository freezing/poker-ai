package io.freezing.ai.domain;

public class EvaluatedHand implements Comparable<EvaluatedHand> {
    private final WholeHand wholeHand;
    private final int rank;

    public EvaluatedHand(WholeHand wholeHand, int rank) {
        this.wholeHand = wholeHand;
        this.rank = rank;
    }

    public WholeHand getWholeHand() {
        return wholeHand;
    }

    public int getRank() {
        return rank;
    }


    @Override
    public int compareTo(EvaluatedHand o) {
        return Integer.compare(this.rank, o.getRank());
    }

    @Override
    public String toString() {
        return String.format("EvaluatedHand(rank = %d, %s)", rank, wholeHand);
    }
}

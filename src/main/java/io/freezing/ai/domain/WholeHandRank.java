package io.freezing.ai.domain;

public final class WholeHandRank implements Comparable<WholeHandRank> {
    private final int rank;

    public WholeHandRank(int rank) {
        this.rank = rank;
    }

    public int getRank() {
        return rank;
    }

    @Override
    public int compareTo(WholeHandRank o) {
        if (rank < o.getRank()) return -1;
        else if (rank > o.getRank()) return 1;
        else return 0;
    }
}

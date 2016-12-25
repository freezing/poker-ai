package io.freezing.ai.rules;

import io.freezing.ai.domain.WholeHand;
import io.freezing.ai.domain.WholeHandRank;

public interface WholeHandRanking {
    WholeHandRank calculateRank(WholeHand wholeHand);
}

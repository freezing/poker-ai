package io.freezing.ai.rules;

import io.freezing.ai.domain.EvaluatedHand;
import io.freezing.ai.domain.Hand;
import io.freezing.ai.domain.Table;

public interface HandEvaluator {
    EvaluatedHand evaluate(Hand hand, Table table);
}

package io.freezing.ai.rules.impl.texasholdem;

import io.freezing.ai.domain.EvaluatedHand;
import io.freezing.ai.domain.Hand;
import io.freezing.ai.domain.Table;
import io.freezing.ai.rules.HandEvaluator;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class TexasHoldEmHandEvaluator implements HandEvaluator {
    @Override
    public EvaluatedHand evaluate(Hand hand, Table table) {
        throw new NotImplementedException();
    }
}

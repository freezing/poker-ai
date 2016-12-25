package io.freezing.ai.rules.impl.texasholdem;

import io.freezing.ai.domain.WholeHand;
import io.freezing.ai.domain.WholeHandRank;
import io.freezing.ai.rules.WholeHandRanker;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class TexasHoldEmWholeHandRanker implements WholeHandRanker {
    @Override
    public WholeHandRank calculateRank(WholeHand wholeHand) {
        throw new NotImplementedException();
    }
}

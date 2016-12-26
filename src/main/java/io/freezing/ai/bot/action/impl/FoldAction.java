package io.freezing.ai.bot.action.impl;

import io.freezing.ai.bot.BotActionRationale;
import io.freezing.ai.bot.action.BotAction;

public class FoldAction implements BotAction {
    private final BotActionRationale rationale;

    public FoldAction(BotActionRationale rationale) {
        this.rationale = rationale;
    }

    @Override
    public BotActionRationale getRationale() {
        return rationale;
    }

    @Override
    public String toString() {
        return String.format("FoldAction(rationale = %s)", rationale.toString());
    }
}

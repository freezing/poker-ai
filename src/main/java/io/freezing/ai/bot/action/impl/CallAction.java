package io.freezing.ai.bot.action.impl;

import io.freezing.ai.bot.BotActionRationale;
import io.freezing.ai.bot.action.BotAction;

public class CallAction implements BotAction {
    // Rationale that was used
    protected final BotActionRationale rationale;

    public CallAction(BotActionRationale rationale) {
        this.rationale = rationale;
    }

    @Override
    public BotActionRationale getRationale() {
        return this.rationale;
    }

    @Override
    public String toString() {
        return String.format("CallAction(rationale = %s)", rationale);
    }
}

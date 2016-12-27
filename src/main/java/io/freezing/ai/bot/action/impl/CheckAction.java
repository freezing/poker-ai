package io.freezing.ai.bot.action.impl;

import io.freezing.ai.bot.BotActionRationale;

/** CheckAction is the same as CallAction with 0 amount */
public class CheckAction extends CallAction {
    public CheckAction(BotActionRationale rationale) {
        super(rationale);
    }

    @Override
    public String toString() {
        return String.format("CheckAction(rationale = %s)", this.rationale.toString());
    }
}

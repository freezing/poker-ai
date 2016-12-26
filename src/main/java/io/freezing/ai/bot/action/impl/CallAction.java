package io.freezing.ai.bot.action.impl;

import io.freezing.ai.bot.BotActionRationale;
import io.freezing.ai.bot.action.BotAction;

public class CallAction implements BotAction {
    // Amount that was called
    protected final int amount;

    // Rationale that was used
    protected final BotActionRationale rationale;

    public CallAction(int amount, BotActionRationale rationale) {
        this.amount = amount;
        this.rationale = rationale;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public BotActionRationale getRationale() {
        return this.rationale;
    }

    @Override
    public String toString() {
        return String.format("CallAction(amount = %d, rationale = %s)", amount, rationale);
    }
}

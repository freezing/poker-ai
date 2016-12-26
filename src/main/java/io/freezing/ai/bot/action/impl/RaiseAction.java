package io.freezing.ai.bot.action.impl;

import io.freezing.ai.bot.BotActionRationale;
import io.freezing.ai.bot.action.BotAction;

public class RaiseAction implements BotAction {
    private final int amount;
    private final BotActionRationale rationale;

    public RaiseAction(int amount, BotActionRationale rationale) {
        this.amount = amount;
        this.rationale = rationale;
    }

    @Override
    public BotActionRationale getRationale() {
        return rationale;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return String.format("RaiseAction(amount = %d, rationale = %s)", amount, rationale);
    }
}

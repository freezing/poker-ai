package io.freezing.ai.bot.action.impl;

import io.freezing.ai.bot.action.BotAction;

public class CallAction implements BotAction {
    // Amount that was called
    private final int amount;

    public CallAction(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return String.format("CallAction(amount = %d)", amount);
    }
}

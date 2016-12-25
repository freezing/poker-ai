package io.freezing.ai.bot.action.impl;

import io.freezing.ai.bot.action.BotAction;

public class RaiseAction implements BotAction {
    private final int amount;

    public RaiseAction(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return String.format("RaiseAction(amount = %d)", amount);
    }
}

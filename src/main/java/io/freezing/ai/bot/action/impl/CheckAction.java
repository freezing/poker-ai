package io.freezing.ai.bot.action.impl;

/** CheckAction is the same as CallAction with 0 amount */
public class CheckAction extends CallAction {
    public CheckAction() {
        super(0);
    }

    @Override
    public String toString() {
        return "CheckAction()";
    }
}

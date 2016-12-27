package io.freezing.ai.bot;

import io.freezing.ai.bot.action.BotAction;
import io.freezing.ai.domain.PokerState;
import io.freezing.ai.rules.PokerRules;

public interface PokerBot {
    BotAction nextAction(PokerState state);
    String getName();
    PokerRules getRules();
}

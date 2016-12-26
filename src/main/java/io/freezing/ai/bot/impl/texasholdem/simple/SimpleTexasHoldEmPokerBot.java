package io.freezing.ai.bot.impl.texasholdem.simple;

import io.freezing.ai.bot.PokerBot;
import io.freezing.ai.bot.action.BotAction;
import io.freezing.ai.domain.PokerState;
import io.freezing.ai.rules.impl.texasholdem.TexasHoldEmRules;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class SimpleTexasHoldEmPokerBot implements PokerBot {
    private final TexasHoldEmRules rules;

    public SimpleTexasHoldEmPokerBot(TexasHoldEmRules rules) {
        this.rules = rules;
    }

    @Override
    public BotAction nextAction(PokerState state) {
        // TODO: Implement, first just calculate the probability
        throw new NotImplementedException();
    }

    @Override
    public String getName() {
        return SimpleTexasHoldEmPokerBot.class.getSimpleName();
    }
}

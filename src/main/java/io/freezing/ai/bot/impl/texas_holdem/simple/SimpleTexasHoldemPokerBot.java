package io.freezing.ai.bot.impl.texas_holdem.simple;

import io.freezing.ai.bot.PokerBot;
import io.freezing.ai.bot.action.BotAction;
import io.freezing.ai.config.TexasHoldemConfig;
import io.freezing.ai.domain.PokerState;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class SimpleTexasHoldemPokerBot implements PokerBot {
    private final TexasHoldemConfig config;

    public SimpleTexasHoldemPokerBot(TexasHoldemConfig config) {
        this.config = config;
    }

    @Override
    public BotAction nextAction(PokerState state) {
        throw new NotImplementedException();
    }
}

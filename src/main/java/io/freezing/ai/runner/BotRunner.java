package io.freezing.ai.runner;

import io.freezing.ai.bot.PokerBot;
import io.freezing.ai.bot.action.BotAction;
import io.freezing.ai.domain.PokerState;
import io.freezing.ai.exception.input.PokerInputException;
import io.freezing.ai.function.PokerStateUtils;
import io.freezing.ai.io.output.PokerOutput;

import java.io.IOException;
import java.util.logging.Logger;

public class BotRunner implements AutoCloseable {
    private static final Logger logger = Logger.getLogger(BotRunner.class.getName());

    private final PokerBot bot;
    private final PokerOutput output;

    public BotRunner(PokerBot bot, PokerOutput output) {
        this.bot = bot;
        this.output = output;
    }

    public void run(PokerState state) throws PokerInputException, IOException {
        PokerStateUtils.validatePokerState(state, bot.getRules());
        BotAction action = bot.nextAction(state);
        output.handle(action);
    }

    @Override
    public void close() throws Exception {
        this.output.close();
    }
}

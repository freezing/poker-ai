package io.freezing.ai.io.output;

import io.freezing.ai.bot.action.BotAction;

import java.io.IOException;

public interface PokerOutput extends AutoCloseable {
    void handle(BotAction action) throws IOException;
}

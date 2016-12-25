package io.freezing.ai.io.output;

import io.freezing.ai.bot.action.BotAction;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

public class StandardOutputBotActionHandler implements PokerOutput {
    private final PrintWriter writer;

    public StandardOutputBotActionHandler(OutputStream os) {
        this.writer = new PrintWriter(os);
    }

    @Override
    public void handle(BotAction action) throws IOException {
        String output = String.format("%s\n", action.toString());
        this.writer.write(output);
        this.writer.flush();
    }

    @Override
    public void close() throws Exception {
        this.writer.close();
    }
}

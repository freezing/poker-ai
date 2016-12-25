package io.freezing.ai.io.message.impl;

import io.freezing.ai.io.message.PokerMessage;
import io.freezing.ai.io.message.PokerMessageHandler;

import java.io.OutputStream;
import java.io.PrintWriter;

public class StandardPokerMessageHandler<T extends PokerMessage> implements PokerMessageHandler<T> {
    private final PrintWriter writer;

    public StandardPokerMessageHandler(OutputStream os) {
        this.writer = new PrintWriter(os);
    }

    @Override
    public void handle(T message) {
        this.writer.write(String.format("%s\n", message.getMessage()));
        this.writer.flush();
    }

    @Override
    public void close() throws Exception {
        writer.close();
    }
}

package io.freezing.ai.io.error.impl;

import io.freezing.ai.exception.parse.ParseException;
import io.freezing.ai.io.error.PokerInputExceptionHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class StandardOutputInputExceptionHandler implements PokerInputExceptionHandler<ParseException> {
    private final OutputStreamWriter writer;

    public StandardOutputInputExceptionHandler(OutputStream os) {
        this.writer = new OutputStreamWriter(os);
    }

    @Override
    public void handle(ParseException exception) {
        try {
            this.writer.write(String.format("%s\n", exception.getMessage()));
            this.writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws Exception {
        this.writer.close();
    }
}

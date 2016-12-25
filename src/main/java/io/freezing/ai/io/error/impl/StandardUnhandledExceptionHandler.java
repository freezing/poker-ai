package io.freezing.ai.io.error.impl;

import io.freezing.ai.io.error.UnhandledExceptionHandler;

import java.io.OutputStream;
import java.io.PrintWriter;

public class StandardUnhandledExceptionHandler implements UnhandledExceptionHandler {
    private final PrintWriter writer;

    public StandardUnhandledExceptionHandler(OutputStream os) {
        this.writer = new PrintWriter(os);
    }

    @Override
    public void handle(Exception e) {
        e.printStackTrace(writer);
    }

    @Override
    public void close() throws Exception {
        writer.close();
    }
}

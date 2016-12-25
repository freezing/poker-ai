package io.freezing.ai.io.error;

public interface UnhandledExceptionHandler extends AutoCloseable {
    void handle(Exception e);
}

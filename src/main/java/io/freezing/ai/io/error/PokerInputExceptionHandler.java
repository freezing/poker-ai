package io.freezing.ai.io.error;

import io.freezing.ai.exception.input.PokerInputException;

public interface PokerInputExceptionHandler<T extends PokerInputException> extends AutoCloseable {
    void handle(T exception);
}

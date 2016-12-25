package io.freezing.ai.io.error;

import io.freezing.ai.exception.parse.PokerInputException;

public interface PokerInputExceptionHandler<T extends PokerInputException> extends AutoCloseable {
    void handle(T exception);
}

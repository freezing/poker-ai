package io.freezing.ai.io.message;

public interface PokerMessageHandler<T extends PokerMessage> extends AutoCloseable {
    void handle(T message);
}

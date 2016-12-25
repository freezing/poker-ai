package io.freezing.ai.io.message.impl;

import io.freezing.ai.io.message.PokerMessage;

public class StringPokerMessage implements PokerMessage<String> {
    private final String message;

    public StringPokerMessage(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

package io.freezing.ai.io.input.stream;

import io.freezing.ai.domain.PokerState;
import io.freezing.ai.exception.input.PokerInputException;
import io.freezing.ai.io.input.PokerInput;
import io.freezing.ai.io.message.PokerMessageHandler;
import io.freezing.ai.io.message.impl.StringPokerMessage;
import io.freezing.ai.io.parser.PokerInputParser;

import java.io.InputStream;
import java.util.Scanner;
import java.util.Optional;

// Not going to be used, so I'll probably remove it when I feel comfortable that I don't need it
public class StandardStreamPokerInput implements PokerInput {
    private final PokerInputParser parser;
    private final PokerMessageHandler<StringPokerMessage> pokerMessageHandler;
    private final Scanner sc;

    public StandardStreamPokerInput(InputStream is,
                              PokerInputParser parser,
                              PokerMessageHandler<StringPokerMessage> pokerMessageHandler) {
        this.sc = new Scanner(is);
        this.parser = parser;
        this.pokerMessageHandler = pokerMessageHandler;
    }

    @Override
    public Optional<PokerState> getState() throws PokerInputException {
        pokerMessageHandler.handle(new StringPokerMessage(
                String.format("Use the following format:\n%s", parser.getFormat()))
        );
        if (sc.hasNext()) {
            return Optional.of(this.parser.parse(sc.nextLine()));
        } else {
            return Optional.empty();
        }
    }


    @Override
    public void close() throws Exception {
        sc.close();
    }
}

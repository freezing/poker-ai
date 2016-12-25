package io.freezing.ai.io.input.stdin;

import io.freezing.ai.domain.PokerState;
import io.freezing.ai.exception.parse.PokerInputException;
import io.freezing.ai.io.input.PokerInput;
import io.freezing.ai.io.message.PokerMessageHandler;
import io.freezing.ai.io.message.impl.StringPokerMessage;
import io.freezing.ai.io.parser.PokerInputParser;

import java.io.InputStream;
import java.util.Optional;
import java.util.Scanner;

public class StandardPokerInput implements PokerInput {
    private final Scanner sc;
    private final PokerInputParser parser;
    private final PokerMessageHandler<StringPokerMessage> pokerMessageHandler;

    public StandardPokerInput(InputStream is,
                              PokerInputParser parser,
                              PokerMessageHandler<StringPokerMessage> pokerMessageHandler) {
        this.sc = new Scanner(is);
        this.parser = parser;
        this.pokerMessageHandler = pokerMessageHandler;
    }

    @Override
    public Optional<PokerState> getNextState() throws PokerInputException {
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

package io.freezing.ai.io.input.string;

import io.freezing.ai.domain.PokerState;
import io.freezing.ai.exception.input.PokerInputException;
import io.freezing.ai.io.input.PokerInput;
import io.freezing.ai.io.parser.impl.TexasHoldemInputParser;

import java.util.Optional;

public class StringPokerInput implements PokerInput {
    private final String input;

    public StringPokerInput(String input) {
        this.input = input;
    }

    @Override
    public Optional<PokerState> getState() throws PokerInputException {
        return Optional.of(new TexasHoldemInputParser().parse(input));
    }

    @Override
    public void close() throws Exception {

    }
}

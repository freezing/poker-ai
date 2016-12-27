package io.freezing.ai.io.cli;

import io.freezing.ai.exception.input.PokerInputException;
import io.freezing.ai.io.cli.command.Command;

import java.io.IOException;

public interface CommandProcessor extends AutoCloseable {
    void process(Command cmd) throws IOException, PokerInputException;
}

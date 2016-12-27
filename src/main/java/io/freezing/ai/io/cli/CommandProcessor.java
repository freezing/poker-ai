package io.freezing.ai.io.cli;

import io.freezing.ai.io.cli.command.Command;

public interface CommandProcessor extends AutoCloseable {
    void process(Command cmd);
}

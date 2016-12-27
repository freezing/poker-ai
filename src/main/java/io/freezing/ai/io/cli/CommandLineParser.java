package io.freezing.ai.io.cli;

import io.freezing.ai.io.cli.command.Command;

public interface CommandLineParser {
    Command parseLine(String line);
}

package io.freezing.ai.io.cli.impl;

import io.freezing.ai.io.cli.CommandLineParser;
import io.freezing.ai.io.cli.command.Command;
import io.freezing.ai.io.cli.command.EvaluatePokerStateCommand;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.logging.Logger;

public class CommandLineParserImpl implements CommandLineParser {
    private static final Logger logger = Logger.getLogger(CommandLineParserImpl.class.getName());

    @Override
    public Command parseLine(String line) {
        logger.info(String.format("Parsing command line: %s", line));
        if (line.toLowerCase().startsWith("evaluate")) {
            return new EvaluatePokerStateCommand(line.substring("evaluate ".length()));
        }
        throw new NotImplementedException();
    }
}

package io.freezing.ai.io.cli.impl;

import io.freezing.ai.io.cli.CommandLineParser;
import io.freezing.ai.io.cli.command.Command;
import io.freezing.ai.io.cli.command.EvaluatePokerStateCommand;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.logging.Logger;

public class CommandLineParserImpl implements CommandLineParser {
    private static final Logger logger = Logger.getLogger(CommandLineParserImpl.class.getName());

    // TODO: Implement command line parser properly (e.g. being able to add new command by providing
    // - command name (string that invokes command)
    // - function String => Command
    // - action => void

    @Override
    public Command parseLine(String _line) {
        String line = _line.toString();
        logger.info(String.format("Parsing command line: %s", line));
        if (line.startsWith("evaluate")) {
            return new EvaluatePokerStateCommand(line.substring("evaluate ".length()));
        }
        throw new NotImplementedException();
    }
}

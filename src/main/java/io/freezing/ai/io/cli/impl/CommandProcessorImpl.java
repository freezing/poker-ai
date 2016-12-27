package io.freezing.ai.io.cli.impl;

import io.freezing.ai.io.cli.CommandProcessor;
import io.freezing.ai.io.cli.command.Command;
import io.freezing.ai.io.cli.command.EvaluatePokerStateCommand;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.logging.Logger;

public class CommandProcessorImpl implements CommandProcessor {
    private static final Logger logger = Logger.getLogger(CommandProcessorImpl.class.getName());

    private final OutputStream pokerOutputStream;

    public CommandProcessorImpl(OutputStream pokerOutputStream) {
        this.pokerOutputStream = pokerOutputStream;
    }

    @Override
    public void process(Command command) {
        logger.info(String.format("About to process command: %s", command.toString()));
        if (command instanceof EvaluatePokerStateCommand) {
            EvaluatePokerStateCommand cmd = (EvaluatePokerStateCommand) command;
            try {
                this.pokerOutputStream.write(String.format("%s\n", cmd.getLine()).getBytes(Charset.defaultCharset()));
            } catch (IOException e) {
                // TODO: Handle command processor exceptions properly (use exception handler)
                e.printStackTrace();
            }
        } else {
            logger.warning(String.format("Unhandled command: %s", command.getClass().getName()));
        }
    }

    @Override
    public void close() throws Exception {
        this.pokerOutputStream.close();
    }
}

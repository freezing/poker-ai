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
        try {
            logger.info(String.format("About to process command: %s", command.toString()));
            if (command instanceof EvaluatePokerStateCommand) {
                evaluatePokerStateCommand((EvaluatePokerStateCommand) command);
            } else {
                logger.warning(String.format("Unhandled command: %s", command.getClass().getName()));
            }
        } catch (Exception e) {
            logger.severe(e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    private void evaluatePokerStateCommand(EvaluatePokerStateCommand cmd) throws IOException {
        this.pokerOutputStream.write(String.format("%s\n", cmd.getLine()).getBytes(Charset.defaultCharset()));
    }

    @Override
    public void close() throws Exception {
        this.pokerOutputStream.close();
    }
}

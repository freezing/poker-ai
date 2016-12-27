package io.freezing.ai.io.cli.impl;

import io.freezing.ai.domain.PokerState;
import io.freezing.ai.exception.input.PokerInputException;
import io.freezing.ai.exception.parse.ParseException;
import io.freezing.ai.io.cli.CommandProcessor;
import io.freezing.ai.io.cli.command.Command;
import io.freezing.ai.io.cli.command.EvaluatePokerStateCommand;
import io.freezing.ai.io.parser.impl.TexasHoldemInputParser;
import io.freezing.ai.runner.BotRunner;

import java.io.IOException;
import java.util.logging.Logger;

public class CommandProcessorImpl implements CommandProcessor {
    private static final Logger logger = Logger.getLogger(CommandProcessorImpl.class.getName());

    private final BotRunner botRunner;

    public CommandProcessorImpl(BotRunner botRunner) {
        this.botRunner = botRunner;
    }

    @Override
    public void process(Command command) throws IOException, PokerInputException {
        logger.info(String.format("About to process command: %s", command.toString()));
        if (command instanceof EvaluatePokerStateCommand) {
            evaluatePokerStateCommand((EvaluatePokerStateCommand) command);
        } else {
            logger.warning(String.format("Unhandled command: %s", command.getClass().getName()));
        }
    }

    private void evaluatePokerStateCommand(EvaluatePokerStateCommand cmd) throws PokerInputException, IOException {
        TexasHoldemInputParser parser = new TexasHoldemInputParser();
        PokerState state = parser.parse(cmd.getLine());
        botRunner.run(state);
    }

    @Override
    public void close() throws Exception {
        this.botRunner.close();
    }
}

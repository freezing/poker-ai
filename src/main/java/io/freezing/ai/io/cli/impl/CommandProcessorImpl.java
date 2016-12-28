package io.freezing.ai.io.cli.impl;

import io.freezing.ai.domain.PokerState;
import io.freezing.ai.exception.input.PokerInputException;
import io.freezing.ai.io.cli.CommandProcessor;
import io.freezing.ai.io.cli.command.Command;
import io.freezing.ai.io.cli.command.EvaluateImagePokerStateCommand;
import io.freezing.ai.io.cli.command.EvaluatePokerStateCommand;
import io.freezing.ai.io.input.image.pokerstars.PokerStarsImageScanner;
import io.freezing.ai.io.input.string.StringPokerInput;
import io.freezing.ai.runner.BotRunner;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Optional;
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
        } else if (command instanceof EvaluateImagePokerStateCommand) {
          evaluateImagePokerStateCommand((EvaluateImagePokerStateCommand) command);
        } else {
            logger.warning(String.format("Unhandled command: %s", command.getClass().getName()));
        }
    }

    private void evaluatePokerStateCommand(EvaluatePokerStateCommand cmd) throws PokerInputException, IOException {
        StringPokerInput stringPokerInput = new StringPokerInput(cmd.getLine());
        runStateOpt(stringPokerInput.getState());
    }

    private void evaluateImagePokerStateCommand(EvaluateImagePokerStateCommand cmd) throws PokerInputException, IOException {
        BufferedImage image = null; // TODO: Read image
        PokerStarsImageScanner imageScanner = new PokerStarsImageScanner(image);
        runStateOpt(imageScanner.getState());
    }

    private void runStateOpt(Optional<PokerState> stateOpt) throws IOException, PokerInputException {
        if (stateOpt.isPresent()) {
            botRunner.run(stateOpt.get());
        } else {
            logger.warning(String.format("Bot will not run as PokerInput has returned EMPTY state"));
        }
    }

    @Override
    public void close() throws Exception {
        this.botRunner.close();
    }
}

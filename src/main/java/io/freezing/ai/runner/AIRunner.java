package io.freezing.ai.runner;

import io.freezing.ai.bot.PokerBot;
import io.freezing.ai.bot.impl.texasholdem.simple.SimpleTexasHoldEmPokerBot;
import io.freezing.ai.bot.impl.texasholdem.simple.SimpleTexasHoldEmPokerBotConfig;
import io.freezing.ai.domain.PokerState;
import io.freezing.ai.exception.input.PokerInputException;
import io.freezing.ai.io.error.impl.StandardOutputInputExceptionHandler;
import io.freezing.ai.io.error.UnhandledExceptionHandler;
import io.freezing.ai.io.error.impl.StandardUnhandledExceptionHandler;
import io.freezing.ai.io.input.PokerInput;
import io.freezing.ai.io.error.PokerInputExceptionHandler;
import io.freezing.ai.io.input.stream.StandardStreamPokerInput;
import io.freezing.ai.io.message.impl.StandardPokerMessageHandler;
import io.freezing.ai.io.output.PokerOutput;
import io.freezing.ai.io.output.StandardOutputBotActionHandler;
import io.freezing.ai.io.parser.impl.TexasHoldemInputParser;
import io.freezing.ai.rules.impl.texasholdem.TexasHoldEmHandEvaluator;
import io.freezing.ai.rules.impl.texasholdem.TexasHoldEmRules;

import java.util.Optional;
import java.util.logging.Logger;

public class AIRunner implements AutoCloseable {
    private static final Logger logger = Logger.getLogger(AIRunner.class.getName());

    private final PokerInput input;
    private final BotRunner botRunner;

    // Error handlers
    private final PokerInputExceptionHandler<PokerInputException> exceptionHandler;
    private final UnhandledExceptionHandler unhandledExceptionHandler;

    public AIRunner(PokerInput input, BotRunner botRunner, PokerInputExceptionHandler<PokerInputException> exceptionHandler, UnhandledExceptionHandler unhandledExceptionHandler) {
        this.input = input;
        this.botRunner = botRunner;
        this.exceptionHandler = exceptionHandler;
        this.unhandledExceptionHandler = unhandledExceptionHandler;
    }

    public void singleRun() {
        try {
            // TODO: Can I do something nice as using Extractors in Scala?
            Optional<PokerState> stateOpt = this.input.getState();

            if (stateOpt.isPresent()) {
                PokerState state = stateOpt.get();
                botRunner.run(state);
            } else {
                // No more input
                logger.info("Shutting down the AI Runner. No more input expected.");
            }
        } catch (PokerInputException e) {
            exceptionHandler.handle(e);
        } catch (Exception e) {
            unhandledExceptionHandler.handle(e);
        }
    }

    public void runForever() {
        while (true) {
            singleRun();
        }
    }

    @Override
    public void close() throws Exception {
        this.input.close();
    }

    public static void main(String[] args) throws Exception {
        PokerInput input = new StandardStreamPokerInput(
                System.in,
                new TexasHoldemInputParser(),
                new StandardPokerMessageHandler<>(System.out)
        );
        PokerOutput output = new StandardOutputBotActionHandler(System.out);
        PokerInputExceptionHandler<PokerInputException> exceptionHandler = new StandardOutputInputExceptionHandler(System.err);
        UnhandledExceptionHandler unhandledExceptionHandler = new StandardUnhandledExceptionHandler(System.err);

        // Create Simple AI Bot
        TexasHoldEmRules rules = new TexasHoldEmRules(9, 60000, 0, 10, 1000, new TexasHoldEmHandEvaluator());
        PokerBot bot = new SimpleTexasHoldEmPokerBot(rules, new SimpleTexasHoldEmPokerBotConfig(10000, 186544L));

        // Create BotRunner
        BotRunner botRunner = new BotRunner(bot, output);

        // Initialize AI Runner
        AIRunner ai = new AIRunner(input, botRunner, exceptionHandler, unhandledExceptionHandler);

        // Run AI
        ai.runForever();

        // AI has finished. No more input expected. Close IO.
        ai.close();
    }

    // For testing:
    // 1 2 1 50 100 250 100 1000 ST DA SA D3 D4 CT S8
}

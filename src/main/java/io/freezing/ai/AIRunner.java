package io.freezing.ai;

import io.freezing.ai.bot.PokerBot;
import io.freezing.ai.bot.action.BotAction;
import io.freezing.ai.bot.impl.texasholdem.simple.SimpleTexasHoldEmPokerBot;
import io.freezing.ai.bot.impl.texasholdem.simple.SimpleTexasHoldEmPokerBotConfig;
import io.freezing.ai.domain.PokerState;
import io.freezing.ai.exception.input.PokerInputException;
import io.freezing.ai.function.PokerStateUtils;
import io.freezing.ai.io.error.impl.StandardOutputInputExceptionHandler;
import io.freezing.ai.io.error.UnhandledExceptionHandler;
import io.freezing.ai.io.error.impl.StandardUnhandledExceptionHandler;
import io.freezing.ai.io.input.PokerInput;
import io.freezing.ai.io.error.PokerInputExceptionHandler;
import io.freezing.ai.io.input.stdin.StandardPokerInput;
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
    private final PokerOutput output;
    private final PokerBot bot;

    // Error handlers
    private final PokerInputExceptionHandler<PokerInputException> exceptionHandler;
    private final UnhandledExceptionHandler unhandledExceptionHandler;

    public AIRunner(PokerInput input, PokerOutput output,
                    PokerInputExceptionHandler<PokerInputException> exceptionHandler,
                    UnhandledExceptionHandler unhandledExceptionHandler,
                    PokerBot bot) {
        this.input = input;
        this.output = output;
        this.exceptionHandler = exceptionHandler;
        this.unhandledExceptionHandler = unhandledExceptionHandler;
        this.bot = bot;
    }

    public void run() {
        while (true) {
            try {
                // TODO: Can I do something nice as using Extractors in Scala?
                Optional<PokerState> stateOpt = this.input.getNextState();

                if (stateOpt.isPresent()) {
                    PokerState state = stateOpt.get();
                    // TODO: Do I want this check here or in the PokerState itself?
                    // Pros in the PokerState - can't create invalid PokerState
                    // Cons in the PokerState - Doesn't work if logic is different for other games
                    //                        - Don't like to have logic like this in the constructor,
                    //                        - It might have to be unchecked exception which I don't like
                    PokerStateUtils.validateNoDuplicates(state);
                    BotAction action = bot.nextAction(state);
                    output.handle(action);
                } else {
                    // No more input
                    logger.info("Shutting down the AI Runner. No more input expected.");
                    return;
                }
            } catch (PokerInputException e) {
                exceptionHandler.handle(e);
            } catch (Exception e) {
                unhandledExceptionHandler.handle(e);
            }
        }
    }

    @Override
    public void close() throws Exception {
        this.input.close();
        this.output.close();
    }

    public static void main(String[] args) throws Exception {
        PokerInput input = new StandardPokerInput(
                System.in,
                new TexasHoldemInputParser(),
                new StandardPokerMessageHandler<>(System.out)
        );
        PokerOutput output = new StandardOutputBotActionHandler(System.out);
        PokerInputExceptionHandler exceptionHandler = new StandardOutputInputExceptionHandler(System.err);
        UnhandledExceptionHandler unhandledExceptionHandler = new StandardUnhandledExceptionHandler(System.err);

        // Create Simple AI Bot
        TexasHoldEmRules rules = new TexasHoldEmRules(9, 60000, 0, 10, 1000, new TexasHoldEmHandEvaluator());
        PokerBot bot = new SimpleTexasHoldEmPokerBot(rules, new SimpleTexasHoldEmPokerBotConfig(10000, 186544L));

        // Initialize AI Runner
        AIRunner ai = new AIRunner(input, output, exceptionHandler, unhandledExceptionHandler, bot);

        // Run AI
        ai.run();

        // AI has finished. No more input expected. Close IO.
        ai.close();
    }

    // For testing:
    // 1 2 50 100 250 100 1000 ST DA SA D3 D4 CT S8
}

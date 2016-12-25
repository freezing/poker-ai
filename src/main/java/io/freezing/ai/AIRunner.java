package io.freezing.ai;

import io.freezing.ai.bot.PokerBot;
import io.freezing.ai.bot.action.BotAction;
import io.freezing.ai.bot.impl.texas_holdem.simple.SimpleTexasHoldemPokerBot;
import io.freezing.ai.config.TexasHoldemConfig;
import io.freezing.ai.domain.PokerState;
import io.freezing.ai.io.input.PokerInput;
import io.freezing.ai.io.input.StandardPokerInput;
import io.freezing.ai.io.output.PokerOutput;
import io.freezing.ai.io.output.StandardOutputBotActionHandler;
import io.freezing.ai.io.parser.impl.TexasHoldemInputParser;

import java.util.Optional;
import java.util.logging.Logger;

public class AIRunner implements AutoCloseable {
    private static final Logger logger = Logger.getLogger(AIRunner.class.getName());

    private final PokerInput input;
    private final PokerOutput output;
    private final PokerBot bot;

    public AIRunner(PokerInput input, PokerOutput output, PokerBot bot) {
        this.input = input;
        this.output = output;
        this.bot = bot;
    }

    public void run() {
        while (true) {
            // TODO: Can I do something nice as using Extractors in Scala?
            Optional<PokerState> stateOpt = this.input.getNextState();

            if (stateOpt.isPresent()) {
                PokerState state = stateOpt.get();
                BotAction action = bot.nextAction(state);

            } else {
                // No more input
                logger.info("Shutting down the AI Runner. No more input expected.");
                return;
            }
        }
    }

    @Override
    public void close() throws Exception {
        this.input.close();
        this.output.close();
    }

    public static void main(String[] args) throws Exception {
        PokerInput input = new StandardPokerInput(System.in, new TexasHoldemInputParser());
        TexasHoldemConfig config = new TexasHoldemConfig(60000, 0, 10, 1000, "Simple Bot");
        PokerOutput output = new StandardOutputBotActionHandler(System.out);

        // Create Simple AI Bot
        PokerBot bot = new SimpleTexasHoldemPokerBot(config);

        // Initialize AI Runner
        AIRunner ai = new AIRunner(input, output, bot);

        // Run AI
        ai.run();

        // AI has finished. No more input expected. Close IO.
        ai.close();
    }
}

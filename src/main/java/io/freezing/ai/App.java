package io.freezing.ai;

import io.freezing.ai.bot.PokerBot;
import io.freezing.ai.bot.impl.texasholdem.simple.SimpleTexasHoldEmPokerBot;
import io.freezing.ai.bot.impl.texasholdem.simple.SimpleTexasHoldEmPokerBotConfig;
import io.freezing.ai.exception.input.PokerInputException;
import io.freezing.ai.io.cli.CommandLineParser;
import io.freezing.ai.io.cli.CommandProcessor;
import io.freezing.ai.io.cli.impl.CommandLineParserImpl;
import io.freezing.ai.io.cli.impl.CommandProcessorImpl;
import io.freezing.ai.io.error.PokerInputExceptionHandler;
import io.freezing.ai.io.error.UnhandledExceptionHandler;
import io.freezing.ai.io.error.impl.StandardOutputInputExceptionHandler;
import io.freezing.ai.io.error.impl.StandardUnhandledExceptionHandler;
import io.freezing.ai.io.output.PokerOutput;
import io.freezing.ai.io.output.StandardOutputBotActionHandler;
import io.freezing.ai.rules.impl.texasholdem.TexasHoldEmHandEvaluator;
import io.freezing.ai.rules.impl.texasholdem.TexasHoldEmRules;
import io.freezing.ai.runner.BotRunner;

import java.io.*;
import java.util.Scanner;

public class App implements AutoCloseable {
    private final CommandLineParser commandLineParser;
    private final CommandProcessor commandProcessor;
    private final BotRunner botRunner;
    private final Scanner commandScanner;

    // Error handlers
    private final PokerInputExceptionHandler<PokerInputException> pokerInputExceptionHandler;
    private final UnhandledExceptionHandler unhandledExceptionHandler;

    public App() {
        this.commandScanner = new Scanner(System.in);

        PokerOutput pokerOutput = new StandardOutputBotActionHandler(System.out);
        this.pokerInputExceptionHandler = new StandardOutputInputExceptionHandler(System.err);
        this.unhandledExceptionHandler = new StandardUnhandledExceptionHandler(System.err);

        TexasHoldEmHandEvaluator handEvaluator = new TexasHoldEmHandEvaluator();
        TexasHoldEmRules pokerRules = new TexasHoldEmRules(9, 60000, 8000, 9, 1500, handEvaluator);
        SimpleTexasHoldEmPokerBotConfig pokerConfig = new SimpleTexasHoldEmPokerBotConfig(10000, 12443L);
        PokerBot pokerBot = new SimpleTexasHoldEmPokerBot(pokerRules, pokerConfig);

        this.commandLineParser = new CommandLineParserImpl();
        this.botRunner = new BotRunner(pokerBot, pokerOutput);
        this.commandProcessor = new CommandProcessorImpl(botRunner);
    }

    public void singleRun() throws IOException, PokerInputException {
        commandProcessor.process(commandLineParser.parseLine(commandScanner.nextLine()));
    }

    public void runForever() {
        while (true) {
            try {
                singleRun();
            } catch (PokerInputException e) {
                pokerInputExceptionHandler.handle(e);
            } catch (Exception e) {
                unhandledExceptionHandler.handle(e);
            }
        }
    }

    @Override
    public void close() throws Exception {
        this.botRunner.close();
        this.commandScanner.close();
    }

    public static void main(String[] args) {
        new App().runForever();
    }
}

// 1 2 1 50 100 250 100 1000 ST DA SA D3 D4 CT S8

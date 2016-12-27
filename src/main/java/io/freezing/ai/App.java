package io.freezing.ai;

import io.freezing.ai.bot.PokerBot;
import io.freezing.ai.bot.impl.texasholdem.simple.SimpleTexasHoldEmPokerBot;
import io.freezing.ai.bot.impl.texasholdem.simple.SimpleTexasHoldEmPokerBotConfig;
import io.freezing.ai.io.cli.CommandLineParser;
import io.freezing.ai.io.cli.CommandProcessor;
import io.freezing.ai.io.cli.impl.CommandLineParserImpl;
import io.freezing.ai.io.cli.impl.CommandProcessorImpl;
import io.freezing.ai.io.error.PokerInputExceptionHandler;
import io.freezing.ai.io.error.UnhandledExceptionHandler;
import io.freezing.ai.io.error.impl.StandardOutputInputExceptionHandler;
import io.freezing.ai.io.error.impl.StandardUnhandledExceptionHandler;
import io.freezing.ai.io.input.PokerInput;
import io.freezing.ai.io.input.stdin.StandardPokerInput;
import io.freezing.ai.io.message.impl.StandardPokerMessageHandler;
import io.freezing.ai.io.output.PokerOutput;
import io.freezing.ai.io.output.StandardOutputBotActionHandler;
import io.freezing.ai.io.parser.impl.TexasHoldemInputParser;
import io.freezing.ai.rules.impl.texasholdem.TexasHoldEmHandEvaluator;
import io.freezing.ai.rules.impl.texasholdem.TexasHoldEmRules;
import io.freezing.ai.runner.AIRunner;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Scanner;

public class App implements AutoCloseable {
    private final CommandLineParser commandLineParser;
    private final CommandProcessor commandProcessor;
    private final AIRunner aiRunner;
    private final Scanner commandScanner;

    public App() throws IOException {
        this.commandScanner = new Scanner(System.in);
        PipedOutputStream pipedPokerOutputStream = new PipedOutputStream();
        PipedInputStream pokerInputStream = new PipedInputStream(pipedPokerOutputStream);

        PokerInput pokerInput = new StandardPokerInput(
                pokerInputStream,
                new TexasHoldemInputParser(),
                new StandardPokerMessageHandler<>(System.out)
        );
        PokerOutput pokerOutput = new StandardOutputBotActionHandler(System.out);
        PokerInputExceptionHandler pokerInputExceptionHandler = new StandardOutputInputExceptionHandler(System.err);
        UnhandledExceptionHandler unhandledExceptionHandler = new StandardUnhandledExceptionHandler(System.err);

        TexasHoldEmHandEvaluator handEvaluator = new TexasHoldEmHandEvaluator();
        TexasHoldEmRules pokerRules = new TexasHoldEmRules(9, 60000, 8000, 9, 1500, handEvaluator);
        SimpleTexasHoldEmPokerBotConfig pokerConfig = new SimpleTexasHoldEmPokerBotConfig(10000, 12443L);
        PokerBot pokerBot = new SimpleTexasHoldEmPokerBot(pokerRules, pokerConfig);

        this.commandLineParser = new CommandLineParserImpl();
        this.aiRunner = new AIRunner(pokerInput, pokerOutput,
                pokerInputExceptionHandler, unhandledExceptionHandler,
                pokerBot);
        this.commandProcessor = new CommandProcessorImpl(pipedPokerOutputStream);
    }

    public void singleRun() {
        commandProcessor.process(commandLineParser.parseLine(commandScanner.nextLine()));
        this.aiRunner.singleRun();
    }

    public void runForever() {
        while (true) {
            singleRun();
        }
    }

    @Override
    public void close() throws Exception {
        this.aiRunner.close();
        this.commandScanner.close();
    }

    public static void main(String[] args) throws IOException {
        new App().runForever();
    }
}

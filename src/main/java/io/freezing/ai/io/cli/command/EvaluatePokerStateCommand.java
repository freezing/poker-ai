package io.freezing.ai.io.cli.command;

public class EvaluatePokerStateCommand implements Command {
    private final String line;

    public EvaluatePokerStateCommand(String line) {
        this.line = line;
    }

    public String getLine() {
        return line;
    }

    @Override
    public String toString() {
        return String.format("EvaluatePokerStateCommand(state = %s)", line);
    }
}

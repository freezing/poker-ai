package io.freezing.ai.io.cli.command;

import java.nio.file.Path;

public class EvaluateImagePokerStateCommand implements Command {
    private final Path imagePath;

    public EvaluateImagePokerStateCommand(Path imagePath) {
        this.imagePath = imagePath;
    }

    public Path getImagePath() {
        return imagePath;
    }

    @Override
    public String toString() {
        return String.format("EvaluateImagePokerStateCommand(path = %s)", imagePath);
    }
}

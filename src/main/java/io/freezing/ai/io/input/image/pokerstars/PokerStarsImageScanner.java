package io.freezing.ai.io.input.image.pokerstars;

import io.freezing.ai.domain.PokerState;
import io.freezing.ai.exception.input.PokerInputException;
import io.freezing.ai.io.input.PokerInput;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.awt.image.BufferedImage;
import java.util.Optional;

public class PokerStarsImageScanner implements PokerInput {
    private final BufferedImage image;

    public PokerStarsImageScanner(BufferedImage image) {
        this.image = image;
    }

    @Override
    public Optional<PokerState> getState() throws PokerInputException {
        throw new NotImplementedException();
    }

    @Override
    public void close() throws Exception {

    }
}

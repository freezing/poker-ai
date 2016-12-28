package io.freezing.ai.io.input.image.pokerstars;

import io.freezing.ai.domain.PokerState;
import io.freezing.ai.exception.input.PokerInputException;
import io.freezing.ai.io.input.PokerInput;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_imgcodecs;
import org.bytedeco.javacpp.opencv_imgproc;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    public static void main(String[] args) {
        Path imageFile = Paths.get("/Users/freezing/Data/PokerStars/Labeled/1.png");
        opencv_core.Mat image = opencv_imgcodecs.imread(imageFile.toString());
        opencv_core.Mat dest = new opencv_core.Mat();
        opencv_imgproc.resize(image, dest, new opencv_core.Size(500, 300));
        opencv_imgcodecs.imwrite("/Users/freezing/Data/PokerStars/Analyzed/1.png", dest);
    }
}

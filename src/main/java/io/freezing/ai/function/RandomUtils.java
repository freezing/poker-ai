package io.freezing.ai.function;

import java.util.Random;

public class RandomUtils {
    public static void shuffleArray(int[] array, Random rnd, int iterations) {
        while (iterations-- > 0) {
            int i = rnd.nextInt(array.length);
            int j = rnd.nextInt(array.length);

            int tmp = array[i];
            array[i] = array[j];
            array[j] = tmp;
        }
    }
}

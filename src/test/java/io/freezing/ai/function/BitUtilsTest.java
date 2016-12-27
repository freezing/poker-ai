package io.freezing.ai.function;

import org.junit.Assert;
import org.junit.Test;

public class BitUtilsTest {
    @Test
    public void testCountSetBits() {
        Assert.assertTrue(4 == BitUtils.countSetBits(15));
        Assert.assertTrue(1 == BitUtils.countSetBits(8));
        Assert.assertTrue(25 == BitUtils.countSetBits(2147483150));
        Assert.assertTrue(12 == BitUtils.countSetBits(17920377));

        // Test all versions of one bit
        for (int k = 0; k < 32; k++) {
            Assert.assertTrue(1 == BitUtils.countSetBits(1 << k));
        }

        for (int k = 0; k < 24; k++) {
            Assert.assertTrue(4 == BitUtils.countSetBits(0x000F << k));
        }
    }
}

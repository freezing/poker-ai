package io.freezing.ai.function;

import org.junit.Assert;
import org.junit.Test;

public class BitUtilsTest {
    @Test
    public void testCountSetBitsInt() {
        Assert.assertTrue(4 == BitUtils.countSetBits(15));
        Assert.assertTrue(1 == BitUtils.countSetBits(8));
        Assert.assertTrue(25 == BitUtils.countSetBits(2147483150));
        Assert.assertTrue(12 == BitUtils.countSetBits(17920377));

        // Test all versions of one bit
        for (int k = 0; k < 32; k++) {
            Assert.assertTrue(1 == BitUtils.countSetBits(1 << k));
        }

        for (int k = 0; k < 24; k++) {
            Assert.assertTrue(4 == BitUtils.countSetBits(0x0F << k));
            Assert.assertTrue(4 == BitUtils.countSetBits(0xF0 << k));
            Assert.assertTrue(8 == BitUtils.countSetBits(0xFF << k));
        }
    }

    @Test
    public void testCountSetBitsLong() {
        Assert.assertTrue(4 == BitUtils.countSetBits(15L));
        Assert.assertTrue(1 == BitUtils.countSetBits(8L));
        Assert.assertTrue(25 == BitUtils.countSetBits(2147483150L));
        Assert.assertTrue(12 == BitUtils.countSetBits(17920377L));

        // Test all versions of one bit
        for (int k = 0; k < 64; k++) {
            Assert.assertTrue(1 == BitUtils.countSetBits(1L << k));
        }

        for (int k = 0; k < 64 - 4*4; k++) {
            Assert.assertTrue(4  == BitUtils.countSetBits(0x000FL << k));
            Assert.assertTrue(8  == BitUtils.countSetBits(0xF00FL << k));
            Assert.assertTrue(12 == BitUtils.countSetBits(0xF0FFL << k));
            Assert.assertTrue(12 == BitUtils.countSetBits(0xFF0FL << k));
            Assert.assertTrue(16 == BitUtils.countSetBits(0xFFFFL << k));
        }
    }
}

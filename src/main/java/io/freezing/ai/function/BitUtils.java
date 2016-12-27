package io.freezing.ai.function;

public class BitUtils {
    public static int countSetBits(int n) {
        n = n - ((n >> 1) & 0x55555555);
        n = (n & 0x33333333) + ((n >> 2) & 0x33333333);
        n = (n + (n >> 4)) & 0x0F0F0F0F;
        n = n + (n >> 8);
        n = n + (n >> 16);
        return n & 0x0000003F;
    }

    public static int countSetBits(long n) {
        return
                countSetBits((int)((n      ) & 0xFFFF)) +
                countSetBits((int)((n >> 16) & 0xFFFF)) +
                countSetBits((int)((n >> 32) & 0xFFFF)) +
                countSetBits((int)((n >> 48) & 0xFFFF));
    }
}

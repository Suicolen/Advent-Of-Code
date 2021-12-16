package suic._2021.days.day16;

import lombok.Getter;

public class BitReader {

    private final byte[] bytes;
    @Getter
    private int position;

    public BitReader(byte[] bytes) {
        this.bytes = bytes;
    }

    public int readBits(int bits) {

        int bytePos = position >> 3;
        int msb = 8 - (position & 0x7);
        int i = 0;

        position += bits;

        for (; bits > msb; msb = 8) {
            i += ((bytes[bytePos++] & BIT_MASKS[msb]) << (bits - msb));
            bits -= msb;
        }

        if (bits == msb) {
            i += bytes[bytePos] & BIT_MASKS[msb];
        } else {
            i += ((bytes[bytePos] >> (msb - bits)) & BIT_MASKS[bits]);
        }

        return i;
    }

    private static final int[] BIT_MASKS;

    static {
        BIT_MASKS = new int[32];
        for (int i = 0; i < 32; i++) {
            BIT_MASKS[i] = (1 << i) - 1;
        }
    }
}
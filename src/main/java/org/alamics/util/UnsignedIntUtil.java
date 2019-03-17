package org.alamics.util;

import org.joou.UByte;
import org.joou.UInteger;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class UnsignedIntUtil {
  public static long readUnsignedInt(byte[] b) {
    byte[] fullB = new byte[8];
    fullB[0] = b.length > 0 ? b[0] : 0;
    fullB[1] = b.length > 1 ? b[1] : 0;
    fullB[2] = b.length > 2 ? b[2] : 0;
    fullB[3] = b.length > 3 ? b[3] : 0;
    fullB[4] = b.length > 4 ? b[4] : 0;
    fullB[5] = b.length > 5 ? b[5] : 0;
    fullB[6] = b.length > 6 ? b[6] : 0;
    fullB[7] = b.length > 7 ? b[7] : 0;
    long length = ByteBuffer
        .wrap(fullB)
        .order(ByteOrder.LITTLE_ENDIAN)
        .getLong();
    return length;
  }
}

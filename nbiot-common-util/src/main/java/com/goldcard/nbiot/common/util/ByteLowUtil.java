package com.goldcard.nbiot.common.util;

public class ByteLowUtil {

	/**
	 * long 转换为字�?
	 * @param x
	 * @return
	 */
	public static byte[] long2Byte(long x) {

		byte[] bb = new byte[8];
		bb[0] = (byte) (x >> 0);
		bb[1] = (byte) (x >> 8);
		bb[2] = (byte) (x >> 16);
		bb[3] = (byte) (x >> 24);
		bb[4] = (byte) (x >> 32);
		bb[5] = (byte) (x >> 40);
		bb[6] = (byte) (x >> 48);
		bb[7] = (byte) (x >> 56);

		return bb;
	}

	/**
	 * 字节数组转为Long
	 * 
	 * @param bb
	 * @return
	 */
	public static Long byte2Long(byte[] bb) {

		return ((((long) bb[0] & 0xff) << 0) | (((long) bb[1] & 0xff) << 8)
				| (((long) bb[2] & 0xff) << 16) | (((long) bb[3] & 0xff) << 24)
				| (((long) bb[4] & 0xff) << 32) | (((long) bb[5] & 0xff) << 40)
				| (((long) bb[6] & 0xff) << 48) | (((long) bb[7] & 0xff) << 56));

	}

	/**
	 * int 转换为字�?
	 * @param x
	 * @return
	 */
	public static byte[] int2Byte(int x) {
		byte[] bb = new byte[4];
		bb[0] = (byte) (x >> 0);
		bb[1] = (byte) (x >> 8);
		bb[2] = (byte) (x >> 16);
		bb[3] = (byte) (x >> 24);
		return bb;
	}

	/**
	 * 字节转换为int
	 * @param bb
	 * @return
	 */
	public static int byte2Int(byte[] bb) {

		return ((((int) bb[0] & 0xff) << 0) | (((int) bb[1] & 0xff) << 8)
				| (((int) bb[2] & 0xff) << 16) | (((int) bb[3] & 0xff) << 24));

	}

	/**
	 * short转换为字�?
	 * 
	 * @param bb
	 * @return
	 */
	public static byte[] short2Byte(short x) {

		byte[] bb = new byte[2];
		bb[0] = (byte) (x >> 0);
		bb[1] = (byte) (x >> 8);

		return bb;
	}

	/**
	 * 字节转换为short
	 * @param bytes
	 * @return
	 */
	public static short byte2Short(byte[] bytes) {

		if (bytes != null && bytes.length == 2) {
			return (short) ((0xff & bytes[0]) | (0xff00 & (bytes[1] << 8)));
		}
		return 0;
	}

	public static int getUnsignedInt(byte[] bytes) {
		return byte2Short(bytes) & 0x0FFFF;
	}

	public static long getUnsignedLong(byte[] bytes) { // 将int数据转换�?~4294967295
														// (0xFFFFFFFF即DWORD)�?
		return byte2Int(bytes) & 0x0FFFFFFFFl;
	}
}

package com.goldcard.nbiot.common.util;

public class CheckUtil {
	/**
	 * 返回CS校验码
	 * @param messageHex
	 * @return
	 */
	/*public static byte getCS(byte[] data) {

		int sum = 0;
		for (int i = 0; i < data.length; i++) {

			sum += Integer.valueOf(ByteUtil.byteToHex(data[i]), 16);
		}

		String sumHex = Integer.toHexString(sum);
		
		sumHex = sumHex.substring(sumHex.length()-2);
		
		return ByteUtil.hexToByte(sumHex);

	}*/
	
	public static byte getCS(byte[] bytes){
		StringBuilder sb = new StringBuilder();
		String tmp = null;
		int sum = 0;
		for (byte b : bytes) {
			// 将每个字节与0xFF进行与运算，然后转化为10进制，然后借助于Integer再转化为16进制
			tmp = Integer.toHexString(0xFF & b);
			if (tmp.length() == 1)// 每个字节8为，转为16进制标志，2个16进制位
			{
				tmp = "0" + tmp;
			}
			sum = sum + Integer.parseInt(tmp.toString(), 16);
			sb.append(tmp);
		}
		sum = sum % 256;
		String result = Integer.toHexString(sum);
		if(result.length() == 1){
			result = "0" + result;
		}
		return ByteUtil.hexToByte(result);
	}

}

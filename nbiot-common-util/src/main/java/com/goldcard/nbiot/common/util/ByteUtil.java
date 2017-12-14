package com.goldcard.nbiot.common.util;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

public class ByteUtil {

	/**
	 * 字节转换�?6进制字符�?
	 * @param b
	 * @return
	 */
	public static String byteToHex(byte b) {

		String hex = Integer.toHexString(b & 0xFF);
		if (hex.length() == 1) {
			hex = '0' + hex;
		}
		return hex.toUpperCase();
	}
	
	/**
	 * 字节数组转换�?6 进制HEX字符�?
	 * @param bs
	 * @return
	 */
	public static String byteToHex(byte[] bs){
		
		StringBuffer sb = new StringBuffer();
		if(bs != null && bs.length > 0){
			for (int i = 0; i < bs.length; i++) {   
				sb.append(byteToHex(bs[i]));
		    }  
		}
		
		return sb.toString();
	}
	
	public static byte hexToByte(int n){
		
		return (byte)n;
	}
	
	
	/**
	 * HEX字符串转换为字节
	 * @param hexString
	 * @return
	 */
	public static byte hexToByte(String hexString){
		
		 byte[] b = hexStringToBytes(hexString);
		 if(b == null || b.length != 1){
		//	 throw new CommandException(hexString+"转换成字节异�?);
		 }
		
		return b[0];
	}
	
	
	 /**
	  * HEX字符串转换为字节数组
	 * @param hexString
	 * @return
	 */
	public static byte[] hexStringToBytes(String hexString) {  
		    if (hexString == null || hexString.equals("")) {  
		        return null;  
		    }  
		    hexString = hexString.toUpperCase();  
		    int length = hexString.length() / 2;  
		    char[] hexChars = hexString.toCharArray();  
		    byte[] d = new byte[length];  
		    for (int i = 0; i < length; i++) {  
		        int pos = i * 2;  
		        d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));  
		    }  
		    return d;  
		}  
	   
	 private static byte charToByte(char c) {  
		    return (byte) "0123456789ABCDEF".indexOf(c);  
		}
	
	 
	/**
	 * 字节数组转为ASCII字符�?
	 * @param bs
	 * @return
	 */
	public static String parseAscii(byte[] bs){
		StringBuffer str=new StringBuffer("");
		for(int i=0;i<bs.length;i++){
			char a=(char) Integer.parseInt(String.valueOf(bs[i]));
			str.append(a);
		}
		return str.toString();
	}
	
	/**
	 * 字符串转为ASCII，再转为16进制字符�?
	 * @param str
	 * @return
	 */
	public static String getAscii(String str){
		if(str==null ||"".equals(str)){
			return null;
		}
		char[] ch=str.toCharArray();
		StringBuffer sb=new StringBuffer("");
		for(int i=0;i<ch.length;i++){
			sb.append(Integer.toHexString(Integer.valueOf(Integer.toString(ch[i]))));
		}
		return sb.toString();
	}
	
	/**
	 * 字节数组转为�?0进制字符�?
	 * @param bs
	 * @return
	 */
	public static String byteToStr(byte[] bs){
		
		StringBuffer sb = new StringBuffer();

		if(bs != null && bs.length > 0){
			for (int i = 0; i < bs.length; i++) {
				String str = String.valueOf(bs[i] & 0xFF);
				if (str.length() == 1) {
					str = '0' + str;
				}
				sb.append(str);
			}  
		}
		return sb.toString();
	}
	
	public static byte[] strTobyte(String str){
		int length=str.length()/2;
		byte[] bytes =new byte[length];
		if(str != null && length > 0){
			for (int i = 0; i < length; i++) {
				String s = str.substring(i*2,i*2+2);
				bytes[i]=(byte)Integer.parseInt(s);
			}  
		}
		return bytes;
	}
	
	
	/**
	 * 字符串转换为byte数组
	 * @param str
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static byte[] stringToBytes(String str) throws UnsupportedEncodingException {
		// 根据默认编码获取字节数组
		byte[] bytes = str.getBytes("GBK");
		StringBuilder sb = new StringBuilder(bytes.length * 2);
		// 将字节数组中每个字节拆解�?�?6进制整数
		for (int i = 0; i < bytes.length; i++) {
			sb.append("0123456789ABCDEF".charAt((bytes[i] & 0xf0) >> 4));
			sb.append("0123456789ABCDEF".charAt((bytes[i] & 0x0f) >> 0));
		}
		return hexStringToBytes(sb.toString());
	}
	
	/**
	 * byte数组转换为字符串
	 * @param bts
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String bytesToString(byte[] bts) throws UnsupportedEncodingException {
		String bytes=byteToHex(bts);
		ByteArrayOutputStream baos = new ByteArrayOutputStream(
				bytes.length() / 2);
		// 将每2�?6进制整数组装成一个字�?
		for (int i = 0; i < bytes.length(); i += 2)
			baos.write(("0123456789ABCDEF".indexOf(bytes.charAt(i)) << 4 | "0123456789ABCDEF"
					.indexOf(bytes.charAt(i + 1))));
		return new String(baos.toByteArray(),"GBK");
	}
	

	
	public static byte[] long2Byte(long x) {

		byte[] bb = new byte[8];
			bb[0] = (byte) (x >> 56); 
	        bb[1] = (byte) (x >> 48); 
	        bb[2] = (byte) (x >> 40); 
	        bb[3] = (byte) (x >> 32); 
	        bb[4] = (byte) (x >> 24); 
	        bb[5] = (byte) (x >> 16); 
	        bb[6] = (byte) (x >> 8); 
	        bb[7] = (byte) (x >> 0); 
	        
	   return bb;
  } 


	/**
	 * 字节数组转为Long
	 * @param bb
	 * @return
	 */
	public static Long byte2Long(byte[] bb) { 
		
		if(bb!=null && bb.length==8){
			
			ByteBuffer aa = ByteBuffer.wrap(bb);
			
			return aa.getLong();
		}
		
		return null;
		
	/**	
       return ((((long) bb[0] & 0xff) << 56) 
               | (((long) bb[1] & 0xff) << 48) 
               | (((long) bb[2] & 0xff) << 40) 
               | (((long) bb[3] & 0xff) << 32) 
               | (((long) bb[4] & 0xff) << 24) 
               | (((long) bb[5] & 0xff) << 16) 
               | (((long) bb[6] & 0xff) << 8) 
               | (((long) bb[7] & 0xff) << 0)); 
               
             **/
  } 
	
	public static byte[] short2Byte(short x) {

		byte[] bb = new byte[2];
	        bb[0] = (byte) (x >> 8); 
	        bb[1] = (byte) (x >> 0); 
	        
	   return bb;
  } 


	public static Short byte2Short(byte[] bb) { 
		
		if(bb!=null && bb.length==2){
			
			ByteBuffer aa = ByteBuffer.wrap(bb);
			
			return aa.getShort();
		}
		
		return null;
	}
	/**
	 * 字节数组转为int
	 * @param bb
	 * @return
	 */
	public static Integer byte2Int(byte[] bb) { 
		
		if(bb!=null && bb.length==4){
			
			ByteBuffer aa = ByteBuffer.wrap(bb);
			
			return aa.getInt();
		}
		
		return null;
		
    } 
	/**
	 * int 转为 字节数组
	 * @param bb
	 * @return
	 */
	public static byte[] int2Byte(int x) {
		byte[] bb = new byte[4];
		bb[0] = (byte) (x >> 24);
		bb[1] = (byte) (x >> 16);
		bb[2] = (byte) (x >> 8);
		bb[3] = (byte) (x >> 0);
		return bb;
	}
	/**
	 * byte数组 �?二进�?
	 * 
	 * @param bytes
	 * @return
	 */
	public static String bytesToBinary(byte[] bytes){
		
		StringBuffer sb = new StringBuffer();
		
		for(byte b : bytes){
			
			sb.append(byteToBinary(b));
		}
		return sb.toString();
	}
	
	/**
	 * byte �?二进�?例如   0x01 ---> 00000001
	 * 
	 * @param bye
	 * @return
	 */
	public static String byteToBinary(byte bye){
		String status1=Integer.toBinaryString(bye & 0xFF);
		int length=8-status1.length();
		for(int i=0;i<length;i++){
			status1="0"+status1;
		}
		return status1;
		
	}
	/**
	 * 二进�?异或 运算（相同为0�?
	 * @param a 二进制字符串
	 * @param b 二进制字符串
	 * @return 二进制字符串
	 */
	public static String XOR(String a,String b){
		if(null!=a || null!=b){
			StringBuffer sb = new StringBuffer();
			for(int i=0;i<b.length();i++){
				if(a.charAt(i)==b.charAt(i)){
					sb.append("0");
				}else {
					sb.append("1");
				}
			}
			return sb.toString();
		}
		return null;
	}
	/**
	 * 二进制转换成16进制
	 * @param binary
	 * @return
	 */
	public static String binaryString2hexString(String binary) {  
        if (binary == null || binary.equals("") || binary.length() % 8 != 0){
        	return null;
        }
        StringBuffer tmp = new StringBuffer();  
        int iTmp = 0;  
        for (int i = 0; i < binary.length(); i += 4){  
            iTmp = 0;  
            for (int j = 0; j < 4; j++){  
                iTmp += Integer.parseInt(binary.substring(i + j, i + j + 1)) << (4 - j - 1);  
            }  
            tmp.append(Integer.toHexString(iTmp));  
        }
        return tmp.toString();  
    }
	/**
	 * 返回CS校验码 
	 */
	public static byte getCS(byte[] data) throws Exception {
		int sum = 0;
		for (int i = 0; i < data.length; i++) {
			sum += Integer.valueOf(byteToHex(data[i]), 16);
		}
		String sumHex = Integer.toHexString(sum);			
		sumHex = sumHex.substring(sumHex.length()-2);			
		return hexToByte(sumHex);
	}
	public static void main(String[] args) throws UnsupportedEncodingException{
		
//		int a = 1;
//		short b = -1;
//		int in = b;
//		byte[] bb = short2Byte(b);
//		short s = byte2Short(bb);
//		System.out.println(s);
	    String str="JJJSDSFSDF";
	    System.out.println(ByteUtil.bytesToString(ByteUtil.stringToBytes(str)));
	    
	    System.out.println(ByteUtil.parseAscii(ByteUtil.hexStringToBytes(ByteUtil.getAscii(str))));
		

	}

}

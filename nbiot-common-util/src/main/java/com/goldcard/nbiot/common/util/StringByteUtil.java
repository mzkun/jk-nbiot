package com.goldcard.nbiot.common.util;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.lang.StringUtils;

public class StringByteUtil {
	private final static String DES = "DES";
	@SuppressWarnings("unused")
	private static final String CODE = "523593D5B8DC1676";
	private static final String KEY = "C83E7386FA4DB629";
	
	public static void main(String[] args) {
		byte[] test = new byte[]{0x21,(byte) 0xA2};
		String tes = byteToHex(test);//21A2

		try {
			System.out.println(hexToByte("21"));//33
			System.out.println(hexToByte(21));//21
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(tes);
	}
	 /**HEX字符串转换为字节  数组 */
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
		 byte a=(byte) "0123456789ABCDEF".indexOf(c);
		 return a;
	}
	
	
	 /**
	 * 字节转换为16进制字符串
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
	 * 字节  数组   转换为16 进制HEX字符串
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
	
	public static byte[] decrypt(byte[] data) {
		String KEY = "C83E7386FA4DB629";
		return decrypt(data, KEY);
	}
	
	private static byte[] decrypt(byte[] data , String key){
				
		byte[] keys = hexStringToBytes(key);		
		byte[] datas = hexStringToBytes(byteToHex(data)+"523593D5B8DC1676");
		byte[] datas_jie = decrypt(datas , keys);		
		return datas_jie;
	}
	
	/**
	 * Description 根据键值进行解密
	 */
	private static byte[] decrypt(byte[] data, byte[] key) {
		// 生成一个可信任的随机数源
		SecureRandom sr = new SecureRandom();
		byte[] b = null;
		try {
			// 从原始密钥数据创建DESKeySpec对象
			DESKeySpec dks = new DESKeySpec(key);
			// 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey securekey = keyFactory.generateSecret(dks);
			// Cipher对象实际完成解密操作
			Cipher cipher = Cipher.getInstance("DES");
			// 用密钥初始化Cipher对象
			cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
			b = cipher.doFinal(data);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return b;
	}
	
	
	/** HEX字符串转换为字节*/
	public static byte hexToByte(String hexString) throws Exception{
		 byte[] b = hexStringToBytes(hexString);
		 if(b == null || b.length != 1){
			 throw new Exception("十六进制字符串"+hexString+"转换成字节异常");
		 }
		return b[0];
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
	
	
	/**
	 * CRC16 CCITT标准[ x16+x12+x5+1   1021] 
	 */
	public static byte[] getCRC(byte[] data) {
		int crc = 0x0000;
		for (int i = 0; i < data.length; i++) {
			crc = (data[i] << 8) ^ crc;
			for (int j = 0; j < 8; j++) {
				if ((crc & 0x8000) != 0){
					crc = crc - 0x8000;
					crc = (crc << 1) ^ 0x1021;
				}else{
					crc <<= 1;
				}
			}
		}
		
		String LO = Integer.toHexString(crc&0xFF);
		if(LO.length()==1){
			LO = "0"+LO;
		}
		
		String HI = Integer.toHexString((crc>>8)&0xFF);
		if(HI.length()==1){
			HI = "0" + HI ;
		}
		
		return  hexStringToBytes(StringUtils.upperCase(HI+LO)) ; 
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
		// 将每2位16进制整数组装成一个字节
		for (int i = 0; i < bytes.length(); i += 2)
			baos.write(("0123456789ABCDEF".indexOf(bytes.charAt(i)) << 4 | "0123456789ABCDEF"
					.indexOf(bytes.charAt(i + 1))));
		return new String(baos.toByteArray(),"GBK");
	}
	
	/**
	 * 字节数组转为了10进制字符串
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
	
	/**
	 * 例如 data = -123.456 修改累计气量，由于表内存储累计气量为1符号+4整数+3小数。 修改的时候也按这样的模式执行。
	 * 例如要将8字节BCD码为{0x01,0x00,0x00,0x01,0x23,0x45,0x60,0x00} 累计气量修改为-123.456
	 * @param d
	 * @param lengthDecimal
	 * @param highMark
	 * @return
	 */
	public static double getBCDDoubleHighMark(byte[] d, int lengthDecimal, boolean highMark){
		StringBuffer data = new StringBuffer(byteToHex(d));
		int fuwei = 1; 
		double result=0.0;
		data.insert((d.length-lengthDecimal)*2, ".");
		if(highMark){
			if("01".equals(data.substring(0,2))){
			   fuwei=-1;
			}
		}
		result=fuwei*Double.valueOf(data.toString());
		return result;
	}
	
	/**
	 * byte 转 二进制 例如   0x01 ---> 00000001
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
	
	public static byte hexToByte(int n){
		
		return (byte)n;
	}
	
	
	
	/**
	 * 返回指定字节数据长度 lengthAll的data , 小数部分占lengthDecimal个字节    , 且最高位表示符号位
	 * 整数前补0x00 , 小数部分后补0x00  
	 * 例如 
	 * 		  data = -123.456  lengthAll = 8 lengthDecimal = 3
	 * return {0x01, 0x00 ,0x00 , 0x01 , 0x23 , 0x45 , 0x60 , 0x00}
	 * lengthAll
	 * lengthDecimal:
	 * 
	 */
	public static byte[] getBCDDoubleWithHighMark(double data, int lengthAll, int lengthDecimal, boolean highMark) {


		data = BigDecimal.valueOf(data).setScale(lengthDecimal*2, BigDecimal.ROUND_DOWN).doubleValue();
		
		String d = String.valueOf(data);
		
		return getBCD(d , lengthAll , lengthDecimal , highMark);
	}
	

	/**
	 * getBCD( -123.456, 8 , 3 , true ) 返回 {0x01,0x00,0x00,0x01,0x23,0x45,0x60,0x00}
	 * 
	 * 例如 data = -123.456 修改累计气量，由于表内存储累计气量为1符号+4整数+3小数。 修改的时候也按这样的模式执行。
	 * 例如要将累计气量修改为-123.456方、则形成的8字节BCD码为{0x01,0x00,0x00,0x01,0x23,0x45,0x60,0x00}
	 * @param data 需要转换的数据
	 * @param lengthAll  总共几个字节位数
	 * @param lengthDecimal 小数和几个字节
	 * @param highMark  最高位是否表示正负 [ 正 :0x00 ; 负:0x01 ]
	 * @return
	 */
	private static byte[] getBCD(String data, int lengthAll, int lengthDecimal, boolean highMark) {
		String d = data;
		int xiao = 0;  // data  小数位数	 	-123.456   xiao=3
		int fuwei = 0; // fuwei 负数 		-123.456   fuwei=1
		int zheng = 0; // data  整数位数   	-123.456   zheng=3
		int length = d.length();
		int dian = d.indexOf(".");
		int fu = d.indexOf("-");
		// 去除小数.
		if (dian > -1) {
			xiao = length - dian - 1;
			d = d.substring(0, dian) + d.substring(dian + 1, d.length());
		}
		// 去除符号位
		if (fu > -1) {
			fuwei = 1;
			d = d.substring(0, fu) + d.substring(fu + 1, d.length());
		}		
		// 计算整数位
		if(xiao > 0){
			zheng  =  length - xiao - 1 - fuwei;
		}else{
			zheng  =  length - xiao  - fuwei;
		}
		// 前补0个数
		int before = (lengthAll - fuwei - lengthDecimal) * 2 - zheng;
		// 后补0个数
		int after = lengthDecimal * 2 - xiao;		
		// 补0
		String dataHex = fill0(d, before, after);	
		// 如果有负数位
		if(fuwei > 0){		
			// 如果需要标记符号位
			if(highMark){
				dataHex = "01" + dataHex;
			}else{
				dataHex = "00" + dataHex;
			}
		}
		return hexStringToBytes(dataHex);
	}

	/**
	 * 
	 * @param data
	 * @param before
	 *            data前补before个0
	 * @param after
	 *            data后补after个0
	 * @return
	 */
	private static String fill0(String data, int before, int after) {
		StringBuffer sb = new StringBuffer();
		if (before > 0) {
			for (int i = 0; i < before; i++) {
				sb.append("0");
			}
		}
		sb.append(data);
		if (after > 0) {
			for (int i = 0; i < after; i++) {
				sb.append("0");
			}
		}
		return sb.toString();
	}
	
	//二进制转int
	public int binaryToDecimal(String str){
		StringBuffer sb=new StringBuffer();
		int count=8-str.length();
		for(int i=0;i<count;i++){
			sb.append("0");	
		}
		sb.append(str);
		return Integer.parseInt(sb.toString(), 2);
	}
	//字节数组 放入 list
	public void addToList(List<Byte> list, byte[] data) {
		if (list != null && data != null) {
			for (byte b : data) {			
				list.add(b);
			}
		}

	}
	
	
	/**
	 * 把 list<Byte> 装换成 byte[]
	 */
	public byte[] listToArray(List<Byte> list) {
		if (list != null && list.size() > 0) {
			byte[] data = new byte[list.size()];		
			for (int i = 0; i < list.size(); i++) {
				data[i] = list.get(i);
			}
			return data;
		}
		return null;
	}
	
	
	
	
	public byte[] removeZero(byte[] datas) {
		List<Byte> list = new ArrayList<Byte>();
		addToList(list, datas);
		for (int i = datas.length - 1; i >= 0; i--) {
			if (datas[i] == 0) {
				list.remove(i);
			}
		}
		return listToArray(list);
	}

	/**
	 * 字节数组转为ASCII字符串
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
	
	
	public String ipToShortIp(String ipAdress) {
		while (ipAdress.contains(".0")) {
		   ipAdress = ipAdress.replace(".0", ".");
		}
		return ipAdress;
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
	 * 自动增加CRC码
	 * 返回  byte[]{message + CRC}
	 * @param message
	 * @return
	 */
	public static byte[] autoCRC(byte[] message){
		
		byte[] crc = getCRC(message);
		byte[] dest = new byte[message.length+crc.length];
		
		System.arraycopy(message, 0, dest, 0, message.length);
		System.arraycopy(crc, 0, dest, message.length, crc.length);
		
		return dest;
	}
	
	
	/**
	 * 加密 message with KEY
	 */
	public static byte[] encrypt(byte[] data){ 
		
		byte[] keys = hexStringToBytes(KEY);
		
		if(data.length>8){
			byte[] data_jia = encrypt(Arrays.copyOfRange(data, 0, 8) , keys);
			byte[] dest = new byte[data.length];
	        System.arraycopy(data_jia, 0, dest, 0, 8);
			System.arraycopy(data, 8, dest, 8, data.length-8);
			return dest;
		}else{
			byte[] data_jia = encrypt(data , keys);
			// 加密后会有8个字节变成16个字节, 后8个字节为固定字节 也就是 DESUtil.CODE
			byte[] dest = Arrays.copyOfRange(data_jia, 0, 8);
			return dest;
		}
		
	}
	
	/**
	 * Description 根据键值进行加密
	 * 
	 * @param data
	 * @param key
	 *            加密键byte数组
	 * @return
	 * @throws Exception
	 */
	private static byte[] encrypt(byte[] data, byte[] key) {
		// 生成一个可信任的随机数源
		SecureRandom sr = new SecureRandom();

		byte[] b = null;
		try {
			// 从原始密钥数据创建DESKeySpec对象
			DESKeySpec dks = new DESKeySpec(key);

			// 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
			SecretKey securekey = keyFactory.generateSecret(dks);

			// Cipher对象实际完成加密操作
			Cipher cipher = Cipher.getInstance(DES);

			// 用密钥初始化Cipher对象
			cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);

			b = cipher.doFinal(data);
		} catch (Exception e) {
			
			//log.error(e.getMessage() , e);
		} 

		return b;
	}
	
	
	/**
	 * key1=value1@key2=value2
	 * 
	 * @param paramValues
	 * @return
	 */
	public static Map<String, String> getCommandParamValueMap(String paramValues) {

		if(StringUtils.isNotBlank(paramValues)){
			String[] keyValues = paramValues.split("@");
			Map<String, String> map = new HashMap<String, String>();
			for (String p : keyValues) {

				String[] params = p.split("=");
				if(params.length == 1){
					map.put(params[0], null);
				}else{
					map.put(params[0], params[1]);
				}
				
			}
			return map;
		}
		return null;
	}

	/**
	 * short转换为字节
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
	
	
public static byte[] autoCRCCCIT(byte[] message){
		
		byte[] crc = calcCRCCCIT(message);
		byte[] dest = new byte[message.length+crc.length];
		
		System.arraycopy(message, 0, dest, 0, message.length);
		System.arraycopy(crc, 0, dest, message.length, crc.length);
		
		return dest;
	}
	public static byte[] calcCRCCCIT(byte[] data){
		short s=(short)calcCRC16(data);
		return short2Byte(s);
		
	}
	public static int calcCRC16(byte[] data){
	    int wCRC =  0xffff;
	    for (int k = 0; k <data.length; k++)
	    {
	        wCRC = (wCRC ^ (int)(0xFF & data[k]));
	        for (int i = 0; i < 8; i++)
	        {
	            if ((wCRC & 0x0001) > 0)
	                wCRC = (int)(wCRC >> 1 ^ 0xA001);
	            else
	                wCRC = (int)(wCRC >> 1);
	        }
	    }
	    wCRC = (int)((wCRC << 8) | ((wCRC >> 8) & 0xFF));
	    return wCRC;
	}
	
	/**
	 * 按字节分页
	 * @param str  要分页的 字符串
	 * @param len  每页的大小
	 * @return
	 * 例如：
	 *  "中国abc测a试test" 应返回
	 *  list0 = 中
	 *	list1 = 国ab
	 *  list2 = c测a
	 *	list3 = 试te
	 *	list4 = st
	 * @throws UnsupportedEncodingException 
	 */
	public static List<String> pageString(String str, int size) throws UnsupportedEncodingException {
	
		List<String> list = new ArrayList<String>();
		StringBuffer sb = new StringBuffer();
		int count = 0;
		
		for (int i = 0; i < str.length(); i++) {
			
			char c = str.charAt(i);
			int length = String.valueOf(c).getBytes("GBK").length;
			count += length;
			if (count > size) {
				list.add(sb.toString());
				count = length;
				sb = new StringBuffer();
			}
			sb.append(c);
		}
		list.add(sb.toString());
	
		return list;
	}

	public String ipToLongIp(String ipAdress){
		
		if(StringUtils.isEmpty(ipAdress)){
			return null;
		}
		StringBuffer sbf=new StringBuffer("");
		String[] strArray=ipAdress.split("\\.");
		for(int i=0;i<strArray.length;i++){
			int length=3-strArray[i].length();
			for(int j=0;j<length;j++){
				strArray[i]="0"+strArray[i];
			}
			if(i>0){
				sbf.append(".");
			}
			sbf.append(strArray[i]);
		}
		return sbf.toString();
	}

	/**
	 * 价格 datas[20]
	 * 
	 * @param datas
	 * @param num
	 * @return
	 */
	public String getPrice(byte[] datas) {
	
		StringBuffer bufStr = new StringBuffer();
		for (int i = 0; i < 5; i++) {
			if (i != 0) {
				bufStr.append(",");
			}
			bufStr.append(getIntNumber(Arrays.copyOfRange(datas, 4 * i, 4 * (i + 1)), 10000));
		}
		return bufStr.toString();
	}
	/**
	 * byte[4] INT+INT 数据解析
	 * 
	 * @param datas
	 * @param num
	 * @return
	 */
	public String getIntNumber(byte[] datas, long num) {
	
		return BigDecimal.valueOf(ByteLowUtil.byte2Short(Arrays.copyOfRange(datas, 0, 2)))
				.add(BigDecimal.valueOf(ByteLowUtil.byte2Short(Arrays.copyOfRange(datas, 2, 4))).divide(BigDecimal.valueOf(num))).toString();
	}
	public static Date parseStringToDate (String str, String format) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.parse(str);
		
	}

	public static String getUnsignedChar(byte bytes){
		return String.valueOf(bytes & 0xFF);
	}


}

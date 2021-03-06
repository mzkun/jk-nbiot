package com.goldcard.nbiot.web.home.tools;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @Description: TODO(字符串处理类)
 * @author
 * @date 2015年9月23日 下午5:28:00
 * @version V1.0
 */
public class StringUtil extends StringUtils {

    /**
     * 去除以逗号分隔的字符串的重复项
     *
     * @param str
     * @return
     */
    public static List<String> RemoveRepeatItem(String str) {
        if (!isNotBlank(str)) {
            return null;
        }
        List<String> list = new ArrayList<String>();
        String[] array = str.split(",");
        for (String s : array) {
            if (Collections.frequency(list, s) < 1) {
                list.add(s);
            }
        }
        return list;
    }

    /**
     * 数字不足位数左补0
     *
     * @param str
     * @param strLength
     */
    public static String addZeroForNum(String str, int strLength) {
        int strLen = str.length();
        if (strLen < strLength) {
            while (strLen < strLength) {
                StringBuffer sb = new StringBuffer();
                sb.append("0").append(str); // 左补0
                str = sb.toString();
                strLen = str.length();
            }
        }
        return str;
    }

    /**
     * 字符比较
     */
    public static boolean isEq(String str1, String str2) {
        if (isEmpty(str1) || isEmpty(str2)) {
            return false;
        }
        return str1.equals(str2);
    }

    /**
     * return null input string as emptry string
     *
     * @param s
     *            input String
     * @param def
     *            default String if input s is null
     * @return String
     */
    public static String null2Str(String s, String def) {
        if (isNull(s))
            return def;
        else
            return s.trim();
    }

    /**
     * check if the input string is null
     *
     * @param s
     *            String
     * @return boolean
     */
    public static boolean isNull(String s) {
        if (s == null || s.trim().equals(""))
            return true;
        else
            return false;
    }

    /**
     * Validate the input string which is digit or char.
     *
     * @param s,
     *            input string
     * @return true for digit, false for char
     * @version 1.0 shine new
     */
    public static boolean isDigit(String s) {
        if (s == null)
            return false;
        return Pattern.matches("^\\d+$", s);
    }

    /**
     * 判断字符串是否为数字(含小数)
     *
     * @param number
     * @return
     */
    public static boolean isNumberPoint(String number) {
        int index = number.indexOf(".");
        if (index < 0) {
            return StringUtils.isNumeric(number);
        } else {
            String num1 = number.substring(0, index);
            String num2 = number.substring(index + 1);

            return StringUtils.isNumeric(num1) && StringUtils.isNumeric(num2);
        }
    }

    /**
     * 去掉数字后面多余的零
     *
     * @param number
     */
    public static String RemoveLastZero(String number) {
        String num = StringUtil.stripEnd(number, "0");
        if (num == null) {
            return "";
        } else if (num.equals("0.")) {
            return "0";
        } else {
            return num;
        }
    }
}
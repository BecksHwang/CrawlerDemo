package com.becks.util;

/**
 * 转换字符串常用类
 * 
 * @author
 *
 */
public class ConvertStringUtil {
	// 转换sql语句
	public static String getSelectHqlByClass(Class clz) {
		return "from " + clz.getName() + " where 1 = 1 ";
	}
}

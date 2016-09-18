package com.becks.util;

import java.text.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 创建时间：
 * 
 * @Description DateUtil时间工具类
 * @author BecksHwang
 * @version
 */
public class DateUtil {
	public static Date parse(String input, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		try {
			return formatter.parse(input);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String dateToString(Date date, String type) {
		String str = null;
		DateFormat format = new SimpleDateFormat(type);
		str = format.format(date);
		return str;
	}

	public static Long getTodayTimeStamp() {
		Calendar todayStart = Calendar.getInstance();
		todayStart.set(Calendar.HOUR_OF_DAY, 23);
		todayStart.set(Calendar.MINUTE, 59);
		todayStart.set(Calendar.SECOND, 59);
		todayStart.set(Calendar.MILLISECOND, 0);
		return todayStart.getTime().getTime();
	}
	
	public static Long getTodayZeroTimeStamp() {
		Calendar todayStart = Calendar.getInstance();
		todayStart.set(Calendar.HOUR_OF_DAY, 00);
		todayStart.set(Calendar.MINUTE, 00);
		todayStart.set(Calendar.SECOND, 00);
		todayStart.set(Calendar.MILLISECOND, 0);
		return todayStart.getTime().getTime();
	}

	public static boolean isToday(Date date) {
		Long dateTime = date.getTime();
		return isToday(dateTime);
	}

	public static boolean isToday(Long now) {
		Long span = getTodayTimeStamp() - now;
		if (span >= 0 && span < 24 * 60 * 60 * 1000) {
			return true;
		} else {
			return false;
		}
	}

	public static Long intervalBetweenNow(Long timeStamp) {
		Long now = new Date().getTime();
		return timeStamp - now;
	}

	public static void main(String[] args) {
		Date date = new Date(getTodayTimeStamp());
		System.out.println(date);
		Calendar todayStart = Calendar.getInstance();
		todayStart.set(Calendar.HOUR_OF_DAY, 0);
		todayStart.set(Calendar.MINUTE, 0);
		todayStart.set(Calendar.SECOND, 0);
		todayStart.set(Calendar.MILLISECOND, 0);
	}
}

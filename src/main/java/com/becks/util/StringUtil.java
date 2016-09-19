package com.becks.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.ByteBuffer;

import java.util.zip.CRC32;

/**
 * 创建时间：
 * 
 * @Description StringUtil工具类
 * @author BecksHwang
 * @version
 */
public class StringUtil {
	public static boolean isNullOrEmpty(String input) {
		return (input == null) || (input.isEmpty());
	}

	public static String toCamelCase(String input) {
		if (input.contains("_")) {
			input = input.replace("_", "-");
		}
		if (input.contains("-")) {
			String[] words = input.split("-");
			input = "";
			for (String word : words) {
				input = input + word.substring(0, 1).toUpperCase() + word.substring(1);
			}
		} else {
			input = input.substring(0, 1).toUpperCase() + input.substring(1);
		}
		return input;
	}

	public static String decodeURL(String value) {
		if (value != null) {
			try {
				value = URLDecoder.decode(value, "utf-8");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return value;
	}

	public static String encodeURL(String value) {
		if (value != null) {
			try {
				value = URLEncoder.encode(value, "utf-8");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return value;
	}

	public static String trimPunctuation(String value) {
		if (value == null)
			return null;
		return value.replaceAll("\\pP", "").replaceAll("\\s*", "");
	}

	/**
	 * 判断是否为空白字符串 或为null
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isBlank(String value) {
		return value == null || value.trim().equals("");
	}

	/**
	 * crc32
	 * 
	 * @param bytes
	 * @return
	 */
	public static Long getCheckCode(byte[] bytes) {
		CRC32 crc32 = new CRC32();
		crc32.update(bytes);
		Long checkCode = Long.valueOf(crc32.getValue());
		return checkCode;
	}

	/**
	 * 过滤4个字节字符
	 * 
	 * @param text
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String filterOffUtf8Mb4(String text) throws UnsupportedEncodingException {
		byte[] bytes = text.getBytes("utf-8");
		ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
		int i = 0;
		while (i < bytes.length) {
			short b = bytes[i];
			if (b > 0) {
				buffer.put(bytes[i++]);
				continue;
			}
			b += 256;
			if ((b ^ 0xC0) >> 4 == 0) {
				buffer.put(bytes, i, 2);
				i += 2;
			} else if ((b ^ 0xE0) >> 4 == 0) {
				buffer.put(bytes, i, 3);
				i += 3;
			} else if ((b ^ 0xF0) >> 4 == 0) {
				i += 4;
			}
		}
		buffer.flip();
		return new String(buffer.array(), "utf-8");
	}

}

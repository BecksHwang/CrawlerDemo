package com.becks.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger;

import net.htmlparser.jericho.Source;

/**
 * 创建时间：
 * 
 * @Description GrapMethodUtil抓取方法工具类
 * @author BecksHwang
 * @version
 */
public class GrapMethodUtil {
	private Logger logger = Logger.getLogger(GrapMethodUtil.class);

	public static void main(String[] args) throws UnsupportedEncodingException, MalformedURLException {
		String url = "http://www.mod.gov.cn/shouye/2016-05/25/content_4664294.htm";
		String ursourceURL = "http://www.mod.gov.cn/shouye/node_47102.htm";
		URL sourceURL = new URL(ursourceURL);
		System.out.println(buildURL(sourceURL, url));
	}

	/**
	 * 根据method获取字符串
	 * 
	 * @param method
	 * @return
	 */
	public String methodGetString(GetMethod method) {
		StringBuffer stringBuffer = new StringBuffer("");
		InputStream inputStream = null;
		try {
			inputStream = method.getResponseBodyAsStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "gb2312"));
			String str = "";
			while ((str = br.readLine()) != null) {
				stringBuffer.append(str);
			}
		} catch (IOException e) {
			logger.error(GrapMethodUtil.class.getName() + "  methodGetString抓取失败:" + method.getPath() + "\r\n"
					+ e.getMessage());
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					logger.error(GrapMethodUtil.class.getName() + "  methodGetString流未关闭:" + method.getPath() + "\r\n"
							+ e.getMessage());
				}
			}
		}
		return stringBuffer.toString();
	}

	/**
	 * 根据method获取字符串
	 * 
	 * @param method
	 * @return
	 */
	public String urlGetString(String strURL, String encode) {
		encode = encode == null ? "gb2312" : encode;
		StringBuffer stringBuffer = new StringBuffer("");
		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader br = null;
		try {
			URL url = new URL(strURL);
			HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
			httpConn.setRequestProperty("User-Agent",
					"Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.1; WOW64; Trident/5.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; 4399Box.1334; 4399Box.1334)");
			httpConn.setConnectTimeout(10000);
			httpConn.setReadTimeout(15000);
			if (httpConn.getResponseCode() != httpConn.HTTP_OK)
				return stringBuffer.toString();
			inputStream = httpConn.getInputStream();
			inputStreamReader = new InputStreamReader(inputStream, encode);
			br = new BufferedReader(inputStreamReader);
			String str = "";
			while ((str = br.readLine()) != null) {
				stringBuffer.append(str);
			}
		} catch (IOException e) {
			logger.error(GrapMethodUtil.class.getName() + "  urlGetString抓取网页失败:" + strURL + "\r\n" + e.getMessage());
			try {
				Thread.sleep(100);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					logger.error(
							GrapMethodUtil.class.getName() + "  urlGetString流未关闭:" + strURL + "\r\n" + e.getMessage());
				}
			}
			if (inputStreamReader != null) {
				try {
					inputStreamReader.close();
				} catch (IOException e) {
					logger.error(
							GrapMethodUtil.class.getName() + "  urlGetString流未关闭:" + strURL + "\r\n" + e.getMessage());
				}
			}
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					logger.error(
							GrapMethodUtil.class.getName() + "  urlGetString流未关闭:" + strURL + "\r\n" + e.getMessage());
				}
			}
		}
		return stringBuffer.toString();
	}

	/**
	 * 根据strURL获取字符串
	 * 
	 * @param strURL
	 * @return
	 */
	public String getStringByUrl(String strURL) {
		StringBuffer stringBuffer = new StringBuffer("");
		InputStream inputStream = null;
		BufferedReader br = null;
		try {
			URL url = new URL(strURL);
			HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
			httpConn.setConnectTimeout(10000);
			httpConn.setReadTimeout(10000);
			if (httpConn.getResponseCode() != httpConn.HTTP_OK)
				return stringBuffer.toString();
			inputStream = httpConn.getInputStream();
			stringBuffer = new StringBuffer(new Source(inputStream).toString());
		} catch (IOException e) {
			logger.error(GrapMethodUtil.class.getName() + "  getStringByUrl抓取网页失败:" + strURL + "\r\n" + e.getMessage());
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					logger.error(GrapMethodUtil.class.getName() + "  getStringByUrl流未关闭:" + strURL + "\r\n"
							+ e.getMessage());
				}
			}
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					logger.error(GrapMethodUtil.class.getName() + "  getStringByUrl流未关闭:" + strURL + "\r\n"
							+ e.getMessage());
				}
			}
		}
		return stringBuffer.toString();
	}
	
	/**
	 * 根据strURL获取字符串-和讯新闻
	 * 
	 * @param strURL
	 * @return
	 */
	public String getStringByUrlHeXun(String strURL) {
		StringBuffer stringBuffer = new StringBuffer("");
		InputStream inputStream = null;
		BufferedReader br = null;
		try {
			URL url = new URL(strURL);
			HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
			httpConn.setConnectTimeout(60000);
			httpConn.setReadTimeout(60000);
			if (httpConn.getResponseCode() != httpConn.HTTP_OK)
				return stringBuffer.toString();
			inputStream = httpConn.getInputStream();
			stringBuffer = new StringBuffer(new Source(inputStream).toString());
		} catch (IOException e) {
			logger.error(GrapMethodUtil.class.getName() + "  getStringByUrl抓取网页失败:" + strURL + "\r\n" + e.getMessage());
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					logger.error(GrapMethodUtil.class.getName() + "  getStringByUrl流未关闭:" + strURL + "\r\n"
							+ e.getMessage());
				}
			}
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					logger.error(GrapMethodUtil.class.getName() + "  getStringByUrl流未关闭:" + strURL + "\r\n"
							+ e.getMessage());
				}
			}
		}
		return stringBuffer.toString();
	}

	/**
	 * 转换字符串
	 * 
	 * @param theString
	 * @return
	 */
	public static String htmlEncode(String theString) {
		theString = theString.replace(">", "&gt;");
		theString = theString.replace("<", "&lt;");
		theString = theString.replace(" ", " &nbsp;");
		theString = theString.replace(" ", " &nbsp;");
		theString = theString.replace("\"", "&quot;");
		theString = theString.replace("\'", "&#39;");
		theString = theString.replace("\n", "<br/> ");
		return theString;
	}

	public static String htmlDiscode(String theString) {
		theString = theString.replace("&gt;", ">");
		theString = theString.replace("&lt;", "<");
		theString = theString.replace("&nbsp;", " ");
		theString = theString.replace(" &nbsp;", " ");
		theString = theString.replace("&quot;", "\"");
		theString = theString.replace("&#39;", "\'");
		theString = theString.replace("<br/> ", "\n");
		return theString;
	}

	public static boolean validCheck(String title, String url) {
		if ((StringUtil.isNullOrEmpty(title)) || (StringUtil.isNullOrEmpty(url)))
			return false;
		if ((title.length() <= 4) || (title.length() > 200))
			return false;
		if (valiDateTimeWithLongFormat(title))
			return false;
		if (url.startsWith("#"))
			return false;
		if (url.toLowerCase().startsWith("javascript:"))
			return false;
		if (checkDateTimeOfHref(url))
			return false;
		title = "";
		return true;
	}

	// 发布日期比当前日期晚一天以上的返回ture ,其他返回false,返回ture不保存信息
	public static boolean checkDateTimeOfHref(String hrefStr) {
		String reg = "(201[0-9]|202[0-9])/?-?(((0[13578]|1[02])/?([01]\\d|2[1-9]|3[01]))|((0[469]|11)/?([01]\\d|2[1-9]|30))|(02/?([01]\\d|2[1-9])))";
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(hrefStr);
		if (matcher.find()) {
			String date = matcher.group();
			String newDate1 = date.replaceAll("\\-", "");
			String newDate2 = newDate1.replaceAll("\\/", "");
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			Calendar c = Calendar.getInstance();
			try {
				c.setTime(format.parse(newDate2));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			// 系统的时间
			Calendar sysdate = Calendar.getInstance();
			sysdate.set(Calendar.HOUR_OF_DAY, 0);
			sysdate.set(Calendar.MINUTE, 0);
			sysdate.set(Calendar.SECOND, 0);
			sysdate.set(Calendar.MILLISECOND, 0);
			long times = sysdate.getTimeInMillis() - c.getTimeInMillis();
			long days = times / (1000 * 3600 * 24);
			if (days <= 1) {
				return false;
			} else {
				return true;
			}
		} else {
			return false;
		}

	}

	// 检查Title是不是为日期格式，返回true,则过滤掉
	public static boolean valiDateTimeWithLongFormat(String timeStr) {
		String format = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
		Pattern pattern = Pattern.compile(format);
		Matcher matcher = pattern.matcher(timeStr);
		return matcher.matches();
	}

	public static String buildURL(URL sourceURL, String url) {
		String original = sourceURL.toString();
		if ((url.toLowerCase().startsWith("http://")) || (url.toLowerCase().startsWith("https://"))) {
			return url;
		}
		if (url.startsWith("/")) {
			return sourceURL.getProtocol() + "://" + sourceURL.getHost()
					+ (sourceURL.getPort() == -1 ? "" : new StringBuilder(":").append(sourceURL.getPort()).toString())
					+ url;
		}
		int sep = original.lastIndexOf("/");
		int dot = original.lastIndexOf(".");
		if ((dot != -1) && (dot > sep)) {
			url = original.substring(0, sep + 1) + url;
		} else {
			if (!sourceURL.getPath().endsWith("/")) {
				original = original + "/";
			}
			url = original + url;
		}
		return url;
	}

}

package com.becks.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.jsoup.Jsoup;

import net.sf.json.JSONObject;

/**
 * 创建时间：
 * 
 * @Description SendUrlUtil工具类
 * @author BecksHwang
 * @version
 */
public class SendUrlUtil {

	public static JSONObject returnJsonObjectGet(String url, HashMap<String, String> hm) {
		HttpClient client = new HttpClient();
		boolean isFirst = true;
		String encodeUrl = "";
		try {
			if (hm != null) {
				for (Entry<String, String> entry : hm.entrySet()) {
					String hmValue = entry.getValue();
					String hmKey = entry.getKey();
					if (hmValue != null && !hmValue.equals("")) {
						if (isFirst) {
							encodeUrl += "?" + hmKey + "=" + URLEncoder.encode(hmValue, "UTF-8");
							isFirst = false;
						} else {
							encodeUrl += "&" + hmKey + "=" + URLEncoder.encode(hmValue, "UTF-8");
						}
					}
				}
			}
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		url += encodeUrl;
		GetMethod method = new GetMethod(url);
		int statusCode = 0;
		JSONObject jsonObject = null;
		try {
			statusCode = client.executeMethod(method);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + method.getStatusLine());
			}
			byte[] responseBody = method.getResponseBody();
			jsonObject = JSONObject.fromObject(new String(responseBody, "utf-8"));
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}

	public static JSONObject returnJsonObjectPost(String url, HashMap<String, String> hm) {
		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(url);
		method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
		int arrCount = 0;
		for (Entry<String, String> entry : hm.entrySet()) {
			String hmValue = entry.getValue();
			if (hmValue != null && !hmValue.equals("")) {
				arrCount++;
			}
		}
		NameValuePair[] data = new NameValuePair[arrCount];
		// 把参数值放入postMethod中
		int count = 0;
		for (Entry<String, String> entry : hm.entrySet()) {
			String hmValue = entry.getValue();
			String hmKey = entry.getKey();
			if (hmValue != null && !hmValue.equals("")) {
				NameValuePair temp = new NameValuePair(hmKey, hmValue);
				data[count] = temp;
			}
			count++;
		}
		method.setRequestBody(data);
		int statusCode = 0;
		JSONObject jsonObject = null;
		try {
			statusCode = client.executeMethod(method);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + method.getStatusLine());
			}
			byte[] responseBody = method.getResponseBody();
			jsonObject = JSONObject.fromObject(new String(responseBody, "utf-8"));
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}

	public static String getHtml(String address, HashMap headParam) throws Exception {
		HttpClient httpClient = new HttpClient();
		/* 连接超时 */
		httpClient.getParams().setIntParameter("http.socket.timeout", 10000);
		GetMethod getMethod = new GetMethod(address);
		if (headParam != null) {
			Iterator iter = headParam.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				String key = (String) entry.getKey();
				String val = (String) entry.getValue();
				getMethod.setRequestHeader(key, val);
			}
		}
		getMethod.getParams().setParameter("http.protocol.cookie-policy", CookiePolicy.BROWSER_COMPATIBILITY);
		String html = null;
		try {
			int statusCode = httpClient.executeMethod(getMethod);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + getMethod.getStatusLine());
				return html;
			}
			InputStream resStream = getMethod.getResponseBodyAsStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(resStream, "UTF-8"));
			StringBuffer resBuffer = new StringBuffer();
			String resTemp = "";
			while ((resTemp = br.readLine()) != null) {
				resBuffer.append(resTemp);
			}
			html = resBuffer.toString();
		} catch (Exception e) {
			System.out.println(address);
			e.printStackTrace();
			return "";
		} finally {
			getMethod.releaseConnection();
		}
		return html;
	}

	public static String getHtml(String address) throws Exception {
		HttpClient httpClient = new HttpClient();
		/* 连接超时 */
		httpClient.getParams().setIntParameter("http.socket.timeout", 10000);
		GetMethod getMethod = new GetMethod(address);
		getMethod.getParams().setParameter("http.protocol.cookie-policy", CookiePolicy.BROWSER_COMPATIBILITY);
		String html = null;
		try {
			int statusCode = httpClient.executeMethod(getMethod);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + getMethod.getStatusLine());
				return html;
			}
			InputStream resStream = getMethod.getResponseBodyAsStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(resStream, "UTF-8"));
			StringBuffer resBuffer = new StringBuffer();
			String resTemp = "";
			while ((resTemp = br.readLine()) != null) {
				resBuffer.append(resTemp);
			}
			html = resBuffer.toString();
		} catch (Exception e) {
			System.out.println(address);
			e.printStackTrace();
			return "";
		} finally {
			getMethod.releaseConnection();
		}
		return html;
	}

	public static String getHtml(String address, String encode) throws Exception {
		HttpClient httpClient = new HttpClient();
		/* 连接超时 */
		httpClient.getParams().setIntParameter("http.socket.timeout", 10000);
		GetMethod getMethod = new GetMethod(address);
		getMethod.getParams().setParameter("http.protocol.cookie-policy", CookiePolicy.BROWSER_COMPATIBILITY);
		String html = null;
		try {
			int statusCode = httpClient.executeMethod(getMethod);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + getMethod.getStatusLine());
				return html;
			}
			InputStream resStream = getMethod.getResponseBodyAsStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(resStream, "GB2312"));
			StringBuffer resBuffer = new StringBuffer();
			String resTemp = "";
			while ((resTemp = br.readLine()) != null) {
				resBuffer.append(resTemp);
			}
			html = resBuffer.toString();
		} catch (Exception e) {
			System.out.println(address);
			e.printStackTrace();
			return "";
		} finally {
			getMethod.releaseConnection();
		}
		return html;
	}

	public static String getHtmlByJousp(String address) throws IOException {
		return Jsoup.connect(address).get().toString();
	}
	public static JSONObject returnJsonObjectForJRJGGSD(String url, HashMap<String, String> hm) {
		HttpClient client = new HttpClient();

		PostMethod method = new PostMethod(url);
		method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
		int arrCount = 0;
		for (Entry<String, String> entry : hm.entrySet()) {
			String hmValue = entry.getValue();
			if (hmValue != null && !hmValue.equals("")) {
				arrCount++;
			}
		}
		NameValuePair[] data = new NameValuePair[arrCount];
		// 把参数值放入postMethod中
		int count = 0;
		for (Entry<String, String> entry : hm.entrySet()) {
			String hmValue = entry.getValue();
			String hmKey = entry.getKey();
			if (hmValue != null && !hmValue.equals("")) {
				NameValuePair temp = new NameValuePair(hmKey, hmValue);
				data[count] = temp;
			}
			count++;
		}
		method.setRequestBody(data);
		int statusCode = 0;
		JSONObject jsonObject = null;
		try {
			statusCode = client.executeMethod(method);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + method.getStatusLine());
			}
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(method.getResponseBodyAsStream(), "utf-8"));
			StringBuffer stringBuffer = new StringBuffer();
			String str = "";
			while ((str = reader.readLine()) != null) {
				stringBuffer.append(str);
			}
			String str1 = stringBuffer.toString();
			String str2 = str1.substring(16, str1.length() - 1);
			jsonObject = JSONObject.fromObject(str2);
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}

	public static void main(String[] args) throws Exception {
		String url = "http://www.pbc.gov.cn/goutongjiaoliu/113456/113469/index.html";
		String html = null;
		html = getHtmlByJousp(url);
		System.out.println(html);
	}
}

package com.becks.util;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.becks.config.SinaConfig;
import com.becks.entity.News;

import net.sf.json.JSONObject;

/**
 * 数据推送新浪相关的工具类
 *
 * @author BecksHwang
 */
public class SinaUtil {
	static Logger logger = Logger.getLogger(SinaUtil.class);

	@Autowired
	private SinaConfig sinaConfig;

	/**
	 * 组装参数执行推送
	 * 
	 * @param hm
	 * @return
	 */
	@SuppressWarnings("static-access")
	public void pushSina(News news) {
		HashMap<String, String> hmDate = new HashMap<String, String>();
		hmDate.put("targetId", news.getTargetId().toString());
		hmDate.put("title", news.getTitle());
		hmDate.put("url", news.getUrl());
		hmDate.put("source", news.getSource());
		hmDate.put("sourceUrl", news.getSourceUrl());
		hmDate.put("pickTime", DateUtil.dateToString(news.getPickTime(), "yyyy-MM-dd HH:mm:ss"));
		hmDate.put("checkCode", news.getCheckCode().toString());
		hmDate.put("content", news.getContent());
		JSONObject date = new JSONObject().fromObject(hmDate);
		HashMap<String, String> hmPushDate = new HashMap<String, String>();
		hmPushDate.put("oper", "add");
		hmPushDate.put("obj", date.toString());
		hmPushDate.put("sign", "Fid");
		hmPushDate.put("time", DateUtil.getNowTimeStamp().toString().substring(0, 10));
		JSONObject jo = null;
		jo = push(hmPushDate);
		String code = jo.getString("code");
		String msg = jo.getString("msg");
		if (!("0".equals(code))) {
			logger.error("信息推送失败！返回提示信息：" + msg);
			logger.error("推送失败的信息为：" + news.toString());
		} else {
			logger.error("信息推送成功！信息ID：" + news.getId() + "————来源：" + news.getSource());
		}
	}

	/**
	 * 发送post请求，进行推送
	 * 
	 * @param hm
	 * @return
	 */
	public JSONObject push(HashMap<String, String> hm) {
		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(sinaConfig.getSinaUrl());
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
		// method.setParams(params);
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

	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		HashMap<String, String> hmDate = new HashMap<String, String>();
		hmDate.put("targetId", "1");
		hmDate.put("title", "测试线上推送3");
		hmDate.put("url", "www.baidu.com");
		hmDate.put("source", "来源名称");
		hmDate.put("sourceUrl", "www.baidu.com");
		hmDate.put("pickTime", DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
		hmDate.put("checkCode", "4027243938");
		hmDate.put("stockName", "百度");
		hmDate.put("stockCode", "CYOU");
		JSONObject date = new JSONObject().fromObject(hmDate);
		HashMap<String, String> hmPushDate = new HashMap<String, String>();
		hmPushDate.put("oper", "add");
		hmPushDate.put("obj", date.toString());
		hmPushDate.put("sign", "Fid");
		hmPushDate.put("time", DateUtil.getNowTimeStamp().toString().substring(0, 10));
		SinaUtil sinaUtil = new SinaUtil();
		JSONObject jo = sinaUtil.push(hmPushDate);
		String code = jo.getString("code");
		String msg = jo.getString("msg");
		if (!("0".equals(code))) {
			logger.error("信息推送失败！返回提示信息：" + msg);
			logger.error("推送失败的信息为：");
		} else {
			logger.error("信息推送成功！信息ID：");
		}
	}

}

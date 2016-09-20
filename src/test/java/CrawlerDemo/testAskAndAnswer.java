package CrawlerDemo;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.becks.service.ThsggsdUrlGrapService;
import com.becks.util.SendUrlUtil;
import com.becks.util.StringUtil;

public class testAskAndAnswer {
	static Logger logger = Logger.getLogger(ThsggsdUrlGrapService.class);

	public static void main(String[] args) throws MalformedURLException {

		new Thread(new Runnable() {
			public void run() {
				try {
					specialParseTarget("http://irm.cninfo.com.cn/szse/index.html");
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();

	}

	protected static void specialParseTarget(String url) throws Exception {

		while (true) {
			try {
				Thread.sleep(3000L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			
			//String urlstr = url + new Date().getTime();
			String urlstr = url;
			String html = null;
			html = new com.becks.util.GrapMethodUtil().getStringByUrl(urlstr);
			// html = new SpecialGrapMethodUtil().getStringByUrlChrome(urlstr);
			HashMap hm = new HashMap<>();
			//hm.put("Referer", "http://www.yicai.com/brief/");
			//html = new SendUrlUtil().getHtml(url);
			System.out.println("*******************抓取中*******************");
			//System.out.println(html);
			if (com.becks.util.StringUtil.isNullOrEmpty(html)) {
				System.out.println("没有抓取到内容！");
				continue;
			}
			
			String content = html;
			String startTag = "<div id="+ "\"" + "con_one_3" + "\"" + " style=" + "\"" +"display:none" + "\"" +">";//<div id="con_one_3" style="display:none">
			String endTag = "<div id="+ "\"" + "con_one_2" + "\"" + " style=" + "\"" +"display:none;" + "\"" +">";
			int begin;
			if ((StringUtil.isNullOrEmpty(startTag)) || (content.indexOf(startTag) == -1))
				begin = 0;
			else
				begin = content.indexOf(startTag);
			int end;
			if ((StringUtil.isNullOrEmpty(endTag)) || (content.indexOf(endTag) == -1))
				end = content.length();
			else {
				end = content.indexOf(endTag);
			}
			content = content.substring(begin, end);
			
			
			Document document = Jsoup.parse(content);
			System.out.println(document);
			List<Element> elementList1 = document.getElementsByTag("ul");
			Element element1 = elementList1.get(0);
			List<Element> elementList = element1.getElementsByTag("li");
			for (int e = 0; e < elementList.size(); e++) {
				Element element = (Element) elementList.get(e);
				Elements msgBoxElements = element.getElementsByClass("msgBox");
				Elements answerBoxElements = element.getElementsByClass("answerBox");
				Elements askElements = msgBoxElements.get(0).getElementsByTag("a");
				Elements answerElements = answerBoxElements.get(0).getElementsByTag("a");
				Element askElement = (Element) askElements.get(2);
				Element answerElement = (Element) answerElements.get(2);
				Element companyElement = (Element) askElements.get(1);
				String ask = askElement.text();
				String answer = answerElement.text();
				String company = companyElement.text();
				System.out.println("ask:"+ask+"</br>"+"answer:"+answer+"</br>"+"company:"+company+"</br>");
				
			}

		}
	}

}

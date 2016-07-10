package ggzzjc;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.becks.service.PdfUrlGrapService;

public class testInt {
	static Logger logger = Logger.getLogger(PdfUrlGrapService.class);

	public static void main(String[] args) throws MalformedURLException {

		new Thread(new Runnable() {
			public void run() {
				try {
					specialParseTarget("http://ircs.p5w.net/ircs/interaction/moreQuestionForGszz.do");
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}
		}).start();

	}

	protected static void specialParseTarget(String url) throws MalformedURLException {

		while (true) {
			try {
				Thread.sleep(3000L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			String urlstr = url;
			String html = null;
			html = new com.becks.util.GrapMethodUtil().getStringByUrl(urlstr);
			// html = new SpecialGrapMethodUtil().getStringByUrlChrome(urlstr);
			System.out.println("*******************抓取中*******************");
			// System.out.println(html);
			if (com.becks.util.StringUtil.isNullOrEmpty(html)) {
				System.out.println("没有抓取到内容！");
				continue;
			}
			Document document = Jsoup.parse(html);
			List<Element> elementList = document.getElementsByClass("req_box2");
			for (int e = 0; e < elementList.size(); e++) {
				Element element = (Element) elementList.get(e);
				Elements askElements = element.getElementsByClass("hd_td1");
				Elements answerElements = element.getElementsByClass("hd_td3");
				Elements companyElements = element.getElementsByAttributeValue("width", "110");
				
				Element askElement = (Element) askElements.get(0);
				Element answerElement = (Element) answerElements.get(0);
				Element companyElement = (Element) companyElements.get(0);
				String ask = askElement.text();
				String answer = answerElement.text();
				String company = companyElement.text();
				System.out.println("company=" + company);
				System.out.println("ask=" + ask);
				System.out.println("answer=" + answer);
			}

		}
	}

}

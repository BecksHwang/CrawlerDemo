package ggzzjc;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.becks.service.ThsggsdUrlGrapService;
import com.becks.util.SendUrlUtil;

public class testInt {
	static Logger logger = Logger.getLogger(ThsggsdUrlGrapService.class);

	public static void main(String[] args) throws MalformedURLException {

		new Thread(new Runnable() {
			public void run() {
				try {
					specialParseTarget("http://www.lianhuacaijing.com/kuaibao/");
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
			
			//String urlstr = url + formatter.format(new Date());
			String html = null;
			html = new com.becks.util.GrapMethodUtil().getStringByUrl(url);
			// html = new SpecialGrapMethodUtil().getStringByUrlChrome(urlstr);
			HashMap hm = new HashMap<>();
			//hm.put("Referer", "http://www.yicai.com/brief/");
			//html = new SendUrlUtil().getHtml(url, hm);
			System.out.println("*******************抓取中*******************");
			//System.out.println(html);
			if (com.becks.util.StringUtil.isNullOrEmpty(html)) {
				System.out.println("没有抓取到内容！");
				continue;
			}
			Document document = Jsoup.parse(html);
			System.out.println(document);
			List<Element> elementList = document.getElementsByClass("content");
			for (int e = 0; e < elementList.size(); e++) {
				Element element = (Element) elementList.get(e);
				//Elements pElements = element.getElementsByTag("p");
				
				//Element pElement = (Element) pElements.get(0);
				String title = element.text();
				System.out.println("title=" + title);
				
			}

		}
	}

}

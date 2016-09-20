package CrawlerDemo;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.becks.service.ThsggsdUrlGrapService;
import com.becks.util.GrapMethodUtil;
import com.becks.util.StringUtil;


public class testJs {
	static Logger logger = Logger.getLogger(ThsggsdUrlGrapService.class);

	public static void main(String[] args) throws MalformedURLException {

		new Thread(new Runnable() {
			public void run() {
				try {
					specialParseTarget("http://stock.hexun.com/gsxw/");
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
			//html = new SpecialGrapMethodUtil().getStringByUrlChrome(urlstr);
			System.out.println("*******************抓取中*******************");
			// System.out.println(html);
			if (com.becks.util.StringUtil.isNullOrEmpty(html)) {
				System.out.println("没有抓取到内容！");
				continue;
			}
			String content = html;
			
			String startTag = "<div class="+ "\"" + "temp01" + "\"" + ">";//"<div class="+ "\"" + "temp01" + "\"" + ">"
			String endTag = "<div class=" + "\"" + "listdh"+ "\"" + ">";

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
			
			
			
			List<Element> elementList = document.getElementsByTag("a");
			for (int e = 0; e < elementList.size(); e++) {
				Element element = (Element) elementList.get(e);
				String href = element.attr("href");
				String title = element.text();
				if (GrapMethodUtil.validCheck(title, href)) {
					href = com.becks.util.GrapMethodUtil.buildURL(new URL(urlstr), href);
					System.out.println("title=" + title);
					System.out.println("URL=" + href);
				}
			}

		}
	}

}

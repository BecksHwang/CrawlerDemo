package com.becks.service;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.becks.common.CommonParameter;
import com.becks.entity.News;
import com.becks.entity.Target;
import com.becks.util.RedisAPI;
import com.becks.util.SendUrlUtil;
import com.becks.util.StringUtil;

/**
 * @Description: 第一财经快讯任务抓取程序
 * @author BecksHwang
 * @date
 */
@Component
public class DycjkxUrlGrapService {
	static Logger logger = Logger.getLogger(DycjkxUrlGrapService.class);
	@Autowired
	private TargetService targetService;
	@Autowired
	private NewsService newsService;
	static List<Target> targetList = new ArrayList<>();
	static BlockingQueue<Target> targetQueue = new ArrayBlockingQueue<>(3);

	public boolean missionCheckCode(String title, String url, Long targetId) {
		String unique = title + "-" + url + "-" + targetId;
		if (RedisAPI.get(CommonParameter.MISSION_CHECKCODE, unique) || newsService.isExits(targetId, title, url)) {
			return true;
		} else {
			return false;
		}
	}

	public void grap() {
		targetList = targetService.findAll();
		for (Target target : targetList) {
			if (target.getMissionId() == CommonParameter.DYCJKX_URL) {
				targetQueue.offer(target);
			}
		}
		for (int i = 0; i < 1; i++) {
			logger.error("抓第一财经快讯网址，启动第" + i + "个线程！");
			new Thread(this.new GrapThread()).start();
		}
	}

	public class GrapThread extends Thread {

		@SuppressWarnings({ "rawtypes", "unchecked" })
		@Override
		public void run() {
			while (!targetQueue.isEmpty()) {
				ReferenceQueue refq = new ReferenceQueue();
				WeakReference wrf = new WeakReference(getTarget(), refq);
				try {
					this.performTarget((Target) wrf.get());
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					targetQueue.offer((Target) wrf.get());
				}
				try {
					Thread.sleep(2000l);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		protected void performTarget(Target target) {
			logger.error(
					"抓取网址：" + "targetId:" + target.getId() + "-名称：" + target.getName() + "-URL:" + target.getUrl());
			if (target == null)
				return;
			try {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				String urlstr = target.getUrl() + formatter.format(new Date());// 拼接时间组成抓取网址
				if (StringUtil.isNullOrEmpty(urlstr)) {
					return;
				}
				String html = null;
				HashMap hm = new HashMap<>();
				hm.put("Referer", "http://www.yicai.com/brief/");// 添加请求来源，防止非法请求得不到返回HTML
				html = new SendUrlUtil().getHtml(urlstr, hm);
				if (StringUtil.isNullOrEmpty(html)) {
					logger.error("抓取内容为空，名称：" + target.getName() + "-URL:" + target.getUrl());
					return;
				}
				Document document = Jsoup.parse(html);
				String content = html;
				// 校验该网页是否有更新

				String code = DigestUtils.md5Hex(content);
				if (code.equals(target.getMd5())) {
					return;
				} else {
					target.setMd5(code);
					try {
						targetService.Update(target);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				int begin;
				if ((StringUtil.isNullOrEmpty(target.getStartTag())) || (content.indexOf(target.getStartTag()) == -1))
					begin = 0;
				else
					begin = content.indexOf(target.getStartTag());
				int end;
				if ((StringUtil.isNullOrEmpty(target.getEndTag())) || (content.indexOf(target.getEndTag()) == -1))
					end = content.length();
				else {
					end = content.indexOf(target.getEndTag());
				}
				content = content.substring(begin, end);

				Set<Long> checkCodeSet = new HashSet<>();
				List<Element> elementList = document.getElementsByClass("txt");
				for (int e = 0; e < elementList.size(); e++) {
					Element element = (Element) elementList.get(e);
					Elements pElements = element.getElementsByTag("p");
					Element pElement = (Element) pElements.get(0);
					String title = pElement.text();
					String href = "http://www.yicai.com/brief/";
					if (true) {
						String keys = "";
						String pureTitle = StringUtil.trimPunctuation(title);
						Long checkCode = StringUtil.getCheckCode(title.getBytes());
						// 标识唯一
						if (!checkCodeSet.contains(checkCode) && !missionCheckCode(title, href, target.getId())) {
							News news = new News();
							news.setTitle(title);
							news.setUrl(href);
							news.setPureTitle(pureTitle);
							news.setPickTime(new Timestamp(System.currentTimeMillis()));
							news.setStatus("normal");
							news.setKeywords(keys);
							news.setMonitorType("page");
							news.setSource(target.getName());
							news.setSourceUrl(href);
							news.setTargetId(target.getId());
							news.setCheckCode(checkCode);
							newsService.save(news);
							checkCodeSet.add(checkCode);
							logger.error("保存消息:" + news.getTitle() + "-网址：" + href);
							logger.error("来源：targetId:" + target.getId() + "-名称：" + target.getName() + "-URL:"
									+ target.getUrl());
							String unique = title + "-" + href + "-" + target.getId();
							RedisAPI.set(CommonParameter.MISSION_CHECKCODE, unique);
							System.gc();
						}
					}
				}
				logger.error("抓取网址完毕：" + "-targetId:" + target.getId() + "-名称：" + target.getName() + "-URL:"
						+ target.getUrl());
			} catch (Exception e) {
				logger.error("抓取失败的网址:" + target.getUrl());
				e.printStackTrace();
			}

		}

	}

	public static synchronized Target getTarget() {
		Target target = targetQueue.poll();
		if (target == null)
			return null;
		return target;
	}

	public static void main(String[] args) {
	}

}
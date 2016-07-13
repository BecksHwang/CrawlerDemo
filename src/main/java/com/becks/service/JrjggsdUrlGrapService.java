package com.becks.service;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

import com.becks.entity.News;
import com.becks.entity.Target;
import com.becks.util.GrapMethodUtil;
import com.becks.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.becks.common.CommonParameter;
import com.becks.util.RedisAPI;
import com.becks.util.SendUrlUtil;

/**
 * @Description: 金融界公告速递网址任务抓取程序
 * @author BecksHwang
 * @date
 */
@Component
public class JrjggsdUrlGrapService {
	static Logger logger = Logger.getLogger(JrjggsdUrlGrapService.class);
	@Autowired
	private TargetService targetService;
	@Autowired
	private NewsService newsService;
	static List<Target> targetList = new ArrayList<>();
	static BlockingQueue<Target> targetQueue = new ArrayBlockingQueue<>(3);

	public JrjggsdUrlGrapService() {

	}

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
			if (target.getMissionId() == CommonParameter.JRJGGSD_URL) {
				targetQueue.offer(target);
			}
		}
		for (int i = 0; i < 1; i++) {
			logger.error("抓-金融界公告速递-网址，启动第" + i + "个线程！");
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
				String urlstr = target.getUrl();
				if (StringUtil.isNullOrEmpty(urlstr)) {
					return;
				}
				HashMap hm = new HashMap();
				hm.put("vname", "_notic_list");
				hm.put("type", "8");
				hm.put("page", "1");
				hm.put("psize", "20");
				long date = (long) (System.currentTimeMillis() / 1000);
				String dc = String.valueOf(date);
				hm.put("_dc", dc);
				JSONObject jo = SendUrlUtil.returnJsonObjectForJRJGGSD(urlstr, hm);
				// 校验该网页是否有更新
				String code = DigestUtils.md5Hex(jo.toString());
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
				JSONArray ja = jo.getJSONArray("data");
				for (int i = 0; i < ja.size(); i++) {
					String stockcode = ja.getJSONArray(i).getString(3);
					String href = "http://stock.jrj.com.cn/share," + stockcode + ",ggcontent.shtml?discId="
							+ ja.getJSONArray(i).getString(0);
					String title = ja.getJSONArray(i).getString(2);

					Set<Long> checkCodeSet = new HashSet<>();
					Long checkCode = StringUtil.getCheckCode((title + href).getBytes());
					if (!checkCodeSet.contains(checkCode) && !missionCheckCode(title, href, target.getId())) {
						News news = new News();
						news.setTitle(title);
						news.setUrl(href);
						news.setPickTime(new Timestamp(System.currentTimeMillis()));
						news.setSource(target.getName());
						news.setSourceUrl(target.getUrl());
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

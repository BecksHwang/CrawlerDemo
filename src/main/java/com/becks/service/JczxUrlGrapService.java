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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

import com.becks.entity.News;
import com.becks.entity.StockLabel;
import com.becks.entity.Target;
import com.becks.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.becks.common.CommonParameter;
import com.becks.util.RedisAPI;
import com.becks.util.SendUrlUtil;

/**
 * @Description: 巨潮资讯网任务抓取程序
 * @author BecksHwang
 * @date
 */
@Component
public class JczxUrlGrapService {
	static Logger logger = Logger.getLogger(JczxUrlGrapService.class);
	@Autowired
	private TargetService targetService;
	@Autowired
	private NewsService newsService;
	@Autowired
	private StockLabelService stockLabelService;
	static List<Target> targetList = new ArrayList<>();
	static List<StockLabel> stockLableList = new ArrayList<>();
	static BlockingQueue<Target> targetQueue = new ArrayBlockingQueue<>(3);

	public JczxUrlGrapService() {

	}

	public boolean missionCheckCode(String title, String url, Long targetId) {
		String unique = title + "-" + url + "-" + targetId;
		if (RedisAPI.get(CommonParameter.MISSION_CHECKCODE, unique) || newsService.isExits(targetId, title, url)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean jczxCheck(String title) {
		boolean checkSight = false;
		// 筛选匹配标签的消息
		for (StockLabel stockLabel : stockLableList) {
			if (title.contains(stockLabel.getLableName())) {
				checkSight = true;
				break;
			}
		}
		return checkSight;
	}

	public void grap() {
		targetList = targetService.findAll();
		stockLableList = stockLabelService.findAll();
		for (Target target : targetList) {
			if (target.getMissionId() == CommonParameter.JCZX_URL) {
				targetQueue.offer(target);
			}
		}
		for (int i = 0; i < 1; i++) {
			logger.error("抓-巨潮资讯网-网址，启动第" + i + "个线程！");
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

		@SuppressWarnings({ "unchecked", "rawtypes" })
		protected void performTarget(Target target) {
			logger.error(
					"抓取网址：" + "targetId:" + target.getId() + "-名称：" + target.getName() + "-URL:" + target.getUrl());
			try {
				String urlstr = target.getUrl();
				if (StringUtil.isNullOrEmpty(urlstr)) {
					return;
				}
				HashMap hm = new HashMap();
				hm.put("column", "szse");
				hm.put("columnTitle", "深市公告");
				hm.put("pageNum", "1");
				hm.put("pageSize", "30");
				hm.put("tabName", "latest");
				hm.put("tabName", "请选择日期");
				JSONObject jo = SendUrlUtil.returnJsonObjectPost(urlstr, hm);
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
				JSONArray ja = jo.getJSONArray("classifiedAnnouncements");
				for (int i = 0; i < ja.size(); i++) {
					JSONArray ja2 = ja.getJSONArray(i);
					for (int j = 0; j < ja2.size(); j++) {
						JSONObject jo2 = ja2.getJSONObject(j);
						String secCode = jo2.getString("secCode");
						String secName = jo2.getString("secName");
						String title = secCode + "-" + secName + "-" + jo2.getString("announcementTitle");
						String announcementId = jo2.getString("announcementId");
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
						String date = formatter.format(new Date()).toString();
						String href = "http://www.cninfo.com.cn/cninfo-new/disclosure/szse/bulletin_detail/true/"
								+ announcementId + "?announceTime=" + date;
						Set<Long> checkCodeSet = new HashSet<>();

						if (jczxCheck(title)) {
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

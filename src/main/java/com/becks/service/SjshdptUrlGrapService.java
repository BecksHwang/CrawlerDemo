package com.becks.service;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
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
import com.becks.entity.Interaction;
import com.becks.entity.Target;
import com.becks.util.RedisAPI;
import com.becks.util.SendUrlUtil;
import com.becks.util.StringUtil;
/**
 * @Description: 上交所互动平台任务抓取程序
 * @author BecksHwang
 * @date
 */
@Component
public class SjshdptUrlGrapService {
	static Logger logger = Logger.getLogger(SjshdptUrlGrapService.class);
	@Autowired
	private TargetService targetService;
	@Autowired
	private InteractionService interactionService;
	static List<Target> targetList = new ArrayList<>();
	static BlockingQueue<Target> targetQueue = new ArrayBlockingQueue<>(3);

	public boolean missionCheckCode(String ask, String answer) {
		String unique = ask + "-" + answer ;
		if (RedisAPI.get(CommonParameter.MISSION_CHECKCODE_ITRCT, unique) || interactionService.isExits(ask, answer)) {
			return true;
		} else {
			return false;
		}
	}

	

	public void grap() {
		targetList = targetService.findAll();
		for (Target target : targetList) {
			if (target.getMissionId() == CommonParameter.SJSHDPT_URL) {
				targetQueue.offer(target);
			}
		}
		for (int i = 0; i < 1; i++) {
			logger.error("抓上交所互动平台网址，启动第" + i + "个线程！");
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

		@SuppressWarnings("static-access")
		protected void performTarget(Target target) {
			logger.error(
					"抓取网址：" + "targetId:" + target.getId() + "-名称：" + target.getName() + "-URL:" + target.getUrl());
			try {
				String urlstr = target.getUrl() + new Date().getTime();
				if (StringUtil.isNullOrEmpty(urlstr)) {
					return;
				}
				String html = null;
				html = new SendUrlUtil().getHtml(urlstr);
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
				List<Element> elementList = document.getElementsByClass("m_feed_item");
				for (int e = 0; e < elementList.size(); e++) {
					Element element = (Element) elementList.get(e);
					Elements allElements = element.getElementsByClass("m_feed_txt");
					Element askElement = (Element) allElements.get(0);
					Element answerElement = (Element) allElements.get(1);
					Elements companyElements = askElement.getElementsByTag("a");
					Element companyElement = (Element) companyElements.get(0);
					String ask = askElement.text();
					String answer = answerElement.text();
					String companystr = companyElement.text();
					String company = companystr.substring(1, companystr.length());
					if (true) {
						Long checkCode = StringUtil.getCheckCode((ask + answer).getBytes());
						// 标识唯一
						if (!checkCodeSet.contains(checkCode) && !missionCheckCode(ask, answer)) {
							Interaction interaction = new Interaction();
							interaction.setAsk(ask);
							interaction.setAnswer(answer);
							interaction.setPickTime(new Timestamp(System.currentTimeMillis()));
							interaction.setSource(target.getName());
							interaction.setSourceUrl("http://sns.sseinfo.com");
							interaction.setTargetId(target.getId());
							interaction.setCheckCode(checkCode);
							interaction.setCompany(company);
							interactionService.save(interaction);
							checkCodeSet.add(checkCode);
							logger.error("保存问答-问:" + ask + "-答：" + answer);
							logger.error("来源：targetId:" + target.getId() + "-名称：" + target.getName() + "-URL:"
									+ target.getUrl());
							String unique = ask + "-" + answer;
							RedisAPI.set(CommonParameter.MISSION_CHECKCODE_ITRCT, unique);
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

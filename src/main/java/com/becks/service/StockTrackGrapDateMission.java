package com.becks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @Description: 个股追踪监测抓取总任务
 * @author BecksHwang
 * @date
 */
@SuppressWarnings("rawtypes")
@Component
public class StockTrackGrapDateMission implements ApplicationListener {
	private static boolean isStart = true;

	@Autowired
	private CommonUrlGrapService commonUrlGrapService;

	@Autowired
	private ThsggsdUrlGrapService thsggsdUrlGrapService;

	@Autowired
	private InteractionUrlGrapService interactionUrlGrapService;

	@Autowired
	private JrjggsdUrlGrapService jrjggsdUrlGrapService;

	@Autowired
	private HeXunStockUrlGrapService heXunStockUrlGrapService;
	
	@Autowired
	private JczxUrlGrapService JczxUrlGrapService;

	public void onApplicationEvent(ApplicationEvent arg0) {
		if (isStart) {
			// 确保抓取任务只启动一次
			isStart = false;
			// 启动common网址抓取任务
			commonUrlGrapService.grap();
			// 启动同花顺公告速递网址抓取任务
			thsggsdUrlGrapService.grap();
			// 启动全景网互动精华网址抓取任务
			interactionUrlGrapService.grap();
			// 启动金融公告速度网址抓取任务
			jrjggsdUrlGrapService.grap();
			// 启动和讯公司网址抓取任务
			heXunStockUrlGrapService.grap();
			// 启动巨潮资讯网址抓取任务
			JczxUrlGrapService.grap();
		}
	}

	public static void main(String[] args) {

	}

}

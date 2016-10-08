package com.becks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @Description: 抓取总任务
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
	private InteractionUrlGrapService interactionUrlGrapService;

	public void onApplicationEvent(ApplicationEvent arg0) {
		if (isStart) {
			// 确保抓取任务只启动一次
			isStart = false;
			// 启动common网址抓取任务
			commonUrlGrapService.grap();
			// 启动全景网互动精华网址抓取任务
			interactionUrlGrapService.grap();
		}
	}

	public static void main(String[] args) {

	}

}

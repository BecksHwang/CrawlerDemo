package com.becks.controller;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.becks.dao.impl.NewsDaoImpl;
import com.becks.entity.News;

/**
 * 创建时间：
 * @Description NewsController
 * @author	BecksHwang
 * @version 
 */
@Controller
@RequestMapping("/news")
public class NewsController {
	static Logger logger = Logger.getLogger(NewsController.class);

	@Autowired
	private NewsDaoImpl newsDao;

	@RequestMapping("/getNews")
	public @ResponseBody List<News> getNews(News newsq) {
		logger.error("查询数据显示到页面！");
		News news = newsq;
		List<News> result = null;
		if (news.getPickTime() == null) {
			news.setPickTime(new Date());
			try {
				result = newsDao.getOldNewsByGrabTime(news);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				result = newsDao.getNewNewsByGrabTime(news);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	@RequestMapping("/test")
	public void test() {
		System.out.println("test");
	}

	public static void main(String[] args) throws Exception {
	}

}

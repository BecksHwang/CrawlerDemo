package com.becks.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.becks.dao.impl.NewsDaoImpl;
import com.becks.entity.News;
import com.becks.util.Page;
import com.becks.vo.NewsQueryVo;

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
		//logger.error("查询数据显示到页面！");
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
	
	@RequestMapping(value = "/queryNews", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> page(@ModelAttribute Page page, @ModelAttribute NewsQueryVo qVo) {
		Map<String, Object> result = new HashMap<String, Object>();
		Page pageResult = new Page();
		try {
			pageResult = newsDao.queryPage(page, qVo);
		} catch (Exception e) {
			logger.error("Page net error", e);
			return result;
		}
		result.put("total", pageResult.getTotalRow());
		result.put("rows", pageResult.getItems());
		return result;
	}
	
	@RequestMapping("/newsList")
	public String newsList() {
		return "newsList";
	}

	@RequestMapping("/test")
	public void test() {
		System.out.println("test");
	}

	public static void main(String[] args) throws Exception {
	}

}

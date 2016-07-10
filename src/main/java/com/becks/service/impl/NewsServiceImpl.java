package com.becks.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.becks.dao.impl.NewsDaoImpl;
import com.becks.entity.News;
import com.becks.service.NewsService;

/**
 * 创建时间：
 * 
 * @Description NewsServiceImpl接口
 * @author BecksHwang
 * @version
 */
@Service("newsService")
public class NewsServiceImpl implements NewsService {

	@Autowired
	private NewsDaoImpl newsDao;

	@Override
	public boolean isExits(Long targetId, String title, String url) {
		return newsDao.isExits(targetId, title, url);
	}

	@Override
	public List<News> findAll() {
		return newsDao.findAll();
	}

	@Override
	public void save(News entity) throws Exception {
		newsDao.save(entity);
	}

	@Override
	public void Update(News entity) throws Exception {
		newsDao.update(entity);
	}

	@Override
	public void delete(News entity) throws Exception {
		newsDao.delete(entity);
	}

}

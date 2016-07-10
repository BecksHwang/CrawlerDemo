package com.becks.service;

import java.util.List;

import com.becks.entity.News;

/**
 * 创建时间：
 * 
 * @author	BecksHwang
 * @version NewsService接口
 */
public interface NewsService {

	List<News> findAll();

	void save(News entity) throws Exception;

	void Update(News entity) throws Exception;

	void delete(News entity) throws Exception;

	boolean isExits(Long targetId, String title, String url);
}

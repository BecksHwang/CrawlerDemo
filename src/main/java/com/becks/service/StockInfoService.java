package com.becks.service;

import java.util.List;

import com.becks.entity.StockInfo;

/**
 * 创建时间：
 * 
 * @author BecksHwang
 * @version StockInfoService接口
 */
public interface StockInfoService {

	StockInfo get(Long id) throws Exception;

	List<StockInfo> findAll();

	void save(StockInfo entity) throws Exception;

	void Update(StockInfo entity) throws Exception;

	void delete(StockInfo entity) throws Exception;
}

package com.becks.service;

import java.util.List;

import com.becks.entity.StockLabel;

/**
 * 创建时间：
 * 
 * @author BecksHwang
 * @version StockLabelService接口
 */
public interface StockLabelService {

	StockLabel get(Long id) throws Exception;

	List<StockLabel> findAll();

	void save(StockLabel entity) throws Exception;

	void Update(StockLabel entity) throws Exception;

	void delete(StockLabel entity) throws Exception;
}

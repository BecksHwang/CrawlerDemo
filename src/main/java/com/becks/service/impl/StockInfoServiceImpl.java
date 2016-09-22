package com.becks.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.becks.dao.impl.StockInfoDaoImpl;
import com.becks.entity.StockInfo;
import com.becks.service.StockInfoService;

/**
 * 创建时间：
 * 
 * @Description StockInfoServiceImpl接口
 * @author BecksHwang
 * @version
 */
@Service("stockInfoService")
public class StockInfoServiceImpl implements StockInfoService {

	@Autowired
	private StockInfoDaoImpl stockInfoDao;

	public StockInfo get(Long id) throws Exception {
		return stockInfoDao.get(StockInfo.class, id);
	}

	public List<StockInfo> findAll() {
		return stockInfoDao.findAll();
	}

	public void save(StockInfo entity) throws Exception {
		stockInfoDao.save(entity);
	}

	public void Update(StockInfo entity) throws Exception {
		stockInfoDao.update(entity);
	}

	public void delete(StockInfo entity) throws Exception {
		stockInfoDao.delete(entity);
	}

	public StockInfoDaoImpl getstockInfoDao() {
		return stockInfoDao;
	}

	public void setstockInfoDao(StockInfoDaoImpl stockInfoDao) {
		this.stockInfoDao = stockInfoDao;
	}

}

package com.becks.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.becks.dao.impl.StockLabelDaoImpl;
import com.becks.entity.StockLabel;
import com.becks.service.StockLabelService;

/**
 * 创建时间：
 * 
 * @Description StockLabelServiceImpl接口
 * @author BecksHwang
 * @version
 */
@Service("stockLabelService")
public class StockLabelServiceImpl implements StockLabelService {

	@Autowired
	private StockLabelDaoImpl stockLabelDao;

	public StockLabel get(Long id) throws Exception {
		return stockLabelDao.get(StockLabel.class, id);
	}

	public List<StockLabel> findAll() {
		return stockLabelDao.findAll();
	}

	public void save(StockLabel entity) throws Exception {
		stockLabelDao.save(entity);
	}

	public void Update(StockLabel entity) throws Exception {
		stockLabelDao.update(entity);
	}

	public void delete(StockLabel entity) throws Exception {
		stockLabelDao.delete(entity);
	}

	public StockLabelDaoImpl getStockLabelDao() {
		return stockLabelDao;
	}

	public void setStockLabelDao(StockLabelDaoImpl stockLabelDao) {
		this.stockLabelDao = stockLabelDao;
	}

}

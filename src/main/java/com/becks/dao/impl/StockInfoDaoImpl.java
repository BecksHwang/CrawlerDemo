package com.becks.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.becks.entity.StockInfo;

/**
 * 创建时间：
 * 
 * @Description StockInfoDaoImpl接口
 * @author BecksHwang
 * @version
 */
@Repository("stockInfoDao")
public class StockInfoDaoImpl extends BaseDaoImpl<StockInfo> {

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public List<StockInfo> findAll() {
		Session session = this.sessionFactory.openSession();
		List<StockInfo> stockInfos = session.createQuery("from StockInfo").list();
		session.close();
		return stockInfos;
	}

}

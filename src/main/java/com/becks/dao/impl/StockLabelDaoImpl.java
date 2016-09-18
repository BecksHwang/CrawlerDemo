package com.becks.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.becks.entity.StockLabel;

/**
 * 创建时间：
 * 
 * @Description StockLabelDaoImpl接口
 * @author BecksHwang
 * @version
 */
@Repository("stockLabelDao")
public class StockLabelDaoImpl extends BaseDaoImpl<StockLabel> {

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public List<StockLabel> findAll() {
		Session session = this.sessionFactory.openSession();
		List<StockLabel> Targets = session.createQuery("from StockLabel").list();
		session.close();
		return Targets;
	}

}

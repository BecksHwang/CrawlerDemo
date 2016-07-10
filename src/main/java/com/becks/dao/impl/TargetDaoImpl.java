package com.becks.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.becks.entity.Target;

/**
 * 创建时间：
 * 
 * @Description TargetDaoImpl接口
 * @author BecksHwang
 * @version
 */
@Repository("targetDao")
public class TargetDaoImpl extends BaseDaoImpl<Target> {

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public List<Target> findAll() {
		Session session = this.sessionFactory.openSession();
		List<Target> Targets = session.createQuery("from Target").list();
		session.close();
		return Targets;
	}

}

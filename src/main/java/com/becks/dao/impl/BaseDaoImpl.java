package com.becks.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.becks.util.HqlPage;

/**
 * @Description: DAO实现类，提供通用的对数据持久化的方法实现，支持泛型<br/>
 *               如果要用泛型，你传进来的实现类需要继承GenericModel,这个类是公用的，不要随便进行更改。
 * @author BecksHwang
 * @date
 */
@Component
public class BaseDaoImpl<T> {

	public BaseDaoImpl() {

	}

	public BaseDaoImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	};

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void save(T entity) throws Exception {

		Session session = sessionFactory.openSession();
		try {
			session.getTransaction().begin();

			session.save(entity);

			session.getTransaction().commit();
			session.close();
			// HibernateUtils.closeSession(s);
		} catch (RuntimeException e) {
			session.getTransaction().rollback();
			session.close();
			e.printStackTrace();
		}
	}

	public void delete(T entity) throws Exception {

		Session session = sessionFactory.openSession();
		try {
			session.getTransaction().begin();

			session.delete(entity);
			// throw new Exception();
			session.getTransaction().commit();
		} catch (RuntimeException e) {
			session.getTransaction().rollback();
			session.close();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public void update(T entity) throws Exception {

		Session session = sessionFactory.openSession();
		try {
			session.getTransaction().begin();

			session.update(entity);

			session.getTransaction().commit();
			session.close();
			// HibernateUtils.closeSession(s);
		} catch (RuntimeException e) {
			session.getTransaction().rollback();
			session.close();
			e.printStackTrace();
		}
	}

	public T get(Class<T> entityClass, Long id) throws Exception {
		Object retObj = null;
		Session session = sessionFactory.openSession();
		try {
			session.getTransaction().begin();

			retObj = session.get(entityClass, id);

			session.getTransaction().commit();
			session.close();
			// HibernateUtils.closeSession(s);
		} catch (RuntimeException e) {
			session.getTransaction().rollback();
			session.close();
			e.printStackTrace();
		}

		return (T) retObj;
	}

	public List getListByHql(String hql, HqlPage page) {
		List retList = null;
		Session session = sessionFactory.openSession();
		// HibernateUtils.closeSession(s);
		try {
			session.getTransaction().begin();
			Query query = (Query) session.createQuery(hql);
			// System.out.println("Hql:"+hql);
			if (page != null) {
				// System.out.println("index:"+page.index);
				// System.out.println("count:"+page.count);
				query.setFirstResult(page.index);
				query.setMaxResults(page.count);
			}
			retList = query.list(); // 得到每页的数据
			session.getTransaction().commit();
		} catch (Exception e) {
			System.err.println("getListByHql 出错：" + hql);
			e.printStackTrace();
		} finally {
			session.close();
		}
		return retList;
	}

	@SuppressWarnings("unchecked")
	public List<T> getListByHqlByParams(String hql, Map<String, Object> params, HqlPage page) {
		List<T> retList = null;
		Session session = sessionFactory.openSession();

		// HibernateUtils.closeSession(s);
		try {

			session.getTransaction().begin();

			Query query = (Query) session.createQuery(hql);
			// System.out.println("Hql:"+hql);
			if (page != null) {
				// System.out.println("index:"+page.index);
				// System.out.println("count:"+page.count);
				query.setFirstResult(page.index);
				query.setMaxResults(page.count);
			}
			if (params != null) {
				for (Entry<String, Object> entry : params.entrySet()) {
					if (entry.getValue() != null) {
						Object value = entry.getValue();
						if (value instanceof String) {
							value = new String(((String) value).getBytes("iso-8859-1"), "utf-8");
						}
						query.setParameter(entry.getKey(), value);
					}
				}
			}
			retList = query.list(); // 得到每页的数据

			session.getTransaction().commit();

		} catch (Exception e) {
			System.err.println("getListByHqlByParams 出错：" + hql);
			e.printStackTrace();
		} finally {
			session.close();
		}

		return retList;
	}

	public T getOneHql(String hql) throws Exception {
		List list = getListByHql(hql, null);
		if (list == null || list.size() == 0) {
			return null;
		} else {
			return (T) list.get(0);
		}
	}
}

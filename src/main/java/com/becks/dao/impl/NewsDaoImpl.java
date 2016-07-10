package com.becks.dao.impl;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.becks.common.CommonParameter;
import com.becks.entity.News;
import com.becks.util.ConvertStringUtil;
import com.becks.util.HqlPage;
import com.becks.util.Page;

/**
 * 创建时间：
 * 
 * @Description NewsDaoImpl接口
 * @author BecksHwang
 * @version
 */
@Repository("newsDao")
public class NewsDaoImpl extends BaseDaoImpl<News> {

	@Autowired
	private SessionFactory sessionFactory;

	private Session getCurrentSession() {
		return this.sessionFactory.getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	public List<News> findAll() {
		List<News> Newss = this.getCurrentSession().createQuery("from News").setCacheable(true).list();
		return Newss;
	}

	public List<News> getOldNewsByGrabTime(News news) throws Exception {
		StringBuffer hql = new StringBuffer("from News ");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		hql.append(" where pickTime < '" + formatter.format(news.getPickTime()) + "'");
		createOrder(hql, CommonParameter.hqlOrderByDesc, "pickTime");
		return this.getListByHql(hql.toString(), new HqlPage());
	}

	public List<News> getNewNewsByGrabTime(News news) throws Exception {
		StringBuffer hql = new StringBuffer("from News ");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		hql.append(" where pickTime > '" + formatter.format(news.getPickTime()) + "'");
		createOrder(hql, CommonParameter.hqlOrderByDesc, "pickTime");
		return this.getListByHql(hql.toString(), new HqlPage());
	}

	public boolean isExits(Long targetId, String title, String url) {
		boolean result = false;
		String hql = "select count(*) " + ConvertStringUtil.getSelectHqlByClass(News.class);
		String condition = " and targetId =:targetId" + " and title =:title" + " and url=:url";
		hql += condition;
		Map<String, Object> param = new HashMap<>();
		param.put("targetId", targetId);
		param.put("title", title);
		param.put("url", url);
		List<Long> list = this.getListByHqlByParams2(hql, param, null);
		if (list != null && list.size() > 0) {
			Long count = list.get(0);
			if (count > 0) {
				result = true;
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> getListByHqlByParams2(String hql, Map<String, Object> params, Page page) {
		List<T> retList = null;
		Session session = this.sessionFactory.openSession();
		try {
			session.getTransaction().begin();
			Query query = (Query) session.createQuery(hql);
			if (page != null) {
				query.setFirstResult(page.nextPageStart());
				query.setMaxResults(page.getPageSize());
			}
			if (params != null) {
				for (Entry<String, Object> entry : params.entrySet()) {
					query.setParameter(entry.getKey(), entry.getValue());
				}
			}
			retList = query.list(); // 得到每页的数据

			session.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return retList;
	}

	public StringBuffer createOrder(StringBuffer sb, String orderBy, String orderStr) {

		if (orderBy == null || orderBy.equals("")) {
			return sb;
		}
		if (orderBy.equals(CommonParameter.hqlOrderByAsc)) {
			sb.append(" order by " + orderStr + " asc ");
		} else if (orderBy.equals(CommonParameter.hqlOrderByDesc)) {
			sb.append(" order by " + orderStr + " desc ");
		}

		return sb;
	}

}

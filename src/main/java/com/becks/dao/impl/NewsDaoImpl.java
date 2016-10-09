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
import com.becks.util.DateUtil;
import com.becks.util.HqlPage;
import com.becks.util.Page;
import com.becks.util.StringUtil;
import com.becks.vo.NewsQueryVo;

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

	@SuppressWarnings("unchecked")
	public List<News> getOldNewsByGrabTime(News news) throws Exception {
		StringBuffer hql = new StringBuffer("from News ");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		hql.append(" where pickTime < '" + formatter.format(news.getPickTime()) + "'");
		createOrder(hql, CommonParameter.hqlOrderByDesc, "pickTime");
		return this.getListByHql(hql.toString(), new HqlPage());
	}

	@SuppressWarnings("unchecked")
	public List<News> getNewNewsByGrabTime(News news) throws Exception {
		StringBuffer hql = new StringBuffer("from News ");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		hql.append(" where pickTime > '" + formatter.format(news.getPickTime()) + "'");
		createOrder(hql, CommonParameter.hqlOrderByAsc, "pickTime");
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

	@SuppressWarnings({ "static-access", "unchecked" })
	public Page queryPage(Page page, NewsQueryVo qVo) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Page resultPage = new Page();
		String hql = ConvertStringUtil.getSelectHqlByClass(News.class);
		String hqlCount = "select count(*) " + ConvertStringUtil.getSelectHqlByClass(News.class);
		StringBuffer condition = new StringBuffer();
		if (!StringUtil.isNullOrEmpty(qVo.getTitle())) {
			condition.append("and title like '%" + qVo.getTitle() + "%'");
		}
		if (!StringUtil.isNullOrEmpty(qVo.getSource())) {
			condition.append("and source like '%" + qVo.getSource() + "%'");
		}
		if (qVo.getBeginDate() != null) {
			condition.append("and pickTime >= '" + formatter.format(qVo.getBeginDate()) + "'");
		}else{
			condition.append("and pickTime >= '" + formatter.format(new DateUtil().getTodayZeroTimeStamp()) + "'");
		}
		if (qVo.getEndDate() != null) {
			condition.append("and pickTime <= '" + formatter.format(qVo.getEndDate()) + "'");
		}
		hql += condition + "order by pickTime desc";
		hqlCount += condition;
		List<News> retList = null;
		Session session = this.sessionFactory.openSession();
		try {
			session.getTransaction().begin();
			Query query = (Query) session.createQuery(hql);
			Query query2 = (Query) session.createQuery(hqlCount);
			if (page != null) {
				query.setFirstResult(page.getOffset());
				query.setMaxResults(page.getPageSize());
			}
			retList = query.list(); // 得到每页的数据
			int totleCount = Integer.valueOf(query2.list().get(0).toString());// 得到数据条数
			session.getTransaction().commit();
			resultPage.setTotalRow(totleCount);
			resultPage.setItems(retList);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return resultPage;
	}

}

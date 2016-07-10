package com.becks.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.becks.dao.impl.TargetDaoImpl;
import com.becks.entity.Target;
import com.becks.service.TargetService;

/**
 * 创建时间：
 * @Description TargetServiceImpl接口
 * @author BecksHwang
 * @version
 */
@Service("targetService")
public class TargetServiceImpl implements TargetService {

	@Autowired
	private TargetDaoImpl targetDao;

	public Target get(Long id) throws Exception {
		return targetDao.get(Target.class, id);
	}

	public List<Target> findAll() {
		return targetDao.findAll();
	}

	public void save(Target entity) throws Exception {
		targetDao.save(entity);
	}

	public void Update(Target entity) throws Exception {
		targetDao.update(entity);
	}

	public void delete(Target entity) throws Exception {
		targetDao.delete(entity);
	}

	public TargetDaoImpl getTargetDao() {
		return targetDao;
	}

	public void setTargetDao(TargetDaoImpl targetDao) {
		this.targetDao = targetDao;
	}

}

package com.becks.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.becks.dao.impl.InteractionDaoImpl;
import com.becks.entity.Interaction;
import com.becks.service.InteractionService;

/**
 * 创建时间：
 * 
 * @Description InteractionServiceImpl接口
 * @author BecksHwang
 * @version
 */
@Service("interactionService")
public class InteractionServiceImpl implements InteractionService {

	@Autowired
	private InteractionDaoImpl interactionDao;

	@Override
	public boolean isExits(String ask, String answer) {
		return interactionDao.isExits(ask, answer);
	}

	@Override
	public List<Interaction> findAll() {
		return interactionDao.findAll();
	}

	@Override
	public void save(Interaction entity) throws Exception {
		interactionDao.save(entity);
	}

	@Override
	public void Update(Interaction entity) throws Exception {
		interactionDao.update(entity);
	}

	@Override
	public void delete(Interaction entity) throws Exception {
		interactionDao.delete(entity);
	}

}

package com.becks.service;

import java.util.List;

import com.becks.entity.Interaction;

/**
 * 创建时间：
 * 
 * @author BecksHwang
 * @version InteractionService接口
 */
public interface InteractionService {

	List<Interaction> findAll();

	void save(Interaction entity) throws Exception;

	void Update(Interaction entity) throws Exception;

	void delete(Interaction entity) throws Exception;

	boolean isExits(String ask, String answer);
}

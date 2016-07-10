package com.becks.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.becks.entity.Target;

/**
 * 创建时间：
 * 
 * @Description TargetService接口
 * @author BecksHwang
 * @version
 */
@Component
public interface TargetService {

	Target get(Long id) throws Exception;

	List<Target> findAll();

	void save(Target entity) throws Exception;

	void Update(Target entity) throws Exception;

	void delete(Target entity) throws Exception;

}

package com.becks.service;

import java.util.List;

import com.becks.entity.User;

/**
 * 创建时间：
 * 
 * @Description userService接口
 * @author BecksHwang
 * @version
 */
public interface UserService {
	User load(String id);

	User get(String id);

	List<User> findAll();

	void persist(User entity);

	Integer save(User entity);

	void saveOrUpdate(User entity);

	void delete(String id);

	void flush();
}

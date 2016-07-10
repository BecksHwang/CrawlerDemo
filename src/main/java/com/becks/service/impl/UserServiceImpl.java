package com.becks.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.becks.dao.UserDao;
import com.becks.entity.User;
import com.becks.service.UserService;

/**
 * 创建时间：
 * @Description UserServiceImpl接口
 * @author BecksHwang
 * @version
 */
@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	public User load(String id) {
		return userDao.load(id);
	}

	public User get(String id) {
		return userDao.get(id);
	}

	public List<User> findAll() {
		return userDao.findAll();
	}

	public void persist(User entity) {
		userDao.persist(entity);
	}

	public Integer save(User entity) {
		return userDao.save(entity);
	}

	public void saveOrUpdate(User entity) {
		userDao.saveOrUpdate(entity);
	}

	public void delete(String id) {
		userDao.delete(id);
	}

	public void flush() {
		userDao.flush();
	}

}

package com.becks.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.becks.dao.UserDao;
import com.becks.entity.User;

/**
 * 创建时间：
 * 
 * @Description UserDaoImpl接口
 * @author BecksHwang
 * @version
 */  
@Repository("userDao")  
public class UserDaoImpl implements UserDao {  
 
   @Autowired  
   private SessionFactory sessionFactory;  
 
   private Session getCurrentSession() {  
       return this.sessionFactory.getCurrentSession();  
   }  
 
   public User load(String id) {  
       return (User) this.getCurrentSession().load(User.class, id);  
   }  
     
   public User get(String id) {  
       return (User) this.getCurrentSession().get(User.class, id);  
   }  
 
   @SuppressWarnings("unchecked")  
   public List<User> findAll() {  
       List<User> Users = this.getCurrentSession().createQuery("from User").setCacheable(true).list();  
       return Users;  
   }  
 
   public void persist(User entity) {  
       this.getCurrentSession().persist(entity);  
 
   }
   
	public Integer save(User entity) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.save(entity);
		tx.commit();
		return 1;
	}
 
   public void saveOrUpdate(User entity) {  
       this.getCurrentSession().saveOrUpdate(entity);  
   }  
 
   public void delete(String id) {  
       User entity = this.load(id);  
       this.getCurrentSession().delete(entity);  
   }  
 
   public void flush() {  
       this.getCurrentSession().flush();  
   }  
 
}  

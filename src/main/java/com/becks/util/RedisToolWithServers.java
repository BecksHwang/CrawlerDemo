package com.becks.util;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * 创建时间：
 * 
 * @Description Redis工具类 根据传入不同的ShardedJedisPool，使用的是不同服务器上的redis
 * @author BecksHwang
 * @version
 */
public class RedisToolWithServers {
	
	private static ShardedJedisPool jedisPool;
	
	

	public RedisToolWithServers(ShardedJedisPool jedisPoolSource) {
		jedisPool = jedisPoolSource;
	}

	/**
	 * 返还到连接池
	 * 
	 * @param jedisPool2
	 * @param jedis
	 */
	public static void returnResource(ShardedJedisPool jedisPool2, ShardedJedis jedis) {
		if (jedis != null) {
			jedisPool2.returnResourceObject(jedis);
		}
	}

	/**
	 * 获取数据
	 * 
	 * @param key
	 * @return
	 */
	public String get(String key) {
		String value = null;
		ShardedJedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			value = jedis.get(key);
		} catch (Exception e) {
			// 释放redis对象
			jedisPool.returnResourceObject(jedis);
			e.printStackTrace();
		} finally {
			// 返还到连接池
			returnResource(jedisPool, jedis);
		}
		return value;
	}

	/**
	 * 获取数据
	 * 
	 * @param key
	 * @return
	 */
	public Boolean get(String key, String value) {
		Boolean isExists = false;
		ShardedJedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			isExists = jedis.sismember(key, value);
		} catch (Exception e) {
			// 释放redis对象
			jedisPool.returnResourceObject(jedis);
			e.printStackTrace();
		} finally {
			// 返还到连接池
			returnResource(jedisPool, jedis);
		}
		return isExists;
	}

	public void set(String key, String value) {

		ShardedJedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.sadd(key, value);
		} catch (Exception e) {
			// 释放redis对象
			jedisPool.returnResourceObject(jedis);
			e.printStackTrace();
		} finally {
			// 返还到连接池
			returnResource(jedisPool, jedis);
		}
	}

	public static void main(String[] args) {
	}
}
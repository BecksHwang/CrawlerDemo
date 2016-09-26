package com.becks.util;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * 创建时间：
 * 
 * @Description Redis工具类
 * @author BecksHwang
 * @version
 */
public class RedisAPI {
	
	private static ShardedJedisPool jedisPool;

	@SuppressWarnings("static-access")
	public RedisAPI(ShardedJedisPool shardedJedisPool) {
		this.jedisPool = shardedJedisPool;
	}

	/**
	 * 返还到连接池
	 * 
	 * @param pool
	 * @param redis
	 */
	public static void returnResource(ShardedJedisPool jedisPool, ShardedJedis redis) {
		if (redis != null) {
			jedisPool.returnResourceObject(redis);
		}
	}

	/**
	 * 获取key对应的value数据
	 * 
	 * @param key
	 * @return String
	 */
	public static String get(String key) {
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
	 * 判断value是否已经存在于key
	 * 
	 * @param key,value
	 * @return Boolean
	 */
	public static Boolean get(String key, String value) {
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

	public static void set(String key, String value) {
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
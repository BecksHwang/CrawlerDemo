package com.becks.util;

import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
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
	
	@Autowired
	private static ShardedJedisPool shardedJedisPool;

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
	 * 获取数据
	 * 
	 * @param key
	 * @return
	 */
	public static String get(String key) {
		String value = null;
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			value = jedis.get(key);
		} catch (Exception e) {
			// 释放redis对象
			shardedJedisPool.returnResourceObject(jedis);
			e.printStackTrace();
		} finally {
			// 返还到连接池
			returnResource(shardedJedisPool, jedis);
		}
		return value;
	}

	/**
	 * 获取数据
	 * 
	 * @param key
	 * @return
	 */
	public static Boolean get(String key, String value) {
		Boolean isExists = false;
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			// value = jedis.get(key);
			isExists = jedis.sismember(key, value);
		} catch (Exception e) {
			// 释放redis对象
			shardedJedisPool.returnResourceObject(jedis);
			e.printStackTrace();
		} finally {
			// 返还到连接池
			returnResource(shardedJedisPool, jedis);
		}
		return isExists;
	}

	public static void set(String key, String value) {

		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			jedis.sadd(key, value);
		} catch (Exception e) {
			// 释放redis对象
			shardedJedisPool.returnResourceObject(jedis);
			e.printStackTrace();
		} finally {
			// 返还到连接池
			returnResource(shardedJedisPool, jedis);
		}
	}

	public static ShardedJedisPool getShardedJedisPool() {
		return shardedJedisPool;
	}

	public static void setShardedJedisPool(ShardedJedisPool shardedJedisPool) {
		RedisAPI.shardedJedisPool = shardedJedisPool;
	}

	public static void main(String[] args) {
		new RedisAPI().set("RedisAPI22", "RedisAPI222");
	}
}
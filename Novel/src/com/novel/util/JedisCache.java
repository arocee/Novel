package com.novel.util;

import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisCache {

	/**
	 * 存MAP
	 * @param key
	 * @param map
	 */
	public static String putMap(String key, Map<String, String> map) {
		Jedis jedis = null;
		String isOk = null;
		try {
			jedis = CachePool.getInstance().getJedis();
			isOk =  jedis.hmset(key, map);
		} finally {
			if (jedis != null)
				jedis.close();
		}
		
		return isOk;
		
	}
	
	/**
	 * 取MAP
	 * @param key
	 * @return
	 */
	public static Set<String> getMap(String key) {
		Jedis jedis = null;
		Set<String> set = null;
		try {
			jedis = CachePool.getInstance().getJedis();
			set = jedis.hkeys(key);
		} finally {
			if (jedis != null)
				jedis.close();
		}
		return set;
	}
	
	public static String getValueByKey(String key, String fields) {
		Jedis jedis = null;
		String value = null;
		try {
			jedis = CachePool.getInstance().getJedis();
			value = jedis.hmget(key, fields).get(0);
		} finally {
			if (jedis != null)
				jedis.close();
		}
		return value;
	}
	
	/**
	 * 存list
	 * @param key
	 * @param value
	 */
	public static long putList(String key, String value) {
		Jedis jedis = null;
		long count;
		try {
			jedis = CachePool.getInstance().getJedis();
			count = jedis.lpush(key, value);
		} finally {
			if (jedis != null)
				jedis.close();
		}
		
		return count;
	}
	
	/**
	 * 取list
	 * @param key
	 * @return
	 */
	public static List<String> getList(String key) {
		Jedis jedis = null;
		List<String> list = null;
		try {
			jedis = CachePool.getInstance().getJedis();
			list = jedis.lrange(key, 0, -1);
		} finally {
			if (jedis != null)
				jedis.close();
		}
		
		return list;
	}
	
	/**
	 * Map返回list
	 * @param key
	 * @param fields
	 * @return
	 */
	public static List<String> getList(final String key, final String... fields) {
		Jedis jedis = null;
		List<String> list = null;
		try {
			jedis = CachePool.getInstance().getJedis();
			list = jedis.hmget(key, fields);
		} finally {
			if (jedis != null)
				jedis.close();
		}
		
		return list;
	}
	
	/**
	 * 删除list中的某个元素
	 * @param key
	 * @return
	 */
	public static String removeList(final String key) {
		Jedis jedis = null;
		String value = null;
		try {
			jedis = CachePool.getInstance().getJedis();
			value = jedis.lpop(key);
		} finally {
			if (jedis != null)
				jedis.close();
		}
		return value;
	}
	
	/**
	 * 删除Map中的某个元素
	 * @param key
	 * @return
	 */
	public static long removeMap(String key) {
		Jedis jedis = null;
		long count;
		try {
			jedis = CachePool.getInstance().getJedis();
			count = jedis.del(key);
		} finally {
			if (jedis != null)
				jedis.close();
		}
		
		return count;
	}
	
	/**
	 * 删除数据
	 * @param key
	 * @return
	 */
	public static long del(String key) {
		Jedis jedis = null;
		long count;
		try {
			jedis = CachePool.getInstance().getJedis();
			count = jedis.del(key);
		} finally {
			if (jedis != null)
				jedis.close();
		}
		
		return count;
	}
	
	public static class CachePool {
		
		JedisPool pool;
		
		private static final CachePool cachePool = new CachePool();

		public static CachePool getInstance() {
			return cachePool;
		}

		private CachePool() {
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxTotal(Integer.parseInt(PropertiesUtil.getPropertyValue("redis.maxTotal")));
			config.setMaxIdle(Integer.parseInt(PropertiesUtil.getPropertyValue("redis.maxIdle")));
			config.setMaxWaitMillis(Integer.parseInt(PropertiesUtil.getPropertyValue("redis.maxWaitMillis")));
			pool = new JedisPool(config, 
				PropertiesUtil.getPropertyValue("redis.url"), 
				Integer.parseInt(PropertiesUtil.getPropertyValue("redis.port")),
				Integer.parseInt(PropertiesUtil.getPropertyValue("redis.timeout")),
				PropertiesUtil.getPropertyValue("redis.password"),
				1  // 数据库1
			);
		}

		public Jedis getJedis() {
			return pool.getResource();
		}

		public JedisPool getJedisPool() {
			return this.pool;
		}

	}
}

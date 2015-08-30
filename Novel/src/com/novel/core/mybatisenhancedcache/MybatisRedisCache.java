package com.novel.core.mybatisenhancedcache;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.cache.Cache;

import com.novel.util.PropertiesUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class MybatisRedisCache implements Cache {
	private static Log log = LogFactory.getLog(MybatisRedisCache.class);
	/** The ReadWriteLock. */
	private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

	private String id;

	public MybatisRedisCache(final String id) {
		if (id == null) {
			throw new IllegalArgumentException("必须传入ID");
		}
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>MybatisRedisCache:id=" + id);
		this.id = id;
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public int getSize() {
		Jedis jedis = null;
		int result = 0;
		try {
			jedis = CachePool.getInstance().getJedis();
			result = Integer.valueOf(jedis.dbSize().toString());
		} finally {
			if (jedis != null)
				jedis.close();
		}
		return result;

	}

	@Override
	public void putObject(Object key, Object value) {
		if (log.isDebugEnabled())
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>putObject:" + key.hashCode()
					+ "=" + value);
		if (log.isInfoEnabled())
			log.info("put to redis sql :" + key.toString());
		Jedis jedis = null;
		try {
			jedis = CachePool.getInstance().getJedis();
			jedis.set(SerializeUtil.serialize(key.hashCode()),
					SerializeUtil.serialize(value));
		} finally {
			if (jedis != null)
				jedis.close();
		}
	}

	@Override
	public Object getObject(Object key) {
		Jedis jedis = null;
		Object value = null;
		try {
			jedis = CachePool.getInstance().getJedis();
			value = SerializeUtil.unserialize(jedis.get(SerializeUtil
					.serialize(key.hashCode())));
		} finally {
			if (jedis != null)
				jedis.close();
		}
		if (log.isDebugEnabled())
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>getObject:" + key.hashCode()
					+ "=" + value);
		return value;
	}

	@Override
	public Object removeObject(Object key) {
		Jedis jedis = null;
		Object value = null;
		try {
			jedis = CachePool.getInstance().getJedis();
			value = jedis.expire(SerializeUtil.serialize(key.hashCode()), 0);
		} finally {
			if (jedis != null)
				jedis.close();
		}
		if (log.isDebugEnabled())
			log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>getObject:" + key.hashCode()
					+ "=" + value);
		return value;
	}

	@Override
	public void clear() {
		Jedis jedis = null;
		try {
			jedis = CachePool.getInstance().getJedis();
			jedis.flushDB();
			jedis.flushAll();
		} finally {
			if (jedis != null)
				jedis.close();
		}
	}

	@Override
	public ReadWriteLock getReadWriteLock() {
		return readWriteLock;
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
				0  // 数据库0
			);
		}

		public Jedis getJedis() {
			return pool.getResource();
		}

		public JedisPool getJedisPool() {
			return this.pool;
		}

	}

	public static class SerializeUtil {
		public static byte[] serialize(Object object) {
			ObjectOutputStream oos = null;
			ByteArrayOutputStream baos = null;
			try {
				// 序列化
				baos = new ByteArrayOutputStream();
				oos = new ObjectOutputStream(baos);
				oos.writeObject(object);
				byte[] bytes = baos.toByteArray();
				return bytes;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		public static Object unserialize(byte[] bytes) {
			if (bytes == null)
				return null;
			ByteArrayInputStream bais = null;
			try {
				// 反序列化
				bais = new ByteArrayInputStream(bytes);
				ObjectInputStream ois = new ObjectInputStream(bais);
				return ois.readObject();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	}
}
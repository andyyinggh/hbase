package cn.edu.cuit.redis;

import cn.edu.cuit.utils.ConfigUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisClient {
	
	private static JedisPool jedisPool;
	private static int maxActive;
	private static int maxWait;
	private static int maxIdle;
	
	private static void createJedisPool() {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxActive(maxActive);
		config.setMaxWait(maxWait);
		config.setMaxIdle(maxIdle);
		jedisPool = new JedisPool(config,"localhost",6379);
	}
	
	private static void loadConfig() {
		ConfigUtil.load("redis");
		maxActive = ConfigUtil.getInteger("redis.maxActive");
		maxWait = ConfigUtil.getInteger("redis.maxWait");
		maxIdle = ConfigUtil.getInteger("redis.maxIdle");
	}
	
	private static synchronized void init() {
		if(jedisPool == null) {
			loadConfig();
			createJedisPool();
		}
	}
	
	public static Jedis getJedis() {
		init();
		return jedisPool.getResource();
	}
	
	public static void returnResource(Jedis jedis) {
		if(jedis != null) {
			jedisPool.returnResource(jedis);
		}
	}
	
	public boolean exists(String key) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			return jedis.exists(key);
		} finally {
			returnResource(jedis);
		}
	}
	
	public static String set(String key, String value) {
		Jedis jedis = null;
		String result = null;
		try {
			jedis = getJedis();
			result = jedis.set(key, value);
		} finally {
			returnResource(jedis);
		}
		return result;
	}
	
	public static String get(String key) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			String value = jedis.get(key);
			return value;
		} finally {
			returnResource(jedis);
		}
	}

	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

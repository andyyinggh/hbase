package cn.edu.cuit.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.edu.cuit.utils.ConfigUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisClient {
	
	private static Logger logger = LoggerFactory.getLogger(RedisClient.class);
	
	private static JedisPool jedisPool;
	private static String host;
	private static int port;
	private static String pass;
	private static int maxActive;
	private static int maxWait;
	private static int maxIdle;
	
	private void createJedisPool() {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxActive(maxActive);
		config.setMaxWait(maxWait);
		config.setMaxIdle(maxIdle);
		jedisPool = new JedisPool(config, host, port, 50000, pass);
	}
	
	private void loadConfig() {
		ConfigUtil.load("redis");
		host = ConfigUtil.getString("redis.host");
		port = ConfigUtil.getInteger("redis.port");
		pass = ConfigUtil.getString("redis.pass");
		maxActive = ConfigUtil.getInteger("redis.maxActive");
		maxWait = ConfigUtil.getInteger("redis.maxWait");
		maxIdle = ConfigUtil.getInteger("redis.maxIdle");
	}
	
	private synchronized void init() {
		if(jedisPool == null) {
			loadConfig();
			createJedisPool();
		}
	}
	
	public Jedis getJedis() {
		init();
		return jedisPool.getResource();
	}
	
	public void returnResource(Jedis jedis) {
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
	
	public String set(String key, String value) {
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
	
	public String get(String key) {
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
		RedisClient rc = new RedisClient();
		System.out.println(rc.get("user"));
	}

}

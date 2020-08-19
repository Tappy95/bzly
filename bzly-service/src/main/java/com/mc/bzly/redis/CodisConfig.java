package com.mc.bzly.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Component
@ConfigurationProperties
public class CodisConfig {
	
	private static final Logger log = LoggerFactory.getLogger(CodisConfig.class);

	@Value("${jedis.host}")
	private String HOST; // redis IP地址

	@Value("${jedis.port}")
	private int PORT; // redis 端口号

	@Value("${jedis.max.total}")
	private int MAX_TOTAL; // 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。

	@Value("${jedis.min.idle}")
	private int MIN_IDLE; // 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。

	@Value("${jedis.max.idle}")
	private int MAX_IDLE; // 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。

	@Value("${jedis.max.wait.millis}")
	private int MAX_WAIT_MILLIS; // 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超时，则直接抛出JedisConnectionException；

	@Value("${jedis.test.on.borrow}")
	private boolean TEST_ON_BORROW; // 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；

	private static JedisPool jedisPool = null;

	public static final Integer DEFAULT_EXPIRE = 60 * 60 * 24 * 30;

	public Jedis getJedis() {
		Jedis jedis = null;
		try {
			if (jedisPool == null) {
				JedisPoolConfig config = new JedisPoolConfig();
				config.setMaxTotal(MAX_TOTAL);
				config.setMinIdle(MIN_IDLE);
				config.setMaxIdle(MAX_IDLE);
				config.setMaxWaitMillis(MAX_WAIT_MILLIS);
				config.setTestOnBorrow(TEST_ON_BORROW);
				jedisPool = new JedisPool(config, HOST, PORT, 10000);
			}
			jedis = jedisPool.getResource();
		} catch (Exception e) {
			log.error("init jedis resource exception:{}" + e.getMessage());
			return null;
		}
		return jedis;
	}
}

package com.mc.bzly.impl.redis;

import org.springframework.stereotype.Service;

import com.mc.bzly.redis.CodisPoolsUtil;

import redis.clients.jedis.Jedis;

/**
 * 缓存锁
 */
@Service
public class RedisLockServiceImpl {
	/**
	 * 锁定
	 * @param lockKey
	 * @return
	 */
	public boolean lock(String lockKey,String lockValue) {
		Jedis jedis = null;
		try {
			jedis = CodisPoolsUtil.getJedis();
			long value = jedis.setnx(lockKey,lockValue);
			jedis.expire(lockKey, 60);
			return value == 1 ? true : false;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
		}
		return false;
	}

	/**
	 * 解锁
	 * @param lockKey
	 */
	public void unlock(String lockKey,String lockValue) {
		Jedis jedis = null;
		try {
			jedis = CodisPoolsUtil.getJedis();
			String value = jedis.get(lockKey);
			if(lockValue.equals(value)){
				jedis.del(lockKey);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
		}
	}
}

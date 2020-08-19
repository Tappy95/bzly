package com.mc.bzly.redis;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSON;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

public class CodisPoolsUtil {
	public static Jedis getJedis() {
		CodisConfig codisConfig = new CodisConfig();
		return codisConfig.getJedis();
	}

	/**
	 * 通配返回键值集合，如：key_*, *_key
	 * 
	 * @param pattern
	 * @return
	 */
	public static Set<String> keys(String pattern) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			return jedis.keys(pattern);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
		}
		return null;
	}

	/**
	 * 设置字符串键-值
	 * 
	 * @param key
	 * @param value
	 */
	public static void setString(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			value = (value == null) ? "" : value;
			jedis.set(key, value);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
		}
	}

	/**
	 * 返回字符串
	 * 
	 * @param key
	 * @return
	 */
	public static String getString(String key) {
		Jedis jedis = null;
		try {
			jedis = getJedis();

			return jedis.get(key);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
		}
		return null;
	}

	/**
	 * 设置有生存周期的键-值
	 * 
	 * @param key
	 * @param seconds
	 * @param value
	 */
	public static void setString(String key, int seconds, String value) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			value = (value == null) ? "" : value;
			jedis.setex(key, seconds, value);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
		}
	}

	/**
	 * 设置有生存周期的键-值
	 * 
	 * @param key
	 * @param seconds
	 * @param value
	 */
	public static void setByte(byte[] key, int seconds, byte[] value) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.setex(key, seconds, value);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
		}
	}

	/**
	 * 获取byte类型的值
	 * 
	 * @param key
	 * @return
	 */
	public static byte[] getByte(byte[] key) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			return jedis.get(key);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
		}
		return null;
	}

	/**
	 * 设置hash对象的键-值,如果key存在,清除key
	 * 
	 * @param key
	 * @param map
	 *            注：map的value不能为null
	 */
	public static void setMap(String key, Map<String, String> map) {
		Jedis jedis = null;
		try {
			if (map != null && !map.isEmpty()) {
				jedis = getJedis();
				if (jedis.exists(key)) {
					jedis.del(key);
				}
				jedis.hmset(key, map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
		}
	}

	/**
	 * 返回hash对象的所有字段和相关的值
	 * 
	 * @param key
	 * @param field
	 * @return
	 */
	public static Map<String, String> getMap(String key) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			if (!jedis.exists(key)) {
				return null;
			}
			return jedis.hgetAll(key);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
		}
		return null;
	}

	/**
	 * 修改hash某一域field值
	 * 
	 * @param key
	 * @param field
	 * @return
	 */
	public static void getMap(String key, String field, String value) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			Map<String, String> map = jedis.hgetAll(key);
			if (map != null && !map.isEmpty()) {
				map.put(field, value);
			}
			jedis.hmset(key, map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
		}
	}

	/**
	 * 设置hash对象的键-值
	 * 
	 * @param key
	 * @param map
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void hmset(String key, Map map) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.hmset(key, map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
		}
	}

	/**
	 * 设置hash对象的键-值
	 * 
	 * @param key
	 * @param map
	 */
	public static void hset(String key, String field, String value) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.hset(key, field, value);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
		}
	}

	/**
	 * 返回hash某一域field值
	 * 
	 * @param key
	 * @param field
	 * @return
	 */
	public static String getMapFiledValue(String key, String field) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			return jedis.hget(key, field);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
		}
		return null;
	}

	/**
	 * 返回hash指定域fields的集合值
	 * 
	 * @param key
	 * @param field
	 * @return
	 */
	public static List<String> getMapFiledsValue(String key, String... fields) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			if (!jedis.exists(key))
				return null;
			return (List<String>) jedis.hmget(key, fields);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
		}
		return null;
	}

	/**
	 * 删除指定hash的域
	 * 
	 * @param key
	 * @param field
	 */
	public static void deleteMapField(String key, String field) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.hdel(key, field);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
		}
	}

	/**
	 * 判断是否存在key值
	 * 
	 * @param key
	 * @return
	 */
	public static boolean existsKeyValue(String key) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			return jedis.exists(key);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
		}
		return false;
	}

	/**
	 * 删除指定key值
	 * 
	 * @param key
	 * @return
	 */
	public static long deleteKeyValue(String key) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			if (jedis.exists(key)) {
				return jedis.del(key);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return 0;
	}

	/**
	 * 删除key的值
	 * 
	 * @param key
	 * @return
	 */
	public static long deleteByteKey(byte[] key) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			return jedis.del(key);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
		}
		return 0;
	}

	/**
	 * 存储REDIS队列 顺序存储,先进先出(lpush-->rpop)
	 * 
	 * @param key
	 * @param value
	 */
	public static void lpush(byte[] key, byte[] value) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.lpush(key, value);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 返还到连接池
			jedis.close();
		}
	}

	/**
	 * 获取指定区域集合
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public static List<byte[]> lrange(byte[] key, long start, long end) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			return jedis.lrange(key, start, end);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 返还到连接池
			jedis.close();
		}
		return null;
	}

	/**
	 * 保留指定区域长度集合,其余删除
	 * 
	 * @param key
	 * @param start
	 * @param end
	 */
	public static void ltrim(byte[] key, long start, long end) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.ltrim(key, start, end);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 返还到连接池
			jedis.close();
		}
	}

	/**
	 * 存储REDIS队列 反向存储,插入到队列最前端(先进先出rpush-->lpop)
	 * 
	 * @param key
	 * @param value
	 */
	public static void rpush(byte[] key, byte[] value) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.rpush(key, value);
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			// 返还到连接池
			jedis.close();
		}
	}

	/**
	 * 移除并返回列表 key 的头元素
	 * 
	 * @param key
	 * @return
	 */
	public static byte[] lpop(byte[] key) {
		byte[] bytes = null;
		Jedis jedis = null;
		try {
			jedis = getJedis();
			bytes = jedis.lpop(key);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 返还到连接池
			jedis.close();
		}
		return bytes;
	}

	/**
	 * 移除并返回列表 key 的尾元素
	 * 
	 * @param key
	 * @return
	 */
	public static byte[] rpop(byte[] key) {
		byte[] bytes = null;
		Jedis jedis = null;
		try {
			jedis = getJedis();
			bytes = jedis.rpop(key);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 返还到连接池
			jedis.close();
		}
		return bytes;
	}

	/**
	 * 为哈希表 key 中的域 field 的值加上增量 minus
	 * 
	 * @param key
	 * @param field
	 * @param minus
	 * @return
	 */
	public static long hincrby(String key, String field, long minus) {
		long value = 0;
		Jedis jedis = null;
		try {
			jedis = getJedis();
			value = jedis.hincrBy(key, field, minus);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 返还到连接池
			jedis.close();
		}
		return value;
	}

	/**
	 * codis连接测试
	 * 
	 * @return
	 */
	public static String pingTest() {
		Jedis jedis = null;
		String result = "";
		try {
			jedis = getJedis();
			result = jedis.ping();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 返还到连接池
			jedis.close();
		}
		return result;
	}

	/**
	 * 批量顺序存储
	 * 
	 * @param key
	 * @param list
	 */
	public static void pipelineLpushJson(String key, List<?> list) {
		Jedis jedis = null;
		Pipeline pl = null;
		try {
			jedis = getJedis();
			pl = jedis.pipelined();
			for (int i = 0; i < list.size(); i++) {
				pl.lpush(key, JSON.toJSONString(list.get(i)));
			}
			pl.sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
			try {
				pl.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 批量顺序存储
	 * 
	 * @param key
	 * @param list
	 */
	public static void pipelineLpush(byte[] key, List<?> list) {
		Jedis jedis = null;
		Pipeline pl = null;
		try {
			jedis = getJedis();
			pl = jedis.pipelined();
			for (int i = 0; i < list.size(); i++) {
				pl.lpush(key, serialize(list.get(i)));
			}
			pl.sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
			try {
				pl.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * 批量获取列表尾部指定个数元素
	 * 
	 * @param count
	 * @param key
	 * @return
	 */
	public static List<Object> pipelineRpop(Integer count, byte[] key) {
		Jedis jedis = null;
		Pipeline pl = null;
		try {
			jedis = getJedis();
			pl = jedis.pipelined();
			for (int i = 0; i < count; i++) {
				pl.rpop(key);
			}
			return pl.syncAndReturnAll();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
			try {
				pl.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return null;
	}

	/**
	 * 批量获取列表尾部指定个数元素
	 * 
	 * @param count
	 * @param key
	 * @return
	 */
	public static List<?> pipelineRpop(Integer count, String key) {
		Jedis jedis = null;
		Pipeline pl = null;
		try {
			jedis = getJedis();
			pl = jedis.pipelined();
			for (int i = 0; i < count; i++) {
				pl.rpop(key);
			}
			return pl.syncAndReturnAll();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
			try {
				pl.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 返回列表 key的长度
	 * 
	 * @param key
	 * @return
	 */
	public static long llen(byte[] key) {
		long len = 0;
		Jedis jedis = null;
		try {
			jedis = getJedis();
			len = jedis.llen(key);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 返还到连接池
			jedis.close();
		}
		return len;
	}

	/**
	 * 设置对象，并设置失效时间
	 * 
	 * @param key
	 * @param value
	 * @param expireSeconds
	 *            失效时间
	 */
	public static void setObject(String key, Object value, int expireSeconds) {
		if (value == null) {
			return;
		}
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.set(key.getBytes(), serialize(value));
			if (expireSeconds > 0)
				jedis.expire(key.getBytes(), expireSeconds);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 返还到连接池
			jedis.close();
		}
	}

	/**
	 * 获得存储的缓存对象
	 * 
	 * @param key
	 * @param value
	 */
	public static Object getObject(String key) {
		Object object = null;
		Jedis jedis = null;
		try {
			jedis = getJedis();
			byte[] resbytes = jedis.get(key.getBytes());
			if (resbytes == null) {
				return null;
			}
			object = unserialize(resbytes);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 返还到连接池
			jedis.close();
		}
		return object;
	}

	/**
	 * 序列化对象
	 * 
	 * @param object
	 * @return
	 */
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

	/**
	 * 反序列化
	 * 
	 * @param bytes
	 * @return
	 */
	public static Object unserialize(byte[] bytes) {
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

	/**
	 * 进行加1操作
	 * 
	 * @param key
	 */
	public static long incr(String key) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			return jedis.incr(key);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
		}
		return 0;
	}

	/**
	 * 设置过期时间
	 * 
	 * @param key
	 * @param seconds
	 *            秒数
	 */
	public static void expire(String key, int seconds) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.expire(key, seconds);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
		}
	}

	/**
	 * 用于实现redis锁
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public static int setnx(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			return jedis.setnx(key, value).intValue();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 返还到连接池
			jedis.close();
		}
		return 0;
	}

	/**
	 * 管道批量存储
	 * 
	 * @param keys
	 */
	public static void pipelineSadd(String key, List<String> values) {
		Jedis jedis = null;
		Pipeline pl = null;
		try {
			jedis = getJedis();
			pl = jedis.pipelined();
			for (String value : values) {
				pl.sadd(key, value);
			}
			pl.sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
			try {
				pl.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 查看集合中的所有元素
	 * 
	 * @param key
	 * @return
	 */
	public static Set<String> pipelineSmembers(String key) {
		Jedis jedis = null;
		Pipeline pl = null;
		try {
			jedis = getJedis();
			pl = jedis.pipelined();
			Response<Set<String>> res = pl.smembers(key);
			pl.sync();
			return res.get();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
			try {
				pl.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 比较两个集合的差集
	 * 
	 * @param key1
	 * @param key2
	 * @return
	 */
	public static Set<String> pipelineSdiff(String key1, String key2) {
		Jedis jedis = null;
		Pipeline pl = null;
		try {
			jedis = getJedis();
			pl = jedis.pipelined();
			Response<Set<String>> res = pl.sdiff(key1, key2);
			pl.sync();
			return res.get();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
			try {
				pl.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 比较两个集合的交集
	 * 
	 * @param key1
	 * @param key2
	 * @return
	 */
	public static Set<String> pipelineSinter(String key1, String key2) {
		Jedis jedis = null;
		Pipeline pl = null;
		try {
			jedis = getJedis();
			pl = jedis.pipelined();
			Response<Set<String>> res = pl.sinter(key1, key2);
			pl.sync();
			return res.get();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
			try {
				pl.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 比较两个集合的并集
	 * 
	 * @param key1
	 * @param key2
	 * @return
	 */
	public static Set<String> pipelineSunion(String key1, String key2) {
		Jedis jedis = null;
		Pipeline pl = null;
		try {
			jedis = getJedis();
			pl = jedis.pipelined();
			Response<Set<String>> res = pl.sunion(key1, key2);
			pl.sync();
			return res.get();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
			try {
				pl.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static void main(String[] args) {
		Jedis jedis = getJedis();
		Set<String> set = jedis.keys("MZ_APP_AD_LIST_KEY*");
		for (String key : set) {
			System.out.println(key);
			deleteKeyValue(key);
		}
	}

	/**
	 * 查找所有key
	 * @param pattern
	 * @return
	 */
	public static Set<String> findKey(String pattern) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			return jedis.keys(pattern);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
		}
		return null;
	}
}

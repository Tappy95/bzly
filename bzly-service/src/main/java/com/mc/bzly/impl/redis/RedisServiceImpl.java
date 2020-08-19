package com.mc.bzly.impl.redis;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.mc.bzly.redis.CodisConfig;
import com.mc.bzly.redis.CodisPoolsUtil;
import com.mc.bzly.service.redis.RedisService;
import com.mc.bzly.util.StringUtil;

@Service("redisService")
public class RedisServiceImpl implements RedisService {

	@Override
	public void put(String key, Object value) {
		// TODO Auto-generated method stub
		if (StringUtil.notEmpty(key) && value != null) {
			CodisPoolsUtil.setObject(key, value, CodisConfig.DEFAULT_EXPIRE);
			// TODO:考虑是否持久化到数据库
		}
	}

	@Override
	public void put(String key, int expiredTime, String value) {
		// TODO Auto-generated method stub
		if (StringUtil.notEmpty(key) && StringUtil.notEmpty(value)) {
			CodisPoolsUtil.setString(key, expiredTime, value);
			// TODO:考虑是否持久化到数据库
		}
	}

	@Override
	public void delete(String key) {
		// TODO Auto-generated method stub
		// 删除缓存会话
		CodisPoolsUtil.deleteKeyValue(key);
		// 删除数据库会话 TODO:考虑是否持久化到数据库
	}

	@Override
	public Object getObject(String key) {
		// TODO Auto-generated method stub
		if (StringUtil.isEmpty(key)) {
			return null;
		}
		return CodisPoolsUtil.getObject(key);
	}

	public String getString(final String key) {
		if(StringUtils.isBlank(key)){
			return null;
		}
		return CodisPoolsUtil.getString(key);
	}

	@Override
	public void put(String key, Object value,int expiredTime) {
		if (StringUtil.notEmpty(key) && value != null) {
			CodisPoolsUtil.setObject(key,value,expiredTime);
		}
	}
}

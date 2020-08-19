package com.mc.bzly.service.redis;

public interface RedisService {

	public void put(String key, Object value);
	
	public void put(String key, int expiredTime, String value);

	public void delete(String key);

	public Object getObject(String key);

	public String getString(final String key);
	
	public void put(String key,Object value,int expiredTime);
}

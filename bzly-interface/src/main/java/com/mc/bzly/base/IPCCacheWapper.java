package com.mc.bzly.base;

import java.io.Serializable;

public class IPCCacheWapper implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int value;
	
	private long expireTime;

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public long getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(long expireTime) {
		this.expireTime = expireTime;
	}
	
}

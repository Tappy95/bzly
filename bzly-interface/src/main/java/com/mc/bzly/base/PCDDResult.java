package com.mc.bzly.base;

import java.io.Serializable;

public class PCDDResult implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int success; // 1-成功 0-失败
	
	private String message;

	public int getSuccess() {
		return success;
	}

	public void setSuccess(int success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}

package com.mc.bzly.base;

import java.io.Serializable;

public class YmResult implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String code;//状态码（1,失败；0,成功）
	private String message;//失败提示
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	

}

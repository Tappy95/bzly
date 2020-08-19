package com.mc.bzly.interfaces;

public enum RespCodeState {

	/** 非法应用 */
	API_OPERATOR_SUCCESS("2000", "操作成功"), 
	
	API_ERROE_CODE_3000("3000", "操作失败");

	private String statusCode;
	private String message;

	private RespCodeState(String statusCode, String message) {
		this.statusCode = statusCode;
		this.message = message;
	}

	public static String getResponseEnumValue(String statusCode) {
		for (RespCodeState responseEnum : RespCodeState.values()) {
			if (responseEnum.getStatusCode().equals(statusCode)) {
				return responseEnum.getMessage();
			}
		}
		return null;
	}

	public static RespCodeState getResponseEnum(String statusCode) {
		for (RespCodeState responseEnum : RespCodeState.values()) {
			if (statusCode.equals(responseEnum.getStatusCode())) {
				return responseEnum;
			}
		}
		return null;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}

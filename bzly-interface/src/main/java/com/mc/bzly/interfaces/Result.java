package com.mc.bzly.interfaces;

import java.io.Serializable;
import java.util.Map;


public class Result implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3689031409108092697L;

	/**
     * 是否成功
     */

    private boolean           isSuccess;

    /**
     * 错误代码
     */
    private String statusCode;
    /**
     * 错误信息
     */
    private String message;
    
    /**
     * 数据集
     */
    private Object data;
    /**
     * token
     */
    private String token;
    /**
     * 返回json Key是否需要转换
     */
    private boolean isNeedChange=false;
    
    /**
     * 返回格式
     */
    private RespType respType= RespType.JSON;
    
    /**
     * 返回Json转换后的结果集
     */
    private Map<String, Object> changeMap;

    public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Result() {
        this.isSuccess = false;
        statusCode=RespCodeState.API_ERROE_CODE_3000.getStatusCode();
        message=RespCodeState.API_ERROE_CODE_3000.getMessage();
    }

    public Result(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public Result(boolean isSuccess, String statusCode, String message) {
        this.isSuccess = isSuccess;
        this.statusCode = statusCode;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean isSuccess) {
    	if(statusCode==RespCodeState.API_ERROE_CODE_3000.getStatusCode()||statusCode==null){
    		if(isSuccess){
        		this.statusCode=RespCodeState.API_OPERATOR_SUCCESS.getStatusCode();
        		this.message=RespCodeState.API_OPERATOR_SUCCESS.getMessage();
        	}else {
        		this.statusCode=RespCodeState.API_ERROE_CODE_3000.getStatusCode();
        		this.message=RespCodeState.API_ERROE_CODE_3000.getMessage();
    		}
    	}
    	
        this.isSuccess = isSuccess;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public boolean getNeedChange() {
		return isNeedChange;
	}

	public void setNeedChange(boolean isNeedChange) {
		this.isNeedChange = isNeedChange;
	}

	public Map<String, Object> getChangeMap() {
		return changeMap;
	}

	public void setChangeMap(Map<String, Object> changeMap) {
		this.changeMap = changeMap;
	}
	public RespType getRespType() {
		return respType;
	}

	public void setRespType(RespType respType) {
		this.respType = respType;
	}
	public enum RespType{
		NORMAL,
		JSON,
		XML,
		SECURITY
	}
}

package com.mc.bzly.model.thirdparty;

import java.io.Serializable;
import java.util.List;

public class CashtoutiaoResp implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<CashtoutiaoRespData> data;
	
	private boolean state; 
	
	private String msg; 
	
	private Long count; 

	public List<CashtoutiaoRespData> getData() {
		return data;
	}

	public void setData(List<CashtoutiaoRespData> data) {
		this.data = data;
	}

	public boolean getState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}
	
}

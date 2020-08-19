package com.mc.bzly.model;

import java.io.Serializable;

public class BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer pageSize;
	
	private Integer pageNum;

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	
}

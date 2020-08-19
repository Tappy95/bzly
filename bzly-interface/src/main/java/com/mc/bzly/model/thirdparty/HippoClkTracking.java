package com.mc.bzly.model.thirdparty;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;

@Alias("HippoClkTracking")
public class HippoClkTracking extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L; 
	private Integer id;
	private String clkTracking;
	private Long createTime;
	public HippoClkTracking() {
	}
	public HippoClkTracking(String clkTracking, Long createTime) {
		super();
		this.clkTracking = clkTracking;
		this.createTime = createTime;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getClkTracking() {
		return clkTracking;
	}
	public void setClkTracking(String clkTracking) {
		this.clkTracking = clkTracking;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
}

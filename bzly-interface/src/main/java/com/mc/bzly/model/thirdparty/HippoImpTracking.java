package com.mc.bzly.model.thirdparty;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;

@Alias("HippoImpTracking")
public class HippoImpTracking extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L; 
	private Integer id;
	private String impTracking;
	private Long createTime;
	public HippoImpTracking() {
		// TODO Auto-generated constructor stub
	}
	public HippoImpTracking(String impTracking, Long createTime) {
		super();
		this.impTracking = impTracking;
		this.createTime = createTime;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getImpTracking() {
		return impTracking;
	}
	public void setImpTracking(String impTracking) {
		this.impTracking = impTracking;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
}

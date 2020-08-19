package com.mc.bzly.model.checkin;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;

@Alias("CCheckinContinuity")
public class CCheckinContinuity extends BaseModel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String userId;//用户id
	private Integer continuityDays;//连续天数
	private Long updateTime;//修改时间
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Integer getContinuityDays() {
		return continuityDays;
	}
	public void setContinuityDays(Integer continuityDays) {
		this.continuityDays = continuityDays;
	}
	public Long getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}
}

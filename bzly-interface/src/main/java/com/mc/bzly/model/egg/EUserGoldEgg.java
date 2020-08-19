package com.mc.bzly.model.egg;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;

@Alias("EUserGoldEgg")
public class EUserGoldEgg extends BaseModel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String userId;//用户id
	private int frequency;//次数
	private Long creatorTime;//创建时间
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
	public int getFrequency() {
		return frequency;
	}
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	public Long getCreatorTime() {
		return creatorTime;
	}
	public void setCreatorTime(Long creatorTime) {
		this.creatorTime = creatorTime;
	}
	public Long getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}
}

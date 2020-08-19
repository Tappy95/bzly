package com.mc.bzly.model.wish;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;

@Alias("UserWishValue")
public class UserWishValue extends BaseModel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String userId;//用户id
	private int wishValue;//心愿值
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
	public int getWishValue() {
		return wishValue;
	}
	public void setWishValue(int wishValue) {
		this.wishValue = wishValue;
	}
	public Long getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}
}

package com.mc.bzly.model.fighting;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;

@Alias("AnswerUser")
public class AnswerUser extends BaseModel implements Serializable{
	
	 private static final long serialVersionUID = 1L;
	 
	 private String userId; 
	 private Integer coin;
	 private Integer isMatching; 
	 private Integer role; 
	 private String entryCode; 
	 
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Integer getCoin() {
		return coin;
	}
	public void setCoin(Integer coin) {
		this.coin = coin;
	}
	public Integer getIsMatching() {
		return isMatching;
	}
	public void setIsMatching(Integer isMatching) {
		this.isMatching = isMatching;
	}
	public Integer getRole() {
		return role;
	}
	public void setRole(Integer role) {
		this.role = role;
	}
	public String getEntryCode() {
		return entryCode;
	}
	public void setEntryCode(String entryCode) {
		this.entryCode = entryCode;
	}
}

package com.mc.bzly.model.user;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;
@Alias("LActiveGoldLog")
public class LActiveGoldLog extends BaseModel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String userId; 
	private Integer vipId; 
	private Long activeCoin; 
	private Long activePig; 
	private Long days; 
	private Long creatorTime; 
	
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
	public Integer getVipId() {
		return vipId;
	}
	public void setVipId(Integer vipId) {
		this.vipId = vipId;
	}
	public Long getActiveCoin() {
		return activeCoin;
	}
	public void setActiveCoin(Long activeCoin) {
		this.activeCoin = activeCoin;
	}
	public Long getActivePig() {
		return activePig;
	}
	public void setActivePig(Long activePig) {
		this.activePig = activePig;
	}
	public Long getCreatorTime() {
		return creatorTime;
	}
	public void setCreatorTime(Long creatorTime) {
		this.creatorTime = creatorTime;
	}
	public Long getDays() {
		return days;
	}
	public void setDays(Long days) {
		this.days = days;
	}
}

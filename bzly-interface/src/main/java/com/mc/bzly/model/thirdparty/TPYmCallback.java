package com.mc.bzly.model.thirdparty;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;

@Alias("TPYmCallback")
public class TPYmCallback extends BaseModel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String userId;//用户id
	private String coinCallbackId;//奖励记录Id
	private Long coinCount;//奖励金币数
	private Long callbackTime;//回调时间
	private String sign;//加密值
	private Integer state;//状态1成功2失败
	private Long creatorTime;//创建时间
	
	private Long ts;//阅盟回调时传的时间戳
	
	private String accountId;
		
	private Integer pageIndex;
	private Long startTime;
	private Long endTime;
	
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
	public String getCoinCallbackId() {
		return coinCallbackId;
	}
	public void setCoinCallbackId(String coinCallbackId) {
		this.coinCallbackId = coinCallbackId;
	}
	public Long getCoinCount() {
		return coinCount;
	}
	public void setCoinCount(Long coinCount) {
		this.coinCount = coinCount;
	}
	public Long getCallbackTime() {
		return callbackTime;
	}
	public void setCallbackTime(Long callbackTime) {
		this.callbackTime = callbackTime;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Long getCreatorTime() {
		return creatorTime;
	}
	public void setCreatorTime(Long creatorTime) {
		this.creatorTime = creatorTime;
	}
	public Long getTs() {
		return ts;
	}
	public void setTs(Long ts) {
		this.ts = ts;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public Integer getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
	public Long getStartTime() {
		return startTime;
	}
	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}
	public Long getEndTime() {
		return endTime;
	}
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}
	
}

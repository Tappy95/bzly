package com.mc.bzly.model.user;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;

@Alias("LuserCashGameStatistic")
public class LuserCashGameStatistic extends BaseModel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer accountId;//用户id
	private String channelCode;//渠道号
	private String moneyMin;//最小钱数
	private String moneyMax;//最大钱数
	private String gameMin;//最小游戏次数
	private String gameMax;//最大游戏次数
	private Long startRegisterTime;//起始注册时间
	private Long endRegisterTime;//结束注册时间
	private Integer state;//1审核中2提现成功3提现失败4提现异常5提现通过
	
	private String channel;
	private Integer userRelation;
	
	private Integer pageIndex;

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getMoneyMin() {
		return moneyMin;
	}

	public void setMoneyMin(String moneyMin) {
		this.moneyMin = moneyMin;
	}

	public String getMoneyMax() {
		return moneyMax;
	}

	public void setMoneyMax(String moneyMax) {
		this.moneyMax = moneyMax;
	}

	public String getGameMin() {
		return gameMin;
	}

	public void setGameMin(String gameMin) {
		this.gameMin = gameMin;
	}

	public String getGameMax() {
		return gameMax;
	}

	public void setGameMax(String gameMax) {
		this.gameMax = gameMax;
	}

	public Long getStartRegisterTime() {
		return startRegisterTime;
	}

	public void setStartRegisterTime(Long startRegisterTime) {
		this.startRegisterTime = startRegisterTime;
	}

	public Long getEndRegisterTime() {
		return endRegisterTime;
	}

	public void setEndRegisterTime(Long endRegisterTime) {
		this.endRegisterTime = endRegisterTime;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public Integer getUserRelation() {
		return userRelation;
	}

	public void setUserRelation(Integer userRelation) {
		this.userRelation = userRelation;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	
}

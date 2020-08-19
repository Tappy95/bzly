package com.mc.bzly.model.user;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;
@Alias("LUserReconciliation")
public class LUserReconciliation extends BaseModel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer accountId;//账户id
	private Integer userRelation;//渠道关系
	private String channelCode;//渠道标识
    private String channel;//登录用户渠道标识
    private Long minZcYxCount;//最小注册当天完成游戏任务次数
    private Long maxZcYxCount;//最大注册当天完成游戏任务次数
    private Long minYxCount;//最小累计完成游戏任务次数
    private Long maxYxCount;//最大累计完成游戏任务次数
    private Long startTime;
    private Long endTime;
	
	private Integer pageIndex;

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public Integer getUserRelation() {
		return userRelation;
	}

	public void setUserRelation(Integer userRelation) {
		this.userRelation = userRelation;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public Long getMinZcYxCount() {
		return minZcYxCount;
	}

	public void setMinZcYxCount(Long minZcYxCount) {
		this.minZcYxCount = minZcYxCount;
	}

	public Long getMaxZcYxCount() {
		return maxZcYxCount;
	}

	public void setMaxZcYxCount(Long maxZcYxCount) {
		this.maxZcYxCount = maxZcYxCount;
	}

	public Long getMinYxCount() {
		return minYxCount;
	}

	public void setMinYxCount(Long minYxCount) {
		this.minYxCount = minYxCount;
	}

	public Long getMaxYxCount() {
		return maxYxCount;
	}

	public void setMaxYxCount(Long maxYxCount) {
		this.maxYxCount = maxYxCount;
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

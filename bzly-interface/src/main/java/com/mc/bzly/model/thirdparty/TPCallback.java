package com.mc.bzly.model.thirdparty;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;

/**
 * 游戏回调总表
 */
@Alias("TPCallback")
public class TPCallback extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id; // 
	private String tpName; // 游戏方名称
	private String gameId; // 游戏id
	private String gameName; // 游戏名称
	private String orderNum; // 流水号（订单号）
	private String gameReward; // 游戏方奖励金币数
	private String reward; // 总奖励金币数
	private String tpGameId; // 用户在游戏方的id
	private String userId; // 奖励用户id
	private String mobile; // 用户手机号
	private String channelCode; // 渠道标识
	private String channelName; // 渠道名称
	private Long createTime; // 创建时间
	private Integer status; // 状态（1-成功 2-失败）
	private Integer accountId;
	
	private String channel;
	private Integer userRelation;
	
	private String createDate;
	
	private Long startTime;
	private Long endTime;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTpName() {
		return tpName;
	}
	public void setTpName(String tpName) {
		this.tpName = tpName;
	}
	public String getGameId() {
		return gameId;
	}
	public void setGameId(String gameId) {
		this.gameId = gameId;
	}
	public String getGameName() {
		return gameName;
	}
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public String getGameReward() {
		return gameReward;
	}
	public void setGameReward(String gameReward) {
		this.gameReward = gameReward;
	}
	public String getReward() {
		return reward;
	}
	public void setReward(String reward) {
		this.reward = reward;
	}
	public String getTpGameId() {
		return tpGameId;
	}
	public void setTpGameId(String tpGameId) {
		this.tpGameId = tpGameId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getChannelCode() {
		return channelCode;
	}
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
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
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
}

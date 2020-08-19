package com.mc.bzly.model.checkin;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;

@Alias("CCheckinLog")
public class CCheckinLog extends BaseModel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String userId;//用户id
	private Long payCoin;//支付金币
	private Long rewardCoin;//奖励金币
	private Integer userType;//用户类型：1真实用户2机器人
	private Integer state;//状态：1待打卡，2打卡成功，3打卡失败
	private Long createTime;//创建时间
	private Long checkinTime;//打卡日期（年月日）
	private Long userTime;//用户打卡时间
	private Long lastTime;//上次打卡时间
	private Integer continuityDays;//连续打卡次数
	private Integer isTips;//是否已提示打卡失败弹框1未提示2提示
	private Integer isCoupon;//是否使用补签券1未使用2使用
	
	private Integer accountId;//用户id
	private Long startCreateTime;//起始时间
	private Long endCreateTime;//结束时间
	private Integer pageIndex;

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

	public Long getPayCoin() {
		return payCoin;
	}

	public void setPayCoin(Long payCoin) {
		this.payCoin = payCoin;
	}

	public Long getRewardCoin() {
		return rewardCoin;
	}

	public void setRewardCoin(Long rewardCoin) {
		this.rewardCoin = rewardCoin;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Long getCheckinTime() {
		return checkinTime;
	}

	public void setCheckinTime(Long checkinTime) {
		this.checkinTime = checkinTime;
	}

	public Long getUserTime() {
		return userTime;
	}

	public void setUserTime(Long userTime) {
		this.userTime = userTime;
	}

	public Long getLastTime() {
		return lastTime;
	}

	public void setLastTime(Long lastTime) {
		this.lastTime = lastTime;
	}

	public Integer getContinuityDays() {
		return continuityDays;
	}

	public void setContinuityDays(Integer continuityDays) {
		this.continuityDays = continuityDays;
	}

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public Integer getIsTips() {
		return isTips;
	}

	public void setIsTips(Integer isTips) {
		this.isTips = isTips;
	}

	public Integer getIsCoupon() {
		return isCoupon;
	}

	public void setIsCoupon(Integer isCoupon) {
		this.isCoupon = isCoupon;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public Long getStartCreateTime() {
		return startCreateTime;
	}

	public void setStartCreateTime(Long startCreateTime) {
		this.startCreateTime = startCreateTime;
	}

	public Long getEndCreateTime() {
		return endCreateTime;
	}

	public void setEndCreateTime(Long endCreateTime) {
		this.endCreateTime = endCreateTime;
	}
	
}

package com.mc.bzly.model.user;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;

/**
 * 渠道查询字段用户
 * @author admin
 *
 */
@Alias("MChannelUser")
public class MChannelUser extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
    private String accountId;//用户id
    private String mobile;//手机号
    private Long createTime;//注册时间
    private String channelCode;//渠道标识
    private Long coin;//剩余金币
    private Long pigCoin;//剩余金猪
    private Long sumCoin;//累计获得金币
    private Double sumRecharge;//累计充值
    private Long sumCash;//累计提现
    private Integer roleType;//角色类型（1-小猪猪 2-团队长 3-超级合伙人）
    private String channelRelation;
    private String userId;
    private String remark;
    private Integer userRelation;
    private String level;
    private Integer equipmentType;
    
    private Long startTime;//起始时间
    private Long endTime;//结束时间
    private Long maxCoin;//最大累计金币数
    private Long minCoin;//最小累计金币数
    
    private Integer pageIndex;
    
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public String getChannelCode() {
		return channelCode;
	}
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	public Long getCoin() {
		return coin;
	}
	public void setCoin(Long coin) {
		this.coin = coin;
	}
	public Long getPigCoin() {
		return pigCoin;
	}
	public void setPigCoin(Long pigCoin) {
		this.pigCoin = pigCoin;
	}
	public Long getSumCoin() {
		return sumCoin;
	}
	public void setSumCoin(Long sumCoin) {
		this.sumCoin = sumCoin;
	}
	public Double getSumRecharge() {
		return sumRecharge;
	}
	public void setSumRecharge(Double sumRecharge) {
		this.sumRecharge = sumRecharge;
	}
	public Long getSumCash() {
		return sumCash;
	}
	public void setSumCash(Long sumCash) {
		this.sumCash = sumCash;
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
	public Long getMaxCoin() {
		return maxCoin;
	}
	public void setMaxCoin(Long maxCoin) {
		this.maxCoin = maxCoin;
	}
	public Long getMinCoin() {
		return minCoin;
	}
	public void setMinCoin(Long minCoin) {
		this.minCoin = minCoin;
	}
	public Integer getRoleType() {
		return roleType;
	}
	public void setRoleType(Integer roleType) {
		this.roleType = roleType;
	}
	public String getChannelRelation() {
		return channelRelation;
	}
	public void setChannelRelation(String channelRelation) {
		this.channelRelation = channelRelation;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getUserRelation() {
		return userRelation;
	}
	public void setUserRelation(Integer userRelation) {
		this.userRelation = userRelation;
	}
	public Integer getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public Integer getEquipmentType() {
		return equipmentType;
	}
	public void setEquipmentType(Integer equipmentType) {
		this.equipmentType = equipmentType;
	}
	
}

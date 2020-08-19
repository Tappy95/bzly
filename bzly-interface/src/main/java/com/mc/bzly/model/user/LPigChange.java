package com.mc.bzly.model.user;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;

@Alias("LPigChange")
public class LPigChange extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String userId; 
	private Long amount; 
	private Integer flowType; 
	private Integer changedType; 
	private Long changedTime; 
	private String remarks;//备注
    private String level;
    private Long pigBalance;//金猪余额
	
	private Long revenue;//收入
	private Long expend;//支出
	
	private Long startTime;//起始时间
	private Long endTime;//结束时间
	
	private String userName; 
	private String channelCode;//渠道标识
	private Integer accountId;//账户id
	private String mobile;//用户手机号码
	private String channelRelation;//渠道关系
	private Integer roleType;//角色类型（1-小猪猪 2-团队长 3-超级合伙人）
	private Integer userRelation;
	private Long registerTime;//注册时间
	private String exclRegisterTime;
	
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
	
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public Integer getFlowType() {
		return flowType;
	}
	public void setFlowType(Integer flowType) {
		this.flowType = flowType;
	}
	public Integer getChangedType() {
		return changedType;
	}
	public void setChangedType(Integer changedType) {
		this.changedType = changedType;
	}
	public Long getChangedTime() {
		return changedTime;
	}
	public void setChangedTime(Long changedTime) {
		this.changedTime = changedTime;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public LPigChange() {
	}
	public LPigChange(String userId, Long amount, Integer flowType, Integer changedType, Long changedTime,String remarks,Long pigBalance) {
		super();
		this.userId = userId;
		this.amount = amount;
		this.flowType = flowType;
		this.changedType = changedType;
		this.changedTime = changedTime;
		this.remarks = remarks;
		this.pigBalance = pigBalance;
	}
	public String getChannelCode() {
		return channelCode;
	}
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	public Integer getAccountId() {
		return accountId;
	}
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getChannelRelation() {
		return channelRelation;
	}
	public void setChannelRelation(String channelRelation) {
		this.channelRelation = channelRelation;
	}
	public Integer getRoleType() {
		return roleType;
	}
	public void setRoleType(Integer roleType) {
		this.roleType = roleType;
	}
	public Integer getUserRelation() {
		return userRelation;
	}
	public void setUserRelation(Integer userRelation) {
		this.userRelation = userRelation;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public Long getRevenue() {
		return revenue;
	}
	public void setRevenue(Long revenue) {
		this.revenue = revenue;
	}
	public Long getExpend() {
		return expend;
	}
	public void setExpend(Long expend) {
		this.expend = expend;
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
	public Integer getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
	public Long getPigBalance() {
		return pigBalance;
	}
	public void setPigBalance(Long pigBalance) {
		this.pigBalance = pigBalance;
	}
	public Long getRegisterTime() {
		return registerTime;
	}
	public void setRegisterTime(Long registerTime) {
		this.registerTime = registerTime;
	}
	public String getExclRegisterTime() {
		return exclRegisterTime;
	}
	public void setExclRegisterTime(String exclRegisterTime) {
		this.exclRegisterTime = exclRegisterTime;
	}
	
	
}

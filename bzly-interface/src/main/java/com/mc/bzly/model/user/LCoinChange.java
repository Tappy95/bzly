package com.mc.bzly.model.user;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;

@Alias("LCoinChange")
public class LCoinChange extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String userId;      
	private Long amount;        
	private Integer flowType;   
	private Integer changedType; // 变更原因1答题2来访礼3提现4推荐用户获得5徒弟贡献6vip 7.游戏试玩奖励 8.徒弟到达4L奖励 9-新人注册奖励10任务11出题12兑换金猪 13-阅读资讯14-提现退回15直属用户返利 16-团队长赠送17间接用户返利18居间返利 19-阅读广告奖励 20-分享资讯 21-签到赚 22-大众团队长分佣 23-快速赚任务 24-达人首次奖励 25-达人后续奖励 26-阅读小说 27 达人邀请周榜奖励 28-高额赚提成 29 每日红包任务 30观看视频 31 小游戏奖励 32打卡消耗33打卡奖励 34 金币排行日榜奖励 35-心愿猪宝箱抽奖36-心愿猪新手引导奖励 37-分红心瓜分金额 38-幸运星瓜分金额 39-徒弟贡献金额 40-徒孙贡献金额 41-直接好友开通VIP奖励 42-简介好友开通VIP奖励
	private Long changedTime;   
	private Integer status;     
	private Integer accountType;
	private Long auditTime;     
	private String reason;      
	private Double amountM;
	private String remarks;//备注
	private String level;
	private Long coinBalance;
	
	private Long actualAmount;//实际金额
	private Double actualAmountM;//实际金额
	
	private Long revenue;//收入
	private Long expend;//支出
	
	private Long startTime;//起始时间
	private Long endTime;//结束时间
	
	private Long coinMin;
	private Long coinMax;
	
	private String userName;//用户姓名
	private String mobile;//用户手机号码
    private String aliNum;//用户支付宝号码
    private String channelCode;//渠道标识
    private Integer accountId;//账户id
    private String channelRelation;//渠道关系
    private Integer roleType;//角色类型（1-小猪猪 2-团队长 3-超级合伙人）
    private String account;//提现账号
    private Integer userRelation;//用户关系1全部2直属用户3非直属用户
    private Long registerTime;//注册时间
    private String exclRegisterTime;
    private String userIp;
    private Integer equipmentType;
    
    private Integer pageIndex;
    private List<String> userIds;
	
	
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getAccountType() {
		return accountType;
	}
	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}
	public Long getAuditTime() {
		return auditTime;
	}
	public void setAuditTime(Long auditTime) {
		this.auditTime = auditTime;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public LCoinChange() {
	}
	
	public Double getAmountM() {
		return amountM;
	}
	public void setAmountM(Double amountM) {
		this.amountM = amountM;
	}
	/**
	 * 
	 * @param userId 用户id
	 * @param amount 变更金额
	 * @param flowType 流水类型 1-收入 2-支出
	 * @param changedType 变更类型
	 * @param changedTime 变更时间
	 * @param status 状态
	 * @param remarks 描述
	 * @param coinBalance 剩余金币
	 */
	public LCoinChange(String userId, Long amount, Integer flowType, Integer changedType, Long changedTime,Integer status,String remarks,Long coinBalance) {
		super();
		this.userId = userId;
		this.amount = amount;
		this.flowType = flowType;
		this.changedType = changedType;
		this.changedTime = changedTime;
		this.status = status;
		this.remarks = remarks;
		this.coinBalance = coinBalance;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getAliNum() {
		return aliNum;
	}
	public void setAliNum(String aliNum) {
		this.aliNum = aliNum;
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
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Integer getUserRelation() {
		return userRelation;
	}
	public void setUserRelation(Integer userRelation) {
		this.userRelation = userRelation;
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
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public Integer getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
	public Long getCoinBalance() {
		return coinBalance;
	}
	public void setCoinBalance(Long coinBalance) {
		this.coinBalance = coinBalance;
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
	public Long getCoinMin() {
		return coinMin;
	}
	public void setCoinMin(Long coinMin) {
		this.coinMin = coinMin;
	}
	public Long getCoinMax() {
		return coinMax;
	}
	public void setCoinMax(Long coinMax) {
		this.coinMax = coinMax;
	}
	public String getUserIp() {
		return userIp;
	}
	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}
	public Integer getEquipmentType() {
		return equipmentType;
	}
	public void setEquipmentType(Integer equipmentType) {
		this.equipmentType = equipmentType;
	}
	public Long getActualAmount() {
		return actualAmount;
	}
	public void setActualAmount(Long actualAmount) {
		this.actualAmount = actualAmount;
	}
	public List<String> getUserIds() {
		return userIds;
	}
	public void setUserIds(List<String> userIds) {
		this.userIds = userIds;
	}
	public Double getActualAmountM() {
		return actualAmountM;
	}
	public void setActualAmountM(Double actualAmountM) {
		this.actualAmountM = actualAmountM;
	}
	
	
	
}

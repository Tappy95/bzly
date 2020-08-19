package com.mc.bzly.model.user;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;
/**
 * 用户提现记录
 * @author admin
 *
 */
@Alias("LUserExchangeCash")
public class LUserExchangeCash extends BaseModel implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer coinChangeId;//金币变动id
	private String userId;//用户id
	private String outTradeNo;//订单号
	private Long coin;//提现金币
	private BigDecimal actualAmount;//实际提现金额（元）
	private BigDecimal serviceCharge;//提现服务费（元）
	private Integer coinToMoney;//兑换比例
	private Long creatorTime;//创建时间
	private String operatorMobile;//操作人账号
	private Long examineTime;//审核时间
	private String remarks;//备注
	private Integer state;//状态1审核中2提现成功3提现失败
	private String lockingMobile;//锁定人账号
	private Integer isLocking;//是否锁定1是2否
	private String bankAccount;//银行卡号
	private String realName;//真实姓名
	private String userIp;
	private Integer cashType;//提现类型1不用完成任务2完成任务
	
	private String admin;//管理员账号
	private Integer accountId;//用户id
	private String channelCode;//渠道号
	private String bankName;//银行名称
	private String bankNum;//银行卡号
	private String userName;//用户真实姓名
	private String operatorName;//操作人姓名
	private Long coinBalance;//金币余额
	private Integer locking;//2可以解锁
	private String stateName;
	private String creatorDate;
	private String examineDate;
	private String registerDate;
	
	private Long registerTime;
	
	private Long startTime;//起始时间
	private Long endTime;//结束时间
	private Double coinMin;//最小金币数
	private Double coinMax;//最大金币数
	private Long examineStartTime;//审核起始时间
	private Long examineEndTime;//审核结束时间
	private Integer days;
	private Integer userRelation;
	private String channel;
	
	private Integer pageIndex;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCoinChangeId() {
		return coinChangeId;
	}
	public void setCoinChangeId(Integer coinChangeId) {
		this.coinChangeId = coinChangeId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getOutTradeNo() {
		return outTradeNo;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	public Long getCoin() {
		return coin;
	}
	public void setCoin(Long coin) {
		this.coin = coin;
	}
	public BigDecimal getActualAmount() {
		return actualAmount;
	}
	public void setActualAmount(BigDecimal actualAmount) {
		this.actualAmount = actualAmount;
	}
	public BigDecimal getServiceCharge() {
		return serviceCharge;
	}
	public void setServiceCharge(BigDecimal serviceCharge) {
		this.serviceCharge = serviceCharge;
	}
	public Integer getCoinToMoney() {
		return coinToMoney;
	}
	public void setCoinToMoney(Integer coinToMoney) {
		this.coinToMoney = coinToMoney;
	}
	public Long getCreatorTime() {
		return creatorTime;
	}
	public void setCreatorTime(Long creatorTime) {
		this.creatorTime = creatorTime;
	}
	public String getOperatorMobile() {
		return operatorMobile;
	}
	public void setOperatorMobile(String operatorMobile) {
		this.operatorMobile = operatorMobile;
	}
	public Long getExamineTime() {
		return examineTime;
	}
	public void setExamineTime(Long examineTime) {
		this.examineTime = examineTime;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getLockingMobile() {
		return lockingMobile;
	}
	public void setLockingMobile(String lockingMobile) {
		this.lockingMobile = lockingMobile;
	}
	public Integer getIsLocking() {
		return isLocking;
	}
	public void setIsLocking(Integer isLocking) {
		this.isLocking = isLocking;
	}
	public String getAdmin() {
		return admin;
	}
	public void setAdmin(String admin) {
		this.admin = admin;
	}
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
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankNum() {
		return bankNum;
	}
	public void setBankNum(String bankNum) {
		this.bankNum = bankNum;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public Long getCoinBalance() {
		return coinBalance;
	}
	public void setCoinBalance(Long coinBalance) {
		this.coinBalance = coinBalance;
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
	public Double getCoinMin() {
		return coinMin;
	}
	public void setCoinMin(Double coinMin) {
		this.coinMin = coinMin;
	}
	public Double getCoinMax() {
		return coinMax;
	}
	public void setCoinMax(Double coinMax) {
		this.coinMax = coinMax;
	}
	public Integer getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
	public Integer getLocking() {
		return locking;
	}
	public void setLocking(Integer locking) {
		this.locking = locking;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public String getCreatorDate() {
		return creatorDate;
	}
	public void setCreatorDate(String creatorDate) {
		this.creatorDate = creatorDate;
	}
	public String getExamineDate() {
		return examineDate;
	}
	public void setExamineDate(String examineDate) {
		this.examineDate = examineDate;
	}
	public Long getExamineStartTime() {
		return examineStartTime;
	}
	public void setExamineStartTime(Long examineStartTime) {
		this.examineStartTime = examineStartTime;
	}
	public Long getExamineEndTime() {
		return examineEndTime;
	}
	public void setExamineEndTime(Long examineEndTime) {
		this.examineEndTime = examineEndTime;
	}
	public Long getRegisterTime() {
		return registerTime;
	}
	public void setRegisterTime(Long registerTime) {
		this.registerTime = registerTime;
	}
	public String getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}
	public Integer getDays() {
		return days;
	}
	public void setDays(Integer days) {
		this.days = days;
	}
	public Integer getUserRelation() {
		return userRelation;
	}
	public void setUserRelation(Integer userRelation) {
		this.userRelation = userRelation;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getUserIp() {
		return userIp;
	}
	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}
	public Integer getCashType() {
		return cashType;
	}
	public void setCashType(Integer cashType) {
		this.cashType = cashType;
	}
	
    
}

package com.mc.bzly.model.passbook;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;

@Alias("MVipInfo")
public class MVipInfo extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private String name;
	
	private String logo;

	private Long firstReward; 

	private Integer firstRewardUnit; 

	private Long continueReward; 

	private Integer continueRewardUnit; 

	private Integer gameAddition; 

	private Integer useDay; 
	
	private Double coinToPigAddition; 

	private Long everydayActivePig; 

	private Long everydayActiveCoin; 
	
	private Integer onetimeLimit; 
	
	private Integer auditFirst; 
	
	private Integer sendSms; 
	
	private Long everydayReliefPig; 
	
	private Integer everydayReliefPigTimes; 
	
	private Integer oneWithdrawals; 
	
	private BigDecimal price; 
	
	private Long createTime;
	
	private Integer status; 
	
	private Integer orderId;
	
	private String backgroundImg;//背景图片
	
	private String overdueImg;
	
	private int isBuy; 
	private int isPay;
	
	private Integer isTask;//是否需要完成任务1是2否
	
	private Integer taskNum;//任务个数
	
	private Integer isRenew;//是否可以续费1是2否
	
	private Integer vipType;//1.普通vip 2.中青赚点 3-高额赚VIP
	private Integer cashMoney;//可提现金额 单位元，-1无限制
	private Integer rewardDay;//奖励天数,-1无限制
	private Integer returnVip;//退款vipId,-1不需要
	private Integer highVip; // 是否为高额赚VIP（1-不是 2-是）

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public Long getFirstReward() {
		return firstReward;
	}

	public void setFirstReward(Long firstReward) {
		this.firstReward = firstReward;
	}

	public Integer getFirstRewardUnit() {
		return firstRewardUnit;
	}

	public void setFirstRewardUnit(Integer firstRewardUnit) {
		this.firstRewardUnit = firstRewardUnit;
	}

	public Long getContinueReward() {
		return continueReward;
	}

	public void setContinueReward(Long continueReward) {
		this.continueReward = continueReward;
	}

	public Integer getContinueRewardUnit() {
		return continueRewardUnit;
	}

	public void setContinueRewardUnit(Integer continueRewardUnit) {
		this.continueRewardUnit = continueRewardUnit;
	}

	public Integer getGameAddition() {
		return gameAddition;
	}

	public void setGameAddition(Integer gameAddition) {
		this.gameAddition = gameAddition;
	}

	public Integer getUseDay() {
		return useDay;
	}

	public void setUseDay(Integer useDay) {
		this.useDay = useDay;
	}

	public Double getCoinToPigAddition() {
		return coinToPigAddition;
	}

	public void setCoinToPigAddition(Double coinToPigAddition) {
		this.coinToPigAddition = coinToPigAddition;
	}

	public Long getEverydayActivePig() {
		return everydayActivePig;
	}

	public void setEverydayActivePig(Long everydayActivePig) {
		this.everydayActivePig = everydayActivePig;
	}

	public Long getEverydayActiveCoin() {
		return everydayActiveCoin;
	}

	public void setEverydayActiveCoin(Long everydayActiveCoin) {
		this.everydayActiveCoin = everydayActiveCoin;
	}

	public Integer getOnetimeLimit() {
		return onetimeLimit;
	}

	public void setOnetimeLimit(Integer onetimeLimit) {
		this.onetimeLimit = onetimeLimit;
	}

	public Integer getAuditFirst() {
		return auditFirst;
	}

	public void setAuditFirst(Integer auditFirst) {
		this.auditFirst = auditFirst;
	}

	public Integer getSendSms() {
		return sendSms;
	}

	public void setSendSms(Integer sendSms) {
		this.sendSms = sendSms;
	}

	public Long getEverydayReliefPig() {
		return everydayReliefPig;
	}

	public void setEverydayReliefPig(Long everydayReliefPig) {
		this.everydayReliefPig = everydayReliefPig;
	}

	public Integer getEverydayReliefPigTimes() {
		return everydayReliefPigTimes;
	}

	public void setEverydayReliefPigTimes(Integer everydayReliefPigTimes) {
		this.everydayReliefPigTimes = everydayReliefPigTimes;
	}

	public Integer getOneWithdrawals() {
		return oneWithdrawals;
	}

	public void setOneWithdrawals(Integer oneWithdrawals) {
		this.oneWithdrawals = oneWithdrawals;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
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

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public int getIsBuy() {
		return isBuy;
	}

	public void setIsBuy(int isBuy) {
		this.isBuy = isBuy;
	}

	public int getIsPay() {
		return isPay;
	}

	public void setIsPay(int isPay) {
		this.isPay = isPay;
	}

	public Integer getIsTask() {
		return isTask;
	}

	public void setIsTask(Integer isTask) {
		this.isTask = isTask;
	}

	public Integer getTaskNum() {
		return taskNum;
	}

	public void setTaskNum(Integer taskNum) {
		this.taskNum = taskNum;
	}

	public Integer getIsRenew() {
		return isRenew;
	}

	public void setIsRenew(Integer isRenew) {
		this.isRenew = isRenew;
	}

	public String getBackgroundImg() {
		return backgroundImg;
	}

	public void setBackgroundImg(String backgroundImg) {
		this.backgroundImg = backgroundImg;
	}

	public Integer getVipType() {
		return vipType;
	}

	public void setVipType(Integer vipType) {
		this.vipType = vipType;
	}

	public Integer getCashMoney() {
		return cashMoney;
	}

	public void setCashMoney(Integer cashMoney) {
		this.cashMoney = cashMoney;
	}

	public Integer getRewardDay() {
		return rewardDay;
	}

	public void setRewardDay(Integer rewardDay) {
		this.rewardDay = rewardDay;
	}

	public Integer getReturnVip() {
		return returnVip;
	}

	public void setReturnVip(Integer returnVip) {
		this.returnVip = returnVip;
	}

	public String getOverdueImg() {
		return overdueImg;
	}

	public void setOverdueImg(String overdueImg) {
		this.overdueImg = overdueImg;
	}

	public Integer getHighVip() {
		return highVip;
	}

	public void setHighVip(Integer highVip) {
		this.highVip = highVip;
	}
	
}

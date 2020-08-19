package com.mc.bzly.model.user;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;

@Alias("LUserCashLog")
public class LUserCashLog extends BaseModel implements Serializable{
	
	public Integer getMinDays() {
		return minDays;
	}
	public void setMinDays(Integer minDays) {
		this.minDays = minDays;
	}
	public Integer getMaxDays() {
		return maxDays;
	}
	public void setMaxDays(Integer maxDays) {
		this.maxDays = maxDays;
	}
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String userId;//用户id
	private String outTradeNo;//提现订单号
	private Long cashCoin;//提现金额 单位：金币
	private Long cashTime;//提现时间
	private Integer cashNum;//提现次数
    private Integer days;//距离注册时间的天数
    
    private String channelCode;
    private Integer accountId;
    private Integer createTime;
    private String channel;
    private Integer userRelation;
    
    private Integer equipmentType;
    
    
    private Integer minDays;
    private Integer maxDays;
    private Long minCreateDate;
    private Long maxCreateDate;
    private String cashDate;
    private String createDate;
    private Integer pageIndex;
    private Long minCashTime;
    private Long maxCashTime;
    
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
	public String getOutTradeNo() {
		return outTradeNo;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	public Long getCashCoin() {
		return cashCoin;
	}
	public void setCashCoin(Long cashCoin) {
		this.cashCoin = cashCoin;
	}
	public Long getCashTime() {
		return cashTime;
	}
	public void setCashTime(Long cashTime) {
		this.cashTime = cashTime;
	}
	public Integer getCashNum() {
		return cashNum;
	}
	public void setCashNum(Integer cashNum) {
		this.cashNum = cashNum;
	}
	public Integer getDays() {
		return days;
	}
	public void setDays(Integer days) {
		this.days = days;
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
	public Integer getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
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
	public String getCashDate() {
		return cashDate;
	}
	public void setCashDate(String cashDate) {
		this.cashDate = cashDate;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public Long getMinCreateDate() {
		return minCreateDate;
	}
	public void setMinCreateDate(Long minCreateDate) {
		this.minCreateDate = minCreateDate;
	}
	public Long getMaxCreateDate() {
		return maxCreateDate;
	}
	public void setMaxCreateDate(Long maxCreateDate) {
		this.maxCreateDate = maxCreateDate;
	}
	public Integer getEquipmentType() {
		return equipmentType;
	}
	public void setEquipmentType(Integer equipmentType) {
		this.equipmentType = equipmentType;
	}
	public Long getMinCashTime() {
		return minCashTime;
	}
	public void setMinCashTime(Long minCashTime) {
		this.minCashTime = minCashTime;
	}
	public Long getMaxCashTime() {
		return maxCashTime;
	}
	public void setMaxCashTime(Long maxCashTime) {
		this.maxCashTime = maxCashTime;
	}
	
	
}

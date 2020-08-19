package com.mc.bzly.model.pay;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;
 
@Alias("PPayLog")
public class PPayLog extends BaseModel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
    private int id;
    private String userId; 
    private String outTradeNo; 
    private BigDecimal price; 
    private BigDecimal balance; 
    private BigDecimal actualPrice; 
    private String descripte; 
    private String payMode; 
    private String payType; 
    private String payIp; 
    private int couponId; 
    private String openId; 
    private Integer state; 
    private long creatorTime; 
    private long payTime; 
    private long cancelTime; 
    private int isDelete; 
    private int payPurpose; 
    private int vipId; 
    private int isBalance; 
    private int webState;
    private String channelCode;
    
    private BigDecimal discount;//折扣金额
    private Integer accountId;//用户id
    private String typeName;
    private String changedDate;
    private String payDate;
    
    private String userChannelCode;
    
    private Long startTime;
    private Long endTime;
    private String stateName;
    
    private Long startPayTime;
    private Long endPayTime;
    
    private String userName; 
    private String purposeName;
    
    private Integer pageIndex;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOutTradeNo() {
		return outTradeNo;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	public String getDescripte() {
		return descripte;
	}
	public void setDescripte(String descripte) {
		this.descripte = descripte;
	}
	public String getPayMode() {
		return payMode;
	}
	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getPayIp() {
		return payIp;
	}
	public void setPayIp(String payIp) {
		this.payIp = payIp;
	}
	public int getCouponId() {
		return couponId;
	}
	public void setCouponId(int couponId) {
		this.couponId = couponId;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public long getCreatorTime() {
		return creatorTime;
	}
	public void setCreatorTime(long creatorTime) {
		this.creatorTime = creatorTime;
	}
	public long getPayTime() {
		return payTime;
	}
	public void setPayTime(long payTime) {
		this.payTime = payTime;
	}
	public long getCancelTime() {
		return cancelTime;
	}
	public void setCancelTime(long cancelTime) {
		this.cancelTime = cancelTime;
	}
	public int getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getPayPurpose() {
		return payPurpose;
	}
	public void setPayPurpose(int payPurpose) {
		this.payPurpose = payPurpose;
	}
	public int getVipId() {
		return vipId;
	}
	public void setVipId(int vipId) {
		this.vipId = vipId;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public BigDecimal getActualPrice() {
		return actualPrice;
	}
	public void setActualPrice(BigDecimal actualPrice) {
		this.actualPrice = actualPrice;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getWebState() {
		return webState;
	}
	public void setWebState(int webState) {
		this.webState = webState;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public int getIsBalance() {
		return isBalance;
	}
	public void setIsBalance(int isBalance) {
		this.isBalance = isBalance;
	}
	public BigDecimal getDiscount() {
		return discount;
	}
	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
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
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getChangedDate() {
		return changedDate;
	}
	public void setChangedDate(String changedDate) {
		this.changedDate = changedDate;
	}
	public Integer getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
	public String getPurposeName() {
		return purposeName;
	}
	public void setPurposeName(String purposeName) {
		this.purposeName = purposeName;
	}
	public String getUserChannelCode() {
		return userChannelCode;
	}
	public void setUserChannelCode(String userChannelCode) {
		this.userChannelCode = userChannelCode;
	}
	public Long getStartPayTime() {
		return startPayTime;
	}
	public void setStartPayTime(Long startPayTime) {
		this.startPayTime = startPayTime;
	}
	public Long getEndPayTime() {
		return endPayTime;
	}
	public void setEndPayTime(Long endPayTime) {
		this.endPayTime = endPayTime;
	}
	public String getPayDate() {
		return payDate;
	}
	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}
	
}

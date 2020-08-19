package com.mc.bzly.model.egg;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;

@Alias("EGoleEggOrder")
public class EGoleEggOrder extends BaseModel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String userId;//用户id
	private Long pigCoin;//消耗金猪数
	private Long obtainPigCoin;//获得金猪
	private String cardNumber;//卡号
	private String cardPassword;//卡密
	private Integer state;//状态：1未使用2已使用
	private String exchangeUserId;//使用者用户id
	private Long creatorTime;//创建时间
	private Long exchangeTime;//使用时间
	private Integer isProhibit;//是否禁用：1否2是
	private int modifyPassword;//修改密码次数
	
	private Integer accountId;
	private Integer exchangeAccountId;
	private Long startCreatorTime;
	private Long endCreatorTime;
	private Long startExchangeTime;
	private Long endExchangeTime;
	private Integer pageIndex;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Long getPigCoin() {
		return pigCoin;
	}

	public void setPigCoin(Long pigCoin) {
		this.pigCoin = pigCoin;
	}

	public Long getObtainPigCoin() {
		return obtainPigCoin;
	}

	public void setObtainPigCoin(Long obtainPigCoin) {
		this.obtainPigCoin = obtainPigCoin;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getCardPassword() {
		return cardPassword;
	}

	public void setCardPassword(String cardPassword) {
		this.cardPassword = cardPassword;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getExchangeUserId() {
		return exchangeUserId;
	}

	public void setExchangeUserId(String exchangeUserId) {
		this.exchangeUserId = exchangeUserId;
	}

	public Long getCreatorTime() {
		return creatorTime;
	}

	public void setCreatorTime(Long creatorTime) {
		this.creatorTime = creatorTime;
	}

	public Long getExchangeTime() {
		return exchangeTime;
	}

	public void setExchangeTime(Long exchangeTime) {
		this.exchangeTime = exchangeTime;
	}

	public Integer getIsProhibit() {
		return isProhibit;
	}

	public void setIsProhibit(Integer isProhibit) {
		this.isProhibit = isProhibit;
	}

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public Integer getExchangeAccountId() {
		return exchangeAccountId;
	}

	public void setExchangeAccountId(Integer exchangeAccountId) {
		this.exchangeAccountId = exchangeAccountId;
	}

	public Long getStartCreatorTime() {
		return startCreatorTime;
	}

	public void setStartCreatorTime(Long startCreatorTime) {
		this.startCreatorTime = startCreatorTime;
	}

	public Long getEndCreatorTime() {
		return endCreatorTime;
	}

	public void setEndCreatorTime(Long endCreatorTime) {
		this.endCreatorTime = endCreatorTime;
	}

	public Long getStartExchangeTime() {
		return startExchangeTime;
	}

	public void setStartExchangeTime(Long startExchangeTime) {
		this.startExchangeTime = startExchangeTime;
	}

	public Long getEndExchangeTime() {
		return endExchangeTime;
	}

	public void setEndExchangeTime(Long endExchangeTime) {
		this.endExchangeTime = endExchangeTime;
	}

	public int getModifyPassword() {
		return modifyPassword;
	}

	public void setModifyPassword(int modifyPassword) {
		this.modifyPassword = modifyPassword;
	}
	
}

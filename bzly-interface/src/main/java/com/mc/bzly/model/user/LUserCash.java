package com.mc.bzly.model.user;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;

@Alias("LUserCash")
public class LUserCash  extends BaseModel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String userId;//用户id
	private String outTradeNo;//提现订单号
	private Integer mode;//提现方式1-微信 2-支付宝
	private Integer money;//提现金额
	private Integer state;//状态：1未完成2已完成3审核中4提现成功
	private Long creatorTime;//创建时间
	
	private Integer accountId;
	private String creatorDate;
	
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
	public Integer getMode() {
		return mode;
	}
	public void setMode(Integer mode) {
		this.mode = mode;
	}
	public Integer getMoney() {
		return money;
	}
	public void setMoney(Integer money) {
		this.money = money;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Long getCreatorTime() {
		return creatorTime;
	}
	public void setCreatorTime(Long creatorTime) {
		this.creatorTime = creatorTime;
	}
	public String getCreatorDate() {
		return creatorDate;
	}
	public void setCreatorDate(String creatorDate) {
		this.creatorDate = creatorDate;
	}
	public Integer getAccountId() {
		return accountId;
	}
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}
}

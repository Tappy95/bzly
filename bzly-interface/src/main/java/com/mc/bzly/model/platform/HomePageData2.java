package com.mc.bzly.model.platform;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

@Alias("HomePageData2")
public class HomePageData2 implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long cashUserNum;// 提现人数
	private Double vipAmount; // 购买VIP总金额
	private Double swAmount; // 试玩累计金额
	private Double txzAmount; //  提现总金额
	private Double hygxAmount; // 好友贡献	
	private Long bindZFB; // 绑定支付宝人数
	private Long vipCounts; // VIP人数
	
	public Long getCashUserNum() {
		return cashUserNum;
	}
	public void setCashUserNum(Long cashUserNum) {
		this.cashUserNum = cashUserNum;
	}
	public Double getVipAmount() {
		return vipAmount;
	}
	public void setVipAmount(Double vipAmount) {
		this.vipAmount = vipAmount;
	}
	public Double getSwAmount() {
		return swAmount;
	}
	public void setSwAmount(Double swAmount) {
		this.swAmount = swAmount;
	}
	public Double getTxzAmount() {
		return txzAmount;
	}
	public void setTxzAmount(Double txzAmount) {
		this.txzAmount = txzAmount;
	}
	public Double getHygxAmount() {
		return hygxAmount;
	}
	public void setHygxAmount(Double hygxAmount) {
		this.hygxAmount = hygxAmount;
	}
	public Long getBindZFB() {
		return bindZFB;
	}
	public void setBindZFB(Long bindZFB) {
		this.bindZFB = bindZFB;
	}
	public Long getVipCounts() {
		return vipCounts;
	}
	public void setVipCounts(Long vipCounts) {
		this.vipCounts = vipCounts;
	}
	
}

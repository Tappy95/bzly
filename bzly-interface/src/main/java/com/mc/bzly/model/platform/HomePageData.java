package com.mc.bzly.model.platform;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

@Alias("HomePageData")
public class HomePageData implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long signCount; // 签到人数
	private Long vipCounts; // VIP人数
	private Long regUser; // 新增注册量
	private Long loginCount; // 登陆人数
	private Long firstVip; // 首次购买VIP人数 
	private Long bindZFB; // 绑定支付宝人数
	private Double cjzAmount; // 抽奖总金额
	private Double txzAmount; //  提现总金额
	private Double hygxAmount; // 好友贡献
	private Double vipAmount; // 购买VIP总金额
	private Double tdzAmount; // 运营总监总金额
	private Double jzdhAmount; // 金猪兑换总金额
	private Double zjzAmount; // 中奖总金额
	private Double swAmount; // 试玩累计金额
	private Double lpdjAmount; // 礼品等价总金额
	private Double hdjlAmount; // 活动简历 
	private Double jczAmount; // 竞猜总金额
	private Long dtCount;//参与答题人数
	private Long cashUserNum;// 提现人数
	private Long kszUserNum; // 快速赚任务
	private Long qdzUserNum; // 签到赚任务
	private Long lxdlCount; // 连续签到任务
	private Long jcrAmount; // 竞猜人数
	
	public Long getSignCount() {
		return signCount;
	}
	public void setSignCount(Long signCount) {
		this.signCount = signCount == null?0:signCount;
	}
	public Long getVipCounts() {
		return vipCounts;
	}
	public void setVipCounts(Long vipCounts) {
		this.vipCounts = vipCounts == null?0:vipCounts;
	}
	public Long getRegUser() {
		return regUser;
	}
	public void setRegUser(Long regUser) {
		this.regUser = regUser == null?0:regUser;
	}
	public Long getLoginCount() {
		return loginCount;
	}
	public void setLoginCount(Long loginCount) {
		this.loginCount = loginCount == null?0:loginCount;
	}
	public Long getFirstVip() {
		return firstVip;
	}
	public void setFirstVip(Long firstVip) {
		this.firstVip = firstVip == null?0:firstVip;
	}
	public Long getBindZFB() {
		return bindZFB;
	}
	public void setBindZFB(Long bindZFB) {
		this.bindZFB = bindZFB == null?0:bindZFB;
	}
	public Double getCjzAmount() {
		return cjzAmount;
	}
	public void setCjzAmount(Double cjzAmount) {
		this.cjzAmount = cjzAmount == null?0:cjzAmount;
	}
	public Double getTxzAmount() {
		return txzAmount;
	}
	public void setTxzAmount(Double txzAmount) {
		this.txzAmount = txzAmount == null?0:txzAmount;
	}
	public Double getHygxAmount() {
		return hygxAmount;
	}
	public void setHygxAmount(Double hygxAmount) {
		this.hygxAmount = hygxAmount == null?0:hygxAmount;
	}
	public Double getVipAmount() {
		return vipAmount;
	}
	public void setVipAmount(Double vipAmount) {
		this.vipAmount = vipAmount == null?0:vipAmount;
	}
	public Double getTdzAmount() {
		return tdzAmount;
	}
	public void setTdzAmount(Double tdzAmount) {
		this.tdzAmount = tdzAmount == null?0:tdzAmount;
	}
	public Double getJzdhAmount() {
		return jzdhAmount;
	}
	public void setJzdhAmount(Double jzdhAmount) {
		this.jzdhAmount = jzdhAmount == null?0:jzdhAmount;
	}
	public Double getZjzAmount() {
		return zjzAmount;
	}
	public void setZjzAmount(Double zjzAmount) {
		this.zjzAmount = zjzAmount == null?0:zjzAmount;
	}
	public Double getSwAmount() {
		return swAmount;
	}
	public void setSwAmount(Double swAmount) {
		this.swAmount = swAmount == null?0:swAmount;
	}
	public Double getLpdjAmount() {
		return lpdjAmount;
	}
	public void setLpdjAmount(Double lpdjAmount) {
		this.lpdjAmount = lpdjAmount == null?0:lpdjAmount;
	}
	public Double getHdjlAmount() {
		return hdjlAmount;
	}
	public void setHdjlAmount(Double hdjlAmount) {
		this.hdjlAmount = hdjlAmount == null?0:hdjlAmount;
	}
	public Double getJczAmount() {
		return jczAmount;
	}
	public void setJczAmount(Double jczAmount) {
		this.jczAmount = jczAmount == null?0:jczAmount;
	}
	public Long getCashUserNum() {
		return cashUserNum;
	}
	public void setCashUserNum(Long cashUserNum) {
		this.cashUserNum = cashUserNum;
	}
	public Long getDtCount() {
		return dtCount;
	}
	public void setDtCount(Long dtCount) {
		this.dtCount = dtCount;
	}
	public Long getKszUserNum() {
		return kszUserNum;
	}
	public void setKszUserNum(Long kszUserNum) {
		this.kszUserNum = kszUserNum;
	}
	public Long getQdzUserNum() {
		return qdzUserNum;
	}
	public void setQdzUserNum(Long qdzUserNum) {
		this.qdzUserNum = qdzUserNum;
	}
	public Long getLxdlCount() {
		return lxdlCount;
	}
	public void setLxdlCount(Long lxdlCount) {
		this.lxdlCount = lxdlCount;
	}
	public Long getJcrAmount() {
		return jcrAmount;
	}
	public void setJcrAmount(Long jcrAmount) {
		this.jcrAmount = jcrAmount;
	}
	
}

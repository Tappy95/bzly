package com.mc.bzly.model.thirdparty;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;
/**
 * 裂变方案
 * @author admin
 *
 */
@Alias("MFissionScheme")
public class MFissionScheme extends BaseModel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String name;//方案名称
	private BigDecimal teamPrice;//团队长价格
	private BigDecimal renewPrice;//续费价格
	private Double oneCommission;//一级分佣
	private Double twoCommission;//二级分佣
	private Double partnerCommission;//合伙人分佣
	private Integer effectiveDay;//团队长有效天数
	private Double ordinaryExchange;//普通用户起提金额
	private Double groupExchange;//团队长起提金额
	private Double giveMoney;//每日赠送人民币数
	private Long giveCoin;//每天赠送金币数
	private Long givePig;//每天赠送金猪数
	private Integer giveDay;//赠送天数
	private String schemeImg;//方案图
	private String ordinaryRewardImg;//普通用户奖励图
	private String teamRewardImg;//团队长奖励图
	private String darenRewardImg;//达人奖励图
	private String inviteImg;//邀请流程图
	private String remarks;//备注
	private Long createrTime;//创建时间
	
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
	public BigDecimal getTeamPrice() {
		return teamPrice;
	}
	public void setTeamPrice(BigDecimal teamPrice) {
		this.teamPrice = teamPrice;
	}
	public BigDecimal getRenewPrice() {
		return renewPrice;
	}
	public void setRenewPrice(BigDecimal renewPrice) {
		this.renewPrice = renewPrice;
	}
	public Double getOneCommission() {
		return oneCommission;
	}
	public void setOneCommission(Double oneCommission) {
		this.oneCommission = oneCommission;
	}
	public Double getTwoCommission() {
		return twoCommission;
	}
	public void setTwoCommission(Double twoCommission) {
		this.twoCommission = twoCommission;
	}
	public Double getPartnerCommission() {
		return partnerCommission;
	}
	public void setPartnerCommission(Double partnerCommission) {
		this.partnerCommission = partnerCommission;
	}
	public Integer getEffectiveDay() {
		return effectiveDay;
	}
	public void setEffectiveDay(Integer effectiveDay) {
		this.effectiveDay = effectiveDay;
	}
	public Double getOrdinaryExchange() {
		return ordinaryExchange;
	}
	public void setOrdinaryExchange(Double ordinaryExchange) {
		this.ordinaryExchange = ordinaryExchange;
	}
	public Double getGroupExchange() {
		return groupExchange;
	}
	public void setGroupExchange(Double groupExchange) {
		this.groupExchange = groupExchange;
	}
	public Double getGiveMoney() {
		return giveMoney;
	}
	public void setGiveMoney(Double giveMoney) {
		this.giveMoney = giveMoney;
	}
	public Long getGiveCoin() {
		return giveCoin;
	}
	public void setGiveCoin(Long giveCoin) {
		this.giveCoin = giveCoin;
	}
	public Long getGivePig() {
		return givePig;
	}
	public void setGivePig(Long givePig) {
		this.givePig = givePig;
	}
	public Integer getGiveDay() {
		return giveDay;
	}
	public void setGiveDay(Integer giveDay) {
		this.giveDay = giveDay;
	}
	public String getSchemeImg() {
		return schemeImg;
	}
	public void setSchemeImg(String schemeImg) {
		this.schemeImg = schemeImg;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Long getCreaterTime() {
		return createrTime;
	}
	public void setCreaterTime(Long createrTime) {
		this.createrTime = createrTime;
	}
	public String getOrdinaryRewardImg() {
		return ordinaryRewardImg;
	}
	public void setOrdinaryRewardImg(String ordinaryRewardImg) {
		this.ordinaryRewardImg = ordinaryRewardImg;
	}
	public String getTeamRewardImg() {
		return teamRewardImg;
	}
	public void setTeamRewardImg(String teamRewardImg) {
		this.teamRewardImg = teamRewardImg;
	}
	public String getDarenRewardImg() {
		return darenRewardImg;
	}
	public void setDarenRewardImg(String darenRewardImg) {
		this.darenRewardImg = darenRewardImg;
	}
	public String getInviteImg() {
		return inviteImg;
	}
	public void setInviteImg(String inviteImg) {
		this.inviteImg = inviteImg;
	}
}

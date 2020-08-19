package com.mc.bzly.model.egg;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;
@Alias("EGoldEggType")
public class EGoldEggType extends BaseModel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	 
	private Integer id;
	private String name;//名称
	private Long pigCoin;//消耗金猪数
	private Long creatorTime;//创建时间
	private Integer orders;//排序
	private Long servicePigCoin;//手续费
	private String cardSign;//卡号标记
	
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
	public Long getPigCoin() {
		return pigCoin;
	}
	public void setPigCoin(Long pigCoin) {
		this.pigCoin = pigCoin;
	}
	public Long getCreatorTime() {
		return creatorTime;
	}
	public void setCreatorTime(Long creatorTime) {
		this.creatorTime = creatorTime;
	}
	public Integer getOrders() {
		return orders;
	}
	public void setOrders(Integer orders) {
		this.orders = orders;
	}
	public Long getServicePigCoin() {
		return servicePigCoin;
	}
	public void setServicePigCoin(Long servicePigCoin) {
		this.servicePigCoin = servicePigCoin;
	}
	public String getCardSign() {
		return cardSign;
	}
	public void setCardSign(String cardSign) {
		this.cardSign = cardSign;
	}
}

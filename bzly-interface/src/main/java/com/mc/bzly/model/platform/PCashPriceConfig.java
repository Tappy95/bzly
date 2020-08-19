package com.mc.bzly.model.platform;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;

@Alias("PCashPriceConfig")
public class PCashPriceConfig extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer price;//提现价格
	private Integer orders;//排序
	private Integer isTask;//是否需要完成任务1是2否
	private Integer webType;//平台类型1app,2小程序-现在不用,3心愿猪app
	private Double priceDouble;//心愿猪小数提现需求
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Integer getIsTask() {
		return isTask;
	}
	public void setIsTask(Integer isTask) {
		this.isTask = isTask;
	}
	public Integer getOrders() {
		return orders;
	}
	public void setOrders(Integer orders) {
		this.orders = orders;
	}
	public Integer getWebType() {
		return webType;
	}
	public void setWebType(Integer webType) {
		this.webType = webType;
	}
	public Double getPriceDouble() {
		return priceDouble;
	}
	public void setPriceDouble(Double priceDouble) {
		this.priceDouble = priceDouble;
	}
	
}

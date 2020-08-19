package com.mc.bzly.model.platform;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

@Alias("ExcelDataModelQD")
public class ExcelDataModelQD implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String ordernum;
	private String adname;
	private Integer dlevel;
	private String event;
	private Double price;
	private Double money;
	private String createTime;
	private Integer account_id;
	public String getOrdernum() {
		return ordernum;
	}
	public void setOrdernum(String ordernum) {
		this.ordernum = ordernum;
	}
	public String getAdname() {
		return adname;
	}
	public void setAdname(String adname) {
		this.adname = adname;
	}
	public Integer getDlevel() {
		return dlevel;
	}
	public void setDlevel(Integer dlevel) {
		this.dlevel = dlevel;
	}
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public Integer getAccount_id() {
		return account_id;
	}
	public void setAccount_id(Integer account_id) {
		this.account_id = account_id;
	}
}

package com.mc.bzly.model.platform;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

@Alias("HomePageData3")
public class HomePageData3 implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long jcrAmount; // 竞猜人数
	private Double jczAmount; // 竞猜总金额
	private Double zjzAmount; // 中奖总金额
	private Double cjzAmount; // 抽奖总金额
	
	public Long getJcrAmount() {
		return jcrAmount;
	}
	public void setJcrAmount(Long jcrAmount) {
		this.jcrAmount = jcrAmount;
	}
	public Double getJczAmount() {
		return jczAmount;
	}
	public void setJczAmount(Double jczAmount) {
		this.jczAmount = jczAmount;
	}
	public Double getZjzAmount() {
		return zjzAmount;
	}
	public void setZjzAmount(Double zjzAmount) {
		this.zjzAmount = zjzAmount;
	}
	public Double getCjzAmount() {
		return cjzAmount;
	}
	public void setCjzAmount(Double cjzAmount) {
		this.cjzAmount = cjzAmount;
	}
	
}

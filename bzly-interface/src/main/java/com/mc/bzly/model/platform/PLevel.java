package com.mc.bzly.model.platform;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;

@Alias("PLevel")
public class PLevel extends BaseModel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String level; 
	private Long experience; 
	private String imgUrl;  
	private Integer orderId; 
	private Integer addition; 
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public Long getExperience() {
		return experience;
	}
	public void setExperience(Long experience) {
		this.experience = experience;
	}

	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public Integer getAddition() {
		return addition;
	}
	public void setAddition(Integer addition) {
		this.addition = addition;
	}
	
}

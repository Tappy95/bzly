package com.mc.bzly.model.user;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;
 
@Alias("LUserHippo")
public class LUserHippo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String userId;
	private String category;
	private Long createTime;
	private Integer status;
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
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}

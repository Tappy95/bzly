package com.mc.bzly.model.user;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;

@Alias("LRankMachine")
public class LRankMachine extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String img;//头像
	private String mobile;//手机号
	private Long createTime;//创建时间
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
}

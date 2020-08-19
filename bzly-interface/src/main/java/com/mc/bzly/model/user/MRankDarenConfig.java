package com.mc.bzly.model.user;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;

@Alias("MRankDarenConfig")
public class MRankDarenConfig extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer darenLevel;
	private Integer needNum;
	private Long reward;
	private Long createTime;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getDarenLevel() {
		return darenLevel;
	}
	public void setDarenLevel(Integer darenLevel) {
		this.darenLevel = darenLevel;
	}
	public Integer getNeedNum() {
		return needNum;
	}
	public void setNeedNum(Integer needNum) {
		this.needNum = needNum;
	}
	public Long getReward() {
		return reward;
	}
	public void setReward(Long reward) {
		this.reward = reward;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
}

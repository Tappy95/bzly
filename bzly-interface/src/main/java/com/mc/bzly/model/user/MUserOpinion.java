package com.mc.bzly.model.user;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;

@Alias("MUserOpinion")
public class MUserOpinion extends BaseModel implements Serializable{
	private Integer id;
	private Integer accountId;//用户id
	private String vipName;//vip等级
	private Long experience;//用户成长值
	private Integer opinionType;//意见类型1会员相关2积分提现3信息错误4 友好意见5其他
	private String opinionContent;//意见内容
	private String contentImg;//图片
	private String email;//邮箱
	private Integer state;//1待处理2已处理3已反馈4作废
	private String remarks;//备注
	private Long createrTime;//创建时间
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getAccountId() {
		return accountId;
	}
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}
	public String getVipName() {
		return vipName;
	}
	public void setVipName(String vipName) {
		this.vipName = vipName;
	}
	public Long getExperience() {
		return experience;
	}
	public void setExperience(Long experience) {
		this.experience = experience;
	}
	public Integer getOpinionType() {
		return opinionType;
	}
	public void setOpinionType(Integer opinionType) {
		this.opinionType = opinionType;
	}
	public String getOpinionContent() {
		return opinionContent;
	}
	public void setOpinionContent(String opinionContent) {
		this.opinionContent = opinionContent;
	}
	public String getContentImg() {
		return contentImg;
	}
	public void setContentImg(String contentImg) {
		this.contentImg = contentImg;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
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
	
	
}

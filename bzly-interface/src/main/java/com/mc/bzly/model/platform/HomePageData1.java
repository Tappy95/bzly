package com.mc.bzly.model.platform;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

@Alias("HomePageData1")
public class HomePageData1 implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long regUser; // 新增注册量
	private Long signCount; // 签到人数
	private Long loginCount; // 登陆人数
	private Long lxdlCount; // 连续签到任务
	
	private Long dtCount;//参与答题人数
	private Long kszUserNum; // 快速赚任务
	private Long qdzUserNum; // 签到赚任务
	
	public Long getRegUser() {
		return regUser;
	}
	public void setRegUser(Long regUser) {
		this.regUser = regUser;
	}
	public Long getLoginCount() {
		return loginCount;
	}
	public void setLoginCount(Long loginCount) {
		this.loginCount = loginCount;
	}
	public Long getLxdlCount() {
		return lxdlCount;
	}
	public void setLxdlCount(Long lxdlCount) {
		this.lxdlCount = lxdlCount;
	}
	public Long getSignCount() {
		return signCount;
	}
	public void setSignCount(Long signCount) {
		this.signCount = signCount;
	}
	public Long getDtCount() {
		return dtCount;
	}
	public void setDtCount(Long dtCount) {
		this.dtCount = dtCount;
	}
	public Long getKszUserNum() {
		return kszUserNum;
	}
	public void setKszUserNum(Long kszUserNum) {
		this.kszUserNum = kszUserNum;
	}
	public Long getQdzUserNum() {
		return qdzUserNum;
	}
	public void setQdzUserNum(Long qdzUserNum) {
		this.qdzUserNum = qdzUserNum;
	}
	

}

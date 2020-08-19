package com.mc.bzly.model.sms;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户账号拉黑
 * @author admin
 *
 */
public class SSmsBlack implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String accountNum;//账号
	private Date createTime;//创建时间
	private Integer limitTime;//限制时长：0-永久
	private Integer blackCount;//拉黑时长
	private String type;//锁定状态：0-拉黑，1-解除
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAccountNum() {
		return accountNum;
	}
	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getLimitTime() {
		return limitTime;
	}
	public void setLimitTime(Integer limitTime) {
		this.limitTime = limitTime;
	}
	public Integer getBlackCount() {
		return blackCount;
	}
	public void setBlackCount(Integer blackCount) {
		this.blackCount = blackCount;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	

}

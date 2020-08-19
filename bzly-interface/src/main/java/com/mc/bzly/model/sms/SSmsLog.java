package com.mc.bzly.model.sms;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

/**
 * 短信验证码记录表实体
 * @author admin
 *
 */
@Alias("SSmsLog")
public class SSmsLog extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;//短信验证码记录表id
	private String accountNum;//发送账号
	private String code;//验证码
	private String ip;//请求ip
	private String ruleNum;//发送规则
	private Integer sendMode;//发送方式
	private Date createTime;//发送时间
	
	private String smsMessageId; // 短信发送id
	private String remark; // 描述
	private Integer status; // 发送状态（1-待确认 2-已确认，发送失败 3-已确认，发送成功）
	
	private Integer isValid;//是否有效1有2没有
	
	private String createDate;
	
	private Integer pageIndex;
	
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public String getRuleNum() {
		return ruleNum;
	}
	public void setRuleNum(String ruleNum) {
		this.ruleNum = ruleNum;
	}
	public Integer getSendMode() {
		return sendMode;
	}
	public void setSendMode(Integer sendMode) {
		this.sendMode = sendMode;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getSmsMessageId() {
		return smsMessageId;
	}
	public void setSmsMessageId(String smsMessageId) {
		this.smsMessageId = smsMessageId;
	}
	public Integer getIsValid() {
		return isValid;
	}
	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}
	public Integer getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
}

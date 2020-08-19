package com.mc.bzly.model.sms;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * 消息发送规则
 * @author admin
 *
 */
@Alias("SSmsSendRule")
public class SSmsSendRule implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;//规则id
	private String scene;//来源1-pc,2-app
	private String sendMode;//发送方式：1-手机号,2邮箱
	private String ruleNum;//短型类型编号
	private String type;//短信类型
	private Integer limitSendNumber;//限制条数
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getScene() {
		return scene;
	}
	public void setScene(String scene) {
		this.scene = scene;
	}
	public String getSendMode() {
		return sendMode;
	}
	public void setSendMode(String sendMode) {
		this.sendMode = sendMode;
	}
	
	public String getRuleNum() {
		return ruleNum;
	}
	public void setRuleNum(String ruleNum) {
		this.ruleNum = ruleNum;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getLimitSendNumber() {
		return limitSendNumber;
	}
	public void setLimitSendNumber(Integer limitSendNumber) {
		this.limitSendNumber = limitSendNumber;
	}
	
	
	

}

package com.mc.bzly.model.platform;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;

@Alias("PDictionary")
public class PDictionary extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private String dicName; 
	
	private String dicRemark; 
	
	private String dicValue; 
	
	private String modifuUserId; 
	
	private Long modifyTime; 
	
	private Integer valueType; 
	
	private Integer status; 
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDicName() {
		return dicName;
	}
	public void setDicName(String dicName) {
		this.dicName = dicName;
	}
	public String getDicRemark() {
		return dicRemark;
	}
	public void setDicRemark(String dicRemark) {
		this.dicRemark = dicRemark;
	}
	public String getDicValue() {
		return dicValue;
	}
	public void setDicValue(String dicValue) {
		this.dicValue = dicValue;
	}
	public String getModifuUserId() {
		return modifuUserId;
	}
	public void setModifuUserId(String modifuUserId) {
		this.modifuUserId = modifuUserId;
	}
	public Long getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Long modifyTime) {
		this.modifyTime = modifyTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getValueType() {
		return valueType;
	}
	public void setValueType(Integer valueType) {
		this.valueType = valueType;
	}
	
}

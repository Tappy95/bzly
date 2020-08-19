package com.mc.bzly.model.passbook;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;

@Alias("MPassbook")
public class MPassbook extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private String passbookName; 
	
	private Integer passbookType; 
	
	private Integer useDay; 
	
	private Integer passbookValue; 

	private Integer regSend; 
	
	private String taskTypeName; 
	
	private Long createTime; 
	
	private String remark; 

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPassbookName() {
		return passbookName;
	}

	public void setPassbookName(String passbookName) {
		this.passbookName = passbookName;
	}

	public Integer getPassbookType() {
		return passbookType;
	}

	public void setPassbookType(Integer passbookType) {
		this.passbookType = passbookType;
	}

	public Integer getUseDay() {
		return useDay;
	}

	public void setUseDay(Integer useDay) {
		this.useDay = useDay;
	}

	public Integer getPassbookValue() {
		return passbookValue;
	}

	public void setPassbookValue(Integer passbookValue) {
		this.passbookValue = passbookValue;
	}

	public String getTaskTypeName() {
		return taskTypeName;
	}

	public void setTaskTypeName(String taskTypeName) {
		this.taskTypeName = taskTypeName;
	}

	public Integer getRegSend() {
		return regSend;
	}

	public void setRegSend(Integer regSend) {
		this.regSend = regSend;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	

}

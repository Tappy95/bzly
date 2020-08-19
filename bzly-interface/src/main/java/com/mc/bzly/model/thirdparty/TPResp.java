package com.mc.bzly.model.thirdparty;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;

@Alias("TPResp")
public class TPResp extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer interfaceId;
	private String field;
	private String key;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getInterfaceId() {
		return interfaceId;
	}
	public void setInterfaceId(Integer interfaceId) {
		this.interfaceId = interfaceId;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
}

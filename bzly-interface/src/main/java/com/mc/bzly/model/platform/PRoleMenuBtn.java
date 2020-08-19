package com.mc.bzly.model.platform;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

@Alias("PRoleMenuBtn")
public class PRoleMenuBtn implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private Integer roleId; 
	
	private Integer type; 
	
	private Integer refId; 
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getRefId() {
		return refId;
	}
	public void setRefId(Integer refId) {
		this.refId = refId;
	}
	public PRoleMenuBtn() {
	}
	public PRoleMenuBtn(Integer id, Integer roleId, Integer type, Integer refId) {
		super();
		this.id = id;
		this.roleId = roleId;
		this.type = type;
		this.refId = refId;
	}
}

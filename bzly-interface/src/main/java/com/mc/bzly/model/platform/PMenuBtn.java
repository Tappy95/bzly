package com.mc.bzly.model.platform;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;

@Alias("PMenuBtn")
public class PMenuBtn extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;

	private Integer menuId;

	private String btnName;

	private String btnCode;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	public String getBtnName() {
		return btnName;
	}

	public void setBtnName(String btnName) {
		this.btnName = btnName;
	}

	public String getBtnCode() {
		return btnCode;
	}

	public void setBtnCode(String btnCode) {
		this.btnCode = btnCode;
	}
	public PMenuBtn() {
		// TODO Auto-generated constructor stub
	}

	public PMenuBtn(Integer menuId) {
		super();
		this.menuId = menuId;
	}
}

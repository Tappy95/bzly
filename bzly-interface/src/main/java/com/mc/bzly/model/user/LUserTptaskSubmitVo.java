package com.mc.bzly.model.user;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

/**
 * 用户提交材料
 */
@Alias("LUserTptaskSubmitVo")
public class LUserTptaskSubmitVo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer submitId;//提交证明控件id
	private String content;//提交内容
	
	public Integer getSubmitId() {
		return submitId;
	}
	public void setSubmitId(Integer submitId) {
		this.submitId = submitId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
}

package com.mc.bzly.model.user;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;
/**
 * 记录用户是否第一次进去app
 * @author admin
 *
 */
@Alias("LUserFirstLog")
public class LUserFirstLog extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private String userId;//用户id
	
	private Integer isOne;//是否第一次进入app1是2不是

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getIsOne() {
		return isOne;
	}

	public void setIsOne(Integer isOne) {
		this.isOne = isOne;
	}

	
	
	

}

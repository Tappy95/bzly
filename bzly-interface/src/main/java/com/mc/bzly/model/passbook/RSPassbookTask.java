package com.mc.bzly.model.passbook;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

@Alias("RSPassbookTask")
public class RSPassbookTask implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private Integer passbookId;
	
	private Integer taskTypeId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPassbookId() {
		return passbookId;
	}

	public void setPassbookId(Integer passbookId) {
		this.passbookId = passbookId;
	}

	public Integer getTaskTypeId() {
		return taskTypeId;
	}

	public void setTaskTypeId(Integer taskTypeId) {
		this.taskTypeId = taskTypeId;
	}
	
}

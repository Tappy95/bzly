package com.mc.bzly.model.task;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.type.Alias;

@Alias("MTaskType")
public class MTaskType implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String typeName;
	private String shortName;
	
	private List<Map<String,Object>> tasks;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public List<Map<String, Object>> getTasks() {
		return tasks;
	}
	public void setTasks(List<Map<String, Object>> tasks) {
		this.tasks = tasks;
	}
	
}

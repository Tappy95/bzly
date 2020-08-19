package com.mc.bzly.model.jms;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;

@Alias("JmsNewsLog")
public class JmsNewsLog extends BaseModel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	 
	private Integer id;
	private String jmsWrapper; 
	private String jmsDestination; 
	private String jmsException; 
	private Integer state; 
	private Long createrTime;
	
	private Integer pageIndex;
	 
	public JmsNewsLog() {
		super();
	}
	public JmsNewsLog(String jmsWrapper, String jmsDestination, Integer state, Long createrTime) {
		super();
		this.jmsWrapper = jmsWrapper;
		this.jmsDestination = jmsDestination;
		this.state = state;
		this.createrTime = createrTime;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getJmsWrapper() {
		return jmsWrapper;
	}
	public void setJmsWrapper(String jmsWrapper) {
		this.jmsWrapper = jmsWrapper;
	}
	public String getJmsDestination() {
		return jmsDestination;
	}
	public void setJmsDestination(String jmsDestination) {
		this.jmsDestination = jmsDestination;
	}
	public String getJmsException() {
		return jmsException;
	}
	public void setJmsException(String jmsException) {
		this.jmsException = jmsException;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Long getCreaterTime() {
		return createrTime;
	}
	public void setCreaterTime(Long createrTime) {
		this.createrTime = createrTime;
	}
	public Integer getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
	
}

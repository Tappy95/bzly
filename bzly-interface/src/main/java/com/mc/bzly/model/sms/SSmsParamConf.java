package com.mc.bzly.model.sms;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * 短信发送条数配置
 * @author admin
 *
 */
@Alias("SSmsParamConf")
public class SSmsParamConf implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;//主键
	private String name;//配置项名称
	private Integer maxValue;//限制最大值
    private String memo;//备注
    
    private Integer pageSize;
	
	private Integer pageNum;
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getMaxValue() {
		return maxValue;
	}
	public void setMaxValue(Integer maxValue) {
		this.maxValue = maxValue;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getPageNum() {
		return pageNum;
	}
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
    
    
}

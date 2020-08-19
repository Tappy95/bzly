package com.mc.bzly.model.thirdparty;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;

/**
 * @date 2019年3月15日
 * TODO 接口参数配置
 */
@Alias("TPParams")
public class TPParams extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer interfaceId; 
	private String interfaceName; 
	private String name; 
	private String code; 
	private Integer type; 
	private String value; 
	
	private Integer isEncrypt; 
	private Integer encryptType; 
	private String encryptSeparator; 
	
	private Integer isNeed; 
	private Integer encryptNeed; 
	private Integer orderId; 
	
	private Integer isReplace; 
	private String replaceCode; 
	
	private Integer infoEncryptNeed; 
	
	private Long createTime; 
	
	public String getReplaceCode() {
		return replaceCode;
	}
	public void setReplaceCode(String replaceCode) {
		this.replaceCode = replaceCode;
	}
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
	public String getInterfaceName() {
		return interfaceName;
	}
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Integer getIsEncrypt() {
		return isEncrypt;
	}
	public void setIsEncrypt(Integer isEncrypt) {
		this.isEncrypt = isEncrypt;
	}
	public Integer getEncryptType() {
		return encryptType;
	}
	public void setEncryptType(Integer encryptType) {
		this.encryptType = encryptType;
	}
	public String getEncryptSeparator() {
		return encryptSeparator;
	}
	public void setEncryptSeparator(String encryptSeparator) {
		this.encryptSeparator = encryptSeparator;
	}
	public Integer getIsNeed() {
		return isNeed;
	}
	public void setIsNeed(Integer isNeed) {
		this.isNeed = isNeed;
	}
	public Integer getEncryptNeed() {
		return encryptNeed;
	}
	public void setEncryptNeed(Integer encryptNeed) {
		this.encryptNeed = encryptNeed;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public Integer getIsReplace() {
		return isReplace;
	}
	public void setIsReplace(Integer isReplace) {
		this.isReplace = isReplace;
	}
	public Integer getInfoEncryptNeed() {
		return infoEncryptNeed;
	}
	public void setInfoEncryptNeed(Integer infoEncryptNeed) {
		this.infoEncryptNeed = infoEncryptNeed;
	}
}

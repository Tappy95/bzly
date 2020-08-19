package com.mc.bzly.model.thirdparty;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;

@Alias("MChannelInfo")
public class MChannelInfo extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String channelCode; // 渠道标识
	private Integer channelId; // 渠道id
	private String channelName; // 渠道名称
	private String channelPosition; // 渠道推广位置
	private Integer channelPushType; // 推广方式（1-banner+链接 2-文字+链接）
	private String content; // banner url/投放文字
	private String downloadUrl; // 下载链接
	private Long createTime; // 创建时间
	private Integer status; // 状态（1-启用 2-停用）
	private Integer openAli;//是否开启支付宝支付1开启2关闭
	private Integer openWx;//是否开启微信支付1开启2关闭
	private String wxAppId;//微信支付appId
	private String mchId;//微信支付mchId
	private String apiKey;//微信支付apiKey
	private String aliAppId;//支付宝appId
	private String aliPrivateKey;//支付宝私钥
	private String aliPublicKey;//支付宝公钥
	private Integer webType;//平台类型1app,2小程序
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getChannelCode() {
		return channelCode;
	}
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	public Integer getChannelId() {
		return channelId;
	}
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}
	public String getChannelPosition() {
		return channelPosition;
	}
	public void setChannelPosition(String channelPosition) {
		this.channelPosition = channelPosition;
	}
	public Integer getChannelPushType() {
		return channelPushType;
	}
	public void setChannelPushType(Integer channelPushType) {
		this.channelPushType = channelPushType;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDownloadUrl() {
		return downloadUrl;
	}
	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getWxAppId() {
		return wxAppId;
	}
	public void setWxAppId(String wxAppId) {
		this.wxAppId = wxAppId;
	}
	public String getMchId() {
		return mchId;
	}
	public void setMchId(String mchId) {
		this.mchId = mchId;
	}
	public String getApiKey() {
		return apiKey;
	}
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	public Integer getOpenAli() {
		return openAli;
	}
	public void setOpenAli(Integer openAli) {
		this.openAli = openAli;
	}
	public Integer getOpenWx() {
		return openWx;
	}
	public void setOpenWx(Integer openWx) {
		this.openWx = openWx;
	}
	public String getAliAppId() {
		return aliAppId;
	}
	public void setAliAppId(String aliAppId) {
		this.aliAppId = aliAppId;
	}
	public String getAliPrivateKey() {
		return aliPrivateKey;
	}
	public void setAliPrivateKey(String aliPrivateKey) {
		this.aliPrivateKey = aliPrivateKey;
	}
	public String getAliPublicKey() {
		return aliPublicKey;
	}
	public void setAliPublicKey(String aliPublicKey) {
		this.aliPublicKey = aliPublicKey;
	}
	public Integer getWebType() {
		return webType;
	}
	public void setWebType(Integer webType) {
		this.webType = webType;
	}
	
}

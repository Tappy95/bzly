package com.mc.bzly.model.news;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;

@Alias("AppNewsNotice")
public class AppNewsNotice extends BaseModel implements Serializable{
	private static final long serialVersionUID = 1L;
    private Integer id; 
    private Integer noticeType;//公告类型1公告2活动
    private String noticeTitle;//公告标题
    private String noticeContent; //公告内容
    private String imgUrl;//公告图片
    private String linkAddress;//链接地址
    private Integer ranges;//通知范围1全部
    private Long releaserTime;//发布时间
    private Long cancelTime;//取消时间
    private Long createrTime; //创建时间
    private String createrMobile;//创建人电话
    private Integer isRelease; //定时器状态发布1.发布2.未发布3.已失效
    private Integer isPublish;//手动处理是否发布，1是2否
    private Integer appType;//app类型：1宝猪2中青赚点
    private Integer isRead;
    
    private String userId;
    
    
    private String createrName;
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNoticeTitle() {
		return noticeTitle;
	}
	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle;
	}
	public String getNoticeContent() {
		return noticeContent;
	}
	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}
	
	public Long getCreaterTime() {
		return createrTime;
	}
	public void setCreaterTime(Long createrTime) {
		this.createrTime = createrTime;
	}
	public Integer getIsRelease() {
		return isRelease;
	}
	public void setIsRelease(Integer isRelease) {
		this.isRelease = isRelease;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getLinkAddress() {
		return linkAddress;
	}
	public void setLinkAddress(String linkAddress) {
		this.linkAddress = linkAddress;
	}
	
	public Integer getRanges() {
		return ranges;
	}
	public void setRanges(Integer ranges) {
		this.ranges = ranges;
	}
	public Long getReleaserTime() {
		return releaserTime;
	}
	public void setReleaserTime(Long releaserTime) {
		this.releaserTime = releaserTime;
	}
	public Long getCancelTime() {
		return cancelTime;
	}
	public void setCancelTime(Long cancelTime) {
		this.cancelTime = cancelTime;
	}
	public Integer getNoticeType() {
		return noticeType;
	}
	public void setNoticeType(Integer noticeType) {
		this.noticeType = noticeType;
	}
	public String getCreaterMobile() {
		return createrMobile;
	}
	public void setCreaterMobile(String createrMobile) {
		this.createrMobile = createrMobile;
	}
	public String getCreaterName() {
		return createrName;
	}
	public void setCreaterName(String createrName) {
		this.createrName = createrName;
	}
	public Integer getIsPublish() {
		return isPublish;
	}
	public void setIsPublish(Integer isPublish) {
		this.isPublish = isPublish;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Integer getIsRead() {
		return isRead;
	}
	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}
	public Integer getAppType() {
		return appType;
	}
	public void setAppType(Integer appType) {
		this.appType = appType;
	}
	
}

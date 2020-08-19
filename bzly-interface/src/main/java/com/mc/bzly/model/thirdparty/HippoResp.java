package com.mc.bzly.model.thirdparty;

import java.io.Serializable;
import java.util.List;

public class HippoResp implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private boolean is_ad; // 是否是广告
	private String id; 
	private String title; // 新闻标题 或广告标题
	private String source; // 新闻来源/视频作者
	private String update_time; // 新闻更新时间
	private Long timestamp; // 根据 update_trime 转换成时间戳
	private boolean is_video; // 新闻是否是视频类型
	private List<HippoRespImage> images; // 新闻图片/广告图片
	private String clk_url; // 新闻落地页
	private String cat; // 新闻类目
	private String[] imp_tracking; // 新闻曝光监测/广告曝光监测
	private List<HippoRespAd> ad;
	
	public boolean isIs_ad() {
		return is_ad;
	}
	public void setIs_ad(boolean is_ad) {
		this.is_ad = is_ad;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	public boolean isIs_video() {
		return is_video;
	}
	public void setIs_video(boolean is_video) {
		this.is_video = is_video;
	}
	public List<HippoRespImage> getImages() {
		return images;
	}
	public void setImages(List<HippoRespImage> images) {
		this.images = images;
	}
	public String getClk_url() {
		return clk_url;
	}
	public void setClk_url(String clk_url) {
		this.clk_url = clk_url;
	}
	public String getCat() {
		return cat;
	}
	public void setCat(String cat) {
		this.cat = cat;
	}
	public String[] getImp_tracking() {
		return imp_tracking;
	}
	public void setImp_tracking(String[] imp_tracking) {
		this.imp_tracking = imp_tracking;
	}
	public List<HippoRespAd> getAd() {
		return ad;
	}
	public void setAd(List<HippoRespAd> ad) {
		this.ad = ad;
	}
	
}

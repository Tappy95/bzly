package com.mc.bzly.model.thirdparty;

import java.io.Serializable;
import java.util.List;

public class HippoRespNativead implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String title;
	
	private String desc;
	
	private List<HippoRespImage> img; // 大图
	
	private List<HippoRespIcon> icon; // 小图
	
	private String ldp;
	
	private String deeplink;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public List<HippoRespImage> getImg() {
		return img;
	}

	public void setImg(List<HippoRespImage> img) {
		this.img = img;
	}

	public List<HippoRespIcon> getIcon() {
		return icon;
	}

	public void setIcon(List<HippoRespIcon> icon) {
		this.icon = icon;
	}

	public String getLdp() {
		return ldp;
	}

	public void setLdp(String ldp) {
		this.ldp = ldp;
	}

	public String getDeeplink() {
		return deeplink;
	}

	public void setDeeplink(String deeplink) {
		this.deeplink = deeplink;
	}
}

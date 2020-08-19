package com.mc.bzly.model.thirdparty;

import java.io.Serializable;

public class HippoRespImage implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String url; // 图片地址
	
	private Integer w; // 图片宽度

	private Integer h; // 图片高度

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getW() {
		return w;
	}

	public void setW(Integer w) {
		this.w = w;
	}

	public Integer getH() {
		return h;
	}

	public void setH(Integer h) {
		this.h = h;
	}
	
}

package com.mc.bzly.model.thirdparty;

import java.io.Serializable;

public class CashtoutiaoRespCover implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer imgheight;
	
	private Integer imgwidth;
	
	private String src;

	public Integer getImgheight() {
		return imgheight;
	}

	public void setImgheight(Integer imgheight) {
		this.imgheight = imgheight;
	}

	public Integer getImgwidth() {
		return imgwidth;
	}

	public void setImgwidth(Integer imgwidth) {
		this.imgwidth = imgwidth;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}
}

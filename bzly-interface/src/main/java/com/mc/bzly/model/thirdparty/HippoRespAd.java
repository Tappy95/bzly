package com.mc.bzly.model.thirdparty;

import java.io.Serializable;
import java.util.List;

public class HippoRespAd implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String cid; // 创意id
	private List<HippoRespNativead> nativead; // 信息流广告素材
	private String[] imp_tracking;
	private String[] clk_tracking;
	private Integer interact_type; // 0-打开网页  1-下载应用 不填写默认为打开网页
	private String[] ds_t;
	private String[] dc_t;
	private String[] is_t;
	private String[] ic_t;
	private String[] op_t;
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public List<HippoRespNativead> getNativead() {
		return nativead;
	}
	public void setNativead(List<HippoRespNativead> nativead) {
		this.nativead = nativead;
	}
	public String[] getImp_tracking() {
		return imp_tracking;
	}
	public void setImp_tracking(String[] imp_tracking) {
		this.imp_tracking = imp_tracking;
	}
	public String[] getClk_tracking() {
		return clk_tracking;
	}
	public void setClk_tracking(String[] clk_tracking) {
		this.clk_tracking = clk_tracking;
	}
	public Integer getInteract_type() {
		return interact_type;
	}
	public void setInteract_type(Integer interact_type) {
		this.interact_type = interact_type;
	}
	public String[] getDs_t() {
		return ds_t;
	}
	public void setDs_t(String[] ds_t) {
		this.ds_t = ds_t;
	}
	public String[] getDc_t() {
		return dc_t;
	}
	public void setDc_t(String[] dc_t) {
		this.dc_t = dc_t;
	}
	public String[] getIs_t() {
		return is_t;
	}
	public void setIs_t(String[] is_t) {
		this.is_t = is_t;
	}
	public String[] getIc_t() {
		return ic_t;
	}
	public void setIc_t(String[] ic_t) {
		this.ic_t = ic_t;
	}
	public String[] getOp_t() {
		return op_t;
	}
	public void setOp_t(String[] op_t) {
		this.op_t = op_t;
	}
}

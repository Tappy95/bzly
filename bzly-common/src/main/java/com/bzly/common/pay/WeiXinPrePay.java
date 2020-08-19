package com.bzly.common.pay;

import java.io.Serializable;

public class WeiXinPrePay implements Serializable {

    private static final long serialVersionUID = 927873097572108345L;

    private String appid;
    private String partnerid;
    private String timestamp;
    private String noncestr;
    private String prepayid;
    private String bzPackage;
    private String sign;
    private String outTradeNo;

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

	public String getBzPackage() {
		return bzPackage;
	}

	public void setBzPackage(String bzPackage) {
		this.bzPackage = bzPackage;
	}

    
}
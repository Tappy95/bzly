package com.mc.bzly.util;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.bzly.common.utils.MD5Util;

public class SignUtil {
	
	public static String getMD5Sign(List<String> encrptyList,String separator){
		StringBuffer sb = new StringBuffer();
		boolean flag = true;
		if(StringUtils.isEmpty(separator)){
			separator = "";
			flag = false;
		}
		for (String encrpty : encrptyList) {
			sb = sb.append(encrpty).append(separator);
		}
		if(flag){
			return sb.toString().substring(0,sb.length()-1);
		}
		return MD5Util.getMd5(sb.toString());
	}
}

package com.mc.bzly.base;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mc.bzly.model.platform.PAdmin;
import com.mc.bzly.model.user.MUserInfo;
import com.mc.bzly.service.redis.RedisService;
import com.mc.bzly.service.user.MUserInfoService;

import net.sf.json.JSONObject;

public class BaseController {
	protected static Logger logger = LoggerFactory.getLogger(BaseController.class);
	public Map<String, String[]> paramMap = new HashMap<String, String[]>();
	
	@Reference(version=WebConfig.dubboServiceVersion,interfaceClass=RedisService.class,check=false,timeout=3000)
	protected RedisService redisService;
	
	@Reference(version=WebConfig.dubboServiceVersion,interfaceClass=MUserInfoService.class,check=false,timeout=3000)
	protected MUserInfoService mUserInfoService;
	
	private static final Integer DEFAULT_EXPIRE = 60 * 60 * 24 * 30;
	
	//protected Map<String, String> respMap = new HashMap<>();

	public BaseController() {
	}
	
	public String  getAdminId(String token) throws Exception{
		String tokenValue = redisService.getString(token);
		PAdmin pAdmin = (PAdmin)redisService.getObject(tokenValue);
		return pAdmin.getAdminId();
	}
	
	public PAdmin  getAdmin(String token) throws Exception{
		String tokenValue = redisService.getString(token);
		PAdmin pAdmin = (PAdmin)redisService.getObject(tokenValue);
		return pAdmin;
	}

	public String getUserId(String token) throws Exception{
		if(StringUtils.isEmpty(token)){
			throw new Exception("token为空");
		}
		Map<String,Object> map = null;
		String userId=null;
		try{
			map = (Map)redisService.getObject(token);
			if(map == null){
				Map<String, Object> param = new HashMap<>();
				param.put("token", token);
				MUserInfo mUserInfo = mUserInfoService.queryUserBaseInfo(param);
				if(mUserInfo == null){
					throw new Exception("token已失效");
				}else{
					if(mUserInfo.getStatus().intValue() == 2){
						return "userfreeze";
					}
					userId=mUserInfo.getUserId();
					map=new HashMap<>();
					map.put("userId", userId);
					redisService.put(token, map, DEFAULT_EXPIRE);
				}
			}else {
				userId=map.get("userId").toString();
				Map<String, Object> param = new HashMap<>();
				param.put("userId", userId);
				MUserInfo mUserInfo = mUserInfoService.queryUserBaseInfo(param);
				if(mUserInfo.getStatus().intValue() == 2){
					return "userfreeze";
				}
			}
		}catch (Exception e) {
			throw new Exception("token已失效");
		}
		return userId;
	}

	public Integer getAccountId(String token) throws Exception{
		if(StringUtils.isEmpty(token)){
			throw new Exception("token为空");
		}
		MUserInfo mUserInfo = null;
		try{
			mUserInfo = (MUserInfo)redisService.getObject(token);
			if(mUserInfo == null){
				Map<String, Object> param = new HashMap<>();
				param.put("token", token);
				mUserInfo = mUserInfoService.queryUserBaseInfo(param);
				if(mUserInfo == null){
					throw new Exception("token已失效");
				}else{
					redisService.put(token, mUserInfo, DEFAULT_EXPIRE);
				}
			}
		}catch (Exception e) {
			throw new Exception("token已失效");
		}
		return mUserInfo.getAccountId();
	}
	
	protected void initResponseMap(HttpServletRequest request, Map<String, String> respMap) {
//		if(this.respMap.size()>0) {
//			this.respMap.clear();
//		}
		String requestUrl = "";
		Map<String, String[]> temp = request.getParameterMap();
		if (null != temp && temp.size() > 0) {
			for (Map.Entry<String, String[]> item : temp.entrySet()) {
				String key = item.getKey();
				String[] values = item.getValue();
				String value = "";
				for (String string : values) {
					value += string;
				}
				try {
					value = URLDecoder.decode(value, "utf-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				respMap.put(key, value.trim());
				if (!requestUrl.isEmpty()) {
					requestUrl += "&";
				}
				requestUrl += key + "=" + value;
			}
		}
		if(respMap.containsKey("appkey")) {
			respMap.put("app_key", respMap.get("appkey"));
			respMap.remove("appkey");
		}
		logger.info("got request params is:" + requestUrl);
	}

	protected void initResponseMapNew(HttpServletRequest request, Map<String, String> respMap) {
//		if(this.respMap.size()>0) {
//			this.respMap.clear();
//		}
		String requestUrl = "";
		Map<String, String[]> temp = request.getParameterMap();
		if (null != temp && temp.size() > 0) {
			for (Map.Entry<String, String[]> item : temp.entrySet()) {
				String key = item.getKey();
				String[] values = item.getValue();
				String value = "";
				for (String string : values) {
					value += string;
				}
				try {
					value = value.replaceAll("%(?![0-9a-fA-F]{2})","%25");
					value = URLDecoder.decode(value, "utf-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				respMap.put(key, value.trim());
				if (!requestUrl.isEmpty()) {
					requestUrl += "&";
				}
				requestUrl += key + "=" + value;
			}
		}
		if(respMap.containsKey("appkey")) {
			respMap.put("app_key", respMap.get("appkey"));
			respMap.remove("appkey");
		}
		logger.info("got request params is:" + requestUrl);
	}

	protected void outStrJSONWithResult(HttpServletResponse response, Result result, Map<String, String> respMap) {
		Map<String, Object> map = new HashMap<>();
		if (result.getNeedChange()) {
			map = result.getChangeMap();
		} else {
			map.put("statusCode", result.getStatusCode());
			map.put("message", result.getMessage());
			map.put("data", result.getData());
			if (respMap.containsKey("token")) {
				map.put("token", respMap.get("token"));
			}
		}

		String content = "";
		String header = "application/json;charset=UTF-8";
		switch (result.getRespType()) {
		case NORMAL:
			header = "text/html;charset=UTF-8";
			if (result.getNeedChange()) {
				for (Map.Entry<String, Object> item : map.entrySet()) {
					Object value = item.getValue();

					if (!content.isEmpty()) {
						content += ",";
					}
					if (value == null || (value + "").isEmpty()) {
						content += item.getKey();
						continue;
					}
					content += item.getKey() + ":" + item.getValue();
				}
			} else {
				content =JSONObject.fromObject(map).toString();
			}

			break;
		case JSON:
			content = JSONObject.fromObject(map).toString();
			break;
		case XML:
			break;
		case SECURITY:
			break;

		default:
			content = JSONObject.fromObject(map).toString();
			break;
		}
		outStr(response, content, header);
	}

	protected void outStr(HttpServletResponse response, String content, String header) {
		//logger.info("response result=>" + content + "----- hasHeader:" + header);

		OutputStream outputStream = null;
		try {
			outputStream = response.getOutputStream();
			if (header != null) {
				response.setHeader("content-type", header);
			} else {
				response.setHeader("content-type", "application/json;charset=UTF-8");
			}
			byte[] dataByteArr = content.getBytes("UTF-8");
			outputStream.write(dataByteArr);
		} catch (Exception e) {
			logger.error("outStr error", e);
		} finally {
			try {
				if (null != outputStream) {
					outputStream.flush();
					outputStream.close();
				}
			} catch (Exception e2) {
				logger.error("outStr close stream error", e2);
			}
		}
	}
	
	public boolean checkCode(String codeKey){
		String codeValue = redisService.getString(codeKey);
		redisService.delete(codeKey);
		if(!"1".equals(codeValue)){
			return false;
		}
		return true;
	}

	public void test(HashMap maps) {
		logger.info("test values {}", maps);
	}
}

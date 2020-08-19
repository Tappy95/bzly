package com.mc.bzly.controller.thirdparty;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bzly.common.utils.MD5Util;
import com.mc.bzly.annotation.NeedAuth;
import com.mc.bzly.annotation.NeedToken;
import com.mc.bzly.base.BaseController;
import com.mc.bzly.base.RespCodeState;
import com.mc.bzly.base.Result;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.model.thirdparty.TPCompany;
import com.mc.bzly.service.thirdparty.TPCompanyService;

@RestController
@RequestMapping("/tpCompany")
public class TPCompanyController extends BaseController {


	// pc蛋蛋
	@Value("${pcdd.pid}")
	private String pcddPID;
	@Value("${pcdd.appkey}")
	private String pcddAppkey;
	@Value("${pcdd.baseUrl}")
	private String pcddBaseUrl;
	
	// 闲玩游戏
	@Value("${xw.appid}")
	private String xwAppid;
	@Value("${xw.appsecret}")
	private String xwAppsecret;
	@Value("${xw.baseUrl}")
	private String xwBaseUrl;
	
	@Reference(version = WebConfig.dubboServiceVersion, interfaceClass = TPCompanyService.class, check = false, timeout=10000)
	private TPCompanyService tpCompanyService;

	@NeedAuth
	@RequestMapping(value = "list", method = RequestMethod.GET)
	void page(HttpServletRequest request, HttpServletResponse response, TPCompany tpCompany) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(tpCompanyService.queryList(tpCompany));
			result.setSuccess(true);
			result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
			result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
		}
		outStrJSONWithResult(response, result, respMap);
	}

	@NeedAuth
	@RequestMapping(value = "add", method = RequestMethod.POST)
	void add(HttpServletRequest request, HttpServletResponse response, TPCompany tpCompany) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(tpCompanyService.add(tpCompany));
			result.setSuccess(true);
			result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
			result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
		}

		outStrJSONWithResult(response, result, respMap);
	}

	@NeedAuth
	@RequestMapping(value = "modify", method = RequestMethod.POST)
	void modify(HttpServletRequest request, HttpServletResponse response, TPCompany tpCompany) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(tpCompanyService.modify(tpCompany));
			result.setSuccess(true);
			result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
			result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
		}
		outStrJSONWithResult(response, result, respMap);
	}

	@NeedAuth
	@RequestMapping(value = "remove", method = RequestMethod.GET)
	void remove(HttpServletRequest request, HttpServletResponse response, Integer id) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(tpCompanyService.remove(id));
			result.setSuccess(true);
			result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
			result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
		}

		outStrJSONWithResult(response, result, respMap);
	}

	@NeedAuth
	@RequestMapping(value = "queryOne", method = RequestMethod.GET)
	void queryOne(HttpServletRequest request, HttpServletResponse response, Integer id) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(tpCompanyService.queryOne(id));
			result.setSuccess(true);
			result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
			result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
		}
		outStrJSONWithResult(response, result, respMap);
	}

	@NeedAuth
	@RequestMapping(value = "queryOpt", method = RequestMethod.GET)
	void queryOpt(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(tpCompanyService.queryOpt());
			result.setSuccess(true);
			result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
			result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
		}
		outStrJSONWithResult(response, result, respMap);
	}
	
	// @NeedToken
	@RequestMapping(value = "queryFList", method = RequestMethod.GET)
	void queryList(HttpServletRequest request, HttpServletResponse response,TPCompany tpCompany) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			tpCompany.setH5Type(2);
			tpCompany.setStatus(1);
			result.setData(tpCompanyService.queryList(tpCompany));
			result.setSuccess(true);
			result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
			result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
		}
		outStrJSONWithResult(response, result, respMap);
	}
	
	@NeedToken
	@RequestMapping(value = "getPCDDUrl", method = RequestMethod.GET)
	void getPCDDUrl(HttpServletRequest request, HttpServletResponse response,String token,String shortName) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			String userId = getUserId(token);
			String imei = respMap.get("imei");
			String url = "";
			if("PCDD".equals(shortName)){
				String ptype = "2";
				String md5Str = pcddPID+imei+ptype+userId+pcddAppkey;
				url = pcddBaseUrl+"?ptype="+ptype+"&deviceid="+imei+"&pid="+pcddPID+"&userid="+userId+"&keycode="+MD5Util.getMd5(md5Str)+"&newversion=1";
			}else if("XW123".equals(shortName)){
				String ptype = "2";
		    	String keyCode = xwAppid+imei+ptype+userId+xwAppsecret;
		    	url = xwBaseUrl+"ptype="+ptype+"&deviceid="+imei+"&appid="+xwAppid+"&appsign="+userId+"&keycode="+MD5Util.getMd5(keyCode);
			}
			result.setData(url);
			result.setSuccess(true);
			result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
			result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
		}
		outStrJSONWithResult(response, result, respMap);
	}

	@NeedToken
	@RequestMapping(value = "getXWUrl", method = RequestMethod.GET)
	void getXWUrl(HttpServletRequest request, HttpServletResponse response,String token) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			String userId = getUserId(token);
			String imei = respMap.get("imei");
			String ptype = "2";
			String equipmentType = respMap.get("equipmentType"); // 1- 安卓 2-ios
			String url = "";
			if("2".equals(equipmentType)){ // ios链接
				String appid = "4400";
				String appsecret = "kmuj5qs1z26z9jgr";
				ptype = "1";
				String keyCode = appid+imei+ptype+userId+appsecret;
				url = xwBaseUrl+"ptype="+ptype+"&deviceid="+imei+"&appid="+appid+"&appsign="+userId+"&keycode="+MD5Util.getMd5(keyCode);
			}else{ // 安卓链接
				String appid = "4399";
				String appsecret = "siig8gsy5zae1j7z";
				String keyCode = appid+imei+ptype+userId+appsecret;
				url = xwBaseUrl+"ptype="+ptype+"&deviceid="+imei+"&appid="+appid+"&appsign="+userId+"&keycode="+MD5Util.getMd5(keyCode);				
			}
			result.setData(url);
			result.setSuccess(true);
			result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
			result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
		}
		outStrJSONWithResult(response, result, respMap);
	}

}

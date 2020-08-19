package com.mc.bzly.controller.thirdparty;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mc.bzly.annotation.NeedAuth;
import com.mc.bzly.base.BaseController;
import com.mc.bzly.base.RespCodeState;
import com.mc.bzly.base.Result;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.model.thirdparty.TPCallback;
import com.mc.bzly.model.user.MUserInfo;
import com.mc.bzly.service.thirdparty.TPCallbackService;
import com.mc.bzly.service.user.MUserInfoService;

@RestController
@RequestMapping("tpCallback")
public class TPCallbackController extends BaseController {
	
	@Reference(version = WebConfig.dubboServiceVersion, interfaceClass = TPCallbackService.class, check = false, timeout=10000)
	private TPCallbackService tpCallbackService;
	
	@Reference(version = WebConfig.dubboServiceVersion, interfaceClass = MUserInfoService.class, check = false, timeout=10000)
	private MUserInfoService mUserInfoService;
	
	@NeedAuth
	@RequestMapping(value="queryList",method=RequestMethod.GET)
	void queryList(HttpServletRequest request,HttpServletResponse response,TPCallback tpCallback){
		Map<String, String> respMap = new HashMap<>();
		initResponseMap(request, respMap);
		/*String channel = respMap.get("channel");*/
		
		//tpCallback.setChannelCode(respMap.get("channel"));
		tpCallback.setUserRelation(Integer.parseInt(respMap.get("relation")));
		
		Result result = new Result();
		try {
			if(tpCallback.getAccountId()!=null) {
				MUserInfo mUserInfo=mUserInfoService.selectAccountId(tpCallback.getAccountId());
				if(mUserInfo==null) {
					result.setStatusCode(RespCodeState.MOBILE_NOT_EXIST.getStatusCode());
					result.setMessage(RespCodeState.MOBILE_NOT_EXIST.getMessage());
				}else {
					result.setData(tpCallbackService.queryList(tpCallback));
					result.setSuccess(true);
					result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
					result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
				}
			}else {
				result.setData(tpCallbackService.queryList(tpCallback));
				result.setSuccess(true);
				result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
				result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());	
			}
		}catch (Exception e) {
			e.printStackTrace();
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
		}
		outStrJSONWithResult(response, result, respMap);
	}
	/**
	 * 后台获取游戏商户列表
	 * @param request
	 * @param response
	 */
	@NeedAuth
	@RequestMapping(value="selectDownList",method=RequestMethod.GET)
	void selectDownList(HttpServletRequest request,HttpServletResponse response){
		Map<String, String> respMap = new HashMap<>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(tpCallbackService.selectDownList());
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
	@RequestMapping(value="gameTeskExcl",method=RequestMethod.GET)
	void gameTeskExcl(HttpServletRequest request,HttpServletResponse response,TPCallback tpCallback){
		Map<String, String> respMap = new HashMap<>();
		initResponseMap(request, respMap);
		/*String channel = respMap.get("channel");*/
		
		//tpCallback.setChannelCode(respMap.get("channel"));
		tpCallback.setUserRelation(Integer.parseInt(respMap.get("relation")));
		
		Result result = new Result();
		try {
			result.setData(tpCallbackService.selectExcl(tpCallback));
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
}

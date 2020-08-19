package com.mc.bzly.controller.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mc.bzly.interfaces.RespCodeState;
import com.mc.bzly.annotation.NeedAuth;
import com.mc.bzly.annotation.NeedToken;
import com.mc.bzly.base.BaseController;
import com.mc.bzly.base.Result;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.model.user.LUserActivity;
import com.mc.bzly.service.user.LUserActivityService;

@RestController
@RequestMapping("userActivity")
public class LUserActivityController extends BaseController {
	

	@Reference(version = WebConfig.dubboServiceVersion, interfaceClass = LUserActivityService.class, check = false, timeout = 10000)
	private LUserActivityService lUserActivityService;
	
	@NeedToken
	@RequestMapping(value = "myActivity",method = RequestMethod.GET)
	void myActivity(HttpServletRequest request,HttpServletResponse response,String token){
		Map<String, String> respMap = new HashMap<String, String>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try{
			String userId = getUserId(token);
			result.setData(lUserActivityService.myActivity(userId));
			result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
			result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
		}
		outStrJSONWithResult(response, result, respMap);
	}

	/**
	 * @param request
	 * @param response
	 * @param lUserActivity.qualityScore 质量分
	 * @param lUserActivity.activityScore 活跃度
	 * @param lUserActivity.userId 用户id
	 */
	@NeedAuth
	@RequestMapping(value = "modifyScore",method = RequestMethod.POST)
	void modifyScore(HttpServletRequest request,HttpServletResponse response,LUserActivity lUserActivity){
		Map<String, String> respMap = new HashMap<String, String>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try{
			lUserActivityService.modifyScore(lUserActivity);
			result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
			result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
		}
		outStrJSONWithResult(response, result, respMap);
	}

	/**
	 * @param request
	 * @param response
	 * @param userId
	 * @param openActivity
	 */
	@NeedAuth
	@RequestMapping(value = "openActivity",method = RequestMethod.POST)
	void openActivity(HttpServletRequest request,HttpServletResponse response,String userId,Integer openActivity){
		Map<String, String> respMap = new HashMap<String, String>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try{
			lUserActivityService.openActivity(userId,openActivity);
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

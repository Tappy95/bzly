package com.mc.bzly.controller.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mc.bzly.annotation.NeedAuth;
import com.mc.bzly.annotation.NeedToken;
import com.mc.bzly.base.BaseController;
import com.mc.bzly.base.RespCodeState;
import com.mc.bzly.base.Result;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.model.user.LUserTptask;
import com.mc.bzly.service.user.LUserTptaskService;

@RestController
@RequestMapping("/userTptask")
public class LUserTptaskController extends BaseController {
	
	@Reference(version=WebConfig.dubboServiceVersion,interfaceClass=LUserTptaskService.class,check=false,timeout = 10000)
	private LUserTptaskService lUserTptaskService;
	
	/**
	 * @param request
	 * @param response
	 * @param lUserTptask
	 */
	@NeedToken
	@RequestMapping(value = "add", method = RequestMethod.POST)
	void add(HttpServletRequest request, HttpServletResponse response, LUserTptask lUserTptask) {
		Map<String, String> respMap = new HashMap<>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try {
			lUserTptask.setUserId(getUserId(respMap.get("token")));
			lUserTptask.setAccountId(getAccountId(respMap.get("token")));
			result = lUserTptaskService.add(lUserTptask);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
		}
		outStrJSONWithResult(response, result, respMap);
	}

	@NeedToken
	@RequestMapping(value = "addYY", method = RequestMethod.POST)
	void addYY(HttpServletRequest request, HttpServletResponse response, LUserTptask lUserTptask) {
		Map<String, String> respMap = new HashMap<>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try {
			lUserTptask.setUserId(getUserId(respMap.get("token")));
			lUserTptask.setAccountId(getAccountId(respMap.get("token")));
			result = lUserTptaskService.addYY(lUserTptask);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
		}
		outStrJSONWithResult(response, result, respMap);
	}

	@NeedToken
	@RequestMapping(value = "queryInfo", method = RequestMethod.GET)
	void info(HttpServletRequest request, HttpServletResponse response,String token,Integer taskId) {
		Map<String, String> respMap = new HashMap<>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try {
			String userId = getUserId(token);
			result = lUserTptaskService.queryInfo(taskId,userId);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
		}
		outStrJSONWithResult(response, result, respMap);
	}

	@NeedAuth
	@RequestMapping(value = "sendData", method = RequestMethod.POST)
	void sendData(HttpServletRequest request, HttpServletResponse response,Integer id) {
		Map<String, String> respMap = new HashMap<>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result = lUserTptaskService.sendData(id);
			result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
			result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
		} catch (Exception e) {
			e.printStackTrace();
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
		}
		outStrJSONWithResult(response, result, respMap);
	}
	
	@NeedToken
	@RequestMapping(value = "queryFlist", method = RequestMethod.GET)
	void queryFlist(HttpServletRequest request, HttpServletResponse response,LUserTptask lUserTptask) {
		Map<String, String> respMap = new HashMap<>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try {
			lUserTptask.setUserId(getUserId(respMap.get("token")));
			result.setData(lUserTptaskService.queryFlist(lUserTptask));
			result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
			result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
		} catch (Exception e) {
			e.printStackTrace();
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
		}
		outStrJSONWithResult(response, result, respMap);
	}
	
	@NeedToken
	@RequestMapping(value = "givein", method = RequestMethod.GET)
	void givein(HttpServletRequest request, HttpServletResponse response,Integer id,String remark) {
		Map<String, String> respMap = new HashMap<>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try{
			result = lUserTptaskService.givein(id,remark);	
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
		}
		outStrJSONWithResult(response, result, respMap);
	}
}

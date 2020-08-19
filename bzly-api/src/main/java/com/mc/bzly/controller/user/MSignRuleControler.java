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
import com.mc.bzly.model.user.MSignRule;
import com.mc.bzly.service.user.MSignRuleService;

@RestController
@RequestMapping("/mSignRule")
public class MSignRuleControler extends BaseController {

	@Reference(version=WebConfig.dubboServiceVersion,interfaceClass=MSignRuleService.class,check=false,timeout = 10000)
	private MSignRuleService mSignRuleService;
	@NeedAuth
	@RequestMapping(value = "list", method = RequestMethod.GET)
	void page(HttpServletRequest request, HttpServletResponse response, MSignRule mSignRule) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try{
			result.setData(mSignRuleService.queryList(mSignRule));
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
	@RequestMapping(value="add",method = RequestMethod.POST)
	void add(HttpServletRequest request, HttpServletResponse response,MSignRule mSignRule) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			int i=mSignRuleService.add(mSignRule);
			if(i==3) {
				result.setSuccess(false);
				result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
				result.setMessage(RespCodeState.SIGN_STICK_TIME.getMessage());
			}else if(i==0) {
				result.setSuccess(false);
				result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
				result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
			}else {
				result.setSuccess(true);
				result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
				result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
		}
		
		outStrJSONWithResult(response, result, respMap);
	}
	@NeedAuth
	@RequestMapping(value="info",method = RequestMethod.GET)
	void add(HttpServletRequest request, HttpServletResponse response,Integer ruleId) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(mSignRuleService.info(ruleId));
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
	@RequestMapping(value="update",method = RequestMethod.POST)
	void update(HttpServletRequest request, HttpServletResponse response,MSignRule mSignRule) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		int i=mSignRuleService.updateRule(mSignRule);
		try {
			if(i==0) {
				result.setSuccess(false);
				result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
				result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
			}else {
				result.setSuccess(true);
				result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
				result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
		}
		
		outStrJSONWithResult(response, result, respMap);
	}
	@NeedAuth
	@RequestMapping(value="delete",method = RequestMethod.GET)
	void delete(HttpServletRequest request, HttpServletResponse response,Integer ruleId) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		int i=mSignRuleService.deleteRule(ruleId);
		try {
			if(i==0) {
				result.setSuccess(false);
				result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
				result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
			}else {
				result.setSuccess(true);
				result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
				result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
		}
		
		outStrJSONWithResult(response, result, respMap);
	}
	
	@NeedToken
	@RequestMapping(value = "appList", method = RequestMethod.GET)
	void appList(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try{
			result.setData(mSignRuleService.appList());
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

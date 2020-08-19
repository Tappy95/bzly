package com.mc.bzly.controller.checkin;

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
import com.mc.bzly.model.checkin.CCheckinRewardRule;
import com.mc.bzly.service.checkin.CCheckinRewardRuleService;

@RestController
@RequestMapping("/checkinRewardRule")
public class CCheckinRewardRuleController extends BaseController{
	
	@Reference(version=WebConfig.dubboServiceVersion,interfaceClass=CCheckinRewardRuleService.class,check=false,timeout=10000)
	private CCheckinRewardRuleService cCheckinRewardRuleService;
	
	@NeedAuth
	@RequestMapping(value = "list", method = RequestMethod.GET)
	void list(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try{
			result.setData(cCheckinRewardRuleService.selectList());
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
	@RequestMapping(value = "add",method = RequestMethod.POST)
	public void add(HttpServletRequest request, HttpServletResponse response,CCheckinRewardRule cCheckinRewardRule) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(cCheckinRewardRuleService.add(cCheckinRewardRule));
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
	void update(HttpServletRequest request, HttpServletResponse response,CCheckinRewardRule cCheckinRewardRule) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(cCheckinRewardRuleService.update(cCheckinRewardRule));
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
	@RequestMapping(value="delete",method = RequestMethod.GET)
	void delete(HttpServletRequest request, HttpServletResponse response,Integer id) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(cCheckinRewardRuleService.delete(id));
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
	@RequestMapping(value = "info", method = RequestMethod.GET)
	void info(HttpServletRequest request, HttpServletResponse response,Integer id) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try{
			result.setData(cCheckinRewardRuleService.selectOne(id));
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

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
import com.mc.bzly.model.user.LUserCashLog;
import com.mc.bzly.model.user.LUserTask;
import com.mc.bzly.service.user.LUserTaskService;

@RestController
@RequestMapping("/lUserTask")
public class LUserTaskController extends BaseController{
	
	@Reference(version=WebConfig.dubboServiceVersion,interfaceClass=LUserTaskService.class,check=false,timeout = 10000)
	private LUserTaskService lUserTaskService;
	/**
	 * 领取新手任务奖励
	 * @param request
	 * @param response
	 * @param token
	 * @param id
	 */
	@NeedToken
	@RequestMapping(value = "userReceiveTask", method = RequestMethod.GET)
	void userReceiveTask(HttpServletRequest request, HttpServletResponse response,String token,Integer id) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			Integer res=lUserTaskService.userReceiveTask(getUserId(token), id);
			if(res==3) {
				result.setData(res);
				result.setSuccess(true);
				result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
				result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
			}else if(res==2) {
				result.setData(res);
				result.setSuccess(true);
				result.setStatusCode(RespCodeState.REPEAT_RECEIVE.getStatusCode());
				result.setMessage(RespCodeState.REPEAT_RECEIVE.getMessage());
			}else {
				result.setData(res);
				result.setSuccess(true);
				result.setStatusCode(RespCodeState.NOT_COMPLETE.getStatusCode());
				result.setMessage(RespCodeState.NOT_COMPLETE.getMessage());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
		}
		
		outStrJSONWithResult(response, result, respMap);
	}
	/**
	 * 福利会任务完成接口
	 * @param request
	 * @param response
	 * @param token
	 */
	@NeedToken
	@RequestMapping(value = "welfareTask", method = RequestMethod.GET)
	void welfareTask(HttpServletRequest request, HttpServletResponse response,String token) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			Integer res=lUserTaskService.welfareTask(getUserId(token));
			result.setData(res);
			result.setSuccess(true);
			result.setStatusCode(RespCodeState.NOT_COMPLETE.getStatusCode());
			result.setMessage(RespCodeState.NOT_COMPLETE.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
		}
		
		outStrJSONWithResult(response, result, respMap);
	}

	@NeedAuth
	@RequestMapping(value = "list", method = RequestMethod.GET)
	void list(HttpServletRequest request, HttpServletResponse response,LUserTask lUserTask) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);Result result = new Result();
		try {
			Map<String, Object> data=lUserTaskService.list(lUserTask);
			if("2".equals(data.get("res"))) {
				result.setStatusCode(RespCodeState.MOBILE_NOT_EXIST.getStatusCode());
				result.setMessage(RespCodeState.MOBILE_NOT_EXIST.getMessage());
			}else {
				result.setData(data);
				result.setSuccess(true);
				result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
				result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());	
			}
		} catch (Exception e) {
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
			e.printStackTrace();
		}
		outStrJSONWithResult(response, result, respMap);
	}
}

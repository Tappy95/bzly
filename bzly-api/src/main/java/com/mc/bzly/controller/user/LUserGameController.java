package com.mc.bzly.controller.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mc.bzly.annotation.NeedToken;
import com.mc.bzly.base.BaseController;
import com.mc.bzly.base.RespCodeState;
import com.mc.bzly.base.Result;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.model.user.LUserGame;
import com.mc.bzly.service.user.LUserGameService;

@RestController
@RequestMapping("lUserGame")
public class LUserGameController extends BaseController {

	@Reference(version = WebConfig.dubboServiceVersion, interfaceClass = LUserGameService.class, check = false, timeout = 10000)
	private LUserGameService lUserGameService;

	@NeedToken
	@RequestMapping(value = "list", method = RequestMethod.GET)
	void list(HttpServletRequest request, HttpServletResponse response, LUserGame lUserGame, String token) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			lUserGame.setUserId(getUserId(token));
			result.setData(lUserGameService.queryMyTry(lUserGame));
			result.setSuccess(true);
			result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
			result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
		} catch (Exception e) {
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
			e.printStackTrace();
		}
		outStrJSONWithResult(response, result, respMap);
	}

	@NeedToken
	@RequestMapping(value = "remove", method = RequestMethod.GET)
	void remove(HttpServletRequest request, HttpServletResponse response,Integer id, String token) {
		Map<String, String> respMap = new HashMap<>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try {
			lUserGameService.remove(id,getUserId(token));
			result.setSuccess(true);
			result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
			result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
		} catch (Exception e) {
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
			e.printStackTrace();
		}
		outStrJSONWithResult(response, result, respMap);
	}
	
	@NeedToken
	@RequestMapping(value = "removeQDZ", method = RequestMethod.GET)
	void removeQDZ(HttpServletRequest request, HttpServletResponse response,Integer lid, String token) {
		Map<String, String> respMap = new HashMap<>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try {
			lUserGameService.removeQDZ(lid,getUserId(token));
			result.setSuccess(true);
			result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
			result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
		} catch (Exception e) {
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
			e.printStackTrace();
		}
		outStrJSONWithResult(response, result, respMap);
	}
	
	@NeedToken
	@RequestMapping(value = "queryTodayFinish", method = RequestMethod.GET)
	void queryTodayFinish(HttpServletRequest request, HttpServletResponse response,String token) {
		Map<String, String> respMap = new HashMap<>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(lUserGameService.queryTodayFinish(getUserId(token)));
			result.setSuccess(true);
			result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
			result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
		} catch (Exception e) {
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
			e.printStackTrace();
		}
		outStrJSONWithResult(response, result, respMap);
	}
}

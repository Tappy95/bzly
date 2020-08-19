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
import com.mc.bzly.model.user.LPigChange;
import com.mc.bzly.service.user.LPigChangeService;

@RestController
@RequestMapping("/lPigChange")
public class LPigChangeControler extends BaseController {

	@Reference(version = WebConfig.dubboServiceVersion, interfaceClass = LPigChangeService.class, check = false, timeout = 10000)
	private LPigChangeService lPigChangeService;

	@NeedToken
	@RequestMapping(value = "list", method = RequestMethod.GET)
	void list(HttpServletRequest request, HttpServletResponse response, LPigChange lPigChange, String token) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			lPigChange.setUserId(getUserId(token));
			result.setData(lPigChangeService.queryList(lPigChange));
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

	@NeedAuth
	@RequestMapping(value = "page", method = RequestMethod.GET)
	void page(HttpServletRequest request, HttpServletResponse response, LPigChange lPigChange) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			lPigChange.setChannelCode(respMap.get("channel"));
			lPigChange.setUserRelation(Integer.parseInt(respMap.get("relation")));
			Map<String, Object> data=lPigChangeService.pageList(lPigChange);
			if("2".equals(data.get("res"))) {
				result.setStatusCode(RespCodeState.MOBILE_NOT_EXIST.getStatusCode());
				result.setMessage(RespCodeState.MOBILE_NOT_EXIST.getMessage());
			}else {
				result.setData(data);
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

	@NeedAuth
	@RequestMapping(value = "info", method = RequestMethod.GET)
	void info(HttpServletRequest request, HttpServletResponse response, Integer id) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(lPigChangeService.info(id));
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
	@RequestMapping(value = "exclPigChange", method = RequestMethod.GET)
	void exclPigChange(HttpServletRequest request, HttpServletResponse response,LPigChange lPigChange) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);Result result = new Result();
		lPigChange.setChannelCode(respMap.get("channel"));
		lPigChange.setUserRelation(Integer.parseInt(respMap.get("relation")));
		try {
			result.setData(lPigChangeService.exclPigChange(lPigChange));
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
	
	/**
	 * 领取宝猪28救济金猪
	 * @param request
	 * @param response
	 * @param lPigChange
	 * @param token
	 */
	@NeedToken
	@RequestMapping(value = "addPigCoin", method = RequestMethod.GET)
	void addPigCoin(HttpServletRequest request, HttpServletResponse response, String token) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			String userId = getUserId(token);
			result = lPigChangeService.addPigCoin(userId);
		} catch (Exception e) {
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
			e.printStackTrace();
		}
		outStrJSONWithResult(response, result, respMap);
	}
}

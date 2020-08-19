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
import com.mc.bzly.service.user.LActiveGoldLogService;

@RestController
@RequestMapping("/lActiveGoldLog")
public class LActiveGoldLogControler extends BaseController {

	@Reference(version = WebConfig.dubboServiceVersion, interfaceClass = LActiveGoldLogService.class, check = false, timeout = 10000)
	private LActiveGoldLogService lActiveGoldLogService;

	@NeedToken
	@RequestMapping(value = "receiveActive", method = RequestMethod.GET)
	void receiveActive(HttpServletRequest request, HttpServletResponse response, String token) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			Map<String, Object> date = lActiveGoldLogService.receiveActive(getUserId(token));
			if ("3".equals(date.get("res").toString())) {
				result.setSuccess(false);
				result.setStatusCode(RespCodeState.USER_NO_VIP.getStatusCode());
				result.setMessage(RespCodeState.USER_NO_VIP.getMessage());
			} else if ("2".equals(date.get("res").toString())) {
				result.setSuccess(false);
				result.setStatusCode(RespCodeState.TODAY_RECEIVE.getStatusCode());
				result.setMessage(RespCodeState.TODAY_RECEIVE.getMessage());
			} else {
				result.setData(date);
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
	@RequestMapping(value = "receiveActiveNews", method = RequestMethod.GET)
	void receiveActiveNews(HttpServletRequest request, HttpServletResponse response, String token,int vipId) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result=lActiveGoldLogService.receiveActiveNews(getUserId(token),vipId);
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
		}

		outStrJSONWithResult(response, result, respMap);
	}
	
	@NeedToken
	@RequestMapping(value = "recommendGameVip", method = RequestMethod.GET)
	void recommendGameVip(HttpServletRequest request, HttpServletResponse response, String token,int vipId) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			String equipmentType = respMap.get("equipmentType"); // 1-安卓 2-iOS
			int ptype=new Integer(equipmentType);
			if(ptype == 2){
				ptype = 1;
			}else {
				ptype = 2;
			}
			Map<String, Object> date = lActiveGoldLogService.recommendGameVip(ptype,getUserId(token),vipId);
			result.setData(date);
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

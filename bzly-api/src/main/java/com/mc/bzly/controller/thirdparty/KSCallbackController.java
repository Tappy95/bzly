package com.mc.bzly.controller.thirdparty;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mc.bzly.base.BaseController;
import com.mc.bzly.base.RespCodeState;
import com.mc.bzly.base.Result;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.model.thirdparty.TPKsCallback;
import com.mc.bzly.service.thirdparty.TPKsCallbackService;

@RestController
@RequestMapping("/ks")
public class KSCallbackController extends BaseController{
	
	@Reference(version = WebConfig.dubboServiceVersion, interfaceClass = TPKsCallbackService.class, check = false, timeout = 10000)
	private TPKsCallbackService tpKsCallbackService;

	
	@RequestMapping(value = "req", method = RequestMethod.GET)
	void page(HttpServletRequest request, HttpServletResponse response,TPKsCallback tpKsCallback) {
		Map<String, String> respMap = new HashMap<>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try {
			tpKsCallback.setStatus(1);
			tpKsCallback.setCreateTime(new Date().getTime());
			tpKsCallback.setEquipmentType(2);
			tpKsCallbackService.add(tpKsCallback);
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

	@RequestMapping(value = "reqAndroid", method = RequestMethod.GET)
	void reqAndroid(HttpServletRequest request, HttpServletResponse response,TPKsCallback tpKsCallback) {
		Map<String, String> respMap = new HashMap<>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try {
			tpKsCallback.setStatus(1);
			tpKsCallback.setCreateTime(new Date().getTime());
			tpKsCallback.setEquipmentType(2);
			tpKsCallbackService.add(tpKsCallback);
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

	@RequestMapping(value = "reqIOS", method = RequestMethod.GET)
	void reqIos(HttpServletRequest request, HttpServletResponse response,TPKsCallback tpKsCallback) {
		Map<String, String> respMap = new HashMap<>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try {
			tpKsCallback.setStatus(1);
			tpKsCallback.setCreateTime(new Date().getTime());
			tpKsCallback.setEquipmentType(1);
			tpKsCallbackService.add(tpKsCallback);
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

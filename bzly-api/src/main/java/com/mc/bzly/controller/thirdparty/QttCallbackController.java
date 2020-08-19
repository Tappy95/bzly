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
import com.mc.bzly.model.thirdparty.TPQttCallback;
import com.mc.bzly.service.thirdparty.TPQttCallbackService;

@RestController
@RequestMapping("/qtt")
public class QttCallbackController extends BaseController{
	
	@Reference(version = WebConfig.dubboServiceVersion, interfaceClass = TPQttCallbackService.class, check = false, timeout = 10000)
	private TPQttCallbackService tpPQttCallbackService;

	
	@RequestMapping(value = "reqAndroid", method = RequestMethod.GET)
	void reqAndroid(HttpServletRequest request, HttpServletResponse response,TPQttCallback tpQttCallback) {
		Map<String, String> respMap = new HashMap<>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try {
			tpQttCallback.setStatus(1);
			tpQttCallback.setCreateTime(new Date().getTime());
			tpQttCallback.setEquipmentType(2);
			tpPQttCallbackService.add(tpQttCallback);
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
	void reqIos(HttpServletRequest request, HttpServletResponse response,TPQttCallback tpQttCallback) {
		Map<String, String> respMap = new HashMap<>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try {
			tpQttCallback.setStatus(1);
			tpQttCallback.setCreateTime(new Date().getTime());
			tpQttCallback.setEquipmentType(1);
			tpPQttCallbackService.add(tpQttCallback);
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

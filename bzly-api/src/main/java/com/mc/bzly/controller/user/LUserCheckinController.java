package com.mc.bzly.controller.user;

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
import com.mc.bzly.model.user.LUserCheckin;
import com.mc.bzly.service.user.LUserCheckinService;

@RestController
@RequestMapping("/lUserCheckin")
public class LUserCheckinController extends BaseController {

	@Reference(version = WebConfig.dubboServiceVersion, interfaceClass = LUserCheckinService.class, check = false, timeout = 10000)
	private LUserCheckinService lUserCheckinService;

	@RequestMapping(value = "myCheckinRecord", method = RequestMethod.GET)
	void myCheckinRecord(HttpServletRequest request, HttpServletResponse response, String token) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			String userId = getUserId(token);
			LUserCheckin record = lUserCheckinService.queryOne(userId);
			if (record != null) {
				result.setData(record);
				result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
				result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
			} else {
				result.setData(null);
				result.setMessage(RespCodeState.NO_JOIN_ACTIVITY.getMessage());
				result.setStatusCode(RespCodeState.NO_JOIN_ACTIVITY.getStatusCode());
			}
		} catch (Exception e) {
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			e.printStackTrace();
		}
		outStrJSONWithResult(response, result, respMap);
	}

}

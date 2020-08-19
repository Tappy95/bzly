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
import com.mc.bzly.model.user.LUserFirstLog;
import com.mc.bzly.service.user.LUserFirstLogService;

@RestController
@RequestMapping("/lUserFirstLog")
public class LUserFirstLogController extends BaseController{
	
	@Reference(version = WebConfig.dubboServiceVersion, interfaceClass = LUserFirstLogService.class, check = false, timeout = 10000)
	private LUserFirstLogService lUserFirstLogService;

	@NeedToken
	@RequestMapping(value = "save", method = RequestMethod.GET)
	void save(HttpServletRequest request, HttpServletResponse response,LUserFirstLog lUserFirstLog,String token) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			lUserFirstLog.setUserId(getUserId(token));
			result.setData(lUserFirstLogService.insert(lUserFirstLog));
			result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
			result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
		} catch (Exception e) {
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			e.printStackTrace();
		}
		outStrJSONWithResult(response, result, respMap);
	}

}

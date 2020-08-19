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
import com.mc.bzly.base.BaseController;
import com.mc.bzly.base.RespCodeState;
import com.mc.bzly.base.Result;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.model.user.LUserCashLog;
import com.mc.bzly.service.user.LUserCashLogService;

@RestController
@RequestMapping("/userCashLog")
public class LUserCashLogController extends BaseController{
	
	@Reference(version = WebConfig.dubboServiceVersion, interfaceClass = LUserCashLogService.class, check = false, timeout = 10000)
	private LUserCashLogService lUserCashLogService;
	
	@NeedAuth
	@RequestMapping(value = "list", method = RequestMethod.GET)
	void list(HttpServletRequest request, HttpServletResponse response,LUserCashLog lUserCashLog) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);Result result = new Result();
		try {
			lUserCashLog.setChannel(respMap.get("channel"));
			lUserCashLog.setUserRelation(Integer.parseInt(respMap.get("relation")));
			Map<String, Object> data=lUserCashLogService.selectList(lUserCashLog);
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

	@NeedAuth
	@RequestMapping(value = "excl", method = RequestMethod.GET)
	void excl(HttpServletRequest request, HttpServletResponse response,LUserCashLog lUserCashLog) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);Result result = new Result();
		try {
			lUserCashLog.setChannel(respMap.get("channel"));
			lUserCashLog.setUserRelation(Integer.parseInt(respMap.get("relation")));
			lUserCashLog.setPageNum(1);
			lUserCashLog.setPageSize(5000);
			result.setData(lUserCashLogService.excl(lUserCashLog));
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

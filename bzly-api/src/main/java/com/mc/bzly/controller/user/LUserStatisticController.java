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
import com.mc.bzly.model.user.LUserReconciliation;
import com.mc.bzly.service.user.LUserStatisticServcie;
@RestController
@RequestMapping("/userStatistic")
public class LUserStatisticController extends BaseController{
	
	@Reference(version = WebConfig.dubboServiceVersion, interfaceClass = LUserStatisticServcie.class, check = false, timeout = 10000)
	private LUserStatisticServcie lUserStatisticServcie;
	
	@NeedAuth
	@RequestMapping(value = "userList", method = RequestMethod.GET)
	void userList(HttpServletRequest request, HttpServletResponse response,LUserReconciliation lUserReconciliation) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);Result result = new Result();
		try {
			lUserReconciliation.setChannel(respMap.get("channel"));
			lUserReconciliation.setUserRelation(Integer.parseInt(respMap.get("relation")));
			Map<String, Object> data=lUserStatisticServcie.userList(lUserReconciliation);
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
	void excl(HttpServletRequest request, HttpServletResponse response,LUserReconciliation lUserReconciliation) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);Result result = new Result();
		try {
			lUserReconciliation.setChannel(respMap.get("channel"));
			lUserReconciliation.setUserRelation(Integer.parseInt(respMap.get("relation")));
			lUserReconciliation.setPageNum(1);
			lUserReconciliation.setPageSize(5000);
			result.setData(lUserStatisticServcie.excl(lUserReconciliation));
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

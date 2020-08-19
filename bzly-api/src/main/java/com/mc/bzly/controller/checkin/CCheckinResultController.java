package com.mc.bzly.controller.checkin;

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
import com.mc.bzly.model.checkin.CCheckinResult;
import com.mc.bzly.service.checkin.CCheckinResultService;

@RestController
@RequestMapping("/checkinResult")
public class CCheckinResultController extends BaseController{
	
	@Reference(version=WebConfig.dubboServiceVersion,interfaceClass=CCheckinResultService.class,check=false,timeout=10000)
	private CCheckinResultService cCheckinResultService;
	
	@NeedAuth
	@RequestMapping(value = "page", method = RequestMethod.GET)
	void page(HttpServletRequest request, HttpServletResponse response,CCheckinResult cCheckinResult) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try{
			result.setData(cCheckinResultService.page(cCheckinResult));
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

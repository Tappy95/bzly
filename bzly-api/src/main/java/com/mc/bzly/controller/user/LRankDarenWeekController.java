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
import com.mc.bzly.model.user.LRankDarenWeek;
import com.mc.bzly.service.user.LRankDarenWeekService;

@RestController
@RequestMapping("rankDarenWeek")
public class LRankDarenWeekController extends BaseController {

	@Reference(version = WebConfig.dubboServiceVersion, interfaceClass = LRankDarenWeekService.class, check = false, timeout = 10000)
	private LRankDarenWeekService lRankDarenWeekService;
	
	@RequestMapping(value="queryFlist",method=RequestMethod.GET)
	void queryFlist(HttpServletRequest request,HttpServletResponse response){
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(lRankDarenWeekService.queryFlist());
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
	@RequestMapping(value="list",method=RequestMethod.GET)
	void list(HttpServletRequest request,HttpServletResponse response,LRankDarenWeek lRankDarenWeek){
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(lRankDarenWeekService.page(lRankDarenWeek));
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
	@RequestMapping(value="cycle",method=RequestMethod.GET)
	void cycle(HttpServletRequest request,HttpServletResponse response){
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(lRankDarenWeekService.selectCycle());
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
	
	@RequestMapping(value="queryByCycel",method=RequestMethod.GET)
	void queryByCycel(HttpServletRequest request,HttpServletResponse response,String rankCycle){
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(lRankDarenWeekService.queryByCycel(rankCycle));
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

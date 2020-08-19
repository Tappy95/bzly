package com.mc.bzly.controller.message;

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
import com.mc.bzly.service.user.LCoinChangeService;

@RestController
@RequestMapping("/message")
public class MessageController extends BaseController {

	@Reference(version=WebConfig.dubboServiceVersion,interfaceClass=LCoinChangeService.class,check=false,timeout=10000)
	private LCoinChangeService lCoinChangeService;
	
	@RequestMapping(value="list",method = RequestMethod.GET)
	void list(HttpServletRequest request,HttpServletResponse response){
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try{
			result.setData(lCoinChangeService.queryMessageList());
			result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
			result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
		}
		outStrJSONWithResult(response, result, respMap);
	}
	
	@RequestMapping(value="qdzList",method = RequestMethod.GET)
	void fxzList(HttpServletRequest request,HttpServletResponse response){
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try{
			result.setData(lCoinChangeService.queryqdzList());
			result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
			result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
		}
		outStrJSONWithResult(response, result, respMap);
	}

	@RequestMapping(value="kszList",method = RequestMethod.GET)
	void kszList(HttpServletRequest request,HttpServletResponse response){
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try{
			result.setData(lCoinChangeService.querykszList());
			result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
			result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
		}
		outStrJSONWithResult(response, result, respMap);
	}

}

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
import com.mc.bzly.service.user.LUserSearchLogService;

@RestController
@RequestMapping("/userSearchLogs")
public class LUserSearchLogController extends BaseController {

	@Reference(version = WebConfig.dubboServiceVersion, interfaceClass = LUserSearchLogService.class, check = false, timeout = 10000)
	private LUserSearchLogService lUserSearchLogService;
	
	@NeedToken
	@RequestMapping(value="list",method = RequestMethod.GET)
	void list(HttpServletRequest request,HttpServletResponse response,String token,Integer searchType){
		Map<String, String> respMap = new HashMap<String, String>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try{
			String userId = getUserId(token);
			result.setData(lUserSearchLogService.queryList(userId,searchType));
			result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
			result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
		}catch (Exception e) {
			e.printStackTrace();
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
		}
		outStrJSONWithResult(response, result, respMap);
	}

	@NeedToken
	@RequestMapping(value="removeAll",method = RequestMethod.POST)
	void removeAll(HttpServletRequest request,HttpServletResponse response,String token,Integer searchType){
		Map<String, String> respMap = new HashMap<String, String>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try{
			String userId = getUserId(token);
			lUserSearchLogService.removeAll(userId,searchType);
			result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
			result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
		}catch (Exception e) {
			e.printStackTrace();
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
		}
		outStrJSONWithResult(response, result, respMap);
	}
	
	
}

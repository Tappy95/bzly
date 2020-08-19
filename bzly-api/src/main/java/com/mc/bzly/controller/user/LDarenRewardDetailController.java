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
import com.mc.bzly.annotation.NeedToken;
import com.mc.bzly.base.BaseController;
import com.mc.bzly.base.RespCodeState;
import com.mc.bzly.base.Result;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.model.user.LDarenRewardDetail;
import com.mc.bzly.service.user.LDarenRewardDetailService;

@RestController
@RequestMapping("/darenRewardDetail")
public class LDarenRewardDetailController extends BaseController {

	@Reference(version=WebConfig.dubboServiceVersion,interfaceClass=LDarenRewardDetailService.class,check=false,timeout = 10000)
	private LDarenRewardDetailService lDarenRewardDetailService;
	
	@NeedAuth
	@RequestMapping(value="list",method=RequestMethod.GET)
	void list(HttpServletRequest request,HttpServletResponse response,LDarenRewardDetail lDarenRewardDetail){
		Map<String, String> respMap = new HashMap<>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(lDarenRewardDetailService.list(lDarenRewardDetail));
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

	@NeedToken
	@RequestMapping(value="listF",method=RequestMethod.GET)
	void listF(HttpServletRequest request,HttpServletResponse response,String token,String accountId,Integer pageSize,Integer pageNum){
		Map<String, String> respMap = new HashMap<>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try {
			String userId = getUserId(token);
			//String userId = "1e732908185242e99fd2802696f5f46f";
			result.setData(lDarenRewardDetailService.listF(userId,accountId,pageSize,pageNum));
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

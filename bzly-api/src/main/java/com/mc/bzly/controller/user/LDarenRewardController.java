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
import com.mc.bzly.model.user.LDarenReward;
import com.mc.bzly.service.user.LDarenRewardService;

@RestController
@RequestMapping("/ldarenReward")
public class LDarenRewardController extends BaseController {

	@Reference(version=WebConfig.dubboServiceVersion,interfaceClass=LDarenRewardService.class,check=false,timeout = 10000)
	private LDarenRewardService lDarenRewardService;
	
	@NeedToken
	@RequestMapping(value="list",method=RequestMethod.GET)
	void list(HttpServletRequest request,HttpServletResponse response,LDarenReward lDarenReward){
		Map<String, String> respMap = new HashMap<>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try {
			lDarenReward.setUserId(getUserId(request.getParameter("token")));
			result.setData(lDarenRewardService.list(lDarenReward));
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

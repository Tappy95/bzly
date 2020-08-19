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
import com.mc.bzly.model.user.LRankCoin;
import com.mc.bzly.service.user.LRankCoinService;

@RestController
@RequestMapping("rankCoin")
public class LRankCoinController extends BaseController {

	@Reference(version = WebConfig.dubboServiceVersion, interfaceClass = LRankCoinService.class, check = false, timeout = 10000)
	private LRankCoinService lRankCoinService;
	
	@NeedAuth
	@RequestMapping(value="queryFlist",method=RequestMethod.GET)
	void queryFlist(HttpServletRequest request,HttpServletResponse response,LRankCoin lRankCoin){
		Map<String, String> respMap = new HashMap<>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(lRankCoinService.list(lRankCoin));
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
	
	/**
	 * @param request
	 * @param response
	 * @param lRankCoin.rankType
	 */
	@RequestMapping(value="listF",method=RequestMethod.GET)
	void listF(HttpServletRequest request,HttpServletResponse response,LRankCoin lRankCoin){
		Map<String, String> respMap = new HashMap<>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(lRankCoinService.listF(lRankCoin));
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

	/**
	 * 
	 * @param request
	 * @param response
	 * @param lRankCoin.rankType
	 * @param token
	 */
	@NeedToken
	@RequestMapping(value="queryHisMy",method=RequestMethod.GET)
	void queryHisMy(HttpServletRequest request,HttpServletResponse response,LRankCoin lRankCoin,String token){
		Map<String, String> respMap = new HashMap<>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try {
			String userId = getUserId(token);
			result.setData(lRankCoinService.queryHisMy(lRankCoin,userId));
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

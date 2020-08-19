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
import com.mc.bzly.model.user.LRankPig;
import com.mc.bzly.service.user.LRankPigService;

@RestController
@RequestMapping("/rankPig")
public class LRankPigController extends BaseController {
	
	@Reference(version = WebConfig.dubboServiceVersion, interfaceClass = LRankPigService.class, check = false, timeout = 10000)
	private LRankPigService lRankPigService;
	
	@NeedAuth
	@RequestMapping(value = "list", method = RequestMethod.GET)
	void list(HttpServletRequest request,HttpServletResponse response,LRankPig lRankPig){
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(lRankPigService.queryList(lRankPig));
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
	 * 前端会H5查询排行榜
	 * @param request
	 * @param response
	 * @param pageSize
	 * @param pageNum
	 * @param lRankPig.rankType = 3
	 */
	@NeedToken
	@RequestMapping(value = "listF", method = RequestMethod.GET)
	void listF(HttpServletRequest request,HttpServletResponse response,LRankPig lRankPig){
		Map<String, String> respMap = new HashMap<>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(lRankPigService.listF(lRankPig));
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
	 * 前端会H5查询排行榜
	 * @param request
	 * @param response
	 * @param lRankPig.rankType = 3
	 * @param imei
	 * @param token
	 */
	@NeedToken
	@RequestMapping(value = "queryMyRank", method = RequestMethod.GET)
	void queryMyRank(HttpServletRequest request,HttpServletResponse response){
		Map<String, String> respMap = new HashMap<>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try {
			String userId = getUserId(respMap.get("token"));
			int rankType = new Integer(respMap.get("rankType"));
			result.setData(lRankPigService.queryMyRank(userId,rankType));
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
	 * 前端会H5查询排行榜
	 * @param request
	 * @param response
	 * @param pageSize
	 * @param pageNum
	 * @param lRankPig.rankType = 1
	 */
	@RequestMapping(value = "listFDay", method = RequestMethod.GET)
	void listFDay(HttpServletRequest request,HttpServletResponse response,LRankPig lRankPig){
		Map<String, String> respMap = new HashMap<>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(lRankPigService.listFDay(lRankPig));
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
	 * 前端会H5查询排行榜
	 * @param request
	 * @param response
	 * @param lRankPig.rankType = 1
	 * @param imei
	 * @param token
	 */
	@NeedToken
	@RequestMapping(value = "queryMyRankDay", method = RequestMethod.GET)
	void queryMyRankDay(HttpServletRequest request,HttpServletResponse response){
		Map<String, String> respMap = new HashMap<>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try {
		    String userId = getUserId(respMap.get("token"));
			int rankType = new Integer(respMap.get("rankType"));
			result.setData(lRankPigService.queryMyRankDay(userId,rankType));
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

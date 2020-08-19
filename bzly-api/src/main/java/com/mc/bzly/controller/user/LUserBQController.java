package com.mc.bzly.controller.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bzly.common.utils.MD5Util;
import com.mc.bzly.annotation.NeedToken;
import com.mc.bzly.base.BaseController;
import com.mc.bzly.base.RespCodeState;
import com.mc.bzly.base.Result;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.model.user.LUserBQ;
import com.mc.bzly.service.user.LUserBQService;

@RestController
@RequestMapping("userBQ")
public class LUserBQController extends BaseController {
	
	@Reference(version=WebConfig.dubboServiceVersion,interfaceClass=LUserBQService.class,check=false,timeout = 10000)
	private LUserBQService lUserBQService;
	
	@NeedToken
	@RequestMapping(value="add",method=RequestMethod.POST)
	void add(HttpServletRequest request,HttpServletResponse response,String token,long time,String sign){
		Map<String, String> respMap = new HashMap<>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try{
			String userId=getUserId(token);
			String md5=MD5Util.getMd5(token+"bzlyInformationMd5"+time);
			if(!md5.equals(sign)) {
				result.setSuccess(true);
				result.setStatusCode(RespCodeState.SIGN_ERROR.getStatusCode());
				result.setMessage(RespCodeState.SIGN_ERROR.getMessage());
			}else {
				LUserBQ lUserBQ = new LUserBQ();
				lUserBQ.setUserId(userId);
				lUserBQService.add(lUserBQ);
				result.setSuccess(true);
				result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
				result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
			}
		}catch (Exception e) {
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
			e.printStackTrace();
		}
		outStrJSONWithResult(response, result, respMap);
	}

	@NeedToken
	@RequestMapping(value="myCardCount",method=RequestMethod.GET)
	void myCardCount(HttpServletRequest request,HttpServletResponse response,String token){
		Map<String, String> respMap = new HashMap<>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try{
			String userId=getUserId(token);
			result.setData(lUserBQService.queryCount(userId));
			result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
			result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
		}catch (Exception e) {
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
			e.printStackTrace();
		}
		outStrJSONWithResult(response, result, respMap);
	}

	/**
	 * 用户使用补签卡
	 * @param request
	 * @param response
	 * @param token
	 * @param signinId 待补签的签到记录id
	 */
	@NeedToken
	@RequestMapping(value="useCard",method=RequestMethod.GET)
	void useCard(HttpServletRequest request,HttpServletResponse response,String token,int signinId){
		Map<String, String> respMap = new HashMap<>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try{
			String userId=getUserId(token);
			int res = lUserBQService.useCard(userId,signinId);
			//int res = lUserBQService.useCard("356eeb4cd3de431fae929ccdbbfa7d2e",signinId);
			if(res == 1){
				result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
				result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
			}else if(res == 2){
				result.setStatusCode(RespCodeState.BQ_CARD_NOT_ENOUGH.getStatusCode());
				result.setMessage(RespCodeState.BQ_CARD_NOT_ENOUGH.getMessage());	
			}else if(res == 3){
				result.setStatusCode(RespCodeState.RECORD_NEED_NOT_ACTIVE.getStatusCode());
				result.setMessage(RespCodeState.RECORD_NEED_NOT_ACTIVE.getMessage());	
			}else if(res == 4){
				result.setStatusCode(RespCodeState.HAS_NO_ACTIVE_BEFORE.getStatusCode());
				result.setMessage(RespCodeState.HAS_NO_ACTIVE_BEFORE.getMessage());
			}
		}catch (Exception e) {
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
			e.printStackTrace();
		}
		outStrJSONWithResult(response, result, respMap);
	}
	
	@NeedToken
	@RequestMapping(value="checkinCount",method=RequestMethod.GET)
	void checkinCount(HttpServletRequest request,HttpServletResponse response,String token){
		Map<String, String> respMap = new HashMap<>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try{
			String userId=getUserId(token);
			result.setData(lUserBQService.checkinCount(userId));
			result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
			result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
		}catch (Exception e) {
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
			e.printStackTrace();
		}
		outStrJSONWithResult(response, result, respMap);
	}
}

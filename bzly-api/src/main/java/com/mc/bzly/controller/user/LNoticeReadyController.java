package com.mc.bzly.controller.user;

import java.util.Date;
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
import com.mc.bzly.model.user.LNoticeReady;
import com.mc.bzly.model.user.MUserInfo;
import com.mc.bzly.service.user.LNoticeReadyService;

@RestController
@RequestMapping("/noticeReady")
public class LNoticeReadyController extends BaseController {
	
	@Reference(version = WebConfig.dubboServiceVersion, interfaceClass = LNoticeReadyService.class, check = false, timeout = 10000)
	private LNoticeReadyService lNoticeReadyService;
	
	@NeedAuth
	@RequestMapping(value = "list", method = RequestMethod.GET)
	void list(HttpServletRequest request,HttpServletResponse response,LNoticeReady lNoticeReady){
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			if(lNoticeReady.getAccountId()!=null) {
				MUserInfo mUserInfo=mUserInfoService.selectAccountId(lNoticeReady.getAccountId());
				if(mUserInfo==null) {
					result.setStatusCode(RespCodeState.MOBILE_NOT_EXIST.getStatusCode());
					result.setMessage(RespCodeState.MOBILE_NOT_EXIST.getMessage());
				}else {
					result.setData(lNoticeReadyService.list(lNoticeReady));
					result.setSuccess(true);
					result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
					result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
				}
			}else {
				result.setData(lNoticeReadyService.list(lNoticeReady));
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

	/**
	 * 加入每日提醒
	 * @param noticeType 1-每日红包提醒
	 * @param status 1- 开启 2-关闭
	 */
	@NeedToken
	@RequestMapping(value = "openOrClose", method = RequestMethod.POST)
	void openOrClose(HttpServletRequest request,HttpServletResponse response,LNoticeReady lNoticeReady,String token,Integer status){
		Map<String, String> respMap = new HashMap<>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try {
			lNoticeReady.setUserId(getUserId(token));
			if(status.intValue() == 1){
				lNoticeReady.setCreateTime(new Date().getTime());
				lNoticeReadyService.add(lNoticeReady);
			}else{
				lNoticeReadyService.remove(lNoticeReady);
			}
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
	 * 取消每日提醒
	 * @param noticeType 1-每日红包提醒
	 */
	@NeedToken
	@RequestMapping(value = "remove", method = RequestMethod.GET)
	void remove(HttpServletRequest request,HttpServletResponse response,LNoticeReady lNoticeReady,String token){
		Map<String, String> respMap = new HashMap<>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try {
			lNoticeReady.setUserId(getUserId(token));
			lNoticeReadyService.remove(lNoticeReady);
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
	 * 查看是否开起每日提醒
	 * @param noticeType 1-每日红包提醒
	 */
	@NeedToken
	@RequestMapping(value = "queryExist", method = RequestMethod.GET)
	void queryExist(HttpServletRequest request,HttpServletResponse response,LNoticeReady lNoticeReady,String token){
		Map<String, String> respMap = new HashMap<>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try {
			lNoticeReady.setUserId(getUserId(token));
			long count = lNoticeReadyService.queryExist(lNoticeReady);
			if(count > 0){
				result.setData("yes");
			}else{
				result.setData("no");
			}
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

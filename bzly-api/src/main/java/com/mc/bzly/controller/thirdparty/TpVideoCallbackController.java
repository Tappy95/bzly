package com.mc.bzly.controller.thirdparty;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mc.bzly.annotation.NeedAuth;
import com.mc.bzly.annotation.NeedToken;
import com.mc.bzly.base.BaseController;
import com.mc.bzly.base.CsjResult;
import com.mc.bzly.base.RespCodeState;
import com.mc.bzly.base.Result;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.model.thirdparty.TpVideoCallback;
import com.mc.bzly.service.thirdparty.TpVideoCallbackService;
@RestController
@RequestMapping("/video")
public class TpVideoCallbackController extends BaseController{
	
	@Value("${csj.syAppSecurityKey}")
	private String syAppSecurityKey;
	@Value("${csj.hbAppSecurityKey}")
	private String hbAppSecurityKey;
	@Value("${csj.bqAppSecurityKey}")
	private String bqAppSecurityKey;
	@Value("${csj.tkAppSecurityKey}")
	private String tkAppSecurityKey;
	@Value("${csj.hbAppSecurityKeyIos}")
	private String hbAppSecurityKeyIos;
	@Value("${csj.tkAppSecurityKeyIos}")
	private String tkAppSecurityKeyIos;
	
	@Reference(version = WebConfig.dubboServiceVersion, interfaceClass = TpVideoCallbackService.class, check = false, timeout=10000)
	private TpVideoCallbackService tpVideoService;
	
	@RequestMapping(value="hbVideoCallback",method=RequestMethod.GET)
	public CsjResult hbVideoCallback(HttpServletRequest request, HttpServletResponse response,TpVideoCallback tpVideoCallback){
		Map<String, String> respMap = new HashMap<String, String>();
		initResponseMap(request, respMap);
		CsjResult result = new CsjResult();
		try{
			tpVideoCallback.setOperateType(1);
			tpVideoCallback.setState(1);
			tpVideoCallback.setReward_amount(0);
			result=tpVideoService.hbVideoCallback(tpVideoCallback,hbAppSecurityKey);
		}catch (Exception e) {
			e.printStackTrace();
			result.setIsValid(false);
		}
		return result;
	}
	
	@RequestMapping(value="hbVideoCallbackIos",method=RequestMethod.GET)
	public CsjResult hbVideoCallbackIos(HttpServletRequest request, HttpServletResponse response,TpVideoCallback tpVideoCallback){
		Map<String, String> respMap = new HashMap<String, String>();
		initResponseMap(request, respMap);
		CsjResult result = new CsjResult();
		try{
			tpVideoCallback.setOperateType(1);
			tpVideoCallback.setState(1);
			tpVideoCallback.setReward_amount(0);
			result=tpVideoService.hbVideoCallback(tpVideoCallback,hbAppSecurityKeyIos);
		}catch (Exception e) {
			e.printStackTrace();
			result.setIsValid(false);
		}
		return result;
	}
	
	@RequestMapping(value="syVideoCallback",method=RequestMethod.GET)
	public CsjResult syVideoCallback(HttpServletRequest request, HttpServletResponse response,TpVideoCallback tpVideoCallback){
		Map<String, String> respMap = new HashMap<String, String>();
		initResponseMap(request, respMap);
		CsjResult result = new CsjResult();
		try{
			tpVideoCallback.setOperateType(2);
			tpVideoCallback.setState(1);
			result=tpVideoService.syVideoCallback(tpVideoCallback,syAppSecurityKey);
		}catch (Exception e) {
			e.printStackTrace();
			result.setIsValid(false);
		}
		return result;
	}
	
	@RequestMapping(value="bqVideoCallback",method=RequestMethod.GET)
	public CsjResult bqVideoCallback(HttpServletRequest request, HttpServletResponse response,TpVideoCallback tpVideoCallback){
		Map<String, String> respMap = new HashMap<String, String>();
		initResponseMap(request, respMap);
		CsjResult result = new CsjResult();
		try{
			tpVideoCallback.setOperateType(3);
			tpVideoCallback.setState(1);
			tpVideoCallback.setReward_amount(0);
			result=tpVideoService.bqVideoCallback(tpVideoCallback,bqAppSecurityKey);
		}catch (Exception e) {
			e.printStackTrace();
			result.setIsValid(false);
		}
		return result;
	}
	
	@NeedToken
	@RequestMapping(value = "videoUser", method = RequestMethod.GET)
	void videoUser(HttpServletRequest request, HttpServletResponse response, String token) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(tpVideoService.videoUser(getUserId(token)));
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
	@NeedToken
	@RequestMapping(value = "videoCount", method = RequestMethod.GET)
	void videoCount(HttpServletRequest request, HttpServletResponse response, String token) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(tpVideoService.videoCount(getUserId(token)));
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
	
	@NeedToken
	@RequestMapping(value = "videoTimeUser", method = RequestMethod.GET)
	void videoTimeUser(HttpServletRequest request, HttpServletResponse response, String token) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(tpVideoService.videoTimeUser(getUserId(token)));
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
	
	@NeedAuth
	@RequestMapping(value = "list", method = RequestMethod.GET)
	void list(HttpServletRequest request, HttpServletResponse response, TpVideoCallback tpVideoCallback) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(tpVideoService.list(tpVideoCallback));
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
	
	@RequestMapping(value="tkAppSecurityKey",method=RequestMethod.GET)
	public CsjResult zcVideoCallback(HttpServletRequest request, HttpServletResponse response,TpVideoCallback tpVideoCallback){
		Map<String, String> respMap = new HashMap<String, String>();
		initResponseMap(request, respMap);
		CsjResult result = new CsjResult();
		try{
			tpVideoCallback.setOperateType(4);
			tpVideoCallback.setState(1);
			result=tpVideoService.tkVideoCallback(tpVideoCallback,tkAppSecurityKey);
		}catch (Exception e) {
			e.printStackTrace();
			result.setIsValid(false);
		}
		return result;
	}
	
	@RequestMapping(value="tkVideoCallbackIos",method=RequestMethod.GET)
	public CsjResult zcVideoCallbackIos(HttpServletRequest request, HttpServletResponse response,TpVideoCallback tpVideoCallback){
		Map<String, String> respMap = new HashMap<String, String>();
		initResponseMap(request, respMap);
		CsjResult result = new CsjResult();
		try{
			tpVideoCallback.setOperateType(4);
			tpVideoCallback.setState(1);
			result=tpVideoService.tkVideoCallback(tpVideoCallback,tkAppSecurityKeyIos);
		}catch (Exception e) {
			e.printStackTrace();
			result.setIsValid(false);
		}
		return result;
	}

	@NeedToken
	@RequestMapping(value = "newUserVideo", method = RequestMethod.GET)
	void newUserVideo(HttpServletRequest request, HttpServletResponse response, String token) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(tpVideoService.newUserVideo(getUserId(token)));
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
	
	@NeedToken
	@RequestMapping(value = "newUserVideoCoin", method = RequestMethod.GET)
	void newUserVideoCoin(HttpServletRequest request, HttpServletResponse response, String token) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(tpVideoService.newUserVideoCoin(getUserId(token)));
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

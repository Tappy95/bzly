package com.mc.bzly.controller.thirdparty;

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
import com.mc.bzly.model.thirdparty.MChannelConfigUser;
import com.mc.bzly.service.thirdparty.MChannelConfigUserService;

@RestController
@RequestMapping("/channelConfigUser")
public class MChannelConfigUserController extends BaseController {


	@Reference(version = WebConfig.dubboServiceVersion, interfaceClass = MChannelConfigUserService.class, check = false, timeout = 10000)
	private MChannelConfigUserService mChannelConfigUserService;

	@NeedAuth
	@RequestMapping(value = "list", method = RequestMethod.GET)
	void page(HttpServletRequest request, HttpServletResponse response, MChannelConfigUser mChannelConfigUser) {
		Map<String, String> respMap = new HashMap<>();
		Result result = new Result();
		initResponseMap(request, respMap);
		try {
			result.setData(mChannelConfigUserService.queryList(mChannelConfigUser));
			result.setSuccess(true);
			result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
			result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
		}
		outStrJSONWithResult(response, result, respMap);
	}

	@NeedAuth
	@RequestMapping(value = "add", method = RequestMethod.POST)
	void add(HttpServletRequest request, HttpServletResponse response, MChannelConfigUser mChannelConfigUser) {
		Map<String, String> respMap = new HashMap<>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try {
			int res=mChannelConfigUserService.add(mChannelConfigUser);
			if(res==1) {
				result.setSuccess(true);
				result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
				result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
			}else {
				result.setSuccess(true);
				result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
				result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
		}

		outStrJSONWithResult(response, result, respMap);
	}

	@NeedAuth
	@RequestMapping(value = "modify", method = RequestMethod.POST)
	void modify(HttpServletRequest request, HttpServletResponse response, MChannelConfigUser mChannelConfigUser) {
		Map<String, String> respMap = new HashMap<>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try {
			int res=mChannelConfigUserService.modify(mChannelConfigUser);
			if(res==1) {
				result.setSuccess(true);
				result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
				result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
			}else {
				result.setSuccess(true);
				result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
				result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
		}
		outStrJSONWithResult(response, result, respMap);
	}

	@NeedAuth
	@RequestMapping(value = "remove", method = RequestMethod.GET)
	void remove(HttpServletRequest request, HttpServletResponse response, Integer id) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			int res = mChannelConfigUserService.remove(id);
			if(res==1) {
				result.setSuccess(true);
				result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
				result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
			}else {
				result.setSuccess(true);
				result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
				result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
		}

		outStrJSONWithResult(response, result, respMap);
	}

	@NeedAuth
	@RequestMapping(value = "queryOne", method = RequestMethod.GET)
	void queryOne(HttpServletRequest request, HttpServletResponse response, Integer id) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(mChannelConfigUserService.queryOne(id));
			result.setSuccess(true);
			result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
			result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
		}
		outStrJSONWithResult(response, result, respMap);
	}
}

package com.mc.bzly.controller.thirdparty;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mc.bzly.util.StringUtil;
import com.mc.bzly.annotation.NeedAuth;
import com.mc.bzly.annotation.NeedToken;
import com.mc.bzly.base.BaseController;
import com.mc.bzly.base.RespCodeState;
import com.mc.bzly.base.Result;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.model.thirdparty.MChannelConfig;
import com.mc.bzly.model.thirdparty.MChannelConfigHt;
import com.mc.bzly.service.thirdparty.MChannelConfigService;

@RestController
@RequestMapping("/mChannelConfig")
public class MChannelConfigController extends BaseController{
	
	@Reference(version = WebConfig.dubboServiceVersion, interfaceClass = MChannelConfigService.class, check = false, timeout = 10000)
	private MChannelConfigService mChannelConfigService;

	@NeedAuth
	@RequestMapping(value = "list", method = RequestMethod.GET)
	void page(HttpServletRequest request, HttpServletResponse response, MChannelConfig mChannelConfig) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(mChannelConfigService.selectList(mChannelConfig));
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
	void add(HttpServletRequest request, HttpServletResponse response, MChannelConfigHt mChannelConfigHt) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			MChannelConfig mChannelConfig=new MChannelConfig();
			mChannelConfig.setChannelCode(mChannelConfigHt.getChannelCode());
			mChannelConfig.setFissionId(mChannelConfigHt.getFissionId());
			mChannelConfig.setChargeMode(mChannelConfigHt.getChargeMode());
			mChannelConfig.setEffectiveObject(mChannelConfigHt.getEffectiveObject());
			mChannelConfig.setOpenGame(mChannelConfigHt.getOpenGame());
			mChannelConfig.setApplyTask(mChannelConfigHt.getApplyTask());
			if(StringUtil.isNullOrEmpty(mChannelConfigHt.getGame28())) {
				mChannelConfig.setGame28(2);
			}else {
				mChannelConfig.setGame28(mChannelConfigHt.getGame28()==false?2:1);
			}
			if(StringUtil.isNullOrEmpty(mChannelConfigHt.getPcdd28())) {
				mChannelConfig.setPcdd28(2);
			}else {
				mChannelConfig.setPcdd28(mChannelConfigHt.getPcdd28()==false?2:1);
			}
			if(StringUtil.isNullOrEmpty(mChannelConfigHt.getJnd28())) {
				mChannelConfig.setJnd28(2);
			}else {
				mChannelConfig.setJnd28(mChannelConfigHt.getJnd28()==false?2:1);
			}
			result.setData(mChannelConfigService.insert(mChannelConfig));
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
	@RequestMapping(value = "modify", method = RequestMethod.POST)
	void modify(HttpServletRequest request, HttpServletResponse response,MChannelConfigHt mChannelConfigHt) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			MChannelConfig mChannelConfig=new MChannelConfig();
			mChannelConfig.setId(mChannelConfigHt.getId());
			mChannelConfig.setChannelCode(mChannelConfigHt.getChannelCode());
			mChannelConfig.setFissionId(mChannelConfigHt.getFissionId());
			mChannelConfig.setChargeMode(mChannelConfigHt.getChargeMode());
			mChannelConfig.setEffectiveObject(mChannelConfigHt.getEffectiveObject());
			mChannelConfig.setOpenGame(mChannelConfigHt.getOpenGame());
			mChannelConfig.setApplyTask(mChannelConfigHt.getApplyTask());
			if(StringUtil.isNullOrEmpty(mChannelConfigHt.getGame28())) {
				mChannelConfig.setGame28(2);
			}else {
				mChannelConfig.setGame28(mChannelConfigHt.getGame28()==false?2:1);
			}
			if(StringUtil.isNullOrEmpty(mChannelConfigHt.getPcdd28())) {
				mChannelConfig.setPcdd28(2);
			}else {
				mChannelConfig.setPcdd28(mChannelConfigHt.getPcdd28()==false?2:1);
			}
			if(StringUtil.isNullOrEmpty(mChannelConfigHt.getJnd28())) {
				mChannelConfig.setJnd28(2);
			}else {
				mChannelConfig.setJnd28(mChannelConfigHt.getJnd28()==false?2:1);
			}
			result.setData(mChannelConfigService.update(mChannelConfig));
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
	@RequestMapping(value = "remove", method = RequestMethod.GET)
	void remove(HttpServletRequest request, HttpServletResponse response, Integer id) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(mChannelConfigService.delete(id));
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
	@RequestMapping(value = "queryOne", method = RequestMethod.GET)
	void queryOne(HttpServletRequest request, HttpServletResponse response, Integer id) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(mChannelConfigService.selectOne(id));
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
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param token
	 * @param id（游戏列表id）
	 * @return 1-关闭 2-打开
	 */
	@NeedToken
	@RequestMapping(value = "hasGameRight", method = RequestMethod.GET)
	void hasGameRight(HttpServletRequest request, HttpServletResponse response,String token,Integer id){
		Map<String, String> respMap = new HashMap<>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try {
			String userId = getUserId(token);
			int res = mChannelConfigService.checkGameRight(userId,id);
			Map<String, Object> resultMaps = new HashMap<>();
			resultMaps.put("res", res);
			result.setData(resultMaps);
			result.setSuccess(true);
			result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
			result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		outStrJSONWithResult(response, result, respMap);
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param token
	 * @param name28
	 * @return 1-关闭 2-打开
	 */
	@NeedToken
	@RequestMapping(value = "has28Right", method = RequestMethod.GET)
	void has28Right(HttpServletRequest request, HttpServletResponse response,String token,String name28){
		Map<String, String> respMap = new HashMap<>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try {
			String userId = getUserId(token);
			int res = mChannelConfigService.check28Right(userId,name28);
			Map<String, Object> resultMaps = new HashMap<>();
			resultMaps.put("res", res);
			result.setData(resultMaps);
			result.setSuccess(true);
			result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
			result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		outStrJSONWithResult(response, result, respMap);
	}
	
	@NeedToken
	@RequestMapping(value = "check28RightNews", method = RequestMethod.GET)
	void check28RightNews(HttpServletRequest request, HttpServletResponse response,String token){
		Map<String, String> respMap = new HashMap<>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try {
			String userId = getUserId(token);
			result.setData(mChannelConfigService.check28RightNews(userId));
			result.setSuccess(true);
			result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
			result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		outStrJSONWithResult(response, result, respMap);
	}
	
	@NeedToken
	@RequestMapping(value = "zjdSwitch", method = RequestMethod.GET)
	void zjdSwitch(HttpServletRequest request, HttpServletResponse response,String token){
		Map<String, String> respMap = new HashMap<>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try {
			String userId = getUserId(token);
			result.setData(mChannelConfigService.zjdSwitch(userId));
			result.setSuccess(true);
			result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
			result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		outStrJSONWithResult(response, result, respMap);
	}
}

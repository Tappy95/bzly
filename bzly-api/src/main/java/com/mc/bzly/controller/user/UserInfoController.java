package com.mc.bzly.controller.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mc.bzly.annotation.NeedAuth;
import com.mc.bzly.annotation.NeedCodeKey;
import com.mc.bzly.annotation.NeedToken;
import com.mc.bzly.base.BaseController;
import com.mc.bzly.base.RespCodeState;
import com.mc.bzly.base.Result;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.model.user.DarenUserVo;
import com.mc.bzly.model.user.MChannelUser;
import com.mc.bzly.model.user.MUserInfo;
import com.mc.bzly.service.user.MUserInfoService;

@RestController
@RequestMapping("/userInfo")
public class UserInfoController extends BaseController {
	
	@Value("${wx.appId}")
	private String appId;
	@Value("${wx.appSecret}")
	private String appSecret;
	
	@Reference(version=WebConfig.dubboServiceVersion,interfaceClass=MUserInfoService.class,check=false,timeout = 10000)
	private MUserInfoService mUserInfoService;
	
	@RequestMapping(value = "mobileExist",method = RequestMethod.GET)
	public void mobileExist(HttpServletRequest request,HttpServletResponse response,String mobile){
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		MUserInfo muserInfo = mUserInfoService.queryByMobile(mobile);
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("mobile", mobile);
		if(muserInfo != null){
			paramMap.put("flag", true);
		}else{
			paramMap.put("flag", false);
		}
		result.setData(paramMap);
		result.setSuccess(true);
		result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
		result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
		outStrJSONWithResult(response, result, respMap);
	}

	@RequestMapping(value = "loginByPassword",method = RequestMethod.GET)
	public void loginByPassword(HttpServletRequest request,HttpServletResponse response,@RequestParam Map<String, Object> paramMap){
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try{
			result = mUserInfoService.loginByPassword(paramMap);
			respMap.put("token", result.getToken());
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
		}
		outStrJSONWithResult(response, result, respMap);
	}

	@NeedCodeKey
	@RequestMapping(value = "loginBySms",method = RequestMethod.GET)
	public void loginBySms(HttpServletRequest request,HttpServletResponse response,@RequestParam Map<String, Object> paramMap){
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try{
			result = mUserInfoService.loginBySms(paramMap);
			respMap.put("token", result.getToken());
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
		}
		outStrJSONWithResult(response, result, respMap);
	}
	
	@NeedCodeKey
	@RequestMapping(value = "reg",method = RequestMethod.POST)
	public void reg(HttpServletRequest request,HttpServletResponse response,MUserInfo mUserInfo){
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			if(StringUtils.isNotEmpty(mUserInfo.getQrCode())){
				Map<String, Object> map =  mUserInfoService.queryByQrCode(mUserInfo.getQrCode());
				if(map == null){
					result.setStatusCode(RespCodeState.REFERRER_NOT_EXIST.getStatusCode());
					result.setMessage(RespCodeState.REFERRER_NOT_EXIST.getMessage());
					outStrJSONWithResult(response, result, respMap);
					return;
				}else{
					String channelCode=mUserInfo.getChannelCode();
					mUserInfo.setChannelCode("");
					mUserInfo.setReferrer((String)map.get("refferrer"));
					if(map.get("channelCode") != null && (!"".equals(map.get("channelCode")))){
						mUserInfo.setParentChannelCode((String)map.get("channelCode") );
					} else if(map.get("parentChannelCode") != null && (!"".equals(map.get("parentChannelCode")))){
						mUserInfo.setParentChannelCode((String)map.get("parentChannelCode"));
					}else{
						mUserInfo.setParentChannelCode(channelCode);
					}
				}
			}
			result = mUserInfoService.addMUserInfo(mUserInfo);
			respMap.put("token", result.getToken());
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatusCode(RespCodeState.USER_REGISTERED_FAILURE.getStatusCode());
			result.setMessage(RespCodeState.USER_REGISTERED_FAILURE.getMessage());
		}
		outStrJSONWithResult(response, result, respMap);
	}
	
	@NeedToken
	@RequestMapping(value = "quit",method = RequestMethod.GET)
	public void quit(HttpServletRequest request,HttpServletResponse response,String token){
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		String v = redisService.getString(token);
		redisService.delete(v);
		redisService.delete(token);
		respMap.put("token", "");
		result.setSuccess(true);
		result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
		result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
		outStrJSONWithResult(response, result, respMap);
	}
	
	@NeedToken
	@RequestMapping(value = "queryBaseInfo",method = RequestMethod.GET)
	public void queryBaseInfo(HttpServletRequest request,HttpServletResponse response,String token){
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			param.put("userId",  getUserId(token));
			result.setData(mUserInfoService.queryUserBaseInfo(param));
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
	@RequestMapping(value = "modifyBaseInfo",method = RequestMethod.POST)
	public void modifyBaseInfo(HttpServletRequest request,HttpServletResponse response,MUserInfo mUserInfo,String token){
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			mUserInfo.setUserId(getUserId(token));
			mUserInfoService.modifyBaseMUserInfo(mUserInfo);
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
	void page(HttpServletRequest request, HttpServletResponse response, MUserInfo mUserInfo) {
		Map<String, String> respMap = new HashMap<>();
		Result result = new Result();
		initResponseMap(request, respMap);
		try{
			result.setData(mUserInfoService.queryListNew(mUserInfo));
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
	void queryOne(HttpServletRequest request, HttpServletResponse response, String userId) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try{
			result.setData(mUserInfoService.queryOne(userId));
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
	
	@RequestMapping(value = "queryByQrCode", method = RequestMethod.GET)
	void queryByQrCode(HttpServletRequest request, HttpServletResponse response, String qrCode) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try{
			result.setData(mUserInfoService.queryByQrCode(qrCode));
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
	 
	@NeedCodeKey
	@RequestMapping(value="resetPassword",method = RequestMethod.POST)
	void resetPassword(HttpServletRequest request, HttpServletResponse response,MUserInfo mUserInfo){
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			if(StringUtils.isEmpty(mUserInfo.getMobile())){
				result.setStatusCode(RespCodeState.MOBILE_NEED.getStatusCode());
				result.setMessage(RespCodeState.MOBILE_NEED.getMessage());
				outStrJSONWithResult(response, result, respMap);
				return;
			}
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("mobile", mUserInfo.getMobile());
			MUserInfo muserInfo = mUserInfoService.queryUserBaseInfo(paramMap);
			if(muserInfo == null){
				result.setStatusCode(RespCodeState.MOBILE_NOT_EXIST.getStatusCode());
				result.setMessage(RespCodeState.MOBILE_NOT_EXIST.getMessage());
				outStrJSONWithResult(response, result, respMap);
				return;
			}
			muserInfo.setPassword(mUserInfo.getPassword());
			result=mUserInfoService.resetPassword(muserInfo);
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
		}
		outStrJSONWithResult(response, result, respMap);
	}
	
	@NeedToken
	@RequestMapping(value="bindQrCode",method = RequestMethod.POST)
	void bindQrCode(HttpServletRequest request, HttpServletResponse response,String token,String qrCode){
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			String userId = getUserId(token);
			result = mUserInfoService.bindQrCode(userId,qrCode);
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
		}
		outStrJSONWithResult(response, result, respMap);
	}
	
	@NeedCodeKey
	@RequestMapping(value = "bindingNumber",method = RequestMethod.GET)
	public void bindingNumber(HttpServletRequest request,HttpServletResponse response,MUserInfo mUserInfo,String token,Integer type){
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			mUserInfo.setUserId(getUserId(token));
			result=mUserInfoService.bindingNumber(mUserInfo, type,appId,appSecret);
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
		}
		outStrJSONWithResult(response, result, respMap);
	}
	
	@NeedCodeKey
	@RequestMapping(value = "modifyBindingNumber",method = RequestMethod.GET)
	public void modifyBindingNumber(HttpServletRequest request,HttpServletResponse response,MUserInfo mUserInfo,String token,Integer type){
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			mUserInfo.setUserId(getUserId(token));
			int num=mUserInfoService.modifyBindingNumber(mUserInfo, type,appId,appSecret);
			if(num==2) {
				result.setSuccess(false);
				result.setStatusCode(RespCodeState.NUM_ERROR.getStatusCode());
				result.setMessage(RespCodeState.NUM_ERROR.getMessage());
			}else if(num==6) {
				result.setSuccess(false);
				result.setStatusCode(RespCodeState.ACCOUNT_ERROR.getStatusCode());
				result.setMessage(RespCodeState.ACCOUNT_ERROR.getMessage());
			}else{
				result.setSuccess(true);
				result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
				result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
		}
		outStrJSONWithResult(response, result, respMap);
	}
	 
	@NeedToken
	@RequestMapping(value = "getUserCoin",method = RequestMethod.GET)
	public void getUserCoin(HttpServletRequest request,HttpServletResponse response,String token){
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(mUserInfoService.getUserCoin(getUserId(token)));
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
	@RequestMapping(value = "getUserPigCoin",method = RequestMethod.GET)
	public void getUserPigCoin(HttpServletRequest request,HttpServletResponse response,String token){
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(mUserInfoService.getUserPigCoin(getUserId(token)));
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
	
	/**
	 * H5注册
	 * @param request
	 * @param response
	 * @param mUserInfo
	 */
	@NeedCodeKey
	@RequestMapping(value = "regH5",method = RequestMethod.GET)
	public void regH5(HttpServletRequest request,HttpServletResponse response,MUserInfo mUserInfo){
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			if(StringUtils.isNotEmpty(mUserInfo.getQrCode())){
				Map<String, Object> map =  mUserInfoService.queryByQrCode(mUserInfo.getQrCode());
				if(map == null){
					result.setStatusCode(RespCodeState.REFERRER_NOT_EXIST.getStatusCode());
					result.setMessage(RespCodeState.REFERRER_NOT_EXIST.getMessage());
					outStrJSONWithResult(response, result, respMap);
					return;
				}else{
					mUserInfo.setReferrer((String)map.get("refferrer"));
					if(map.get("channelCode") != null && (!"".equals(map.get("channelCode")))){
						mUserInfo.setParentChannelCode((String)map.get("channelCode") );
					} else if(map.get("parentChannelCode") != null && (!"".equals(map.get("parentChannelCode")))){
						mUserInfo.setParentChannelCode((String)map.get("parentChannelCode"));
					}else{
						mUserInfo.setParentChannelCode("baozhu");
					}
				}
			}
			result = mUserInfoService.regH5(mUserInfo);
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatusCode(RespCodeState.USER_REGISTERED_FAILURE.getStatusCode());
			result.setMessage(RespCodeState.USER_REGISTERED_FAILURE.getMessage());
		}
		outStrJSONWithResult(response, result, respMap);
	}
	
	@NeedCodeKey
	@NeedToken
	@RequestMapping(value = "updatePayPassword",method = RequestMethod.GET)
	public void updatePayPassword(HttpServletRequest request,HttpServletResponse response,MUserInfo mUserInfo,String token){
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			mUserInfo.setUserId(getUserId(token));
			result.setData(mUserInfoService.updatePayPassword(mUserInfo));
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
	@RequestMapping(value = "channelList", method = RequestMethod.GET)
	void channelList(HttpServletRequest request, HttpServletResponse response, MChannelUser mChannelUser) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try{
			mChannelUser.setChannelCode(respMap.get("channel"));
			mChannelUser.setUserRelation(Integer.parseInt(respMap.get("relation")));
			result.setData(mUserInfoService.selectChannelUser(mChannelUser));
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
	@RequestMapping(value = "channelExclUser", method = RequestMethod.GET)
	void channelExclUser(HttpServletRequest request, HttpServletResponse response, MChannelUser mChannelUser) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try{
			mChannelUser.setChannelCode(respMap.get("channel"));
			mChannelUser.setUserRelation(Integer.parseInt(respMap.get("relation")));
			result.setData(mUserInfoService.selectChannelExclUser(mChannelUser));
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
	
	/*
	 * token
	 * userId
	 * remark
	 */
	@NeedAuth
	@RequestMapping(value = "setSuperPartner", method = RequestMethod.POST)
	void setSuperPartner(HttpServletRequest request, HttpServletResponse response){
		Map<String, String> respMap = new HashMap<>();
		Result result = new Result();
		initResponseMap(request, respMap);
		try{
			int res = mUserInfoService.setSuperPartner(respMap.get("userId"),Integer.parseInt(respMap.get("isSuper")),respMap.get("remark"));
			if(res ==1){
				result.setSuccess(true);
				result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
				result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
			}else if(res == 2){
				result.setStatusCode(RespCodeState.USER_ROLE_ERROR.getStatusCode());
				result.setMessage(RespCodeState.USER_ROLE_ERROR.getMessage());
			}else if(res == 3){
				result.setStatusCode(RespCodeState.USER_STATUS_FREEZE.getStatusCode());
				result.setMessage(RespCodeState.USER_STATUS_FREEZE.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
		}
		outStrJSONWithResult(response, result, respMap);
	}
	
	/*
	 * token
	 * userId
	 * remark
	 */
	@NeedAuth
	@RequestMapping(value = "freezeUser", method = RequestMethod.POST)
	void freezeUser(HttpServletRequest request, HttpServletResponse response){
		Map<String, String> respMap = new HashMap<>();
		Result result = new Result();
		initResponseMap(request, respMap);
		try{
			mUserInfoService.freezeUser(respMap.get("userId"),Integer.parseInt(respMap.get("isSuper")),respMap.get("remark"));
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
	@RequestMapping(value = "selectFrozen", method = RequestMethod.GET)
	void selectFrozen(HttpServletRequest request, HttpServletResponse response){
		Map<String, String> respMap = new HashMap<>();
		Result result = new Result();
		try{
			initResponseMap(request, respMap);
			mUserInfoService.selectFrozen(respMap.get("userId"));
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
	@RequestMapping(value = "extensionStatistic", method = RequestMethod.GET)
	void extensionStatistic(HttpServletRequest request, HttpServletResponse response,String token){
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(mUserInfoService.extensionStatistic(getUserId(token)));
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
	@RequestMapping(value = "userExcl", method = RequestMethod.GET)
	void userExcl(HttpServletRequest request, HttpServletResponse response, MUserInfo mUserInfo) {
		Map<String, String> respMap = new HashMap<>();
		Result result = new Result();
		try{
			initResponseMap(request, respMap);
			result.setData(mUserInfoService.selectExcl(mUserInfo));
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
	
	@RequestMapping(value = "bindingWxNumber",method = RequestMethod.GET)
	public void bindingWxNumber(HttpServletRequest request,HttpServletResponse response,MUserInfo mUserInfo,String token,Integer type){
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			mUserInfo.setUserId(getUserId(token));
			result=mUserInfoService.bindingNumber(mUserInfo, type,appId,appSecret);
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
		}
		outStrJSONWithResult(response, result, respMap);
	}
	
	@RequestMapping(value = "modifyBindingWxNumber",method = RequestMethod.GET)
	public void modifyBindingWxNumber(HttpServletRequest request,HttpServletResponse response,MUserInfo mUserInfo,String token,Integer type){
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			mUserInfo.setUserId(getUserId(token));
			int num=mUserInfoService.modifyBindingWxNumber(mUserInfo, type,appId,appSecret);
			if(num==2) {
				result.setSuccess(false);
				result.setStatusCode(RespCodeState.NUM_ERROR.getStatusCode());
				result.setMessage(RespCodeState.NUM_ERROR.getMessage());
			}else if(num==6) {
				result.setSuccess(false);
				result.setStatusCode(RespCodeState.ACCOUNT_ERROR.getStatusCode());
				result.setMessage(RespCodeState.ACCOUNT_ERROR.getMessage());
			}else if(num==4) {
				result.setSuccess(false);
				result.setStatusCode(RespCodeState.OPENID_ERROR.getStatusCode());
				result.setMessage(RespCodeState.OPENID_ERROR.getMessage());
			}else{
				result.setSuccess(true);
				result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
				result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
		}
		outStrJSONWithResult(response, result, respMap);
	}
	
	@NeedAuth
	@RequestMapping(value = "drList",method = RequestMethod.GET)
	public void drList(HttpServletRequest request,HttpServletResponse response,DarenUserVo darenUserVo){
		Map<String, String> respMap = new HashMap<>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(mUserInfoService.selectDrList(darenUserVo));
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
	@RequestMapping(value = "setDR",method = RequestMethod.POST)
	public void setDR(HttpServletRequest request,HttpServletResponse response,long accountId){
		Map<String, String> respMap = new HashMap<>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try {
			int acc=(int)accountId;
			int res=mUserInfoService.setDR(acc);
			if(res==2) {
				result.setSuccess(true);
				result.setStatusCode(RespCodeState.USER_NO_PIG.getStatusCode());
				result.setMessage(RespCodeState.USER_NO_PIG.getMessage());	
			}else if(res==0) {
				result.setSuccess(true);
				result.setStatusCode(RespCodeState.MOBILE_NOT_EXIST.getStatusCode());
				result.setMessage(RespCodeState.MOBILE_NOT_EXIST.getMessage());	
			}else if(res==3) {
				result.setSuccess(true);
				result.setStatusCode(RespCodeState.USER_YES_DAREN.getStatusCode());
				result.setMessage(RespCodeState.USER_YES_DAREN.getMessage());	
			}else {
				result.setSuccess(true);
				result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
				result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());	
			}
		}catch (Exception e) {
			e.printStackTrace();
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
		}
		outStrJSONWithResult(response, result, respMap);
	}
	
	@NeedAuth
	@RequestMapping(value = "cancleDR",method = RequestMethod.GET)
	public void cancleDR(HttpServletRequest request,HttpServletResponse response,Integer accountId){
		Map<String, String> respMap = new HashMap<>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try {
			mUserInfoService.cancleDR(accountId);
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
	@RequestMapping(value = "drExcel",method = RequestMethod.GET)
	public void drExcel(HttpServletRequest request,HttpServletResponse response,DarenUserVo darenUserVo){
		Map<String, String> respMap = new HashMap<>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try {
			darenUserVo.setPageNum(1);
			darenUserVo.setPageSize(5000);
			result.setData(mUserInfoService.drExcel(darenUserVo));
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
	
	@NeedCodeKey
	@RequestMapping(value = "regChannelH5",method = RequestMethod.GET)
	public void regChannelH5(HttpServletRequest request,HttpServletResponse response,MUserInfo mUserInfo){
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result = mUserInfoService.regH5(mUserInfo);
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatusCode(RespCodeState.USER_REGISTERED_FAILURE.getStatusCode());
			result.setMessage(RespCodeState.USER_REGISTERED_FAILURE.getMessage());
		}
		outStrJSONWithResult(response, result, respMap);
	}
	
	@NeedToken
	@RequestMapping(value = "isUser",method = RequestMethod.GET)
	public void isUser(HttpServletRequest request,HttpServletResponse response,Integer accountId){
		Map<String, String> respMap = new HashMap<>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(mUserInfoService.isUser(accountId));
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
	
	@NeedToken
	@RequestMapping(value = "zjdUser",method = RequestMethod.GET)
	public void zjdUser(HttpServletRequest request,HttpServletResponse response,String token){
		Map<String, String> respMap = new HashMap<>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(mUserInfoService.zjdUser(getUserId(token)));
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
	
	@RequestMapping(value = "checkingMailList",method = RequestMethod.GET)
	public void checkingMailList(HttpServletRequest request,HttpServletResponse response,int addressBook, int callRecord){
		Map<String, String> respMap = new HashMap<>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(mUserInfoService.mailList(addressBook,callRecord));
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
	 * @param wxCode
	 * @param equipmentType
	 * @param imei
	 * @param registrationId
	 */
	@RequestMapping(value = "wechatLogin",method = RequestMethod.GET)
	public void wechatLogin(HttpServletRequest request,HttpServletResponse response,MUserInfo mUserInfo){
		Map<String, String> respMap = new HashMap<>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result = mUserInfoService.wechatLogin(mUserInfo,appId,appSecret);
			respMap.put("token", result.getToken());
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
	 * @param openId
	 * @param imei
	 * @param equipmentType
	 * @param registrationId
	 * @param mobile
	 * @param profile
	 * @param aliasName
	 * @param qrCode
	 */
	@NeedCodeKey
	@RequestMapping(value = "bindMobile",method = RequestMethod.GET)
	public void bindMobile(HttpServletRequest request,HttpServletResponse response,MUserInfo mUserInfo){
		Map<String, String> respMap = new HashMap<>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try {
			/*if(StringUtils.isNotEmpty(mUserInfo.getQrCode())){
				Map<String, Object> map =  mUserInfoService.queryByQrCode(mUserInfo.getQrCode());
				if(map == null){
					result.setStatusCode(RespCodeState.REFERRER_NOT_EXIST.getStatusCode());
					result.setMessage(RespCodeState.REFERRER_NOT_EXIST.getMessage());
					outStrJSONWithResult(response, result, respMap);
					return;
				}else{
					String channelCode=mUserInfo.getChannelCode();
					mUserInfo.setChannelCode("");
					mUserInfo.setReferrer((String)map.get("refferrer"));
					if(map.get("channelCode") != null && (!"".equals(map.get("channelCode")))){
						mUserInfo.setParentChannelCode((String)map.get("channelCode") );
					} else if(map.get("parentChannelCode") != null && (!"".equals(map.get("parentChannelCode")))){
						mUserInfo.setParentChannelCode((String)map.get("parentChannelCode"));
					}else{
						mUserInfo.setParentChannelCode(channelCode);
					}
				}
			}*/
			result = mUserInfoService.bindMobile(mUserInfo);
			respMap.put("token", result.getToken());
		}catch (Exception e) {
			e.printStackTrace();
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
		}
		outStrJSONWithResult(response, result, respMap);
	}
	/**
	 * 心愿猪app注册
	 * @param request
	 * @param response
	 * @param mUserInfo
	 */
	@NeedCodeKey
	@RequestMapping(value = "regWish",method = RequestMethod.POST)
	public void regWish(HttpServletRequest request,HttpServletResponse response,MUserInfo mUserInfo){
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			if(StringUtils.isNotEmpty(mUserInfo.getQrCode())){
				Map<String, Object> map =  mUserInfoService.queryByQrCode(mUserInfo.getQrCode());
				if(map == null){
					result.setStatusCode(RespCodeState.REFERRER_NOT_EXIST.getStatusCode());
					result.setMessage(RespCodeState.REFERRER_NOT_EXIST.getMessage());
					outStrJSONWithResult(response, result, respMap);
					return;
				}else{
					String channelCode=mUserInfo.getChannelCode();
					mUserInfo.setChannelCode("");
					mUserInfo.setReferrer((String)map.get("refferrer"));
					if(map.get("channelCode") != null && (!"".equals(map.get("channelCode")))){
						mUserInfo.setParentChannelCode((String)map.get("channelCode") );
					} else if(map.get("parentChannelCode") != null && (!"".equals(map.get("parentChannelCode")))){
						mUserInfo.setParentChannelCode((String)map.get("parentChannelCode"));
					}else{
						mUserInfo.setParentChannelCode(channelCode);
					}
				}
			}
			result = mUserInfoService.addWishMUserInfo(mUserInfo);
			respMap.put("token", result.getToken());
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatusCode(RespCodeState.USER_REGISTERED_FAILURE.getStatusCode());
			result.setMessage(RespCodeState.USER_REGISTERED_FAILURE.getMessage());
		}
		outStrJSONWithResult(response, result, respMap);
	}
	
	/**
	 * 心愿猪H5注册
	 * @param request
	 * @param response
	 * @param mUserInfo
	 */
	@NeedCodeKey
	@RequestMapping(value = "regWishH5",method = RequestMethod.GET)
	public void regWishH5(HttpServletRequest request,HttpServletResponse response,MUserInfo mUserInfo){
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			if(StringUtils.isNotEmpty(mUserInfo.getQrCode())){
				Map<String, Object> map =  mUserInfoService.queryByQrCode(mUserInfo.getQrCode());
				if(map == null){
					result.setStatusCode(RespCodeState.REFERRER_NOT_EXIST.getStatusCode());
					result.setMessage(RespCodeState.REFERRER_NOT_EXIST.getMessage());
					outStrJSONWithResult(response, result, respMap);
					return;
				}else{
					mUserInfo.setReferrer((String)map.get("refferrer"));
					if(map.get("channelCode") != null && (!"".equals(map.get("channelCode")))){
						mUserInfo.setParentChannelCode((String)map.get("channelCode") );
					} else if(map.get("parentChannelCode") != null && (!"".equals(map.get("parentChannelCode")))){
						mUserInfo.setParentChannelCode((String)map.get("parentChannelCode"));
					}else{
						mUserInfo.setParentChannelCode("wishbz");
					}
				}
			}
			result = mUserInfoService.regWishH5(mUserInfo);
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatusCode(RespCodeState.USER_REGISTERED_FAILURE.getStatusCode());
			result.setMessage(RespCodeState.USER_REGISTERED_FAILURE.getMessage());
		}
		outStrJSONWithResult(response, result, respMap);
	}
	
	/**
	 * 心愿猪绑定手机号
	 * @param request
	 * @param response
	 * @param mUserInfo
	 */
	@NeedCodeKey
	@RequestMapping(value = "bindWishMobile",method = RequestMethod.GET)
	public void bindWishMobile(HttpServletRequest request,HttpServletResponse response,MUserInfo mUserInfo){
		Map<String, String> respMap = new HashMap<>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result = mUserInfoService.bindWishMobile(mUserInfo);
			respMap.put("token", result.getToken());
		}catch (Exception e) {
			e.printStackTrace();
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
		}
		outStrJSONWithResult(response, result, respMap);
	}
	
	@RequestMapping(value = "loginWishByPassword",method = RequestMethod.GET)
	public void loginWishByPassword(HttpServletRequest request,HttpServletResponse response,@RequestParam Map<String, Object> paramMap){
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try{
			result = mUserInfoService.loginWishByPassword(paramMap);
			respMap.put("token", result.getToken());
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
		}
		outStrJSONWithResult(response, result, respMap);
	}
	
	@NeedCodeKey
	@RequestMapping(value = "loginWishBySms",method = RequestMethod.GET)
	public void loginWishBySms(HttpServletRequest request,HttpServletResponse response,@RequestParam Map<String, Object> paramMap){
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try{
			result = mUserInfoService.loginWishBySms(paramMap);
			respMap.put("token", result.getToken());
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
		}
		outStrJSONWithResult(response, result, respMap);
	}
	/**
	 * 心愿猪微信登录
	 * @param request
	 * @param response
	 * @param mUserInfo
	 */
	@RequestMapping(value = "wechatWishLogin",method = RequestMethod.GET)
	public void wechatWishLogin(HttpServletRequest request,HttpServletResponse response,MUserInfo mUserInfo){
		Map<String, String> respMap = new HashMap<>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result = mUserInfoService.wechatWishLogin(mUserInfo,appId,appSecret);
			respMap.put("token", result.getToken());
		}catch (Exception e) {
			e.printStackTrace();
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
		}
		outStrJSONWithResult(response, result, respMap);
	}
	/**
	 * 心愿猪app修改密码
	 * @param request
	 * @param response
	 * @param mUserInfo
	 */
	@NeedCodeKey
	@RequestMapping(value="resetWishPassword",method = RequestMethod.POST)
	void resetWishPassword(HttpServletRequest request, HttpServletResponse response,MUserInfo mUserInfo){
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			if(StringUtils.isEmpty(mUserInfo.getMobile())){
				result.setStatusCode(RespCodeState.MOBILE_NEED.getStatusCode());
				result.setMessage(RespCodeState.MOBILE_NEED.getMessage());
				outStrJSONWithResult(response, result, respMap);
				return;
			}
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("mobile", mUserInfo.getMobile());
			MUserInfo muserInfo = mUserInfoService.queryUserBaseInfo(paramMap);
			if(muserInfo == null){
				result.setStatusCode(RespCodeState.MOBILE_NOT_EXIST.getStatusCode());
				result.setMessage(RespCodeState.MOBILE_NOT_EXIST.getMessage());
				outStrJSONWithResult(response, result, respMap);
				return;
			}
			muserInfo.setPassword(mUserInfo.getPassword());
			result=mUserInfoService.resetWishPassword(muserInfo);
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
		}
		outStrJSONWithResult(response, result, respMap);
	}
}

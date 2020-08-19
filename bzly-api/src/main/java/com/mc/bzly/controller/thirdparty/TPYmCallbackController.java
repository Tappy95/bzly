package com.mc.bzly.controller.thirdparty;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mc.bzly.util.StringUtil;
import com.bzly.common.utils.MD5Util;
import com.mc.bzly.annotation.NeedAuth;
import com.mc.bzly.base.BaseController;
import com.mc.bzly.base.RespCodeState;
import com.mc.bzly.base.Result;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.base.YmResult;
import com.mc.bzly.model.thirdparty.TPYmCallback;
import com.mc.bzly.model.thirdparty.XWCallback;
import com.mc.bzly.service.thirdparty.TPYmCallbackService;

@RestController
@RequestMapping("/ymCallback")
public class TPYmCallbackController extends BaseController{
	
	@Value("${ym.baseCallbackSalt}")
	private String baseCallbackSalt;
	
	@Reference(version = WebConfig.dubboServiceVersion, interfaceClass = TPYmCallbackService.class, check = false, timeout=10000)
	private TPYmCallbackService tpYmCallbackService;
	
	@RequestMapping(value="callback",method=RequestMethod.GET)
	public YmResult callback(HttpServletRequest request, HttpServletResponse response,TPYmCallback tPYmCallback){
		YmResult result = new YmResult();
		try {
			 LocalDate now =LocalDate.now(ZoneId.of("Asia/Shanghai"));
			 String salt =MD5Util.getMd5(baseCallbackSalt+now.format(DateTimeFormatter.BASIC_ISO_DATE));
			 String sign=MD5Util.getMd5(tPYmCallback.getUserId() + tPYmCallback.getCoinCount() + salt +tPYmCallback.getTs());
			 if(sign.equals(tPYmCallback.getSign())) {
				 TPYmCallback old=tpYmCallbackService.selectCallbackId(tPYmCallback.getCoinCallbackId());
				 if(StringUtil.isNullOrEmpty(old)) {
					 tPYmCallback.setCallbackTime(tPYmCallback.getTs());
					 tpYmCallbackService.callback(tPYmCallback);
					 result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());//操作成功
					 result.setCode("0");
				 }else {
					 result.setMessage(RespCodeState.CALLBACK_HAS_FINISH.getMessage());//订单已完成
					 result.setCode("1");
				 }
			 }else {
				 result.setMessage(RespCodeState.SIGN_ERROR.getMessage());//验签失败
				 result.setCode("1");
			 }
		}catch (Exception e) {
			e.printStackTrace();
			result.setCode("1");
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
		}
		return result;
	}

	@NeedAuth
	@RequestMapping(value = "list", method = RequestMethod.GET)
	void page(HttpServletRequest request, HttpServletResponse response, TPYmCallback tPYmCallback) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(tpYmCallbackService.page(tPYmCallback));
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
	@RequestMapping(value = "reSend", method = RequestMethod.GET)
	void resend(HttpServletRequest request, HttpServletResponse response, String coinCallbackId) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			// 查询最后一条该订单处理失败的数据
			TPYmCallback tPYmCallback=tpYmCallbackService.selectCallbackId(coinCallbackId);
			if(tPYmCallback == null){
				result.setStatusCode(RespCodeState.CALLBACK_NOT_EXIST.getStatusCode());
				result.setMessage(RespCodeState.CALLBACK_NOT_EXIST.getMessage());
			}else{
				if(tPYmCallback.getState().intValue() == 1){
					result.setStatusCode(RespCodeState.CALLBACK_HAS_FINISH.getStatusCode());
					result.setMessage(RespCodeState.CALLBACK_HAS_FINISH.getMessage());
				}else{
					LocalDate now =LocalDate.now(ZoneId.of("Asia/Shanghai"));
					 String salt =MD5Util.getMd5(baseCallbackSalt+now.format(DateTimeFormatter.BASIC_ISO_DATE));
					 String sign=MD5Util.getMd5(tPYmCallback.getUserId() + tPYmCallback.getCoinCount() + salt +tPYmCallback.getCallbackTime());
					if (StringUtils.isEmpty(tPYmCallback.getSign())){ // 判断sign是否存在
						result.setStatusCode(RespCodeState.SIGN_ERROR.getStatusCode());
						result.setMessage(RespCodeState.SIGN_ERROR.getMessage());
					}else if(!sign.equals(tPYmCallback.getSign())){ // 判断sign是否正确
						result.setStatusCode(RespCodeState.SIGN_ERROR.getStatusCode());
						result.setMessage(RespCodeState.SIGN_ERROR.getMessage());
					}else{ // 处理结果
						tpYmCallbackService.callback(tPYmCallback);
						result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
						result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
		}
		outStrJSONWithResult(response, result, respMap);
	}
}

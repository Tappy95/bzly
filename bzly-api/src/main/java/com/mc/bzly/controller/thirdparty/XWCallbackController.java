package com.mc.bzly.controller.thirdparty;

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
import com.bzly.common.utils.MD5Util;
import com.mc.bzly.annotation.NeedAuth;
import com.mc.bzly.base.BaseController;
import com.mc.bzly.base.RespCodeState;
import com.mc.bzly.base.Result;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.model.thirdparty.XWCallback;
import com.mc.bzly.service.thirdparty.TPGameService;
import com.mc.bzly.service.thirdparty.XWCallbackService;

@RestController
@RequestMapping("/XWCallback")
public class XWCallbackController extends BaseController{
	
	@Value("${xw.appid}")
	private String xwAppid;
	@Value("${xw.appsecret}")
	private String xwAppsecret;
	
	@Reference(version = WebConfig.dubboServiceVersion, interfaceClass = XWCallbackService.class, check = false, timeout=10000)
	private XWCallbackService xWCallbackService;
	@Reference(version = WebConfig.dubboServiceVersion, interfaceClass = TPGameService.class, check = false, timeout=10000)
	private TPGameService tpGameService;
	
	@NeedAuth
	@RequestMapping(value = "list", method = RequestMethod.GET)
	void page(HttpServletRequest request, HttpServletResponse response, XWCallback xwCallback) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(xWCallbackService.page(xwCallback));
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
	void resend(HttpServletRequest request, HttpServletResponse response, String ordernum) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			// 查询最后一条该订单处理失败的数据
			XWCallback xwCallback = xWCallbackService.queryByOrdernum(ordernum);
			if(xwCallback == null){
				result.setStatusCode(RespCodeState.CALLBACK_NOT_EXIST.getStatusCode());
				result.setMessage(RespCodeState.CALLBACK_NOT_EXIST.getMessage());
			}else{
				if(xwCallback.getStatus().intValue() == 1){
					result.setStatusCode(RespCodeState.CALLBACK_HAS_FINISH.getStatusCode());
					result.setMessage(RespCodeState.CALLBACK_HAS_FINISH.getMessage());
				}else{
					int adid = xwCallback.getAdid();
					int dlevel = xwCallback.getDlevel();
					String deviceid = xwCallback.getDeviceid();
					String appsign = xwCallback.getAppsign();
					String price = xwCallback.getPrice();
					String money = xwCallback.getMoney();
					String sign = MD5Util.getMd5(adid+xwAppid+ordernum+dlevel+deviceid+appsign+price+money+xwAppsecret).toUpperCase();
					if (StringUtils.isEmpty(xwCallback.getKeycode())){ // 判断keycode是否存在
						result.setStatusCode(RespCodeState.SIGN_ERROR.getStatusCode());
						result.setMessage(RespCodeState.SIGN_ERROR.getMessage());
					}else if(!sign.equals(xwCallback.getKeycode())){ // 判断keycode是否正确
						result.setStatusCode(RespCodeState.SIGN_ERROR.getStatusCode());
						result.setMessage(RespCodeState.SIGN_ERROR.getMessage());
					}else{ // 处理结果
						XWCallback old = tpGameService.queryByXWOrderNum(ordernum);
						if(old != null){ // 判断是否已经处理了订单
							result.setStatusCode(RespCodeState.CALLBACK_HAS_FINISH.getStatusCode());
							result.setMessage(RespCodeState.CALLBACK_HAS_FINISH.getMessage());
						}else{
							tpGameService.sendXW(xwCallback);
							result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
							result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
						}
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
	
	@NeedAuth
	@RequestMapping(value = "xyzList", method = RequestMethod.GET)
	void xyzList(HttpServletRequest request, HttpServletResponse response, XWCallback xwCallback) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(xWCallbackService.xyzPage(xwCallback));
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

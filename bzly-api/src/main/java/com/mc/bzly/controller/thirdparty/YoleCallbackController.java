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
import com.mc.bzly.model.thirdparty.YoleCallback;
import com.mc.bzly.service.thirdparty.TPGameService;
import com.mc.bzly.service.thirdparty.YoleCallbackService;

@RestController
@RequestMapping("/YoleCallback")
public class YoleCallbackController extends BaseController{

	@Value("${yole.key}")
	private String yoleKey;
	
	@Reference(version = WebConfig.dubboServiceVersion, interfaceClass = YoleCallbackService.class, check = false, timeout=10000)
	private YoleCallbackService yoleCallbackService;
	@Reference(version = WebConfig.dubboServiceVersion, interfaceClass = TPGameService.class, check = false, timeout=10000)
	private TPGameService tpGameService;
	
	@NeedAuth
	@RequestMapping(value = "list", method = RequestMethod.GET)
	void page(HttpServletRequest request, HttpServletResponse response, YoleCallback yoleCallback) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(yoleCallbackService.page(yoleCallback));
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
	void resend(HttpServletRequest request, HttpServletResponse response, String yoleid) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			// 查询最后一条该订单处理失败的数据
			YoleCallback yoleCallback = yoleCallbackService.queryByYoleid(yoleid);
			if(yoleCallback == null){
				result.setStatusCode(RespCodeState.CALLBACK_NOT_EXIST.getStatusCode());
				result.setMessage(RespCodeState.CALLBACK_NOT_EXIST.getMessage());
			}else{
				if(yoleCallback.getStatus().intValue() == 1){
					result.setStatusCode(RespCodeState.CALLBACK_HAS_FINISH.getStatusCode());
					result.setMessage(RespCodeState.CALLBACK_HAS_FINISH.getMessage());
				}else{
					String sign = yoleCallback.getSign();
					String userid = yoleCallback.getUserid();
					String appid = yoleCallback.getAppid();
					String gmgold = yoleCallback.getGmgold();
					String chgold = yoleCallback.getChgold();
					String md5Sign = MD5Util.getMd5(yoleid + userid + appid + gmgold + chgold + yoleKey);
					if (StringUtils.isEmpty(sign)) {
						result.setStatusCode(RespCodeState.SIGN_ERROR.getStatusCode());
						result.setMessage(RespCodeState.SIGN_ERROR.getMessage());
					} else if (!sign.equals(md5Sign)) {
						result.setStatusCode(RespCodeState.SIGN_ERROR.getStatusCode());
						result.setMessage(RespCodeState.SIGN_ERROR.getMessage());
					} else {
						YoleCallback old = tpGameService.queryByYoLeId(yoleid);
						if(old != null){ // 判断是否已经处理了订单
							result.setStatusCode(RespCodeState.CALLBACK_HAS_FINISH.getStatusCode());
							result.setMessage(RespCodeState.CALLBACK_HAS_FINISH.getMessage());
						}else{
							tpGameService.sendYOLE(yoleCallback);
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

}

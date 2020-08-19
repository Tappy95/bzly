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
import com.mc.bzly.model.thirdparty.BZCallback;
import com.mc.bzly.model.thirdparty.XWCallback;
import com.mc.bzly.service.thirdparty.BZCallbackService;
import com.mc.bzly.service.thirdparty.TPGameService;

@RestController
@RequestMapping("/BZCallback")
public class BZCallbackController extends BaseController{
	
	@Value("${xw.appid}")
	private String xwAppid;
	@Value("${xw.appsecret}")
	private String xwAppsecret;
	
	//宝猪任务平台游戏
	@Value("${bz.userChannel}")
	private String userChannel;
	@Value("${bz.appKey}")
	private String bzAppKey;
	
	@Reference(version = WebConfig.dubboServiceVersion, interfaceClass = BZCallbackService.class, check = false, timeout=10000)
	private BZCallbackService bZCallbackService;
	@Reference(version = WebConfig.dubboServiceVersion, interfaceClass = TPGameService.class, check = false, timeout=10000)
	private TPGameService tpGameService;
	
	@NeedAuth
	@RequestMapping(value = "list", method = RequestMethod.GET)
	void page(HttpServletRequest request, HttpServletResponse response, BZCallback bzCallback) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(bZCallbackService.page(bzCallback));
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
			BZCallback bzCallback = bZCallbackService.queryByOrdernum(ordernum);
			if(bzCallback == null){
				result.setStatusCode(RespCodeState.CALLBACK_NOT_EXIST.getStatusCode());
				result.setMessage(RespCodeState.CALLBACK_NOT_EXIST.getMessage());
			}else{
				if(bzCallback.getStatus().intValue() == 1){
					result.setStatusCode(RespCodeState.CALLBACK_HAS_FINISH.getStatusCode());
					result.setMessage(RespCodeState.CALLBACK_HAS_FINISH.getMessage());
				}else{
					String deviceid = bzCallback.getDeviceid();
					String appsign = bzCallback.getAppsign();
					String sign = MD5Util.getMd5(userChannel+ordernum+deviceid+appsign+bzAppKey);
					if (StringUtils.isEmpty(bzCallback.getKeycode())){ // 判断keycode是否存在
						result.setStatusCode(RespCodeState.SIGN_ERROR.getStatusCode());
						result.setMessage(RespCodeState.SIGN_ERROR.getMessage());
					}else if(!sign.equals(bzCallback.getKeycode())){ // 判断keycode是否正确
						result.setStatusCode(RespCodeState.SIGN_ERROR.getStatusCode());
						result.setMessage(RespCodeState.SIGN_ERROR.getMessage());
					}else{ // 处理结果
						BZCallback old = tpGameService.queryByBZOrderNum(ordernum);
						if(old != null){ // 判断是否已经处理了订单
							result.setStatusCode(RespCodeState.CALLBACK_HAS_FINISH.getStatusCode());
							result.setMessage(RespCodeState.CALLBACK_HAS_FINISH.getMessage());
						}else{
							tpGameService.sendBZ(bzCallback);
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

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
import com.mc.bzly.model.thirdparty.PCDDCallback;
import com.mc.bzly.service.thirdparty.PCDDCallbackService;
import com.mc.bzly.service.thirdparty.TPGameService;

@RestController
@RequestMapping("/PCDDCallback")
public class PCDDCallbackController extends BaseController{
	
	@Value("${pcdd.pid}")
	private String pcddPID;
	@Value("${pcdd.appkey}")
	private String pcddAppkey;
	@Value("${pcdd.baseUrl}")
	private String pcddBaseUrl;
	
	@Reference(version = WebConfig.dubboServiceVersion, interfaceClass = PCDDCallbackService.class, check = false, timeout=10000)
	private PCDDCallbackService pCDDCallbackService;
	
	@Reference(version = WebConfig.dubboServiceVersion, interfaceClass = TPGameService.class, check = false, timeout=10000)
	private TPGameService tpGameService;
	
	@NeedAuth
	@RequestMapping(value = "list", method = RequestMethod.GET)
	void page(HttpServletRequest request, HttpServletResponse response, PCDDCallback pcDDCallback) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(pCDDCallbackService.page(pcDDCallback));
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
			PCDDCallback pcDDCallback = pCDDCallbackService.queryByOrdernum(ordernum);
			if(pcDDCallback == null){
				result.setStatusCode(RespCodeState.CALLBACK_NOT_EXIST.getStatusCode());
				result.setMessage(RespCodeState.CALLBACK_NOT_EXIST.getMessage());
			}else{
				if(pcDDCallback.getStatus().intValue() == 2){
					result.setStatusCode(RespCodeState.CALLBACK_HAS_FINISH.getStatusCode());
					result.setMessage(RespCodeState.CALLBACK_HAS_FINISH.getMessage());
				}else{
					int adid = pcDDCallback.getAdid();
					String imei = pcDDCallback.getDeviceid();
					String md5Sign = MD5Util.getMd5(adid+pcddPID+ordernum+imei+pcddAppkey);
					if (StringUtils.isEmpty(pcDDCallback.getKeycode())) {  
						result.setMessage(RespCodeState.SIGN_ERROR.getMessage());
						result.setStatusCode(RespCodeState.SIGN_ERROR.getStatusCode());
					} else if (!pcDDCallback.getKeycode().equals(md5Sign)){
						result.setMessage(RespCodeState.SIGN_ERROR.getMessage());
						result.setStatusCode(RespCodeState.SIGN_ERROR.getStatusCode());
					}else{  
						// 判断是否已经领取
						PCDDCallback old = tpGameService.queryByPCDDOrderNum(ordernum);
						if(old != null){ // 判断是否已经处理了订单
							result.setStatusCode(RespCodeState.CALLBACK_HAS_FINISH.getStatusCode());
							result.setMessage(RespCodeState.CALLBACK_HAS_FINISH.getMessage());
						}else{
							tpGameService.sendPCDD(pcDDCallback);
							result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
							result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
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

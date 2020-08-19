package com.mc.bzly.controller.push;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mc.bzly.model.sms.SSmsLog;
import com.mc.bzly.service.sms.ValidateCodeService;
import com.mc.bzly.base.BaseController;
import com.mc.bzly.base.IPUtil;
import com.mc.bzly.base.Result;
import com.mc.bzly.base.WebConfig;

@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController extends BaseController {

	@Reference(version = WebConfig.dubboServiceVersion, interfaceClass = ValidateCodeService.class, check = false, timeout = 10000)
	private ValidateCodeService validateCodeService;

	@RequestMapping(value = "/validateSmsCode", produces = WebConfig.jsonMimeType)
	public void validateSmsCode(HttpServletRequest request, HttpServletResponse response, SSmsLog smsLog) {
		smsLog.setIp(IPUtil.getIp(request));
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		result.setData(validateCodeService.validateSmsCode(smsLog));
		result.setSuccess(true);
		outStrJSONWithResult(response, result, respMap);
	}

}

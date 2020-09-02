package com.mc.bzly.controller.user;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bzly.common.utils.StringUtil;
import com.mc.bzly.annotation.NeedToken;
import com.mc.bzly.base.BaseController;
import com.mc.bzly.base.IPUtil;
import com.mc.bzly.base.RespCodeState;
import com.mc.bzly.base.Result;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.model.user.LUserActive;
import com.mc.bzly.service.user.LUserActiveService;

@RestController
@RequestMapping("/uerActive")
public class LUserActiveController extends BaseController {

	@Reference(version=WebConfig.dubboServiceVersion,interfaceClass=LUserActiveService.class,check=false,timeout=3000)
	private LUserActiveService lUserActiveService;
	
	private static SimpleDateFormat sdf5 = new SimpleDateFormat("yyyy-MM-dd");
	
	@NeedToken
	@RequestMapping(value = "add",method = RequestMethod.POST)
	void add(HttpServletRequest request,HttpServletResponse response,String token){
		Map<String, String> respMap = new HashMap<>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try {
			String now = sdf5.format(new Date());
			String ip = IPUtil.getIp(request);
			String userId = getUserId(token);
			synchronized (userId) {
				LUserActive lUserActive=lUserActiveService.selectOne(userId, now);
				if(StringUtil.isNullOrEmpty(lUserActive)) {
					LUserActive userAction=new LUserActive();
					userAction.setUserId(userId);
					userAction.setActiveTime(now);
					userAction.setActiveIp(ip);
					lUserActiveService.insert(userAction);
				}
			}
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

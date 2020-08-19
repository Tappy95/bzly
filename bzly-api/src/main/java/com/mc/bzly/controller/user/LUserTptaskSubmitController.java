package com.mc.bzly.controller.user;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.bzly.common.utils.Base64;
import com.mc.bzly.annotation.NeedToken;
import com.mc.bzly.base.BaseController;
import com.mc.bzly.base.RespCodeState;
import com.mc.bzly.base.Result;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.model.user.LUserTptaskSubmit;
import com.mc.bzly.service.user.LUserTptaskSubmitService;

@RestController
@RequestMapping("/userTptaskSubmit")
public class LUserTptaskSubmitController extends BaseController {
	
	@Reference(version=WebConfig.dubboServiceVersion,interfaceClass=LUserTptaskSubmitService.class,check=false,timeout = 10000)
	private LUserTptaskSubmitService lUserTptaskSubmitService;
	
	@NeedToken
	@RequestMapping(value = "add", method = RequestMethod.GET)
	void add(HttpServletRequest request, HttpServletResponse response, String token,Integer tpTaskId,Integer lTpTaskId) {
		Map<String, String> respMap = new HashMap<>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try {
			String userId = getUserId(token);
			String submits = respMap.get("submits");
			byte[] submit = Base64.decode(submits);
			submits = new String(submit);
			submits = URLDecoder.decode(submits,"utf-8");
			List<LUserTptaskSubmit> lUserTptaskSubmits = JSON.parseArray(submits, LUserTptaskSubmit.class);
			if(lUserTptaskSubmits.size() >0){
				result = lUserTptaskSubmitService.add(lUserTptaskSubmits, userId,tpTaskId,lTpTaskId);
				/*if(res == 2){
					result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
					result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
				}else if(res == 1){
					result.setMessage(RespCodeState.EXPIRE_TIME_OUT.getMessage());
					result.setStatusCode(RespCodeState.EXPIRE_TIME_OUT.getStatusCode());
				}*/
			}else {
				result.setMessage(RespCodeState.DATA_IS_EMPTY.getMessage());
				result.setStatusCode(RespCodeState.DATA_IS_EMPTY.getStatusCode());
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
		}
		outStrJSONWithResult(response, result, respMap);
	}
}

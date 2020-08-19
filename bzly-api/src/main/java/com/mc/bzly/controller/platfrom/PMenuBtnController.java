package com.mc.bzly.controller.platfrom;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mc.bzly.annotation.NeedAuth;
import com.mc.bzly.base.BaseController;
import com.mc.bzly.base.RespCodeState;
import com.mc.bzly.base.Result;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.model.platform.PAdmin;
import com.mc.bzly.model.platform.PMenuBtn;
import com.mc.bzly.service.platform.PMenuBtnService;

@RestController
@RequestMapping("/pMenuBtn")
public class PMenuBtnController extends BaseController {

	@Reference(version = WebConfig.dubboServiceVersion, interfaceClass = PMenuBtnService.class, check = false, timeout = 10000)
	private PMenuBtnService pMenuBtnService;

	@NeedAuth
	@RequestMapping(value = "list", method = RequestMethod.GET)
	void page(HttpServletRequest request, HttpServletResponse response, PMenuBtn pMenuBtn) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(pMenuBtnService.queryList(pMenuBtn));
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
	void add(HttpServletRequest request, HttpServletResponse response, PMenuBtn pMenuBtn) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(pMenuBtnService.add(pMenuBtn));
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
	@RequestMapping(value = "remove", method = RequestMethod.GET)
	void remove(HttpServletRequest request, HttpServletResponse response, Integer id) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(pMenuBtnService.remove(id));
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
	@RequestMapping(value = "queryBtns", method = RequestMethod.GET)
	void queryBtns(HttpServletRequest request, HttpServletResponse response, String token, Integer menuId) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			String tokenValue = redisService.getString(token);
			PAdmin pAdmin = (PAdmin) redisService.getObject(tokenValue);
			result.setData(pMenuBtnService.queryBtns(pAdmin.getRoleId(), menuId));
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

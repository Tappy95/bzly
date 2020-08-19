package com.mc.bzly.controller.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mc.bzly.annotation.NeedAuth;
import com.mc.bzly.annotation.NeedToken;
import com.mc.bzly.base.BaseController;
import com.mc.bzly.base.RespCodeState;
import com.mc.bzly.base.Result;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.model.user.LUserSignGame;
import com.mc.bzly.model.user.LUserSignin;
import com.mc.bzly.service.user.LUserSigninService;

@RestController
@RequestMapping("/userSignin")
public class LUserSigninController extends BaseController {
	
	@Reference(version=WebConfig.dubboServiceVersion,interfaceClass=LUserSigninService.class,check=false,timeout = 10000)
	private LUserSigninService lUserSignInService;
	
	@NeedToken
	@RequestMapping(value = "init", method = RequestMethod.GET)
	void list(HttpServletRequest request, HttpServletResponse response, LUserSignin lUserSignin) {
		Map<String, String> respMap = new HashMap<>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try {
			lUserSignin.setUserId(getUserId(respMap.get("token")));
			result = lUserSignInService.init(lUserSignin);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
		}
		outStrJSONWithResult(response, result, respMap);
	}

	@NeedToken
	@RequestMapping(value = "receiveReward", method = RequestMethod.GET)
	void receiveReward(HttpServletRequest request, HttpServletResponse response, LUserSignin lUserSignin) {
		Map<String, String> respMap = new HashMap<>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try {
			lUserSignin.setUserId(getUserId(respMap.get("token")));
			result = lUserSignInService.receiveReward(lUserSignin);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
		}
		outStrJSONWithResult(response, result, respMap);
	}

	@NeedToken
	@RequestMapping(value = "todayFinish", method = RequestMethod.GET)
	void todayFinish(HttpServletRequest request, HttpServletResponse response,String token) {
		Map<String, String> respMap = new HashMap<>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try {
			String userId = getUserId(token);
			result.setData(lUserSignInService.todayFinish(userId));
			result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
			result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
		} catch (Exception e) {
			e.printStackTrace();
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
		}
		outStrJSONWithResult(response, result, respMap);
	}
	
	@NeedToken
	@RequestMapping(value = "signinGames", method = RequestMethod.GET)
	public void signinGames(HttpServletRequest request, HttpServletResponse response,String token,Integer signinId){
		Map<String, String> respMap = new HashMap<>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try {
			String equipmentType = respMap.get("equipmentType"); // 1-安卓 2-iOS
			int ptype=new Integer(equipmentType);
			if(ptype == 2){
				ptype = 1;
			}else {
				ptype = 2;
			}
			String userId = getUserId(token);
			result.setData(lUserSignInService.signinGames(ptype,userId,signinId));
			result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
			result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
		} catch (Exception e) {
			e.printStackTrace();
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
		}
		outStrJSONWithResult(response, result, respMap);
	}
	
	@NeedAuth
	@RequestMapping(value = "pageList", method = RequestMethod.GET)
	void pageList(HttpServletRequest request, HttpServletResponse response, LUserSignin lUserSignin) {
		Map<String, String> respMap = new HashMap<>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(lUserSignInService.pageList(lUserSignin));
			result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
			result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
		} catch (Exception e) {
			e.printStackTrace();
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
		}
		outStrJSONWithResult(response, result, respMap);
	}
	
	@NeedAuth
	@RequestMapping(value = "gameList", method = RequestMethod.GET)
	void gameList(HttpServletRequest request, HttpServletResponse response, LUserSignGame lUserSignGame) {
		Map<String, String> respMap = new HashMap<>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(lUserSignInService.signinGameList(lUserSignGame));
			result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
			result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
		} catch (Exception e) {
			e.printStackTrace();
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
		}
		outStrJSONWithResult(response, result, respMap);
	}
	
	@NeedAuth
	@RequestMapping(value = "info", method = RequestMethod.GET)
	void info(HttpServletRequest request, HttpServletResponse response,Integer id) {
		Map<String, String> respMap = new HashMap<>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(lUserSignInService.selectOne(id));
			result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
			result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
		} catch (Exception e) {
			e.printStackTrace();
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
		}
		outStrJSONWithResult(response, result, respMap);
	}
	
	@NeedAuth
	@RequestMapping(value = "update", method = RequestMethod.POST)
	void update(HttpServletRequest request, HttpServletResponse response,LUserSignin lUserSignin) {
		Map<String, String> respMap = new HashMap<>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try {
			lUserSignInService.modify(lUserSignin);
			result.setData(1);
			result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
			result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
		} catch (Exception e) {
			e.printStackTrace();
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
		}
		outStrJSONWithResult(response, result, respMap);
	}
	
	@NeedAuth
	@RequestMapping(value = "deleteGame", method = RequestMethod.GET)
	void deleteGame(HttpServletRequest request, HttpServletResponse response,Integer id) {
		Map<String, String> respMap = new HashMap<>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(lUserSignInService.deleteGame(id));
			result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
			result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
		} catch (Exception e) {
			e.printStackTrace();
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
		}
		outStrJSONWithResult(response, result, respMap);
	}
}

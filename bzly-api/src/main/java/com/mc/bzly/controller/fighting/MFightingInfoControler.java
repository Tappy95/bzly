package com.mc.bzly.controller.fighting;

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
import com.mc.bzly.model.fighting.AnswerStart;
import com.mc.bzly.model.fighting.AnswerUser;
import com.mc.bzly.model.fighting.LFightingInfo;
import com.mc.bzly.model.fighting.MFightingInfo;
import com.mc.bzly.service.fighting.MFightingInfoService;

@RestController
@RequestMapping("/mFightingInfo")
public class MFightingInfoControler extends BaseController {

	@Reference(version = WebConfig.dubboServiceVersion, interfaceClass = MFightingInfoService.class, check = false, timeout = 10000)
	private MFightingInfoService mFightingInfoService;

	@NeedAuth
	@RequestMapping(value = "list", method = RequestMethod.GET)
	void list(HttpServletRequest request, HttpServletResponse response, MFightingInfo mFightingInfo) {
		Map<String, String> respMap = new HashMap<>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(mFightingInfoService.pageList(mFightingInfo));
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

	@NeedToken
	@RequestMapping(value = "foundRoom", method = RequestMethod.GET)
	void foundRoom(HttpServletRequest request, HttpServletResponse response, MFightingInfo mFightingInfo,
			String token) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			mFightingInfo.setInitiator(getUserId(token));
			Map<String, Object> data = mFightingInfoService.foundRoom(mFightingInfo);
			if ("2".equals(data.get("res"))) {
				result.setSuccess(false);
				result.setStatusCode(RespCodeState.COIN_INSUFFICIENT.getStatusCode());
				result.setMessage(RespCodeState.COIN_INSUFFICIENT.getMessage());
			} else {
				result.setData(data);
				result.setSuccess(true);
				result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
				result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
		}
		outStrJSONWithResult(response, result, respMap);

	}

	@NeedToken
	@RequestMapping(value = "joinRoom", method = RequestMethod.GET)
	void joinRoom(HttpServletRequest request, HttpServletResponse response, MFightingInfo mFightingInfo,
			String token) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			mFightingInfo.setDefense(getUserId(token));
			Map<String, Object> data = mFightingInfoService.joinRoom(mFightingInfo);
			if ((Integer) data.get("res") == 3) {
				result.setSuccess(false);
				result.setStatusCode(RespCodeState.ROOM_NO.getStatusCode());
				result.setMessage(RespCodeState.ROOM_NO.getMessage());
			} else if ((Integer) data.get("res") == 2) {
				result.setSuccess(false);
				result.setStatusCode(RespCodeState.COIN_INSUFFICIENT.getStatusCode());
				result.setMessage(RespCodeState.COIN_INSUFFICIENT.getMessage());
			}else if ((Integer) data.get("res") == 5) {
				result.setSuccess(false);
				result.setStatusCode(RespCodeState.ROOM_MAX.getStatusCode());
				result.setMessage(RespCodeState.ROOM_MAX.getMessage());
			}else {
				result.setData(data);
				result.setSuccess(true);
				result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
				result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
		}
		outStrJSONWithResult(response, result, respMap);

	}

	@NeedToken
	@RequestMapping(value = "cancelRoom", method = RequestMethod.GET)
	void cancelRoom(HttpServletRequest request, HttpServletResponse response, String entryCode) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(mFightingInfoService.cancelRoom(entryCode));
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

	@NeedToken
	@RequestMapping(value = "seeRoom", method = RequestMethod.GET)
	void seeRoom(HttpServletRequest request, HttpServletResponse response, String entryCode) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(mFightingInfoService.seeRoot(entryCode));
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

	@NeedToken
	@RequestMapping(value = "startFighting", method = RequestMethod.GET)
	void startFighting(HttpServletRequest request, HttpServletResponse response, String entryCode) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(mFightingInfoService.startFighting(entryCode));
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

	@NeedToken
	@RequestMapping(value = "getStart", method = RequestMethod.GET)
	void getStart(HttpServletRequest request, HttpServletResponse response, MFightingInfo mFightingInfo) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(mFightingInfoService.isStart(mFightingInfo));
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

	@NeedToken
	@RequestMapping(value = "getQuestion", method = RequestMethod.GET)
	void getQuestion(HttpServletRequest request, HttpServletResponse response, AnswerStart answerStart) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(mFightingInfoService.getQuestion(answerStart));
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

	@NeedToken
	@RequestMapping(value = "subResult", method = RequestMethod.GET)
	void subResult(HttpServletRequest request, HttpServletResponse response, LFightingInfo lFightingInfo,
			String token) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			lFightingInfo.setUserId(getUserId(token));
			LFightingInfo info = mFightingInfoService.subResult(lFightingInfo);
			if (info == null) {
				result.setSuccess(false);
				result.setStatusCode(RespCodeState.SUBNITTED.getStatusCode());
				result.setMessage(RespCodeState.SUBNITTED.getMessage());
			} else {
				result.setData(info);
				result.setSuccess(true);
				result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
				result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
		}
		outStrJSONWithResult(response, result, respMap);
	}

	@NeedToken
	@RequestMapping(value = "getEnemyScore", method = RequestMethod.GET)
	void getEnemyScore(HttpServletRequest request, HttpServletResponse response, LFightingInfo lFightingInfo) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(mFightingInfoService.getEnemyScore(lFightingInfo));
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

	@NeedToken
	@RequestMapping(value = "luckDraw", method = RequestMethod.GET)
	void luckDraw(HttpServletRequest request, HttpServletResponse response, Integer fightId) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(mFightingInfoService.luckDraw(fightId));
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

	@NeedToken
	@RequestMapping(value = "partakeAnswer", method = RequestMethod.GET)
	void partakeAnswer(HttpServletRequest request, HttpServletResponse response, AnswerUser answerUser,
			String token) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			answerUser.setUserId(getUserId(token));
			Integer res = mFightingInfoService.partakeAnswer(answerUser);
			if (res == 0) {
				result.setData(res);
				result.setSuccess(false);
				result.setStatusCode(RespCodeState.COIN_INSUFFICIENT.getStatusCode());
				result.setMessage(RespCodeState.COIN_INSUFFICIENT.getMessage());
			}else if(res == 4){
				result.setData(res);
				result.setSuccess(false);
				result.setStatusCode(RespCodeState.COIN_RANGE.getStatusCode());
				result.setMessage(RespCodeState.COIN_RANGE.getMessage());
			}else {
				result.setData(res);
				result.setSuccess(true);
				result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
				result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
		}
		outStrJSONWithResult(response, result, respMap);
	}

	@NeedToken
	@RequestMapping(value = "randomUser", method = RequestMethod.GET)
	void randomUser(HttpServletRequest request, HttpServletResponse response, String token, Double time) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(mFightingInfoService.randomUser(getUserId(token), time));
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

	@NeedToken
	@RequestMapping(value = "cancelMatching", method = RequestMethod.GET)
	void cancelMatching(HttpServletRequest request, HttpServletResponse response, String token) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(mFightingInfoService.cancelMatching(getUserId(token)));
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
	 
	@NeedToken
	@RequestMapping(value = "getNotRoom", method = RequestMethod.GET)
	void getNotRoom(HttpServletRequest request, HttpServletResponse response, String entryCode) {
		Map<String, String> respMap = new HashMap<>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try{
			result.setData(mFightingInfoService.getNotRoom(entryCode));
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

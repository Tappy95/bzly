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
import com.mc.bzly.model.user.LUserExchangeCash;
import com.mc.bzly.model.user.LuserCashGameStatistic;
import com.mc.bzly.service.user.LUserExchangeCashService;

@RestController
@RequestMapping("/lUserExchangeCash")
public class LUserExchangeCashController extends BaseController{
	
	@Reference(version = WebConfig.dubboServiceVersion, interfaceClass = LUserExchangeCashService.class, check = false, timeout = 10000)
	private LUserExchangeCashService lUserExchangeCashService;
	
	@NeedAuth
	@RequestMapping(value = "list", method = RequestMethod.GET)
	void list(HttpServletRequest request, HttpServletResponse response,LUserExchangeCash lUserExchangeCash,String token) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);Result result = new Result();
		try {
			lUserExchangeCash.setAdmin(getAdmin(token).getMobile());
			lUserExchangeCash.setChannel(respMap.get("channel"));
			lUserExchangeCash.setUserRelation(Integer.parseInt(respMap.get("relation")));
			Map<String, Object> data=lUserExchangeCashService.selectList(lUserExchangeCash);
			if("2".equals(data.get("res"))) {
				result.setStatusCode(RespCodeState.MOBILE_NOT_EXIST.getStatusCode());
				result.setMessage(RespCodeState.MOBILE_NOT_EXIST.getMessage());
			}else {
				result.setData(data);
				result.setSuccess(true);
				result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
				result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());	
			}
		} catch (Exception e) {
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
			e.printStackTrace();
		}
		outStrJSONWithResult(response, result, respMap);
	}

	@NeedAuth
	@RequestMapping(value = "updateLocking", method = RequestMethod.GET)
	void updateLocking(HttpServletRequest request, HttpServletResponse response,LUserExchangeCash lUserExchangeCash,String token) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);Result result = new Result();
		try {
			lUserExchangeCash.setLockingMobile(getAdmin(token).getMobile());
			int res=lUserExchangeCashService.updateLocking(lUserExchangeCash);
			if(res==2) {
				result.setSuccess(false);
				result.setStatusCode(RespCodeState.ALREADY_LOCKING.getStatusCode());
				result.setMessage(RespCodeState.ALREADY_LOCKING.getMessage());
			}else {
				result.setSuccess(true);
				result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
				result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
			}
		} catch (Exception e) {
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
			e.printStackTrace();
		}
		outStrJSONWithResult(response, result, respMap);
	}
	
	@NeedAuth
	@RequestMapping(value = "updateLockingList", method = RequestMethod.GET)
	void updateLockingList(HttpServletRequest request, HttpServletResponse response,String ids,String token) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);Result result = new Result();
		try {
			int res=lUserExchangeCashService.updateLockingList(ids,getAdmin(token).getMobile());
			if(res==2) {
				result.setSuccess(false);
				result.setStatusCode(RespCodeState.ALREADY_LOCKING.getStatusCode());
				result.setMessage(RespCodeState.ALREADY_LOCKING.getMessage());
			}else {
				result.setSuccess(true);
				result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
				result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
			}
		} catch (Exception e) {
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
			e.printStackTrace();
		}
		outStrJSONWithResult(response, result, respMap);
	}

	@NeedAuth
	@RequestMapping(value = "relieveLocking", method = RequestMethod.GET)
	void relieveLocking(HttpServletRequest request, HttpServletResponse response,Integer id,String token) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);Result result = new Result();
		try {
			int res=lUserExchangeCashService.relieveLocking(id,getAdmin(token).getMobile());
			if(res==2) {
				result.setSuccess(false);
				result.setStatusCode(RespCodeState.RELIEVE_LOCKING.getStatusCode());
				result.setMessage(RespCodeState.RELIEVE_LOCKING.getMessage());
			}else {
				result.setSuccess(true);
				result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
				result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
			}
		} catch (Exception e) {
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
			e.printStackTrace();
		}
		outStrJSONWithResult(response, result, respMap);
	}
	
	@NeedAuth
	@RequestMapping(value = "info", method = RequestMethod.GET)
	void info(HttpServletRequest request, HttpServletResponse response,Integer id) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);Result result = new Result();
		try {
			result.setData(lUserExchangeCashService.selectInfo(id));
			result.setSuccess(true);
			result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
			result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());	
		} catch (Exception e) {
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
			e.printStackTrace();
		}
		outStrJSONWithResult(response, result, respMap);
	}
	@NeedAuth
	@RequestMapping(value = "update", method = RequestMethod.GET)
	void update(HttpServletRequest request, HttpServletResponse response,LUserExchangeCash lUserExchangeCash,String token) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);Result result = new Result();
		try {
			lUserExchangeCash.setOperatorMobile(getAdmin(token).getMobile());
			int res = lUserExchangeCashService.update(lUserExchangeCash);
			if(res == 1){
				result.setSuccess(true);
				result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
				result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
			}else if(res == 0){
				result.setSuccess(false);
				result.setStatusCode("3024");
				result.setMessage("变更方式不是提现");
			}else if(res == 2){
				result.setSuccess(false);
				result.setStatusCode("3024");
				result.setMessage("该记录已审核");
			}else if(res == 3){
				result.setSuccess(false);
				result.setStatusCode("3024");
				result.setMessage("请选择审核状态");
			}
		} catch (Exception e) {
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
			e.printStackTrace();
		}
		outStrJSONWithResult(response, result, respMap);
	}
	
	@NeedAuth
	@RequestMapping(value = "exclCash", method = RequestMethod.GET)
	void exclCash(HttpServletRequest request, HttpServletResponse response,LUserExchangeCash lUserExchangeCash,String token) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);Result result = new Result();
		try {
			lUserExchangeCash.setAdmin(getAdmin(token).getMobile());
			lUserExchangeCash.setChannel(respMap.get("channel"));
			lUserExchangeCash.setUserRelation(Integer.parseInt(respMap.get("relation")));
			result.setData(lUserExchangeCashService.selectExcl(lUserExchangeCash));
			result.setSuccess(true);
			result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
			result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
		} catch (Exception e) {
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
			e.printStackTrace();
		}
		outStrJSONWithResult(response, result, respMap);
	}
	
	@NeedToken
	@RequestMapping(value = "h5List", method = RequestMethod.GET)
	void h5List(HttpServletRequest request, HttpServletResponse response,LUserExchangeCash lUserExchangeCash,String token) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);Result result = new Result();
		try {
			lUserExchangeCash.setUserId(getUserId(token));
			result.setData(lUserExchangeCashService.selectH5List(lUserExchangeCash));
			result.setSuccess(true);
			result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
			result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());	
		} catch (Exception e) {
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
			e.printStackTrace();
		}
		outStrJSONWithResult(response, result, respMap);
	}
	
	@NeedAuth
	@RequestMapping(value = "cashGameStatistic", method = RequestMethod.GET)
	void cashGameStatistic(HttpServletRequest request, HttpServletResponse response,LuserCashGameStatistic luserCashGameStatistic) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);Result result = new Result();
		try {
			luserCashGameStatistic.setChannel(respMap.get("channel"));
			luserCashGameStatistic.setUserRelation(Integer.parseInt(respMap.get("relation")));
			Map<String, Object> data=lUserExchangeCashService.selectCashGame(luserCashGameStatistic);
			if("2".equals(data.get("res"))) {
				result.setStatusCode(RespCodeState.MOBILE_NOT_EXIST.getStatusCode());
				result.setMessage(RespCodeState.MOBILE_NOT_EXIST.getMessage());
			}else {
				result.setData(data);
				result.setSuccess(true);
				result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
				result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());	
			}
		} catch (Exception e) {
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
			e.printStackTrace();
		}
		outStrJSONWithResult(response, result, respMap);
	}
	
	@NeedAuth
	@RequestMapping(value = "channelStatisticExcl", method = RequestMethod.GET)
	void channelStatisticExcl(HttpServletRequest request, HttpServletResponse response,LuserCashGameStatistic luserCashGameStatistic) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);Result result = new Result();
		try {
			luserCashGameStatistic.setChannel(respMap.get("channel"));
			luserCashGameStatistic.setUserRelation(Integer.parseInt(respMap.get("relation")));
			luserCashGameStatistic.setPageNum(1);
			luserCashGameStatistic.setPageSize(5000);
			
			result.setData(lUserExchangeCashService.channelStatisticExcl(luserCashGameStatistic));
			result.setSuccess(true);
			result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
			result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());	
			
		} catch (Exception e) {
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
			e.printStackTrace();
		}
		outStrJSONWithResult(response, result, respMap);
	}
}

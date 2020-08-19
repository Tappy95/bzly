package com.mc.bzly.controller.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bzly.common.utils.MD5Util;
import com.mc.bzly.annotation.NeedAuth;
import com.mc.bzly.annotation.NeedCodeKey;
import com.mc.bzly.annotation.NeedToken;
import com.mc.bzly.base.BaseController;
import com.mc.bzly.base.IPUtil;
import com.mc.bzly.base.RespCodeState;
import com.mc.bzly.base.Result;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.model.user.LCoinChange;
import com.mc.bzly.model.user.UserWithdrawals;
import com.mc.bzly.service.user.LCoinChangeService;

@RestController
@RequestMapping("/lCoinChange")
public class LCoinChangeControler extends BaseController {

	@Reference(version=WebConfig.dubboServiceVersion,interfaceClass=LCoinChangeService.class,check=false,timeout = 10000)
	private LCoinChangeService lCoinChangeService;
	
	@NeedToken
	@RequestMapping(value = "list", method = RequestMethod.GET)
	void list(HttpServletRequest request, HttpServletResponse response,LCoinChange lCoinChange,String token) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);Result result = new Result();
		try {
			lCoinChange.setUserId(getUserId(token));
			result.setData(lCoinChangeService.queryList(lCoinChange));
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
	@RequestMapping(value = "page", method = RequestMethod.GET)
	void page(HttpServletRequest request, HttpServletResponse response,LCoinChange lCoinChange) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);Result result = new Result();
		try {
			lCoinChange.setChannelCode(respMap.get("channel"));
			lCoinChange.setUserRelation(Integer.parseInt(respMap.get("relation")));
			Map<String, Object> data=lCoinChangeService.selectList(lCoinChange);
			if("2".equals(data.get("res"))) {
				result.setStatusCode(RespCodeState.MOBILE_NOT_EXIST.getStatusCode());
				result.setMessage(RespCodeState.MOBILE_NOT_EXIST.getMessage());
			}else {
				result.setData(data);
				result.setSuccess(true);
				result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
				result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());	
			}
		}catch (Exception e) {
			e.printStackTrace();
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
		}
		outStrJSONWithResult(response, result, respMap);
	}
	
	@NeedAuth
	@RequestMapping(value = "info", method = RequestMethod.GET)
	void info(HttpServletRequest request, HttpServletResponse response,Integer id) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);Result result = new Result();
		try {
			result.setData(lCoinChangeService.info(id));
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
	
	@NeedCodeKey
	@NeedToken
	@RequestMapping(value="withdrawalsApply",method = RequestMethod.GET)
	void withdrawalsApply(HttpServletRequest request, HttpServletResponse response,LCoinChange lCoinChange,String token){
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			lCoinChange.setUserIp(IPUtil.getIp(request));
			if(lCoinChange.getAmount() <=0){
				result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
				result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
			}else{
				String equipmentType = respMap.get("equipmentType"); // 1-安卓 2-iOS
				int ptype = "1".equals(equipmentType)?2:1;
				lCoinChange.setUserId(getUserId(token));
				result = lCoinChangeService.withdrawalsApply(lCoinChange,ptype);
			}
		} catch (Exception e) {
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
			e.printStackTrace();
		}
		outStrJSONWithResult(response, result, respMap);
	}
	
	@NeedAuth
	@RequestMapping(value="queryWithdrawals",method = RequestMethod.GET)
	void queryWithdrawals(HttpServletRequest request, HttpServletResponse response,UserWithdrawals userWithdrawals){
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(lCoinChangeService.queryWithdrawals(userWithdrawals));
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
	@RequestMapping(value="queryWithdrawalsInfo",method = RequestMethod.GET)
	void queryWithdrawalsInfo(HttpServletRequest request, HttpServletResponse response,Integer id){
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setSuccess(true);
			result.setData(lCoinChangeService.queryWithdrawalsInfo(id));
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
	@RequestMapping(value="audit",method = RequestMethod.GET)
	void audit(HttpServletRequest request, HttpServletResponse response,LCoinChange lCoinChange){
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		int res = lCoinChangeService.audit(lCoinChange);
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
		}
		outStrJSONWithResult(response, result, respMap);
	}
	@NeedToken
	@RequestMapping(value="coinExchangePig",method = RequestMethod.GET)
	void coinExchangePig(HttpServletRequest request, HttpServletResponse response,long pig,String token){
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			String userId=getUserId(token);
			int res = lCoinChangeService.coinExchangePig(pig, userId);
			if(res == 3){
				result.setSuccess(true);
				result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
				result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
			}else if(res == 1){
				result.setSuccess(false);
				result.setStatusCode(RespCodeState.EXCHANGE_10_MUL.getStatusCode());
				result.setMessage(RespCodeState.EXCHANGE_10_MUL.getMessage());
			}else{
				result.setSuccess(false);
				result.setStatusCode(RespCodeState.USER_BALANCE_NOT_ENOUGH.getStatusCode());
				result.setMessage(RespCodeState.USER_BALANCE_NOT_ENOUGH.getMessage());
			}
		}catch (Exception e) {
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
			e.printStackTrace();
		}
		outStrJSONWithResult(response, result, respMap);
	}
	
	/*@NeedToken
	@RequestMapping(value="readZXReward",method = RequestMethod.POST)
	void readZXReward(HttpServletRequest request, HttpServletResponse response){
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			String userId=getUserId(respMap.get("token"));
			int res = lCoinChangeService.readZXReward(userId);
			if(res == 1){
				result.setStatusCode(RespCodeState.READ_ZX_COIN_REWARD_LIMIT.getStatusCode());
				result.setMessage(RespCodeState.READ_ZX_COIN_REWARD_LIMIT.getMessage());
			}else if(res == 2){
				result.setSuccess(true);
				result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
				result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
			}
		}catch (Exception e) {
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
			e.printStackTrace();
		}
		outStrJSONWithResult(response, result, respMap);
	}*/
	
	/*@NeedToken
	@RequestMapping(value="readNewZXReward",method = RequestMethod.POST)
	void readNewZXReward(HttpServletRequest request, HttpServletResponse response,String token,String sign,Long time){
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			String userId=getUserId(token);
			String md5=MD5Util.getMd5(token+"bzlyInformationMd5"+time);
			if(!md5.equals(sign)) {
				result.setSuccess(true);
				result.setStatusCode(RespCodeState.OPERATION_FREQUENTLY.getStatusCode());
				result.setMessage(RespCodeState.OPERATION_FREQUENTLY.getMessage());
			}else {
				int res = lCoinChangeService.readNewZXReward(userId,time);
				if(res == 1){
					result.setStatusCode(RespCodeState.READ_ZX_COIN_REWARD_LIMIT.getStatusCode());
					result.setMessage(RespCodeState.READ_ZX_COIN_REWARD_LIMIT.getMessage());
				}else if(res == 2){
					result.setSuccess(true);
					result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
					result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
				}else {
					result.setSuccess(true);
					result.setStatusCode(RespCodeState.OPERATION_FREQUENTLY.getStatusCode());
					result.setMessage(RespCodeState.OPERATION_FREQUENTLY.getMessage());
				}
			}
		}catch (Exception e) {
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
			e.printStackTrace();
		}
		outStrJSONWithResult(response, result, respMap);
	}*/
	
	/*@NeedToken
	@RequestMapping(value="getZXConifg",method = RequestMethod.GET)
	void getZXConifg(HttpServletRequest request, HttpServletResponse response){
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			String userId=getUserId(respMap.get("token"));
			result.setData(lCoinChangeService.getZXConifg(userId));
			result.setSuccess(true);
			result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
			result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
		}
		outStrJSONWithResult(response, result, respMap);
	}*/
	
	@NeedToken
	@RequestMapping(value = "getApprenticeReward", method = RequestMethod.GET)
	void getApprenticeReward(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			String userId = getUserId(respMap.get("token"));
			result.setData(lCoinChangeService.getApprenticeReward(userId));
			result.setSuccess(true);
			result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
			result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		outStrJSONWithResult(response, result, respMap);
	}

	@NeedToken
	@RequestMapping(value = "getApprenticeRewardDetail", method = RequestMethod.GET)
	void getApprenticeRewardDetail(HttpServletRequest request, HttpServletResponse response,LCoinChange lCoinChange) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			String userId = getUserId(respMap.get("token"));
			lCoinChange.setUserId(userId);
			result.setData(lCoinChangeService.getApprenticeRewardDetail(lCoinChange));
			result.setSuccess(true);
			result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
			result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		outStrJSONWithResult(response, result, respMap);
	}
	
	@NeedAuth
	@RequestMapping(value = "exclCoinChange", method = RequestMethod.GET)
	void exclCoinChange(HttpServletRequest request, HttpServletResponse response,LCoinChange lCoinChange) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);Result result = new Result();
		try {
			lCoinChange.setChannelCode(respMap.get("channel"));
			lCoinChange.setUserRelation(Integer.parseInt(respMap.get("relation")));
			result.setData(lCoinChangeService.exclCoinChange(lCoinChange));
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
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param token
	 * @param sign
	 * @param time
	 * @param type 13-阅读资讯 19-阅读广告 20-分享内容
	 */
	/*@NeedToken
	@RequestMapping(value="readReward",method = RequestMethod.POST)
	void readReward(HttpServletRequest request, HttpServletResponse response,String token,String sign,Long time,Integer type,String content){
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			String userId=getUserId(token);
			String md5=MD5Util.getMd5(token+"bzlyInformationMd5"+time);
			if(!md5.equals(sign)) {
				result.setSuccess(true);
				result.setStatusCode(RespCodeState.OPERATION_FREQUENTLY.getStatusCode());
				result.setMessage(RespCodeState.OPERATION_FREQUENTLY.getMessage());
			}else {
				int res = lCoinChangeService.readReward(userId,time,type,content);
				if(res == 1){
					result.setStatusCode(RespCodeState.READ_ZX_COIN_REWARD_LIMIT.getStatusCode());
					result.setMessage(RespCodeState.READ_ZX_COIN_REWARD_LIMIT.getMessage());
				}else if(res == 2){
					result.setSuccess(true);
					result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
					result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
				}else {
					result.setSuccess(true);
					result.setStatusCode(RespCodeState.OPERATION_FREQUENTLY.getStatusCode());
					result.setMessage(RespCodeState.OPERATION_FREQUENTLY.getMessage());
				}
			}
		}catch (Exception e) {
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
			e.printStackTrace();
		}
		outStrJSONWithResult(response, result, respMap);
	}*/
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param type 13-阅读资讯 19-阅读广告
	 */
	/*@NeedToken
	@RequestMapping(value="getRewardConifg",method = RequestMethod.GET)
	void getZXConifg(HttpServletRequest request, HttpServletResponse response,Integer type,String content){
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			String userId=getUserId(respMap.get("token"));
			result.setData(lCoinChangeService.getRewardConifg(userId,type,content));
			result.setSuccess(true);
			result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
			result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
		}
		outStrJSONWithResult(response, result, respMap);
	}*/
	
	@NeedToken
	@RequestMapping(value="cashNum",method = RequestMethod.GET)
	void cashNum(HttpServletRequest request, HttpServletResponse response,String token){
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			Map<String,Object> data=new HashMap<>();
			data.put("cashNum", lCoinChangeService.cashNum(getUserId(token)));
			result.setData(data);
			result.setSuccess(true);
			result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
			result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
		}
		outStrJSONWithResult(response, result, respMap);
	}
	
	/**
	 * 小游戏奖励配置
	 * @param request
	 * @param response
	 * @param token
	 * @param seconds
	 * @param sign
	 */
	@NeedToken
	@RequestMapping(value="xyxRewardConfig",method = RequestMethod.GET)
	void xyxRewardConfig(HttpServletRequest request, HttpServletResponse response,String token){
		Map<String, String> respMap = new HashMap<>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try {
			String userId=getUserId(respMap.get("token"));
			result.setData(lCoinChangeService.xyxRewardConifg(userId));
			result.setSuccess(true);
			result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
			result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
		}
		outStrJSONWithResult(response, result, respMap);
	}
	
	/**
	 * 获取小游戏奖励
	 * @param request
	 * @param response
	 * @param token
	 * @param seconds
	 * @param sign
	 * @param time
	 */
	@NeedToken
	@RequestMapping(value="xyxReward",method = RequestMethod.GET)
	void xyxReward(HttpServletRequest request, HttpServletResponse response,String token,Long time,String sign,Long seconds){
		Map<String, String> respMap = new HashMap<>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try {
			String md5=MD5Util.getMd5(token+"bzlyInformationMd5"+time);
			if(!md5.equals(sign)) {
				result.setSuccess(true);
				result.setStatusCode(RespCodeState.OPERATION_FREQUENTLY.getStatusCode());
				result.setMessage(RespCodeState.OPERATION_FREQUENTLY.getMessage());
			}
			String userId = getUserId(token);
			result = lCoinChangeService.getXYXReward(userId,seconds);
		}catch (Exception e) {
			e.printStackTrace();
		}
		outStrJSONWithResult(response, result, respMap);
	}
	/**
	 * 心愿猪app用户提现
	 * @param request
	 * @param response
	 * @param lCoinChange
	 * @param token
	 */
	@NeedToken
	@RequestMapping(value="wishWithdrawalsApply",method = RequestMethod.GET)
	void wishWithdrawalsApply(HttpServletRequest request, HttpServletResponse response,LCoinChange lCoinChange,String token){
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			lCoinChange.setUserIp(IPUtil.getIp(request));
			if(lCoinChange.getAmountM() <=0){
				result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
				result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
			}else{
				lCoinChange.setUserId(getUserId(token));
				result = lCoinChangeService.wishCash(lCoinChange);
			}
		} catch (Exception e) {
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
			e.printStackTrace();
		}
		outStrJSONWithResult(response, result, respMap);
	}
	
	@NeedToken
	@RequestMapping(value = "listHighVip", method = RequestMethod.GET)
	void listHighVip(HttpServletRequest request, HttpServletResponse response,Integer pageSize,Integer pageNum,String token) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);Result result = new Result();
		try {
			String userId = getUserId(token);
			result.setData(lCoinChangeService.listHighVip(userId,pageSize,pageNum));
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

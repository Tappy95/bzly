package com.mc.bzly.controller.lottery;

import java.util.Date;
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
import com.mc.bzly.model.lottery.MLotteryOrder;
import com.mc.bzly.model.user.MUserInfo;
import com.mc.bzly.service.lottery.MLotteryOrderService;

@RestController
@RequestMapping("/mLotteryOrder")
public class MLotteryOrderController extends BaseController{
	
	@Reference(version=WebConfig.dubboServiceVersion,interfaceClass=MLotteryOrderService.class,check=false,timeout=10000)
	private MLotteryOrderService mLotteryOrderService;
	
	@NeedAuth
	@RequestMapping(value = "list", method = RequestMethod.GET)
	void page(HttpServletRequest request, HttpServletResponse response, MLotteryOrder mLotteryOrder,String token) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			mLotteryOrder.setAdmin(getAdmin(token).getMobile());;
			Map<String, Object> data=mLotteryOrderService.queryList(mLotteryOrder);
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
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
			e.printStackTrace();
		}
		outStrJSONWithResult(response, result, respMap);
	}
	
	/**
	 * 用户抽奖
	 * @param request
	 * @param response
	 * @param token
	 * @param imei
	 * @param typeId
	 */
	@NeedToken
	@RequestMapping(value="clickLottery",method = RequestMethod.GET)
	void clickLottery(HttpServletRequest request, HttpServletResponse response, MLotteryOrder mLotteryOrder,String password) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			long now = new Date().getTime();
			MUserInfo mUserInfo = (MUserInfo)redisService.getObject(respMap.get("token"));
			mLotteryOrder.setUserId(mUserInfo.getUserId());
			mLotteryOrder.setAccountId(mUserInfo.getAccountId());
			mLotteryOrder.setCreateTime(now);
			mLotteryOrder.setUpdateTime(now);
			mLotteryOrder.setStatus(1);
			result = mLotteryOrderService.clickLottery(mLotteryOrder,password);
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
		}
		outStrJSONWithResult(response, result, respMap);
	}

	@NeedAuth
	@RequestMapping(value="modify",method = RequestMethod.POST)
	void modify(HttpServletRequest request, HttpServletResponse response, MLotteryOrder mLotteryOrder,String token) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			mLotteryOrder.setOperatorMobile(getAdmin(token).getMobile());
			int res=mLotteryOrderService.modify(mLotteryOrder);
            if(res==1) {
            	result.setSuccess(true);
    			result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
    			result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
            }else {
            	result.setSuccess(true);
    			result.setStatusCode(RespCodeState.GOODS_SHIPPED.getStatusCode());
    			result.setMessage(RespCodeState.GOODS_SHIPPED.getMessage());
            }
			
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
		}
		outStrJSONWithResult(response, result, respMap);
	}
	@NeedAuth
	@RequestMapping(value="queryOne",method = RequestMethod.GET)
	void queryOne(HttpServletRequest request, HttpServletResponse response,Integer id) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try{
			result.setData(mLotteryOrderService.queryOne(id));
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
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param id
	 * @param addressId
	 * @param token
	 * @param imei
	 */
	@NeedToken
	@RequestMapping(value="setAddress",method = RequestMethod.GET)
	void setAddress(HttpServletRequest request, HttpServletResponse response,MLotteryOrder mLotteryOrder) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			mLotteryOrderService.modify(mLotteryOrder);
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

	/**
	 * 
	 * @param request
	 * @param response
	 * @param token
	 * @param imei
	 * @param pageSize
	 * @param pageNum
	 */
	@NeedToken
	@RequestMapping(value="queryByUser",method = RequestMethod.GET)
	void queryByUser(HttpServletRequest request, HttpServletResponse response,MLotteryOrder mLotteryOrder) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			mLotteryOrder.setUserId(getUserId(respMap.get("token")));
			result.setData(mLotteryOrderService.appQueryList(mLotteryOrder));
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
	/**
	 * h5注册页面获取抽奖滚动消息
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="orderNews",method = RequestMethod.GET)
	void orderNews(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		result.setData(mLotteryOrderService.orderNews());
		result.setSuccess(true);
		result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
		result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
		outStrJSONWithResult(response, result, respMap);
	}
	/**
	 * app获取奖品详情
	 * @param request
	 * @param response
	 * @param id
	 */
	@NeedToken
	@RequestMapping(value="appQueryOne",method = RequestMethod.GET)
	void appQueryOne(HttpServletRequest request, HttpServletResponse response,Integer id) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		result.setData(mLotteryOrderService.appQueryOne(id));
		result.setSuccess(true);
		result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
		result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
		outStrJSONWithResult(response, result, respMap);
	}
	/**
	 * 获取卡密
	 * @param request
	 * @param response
	 * @param payPassword
	 * @param token
	 * @param id
	 */
	@NeedToken
	@RequestMapping(value="getCardPassword",method = RequestMethod.GET)
	void getCardPassword(HttpServletRequest request, HttpServletResponse response,String payPassword,String token,Integer id) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			Map<String,Object> data=mLotteryOrderService.getCardPassword(payPassword, getUserId(token), id);
			if("1".equals(data.get("res").toString())) {
				result.setData(data.get("expressNumbers"));
				result.setSuccess(true);
				result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
				result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());	
			}else {
				result.setSuccess(false);
				result.setStatusCode(RespCodeState.PAY_PASSWORD_ERROR.getStatusCode());
				result.setMessage(RespCodeState.PAY_PASSWORD_ERROR.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
		}
		outStrJSONWithResult(response, result, respMap);
	}
	
	@NeedToken
	@RequestMapping(value="userOrderCount",method = RequestMethod.GET)
	void userOrderCount(HttpServletRequest request, HttpServletResponse response,Integer typeId,String userId) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(mLotteryOrderService.userOrderCount(typeId,getUserId(respMap.get("token"))));
			result.setSuccess(true);
			result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
			result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		outStrJSONWithResult(response, result, respMap);
	}
	
	@NeedAuth
	@RequestMapping(value = "updateLocking", method = RequestMethod.GET)
	void updateLocking(HttpServletRequest request, HttpServletResponse response,MLotteryOrder mLotteryOrder,String token) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);Result result = new Result();
		try {
			mLotteryOrder.setLockingMobile(getAdmin(token).getMobile());
			int res=mLotteryOrderService.updateLocking(mLotteryOrder);
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
			int res=mLotteryOrderService.updateLockingList(ids,getAdmin(token).getMobile());
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
			int res=mLotteryOrderService.relieveLocking(id,getAdmin(token).getMobile());
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
	@RequestMapping(value = "lotteryOrderExcl", method = RequestMethod.GET)
	void exclCash(HttpServletRequest request, HttpServletResponse response,MLotteryOrder mLotteryOrder,String token) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);Result result = new Result();
		try {
			mLotteryOrder.setAdmin(getAdmin(token).getMobile());
			result.setData(mLotteryOrderService.selectExcl(mLotteryOrder));
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
	@RequestMapping(value = "exchangeGoods", method = RequestMethod.GET)
	void exchangeGoods(HttpServletRequest request, HttpServletResponse response,Integer id,String token,String password) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);Result result = new Result();
		try {
			result=mLotteryOrderService.userExchangeGoods(getUserId(token),id,password);
		} catch (Exception e) {
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
			e.printStackTrace();
		}
		outStrJSONWithResult(response, result, respMap);
	}
	
	@NeedToken
	@RequestMapping(value = "addressBinding", method = RequestMethod.GET)
	void addressBinding(HttpServletRequest request, HttpServletResponse response,String token,Integer orderId, Integer addressId) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);Result result = new Result();
		try {
			result=mLotteryOrderService.addressBinding(getUserId(token), orderId, addressId);
			
		} catch (Exception e) {
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
			e.printStackTrace();
		}
		outStrJSONWithResult(response, result, respMap);
	}
}

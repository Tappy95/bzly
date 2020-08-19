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
import com.mc.bzly.annotation.NeedToken;
import com.mc.bzly.base.BaseController;
import com.mc.bzly.base.IPUtil;
import com.mc.bzly.base.PCDDResult;
import com.mc.bzly.base.RespCodeState;
import com.mc.bzly.base.Result;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.model.thirdparty.BZCallback;
import com.mc.bzly.model.thirdparty.PCDDCallback;
import com.mc.bzly.model.thirdparty.TPGame;
import com.mc.bzly.model.thirdparty.XWCallback;
import com.mc.bzly.model.thirdparty.YoleCallback;
import com.mc.bzly.service.thirdparty.TPGameService;

@RestController
@RequestMapping("/tpGame")
public class TPGameController extends BaseController {

	@Value("${yole.key}")
	private String yoleKey;

	@Value("${pcdd.pid}")
	private String pcddPID;
	
	@Value("${pcdd.appkey}")
	private String pcddAppkey;

	@Value("${pcdd.pidIOS}")
	private String pcddPIDIOS;
	
	@Value("${pcdd.appkeyIOS}")
	private String pcddAppkeyIOS;

	@Value("${pcdd.baseUrl}")
	private String pcddBaseUrl;
	
	// 闲玩游戏
	@Value("${xw.appid}")
	private String xwAppid;
	@Value("${xw.appsecret}")
	private String xwAppsecret;
	@Value("${xw.ip}")
	private String ipStr;
	
	//宝猪任务平台游戏
	@Value("${bz.userChannel}")
	private String userChannel;
	@Value("${bz.appKey}")
	private String bzAppKey;
	
	@Reference(version = WebConfig.dubboServiceVersion, interfaceClass = TPGameService.class, check = false, timeout=10000)
	private TPGameService tpGameService;

	@RequestMapping(value = "list", method = RequestMethod.GET)
	void page(HttpServletRequest request, HttpServletResponse response, TPGame tpGame,Integer marketId) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			String equipmentType = respMap.get("equipmentType"); // 1-安卓 2-iOS
			int ptype = "1".equals(equipmentType)?2:1;
			tpGame.setPtype(ptype);
			result.setData(tpGameService.queryList(tpGame,marketId));
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
	void add(HttpServletRequest request, HttpServletResponse response, TPGame tpGame) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(tpGameService.add(tpGame));
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
	@RequestMapping(value = "modify", method = RequestMethod.POST)
	void modify(HttpServletRequest request, HttpServletResponse response, TPGame tpGame) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			int res=tpGameService.modify(tpGame);
			if(res==2) {
				result.setData(res);
				result.setSuccess(true);
				result.setStatusCode(RespCodeState.ORDER_EXISE.getStatusCode());
				result.setMessage(RespCodeState.ORDER_EXISE.getMessage());
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

	@NeedAuth
	@RequestMapping(value = "remove", method = RequestMethod.GET)
	void remove(HttpServletRequest request, HttpServletResponse response, Integer id) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(tpGameService.remove(id));
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
	@RequestMapping(value = "queryOne", method = RequestMethod.GET)
	void queryOne(HttpServletRequest request, HttpServletResponse response, Integer id) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(tpGameService.queryOne(id));
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

	@NeedToken
	@RequestMapping(value = "getUrl", method = RequestMethod.GET)
	void getUrl(HttpServletRequest request, HttpServletResponse response, Integer id, String token) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			respMap.put("userId", getUserId(token));
			result.setData(tpGameService.buildUrl(id, respMap));
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

	@RequestMapping(value = "yoleCallback", method = RequestMethod.GET)
	void yoleCallback(HttpServletRequest request, HttpServletResponse response, YoleCallback yoleCallback) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			yoleCallback.setStatus(2);
			// 验证是否sign是否正确
			String sign = yoleCallback.getSign();
			String yoleid = yoleCallback.getYoleid();
			String userid = yoleCallback.getUserid();
			String appid = yoleCallback.getAppid();
			String gmgold = yoleCallback.getGmgold();
			String chgold = yoleCallback.getChgold();
			String md5Sign = MD5Util.getMd5(yoleid + userid + appid + gmgold + chgold + yoleKey);
			if (StringUtils.isEmpty(sign)) {
				result.setStatusCode(RespCodeState.SIGN_ERROR.getStatusCode());
				result.setMessage(RespCodeState.SIGN_ERROR.getMessage());
			} else if (!sign.equals(md5Sign)) {
				result.setStatusCode(RespCodeState.SIGN_ERROR.getStatusCode());
				result.setMessage(RespCodeState.SIGN_ERROR.getMessage());
			} else {
				YoleCallback old = tpGameService.queryByYoLeId(yoleid);
				if(old != null){ // 判断是否已经处理了订单
					result.setStatusCode("3001");
					result.setMessage("订单已接收");
				}else{
					tpGameService.sendYOLE(yoleCallback);
					result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
					result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
		}
		outStrJSONWithResult(response, result, respMap);
	}
	
	@NeedAuth
	@RequestMapping(value="queryBList",method=RequestMethod.GET)
	void queryBList(HttpServletRequest request,HttpServletResponse response,TPGame tpGame){
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(tpGameService.queryBList(tpGame));
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
	 * PC蛋蛋游戏回调
	 * @param request
	 * @param response
	 * @param pcDDCallback
	 * @return
	 */
	@RequestMapping(value = "pcddCallback", method = RequestMethod.GET)
	PCDDResult pcddCallback(HttpServletRequest request, HttpServletResponse response, PCDDCallback pcDDCallback) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		PCDDResult result = new PCDDResult();
		try {
			pcDDCallback.setPtype(2);
			pcDDCallback.setStatus(1);
			int adid = pcDDCallback.getAdid();
			String ordernum = pcDDCallback.getOrdernum();
			String imei = pcDDCallback.getDeviceid();
			String md5Sign = MD5Util.getMd5(adid+pcddPID+ordernum+imei+pcddAppkey);
			if (StringUtils.isEmpty(pcDDCallback.getKeycode())) {  
				result.setSuccess(0);
				result.setMessage(RespCodeState.SIGN_ERROR.getMessage());
			} else if (!pcDDCallback.getKeycode().equals(md5Sign)){  
				result.setSuccess(0);
				result.setMessage(RespCodeState.SIGN_ERROR.getMessage());
			}else{  
				// 判断是否已经领取
				PCDDCallback old = tpGameService.queryByPCDDOrderNum(ordernum);
				if(old != null){ // 判断是否已经处理了订单
					result.setSuccess(1);
					result.setMessage("订单已接收");
				}else{
					tpGameService.sendPCDD(pcDDCallback);
					result.setSuccess(1);
					result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(0);
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
		}
		return result;
	}

	/**
	 * PC蛋蛋游戏回调
	 * @param request
	 * @param response
	 * @param pcDDCallback
	 * @return
	 */
	@RequestMapping(value = "pcddCallbackIOS", method = RequestMethod.GET)
	PCDDResult pcddCallbackIOS(HttpServletRequest request, HttpServletResponse response, PCDDCallback pcDDCallback) {
		Map<String, String> respMap = new HashMap<>();
		initResponseMap(request, respMap);
		PCDDResult result = new PCDDResult();
		try {
			pcDDCallback.setPtype(1);
			pcDDCallback.setStatus(1);
			int adid = pcDDCallback.getAdid();
			String ordernum = pcDDCallback.getOrdernum();
			String imei = pcDDCallback.getDeviceid();
			String md5Sign = MD5Util.getMd5(adid+pcddPIDIOS+ordernum+imei+pcddAppkeyIOS);
			if (StringUtils.isEmpty(pcDDCallback.getKeycode())) {  
				result.setSuccess(0);
				result.setMessage(RespCodeState.SIGN_ERROR.getMessage());
			} else if (!pcDDCallback.getKeycode().equals(md5Sign)){  
				result.setSuccess(0);
				result.setMessage(RespCodeState.SIGN_ERROR.getMessage());
			}else{  
				// 判断是否已经领取
				PCDDCallback old = tpGameService.queryByPCDDOrderNum(ordernum);
				if(old != null){ // 判断是否已经处理了订单
					result.setSuccess(1);
					result.setMessage("订单已接收");
				}else{
					tpGameService.sendPCDD(pcDDCallback);
					result.setSuccess(1);
					result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(0);
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
		}
		return result;
	}

	/**
	 * 闲玩游戏回调
	 * @param request
	 * @param response
	 * @param xwCallback
	 * @return
	 */
	@RequestMapping(value = "xwCallback", method = RequestMethod.GET)
	PCDDResult xwCallback(HttpServletRequest request, HttpServletResponse response,XWCallback xwCallback) {
		Map<String, String> respMap = new HashMap<>();
		initResponseMapNew(request, respMap);
		PCDDResult result = new PCDDResult();
		try {
			String ip = IPUtil.getIp(request);
			xwCallback.setStatus(2);
			int adid = xwCallback.getAdid();
			String ordernum = xwCallback.getOrdernum();
			int dlevel = xwCallback.getDlevel();
			String deviceid = xwCallback.getDeviceid();
			String appsign = xwCallback.getAppsign();
			String price = xwCallback.getPrice();
			String money = xwCallback.getMoney();
			String sign = MD5Util.getMd5(adid+xwAppid+ordernum+dlevel+deviceid+appsign+price+money+xwAppsecret).toUpperCase();
			if(!ipStr.contains(ip)){ // 正对IP进行处理
				result.setSuccess(0);
				result.setMessage("订单无效");
			}else if (StringUtils.isEmpty(xwCallback.getKeycode())){ // 判断keycode是否存在
				result.setSuccess(0);
				result.setMessage(RespCodeState.SIGN_ERROR.getMessage());
			}else if(!sign.equals(xwCallback.getKeycode())){ // 判断keycode是否正确
				result.setSuccess(0);
				result.setMessage(RespCodeState.SIGN_ERROR.getMessage());
			}else{ // 处理结果
				XWCallback old = tpGameService.queryByXWOrderNum(ordernum);
				if(old != null){ // 判断是否已经处理了订单
					result.setSuccess(1);
					result.setMessage("订单已接收");
				}else{
					tpGameService.sendXW(xwCallback);
					result.setSuccess(1); // 返回结果状态
					result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(0);
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
		}
		return result;
	}
	
	@NeedToken
	@RequestMapping(value="recommendGame",method=RequestMethod.GET)
	void recommendGame(HttpServletRequest request,HttpServletResponse response,String token){
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
			result.setData(tpGameService.recommendGame(ptype,userId));
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
	 * 签到赚
	 * @param request
	 * @param response
	 * @param token
	 */
	@NeedToken
	@RequestMapping(value="recommendGameNew",method=RequestMethod.GET)
	void recommendGameNew(HttpServletRequest request,HttpServletResponse response,String token){
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
			result.setData(tpGameService.recommendGameNew(ptype,getUserId(token)));
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
	 * 签到赚获取游戏链接
	 * @param request
	 * @param response
	 * @param id 游戏id
	 * @param token
	 * @param signinId 签到赚记录id
	 */
	@NeedToken
	@RequestMapping(value = "getqdGameUrl", method = RequestMethod.GET)
	void getqdGameUrl(HttpServletRequest request, HttpServletResponse response, Integer id, String token,Integer signinId) {
		Map<String, String> respMap = new HashMap<>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try {
			respMap.put("userId", getUserId(token));
			result.setData(tpGameService.buildQDGameUrl(id, respMap,signinId));
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
	
	@RequestMapping(value = "bzCallback", method = RequestMethod.GET)
	Result bzCallback(HttpServletRequest request, HttpServletResponse response,BZCallback bzCallback) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			bzCallback.setStatus(2);
			String ordernum = bzCallback.getOrdernum();
			String deviceid = bzCallback.getDeviceid();
			String appsign = bzCallback.getAppsign();
			String sign = MD5Util.getMd5(userChannel+ordernum+deviceid+appsign+bzAppKey);
			if (StringUtils.isEmpty(bzCallback.getKeycode())){ // 判断keycode是否存在
				result.setSuccess(true);
				result.setStatusCode(RespCodeState.SIGN_ERROR.getStatusCode());
				result.setMessage(RespCodeState.SIGN_ERROR.getMessage());
			}else if(!sign.equals(bzCallback.getKeycode())){ // 判断keycode是否正确
				result.setSuccess(true);
				result.setStatusCode(RespCodeState.SIGN_ERROR.getStatusCode());
				result.setMessage(RespCodeState.SIGN_ERROR.getMessage());
			}else{ // 处理结果
				BZCallback old = tpGameService.queryByBZOrderNum(ordernum);
				if(old != null){ // 判断是否已经处理了订单
					result.setSuccess(true);
					result.setStatusCode(RespCodeState.CALLBACK_HAS_FINISH.getStatusCode());
					result.setMessage(RespCodeState.CALLBACK_HAS_FINISH.getMessage());
					result.setMessage("订单已接收");
				}else{
					tpGameService.sendBZ(bzCallback);
					result.setSuccess(true); // 返回结果状态
					result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
					result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(true);
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
		}
		return result;
	}
	/**
	 * 领取活跃金获取游戏链接
	 * @param request
	 * @param response
	 * @param id
	 * @param token
	 * @param vipId
	 */
	@NeedToken
	@RequestMapping(value = "getHyjGameUrl", method = RequestMethod.GET)
	void getHyjGameUrl(HttpServletRequest request, HttpServletResponse response, Integer id, String token,Integer vipId) {
		Map<String, String> respMap = new HashMap<>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try {
			respMap.put("userId", getUserId(token));
			result.setData(tpGameService.buildHyjGameUrl(id, respMap,vipId));
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
	@RequestMapping(value = "getTxGameUrl", method = RequestMethod.GET)
	void buildTxGameUrl(HttpServletRequest request, HttpServletResponse response, Integer id, String token,Integer cashId) {
		Map<String, String> respMap = new HashMap<>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try {
			respMap.put("userId", getUserId(token));
			result.setData(tpGameService.buildTxGameUrl(id, respMap,cashId));
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
	@RequestMapping(value = "getMrhbGameUrl", method = RequestMethod.GET)
	void buildTxGameUrl(HttpServletRequest request, HttpServletResponse response, Integer id, String token) {
		Map<String, String> respMap = new HashMap<>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try {
			respMap.put("userId", getUserId(token));
			result.setData(tpGameService.buildMrhbGameUrl(id, respMap));
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
	@RequestMapping(value="recommendGameTask",method=RequestMethod.GET)
	void recommendGameTask(HttpServletRequest request,HttpServletResponse response,String token){
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
			result.setData(tpGameService.recommendGameTask(ptype,userId));
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
	@RequestMapping(value = "lenovoList", method = RequestMethod.GET)
	void lenovoList(HttpServletRequest request, HttpServletResponse response, TPGame tpGame,String token) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			String equipmentType = respMap.get("equipmentType"); // 1-安卓 2-iOS
			int ptype = "1".equals(equipmentType)?2:1;
			tpGame.setPtype(ptype);
			String userId = getUserId(token);
			result.setData(tpGameService.lenovoList(tpGame,userId));
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
	 * 推荐游戏
	 * @param request
	 * @param response
	 * @param tpGame
	 */
	@RequestMapping(value = "tjList", method = RequestMethod.GET)
	void tjList(HttpServletRequest request, HttpServletResponse response, TPGame tpGame) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			String equipmentType = respMap.get("equipmentType"); // 1-安卓 2-iOS
			int ptype = "1".equals(equipmentType)?2:1;
			tpGame.setPtype(ptype);
			result.setData(tpGameService.tjList(tpGame));
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
	 * 心愿猪闲玩游戏回调-安卓
	 * @param request
	 * @param response
	 * @param xwCallback
	 * @return
	 */
	@RequestMapping(value = "xwCallbackAndroid", method = RequestMethod.GET)
	PCDDResult xwCallbackAndroid(HttpServletRequest request, HttpServletResponse response,XWCallback xwCallback) {
		Map<String, String> respMap = new HashMap<>();
		initResponseMapNew(request, respMap);
		PCDDResult result = new PCDDResult();
		try {
			String ip = IPUtil.getIp(request);
			xwCallback.setStatus(2);
			int adid = xwCallback.getAdid();
			String ordernum = xwCallback.getOrdernum();
			int dlevel = xwCallback.getDlevel();
			String deviceid = xwCallback.getDeviceid();
			String appsign = xwCallback.getAppsign();
			String price = xwCallback.getPrice();
			String money = xwCallback.getMoney();
			String appid = "4399";
			String appsecret = "siig8gsy5zae1j7z";
			String sign = MD5Util.getMd5(adid+appid+ordernum+dlevel+deviceid+appsign+price+money+appsecret).toUpperCase();
			if(!ipStr.contains(ip)){ // 正对IP进行处理
				result.setSuccess(0);
				result.setMessage("订单无效");
			}else if (StringUtils.isEmpty(xwCallback.getKeycode())){ // 判断keycode是否存在
				result.setSuccess(0);
				result.setMessage(RespCodeState.SIGN_ERROR.getMessage());
			}else if(!sign.equals(xwCallback.getKeycode())){ // 判断keycode是否正确
				result.setSuccess(0);
				result.setMessage(RespCodeState.SIGN_ERROR.getMessage());
			}else{ // 处理结果
				XWCallback old = tpGameService.queryByXWOrderNumXYZ(ordernum);
				if(old != null){ // 判断是否已经处理了订单
					result.setSuccess(1);
					result.setMessage("订单已接收");
				}else{
					tpGameService.sendXYZ(xwCallback);
					result.setSuccess(1); // 返回结果状态
					result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(0);
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
		}
		return result;
	}
	
	/**
	 * 心愿猪闲玩游戏回调-ios
	 * @param request
	 * @param response
	 * @param xwCallback
	 * @return
	 */
	@RequestMapping(value = "xwCallbackIOS", method = RequestMethod.GET)
	PCDDResult xwCallbackIOS(HttpServletRequest request, HttpServletResponse response,XWCallback xwCallback) {
		Map<String, String> respMap = new HashMap<>();
		initResponseMapNew(request, respMap);
		PCDDResult result = new PCDDResult();
		try {
			String ip = IPUtil.getIp(request);
			xwCallback.setStatus(2);
			int adid = xwCallback.getAdid();
			String ordernum = xwCallback.getOrdernum();
			int dlevel = xwCallback.getDlevel();
			String deviceid = xwCallback.getDeviceid();
			String appsign = xwCallback.getAppsign();
			String price = xwCallback.getPrice();
			String money = xwCallback.getMoney();
			String appid = "4400";
			String appsecret = "kmuj5qs1z26z9jgr";
			String sign = MD5Util.getMd5(adid+appid+ordernum+dlevel+deviceid+appsign+price+money+appsecret).toUpperCase();
			if(!ipStr.contains(ip)){ // 正对IP进行处理
				result.setSuccess(0);
				result.setMessage("订单无效");
			}else if (StringUtils.isEmpty(xwCallback.getKeycode())){ // 判断keycode是否存在
				result.setSuccess(0);
				result.setMessage(RespCodeState.SIGN_ERROR.getMessage());
			}else if(!sign.equals(xwCallback.getKeycode())){ // 判断keycode是否正确
				result.setSuccess(0);
				result.setMessage(RespCodeState.SIGN_ERROR.getMessage());
			}else{ // 处理结果
				XWCallback old = tpGameService.queryByXWOrderNumXYZ(ordernum);
				if(old != null){ // 判断是否已经处理了订单
					result.setSuccess(1);
					result.setMessage("订单已接收");
				}else{
					tpGameService.sendXYZ(xwCallback);
					result.setSuccess(1); // 返回结果状态
					result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(0);
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
		}
		return result;
	}	
	
}
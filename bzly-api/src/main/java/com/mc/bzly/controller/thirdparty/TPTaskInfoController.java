package com.mc.bzly.controller.thirdparty;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bzly.common.utils.MD5Util;
import com.mc.bzly.annotation.NeedAuth;
import com.mc.bzly.annotation.NeedToken;
import com.mc.bzly.base.BaseController;
import com.mc.bzly.base.RespCodeState;
import com.mc.bzly.base.Result;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.model.thirdparty.TPTaskInfo;
import com.mc.bzly.model.thirdparty.TPTaskStatusCallback;
import com.mc.bzly.model.thirdparty.TaskCallback;
import com.mc.bzly.service.thirdparty.TPTaskInfoService;
import com.mc.bzly.service.thirdparty.TPTaskStatusCallbackService;

@RestController
@RequestMapping("/tpTaskInfo")
public class TPTaskInfoController extends BaseController {
	
	@Value("${bzly.appKey}")
	private String bzlyAppkey;
	@Value("${bzly.userChannel}")
	private String bzlyuserChannel;
	
	@Reference(version = WebConfig.dubboServiceVersion, interfaceClass = TPTaskInfoService.class, check = false, timeout=10000)
	private TPTaskInfoService tpTaskInfoService;
	
	@Reference(version = WebConfig.dubboServiceVersion, interfaceClass = TPTaskStatusCallbackService.class, check = false, timeout=10000)
	private TPTaskStatusCallbackService tpTaskStatusCallbackService;
	
	@NeedAuth
	@RequestMapping(value="getTasks",method=RequestMethod.GET)
	public void getTasks(HttpServletRequest request, HttpServletResponse response){
		Map<String, String> respMap = new HashMap<String, String>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try {
			int res = tpTaskInfoService.getTasks();
			if(res == 1){
				result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
				result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
			}else if(res == 2){
				result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
				result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			}else if(res == 3){
				result.setMessage(RespCodeState.HAVE_NO_DATA.getMessage());
				result.setStatusCode(RespCodeState.HAVE_NO_DATA.getStatusCode());
			}else if(res == 4){
				result.setMessage(RespCodeState.SIGN_ERROR.getMessage());
				result.setStatusCode(RespCodeState.SIGN_ERROR.getStatusCode());
			}else if(res == 5){
				result.setMessage(RespCodeState.ADMIN_NO_PERMISSION.getMessage());
				result.setStatusCode(RespCodeState.ADMIN_NO_PERMISSION.getStatusCode());
			}
		}catch (Exception e) {
			e.printStackTrace();
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
		}
		outStrJSONWithResult(response, result, respMap);
	}

	@NeedAuth
	@RequestMapping(value="list",method=RequestMethod.GET)
	public void list(HttpServletRequest request, HttpServletResponse response,TPTaskInfo tpTaskInfo){
		Map<String, String> respMap = new HashMap<String, String>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(tpTaskInfoService.queryList(tpTaskInfo));
			result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
			result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
		}catch (Exception e) {
			e.printStackTrace();
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
		}
		outStrJSONWithResult(response, result, respMap);
	}
	
	@NeedAuth
	@RequestMapping(value="modify",method=RequestMethod.POST)
	public void modify(HttpServletRequest request, HttpServletResponse response,TPTaskInfo tpTaskInfo){
		Map<String, String> respMap = new HashMap<String, String>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try{
			tpTaskInfoService.modify(tpTaskInfo);
			result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
			result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
		}catch (Exception e) {
			e.printStackTrace();
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
		}
		outStrJSONWithResult(response, result, respMap);
	}
	
	@NeedToken
	@RequestMapping(value="getFList",method=RequestMethod.GET)
	public void getFList(HttpServletRequest request, HttpServletResponse response,TPTaskInfo tpTaskInfo){
		Map<String, String> respMap = new HashMap<String, String>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try{
			String userId = getUserId(respMap.get("token"));
			tpTaskInfo.setUserId(userId);
			result.setData(tpTaskInfoService.queryFList(tpTaskInfo));
			result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
			result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
		}catch (Exception e) {
			e.printStackTrace();
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
		}
		outStrJSONWithResult(response, result, respMap);
	}

	@NeedToken
	@RequestMapping(value="info",method=RequestMethod.GET)
	public void info(HttpServletRequest request, HttpServletResponse response,Integer id,String token){
		Map<String, String> respMap = new HashMap<String, String>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try{
			String userId = getUserId(token);
			result = tpTaskInfoService.info(id,userId);
		}catch (Exception e) {
			e.printStackTrace();
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
		}
		outStrJSONWithResult(response, result, respMap);
	}
	
	@NeedAuth
	@RequestMapping(value="queryOne",method=RequestMethod.GET)
	public void queryOne(HttpServletRequest request, HttpServletResponse response,Integer id){
		Map<String, String> respMap = new HashMap<String, String>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try{
			result.setData(tpTaskInfoService.queryOne(id));
			result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
			result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
		}catch (Exception e) {
			e.printStackTrace();
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
		}
		outStrJSONWithResult(response, result, respMap);
	}
	
	@RequestMapping(value="taskCallBack",method=RequestMethod.GET)
	public void taskCallBack(HttpServletRequest request, HttpServletResponse response,TaskCallback taskCallback){
		Map<String, String> respMap = new HashMap<String, String>();
		initResponseMap(request, respMap);
		Result result = new Result();
		taskCallback.setStatus(2);
		taskCallback.setCreateTime(new Date().getTime());
		try{
			int taskId = taskCallback.getTaskId();
			String userId = taskCallback.getUserId();
			String sign = taskCallback.getSign();
			long date = taskCallback.getDate();
			// 加密：appkey+userId+taskId+date+code
			String signStr = MD5Util.getMd5(bzlyAppkey + userId + taskId + taskCallback.getOrderNum() + date).toUpperCase();
			if(!sign.equals(signStr)){
				result.setMessage("验签失败");
				result.setStatusCode("3");
			}else {
				String orderNum = taskCallback.getOrderNum();
				TaskCallback old = tpTaskInfoService.queryCallBack(orderNum);
				if(old != null){
					taskCallback.setStatus(3);
					result.setMessage("订单已完成");
					result.setStatusCode("1");
				}else {
					// 开始处理订单
					long reward = tpTaskInfoService.settle(taskCallback);
					taskCallback.setStatus(1);
					taskCallback.setTotalCoin(reward);
					result.setMessage("接收成功");
					result.setStatusCode("1");
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
		}finally {
			tpTaskInfoService.addCallBack(taskCallback);
		}
		outStrJSONWithResult(response, result, respMap);
	}
	@NeedToken
	@RequestMapping(value="getConductTask",method=RequestMethod.GET)
	public void getConductTask(HttpServletRequest request, HttpServletResponse response,String token){
		Map<String, String> respMap = new HashMap<String, String>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try{
			result.setData(tpTaskInfoService.getConductTask(getUserId(token)));
			result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
			result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
		}catch (Exception e) {
			e.printStackTrace();
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
		}
		outStrJSONWithResult(response, result, respMap);
	}
	
	/**
	 * 构建任务详情访问链接
	 * @param request
	 * @param response
	 * @param token
	 * @param id
	 */
	@NeedToken
	@RequestMapping(value="buildUrl",method=RequestMethod.GET)
	public void buildUrl(HttpServletRequest request, HttpServletResponse response,String token,Integer id){
		Map<String, String> respMap = new HashMap<String, String>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try{
			result.setData(tpTaskInfoService.buildUrl(getUserId(token),id,bzlyuserChannel,bzlyAppkey));
			result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
			result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
		}catch (Exception e) {
			e.printStackTrace();
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
		}
		outStrJSONWithResult(response, result, respMap);
	}
	
	/**
	 * 接收用户-任务关系变动回调
	 */
	@RequestMapping(value="taskUserChangedCallback",method=RequestMethod.GET)
	public void taskUserChangedCallback(HttpServletRequest request, HttpServletResponse response,TPTaskStatusCallback tpTaskStatusCallback){
		Map<String, String> respMap = new HashMap<String, String>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try{
			
			// 1.验签
			String accountId = tpTaskStatusCallback.getUserId();
			Integer taskId = tpTaskStatusCallback.getTaskId();
			String sign = tpTaskStatusCallback.getSign();
			String flewNum = tpTaskStatusCallback.getFlewNum();
			String signL = MD5Util.getMd5(accountId + bzlyuserChannel + taskId + bzlyAppkey + flewNum);
			if(!signL.equals(sign)){
				// 验签失败
				result.setMessage("验签失败");
				result.setStatusCode("3");
			}else{
				// 查看是否已获取
				TPTaskStatusCallback old = tpTaskStatusCallbackService.queryByNum(flewNum,tpTaskStatusCallback.getStatus());
				if(old != null){
					result.setMessage("订单已完成");
					result.setStatusCode("1");
				} else {
					// 业务逻辑处理
					tpTaskInfoService.taskUserChangedCallback(tpTaskStatusCallback);
					result.setMessage("操作成功");
					result.setStatusCode("1");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
		}
		outStrJSONWithResult(response, result, respMap);
	}
	
	@NeedAuth
	@RequestMapping(value="delete",method=RequestMethod.GET)
	public void delete(HttpServletRequest request, HttpServletResponse response,Integer id){
		Map<String, String> respMap = new HashMap<String, String>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(tpTaskInfoService.delete(id));
			result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
			result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
		}catch (Exception e) {
			e.printStackTrace();
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
		}
		outStrJSONWithResult(response, result, respMap);
	}
}

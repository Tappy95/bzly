package com.mc.bzly.interceptor;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.mc.bzly.annotation.NeedAuth;
import com.mc.bzly.annotation.NeedCodeKey;
import com.mc.bzly.annotation.NeedToken;
import com.mc.bzly.base.BaseController;
import com.mc.bzly.base.RespCodeState;
import com.mc.bzly.base.Result;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.model.platform.PAdmin;
import com.mc.bzly.service.platform.PAdminService;
import com.mc.bzly.service.redis.RedisService;
import com.mc.bzly.service.user.LUserActiveService;
import com.mc.bzly.service.user.MUserInfoService;

@Component
public class GlobalInperceptor extends BaseController implements HandlerInterceptor {

	@Reference(version=WebConfig.dubboServiceVersion,interfaceClass=RedisService.class,check=false,timeout=3000)
	protected RedisService redisService;

	@Reference(version=WebConfig.dubboServiceVersion,interfaceClass=MUserInfoService.class,check=false,timeout=3000)
	protected MUserInfoService mUserInfoService;
	
	@Reference(version=WebConfig.dubboServiceVersion,interfaceClass=PAdminService.class,check=false,timeout=3000)
	protected PAdminService pAdminService;
	
	@Reference(version=WebConfig.dubboServiceVersion,interfaceClass=LUserActiveService.class,check=false,timeout=3000)
	protected LUserActiveService lUserActiveService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
		if (!handler.getClass().isAssignableFrom(HandlerMethod.class)) {
			return true;
		}
		final HandlerMethod handlerMethod = (HandlerMethod) handler;
		final Method method = handlerMethod.getMethod();
		final Class<?> clazz = method.getDeclaringClass();
		try {
			
			if (clazz.isAnnotationPresent(NeedCodeKey.class) || method.isAnnotationPresent(NeedCodeKey.class)) {
				String codeKey = request.getParameter("codeKey");
				String channelCode = request.getParameter("channelCode");
				if("xiaqiu".equals(channelCode) || "xiaqiu2".equals(channelCode)){
					return true;
				}
				boolean flag = checkCode(codeKey);
				if(!flag){
					result.setData(null);
					result.setMessage(RespCodeState.SMS_CHECKED_ERROR.getMessage());
					result.setStatusCode(RespCodeState.SMS_CHECKED_ERROR.getStatusCode());
					outStrJSONWithResult(response, result, respMap);
					return false;
				}
			}
			
			if (clazz.isAnnotationPresent(NeedToken.class) || method.isAnnotationPresent(NeedToken.class)) {
				String token = request.getParameter("token");
				if(StringUtils.isNotEmpty(token)){
					String userId = getUserId(token);
					if(userId != null){
						if("userfreeze".equals(userId)){
							respMap.put("token", null);
							result.setMessage(RespCodeState.ACCOUNT_EXECPTION.getMessage());
							result.setStatusCode(RespCodeState.USER_NO_PREMISSION.getStatusCode());
							outStrJSONWithResult(response, result, respMap);
							return false;
						}
						return true;
					}else{
						redisService.delete(token);  
						respMap.put("token", null);
						result.setMessage(RespCodeState.ACCOUNT_LOGIN_OTHER_EQUIPMENT.getMessage());
						result.setStatusCode(RespCodeState.ACCOUNT_LOGIN_OTHER_EQUIPMENT.getStatusCode());
						outStrJSONWithResult(response, result, respMap);
						return false;
					}
				}else{
					respMap.put("token", null);
					result.setMessage(RespCodeState.USER_NO_PREMISSION.getMessage());
					result.setStatusCode(RespCodeState.USER_NO_PREMISSION.getStatusCode());
					outStrJSONWithResult(response, result, respMap);
					return false;
				}
			}
			
			if (clazz.isAnnotationPresent(NeedAuth.class) || method.isAnnotationPresent(NeedAuth.class)) {
				String token = request.getParameter("token");
				String tokenValue = redisService.getString(token);
				if(tokenValue != null){
					PAdmin pAdmin = (PAdmin)redisService.getObject(tokenValue);
					pAdmin = pAdminService.queryOne(pAdmin.getAdminId());
					if(pAdmin.getStatus().intValue() == 2){
						result.setMessage(RespCodeState.ADMIN_NO_PERMISSION.getMessage());
						result.setStatusCode(RespCodeState.ADMIN_NO_PERMISSION.getStatusCode());
						outStrJSONWithResult(response, result, respMap);
						return false;
					}
					return true;
				}else{
					respMap.put("token", null);
					result.setMessage(RespCodeState.ADMIN_NO_PERMISSION.getMessage());
					result.setStatusCode(RespCodeState.ADMIN_NO_PERMISSION.getStatusCode());
					outStrJSONWithResult(response, result, respMap);
					return false;
				}
			}
		} catch (Exception e) {
			String channelCode = request.getParameter("channelCode");
			if("xiaqiu".equals(channelCode) || "xiaqiu2".equals(channelCode)){
				result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
				result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
				return false;
			}
			e.printStackTrace();
			result.setMessage(RespCodeState.USER_NO_PREMISSION.getMessage());
			result.setStatusCode(RespCodeState.USER_NO_PREMISSION.getStatusCode());
			outStrJSONWithResult(response, result, respMap);
			return false;
		}
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}
}

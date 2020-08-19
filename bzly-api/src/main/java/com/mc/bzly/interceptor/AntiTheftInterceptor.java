package com.mc.bzly.interceptor;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mc.bzly.base.BaseController;
import com.mc.bzly.base.IPCCacheWapper;
import com.mc.bzly.base.IPUtil;
import com.mc.bzly.base.RespCodeState;
import com.mc.bzly.base.Result;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.model.platform.PIpBlacklist;
import com.mc.bzly.model.platform.PWhitelist;
import com.mc.bzly.model.user.MUserInfo;
import com.mc.bzly.service.platform.PDictionaryService;
import com.mc.bzly.service.platform.PIpBlacklistService;
import com.mc.bzly.service.platform.PWhitelistService;
import com.mc.bzly.service.redis.RedisService;
import com.mc.bzly.service.user.MUserInfoService;

public class AntiTheftInterceptor extends BaseController implements HandlerInterceptor {
	
	protected static Logger logger = LoggerFactory.getLogger(AntiTheftInterceptor.class);
	
	@Reference(version=WebConfig.dubboServiceVersion,interfaceClass=PIpBlacklistService.class,check=false,timeout=3000)
	protected PIpBlacklistService pIpBlacklistService;
	
	@Reference(version=WebConfig.dubboServiceVersion,interfaceClass=PWhitelistService.class,check=false,timeout=3000)
	protected PWhitelistService pWhitelistService;

	@Reference(version=WebConfig.dubboServiceVersion,interfaceClass=MUserInfoService.class,check=false,timeout=3000)
	protected MUserInfoService mUserInfoService;
	
	@Reference(version=WebConfig.dubboServiceVersion,interfaceClass=RedisService.class,check=false,timeout=3000)
	protected RedisService redisService;

	@Reference(version=WebConfig.dubboServiceVersion,interfaceClass=PDictionaryService.class,check=false,timeout=3000)
	protected PDictionaryService pDictionaryService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Result result = new Result();
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		String ip = IPUtil.getIp(request);
		String uri = request.getRequestURI();
		String mobile = request.getParameter("mobile");
		String imei = request.getParameter("imei");
		String channelCode = request.getParameter("channelCode");
		logger.info("ip:{}",ip);
		logger.info("mobile:{}",mobile);
		logger.info("imei:{}",imei);
		long now = new Date().getTime();
		if("xiaqiu".equals(channelCode) || "xiaqiu2".equals(channelCode)){
			return true;
		}
		// 判断是否在白名单
		List<PWhitelist> pWhitelistIp = pWhitelistService.queryByContent(ip);
		if(pWhitelistIp != null && pWhitelistIp.size() > 0){
			return true;
		}else{
			List<PWhitelist> pWhitelistMobile = pWhitelistService.queryByContent(mobile);
			if(pWhitelistMobile != null && pWhitelistMobile.size() > 0){
				return true;
			}
		}
		try{
			// 判断是否在黑名单
			if("/userInfo/reg".equals(uri) || "/userInfo/regH5".equals(uri)){
				PIpBlacklist pIpBlacklist = pIpBlacklistService.queryByIp(ip);
				if(pIpBlacklist != null){
					result.setMessage(RespCodeState.IP_IN_BLACKLIST.getMessage());
					result.setStatusCode(RespCodeState.IP_IN_BLACKLIST.getStatusCode());
					outStrJSONWithResult(response, result, respMap);
					return false;
				}
				// 同一个手机设备只可以注册一个账号
				if(imei != null){
					Map<String, Object> param = new HashMap<String, Object>();
					param.put("regImei", imei);
					MUserInfo mUserInfo = mUserInfoService.queryUserBaseInfoSimple(param);
					if(mUserInfo != null){
						result.setMessage(RespCodeState.ONE_EQUIPMENT_ONE_USER.getMessage());
						result.setStatusCode(RespCodeState.ONE_EQUIPMENT_ONE_USER.getStatusCode());
						outStrJSONWithResult(response, result, respMap);
						return false;
					}
				}
	
				// 同IP（c段）注册用户限制
				String one_minite_reg_limitStr = pDictionaryService.queryByName("one_minite_reg_limit").getDicValue();
				Integer one_minite_reg_limit = new Integer(one_minite_reg_limitStr); // 同IP（c段）每24小时不能超过5个注册用户
				// 同IP（c段）每分钟不能超过2个注册用户
				//String ipC = ip.split("\\.")[2]; // 特别注意：如果是点进行分隔，一定要转义 MMP
				String oneMinite = "ipC-one-minite-"+ip;
				String oneDay = "ipC-one-day-"+ip;
				if(redisService.getObject(oneMinite)!= null){
					IPCCacheWapper oneMiniteIPCCacheWapper = (IPCCacheWapper)redisService.getObject(oneMinite);
					if(oneMiniteIPCCacheWapper.getValue() == one_minite_reg_limit){
						result.setMessage(RespCodeState.IP_REG_FREQUENT.getMessage());
						result.setStatusCode(RespCodeState.IP_REG_FREQUENT.getStatusCode());
						outStrJSONWithResult(response, result, respMap);
						return false;
					}else{
						// 设置过期时间
						int expireTime = (int) (oneMiniteIPCCacheWapper.getExpireTime() - (now / 1000));
						oneMiniteIPCCacheWapper.setValue(oneMiniteIPCCacheWapper.getValue() + 1);
						redisService.put(oneMinite, oneMiniteIPCCacheWapper, expireTime);
					}
				}else {
					IPCCacheWapper oneMiniteIPCCacheWapper = new IPCCacheWapper();
					oneMiniteIPCCacheWapper.setExpireTime(now / 1000 + 60);
					oneMiniteIPCCacheWapper.setValue(1);
					redisService.put(oneMinite, oneMiniteIPCCacheWapper, 60);
				}
				String one_day_reg_limitStr = pDictionaryService.queryByName("one_day_reg_limit").getDicValue();
				Integer one_day_reg_limit = new Integer(one_day_reg_limitStr); // 同IP（c段）每分钟不能超过2个注册用户 
				// 同IP（c段）每24小时不能超过5个注册用户
				if(redisService.getObject(oneDay)!= null){
					IPCCacheWapper oneDayIPCCacheWapper = (IPCCacheWapper)redisService.getObject(oneDay);
					if(oneDayIPCCacheWapper.getValue() == one_day_reg_limit){
						result.setMessage(RespCodeState.IP_REG_FREQUENT.getMessage());
						result.setStatusCode(RespCodeState.IP_REG_FREQUENT.getStatusCode());
						outStrJSONWithResult(response, result, respMap);
						return false;
					}else{
						// 设置过期时间
						int expireTime = (int) (oneDayIPCCacheWapper.getExpireTime() - (now / 1000));
						oneDayIPCCacheWapper.setValue(oneDayIPCCacheWapper.getValue() + 1);
						redisService.put(oneDay, oneDayIPCCacheWapper, expireTime);
					}
				}else {
					IPCCacheWapper oneDayIPCCacheWapper = new IPCCacheWapper();
					oneDayIPCCacheWapper.setExpireTime(now / 1000 + 60 * 60 * 24);
					oneDayIPCCacheWapper.setValue(1);
					redisService.put(oneDay, oneDayIPCCacheWapper, 60 * 60 * 24);
				}
			}
			// 同一个手机一个星期内只能登陆一个账号
			if("/userInfo/loginByPassword".equals(uri) || "/userInfo/loginBySms".equals(uri)){
				
				/*param.put("imei", imei);
				MUserInfo muserInfo = mUserInfoService.queryUserBaseInfoSimple(param);
				if(muserInfo != null){
					long updateTime = muserInfo.getUpdateTime() + 24*60*60*1000*7l;
					if(updateTime > now){ // 说明在一周之内，该账号操作过
						if(!mobile.equals(muserInfo.getMobile())){
							result.setMessage(RespCodeState.EQUIPMENT_CHANGED_ACCOUNT_FREQUENT.getMessage());
							result.setStatusCode(RespCodeState.EQUIPMENT_CHANGED_ACCOUNT_FREQUENT.getStatusCode());
							outStrJSONWithResult(response, result, respMap);
							return false;
						}
					}
				}
				param.remove("imei");*/
				// 同一个账号，注册时间小于30天的，登陆设备数不能大于等于2台
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("mobile", mobile);
				MUserInfo muserInfo = mUserInfoService.queryUserBaseInfoSimple(param);
				if(muserInfo != null){
					if("xiaqiu".equals(muserInfo.getChannelCode()) || "xiaqiu".equals(muserInfo.getParentChannelCode())){
						return true;
					}
					long mill30 = 24*60*60*1000*30l;
					long createTime = muserInfo.getCreateTime() + mill30;
					if(createTime > now){ // 当前时间在注册30天时间之后
						if(StringUtils.isEmpty(muserInfo.getImei())){ // 针对H5注册用户没有IMEI的情况
							return true;
						}
						if(!imei.equals(muserInfo.getImei())){
							result.setMessage(RespCodeState.ACCOUNT_CHANGED_EQUIPMENT_FREQUENT.getMessage());
							result.setStatusCode(RespCodeState.ACCOUNT_CHANGED_EQUIPMENT_FREQUENT.getStatusCode());
							outStrJSONWithResult(response, result, respMap);
							return false;
						}
					}
				}
			}
		}catch (Exception e) {
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			outStrJSONWithResult(response, result, respMap);
			return false;
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}

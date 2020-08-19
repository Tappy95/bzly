package com.mc.bzly.impl.platform;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.bzly.common.utils.MD5Util;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.bzly.base.RespCodeState;
import com.mc.bzly.base.Result;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.platform.PAdminDao;
import com.mc.bzly.dao.platform.PRoleDao;
import com.mc.bzly.model.platform.PAdmin;
import com.mc.bzly.model.platform.PRole;
import com.mc.bzly.redis.CodisConfig;
import com.mc.bzly.service.platform.PAdminService;
import com.mc.bzly.service.redis.RedisService;
import com.mc.bzly.util.StringUtil;

@Service(interfaceClass = PAdminService.class,version = WebConfig.dubboServiceVersion)
public class PAdminServiceImpl implements PAdminService {
	
	private Logger logger = LoggerFactory.getLogger(PAdminServiceImpl.class);
	
	@Autowired 
	private PAdminDao pAdminDao;
	@Autowired 
	private PRoleDao pRoleDao;
	
	@Autowired
	private RedisService redisService;
	
	private final String tokenPrefix = "bz_admin_token_";
	
	@Transactional
	@Override
	public int add(PAdmin pAdmin) throws Exception {
		pAdmin.setAdminId(UUID.randomUUID().toString().replaceAll("-", ""));
		pAdmin.setCreateTime(new Date().getTime());
		pAdminDao.insert(pAdmin);
		return 1;
	}

	@Transactional
	@Override
	public int modify(PAdmin pAdmin,PAdmin logAdmin) throws Exception {
		if(pAdmin.getAdminId() == null){
			throw new Exception("管理员id为空");
		}
		PAdmin adm=pAdminDao.selectOne(pAdmin.getAdminId());
		pAdminDao.update(pAdmin);
		logger.info("操作人："+logAdmin.toString()+"管理员修改信息旧信息："+adm.toString()+"新信息："+pAdmin.toString());
		return 1;
	}

	@Transactional
	@Override
	public int remove(String adminId) throws Exception {
		if(StringUtils.isEmpty(adminId)){
			throw new Exception("管理员id为空");
		}
		pAdminDao.delete(adminId);
		return 1;
	}

	@Override
	public PAdmin queryOne(String adminId) {
		return pAdminDao.selectOne(adminId);
	}

	@Override
	public PageInfo<PAdmin> queryList(PAdmin pAdmin) {
		PageHelper.startPage(pAdmin.getPageNum(), pAdmin.getPageSize());
		List<PAdmin> pAdminList = pAdminDao.selectList(pAdmin);
		return new PageInfo<>(pAdminList);
	}

	@Override
	public Result login(PAdmin pAdmin) {
		Result result = new Result();
		pAdmin = pAdminDao.selectForLogin(pAdmin);
		if(pAdmin == null){
			result.setData(null);
			result.setMessage(RespCodeState.ADMIN_LOGIN_FAILURE.getMessage());
			result.setStatusCode(RespCodeState.ADMIN_LOGIN_FAILURE.getStatusCode());
		}else if(pAdmin.getStatus() == 2) {
			result.setData(null);
			result.setMessage(RespCodeState.ADMIN_LOGIN_FORBIDDEN.getMessage());
			result.setStatusCode(RespCodeState.ADMIN_LOGIN_FORBIDDEN.getStatusCode());
		}else{
			pAdmin.setPassword(null);
			// 获取随机参数
			Integer rd = (int) Math.random();
			// 生成token
			String v = tokenPrefix+pAdmin.getAdminId()+"_"+rd;
			String token = MD5Util.getMd5(v);
			redisService.put(token,CodisConfig.DEFAULT_EXPIRE, v);
			redisService.put(v, pAdmin);
			PRole role=pRoleDao.selectOne(pAdmin.getRoleId());
			Map<String, Object> resultMap = new HashMap<>();
			resultMap.put("mobile", pAdmin.getMobile());
			resultMap.put("realname", pAdmin.getRealname());
			resultMap.put("adminId", pAdmin.getAdminId());
			resultMap.put("channelCode", pAdmin.getChannelCode());
			resultMap.put("userRelation", pAdmin.getUserRelation());
			resultMap.put("roleName", role.getRoleName());
			result.setToken(token);
			result.setData(resultMap);
			result.setSuccess(true);
			result.setMessage(RespCodeState.ADMIN_LOGIN_SUCCESS.getMessage());
			result.setStatusCode(RespCodeState.ADMIN_LOGIN_SUCCESS.getStatusCode());
		}
		return result;
	}

	@Override
	public int updatePassword(PAdmin pAdmin) {
		PAdmin adm=pAdminDao.selectOne(pAdmin.getAdminId());
		if(StringUtil.isNullOrEmpty(adm)) {
			return 1;//管理员不存在
		}
		if(!adm.getPassword().equals(pAdmin.getOldPassword())) {
			return 2;//旧密码错误
		}
		pAdminDao.update(pAdmin);
		logger.info("管理员修改密码旧密码："+pAdmin.getOldPassword()+"新密码："+pAdmin.getPassword());
		return 3;//修改成功
	}

}

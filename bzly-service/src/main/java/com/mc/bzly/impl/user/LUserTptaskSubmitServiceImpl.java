package com.mc.bzly.impl.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.bzly.common.utils.HttpUtil;
import com.bzly.common.utils.MD5Util;
import com.mc.bzly.base.RespCodeState;
import com.mc.bzly.base.Result;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.platform.PDictionaryDao;
import com.mc.bzly.dao.thirdparty.TPTaskInfoDao;
import com.mc.bzly.dao.user.LUserTptaskDao;
import com.mc.bzly.dao.user.LUserTptaskSubmitDao;
import com.mc.bzly.model.thirdparty.TPTaskInfo;
import com.mc.bzly.model.user.LUserTptask;
import com.mc.bzly.model.user.LUserTptaskSubmit;
import com.mc.bzly.model.user.LUserTptaskSubmitVo;
import com.mc.bzly.service.user.LUserTptaskSubmitService;
import com.mc.bzly.util.StringUtil;

@Service(interfaceClass = LUserTptaskSubmitService.class,version = WebConfig.dubboServiceVersion)
public class LUserTptaskSubmitServiceImpl implements LUserTptaskSubmitService {

	@Autowired
	private LUserTptaskSubmitDao lUserTptaskSubmitDao;
	@Autowired
	private LUserTptaskDao lUserTptaskDao;
	@Autowired
	private TPTaskInfoDao tpTaskInfoDao;
	@Autowired
	private PDictionaryDao pDictionaryDao;
	
	@Transactional
	@Override
	public Result add(List<LUserTptaskSubmit> lUserTptaskSubmit,String userId,Integer tpTaskId,Integer lTpTaskId) {
		Result result = new Result();
		long now = new Date().getTime();
		for(LUserTptaskSubmit t:lUserTptaskSubmit) {
			if(!StringUtil.isChinese(t.getSubmitValue()) && !StringUtil.isUrl(t.getSubmitValue())) {
				result.setMessage(RespCodeState.CONTENT_ILLEGAL.getMessage());
				result.setStatusCode(RespCodeState.CONTENT_ILLEGAL.getStatusCode());
				return result;
			}
		}
		// 判断是否在过期时间之内
		LUserTptask lUserTptask = lUserTptaskDao.selectByTaskId(tpTaskId, userId, 1);
		TPTaskInfo tpTaskInfo = tpTaskInfoDao.selectOne(lUserTptask.getTpTaskId());
		if(lUserTptask != null){
			if(now > lUserTptask.getExpireTime().longValue()){
				result.setMessage(RespCodeState.EXPIRE_TIME_OUT.getMessage());
				result.setStatusCode(RespCodeState.EXPIRE_TIME_OUT.getStatusCode());
				return result;
			}
		}else {
			result.setMessage(RespCodeState.EXPIRE_TIME_OUT.getMessage());
			result.setStatusCode(RespCodeState.EXPIRE_TIME_OUT.getStatusCode());
			return result;
		}
		
		Map<String, Object> param = new HashMap<>();
		// 判断状态
		if(lUserTptask.getStatus().intValue() != 1){ // -1-已过期 1-待提交 2-已提交，待审核 3-审核通过 4-审核失败
			result.setStatusCode(RespCodeState.TASK_CANNOT_SUBMIT.getStatusCode());
			result.setMessage(RespCodeState.TASK_CANNOT_SUBMIT.getMessage());
			return result;
		}
		// 查询提交记录
		List<LUserTptaskSubmitVo> lUserTptaskSubmitVos = new ArrayList<>();
		for (int i = 0; i < lUserTptaskSubmit.size(); i++) {
			lUserTptaskSubmit.get(i).setlTpTaskId(lTpTaskId);
			lUserTptaskSubmit.get(i).setTpTaskId(tpTaskId);
			lUserTptaskSubmit.get(i).setUserId(userId);
			lUserTptaskSubmit.get(i).setCreateTime(now);
			lUserTptaskSubmit.get(i).setStatus(1);
			
			LUserTptaskSubmitVo lUserTptaskSubmitVo = new LUserTptaskSubmitVo();
			lUserTptaskSubmitVo.setContent(lUserTptaskSubmit.get(i).getSubmitValue());
			lUserTptaskSubmitVo.setSubmitId(lUserTptaskSubmit.get(i).getSubmitId());
			lUserTptaskSubmitVos.add(lUserTptaskSubmitVo);
		}
		String task_appkey = pDictionaryDao.selectByName("task_appkey").getDicValue();
		String task_channel = pDictionaryDao.selectByName("task_channel").getDicValue();
		String task_send_data_url = pDictionaryDao.selectByName("task_send_data_url").getDicValue();
		
		param.put("userId", lUserTptask.getAccountId());
		param.put("userChannel", task_channel);
		param.put("taskId", lUserTptask.getTpTaskId());
		param.put("taskName", tpTaskInfo.getName());
		param.put("submitTime", now);
		// appkey+channel+date+userId
		String sign = MD5Util.getMd5(task_appkey+task_channel+now+lUserTptask.getAccountId());
		param.put("sign", sign);
		param.put("userDatas", lUserTptaskSubmitVos);
		try {
			String resp = HttpUtil.sendPOST1(param, task_send_data_url);
			// 后续处理
			result = JSON.parseObject(resp, Result.class);
			if(result.getStatusCode().startsWith("2000")){
				// 修改记录状态
				lUserTptask.setStatus(2);
				lUserTptask.setUpdateTime(now);
				lUserTptaskDao.update(lUserTptask);
				// 删除原来的提交信息
				lUserTptaskSubmitDao.delete(userId, lTpTaskId);
				// 添加提交信息
				lUserTptaskSubmitDao.batchInsert(lUserTptaskSubmit);
				result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
				result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
			}else if(result.getStatusCode().startsWith("3023")){
				result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getMessage());
			return result;
		}
		return result;
	}

}

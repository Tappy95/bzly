package com.mc.bzly.impl.user;

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
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.bzly.base.RespCodeState;
import com.mc.bzly.base.Result;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.platform.PDictionaryDao;
import com.mc.bzly.dao.thirdparty.TPTaskInfoDao;
import com.mc.bzly.dao.user.LUserTptaskDao;
import com.mc.bzly.dao.user.LUserTptaskSubmitDao;
import com.mc.bzly.dao.user.MUserInfoDao;
import com.mc.bzly.impl.redis.AWorker;
import com.mc.bzly.model.thirdparty.TPTaskInfo;
import com.mc.bzly.model.user.LUserTptask;
import com.mc.bzly.model.user.LUserTptaskSimple;
import com.mc.bzly.model.user.LUserTptaskSubmit;
import com.mc.bzly.model.user.LUserTptaskSubmitVo;
import com.mc.bzly.service.user.LUserTptaskService;

@Service(interfaceClass = LUserTptaskService.class,version = WebConfig.dubboServiceVersion)
public class LUserTptaskServiceImpl implements LUserTptaskService {

	@Autowired
	private LUserTptaskDao lUserTptaskDao;
	@Autowired
	private TPTaskInfoDao tpTaskInfoDao;
	@Autowired
	private LUserTptaskSubmitDao lUserTptaskSubmitDao;
	@Autowired
	private PDictionaryDao pDictionaryDao;
	@Autowired
	private MUserInfoDao mUserInfoDao;
	
	@Override
	public Result add(LUserTptask lUserTptask) {
		Result result = new Result();
		new AWorker<String>() {
			@Override
			public String call(String userId) throws Exception {
				LUserTptask old = lUserTptaskDao.selectByTaskId(lUserTptask.getTpTaskId(), lUserTptask.getUserId(),1);
				if(old != null){
					result.setMessage(RespCodeState.OPERATION_FREQUENTLY.getMessage());
					result.setStatusCode(RespCodeState.OPERATION_FREQUENTLY.getStatusCode());
					return "1";
				}
				List<LUserTptask> olds = lUserTptaskDao.selectByTaskIdHasFinish(lUserTptask.getTpTaskId(), lUserTptask.getUserId());
				if(olds.size() > 0){
					result.setMessage(RespCodeState.TASK_HAS_FINISH.getMessage());
					result.setStatusCode(RespCodeState.TASK_HAS_FINISH.getStatusCode());
					return "1";
				}
				long now = new Date().getTime();
				// 判断任务数量
				TPTaskInfo tpTaskInfo = tpTaskInfoDao.selectOne(lUserTptask.getTpTaskId());
				int time = tpTaskInfo.getFulfilTime();
				int unit = tpTaskInfo.getTimeUnit(); // 时间单位1天2小时3分钟
				long expireTime = 0l;
				if (1 == unit) {
					expireTime = time * 24 * 60 * 60 * 1000l;
				} else if (2 == unit) {
					expireTime = time * 60 * 60 * 1000l;
				} else if (3 == unit) {
					expireTime = time * 60 * 1000l;
				}
				/*LUserTptask yy = lUserTptaskDao.selectByTaskId(lUserTptask.getTpTaskId(), lUserTptask.getUserId(),5);
				if(yy != null){
					// 插入用户记录表
					yy.setExpireTime(now + expireTime);
					yy.setStatus(1);
					lUserTptaskDao.update(yy);
					yy.setExpireTime(expireTime);
					result.setData(lUserTptask);
					result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
					result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
					return "2"; // 操作成功
				}*/
				long count = lUserTptaskDao.selectTaskCount(lUserTptask.getUserId(),1);
				if(count >= 2){
					result.setMessage(RespCodeState.USER_COUNT_NOT_ENOUGH.getMessage());
					result.setStatusCode(RespCodeState.USER_COUNT_NOT_ENOUGH.getStatusCode());
					return "1";
				}
				
				if (tpTaskInfo.getSurplusChannelTaskNumber() > 0) {
					// 减少渠道用户可用数量
					tpTaskInfo.setSurplusChannelTaskNumber(tpTaskInfo.getSurplusChannelTaskNumber() - 1);
					tpTaskInfoDao.update(tpTaskInfo);
					if(lUserTptask.getAccountId() == null){
						lUserTptask.setAccountId(mUserInfoDao.selectOne(lUserTptask.getUserId()).getAccountId());
					}
					// 插入用户记录表
					lUserTptask.setExpireTime(now + expireTime);
					lUserTptask.setCreateTime(now);
					lUserTptask.setStatus(1);
					lUserTptaskDao.insert(lUserTptask);
					
					lUserTptask.setExpireTime(expireTime);
					result.setData(lUserTptask);
					result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
					result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
					return "2"; // 操作成功
				} else {
					result.setMessage(RespCodeState.TASK_COUNT_NOT_ENOUGH.getMessage());
					result.setStatusCode(RespCodeState.TASK_COUNT_NOT_ENOUGH.getStatusCode());
					return "1"; // 任务数量不足
				}
			}
		}.execute(lUserTptask.getUserId(), "redis_lock_add_task");
		return result;
	}

	@Override
	public List<LUserTptask> queryByUser(String userId, Integer pageSize, Integer pageNum) {
		return null;
	}

	@Override
	public void modify(LUserTptask lUserTptask) {
		lUserTptaskDao.update(lUserTptask);
	}

	@Override
	public Result queryInfo(Integer taskId, String userId) {
		Result result= new Result();
		Map<String, Object> resultMap = new HashMap<>();
		LUserTptask lUserTptask = lUserTptaskDao.selectByTaskId(taskId, userId,1);
		List<LUserTptaskSubmit> lUserTptaskSubmits = lUserTptaskSubmitDao.selectList(userId, taskId);
		resultMap.put("lUserTptask", lUserTptask);
		resultMap.put("submits", lUserTptaskSubmits);
		result.setData(resultMap);
		result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
		result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
		return result;
	}

	@Override
	public Result sendData(Integer id) {
		Result result = new Result();
		long now = new Date().getTime();
		Map<String, Object> param = new HashMap<>();
		LUserTptask lUserTptask = lUserTptaskDao.selectOne(id);
		TPTaskInfo tpTaskInfo = tpTaskInfoDao.selectOne(lUserTptask.getTpTaskId());
		// 判断状态
		if(lUserTptask.getStatus().intValue() != 1){ // -1-已过期 1-待提交 2-已提交，待审核 3-审核通过 4-审核失败
			result.setStatusCode(RespCodeState.TASK_CANNOT_SUBMIT.getStatusCode());
			result.setMessage(RespCodeState.TASK_CANNOT_SUBMIT.getMessage());
			return result;
		}
		// 查询提交记录
		List<LUserTptaskSubmitVo> lUserTptaskSubmitVos = lUserTptaskSubmitDao.selectLUserTptaskSubmitVo(lUserTptask.getUserId(), lUserTptask.getId());
		String task_appkey = pDictionaryDao.selectByName("task_appkey").getDicValue();
		String task_channel = pDictionaryDao.selectByName("task_channel").getDicValue();
		String task_send_data_url = pDictionaryDao.selectByName("task_send_data_url").getDicValue();
		
		param.put("userId", lUserTptask.getAccountId());
		param.put("userChannel", task_channel);
		param.put("taskId", lUserTptask.getTpTaskId());
		param.put("taskName", tpTaskInfo.getName());
		param.put("submitTime", now);
		// appkey+channel+date+userId
		String sign = MD5Util.getMd5(task_appkey+task_channel+now+lUserTptask.getUserId());
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
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return result;
		}
	}

	@Override
	public Result addYY(LUserTptask lUserTptask) {
		Result result = new Result();
		new AWorker<String>() {
			@Override
			public String call(String userId) throws Exception {
				LUserTptask old = lUserTptaskDao.selectByTaskId(lUserTptask.getTpTaskId(), lUserTptask.getUserId(),5);
				if(old != null){
					result.setMessage(RespCodeState.OPERATION_FREQUENTLY.getMessage());
					result.setStatusCode(RespCodeState.OPERATION_FREQUENTLY.getStatusCode());
					return "1";
				}
				long count = lUserTptaskDao.selectTaskCount(lUserTptask.getUserId(),5);
				if(count >= 2){
					result.setMessage(RespCodeState.USER_COUNT_NOT_ENOUGH.getMessage());
					result.setStatusCode(RespCodeState.USER_COUNT_NOT_ENOUGH.getStatusCode());
					return "1";
				}
				long now = new Date().getTime();
				// 判断任务数量
				TPTaskInfo tpTaskInfo = tpTaskInfoDao.selectOne(lUserTptask.getTpTaskId());
				if (tpTaskInfo.getSurplusChannelTaskNumber() > 0) {
					// 减少渠道用户可用数量
					tpTaskInfo.setSurplusChannelTaskNumber(tpTaskInfo.getSurplusChannelTaskNumber() - 1);
					tpTaskInfoDao.update(tpTaskInfo);
					if(lUserTptask.getAccountId() == null){
						lUserTptask.setAccountId(mUserInfoDao.selectOne(lUserTptask.getUserId()).getAccountId());
					}
					int time = tpTaskInfo.getFulfilTime();
					int unit = tpTaskInfo.getTimeUnit(); // 时间单位1天2小时3分钟
					long expireTime = 0l;
					if (1 == unit) {
						expireTime = time * 24 * 60 * 60 * 1000l;
					} else if (2 == unit) {
						expireTime = time * 60 * 60 * 1000l;
					} else if (3 == unit) {
						expireTime = time * 60 * 1000l;
					}
					// 插入用户记录表
					lUserTptask.setExpireTime(expireTime + tpTaskInfo.getOrderTime());
					lUserTptask.setCreateTime(now);
					lUserTptask.setStatus(5);
					lUserTptaskDao.insert(lUserTptask);
					result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
					result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
					return "2"; // 操作成功
				} else {
					result.setMessage(RespCodeState.TASK_COUNT_NOT_ENOUGH.getMessage());
					result.setStatusCode(RespCodeState.TASK_COUNT_NOT_ENOUGH.getStatusCode());
					return "1"; // 任务数量不足
				}
			}
		}.execute(lUserTptask.getUserId(), "redis_lock_addYY_task");
		return result;
	}

	@Override
	public PageInfo<LUserTptaskSimple> queryFlist(LUserTptask lUserTptask) {
		PageHelper.startPage(lUserTptask.getPageNum(), lUserTptask.getPageSize());
		List<LUserTptaskSimple> lUserTptasks = lUserTptaskDao.selectFList(lUserTptask);
		return new PageInfo<>(lUserTptasks);
	}

	@Transactional
	@Override
	public Result givein(Integer id,String remark) {
		Result result = new Result();
		// 修改任务记录状态
		LUserTptask lUserTptask  = lUserTptaskDao.selectOne(id);
		lUserTptask.setRemark(remark);
		lUserTptask.setStatus(-2);
		lUserTptaskDao.update(lUserTptask);
		// 修改任务剩余数量
		TPTaskInfo tpTaskInfo = tpTaskInfoDao.selectOne(lUserTptask.getTpTaskId());
		tpTaskInfo.setSurplusChannelTaskNumber(tpTaskInfo.getSurplusChannelTaskNumber() + 1);
		tpTaskInfoDao.update(tpTaskInfo);
		result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
		result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
		return result;
	}

}

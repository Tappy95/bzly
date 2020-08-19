package com.mc.bzly.impl.thirdparty;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bzly.common.constant.ConstantUtil;
import com.bzly.common.constant.DictionaryUtil;
import com.bzly.common.utils.HttpUtil;
import com.bzly.common.utils.MD5Util;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.bzly.base.RespCodeState;
import com.mc.bzly.base.Result;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.platform.PDictionaryDao;
import com.mc.bzly.dao.thirdparty.MChannelConfigDao;
import com.mc.bzly.dao.thirdparty.TPCallbackDao;
import com.mc.bzly.dao.thirdparty.TPTaskInfoDao;
import com.mc.bzly.dao.thirdparty.TPTaskStatusCallbackDao;
import com.mc.bzly.dao.thirdparty.TaskCallbackDao;
import com.mc.bzly.dao.user.LCoinChangeDao;
import com.mc.bzly.dao.user.LDarenRewardDetailDao;
import com.mc.bzly.dao.user.LUserTptaskDao;
import com.mc.bzly.dao.user.LUserVipDao;
import com.mc.bzly.dao.user.MUserInfoDao;
import com.mc.bzly.model.platform.PDictionary;
import com.mc.bzly.model.thirdparty.MChannelConfig;
import com.mc.bzly.model.thirdparty.TPTaskInfo;
import com.mc.bzly.model.thirdparty.TPTaskStatusCallback;
import com.mc.bzly.model.thirdparty.TaskCallback;
import com.mc.bzly.model.user.LCoinChange;
import com.mc.bzly.model.user.LDarenRewardDetail;
import com.mc.bzly.model.user.LUserTptask;
import com.mc.bzly.model.user.MUserInfo;
import com.mc.bzly.service.thirdparty.TPTaskInfoService;
import com.mc.bzly.util.StringUtil;

@Service(interfaceClass = TPTaskInfoService.class,version = WebConfig.dubboServiceVersion)
public class TPTaskInfoServiceImpl implements TPTaskInfoService {

	@Autowired
	private TPTaskInfoDao tpTaskInfoDao;
	@Autowired
	private PDictionaryDao pDictionaryDao;
	@Autowired
	private TaskCallbackDao taskCallbackDao;
	@Autowired
	private MUserInfoDao mUserInfoDao;
	@Autowired
	private LCoinChangeDao lCoinChangeDao;
	@Autowired
	private LUserTptaskDao lUserTptaskDao;
	@Autowired
	private MChannelConfigDao mChannelConfigDao;
	@Autowired
	private TPCallbackDao tPCallbackDao;
	@Autowired
	private TPTaskStatusCallbackDao tpTaskStatusCallbackDao;
	@Autowired
	private LDarenRewardDetailDao lDarenRewardDetailDao;
	@Autowired
	private LUserVipDao lUserVipDao;
	
	@Override
	public int getTasks() {
		List<TPTaskInfo> tpTaskInfos = new ArrayList<>();
		String task_appkey = pDictionaryDao.selectByName("task_appkey").getDicValue();
		String task_channel = pDictionaryDao.selectByName("task_channel").getDicValue();
		String task_list_url = pDictionaryDao.selectByName("task_list_url").getDicValue();
		long createTime = new Date().getTime();
		String sign = MD5Util.getMd5(task_appkey+task_channel+createTime);
		Map<String, Object> param = new HashMap<>();
		param.put("appkey", task_appkey); // 平台给的appkey
		param.put("channelNo", task_channel); // 平台给的渠道唯一标识
		param.put("date", createTime); // 请求时间戳（毫秒值）
		param.put("sign", sign.toUpperCase()); // 签名 md5大写，格式：appkey+channelNo+date
		param.put("pageSize", 500);
		param.put("pageNum", 1); 
		try {
			String rspText = HttpUtil.sendGet(param, task_list_url);
			Result result = JSON.parseObject(rspText, Result.class);
			if(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode().equals(result.getStatusCode())){
				List<TPTaskInfo> TPTaskInfo = JSON.parseArray(result.getData().toString(), TPTaskInfo.class);
				for (TPTaskInfo taskInfo : TPTaskInfo) {
					TPTaskInfo oldTPTaskInfo = tpTaskInfoDao.selectOne(taskInfo.getId());
					if(oldTPTaskInfo != null){
						if(oldTPTaskInfo.getIsOrder().intValue() == 1){
							if(taskInfo.getIsOrder().intValue() == 2){
								tpTaskInfoDao.updateIsUpper(taskInfo.getId());
								// 将已预约的记录修改成待提交，提价时间
								List<LUserTptask> lUserTptask = lUserTptaskDao.selectListByTaskId(taskInfo.getId());
								if(lUserTptask.size() > 0){
									int time = taskInfo.getFulfilTime();
									int unit = taskInfo.getTimeUnit(); // 时间单位1天2小时3分钟
									long expireTime = 0l;
									if (1 == unit) {
										expireTime = time * 24 * 60 * 60 * 1000;
									} else if (2 == unit) {
										expireTime = time * 60 * 60 * 1000;
									} else if (3 == unit) {
										expireTime = time * 60 * 1000;
									}
									expireTime = createTime + expireTime;
									lUserTptaskDao.batchUpdateStatus(expireTime,taskInfo.getId());
								}
							}
						}
					}else {
						taskInfo.setIsSignin(2);
						tpTaskInfos.add(taskInfo);
						if(tpTaskInfos.size() > 50){
							tpTaskInfoDao.batchInsert(tpTaskInfos);
							tpTaskInfos.clear();
						}
					}
				}
			}else if(RespCodeState.SIGN_ERROR.getStatusCode().equals(result.getStatusCode())){
				return 4;
			}else if(RespCodeState.HAVE_NO_DATA.getStatusCode().equals(result.getStatusCode())){
				return 3;
			}else if(RespCodeState.ADMIN_NO_PERMISSION.getStatusCode().equals(result.getStatusCode())){
				return 5;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 2;
		}
		if(tpTaskInfos.size() > 0){
			tpTaskInfoDao.batchInsert(tpTaskInfos);
		}
		return 1;
	}

	@Override
	public void modify(TPTaskInfo tpTaskInfo) {
		tpTaskInfoDao.update(tpTaskInfo);
	}

	@Override
	public PageInfo<TPTaskInfo> queryList(TPTaskInfo tpTaskInfo) {
		PageHelper.startPage(tpTaskInfo.getPageNum(), tpTaskInfo.getPageSize());
		List<TPTaskInfo> tpTaskInfos = tpTaskInfoDao.selectList(tpTaskInfo);
		return new PageInfo<>(tpTaskInfos);
	}

	@Override
	public PageInfo<TPTaskInfo> queryFList(TPTaskInfo tpTaskInfo) {
		MUserInfo muserInfo = mUserInfoDao.selectOne(tpTaskInfo.getUserId());
		PageHelper.startPage(tpTaskInfo.getPageNum(), tpTaskInfo.getPageSize());
		List<TPTaskInfo> tpTaskInfos = tpTaskInfoDao.selectList(tpTaskInfo);
		if(muserInfo.getRoleType().intValue() != ConstantUtil.ROLE_TYPE_4){
			for (TPTaskInfo tpTaskInfo2 : tpTaskInfos) {
				tpTaskInfo2.setDrReward(0d);
			}
		}
		return new PageInfo<>(tpTaskInfos);
	}

	@Override
	public Result info(Integer id,String userId) {
		Result result =  new Result();
		String task_appkey = pDictionaryDao.selectByName("task_appkey").getDicValue();
		String task_channel = pDictionaryDao.selectByName("task_channel").getDicValue();
		String task_info_url = pDictionaryDao.selectByName("task_info_url").getDicValue();
		long createTime = new Date().getTime();
		String sign = MD5Util.getMd5(task_appkey+task_channel+createTime);
		Map<String, Object> param = new HashMap<>();
		param.put("id", id); // 平台给的appkey
		param.put("appkey", task_appkey); // 平台给的appkey
		param.put("channelNo", task_channel); // 平台给的渠道唯一标识
		param.put("date", createTime); // 请求时间戳（毫秒值）
		param.put("sign", sign.toUpperCase()); // 签名 md5大写，格式：appkey+channelNo+date
		try {
			String rspText = HttpUtil.sendGet(param, task_info_url);
			result = JSON.parseObject(rspText, Result.class);
			// 查询用户对于该任务的记录
			if(StringUtils.isNotEmpty(userId)){
				LUserTptask lUserTptask = lUserTptaskDao.selectByTaskId(id, userId, 1);
				if(lUserTptask == null){
					lUserTptask = lUserTptaskDao.selectByTaskId(id, userId, 5);
					lUserTptask.setExpireTime(0l);
				}else {
					lUserTptask.setExpireTime(lUserTptask.getExpireTime() - new Date().getTime());
				}
				JSONObject o = JSON.parseObject(result.getData().toString());
				o.put("lUserTptask", lUserTptask);
				result.setData(o);
			}
			
		}catch (Exception e) {
		}
		return result;
	}

	@Override
	public TPTaskInfo queryOne(Integer id) {
		return tpTaskInfoDao.selectOne(id);
	}

	@Override
	public TaskCallback queryCallBack(String orderNum) {
		return taskCallbackDao.selectByOrderNum(orderNum);
	}

	@Override
	public long settle(TaskCallback taskCallback) {
		int accountId = new Integer(taskCallback.getUserId());
		long reward = (long)(taskCallback.getUserReward()*10000);
		long now = new Date().getTime();
		LUserTptask lUserTptask = lUserTptaskDao.selectByTaskIdAccount(taskCallback.getTaskId(),accountId, 2);
		if(lUserTptask != null){
			if(taskCallback.getResultCode().intValue() == 2){
				MUserInfo mUserInfo = mUserInfoDao.selectByAccountId(accountId);
				mUserInfo.setCoin(mUserInfo.getCoin() + reward);
				mUserInfo.setUpdateTime(now);
				LCoinChange lCoinChange = new LCoinChange(mUserInfo.getUserId(), reward, ConstantUtil.FLOWTYPE_IN, ConstantUtil.COIN_CHANGED_TYPE_23, now,
						1, taskCallback.getName(), mUserInfo.getCoin());
				// 修改用户金币
				mUserInfoDao.update(mUserInfo);
				// 添加用户金币变动记录
				lCoinChangeDao.insert(lCoinChange);
				lUserTptask.setStatus(3);
				// 给达人奖励
				MUserInfo referrer = mUserInfoDao.selectOne(mUserInfo.getReferrer());
				if(referrer != null ){
					if(referrer.getRoleType().intValue() == ConstantUtil.ROLE_TYPE_4){
						// 好友做的高额赚前2个奖励还有2-10元提成（注册7天内）。
						long regTime = mUserInfo.getCreateTime();
						String days = pDictionaryDao.selectByName("daren_task_reward_day").getDicValue(); // 达人邀请多少天内完成高额赚有奖励
						int day = new Integer(days);
						long endTime = 24 * 60 * 60 * 1000 * day;
						long cz = now - regTime;
						TPTaskInfo tpTaskInfo = tpTaskInfoDao.selectOne(taskCallback.getTaskId());
						PDictionary pDictionary = pDictionaryDao.selectByName("daren_task_reward_count"); // 达人高额赚奖励次数
						List<LUserTptask> list  = lUserTptaskDao.selectByUserHasFinish(mUserInfo.getUserId());
						int count = new Integer(pDictionary.getDicValue());
						if(count >= list.size() && cz < endTime){
							if(tpTaskInfo.getDrReward() > 0){
								long drRewardD = (long) (tpTaskInfo.getDrReward() * 10000);
								referrer.setCoin(referrer.getCoin() + drRewardD);
								referrer.setUpdateTime(now);
								LCoinChange referrerCoinChange = new LCoinChange(referrer.getUserId(), drRewardD, ConstantUtil.FLOWTYPE_IN, ConstantUtil.COIN_CHANGED_TYPE_28, now,
										1, mUserInfo.getAccountId()+"完成"+taskCallback.getName()+"提成", referrer.getCoin());
								// 修改用户金币
								mUserInfoDao.update(referrer);
								// 添加用户金币变动记录
								lCoinChangeDao.insert(referrerCoinChange);
								// 新增达人变帐明细
								LDarenRewardDetail lDarenRewardDetail = new LDarenRewardDetail();
								lDarenRewardDetail.setUserId(referrer.getUserId());
								lDarenRewardDetail.setReward(drRewardD);
								lDarenRewardDetail.setApprenticeId(mUserInfo.getUserId());
								lDarenRewardDetail.setApprenticeReward(reward);
								lDarenRewardDetail.setTaskType(3);
								lDarenRewardDetail.setTaskName(taskCallback.getName());
								lDarenRewardDetail.setCreateTime(now);
								lDarenRewardDetailDao.insert(lDarenRewardDetail);
							}
						}
					}
				}
			}else {
				reward = 0l;
				lUserTptask.setStatus(4);
				// 把剩余数量修改回来
				TPTaskInfo tpTaskInfo = tpTaskInfoDao.selectOne(taskCallback.getTaskId());
				tpTaskInfo.setSurplusChannelTaskNumber(tpTaskInfo.getSurplusChannelTaskNumber() + 1);
				tpTaskInfoDao.update(tpTaskInfo);
			}
			lUserTptask.setRemark(taskCallback.getRemark());
			lUserTptask.setUpdateTime(now);
			lUserTptaskDao.update(lUserTptask);
		}else {
			return 0;
		}
		// 修改用户任务记录状态
		return reward;
	}

	@Override
	public void addCallBack(TaskCallback taskCallback) {
		taskCallbackDao.insert(taskCallback);
	}

	@Override
	public Map<String, Object> getConductTask(String userId) {
		Map<String,Object> data=new HashMap<>();
		data.put("isGame", 1);//是否有访问限制1没有2有限制且未匹配任何任务3有限制且匹配了任务
		MUserInfo mUserInfo = mUserInfoDao.selectOne(userId);
		String channelCode = mUserInfo.getChannelCode();
    	if(StringUtil.isNullOrEmpty(channelCode)) {
    		channelCode=mUserInfo.getParentChannelCode();
    	}
    	if(StringUtil.isNullOrEmpty(channelCode)){
    		channelCode = "baozhu";
    	}
    	MChannelConfig mChannelConfig = mChannelConfigDao.selectByChannelCode(channelCode);
    	if(mChannelConfig == null){
    		mChannelConfig = mChannelConfigDao.selectByChannelCode("baozhu");
    	}
    	if(mChannelConfig.getApplyTask().intValue()==1) {
    		PDictionary rechargeDictionary=pDictionaryDao.selectByName(DictionaryUtil.USER_APPLY_TASK);
    		int taskCount=lUserTptaskDao.selectUserCount(userId);
    		long gameCount=tPCallbackDao.selectCount(userId);
    		long count=gameCount+taskCount;
    		if(count<Long.parseLong(rechargeDictionary.getDicValue())) {
    			TPTaskInfo tpTaskInfo=tpTaskInfoDao.selectUserTask(userId);
    			if(StringUtil.isNullOrEmpty(tpTaskInfo)) {
    				data.put("isGame", 2);
    			}else {
    				data.put("isGame", 3);
    				data.put("task", tpTaskInfo);
    			}    			
    		}
    		
    	}
		return data;
	}

	@Override
	public String buildUrl(String userId, Integer id,String bzlyuserChannel,String bzlyAppkey) {
		// 判断用户是否可以继续做高额赚任务
		MUserInfo mUserInfo = mUserInfoDao.selectOne(userId);
		if("shendumeng".equals(mUserInfo.getChannelCode()) || "shendumeng".equals(mUserInfo.getParentChannelCode())){
			List<LUserTptask> userTptasks = lUserTptaskDao.selectByUserHasFinish(userId);
			if(userTptasks.size() >= 2){
				long count = lUserVipDao.selectHighVipHasBuy(userId);
				if(count < 1){
					return "HighVipFirst";
				}
			}
		}
		TPTaskInfo tpTaskInfo = tpTaskInfoDao.selectOne(id);
		String sign = MD5Util.getMd5(mUserInfo.getAccountId() + bzlyuserChannel + id + bzlyAppkey);
		String url = tpTaskInfo.getTaskInfoUrl();
		url = url.replace("[userId]", mUserInfo.getAccountId()+"")
				.replace("[sign]", sign)
				.replace("[userChannel]",bzlyuserChannel);
		return url;
	}

	@Override
	public void taskUserChangedCallback(TPTaskStatusCallback tpTaskStatusCallback) {
		MUserInfo mUserInfo = mUserInfoDao.selectByAccountId(new Integer(tpTaskStatusCallback.getUserId()));
		TPTaskInfo tpTaskInfo = tpTaskInfoDao.selectOne(tpTaskStatusCallback.getTaskId());
		int status = tpTaskStatusCallback.getStatus().intValue();
		if(tpTaskStatusCallback.getExpireTime().longValue() == 0l){
			tpTaskStatusCallback.setExpireTime(null);
		}
		LUserTptask lUserTptask = new LUserTptask();
		long now = new Date().getTime();
		try{
			switch (status) {
			case 1:
				lUserTptask.setAccountId(mUserInfo.getAccountId());
				lUserTptask.setUserId(mUserInfo.getUserId());
				lUserTptask.setTpTaskId(tpTaskStatusCallback.getTaskId());
				lUserTptask.setStatus(1);
				lUserTptask.setCreateTime(now);
				lUserTptask.setUpdateTime(now);
				lUserTptask.setRemark("领取任务");
				lUserTptask.setFlewNum(tpTaskStatusCallback.getFlewNum());
				lUserTptask.setExpireTime(tpTaskStatusCallback.getExpireTime());
				tpTaskInfo.setSurplusChannelTaskNumber(tpTaskInfo.getSurplusChannelTaskNumber() - 1);
				tpTaskInfoDao.update(tpTaskInfo);
				if(lUserTptask.getAccountId() == null){
					lUserTptask.setAccountId(mUserInfoDao.selectOne(lUserTptask.getUserId()).getAccountId());
				}
				lUserTptaskDao.insert(lUserTptask);
				break;
			case 5:
				lUserTptask.setAccountId(mUserInfo.getAccountId());
				lUserTptask.setUserId(mUserInfo.getUserId());
				lUserTptask.setTpTaskId(tpTaskStatusCallback.getTaskId());
				lUserTptask.setStatus(5);
				lUserTptask.setCreateTime(now);
				lUserTptask.setUpdateTime(now);
				lUserTptask.setRemark("预约成功");
				lUserTptask.setFlewNum(tpTaskStatusCallback.getFlewNum());
				lUserTptask.setExpireTime(tpTaskStatusCallback.getExpireTime());
				tpTaskInfo.setSurplusChannelTaskNumber(tpTaskInfo.getSurplusChannelTaskNumber() - 1);
				tpTaskInfoDao.update(tpTaskInfo);
				if(lUserTptask.getAccountId() == null){
					lUserTptask.setAccountId(mUserInfoDao.selectOne(lUserTptask.getUserId()).getAccountId());
				}
				lUserTptaskDao.insert(lUserTptask);
				break;
			case 2:
				lUserTptask = lUserTptaskDao.selectByFlewNum(tpTaskStatusCallback.getFlewNum());
				lUserTptask.setStatus(2);
				lUserTptask.setUpdateTime(now);
				lUserTptask.setRemark("材料已提交，等待审核");
				lUserTptaskDao.updateStatus(lUserTptask);
				break;
			case 3:
				lUserTptask = lUserTptaskDao.selectByFlewNum(tpTaskStatusCallback.getFlewNum());
				lUserTptask.setStatus(3);
				lUserTptask.setUpdateTime(now);
				lUserTptask.setRemark("审核通过");
				lUserTptaskDao.updateStatus(lUserTptask);
			case 4:
				lUserTptask = lUserTptaskDao.selectByFlewNum(tpTaskStatusCallback.getFlewNum());
				lUserTptask.setStatus(4);
				lUserTptask.setUpdateTime(now);
				lUserTptask.setRemark("审核未通过");
				lUserTptaskDao.updateStatus(lUserTptask);
				break;
			case 6:
				lUserTptask = lUserTptaskDao.selectByFlewNum(tpTaskStatusCallback.getFlewNum());
				lUserTptask.setStatus(-1);
				lUserTptask.setUpdateTime(now);
				lUserTptask.setRemark("任务已过期");
				lUserTptaskDao.updateStatus(lUserTptask);
				tpTaskInfo.setSurplusChannelTaskNumber(tpTaskInfo.getSurplusChannelTaskNumber() + 1);
				tpTaskInfoDao.update(tpTaskInfo);
				break;
			case 7:
				lUserTptask = lUserTptaskDao.selectByFlewNum(tpTaskStatusCallback.getFlewNum());
				lUserTptask.setStatus(-2);
				lUserTptask.setUpdateTime(now);
				lUserTptask.setRemark("任务已放弃");
				lUserTptaskDao.updateStatus(lUserTptask);
				tpTaskInfo.setSurplusChannelTaskNumber(tpTaskInfo.getSurplusChannelTaskNumber() + 1);
				tpTaskInfoDao.update(tpTaskInfo);
				break;
			}
			tpTaskStatusCallback.setDealStatus(1);
		}catch (Exception e) {
			e.printStackTrace();
			tpTaskStatusCallback.setDealStatus(2);
		}
		tpTaskStatusCallback.setCreateTime(now);
		tpTaskStatusCallbackDao.insert(tpTaskStatusCallback);
	}

	@Override
	public int delete(Integer id) {
		return tpTaskInfoDao.delete(id);
	}

}

package com.mc.bzly.impl.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.bzly.common.constant.ConstantUtil;
import com.bzly.common.utils.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.bzly.base.RespCodeState;
import com.mc.bzly.base.Result;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.platform.PDictionaryDao;
import com.mc.bzly.dao.thirdparty.TPGameDao;
import com.mc.bzly.dao.user.LCoinChangeDao;
import com.mc.bzly.dao.user.LUserBQDao;
import com.mc.bzly.dao.user.LUserSignGameDao;
import com.mc.bzly.dao.user.LUserSigninDao;
import com.mc.bzly.dao.user.MUserInfoDao;
import com.mc.bzly.dao.user.RSUserPassbookDao;
import com.mc.bzly.impl.redis.AWorker;
import com.mc.bzly.model.news.AppNewsNotice;
import com.mc.bzly.model.thirdparty.TPGame;
import com.mc.bzly.model.user.LCoinChange;
import com.mc.bzly.model.user.LUserBQ;
import com.mc.bzly.model.user.LUserSignGame;
import com.mc.bzly.model.user.LUserSignin;
import com.mc.bzly.model.user.MUserInfo;
import com.mc.bzly.model.user.RSUserPassbook;
import com.mc.bzly.service.user.LUserSigninService;

@Service(interfaceClass = LUserSigninService.class,version = WebConfig.dubboServiceVersion)
public class LUserSigninServiceImpl implements LUserSigninService {

	@Autowired
	private LUserSigninDao lUserSigninDao;
	@Autowired
	private MUserInfoDao mUserInfoDao;
	@Autowired
	private PDictionaryDao pDictionaryDao;
	@Autowired
	private LUserSignGameDao lUserSignGameDao;
	@Autowired
	private LCoinChangeDao lCoinChangeDao;
	@Autowired
	private LUserBQDao lUserBQDao;
	@Autowired
	private TPGameDao tpGameDao;
	@Autowired
	private RSUserPassbookDao rsUserPassbookDao;
	
	@Override
	public void add(LUserSignin lUserSignin) {
		lUserSignin.setCreateTime(new Date().getTime());
		lUserSignin.setStatus(1);
		lUserSigninDao.insert(lUserSignin);
	}

	@Override
	public void modify(LUserSignin lUserSignin) {
		lUserSignin.setUpdateTime(new Date().getTime());
		lUserSigninDao.update(lUserSignin);
	}

	@Transactional
	@Override
	public Result init(LUserSignin lUserSignin) {
		Result result = new Result();
		Map<String, Object> resultMap = new HashMap<>();
		// 如果是老用户，活动开始时间从配置里面去，如果是新用户，则从注册时间开始算
		String qdz_start_time = pDictionaryDao.selectByName("qdz_start_time").getDicValue();
		long qdz_start_time_l = DateUtil.praseStringDate(qdz_start_time).getTime();
		MUserInfo mUserInfo = mUserInfoDao.selectOne(lUserSignin.getUserId());
		if(mUserInfo.getCreateTime() > qdz_start_time_l){
			qdz_start_time_l = mUserInfo.getCreateTime();
		}
		long now = new Date().getTime();
		// 根据时间，计算今天是第几天
		int days =(int) Math.ceil((now - qdz_start_time_l) * 1.0 / (24*60*60*1000));
		if(days <= 0){
			result.setData(null);
			result.setMessage(RespCodeState.NOT_IN_ACTIVITY_TIMINT.getMessage());
			result.setStatusCode(RespCodeState.NOT_IN_ACTIVITY_TIMINT.getStatusCode());
			return result;
		}
		List<LUserSignin> resultList = null;
		synchronized (lUserSignin.getUserId()) {
			resultList = lUserSigninDao.selectList(lUserSignin);
			if(resultList.size() == 0 ){
				List<LUserSignin> insertList = getInsertList(days, lUserSignin.getUserId(), now);
				lUserSigninDao.batchInsert(insertList);
				resultList = lUserSigninDao.selectList(lUserSignin);
			}else {
				for (int i = 0; i < resultList.size(); i++) {
					if(resultList.get(i).getStatus().intValue() == -1){
						if(days > resultList.get(i).getSignDay().intValue()){
							resultList.get(i).setStatus(1);
							lUserSigninDao.updateStatus(lUserSignin.getUserId(), resultList.get(i).getSignDay(), 1);	
						}else if(days == resultList.get(i).getSignDay().intValue()){
							resultList.get(i).setStatus(2);
							lUserSigninDao.updateStatus(lUserSignin.getUserId(), resultList.get(i).getSignDay(), 2);	
						}
					}
				}
			}
		}
		resultMap.put("list", resultList);
		resultMap.put("day", days);
		result.setData(resultMap);
		result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
		result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
		return result;
	}
	
 	/**
	 * 创建分享赚记录，1-一个游戏 2，5，10，15-2个游戏，其他3个游戏
	 * @param dayList
	 * @param userId
	 * @param now
	 * @return
	 */
	public List<LUserSignin> getInsertList(int day,String userId,long now){
		List<LUserSignin> resultList = new ArrayList<LUserSignin>();
		int status = 1;
		for (int i = 1; i <= 15; i++) {
			if(i == day){
				status = 2;
			}else if(i > day){
				status = -1;
			}
			if(i == 1){
				LUserSignin lUserSignin = new LUserSignin(userId, i,1, now, status,1);
				resultList.add(lUserSignin);
			}else if(i == 2){
				LUserSignin lUserSignin = new LUserSignin(userId, i,2, now, status,1);
				resultList.add(lUserSignin);
			}else if(i == 5 || i == 10){
				LUserSignin lUserSignin = new LUserSignin(userId, i,2, now, status,3);
				resultList.add(lUserSignin);
			}else if(i == 15){
				LUserSignin lUserSignin = new LUserSignin(userId, i,2, now, status,8);
				resultList.add(lUserSignin);
			}else{
				LUserSignin lUserSignin = new LUserSignin(userId, i,3, now, status,1);
				resultList.add(lUserSignin);
			}
		}
		return resultList;
	}
 
	@Override
	public Result receiveReward(LUserSignin lUserSignin) {
		Result result = new Result();
		long now = new Date().getTime();
		// 查看是否所有游戏任务都完成
		String str = new AWorker<Integer>() {
			@Override
			public String call(Integer signinId) throws Exception {
				LUserSignin lUserSignin1 = lUserSigninDao.selectOne(lUserSignin.getId());
				List<LUserSignGame> lUserSignGames = lUserSignGameDao.selectByUserSignin(lUserSignin1.getUserId(), lUserSignin1.getId(),2);
				if(lUserSignin1.getGameCount().intValue() > lUserSignGames.size()){
					result.setStatusCode(RespCodeState.TASK_NOT_ENOUGH.getStatusCode());
					result.setMessage(RespCodeState.TASK_NOT_ENOUGH.getMessage());
				}
				// 查看是否已领取
				if(lUserSignin1.getStatus() != 3){
					result.setStatusCode(RespCodeState.CAN_NOT_RECEIVE.getStatusCode());
					result.setMessage(RespCodeState.CAN_NOT_RECEIVE.getMessage());
					return "1";
				}
				// 修改领取状态
				lUserSignin1.setStatus(4);
				lUserSignin1.setUpdateTime(now);
				lUserSigninDao.update(lUserSignin1);
				// 修改账户金币余额
				MUserInfo mUserInfo = mUserInfoDao.selectOne(lUserSignin1.getUserId());
				long reward = lUserSignin1.getReward() * 10000l;
				mUserInfo.setCoin(mUserInfo.getCoin() + reward);
				mUserInfo.setUpdateTime(now);
				mUserInfoDao.update(mUserInfo);
				// 新增金币变更记录
				LCoinChange lCoinChange = new LCoinChange(lUserSignin1.getUserId(), reward, ConstantUtil.FLOWTYPE_IN,
						ConstantUtil.COIN_CHANGED_TYPE_21, now, 1,"签到赚",mUserInfo.getCoin());
				lCoinChangeDao.insert(lCoinChange);
				result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
				result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
				return "2";
			}
		}.execute(lUserSignin.getId(), "redis_lock_signin");
		return result;
	}

	@Override
	public Map<String, Object> todayFinish(String userId) {
		// 获取今日的毫秒值
		long now = new Date().getTime();
		long dayT = 24 * 60 * 60 * 1000l;
		long time = now - (now % dayT);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 今日完成任务数
		long count = lUserSignGameDao.selectCountByUser(userId,time);
		resultMap.put("taskCount", count);
		if(count == 0){
			resultMap.put("coinTotal", 0);
		}else {
			// 今日签到赚得金币数 1-游戏任务 2-签到奖励
			long coin1 = lUserSignGameDao.selectSumByUser(userId,time);
			long coin2 = lCoinChangeDao.selectSumByUser(userId,time,ConstantUtil.COIN_CHANGED_TYPE_21);
			resultMap.put("coinTotal", coin1 + coin2);
		}
		LUserBQ lUserBQ = lUserBQDao.selectByUserId(userId,1);
		if(lUserBQ == null){
			resultMap.put("bqCount", 0);
		}else{
			resultMap.put("bqCount",lUserBQ.getCardCount());
		}
		// 是否有加成券
		RSUserPassbook rsUserPassbook = rsUserPassbookDao.selectAddDiscount(userId);
		if(rsUserPassbook == null){
			resultMap.put("addDiscount", 1);
		}else{
			resultMap.put("addDiscount", 2);
		}
		return resultMap;
	}

	@Override
	public List<TPGame> signinGames(int ptype,String userId, Integer signinId) {
		List<TPGame> tpGames = new ArrayList<>();
		LUserSignin lUserSignin = lUserSigninDao.selectOne(signinId);
		List<LUserSignGame> lUserSignGames = lUserSignGameDao.selectByUserSignin(userId, signinId, null);
		if(lUserSignGames.size() > 0){
			for (LUserSignGame lUserSignGame : lUserSignGames) {
				TPGame tpGame = new TPGame();
				tpGame = tpGameDao.selectOne(lUserSignGame.getGameId());
				tpGame.setTaskStatus(lUserSignGame.getStatus());
				tpGames.add(tpGame);
			}
			// 如果游戏是已完成的，并且游戏数比需要做的任务数小，则需要多分配一个游戏
			if(lUserSignin.getGameCount().intValue() > lUserSignGames.size() && lUserSignGames.get(lUserSignGames.size() - 1).getStatus() == 2){
				TPGame tpGameNew = new TPGame();
				List<TPGame> tpGameAll = tpGameDao.recommendGameList(ptype,userId);
				Set<TPGame> tpGameSet = new HashSet<TPGame>(tpGameAll);
				for (TPGame TPGame:tpGameSet) {
					tpGameNew = TPGame;
					tpGameNew.setTaskStatus(1);
					if(tpGameNew.getId() != null){
						break;
					}
				}
				tpGames.add(tpGameNew);
			}
		}else {
			TPGame tpGame = new TPGame();
			List<TPGame> tpGameAll = tpGameDao.recommendGameList(ptype,userId);
			Set<TPGame> tpGameSet = new HashSet<TPGame>(tpGameAll);
			for (TPGame TPGame:tpGameSet) {
				tpGame = TPGame;
				tpGame.setTaskStatus(1);
				if(tpGame.getId() != null){
					break;
				}
			}
			tpGames.add(tpGame);
		}
		return tpGames;
	}

	@Override
	public Map<String, Object> pageList(LUserSignin lUserSignin) {
		Map<String, Object> result = new HashMap<String, Object>();
		/*lUserSignin.setPageIndex(lUserSignin.getPageSize() * (lUserSignin.getPageNum() - 1));*/
		List<LUserSignin> LUserSigninList =lUserSigninDao.selectPageList(lUserSignin);
		/*int count=lUserSigninDao.selectPageCount(lUserSignin);*/
		result.put("list", LUserSigninList);
		/*result.put("total", count);*/
		return result;
	}

	@Override
	public PageInfo<LUserSignGame> signinGameList(LUserSignGame lUserSignGame) {
		PageHelper.startPage(lUserSignGame.getPageNum(), lUserSignGame.getPageSize());
		List<LUserSignGame> lUserSignGameList =lUserSignGameDao.selectSignList(lUserSignGame);
		return new PageInfo<>(lUserSignGameList);
	}

	@Override
	public LUserSignin selectOne(Integer id) {
		return lUserSigninDao.selectOne(id);
	}

	@Override
	public int deleteGame(Integer id) {
		return lUserSignGameDao.delete(id);
	}
	
	
}
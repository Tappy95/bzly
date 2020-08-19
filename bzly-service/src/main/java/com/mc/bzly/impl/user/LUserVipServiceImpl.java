package com.mc.bzly.impl.user;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.bzly.common.constant.ConstantUtil;
import com.bzly.common.constant.InformConstant;
import com.bzly.common.utils.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.news.AppNewsInformDao;
import com.mc.bzly.dao.platform.PDictionaryDao;
import com.mc.bzly.dao.user.LCoinChangeDao;
import com.mc.bzly.dao.user.LPigChangeDao;
import com.mc.bzly.dao.user.LUserActivityDao;
import com.mc.bzly.dao.user.LUserVipDao;
import com.mc.bzly.dao.user.MUserApprenticeDao;
import com.mc.bzly.dao.user.MUserInfoDao;
import com.mc.bzly.model.jms.JmsWrapper.Type;
import com.mc.bzly.model.news.AppNewsInform;
import com.mc.bzly.model.user.LPigChange;
import com.mc.bzly.model.user.LUserActivity;
import com.mc.bzly.model.user.LUserVip;
import com.mc.bzly.model.user.MUserApprentice;
import com.mc.bzly.model.user.MUserInfo;
import com.mc.bzly.service.jms.JMSProducer;
import com.mc.bzly.service.user.LUserVipService;
import com.mc.bzly.util.StringUtil;

@Service(interfaceClass = LUserVipService.class,version = WebConfig.dubboServiceVersion)
public class LUserVipServiceImpl implements LUserVipService {

	private Logger logger = LoggerFactory.getLogger(LUserVipServiceImpl.class);
	
	@Autowired
	private LUserVipDao lUserVipDao;
	
	@Autowired 
	private MUserInfoDao mUserInfoDao;
	
	@Autowired
	private AppNewsInformDao appNewsInformDao;
	
	@Autowired
	private PDictionaryDao pDictionaryDao;
	@Autowired
	private LPigChangeDao lPigChangeDao;
	@Autowired
	private LCoinChangeDao lCoinChangeDao;
	@Autowired
	private JMSProducer jmsProducer;
	@Autowired
	private MUserApprenticeDao mUserApprenticeDao;
	@Autowired
	private LUserActivityDao lUserActivityDao;
	
	@Override
	public int add(LUserVip lUserVip) throws Exception {
		lUserVip.setCreateTime(new Date().getTime());
		return lUserVipDao.insert(lUserVip);
	}

	@Override
	public int modify(LUserVip lUserVip) throws Exception {
		return lUserVipDao.update(lUserVip);
	}

	@Override
	public int remove(Integer id) throws Exception {
		return lUserVipDao.delete(id);
	}

	@Override
	public LUserVip queryOne(Integer id) {
		return lUserVipDao.selectOne(id);
	}

	@Override
	public PageInfo<LUserVip> queryList(LUserVip lUserVip) {
		PageHelper.startPage(lUserVip.getPageNum(), lUserVip.getPageSize());
		List<LUserVip> lUserVips = lUserVipDao.selectList(lUserVip);
		return new PageInfo<>(lUserVips);
	}

	@Override
	public void checkOverdue() {
		long hours = 60*60*1000*7;
		long now = new Date().getTime();
		logger.info("LUserVipServiceImpl.checkOverdue...start");
		LUserVip param = new LUserVip();
		param.setStatus(1);
		List<LUserVip> lUserVips = lUserVipDao.selectList(param);
		List<LUserVip> updates = new ArrayList<LUserVip>();
		
		List<AppNewsInform> AppNewsInforms = new ArrayList<AppNewsInform>();
		for (LUserVip lUserVip : lUserVips) {
			MUserInfo mUserInfo = mUserInfoDao.selectOne(lUserVip.getUserId());
			try{
				if(lUserVip.getSurplusDay().intValue() == 0){
					// 设置状态未已过期
					lUserVip.setStatus(2);
					// 通知已过期
					AppNewsInform appNewsInform = new AppNewsInform();
					appNewsInform.setUserId(lUserVip.getUserId());
					appNewsInform.setMobile(mUserInfo.getMobile());
					appNewsInform.setPushTime(now+hours);
					appNewsInform.setCreaterTime(now);
					appNewsInform.setInformTitle(InformConstant.VIP_TITLE_OVERDUE);
					appNewsInform.setInformContent(InformConstant.VIP_CONTENT_OVERDUE);
					appNewsInform.setPushMode(InformConstant.PUSH_MODE_AURORA);
					appNewsInform.setPushObject(1);
					appNewsInform.setIsRelease(1);
					appNewsInform.setInformType(1);
					appNewsInform.setIsPush(1);
					appNewsInform.setAddMode(2);
					appNewsInform.setInformUrl("1");
					AppNewsInforms.add(appNewsInform);
					
				}else if(lUserVip.getSurplusDay().intValue() < 4 ){ // 今天小于4，说明剩余3天，开始提醒用户
					lUserVip.setSurplusDay(lUserVip.getSurplusDay().intValue() - 1 );
					// 通知即将过期
					AppNewsInform appNewsInform = new AppNewsInform();
					appNewsInform.setUserId(lUserVip.getUserId());
					appNewsInform.setMobile(mUserInfo.getMobile());
					appNewsInform.setPushTime(now+hours);
					appNewsInform.setCreaterTime(now);
					appNewsInform.setInformTitle(InformConstant.VIP_TITLE_OVERDUE);
					appNewsInform.setInformContent(InformConstant.VIP_CONTENT_SOON_OVERDUE);
					appNewsInform.setPushMode(InformConstant.PUSH_MODE_AURORA);
					appNewsInform.setPushObject(1);
					appNewsInform.setIsRelease(1);
					appNewsInform.setInformType(1);
					appNewsInform.setIsPush(1);
					appNewsInform.setAddMode(2);
					appNewsInform.setInformUrl("1");
					AppNewsInforms.add(appNewsInform);
					
				}else{ // 只做剩余天数修改
					lUserVip.setSurplusDay(lUserVip.getSurplusDay().intValue() - 1 );
				}
				updates.add(lUserVip);
				// 批量修改
				if(updates.size() > 50){
					lUserVipDao.batchUpdate(updates);
					updates.clear();
				}
				// 插入消息
				if(AppNewsInforms.size() > 50){
					appNewsInformDao.saveInformList(AppNewsInforms);
					AppNewsInforms.clear();
				}
			}catch (Exception e) {
				e.printStackTrace();
				logger.info("LUserVipServiceImpl.checkOverdue异常，id：{}",lUserVip.getId());
				continue;
			}
		}
		// 批量修改
		if(updates.size() > 0){
			lUserVipDao.batchUpdate(updates);
			updates.clear();
		}
		// 插入消息
		if(AppNewsInforms.size() > 0){
			appNewsInformDao.saveInformList(AppNewsInforms);
			AppNewsInforms.clear();
		}
		logger.info("LUserVipServiceImpl.checkOverdue...end");
	}

	@Override
	public List<Map<String, String>> selectVipNews(String channelCode) {
		if(StringUtil.isNullOrEmpty(channelCode)) {
			channelCode="baozhu";
		}
		return lUserVipDao.selectVipNews(channelCode);
	}

	@Override
	public List<Map<String, Object>> queryMyVips(String userId) {
		return lUserVipDao.selectByUserOrder(userId);
	}

	@Override
	public List<Map<String, Object>> queryMyVipsRecord(String userId) {
		// 查询所有的购买记录
		List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> records = lUserVipDao.selectByUserAll(userId);
		for (Map<String, Object> record : records) {
			int vipId = (int)record.get("vipId");
			boolean flag = true;
			for (Map<String, Object> result : results) {
				int resultVipId = (int)result.get("vipId");
				if(vipId == resultVipId){
					flag = false;
					continue;
				}
			}
			if(flag){
				results.add(record);
			}
		}
		return results;
	}

	@Override
	public Map<String, Object> readyWithdrawals(String userId) {
		Map<String, Object> result = new HashMap<String, Object>();
		double coin_to_money = new Double(pDictionaryDao.selectByName("coin_to_money").getDicValue());
		MUserInfo mUserInfo = mUserInfoDao.selectOne(userId);
		double balance = mUserInfo.getCoin() / coin_to_money * 100;
		List<Map<String, Object>> vips = lUserVipDao.selectByUser(userId);
		boolean flag = false;
		for (int i = 0; i < vips.size(); i++) {
			int one_withdrawals = (int) vips.get(i).get("one_withdrawals");
			if(one_withdrawals == 1){
				flag = true;
			}
		}
		boolean isName = true;
		boolean isUpdate = true;
		if(StringUtil.isNullOrEmpty(mUserInfo.getUserName())) {
			isName=false;
		}else {
			int countWx=lCoinChangeDao.selectWXcount(userId);
			if(countWx>0) {
				isUpdate=false;
			}
		}
		Boolean isDaren=false;//
		if(mUserInfo.getRoleType().intValue()==4) {
			isDaren=true;
		}else {
			List<MUserApprentice> apprenticeList=mUserApprenticeDao.selectListByUser(mUserInfo.getUserId(),2);
			if(!StringUtil.isNullOrEmpty(apprenticeList) && apprenticeList.size()>0) {
				isDaren=true;
			}
		}
		if(isDaren) {
			if(mUserInfo.getOpenActivity().intValue()==2) {
				result.put("openActivity", 2);//展示活跃度
				LUserActivity lUserActive=lUserActivityDao.selectByDate(DateUtil.getCurrentDate5(), mUserInfo.getUserId());
				if(lUserActive.getQualityScore().intValue()>=40) {
					result.put("activityScore", 100);//到账金额百分比
				}else if(lUserActive.getQualityScore().intValue()<=39 && lUserActive.getQualityScore().intValue()>=30) {
					result.put("activityScore", 80);//到账金额百分比
				}else if(lUserActive.getQualityScore().intValue()<=29 && lUserActive.getQualityScore().intValue()>=20) {
					result.put("activityScore", 60);//到账金额百分比
				}else if(lUserActive.getQualityScore().intValue()<=19 && lUserActive.getQualityScore().intValue()>=10) {
					result.put("activityScore", 40);//到账金额百分比
				}else if(lUserActive.getQualityScore().intValue()<10){
					result.put("activityScore",20);//到账金额百分比
				}
				result.put("qualityScore", lUserActive.getQualityScore());//质量分
			}else {
				result.put("openActivity", 1);//不展示活跃度
			}
			result.put("daren", 1);
		}else {
			result.put("daren", 2);//不是达人
		}
		result.put("isName", isName);//true有姓名。false没有姓名
		result.put("countWx", isUpdate);//true可修改。false不可修改
		result.put("balance", Math.floor(balance) / 100);
		result.put("one_withdrawals", flag);
		
		return result;
	}

	@Override
	public Map<String, Object> selectReliefPig(String userId) {
		Map<String, Object> data=new HashMap<>();
		Map<String,Object> map=lUserVipDao.selectReliefPig(userId);
		if(map==null) {
			data.put("res", 1);//没有会员
			return data;
		}
		MUserInfo mUserInfo=mUserInfoDao.selectOne(userId);
		if(mUserInfo.getPigCoin()>=10000) {
			data.put("res", 3);//剩余金猪大于10000
			return data;
		}
		if(mUserInfo.getCoin()>=1000) {
			data.put("res", 5);//你有还有足够的金币可以兑换为金猪
			return data;
		}
		int num=lPigChangeDao.getReliefPig(userId);
		if(num>=(int)map.get("everydayReliefPigTimes")) {
			data.put("res", 2);//领取次数已用完
			return data;
		}
		Map<String,Object> jms=new HashMap<>();
		jms.put("userId", userId);
		jms.put("pig", (long)map.get("everydayReliefPig"));
		jms.put("num", (int)map.get("everydayReliefPigTimes"));
		
		jmsProducer.reliefPig(Type.RELIE_PIG, jms);
		
	    data.put("res", 4);//领取成功
	    data.put("pigNum", map.get("everydayReliefPig"));//领取金额
		return data;
	}

	@Override
	public Map<String, Object> receiveReliefPig(String userId) throws ParseException {
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, Object> data=new HashMap<>();
		long now = new Date().getTime();
		// 查询是否存在正在投注中的投注记录--宝猪28
		long bzCount = mUserInfoDao.selectBZ28INGCount(userId);
		if(bzCount > 0){
			data.put("res", 6);//您有金猪在游戏中投资尚未揭晓！
			return data;
		}
		// 查询是否存在正在投注中的投注记录--PC28
		long xyCount = mUserInfoDao.selectXY28INGCount(userId);
		if(xyCount > 0){
			data.put("res", 6);//您有金猪在游戏中投资尚未揭晓！
			return data;
		}
		Map<String,Object> map=lUserVipDao.selectReliefPig(userId);
		// 查询普通用户每日可领取次数
		int jjCount = new Integer(pDictionaryDao.selectByName("jj_count").getDicValue());
		// 查询普通用户每次可领取金猪数
		long preAmount = new Long(pDictionaryDao.selectByName("pre_jj_amount").getDicValue());
		if(map==null) {
			// 查询今日已领取次数
			long startTime = sdf1.parse(sdf2.format(new Date()) +" 00:00:00").getTime();
			Long countL = lPigChangeDao.selectCountJJ(ConstantUtil.PIG_CHANGED_TYPE_12, userId, startTime);
			long count = countL == null?0:countL.longValue();
			if(count >= jjCount){
				data.put("res", 2); // 领取次数已用完
				return data;
			}
			MUserInfo mUserInfo = mUserInfoDao.selectOne(userId);
			if(mUserInfo.getPigCoin()>=10000) {
				data.put("res", 3);//剩余金猪大于10000
				return data;
			}
			if(mUserInfo.getCoin()>=1000) {
				data.put("res", 5);//你有还有足够的金币可以兑换为金猪
				return data;
			}
			mUserInfo.setPigCoin(mUserInfo.getPigCoin() + preAmount);
			// 添加金猪记录
			LPigChange lPigChange = new LPigChange(userId, preAmount, ConstantUtil.FLOWTYPE_IN, ConstantUtil.PIG_CHANGED_TYPE_12, now, "救济金猪", mUserInfo.getPigCoin());
			lPigChangeDao.insert(lPigChange);
			// 修改金猪数
			mUserInfoDao.update(mUserInfo);
			data.put("res", 4);//领取成功
			data.put("pigNum", preAmount);//领取金额
		}else { // 如果是会员则多增加一次机会
			MUserInfo mUserInfo=mUserInfoDao.selectOne(userId);
			if(mUserInfo.getPigCoin()>=10000) {
				data.put("res", 3);//剩余金猪大于10000
				return data;
			}
			if(mUserInfo.getCoin()>=1000) {
				data.put("res", 5);//你有还有足够的金币可以兑换为金猪
				return data;
			}
			int num=lPigChangeDao.getReliefPig(userId);
			if(num>=(int)map.get("everydayReliefPigTimes")) {
				// 查询今日已领取次数
				long startTime = sdf1.parse(sdf2.format(new Date()) +" 00:00:00").getTime();
				Long countL = lPigChangeDao.selectCountJJ(ConstantUtil.PIG_CHANGED_TYPE_12, userId, startTime);
				long count = countL == null?0:countL.longValue();
				if(count >= jjCount){
					data.put("res", 2); // 领取次数已用完
					return data;
				}
				mUserInfo.setPigCoin(mUserInfo.getPigCoin() + preAmount);
				// 添加金猪记录
				LPigChange lPigChange = new LPigChange(userId, preAmount, ConstantUtil.FLOWTYPE_IN, ConstantUtil.PIG_CHANGED_TYPE_12, now, "救济金猪", mUserInfo.getPigCoin());
				lPigChangeDao.insert(lPigChange);
				// 修改金猪数
				mUserInfoDao.update(mUserInfo);
				data.put("res", 4);//领取成功
				data.put("pigNum", preAmount);//领取金额
			}else {
				Map<String,Object> jms=new HashMap<>();
				jms.put("userId", userId);
				jms.put("pig", (long)map.get("everydayReliefPig"));
				jms.put("num", (int)map.get("everydayReliefPigTimes"));
				jmsProducer.reliefPig(Type.RELIE_PIG, jms);
				data.put("res", 4);//领取成功
				data.put("pigNum", map.get("everydayReliefPig"));//领取金额
			}
		}
		return data;
	}

}

package com.mc.bzly.impl.thirdparty;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.bzly.common.constant.DictionaryUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.lottery.MLotteryOrderDao;
import com.mc.bzly.dao.pay.PPayLogDao;
import com.mc.bzly.dao.platform.PDictionaryDao;
import com.mc.bzly.dao.thirdparty.MChannelConfigDao;
import com.mc.bzly.dao.thirdparty.MChannelConfigUserDao;
import com.mc.bzly.dao.thirdparty.MChannelInfoDao;
import com.mc.bzly.dao.thirdparty.TPCallbackDao;
import com.mc.bzly.dao.user.LUserTptaskDao;
import com.mc.bzly.dao.user.LUserVipDao;
import com.mc.bzly.dao.user.MUserInfoDao;
import com.mc.bzly.model.platform.PDictionary;
import com.mc.bzly.model.thirdparty.MChannelConfig;
import com.mc.bzly.model.thirdparty.MChannelConfigUser;
import com.mc.bzly.model.thirdparty.MChannelInfo;
import com.mc.bzly.model.user.MUserInfo;
import com.mc.bzly.service.thirdparty.MChannelConfigService;
import com.mc.bzly.util.StringUtil;

@Service(interfaceClass = MChannelConfigService.class,version = WebConfig.dubboServiceVersion)
public class MChannelConfigServiceImpl implements MChannelConfigService{
	
    @Autowired
	private MChannelConfigDao mChannelConfigDao;
    @Autowired
    private MUserInfoDao mUserInfoDao;
    @Autowired
    private LUserVipDao lUserVipDao;
    @Autowired 
	private PDictionaryDao pDictionaryDao;
    @Autowired
    private PPayLogDao pPayLogDao;
    @Autowired
    private TPCallbackDao tPCallbackDao;
    @Autowired
    private LUserTptaskDao lUserTptaskDao;
    @Autowired
    private MLotteryOrderDao mLotteryOrderDao;
    @Autowired
    private MChannelInfoDao mChannelInfoDao;
    @Autowired
    private MChannelConfigUserDao mChannelConfigUserDao;
    
	@Override
	public int insert(MChannelConfig mChannelConfig) {
		long time=new Date().getTime();
		mChannelConfig.setCreateTime(time);
		int res=mChannelConfigDao.insert(mChannelConfig);
		if(res>0) {
			MChannelInfo mChannelInfo=mChannelInfoDao.selectByChannelCode(mChannelConfig.getChannelCode());
			if(mChannelInfo.getWebType().intValue()==3) {
				MChannelConfigUser mChannelConfigUser=new MChannelConfigUser();
				mChannelConfigUser.setConfigId(mChannelConfig.getId());
				mChannelConfigUser.setUserType(1);
				mChannelConfigUser.setSign7(0l);
				mChannelConfigUser.setSign15(0l);
				mChannelConfigUser.setVip18(0);
				mChannelConfigUser.setVip48(0);
				mChannelConfigUser.setVip228(0);
				mChannelConfigUser.setVip1188(0);
				mChannelConfigUser.setVip1688(0);
				mChannelConfigUser.setVip1888(0);
				mChannelConfigUser.setVip3188(0);
				mChannelConfigUser.setLevel4(0l);
				mChannelConfigUser.setLevel6(0l);
				mChannelConfigUser.setLevel8(0l);
				mChannelConfigUser.setLevel12(0l);
				mChannelConfigUser.setReferrerAddition(0);
				mChannelConfigUser.setRecommendCoin(0l);
				mChannelConfigUser.setCreateTime(time);
				mChannelConfigUser.setStatus(1);
				mChannelConfigUser.setDarenCoin(0);
				mChannelConfigUserDao.insert(mChannelConfigUser);
				mChannelConfigUser.setUserType(2);
				mChannelConfigUserDao.insert(mChannelConfigUser);
			}
		}
		return 1;
	}

	@Override
	public MChannelConfig selectOne(Integer id) {
		return mChannelConfigDao.selectOne(id);
	}

	@Override
	public PageInfo<MChannelConfig> selectList(MChannelConfig mChannelConfig) {
		PageHelper.startPage(mChannelConfig.getPageNum(), mChannelConfig.getPageSize());
		List<MChannelConfig> mChannelConfigs =mChannelConfigDao.selectList(mChannelConfig);
		return new PageInfo<>(mChannelConfigs);
	}

	@Override
	public int update(MChannelConfig mChannelConfig) {
		return mChannelConfigDao.update(mChannelConfig);
	}

	@Override
	public int delete(Integer id) {
		return mChannelConfigDao.delete(id);
	}

	@Override
	public int checkGameRight(String userId, Integer id) {
		int res = 2;
		MUserInfo mUserInfo = mUserInfoDao.selectOne(userId);
		if(mUserInfo.getRoleType().intValue() == 2 || mUserInfo.getRoleType().intValue() == 3 ){
			return res;
		}
		// 判断是不是VIP
		List<Map<String, Object>> vips = lUserVipDao.selectByUser(userId);
		if(vips != null && vips.size() >0){
			return 2;
		}
		String channelCode = mUserInfo.getChannelCode() == null?mUserInfo.getParentChannelCode():mUserInfo.getChannelCode();
		if(StringUtils.isBlank(channelCode)){
			channelCode = "baozhu";
		}
		MChannelConfig mChannelConfig = mChannelConfigDao.selectByChannelCode(channelCode);
		String openGames[] = mChannelConfig.getOpenGame().split(",");
		for (String openGameId : openGames) {
			if(openGameId.equals(id+"")){
				res = 1;
				break;
			}
		}
		return res;
	}

	@Override
	public int check28Right(String userId, String name28) {
		MUserInfo mUserInfo = mUserInfoDao.selectOne(userId);
		if(mUserInfo.getRoleType().intValue() == 2 || mUserInfo.getRoleType().intValue() == 3 ){
			return 2;
		}
		// 判断是不是VIP
		List<Map<String, Object>> vips = lUserVipDao.selectByUser(userId);
		if(vips != null && vips.size() >0){
			return 2;
		}
		String channelCode = mUserInfo.getChannelCode() == null?mUserInfo.getParentChannelCode():mUserInfo.getChannelCode();
		if(StringUtils.isBlank(channelCode)){
			channelCode = "baozhu";
		}
		MChannelConfig mChannelConfig = mChannelConfigDao.selectByChannelCode(channelCode);
		if("game28".equals(name28)){
			return mChannelConfig.getGame28();
		}else if("pcdd28".equals(name28)){
			return mChannelConfig.getPcdd28();
		}else if("jnd28".equals(name28)){
			return mChannelConfig.getJnd28();
		}
		return 1;
	}

	@Override
	public Map<String, Object> check28RightNews(String userId) {
		Map<String, Object> data=new HashMap<String, Object>();
		MUserInfo mUserInfo = mUserInfoDao.selectOne(userId);
		long day=Integer.parseInt(pDictionaryDao.selectByName(DictionaryUtil.SMASH_GOLD_EGG_POWER).getDicValue())*24*60*60*1000;
		long time=new Date().getTime()-mUserInfo.getCreateTime();
		data.put("zjd", 1);//1关闭2开启
		if(time>=day) {
			/*int count=mLotteryOrderDao.selectUserCount(userId);
			if(count>0) {
				data.put("zjd", 2);	
			}*/
			data.put("zjd", 2);	
		}
		String channelCode = mUserInfo.getChannelCode();
    	if(StringUtil.isNullOrEmpty(channelCode)) {
    		channelCode=mUserInfo.getParentChannelCode();
    	}
    	if(StringUtil.isNullOrEmpty(channelCode)){
    		channelCode = "baozhu";
    	}
    	MChannelConfig mChannelConfig = mChannelConfigDao.selectByChannelCode(channelCode);
    	data.put("game28", mChannelConfig.getGame28());//1关闭2开启
    	data.put("pcdd28", mChannelConfig.getPcdd28());
    	data.put("jnd28", mChannelConfig.getJnd28());
    	if(mChannelConfig.getApplyTask().intValue()==1) {
    		PDictionary rechargeDictionary=pDictionaryDao.selectByName(DictionaryUtil.VIP_RECHARGE);
        	Double recharge=Double.parseDouble(rechargeDictionary.getDicValue());
        	
        	PDictionary gameDictionary=pDictionaryDao.selectByName(DictionaryUtil.USER_GAME_TASK);
        	Long game=Long.parseLong(gameDictionary.getDicValue());
        	
    		Double rechargeUser=pPayLogDao.selectSumUserRecharge(userId);
    		long gameCount=tPCallbackDao.selectCount(userId);
    		long applyCount=lUserTptaskDao.selectUserCount(userId);
    		long gameUser=gameCount+applyCount;
        	if(mChannelConfig.getGame28().intValue()==2) {
        		if(!(rechargeUser>=recharge) && !(gameUser>=game)) {
        			data.put("game28", 1);
        		}
        	}
        	if(mChannelConfig.getPcdd28().intValue()==2) {
        		if(!(rechargeUser>=recharge) && !(gameUser>=game)) {
        			data.put("pcdd28", 1);
        		}
        	}
        	if(mChannelConfig.getJnd28().intValue()==2) {
        		if(!(rechargeUser>=recharge) && !(gameUser>=game)) {
        			data.put("jnd28", 1);
        		}
        	}
    	}
    	
		return data;
	}

	@Override
	public Map<String, Object> zjdSwitch(String userId) {
		Map<String, Object> data=new HashMap<String, Object>();
		MUserInfo mUserInfo = mUserInfoDao.selectOne(userId);
		long day=Integer.parseInt(pDictionaryDao.selectByName(DictionaryUtil.SMASH_GOLD_EGG_POWER).getDicValue())*24*60*60*1000;
		long time=new Date().getTime()-mUserInfo.getCreateTime();
		data.put("zjd", 1);//1关闭2开启
		if(time>=day) {
			/*int count=mLotteryOrderDao.selectUserCount(userId);
			if(count>0) {
				data.put("zjd", 2);	
			}*/
			data.put("zjd", 2);
		}
		return data;
	}
	
	

}

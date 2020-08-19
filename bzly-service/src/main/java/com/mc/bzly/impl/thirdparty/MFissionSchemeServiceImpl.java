package com.mc.bzly.impl.thirdparty;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.bzly.common.utils.UploadUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.thirdparty.MFissionSchemeDao;
import com.mc.bzly.dao.user.LCoinChangeDao;
import com.mc.bzly.dao.user.MUserInfoDao;
import com.mc.bzly.dao.thirdparty.MChannelConfigDao;
import com.mc.bzly.model.thirdparty.MFissionScheme;
import com.mc.bzly.model.user.MUserInfo;
import com.mc.bzly.service.thirdparty.MFissionSchemeService;
import com.mc.bzly.util.StringUtil;

@Service(interfaceClass = MFissionSchemeService.class,version = WebConfig.dubboServiceVersion)
public class MFissionSchemeServiceImpl implements MFissionSchemeService{
	
    @Autowired
	private MFissionSchemeDao mFissionSchemeDao;
    @Autowired 
	private MUserInfoDao mUserInfoDao;
    @Autowired
    private LCoinChangeDao lCoinChangeDao;
    @Autowired
    private MChannelConfigDao MChannelConfigDao;
	
	@Override
	public int insert(MFissionScheme mFissionScheme) {
		mFissionScheme.setCreaterTime(new Date().getTime());
		return mFissionSchemeDao.insert(mFissionScheme);
	}

	@Override
	public MFissionScheme selectOne(Integer id) {
		return mFissionSchemeDao.selectOne(id);
	}

	@Override
	public PageInfo<MFissionScheme> selectList(MFissionScheme mFissionScheme) {
		PageHelper.startPage(mFissionScheme.getPageNum(), mFissionScheme.getPageSize());
		List<MFissionScheme> mFissionSchemes =mFissionSchemeDao.selectList(mFissionScheme);
		return new PageInfo<>(mFissionSchemes);
	}

	@Override
	public int update(MFissionScheme mFissionScheme) {
		return mFissionSchemeDao.update(mFissionScheme);
	}

	@Override
	public int delete(Integer id) {
		int count=MChannelConfigDao.selectFissionId(id);
		if(count>0) {
			return 2;
		}
		MFissionScheme mFissionScheme=mFissionSchemeDao.selectOne(id);
		String[] schemeImg=mFissionScheme.getSchemeImg().split(",");
		String[] ordinaryRewardImg=mFissionScheme.getOrdinaryRewardImg().split(",");
		String[] teamRewardImg=mFissionScheme.getTeamRewardImg().split(",");
		mFissionSchemeDao.delete(id);
		for(int i=0;i<schemeImg.length;i++) {
			UploadUtil.deleteFromQN(schemeImg[i]);
		}
		for(int i=0;i<ordinaryRewardImg.length;i++) {
			UploadUtil.deleteFromQN(ordinaryRewardImg[i]);
		}
		for(int i=0;i<teamRewardImg.length;i++) {
			UploadUtil.deleteFromQN(teamRewardImg[i]);
		}
		return 1;
	}

	@Override
	public List<MFissionScheme> selectDownList() {
		return mFissionSchemeDao.selectDownList();
	}

	@Override
	public Map<String, Object> selectChannelCodeOne(String userId) {
		Map<String, Object> data=new HashMap<String, Object>();
		MUserInfo mUserInfo=mUserInfoDao.selectOne(userId);
		String channelCode = "";
		if(!StringUtil.isNullOrEmpty(mUserInfo.getChannelCode())) {
			channelCode=mUserInfo.getChannelCode();
		}
		if(!StringUtil.isNullOrEmpty(mUserInfo.getParentChannelCode()) && StringUtil.isNullOrEmpty(channelCode)) {
			channelCode=mUserInfo.getParentChannelCode();
		}
		if(StringUtil.isNullOrEmpty(channelCode)) {
			channelCode="baozhu";
		}
		MFissionScheme mFissionScheme=mFissionSchemeDao.selectSchemeByChannelCode(channelCode);
		data=mFissionSchemeDao.selectChannelCodeOne(channelCode);
		if(mUserInfo.getRoleType()==1) {
			if(new Date().getTime()<mUserInfo.getXqEndTime()) {
				data.put("price", mFissionScheme.getRenewPrice());	
				data.put("isRenewal", 1);
			}else {
				data.put("price", mFissionScheme.getTeamPrice());	
				data.put("isRenewal", 2);
			}
		}else {
			data.put("price", mFissionScheme.getRenewPrice());	
			data.put("isRenewal", 1);
		}
		data.put("accountId", mUserInfo.getAccountId());
		data.put("surplusTime", mUserInfo.getSurplusTime());
		data.put("qrCode", mUserInfo.getQrCode());
		data.put("apprentice", mUserInfo.getApprentice());
		data.put("teamBenefit", lCoinChangeDao.selectGroupContribution(mUserInfo.getUserId()));//团队收益
		data.put("photo", mUserInfo.getProfile());
		data.put("roleType", mUserInfo.getRoleType());
		data.put("highRole", mUserInfo.getHighRole());
		return data;
	}

}

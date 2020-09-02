package com.mc.bzly.impl.thirdparty;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.bzly.common.constant.DictionaryUtil;
import com.bzly.common.utils.UploadUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.platform.PDictionaryDao;
import com.mc.bzly.dao.platform.PVersionDao;
import com.mc.bzly.dao.thirdparty.MChannelConfigDao;
import com.mc.bzly.dao.thirdparty.MChannelInfoDao;
import com.mc.bzly.dao.user.MUserInfoDao;
import com.mc.bzly.model.platform.PDictionary;
import com.mc.bzly.model.platform.PVersion;
import com.mc.bzly.model.thirdparty.MChannelConfig;
import com.mc.bzly.model.thirdparty.MChannelInfo;
import com.mc.bzly.model.user.MUserInfo;
import com.mc.bzly.service.thirdparty.MChannelInfoService;
import com.bzly.common.utils.StringUtil;

@Service(interfaceClass = MChannelInfoService.class,version = WebConfig.dubboServiceVersion)
public class MChannelInfoServiceImpl implements MChannelInfoService {

	@Autowired 
	private MChannelInfoDao mChannelInfoDao;
	
	@Autowired 
	private PDictionaryDao pDictionaryDao;
	@Autowired
	private MUserInfoDao MUserInfoDao;
	@Autowired
	private MChannelConfigDao mChannelConfigDao;
	@Autowired 
	private PVersionDao pVersionDao;
	
	@Transactional
	@Override
	public int add(MChannelInfo mChannelInfo) throws Exception {
		MChannelInfo channel=mChannelInfoDao.selectDownloadUrl(mChannelInfo.getChannelCode());
		if(!StringUtil.isNullOrEmpty(channel)) {
			return 3;//渠道标识已存在
		}
		PDictionary dictionary=null;
		if(mChannelInfo.getWebType().intValue()==1) {
			dictionary=pDictionaryDao.selectByName(DictionaryUtil.CHANNEL_REGISTER_URL);
		}else if(mChannelInfo.getWebType().intValue()==2){
			dictionary=pDictionaryDao.selectByName(DictionaryUtil.CHANNEL_XCX_URL);
		}else {
			dictionary=pDictionaryDao.selectByName(DictionaryUtil.CHANNEL_WISH_APP_URL);
		}
		mChannelInfo.setContent(dictionary.getDicValue()+mChannelInfo.getChannelCode());
		mChannelInfo.setCreateTime(new Date().getTime());
		mChannelInfoDao.insert(mChannelInfo);
		return 1;
	}

	@Transactional
	@Override
	public int modify(MChannelInfo mChannelInfo) throws Exception {
		mChannelInfoDao.update(mChannelInfo);
		return 1;
	}

	@Transactional
	@Override
	public int remove(Integer id) throws Exception {
		String imgUrl = mChannelInfoDao.selectOne(id).getContent();
		mChannelInfoDao.delete(id);
		UploadUtil.deleteFromQN(imgUrl);
		return 1;
	}

	@Override
	public MChannelInfo queryOne(Integer id) {
		return mChannelInfoDao.selectOne(id);
	}

	@Override
	public PageInfo<MChannelInfo> queryList(MChannelInfo mChannelInfo) {
		PageHelper.startPage(mChannelInfo.getPageNum(), mChannelInfo.getPageSize());
		List<MChannelInfo> mChannelInfoList = mChannelInfoDao.selectList(mChannelInfo);
		return new PageInfo<>(mChannelInfoList);
	}

	@Override
	public String getDownloadUrl(int pType,String channelCode) {
		PVersion pVersion = pVersionDao.selectByChannelCode(channelCode);
		if(pVersion.getChannelUpdate().intValue()!=1) {
			MChannelInfo mChannelInfo=mChannelInfoDao.selectByChannelCode(channelCode);
			if(mChannelInfo.getWebType().intValue()==3) {
				pVersion=pVersionDao.selectByChannelCode("wishbz");
			}else {
				pVersion=pVersionDao.selectByChannelCode("baozhu");
			}
			
		}
		if(pType==1) {//安卓
			return pVersion.getUpdateUrl();
		}else {//ios
			return pVersion.getIosUpdateUrl();
		}
	}

	@Override
	public List<Map<String, Object>> selectDownList() {
		return mChannelInfoDao.selectDownList();
	}

	@Override
	public MChannelInfo selectCode(String outTradeNo) {
		return mChannelInfoDao.selectCode(outTradeNo);
	}

	@Override
	public List<Map<String, Object>> selectChannelDownList() {
		return mChannelInfoDao.selectChannelDownList();
	}

	@Override
	public Map<String, Object> openPay(String userId) {
		MUserInfo user=MUserInfoDao.selectOne(userId);
		String channelCode = "";
		if(!StringUtil.isNullOrEmpty(user.getChannelCode())) {
			channelCode=user.getChannelCode();
		}
		if(!StringUtil.isNullOrEmpty(user.getParentChannelCode()) && StringUtil.isNullOrEmpty(channelCode)) {
			channelCode=user.getParentChannelCode();
		}
		if(StringUtil.isNullOrEmpty(channelCode)) {
			channelCode="baozhu";
		}
		MChannelInfo mChannelInfo=null;
		if("baozhu".equals(channelCode)) {
			mChannelInfo=mChannelInfoDao.selectDownloadUrl(channelCode);
		}else {
			MChannelConfig mChannelConfig=mChannelConfigDao.selectByChannelCode(channelCode);
			if(mChannelConfig.getChargeMode().intValue()==1) {
				mChannelInfo=mChannelInfoDao.selectDownloadUrl("baozhu");
			}else {
				mChannelInfo=mChannelInfoDao.selectDownloadUrl(channelCode);
			}
		}
		Map<String, Object> data=new HashMap<>();
		data.put("openAli", mChannelInfo.getOpenAli());
		data.put("openWx", mChannelInfo.getOpenWx());
		return data;
	}
	
	@Override
	public String queryByQrCode(String qrCode) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("qrCode", qrCode);
		MUserInfo mUserInfo = MUserInfoDao.selectUserBaseInfo(paramMap);
		if(mUserInfo == null){
			return "1";
		}
		/*String channelCode = mUserInfo.getChannelCode();
		if(StringUtils.isEmpty(channelCode)){
			channelCode = mUserInfo.getParentChannelCode();
			if(StringUtils.isEmpty(channelCode)){
				channelCode = "baozhu";
			}
		}
		MChannelInfo MChannelInfo = mChannelInfoDao.selectDownloadUrl(channelCode);*/
		PVersion pVersion = pVersionDao.selectByChannelCode("baozhu");
		if(pVersion != null){
			return pVersion.getUpdateUrl();
		}else{
			return pDictionaryDao.selectByName("download_url").getDicValue();
		}
	}

	@Override
	public List<Map<String, Object>> selectVersionDownList() {
		return mChannelInfoDao.selectVersionDownList();
	}

}

package com.mc.bzly.impl.platform;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.bzly.common.constant.DictionaryUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.platform.MTaskJobDao;
import com.mc.bzly.dao.platform.PDictionaryDao;
import com.mc.bzly.dao.platform.PVersionDao;
import com.mc.bzly.dao.task.MTaskInfoDao;
import com.mc.bzly.dao.thirdparty.MChannelInfoDao;
import com.mc.bzly.dao.user.LUserFirstLogDao;
import com.mc.bzly.dao.user.LUserTaskDao;
import com.mc.bzly.dao.user.MUserInfoDao;
import com.mc.bzly.model.platform.PDictionary;
import com.mc.bzly.model.platform.PVersion;
import com.mc.bzly.model.thirdparty.MChannelInfo;
import com.mc.bzly.model.user.LUserFirstLog;
import com.mc.bzly.model.user.LUserTask;
import com.mc.bzly.model.user.MUserInfo;
import com.mc.bzly.service.platform.PDictionaryService;
import com.bzly.common.utils.StringUtil;

@Service(interfaceClass = PDictionaryService.class,version = WebConfig.dubboServiceVersion)
public class PDictionaryServiceImpl implements PDictionaryService {
	
	@Autowired 
	private PDictionaryDao pDictionaryDao;
	@Autowired 
	private LUserTaskDao lUserTaskDao;
	@Autowired 
	private PVersionDao pVersionDao;
	@Autowired
	private MTaskInfoDao mTaskInfoDao;
	@Autowired
	private LUserFirstLogDao lUserFirstLogDao;
	@Autowired
	private MUserInfoDao mUserInfoDao;
	@Autowired
	private MChannelInfoDao mChannelInfoDao;
	@Autowired
	private MTaskJobDao mTaskJobDao;
	
	
	@Transactional
	@Override
	public int add(PDictionary pDictionary) throws Exception {
		pDictionaryDao.insert(pDictionary);
		return 1;
	}

	@Transactional
	@Override
	public int modify(PDictionary pDictionary) throws Exception {
		int i=pDictionaryDao.update(pDictionary);
		if(i>0 && "wish_guess_end_time".equals(pDictionary.getDicName())) {
			mTaskJobDao.update("0 0 "+pDictionary.getDicValue()+" * * ?", new Date().getTime(), "GUESS_LOG");
		}
		return 1;
	}

	@Override
	public PDictionary queryOne(Integer id) {
		return pDictionaryDao.selectOne(id);
	}

	@Override
	public PDictionary queryByName(String dicName) {
		return pDictionaryDao.selectByName(dicName);
	}

	@Override
	public PageInfo<PDictionary> queryList(PDictionary pDictionary) {
		PageHelper.startPage(pDictionary.getPageNum(), pDictionary.getPageSize());
		List<PDictionary> pDictionaryList = pDictionaryDao.selectList(pDictionary);
		return new PageInfo<>(pDictionaryList);
	}

	@Override
	public List<PDictionary> queryListByDicName(String dicName) {
		PDictionary pDictionary = new PDictionary();
		pDictionary.setDicName(dicName);
		return pDictionaryDao.selectList(pDictionary);
	}

	@Override
	public Map<String, Object> homePageDispose(Map<String,String> resp) {
		Map<String,Object> data=new HashMap<>();
		String equipmentType = resp.get("equipmentType"); // 1-安卓 2-iOS
		if(StringUtil.isNullOrEmpty(equipmentType)) {
			equipmentType="1";
		}
		
		PVersion pVersion = pVersionDao.selectByChannelCode(resp.get("channelCode"));
		if(pVersion == null){
			pVersion=pVersionDao.selectByChannelCode("baozhu");
		}else{
			if(pVersion.getChannelUpdate().intValue()!=1) {
				MChannelInfo mChannelInfo=mChannelInfoDao.selectByChannelCode(resp.get("channelCode"));
				if(mChannelInfo.getWebType().intValue()==3) {
					pVersion=pVersionDao.selectByChannelCode("wishbz");
				}else {
					pVersion=pVersionDao.selectByChannelCode("baozhu");
				}
			}
		}
		if("1".equals(equipmentType)) {
			if(pVersion.getVersionNo().equals(resp.get("versionNo").toString())) {
				data.put("pVersion", pVersion);
			}else {
				pVersion.setNeedUpdate(1);
				data.put("pVersion", pVersion);
			}	
		}else {
			if(pVersion.getIosVersionNo().equals(resp.get("versionNo").toString())) {
				data.put("pVersion", pVersion);
			}else {
				pVersion.setNeedUpdate(1);
				data.put("pVersion", pVersion);
			}
		}
		PDictionary fuLiDictionary=pDictionaryDao.selectByName(DictionaryUtil.FU_LI_HUI);
		data.put("fuLiHui", fuLiDictionary.getDicValue());
		if(fuLiDictionary.getStatus()==2) {
			data.put("fuLiHui", 1);
		}
		data.put("recommendGame", pDictionaryDao.selectByName(DictionaryUtil.RECOMMEND_GAME).getDicValue());
		data.put("h5_location_new", pDictionaryDao.selectByName("h5_location_new").getDicValue());
		return data;
	}

	@Override
	public Map<String, Object> noviceBut(String userId,String versionNo,String channelCode) {
		Map<String,Object> data=new HashMap<>();
		data.put("noviceGuide",2);
		data.put("noviceTask", 2);
		MUserInfo user = mUserInfoDao.selectOne(userId);
    	String channel = user.getChannelCode();
    	if(StringUtil.isNullOrEmpty(channel)) {
    		channel=user.getParentChannelCode();
    	}
    	if(StringUtils.isBlank(channel)){
    		channel = "baozhu";
    	}
		PVersion pVersion = pVersionDao.selectVersionChannnel(channel);
		if(StringUtil.isNullOrEmpty(pVersion)) {
			return data;
		}else {
			if(pVersion.getOpenNoviceTask()==2) {
				return data;
			}
		}
		LUserTask userTask=lUserTaskDao.selectOne(userId,11);
		if(StringUtil.isNullOrEmpty(userTask)) {
			return data;
		}
		LUserFirstLog lUserFirstLog=lUserFirstLogDao.selectOne(userId);
		if(StringUtil.isNullOrEmpty(lUserFirstLog)) {
			data.put("noviceGuide", pDictionaryDao.selectByName(DictionaryUtil.NOVICE_GUIDE).getDicValue());
		}
		int taskComplete=lUserTaskDao.selectCompleteCount(userId,6);//用户完成新手任务数量
		int taskCount=mTaskInfoDao.selectCount(6);//新手任务数量
		int isTask=Integer.parseInt(pDictionaryDao.selectByName(DictionaryUtil.NOVICE_TASK).getDicValue());
		if(isTask==1 && taskComplete<taskCount) {
			data.put("noviceTask", 1);
		}
		return data;
	}

	@Override
	public Map<String, Object> contactInformation() {
		Map<String, Object> data=new HashMap<String, Object>();
		data.put("qqGroup", pDictionaryDao.selectByName(DictionaryUtil.BZ_QQ_GROUP).getDicValue());
		data.put("serviceQq", pDictionaryDao.selectByName(DictionaryUtil.BZ_SERVICE_GROUP).getDicValue());
		return data;
	}

	@Override
	public Map<String, Object> homeIosPageDispose(Map<String, String> resp) {
		Map<String,Object> data=new HashMap<>();
		PVersion pVersion = pVersionDao.selectByIosVersionAndChannelCode(resp.get("versionNo"),resp.get("channelCode"));
		if(pVersion != null){
			data.put("pVersion", pVersion);
		} else {
			pVersion = pVersionDao.selectByChannelCode(resp.get("channelCode"));
			pVersion.setNeedUpdate(1);
			data.put("pVersion", pVersion);
		}
		PDictionary fuLiDictionary=pDictionaryDao.selectByName(DictionaryUtil.FU_LI_HUI);
		data.put("fuLiHui", fuLiDictionary.getDicValue());
		if(fuLiDictionary.getStatus()==2) {
			data.put("fuLiHui", 1);
		}
		data.put("recommendGame", pDictionaryDao.selectByName(DictionaryUtil.RECOMMEND_GAME).getDicValue());

		data.put("h5_location_new", pDictionaryDao.selectByName("h5_location_new").getDicValue());
		
		data.put("noviceTask", pDictionaryDao.selectByName(DictionaryUtil.NOVICE_TASK).getDicValue());

		data.put("download_url", pDictionaryDao.selectByName(DictionaryUtil.DOWNLOAD_URL).getDicValue());
		
		return data;
	}

	
}

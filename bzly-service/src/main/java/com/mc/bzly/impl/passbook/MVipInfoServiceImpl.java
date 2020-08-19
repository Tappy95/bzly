package com.mc.bzly.impl.passbook;

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
import com.mc.bzly.dao.passbook.MVipInfoDao;
import com.mc.bzly.dao.thirdparty.TPGameDao;
import com.mc.bzly.dao.user.LUserGameTaskDao;
import com.mc.bzly.dao.user.MUserInfoDao;
import com.mc.bzly.model.passbook.MVipInfo;
import com.mc.bzly.model.thirdparty.TPGame;
import com.mc.bzly.model.user.LUserGameTask;
import com.mc.bzly.model.user.MUserInfo;
import com.mc.bzly.service.passbook.MVipInfoService;
import com.mc.bzly.util.StringUtil;

@Service(interfaceClass=MVipInfoService.class,version=WebConfig.dubboServiceVersion)
public class MVipInfoServiceImpl implements MVipInfoService {
	
	@Autowired 
	private MVipInfoDao mVipInfoDao;
	@Autowired
    private LUserGameTaskDao lUserGameTaskDao;
	@Autowired
	private TPGameDao tPGameDao;
	@Autowired
	private MUserInfoDao mUserInfoDao;
	
	@Override
	public void add(MVipInfo mVipInfo) throws Exception {
		mVipInfo.setCreateTime(new Date().getTime());
		mVipInfoDao.insert(mVipInfo);
	}

	@Override
	public void modify(MVipInfo mVipInfo) throws Exception {
		mVipInfoDao.update(mVipInfo);
	}

	@Override
	public void remove(Integer id) throws Exception {
		String imgUrl = mVipInfoDao.selectOne(id).getLogo();
		mVipInfoDao.delete(id);
		UploadUtil.deleteFromQN(imgUrl);
	}

	@Override
	public MVipInfo queryOne(Integer id) {
		return mVipInfoDao.selectOne(id);
	}

	@Override
	public PageInfo<MVipInfo> queryList(MVipInfo mVipInfo) {
		PageHelper.startPage(mVipInfo.getPageNum(), mVipInfo.getPageSize());
		List<MVipInfo> mVipInfos =mVipInfoDao.selectList(mVipInfo);
		return new PageInfo<>(mVipInfos);
	}

	@Override
	public List<Map<String,Object>> queryListNoPage(String userId) {
		MUserInfo user=mUserInfoDao.selectOne(userId);
		String channelCode=StringUtil.isNullOrEmpty(user.getChannelCode())?user.getParentChannelCode():user.getChannelCode();
		if(StringUtil.isNullOrEmpty(channelCode)) {
			channelCode="baozhu";
		}
		int vipType=1;
		if("zhongqing".equals(channelCode)) {
			vipType=2;
		}
		return mVipInfoDao.selectUserList(vipType,userId);
	}

	@Override
	public Map<String,Object> selectOneUser(Integer id, String userId) {
		Map<String,Object> data=new HashMap<String, Object>();
		MVipInfo mVipInfo=mVipInfoDao.selectOneUser(id, userId);
		if(mVipInfo.getIsBuy()==0 || mVipInfo.getIsTask()==2) {
			data.put("isGameTask", 4);//未购买vip或者不需要匹配任务
			data.put("game", null);//未匹配任务
			data.put("mVipInfo", mVipInfo);
			return data;
		}
		LUserGameTask lUserGameTask=lUserGameTaskDao.selectOne(userId, id, 1);
		if(StringUtil.isNullOrEmpty(lUserGameTask)) {
			data.put("isGameTask", 3);//未匹配任务
			data.put("game", null);//未匹配任务
		}else {
			data.put("isGameTask", lUserGameTask.getState());
			TPGame tpGame=tPGameDao.selectOne(lUserGameTask.getGameId());
			data.put("game", tpGame);
		}
		data.put("mVipInfo", mVipInfo);
		return data;
	}

	@Override
	public List<Map<String, Object>> dowZqVip() {
		return mVipInfoDao.selectZqVip();
	}

}

package com.mc.bzly.impl.thirdparty;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.thirdparty.MChannelConfigUserDao;
import com.mc.bzly.model.thirdparty.MChannelConfigUser;
import com.mc.bzly.service.thirdparty.MChannelConfigUserService;

@Service(interfaceClass = MChannelConfigUserService.class,version = WebConfig.dubboServiceVersion)
public class MChannelConfigUserServiceImpl implements MChannelConfigUserService {

	@Autowired
	private MChannelConfigUserDao mChannelConfigUserDao;
	
	@Transactional
	@Override
	public int add(MChannelConfigUser mChannelConfigUser) throws Exception {
		if(mChannelConfigUser != null){
			mChannelConfigUser.setCreateTime(new Date().getTime());
			mChannelConfigUserDao.insert(mChannelConfigUser);
			return 1;
		}
		return 0;
	}

	@Transactional
	@Override
	public int modify(MChannelConfigUser mChannelConfigUser) throws Exception {
		if(mChannelConfigUser != null){
			mChannelConfigUserDao.update(mChannelConfigUser);
			return 1;
		}
		return 0;
	}

	@Transactional
	@Override
	public int remove(Integer id) throws Exception {
		if(id != null){
			mChannelConfigUserDao.delete(id);
			return 1;
		}
		return 0;
	}

	@Override
	public MChannelConfigUser queryOne(Integer id) {
		return mChannelConfigUserDao.selectOne(id);
	}

	@Override
	public PageInfo<MChannelConfigUser> queryList(MChannelConfigUser mChannelConfigUser) {
		PageHelper.startPage(mChannelConfigUser.getPageNum(), mChannelConfigUser.getPageSize());
		List<MChannelConfigUser> mChannelConfigUserList = mChannelConfigUserDao.selectList(mChannelConfigUser);
		return new PageInfo<>(mChannelConfigUserList);
	}
	
	@Override
	public List<MChannelConfigUser> queryListNoPage(MChannelConfigUser mChannelConfigUser) {
		return mChannelConfigUserDao.selectList(mChannelConfigUser);
	}

}

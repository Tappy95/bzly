package com.mc.bzly.impl.user;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.platform.PAreaDao;
import com.mc.bzly.dao.user.MUserAddressDao;
import com.mc.bzly.model.platform.PArea;
import com.mc.bzly.model.user.MUserAddress;
import com.mc.bzly.service.user.MUserAddressService;
import com.mc.bzly.util.StringUtil;

@Service(interfaceClass = MUserAddressService.class,version = WebConfig.dubboServiceVersion)
public class MUserAddressServiceImpl implements MUserAddressService {

	@Autowired
	private MUserAddressDao mUserAddressDao;
	@Autowired
	private PAreaDao pAreaDao;
	
	@Transactional
	@Override
	public int add(MUserAddress mUserAddress) {
		mUserAddress.setCreateTime(new Date().getTime());
		mUserAddress.setUpdateTime(new Date().getTime());
		mUserAddress.setIsDisabled(1);
		mUserAddress.setFullName(pAreaDao.selectFullName(mUserAddress.getAreaId()));
		mUserAddressDao.updateIsDefault(mUserAddress.getUserId());
		mUserAddressDao.insert(mUserAddress);
		return 1;
	}

	@Transactional
	@Override
	public int modify(MUserAddress mUserAddress) throws Exception {
		mUserAddress.setUpdateTime(new Date().getTime());
		mUserAddress.setFullName(pAreaDao.selectFullName(mUserAddress.getAreaId()));
		if(!StringUtil.isNullOrEmpty(mUserAddress.getIsDefault())) {
			if(mUserAddress.getIsDefault()==1) {
				mUserAddressDao.updateIsDefault(mUserAddress.getUserId());	
			}
		}
		mUserAddressDao.update(mUserAddress);
		return 1;
	}
	
	@Transactional
	@Override
	public int remove(Integer addressId) throws Exception {
		mUserAddressDao.delete(addressId);
		return 1;
	}

	@Override
	public Map<String,Object> queryOne(Integer addressId) {
		return mUserAddressDao.selectOne(addressId);
	}

	@Override
	public PageInfo<MUserAddress> queryList(MUserAddress mUserAddress) {
		PageHelper.startPage(mUserAddress.getPageNum(), mUserAddress.getPageSize());
		List<MUserAddress> mUserAddressList = mUserAddressDao.selectList(mUserAddress);
		return new PageInfo<>(mUserAddressList);
	}

	@Override
	public List<Map<String, Object>> selectUserList(String userId) {
		return mUserAddressDao.selectUserList(userId);
	}

}

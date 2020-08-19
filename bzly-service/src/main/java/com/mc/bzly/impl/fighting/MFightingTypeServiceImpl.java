package com.mc.bzly.impl.fighting;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.fighting.MFightingTypeDao;
import com.mc.bzly.model.fighting.MFightingType;
import com.mc.bzly.service.fighting.MFightingTypeService;

@Service(interfaceClass=MFightingTypeService.class,version=WebConfig.dubboServiceVersion)
public class MFightingTypeServiceImpl implements MFightingTypeService {
	
    @Autowired
	private MFightingTypeDao mFightingTypeDao;
	
	@Override
	public Integer add(MFightingType mFightingType) {
		mFightingType.setCreateTime(new Date().getTime());
		mFightingType.setUpdateTime(new Date().getTime());
		return mFightingTypeDao.save(mFightingType);
	}

	@Override
	public PageInfo<MFightingType> pageList(MFightingType mFightingType) {
		PageHelper.startPage(mFightingType.getPageNum(), mFightingType.getPageSize());
		List<MFightingType> mFightingTypeList =mFightingTypeDao.seleteList(mFightingType);
		return new PageInfo<>(mFightingTypeList);
	}

	@Override
	public MFightingType selectOne(Integer typeId) {
		return mFightingTypeDao.selectOne(typeId);
	}

	@Override
	public Integer update(MFightingType mFightingType) {
		return mFightingTypeDao.update(mFightingType);
	}

	@Override
	public Integer delete(Integer typeId) {
		return mFightingTypeDao.delete(typeId);
	}

}

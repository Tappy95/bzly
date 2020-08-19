package com.mc.bzly.impl.thirdparty;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.thirdparty.TPGameTypeDao;
import com.mc.bzly.dao.thirdparty.TpGameRelationTypeDao;
import com.mc.bzly.model.thirdparty.TPGameType;
import com.mc.bzly.service.thirdparty.TPGameTypeService;

@Service(interfaceClass = TPGameTypeService.class,version = WebConfig.dubboServiceVersion)
public class TPGameTypeServiceImpl implements TPGameTypeService {

	@Autowired 
	private TPGameTypeDao tpGameTypeDao;
	@Autowired
	private TpGameRelationTypeDao tpGameRelationTypeDao;
	
	@Transactional
	@Override
	public int add(TPGameType tpGameType) throws Exception {
		tpGameType.setCreateTime(new Date().getTime());
		tpGameTypeDao.insert(tpGameType);
		return 1;
	}

	@Transactional
	@Override
	public int modify(TPGameType tpGameType) throws Exception {
		tpGameTypeDao.update(tpGameType);
		return 1;
	}

	@Transactional
	@Override
	public int remove(Integer id) throws Exception {
		int i=tpGameTypeDao.delete(id);
		if(i>0) {
			tpGameRelationTypeDao.deleteTypeId(id);	
		}
		return 1;
	}

	@Override
	public TPGameType queryOne(Integer id) {
		return tpGameTypeDao.selectOne(id);
	}

	@Override
	public PageInfo<TPGameType> queryList(TPGameType tpGameType) {
		PageHelper.startPage(tpGameType.getPageNum(), tpGameType.getPageSize());
		List<TPGameType> tpGameTypeList = tpGameTypeDao.selectList(tpGameType);
		return new PageInfo<>(tpGameTypeList);
	}

	@Override
	public List<TPGameType> queryOpt() {
		return tpGameTypeDao.selectList(null);
	}

	@Override
	public List<TPGameType> selectOption() {
		return tpGameTypeDao.selectOption();
	}

}

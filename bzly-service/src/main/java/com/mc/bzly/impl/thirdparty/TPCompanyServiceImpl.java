package com.mc.bzly.impl.thirdparty;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.bzly.common.utils.UploadUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.thirdparty.TPCompanyDao;
import com.mc.bzly.model.thirdparty.TPCompany;
import com.mc.bzly.service.thirdparty.TPCompanyService;

@Service(interfaceClass = TPCompanyService.class,version = WebConfig.dubboServiceVersion)
public class TPCompanyServiceImpl implements TPCompanyService {

	@Autowired 
	private TPCompanyDao tpCompanyDao;
	
	@Transactional
	@Override
	public int add(TPCompany tpCompany) throws Exception {
		tpCompany.setCreateTime(new Date().getTime());
		tpCompanyDao.insert(tpCompany);
		return 1;
	}

	@Transactional
	@Override
	public int modify(TPCompany tpCompany) throws Exception {
		tpCompanyDao.update(tpCompany);
		return 1;
	}

	@Transactional
	@Override
	public int remove(Integer id) throws Exception {
		String imgUrl = tpCompanyDao.selectOne(id).getImageUrl();
		tpCompanyDao.delete(id);
		UploadUtil.deleteFromQN(imgUrl);
		return 1;
	}

	@Override
	public TPCompany queryOne(Integer id) {
		return tpCompanyDao.selectOne(id);
	}

	@Override
	public PageInfo<TPCompany> queryList(TPCompany tpCompany) {
		PageHelper.startPage(tpCompany.getPageNum(), tpCompany.getPageSize());
		List<TPCompany> tpCompanyList = tpCompanyDao.selectList(tpCompany);
		return new PageInfo<>(tpCompanyList);
	}

	@Override
	public List<TPCompany> queryOpt() {
		TPCompany tpCompany = new TPCompany();
		tpCompany.setH5Type(1);
		tpCompany.setStatus(1);
		return tpCompanyDao.selectList(null);
	}

}

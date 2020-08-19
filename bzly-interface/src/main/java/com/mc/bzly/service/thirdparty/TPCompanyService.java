package com.mc.bzly.service.thirdparty;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.mc.bzly.model.thirdparty.TPCompany;

public interface TPCompanyService {

	int add(TPCompany tpCompany) throws Exception;
	
	int modify(TPCompany tpCompany) throws Exception;
	
	int remove(Integer id) throws Exception;
	
	TPCompany queryOne(Integer id);
	
	PageInfo<TPCompany> queryList(TPCompany tpCompany);

	List<TPCompany> queryOpt();
}

package com.mc.bzly.dao.thirdparty;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mc.bzly.model.thirdparty.TPCompany;

@Mapper
public interface TPCompanyDao {

	void insert(TPCompany tpCompany);
	
	void update(TPCompany tpCompany);
	
	void delete(Integer id);
	
	TPCompany selectOne(Integer id);
	
	List<TPCompany> selectList(TPCompany tpCompany);
}

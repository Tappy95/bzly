package com.mc.bzly.dao.thirdparty;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mc.bzly.model.thirdparty.TPParams;

@Mapper
public interface TPParamsDao {

	void insert(TPParams tpParams);
	
	void update(TPParams tpParams);
	
	void delete(Integer id);
	
	TPParams selectOne(Integer id);
	
	List<TPParams> selectList(TPParams tpParams);

	List<TPParams> selectListByInterface(Integer interfaceId);
}

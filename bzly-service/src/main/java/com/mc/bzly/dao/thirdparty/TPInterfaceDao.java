package com.mc.bzly.dao.thirdparty;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.mc.bzly.model.thirdparty.TPInterface;

@Mapper
public interface TPInterfaceDao {

	void insert(TPInterface tpInterface);
	
	void update(TPInterface tpInterface);
	
	void delete(Integer id);
	
	TPInterface selectOne(Integer id);
	
	List<TPInterface> selectList(TPInterface tpInterface);
	
	List<Map<String,Object>> selectDown();
}

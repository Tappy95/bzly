package com.mc.bzly.dao.thirdparty;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mc.bzly.model.thirdparty.TPResp;

@Mapper
public interface TPRespDao {

	void insert(TPResp tpResp);
	
	void update(TPResp tpResp);
	
	void delete(Integer id);
	
	TPResp selectOne(Integer id);
	
	List<TPResp> selectList(TPResp tpResp);
	
	List<TPResp> selectListByInterface(Integer interfaceId);
}

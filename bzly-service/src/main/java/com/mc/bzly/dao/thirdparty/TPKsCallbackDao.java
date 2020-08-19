package com.mc.bzly.dao.thirdparty;

import org.apache.ibatis.annotations.Mapper;

import com.mc.bzly.model.thirdparty.TPKsCallback;

@Mapper
public interface TPKsCallbackDao {

	void insert(TPKsCallback tpKsCallback);
	
	TPKsCallback selectByImei(String imeiMD5);
	
	void update(TPKsCallback tpKsCallback);
}

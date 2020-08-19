package com.mc.bzly.dao.thirdparty;

import org.apache.ibatis.annotations.Mapper;

import com.mc.bzly.model.thirdparty.TPQttCallback;

@Mapper
public interface TPQttCallbackDao {

	void insert(TPQttCallback tpQttCallback);
	
	TPQttCallback selectByImei(String imeiMD5);
	
	void update(TPQttCallback tpQttCallback);
}

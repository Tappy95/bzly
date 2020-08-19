package com.mc.bzly.dao.thirdparty;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mc.bzly.model.thirdparty.TPTaskStatusCallback;

@Mapper
public interface TPTaskStatusCallbackDao {

	void insert(TPTaskStatusCallback tpTaskStatusCallback);

	TPTaskStatusCallback selectByNum(@Param("flewNum")String flewNum,@Param("status")Integer status);
}

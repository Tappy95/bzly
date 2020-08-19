package com.mc.bzly.dao.user;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mc.bzly.model.user.LUserCash;
@Mapper
public interface LUserCashDao {
	
	int insert(LUserCash lUserCash);
	
	int update(LUserCash lUserCash);
	
	LUserCash selectOne(@Param("userId")String userId,@Param("state")Integer state);
	
	LUserCash selectStateOne(@Param("userId")String userId);
	
	LUserCash selectId(@Param("id")Integer id,@Param("userId")String userId);
	
	LUserCash selectNumberOne(String outTradeNo);
	
	LUserCash updateState(@Param("outTradeNo")String outTradeNo,@Param("state")Integer state);
	
	int updateIdState(Integer id);
	
	List<LUserCash> selectList(LUserCash lUserCash);
	
	LUserCash selectTimeId(Integer id);

}

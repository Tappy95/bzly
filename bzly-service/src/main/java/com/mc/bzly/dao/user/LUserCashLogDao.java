package com.mc.bzly.dao.user;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mc.bzly.model.user.LUserCashLog;

@Mapper
public interface LUserCashLogDao {
	
	List<LUserCashLog> selectList(LUserCashLog lUserCashLog);
	
	int selectCount(LUserCashLog lUserCashLog);

}

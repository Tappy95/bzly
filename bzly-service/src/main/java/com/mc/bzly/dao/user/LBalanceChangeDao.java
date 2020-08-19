package com.mc.bzly.dao.user;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mc.bzly.model.user.LBalanceChange;

@Mapper
public interface LBalanceChangeDao {
	
	void insert(LBalanceChange lBalanceChange);
	
	void update(LBalanceChange lBalanceChange);
	
	LBalanceChange selectOne(Integer logId);
	
	List<LBalanceChange> selectList(LBalanceChange lBalanceChange);

	void batchInsert(List<LBalanceChange> lBalanceChangeList);

	List<LBalanceChange> selectRecord(@Param("userId")String userId,@Param("changedType")Integer changedType);

}

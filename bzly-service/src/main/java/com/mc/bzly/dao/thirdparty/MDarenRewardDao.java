package com.mc.bzly.dao.thirdparty;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mc.bzly.model.thirdparty.MDarenReward;

@Mapper
public interface MDarenRewardDao {
	
	int save(MDarenReward mDarenReward);
	
	List<MDarenReward> selectList(MDarenReward mDarenReward);
	
	MDarenReward selectOne(int id);
	
	int update(MDarenReward mDarenReward);
	
	int delete(int id);

	MDarenReward selectByOrders(@Param("orders")long taskCount);
}

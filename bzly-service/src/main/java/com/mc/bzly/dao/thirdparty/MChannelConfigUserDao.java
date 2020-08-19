package com.mc.bzly.dao.thirdparty;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mc.bzly.model.thirdparty.MChannelConfigUser;

@Mapper
public interface MChannelConfigUserDao {

	void insert(MChannelConfigUser mChannelConfigUser);

	void batchInsert(List<MChannelConfigUser> mChannelConfigUsers);
	
	void update(MChannelConfigUser mChannelConfigUser);
	
	void delete(Integer id);
	
	MChannelConfigUser selectOne(Integer id);
	
	List<MChannelConfigUser> selectList(MChannelConfigUser mChannelConfigUser);
	/**
	 * 更具用户类型以及渠道标识查询奖励
	 * @param userType
	 * @param channelCode
	 * @return
	 */
	MChannelConfigUser selectUserChannelCode(@Param("userType")Integer userType,@Param("channelCode")String channelCode);
	
	MChannelConfigUser selectByUserTypeAndConfigId(@Param("configId") Integer configId,@Param("userType") Integer userType);
}
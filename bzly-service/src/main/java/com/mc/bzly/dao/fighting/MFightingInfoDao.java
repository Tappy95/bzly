package com.mc.bzly.dao.fighting;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mc.bzly.model.fighting.MFightingInfo;

@Mapper
public interface MFightingInfoDao {
	
	Integer save(MFightingInfo mFightingInfo);
	
	MFightingInfo selectCode(@Param("entryCode")Integer entryCode);
	
	Integer update(MFightingInfo mFightingInfo);
	
	Integer delete(@Param("fightId")Integer fightId);
	
	List<MFightingInfo> selectList(MFightingInfo mFightingInfo);
	
	MFightingInfo selectOne(@Param("fightId")Integer fightId);
	
	int selectUserCount(@Param("userId")String userId);

}

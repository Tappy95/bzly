package com.mc.bzly.dao.passbook;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mc.bzly.model.passbook.MVipInfo;

@Mapper
public interface MVipInfoDao {

	void insert(MVipInfo mVipInfo);
	
	void update(MVipInfo mVipInfo);
	
	void delete(@Param("id")Integer id);
	
	MVipInfo selectOne(@Param("id")Integer id);
	
	List<MVipInfo> selectList(MVipInfo mVipInfo);
	
	MVipInfo selectOneUser(@Param("id")Integer id,@Param("userId")String userId);

	List<MVipInfo> selectByUser(@Param("userId")String userId);
	
	MVipInfo selectUserVip(@Param("userId")String userId,@Param("vipId")int vipId);
	
	List<Map<String,Object>> selectUserList(@Param("vipType")Integer vipType,@Param("userId")String userId);
	
	List<MVipInfo> selectZqVipUser(@Param("userId")String userId);
	
	List<Map<String,Object>> selectZqVip();
	
	MVipInfo selectZqUserInfo(@Param("userId")String userId,@Param("vipId")int vipId);
}

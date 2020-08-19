package com.mc.bzly.dao.user;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mc.bzly.model.user.MWeakupCheckin;

@Mapper
public interface MWeakupCheckinDao {
	
	void insert(MWeakupCheckin mWeakupCheckin);
	
	void update(MWeakupCheckin mWeakupCheckin);
	
	MWeakupCheckin selectOne(Integer chkId);
	
	List<MWeakupCheckin> selectList(MWeakupCheckin mWeakupCheckin);
	
	void updateFailure(Integer logId);

	void batchUpdate(List<MWeakupCheckin> mWeakupCheckin);
}

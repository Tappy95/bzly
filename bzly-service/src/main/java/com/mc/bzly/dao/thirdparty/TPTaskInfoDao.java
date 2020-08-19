package com.mc.bzly.dao.thirdparty;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mc.bzly.model.thirdparty.TPTaskInfo;

@Mapper
public interface TPTaskInfoDao {
	 
	void batchInsert(List<TPTaskInfo> tpTaskInfos);
	
	void update(TPTaskInfo tpTaskInfo);
	
	List<TPTaskInfo> selectList(TPTaskInfo tpTaskInfo);
	
	TPTaskInfo selectOne(Integer id);

	List<TPTaskInfo> selectFList(TPTaskInfo tpTaskInfo);

	void updateIsUpper(Integer id);
	
	TPTaskInfo selectUserTask(String userId);
	
	int delete(Integer id);
}

package com.mc.bzly.dao.user;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mc.bzly.model.user.LDarenRewardDetail;

@Mapper
public interface LDarenRewardDetailDao {
	
	void insert(LDarenRewardDetail lDarenRewardDetail);
	
	List<LDarenRewardDetail> selectList(LDarenRewardDetail lDarenRewardDetail);

	long selectSumApprenticeReward(String userId);
	
	int selectCount(LDarenRewardDetail lDarenRewardDetail);
	
	long selectSum(LDarenRewardDetail lDarenRewardDetail);

	List<LDarenRewardDetail> selectByUserToday(@Param("apprenticeId")String userId,@Param("createTime") Long time);
	
	List<Map<String, Object>> selectListF(@Param("userId") String userId,@Param("accountId") String accountId, @Param("pageIndex")Integer pageIndex,@Param("pageSize")Integer pageSize);

	long selectListCountF(@Param("userId") String userId,@Param("accountId") String accountId);
	
	/**
	 * 查询徒弟完成了多少任务
	 * @param userId
	 * @return
	 */
	long selectCountTask(@Param("userId")String userId);
}

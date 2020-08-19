package com.mc.bzly.dao.user;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mc.bzly.model.user.LUserTptaskSubmit;
import com.mc.bzly.model.user.LUserTptaskSubmitVo;

@Mapper
public interface LUserTptaskSubmitDao {

	void batchInsert(List<LUserTptaskSubmit> lUserTptaskSubmits);
	
	void delete(@Param("userId")String userId,@Param("lTpTaskId")Integer lTpTaskId);
	
	List<LUserTptaskSubmit> selectList(@Param("userId")String userId,@Param("lTpTaskId")Integer lTpTaskId);

	List<LUserTptaskSubmitVo> selectLUserTptaskSubmitVo(@Param("userId")String userId,@Param("lTpTaskId")Integer lTpTaskId);
}

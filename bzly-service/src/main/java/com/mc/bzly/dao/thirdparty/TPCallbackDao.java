package com.mc.bzly.dao.thirdparty;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mc.bzly.model.thirdparty.TPCallback;

@Mapper
public interface TPCallbackDao {
	
	void insert(TPCallback tpCallback);
	
	List<TPCallback> selectList(TPCallback tpCallback);
	/**
	 * 后台获取游戏商户下拉列表
	 * @return
	 */
	List<TPCallback> selectDownList();
	
	List<TPCallback> selectExcl(TPCallback tpCallback);
	
	long selectCount(@Param("userId")String userId);

	long selectCountMore4000(@Param("userId")String userId);

	long selectTodayFinish(@Param("userId")String userId,@Param("createTime") long now);
}

package com.mc.bzly.dao.thirdparty;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mc.bzly.model.thirdparty.BZCallback;

@Mapper
public interface BZCallbackDao {
	
	void insert(BZCallback bzCallback);
	
	BZCallback selectByOrderNum(@Param("ordernum") String ordernum);
	
    List<BZCallback> selectListPage(BZCallback bzCallback);
	
	int selectCount(BZCallback bzCallback);
	
    Map<String,Object> selectSmallSum(BZCallback bzCallback);
	
	Map<String,Object> selectCountSum(BZCallback bzCallback);

	BZCallback selectByOrderNumStatus(@Param("ordernum")String ordernum, @Param("status")Integer status);

	void deleteByOrderNum(String ordernum);
	
}

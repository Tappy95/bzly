package com.mc.bzly.dao.thirdparty;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mc.bzly.model.thirdparty.YoleCallback;

@Mapper
public interface YoleCallbackDao {
	
	void insert(YoleCallback yoleCallback);

	YoleCallback selectByYoLeId(@Param("yoleid")String yoleid);
	
    List<YoleCallback> selectListPage(YoleCallback yoleCallback);
	
	int selectCount(YoleCallback yoleCallback);
	
    Map<String,Object> selectSmallSum(YoleCallback yoleCallback);
	
	Map<String,Object> selectCountSum(YoleCallback yoleCallback);

	YoleCallback selectByYoleidStatus(@Param("yoleid")String yoleid,@Param("status") Integer status);

	void deleteByYoleid(String yoleid);
}

package com.mc.bzly.dao.thirdparty;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mc.bzly.model.thirdparty.PCDDCallback;

@Mapper
public interface PCDDCallbackDao {
	
	void insert(PCDDCallback pcDDCallback);

	PCDDCallback selectByOrderNum(@Param("ordernum")String ordernum);
	
	List<PCDDCallback> selectListPage(PCDDCallback pcDDCallback);
	
	int selectCount(PCDDCallback pcDDCallback);
	
	Map<String,Object> selectSmallSum(PCDDCallback pcDDCallback);
	
	Map<String,Object> selectCountSum(PCDDCallback pcDDCallback);
	
	PCDDCallback selectByOrderNumStatus(@Param("ordernum")String ordernum,@Param("status") Integer status);

	void deleteByOrderNum(String ordernum);
}

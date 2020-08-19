package com.mc.bzly.dao.thirdparty;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mc.bzly.model.thirdparty.XWCallback;

@Mapper
public interface XWCallbackDao {
	
	void insert(XWCallback xwCallback);
	
	XWCallback selectByOrderNum(@Param("ordernum") String ordernum);
	
    List<XWCallback> selectListPage(XWCallback xwCallback);
	
	int selectCount(XWCallback xwCallback);
	
    Map<String,Object> selectSmallSum(XWCallback xwCallback);
	
	Map<String,Object> selectCountSum(XWCallback xwCallback);

	XWCallback selectByOrderNumStatus(@Param("ordernum")String ordernum, @Param("status")Integer status);

	void deleteByOrderNum(String ordernum);

	void insertXYZ(XWCallback xwCallback);

	XWCallback selectByOrderNumXYZ(String ordernum);
	
   List<XWCallback> selectXyzListPage(XWCallback xwCallback);
	
	int selectXyzCount(XWCallback xwCallback);
	
    Map<String,Object> selectXyzSmallSum(XWCallback xwCallback);
	
	Map<String,Object> selectXyzCountSum(XWCallback xwCallback);
	
}

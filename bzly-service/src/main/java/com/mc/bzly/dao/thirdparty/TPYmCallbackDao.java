package com.mc.bzly.dao.thirdparty;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mc.bzly.model.thirdparty.TPYmCallback;

@Mapper
public interface TPYmCallbackDao {
	
	int insert(TPYmCallback tPYmCallback);
	
	int update(@Param("id")Integer id,@Param("state")Integer state);
	
	TPYmCallback selectCallbackId(String coinCallbackId);
	
	List<TPYmCallback> selectList(TPYmCallback tPYmCallback);
	
	int selectCount(TPYmCallback tPYmCallback);
	
	Map<String,Object>selectSmallSum(TPYmCallback tPYmCallback);
	
	Map<String,Object>selectCountSum(TPYmCallback tPYmCallback);

}

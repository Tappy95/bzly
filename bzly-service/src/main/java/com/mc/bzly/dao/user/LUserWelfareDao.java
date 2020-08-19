package com.mc.bzly.dao.user;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mc.bzly.model.user.LUserWelfare;

@Mapper
public interface LUserWelfareDao {
	
	int add(LUserWelfare lUserWelfare);
	
	int selectCount(@Param("userId")String userId,@Param("types")Integer types);

}

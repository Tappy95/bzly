package com.mc.bzly.dao.platform;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MTaskJobDao {
	
	int update(@Param("cronExpression")String cronExpression,@Param("modifiedDate")long modifiedDate,@Param("jobName")String jobName);

}

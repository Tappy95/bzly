package com.mc.bzly.dao.platform;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mc.bzly.model.platform.PCashPriceConfig;

@Mapper
public interface PCashPriceConfigDao {
	
	int save(PCashPriceConfig pCashPriceConfig);
	
	List<PCashPriceConfig> selectList(PCashPriceConfig pCashPriceConfig);
	
	PCashPriceConfig selectOne(Integer id);
	
	int update(PCashPriceConfig pCashPriceConfig);
	
	int delete(Integer id);
	
	PCashPriceConfig selectPriceOne(@Param("price")Integer price,@Param("webType")Integer webType);

}

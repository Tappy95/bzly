package com.mc.bzly.dao.fighting;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mc.bzly.model.fighting.MFightingType;

@Mapper
public interface MFightingTypeDao {
	
	Integer save(MFightingType mFightingType);
	
	List<MFightingType> seleteList(MFightingType mFightingType);
	
	MFightingType selectOne(@Param("typeId")Integer typeId);
	
	Integer update(MFightingType mFightingType);
	
	Integer delete(@Param("typeId")Integer typeId);
}

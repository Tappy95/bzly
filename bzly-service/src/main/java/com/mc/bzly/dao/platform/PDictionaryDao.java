package com.mc.bzly.dao.platform;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mc.bzly.model.platform.PDictionary;

@Mapper
public interface PDictionaryDao {

	void insert(PDictionary pDictionary);
	
	int update(PDictionary pDictionary);
	
	PDictionary selectOne(Integer id);

	PDictionary selectByName(String selectByName);

	List<PDictionary> selectList(PDictionary pDictionary);
	/**
	 * 根据字典名称和状态查询题目类型
	 * @param status
	 * @param dicName
	 * @return
	 */
	List<PDictionary> selectByNameStatus(@Param("status")Integer status,@Param("dicName")String dicName);
}

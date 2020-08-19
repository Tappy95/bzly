package com.mc.bzly.dao.thirdparty;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mc.bzly.model.thirdparty.TpGameRelationType;

@Mapper
public interface TpGameRelationTypeDao {
	
	int save(TpGameRelationType tpGameRelationType);
	
	int insertList(List<TpGameRelationType> tpGameRelationTypes);
	
	List<Integer> selectListType(Integer gameId);
	
	int delete(Integer gameId);
	
	int deleteTypeId(Integer typeId);

}

package com.mc.bzly.dao.fighting;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mc.bzly.model.fighting.MFightingQuestion;

@Mapper
public interface MFightingQuestionDao {
	
	Integer save(MFightingQuestion mFightingQuestion);
	
	MFightingQuestion selectOne(@Param("qId")Integer qId);
	
	List<MFightingQuestion> selectList(MFightingQuestion mFightingQuestion);
	
	Integer update(MFightingQuestion mFightingQuestion);
    
	Integer delete(@Param("qId")Integer qId);
	
	List<MFightingQuestion> selectCreatorList(MFightingQuestion mFightingQuestion);
	
	Map<String,Object> selectCreator(@Param("qId")Integer qId);
	
	MFightingQuestion getUserTopic(@Param("creator")String creator);
	
	MFightingQuestion selectQueOne(@Param("qId")Integer qId);
}

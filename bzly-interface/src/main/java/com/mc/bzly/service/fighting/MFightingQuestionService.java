package com.mc.bzly.service.fighting;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.mc.bzly.model.fighting.MFightingQuestion;
import com.mc.bzly.model.platform.PDictionary;

public interface MFightingQuestionService {
     
	List<PDictionary> questionTypeList();
	 
	Integer save(MFightingQuestion mFightingQuestion);
	 
	PageInfo<MFightingQuestion> pageList(MFightingQuestion mFightingQuestion);
	 
	Map<String,Object> info(Integer id);
	 
	Integer update(MFightingQuestion mFightingQuestion);
	 
	Integer delete(Integer id);
	 
	PageInfo<MFightingQuestion> pageCreatorList(MFightingQuestion mFightingQuestion);
	 
	Map<String,Object> appResult(Integer qId);
	 
	Integer auditQuestion(MFightingQuestion mFightingQuestion);
	 
	Integer appSave(MFightingQuestion mFightingQuestion,String answer1,String answer2,String answer3,String answer4);
	 
	Map<String,Object> getUserTopic(String userId);
}

package com.mc.bzly.service.fighting;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.mc.bzly.model.fighting.AnswerStart;
import com.mc.bzly.model.fighting.AnswerUser;
import com.mc.bzly.model.fighting.LFightingInfo;
import com.mc.bzly.model.fighting.MFightingInfo;

public interface MFightingInfoService {
	 
	PageInfo<MFightingInfo> pageList(MFightingInfo mFightingInfo);
	 
	Map<String,Object> foundRoom(MFightingInfo mFightingInfo);
	 
	Integer cancelRoom(String entryCode);
	 
	Map<String,Object> joinRoom(MFightingInfo mFightingInfo);
	 
	Map<String,Object> seeRoot(String entryCode);
	 
	Integer startFighting(String entryCode);
	 
	Integer isStart(MFightingInfo mFightingInfo);
	 
	Map<String, Object> getQuestion(AnswerStart answerStart);
	 
	LFightingInfo subResult(LFightingInfo lFightingInfo);
	 
	Map<String,Object> getEnemyScore(LFightingInfo lFightingInfo);
	 
	Integer luckDraw(Integer fightId);
	 
	Integer partakeAnswer(AnswerUser answerUser);
	 
	Map<String,Object> randomUser(String userId,Double time);
	 
	Integer cancelMatching(String userId);
	 
	String getNotRoom(String entryCode);
}

package com.mc.bzly.impl.fighting;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.mc.bzly.dao.fighting.MFightingInfoDao;
import com.mc.bzly.dao.fighting.MFightingTypeDao;
import com.mc.bzly.model.fighting.AnswerUser;
import com.mc.bzly.model.fighting.MFightingInfo;
import com.mc.bzly.model.fighting.MFightingType;
import com.mc.bzly.service.redis.RedisService;
import com.bzly.common.utils.StringUtil;
@Scope("singleton")
@Service
public class RandomMatchingUtil {
	  public static final String ANSWER_USER_S = "answerUsers";
	  @Autowired
	  private RedisService redisService;
	  @Autowired
	  private MFightingInfoDao mFightingInfoDao;
	  @Autowired
	  private MFightingTypeDao mFightingTypeDao;
	  
      public void addAnswerUser(AnswerUser answerUser) {
    	 Map<String,AnswerUser> map=(Map<String,AnswerUser>)redisService.getObject(ANSWER_USER_S);
    	 if(map==null) {
    		 map=new HashMap<String,AnswerUser>();
    	 }
    	 map.remove(answerUser.getUserId());
    	 map.put(answerUser.getUserId(),answerUser);
    	 redisService.put(ANSWER_USER_S, map);
      }

      public AnswerUser random(String userId) {
    	  Map<String,AnswerUser> map=(Map<String,AnswerUser>)redisService.getObject(ANSWER_USER_S);
    	  AnswerUser answerUser=map.get(userId);
    	  if(StringUtil.isNullOrEmpty(answerUser)) {
    		  return null;
    	  }
    	  if(answerUser.getIsMatching()==1) {
    		  map.remove(userId);
  			  redisService.put(ANSWER_USER_S, map);
    		  return answerUser;
    	  }
    	  AnswerUser ans=null;
    	  AnswerUser ansMap=null;
    	  for (String key : map.keySet()) {
    		  if(!userId.equals(key)) {
    			  ansMap=map.get(key);
        		  if(ansMap.getIsMatching()==0) {
        			  ans=ansMap;
        			  break;
        		  }
    		  }
          }
    	  if(!StringUtil.isNullOrEmpty(ans)) {
		    //创建房间将房间放入缓存
			MFightingType type=mFightingTypeDao.selectOne(2);
			MFightingInfo mFightingInfo=new MFightingInfo();
			mFightingInfo.setFightingType(2);
			mFightingInfo.setState(1);
			mFightingInfo.setEntryCode(StringUtil.getRandomStr(10));
			mFightingInfo.setQuesNum(type.getQuestionNum());
			mFightingInfo.setInitiator(answerUser.getUserId());
			mFightingInfo.setDefense(ans.getUserId());
			mFightingInfo.setInitiatorCoin(answerUser.getCoin());
			mFightingInfo.setDefenseCoin(ans.getCoin());
			mFightingInfoDao.save(mFightingInfo);
			redisService.put(mFightingInfo.getEntryCode(),mFightingInfo,600);
			//更改参与者状态
			answerUser.setIsMatching(1);
			answerUser.setEntryCode(mFightingInfo.getEntryCode());
			answerUser.setRole(1);
			AnswerUser a=new AnswerUser();
			a.setUserId(ans.getUserId());
			a.setCoin(ans.getCoin());
			a.setRole(2);
			a.setIsMatching(1);
			a.setEntryCode(mFightingInfo.getEntryCode());
			map.remove(ans.getUserId());
			map.remove(answerUser.getUserId());
			map.put(a.getUserId(), a);
			redisService.put(ANSWER_USER_S, map);
			return answerUser;
    	  }
    	  return answerUser;
      }

      public void deleteRandom(String userId1,String userId2) {
    	  Map<String,AnswerUser> map=(Map<String,AnswerUser>)redisService.getObject(ANSWER_USER_S);
    	  if(!StringUtil.isNullOrEmpty(map.get(userId1))) {
    		  map.remove(userId1);
    	  }
    	  if(!StringUtil.isNullOrEmpty(map.get(userId2))) {
    		  map.remove(userId2);
    	  }
    	  redisService.put(ANSWER_USER_S, map);
      }

      public void deleteRandomUser(String userId) {
    	  Map<String,AnswerUser> map=(Map<String,AnswerUser>)redisService.getObject(ANSWER_USER_S);
    	  if(!StringUtil.isNullOrEmpty(map.get(userId))) {
    		  map.remove(userId);
    	  }
    	  redisService.put(ANSWER_USER_S, map);
      }
      
      /*public AnswerUser lokeUserMap(AnswerUser answerUser,int type) {
    	  lock.lock();
  		  AnswerUser answer= null;
	  		try {
	  			System.out.println(answerUser.getUserId()+"开始在执行");
	  			if(type==1) {//添加匹配信息
	  				addAnswerUser(answerUser);
	  			}else if(type==2) {//匹配
	  				answer=random(answerUser.getUserId());
	  			}
	  			System.out.println(answerUser.getUserId()+"在执行");
	  			return answer;
	  		}catch (Exception e) {
	  			// TODO: handle exception
	  		}finally {
	  			lock.unlock();
	  		}
	  		return answer;
      }*/
}

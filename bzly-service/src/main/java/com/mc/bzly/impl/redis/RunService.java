package com.mc.bzly.impl.redis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.bzly.common.constant.ConstantUtil;
import com.mc.bzly.dao.fighting.MFightingQuestionDao;
import com.mc.bzly.impl.fighting.RandomMatchingUtil;
import com.mc.bzly.model.fighting.AnswerUser;
import com.mc.bzly.model.fighting.MFightingQuestion;
import com.mc.bzly.service.redis.RedisService;

/**
 * 项目启动时将部分信息存入redis
 */
@Component
@Order(value = 1)
public class RunService implements ApplicationRunner{

	@Autowired
    private MFightingQuestionDao mFightingQuestionDao;
	@Autowired
	private RedisService redisService;
	@Override
	public void run(ApplicationArguments args) throws Exception {
		List<MFightingQuestion> qe=(List<MFightingQuestion>)redisService.getObject(ConstantUtil.QUESTION_LIST);
		if(qe==null || qe.size()<=0) {
			MFightingQuestion que=new MFightingQuestion();
			que.setQuestionState(-1);
			List<MFightingQuestion> ques=mFightingQuestionDao.selectList(que);
			if(ques.size() != 0){
				redisService.put(ConstantUtil.QUESTION_LIST, ques);
			}
		}
		Map<String,AnswerUser> map=new HashMap<>();
		redisService.put(RandomMatchingUtil.ANSWER_USER_S, map);
	}

}

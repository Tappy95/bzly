package com.mc.bzly.impl.fighting;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.bzly.common.constant.ConstantUtil;
import com.bzly.common.constant.InformConstant;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.fighting.MFightingAnswerDao;
import com.mc.bzly.dao.fighting.MFightingQuestionDao;
import com.mc.bzly.dao.platform.PDictionaryDao;
import com.mc.bzly.dao.user.LCoinChangeDao;
import com.mc.bzly.dao.user.MUserInfoDao;
import com.mc.bzly.model.fighting.MFightingAnswer;
import com.mc.bzly.model.fighting.MFightingQuestion;
import com.mc.bzly.model.news.AppNewsInform;
import com.mc.bzly.model.platform.PDictionary;
import com.mc.bzly.model.user.LCoinChange;
import com.mc.bzly.model.user.MUserInfo;
import com.mc.bzly.service.fighting.MFightingQuestionService;
import com.mc.bzly.util.StringUtil;

@Service(interfaceClass=MFightingQuestionService.class,version=WebConfig.dubboServiceVersion)
public class MFightingQuestionServiceImpl implements MFightingQuestionService{
	
    @Autowired
    private PDictionaryDao pDictionaryDao;
    @Autowired
    private MFightingQuestionDao mFightingQuestionDao;
    @Autowired
    private MFightingAnswerDao mFightingAnswerDao;
    @Autowired
	private LCoinChangeDao lCoinChangeDao;
    @Autowired
	private MUserInfoDao mUserInfoDao;
    
	@Override
	public List<PDictionary> questionTypeList() {
		List<PDictionary> list=pDictionaryDao.selectByNameStatus(1, "questionType");
		return list;
	}

	@Override
	public Integer save(MFightingQuestion mFightingQuestion) {
		if(StringUtil.isNullOrEmpty(mFightingQuestion.getAnswers())) {
			return 3;
		}
		mFightingQuestion.setQuestionState(0);
		Integer num=mFightingQuestionDao.save(mFightingQuestion);
		if(num>0) {
			MFightingAnswer answer=null;
			List<String> list =JSONObject.parseArray(mFightingQuestion.getAnswers()).toJavaList(String.class);
            for(int i=0;i<list.size();i++) {
            	answer=(MFightingAnswer)JSONObject.parseObject(list.get(i), MFightingAnswer.class);
            	if(!StringUtil.isNullOrEmpty(answer.getAnswer())) {
            		answer.setQuestionId(mFightingQuestion.getqId());
                	mFightingAnswerDao.save(answer);	
            	}
            }
			return num;
		}
		return num;
	}

	@Override
	public PageInfo<MFightingQuestion> pageList(MFightingQuestion mFightingQuestion) {
		PageHelper.startPage(mFightingQuestion.getPageNum(), mFightingQuestion.getPageSize());
		List<MFightingQuestion> mFightingQuestionList =mFightingQuestionDao.selectList(mFightingQuestion);
		return new PageInfo<>(mFightingQuestionList);
	}

	@Override
	public Map<String, Object> info(Integer id) {
		Map<String,Object> data=new HashMap<String,Object>();
		MFightingQuestion question=mFightingQuestionDao.selectOne(id);
		List<MFightingAnswer> answer=mFightingAnswerDao.selectQuestionId(id);
		data.put("question", question);
		data.put("answer", answer);
		return data;
	}

	@Override
	public Integer update(MFightingQuestion mFightingQuestion) {
		Integer num=mFightingQuestionDao.update(mFightingQuestion);
		if(num>0) {
			MFightingAnswer answer=null;
			List<String> list =JSONObject.parseArray(mFightingQuestion.getAnswers()).toJavaList(String.class);
	        for(int i=0;i<list.size();i++) {
	        	answer=(MFightingAnswer)JSONObject.parseObject(list.get(i), MFightingAnswer.class);
	        	if(!StringUtil.isNullOrEmpty(answer.getAnswer())) {
	        		mFightingAnswerDao.update(answer);
            	}
	        }
		}
		return num;
	}

	@Override
	public Integer delete(Integer id) {
		Integer num=mFightingQuestionDao.delete(id);
		if(num>0) {
			mFightingAnswerDao.deleteQuestionId(id);	
		}
		return num;
	}

	@Override
	public PageInfo<MFightingQuestion> pageCreatorList(MFightingQuestion mFightingQuestion) {
		PageHelper.startPage(mFightingQuestion.getPageNum(), mFightingQuestion.getPageSize());
		List<MFightingQuestion> mFightingQuestionList =mFightingQuestionDao.selectCreatorList(mFightingQuestion);
		return new PageInfo<>(mFightingQuestionList);
	}

	@Override
	public Map<String, Object> appResult(Integer qId) {
		return mFightingQuestionDao.selectCreator(qId);
	}

	@Override
	public Integer auditQuestion(MFightingQuestion mFightingQuestion) {
		mFightingQuestion.setCountTime(10);
		mFightingQuestion.setScore(200);
		Integer num=mFightingQuestionDao.update(mFightingQuestion);
		if(mFightingQuestion.getQuestionState()==2) {
			if(num>0) {
				if(!StringUtil.isNullOrEmpty(mFightingQuestion.getCreator())) {
					mUserInfoDao.updatecCoin(mFightingQuestion.getCoin().longValue(), mFightingQuestion.getCreator());
					MUserInfo user=mUserInfoDao.selectOne(mFightingQuestion.getCreator());
					LCoinChange coin=new LCoinChange();
					coin.setUserId(mFightingQuestion.getCreator());
					coin.setAmount(mFightingQuestion.getCoin().longValue());
					coin.setFlowType(1);
					coin.setChangedType(ConstantUtil.COIN_CHANGED_TYPE_11);
					coin.setChangedTime(new Date().getTime());
					coin.setCoinBalance(user.getCoin());
					lCoinChangeDao.insert(coin);
				}
			}
		}
		return num;
	}
    @Transactional
	@Override
	public Integer appSave(MFightingQuestion mFightingQuestion, String answer1, String answer2,
			String answer3, String answer4) {
		mFightingQuestion.setQuestionState(1);
		Integer num=mFightingQuestionDao.save(mFightingQuestion);
		if(num>0) {
			if(!StringUtil.isNullOrEmpty(answer1)) {
				MFightingAnswer a1=new MFightingAnswer();
				a1.setAnswer(answer1);
				a1.setIsCorrect(1);
				a1.setQuestionId(mFightingQuestion.getqId());
	        	mFightingAnswerDao.save(a1);
			}
			if(!StringUtil.isNullOrEmpty(answer2)) {
				MFightingAnswer a2=new MFightingAnswer();
				a2.setAnswer(answer2);
				a2.setIsCorrect(2);
				a2.setQuestionId(mFightingQuestion.getqId());
	        	mFightingAnswerDao.save(a2);
			}
			if(!StringUtil.isNullOrEmpty(answer3)) {
				MFightingAnswer a3=new MFightingAnswer();
				a3.setAnswer(answer3);
				a3.setIsCorrect(2);
				a3.setQuestionId(mFightingQuestion.getqId());
	        	mFightingAnswerDao.save(a3);
			}
			if(!StringUtil.isNullOrEmpty(answer4)) {
				MFightingAnswer a4=new MFightingAnswer();
				a4.setAnswer(answer4);
				a4.setIsCorrect(2);
				a4.setQuestionId(mFightingQuestion.getqId());
	        	mFightingAnswerDao.save(a4);
			}
			return num;
		}
		return num;
	}

	@Override
	public Map<String,Object> getUserTopic(String userId) {
		Map<String,Object> data=new HashMap<String,Object>();
		MFightingQuestion qe=mFightingQuestionDao.getUserTopic(userId);
		if(StringUtil.isNullOrEmpty(qe)) {
			data.put("res", -1);
		}else {
			List<MFightingAnswer> answer=mFightingAnswerDao.selectQuestionId(qe.getqId());	
			data.put("res", 1);
			data.put("answer", answer);
		}
		data.put("question", qe);
		return data;
	}
	
	
}

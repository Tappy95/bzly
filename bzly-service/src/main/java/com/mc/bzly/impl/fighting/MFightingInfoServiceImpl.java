package com.mc.bzly.impl.fighting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.bzly.common.constant.ConstantUtil;
import com.bzly.common.constant.InformConstant;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.fighting.LFightingInfoDao;
import com.mc.bzly.dao.fighting.MFightingAnswerDao;
import com.mc.bzly.dao.fighting.MFightingInfoDao;
import com.mc.bzly.dao.fighting.MFightingQuestionDao;
import com.mc.bzly.dao.fighting.MFightingTypeDao;
import com.mc.bzly.dao.task.MTaskInfoDao;
import com.mc.bzly.dao.user.LCoinChangeDao;
import com.mc.bzly.dao.user.LPigChangeDao;
import com.mc.bzly.dao.user.LUserTaskDao;
import com.mc.bzly.dao.user.MUserInfoDao;
import com.mc.bzly.impl.redis.AWorker;
import com.mc.bzly.model.fighting.AnswerResult;
import com.mc.bzly.model.fighting.AnswerStart;
import com.mc.bzly.model.fighting.AnswerUser;
import com.mc.bzly.model.fighting.LFightingInfo;
import com.mc.bzly.model.fighting.MFightingAnswer;
import com.mc.bzly.model.fighting.MFightingInfo;
import com.mc.bzly.model.fighting.MFightingQuestion;
import com.mc.bzly.model.fighting.MFightingType;
import com.mc.bzly.model.jms.JmsWrapper.Type;
import com.mc.bzly.model.news.AppNewsInform;
import com.mc.bzly.model.task.MTaskInfo;
import com.mc.bzly.model.user.LCoinChange;
import com.mc.bzly.model.user.LPigChange;
import com.mc.bzly.model.user.LUserTask;
import com.mc.bzly.model.user.MUserInfo;
import com.mc.bzly.service.fighting.MFightingInfoService;
import com.mc.bzly.service.jms.JMSProducer;
import com.mc.bzly.service.news.AppNewsInformService;
import com.mc.bzly.service.passbook.MPassbookService;
import com.mc.bzly.service.redis.RedisService;
import com.mc.bzly.util.StringUtil;

@Service(interfaceClass=MFightingInfoService.class,version=WebConfig.dubboServiceVersion)
public class MFightingInfoServiceImpl implements MFightingInfoService {
	
	@Autowired
    private MFightingInfoDao mFightingInfoDao;
	@Autowired
	private RedisService redisService;
	@Autowired
	private MUserInfoDao mUserInfoDao;
	@Autowired
	private LCoinChangeDao lCoinChangeDao;
	@Autowired
	private MFightingTypeDao mFightingTypeDao;
	@Autowired
	private MFightingAnswerDao mFightingAnswerDao;
	@Autowired
	private LFightingInfoDao lFightingInfoDao;
	@Autowired
	private RandomMatchingUtil randomMatchingUtil;
	@Autowired
	private MFightingQuestionDao mFightingQuestionDao;
	@Autowired
	private LUserTaskDao lUserTaskDao;
	@Autowired
	private MTaskInfoDao mTaskInfoDao;
	@Autowired
    private LPigChangeDao lPigChangeDao;
	@Autowired
	private AppNewsInformService appNewsInformService;
	@Autowired
	private MPassbookService mPassbookService;
	@Autowired
	private JMSProducer jmsProducer;
	
	private Lock lock = new ReentrantLock(); 
	
	@Override
	public PageInfo<MFightingInfo> pageList(MFightingInfo mFightingInfo) {
		PageHelper.startPage(mFightingInfo.getPageNum(), mFightingInfo.getPageSize());
		List<MFightingInfo> mFightingInfoList =mFightingInfoDao.selectList(mFightingInfo);
		return new PageInfo<>(mFightingInfoList);
	}

	@Override
	public Map<String,Object> foundRoom(MFightingInfo mFightingInfo) {
		Map<String,Object> data=new HashMap<String,Object>();
		if(mFightingInfo.getInitiatorCoin()<ConstantUtil.QUESTION_MIN_500 || mFightingInfo.getInitiatorCoin()>ConstantUtil.QUESTION_MXI_5000) {
			data.put("res", 4);
			return data;//金币数超出范围
		}
		MUserInfo user=mUserInfoDao.selectOne(mFightingInfo.getInitiator());
		if(user.getCoin()<mFightingInfo.getInitiatorCoin()) {
			data.put("res", 2);
			return data;//金币不足无法参与
		}
		MFightingType type=mFightingTypeDao.selectOne(1);
		mFightingInfo.setFightingType(1);
		mFightingInfo.setState(1);
		mFightingInfo.setEntryCode(StringUtil.getRandomStr(8));
		mFightingInfo.setQuesNum(type.getQuestionNum());
		mFightingInfo.setFightingTime(new Date().getTime());
		mFightingInfoDao.save(mFightingInfo);
		redisService.put(mFightingInfo.getEntryCode(), mFightingInfo,600);
		data.put("img", user.getProfile());//我方头像
		data.put("entryCode", mFightingInfo.getEntryCode());//房间号
		data.put("mobile", user.getMobile());//我的电话号码
		return data;
	}

	@Override
	public Integer cancelRoom(String entryCode) {
		redisService.delete(entryCode);
		return 1;
	}

	@Override
	public Map<String,Object> joinRoom(MFightingInfo mFightingInfo) {
		Map<String,Object> data=new HashMap<String,Object>();
		if(mFightingInfo.getDefenseCoin()<ConstantUtil.QUESTION_MIN_500 || mFightingInfo.getDefenseCoin().intValue()>ConstantUtil.QUESTION_MXI_5000) {
			data.put("res", 4);
			return data;//金币数超出范围
		}
		MUserInfo user=mUserInfoDao.selectOne(mFightingInfo.getDefense());
		if(user.getCoin()<mFightingInfo.getDefenseCoin()) {
			data.put("res", 2);
			return data;//金币不足无法参与
		}
		MFightingInfo info=null;
		String mFightingInfoStr = new AWorker<String>() {
			@Override
			public String call(String userId) throws Exception {
				MFightingInfo info=(MFightingInfo)redisService.getObject(mFightingInfo.getEntryCode());
				if(StringUtil.isNullOrEmpty(info)) {
					return "3";//房间不存在
				}
				if(info.getState()==2) {
					return "5";//房间人数已满
				}
				info.setDefense(mFightingInfo.getDefense());
				info.setDefenseCoin(mFightingInfo.getDefenseCoin());
				info.setState(2);
				redisService.put(info.getEntryCode(), info,600);
				return JSON.toJSONString(info);
			}
		}.execute(mFightingInfo.getDefense(),"redis_lock_joinRoom");
		
		if("5".equals(mFightingInfoStr)) {
			data.put("res", 5);
			return data;//房间人数已满
		}
		if("3".equals(mFightingInfoStr)) {
			data.put("res", 3);
			return data;//房间不存在
		}
		info = JSON.parseObject(mFightingInfoStr,MFightingInfo.class);
		MUserInfo userInitiator=mUserInfoDao.selectOne(info.getInitiator());
		
	/*	MFightingInfo info=(MFightingInfo)redisService.getObject(mFightingInfo.getEntryCode());
		if(info.getState()==2) {
			data.put("res", 5);
			return data;//房间人数已满
		}
		if(StringUtil.isNullOrEmpty(info)) {
			data.put("res", 3);
			return data;//房间不存在
		}
		info.setDefense(mFightingInfo.getDefense());
		info.setDefenseCoin(mFightingInfo.getDefenseCoin());
		info.setState(2);
		MUserInfo userInitiator=mUserInfoDao.selectOne(info.getInitiator());
		redisService.put(info.getEntryCode(), info,600);*/
		
		mFightingInfoDao.update(info);
		data.put("enemyImg", userInitiator.getProfile());//对方头像
		data.put("img", user.getProfile());//我方头像
		data.put("mobile", user.getMobile());//我的电话
		data.put("enemyMoile", userInitiator.getMobile());//对方电话
		data.put("res", 1);
		data.put("role", 2);//角色：1发起者2参与者
		data.put("quesNum",info.getQuesNum());//答题数量
		data.put("fightId", info.getFightId());//对战id
		data.put("entryCode", info.getEntryCode());//房间号
		return data;
	}

	@Override
	public Map<String,Object> seeRoot(String entryCode) {
		Map<String,Object> data=new HashMap<String,Object>();
		MFightingInfo info=(MFightingInfo)redisService.getObject(entryCode);
		if(StringUtil.isNullOrEmpty(info)) {
			data.put("res", 3);//房间不存在
			return data;
		}
		if(info.getState()!=2) {
			data.put("res", 0);//未加入
			return data;
		}
		MUserInfo user=mUserInfoDao.selectOne(info.getDefense());
		data.put("enemyImg", user.getProfile());//对方头像
		data.put("enemyMoile",user.getMobile());//对方电话
		data.put("quesNum", info.getQuesNum());//答题数量
		data.put("fightId", info.getFightId());//对战id
		data.put("res", 1);//加入
		data.put("role", 1);//角色：1发起者2参与者
		data.put("entryCode", info.getEntryCode());//房间号
		return data;
	}

	@Override
	public Integer startFighting(String entryCode) {
		MFightingInfo info=(MFightingInfo)redisService.getObject(entryCode);
		//开始对战扣除金币
		mUserInfoDao.updatecCoinReduce(info.getInitiatorCoin().longValue(), info.getInitiator());
		rechargeRewardPush(info.getInitiator(),info.getInitiatorCoin().longValue(),1);
		mUserInfoDao.updatecCoinReduce(info.getDefenseCoin().longValue(), info.getDefense());
		rechargeRewardPush(info.getDefense(),info.getDefenseCoin().longValue(),1);
		
		MUserInfo initiatorUser=mUserInfoDao.selectOne(info.getInitiator());
		MUserInfo defenseUser=mUserInfoDao.selectOne(info.getDefense());
		
		LCoinChange coin=new LCoinChange();
		coin.setUserId(info.getInitiator());
		coin.setAmount(info.getInitiatorCoin().longValue());
		coin.setChangedType(1);
		coin.setFlowType(2);
		coin.setChangedTime(new Date().getTime());
		coin.setCoinBalance(initiatorUser.getCoin());
		lCoinChangeDao.insert(coin);
		coin.setUserId(info.getDefense());
		coin.setAmount(info.getDefenseCoin().longValue());
		coin.setChangedType(1);
		coin.setFlowType(2);
		coin.setChangedTime(new Date().getTime());
		coin.setCoinBalance(defenseUser.getCoin());
		lCoinChangeDao.insert(coin);
		//修改对战记录状态
		info.setState(3);
		redisService.put(entryCode, info,600);
		AnswerResult redsResult=(AnswerResult)redisService.getObject(info.getFightId()+"-1");
		if(!StringUtil.isNullOrEmpty(redsResult)) {
			redisService.delete(info.getFightId()+"-1");
			redisService.delete(info.getFightId()+"-2");
			redisService.delete(info.getFightId().toString());
			redisService.delete(info.getEntryCode());
			return 1;
		}
		//随机获取需要回答的题目
		List<MFightingQuestion> ques=randomQuestion(info.getQuesNum());
		redisService.put(info.getFightId().toString(), ques,600);
		//创建答题双方分数记录缓存
		AnswerResult answerResult=new AnswerResult();
		answerResult.setScore(0);
		answerResult.setAnswerNum(0);
		answerResult.setIsEnd(0);
		answerResult.setIsSee(0);
		answerResult.setTime(new Date().getTime());
		answerResult.setUserId(info.getInitiator());//发起者
		redisService.put(info.getFightId()+"-1", answerResult,600);
		answerResult.setUserId(info.getDefense());//参与者
		redisService.put(info.getFightId()+"-2", answerResult,600);
		return 1;
	}

	@Override
	public Integer isStart(MFightingInfo mFightingInfo) {
		MFightingInfo info=(MFightingInfo)redisService.getObject(mFightingInfo.getEntryCode());
		if(info.getState()!=3) {
			return 0; //好友还未点击开始挑战
		}
		return 1;
	}
	
	
	//随机获取所有需要回答的题目
	public List<MFightingQuestion> randomQuestion(Integer quesNum){
		List<MFightingQuestion> list=new ArrayList<MFightingQuestion>();
		List<MFightingQuestion> ques=(List<MFightingQuestion>)redisService.getObject(ConstantUtil.QUESTION_LIST);
		if(ques==null || ques.size()==0) {
			MFightingQuestion que=new MFightingQuestion();
			que.setQuestionState(-1);
			ques=mFightingQuestionDao.selectList(que);
		}
		Random rand = new Random();
		MFightingQuestion que=null;
		for(int i=1;i<=quesNum;i++) {
			que=ques.get(rand.nextInt(ques.size()-1));
			list.add(que);
		}
		return list;
	}

	@Override
	public Map<String, Object> getQuestion(AnswerStart answerStart) {
		MFightingInfo info=(MFightingInfo)redisService.getObject(answerStart.getEntryCode());
		if(StringUtil.isNullOrEmpty(info)) {
			return null;
		}
		if(answerStart.getNum()>info.getQuesNum()) {
			redisService.delete(info.getFightId()+"-1");
			redisService.delete(info.getFightId()+"-2");
			redisService.delete(info.getFightId().toString());
			redisService.delete(info.getEntryCode());
			info.setState(5);
			mFightingInfoDao.update(info);
			return null;
		}
		Map<String,Object> data=new HashMap<String,Object>();
		List<MFightingQuestion> ques=(List<MFightingQuestion>)redisService.getObject(answerStart.getFightId().toString());
		MFightingQuestion question=ques.get(answerStart.getNum()-1);
		List<MFightingAnswer> answer=mFightingAnswerDao.selectQuestionId(question.getqId());
		Collections.shuffle(answer);
		data.put("question", question);
		data.put("answer", answer);
		return data;
	}

	@Override
	public LFightingInfo subResult(LFightingInfo lFightingInfo) {
		Long data=new Date().getTime();
		MFightingInfo info=(MFightingInfo)redisService.getObject(lFightingInfo.getEntryCode());
		MFightingQuestion que=mFightingQuestionDao.selectQueOne(lFightingInfo.getQuestionId());
		lFightingInfo.setQuesScore(que.getScore());
		lFightingInfo.setSetTime(que.getCountTime());
		if(info.getFightingType()!=3) {
			if(lFightingInfo.getRole()==1) {//创建者提交答案
				AnswerResult answerResult=(AnswerResult)redisService.getObject(lFightingInfo.getFightId()+"-1");
				Long dateTime=data-answerResult.getTime();
				if(dateTime/1000>20) {
					return null;
				}
				int res=lFightingInfo.getNum()-answerResult.getAnswerNum();
				if(res>1) {
					redisService.delete(lFightingInfo.getFightId()+"-1");
					redisService.delete(lFightingInfo.getFightId()+"-2");
					redisService.delete(lFightingInfo.getFightId().toString());
					redisService.delete(info.getEntryCode());
					info.setState(5);
					mFightingInfoDao.update(info);
					return null;
				}
				return computeResult(info,lFightingInfo,lFightingInfo.getFightId()+"-1");
			}else {//参与者计算分数
				AnswerResult answerResult=(AnswerResult)redisService.getObject(lFightingInfo.getFightId()+"-2");
				Long dateTime=data-answerResult.getTime();
				if(dateTime/1000>20) {
					return null;
				}
				int res=lFightingInfo.getNum()-answerResult.getAnswerNum();
				if(res>1) {
					redisService.delete(lFightingInfo.getFightId()+"-1");
					redisService.delete(lFightingInfo.getFightId()+"-2");
					redisService.delete(lFightingInfo.getFightId().toString());
					redisService.delete(info.getEntryCode());
					info.setState(5);
					mFightingInfoDao.update(info);
					return null;
				}
				return computeResult(info,lFightingInfo,lFightingInfo.getFightId()+"-2");
			}
		}else {
			AnswerResult answerResult=(AnswerResult)redisService.getObject(lFightingInfo.getFightId()+"-1");
			Long dateTime=data-answerResult.getTime();
			if(dateTime/1000>20) {
				return null;
			}
			int res=lFightingInfo.getNum()-answerResult.getAnswerNum();
			if(res>1) {
				redisService.delete(lFightingInfo.getFightId()+"-1");
				redisService.delete(lFightingInfo.getFightId()+"-2");
				redisService.delete(lFightingInfo.getFightId().toString());
				redisService.delete(info.getEntryCode());
				info.setState(5);
				mFightingInfoDao.update(info);
				return null;
			}
			//人机答题
			LFightingInfo rj=new LFightingInfo();
			Random rand = new Random();
			rj.setUserId("1");
			rj.setRole(2);
			rj.setIsCorrect(1);
			int time=rand.nextInt(4000);
			rj.setAnswerTime((long)time);
			rj.setQuestionId(lFightingInfo.getQuestionId());
			rj.setNum(lFightingInfo.getNum());
			rj.setFightId(lFightingInfo.getFightId());
			rj.setQuesScore(lFightingInfo.getQuesScore());
			rj.setSetTime(lFightingInfo.getSetTime());
			rj.setEntryCode(lFightingInfo.getEntryCode());
			rj.setAnswerId(-1);
			computeResult(info,rj,rj.getFightId()+"-2");
			//参与者答题
			return computeResult(info,lFightingInfo,lFightingInfo.getFightId()+"-1");
		}
		
	}
	
	@Override
	public Map<String,Object> getEnemyScore(LFightingInfo lFightingInfo) {
		Map<String,Object> data=new HashMap<String,Object>();
		AnswerResult answer1=(AnswerResult)redisService.getObject(lFightingInfo.getFightId()+"-1");
		AnswerResult answer2=(AnswerResult)redisService.getObject(lFightingInfo.getFightId()+"-2");
		Random rand = new Random();
		if(lFightingInfo.getRole()==1) {
			if(answer1.getIsEnd()==0) {//对战未结束
				if(answer1.getAnswerNum().intValue()==answer2.getAnswerNum().intValue()) {
					lFightingInfo.setEnemyScore(answer2.getScore());
					data.put("enemyScore", answer2.getScore());
					return data;
				}else {
					if(lFightingInfo.getGetScoreTime()<=0) {
						MFightingInfo info=(MFightingInfo)redisService.getObject(lFightingInfo.getEntryCode());
						LFightingInfo rj=new LFightingInfo();//关机者提交答案
						rj.setUserId(info.getDefense());
						rj.setRole(2);
						rj.setIsCorrect(2);
						rj.setAnswerTime(10l);
						rj.setQuestionId(-1);
						rj.setAnswerId(-1);
						rj.setNum(answer1.getAnswerNum());
						rj.setFightId(lFightingInfo.getFightId());
						rj.setQuesScore(100);
						rj.setSetTime(lFightingInfo.getSetTime());
						rj.setEntryCode(lFightingInfo.getEntryCode());
						subResult(rj);
						if(answer1.getAnswerNum()==info.getQuesNum()) {
							/*if(info.getFightingType()==3) {
								redisService.delete(lFightingInfo.getFightId()+"-1");
								redisService.delete(lFightingInfo.getFightId()+"-2");
								redisService.delete(lFightingInfo.getFightId().toString());
								redisService.delete(info.getEntryCode());
							}else {
								//双方都查看过结果，则清空缓存
								redisService.delete(lFightingInfo.getFightId()+"-1");
								redisService.delete(lFightingInfo.getFightId()+"-2");
								redisService.delete(lFightingInfo.getFightId().toString());
								redisService.delete(info.getEntryCode());*/
								if(info.getFightingType()!=1) {
									randomMatchingUtil.deleteRandom(info.getInitiator(),info.getDefense());
								}
							/*}*/
							if(answer1.getScore().intValue()>answer2.getScore().intValue()) {
								info.setWinner(info.getInitiator());
								info.setVictoryScore(answer1.getScore());
								info.setFailureScore(answer2.getScore());
								info.setState(4);
								mFightingInfoDao.update(info);
								data.put("enemyScore", answer2.getScore());
								data.put("isVictory", 1);//是否胜利1胜利2失败
								if(info.getFightingType()!=1) {
									int coin=info.getInitiatorCoin()+rand.nextInt(info.getDefenseCoin());
									randomSettlement(coin,info.getWinner(),2,info.getFightId());
								    data.put("reward", coin);
								    data.put("fightingType",2);
								}else {
									data.put("fightingType",1);
								}
								//双方都查看过结果，则清空缓存
								
								redisService.delete(lFightingInfo.getFightId()+"-1");
								redisService.delete(lFightingInfo.getFightId()+"-2");
								redisService.delete(lFightingInfo.getFightId().toString());
								redisService.delete(info.getEntryCode());
								if(info.getFightingType()!=1) {
									randomMatchingUtil.deleteRandom(info.getInitiator(),info.getDefense());
								}
								
								return data;
							}else {
							
								info.setWinner(info.getDefense());
								info.setVictoryScore(answer2.getScore());
								info.setFailureScore(answer1.getScore());
								info.setState(4);
								mFightingInfoDao.update(info);
								
								data.put("enemyScore", answer2.getScore());
								data.put("isVictory", 2);//是否胜利1胜利2失败
								if(info.getFightingType()!=1) {
								    data.put("fightingType",2);
								}else {
									data.put("fightingType",1);
								}
								return data;
							}
						}
						data.put("enemyScore", answer2.getScore());
						return data;
					}else {
						data.put("enemyScore", -1);
						return data;
					}
				}
			}else {//对战结束
				if(answer1.getAnswerNum().intValue()==answer2.getAnswerNum().intValue()) {
					answer1.setIsSee(1);
					redisService.put(lFightingInfo.getFightId()+"-1", answer1,600);
					MFightingInfo info=(MFightingInfo)redisService.getObject(lFightingInfo.getEntryCode());
					if(info.getFightingType()==3) {
						redisService.delete(lFightingInfo.getFightId()+"-1");
						redisService.delete(lFightingInfo.getFightId()+"-2");
						redisService.delete(lFightingInfo.getFightId().toString());
						redisService.delete(info.getEntryCode());
					}else {
						//双方都查看过结果，则清空缓存
						if(answer1.getIsSee()==1 && answer2.getIsSee()==1) {
							redisService.delete(lFightingInfo.getFightId()+"-1");
							redisService.delete(lFightingInfo.getFightId()+"-2");
							redisService.delete(lFightingInfo.getFightId().toString());
							redisService.delete(info.getEntryCode());
							if(info.getFightingType()!=1) {
								randomMatchingUtil.deleteRandom(info.getInitiator(),info.getDefense());
							}
						}
					}
					if(answer1.getScore().intValue()>answer2.getScore().intValue()) {
						info.setWinner(info.getInitiator());
						info.setVictoryScore(answer1.getScore());
						info.setFailureScore(answer2.getScore());
						info.setState(4);
						mFightingInfoDao.update(info);
						data.put("enemyScore", answer2.getScore());
						data.put("isVictory", 1);//是否胜利1胜利2失败
						if(info.getFightingType()!=1) {
							int coin=info.getInitiatorCoin()+rand.nextInt(info.getDefenseCoin());
							randomSettlement(coin,info.getWinner(),2,info.getFightId());
						    data.put("reward", coin);
						    data.put("fightingType",2);
						}else {
							data.put("fightingType",1);
						}
						return data;
					}else if(answer1.getScore().intValue()==answer2.getScore().intValue()) {
						info.setVictoryScore(answer1.getScore());
						info.setFailureScore(answer2.getScore());
						info.setState(4);
						mFightingInfoDao.update(info);
						data.put("enemyScore", answer2.getScore());
						data.put("isVictory", 3);//是否胜利1胜利2失败3平局
						randomSettlement(info.getInitiatorCoin(),info.getInitiator(),3,info.getFightId());
						//data.put("reward", info.getInitiatorCoin());
						data.put("fightingType",info.getFightingType());
						return data;
					}else {
						if(info.getFightingType()==3) {
							info.setWinner(info.getDefense());
							info.setVictoryScore(answer2.getScore());
							info.setFailureScore(answer1.getScore());
							info.setState(4);
							mFightingInfoDao.update(info);
							taskPush(info.getInitiator());
						}
						data.put("enemyScore", answer2.getScore());
						data.put("isVictory", 2);//是否胜利1胜利2失败
						if(info.getFightingType()!=1) {
						    data.put("fightingType",2);
						}else {
							data.put("fightingType",1);
						}
						//双方都查看过结果，则清空缓存
						redisService.delete(lFightingInfo.getFightId()+"-1");
						redisService.delete(lFightingInfo.getFightId()+"-2");
						redisService.delete(lFightingInfo.getFightId().toString());
						redisService.delete(info.getEntryCode());
						if(info.getFightingType()!=1) {
							randomMatchingUtil.deleteRandom(info.getInitiator(),info.getDefense());
						}
						
						return data;
					}
				}else {
					if(lFightingInfo.getGetScoreTime()<=0) {
						MFightingInfo info=(MFightingInfo)redisService.getObject(lFightingInfo.getEntryCode());
						LFightingInfo rj=new LFightingInfo();//关机者提交答案
						rj.setUserId(info.getDefense());
						rj.setRole(2);
						rj.setIsCorrect(2);
						rj.setAnswerTime(10l);
						rj.setQuestionId(-1);
						rj.setAnswerId(-1);
						rj.setNum(answer1.getAnswerNum());
						rj.setFightId(lFightingInfo.getFightId());
						rj.setQuesScore(100);
						rj.setSetTime(lFightingInfo.getSetTime());
						rj.setEntryCode(lFightingInfo.getEntryCode());
						subResult(rj);
					}
					data.put("enemyScore", -1);
					return data;
				}
			}
		}else {
			if(answer2.getIsEnd()==0) {
				if(answer2.getAnswerNum().intValue()==answer1.getAnswerNum().intValue()) {
					lFightingInfo.setEnemyScore(answer1.getScore());
					data.put("enemyScore", answer1.getScore());
					return data;
				}else {
					if(lFightingInfo.getGetScoreTime()<=0) {
						MFightingInfo info=(MFightingInfo)redisService.getObject(lFightingInfo.getEntryCode());
						LFightingInfo rj=new LFightingInfo();//关机者提交答案
						rj.setUserId(info.getInitiator());
						rj.setRole(1);
						rj.setIsCorrect(2);
						rj.setAnswerTime(10l);
						rj.setQuestionId(-1);
						rj.setAnswerId(-1);
						rj.setNum(answer2.getAnswerNum());
						rj.setFightId(lFightingInfo.getFightId());
						rj.setQuesScore(100);
						rj.setSetTime(lFightingInfo.getSetTime());
						rj.setEntryCode(lFightingInfo.getEntryCode());
						subResult(rj);
						if(answer2.getAnswerNum()==info.getQuesNum()) {
							/*if(info.getFightingType()==3) {
								redisService.delete(lFightingInfo.getFightId()+"-1");
								redisService.delete(lFightingInfo.getFightId()+"-2");
								redisService.delete(lFightingInfo.getFightId().toString());
								redisService.delete(info.getEntryCode());
							}else {
								//双方都查看过结果，则清空缓存
								redisService.delete(lFightingInfo.getFightId()+"-1");
								redisService.delete(lFightingInfo.getFightId()+"-2");
								redisService.delete(lFightingInfo.getFightId().toString());
								redisService.delete(info.getEntryCode());*/
								if(info.getFightingType()!=1) {
									randomMatchingUtil.deleteRandom(info.getInitiator(),info.getDefense());
								}
							/*}*/
							if(answer2.getScore().intValue()>=answer1.getScore().intValue()) {
								info.setWinner(info.getDefense());
								info.setVictoryScore(answer2.getScore());
								info.setFailureScore(answer1.getScore());
								info.setState(4);
								mFightingInfoDao.update(info);
								data.put("enemyScore", answer1.getScore());
								data.put("isVictory", 1);//是否胜利1胜利2失败
								if(info.getFightingType()!=1) {
									int coin=info.getDefenseCoin()+rand.nextInt(info.getInitiatorCoin());
									randomSettlement(coin,info.getWinner(),2,info.getFightId());
								    data.put("reward", coin);
								    data.put("fightingType",2);
								}else {
									data.put("fightingType",1);
								}
								return data;
							}else {
								info.setWinner(info.getInitiator());
								info.setVictoryScore(answer1.getScore());
								info.setFailureScore(answer2.getScore());
								info.setState(4);
								mFightingInfoDao.update(info);
								data.put("enemyScore", answer1.getScore());
								data.put("isVictory", 2);//是否胜利1胜利2失败
								if(info.getFightingType()!=1) {
								    data.put("fightingType",2);
								}else {
									data.put("fightingType",1);
								}
								return data;
							}
						}
						data.put("enemyScore", answer1.getScore());
						return data;
					}else {
						data.put("enemyScore", -1);
						return data;
					}
				}
			}else {//对战结束
				if(answer1.getAnswerNum().intValue()==answer2.getAnswerNum().intValue()) {
					answer2.setIsSee(1);
					redisService.put(lFightingInfo.getFightId()+"-2", answer2,600);
					MFightingInfo info=(MFightingInfo)redisService.getObject(lFightingInfo.getEntryCode());
					//双方都查看过结果，则清空缓存
					if(answer1.getIsSee()==1 && answer2.getIsSee()==1) {
						redisService.delete(lFightingInfo.getFightId()+"-1");
						redisService.delete(lFightingInfo.getFightId()+"-2");
						redisService.delete(lFightingInfo.getFightId().toString());
						redisService.delete(info.getEntryCode());
						if(info.getFightingType()!=1) {
							randomMatchingUtil.deleteRandom(info.getInitiator(),info.getDefense());
						}
					}
					if(answer2.getScore().intValue()>answer1.getScore().intValue()) {
						info.setWinner(info.getDefense());
						info.setVictoryScore(answer2.getScore());
						info.setFailureScore(answer1.getScore());
						info.setState(4);
						mFightingInfoDao.update(info);
						data.put("enemyScore", answer1.getScore());
						data.put("isVictory", 1);//是否胜利1胜利2失败
						if(info.getFightingType()!=1) {
							int coin=info.getDefenseCoin()+rand.nextInt(info.getInitiatorCoin());
							randomSettlement(coin,info.getWinner(),2,info.getFightId());
						    data.put("reward", coin);
						    data.put("fightingType",2);
						}else {
							data.put("fightingType",1);
						}
						return data;
					}else if(answer1.getScore().intValue()==answer2.getScore().intValue()) {
						info.setVictoryScore(answer1.getScore());
						info.setFailureScore(answer2.getScore());
						info.setState(4);
						mFightingInfoDao.update(info);
						data.put("enemyScore", answer1.getScore());
						data.put("isVictory", 3);//是否胜利1胜利2失败3平局
						randomSettlement(info.getDefenseCoin(),info.getDefense(),3,info.getFightId());
						//data.put("reward", info.getInitiatorCoin());
						data.put("fightingType",info.getFightingType());
						return data;
					}else {
						data.put("enemyScore", answer1.getScore());
						data.put("isVictory", 2);//是否胜利1胜利2失败
						if(info.getFightingType()!=1) {
						    data.put("fightingType",2);
						}else {
							data.put("fightingType",1);
						}
						return data;
					}
				}else {
					if(lFightingInfo.getGetScoreTime()<=0) {
						MFightingInfo info=(MFightingInfo)redisService.getObject(lFightingInfo.getEntryCode());
						LFightingInfo rj=new LFightingInfo();//关机者提交答案
						rj.setUserId(info.getInitiator());
						rj.setRole(1);
						rj.setIsCorrect(2);
						rj.setAnswerTime(10l);
						rj.setQuestionId(-1);
						rj.setAnswerId(-1);
						rj.setNum(answer2.getAnswerNum());
						rj.setFightId(lFightingInfo.getFightId());
						rj.setQuesScore(100);
						rj.setSetTime(lFightingInfo.getSetTime());
						rj.setEntryCode(lFightingInfo.getEntryCode());
						subResult(rj);
					}
					data.put("enemyScore", -1);
					return data;
				}
			}
		}
	}

	@Override
	public Integer luckDraw(Integer fightId) {
		MFightingInfo info=mFightingInfoDao.selectOne(fightId);
		Random rand = new Random();
		Integer coin=0;
		LCoinChange coinChange=new LCoinChange();
		coinChange.setChangedType(1);
		coinChange.setFlowType(1);
		coinChange.setChangedTime(new Date().getTime());
		coinChange.setUserId(info.getWinner());
		if(info.getInitiator().equals(info.getWinner())) {
			coin=info.getInitiatorCoin()+rand.nextInt(info.getDefenseCoin());
		}else {
			coin=info.getDefenseCoin()+rand.nextInt(info.getInitiatorCoin());
		}
		Integer coins=coin;
		String res=new AWorker<String>() {
			@Override
			public String call(String userId) throws Exception {
				if(info.getIsReceive().intValue()!=2) {
					return "2";
				}
				mUserInfoDao.updatecCoin(coins.longValue(), info.getWinner());
				MUserInfo user=mUserInfoDao.selectOne(info.getWinner());
				coinChange.setAmount(coins.longValue());
				coinChange.setCoinBalance(user.getCoin());
				lCoinChangeDao.insert(coinChange);
				info.setIsReceive(1);
				mFightingInfoDao.update(info);
				return "1";
			}
		}.execute(info.getWinner(),"redis_lock_winner_receive");
		if("1".equals(res)) {
			rechargeRewardPush(info.getWinner(),coin.longValue(),2);
			//完成答题任务
			taskPush(info.getInitiator());
			taskPush(info.getDefense());	
		}
		return coin;
	}
	
	@Override
	public Integer partakeAnswer(AnswerUser answerUser) {
		if(StringUtil.isNullOrEmpty(answerUser.getCoin())) {
			return 4;//金币数超出范围
		}
		if(answerUser.getCoin().intValue()<ConstantUtil.QUESTION_MIN_500 || answerUser.getCoin().intValue()>ConstantUtil.QUESTION_MXI_5000) {
			return 4;//金币数超出范围
		}
		MUserInfo user=mUserInfoDao.selectOne(answerUser.getUserId());
		if(user.getCoin().intValue()<answerUser.getCoin().intValue()) {
			return 0;//金币不足无法参与
		}
		answerUser.setIsMatching(0);
/*		lock.lock();
		try {
			randomMatchingUtil.addAnswerUser(answerUser);
		}catch (Exception e) {
			// TODO: handle exception
		}finally {
			lock.unlock();
		}*/
		new AWorker<AnswerUser>() {
			@Override
			public String call(AnswerUser answerUser) throws Exception {
				randomMatchingUtil.addAnswerUser(answerUser);
				return "";
			}
		}.execute(answerUser,"redis_lock_partakeAnswer");
		
		return 1;
	}
	
	@Override
	public Map<String, Object> randomUser(String userId,Double time) {
		Map<String,Object> data=new HashMap<String,Object>();
		AnswerUser answerUser= null;
		/*AnswerUser answerUser=randomMatchingUtil.random(userId);*/
		/*lock.lock();
		try {
			answerUser=randomMatchingUtil.random(userId);
		}catch (Exception e) {
			// TODO: handle exception
		}finally {
			lock.unlock();
		}*/
		String answerUserStr = new AWorker<String>() {
			@Override
			public String call(String userId) throws Exception {
				AnswerUser answerUser= randomMatchingUtil.random(userId);
				return JSON.toJSONString(answerUser);
			}
		}.execute(userId,"redis_lock_partakeAnswer");
		answerUser = JSON.parseObject(answerUserStr,AnswerUser.class);
		if(StringUtil.isNullOrEmpty(answerUser)) {
			data.put("res", 0);
			return data;
		}
		if(answerUser.getIsMatching()==0) {
			if(time>=10) {//匹配超过10秒,则匹配人机对手
				//创建房间将房间放入缓存
				MFightingType type=mFightingTypeDao.selectOne(3);
				MFightingInfo info=new MFightingInfo();
				info.setFightingType(3);
				info.setState(1);
				info.setEntryCode(StringUtil.getRandomStr(10));
				info.setQuesNum(type.getQuestionNum());
				info.setFightingTime(new Date().getTime());
				info.setInitiator(answerUser.getUserId());
				info.setDefense("1");
				info.setInitiatorCoin(answerUser.getCoin());
				info.setDefenseCoin(0);
				mFightingInfoDao.save(info);
				redisService.put(info.getEntryCode(), info,600);
				//创建答题结果缓存
				startFighting(info.getEntryCode());
				
				MUserInfo user=mUserInfoDao.selectOne(answerUser.getUserId());
				MUserInfo user1=mUserInfoDao.selectOne("1");
				randomMatchingUtil.deleteRandomUser(answerUser.getUserId());
				data.put("enemyImg", user1.getProfile());//对方图像
				data.put("img", user.getProfile());//我方头像
				data.put("enemyMoile",user1.getMobile());//对方电话
				data.put("mobile", user.getMobile());//我方电话
				data.put("quesNum", info.getQuesNum());//答题数量
				data.put("fightId", info.getFightId());//对战id
				data.put("res", 1);//匹配到对手
				data.put("role", 1);//角色：1发起者2参与者
				data.put("entryCode", info.getEntryCode());//房间号
				return data;
			}else {
			   data.put("res", 0);//未匹配到对手
			   return data;	
			}
		}
		if(answerUser.getRole()==1) {
			startFighting(answerUser.getEntryCode());
			MFightingInfo info=(MFightingInfo)redisService.getObject(answerUser.getEntryCode());
			MUserInfo user1=mUserInfoDao.selectOne(info.getInitiator());//我方
			MUserInfo user=mUserInfoDao.selectOne(info.getDefense());
			data.put("enemyImg", user.getProfile());//对方头像
			data.put("img", user1.getProfile());//我方头像
			data.put("enemyMoile",user.getMobile());//对方电话
			data.put("mobile", user1.getMobile());//我方电话
			data.put("quesNum", info.getQuesNum());//答题数量
			data.put("fightId", info.getFightId());//对战id
			data.put("res", 1);//匹配到对手
			data.put("role", 1);//角色：1发起者2参与者
			data.put("entryCode", info.getEntryCode());//房间号
			return data;
		}
		MFightingInfo info=(MFightingInfo)redisService.getObject(answerUser.getEntryCode());
		MUserInfo user1=mUserInfoDao.selectOne(info.getInitiator());
		MUserInfo user=mUserInfoDao.selectOne(info.getDefense());//我方
		data.put("img", user.getProfile());//我方头像
		data.put("enemyImg", user1.getProfile());//对方头像
		data.put("enemyMoile",user1.getMobile());//对方电话
		data.put("mobile", user.getMobile());//我方电话
		data.put("quesNum", info.getQuesNum());//答题数量
		data.put("fightId", info.getFightId());//对战id
		data.put("res", 1);//匹配到对手
		data.put("role", 2);//角色：1发起者2参与者
		data.put("entryCode", info.getEntryCode());//房间号
		return data;
	}
	
	public LFightingInfo computeResult(MFightingInfo info,LFightingInfo lFightingInfo,String key) {
		//判断题目是否回答过
		AnswerResult answerResult=(AnswerResult)redisService.getObject(key);
		if(lFightingInfo.getNum().intValue()==answerResult.getAnswerNum().intValue()) {
			return null;
		}
		if(lFightingInfo.getAnswerId().intValue()==-1) {
			Double d=lFightingInfo.getAnswerTime()/1000.0;
			Double d1=Math.floor(d);
			Double d2=d%1;
			if(d2>0.5) {
				d1=d1+0.5;
			}
			Integer score=(int)((lFightingInfo.getSetTime()-d1)*lFightingInfo.getQuesScore()/lFightingInfo.getSetTime());
			lFightingInfo.setScore(score);
		}else {
			MFightingAnswer fightingAnswer=mFightingAnswerDao.selectOne(lFightingInfo.getAnswerId());
			//计算分数
			if(fightingAnswer.getIsCorrect()==1) {
				Double d=lFightingInfo.getAnswerTime()/1000.0;
				Double d1=Math.floor(d);
				Double d2=d%1;
				if(d2>0.5) {
					d1=d1+0.5;
				}
				Integer score=(int)((lFightingInfo.getSetTime()-d1)*lFightingInfo.getQuesScore()/lFightingInfo.getSetTime());
				lFightingInfo.setScore(score);
			}else {
				lFightingInfo.setScore(0);
			}
		}
		
		if(!(info.getFightingType()==3 && lFightingInfo.getRole()==2)) {
			lFightingInfoDao.save(lFightingInfo);	
		}
		//修改缓存中发起者答题分数统计
		answerResult.setScore(answerResult.getScore()+lFightingInfo.getScore());
		answerResult.setAnswerNum(answerResult.getAnswerNum()+1);
		answerResult.setTime(new Date().getTime());
		if(lFightingInfo.getNum().intValue()==info.getQuesNum().intValue()) {
			answerResult.setIsEnd(1);
		}
		redisService.put(key, answerResult,600);
		lFightingInfo.setScore(answerResult.getScore());
		return lFightingInfo;
	}
	/**
	 * 查看房间是否存在
	 * @param entryCode
	 * @return
	 */
	@Override
	public String getNotRoom(String entryCode) {
		MFightingInfo info=(MFightingInfo)redisService.getObject(entryCode);
		if(StringUtil.isNullOrEmpty(info)) {
			return "3";//房间不存在
		}
		return "1";
	}
    /**
     * 随机匹配，结算金币
     * @param coin
     * @param userId
     */
	public void randomSettlement(Integer coin,String userId,Integer type,Integer fightId) {
		String res = new AWorker<String>() {
			@Override
			public String call(String userId) throws Exception {
				MFightingInfo info=mFightingInfoDao.selectOne(fightId);
				if(info.getIsReceive().intValue()==1) {
					return "2";//奖励已领取
				}
				LCoinChange coinChange=new LCoinChange();
				coinChange.setChangedType(1);
				coinChange.setFlowType(1);
				coinChange.setChangedTime(new Date().getTime());
				coinChange.setUserId(userId);
				coinChange.setAmount(coin.longValue());
				mUserInfoDao.updatecCoin(coin.longValue(), userId);
				MUserInfo user=mUserInfoDao.selectOne(userId);
				coinChange.setCoinBalance(user.getCoin());
				lCoinChangeDao.insert(coinChange);
				info.setIsReceive(1);
				mFightingInfoDao.update(info);
				return "1";
			}
		}.execute(userId,"redis_lock_randomSettlement");
		if("1".equals(res)) {
			rechargeRewardPush(userId,coin.longValue(),type);
			if(type!=3) {
				MFightingInfo info=mFightingInfoDao.selectOne(fightId);
				//完成答题任务
				taskPush(info.getInitiator());
				taskPush(info.getDefense());
			}else {
				taskPush(userId);	
			}	
		}
	}

	@Override
	public Integer cancelMatching(String userId) {
		/*lock.lock();
		try {
		   randomMatchingUtil.deleteRandomUser(userId);
		}catch (Exception e) {
  			// TODO: handle exception
  		}finally {
  			lock.unlock();
  		}*/
		new AWorker<String>() {
			@Override
			public String call(String userId) throws Exception {
				randomMatchingUtil.deleteRandomUser(userId);
				return "";
			}
		}.execute(userId,"redis_lock_partakeAnswer");
		return 1;
	}
	
	//答题金币变动，推送消息
	public void rechargeRewardPush(String userId,Long num,int type){
		AppNewsInform appNewsInform=new AppNewsInform();
		appNewsInform.setUserId(userId);
		appNewsInform.setInformTitle(InformConstant.ANSWER_TITLE);
		if(type==1) {//答题消耗
			appNewsInform.setInformContent(InformConstant.ANSWER_CONSUME_CONTENT+ "-"+num+"金币");	
		}else if(type==2) {//答题胜利
			appNewsInform.setInformContent(InformConstant.ANSWER_VICTORY_CONTENT+ "+"+num+"金币");	
		}else {//答题平局
			appNewsInform.setInformContent(InformConstant.ANSWER_DRAW_CONTENT+"+"+num+"金币");	
		}
		appNewsInform.setPushMode(InformConstant.PUSH_MODE_AURORA);
		appNewsInformService.addPush(appNewsInform);
	}
	@Transactional
	public void taskPush(String userId) {
		//完成任务获得奖励
		LUserTask lUserTask=lUserTaskDao.selectOne(userId, 5);
		if(StringUtil.isNullOrEmpty(lUserTask)) {
			int num=mFightingInfoDao.selectUserCount(userId);
			if(num>=5) {
				Map<String,Object> data=new HashMap<>();
				data.put("userId", userId);
				data.put("task", 5);
				jmsProducer.userTask(Type.USER_TASK, data);
				/*MTaskInfo mTaskInfo=mTaskInfoDao.selectOne(5);
				date=mPassbookService.taskUsePassbook(userId, mTaskInfo.getTaskType());;
				long multiple=(long)date.get("res");
				LUserTask userTask=new LUserTask();
				userTask.setUserId(userId);
				userTask.setTaskId(5);
				userTask.setCreateTime(new Date().getTime());
				lUserTaskDao.insert(userTask);
				if(mTaskInfo.getRewardUnit()==1) {//金币奖励
					long amount=mTaskInfo.getReward()+mTaskInfo.getReward()*multiple;
					LCoinChange change=new LCoinChange();
					change.setChangedType(ConstantUtil.COIN_CHANGED_TYPE_10);
					change.setFlowType(1);
					change.setChangedTime(new Date().getTime());
					change.setUserId(userId);
					change.setAmount(amount);
					mUserInfoDao.updatecCoin(amount, userId);
					change.setCoinBalance(user.getCoin()+amount);
					lCoinChangeDao.insert(change);
					AppNewsInform appNewsInform=new AppNewsInform();
					appNewsInform.setUserId(userId);
					appNewsInform.setInformTitle(InformConstant.COMPLETE_TASK_TITLE);
					appNewsInform.setInformContent(mTaskInfo.getRemark()+"+"+amount+"金币");	
					appNewsInform.setPushMode(InformConstant.PUSH_MODE_AURORA);
					appNewsInformService.addPush(appNewsInform);
				}else if(mTaskInfo.getRewardUnit()==2){//金猪奖励
					long amount=mTaskInfo.getReward()+mTaskInfo.getReward()*multiple;
					LPigChange pig=new LPigChange();
				    pig.setUserId(userId);
				    pig.setAmount(amount);
				    pig.setFlowType(1);
				    pig.setChangedType(ConstantUtil.PIG_CHANGED_TYPE_3);
				    pig.setChangedTime(new Date().getTime());
				    lPigChangeDao.insert(pig);
				    mUserInfoDao.updatecPigAdd(amount, userId);
					AppNewsInform appNewsInform=new AppNewsInform();
					appNewsInform.setUserId(userId);
					appNewsInform.setInformTitle(InformConstant.COMPLETE_TASK_TITLE);
					appNewsInform.setInformContent(mTaskInfo.getRemark()+"+"+amount+"金猪");	
					appNewsInform.setPushMode(InformConstant.PUSH_MODE_AURORA);
					appNewsInformService.addPush(appNewsInform);
				}
				mPassbookService.passbookOverdue((int)date.get("fbrspassbookId"));*/
			}
		}
	}
}

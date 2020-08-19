package com.mc.bzly.service.jms;

import java.util.Date;

import javax.jms.Destination;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.mc.bzly.model.jms.JmsWrapper;
import com.mc.bzly.model.jms.JmsWrapper.Type;


@Service("producerServiceImpl")  
public class JMSProducer {  
    @Autowired
    private JmsTemplate  jmsTemplate;
	
    // 发送消息，destination是发送到的队列，message是待发送的消息  
	public void sendMessage(Type type,Object data){ 
		Destination destination = new ActiveMQQueue("bz.queue");
		JmsWrapper jmsWrapper = new JmsWrapper(data, type,new Date().getTime());
		String jmsWrapperJson = JSON.toJSONString(jmsWrapper);
		jmsTemplate.convertAndSend(destination, jmsWrapperJson); 
	} 
	//用户提现
	public void userCash(Type type,Object data){ 
		Destination destination = new ActiveMQQueue("bz.queue.cash");
		JmsWrapper jmsWrapper = new JmsWrapper(data, type,new Date().getTime());
		String jmsWrapperJson = JSON.toJSONString(jmsWrapper);
		jmsTemplate.convertAndSend(destination, jmsWrapperJson); 
	}

	//游戏回调
	public void gameCallback(Type type,Object data){ 
		Destination destination = new ActiveMQQueue("bz.queue.game");
		JmsWrapper jmsWrapper = new JmsWrapper(data, type,new Date().getTime());
		String jmsWrapperJson = JSON.toJSONString(jmsWrapper);
		jmsTemplate.convertAndSend(destination, jmsWrapperJson); 
	}
	//每日签到
	public void daySign(Type type,Object data){ 
		Destination destination = new ActiveMQQueue("bz.queue.sign");
		JmsWrapper jmsWrapper = new JmsWrapper(data, type,new Date().getTime());
		String jmsWrapperJson = JSON.toJSONString(jmsWrapper);
		jmsTemplate.convertAndSend(destination, jmsWrapperJson); 
	}
	
	//每日领取vip活跃金
	public void dayActive(Type type,Object data){ 
		Destination destination = new ActiveMQQueue("bz.queue.active");
		JmsWrapper jmsWrapper = new JmsWrapper(data, type,new Date().getTime());
		String jmsWrapperJson = JSON.toJSONString(jmsWrapper);
		jmsTemplate.convertAndSend(destination, jmsWrapperJson); 
	}
	//用户完成系统任务
	public void userTask(Type type,Object data){ 
		Destination destination = new ActiveMQQueue("bz.queue.task");
		JmsWrapper jmsWrapper = new JmsWrapper(data, type,new Date().getTime());
		String jmsWrapperJson = JSON.toJSONString(jmsWrapper);
		jmsTemplate.convertAndSend(destination, jmsWrapperJson); 
	}
	
	//兑换金猪
	public void coinExchangePig(Type type,Object data){ 
		Destination destination = new ActiveMQQueue("bz.queue.coinExchangePig");
		JmsWrapper jmsWrapper = new JmsWrapper(data, type,new Date().getTime());
		String jmsWrapperJson = JSON.toJSONString(jmsWrapper);
		jmsTemplate.convertAndSend(destination, jmsWrapperJson); 
	}
	
	//领取新手任务奖励
	public void userReceiveTask(Type type,Object data){ 
		Destination destination = new ActiveMQQueue("bz.queue.userReceiveTask");
		JmsWrapper jmsWrapper = new JmsWrapper(data, type,new Date().getTime());
		String jmsWrapperJson = JSON.toJSONString(jmsWrapper);
		jmsTemplate.convertAndSend(destination, jmsWrapperJson); 
	}
	//用户兑换奖品
	public void userExchangeGoods(Type type,Object data){ 
		Destination destination = new ActiveMQQueue("bz.queue.userExchangeGoods");
		JmsWrapper jmsWrapper = new JmsWrapper(data, type,new Date().getTime());
		String jmsWrapperJson = JSON.toJSONString(jmsWrapper);
		jmsTemplate.convertAndSend(destination, jmsWrapperJson); 
	}
	//后台审核提现
	public void cashExamine(Type type,Object data){ 
		Destination destination = new ActiveMQQueue("bz.queue.cashExamine");
		JmsWrapper jmsWrapper = new JmsWrapper(data, type,new Date().getTime());
		String jmsWrapperJson = JSON.toJSONString(jmsWrapper);
		jmsTemplate.convertAndSend(destination, jmsWrapperJson); 
	}
	//领取金猪救济金
	public void reliefPig(Type type,Object data){ 
		Destination destination = new ActiveMQQueue("bz.queue.reliefPig");
		JmsWrapper jmsWrapper = new JmsWrapper(data, type,new Date().getTime());
		String jmsWrapperJson = JSON.toJSONString(jmsWrapper);
		jmsTemplate.convertAndSend(destination, jmsWrapperJson); 
	}
	//阅盟奖励发放
	public void ymCallback(Type type,Object data){ 
		Destination destination = new ActiveMQQueue("bz.queue.ymCallback");
		JmsWrapper jmsWrapper = new JmsWrapper(data, type,new Date().getTime());
		String jmsWrapperJson = JSON.toJSONString(jmsWrapper);
		jmsTemplate.convertAndSend(destination, jmsWrapperJson); 
	}
	//需要完成任务的提现
	public void cashTask(Type type,Object data){ 
		Destination destination = new ActiveMQQueue("bz.queue.cashTask");
		JmsWrapper jmsWrapper = new JmsWrapper(data, type,new Date().getTime());
		String jmsWrapperJson = JSON.toJSONString(jmsWrapper);
		jmsTemplate.convertAndSend(destination, jmsWrapperJson); 
	}
	//每日红包任务奖励
	public void signTask(Type type,Object data){ 
		Destination destination = new ActiveMQQueue("bz.queue.signTask");
		JmsWrapper jmsWrapper = new JmsWrapper(data, type,new Date().getTime());
		String jmsWrapperJson = JSON.toJSONString(jmsWrapper);
		jmsTemplate.convertAndSend(destination, jmsWrapperJson); 
	}
	//视频广告奖励
	public void videoTask(Type type,Object data){ 
		Destination destination = new ActiveMQQueue("bz.queue.videoTask");
		JmsWrapper jmsWrapper = new JmsWrapper(data, type,new Date().getTime());
		String jmsWrapperJson = JSON.toJSONString(jmsWrapper);
		jmsTemplate.convertAndSend(destination, jmsWrapperJson); 
	}
	// 达人推荐奖励
	public void darenRecommend(Type type,Object data){ 
		Destination destination = new ActiveMQQueue("bz.queue.darenRecommend");
		JmsWrapper jmsWrapper = new JmsWrapper(data, type,new Date().getTime());
		String jmsWrapperJson = JSON.toJSONString(jmsWrapper);
		jmsTemplate.convertAndSend(destination, jmsWrapperJson); 
	}
	//砸金蛋
	public void smashEggs(Type type,Object data){ 
		Destination destination = new ActiveMQQueue("bz.queue.smashEggs");
		JmsWrapper jmsWrapper = new JmsWrapper(data, type,new Date().getTime());
		String jmsWrapperJson = JSON.toJSONString(jmsWrapper);
		jmsTemplate.convertAndSend(destination, jmsWrapperJson); 
	}
	//使用金蛋
	public void activationCard(Type type,Object data){ 
		Destination destination = new ActiveMQQueue("bz.queue.activationCard");
		JmsWrapper jmsWrapper = new JmsWrapper(data, type,new Date().getTime());
		String jmsWrapperJson = JSON.toJSONString(jmsWrapper);
		jmsTemplate.convertAndSend(destination, jmsWrapperJson); 
	}
	
	//参加早起打卡
	public void enroll(Type type,Object data){ 
		Destination destination = new ActiveMQQueue("bz.queue.enroll");
		JmsWrapper jmsWrapper = new JmsWrapper(data, type,new Date().getTime());
		String jmsWrapperJson = JSON.toJSONString(jmsWrapper);
		jmsTemplate.convertAndSend(destination, jmsWrapperJson); 
	}
	//修改卡密
	public void modifyCardPassword(Type type,Object data){ 
		Destination destination = new ActiveMQQueue("bz.queue.modifyCardPassword");
		JmsWrapper jmsWrapper = new JmsWrapper(data, type,new Date().getTime());
		String jmsWrapperJson = JSON.toJSONString(jmsWrapper);
		jmsTemplate.convertAndSend(destination, jmsWrapperJson); 
	}
	
	//心愿猪app用户提现
	public void wishUserCash(Type type,Object data){ 
		Destination destination = new ActiveMQQueue("bz.queue.wish.cash");
		JmsWrapper jmsWrapper = new JmsWrapper(data, type,new Date().getTime());
		String jmsWrapperJson = JSON.toJSONString(jmsWrapper);
		jmsTemplate.convertAndSend(destination, jmsWrapperJson); 
	}
	
	//心愿猪app师徒关系
	public void wishReceiveQueue(Type type,Object data){ 
		Destination destination = new ActiveMQQueue("bz.queue.wishReceiveQueue");
		JmsWrapper jmsWrapper = new JmsWrapper(data, type,new Date().getTime());
		String jmsWrapperJson = JSON.toJSONString(jmsWrapper);
		jmsTemplate.convertAndSend(destination, jmsWrapperJson); 
	}
	// 高额赚VIP奖励
	public void highVipReward(Type highVipReward, String userId) {
		Destination destination = new ActiveMQQueue("bz.queue.high_vip_reward");
		JmsWrapper jmsWrapper = new JmsWrapper(userId, highVipReward,new Date().getTime());
		String jmsWrapperJson = JSON.toJSONString(jmsWrapper);
		jmsTemplate.convertAndSend(destination, jmsWrapperJson); 
	}
} 

package com.mc.bzly.impl.pay;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.bzly.common.constant.ConstantUtil;
import com.bzly.common.constant.InformConstant;
import com.bzly.common.pay.PayUtil;
import com.bzly.common.pay.WeiXinPrePay;
import com.bzly.common.utils.Base64;
import com.bzly.common.utils.DateUtil;
import com.bzly.common.utils.DateUtils;
import com.mc.bzly.base.RespCodeState;
import com.mc.bzly.base.Result;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.egg.EUserGoldEggDao;
import com.mc.bzly.dao.passbook.MPassbookDao;
import com.mc.bzly.dao.passbook.MVipInfoDao;
import com.mc.bzly.dao.pay.PPayLogDao;
import com.mc.bzly.dao.platform.PDictionaryDao;
import com.mc.bzly.dao.task.MTaskInfoDao;
import com.mc.bzly.dao.thirdparty.MChannelConfigDao;
import com.mc.bzly.dao.thirdparty.MChannelConfigUserDao;
import com.mc.bzly.dao.thirdparty.MChannelInfoDao;
import com.mc.bzly.dao.thirdparty.MFissionSchemeDao;
import com.mc.bzly.dao.user.LBalanceChangeDao;
import com.mc.bzly.dao.user.LCoinChangeDao;
import com.mc.bzly.dao.user.LPigChangeDao;
import com.mc.bzly.dao.user.LUserTaskDao;
import com.mc.bzly.dao.user.LUserVipDao;
import com.mc.bzly.dao.user.MUserApprenticeDao;
import com.mc.bzly.dao.user.MUserInfoDao;
import com.mc.bzly.dao.user.MUserVipReferrerLogDao;
import com.mc.bzly.dao.user.RSUserPassbookDao;
import com.mc.bzly.impl.redis.AWorker;
import com.mc.bzly.model.egg.EUserGoldEgg;
import com.mc.bzly.model.jms.JmsWrapper.Type;
import com.mc.bzly.model.news.AppNewsInform;
import com.mc.bzly.model.passbook.MPassbook;
import com.mc.bzly.model.passbook.MVipInfo;
import com.mc.bzly.model.pay.PPayLog;
import com.mc.bzly.model.task.MTaskInfo;
import com.mc.bzly.model.thirdparty.MChannelConfig;
import com.mc.bzly.model.thirdparty.MChannelConfigUser;
import com.mc.bzly.model.thirdparty.MChannelInfo;
import com.mc.bzly.model.thirdparty.MFissionScheme;
import com.mc.bzly.model.user.LBalanceChange;
import com.mc.bzly.model.user.LCoinChange;
import com.mc.bzly.model.user.LPigChange;
import com.mc.bzly.model.user.LUserTask;
import com.mc.bzly.model.user.LUserVip;
import com.mc.bzly.model.user.MUserApprentice;
import com.mc.bzly.model.user.MUserInfo;
import com.mc.bzly.model.user.MUserVipReferrerLog;
import com.mc.bzly.model.user.RSUserPassbook;
import com.mc.bzly.service.jms.JMSProducer;
import com.mc.bzly.service.news.AppNewsInformService;
import com.mc.bzly.service.passbook.MPassbookService;
import com.mc.bzly.service.pay.PayService;
import com.mc.bzly.util.StringUtil;

@Service(interfaceClass=PayService.class,version=WebConfig.dubboServiceVersion)
public class PayServiceImpl implements PayService{
    @Autowired
	private RSUserPassbookDao rSUserPassbookDao;
    @Autowired
	private MPassbookDao mPassbookDao;
    @Autowired
    private PPayLogDao pPayLogDao;
    @Autowired
    private LUserVipDao lUserVipDao;
    @Autowired
    private MVipInfoDao mVipInfoDao;
    @Autowired
	private MUserApprenticeDao mUserApprenticeDao;
    @Autowired
	private PDictionaryDao pDictionaryDao;
    @Autowired
	private LCoinChangeDao lCoinChangeDao;
    @Autowired
    private LPigChangeDao lPigChangeDao;
    @Autowired
	private MUserInfoDao mUserInfoDao;
    @Autowired
	private LUserTaskDao lUserTaskDao;
	@Autowired
	private MTaskInfoDao mTaskInfoDao;
	@Autowired
	private LBalanceChangeDao lBalanceChangeDao;
	@Autowired
	private AppNewsInformService appNewsInformService;
	@Autowired
	private MPassbookService mPassbookService;
	@Autowired
	private MUserVipReferrerLogDao mUserVipReferrerLogDao;
	@Autowired
	private MChannelConfigUserDao mChannelConfigUserDao;
	@Autowired
	private MChannelConfigDao mChannelConfigDao;
    @Autowired
	private MFissionSchemeDao mFissionSchemeDao;
    @Autowired
    private MChannelInfoDao mChannelInfoDao;
    @Autowired
    private EUserGoldEggDao eUserGoldEggDao;
    @Autowired
    private JMSProducer jmsProducer;
	@Override
	public Result trade(PPayLog pPayLog) {
		//token descripte price couponId openId payPurpose vipId
		Result result = new Result();
		BigDecimal bigDecimal = new BigDecimal("0");
		if(pPayLog.getPrice().compareTo(bigDecimal)==-1) {
			result.setSuccess(true);
			result.setStatusCode(RespCodeState.PLACE_ORDER_FAIL.getStatusCode());
			result.setMessage(RespCodeState.PLACE_ORDER_FAIL.getMessage());
			return result;//0下单失败
		}
		MVipInfo vipInfo=mVipInfoDao.selectOne(pPayLog.getVipId());
		if(pPayLog.getPayPurpose()==1) {
			int count=lUserVipDao.selectVip(pPayLog.getUserId(), pPayLog.getVipId());
			if(count>0) {
				result.setSuccess(true);
				result.setStatusCode(RespCodeState.VIP_REPEAT.getStatusCode());
				result.setMessage(RespCodeState.VIP_REPEAT.getMessage());
				return result;//不可重复购买
			}else {
				if(vipInfo.getPrice().compareTo(pPayLog.getPrice())!=0) {
					result.setSuccess(true);
					result.setStatusCode(RespCodeState.PLACE_ORDER_FAIL.getStatusCode());
					result.setMessage(RespCodeState.PLACE_ORDER_FAIL.getMessage());
					return result;
				}
			}
		}
		int noPay=pPayLogDao.selectNoPay(pPayLog.getUserId());
		if(noPay>0) {
			result.setSuccess(true);
			result.setStatusCode(RespCodeState.NO_PAY.getStatusCode());
			result.setMessage(RespCodeState.NO_PAY.getMessage());
			return result;//你有订单正在处理
		}
		MUserInfo user=mUserInfoDao.selectOne(pPayLog.getUserId());
		String channelCode=StringUtil.isNullOrEmpty(user.getChannelCode())?user.getParentChannelCode():user.getChannelCode();
		if(StringUtil.isNullOrEmpty(channelCode)) {
			channelCode="baozhu";
		}
		BigDecimal price=pPayLog.getPrice();
		if("zhongqing".equals(channelCode) && vipInfo.getVipType().intValue()==2) {
			if(pPayLog.getCouponId()!=0) {
				result.setSuccess(true);
				result.setStatusCode(RespCodeState.NO_PAY.getStatusCode());
				result.setMessage(RespCodeState.NO_PAY.getMessage());
				return result;//无法使用优惠券
			}
			if(vipInfo.getReturnVip().intValue()!=-1) {
				MVipInfo userVip=mVipInfoDao.selectZqUserInfo(user.getUserId(), vipInfo.getReturnVip());
				price=price.subtract(userVip.getPrice());
			}
			/*List<MVipInfo> userVips=mVipInfoDao.selectZqVipUser(user.getUserId());
			for(MVipInfo v:userVips) {
				if(v.getOrderId().intValue()>=vipInfo.getOrderId()) {
					result.setSuccess(true);
					result.setStatusCode(RespCodeState.VIP_LV_MIN.getStatusCode());
					result.setMessage(RespCodeState.VIP_LV_MIN.getMessage());
					return result;//已购买vip等级大于该vip,无法购买
				}
				if(vipInfo.getReturnVip().intValue()==v.getId()) {
					price=price.subtract(v.getPrice());
				}
			}*/
			
		}
		if(pPayLog.getIsBalance()==2) {
			BigDecimal aPrc=price.subtract(user.getBalance());
			if(aPrc.compareTo(BigDecimal.ZERO) <= 0) {//余额全部抵扣直接购买成功
				pPayLog.setActualPrice(new BigDecimal("0"));
				pPayLog.setBalance(pPayLog.getPrice());
				String outTradeNo = Base64.getOutTradeNo();
				pPayLog.setOutTradeNo(outTradeNo);
				pPayLog.setState(1);
				pPayLog.setCreatorTime(new Date().getTime());
				pPayLog.setPayMode("3");
				pPayLogDao.insert(pPayLog);
				paySuccess(pPayLog.getOutTradeNo());
				changeBalance(pPayLog.getUserId(),pPayLog.getBalance());
				result.setData("1");
				result.setSuccess(true);
				result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
				result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
				return result;
			}else {
				pPayLog.setActualPrice(aPrc);
				pPayLog.setBalance(user.getBalance());
			}
		}else {
			pPayLog.setBalance(new BigDecimal("0"));
			pPayLog.setActualPrice(price);
		}
		
		if(pPayLog.getCouponId()!=0) {//使用优惠券
			RSUserPassbook rs=rSUserPassbookDao.selectOne(pPayLog.getCouponId());
			if(!StringUtil.isNullOrEmpty(rs)) {
				if(rs.getStatus()==1) {
					MPassbook mp=mPassbookDao.selectOne(rs.getPassbookId());
					BigDecimal decimal = new BigDecimal(mp.getPassbookValue().doubleValue()/100.0);
					pPayLog.setActualPrice(pPayLog.getActualPrice().multiply(decimal));
				}
			}
		}
		
		String outTradeNo = Base64.getOutTradeNo();
		pPayLog.setOutTradeNo(outTradeNo);
		pPayLog.setState(1);
		pPayLog.setCreatorTime(new Date().getTime());
		pPayLog.setChannelCode("baozhu");
		pPayLogDao.insert(pPayLog);
		
		result.setData(outTradeNo);
		result.setSuccess(true);
		result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
		result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
		return result;
	}
	@Override
	public Map<String,Object> tradeTame(PPayLog pPayLog) {
		Map<String,Object> data=new HashMap<String,Object>();
		data.put("res", 1);
		BigDecimal bigDecimal = new BigDecimal("0");
		if(pPayLog.getPrice().compareTo(bigDecimal)==-1) {
			data.put("res", 0);
			return data;//0下单失败
		}
		int noPay=pPayLogDao.selectNoPay(pPayLog.getUserId());
		if(noPay>0) {
			data.put("res", 4);
			return data;//你有订单正在处理
		}
		MUserInfo mUserInfo=mUserInfoDao.selectOne(pPayLog.getUserId());
		if(mUserInfo.getRoleType()==4) {
			data.put("res", 5);
			return data;//达人不能购买团队长
		}
		String channelCode = "";
		if(!StringUtil.isNullOrEmpty(mUserInfo.getChannelCode())) {
			channelCode=mUserInfo.getChannelCode();
		}
		if(!StringUtil.isNullOrEmpty(mUserInfo.getParentChannelCode()) && StringUtil.isNullOrEmpty(channelCode)) {
			channelCode=mUserInfo.getParentChannelCode();
		}
		if(StringUtil.isNullOrEmpty(channelCode)) {
			channelCode="baozhu";
		}
		MFissionScheme mFissionScheme=null;
	    if("baozhu".equals(channelCode)) {
	    	mFissionScheme=mFissionSchemeDao.selectSchemeByChannelCode(channelCode);
	    }else {
	    	MChannelConfig channelConfig=mChannelConfigDao.selectByChannelCode(channelCode);
			if(!StringUtil.isNullOrEmpty(channelConfig) && channelConfig.getChargeMode()==2) {
				mFissionScheme=mFissionSchemeDao.selectSchemeByChannelCode(channelCode);
				if(StringUtil.isNullOrEmpty(mFissionScheme)) {
					channelCode="baozhu";
					mFissionScheme=mFissionSchemeDao.selectSchemeByChannelCode(channelCode);
				}
			}else {
				channelCode="baozhu";
				mFissionScheme=mFissionSchemeDao.selectSchemeByChannelCode(channelCode);
			}
	    }
		pPayLog.setChannelCode(channelCode);
		if(mUserInfo.getRoleType()==1) {
			if(new Date().getTime()<mUserInfo.getXqEndTime()) {
				pPayLog.setPrice(mFissionScheme.getRenewPrice());
				pPayLog.setActualPrice(mFissionScheme.getRenewPrice());
				pPayLog.setPayPurpose(3);
			}else {
				pPayLog.setPrice(mFissionScheme.getTeamPrice());
				pPayLog.setActualPrice(mFissionScheme.getTeamPrice());
				pPayLog.setPayPurpose(2);
			}
		}else {
			pPayLog.setPrice(mFissionScheme.getRenewPrice());
			pPayLog.setActualPrice(mFissionScheme.getRenewPrice());
			pPayLog.setPayPurpose(3);
		}
		String outTradeNo = Base64.getOutTradeNo();
		pPayLog.setOutTradeNo(outTradeNo);
		pPayLog.setState(1);
		pPayLog.setCreatorTime(new Date().getTime());
		pPayLog.setBalance(bigDecimal);
		pPayLogDao.insert(pPayLog);
		data.put("outTradeNo", outTradeNo);
		data.put("price", pPayLog.getActualPrice());
		data.put("effectiveDay", mFissionScheme.getEffectiveDay());
		data.put("payPurpose", pPayLog.getPayPurpose());
		return data;
	}
	@Override
	public Map<String, Object> payType(String outTradeNo, String payMode,String wxCallback) {
		Map<String,Object> data=new HashMap<String,Object>();
		PPayLog payLog=pPayLogDao.selectOne(outTradeNo);
		if("1".equals(payMode)) {//支付宝支付
			MChannelInfo ChannelInfo=mChannelInfoDao.selectByChannelCode(payLog.getChannelCode());
			if(ChannelInfo.getOpenAli().intValue()!=1) {
				ChannelInfo=mChannelInfoDao.selectByChannelCode("baozhu");
				payLog.setChannelCode("baozhu");
			}
			AlipayTradeAppPayResponse aliPay=PayUtil.aliPay(outTradeNo, payLog.getActualPrice(), payLog.getDescripte(),ChannelInfo.getAliAppId(),ChannelInfo.getAliPrivateKey(),ChannelInfo.getAliPublicKey());
			if(!StringUtil.isNullOrEmpty(aliPay)) {
				payLog.setPayMode("1");
				pPayLogDao.update(payLog);
				data.put("res", aliPay);
				data.put("msg", 1);//1下单成功
			}else {
				data.put("msg", 0);//1下单失败
			}
		}else {//微信支付
			MChannelInfo ChannelInfo=mChannelInfoDao.selectByChannelCode(payLog.getChannelCode());
			if(ChannelInfo.getOpenWx().intValue()!=1) {
				ChannelInfo=mChannelInfoDao.selectByChannelCode("baozhu");
				payLog.setChannelCode("baozhu");
			}
			WeiXinPrePay wx=PayUtil.wxpay(payLog.getActualPrice(), outTradeNo, payLog.getDescripte(), payLog.getPayType(), payLog.getPayIp(),ChannelInfo.getWxAppId(),ChannelInfo.getMchId(),ChannelInfo.getApiKey(),wxCallback);
			if(!StringUtil.isNullOrEmpty(wx)) {
				payLog.setPayMode("2");
				pPayLogDao.update(payLog);
				data.put("res", wx);
				data.put("msg", 1);//1下单成功
			}else {
				data.put("msg", 0);//1下单失败
			}
		}
		return data;
	}

	@Override
	public Integer payAppSuccess(String outTradeNo) {
		PPayLog payLog=pPayLogDao.selectOne(outTradeNo);
		if(StringUtil.isNullOrEmpty(payLog)) {
			return 0;//订单不存在
		}
		PPayLog pay=new PPayLog();
		pay.setOutTradeNo(outTradeNo);
		pay.setWebState(1);
		pPayLogDao.update(pay);
		changeBalance(payLog.getUserId(),payLog.getBalance());
		return 1;//成功
	}

	@Override
	public Integer cancelPay(String outTradeNo) {
		PPayLog payLog=new PPayLog();
		payLog.setOutTradeNo(outTradeNo);
		payLog.setCancelTime(new Date().getTime());
		payLog.setState(3);
		pPayLogDao.update(payLog);
		return 1;
	}

	@Override
	public void paySuccess(String outTradeNo) {
		PPayLog payLog=pPayLogDao.selectOne(outTradeNo);
		if(StringUtil.isNullOrEmpty(payLog)) {
			return;
		}
		String res=new AWorker<String>() {
			@Override
			public String call(String userId) throws Exception {
				if(payLog.getState().intValue()==2) {
					return "2";
				}
				payLog.setPayTime(new Date().getTime());
				payLog.setState(2);
				pPayLogDao.update(payLog);
				return "1";
			}
		}.execute(payLog.getUserId(),"redis_lock_vip_callback");
		if("2".equals(res)) {
			return;
		}
		if(payLog.getPayPurpose()==1) {
			MVipInfo vipInfo=mVipInfoDao.selectOne(payLog.getVipId());
			LUserVip lUserVip=new LUserVip();
			lUserVip.setUserId(payLog.getUserId());
			lUserVip.setVipId(payLog.getVipId());
			lUserVip.setSurplusDay(vipInfo.getUseDay()-1);
			lUserVip.setExpireTime(DateUtil.getAddDay(new Date(),vipInfo.getUseDay().longValue(), DateUtils.SIMPLE_DATE));
			lUserVip.setCreateTime(new Date().getTime());
			lUserVip.setStatus(1);
			lUserVipDao.insert(lUserVip);//添加vip记录
			if(payLog.getCouponId()!=0) {
				if(payLog.getActualPrice().compareTo(BigDecimal.ZERO) > 0) {
					RSUserPassbook rs=new RSUserPassbook();  
					rs.setId(payLog.getCouponId());
					rs.setStatus(2);
					rSUserPassbookDao.update(rs);//修改优惠券使用记录		
				}
			}
			//充值vip奖励
			rechargeReward(vipInfo,payLog.getUserId());
			//购买一次vip奖励
			taskPush(payLog.getUserId(),9,payLog.getPrice().doubleValue());
			//购买两次vip奖励
			//taskPush(payLog.getUserId(),10,0.0);
			//徒弟累计充值金额，师傅获取奖励
			MUserApprentice mUserApprentice=mUserApprenticeDao.selectUserId(payLog.getUserId());
			if(!StringUtil.isNullOrEmpty(mUserApprentice)) {
				int sumPrice=pPayLogDao.selectSumRecharge(payLog.getUserId());
				MUserInfo user=mUserInfoDao.selectOne(mUserApprentice.getReferrerId());
				String channelCode = user.getChannelCode() == null?user.getParentChannelCode():user.getChannelCode();
				MChannelConfigUser mChannelConfigUser=mChannelConfigUserDao.selectUserChannelCode(user.getRoleType() > 1?2:1,channelCode == null?"baozhu":channelCode);
				MUserVipReferrerLog mUserVipReferrerLog=mUserVipReferrerLogDao.selectOne(payLog.getUserId(), mUserApprentice.getReferrerId());
				int rank=0;
				if(!StringUtil.isNullOrEmpty(mUserVipReferrerLog)) {
					rank=mUserVipReferrerLog.getRank();
				}
				long coin=0;
				if(sumPrice>=ConstantUtil.VIP_18 && rank<1) {
					coin=ConstantUtil.VIP_18*10000*mChannelConfigUser.getVip18()/100;
					rechargeRewardUpdate1(user.getUserId(),coin,ConstantUtil.COIN_CHANGED_TYPE_5,1);
					MUserVipReferrerLog log=new MUserVipReferrerLog(payLog.getUserId(),user.getUserId(),1,new Date().getTime());
					mUserVipReferrerLogDao.insert(log);
				}
				if(sumPrice>=ConstantUtil.VIP_48 && rank<2) {
					coin=ConstantUtil.VIP_48*10000*mChannelConfigUser.getVip48()/100;
					rechargeRewardUpdate1(user.getUserId(),coin,ConstantUtil.COIN_CHANGED_TYPE_5,1);
					MUserVipReferrerLog log=new MUserVipReferrerLog(payLog.getUserId(),user.getUserId(),2,new Date().getTime());
					mUserVipReferrerLogDao.insert(log);
				}
				if(sumPrice>=ConstantUtil.VIP_228 && rank<3) {
					coin=ConstantUtil.VIP_228*10000*mChannelConfigUser.getVip228()/100;
					rechargeRewardUpdate1(user.getUserId(),coin,ConstantUtil.COIN_CHANGED_TYPE_5,1);
					MUserVipReferrerLog log=new MUserVipReferrerLog(payLog.getUserId(),user.getUserId(),3,new Date().getTime());
					mUserVipReferrerLogDao.insert(log);
				}
				if(sumPrice>=ConstantUtil.VIP_1188 && rank<4) {
					coin=ConstantUtil.VIP_1188*10000*mChannelConfigUser.getVip1188()/100;
					rechargeRewardUpdate1(user.getUserId(),coin,ConstantUtil.COIN_CHANGED_TYPE_5,1);
					MUserVipReferrerLog log=new MUserVipReferrerLog(payLog.getUserId(),user.getUserId(),4,new Date().getTime());
					mUserVipReferrerLogDao.insert(log);
				}
				if(sumPrice>=ConstantUtil.VIP_1688 && rank<5) {
					coin=ConstantUtil.VIP_1688*10000*mChannelConfigUser.getVip1688()/100;
					rechargeRewardUpdate1(user.getUserId(),coin,ConstantUtil.COIN_CHANGED_TYPE_5,1);
					MUserVipReferrerLog log=new MUserVipReferrerLog(payLog.getUserId(),user.getUserId(),5,new Date().getTime());
					mUserVipReferrerLogDao.insert(log);
				}
				if(sumPrice>=ConstantUtil.VIP_1888 && rank<6) {
					coin=ConstantUtil.VIP_1888*10000*mChannelConfigUser.getVip1888()/100;
					rechargeRewardUpdate1(user.getUserId(),coin,ConstantUtil.COIN_CHANGED_TYPE_5,1);
					MUserVipReferrerLog log=new MUserVipReferrerLog(payLog.getUserId(),user.getUserId(),6,new Date().getTime());
					mUserVipReferrerLogDao.insert(log);
				}
				if(sumPrice>=ConstantUtil.VIP_3188 && rank<7) {
					coin=ConstantUtil.VIP_3188*10000*mChannelConfigUser.getVip3188()/100;
					rechargeRewardUpdate1(user.getUserId(),coin,ConstantUtil.COIN_CHANGED_TYPE_5,1);
					MUserVipReferrerLog log=new MUserVipReferrerLog(payLog.getUserId(),user.getUserId(),7,new Date().getTime());
					mUserVipReferrerLogDao.insert(log);
				}
				// 高额赚VIP奖励，河南分销模式,传入师傅id
				if(vipInfo.getHighVip().intValue() == 2){
					jmsProducer.highVipReward(Type.HIGH_VIP_REWARD,user.getUserId());
				}
			}
		}
	}
	@Override
	public Map<String, Object> queryPageList(PPayLog pPayLog) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		result.put("res", "1");
		if(!StringUtil.isNullOrEmpty(pPayLog.getAccountId())) {
			if(StringUtil.isNullOrEmpty(mUserInfoDao.selectByAccountId(pPayLog.getAccountId()))) {
				result.put("res", "2");
				return result;
			}
		}
		
		pPayLog.setPageIndex(pPayLog.getPageSize() * (pPayLog.getPageNum() - 1));
		List<PPayLog> pPayLogList = pPayLogDao.selectPageList(pPayLog);
		
		pPayLog.setPageIndex(pPayLog.getPageSize() * (pPayLog.getPageNum() - 1));
		Map<String,String> subMap=pPayLogDao.selectSum(pPayLog);
		Map<String,String> countPriceMap=pPayLogDao.selectCountPrice(pPayLog);
		Map<String, String> countMap= pPayLogDao.selectCount(pPayLog);
		result.put("list", pPayLogList); 
		result.put("total", countMap.get("cou"));
		result.put("subTotalPrice", subMap.get("sumParice"));
		result.put("pageTotal", subMap.get("cou"));
		result.put("count", countPriceMap.get("cou"));
		result.put("totalPrice", countPriceMap.get("totalPrice"));
		return result;
	}
	@Override
	public PPayLog selectInfo(Integer id) {
		return pPayLogDao.selectInfo(id);
	}
	//充值奖励
	public void rechargeReward(MVipInfo mVipInfo,String userId){
		int count=lUserVipDao.selectVip(userId, mVipInfo.getId());
		String num="";//奖励数量
		String unit="";//奖励单位
		//首充续充奖励
		if(count==0) {
			if(mVipInfo.getContinueRewardUnit()==1) {//奖励金币
			  rechargeRewardUpdate(userId,mVipInfo.getFirstReward(),ConstantUtil.COIN_CHANGED_TYPE_6,1,mVipInfo.getName());
			  num=mVipInfo.getFirstReward().toString();
			  unit="金币";
			}else {//奖励金猪
			  rechargeRewardUpdate(userId,mVipInfo.getContinueReward(),ConstantUtil.PIG_CHANGED_TYPE_1,2,mVipInfo.getName());
			  num=mVipInfo.getFirstReward().toString();
			  unit="金猪";
			}
		}else {
			if(mVipInfo.getContinueRewardUnit()==1) {
			  rechargeRewardUpdate(userId,mVipInfo.getContinueReward(),ConstantUtil.COIN_CHANGED_TYPE_6,1,mVipInfo.getName());	
			  num=mVipInfo.getContinueReward().toString();
			  unit="金币";
			}else {
			  rechargeRewardUpdate(userId,mVipInfo.getContinueReward(),ConstantUtil.PIG_CHANGED_TYPE_1,2,mVipInfo.getName());
			  num=mVipInfo.getContinueReward().toString();
			  unit="金猪";
			}
		}
		//推送奖励消息
		rechargeRewardPush(userId,num,unit);
	}
	
	public void rechargeRewardUpdate(String userId,Long coinNum,int type,int unit,String vipName){
		if(coinNum > 0L){
			MUserInfo user=mUserInfoDao.selectOne(userId);
			if(unit==1) {
				LCoinChange coin=new LCoinChange();
				coin.setUserId(userId);
				coin.setAmount(coinNum);
				coin.setFlowType(1);
				coin.setChangedType(type);     
				coin.setChangedTime(new Date().getTime());
				coin.setCoinBalance(user.getCoin()+coinNum);
				coin.setRemarks(vipName);
				lCoinChangeDao.insert(coin);
				mUserInfoDao.updatecCoin(coinNum, userId);
			}else {
			    LPigChange pig=new LPigChange();
			    pig.setUserId(userId);
			    pig.setAmount(coinNum);
			    pig.setFlowType(1);
			    pig.setChangedType(type);
			    pig.setChangedTime(new Date().getTime());
			    pig.setPigBalance(user.getPigCoin()+coinNum);
			    pig.setRemarks(vipName);
			    lPigChangeDao.insert(pig);
			    mUserInfoDao.updatecPigAdd(coinNum, userId);
			}
		}
	}
	
	public void rechargeRewardUpdate1(String userId,Long coinNum,int type,int unit){
		MUserInfo user=mUserInfoDao.selectOne(userId);
		if(unit==1) {
			LCoinChange coin=new LCoinChange();
			coin.setUserId(userId);
			coin.setAmount(coinNum);
			coin.setFlowType(1);
			coin.setChangedType(type);     
			coin.setChangedTime(new Date().getTime());
			coin.setCoinBalance(user.getCoin()+coinNum);
			lCoinChangeDao.insert(coin);
			mUserInfoDao.updatecCoin(coinNum, userId);
		}else {
		    LPigChange pig=new LPigChange();
		    pig.setUserId(userId);
		    pig.setAmount(coinNum);
		    pig.setFlowType(1);
		    pig.setChangedType(type);
		    pig.setChangedTime(new Date().getTime());
		    pig.setPigBalance(user.getPigCoin()+coinNum);
		    lPigChangeDao.insert(pig);
		    mUserInfoDao.updatecPigAdd(coinNum, userId);
		}
	}
	
	//充值奖励消息推送
	public void rechargeRewardPush(String userId,String num,String unit){
		AppNewsInform appNewsInform=new AppNewsInform();
		appNewsInform.setUserId(userId);
		appNewsInform.setInformTitle(InformConstant.RECHARGE_VIP_TITLE);
		appNewsInform.setInformContent(String.format(InformConstant.ONE_VIP_CONTENT, num,unit));
		appNewsInform.setPushMode(InformConstant.PUSH_MODE_AURORA);
		appNewsInformService.addPush(appNewsInform);
	}
	//完成任务
	@Transactional
	public void taskPush(String userId,Integer task,Double pice) {
		Map<String,Object> date=new HashMap<>();
		//完成任务获得奖励
		LUserTask lUserTask=lUserTaskDao.selectOne(userId, task);
		if(StringUtil.isNullOrEmpty(lUserTask)) {
			if(task==10) {
				List<Integer> list=lUserVipDao.selectVipUserNum(userId);
				if(list.size()>=2) {
					MVipInfo vipInfo=null;
					for(Integer vipId:list) {
						vipInfo=mVipInfoDao.selectOne(vipId);
						pice=pice+vipInfo.getPrice().doubleValue();
					}	
				}else {
					return;
				}
			}
			MTaskInfo mTaskInfo=mTaskInfoDao.selectOne(task);
			LUserTask userTask=new LUserTask();
			userTask.setUserId(userId);
			userTask.setTaskId(task);
			userTask.setCreateTime(new Date().getTime());
			lUserTaskDao.insert(userTask);
			date=mPassbookService.taskUsePassbook(userId, mTaskInfo.getTaskType());;
			long multiple=(long)date.get("res");
		    Double reward=Math.floor(pice*ConstantUtil.MONEY_COIN*mTaskInfo.getReward()/100);
		    reward=reward+reward*multiple;
		    MUserInfo user=mUserInfoDao.selectOne(userId);
		    if(mTaskInfo.getRewardUnit()==3) {//金币奖励
				LCoinChange change=new LCoinChange();
				change.setChangedType(ConstantUtil.COIN_CHANGED_TYPE_10);
				change.setFlowType(1);
				change.setChangedTime(new Date().getTime());
				change.setUserId(userId);
				change.setAmount(reward.longValue());
				change.setCoinBalance(user.getCoin()+reward.longValue());
				mUserInfoDao.updatecCoin(reward.longValue(), userId);
				lCoinChangeDao.insert(change);
				AppNewsInform appNewsInform=new AppNewsInform();
				appNewsInform.setUserId(userId);
				appNewsInform.setInformTitle(InformConstant.COMPLETE_TASK_TITLE);
				appNewsInform.setInformContent(mTaskInfo.getTaskName()+"+"+reward+"金币");	
				appNewsInform.setPushMode(InformConstant.PUSH_MODE_AURORA);
				appNewsInformService.addPush(appNewsInform);
			}else if(mTaskInfo.getRewardUnit()==4){//金猪奖励
				LPigChange pig=new LPigChange();
			    pig.setUserId(userId);
			    pig.setAmount(reward.longValue());
			    pig.setFlowType(1);
			    pig.setChangedType(ConstantUtil.PIG_CHANGED_TYPE_3);
			    pig.setChangedTime(new Date().getTime());
			    pig.setPigBalance(user.getPigCoin()+reward.longValue());
			    lPigChangeDao.insert(pig);
			    mUserInfoDao.updatecPigAdd(reward.longValue(), userId);
				AppNewsInform appNewsInform=new AppNewsInform();
				appNewsInform.setUserId(userId);
				appNewsInform.setInformTitle(InformConstant.COMPLETE_TASK_TITLE);
				appNewsInform.setInformContent(mTaskInfo.getTaskName()+"+"+reward+"金猪");	
				appNewsInform.setPushMode(InformConstant.PUSH_MODE_AURORA);
				appNewsInformService.addPush(appNewsInform);
			}
		    mPassbookService.passbookOverdue((int)date.get("fbrspassbookId"));
	   }
	}
	//用户余额变更
	public void changeBalance(String userId,BigDecimal balance) {
		mUserInfoDao.updatecBalanceReduce(balance, userId);
		LBalanceChange ban=new LBalanceChange();
		ban.setUserId(userId);
		ban.setAmount(balance.doubleValue());
		ban.setFlowType(2);
		ban.setChangedType(ConstantUtil.BALANCE_CHANGED_TYPE_6);
		ban.setChangedTime(new Date().getTime());
		ban.setIsAuditing(0);
		lBalanceChangeDao.insert(ban);
	}
	@Override
	public List<PPayLog> selectExcl(PPayLog pPayLog) {
		return pPayLogDao.selectExcl(pPayLog);
	}
	@Override
	public PPayLog selectOne(String outTradeNo) {
		return pPayLogDao.selectOne(outTradeNo);
	}
}

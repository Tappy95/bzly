package com.mc.bzly.impl.lottery;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.bzly.common.constant.ConstantUtil;
import com.bzly.common.constant.InformConstant;
import com.mc.bzly.base.RespCodeState;
import com.mc.bzly.base.Result;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.lottery.MLotteryGoodsDao;
import com.mc.bzly.dao.lottery.MLotteryOrderDao;
import com.mc.bzly.dao.lottery.MLotteryTypeDao;
import com.mc.bzly.dao.user.LPigChangeDao;
import com.mc.bzly.dao.user.MUserAddressDao;
import com.mc.bzly.dao.user.MUserInfoDao;
import com.mc.bzly.impl.redis.AWorker;
import com.mc.bzly.model.jms.JmsWrapper.Type;
import com.mc.bzly.model.lottery.MLotteryGoods;
import com.mc.bzly.model.lottery.MLotteryOrder;
import com.mc.bzly.model.lottery.MLotteryType;
import com.mc.bzly.model.news.AppNewsInform;
import com.mc.bzly.model.user.LPigChange;
import com.mc.bzly.model.user.MUserAddress;
import com.mc.bzly.model.user.MUserInfo;
import com.mc.bzly.service.jms.JMSProducer;
import com.mc.bzly.service.lottery.MLotteryOrderService;
import com.mc.bzly.service.news.AppNewsInformService;
import com.mc.bzly.util.PartitionAmountUtil;
import com.mc.bzly.util.StringUtil;

@Service(interfaceClass = MLotteryOrderService.class,version = WebConfig.dubboServiceVersion)
public class MLotteryOrderServiceImpl implements MLotteryOrderService {

	@Autowired 
	private MLotteryOrderDao mLotteryOrderDao;
	@Autowired
	private MUserInfoDao mUserInfoDao;
	@Autowired
	private MLotteryTypeDao mLotteryTypeDao;
	@Autowired
	private LPigChangeDao lPigChangeDao;
	@Autowired
	private MLotteryGoodsDao mLotteryGoodsDao;
	@Autowired
	private MUserAddressDao mUserAddressDao;
	@Autowired
	private AppNewsInformService appNewsInformService;
	@Autowired
	private JMSProducer jmsProducer;
	
	@Transactional
	@Override
	public Result clickLottery(MLotteryOrder mLotteryOrder,String password) throws Exception {
		Result result = new Result();
		int count=mLotteryOrderDao.selectOrderTime(mLotteryOrder.getUserId());
		if(count >= 1){
			result.setStatusCode(RespCodeState.USER_LOTTERY_LIMIT.getStatusCode());
			result.setMessage(RespCodeState.USER_LOTTERY_LIMIT.getMessage());
			return result;
		}
		long onedayMillisecond =  24*60*60*1000l; // 这个l必须加，否则可能超出long类型的取值范围
		long totayMillisecond = mLotteryOrder.getCreateTime() - (mLotteryOrder.getCreateTime() % onedayMillisecond);
		MUserInfo mUserInfo = mUserInfoDao.selectOne(mLotteryOrder.getUserId());
		MLotteryType mLotteryType = mLotteryTypeDao.selectOne(mLotteryOrder.getTypeId());
		// 判断账户余额
		if(mUserInfo.getPigCoin() < mLotteryType.getExpendPigCoin()){
			result.setStatusCode(RespCodeState.USER_PIG_COIN_NOT_ENOUGH.getStatusCode());
			result.setMessage(RespCodeState.USER_PIG_COIN_NOT_ENOUGH.getMessage());
			return result;
		}
		// 判断用户今日抽奖次数
		long userTodayCount  =mLotteryOrderDao.userTodayCount(mLotteryOrder.getUserId(), totayMillisecond,mLotteryOrder.getTypeId());
		if(userTodayCount >= mLotteryType.getTimesOneday()){
			result.setStatusCode(RespCodeState.USER_LOTTERY_LIMIT.getStatusCode());
			result.setMessage(RespCodeState.USER_LOTTERY_LIMIT.getMessage());
			return result;
		}
		// 判断当前类型今日抽奖次数
		long typeTodayCount  =mLotteryOrderDao.typeTodayCount(mLotteryOrder.getTypeId(), totayMillisecond);
		if(typeTodayCount >= mLotteryType.getDayNum()){
			result.setStatusCode(RespCodeState.TODAY_LOTTERY_LIMIT.getStatusCode());
			result.setMessage(RespCodeState.TODAY_LOTTERY_LIMIT.getMessage());
			return result;
		}
		if(!password.equals(mUserInfo.getPayPassword())) {
			result.setStatusCode(RespCodeState.PAY_PASSWORD_ERROR.getStatusCode());
			result.setMessage(RespCodeState.PAY_PASSWORD_ERROR.getMessage());
			return result;
		}
		// 随机选中奖品
		List<MLotteryGoods> MLotteryGoods = mLotteryGoodsDao.selectGoodsList(mLotteryOrder.getTypeId());
		int index = PartitionAmountUtil.getLotteryOrderIndex(MLotteryGoods.size());
		MLotteryGoods.get(index).setRate(null);
		mLotteryOrder.setAccountId(mUserInfo.getAccountId());
		mLotteryOrder.setGoodsId(MLotteryGoods.get(index).getId());
		mLotteryOrder.setExpendPigCoin(mLotteryType.getExpendPigCoin());
		mLotteryOrder.setPrice(MLotteryGoods.get(index).getPrice());
		// 插入订单表
		mLotteryOrderDao.insert(mLotteryOrder);
		// 修改账户金猪余额
		mUserInfo.setPigCoin(mUserInfo.getPigCoin() - mLotteryType.getExpendPigCoin());
		mUserInfo.setUpdateTime(mLotteryOrder.getCreateTime());
		mUserInfoDao.update(mUserInfo);
		// 新增金猪修改记录
		LPigChange lPigChange = new LPigChange(mUserInfo.getUserId(), mLotteryType.getExpendPigCoin(), 
					ConstantUtil.FLOWTYPE_OUT, ConstantUtil.PIG_CHANGED_TYPE_8, mLotteryOrder.getCreateTime(),"金猪抽奖",mUserInfo.getPigCoin());
		lPigChangeDao.insert(lPigChange);
		
		Map<String,Object> data=new HashMap<>();
		data.put("userId", mUserInfo.getUserId());
		data.put("task", 23);
		jmsProducer.userTask(Type.USER_TASK, data);
		
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("goods", MLotteryGoods.get(index));
		resultMap.put("recordId", mLotteryOrder.getId());
		result.setData(resultMap);
		result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
		result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
		return result;
	}

	@Transactional
	@Override
	public int modify(MLotteryOrder mLotteryOrder) throws Exception {
		MLotteryOrder order=mLotteryOrderDao.selectIdOne(mLotteryOrder.getId());
		if(order.getStatus()==4) {
			if(mLotteryOrder.getStatus()!=4) {
				return 2;
			}
		}
		String res=new AWorker<String>() {
			@Override
			public String call(String userId) throws Exception {
				if(mLotteryOrder.getStatus()==2) {
					MUserInfo user=mUserInfoDao.selectOne(mLotteryOrder.getUserId());
					mUserInfoDao.updatecPigAdd(mLotteryOrder.getExpendPigCoin(),mLotteryOrder.getUserId());
					LPigChange pig=new LPigChange();
				    pig.setUserId(mLotteryOrder.getUserId());
				    pig.setAmount(mLotteryOrder.getExpendPigCoin());
				    pig.setFlowType(1);
				    pig.setChangedType(ConstantUtil.PIG_CHANGED_TYPE_9);
				    pig.setChangedTime(new Date().getTime());
				    pig.setPigBalance(user.getPigCoin()+mLotteryOrder.getExpendPigCoin());
				    lPigChangeDao.insert(pig);
				    MLotteryType mLotteryType=mLotteryTypeDao.selectOne(mLotteryOrder.getTypeId());
				    if(mLotteryType.getLotterySort().intValue()==1) {
				    	mLotteryGoodsDao.updateNumberReduce(mLotteryOrder.getGoodsId());
				    }
					 //发送抽奖拒绝消息
					AppNewsInform appNewsInform=new AppNewsInform();
					appNewsInform.setUserId(mLotteryOrder.getUserId());
					appNewsInform.setInformTitle(InformConstant.PUSH_TITLE_LOTTER);
					appNewsInform.setInformContent(InformConstant.PUSH_TITLE_LOTTER_REFUSE);
					appNewsInform.setPushMode(InformConstant.PUSH_MODE_AURORA);
					appNewsInformService.addPush(appNewsInform);
				}
				mLotteryOrder.setUpdateTime(new Date().getTime());
				mLotteryOrderDao.update(mLotteryOrder);
				//发送抽奖已发货消息
				if(mLotteryOrder.getStatus()==4) {
					AppNewsInform appNewsInform=new AppNewsInform();
					appNewsInform.setUserId(mLotteryOrder.getUserId());
					appNewsInform.setInformTitle(InformConstant.PUSH_TITLE_LOTTER);
					appNewsInform.setInformContent(InformConstant.PUSH_TITLE_LOTTER_SUCCESS);
					appNewsInform.setPushMode(InformConstant.PUSH_MODE_AURORA);
					appNewsInformService.addPush(appNewsInform);
				}
				return "1";
			}
		}.execute(mLotteryOrder.getUserId(),"redis_lock_order_examine");
		
		return 1;
	}

	@Override
	public Map<String,Object> queryOne(Integer id) {
		return mLotteryOrderDao.selectOne(id);
	}

	@Override
	public Map<String, Object> queryList(MLotteryOrder mLotteryOrder) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("res", "1");
		if(!StringUtil.isNullOrEmpty(mLotteryOrder.getAccountId())) {
			if(StringUtil.isNullOrEmpty(mUserInfoDao.selectByAccountId(mLotteryOrder.getAccountId()))) {
				result.put("res", "2");
				return result;
			}
		}
		mLotteryOrder.setPageIndex(mLotteryOrder.getPageSize() * (mLotteryOrder.getPageNum() - 1));
		List<MLotteryOrder> mLotteryOrderList = mLotteryOrderDao.selectList(mLotteryOrder);
		Map<String,String> subTotalMap=mLotteryOrderDao.selectSum(mLotteryOrder);
		Map<String,String> totalMap=mLotteryOrderDao.selectCountSum(mLotteryOrder);
		String total = mLotteryOrderDao.selectCount(mLotteryOrder);
		
		result.put("list", mLotteryOrderList);
		result.put("subTotalPrice", subTotalMap.get("subTotalPrice"));
		result.put("pageCount", subTotalMap.get("pageCount"));
		result.put("totalPrice", totalMap.get("totalPrice"));
		result.put("totalPriceCount", totalMap.get("totalPageCount"));
		result.put("total", total);
		
		return result;
	}

	@Override
	public List<Map<String, Object>> orderNews() {
		return mLotteryOrderDao.selectNews();
	}

	@Override
	public Map<String, Object> appQueryOne(Integer id) {
		Map<String,Object> data=mLotteryOrderDao.selectOne(id);
		/*if("2".equals(data.get("lotterySort").toString())) {
			data.put("expressNumbers", "");
		}*/
		data.put("isAddress", 1);
		if(StringUtil.isNullOrEmpty(data.get("addressId"))) {
			data.put("isAddress", 2);
			data.put("addressId", "");
			data.put("receiver", "");
			data.put("mobile", "");
			data.put("detailAddress", "");
		}
		if(StringUtil.isNullOrEmpty(data.get("remarks"))) {
			data.put("remarks", "");
		}
		return data;
	}

	@Override
	public Map<String, Object> getCardPassword(String payPassword,String userId,Integer id) {
		Map<String,Object> data=new HashMap<>();
		data.put("res", 1);//成功
		MUserInfo user=mUserInfoDao.selectOne(userId);
		if(payPassword.equals(user.getPayPassword())) {
		   MLotteryOrder mLotteryOrder=mLotteryOrderDao.selectIdOne(id);
		   data.put("expressNumbers", mLotteryOrder.getExpressNumbers());
		}else {
			data.put("res", 2);//支付密码错误
		}
		return data;
	}

	@Override
	public Long userOrderCount(Integer typeId,String userId) {
		long onedayMillisecond =  24*60*60*1000l; // 这个l必须加，否则可能超出long类型的取值范围
		long todayMillisecond = new Date().getTime();
		todayMillisecond = todayMillisecond - todayMillisecond % onedayMillisecond;
		// 用户今日抽奖次数
		return mLotteryOrderDao.userTodayCount(userId, todayMillisecond,typeId);
	}

	@Override
	public int updateLocking(MLotteryOrder mLotteryOrder) {
		MLotteryOrder order=mLotteryOrderDao.selectOrder(mLotteryOrder.getId());
		if(mLotteryOrder.getLockingMobile().equals(order.getLockingMobile())) {
			return 2;
		}
		mLotteryOrder.setIsLocking(1);
		return mLotteryOrderDao.updateLocking(mLotteryOrder);
	}

	@Override
	public int updateLockingList(String ids, String admin) {
		String[] idArr=ids.split(",");
		List<MLotteryOrder> mLotteryOrderList=new ArrayList<>();
		MLotteryOrder lotteryOrder=null;
		MLotteryOrder order=null;
		for(int i=0;i<idArr.length;i++) {
			order=mLotteryOrderDao.selectOrder(Integer.parseInt(idArr[i]));
			if(!StringUtil.isNullOrEmpty(order.getLockingMobile()) && !admin.equals(order.getLockingMobile())) {
				/*return 2;//已被其他管理员锁定*/
				continue;
			}
			if(order.getStatus()==1) {
				lotteryOrder=new MLotteryOrder();
				lotteryOrder.setId(Integer.parseInt(idArr[i]));
				lotteryOrder.setLockingMobile(admin);
				mLotteryOrderList.add(lotteryOrder);	
			}
		}
		if(mLotteryOrderList.size()>0) {
			mLotteryOrderDao.updateLockingList(mLotteryOrderList);	
		}
		return 0;
	}

	@Override
	public int relieveLocking(Integer id, String admin) {
		MLotteryOrder order=mLotteryOrderDao.selectOrder(id);
		if(!admin.equals(order.getLockingMobile())) {
			return 2;//解除锁定失败
		}
		MLotteryOrder mLotteryOrder=new MLotteryOrder();
		mLotteryOrder.setLockingMobile("");
		mLotteryOrder.setIsLocking(2);
		mLotteryOrder.setId(id);
		return mLotteryOrderDao.updateLocking(mLotteryOrder);
	}

	@Override
	public List<MLotteryOrder> selectExcl(MLotteryOrder mLotteryOrder) {
		return mLotteryOrderDao.selectExcl(mLotteryOrder);
	}

	@Transactional
	@Override
	public Result userExchangeGoods(String userId, Integer id,String password) {
		Result result=new Result();
		int count=mLotteryOrderDao.selectOrderTime(userId);
		if(count >= 1){
			result.setStatusCode(RespCodeState.USER_LOTTERY_LIMIT.getStatusCode());
			result.setMessage(RespCodeState.USER_LOTTERY_LIMIT.getMessage());
			return result;
		}
		MUserInfo user=mUserInfoDao.selectOne(userId);
		if(!password.equals(user.getPayPassword())) {
			result.setStatusCode(RespCodeState.PAY_PASSWORD_ERROR.getStatusCode());
			result.setMessage(RespCodeState.PAY_PASSWORD_ERROR.getMessage());
			return result;
		}
		MLotteryGoods mLotteryGoods=mLotteryGoodsDao.selectOne(id);
		//商品数量不足
		if(mLotteryGoods.getGoodsNumber()-mLotteryGoods.getGoodsConsumeNumber()<=0) {
			result.setStatusCode(RespCodeState.GOODS_NUMBER_SHORT.getStatusCode());
			result.setMessage(RespCodeState.GOODS_NUMBER_SHORT.getMessage());
			return result;
		}
		if(mLotteryGoods.getPigCoin()>user.getPigCoin()) {
			result.setStatusCode(RespCodeState.USER_PIG_COIN_NOT_ENOUGH.getStatusCode());
			result.setMessage(RespCodeState.USER_PIG_COIN_NOT_ENOUGH.getMessage());
			return result;
		}
		Map<String,Object> jmsData=new HashMap<>();
		jmsData.put("id", id);
		jmsData.put("userId", userId);
		jmsProducer.userExchangeGoods(Type.USER_EXCHANGE_GOODS, jmsData);
		
		result.setSuccess(true);
		result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
		result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
		return result;
	}

	@Override
	public Result addressBinding(String userId,Integer orderId, Integer addressId) {
		Result result=new Result();
		MLotteryOrder mLotteryOrder=mLotteryOrderDao.selectUserGoods(orderId,userId);
		if(StringUtil.isNullOrEmpty(mLotteryOrder)) {
			result.setStatusCode(RespCodeState.CALLBACK_NOT_EXIST.getStatusCode());
			result.setMessage(RespCodeState.CALLBACK_NOT_EXIST.getMessage());
			return result;
		}
		if(mLotteryOrder.getStatus().intValue()!=1) {
			result.setStatusCode(RespCodeState.ORDER_NOT_STATUS_1.getStatusCode());
			result.setMessage(RespCodeState.ORDER_NOT_STATUS_1.getMessage());
			return result;
		}
		MUserAddress mUserAddress=mUserAddressDao.selectUserInfo(userId, addressId);
		if(StringUtil.isNullOrEmpty(mUserAddress)) {
			result.setStatusCode(RespCodeState.ADDRESS_NO.getStatusCode());
			result.setMessage(RespCodeState.ADDRESS_NO.getMessage());
			return result;
		}
		mLotteryOrder.setAddressId(addressId);
		mLotteryOrderDao.update(mLotteryOrder);
		result.setSuccess(true);
		result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
		result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
		return result;
	}

	@Override
	public Map<String, Object> appQueryList(MLotteryOrder mLotteryOrder) {
		Map<String, Object> result = new HashMap<String, Object>();
		mLotteryOrder.setPageIndex(mLotteryOrder.getPageSize() * (mLotteryOrder.getPageNum() - 1));
		List<MLotteryOrder> mLotteryOrderList = mLotteryOrderDao.selectList(mLotteryOrder);
		String total = mLotteryOrderDao.selectCount(mLotteryOrder);
		result.put("list", mLotteryOrderList);
		result.put("total", total);
		return result;
	}

}

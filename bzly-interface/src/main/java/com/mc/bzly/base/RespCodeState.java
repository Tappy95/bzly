package com.mc.bzly.base;

public enum RespCodeState {

	API_OPERATOR_SUCCESS("2000", "操作成功"), 
	ADMIN_LOGIN_SUCCESS("2001", "登陆成功"),
	USER_REGISTERED_SUCCESS("2002", "注册成功"),
	JOIN_ACTIVITY_SUCCESS("2003", "报名成功"),
	USER_CHECKIN_SUCCESS("2004", "打卡成功"),
	API_PICTURE_UPLOAD_SUCCESS("2005", "上传成功"),
	
	API_ERROE_CODE_3000("3000", "操作失败"),
	ADMIN_LOGIN_FAILURE("3001", "用户名或密码错误"),
	ADMIN_LOGIN_FORBIDDEN("3002", "管理员已被禁用,请联系客服"),
	USER_LOGIN_FORBIDDEN("3002", "帐号已被管理员禁用,请联系客服"),
	USER_REGISTERED_FAILURE("3003", "注册失败"),
	MOBILE_ALREADY_EXIST("3004", "手机用户已存在"),
	REFERRER_NOT_EXIST("3005", "推荐人不存在"),
	ACCOUNT_LOGIN_OTHER_EQUIPMENT("3006", "账号在其他设备登陆"),
	SIGN_STICK_TIME("3007","签到连续天数已存在"),
	SMS_CHECKED_ERROR("3008", "验证码错误"),
	USER_BALANCE_NOT_ENOUGH("3009", "账户余额不足"),
	ANSWER_NO("30010","答案未填写"),
	ROOM_NO("30011","房间不存在"),
	COIN_INSUFFICIENT("30012","金币不足无法参与"),
	NOT_YET_BEGUN("30013","答题未开始"),
	SUBNITTED("30014","答题已提交"),
	API_PICTURE_UPLOAD_FATLED("3011", "上传失败"),
	PLACE_ORDER_FAIL("30015", "下单失败"),
	VIP_REPEAT("30016", "不可重复购买"),
	ALI_NUM_EXIST("30017", "已绑定支付宝"),
	NUM_ERROR("30018", "账号错误"),
	USER_NO_VIP("30019", "用户没有开通该vip"),
	TODAY_RECEIVE("30020", "今日已领取过活跃金"),
	NO_PAY("30021", "你有订单正在处理"),
	EXCHANGE_10_MUL("30022", "兑换金额必须为10的倍数"),
	PAY_PASSWORD_ERROR("30023", "支付密码错误"),
	ROOM_MAX("30024", "房间人数已满"),
	REPEAT_RECEIVE("30025", "已领取过任务奖励"),
	NOT_COMPLETE("30026", "任务未完成"),
	OPENID_ERROR("30027", "openId获取失败"),
	WX_EXIST("30028", "已绑定微信"),
	EXCEED_CASH_NUM("30029", "超过今日提现次数"),
	ACCOUNT_ERROR("30030", "账号异常"),
	COIN_RANGE("30031","金币超出范围"),
	ALI_ACCOUNT_ERROR("30032","支付宝账号异常"),
	DAREN_NO_TAME("30033","达人不能购买团队长"),
	USER_NO_PIG("30034","用户不是小猪猪无法，不能设置为达人"),
	USER_YES_DAREN("30035","该用户已经是达人"),
	VIP_BUY("30036","会员才能提现"),
	CASH_QUOTA("30037","升级为高级会员提现无限制"),
	VIP_REWARD_EXPIRE("30038","vip奖励已过期"),
	VIP_LV_MIN("30038","已购买vip等级大于该vip,下单失败"),
	VIP_NO_COUPON("30039","该vip不能使用优惠券"),
	WX_ALREADY_BINDING("30040", "微信账号已被绑定"),
	
	HAS_JOIN_ACTIVITY("3012", "请勿重复参加活动"),
	NOT_IN_ACTIVITY_TIMING("3013", "超过报名时间"),
	NO_JOIN_ACTIVITY("3015", "您未参加活动"),

	NOT_AT_CHECKIN_TIME("3016", "未到打卡时间"),
	USER_CHECKIN_FAILURE("3017", "打卡失败"),
	USER_HAS_CHECKIN("3018", "请勿重复打卡"),

	MOBILE_NEED("3019", "请输入手机号"),
	MOBILE_NOT_EXIST("3020", "用户不存在"),
	REFFERRER_NOT_YOURSELF("3021", "推荐人不能为自己"),
	REFFERRER_NOT_USE("3024", "不能互为师徒"),
	SIGN_ERROR("3022", "验签失败"),
	WITHDRAWALS_LIMIT("3023", "提现金额过低"),
	
	ALREADY_LOCKING("3025", "记录已被其他管理员锁定"),
	RELIEVE_LOCKING("3026", "解除锁定失败"),
	
	USER_NO_PREMISSION("3104","请先登陆"),
	ADMIN_NO_PERMISSION("3105","暂无权限"),
	
	CHANNEL_CODE_EXISE("3106","渠道标识已存在"),
	OLD_PASSWORD_ERROR("3107","旧密码错误"),
	ORDER_EXISE("3108","排序值存在"),
	
	IP_IN_BLACKLIST("4101","当前IP地址已命中黑名单，如有疑问请联系客服QQ"),
	EQUIPMENT_CHANGED_ACCOUNT_FREQUENT("4102","同一设备不能频繁切换账号"),
	ACCOUNT_CHANGED_EQUIPMENT_FREQUENT("4103","新用户不能频繁更换登陆设备"),
	IP_REG_FREQUENT("4104","当前IP注册频繁，请稍后再试"),
	ONE_EQUIPMENT_ONE_USER("4105","同一个设备只可以注册一个账户"),
	
	READ_ZX_COIN_REWARD_LIMIT("5100","今日领取金币已达上限"),
	USER_LOTTERY_LIMIT("5101","今日抽奖次数已达上限"),
	TODAY_LOTTERY_LIMIT("5102","今日奖品已放完"),
	USER_PIG_COIN_NOT_ENOUGH("5103","金猪不足，抽不了奖哦～"),
	OPERATION_FREQUENTLY("5104","请勿频繁操作"),
	GOODS_NUMBER_SHORT("5105","商品数量不足"),
	ADDRESS_NO("5106","地址不存在"),
	ORDER_NOT_STATUS_1("5107","订单状态不在审核中"),
	GOODS_SHIPPED("5108","订单已发货，无法修改订单状态"),
	
	USER_ROLE_ERROR("6001","用户必须先成为团队长"),
	USER_STATUS_FREEZE("6002","用户已被冻结"),
	
	NOT_IN_ACTIVITY_TIMINT("7001","不在活动时间范围"),
	BQ_CARD_NOT_ENOUGH("7002","补签卡不足"),
	RECORD_NEED_NOT_ACTIVE("7003","改记录无需激活"),
	CAN_NOT_RECEIVE("7004","签到状态错误"),
	TASK_NOT_ENOUGH("7005","完成任务不足"),
	HAS_NO_ACTIVE_BEFORE("7006","请先完成之前的签到任务"),
	HAVE_NO_DATA("8001","暂无数据"),
	USER_COUNT_NOT_ENOUGH("9001","请选完成领取过的任务再开始新的任务"),
	TASK_COUNT_NOT_ENOUGH("9002","任务数量不足"),
	DATA_IS_EMPTY("9003","数据为空"),
	EXPIRE_TIME_OUT("9004","任务已超时"),
	TASK_CANNOT_SUBMIT("9005","任务状态不可提交"),
	TASK_HAS_FINISH("9006","请勿重复完成任务"),
	CONTENT_ILLEGAL("9007","提交内容不规范"),
	TASK_CHOICE("9008","请选择试用任务"),
	
	CALLBACK_NOT_EXIST("30021","订单不存在"),
	CALLBACK_HAS_FINISH("30022","订单已完成"),
	ORDER_EXPIRE("30023","订单已过期"),
	
	PIG_NO("30024","金猪余额不足"),
	SMASH_EGGS_NO("30025","今日砸金蛋次数不足"),
	CARD_NUMBER_NO("30026","卡号不存在"),
	CARD_USE("30027","激活卡已使用"),
	CARD_MODIFY_PASSWORD("30028","该激活卡已修改过密码"),
	CARD_NO_PASSWORD("30029","激活卡不存在或密码错误"),
	
	CASH_TASK_ERROR("50021","提现任务异常"),
	JJ_COUNT_NOT_ENOUGH("50022","每日领取次数不足"),
	ACCOUNT_ENOUGH("50023","金币或金猪数充足，把机会留给其他人吧"),
	
	DAREN_CASH_MIN_MONEY("50024","提现金额不能小于"),
	DAREN_NO_SCORE("50025","你号没有活跃度评分"),
	DAREN_DAY_CASH_MAX("50026","当天提现总金额超过"),
	PLAY_TIME_NOT_ENOUGH("50027","试玩时长不足"),
	
	TODAY_NO_CHECKIN("60001","今日未打卡，无法报名"),
	NOW_NO_CHECKIN("60002","现在不能报名"),
	
	NEED_BIND_MOBILE("7001","请先绑定手机号"),
	AUTH_ERROR("7002","授权失败"),
	XW_BIND_OTHER_MOBILE("7003","该手机号已被其他微信绑定"),
	
	
	VALID_FRIEND_NO("80000","有效好友不足，不能提现"),
	NO_LOGIN_BZ_WISH("80001","该账号不能登录宝猪乐园心愿版"),
	NO_LOGIN_BZLY("80002","该账号不能登录宝猪乐园"),
	QR_CODE_ERROR("80003","该邀请码不能绑定"),
	BZLY_UPDE_PASSWORD("80004","请在宝猪乐园心愿版app修改密码"),
	WISHBZ_UPDE_PASSWORD("80004","请在宝猪乐园app修改密码"),
	ACCOUNT_EXECPTION("8006","帐号异常，请联系客服");
	
	
	private String statusCode;
	private String message;

	private RespCodeState(String statusCode, String message) {
		this.statusCode = statusCode;
		this.message = message;
	}

	public static String getResponseEnumValue(String statusCode) {
		for (RespCodeState responseEnum : RespCodeState.values()) {
			if (responseEnum.getStatusCode().equals(statusCode)) {
				return responseEnum.getMessage();
			}
		}
		return null;
	}

	public static RespCodeState getResponseEnum(String statusCode) {
		for (RespCodeState responseEnum : RespCodeState.values()) {
			if (statusCode.equals(responseEnum.getStatusCode())) {
				return responseEnum;
			}
		}
		return null;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}

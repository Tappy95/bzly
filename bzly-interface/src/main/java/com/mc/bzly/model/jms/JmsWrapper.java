package com.mc.bzly.model.jms;

public class JmsWrapper{
	
    public enum Type {
        USER_FIRST_WITHDRAWALS, 
        ADD_APPRENTICE,
        REG_SEND_PASSBOOK,
        USER_SIGN,
        USER_INFORM,
        HIPPO_SEND_IMP_TRACKING,
        HIPPO_SEND_CLK_TRACKING,
        SEND_COIN_TO_TEAM_LEADER,
        CASH_PAY,
        EXCHANGE_COIN_TASK_QDZ,
        CALL_BACK_YOLE,
        CALL_BACK_PCDD,
        CALL_BACK_XW,
        CALL_BACK_BZ,
        CALL_BACK_YM,
        USER_CASH,
        DAY_SIGN,
        SIGN_TASK,
        DAY_ACTIVE,
        USER_TASK,
        COIN_PIG,
        USER_RECEIVE_TASK,
        USER_EXCHANGE_GOODS,
        CASH_EXAMINE,
        RELIE_PIG,
        CASH_TASK,
        VIDEO_TASK,
        DAREN_RECOMMEND_REWARD,
        SMASH_EGGS,
        ACT_CARD,
        ENROLL,
        SEND_KS_CALLBACK,
        CARD_PASSWORD,
        WISH_USER_CASH, 
        CALL_BACK_XYZ, 
        SEND_QTT_CALLBACK,
        WISH_ADD_APPRENTICE, HIGH_VIP_REWARD,
    }
    
    private Object data;
    
    private Type type;
    
    private Long createtime;

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Long getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Long createtime) {
		this.createtime = createtime;
	}

	public JmsWrapper(Object data, Type type,Long createtime) {
		super();
		this.data = data;
		this.type = type;
		this.createtime = createtime;
	}
	public JmsWrapper() {
	}
}

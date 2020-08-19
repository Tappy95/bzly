package com.mc.bzly.dao.wish;

import org.apache.ibatis.annotations.Mapper;

import com.mc.bzly.model.wish.WishGain;

@Mapper
public interface WishGainDao {
	
	int insert(WishGain wishGain);
	
	WishGain selectSign(String sign);

}

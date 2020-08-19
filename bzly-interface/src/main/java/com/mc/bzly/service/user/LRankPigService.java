package com.mc.bzly.service.user;

import java.text.ParseException;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.mc.bzly.model.user.LRankPig;
import com.mc.bzly.model.user.LRankPigSimple;

public interface LRankPigService {

	PageInfo<LRankPig> queryList(LRankPig lRankPig);

	PageInfo<LRankPigSimple> listF(LRankPig lRankPig) throws Exception;

	Map<String, Object> queryMyRank(String userId,Integer rankType) throws Exception ;

	PageInfo<LRankPigSimple> listFDay(LRankPig lRankPig);

	Map<String, Object> queryMyRankDay(String userId, int rankType) throws ParseException;
}

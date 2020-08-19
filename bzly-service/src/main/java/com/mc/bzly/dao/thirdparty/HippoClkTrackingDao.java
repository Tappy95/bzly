package com.mc.bzly.dao.thirdparty;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mc.bzly.model.thirdparty.HippoClkTracking;

@Mapper
public interface HippoClkTrackingDao {

	void batchInsert(List<HippoClkTracking> hippoClkTrackings);
}

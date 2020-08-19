package com.mc.bzly.dao.thirdparty;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mc.bzly.model.thirdparty.HippoImpTracking;

@Mapper
public interface HippoImpTrackingDao {

	 void batchInsert(List<HippoImpTracking> hippoImpTrackings);
}

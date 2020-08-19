package com.mc.bzly.dao.news;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mc.bzly.model.news.AppNewsNotice;

@Mapper
public interface AppNewsNoticeDao {
	/**
	 * 添加公告
	 * @param appNewsNotice
	 * @return
	 */
	Integer saveNotice(AppNewsNotice appNewsNotice);
	/**
	 * 后台获取公告列表
	 * @param appNewsNotice
	 * @return
	 */
	List<AppNewsNotice> selectList(AppNewsNotice appNewsNotice);
	/**
	 * 获取公告详情
	 * @param id
	 * @return
	 */
	AppNewsNotice selectId(@Param("id")Integer id);
	/**
	 * 修改公告
	 * @param appNewsNotice
	 * @return
	 */
	Integer updateNotice(AppNewsNotice appNewsNotice);
	/**
	 * 删除公告
	 * @param id
	 * @return
	 */
	Integer deleteNotice(@Param("id")Integer id);
	
	AppNewsNotice selectOne(Long currentime);
	
	List<AppNewsNotice> selectAppList(AppNewsNotice appNewsNotice);
	
	Integer selectIsReadCount(String userId);
	
	List<AppNewsNotice> selectReadList(String userId);
	
	AppNewsNotice selectOneNews(@Param("currentime")long currentime,@Param("appType")Integer appType);
 
}

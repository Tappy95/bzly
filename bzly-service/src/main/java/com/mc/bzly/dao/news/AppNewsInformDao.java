package com.mc.bzly.dao.news;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mc.bzly.model.news.AppNewsInform;

@Mapper
public interface AppNewsInformDao {
	/**
	 * 后台添加通知
	 * @param appNewsInform
	 * @return
	 */
	Integer saveInform(AppNewsInform appNewsInform);
	/**
	 * 通知列表
	 * @param appNewsInform
	 * @return
	 */
	List<AppNewsInform> selectList(AppNewsInform appNewsInform);
	/**
	 * 获取通知详情
	 * @param id
	 * @return
	 */
	AppNewsInform selectOne(@Param("id")Integer id);
	/**
	 * 修改通知
	 * @param appNewsInform
	 * @return
	 */
	Integer update(AppNewsInform appNewsInform);
	/**
	 * 删除通知
	 * @param id
	 * @return
	 */
	Integer delete(@Param("id")Integer id);
	/**
	 * 批量添加消息通知
	 * @param appNewsInforms
	 * @return
	 */
	Integer saveInformList(List<AppNewsInform> appNewsInforms);
	
	int selectIsRead(String userId);
	
	int updateRead(@Param("id")Integer id,@Param("userId")String userId);
	
	int updateWholeRead(String userId);

}

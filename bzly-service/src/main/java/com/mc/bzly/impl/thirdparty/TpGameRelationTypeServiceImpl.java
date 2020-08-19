package com.mc.bzly.impl.thirdparty;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.thirdparty.TpGameRelationTypeDao;
import com.mc.bzly.model.thirdparty.TpGameRelationType;
import com.mc.bzly.service.thirdparty.TpGameRelationTypeService;
import com.mc.bzly.util.StringUtil;

@Service(interfaceClass = TpGameRelationTypeService.class,version = WebConfig.dubboServiceVersion)
public class TpGameRelationTypeServiceImpl implements TpGameRelationTypeService{
	
	@Autowired
	private TpGameRelationTypeDao tpGameRelationTypeDao;

	@Override
	public int update(Integer gameId, String typeIds) {
        int res=tpGameRelationTypeDao.delete(gameId);
		if(!StringUtil.isNullOrEmpty(typeIds)) {
			String[] strs=typeIds.split(",");
			if(strs.length>0) {
				List<TpGameRelationType> list=new ArrayList<TpGameRelationType>();
				TpGameRelationType tpGameRelationType=null;
				for(int i=0;i<strs.length;i++) {
					tpGameRelationType=new TpGameRelationType();
					tpGameRelationType.setGameId(gameId);
					tpGameRelationType.setTypeId(Integer.parseInt(strs[i]));
					list.add(tpGameRelationType);
				}
				tpGameRelationTypeDao.insertList(list);
			}
		}	
		return res;
	}

	@Override
	public List<Integer> listType(Integer gameId) {
		return tpGameRelationTypeDao.selectListType(gameId);
	}

}

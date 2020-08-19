package com.mc.bzly.service.fighting;

import com.github.pagehelper.PageInfo;
import com.mc.bzly.model.fighting.MFightingType;

public interface MFightingTypeService {
    
	Integer add(MFightingType mFightingType);
	 
	PageInfo<MFightingType> pageList(MFightingType mFightingType);
	 
	MFightingType selectOne(Integer typeId);
	 
	Integer update(MFightingType mFightingType);
	 
	Integer delete(Integer typeId);
}

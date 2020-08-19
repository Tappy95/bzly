package com.mc.bzly.service.sms;

import com.mc.bzly.model.sms.SSmsParamConf;
import com.github.pagehelper.PageInfo;

public interface SSmsParamConfService {
	
    int update(SSmsParamConf sSmsParamConf);
    
    SSmsParamConf selectOne(int id);
    
    PageInfo<SSmsParamConf> selectList(SSmsParamConf sSmsParamConf);
    
    int insert(SSmsParamConf sSmsParamConf);

}

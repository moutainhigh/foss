package com.deppon.foss.module.settlement.common.api.server.dao;

import com.deppon.foss.module.settlement.common.api.shared.domain.FossToFinsRemitCommonLogEntity;

/**
 * @Description:  记录推送第三方付款数据到财务自助响应Dao接口
 * @author 321603
 * 2016-10-14
 */
public interface IFossToFinsRemitCommonLogDao {
	
	
    /**
    * @Title: insertFossToFinsRemitCommonLog
    * @Description: 
    * @author 321603
    * @date 2016-10-14
    * @throws
    */
    void insertFossToFinsRemitCommonLog(FossToFinsRemitCommonLogEntity entity);
    
}

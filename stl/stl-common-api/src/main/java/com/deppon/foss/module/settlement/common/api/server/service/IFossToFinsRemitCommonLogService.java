package com.deppon.foss.module.settlement.common.api.server.service;

import com.deppon.foss.module.settlement.common.api.shared.domain.FossToFinsRemitCommonLogEntity;

/**
 * @Description:  记录推送第三方付款数据到财务自助响应service接口
 * @author 321603
 * 2016-10-14
 */
public interface IFossToFinsRemitCommonLogService {
	
	
    /**
    * @Title: insertFossToFinsRemitCommonLogJob
    * @Description: 
    * @author 321603
    * @date 2016-10-14
    * @throws
    */
    void insertFossToFinsRemitCommonLog(FossToFinsRemitCommonLogEntity entity);
    
}

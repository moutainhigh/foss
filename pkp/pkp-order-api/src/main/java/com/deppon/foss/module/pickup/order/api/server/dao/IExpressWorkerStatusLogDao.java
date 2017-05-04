package com.deppon.foss.module.pickup.order.api.server.dao;

import com.deppon.foss.module.pickup.order.api.shared.domain.ExpressWorkerStatusLogEntity;


public interface IExpressWorkerStatusLogDao {
    /**
     * 插入日志
     * */
    int insertSelective(ExpressWorkerStatusLogEntity record);
   
}
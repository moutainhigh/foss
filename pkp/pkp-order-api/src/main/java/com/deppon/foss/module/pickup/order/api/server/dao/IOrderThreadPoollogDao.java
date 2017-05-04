package com.deppon.foss.module.pickup.order.api.server.dao;

import com.deppon.foss.module.pickup.order.api.shared.domain.OrderThreadPoollogEntity;


public interface IOrderThreadPoollogDao {
    /**
     * 
     * @param 插入日志
     * @return
     */
    int insert(OrderThreadPoollogEntity record);

}
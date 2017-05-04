package com.deppon.foss.module.pickup.waybill.api.server.dao;

import com.deppon.foss.module.pickup.waybill.shared.domain.OrderLockEntity;


public interface IOrderLockDao {
    int insert(OrderLockEntity record);
    int insertSelective(OrderLockEntity record);
    int insertOrderLockLog(OrderLockEntity record);
    String queryOrderLockCodeNoCode(OrderLockEntity record);
    void updateOrderLock(OrderLockEntity record);
    
    OrderLockEntity queryOrderLockByDeptCode(String deptCode);
	String queryUnifiedCode(String code);
}
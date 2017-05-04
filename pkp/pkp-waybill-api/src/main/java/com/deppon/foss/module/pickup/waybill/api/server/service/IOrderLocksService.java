package com.deppon.foss.module.pickup.waybill.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.waybill.shared.domain.OrderLockEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.OrderLockResult;

public interface IOrderLocksService  extends IService{
	List<OrderLockResult> crmsyncorderLock(List<OrderLockEntity> records);
	
	
	
	OrderLockEntity queryOrderLockByDeptCode(String deptCode);



	OrderLockEntity queryOrderLockByDeptCodeForSyn(String deptCode);



	String queryUnifiedCode(String code);
}

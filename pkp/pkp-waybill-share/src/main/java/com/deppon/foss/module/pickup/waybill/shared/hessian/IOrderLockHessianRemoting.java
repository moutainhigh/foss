package com.deppon.foss.module.pickup.waybill.shared.hessian;

import com.deppon.foss.framework.service.IHessianService;
import com.deppon.foss.module.pickup.waybill.shared.domain.OrderLockEntity;

public interface IOrderLockHessianRemoting extends IHessianService{
	OrderLockEntity queryOrderLockByDeptCode(String deptCode);



	OrderLockEntity queryOrderLockByDeptCodeForSyn(String deptCode);
	 String  queryUnifiedCode(String code);
}

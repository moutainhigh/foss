package com.deppon.foss.module.pickup.waybill.server.hessian;

import javax.annotation.Resource;

import com.deppon.foss.framework.server.deploy.hessian.annotation.Remote;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IOrderLocksService;
import com.deppon.foss.module.pickup.waybill.shared.domain.OrderLockEntity;
import com.deppon.foss.module.pickup.waybill.shared.hessian.IOrderLockHessianRemoting;
@Remote
public class OrderLockHessianRemoting implements IOrderLockHessianRemoting {
	@Resource
	IOrderLocksService orderLocksService;
	@Resource
	IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	
	@Override
	public OrderLockEntity queryOrderLockByDeptCode(String deptCode) {
		
		
		return orderLocksService.queryOrderLockByDeptCode(deptCode);
	}

	@Override
	public OrderLockEntity queryOrderLockByDeptCodeForSyn(String deptCode) {
		// TODO Auto-generated method stub
		return orderLocksService.queryOrderLockByDeptCodeForSyn(deptCode);
	}

	@Override
	public String queryUnifiedCode(String code) {
		 
		return orderLocksService.queryUnifiedCode(code);
	}

}

package com.deppon.foss.module.transfer.dubbo.api.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;

/**
 * 
 * 运单管理接口
 * 
 * @author foss-sunrui
 * @date 2012-10-30 下午4:08:25
 * @version
 */
public interface IWaybillManagerService4dubbo extends IService {

	/**
	 * 集中开单查开单组所属中转场的默认出发部门
	 * 
	 * @author WangQianJin
	 * @date 2013-6-26 下午3:55:19
	 */
	SaleDepartmentEntity queryPickupCentralizedDeptCode(String billingGroupOrgCode);
}
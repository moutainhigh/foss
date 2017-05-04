package com.deppon.foss.module.transfer.dubbo.api.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.module.base.baseinfo.api.server.service.IBillingGroupTransFerService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BillingGroupTransFerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.transfer.dubbo.api.service.IWaybillManagerService4dubbo;

public class WaybillManagerService4dubbo implements IWaybillManagerService4dubbo {
	private ISaleDepartmentService saleDepartmentService;
	private IBillingGroupTransFerService billingGroupTransFerService;

	@Override
	public SaleDepartmentEntity queryPickupCentralizedDeptCode(String billingGroupOrgCode) {
		SaleDepartmentEntity pickupCentralizedDept = null;
		String stransCenterCode = this.queryTransCenterByBillingGroupCode(billingGroupOrgCode);
		if (!StringUtils.isEmpty(stransCenterCode)) {
			String salesDeptCode = this.queryDefaultSalesDeptByTransCenter(stransCenterCode);
			if (!StringUtils.isEmpty(salesDeptCode)) {
				pickupCentralizedDept = getSaleDepartmentService().querySaleDepartmentByCode(salesDeptCode);
			}
		}
		return pickupCentralizedDept;
	}

	private String queryDefaultSalesDeptByTransCenter(String saleCode) {
		return saleDepartmentService.queryLeaveStationSaleCodeByTransferCenterCode(saleCode);
	}

	private String queryTransCenterByBillingGroupCode(String billingGroupCode) {
		BillingGroupTransFerEntity entity = getBillingGroupTransFerService()
				.queryTransFerListByBillingGroupCode(billingGroupCode);
		if (entity != null) {
			return entity.getTransFerCode();
		} else {
			return "";
		}

	}

	public ISaleDepartmentService getSaleDepartmentService() {
		return saleDepartmentService;
	}

	@Autowired
	public void setSaleDepartmentService(ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}

	public IBillingGroupTransFerService getBillingGroupTransFerService() {
		return billingGroupTransFerService;
	}
	@Autowired
	public void setBillingGroupTransFerService(IBillingGroupTransFerService billingGroupTransFerService) {
		this.billingGroupTransFerService = billingGroupTransFerService;
	}

}

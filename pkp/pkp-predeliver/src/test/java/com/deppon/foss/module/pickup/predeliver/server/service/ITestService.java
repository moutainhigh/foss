package com.deppon.foss.module.pickup.predeliver.server.service;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.LclDeliveryToCashManagementDeliveryEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LclDeliveryToCashManagementUnloadingEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.PathDetailEntity;

public interface ITestService {
	public WaybillEntity queryWaybillBasicByNo(String waybillNo);

	List<PathDetailEntity> queryPathDetailByNos(String waybillNo,List<String> serialNoList);

	SaleDepartmentEntity querySaleDepartmentByCode(String code);

	List<LclDeliveryToCashManagementDeliveryEntity> queryLclDeliveryToCashManagementDeliveryByOrgCode(String code);

	List<LclDeliveryToCashManagementUnloadingEntity> queryLclDeliveryToCashManagementUnloadingEntitytByCode(
			String startOrgCode, String reachOrgCode);
}

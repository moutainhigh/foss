package com.deppon.foss.module.base.baseinfo.api.server.service.esb;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressDeliveryRegionsEntity;

public interface ISendExpressDeliveryRegionsInfoService {
	
	 void synRegionsToGW(List<ExpressDeliveryRegionsEntity> entitys,String operateType);
	 
	 void synRegionsToCRM(List<ExpressDeliveryRegionsEntity> entitys,int operateType);

}

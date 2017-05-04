package com.deppon.foss.module.pickup.predeliver.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverbillEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ResultDto;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;

public interface IGpsDeliverService extends IService{
	ResultDto syscConfirmDeliverTaskToGps(DeliverbillEntity deliverbill);
	
	ResultDto syscCancelDeliverTaskToGps(DeliverbillEntity deliverbill, String waybillNo);
	
	ResultDto syscModifyDeliverTaskToGps(DeliverbillEntity deliverbill, WaybillEntity waybillEntity);
}
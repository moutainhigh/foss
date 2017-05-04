package com.deppon.foss.module.base.baseinfo.api.server.service.esb;

import java.util.List;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressDeliverySmallZoneEntity;

public interface ISendExpressSmallZoneToOMSService {
	
	/**
	 * 同步快递收派小区信息到OMS
	 * @author 313353
	 * @date 2016-3-21 上午11:50:25
	 */
	void syncExpressSmallZoneToOMS(List<ExpressDeliverySmallZoneEntity> entitys) ;
}

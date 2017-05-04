package com.deppon.foss.module.pickup.order.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.order.api.shared.domain.PdaSignEntity;
import com.deppon.foss.module.pickup.order.api.shared.dto.AddressCollectionRetDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.GpsAddressCollectDto;


public interface IPdaAddressCollectionService extends IService {
	/***
	 * @param addressEntity 
	 * @return 插入的Id
	 */
	public AddressCollectionRetDto fossAddressCollection(GpsAddressCollectDto gpsAddressCollectionDto);
	
	
	/**
	 * 同步司机签到信息给oms
	 * @param pdaEntity
	 * @return
	 */
	public void sendModifySignInfo(PdaSignEntity pdaEntity);
	
	

}

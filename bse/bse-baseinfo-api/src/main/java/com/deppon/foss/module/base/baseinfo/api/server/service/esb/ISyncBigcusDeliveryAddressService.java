package com.deppon.foss.module.base.baseinfo.api.server.service.esb;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.BigcusDeliveryAddressEntity;

public interface ISyncBigcusDeliveryAddressService {

	/**
	 * <p>同步零担大客户派送地址库至OMS</p> 
	 * @author 232607 
	 * @date 2016-5-31 上午9:19:48
	 * @param entitys
	 * @see
	 */
	void syncBigcusDeliveryAddressToOMS(List<BigcusDeliveryAddressEntity> entitys);

}

package com.deppon.foss.module.pickup.predeliver.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ArrivalGoodsResponseDto;

/**
 * FOSS给DOP主动推送到货信息(特殊增值服务类运单)
 * @author 243921-FOSS-zhangtingting
 * @date 2015-09-11 下午03:02:44
 */
public interface IArrivalGoodsInformationDopService extends IService{

	/**
	 * 给DOP推送到货信息
	 * @author 243921-FOSS-zhangtingting
	 * @date 2015-09-11 下午03:07:35
	 * @return
	 */
	public ArrivalGoodsResponseDto arrivalGoodsInformation(String waybillNO);
}

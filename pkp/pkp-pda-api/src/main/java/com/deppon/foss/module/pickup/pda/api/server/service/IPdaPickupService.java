package com.deppon.foss.module.pickup.pda.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PadPickupDto;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PadPickupResultDto;

/**
 * pda提货清单的service
 * @author foss-yuting
 * @date  2014-11-27 上午11:51:55 
 * @since
 * @version
 */
public interface IPdaPickupService extends IService {
	
	/**
	 * 提供Pad提货清单列表数据
	 * @date 2014-11-27 下午3:58:10
	 * @param dto
	 * @return 
	 * @see com.deppon.foss.module.pickup.pda.api.server.service.IPdaPickupService#getPadPickupList(com.deppon.foss.module.pickup.pda.api.shared.dto.PadPickupDto)
	 */
	public List<PadPickupResultDto> getPadPickupList(PadPickupDto pickupDto);
	
	/**
	 * pad撤销运单的状态
	 * @date 2014-11-27 下午3:58:10
	 * @param dto
	 * @return
	 * @see com.deppon.foss.module.pickup.pda.api.server.service.IPdaPickupService#pdaChangeWaybillState(java.util.List)
	 */
	public void pdaChangeWaybillState(List<String> waybillNos);
}	

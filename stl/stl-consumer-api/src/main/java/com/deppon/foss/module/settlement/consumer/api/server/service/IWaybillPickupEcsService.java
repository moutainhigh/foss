package com.deppon.foss.module.settlement.consumer.api.server.service;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.WaybillPickupInfoDto;

/**
 * 悟空开单、更改服务
 * @author foss-231434-bieyexiong
 * @date 2016-11-4
 */
public interface IWaybillPickupEcsService {
	
	/**
	 * 悟空新增运单
	 * @author foss-231434-bieyexiong
	 * @date 2016-11-3
	 */
	void addWaybillEcs(WaybillPickupInfoDto waybill, CurrentInfo currentInfo);
	
	/**
	 * 悟空校验能否起草更改
	 * @author foss-231434-bieyexiong
	 * @date 2016-11-3
	 */
	void canChange(String waybillNo);
	
	/**
	 * 悟空作废运单
	 * @author foss-231434-bieyexiong
	 * @date 2016-11-2
	 */
	void cancelWaybillEcs(String waybillNo, CurrentInfo currentInfo);
	
	/**
	 * 悟空更改运单
	 * @author foss-231434-bieyexiong
	 * @date 2016-11-2
	 */
	void modifyWaybillEcs(WaybillPickupInfoDto oldWaybill,WaybillPickupInfoDto newWaybill, CurrentInfo currentInfo);
	
}

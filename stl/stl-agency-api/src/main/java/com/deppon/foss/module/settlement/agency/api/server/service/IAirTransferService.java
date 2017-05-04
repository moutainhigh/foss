package com.deppon.foss.module.settlement.agency.api.server.service;

import java.util.List;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirTransPickupDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirTransPickupbillEntity;


/**
 * 中转提货服务
 * @author ibm-zhuwei
 * @date 2012-11-30 下午3:17:14
 */
public interface IAirTransferService {

	/**
	 * 新增空运中转提货
	 * @author ibm-zhuwei
	 * @date 2012-11-21 上午11:36:04
	 */
	void addAirTransfer(AirTransPickupbillEntity master, List<AirTransPickupDetailEntity> details, CurrentInfo currentInfo);

	/**
	 * 修改空运中转提货明细
	 * 
	 * @author ibm-zhuwei
	 * @date 2012-11-21 上午11:36:06
	 * @param addedDetails 新增明细
	 * @param modifiedDetails 修改明细
	 * @param removedDetails 删除明细（运单集合）
	 */
	void modifyAirTransferDetail(AirTransPickupbillEntity oldMaster, AirTransPickupbillEntity newMaster, 
			List<AirTransPickupDetailEntity> addedDetails, List<AirTransPickupDetailEntity> modifiedDetails,
			List<String> removedDetails, CurrentInfo currentInfo);

	
	/**
	 * 验证空运中转明细
	 * 
	 * @author Foss-chenmingchun
	 * @date 2013-11-21 上午11:36:06
	 * @param waybillNos 修改明细
	 */
	void validateAirTransferDetail(String destOrgCode,String waybillNo);
}

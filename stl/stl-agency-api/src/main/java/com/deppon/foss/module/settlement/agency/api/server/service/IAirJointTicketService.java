package com.deppon.foss.module.settlement.agency.api.server.service;

import java.util.List;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickupbillDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickupbillEntity;


/**
 * 空运合大票服务
 * @author ibm-zhuwei
 * @date 2012-11-21 上午11:34:15
 */
public interface IAirJointTicketService {

	/**
	 * 新增空运合大票
	 * @author ibm-zhuwei
	 * @date 2012-11-21 上午11:36:04
	 */
	void addAirJointTicket(AirPickupbillEntity master, List<AirPickupbillDetailEntity> details, CurrentInfo currentInfo);
	
	/**
	 * 修改空运合大票明细
	 * 
	 * @author ibm-zhuwei
	 * @date 2012-11-21 上午11:36:06
	 * @param addedDetails 新增明细
	 * @param modifiedDetails 修改明细
	 * @param removedDetails 删除明细（运单集合）
	 */
	void modifyAirJointTicketDetail(AirPickupbillEntity oldMaster, AirPickupbillEntity newMaster,
			List<AirPickupbillDetailEntity> addedDetails, List<AirPickupbillDetailEntity> modifiedDetails,
			List<String> removedDetails, CurrentInfo currentInfo);
	
	
	/**
	 * 验证空运合大票明细
	 * 
	 * @author Foss-chenmingchun
	 * @date 2013-11-21 上午11:36:06
	 * @param waybillNos 修改明细
	 */
	void validateAirJointTicketDetail(String waybillNo);
	

}

package com.deppon.foss.module.pickup.sign.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity;
import com.deppon.foss.module.pickup.sign.api.shared.vo.DeliverbillDetailVo;

/**
 * 合伙人快递派送处理service
 * @author gpz
 * @date 2016年1月29日
 */
public interface IPtpExpDeliverHandlerService extends IService {

	/**
	 * 合伙人快递派送处理  无PDA签收
	 * @author gpz
	 * @date 2016年1月29日
	 * @param  arriveSheet 到达联entity
	 * @param  deliverbillDetailVo 派送单明细vo
	 * @return String 
	 */
	String addNoPdaSignForPtpExpDeliver(ArriveSheetEntity arriveSheet,
			DeliverbillDetailVo deliverbillDetailVo);

	/**
	 * 合伙人快递派送处理  有PDA签收
	 * @author gpz
	 * @date 2016年1月29日
	 * @param  arriveSheet 到达联entity
	 * @param  deliverbillDetailVo 派送单明细vo
	 */
	void addPdaSignForPtpExpDeliver(ArriveSheetEntity arriveSheet,
			DeliverbillDetailVo deliverbillDetailVo);

	
}

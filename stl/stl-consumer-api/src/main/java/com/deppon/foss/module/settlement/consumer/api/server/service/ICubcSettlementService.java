package com.deppon.foss.module.settlement.consumer.api.server.service;

import com.deppon.foss.module.settlement.common.api.shared.dto.CUBCResponseDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.FossToCubcRequestDto;


/**
 * CUBC结清中转接口
 * @date 2016-10-31
 * @author 378375
 *
 */
public interface ICubcSettlementService {
	
	/**
	 * FOSS结清调用CUBC结清接口方法
	 * @param dto
	 * @return
	 */
	CUBCResponseDto settleReqToCUBU(FossToCubcRequestDto dto);
	
}

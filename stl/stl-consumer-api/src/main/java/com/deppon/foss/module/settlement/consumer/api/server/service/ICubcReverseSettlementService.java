package com.deppon.foss.module.settlement.consumer.api.server.service;

import com.deppon.foss.module.settlement.common.api.shared.dto.CUBCResponseDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.FossToCubcRequestDto;

/**
 * CUBC反结清中转接口
 * @date 2016-11-02
 * @author 378375
 *
 */
public interface ICubcReverseSettlementService {
	/**
	 * FOSS调用CUBC反结清接口方法
	 * @param dto FOSS调用CUBC的请求参数
	 * @return
	 */
	CUBCResponseDto reverseSettleReqToCUBU(FossToCubcRequestDto dto);
}

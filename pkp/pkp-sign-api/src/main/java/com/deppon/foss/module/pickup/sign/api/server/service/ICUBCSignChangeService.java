package com.deppon.foss.module.pickup.sign.api.server.service;

import com.deppon.foss.module.pickup.sign.api.shared.dto.CUBCSignChangeRequestDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.CUBCSignChangeResultDto;

	/**
	 * CUBC签收变更FOSS客户端调用接口
	 * @author 353654
	 *
	 */
	public interface ICUBCSignChangeService {
		CUBCSignChangeResultDto changeRepayment(CUBCSignChangeRequestDto dto);
	}

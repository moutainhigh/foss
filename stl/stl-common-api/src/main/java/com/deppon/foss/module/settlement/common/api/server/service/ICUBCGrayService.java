package com.deppon.foss.module.settlement.common.api.server.service;

import com.deppon.foss.module.settlement.common.api.shared.dto.CUBCGrayRequestDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.CUBCGrayResultDto;

/**
 * CUBC灰度层接口  FOSS客户端
 * @author 353654
 *
 */
public interface ICUBCGrayService {
	
	/**
	 * 灰度查询分发类型
	 * @param CUBCGrayRequestDto dto
	 * @return
	 */
	CUBCGrayResultDto queryDistributeType(CUBCGrayRequestDto dto);
}

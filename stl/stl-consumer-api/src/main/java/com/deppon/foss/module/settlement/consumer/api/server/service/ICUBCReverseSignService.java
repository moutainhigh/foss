package com.deppon.foss.module.settlement.consumer.api.server.service;

import com.deppon.foss.module.settlement.consumer.api.shared.dto.CUBCSignOrRevSignResultDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.FOSSSignOrRevSignRequestDto;

/**
 * CUBC签收服务接口
 * @author 353654
 *
 */
public interface ICUBCReverseSignService {
	
	/**
	 * FOSS签收调用CBUC反签收接口方法
	 * @param FOSSSignOrRevSignRequestDto reqDto
	 * @return
	 */
	CUBCSignOrRevSignResultDto sendReverseSignReqToCUBC(FOSSSignOrRevSignRequestDto reqDto);
}

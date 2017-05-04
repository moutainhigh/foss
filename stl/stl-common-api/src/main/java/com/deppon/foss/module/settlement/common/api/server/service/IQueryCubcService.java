package com.deppon.foss.module.settlement.common.api.server.service;

import com.deppon.foss.module.settlement.common.api.shared.dto.CodQueryToCubcDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.CodQueryToCubcResponse;

/**
 * 对接cubc服务
 * @author 269044
 * @date 2017-04-07
 */
public interface IQueryCubcService {
	
	/**
	 * 查询代收集合
	 * @author 269044
	 * @date 2017-04-07
	 */
	public CodQueryToCubcResponse queryCubcCodList(CodQueryToCubcDto dto);
}

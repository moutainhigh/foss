package com.deppon.foss.module.settlement.common.api.server.service;

import com.deppon.foss.module.settlement.common.api.shared.domain.FossConfigEntity;

/**
 *  esb地址配置Service
 *  @author 269044
 *  @date 2015-11-04
 */
public interface IFossConfigEntityService {

	/**
	 * 查询esb地址信息
	 * @author 269044
	 * @date 2015-11-04 
	 * @param serverCode
	 * @return FossConfigEntity
	 */
	FossConfigEntity queryFossConfigEntityByServerCode(String serverCode);
}

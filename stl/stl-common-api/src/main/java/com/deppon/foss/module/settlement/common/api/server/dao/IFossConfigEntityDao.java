package com.deppon.foss.module.settlement.common.api.server.dao;

import com.deppon.foss.module.settlement.common.api.shared.domain.FossConfigEntity;

/**
 *  esb地址配置DAO
 *  @author 269044
 *  @date 2015-11-04
 */
public interface IFossConfigEntityDao {
	
	/**
	 * 查询esb地址信息
	 * @author 269044
	 * @date 2015-11-04 
	 * @param serverCode
	 * @return FossConfigEntity
	 */
	FossConfigEntity queryFossConfigEntityByServerCode(String serverCode);
}

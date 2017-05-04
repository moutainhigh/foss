package com.deppon.foss.module.settlement.closing.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrWoodenEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrWoodenDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
/**
 * 功能：代打木架
 * @author 045738
 *
 */
public interface IMvrWoodenEntityService extends IService {
	
	/**
	 * 功能：根据条件查询代打木架信息
	 * @author 045738-foss-maojianqiang
	 * @date 2014-4-11
	 * @return
	 */
	public List<MvrWoodenEntity> selectByConditions(MvrWoodenDto dto, int start,
			int limit)  throws SettlementException ;
	
	/**
	 * 功能：根据条件查询代打木架信息 不分页
	 * @author 045738-foss-maojianqiang
	 * @date 2014-4-11
	 * @return
	 */
	public List<MvrWoodenEntity> selectByConditions(MvrWoodenDto dto)  throws SettlementException ;
	
	/**
	 * 功能：查询代打木架汇总信息
	 * @author 045738-foss-maojianqiang
	 * @date 2014-4-11
	 * @return
	 */
	public MvrWoodenDto queryMvrWoodenTotal(MvrWoodenDto dto); 
}

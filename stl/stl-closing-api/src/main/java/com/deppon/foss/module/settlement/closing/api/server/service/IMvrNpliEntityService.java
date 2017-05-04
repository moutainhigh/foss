package com.deppon.foss.module.settlement.closing.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrNpliEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNpliDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;

/**
 * 始发偏线往来月报表Service
 * @author guxinhua
 * @date 2013-3-6 下午2:08:35
 */
public interface IMvrNpliEntityService extends IService {

	/**
	 * 查询始发偏线往来月报表
	 * 
	 * @author 163576-foss-guxinhua
	 * @date 2013-11-6 下午5:56:31
	 * @param dto
	 * @return
	 */
    List<MvrNpliEntity> selectByConditions(MvrNpliDto dto, int start, int limit)  throws SettlementException ;
    
	/**
	 * 查询始发偏线往来月报表合计
	 * 
	 * @author 163576-foss-guxinhua
	 * @date 2013-11-6 下午5:56:31
	 * @param dto
	 * @return
	 */
    MvrNpliDto selectTotalByConditions(MvrNpliDto dto)  throws SettlementException ;
    
}

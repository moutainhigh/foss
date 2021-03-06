package com.deppon.foss.module.settlement.closing.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrNrfdoEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfdoDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;

/**
 * 01到达月报表Service
 * @author guxinhua
 * @date 2013-3-6 下午2:08:35
 */
public interface IMvrNrfdoEntityService extends IService {

	/**
	 * 查询01到达月报表
	 * 
	 * @author 163576-foss-guxinhua
	 * @date 2013-11-6 下午5:56:31
	 * @param dto
	 * @return
	 */
    List<MvrNrfdoEntity> selectByConditions(MvrNrfdoDto dto, int start, int limit)  throws SettlementException ;
    
	/**
	 * 查询01到达月报表合计
	 * 
	 * @author 163576-foss-guxinhua
	 * @date 2013-11-6 下午5:56:31
	 * @param dto
	 * @return
	 */
    MvrNrfdoDto selectTotalByConditions(MvrNrfdoDto dto)  throws SettlementException ;
    
}

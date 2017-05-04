package com.deppon.foss.module.settlement.closing.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrNafiEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNafiDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;

/**
 * 空运往来月报表Service
 * @author guxinhua
 * @date 2013-11-27 下午5:56:31
 */
public interface IMvrNafiEntityService extends IService {

	/**
	 * 查询空运往来月报表
	 * 
	 * @author 163576-foss-guxinhua
	 * @date 2013-11-27 下午5:56:31
	 * @param dto
	 * @return
	 */
    List<MvrNafiEntity> selectByConditions(MvrNafiDto dto, int start, int limit)  throws SettlementException ;
    
	/**
	 * 查询空运往来月报表合计
	 * 
	 * @author 163576-foss-guxinhua
	 * @date 2013-11-27 下午5:56:31
	 * @param dto
	 * @return
	 */
    MvrNafiDto selectTotalByConditions(MvrNafiDto dto)  throws SettlementException ;
    
}

package com.deppon.foss.module.settlement.closing.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrRfdEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.ClosingPeriodDto;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrRfdDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;

/**
 * 专线到达Service
 * @author guxinhua
 * @date 2013-3-6 下午2:08:35
 */
public interface IMvrRfdEntityService extends IService {

	/**
	 * 查询专线到达报表
	 * 
	 * @author guxinhua
	 * @date 2013-3-6 上午11:21:20
	 * @param dto
	 * @return
	 */
    List<MvrRfdEntity> selectByConditions(MvrRfdDto dto, int start, int limit)  throws SettlementException ;
    
	/**
	 * 查询专线到达报表合计
	 * 
	 * @author guxinhua
	 * @date 2013-3-6 上午11:21:20
	 * @param dto
	 * @return
	 */
    MvrRfdDto selectTotalByConditions(MvrRfdDto dto)  throws SettlementException ;
    
    List<ClosingPeriodDto> queryClosingPeriodList();
}

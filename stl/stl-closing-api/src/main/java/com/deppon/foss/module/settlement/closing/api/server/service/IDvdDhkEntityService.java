package com.deppon.foss.module.settlement.closing.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.DvdDhkEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.DvdDhkDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;

/**
 * 代汇款明细报表Service
 * @author guxinhua
 * @date 2013-3-6 下午2:08:35
 */
public interface IDvdDhkEntityService extends IService {

	/**
	 * 查询代汇款报表
	 * 
	 * @author 163576-foss-guxinhua
	 * @date 2013-12-05 下午4:38:04
	 * @param dto
	 * @return
	 */
    List<DvdDhkEntity> selectByConditions(DvdDhkDto dto, int start, int limit)  throws SettlementException ;
    
	/**
	 * 查询代汇款明细报表合计
	 * 
	 * @author 163576-foss-guxinhua
	 * @date 2013-12-05 下午4:38:04
	 * @param dto
	 * @return
	 */
    DvdDhkDto selectTotalByConditions(DvdDhkDto dto)  throws SettlementException ;
    
}

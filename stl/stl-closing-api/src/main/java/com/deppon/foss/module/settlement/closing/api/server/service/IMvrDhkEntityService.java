package com.deppon.foss.module.settlement.closing.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrDhkEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrDhkDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;

/**
 * 代汇款报表
 *
 * @author 163576-foss-guxinhua
 * @date 2013-12-05 下午4:38:04
 */
public interface IMvrDhkEntityService extends IService {

	/**
	 * 查询代汇款报表
	 * 
	 * @author 163576-foss-guxinhua
	 * @date 2013-12-05 下午4:38:04
	 * @param dto
	 * @return
	 */
    List<MvrDhkEntity> selectByConditions(MvrDhkDto dto, int start, int limit)  throws SettlementException ;
    
	/**
	 * 查询代汇款报表合计
	 * 
	 * @author 163576-foss-guxinhua
	 * @date 2013-12-05 下午4:38:04
	 * @param dto
	 * @return
	 */
    MvrDhkDto selectTotalByConditions(MvrDhkDto dto)  throws SettlementException ;
    
}

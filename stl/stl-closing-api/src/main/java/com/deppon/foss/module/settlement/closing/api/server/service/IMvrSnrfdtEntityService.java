package com.deppon.foss.module.settlement.closing.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrNrfdtEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfdtDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;

/**
 * 02特殊到达月报表Service
 * @author shenweihua
 * @date 2014-6-11 下午2:08:35
 */
public interface IMvrSnrfdtEntityService extends IService{
	
	/**
	 * 查询02到达月报表
	 * 
	 * @author 132599-foss-shenweihua
	 * @date 2014-6-11 下午2:08:35
	 * @param dto
	 * @return
	 */
    List<MvrNrfdtEntity> selectByConditions(MvrNrfdtDto dto, int start, int limit)  throws SettlementException ;
    
	/**
	 * 查询02到达月报表合计
	 * 
	 * @author 132599-foss-shenweihua
	 * @date 2014-6-11 下午2:08:35
	 * @param dto
	 * @return
	 */
    MvrNrfdtDto selectTotalByConditions(MvrNrfdtDto dto)  throws SettlementException ;

}

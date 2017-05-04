package com.deppon.foss.module.settlement.closing.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrNrfiEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfiDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;

/**
 * 始发专线往来月报表SERVICE
 * 
 * @author 132599-foss-shenweihua
 * @date 2014-05-29 下午4:56:31
 */
public interface IMvrSnrfiEntityService extends IService{
	
	/**
	 * 查询始发专线往来月报表
	 * 
	 * @author 132599-foss-shenweihua
	 * @date 2014-05-29 下午4:56:31
	 * @param dto
	 * @return
	 */
	List<MvrNrfiEntity> selectColumnsByConditions(MvrNrfiDto dto, int start,
			int limit) throws SettlementException;

	/**
	 * 查询始发专线往来月报表合计
	 * 
	 * @author 132599-foss-shenweihua
	 * @date 2014-05-29 下午4:56:31
	 * @param dto
	 * @return
	 */
	MvrNrfiEntity selectTotalByConditions(MvrNrfiDto dto)
			throws SettlementException;

}

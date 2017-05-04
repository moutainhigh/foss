package com.deppon.foss.module.settlement.closing.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrNrfiEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfiDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;

/**
 * 始发专线往来月报表SERVICE
 * 
 * @author 163576-foss-guxinhua
 * @date 2013-11-6 下午5:56:31
 */
public interface IMvrNrfiEntityService extends IService {

	/**
	 * 查询始发专线往来月报表
	 * 
	 * @author 163576-foss-guxinhua
	 * @date 2013-11-6 下午5:56:31
	 * @param dto
	 * @return
	 */
	List<MvrNrfiEntity> selectColumnsByConditions(MvrNrfiDto dto, int start,
			int limit) throws SettlementException;

	/**
	 * 查询始发专线往来月报表合计
	 * 
	 * @author 163576-foss-guxinhua
	 * @date 2013-11-6 下午5:56:31
	 * @param dto
	 * @return
	 */
	MvrNrfiEntity selectTotalByConditions(MvrNrfiDto dto)
			throws SettlementException;

}

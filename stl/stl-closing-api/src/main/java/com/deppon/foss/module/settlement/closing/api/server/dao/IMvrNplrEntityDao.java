package com.deppon.foss.module.settlement.closing.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrNplrEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNplrDto;

/**
 * 偏线月报表Dao
 * 
 * @author 163576-foss-guxinhua
 * @date 2013-11-6 下午5:56:31
 */
public interface IMvrNplrEntityDao {

	/**
	 * 查询偏线月报表
	 * 
	 * @author 163576-foss-guxinhua
	 * @date 2013-11-6 下午5:56:31
	 * @param dto
	 * @return
	 */
    List<MvrNplrEntity> selectByConditions(MvrNplrDto dto, int start, int limit);
    
	/**
	 * 查询偏线月报表合计
	 * 
	 * @author 163576-foss-guxinhua
	 * @date 2013-11-6 下午5:56:31
	 * @param dto
	 * @return
	 */
    MvrNplrDto selectTotalByConditions(MvrNplrDto dto);
}

package com.deppon.foss.module.settlement.closing.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrNrfdoEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfdoDto;

/**
 * 01到达月报表Dao
 * 
 * @author 163576-foss-guxinhua
 * @date 2013-11-6 下午5:56:31
 */
public interface IMvrNrfdoEntityDao {

	/**
	 * 查询01到达月报表
	 * 
	 * @author 163576-foss-guxinhua
	 * @date 2013-11-6 下午5:56:31
	 * @param dto
	 * @return
	 */
    List<MvrNrfdoEntity> selectByConditions(MvrNrfdoDto dto, int start, int limit);
    
	/**
	 * 查询01到达月报表合计
	 * 
	 * @author 163576-foss-guxinhua
	 * @date 2013-11-6 下午5:56:31
	 * @param dto
	 * @return
	 */
    MvrNrfdoDto selectTotalByConditions(MvrNrfdoDto dto);
}

package com.deppon.foss.module.settlement.closing.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrNpliEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNpliDto;

/**
 * 始发偏线往来月报表Dao
 * 
 * @author 163576-foss-guxinhua
 * @date 2013-11-6 下午5:56:31
 */
public interface IMvrNpliEntityDao {

	/**
	 * 查询始发偏线往来月报表
	 * 
	 * @author 163576-foss-guxinhua
	 * @date 2013-11-6 下午5:56:31
	 * @param dto
	 * @return
	 */
    List<MvrNpliEntity> selectByConditions(MvrNpliDto dto, int start, int limit);
    
	/**
	 * 查询始发偏线往来月报表合计
	 * 
	 * @author 163576-foss-guxinhua
	 * @date 2013-11-6 下午5:56:31
	 * @param dto
	 * @return
	 */
    MvrNpliDto selectTotalByConditions(MvrNpliDto dto);
}

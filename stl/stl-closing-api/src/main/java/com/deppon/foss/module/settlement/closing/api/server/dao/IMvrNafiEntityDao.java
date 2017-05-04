package com.deppon.foss.module.settlement.closing.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrNafiEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNafiDto;

/**
 * 空运往来月报表Dao
 * 
 * @author 163576-foss-guxinhua
 * @date 2013-11-27 下午5:56:31
 */
public interface IMvrNafiEntityDao {

	/**
	 * 查询空运往来月报表
	 * 
	 * @author 163576-foss-guxinhua
	 * @date 2013-11-27 下午5:56:31
	 * @param dto
	 * @return
	 */
    List<MvrNafiEntity> selectByConditions(MvrNafiDto dto, int start, int limit);
    
	/**
	 * 查询空运往来月报表合计
	 * 
	 * @author 163576-foss-guxinhua
	 * @date 2013-11-27 下午5:56:31
	 * @param dto
	 * @return
	 */
    MvrNafiDto selectTotalByConditions(MvrNafiDto dto);
}

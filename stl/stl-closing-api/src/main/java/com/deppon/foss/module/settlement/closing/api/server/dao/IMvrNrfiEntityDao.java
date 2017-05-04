package com.deppon.foss.module.settlement.closing.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrNrfiEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfiDto;

/**
 * 始发专线往来月报表Dao
 * 
 * @author 163576-foss-guxinhua
 * @date 2013-11-6 下午5:56:31
 */
public interface IMvrNrfiEntityDao {

	/**
	 * 查询始发专线往来月报表
	 * 
	 * @author 163576-foss-guxinhua
	 * @date 2013-11-6 下午5:56:31
	 * @param dto
	 * @return
	 */
	List<MvrNrfiEntity> selectColumnsByConditions(MvrNrfiDto dto, int start,
			int limit);

	/**
	 * 查询始发专线往来月报表合计
	 * 
	 * @author 163576-foss-guxinhua
	 * @date 2013-11-6 下午5:56:31
	 * @param dto
	 * @return
	 */
	MvrNrfiEntity selectTotalByConditions(MvrNrfiDto dto);
}

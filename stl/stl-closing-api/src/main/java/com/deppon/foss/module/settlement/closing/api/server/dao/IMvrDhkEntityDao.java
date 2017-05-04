package com.deppon.foss.module.settlement.closing.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrDhkEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrDhkDto;

/**
 * 代汇款月报表Dao
 * 
 * @author 163576-foss-guxinhua
 * @date 2013-11-6 下午5:56:31
 */
public interface IMvrDhkEntityDao {

	/**
	 * 查询代汇款月报表
	 * 
	 * @author 163576-foss-guxinhua
	 * @date 2013-11-6 下午5:56:31
	 * @param dto
	 * @return
	 */
    List<MvrDhkEntity> selectByConditions(MvrDhkDto dto, int start, int limit);
    
	/**
	 * 查询代汇款月报表合计
	 * 
	 * @author 163576-foss-guxinhua
	 * @date 2013-11-6 下午5:56:31
	 * @param dto
	 * @return
	 */
    MvrDhkDto selectTotalByConditions(MvrDhkDto dto);
}

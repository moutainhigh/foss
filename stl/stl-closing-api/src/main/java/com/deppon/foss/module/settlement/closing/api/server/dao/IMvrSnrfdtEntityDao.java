package com.deppon.foss.module.settlement.closing.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrNrfdtEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfdtDto;

/**
 * 02到达月报表Dao
 * 
 * @author 132599-foss-shenweihua
 * @date 2014-06-12 下午5:56:31
 */
public interface IMvrSnrfdtEntityDao {
	/**
	 * 查询02到达月报表
	 * 
	 * @author 132599-foss-shenweihua
	 * @date 2014-06-12 下午5:56:31
	 * @param dto
	 * @return
	 */
    List<MvrNrfdtEntity> selectByConditions(MvrNrfdtDto dto, int start, int limit);
    
	/**
	 * 查询02到达月报表合计
	 * 
	 * @author 132599-foss-shenweihua
	 * @date 2014-06-12 下午5:56:31
	 * @param dto
	 * @return
	 */
    MvrNrfdtDto selectTotalByConditions(MvrNrfdtDto dto);

}

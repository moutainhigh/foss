package com.deppon.foss.module.settlement.closing.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrRfdEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.ClosingPeriodDto;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrRfdDto;

/**
 * 专线到达DAO.
 *
 * @author ibm-zhuwei
 * @date 2013-3-5 下午6:16:06
 */
public interface IMvrRfdEntityDao {

	/**
	 * 查询专线到达报表
	 * 
	 * @author guxinhua
	 * @date 2013-3-6 上午11:21:20
	 * @param dto
	 * @return
	 */
    List<MvrRfdEntity> selectByConditions(MvrRfdDto dto, int start, int limit);
    
	/**
	 * 查询专线到达报表合计
	 * 
	 * @author guxinhua
	 * @date 2013-3-6 上午11:21:20
	 * @param dto
	 * @return
	 */
    MvrRfdDto selectTotalByConditions(MvrRfdDto dto);
    
    List<ClosingPeriodDto> queryClosingPeriodList();

}

package com.deppon.foss.module.settlement.closing.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrOrccEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrOrccQueryDto;

public interface IMvrOrccDao {
	 /**
	 * 根据多个参数查询始发外请车月报表分页
	 * @author lianghaisheng
	 * @date 2014-05-19
	 * @param 
	 * @return
	 * @see
	 */
	List<MvrOrccEntity> selectMvrOrccByConditions(MvrOrccQueryDto mvrOrccDto,int start,int limit);
	
	 /**
	 * 根据多个参数查询始发外请车月报表不分页
	 * @author lianghaisheng
	 * @date 2014-05-19
	 * @param 
	 * @return
	 * @see
	 */
	List<MvrOrccEntity> selectMvrOrccByConditions(MvrOrccQueryDto mvrOrccDto);
	
	 /**
	 * 根据多个参数查询始发外请车条数
	 * @author lianghaisheng
	 * @date 2014-05-19
	 * @param 
	 * @return
	 * @see
	 */
	Long selectMvrOrccByConditionsCount(MvrOrccQueryDto mvrOrccDto);

	 /**
	 * 根据多个参数查询始发外请车汇总
	 * @author lianghaisheng
	 * @date 2014-05-19
	 * @param 
	 * @return
	 * @see
	 */
	MvrOrccEntity selectMvrOrccByConditionsSum(MvrOrccQueryDto mvrOrccDto);

}

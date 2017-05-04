package com.deppon.foss.module.settlement.closing.api.server.dao;

import java.util.List;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrOrccIEntity;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrPliEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrOrcciQueryDto;

public interface IMvrOrcciDao {
	 /**
	 * 根据多个参数查询始发外请车月报表分页
	 * @author lianghaisheng
	 * @date 2014-05-19
	 * @param 
	 * @return
	 * @see
	 */
	List<MvrOrccIEntity> selectMvrOrcciByConditions(MvrOrcciQueryDto mvrOrccDto,int start,int limit);
	
	 /**
	 * 根据多个参数查询始发外请车月报表不分页
	 * @author lianghaisheng
	 * @date 2014-05-19
	 * @param 
	 * @return
	 * @see
	 */
	List<MvrOrccIEntity> selectMvrOrcciByConditions(MvrOrcciQueryDto mvrOrccDto);
	
	 /**
	 * 根据多个参数查询始发外请车条数
	 * @author lianghaisheng
	 * @date 2014-05-19
	 * @param 
	 * @return
	 * @see
	 */
	Long selectMvrOrcciByConditionsCount(MvrOrcciQueryDto mvrOrccDto);

	 /**
	 * 根据多个参数查询始发外请车汇总
	 * @author lianghaisheng
	 * @date 2014-05-19
	 * @param 
	 * @return
	 * @see
	 */
	MvrPliEntity selectMvrOrcciByConditionsSum(MvrOrcciQueryDto mvrOrccDto);

}

package com.deppon.foss.module.settlement.closing.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrPliEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrPliDto;

/**
 * 始发偏线往来DAO
 * @author ibm-zhuwei
 * @date 2013-3-5 下午6:16:35
 */
public interface IMvrPliEntityDao {

	 /**
		 * 根据多个参数查询始发偏线信息
		 * @author foss-pengzhen
		 * @date 2013-3-6 上午11:17:17
		 * @param 
		 * @return
		 * @see
		 */
		List<MvrPliEntity> selectMvrPliByConditions(MvrPliDto mvrPliDto,int start,int limit);
		
		/**
		 * 根据多个参数查询始发偏线信息.(不分页)
		 *
		 * @param mvrPliDto the mvr pli dto
		 * @return the list
		 * @author foss-pengzhen
		 * @date 2013-3-6 上午11:17:17
		 * @see
		 */
		List<MvrPliEntity> selectMvrPliByConditions(MvrPliDto mvrPliDto);
		
		/**
		 * 根据多个参数查询始发偏线信息总数
		 * @author foss-pengzhen
		 * @date 2013-3-6 上午11:17:17
		 * @param 
		 * @return
		 * @see
		 */
		Long selectMvrPliByConditionsCount(MvrPliDto mvrPliDto);

		/**
		 * 根据多个参数查询统计始发偏线信息总数.
		 *
		 * @param mvrPliDto the mvr pli dto
		 * @return the mvr pli entity
		 * @author foss-pengzhen
		 * @date 2013-3-6 上午11:17:17
		 * @see
		 */
		MvrPliEntity selectMvrPliByConditionsSum(MvrPliDto mvrPliDto);
}

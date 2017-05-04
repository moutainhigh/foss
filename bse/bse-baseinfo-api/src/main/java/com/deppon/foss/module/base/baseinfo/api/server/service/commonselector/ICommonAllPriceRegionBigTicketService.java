package com.deppon.foss.module.base.baseinfo.api.server.service.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.PriceRegionEntity;
/**
 *公共组件--价格区域和
 * @author shenweihua
 * @date 2014-07-4 上午9:27:10
 */
public interface ICommonAllPriceRegionBigTicketService {
		/**
		 * .
		 * <p>
		 * 根据条件查询区域信息<br/>
		 * （分页） 方法名：searchRegionBigTicketByCondition
		 * </p>
		 * 
		 * @param regionEntity
		 *            查询条件
		 * @param start
		 *            其实查询位置
		 * @param limit
		 *            每页几条
		 * @author shenweihua
		 * @时间 2014-07-4
		 * @since JDK1.6
		 */
		public List<PriceRegionEntity> searchRegionByCondition(
				PriceRegionEntity regionEntity, int start, int limit);
		/**
		 * .
		 * <p>
		 * 根据条件查询区域信息个数<br/>
		 * 方法名：countRegionBigTicketByCondition
		 * </p>
		 * 
		 * @param regionEntity
		 *            查询条件
		 * @author shenweihua
		 * @时间 2014-07-4
		 * @since JDK1.6
		 */
		Long countRegionByCondition(PriceRegionEntity regionEntity);
}

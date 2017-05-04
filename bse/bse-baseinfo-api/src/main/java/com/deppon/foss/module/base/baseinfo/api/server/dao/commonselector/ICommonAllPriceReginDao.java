package com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.PriceRegionEntity;

public interface ICommonAllPriceReginDao {
	/**.
	 * <p>
	 * 根据条件查询区域信息<br/>（分页）
	 * 方法名：searchRegionByCondition
	 * </p>
	 * @param regionEntity 查询条件
	 * @param start 其实查询位置
	 * @param limit 每页几条
	 * @author lifanghong
	 * @时间 2013-08-21
	 * @since JDK1.6
	 */
	 List<PriceRegionEntity> searchRegionByCondition(PriceRegionEntity regionEntity,
			int start, int limit);
	/**.
	 * <p>
	 * 根据条件查询区域信息个数<br/>
	 * 方法名：countRegionByCondition
	 * </p>
	 * @param regionEntity 查询条件
	 * @author lifanghong
	 * @时间 2013-08-21
	 * @since JDK1.6
	 */
	Long countRegionByCondition(
			PriceRegionEntity regionEntity);
}

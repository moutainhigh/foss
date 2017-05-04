package com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.PriceRegionEntity;
/**
 *公共组件--首续重价格区域
 */
public interface ICommonPriceRegionEcGoodsDao {
	/**
	 * 根据条件查询大票区域信息
	 */
	public List<PriceRegionEntity> searchRegionEcGoodsByCondition(
			PriceRegionEntity regionEntity, int start, int limit);
	/**
	 * 根据条件查询区域信息个数
	 */
	Long countRegionEcGoodsByCondition(PriceRegionEntity regionEntity);
}

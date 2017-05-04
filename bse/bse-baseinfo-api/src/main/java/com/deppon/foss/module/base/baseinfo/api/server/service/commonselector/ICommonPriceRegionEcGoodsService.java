package com.deppon.foss.module.base.baseinfo.api.server.service.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.PriceRegionEntity;
/**
 *公共组件--首续重价格区域
 */
public interface ICommonPriceRegionEcGoodsService {
	
	public List<PriceRegionEntity> searchRegionEcGoodsByCondition(
			PriceRegionEntity regionEntity, int start, int limit);

	Long countRegionEcGoodsByCondition(PriceRegionEntity regionEntity);
}

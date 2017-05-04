package com.deppon.foss.module.base.baseinfo.api.server.service.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.PriceRegionEntity;
/**
 *公共组件--首续重目的地区域
 */
public interface ICommonAllPriceRegionEcGoodsService {
		/**
		 * 根据条件查询区域信息
		 */
		public List<PriceRegionEntity> searchRegionByCondition(PriceRegionEntity regionEntity, int start, int limit);
		/**
		 * 根据条件查询区域信息个数
		 */
		Long countRegionByCondition(PriceRegionEntity regionEntity);
}

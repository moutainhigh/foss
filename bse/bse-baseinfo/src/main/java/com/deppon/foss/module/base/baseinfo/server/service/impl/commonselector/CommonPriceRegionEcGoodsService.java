package com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonPriceRegionEcGoodsDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonPriceRegionEcGoodsService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PriceRegionEntity;
/**
 * 公共组件查询-首续重区域实现.
 */
public class CommonPriceRegionEcGoodsService implements ICommonPriceRegionEcGoodsService{
	
	private ICommonPriceRegionEcGoodsDao commonPriceRegionEcGoodsDao;
	public void setCommonPriceRegionEcGoodsDao(ICommonPriceRegionEcGoodsDao commonPriceRegionEcGoodsDao) {
		this.commonPriceRegionEcGoodsDao = commonPriceRegionEcGoodsDao;
	}
	
	/**
	 * 实体查询方法.
	 */
	@Override
	public List<PriceRegionEntity> searchRegionEcGoodsByCondition(PriceRegionEntity regionEntity, int start, int limit) {
		return commonPriceRegionEcGoodsDao.searchRegionEcGoodsByCondition(regionEntity, start, limit);
	}
	
	/**
	 * 查询条数.
	 */
	@Override
	public Long countRegionEcGoodsByCondition(PriceRegionEntity regionEntity) {
		return commonPriceRegionEcGoodsDao.countRegionEcGoodsByCondition(regionEntity);
	}
}

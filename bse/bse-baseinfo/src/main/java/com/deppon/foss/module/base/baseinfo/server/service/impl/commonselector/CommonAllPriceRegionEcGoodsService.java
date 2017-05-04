package com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonAllPriceRegionEcGoodsDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonAllPriceRegionEcGoodsService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PriceRegionEntity;

public class CommonAllPriceRegionEcGoodsService implements ICommonAllPriceRegionEcGoodsService{
	
	private ICommonAllPriceRegionEcGoodsDao commonAllPriceRegionEcGoodsDao;
	public void setCommonAllPriceRegionEcGoodsDao(
			ICommonAllPriceRegionEcGoodsDao commonAllPriceRegionEcGoodsDao) {
		this.commonAllPriceRegionEcGoodsDao = commonAllPriceRegionEcGoodsDao;
	}
	
	/**
	 * 实体查询方法.
	 */
	@Override
	public List<PriceRegionEntity> searchRegionByCondition(PriceRegionEntity regionEntity, int start, int limit) {
		return commonAllPriceRegionEcGoodsDao.searchRegionByCondition(regionEntity, start, limit);
	}
	/**
	 * 查询条数.
	 */
	@Override
	public Long countRegionByCondition(PriceRegionEntity regionEntity) {
		return commonAllPriceRegionEcGoodsDao.countRegionByCondition(regionEntity);
	}

}

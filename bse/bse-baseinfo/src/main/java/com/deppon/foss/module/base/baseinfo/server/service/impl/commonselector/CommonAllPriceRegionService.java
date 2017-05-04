package com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonAllPriceReginDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonAllPriceRegionService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PriceRegionEntity;

public class CommonAllPriceRegionService implements
		ICommonAllPriceRegionService {
	
	/** The common price region dao. */
	private ICommonAllPriceReginDao commonAllPriceRegionDao;
	
	/**
	 * 实体查询方法.
	 *
	 * @param regionEntity the region entity
	 * @param start the start
	 * @param limit the limit
	 * @return the list
	 * @author lifanghong
	 * @date 2013-08-21 上午10:53:02
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonPriceRegionService#searchRegionByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.PriceRegionEntity, int, int)
	 */
	@Override
	public List<PriceRegionEntity> searchRegionByCondition(
			PriceRegionEntity regionEntity, int start, int limit) {
		return commonAllPriceRegionDao.searchRegionByCondition(
				regionEntity, start, limit);
	}

	/**
	 * 查询条数.
	 *
	 * @param regionEntity the region entity
	 * @return the long
	 * @author lifanghong
	 * @date 2013-08-21 上午10:53:02
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonPriceRegionService#countRegionByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.PriceRegionEntity)
	 */
	@Override
	public Long countRegionByCondition(PriceRegionEntity regionEntity) {
		return commonAllPriceRegionDao.countRegionByCondition(regionEntity);
	}

	public void setCommonAllPriceRegionDao(
			ICommonAllPriceReginDao commonAllPriceRegionDao) {
		this.commonAllPriceRegionDao = commonAllPriceRegionDao;
	}


}

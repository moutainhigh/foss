package com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonAllPriceRegionBigTicketDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonAllPriceRegionBigTicketService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PriceRegionEntity;

public class CommonAllPriceRegionBigTicketService implements ICommonAllPriceRegionBigTicketService{
	/** The common price region dao. */
	private ICommonAllPriceRegionBigTicketDao commonAllPriceRegionBigTicketDao;
	/**
	 * 实体查询方法.
	 *
	 * @param regionEntity the region entity
	 * @param start the start
	 * @param limit the limit
	 * @return the list
	 * @author shenweihua
	 * @date 2014-07-4 上午10:53:02
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonPriceRegionService#searchRegionByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.PriceRegionEntity, int, int)
	 */
	@Override
	public List<PriceRegionEntity> searchRegionByCondition(
			PriceRegionEntity regionEntity, int start, int limit) {
		return commonAllPriceRegionBigTicketDao.searchRegionByCondition(
				regionEntity, start, limit);
	}
	/**
	 * 查询条数.
	 *
	 * @param regionEntity the region entity
	 * @return the long
	 * @author shenweihua
	 * @date 2014-07-4 上午10:53:02
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonPriceRegionService#countRegionByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.PriceRegionEntity)
	 */
	@Override
	public Long countRegionByCondition(PriceRegionEntity regionEntity) {
		return commonAllPriceRegionBigTicketDao.countRegionByCondition(regionEntity);
	}
	
	/**
	 * 设置大票目的区域dao
	 * @param commonAllPriceRegionBigTicketDao
	 */
	public void setCommonAllPriceRegionBigTicketDao(
			ICommonAllPriceRegionBigTicketDao commonAllPriceRegionBigTicketDao) {
		this.commonAllPriceRegionBigTicketDao = commonAllPriceRegionBigTicketDao;
	}
	
}

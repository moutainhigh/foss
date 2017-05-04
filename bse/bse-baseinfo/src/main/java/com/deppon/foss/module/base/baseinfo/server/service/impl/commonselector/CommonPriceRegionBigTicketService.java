package com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonPriceRegionBigTicketDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonPriceRegionBigTicketService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PriceRegionEntity;
/**
 * 公共组件查询-大票区域实现.
 *
 * @author shenweihua
 * @date 2014-07-3 上午10:53:02
 */
public class CommonPriceRegionBigTicketService implements ICommonPriceRegionBigTicketService{
	/** The common price region dao. */
	private ICommonPriceRegionBigTicketDao commonPriceRegionBigTicketDao;
	
	/**
	 * 实体查询方法.
	 * @param regionEntity the region entity
	 * @param start the start
	 * @param limit the limit
	 * @return the list
	 * @author shenweihua
	 * @date 2014-07-3 上午10:53:02
	 */
	@Override
	public List<PriceRegionEntity> searchRegionBigTicketByCondition(
			PriceRegionEntity regionEntity, int start, int limit) {
		return commonPriceRegionBigTicketDao.searchRegionBigTicketByCondition(
				regionEntity, start, limit);
	}
	
	/**
	 * 查询条数.
	 * @param regionEntity the region entity
	 * @param start the start
	 * @param limit the limit
	 * @return the list
	 * @author shenweihua
	 * @date 2014-07-3 上午10:53:02
	 */
	@Override
	public Long countRegionBigTicketByCondition(PriceRegionEntity regionEntity) {
		return commonPriceRegionBigTicketDao.countRegionBigTicketByCondition(regionEntity);
	}
	
	/**
	 * 获取大票区域dao
	 * @param commonPriceRegionBigTicketDao
	 */
	public void setCommonPriceRegionBigTicketDao(
			ICommonPriceRegionBigTicketDao commonPriceRegionBigTicketDao) {
		this.commonPriceRegionBigTicketDao = commonPriceRegionBigTicketDao;
	}
	
	

}

package com.deppon.foss.module.pickup.waybill.server.fetcher;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.server.components.sync.ISyncDataDao;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionExpressEntity;

/**
 * @author ibm-foss-sxw
 *
 */@Service
 @Transactional
public class PriceRegionExpressFetcher  extends AbstractSyncDataFetcherWaybill<PriceRegionExpressEntity> {
	/**
	 * 数据抓取Dao接口
	 */
	private ISyncDataDao<PriceRegionExpressEntity> syncDataDao;
	
	/**
	 * @功能：获取数据同步dao接口 
	 *  
	 */
	public ISyncDataDao<PriceRegionExpressEntity> getSyncDataDao() {
		return syncDataDao;
	}
	/**
	 * 设置数据抓取Dao接口
	 * @param syncDataDao
	 */
	@Resource(name="syncPriceRegionExpress")
	public void setSyncDataDao(ISyncDataDao<PriceRegionExpressEntity> syncDataDao) {
		this.syncDataDao = syncDataDao;
	}

}
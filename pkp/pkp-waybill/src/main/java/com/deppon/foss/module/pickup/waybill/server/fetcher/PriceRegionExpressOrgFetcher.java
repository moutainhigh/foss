package com.deppon.foss.module.pickup.waybill.server.fetcher;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.server.components.sync.ISyncDataDao;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegioOrgnExpressEntity;

/**
 * @author ibm-foss-sxw
 *
 */
@Service
@Transactional
public class PriceRegionExpressOrgFetcher  extends AbstractSyncDataFetcherWaybill<PriceRegioOrgnExpressEntity> {
	/**
	 * 数据抓取Dao接口
	 */
	private ISyncDataDao<PriceRegioOrgnExpressEntity> syncDataDao;
	
	/**
	 * @功能：获取数据同步dao接口 
	 *  
	 */
	public ISyncDataDao<PriceRegioOrgnExpressEntity> getSyncDataDao() {
		return syncDataDao;
	}
	/**
	 * 设置数据抓取Dao接口
	 * @param syncDataDao
	 */
	@Resource(name="syncPriceRegionExpressOrg")
	public void setSyncDataDao(ISyncDataDao<PriceRegioOrgnExpressEntity> syncDataDao) {
		this.syncDataDao = syncDataDao;
	}

}

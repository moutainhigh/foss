
package com.deppon.foss.module.pickup.waybill.server.fetcher;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.server.components.sync.ISyncDataDao;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionArriveEntity;

/**
 * 到达区域获取类
 * @Description: BUG-55198
 * @author deppon-157229-zxy
 * @date 2013-10-10 上午10:59:47
 */
@Service
@Transactional 
public class SrvPriceArriveRegionFetcher extends AbstractSyncDataFetcherWaybill<PriceRegionArriveEntity>{

	/**
	 * 数据抓取Dao接口
	 */
	@Resource(name="syncPriceRegionArrive")
	private ISyncDataDao<PriceRegionArriveEntity> syncDataDao;
	
	
	@Override
	public ISyncDataDao<PriceRegionArriveEntity> getSyncDataDao() {
		return syncDataDao;
	}

}

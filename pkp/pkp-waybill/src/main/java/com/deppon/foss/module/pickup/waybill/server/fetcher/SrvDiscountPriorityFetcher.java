
package com.deppon.foss.module.pickup.waybill.server.fetcher;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.server.components.sync.ISyncDataDao;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.DiscountPriorityEntity;

/**
 * 折扣优先级获取类
 * @Description: BUG-55198
 * @author deppon-157229-zxy
 * @date 2013-10-12
 */
@Service
@Transactional 
public class SrvDiscountPriorityFetcher extends AbstractSyncDataFetcherWaybill<DiscountPriorityEntity>{

	/**
	 * 数据抓取Dao接口
	 */
	@Resource(name="syncDiscountPriority")
	private ISyncDataDao<DiscountPriorityEntity> syncDataDao;
	
	
	@Override
	public ISyncDataDao<DiscountPriorityEntity> getSyncDataDao() {
		return syncDataDao;
	}

}

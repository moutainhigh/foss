package com.deppon.foss.module.pickup.waybill.server.fetcher;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.server.components.sync.ISyncDataDao;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.MinFeePlanEntity;

/**
 * 最低一票获取类
 * @Description: BUG-55198
 * @author deppon-157229-zxy
 * @date 2013-10-10 上午10:59:47
 */
@Service
@Transactional 
public class SrvMinfeePlanFetcher extends AbstractSyncDataFetcherWaybill<MinFeePlanEntity>{

	/**
	 * 数据抓取Dao接口
	 */
	@Resource(name="syncMinFeePlan")
	private ISyncDataDao<MinFeePlanEntity> syncDataDao;
	
	
	@Override
	public ISyncDataDao<MinFeePlanEntity> getSyncDataDao() {
		return syncDataDao;
	}

}

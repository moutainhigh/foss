package com.deppon.foss.module.pickup.waybill.server.fetcher;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.server.components.sync.ISyncDataDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BillingGroupTransFerEntity;

/**
 * @author 026123-foss-lifengteng
 *
 */
@Service
@Transactional 
public class BillingGroupTransFerFetcher  
	extends AbstractSyncDataFetcherWaybill<BillingGroupTransFerEntity> {
	/**
	 * 数据抓取Dao接口
	 */
	private ISyncDataDao<BillingGroupTransFerEntity> syncDataDao;
	
	/**
	 * @功能：获取数据同步dao接口 
	 *  
	 */
	public ISyncDataDao<BillingGroupTransFerEntity> getSyncDataDao() {
		return syncDataDao;
	}
	/**
	 * 设置数据抓取Dao接口
	 * @param syncDataDao
	 */
	@Resource(name="syncBillingGroupTransFer")
	public void setSyncDataDao(ISyncDataDao<BillingGroupTransFerEntity> syncDataDao) {
		this.syncDataDao = syncDataDao;
	}

}


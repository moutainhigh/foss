/**
 * 
 */
package com.deppon.foss.module.pickup.waybill.server.fetcher;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.server.components.sync.ISyncDataDao;
import com.deppon.foss.module.pickup.waybill.shared.dto.PricePlanExpressDto;

/**
 * @author ibm-foss-sxw
 *
 */
@Service
@Transactional
public class PricePlanExpressFetcher   extends AbstractSyncDataFetcherWaybill<PricePlanExpressDto>{
	/**
	 * 数据抓取Dao接口
	 */
	private ISyncDataDao<PricePlanExpressDto> syncDataDao;
	
	/**
	 * @功能：获取数据同步dao接口 
	 *  
	 */
	public ISyncDataDao<PricePlanExpressDto> getSyncDataDao() {
		return syncDataDao;
	}
	/**
	 * 设置数据抓取Dao接口
	 * @param syncDataDao
	 */
	@Resource(name="syncPricePlanExpress")
	public void setSyncDataDao(ISyncDataDao<PricePlanExpressDto> syncDataDao) {
		this.syncDataDao = syncDataDao;
	}

}

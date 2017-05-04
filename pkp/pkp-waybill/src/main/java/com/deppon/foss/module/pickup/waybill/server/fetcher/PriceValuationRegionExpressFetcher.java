/**
 * 
 */
package com.deppon.foss.module.pickup.waybill.server.fetcher;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.server.components.sync.ISyncDataDao;
import com.deppon.foss.module.pickup.waybill.shared.dto.PriceValuationRegionExpressDto;

/**
 * @author ibm-foss-sxw
 *
 */
@Service
@Transactional
public class PriceValuationRegionExpressFetcher extends AbstractSyncDataFetcherWaybill<PriceValuationRegionExpressDto> {
	/**
	 * 数据抓取Dao接口
	 */
	private ISyncDataDao<PriceValuationRegionExpressDto> syncDataDao;
	
	/**
	 * @功能：获取数据同步dao接口 
	 *  
	 */
	public ISyncDataDao<PriceValuationRegionExpressDto> getSyncDataDao() {
		
		return syncDataDao;
	}
	/**
	 * 设置数据抓取Dao接口
	 * @param syncDataDao
	 */
	@Resource(name="syncPriceValuationRegionExpress")
	public void setSyncDataDao(ISyncDataDao<PriceValuationRegionExpressDto> syncDataDao) {
		this.syncDataDao = syncDataDao;
	}

}

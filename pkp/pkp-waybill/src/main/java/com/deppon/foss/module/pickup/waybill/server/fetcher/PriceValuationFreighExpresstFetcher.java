/**
 * 
 */
package com.deppon.foss.module.pickup.waybill.server.fetcher;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.server.components.sync.ISyncDataDao;
import com.deppon.foss.module.pickup.waybill.shared.dto.PriceValuationFreightExpressDto;

/**
 * @author ibm-foss-sxw
 *
 */
@Service
@Transactional
public class PriceValuationFreighExpresstFetcher extends AbstractSyncDataFetcherWaybill<PriceValuationFreightExpressDto> {
	/**
	 * 数据抓取Dao接口
	 */
	private ISyncDataDao<PriceValuationFreightExpressDto> syncDataDao;
	
	/**
	 * @功能：获取数据同步dao接口 
	 *  
	 */
	public ISyncDataDao<PriceValuationFreightExpressDto> getSyncDataDao() {
		 
		return syncDataDao;
	}
	/**
	 * 设置数据抓取Dao接口
	 * @param syncDataDao
	 */
	@Resource(name="syncPriceValuationFreightExpress")
	public void setSyncDataDao(ISyncDataDao<PriceValuationFreightExpressDto> syncDataDao) {
		this.syncDataDao = syncDataDao;
	}

}

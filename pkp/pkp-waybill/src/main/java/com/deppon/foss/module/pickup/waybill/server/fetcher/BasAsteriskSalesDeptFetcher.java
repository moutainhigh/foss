package com.deppon.foss.module.pickup.waybill.server.fetcher;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.server.components.sync.ISyncDataDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AsteriskSalesDeptEntity;


/**
 * @author 026123-foss-lifengteng
 *
 */
@Service
@Transactional 
public class BasAsteriskSalesDeptFetcher extends AbstractSyncDataFetcherWaybill<AsteriskSalesDeptEntity> {
	/**
	 * 数据抓取Dao接口
	 */
	private ISyncDataDao<AsteriskSalesDeptEntity> syncDataDao;
	
	/**
	 * @功能：获取数据同步dao接口 
	 *  
	 */
	public ISyncDataDao<AsteriskSalesDeptEntity> getSyncDataDao() {
		return syncDataDao;
	}
	/**
	 * 设置数据抓取Dao接口
	 * @param syncDataDao
	 */
	@Resource(name="syncAsteriskSalesDept")
	public void setSyncDataDao(ISyncDataDao<AsteriskSalesDeptEntity> syncDataDao) {
		this.syncDataDao = syncDataDao;
	}

}

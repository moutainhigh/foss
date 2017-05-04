package com.deppon.foss.module.pickup.waybill.server.fetcher;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.server.components.sync.ISyncDataDao;
import com.deppon.foss.module.pickup.waybill.shared.dto.NetGroupMixEntityDto;

/**
 * @author 026123-foss-lifengteng
 *
 */
@Service
@Transactional 
public class NetGroupMixDtoFetcher   extends AbstractSyncDataFetcherWaybill<NetGroupMixEntityDto> {
	/**
	 * 数据抓取Dao接口
	 */
	private ISyncDataDao<NetGroupMixEntityDto> syncDataDao;
	
	/**
	 * @功能：获取数据同步dao接口 
	 *  
	 */
	public ISyncDataDao<NetGroupMixEntityDto> getSyncDataDao() {
		return syncDataDao;
	}
	/**
	 * 设置数据抓取Dao接口
	 * @param syncDataDao
	 */
	@Resource(name="syncNetGroupMixDto")
	public void setSyncDataDao(ISyncDataDao<NetGroupMixEntityDto> syncDataDao) {
		this.syncDataDao = syncDataDao;
	}

}
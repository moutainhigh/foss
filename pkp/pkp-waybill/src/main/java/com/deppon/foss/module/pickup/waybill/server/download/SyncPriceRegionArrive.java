
package com.deppon.foss.module.pickup.waybill.server.download;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.deppon.foss.base.util.ClientUpdateDataPack;
import com.deppon.foss.base.util.DataBundle;
import com.deppon.foss.framework.entity.SyncDataRequest;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.framework.server.components.sync.ISyncDataDao;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPriceDownLoadService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionArriveEntity;

/**
 * 同步到达区域
 * @Description: BUG-55198
 * @author deppon-157229-zxy
 * @date 2013-10-10 上午10:59:47
 */
public class SyncPriceRegionArrive extends iBatis3DaoImpl implements ISyncDataDao<PriceRegionArriveEntity>{

	/**
	 *  baseinfo 下载服务
	 */
	private IPriceDownLoadService priceDownLoadService;
	
	@Override
	public List<PriceRegionArriveEntity> getSyncData(Date fromDate, String userID,
			String regionID, String pagionation, int page) {
		DataBundle data;
		List<PriceRegionArriveEntity> list = new ArrayList<PriceRegionArriveEntity>();
		ClientUpdateDataPack clientInfo = new ClientUpdateDataPack();
		clientInfo.setLastUpdateTime(fromDate);
		clientInfo.setEmpCode(userID);
		clientInfo.setOrgCode(FossUserContext.getCurrentDeptCode());
		data = priceDownLoadService.downPriceRegionArriveData(clientInfo);
		if(data != null){
			if(data.getObject() != null){
				list = ((List<PriceRegionArriveEntity>)data.getObject());
			}
		}
		return  list;
	}

	@Override
	public Date getLastModifyTime() {
		return null; 
	}

	@Override
	public List<PriceRegionArriveEntity> getSyncData(SyncDataRequest request) {
		return null;
	}

	public IPriceDownLoadService getPriceDownLoadService() {
		return priceDownLoadService;
	}

	public void setPriceDownLoadService(IPriceDownLoadService priceDownLoadService) {
		this.priceDownLoadService = priceDownLoadService;
	}

}

/**
 * 
 */
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
import com.deppon.foss.module.pickup.pricing.api.server.service.IPriceDownLoadExpressService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegioOrgnExpressEntity;

/**
 * @author ibm-foss-sxw
 *
 */
public class SyncPriceRegionExpressOrg extends iBatis3DaoImpl implements ISyncDataDao<PriceRegioOrgnExpressEntity> {
	/**
	 *  价格 下载服务
	 */
	private IPriceDownLoadExpressService priceDownLoadExpressService;
	/**
	 * 返回数据列表
	 */
	/**
	 * @功能：同步数据
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getSyncData(Date fromDate,
			String empCode, String regionID,String pagionation, int page) {
		List<PriceRegioOrgnExpressEntity> list = new ArrayList<PriceRegioOrgnExpressEntity>();
		
		DataBundle data;
		ClientUpdateDataPack clientInfo = new ClientUpdateDataPack();
		clientInfo.setLastUpdateTime(fromDate);
		clientInfo.setEmpCode(empCode);
		clientInfo.setOrgCode(FossUserContext.getCurrentDeptCode());
		data = priceDownLoadExpressService.downPriceRegionExpressOrgDetailServerData(clientInfo);
		if(data != null){
			if(data.getObject()!=null){
				list = ((List<PriceRegioOrgnExpressEntity>)data.getObject());
			}
		}
		return  list;
	}

	/**
	 * @功能：获取最后跟新时间
	 */
	@Override
	public Date getLastModifyTime() {
		return null;
	}

	

	public IPriceDownLoadExpressService getPriceDownLoadExpressService() {
		return priceDownLoadExpressService;
	}

	public void setPriceDownLoadExpressService(
			IPriceDownLoadExpressService priceDownLoadExpressService) {
		this.priceDownLoadExpressService = priceDownLoadExpressService;
	}

	/** 同步
	 * @see com.deppon.foss.framework.server.components.sync.ISyncDataDao#getSyncData(com.deppon.foss.framework.entity.SyncDataRequest)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getSyncData(SyncDataRequest request) {
		return null;
	}
}

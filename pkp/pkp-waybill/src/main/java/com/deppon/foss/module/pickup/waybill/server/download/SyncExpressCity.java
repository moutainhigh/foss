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
import com.deppon.foss.module.base.baseinfo.api.server.service.IBaseInfoExpressDownloadService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressCityEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;

/**
 * @author 026123-foss-lifengteng
 *
 */
public class SyncExpressCity   extends iBatis3DaoImpl implements ISyncDataDao<ExpressCityEntity> {

	/**
	 * baseinfo 下载服务
	 */
	private IBaseInfoExpressDownloadService baseInfoExpressDownloadService;
	/**
	 * 返回数据列表
	 */
	
	/**
	 * @功能：同步数据
	 */
	@SuppressWarnings("unchecked")
	public List<ExpressCityEntity> getSyncData(Date fromDate,
			String empCode, String regionID,String pagionation, int page) {
		List<ExpressCityEntity> list = new ArrayList<ExpressCityEntity>();
		
		DataBundle data;
		ClientUpdateDataPack clientInfo = new ClientUpdateDataPack();
		clientInfo.setLastUpdateTime(fromDate);
		clientInfo.setEmpCode(empCode);
		clientInfo.setPagination(pagionation);
		clientInfo.setSyncPage(page);
		clientInfo.setOrgCode(FossUserContext.getCurrentDeptCode());
		data = baseInfoExpressDownloadService.downloadExpressCityData(clientInfo);
		if(data != null){
			if(data.getObject() != null){
				list = ((List<ExpressCityEntity>)data.getObject());
			}
		}
		return  list;
	}

	/**
	 * @功能：获取最后跟新时间
	 */
	public Date getLastModifyTime() {
		return null; 
	}
	

	public IBaseInfoExpressDownloadService getBaseInfoExpressDownloadService() {
		return baseInfoExpressDownloadService;
	}

	public void setBaseInfoExpressDownloadService(
			IBaseInfoExpressDownloadService baseInfoExpressDownloadService) {
		this.baseInfoExpressDownloadService = baseInfoExpressDownloadService;
	}

	/** 同步
	 * @see com.deppon.foss.framework.server.components.sync.ISyncDataDao#getSyncData(com.deppon.foss.framework.entity.SyncDataRequest)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getSyncData(SyncDataRequest request) {
		return null;
	}
}

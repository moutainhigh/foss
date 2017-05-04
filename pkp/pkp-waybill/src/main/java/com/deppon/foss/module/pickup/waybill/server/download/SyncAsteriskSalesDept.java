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
import com.deppon.foss.module.base.baseinfo.api.server.service.IBaseInfoDownloadService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AsteriskSalesDeptEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;

/**
 * @author 026123-foss-lifengteng
 *
 */
public class SyncAsteriskSalesDept  extends iBatis3DaoImpl implements ISyncDataDao<AsteriskSalesDeptEntity> {
	/**
	 * baseinfo 下载服务
	 */
	private IBaseInfoDownloadService baseInfoDownloadService;
	/**o
	 * 返回数据列表
	 */
	
	/**
	 * @功能：同步数据
	 */
	@SuppressWarnings("unchecked")
	public List<AsteriskSalesDeptEntity> getSyncData(Date fromDate,
			String empCode, String regionID,String pagionation, int page) {
		List<AsteriskSalesDeptEntity> list = new ArrayList<AsteriskSalesDeptEntity>();
		
		DataBundle data;
		ClientUpdateDataPack clientInfo = new ClientUpdateDataPack();
		clientInfo.setLastUpdateTime(fromDate);
		clientInfo.setEmpCode(empCode);
		clientInfo.setOrgCode(FossUserContext.getCurrentDeptCode());
		data = baseInfoDownloadService.downloadAsteriskSalesDept(clientInfo);
		if(data != null){
			if(data.getObject() != null){
				list = ((List<AsteriskSalesDeptEntity>)data.getObject());
			}
		}
		return  list;
	}
	
	
	/** 同步 
	 * @see com.deppon.foss.framework.server.components.sync.ISyncDataDao#getSyncData(com.deppon.foss.framework.entity.SyncDataRequest)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getSyncData(SyncDataRequest request) {
		return null;
	}

	/**
	 * @功能：获取最后跟新时间
	 */
	@Override
	public Date getLastModifyTime() {
		return null; 
	}

	public IBaseInfoDownloadService getBaseInfoDownloadService() {
		return baseInfoDownloadService;
	}

	public void setBaseInfoDownloadService(
			IBaseInfoDownloadService baseInfoDownloadService) {
		this.baseInfoDownloadService = baseInfoDownloadService;
	}
	

	
}


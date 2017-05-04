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
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesDescExpandEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;

/**
 * @author 026123-foss-lifengteng
 *
 */
public class SyncSalesDescExpand  extends iBatis3DaoImpl implements ISyncDataDao<SalesDescExpandEntity> {

	/**
	 * baseinfo 下载服务
	 */
	private IBaseInfoDownloadService baseInfoDownloadService;
	/**
	 * 返回数据列表
	 */
	
	/**
	 * @功能：同步数据
	 */
	@SuppressWarnings("unchecked")
	public List<SalesDescExpandEntity> getSyncData(Date fromDate,
			String empCode, String regionID,String pagionation, int page) {
		List<SalesDescExpandEntity> list = new ArrayList<SalesDescExpandEntity>();
		
		DataBundle data;
		ClientUpdateDataPack clientInfo = new ClientUpdateDataPack();
		clientInfo.setLastUpdateTime(fromDate);
		clientInfo.setEmpCode(empCode);
		clientInfo.setPagination(pagionation);
		clientInfo.setSyncPage(page);
		clientInfo.setOrgCode(FossUserContext.getCurrentDeptCode());
		data = baseInfoDownloadService.downloadSalesDescExpandData(clientInfo);
		if(data != null){
			if(data.getObject() != null){
				list = ((List<SalesDescExpandEntity>)data.getObject());
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
	/**
	 * 返回 baseinfo 下载服务
	 * @return
	 */
	public IBaseInfoDownloadService getBaseInfoDownloadService() {
		return baseInfoDownloadService;
	}
	/**
	 * 设置 baseinfo 下载服务
	 * @param baseInfoDownloadService
	 */
	public void setBaseInfoDownloadService(
			IBaseInfoDownloadService baseInfoDownloadService) {
		this.baseInfoDownloadService = baseInfoDownloadService;
	}

	/** 同步
	 * @see com.deppon.foss.framework.server.components.sync.ISyncDataDao#getSyncData(com.deppon.foss.framework.entity.SyncDataRequest)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getSyncData(SyncDataRequest request) {
		return null;
	}
}

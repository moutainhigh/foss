/**
 * 
 */
package com.deppon.foss.module.pickup.waybill.server.download;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;

import com.deppon.foss.base.util.ClientUpdateDataPack;
import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.DataBundle;
import com.deppon.foss.framework.entity.SyncDataRequest;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.framework.server.components.sync.ISyncDataDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IBaseInfoDownloadService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.NetGroupMixEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.waybill.shared.dto.NetGroupMixEntityDto;

/**
 * @author 026123-foss-lifengteng
 *
 */
public class SyncNetGroupMixDto   extends iBatis3DaoImpl implements ISyncDataDao<NetGroupMixEntityDto> {
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
	public List<NetGroupMixEntityDto> getSyncData(Date fromDate,
			String empCode, String regionID,String pagionation, int page) {
		List<NetGroupMixEntity> list = new ArrayList<NetGroupMixEntity>();
		
		DataBundle data;
		ClientUpdateDataPack clientInfo = new ClientUpdateDataPack();
		clientInfo.setLastUpdateTime(fromDate);
		clientInfo.setEmpCode(empCode);
		clientInfo.setOrgCode(FossUserContext.getCurrentDeptCode());
		clientInfo.setPagination(pagionation);
		clientInfo.setSyncPage(page);
		data = baseInfoDownloadService.downloadNetGroupMixData(clientInfo, ComnConst.ORG_TYPE_TARGET);
		if(data != null){
			if(data.getObject() != null){
				list = ((List<NetGroupMixEntity>)data.getObject());
			}
		}
		List<NetGroupMixEntityDto> list2 =new ArrayList<NetGroupMixEntityDto>();
		if(list!=null){
			for(int i=0;i<list.size();i++){
				NetGroupMixEntity e2 =(NetGroupMixEntity) list.get(i);
				NetGroupMixEntityDto dto = new NetGroupMixEntityDto();
				try{
					PropertyUtils.copyProperties(dto, e2);
				}catch(Exception e){
					logger.info(e.getMessage());
				}
				list2.add(dto);
			}
		}
		return  list2;
	}
	
	
	/** 同步 
	 * @see com.deppon.foss.framework.server.components.sync.ISyncDataDao#getSyncData(com.deppon.foss.framework.entity.SyncDataRequest)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getSyncData(SyncDataRequest request) {
	
		return new ArrayList();
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


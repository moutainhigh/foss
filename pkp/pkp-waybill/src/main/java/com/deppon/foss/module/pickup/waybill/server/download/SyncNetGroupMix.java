/**
 * 
 */
package com.deppon.foss.module.pickup.waybill.server.download;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.deppon.foss.base.util.ClientUpdateDataPack;
import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.DataBundle;
import com.deppon.foss.framework.entity.SyncDataRequest;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.framework.server.components.sync.ISyncDataDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IBaseInfoDownloadService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.NetGroupMixEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.util.define.FossConstants;

/**
 * @author 026123-foss-lifengteng
 *
 */
public class SyncNetGroupMix  extends iBatis3DaoImpl implements ISyncDataDao<NetGroupMixEntity> {
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
	public List<NetGroupMixEntity> getSyncData(Date fromDate,
			String empCode, String regionID,String pagionation, int page) {
		List<NetGroupMixEntity> list = new ArrayList<NetGroupMixEntity>();
		
		DataBundle data;
		ClientUpdateDataPack clientInfo = new ClientUpdateDataPack();
		clientInfo.setLastUpdateTime(fromDate);
		clientInfo.setEmpCode(empCode);
		clientInfo.setOrgCode(FossUserContext.getCurrentDeptCode());
		clientInfo.setPagination(pagionation);
		clientInfo.setSyncPage(page);
		data = baseInfoDownloadService.downloadNetGroupMixData(clientInfo,  ComnConst.ORG_TYPE_SOURCE); //下载START营业部线路
		if(data != null){
			if(data.getObject() != null){
				list = ((List<NetGroupMixEntity>)data.getObject());
			}
		}
		return  list;
	}
	
	
	/** 同步 
	 * @see com.deppon.foss.framework.server.components.sync.ISyncDataDao#getSyncData(com.deppon.foss.framework.entity.SyncDataRequest)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getSyncData(SyncDataRequest request) {
		List<SyncDataRequest> list = request.getList();
		List<NetGroupMixEntity> list2 = new ArrayList<NetGroupMixEntity>();
		List returnList = new ArrayList();
		Map map = new HashMap();
		List <ClientUpdateDataPack> inputList =new ArrayList <ClientUpdateDataPack>();
		DataBundle data;
		for (Iterator<SyncDataRequest> iterator = list.iterator(); iterator.hasNext();) {
			SyncDataRequest r = (SyncDataRequest) iterator.next();
			ClientUpdateDataPack clientInfo = new ClientUpdateDataPack();
			clientInfo.setLastUpdateTime(r.getFromDate());
			clientInfo.setEmpCode(r.getUserID());
			clientInfo.setOrgCode(r.getOrgCode());
			inputList.add(clientInfo);
			
		}
		List versionList = new ArrayList();
		data = baseInfoDownloadService.downloadNetGroupMixData(inputList,  ComnConst.ORG_TYPE_SOURCE); //下载START营业部线路
		if(data != null){
			if(data.getObject() != null){
				list2 = ((List<NetGroupMixEntity>)data.getObject());
				map.put("list", list2);
			}
		}else{
		    return versionList;
		}
		List<ClientUpdateDataPack> rsultList =  data.getUpdateList();
		for (Iterator iterator = rsultList.iterator(); iterator.hasNext();) {
			ClientUpdateDataPack clientUpdateDataPack = (ClientUpdateDataPack) iterator.next();
			Map map2 = new HashMap();
			map2.put("version", clientUpdateDataPack.getLastUpdateTime());
			map2.put("orgCode", clientUpdateDataPack.getOrgCode());
			versionList.add(map2);
		}
		
		
		map.put("verionList", versionList);
		map.put("isOrgIncremet", FossConstants.YES);
		returnList.add(map);
		return returnList;
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

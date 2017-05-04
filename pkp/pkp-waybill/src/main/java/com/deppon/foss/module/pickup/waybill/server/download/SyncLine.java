/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-waybill
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/download/SyncLine.java
 * 
 * FILE NAME        	: SyncLine.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.server.download;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.deppon.foss.base.util.ClientUpdateDataPack;
import com.deppon.foss.base.util.DataBundle;
import com.deppon.foss.framework.entity.SyncDataRequest;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.framework.server.components.sync.ISyncDataDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IBaseInfoDownloadService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LineEntity;
import com.deppon.foss.util.define.FossConstants;

/**
 * 同步线路信息数据
 * @author 105089-foss-yangtong
 * @date 2012-10-23 下午4:39:26
 */
public class SyncLine extends iBatis3DaoImpl implements ISyncDataDao<LineEntity>{
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
	public List<LineEntity> getSyncData(Date fromDate,
			String empCode, String regionID,String pagionation, int page) {
		List<LineEntity> list = new ArrayList<LineEntity>();
		
		DataBundle data;
		ClientUpdateDataPack clientInfo = new ClientUpdateDataPack();
		clientInfo.setLastUpdateTime(fromDate);
		clientInfo.setEmpCode(empCode);
		clientInfo.setPagination(pagionation);
		clientInfo.setSyncPage(page);
		//clientInfo.setOrgCode(FossUserContext.getCurrentDeptCode());
		data = baseInfoDownloadService.downloadLineData(clientInfo);
		if(data != null){
			if(data.getObject() != null){
				list = ((List<LineEntity>)data.getObject());
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
		List<LineEntity> list2 = new ArrayList<LineEntity>();
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
		
		data = baseInfoDownloadService.downloadLineData(inputList);
		List versionList = new ArrayList();
		
		if(data != null){
			if(data.getObject() != null){
				list2 = ((List<LineEntity>)data.getObject());
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
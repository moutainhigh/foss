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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/download/SyncAdministrativeRegions.java
 * 
 * FILE NAME        	: SyncAdministrativeRegions.java
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
import java.util.List;

import com.deppon.foss.base.util.ClientUpdateDataPack;
import com.deppon.foss.base.util.DataBundle;
import com.deppon.foss.framework.entity.SyncDataRequest;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.framework.server.components.sync.ISyncDataDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IBaseInfoDownloadService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;

/**
 * 行政区域信息同步
 * @author 105089-foss-yangtong
 * @date 2012-10-29 下午6:16:35
 */
public class SyncAdministrativeRegions extends iBatis3DaoImpl implements ISyncDataDao<AdministrativeRegionsEntity>{
	
	/**
	 *  baseinfo 下载服务
	 */
	private IBaseInfoDownloadService baseInfoDownloadService;
	/**
	 * 返回数据列表
	 */
	
	
	/**
	 * @功能：同步数据
	 */
	@SuppressWarnings("unchecked")
	public List<AdministrativeRegionsEntity> getSyncData(Date fromDate,
			String empCode, String regionID) {
		DataBundle data;
		List<AdministrativeRegionsEntity> list = new ArrayList<AdministrativeRegionsEntity>();
		ClientUpdateDataPack clientInfo = new ClientUpdateDataPack();
		clientInfo.setLastUpdateTime(fromDate);
		clientInfo.setEmpCode(empCode);
		clientInfo.setOrgCode(FossUserContext.getCurrentDeptCode());
		data = baseInfoDownloadService.downloadDistrictData(clientInfo);
		if(data != null){
			if(data.getObject() != null){
				list = ((List<AdministrativeRegionsEntity>)data.getObject());
			}
		}
		return  list;
		 
	}
	
	/* (non-Javadoc)
	 * @see com.deppon.foss.framework.server.components.sync.ISyncDataDao#getSyncData(java.util.Date, java.lang.String, java.lang.String, java.lang.String, int)
	 */
	@SuppressWarnings("unchecked")
	public List<AdministrativeRegionsEntity> getSyncData(Date fromDate,
			String empCode, String regionID, String pagionation, int page) {
		DataBundle data;
		List<AdministrativeRegionsEntity> list = new ArrayList<AdministrativeRegionsEntity>();
		ClientUpdateDataPack clientInfo = new ClientUpdateDataPack();
		clientInfo.setLastUpdateTime(fromDate);
		clientInfo.setEmpCode(empCode);
		//zxy 20140418 MANA-2018 start 新增:增加分页
		clientInfo.setPagination(pagionation);
		clientInfo.setSyncPage(page); 
		//zxy 20140418 MANA-2018 end 新增:增加分页
		
		clientInfo.setOrgCode(FossUserContext.getCurrentDeptCode());
		data = baseInfoDownloadService.downloadDistrictData(clientInfo);
		if(data != null){
			if(data.getObject() != null){
				list = ((List<AdministrativeRegionsEntity>)data.getObject());
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

	public IBaseInfoDownloadService getBaseInfoDownloadService() {
		return baseInfoDownloadService;
	}

	public void setBaseInfoDownloadService(
			IBaseInfoDownloadService baseInfoDownloadService) {
		this.baseInfoDownloadService = baseInfoDownloadService;
	}

	/** 同步
	 * @see com.deppon.foss.framework.server.components.sync.ISyncDataDao#getSyncData(com.deppon.foss.framework.entity.SyncDataRequest)
	 */
	public List<AdministrativeRegionsEntity> getSyncData(SyncDataRequest request) {
		return null;
	}

	

}
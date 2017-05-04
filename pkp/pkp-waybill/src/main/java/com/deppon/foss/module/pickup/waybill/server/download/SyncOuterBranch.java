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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/download/SyncOuterBranch.java
 * 
 * FILE NAME        	: SyncOuterBranch.java
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
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;

/**
 * 外部网点数据同步
 * @author 105089-foss-yangtong
 * @date 2012-10-29 下午6:16:35
 */
public class SyncOuterBranch  extends iBatis3DaoImpl implements ISyncDataDao<OuterBranchEntity>{
	/**
	 * baseinfo 下载服务
	 */
	private IBaseInfoDownloadService baseInfoDownloadService;
	/**
	 * 返回数据列表
	 */
		
	/** 
     * 数据更新
     * @author dp-yangtong
     * @date 2012-10-30 下午4:13:48
     * @param entity  
     * @return
     */
	@SuppressWarnings("unchecked")
	public List<OuterBranchEntity> getSyncData(Date fromDate,
			String empCode, String regionID,String pagionation, int page) {
		List<OuterBranchEntity> list = new ArrayList<OuterBranchEntity>();
		
		DataBundle data;
		ClientUpdateDataPack clientInfo = new ClientUpdateDataPack();
		clientInfo.setLastUpdateTime(fromDate);
		clientInfo.setEmpCode(empCode);
		clientInfo.setOrgCode(null);
		clientInfo.setPagination(pagionation);
		clientInfo.setSyncPage(page);
		//clientInfo.setOrgCode(FossUserContext.getCurrentDeptCode());
		data = baseInfoDownloadService.downloadOuterBranchData(clientInfo);
		if(data != null){
			if(data.getObject() != null){
				list = ((List<OuterBranchEntity>)data.getObject());
			}
		}
		return  list;
		
	}

	/** 
     * 获取最后跟新时间
     * @author dp-yangtong
     * @date 2012-10-30 下午4:13:48
     * @param entity 
     * @return
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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getSyncData(SyncDataRequest request) {
		return null;
	}
}
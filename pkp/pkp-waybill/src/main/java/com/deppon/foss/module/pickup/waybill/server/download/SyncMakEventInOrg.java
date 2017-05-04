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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/download/SyncMakEventInOrg.java
 * 
 * FILE NAME        	: SyncMakEventInOrg.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.server.download;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.entity.SyncDataRequest;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.framework.server.components.sync.ISyncDataDao;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPriceDownLoadService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.MakEventInOrgEntity;


/**
 * 同步市场活动包含组织
 * @author 105089-foss-yangtong
 * @date 2012-10-23 下午4:39:26
 */
public class SyncMakEventInOrg extends iBatis3DaoImpl implements ISyncDataDao<MakEventInOrgEntity> {
	
	
	//private static final String NAMESPACE = "foss.pkp.MakEventInOrgEntityMapper.";
	/**
	 *  价格 下载服务
	 */
	private IPriceDownLoadService priceDownLoadService;
	/**
	 * 返回数据列表
	 */
	private List<MakEventInOrgEntity> list;
	
	/**
	 * @功能：同步数据
	 */
	//@SuppressWarnings("unchecked")
	public List<MakEventInOrgEntity> getSyncData(Date fromDate,
			String userID, String regionID,String pagionation, int page) {
 
		return  list;
		 
	}

	/**
	 * @功能：获取最后跟新时间
	 */
	public Date getLastModifyTime() {
		/*return (Date) getSqlSession().selectOne( NAMESPACE +
				"getLastUpdateTime");*/
		Date date = null;
		/*if(list != null){
			MakEventInOrgEntity makIn = (MakEventInOrgEntity)list.get(list.size()-1);
			date = makIn.getModifyDate();
		}*/
		return date; 
	}

	public IPriceDownLoadService getPriceDownLoadService() {
		return priceDownLoadService;
	}

	public void setPriceDownLoadService(IPriceDownLoadService priceDownLoadService) {
		this.priceDownLoadService = priceDownLoadService;
	}
	
	public List<MakEventInOrgEntity> getList() {
		return list;
	}

	public void setList(List<MakEventInOrgEntity> list) {
		this.list = list;
	}

	/** 同步
	 * @see com.deppon.foss.framework.server.components.sync.ISyncDataDao#getSyncData(com.deppon.foss.framework.entity.SyncDataRequest)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getSyncData(SyncDataRequest request) {
		return null;
	}
}
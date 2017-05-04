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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/download/SyncPricePlan.java
 * 
 * FILE NAME        	: SyncPricePlan.java
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
import java.util.List;
import java.util.Map;

import com.deppon.foss.base.util.ClientUpdateDataPack;
import com.deppon.foss.base.util.DataBundle;
import com.deppon.foss.framework.entity.SyncDataRequest;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.framework.server.components.sync.ISyncDataDao;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPriceDownLoadService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PricePlanEntity;
import com.deppon.foss.util.define.FossConstants;


/**
 * 同步价格方案主信息
 * @author 105089-foss-yangtong
 * @date 2012-10-23 下午4:39:26
 */
public class SyncPricePlan extends iBatis3DaoImpl implements ISyncDataDao<PricePlanEntity> {
	/**
	 *  价格 下载服务
	 */
	private IPriceDownLoadService priceDownLoadService;
	/**
	 * 返回数据列表
	 */
	/**
	 * 返回数据列表
	 */
	
	/**
	 * @功能：同步数据
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getSyncData(Date fromDate, 
			String empCode, String regionID,String pagionation, int page) {
		List<Map<String, List<PricePlanEntity>>> resultList = null;
		List<PricePlanEntity> list = new ArrayList<PricePlanEntity>();
		
		DataBundle data;
		String orgCode = FossUserContext.getCurrentDeptCode();
		ClientUpdateDataPack clientInfo = new ClientUpdateDataPack();
		clientInfo.setLastUpdateTime(fromDate);
		clientInfo.setEmpCode(empCode);
		clientInfo.setOrgCode(orgCode);
		clientInfo.setRegionId(regionID);
		data = priceDownLoadService.downPricePlanServerData(clientInfo);
		if (data != null) {
			if (data.getObject() != null) {
				list = ((List<PricePlanEntity>) data.getObject());
				if (list != null && list.size() > 0) {
					Map<String, List<PricePlanEntity>> map = new HashMap<String, List<PricePlanEntity>>();
					String needDeleteLocalData = data.getNeedDeleteLocalData();
					if (needDeleteLocalData != null
							&& !"".equals(needDeleteLocalData)) {
						//放原价格区域ID
						if (FossConstants.YES.equals(needDeleteLocalData)) {
							map.put(regionID, list);
						} else{
						    map.put(needDeleteLocalData, list);
						}
					}
					resultList = new ArrayList();
					resultList.add(map);
				}
			}
		}
		return  resultList;
	}
	
	/**
	 * @功能：获取最后跟新时间
	 */
	public Date getLastModifyTime() {
		return null;
	}

	public IPriceDownLoadService getPriceDownLoadService() {
		return priceDownLoadService;
	}

	public void setPriceDownLoadService(IPriceDownLoadService priceDownLoadService) {
		this.priceDownLoadService = priceDownLoadService;
	}


	/** 同步
	 * @see com.deppon.foss.framework.server.components.sync.ISyncDataDao#getSyncData(com.deppon.foss.framework.entity.SyncDataRequest)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getSyncData(SyncDataRequest request) {
		return null;
	}


}
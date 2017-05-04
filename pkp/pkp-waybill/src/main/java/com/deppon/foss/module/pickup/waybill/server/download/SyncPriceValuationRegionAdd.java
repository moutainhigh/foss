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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/download/SyncPriceValuationRegionAdd.java
 * 
 * FILE NAME        	: SyncPriceValuationRegionAdd.java
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
import com.deppon.foss.framework.shared.util.classes.BeanUtils;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPriceDownLoadService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceValuationEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.PriceValuationRegionAddDto;
import com.deppon.foss.util.define.FossConstants;

public class SyncPriceValuationRegionAdd extends iBatis3DaoImpl implements ISyncDataDao<PriceValuationRegionAddDto> {
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
	 * 返回数据列表
	 */
	
	/**
	 * @功能：同步数据
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getSyncData(Date fromDate,
			String empCode, String regionID,String pagionation, int page) {
		List<PriceValuationRegionAddDto> priceValuationProductAddList = new ArrayList<PriceValuationRegionAddDto> ();
		List<PriceValuationEntity> list = new ArrayList<PriceValuationEntity> ();
		List<Map<String, List<PriceValuationRegionAddDto>>> resultList = new ArrayList<Map<String, List<PriceValuationRegionAddDto>>> ();
		DataBundle data;
		ClientUpdateDataPack clientInfo = new ClientUpdateDataPack();
		String orgCode = FossUserContext.getCurrentDeptCode();
		clientInfo.setLastUpdateTime(fromDate);
		clientInfo.setEmpCode(empCode);
		clientInfo.setOrgCode(orgCode);
		clientInfo.setRegionId(regionID);
		clientInfo.setPagination(pagionation);
		clientInfo.setSyncPage(page);
		data = priceDownLoadService
				.downPricingValuationRegionValueAddServerData(clientInfo);
		if (data != null) {
			if (data.getObject() != null) {
				list = ((List<PriceValuationEntity>) data.getObject());
				if (list != null && list.size() > 0) {
					Map<String, List<PriceValuationRegionAddDto>> map = new HashMap<String, List<PriceValuationRegionAddDto>>();
					String needDeleteLocalData = data.getNeedDeleteLocalData();
					priceValuationProductAddList = new ArrayList();
					for (PriceValuationEntity entity : list) {
						PriceValuationRegionAddDto dto = new PriceValuationRegionAddDto();
						BeanUtils.copyProperties(entity, dto);
						priceValuationProductAddList.add(dto);
					}
					if (needDeleteLocalData != null
							&& !"".equals(needDeleteLocalData)) {
						// 放原时效区域ID
						if (FossConstants.YES.equals(needDeleteLocalData)) {
							map.put(regionID, priceValuationProductAddList);
						}else{
						    map.put(needDeleteLocalData,
								priceValuationProductAddList);
						}
					}
					resultList = new ArrayList();
					resultList.add(map);
				}
			}
		}
		return resultList;
	}
	
	public IPriceDownLoadService getPriceDownLoadService() {
		return priceDownLoadService;
	}

	public void setPriceDownLoadService(IPriceDownLoadService priceDownLoadService) {
		this.priceDownLoadService = priceDownLoadService;
	}

	public Date getLastModifyTime() {
		return null;
	}

	/** 同步
	 * @see com.deppon.foss.framework.server.components.sync.ISyncDataDao#getSyncData(com.deppon.foss.framework.entity.SyncDataRequest)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getSyncData(SyncDataRequest request) {
		return null;
	}
}
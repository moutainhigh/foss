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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/download/SyncPriceValuationFreight.java
 * 
 * FILE NAME        	: SyncPriceValuationFreight.java
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
import com.deppon.foss.module.pickup.waybill.shared.dto.PriceValuationFreightDto;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 *计价规则 运费信息下载
 * @author foss-105089-yangtong
 * @date 2012-10-24 上午9:28:58
 */
public class SyncPriceValuationFreight extends iBatis3DaoImpl implements ISyncDataDao<PriceValuationFreightDto> {
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
	@Override
	public List getSyncData(Date fromDate, String empCode, String regionID,String pagionation, int page) {
		List<PriceValuationFreightDto> priceValuationFreightList = new ArrayList<PriceValuationFreightDto>();
		List<PriceValuationEntity> list= new ArrayList<PriceValuationEntity>();
		List<Map<String, List<PriceValuationFreightDto>>> resultList = new ArrayList<Map<String, List<PriceValuationFreightDto>>> ();
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
				.downPricingValuationPricingServerData(clientInfo);
		if (data != null) {
			if (data.getObject() != null) {
				list = ((List<PriceValuationEntity>) data.getObject());
				if (list != null && list.size() > 0) {
					Map<String, List<PriceValuationFreightDto>> map = new HashMap<String, List<PriceValuationFreightDto>>();
					String needDeleteLocalData = data.getNeedDeleteLocalData();
					priceValuationFreightList = new ArrayList();
					for (PriceValuationEntity entity : list) {
						PriceValuationFreightDto dto = new PriceValuationFreightDto();
						BeanUtils.copyProperties(entity, dto);
						priceValuationFreightList.add(dto);
					}
					if (needDeleteLocalData != null
							&& !"".equals(needDeleteLocalData)) {
						// 放原时效区域ID
						if (FossConstants.YES.equals(needDeleteLocalData)) {
							map.put(regionID, priceValuationFreightList);
						} else
							map.put(needDeleteLocalData,
									priceValuationFreightList);
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
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
 * PROJECT NAME	: pkp-waybill-share
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/PickupToDoMsgDto.java
 * 
 * FILE NAME        	: PickupToDoMsgDto.java
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
import com.deppon.foss.framework.shared.util.classes.BeanUtils;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPriceDownLoadService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PricePlanEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.PriceAirPlanDto;

/**
 * @author 026123-foss-lifengteng
 *
 */
public class SyncPriceAirPlanDto extends  iBatis3DaoImpl implements ISyncDataDao<PriceAirPlanDto> {
	/**
	 *  价格 下载服务
	 */
	private IPriceDownLoadService priceDownLoadService;
	/**
	 * 返回数据列表
	 */
	
	
	/**
	 * @功能：同步数据
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getSyncData(Date fromDate,
			String empCode, String regionID,String pagionation, int page) {
		List<PriceAirPlanDto> list = new ArrayList<PriceAirPlanDto>();
		DataBundle data;
		ClientUpdateDataPack clientInfo = new ClientUpdateDataPack();
		clientInfo.setLastUpdateTime(fromDate);
		clientInfo.setEmpCode(empCode);
		clientInfo.setOrgCode(FossUserContext.getCurrentDeptCode());
		data = priceDownLoadService.downPriceAirPlanServerData(clientInfo);
		if(data != null){
			if(data.getObject()!=null){
				List<PricePlanEntity> list2  = ((List<PricePlanEntity>)data.getObject());
				for(int i =0 ;i <list2.size();i++){
					PricePlanEntity entity = list2.get(i);
					PriceAirPlanDto dto = new PriceAirPlanDto();
					BeanUtils.copyProperties(entity, dto);
					list.add(dto);
				}
				return  list;
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

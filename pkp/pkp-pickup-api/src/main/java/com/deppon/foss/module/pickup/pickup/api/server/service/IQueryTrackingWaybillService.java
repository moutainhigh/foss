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
 * PROJECT NAME	: pkp-pickup-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pickup/api/server/service/IQueryTrackingWaybillService.java
 * 
 * FILE NAME        	: IQueryTrackingWaybillService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pickup.api.server.service;

import java.io.InputStream;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.pickup.api.shared.domain.WaybillTrackInfoEntity;
import com.deppon.foss.module.pickup.pickup.api.shared.dto.QueryTrackingWaybillDto;
import com.deppon.foss.module.pickup.pickup.api.shared.dto.TrackingWaybillConditionDto;
import com.deppon.foss.module.pickup.pickup.api.shared.dto.TrackingWaybillDto;

/**
 * 
 * 追踪运单Service
 * 
 * @author ibm-wangfei
 * @date Dec 29, 2012 5:39:17 PM
 */
public interface IQueryTrackingWaybillService extends IService {
	
	/**
	 * 
	 * 查询追踪运单记录数
	 * 
	 * @param conditionDto
	 * @return
	 * @author ibm-wangfei
	 * @date Jan 4, 2013 3:39:38 PM
	 */
	Long queryTrackingWaybillCount(TrackingWaybillConditionDto conditionDto);
	
	/**
	 * 
	 * 运单追踪查询
	 * 
	 * @param condition
	 * @param start
	 * @param limit
	 * @return TrackingWaybillDto List
	 * @author ibm-wangfei
	 * @date Jan 4, 2013 3:16:54 PM
	 */
	List<TrackingWaybillDto> queryTrackingWaybillDtoLit(TrackingWaybillConditionDto condition, int start, int limit);
	
	/**
	 * 
	 * 新增运单追踪记录
	 * 
	 * @param waybillTrackInfoEntity
	 * @author ibm-wangfei
	 * @date Jan 4, 2013 3:11:51 PM
	 */
	void addTrackingWaybillEntity(WaybillTrackInfoEntity waybillTrackInfoEntity);
	
	/**
	 * 
	 * 根据运单号查询运单追踪记录
	 * 
	 * @param waybillNo
	 * @return queryTrackingWaybillDto
	 * @author ibm-wangfei
	 * @date Jan 4, 2013 3:12:34 PM
	 */
	QueryTrackingWaybillDto queryByWaybillNo(String waybillNo);
	//RFOSS2015041738跟踪运单增加导出功能需求  065340 liutao
	  /**
		 * 
		 * 运单跟踪导出
		 * 
		 * @author liutao
		* @date 2015-04-28上午9:42:02
		 */

		InputStream queryExport(TrackingWaybillConditionDto dto);
		
	
}
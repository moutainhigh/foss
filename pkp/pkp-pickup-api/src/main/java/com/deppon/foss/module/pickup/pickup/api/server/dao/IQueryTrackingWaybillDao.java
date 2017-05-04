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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pickup/api/server/dao/IQueryTrackingWaybillDao.java
 * 
 * FILE NAME        	: IQueryTrackingWaybillDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pickup.api.server.dao;

import java.util.List;

import com.deppon.foss.module.pickup.pickup.api.shared.domain.WaybillTrackInfoEntity;
import com.deppon.foss.module.pickup.pickup.api.shared.dto.TrackingWaybillConditionDto;
import com.deppon.foss.module.pickup.pickup.api.shared.dto.TrackingWaybillDto;

/**
 * 
 * 追踪运单Dao接口
 * @author ibm-wangfei
 * @date Dec 29, 2012 5:41:34 PM
 */
public interface IQueryTrackingWaybillDao {
	
	/**
	 * 
	 * 查询符合条件的运单记录数-接上次追踪
	 * 
	 * @param condition
	 * @return
	 * @author ibm-wangfei
	 * @date Jan 4, 2013 3:43:57 PM
	 */
	Long queryTrackingWaybillCountForBefore(TrackingWaybillConditionDto condition);
	
	/**
	 * 
	 * 查询符合条件的运单记录数-新追踪
	 * @param condition
	 * @return
	 * @author ibm-wangfei
	 * @date Jan 6, 2013 11:09:35 AM
	 */
	Long queryTrackingWaybillCountForNew(TrackingWaybillConditionDto condition);
	
	/**
	 * 
	 * 查询符合条件的运单记录Dto列表-接上次追踪
	 *  
	 * @param condition
	 * @param start
	 * @param limit
	 * @return
	 * @author ibm-wangfei
	 * @date Jan 4, 2013 3:44:24 PM
	 */
	List<TrackingWaybillDto> queryTrackingWaybillDtoListForBefore(TrackingWaybillConditionDto condition, int start, int limit);
	
	/**
	 * 
	 * 查询符合条件的运单记录Dto列表-新追踪
	 * @param condition
	 * @param start
	 * @param limit
	 * @return
	 * @author ibm-wangfei
	 * @date Jan 6, 2013 11:10:01 AM
	 */
	List<TrackingWaybillDto> queryTrackingWaybillDtoListForNew(TrackingWaybillConditionDto condition, int start, int limit);

	/**
	 * 
	 * 新增运单追踪记录
	 * @param waybillTrackInfoEntity
	 * @return
	 * @author ibm-wangfei
	 * @date Jan 4, 2013 3:02:19 PM
	 */
	int addTrackingWaybillEntity(WaybillTrackInfoEntity waybillTrackInfoEntity);

	/**
	 * 
	 * 根据运单号，查询运单追踪记录
	 * @param waybillNo
	 * @return
	 * @author ibm-wangfei
	 * @date Jan 4, 2013 3:02:40 PM
	 */
	List<WaybillTrackInfoEntity> queryByWaybillNo(String waybillNo);
	//RFOSS2015041738跟踪运单增加导出功能需求  065340 liutao
  /**
	 * 
	 * 查询符合条件的运单记录数-全部
	 * @author liutao
	 * @date 2015-04-28上午9:42:02
	 */
	Long queryTrackingWaybillCountForAll(TrackingWaybillConditionDto condition);
	/**
	 * 
	 * 查询符合条件的运单记录Dto列表-全部
	 * @author liutao
     * @date 2015-04-28上午9:42:02
	 */
	List<TrackingWaybillDto> queryTrackingWaybillDtoListForAll(TrackingWaybillConditionDto condition, int start, int limit);
}
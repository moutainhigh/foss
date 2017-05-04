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
 * PROJECT NAME	: pkp-predeliver-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/server/service/IArriveSheetManngerService.java
 * 
 * FILE NAME        	: IArriveSheetManngerService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.server.service;
import java.util.Date;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillPdaDto;
/**
 * FOSS系统同步PDA作业时间
 * @author 159231-foss-meiying
 * @date 2014-3-6 下午3:34:15
 */
public interface IGpsPdaService extends IService {
	/**
	 * pda开单时FOSS同步数据给GPS
	 *  @author 159231-foss-meiying
	 * @date 2014-3-6 下午4:06:35
	 */
	 void pDaPickupToGps(WaybillPdaDto dto);
	 /**
	  * pda派送签收时FOSS同步数据给GPS
	  *  @author 159231-foss-meiying
	  * @date 2014-3-8 上午8:25:59
	  */
	 void pDaDeliverSignToGps(String arrivesheetNo,String waybillNo,Date signTime);
}
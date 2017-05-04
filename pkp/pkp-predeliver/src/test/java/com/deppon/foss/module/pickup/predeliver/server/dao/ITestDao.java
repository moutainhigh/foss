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
 * PROJECT NAME	: pkp-waybill-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/api/server/dao/IWaybillDao.java
 * 
 * FILE NAME        	: IWaybillDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.pickup.predeliver.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.LclDeliveryToCashManagementDeliveryEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LclDeliveryToCashManagementUnloadingEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.PathDetailEntity;
/**
 * 
 * 
 ******************************************* 
 * <b style="font-family:微软雅黑"><small>Description:IWaybillDao</small></b> </br>
 * <b style="font-family:微软雅黑"><small>HISTORY</small></b></br> <b
 * style="font-family:微软雅黑"><small> ID DATE PERSON REASON</small></b><br>
 ******************************************** 
 * <div style="font-family:微软雅黑,font-size:70%"> 2012-10-10 sunrui 新增 </div>
 ******************************************** 
 */
public interface ITestDao {
	/**
	 * 通过运单编号查询运单
	 * 
	 * @param waybill
	 */
	WaybillEntity queryWaybillByNo(String waybillNo);

	List<PathDetailEntity> queryPathDetailByNos(String waybillNo,			List<String> serialNoList);

	SaleDepartmentEntity querySaleDepartmentByCode(String code);

	List<LclDeliveryToCashManagementDeliveryEntity> queryLclDeliveryToCashManagementDeliveryByOrgCode(String code);

	List<LclDeliveryToCashManagementUnloadingEntity> queryLclDeliveryToCashManagementUnloadingEntitytByCode(
			String startOrgCode, String reachOrgCode);
	
	
	

}
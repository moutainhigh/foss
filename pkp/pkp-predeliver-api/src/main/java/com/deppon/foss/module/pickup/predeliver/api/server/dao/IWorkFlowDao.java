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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/server/dao/IWaybillArriveTempDao.java
 * 
 * FILE NAME        	: IWaybillArriveTempDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.server.dao;

import com.deppon.foss.module.pickup.predeliver.api.shared.domain.WorkFlowEntity;

/**
 * 
 * 工作流DAO
 * 
 * @author 136892
 */
public interface IWorkFlowDao {

	public void insertWorkFlow(WorkFlowEntity entity);

	public WorkFlowEntity workFlowQuery(WorkFlowEntity entity);

	public void updateWorkFlow(long processId, String bizCode,String waybillNo);

	public String getIdByWaybillNo(String waybillNo,String serialNumber);

	public void updateStatus(String waybillNo,String status);

	public String queryOrgCodeByUnifiedCode(String unifiedCode);


}
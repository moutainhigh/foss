/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-load-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/server/service/IPDACommonService.java
 *  
 *  FILE NAME          :IPDACommonService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-load-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.load.api.server.service
 * FILE    NAME: IPDACommonService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.load.api.server.service;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.MakeUpWaybillEntity;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoaderDto;


/**
 * 装卸车PDA共通service
 * @author dp-duyi
 * @date 2013-1-14 上午9:49:39
 */
public interface IPDACommonService {
	public int modifyLoader(String taskId,String loaderTaskType,List<LoaderDto> loaderCode,Date operateTime,String loaderState);
	public OrgAdministrativeInfoEntity getCurrentOutfieldCode(String orgCode);
	public OrgAdministrativeInfoEntity getTopCurrentOutfieldOrSalesDept(String orgCode);
	public void updatePlatformStateByCreateTask(String platformNo,String vehiclNo,String taskNo,String orgCode,String type,String useType,Date starTime,Date perEndTime);
	public void updatePlatformStateByFinishTask(String taskNo, Date useEndTime);
	public void makeUpPDAloadAndUnload(MakeUpWaybillEntity makeUpWaybillEntity);
	public String queryAccessPointName(String pointCode);
}
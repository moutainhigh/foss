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
 *  PROJECT NAME  : tfr-pda-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/pda/api/server/service/IPDAPackagingService.java
 *  
 *  FILE NAME          :IPDAPackagingService.java
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
 * PROJECT NAME: tfr-pda-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.pda.api.server.service
 * FILE    NAME: IPackagingService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.pda.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PackagingSupplierEntity;
import com.deppon.foss.module.transfer.pda.api.shared.domain.PDAPackagingInfoEntity;
import com.deppon.foss.module.transfer.pda.api.shared.domain.QueryPDAUnpackResEntity;
import com.deppon.foss.module.transfer.pda.api.shared.dto.FinishPackResultDto;

/**
 * PDA包装录入接口
 * @author 046130-foss-xuduowei
 * @date 2012-11-3 上午9:10:31
 */
public interface IPDAPackagingService extends IService {
	/**
	 * 
	 * PDA端查询营业部代打包装货物
	 * @param packedDept 代包装部门编码
	 * @return 待包装货物信息
	 * @author 046130-foss-xuduowei
	 * @date 2012-11-3 上午9:27:13
	 */
	List<QueryPDAUnpackResEntity> queryPDAUnpackResult(String packedDept);
	/**
	 * 
	 * PDA包装录入信息
	 * @param pdaPackagingInfoEntity 包装录入信息
	 * @author 046130-foss-xuduowei
	 * @date 2012-11-3 上午9:28:39
	 */
	FinishPackResultDto addPackagingInfo(PDAPackagingInfoEntity pdaPackagingInfoEntity);
	/** 
	 * @Description:根据员工工号查询对应外场部门的包装供应商
	 * @author 042795-duyi
	 */
	List<PackagingSupplierEntity> queryPackagingSupplierListByEmpCode(String empCode);
}
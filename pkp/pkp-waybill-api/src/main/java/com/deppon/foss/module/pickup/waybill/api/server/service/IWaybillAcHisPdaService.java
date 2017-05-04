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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/api/server/service/IWaybillAcHisPdaService.java
 * 
 * FILE NAME        	: IWaybillAcHisPdaService.java
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
package com.deppon.foss.module.pickup.waybill.api.server.service;

import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillAcHisPdaEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillPdaDto;

/**
 * 
 * <p>
 * PDA操作历史记录service
 * </p>
 * @title IWaybillAcHisPdaService.java
 * @package com.deppon.foss.module.pickup.waybill.api.server.service 
 * @author suyujun
 * @version 0.1 2012-12-11
 */
public interface IWaybillAcHisPdaService {
	/**
	 * 新增 PDA运单操作历史
	 * @author 043260-foss-suyujun
	 * @date 2012-12-11
	 * @param entity
	 * @return
	 * @return int
	 * @see
	 */
	int createWaybillAcHisPdaEntity(WaybillAcHisPdaEntity entity);

	/**
	 * 根据Id删除PDA运单操作记录
	 * @author 043260-foss-suyujun
	 * @date 2012-12-11
	 * @param id
	 * @return int
	 */
	int deleteByPrimaryKey(String id);
	
	/**
	 * 更新PDA运单操作记录
	 * @author 043260-foss-suyujun
	 * @date 2012-12-11
	 * @param entity
	 * @return int
	 */
	int updateWaybillAcHisPdaEntityById(WaybillAcHisPdaEntity entity);
	
	/**
	 * 
	 * <p>运单补录（PDA，订单导入）记录更改历史</p> 
	 * @author foss-gengzhe
	 * @date 2012-12-20 下午2:42:29
	 * @param newWaybillPda
	 * @param oldWaybillPda
	 * @see
	 */
	void addWaybillAcHisPdaEntity(WaybillPdaDto newWaybillPda, WaybillPdaDto oldWaybillPda);
}
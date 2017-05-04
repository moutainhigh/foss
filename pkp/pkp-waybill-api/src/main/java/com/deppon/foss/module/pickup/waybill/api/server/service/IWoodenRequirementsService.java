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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/api/server/service/IWoodenRequirementsService.java
 * 
 * FILE NAME        	: IWoodenRequirementsService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: pkp-waybill-api
 * PACKAGE NAME: com.deppon.foss.module.pickup.waybill.api.server.service
 * FILE    NAME: IWoodenRequirements.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.pickup.waybill.api.server.service;

import com.deppon.foss.module.pickup.waybill.shared.domain.WoodenRequirementsEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.LastWaybillRfcQueryDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillSystemDto;


/**
 * 代打木架服务接口
 * @author 026123-foss-lifengteng
 * @date 2012-11-5 下午1:43:49
 */
public interface IWoodenRequirementsService {
    /**
     * 批量新增付款明细实体
     * @author 026123-foss-lifengteng
     * @date 2012-11-5 上午11:40:26
     */
    void addWoodenRequirements(WoodenRequirementsEntity wooden,WaybillSystemDto systemDto );
    
    /**
     * 
     * 更改单起草后追加付款明细实体
     * @author 102246-foss-shaohongliang
     * @date 2012-12-3 下午4:09:39
     */
    void appendWoodenRequirementsAfterChange(WoodenRequirementsEntity wooden,WaybillSystemDto systemDto );

	/**
	 * 新增需要包装运单信息到包装需求表中
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-6 上午10:10:11
	 */
	void addPackagingRequire(WaybillDto waybillDto);

	/**
	 * 
	 * <p>
	 * 新版运单代打木架信息<br />
	 * </p>
	 * @author suyujun
	 * @version 0.1 2012-12-1
	 * @param queryDto
	 * @return
	 * WoodenRequirementsEntity
	 */
	WoodenRequirementsEntity queryNewWoodenRequirements(LastWaybillRfcQueryDto queryDto);

	/**
	 * 
	 * <p>
	 * 更新打木架信息<br />
	 * </p>
	 * @author suyujun
	 * @version 0.1 2012-12-3
	 * @param oldWoodRequirements
	 * void
	 */
	void updateWoodenRequirements(WoodenRequirementsEntity oldWoodRequirements);

	/**
	 * 
	 * <p>
	 * 删除打木架<br />
	 * </p>
	 * @author suyujun
	 * @version 0.1 2012-12-3
	 * @param id
	 * void
	 */
	int deleteWoodRequirementsById(String id);
	/**
	 * 查询打木架信息
	 * @author 043260-foss-suyujun
	 * @date 2012-12-7
	 * @param waybillNo
	 * @return
	 * @return WoodenRequirementsEntity
	 */
	WoodenRequirementsEntity queryWoodenRequirements(String waybillNo);

	/**
	 * @param newWoodRequirements
	 */
	void updateAllOtherWoodenRequirementsToNo(
			WoodenRequirementsEntity newWoodRequirements);
}
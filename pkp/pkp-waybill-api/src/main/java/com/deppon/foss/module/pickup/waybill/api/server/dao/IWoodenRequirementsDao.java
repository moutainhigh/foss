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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/api/server/dao/IWoodenRequirementsDao.java
 * 
 * FILE NAME        	: IWoodenRequirementsDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: pkp-waybill-api
 * PACKAGE NAME: com.deppon.foss.module.pickup.waybill.api.server.dao
 * FILE    NAME: IWoodenRequirementsDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.pickup.waybill.api.server.dao;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WoodenRequirementsEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.LastWaybillRfcQueryDto;


/**
 * 代打木架DAO接口
 * @author 026123-foss-lifengteng
 * @date 2012-11-5 下午1:51:00
 */
public interface IWoodenRequirementsDao extends IService {
	/**
	 * 新增代打木架实体信息
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-5 下午2:03:01
	 */
	int addWoodenRequirementsEntity(WoodenRequirementsEntity wooden);

	/**
	 * 根据运单号查询代打木架实体信息
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-5 下午5:54:00
	 */
	WoodenRequirementsEntity queryWoodenByNo(String waybillNo);

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
	 * 代打木架信息更新<br />
	 * </p>
	 * @author suyujun
	 * @version 0.1 2012-12-3
	 * @param oldWoodRequirements
	 * void
	 */
	void updateWoodenRequirements(WoodenRequirementsEntity oldWoodRequirements);

	void updateAllOtherWoodenRequirementsToNo(WoodenRequirementsEntity oldWoodRequirements);

	
	/**
	 * 
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author suyujun
	 * @version 0.1 2012-12-3
	 * @param id
	 * @return
	 * int
	 */
	int deleteWoodRequirementsById(String id);

	/**
	 * 根据运单号查询打木信息
	 * @param waybillNo
	 * @return
	 */
	WoodenRequirementsEntity queryNewWoodenByNo(String waybillNo); 
	
	/**
	 * 根据code查询运输性质
	 */
	String getProductByCacheCode(String code);
}
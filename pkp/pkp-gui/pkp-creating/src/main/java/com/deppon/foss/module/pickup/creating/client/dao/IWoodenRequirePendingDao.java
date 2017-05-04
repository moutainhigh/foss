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
 * PROJECT NAME	: pkp-creating
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/dao/IWoodenRequirePendingDao.java
 * 
 * FILE NAME        	: IWoodenRequirePendingDao.java
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
 * FILE    NAME: adfasd.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.pickup.creating.client.dao;

import com.deppon.foss.module.pickup.waybill.shared.domain.WoodenRequirePendingEntity;


/**
 * 待处理运单打木架dao接口
 * @author 026123-foss-lifengteng
 * @date 2012-12-13 上午9:38:57
 */
public interface IWoodenRequirePendingDao {
	
    /**
     * 新增待处理运单的打木架信息
     * @author 026123-foss-lifengteng
     * @date 2012-12-13 上午9:39:51
     */
    int insert(WoodenRequirePendingEntity record);

    /**
     * 选择性新增待处理运单的打木架信息
     * @author 026123-foss-lifengteng
     * @date 2012-12-13 上午9:40:10
     */
    int insertSelective(WoodenRequirePendingEntity record);
    
    /**
     * 根据运单号查询待处理运单的打木架信息
     * @author 026123-foss-lifengteng
     * @date 2012-12-13 上午9:41:56
     */
    WoodenRequirePendingEntity queryWoodenRequireByNo(String waybillNo);
    
    /**
	 * <p>
	 * 通过运单编号删除记录
	 * </p>
	 * @author 105089-foss-yangtong
	 * @date 2012-10-31 下午3:40:57
	 * @param waybillNo
	 * @return
	 * @see 
	 */
    int deleteByWaybillNo(String waybillNo); 
}
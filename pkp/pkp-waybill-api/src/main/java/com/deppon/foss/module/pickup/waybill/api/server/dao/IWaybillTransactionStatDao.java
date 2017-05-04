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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/api/server/dao/IWaybillTransactionStatDao.java
 * 
 * FILE NAME        	: IWaybillTransactionStatDao.java
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
package com.deppon.foss.module.pickup.waybill.api.server.dao;

import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillTransactionStatEntity;

/**
 * 
 * 运单完结状态数据持久接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:foss-sunrui,date:2012-12-10 下午2:12:29, </p>
 * @author foss-sunrui
 * @date 2012-12-10 下午2:12:29
 * @since
 * @version
 */
public interface IWaybillTransactionStatDao {
	
	/**
	 * 
	 * <p>插入一条记录</p> 
	 * @author foss-sunrui
	 * @date 2012-12-10 下午2:12:52
	 * @param record
	 * @return
	 * @see
	 */
	int insertSelective(WaybillTransactionStatEntity record);

	/**
	 * 
	 * <p>按主键查询</p> 
	 * @author foss-sunrui
	 * @date 2012-12-10 下午2:13:10
	 * @param id
	 * @return
	 * @see
	 */
    WaybillTransactionStatEntity queryByPrimaryKey(String id);
    
    /**
     * 
     * <p>按运单号码查询</p> 
     * @author foss-sunrui
     * @date 2012-12-10 下午2:13:24
     * @param waybillNo
     * @return
     * @see
     */
    WaybillTransactionStatEntity queryByWaybillNo(String waybillNo);

    /**
     * 
     * <p>按主键更新</p> 
     * @author foss-sunrui
     * @date 2012-12-10 下午2:13:38
     * @param record
     * @return
     * @see
     */
    int updateByPrimaryKeySelective(WaybillTransactionStatEntity record);
    
    /**
     * 
     * <p>按运单号码更新</p> 
     * @author foss-sunrui
     * @date 2012-12-10 下午2:13:47
     * @param record
     * @return
     * @see
     */
    int updateByWaybillNoSelective(WaybillTransactionStatEntity record);

}
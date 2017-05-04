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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/api/server/dao/IReturnBillProcessDao.java
 * 
 * FILE NAME        	: IReturnBillProcessDao.java
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

import com.deppon.foss.module.pickup.waybill.shared.domain.ReturnBillProcessEntity;

/**
 * 
 * 签收单返单处理
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:foss-sunrui,date:2012-11-7 上午11:36:27, </p>
 * @author foss-sunrui
 * @date 2012-11-7 上午11:36:27
 * @since
 * @version
 */
public interface IReturnBillProcessDao {
    
    /**
     * 
     * <p>插入一条记录</p> 
     * @author foss-sunrui
     * @date 2012-11-7 上午11:36:46
     * @param record
     * @return
     * @see
     */
    int insertSelective(ReturnBillProcessEntity record);

    /**
     * 
     * <p>按主键查询</p> 
     * @author foss-sunrui
     * @date 2012-11-7 上午11:36:57
     * @param id
     * @return
     * @see
     */
    ReturnBillProcessEntity queryByPrimaryKey(String id);

    /**
     * 
     * <p>根据主键更新</p> 
     * @author foss-sunrui
     * @date 2012-11-7 上午11:37:10
     * @param record
     * @return
     * @see
     */
    int updateByPrimaryKeySelective(ReturnBillProcessEntity record);
    
    
    /**
     * 根据运单号删除行
     * @param waybillNO
     * @return
     */
    int deleteByWaybillNO(String waybillNO);
    
}
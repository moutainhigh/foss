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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/api/server/dao/IWaybillPaymentPendingDao.java
 * 
 * FILE NAME        	: IWaybillPaymentPendingDao.java
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

import java.util.List;

import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPaymentPendingEntity;

/**
 * 
 * 待处理运单付费明细
 * 
 * @author 026113-foss-linwensheng
 * @date 2012-10-29 上午11:38:53
 */
public interface IWaybillPaymentPendingDao {

    /**
     * 
     * 插入待处理运单付费明细
     * 
     * @author 026113-foss-linwensheng
     * @date 2012-10-30 下午6:51:57
     */
    int addWaybillPaymentPending(WaybillPaymentPendingEntity record);
    
    /**
     * 批量插入待处理运单付费明细
     * @author 026123-foss-lifengteng
     * @date 2012-11-3 下午4:52:35
     */
    int addWaybillPaymentPendingBatch(List<WaybillPaymentPendingEntity> record);

    /**
     * 更新待处理运单信息
     * @author 026113-foss-linwensheng
     * @date 2012-10-30 下午7:08:42
     */
    int updateByPrimaryKey(WaybillPaymentPendingEntity record);
    
	/**
	 * 通过运单号查询待处理运单付款信息
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-6 上午10:30:00
	 */
	List<WaybillPaymentPendingEntity> queryPaymentPendingByNo(String id);

	/**
	 * 
	 * 通过运单号删除待处理运单付款信息
	 * @author foss-sunrui
	 * @date 2013-3-22 下午1:18:20
	 * @param waybillNo
	 * @return
	 * @see
	 */
	int deleteByWaybillNo(String waybillNo);
}
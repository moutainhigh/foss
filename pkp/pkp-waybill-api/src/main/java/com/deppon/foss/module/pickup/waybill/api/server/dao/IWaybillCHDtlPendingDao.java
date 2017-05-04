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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/api/server/dao/IWaybillCHDtlPendingDao.java
 * 
 * FILE NAME        	: IWaybillCHDtlPendingDao.java
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

import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillCHDtlPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillOtherChargeDto;

/**
 * 
 * 待处理运单费用
 * 
 * @author 026113-foss-linwensheng
 * @date 2012-10-29 上午11:37:31
 */
public interface IWaybillCHDtlPendingDao {

    /**
     * 
     * 通过组建删除费用信息
     * 
     * @author 026113-foss-linwensheng
     * @date 2012-10-30 下午6:42:00
     */
    int deleteByPrimaryKey(String id);
    
    /**
     * 
     * 通过组建删除费用信息
     * 
     * @author 026113-foss-linwensheng
     * @date 2012-10-30 下午6:42:00
     */
    int deleteByWaybillNo(String waybillNo);

    /**
     * 
     * 插入待处理运单费用信息
     * 
     * @author 026113-foss-linwensheng
     * @date 2012-10-30 下午6:42:25
     */
    int addWaybillCHDtlPendingSelective(WaybillCHDtlPendingEntity record);
    
    /**
     * 批量插入待处理运单费用信息
     * @author 026123-foss-lifengteng
     * @date 2012-11-3 下午5:03:14
     */
    int addWaybillCHDtlPendingBatch(List<WaybillCHDtlPendingEntity> record);

    /**
     * 
     * 通过主键查询
     * 
     * @author 026113-foss-linwensheng
     * @date 2012-10-30 下午6:42:55
     */
    WaybillCHDtlPendingEntity queryByPrimaryKey(String id);

    /**
     * 
     * 更新待处理运单费用信息
     * 
     * @author 026113-foss-linwensheng
     * @date 2012-10-30 下午6:48:37
     */
    int updateByPrimaryKey(WaybillCHDtlPendingEntity record);

	/**
	 * 通过运单ID查询待处理运单费用信息
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-6 下午2:51:04
	 */
	List<WaybillCHDtlPendingEntity> queryCHDtlPendingByNo(String waybillNo);

	/**
	 * 批量插入待处理运单费用信息
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-7 下午4:35:19
	 */
	int addBatch(List<WaybillCHDtlPendingEntity> list);

	/**
	 * 根据ID集合查询集合对象
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-14 下午6:29:05
	 */
	List<WaybillCHDtlPendingEntity> queryChDtlPendingByListId(List<String> ids);
	
	
	/**
	 * 通过运单号查询费用明细中的其它费用 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-15 下午2:41:46
	 */
	List<WaybillOtherChargeDto> queryOtherChargeByNo(String waybillNo);
	
	/**
	 * 根据运单号删除待补录的费用明细信息
	 * @remark 
	 * @author WangQianJin
	 * @date 2014-1-20 下午1:43:00
	 */
	int deleteCHDtlPendingByWaybillNo(String waybillNo);
}
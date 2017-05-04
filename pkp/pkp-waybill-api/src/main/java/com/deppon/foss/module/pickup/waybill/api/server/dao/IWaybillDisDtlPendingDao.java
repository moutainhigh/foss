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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/api/server/dao/IWaybillDisDtlPendingDao.java
 * 
 * FILE NAME        	: IWaybillDisDtlPendingDao.java
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
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillDisDtlPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillSystemDto;

/**
 * 
 * 运单折扣明细DAO
 * 
 * @author 026113-foss-linwensheng
 * @date 2012-10-30 下午5:25:38
 */
public interface IWaybillDisDtlPendingDao {

    /**
     * 
     * 通过主键删除折扣明细
     * 
     * @author 026113-foss-linwensheng
     * @date 2012-10-30 下午5:26:48
     */
    int deleteByPrimaryKey(String id);
    
    /**
     * 
     * 通过运单号删除折扣明细
     * 
     * @author 026113-foss-linwensheng
     * @date 2012-10-30 下午5:26:48
     */
    int deleteByWaybillNo(String waybillNo);

    /**
     * 方法详细描述说明、方法参数的具体涵
     * 
     * @author 026113-foss-linwensheng
     * @date 2012-10-30 下午5:36:56
     */
    int addWaybillDisDtlPendingSelective(WaybillDisDtlPendingEntity record);

    /**
     * 批量插入WaybillDisDtlPendingEntity实体数据
     * @author 026123-foss-lifengteng
     * @date 2012-11-3 下午5:14:54
     */
    int addWaybillDisDtlPendingBatch(List<WaybillDisDtlPendingEntity> record,WaybillSystemDto systemDto);

    /**
     * 通过主键查询运单折扣信息
     * 
     * @author 026113-foss-linwensheng
     * @date 2012-10-30 下午5:37:59
     */
    WaybillDisDtlPendingEntity queryByPrimaryKey(String id);
    
    /**
     * 
     * 更新
     * @author 026113-foss-linwensheng
     * @date 2012-10-30 下午7:07:51
     */
     int updateByPrimaryKey(WaybillDisDtlPendingEntity record);

	/**
	 * 通过运单号查询待处理运单的折扣信息
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-6 下午2:05:30
	 */
	List<WaybillDisDtlPendingEntity> queryDisDtlPendingByNo(String waybillNo);
     
	/**
	 * 根据运单号和类型查询CRM营销活动
	 * @创建时间 2014-4-22 下午8:34:33   
	 * @创建人： WangQianJin
	 */
	WaybillDisDtlPendingEntity queryActiveInfoByNoAndType(String waybillNo);

}
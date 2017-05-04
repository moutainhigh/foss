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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/api/server/dao/IWaybillDisDtlDao.java
 * 
 * FILE NAME        	: IWaybillDisDtlDao.java
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

import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillDisDtlEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.LastWaybillRfcQueryDto;

/**
 * 
 * 运单折扣明细数据持久层
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:Bobby,date:2012-10-17 下午6:01:50</p>
 * @author Bobby
 * @date 2012-10-17 下午6:01:50
 * @since
 * @version
 */
public interface IWaybillDisDtlDao {
	/**
     * 插入对象数据
     * @author 026123-foss-lifengteng
     * @date 2012-11-5 上午11:02:09
     */
    int insert(WaybillDisDtlEntity record);    

	/**
     * 插入部分对象数据
     * @author WangQianJin
     * @date 2014-05-21
     */
    int insertSelective(WaybillDisDtlEntity record);
    
    /**
     * 批量插入对象数据
     * @author 026123-foss-lifengteng
     * @date 2012-11-5 上午11:02:09
     */
    int addBatch(List<WaybillDisDtlEntity> waybillDisDtl);
    /**
     * 根据id查询折扣明细
     * @author 026123-foss-lifengteng
     * @date 2012-11-5 上午11:02:09
     */
    WaybillDisDtlEntity queryByPrimaryKey(String id);
    
    /**
     * 根据运单号查询折扣明细列表
     * @author 026123-foss-lifengteng
     * @date 2012-11-5 下午4:21:43
     */
    List<WaybillDisDtlEntity> queryDisDtlEntityByNo(String waybillNo);

    /**
     * 更新折扣明细实体
     * @author 043260-foss-suyujun
     * @date 2012-12-18
     * @param record
     * @return int
     */
    int updateByPrimaryKeySelective(WaybillDisDtlEntity record);

    /**
     * 
     * <p>
     * 运单折扣明细<br />
     * </p>
     * @author suyujun
     * @version 0.1 2012-12-1
     * @param queryDto
     * @return
     * List<WaybillDisDtlEntity>
     */
	List<WaybillDisDtlEntity> queryNewDisDtlEntityByNo(LastWaybillRfcQueryDto queryDto);

	/**
	 * 
	 * <p>
	 * 删除折扣明细<br />
	 * </p>
	 * @author suyujun
	 * @version 0.1 2012-12-3
	 * @param id
	 * @return
	 * Object
	 */
	int deleteWaybillDisDtlEntityById(String id);
	
	/**
	 * 根据运单号和类型查询CRM营销活动
	 * @创建时间 2014-4-22 下午8:34:33   
	 * @创建人： WangQianJin
	 */
	WaybillDisDtlEntity queryActiveInfoByNoAndType(String waybillNo);
	
	/**
	 * 根据运单号与类型修改运单状态
	 * @创建时间 2014-5-8 下午8:50:48   
	 * @创建人： WangQianJin
	 */
	int updateByWaybillNoAndType(WaybillDisDtlEntity record);
	
	/**
	 * 激活运单折扣费用明细
	 * @author liyongfei--DMANA-2035
	 * @param waybillNoList
	 * @return
	 */
	int setWaybillDisDtlActive(List<String> waybillNoList);

	int deleteWaybillDisDtlEntityByWaybillNo(String waybillNo);
}
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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/api/server/dao/IWaybillStockDao.java
 * 
 * FILE NAME        	: IWaybillStockDao.java
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
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillStockEntity;
/**
 * 运单库存DAO
 * @author 026113-foss-linwensheng
 * @author Administrator
 *
 */
public interface IWaybillStockDao {
	
	/**
	 * 添加异步入库
	 * @author 026113-foss-linwensheng
	 * @param record
	 * @return
	 */
	int addWaybillStockEntity(WaybillStockEntity record);


	/**
	 *更新JOBID
	 *@author 026113-foss-linwensheng
	 * @param record
	 * @return
	 */
	
	int updateJobIDByID(WaybillStockEntity record);
	
	/**
	 * 更新操作类型
	 * @author 026113-foss-linwensheng
	 * @param record
	 * @return
	 */
	int updateOperateTypeByID(WaybillStockEntity record);
	
	/**
	 * 根据ID查询运单库存
	 * @author 026113-foss-linwensheng
	 * @param id
	 * @return
	 */
	List<WaybillStockEntity>  queryWaybillStockEntity();
		
	
	/**
	 * 通过JobID查询
	 * @author 026113-foss-linwensheng
	 */
	List<WaybillStockEntity>  queryWaybillStockEntityByJobID(String jobId);
	
	
	/**
	 * 通过Job 删除记录
	 * @author 026113-foss-linwensheng
	 * @param jobId
	 */
	void  deleteByJobId(String jobId);
	/**
	 * 通过ID  删除记录
	 * @author 026113-foss-linwensheng
	 * @param jobId
	 */
	void  deleteById(String id);
	
	
	/**
	 * 获取运单库存信息
	 * @author 087584-foss-WangQianJin
	 * @date 2013-3-21 下午4:48:39
	 */
	List<WaybillStockEntity> queryWaybillStockDtoByWaybillNo(String waybillNo);
	
	
}
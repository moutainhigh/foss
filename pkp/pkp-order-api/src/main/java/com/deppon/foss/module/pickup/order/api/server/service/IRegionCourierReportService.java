/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 038590-foss-wanghui
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
 * PROJECT NAME	: pkp-order-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/order/api/server/service/IOrderTaskHandleService.java
 * 
 * FILE NAME        	: IOrderTaskHandleService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.order.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.order.api.shared.domain.RegionCourierReportEntity;

public interface IRegionCourierReportService extends IService {

	
   /**
    * 
    * @Title: queryOneByCommon 
    * @Description: 查询出司机统计信息
    * @param @param courierCode
    * @param @return    设定文件 
    * @return RegionCourierReportEntity    返回类型 
    * @throws
    */
   public RegionCourierReportEntity queryOneByCommon(String courierCode);
   
   /**
    * 
    * @Title: queryMinCourierByCommon 
    * @Description: 查询最少接货快递员工号
    * @param @param courierList
    * @param @return    设定文件 
    * @return String    返回类型 
    * @throws
    */
   public String queryMinCourierByCommon(List<String> courierList); 
   
   /**
    * 
    * @Title: insert 
    * @Description: 增加快递员统计报表数据
    * @param @param regionCourierReportEntity
    * @param @return    设定文件 
    * @return int    返回类型 
    * @throws
    */
	public int insert(RegionCourierReportEntity regionCourierReportEntity);
   
	/**
    * 
    * @Title: update 
    * @Description: 更新快递员统计报表数据
    * @param @param regionCourierReportEntity
    * @param @return    设定文件 
    * @return int    返回类型 
    * @throws
    */
	public int update(RegionCourierReportEntity regionCourierReportEntity);
	/**
	 * 删除快递员统计报表数据
	 * @param regionCourierReportEntity
	 * @return
	 */
	public int delete(RegionCourierReportEntity regionCourierReportEntity);

}
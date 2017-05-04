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
import com.deppon.foss.module.pickup.order.api.shared.domain.OrderReportEntity;

public interface IOrderReportService extends IService {

	/**
	 * 
	 * @Title: insertOrderReport
	 * @Description: 处理订单新增一条记录
	 * @param @param orderReportEntity
	 * @param @return 设定文件
	 * @return int 返回类型
	 * @throws
	 */
	public int insertOrderReport(OrderReportEntity orderReportEntity);

	/**
	 * 
	 * @Title: deleteOrderReport
	 * @Description: 删除每日统计对应的数据
	 * @param @param orderReportEntity
	 * @param @return 设定文件
	 * @return int 返回类型
	 * @throws
	 */
	public int deleteOrderReport(OrderReportEntity orderReportEntity);

	/**
	 * 
	 * @Title: selectOrderReportByAddress
	 * @Description: 根据地址查询对应的快递员
	 * @param @param orderReportEntity
	 * @param @return 设定文件
	 * @return OrderReportEntity 返回类型
	 * @throws
	 */
	public OrderReportEntity selectOrderReportByAddress(
			OrderReportEntity orderReportEntity);
	/**
	 * 
	 * @Title: selectOrderReportByAddress
	 * @Description: 根据地址和快递员工号查询对应的快递员
	 * @param @param orderReportEntity
	 * @param @return 设定文件
	 * @return OrderReportEntity 返回类型
	 * @throws
	 */
	public OrderReportEntity selectOrderReportByAddressDriverCode(
			OrderReportEntity orderReportEntity);
	
	/**
	 * 
	 * @Title: queryMinReceiveOrderDriver 
	 * @Description: 根据机动快递员集合，查询出收货订单量最少快递员
	 * @param @param list
	 * @param @return    设定文件 
	 * @return String    返回类型 
	 * @throws
	 */
	public String queryMinReceiveOrderDriver(List<String> list);
	/**
	 * 14.7.30 gcl 查询 快递员当天接收订单数
	 * @param driverCode
	 * @return
	 */
	public Long queryReceiveCountOrderDriver(String driverCode);

}
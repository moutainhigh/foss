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

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.order.api.shared.domain.CourierReportEntity;
import com.deppon.foss.module.pickup.order.api.shared.dto.CourierQueryDto;

public interface ICourierReportService extends IService {
	/**
	 * 
	 * @Title: insertOrderReport
	 * @Description: 新增一条每日报表记录
	 * @param @param courierReportEntity
	 * @param @return 设定文件
	 * @return int 返回类型
	 * @throws
	 * @update zxy 20140714 修改接口
	 */
	public int insertCourierReport(CourierReportEntity courierReportEntity,Date date);

	/**
	 * 
	 * @Title: queryCourierByCommon
	 * @Description: 根据查询条件返回查询集合
	 * @param @param courierQueryDto
	 * @param @return 设定文件
	 * @return List<CourierReportEntity> 返回类型
	 * @throws
	 */
	public List<CourierReportEntity> queryCourierByCommon(
			CourierQueryDto courierQueryDto,int start, int limit);
	
	/**
	 * 
	 * @Title: queryCourierByCommon
	 * @Description: 根据查询条件返回查询总数
	 * @param @param courierQueryDto
	 * @param @return 设定文件
	 * @return Long
	 * @throws
	 */
	public Long queryCourierByCommonCount(CourierQueryDto courierQueryDto);
	
	/**
	 * 
	 * @Title: queryCourierByCommon
	 * @Description: 查询统计总数
	 * @param @param courierQueryDto
	 * @param @return 设定文件
	 * @return List<CourierReportEntity>
	 * @throws
	 */
	public List<CourierReportEntity> queryCourierReportByDay(Date curDate, Date preDate);
	
	/**
	 * 查询数据
	 * queryCourierReord: <br/>
	 * 
	 * Date:2014-7-5下午3:19:53
	 * @author 157229-zxy
	 * @param vo
	 * @return
	 * @since JDK 1.6
	 */
	public List<CourierReportEntity> queryCourierReord(CourierQueryDto  vo);
	
	/**
	 * 按日志刪除报表统计数据
	 * deleteCourierReportByDay: <br/>
	 * 
	 * Date:2014-7-14下午5:03:48
	 * @author 157229-zxy
	 * @param date
	 * @return
	 * @since JDK 1.6
	 */
	public int deleteCourierReportByDay(Date date);
}
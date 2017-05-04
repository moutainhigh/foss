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
 * PROJECT NAME	: pkp-order
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/order/server/service/impl/OrderPreprocessService.java
 * 
 * FILE NAME        	: OrderPreprocessService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.order.server.service.impl;

import java.util.Date;
import java.util.List;
import com.deppon.foss.module.pickup.order.api.server.dao.ICourierReportEntityDao;
import com.deppon.foss.module.pickup.order.api.server.service.ICourierReportService;
import com.deppon.foss.module.pickup.order.api.shared.domain.CourierReportEntity;
import com.deppon.foss.module.pickup.order.api.shared.dto.CourierQueryDto;
import com.deppon.foss.util.UUIDUtils;


/**
 * 
 * @ClassName: OrderReportService 
 * @Description: 自动调度处理快递每日统计报表服务 
 * @author YANGBIN
 * @date 2014-5-10 上午9:07:25 
 *
 */
public class CourierReportService implements ICourierReportService{
	// 每日报表DAO
	private ICourierReportEntityDao courierReportEntityDao;

	
	/**
	 * 
	 * @Title: insertOrderReport
	 * @Description: 新增一条每日报表记录
	 * @param @param courierReportEntity
	 * @param @return 设定文件
	 * @return int 返回类型
	 * @throws
	 */
	@Override
	public int insertCourierReport(CourierReportEntity courierReportEntity,Date date) {
		courierReportEntity.setId(UUIDUtils.getUUID());
		courierReportEntity.setCreateDate(date);
		return courierReportEntityDao.insertCourierReport(courierReportEntity);
	}

	/**
	 * 
	 * @Title: queryCourierByCommon
	 * @Description: 根据查询条件返回查询集合
	 * @param @param courierQueryDto
	 * @param @return 设定文件
	 * @return List<CourierReportEntity> 返回类型
	 * @throws
	 */
	@Override
	public List<CourierReportEntity> queryCourierByCommon(
			CourierQueryDto courierQueryDto,int start, int limit) {
		return courierReportEntityDao.queryCourierByCommon(courierQueryDto,start,limit);
	}

	/**
	 * 
	 * @Title: queryCourierByCommon
	 * @Description: 根据查询条件返回查询总数
	 * @param @param courierQueryDto
	 * @param @return 设定文件
	 * @return Long
	 * @throws
	 */
	@Override
	public Long queryCourierByCommonCount(CourierQueryDto courierQueryDto) {
		return courierReportEntityDao.queryCourierByCommonCount(courierQueryDto);
	}

	/**
	 * 
	 * @Title: queryCourierByCommon
	 * @Description: 查询统计总数
	 * @param @param courierQueryDto
	 * @param @return 设定文件
	 * @return List<CourierReportEntity>
	 * @throws
	 */
	@Override
	public List<CourierReportEntity> queryCourierReportByDay(Date curDate, Date preDate) {
		return courierReportEntityDao.queryCourierReportByDay(curDate, preDate);
	}
	
	public ICourierReportEntityDao getCourierReportEntityDao() {
		return courierReportEntityDao;
	}

	public void setCourierReportEntityDao(
			ICourierReportEntityDao courierReportEntityDao) {
		this.courierReportEntityDao = courierReportEntityDao;
	}

	@Override
	public List<CourierReportEntity> queryCourierReord(
			CourierQueryDto vo) {
		//设置基本查询条件
		return courierReportEntityDao.queryCourierByCommon(vo);
	}

	@Override
	public int deleteCourierReportByDay(Date date) {
		return courierReportEntityDao.deleteCourierReportByDay(date);
	}
	
}
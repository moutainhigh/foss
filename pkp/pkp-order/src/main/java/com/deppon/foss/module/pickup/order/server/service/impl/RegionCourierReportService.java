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

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.pickup.order.api.server.dao.IRegionCourierReportDao;
import com.deppon.foss.module.pickup.order.api.server.service.IRegionCourierReportService;
import com.deppon.foss.module.pickup.order.api.shared.domain.RegionCourierReportEntity;
import com.deppon.foss.module.pickup.order.api.shared.exception.DispatchException;
import com.deppon.foss.util.UUIDUtils;


/**
 * 
 * @ClassName: OrderVehViewService 
 * @Description: 订单可视化查询服务 
 * @author YANGBIN
 * @date 2014-5-10 上午9:07:25 
 *
 */
public class RegionCourierReportService implements IRegionCourierReportService{
	// 日志信息
	private static final Logger LOGGER = LoggerFactory.getLogger(RegionCourierReportService.class);
	// 快递员报表统计DAO
	private IRegionCourierReportDao regionCourierReportDao;
//	private BigDecimal ONE_COUNT = new BigDecimal(1);
	/**
	 * 
	 * @Title: queryOneByCommon 
	 * @Description: 查询出司机统计信息
	 * @param @param courierCode
	 * @param @return    设定文件 
	 * @return RegionCourierReportEntity    返回类型 
	 * @throws
	 */	
	@Override
	public RegionCourierReportEntity queryOneByCommon(String courierCode) {
		// 判断司机工号是否为空
		if(StringUtils.isEmpty(courierCode)){
			throw new DispatchException("传入的快递员工号为空"); 
		}
		return regionCourierReportDao.queryOneByCommon(courierCode);
	}
	
	 /**
	  * 
	  * @Title: queryMinCourierByCommon 
	  * @Description: 查询最少接货快递员工号
	  * @param @param courierList
	  * @param @return    设定文件 
	  * @return String    返回类型 
	  * @throws
	  */
	@Override
	public String queryMinCourierByCommon(List<String> courierList) {
		// 判断司机工号是否为空
		if(CollectionUtils.isEmpty(courierList)){
			throw new DispatchException("传入的快递员工号集合为空"); 
		}
		return regionCourierReportDao.queryMinCourierByCommon(courierList);
	}
	
	 /**
	   * 
	   * @Title: insert 
	   * @Description: 增加快递员统计报表数据
	   * @param @param regionCourierReportEntity
	   * @param @return    设定文件 
	   * @return int    返回类型 
	   * @throws
	  */
	@Override
	public int insert(RegionCourierReportEntity regionCourierReportEntity) {
		String courierCode = regionCourierReportEntity.getCourierCode();
		// 根据快递员工号判断是否存在
		RegionCourierReportEntity queryParam = regionCourierReportDao.queryOneByCommon(courierCode);
		int rownum = 0;
		if(null != queryParam){
			regionCourierReportEntity.setId(queryParam.getId());
			rownum = update(regionCourierReportEntity);
		}else {
			regionCourierReportEntity.setId(UUIDUtils.getUUID());
			regionCourierReportEntity.setOperateTime(new Date());
			try{
				rownum = regionCourierReportDao.insert(regionCourierReportEntity);
			}catch(Exception e){
				queryParam = regionCourierReportDao.queryOneByCommon(courierCode);
				if(null != queryParam){
					regionCourierReportEntity.setId(queryParam.getId());
					rownum = update(regionCourierReportEntity);
				}
			}
		}
		return rownum;
	}
	
	/**
	 * 
	 * @Title: update 
	 * @Description: 更新快递员统计报表数据
	 * @param @param regionCourierReportEntity
	 * @param @return    设定文件 
	 * @return int    返回类型 
	 * @throws
	 */
	@Override
	public int update(RegionCourierReportEntity regionCourierReportEntity) {
		regionCourierReportEntity.setOperateTime(new Date());
		return regionCourierReportDao.update(regionCourierReportEntity);
	}
	@Override
	public int delete(RegionCourierReportEntity regionCourierReportEntity) {
		return regionCourierReportDao.delete(regionCourierReportEntity);
	}
	public void setRegionCourierReportDao(
			IRegionCourierReportDao regionCourierReportDao) {
		this.regionCourierReportDao = regionCourierReportDao;
	}
}
/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  
 *  PROJECT NAME  : tfr-scheduling-api
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 调度、发车计划、排班、月台、车辆管理等
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/server/service/IPassOrderApplyService.java
 * 
 *  FILE NAME     :IPassOrderApplyService.java
 *  
 *  AUTHOR        : FOSS中转开发组
 *  
 *  TIME          : 
 *  
 *  HOME PAGE     :  http://www.deppon.com
 *  
 *  COPYRIGHT     : Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 *  VERSION       :0.1
 * 
 *  LAST MODIFY TIME:
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-scheduling
 * PACKAGE NAME: com.deppon.foss.module.transfer.scheduling.server.service.impl
 * FILE    NAME: IPassOrderApplyService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.scheduling.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.PassOrderApplyEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.OrderVehicleDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.PassOrderApplyDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.VehicleDriverWithDto;

/**
 * 受理约车Service
 * @author 104306-foss-wangLong
 * @date 2012-11-21 下午5:56:53
 */
public interface IPassOrderApplyService extends IService {

	/**
	 * 新增
	 * @author 104306-foss-wangLong
	 * @date 2012-11-21 下午6:07:20
	 * @param passOrderApplyEntity
	 * @return 受影响的行数 
	 */
	int addPassOrderApply(PassOrderApplyEntity passOrderApplyEntity);

	/**
	 * 修改
	 * @author 104306-foss-wangLong
	 * @date 2012-11-21 下午6:07:20
	 * @param passOrderApplyEntity
	 * @return 受影响的行数 
	 */
	int updatePassOrderApply(PassOrderApplyEntity passOrderApplyEntity);

	/**
	 * 查询集合
	 * @author 104306-foss-wangLong
	 * @date 2012-11-21 下午6:07:20
	 * @param passOrderApplyEntity
	 * @return java.util.List
	 */
	List<PassOrderApplyEntity> queryPassOrderApplyList(PassOrderApplyEntity passOrderApplyEntity);
	
	/**
	 * 根据约车单号查询
	 * @author 104306-foss-wangLong
	 * @date 2012-11-27 下午3:49:41
	 * @param orderNo 约车编号
	 * @return {@link PassOrderApplyEntity}
	 */
	PassOrderApplyEntity queryPassOrderApplyByOrderNo(String orderNo);

	/**
	 * 查询集合 分页
	 * @author 104306-foss-wangLong
	 * @date 2012-11-21 下午6:07:20
	 * @param passOrderApplyEntity
	 * @param start   
	 * @param pageSize
	 * @return java.util.List
	 */
	List<PassOrderApplyEntity> queryPassOrderApplyForPage(PassOrderApplyEntity passOrderApplyEntity, int start, int pageSize);

	/**
	 * 统计记录数
	 * @author 104306-foss-wangLong
	 * @date 2012-11-21 下午6:10:11
	 * @param passOrderApplyEntity
	 * @return {@link java.lang.Long}
	 */
	Long queryCount(PassOrderApplyEntity passOrderApplyEntity);

	/**
	 * 根据约车单号查询  约车审核最终信息， 和约车log信息
	 * @author 104306-foss-wangLong
	 * @date 2012-11-29 下午12:30:47
	 * @param orderNo 约车单号
	 * @return PassOrderApplyEntity
	 */
	PassOrderApplyDto queryPassOrderApplyAndAuditOrderApplyLog(String orderNo);

	/**
	 * 约车审核通过
	 * @author 104306-foss-wangLong
	 * @date 2012-11-24 上午2:09:58
	 * @param passOrderApplyDto
	 * @param orderId
	 */
	void doAcceptedOrderVehicleApply(PassOrderApplyDto passOrderApplyDto, String orderId);

	/**
	 * 查询公司车 & 借来的车辆
	 * @author 104306-foss-wangLong
	 * @date 2012-12-18 下午8:33:12
	 * @param vehicleDriverWithDto
	 */
	List<VehicleDriverWithDto> queryCompanyVehicleAndBorrowVehicle(VehicleDriverWithDto vehicleDriverWithDto);
	
	/**
	 * 查询公司车 & 借来的车辆 by Page
	 * @author huyue
	 * @date 2013-4-16 上午11:03:35
	 */
	public OrderVehicleDto queryCompanyVehicleAndBorrowVehicleByPage(VehicleDriverWithDto vehicleDriverWithDto, int start, int limit);
}
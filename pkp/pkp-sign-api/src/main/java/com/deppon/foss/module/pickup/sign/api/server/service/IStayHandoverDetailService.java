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
 * PROJECT NAME	: pkp-sign-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/server/service/IStayHandoverDetailService.java
 * 
 * FILE NAME        	: IStayHandoverDetailService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.server.service;


import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.sign.api.shared.domain.StayHandoverDetailEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.DeliverbillDetailDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.StayHandoverDetailDto;

/**
 * 交接明细Service
 * @author foss-meiying
 * @date 2012-11-9 下午6:50:21
 * @since
 * @version
 */
public interface IStayHandoverDetailService extends IService {

	/**
	 * 添加交接明细表
	 * @author foss-meiying
	 * @date 2012-11-9 下午6:49:47
	 * @param entity
	 * @return
	 * @see
	 */
	StayHandoverDetailEntity addStayHandoverDetail(StayHandoverDetailEntity entity);
	/**
     * 查询WaybillPending(运单带处理信息)里的运单号，货物总件数,是否作废
     * @author foss-meiying
     * @date 2012-12-11 下午3:04:39
     * @param driverCode 司机工号
     * @return
     * @see
     */
    List<StayHandoverDetailDto> queryPickupByWaybillPending(String driverCode);

    /**
     * 根据派送单编号 修改交接id 
     * @author foss-meiying
     * @date 2013-2-1 上午9:48:33
     * @param dto
     * @see
     */
    void updateByDeliverbillNo(DeliverbillDetailDto dto);
    /**
     * 根据运单号查询交接明细信息
     * @author foss-meiying
     * @date 2013-3-17 下午4:02:34
     * @param waybillNo
     * @return
     * @see
     */
    StayHandoverDetailEntity queryByWaybillNo(String waybillNo);
    /**
     * 根据主键修改交接明细信息
     * @author foss-meiying
     * @date 2013-3-17 下午4:42:09
     * @param entity
     * @return
     * @see
     */
    int updateByPrimaryKeySelective(StayHandoverDetailEntity entity);
	/**
	 * 根据快递员查询补录的接货信息
	 * @param expressEmpCode
	 * @return
	 */
	List<StayHandoverDetailDto> queryPickupByWaybillPendingExpress(
			String expressEmpCode);
	/**
	 * 根据车号查询WayBillPending表货物信息
	* @author 329757-foss-liuxiangcheng 
	* @date 2016-7-23 上午8:26:11
	* @param @param vehicleNo
	* @param @return    设定文件 
	* @return List<StayHandoverDetailDto>    返回类型 
	* @throws
	 */
	List<StayHandoverDetailDto> queryPickupByWaybillPendingByVehicleNo(
			String vehicleNo,boolean isDriver);
	/**
	 * 根据司机工号查询waybillpending货物信息
	 * @param driverCode
	 * @param isDriver
	 * @return
	 */
	List<StayHandoverDetailDto> queryPickupByWaybillPendingDetail(
			String driverCode, boolean isDriver);
}
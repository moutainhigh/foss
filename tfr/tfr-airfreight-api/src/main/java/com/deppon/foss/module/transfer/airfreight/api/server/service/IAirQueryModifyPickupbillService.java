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
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-airfreight-api
 *  
 *  package_name  : 
 *  
 *  FILE PATH          :/IAirQueryModifyPickupbillService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 *  
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.airfreight.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickupbillDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickupbillEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirTranDataCollectionEntity;

/**
 * 查询修改合大票service 
 * @author 099197-foss-zhoudejun
 * @date 2012-12-18 上午9:16:48
 * 
 */
public interface IAirQueryModifyPickupbillService extends IService {
	
	/**
	 * 修改、保存、删除 合大票清单 
	 * @param modifyAirPickupbillDetailList 需要修改的合大票明细list
	 * @param addAirPickupbillDetailList 需要新增的合大票明细list
	 * @param delAirPickupbillDetails 需要删除的合大票明细
	 * @return (0或1)
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-18 上午9:03:53
	 */
	int modifySaveDeleteAirPickupbillDetails (
			AirTranDataCollectionEntity airTranDataCollectionEntity,
			List<AirPickupbillDetailEntity> airPickupbillDetailList);
	
	
	/**
	 * 根据运单号查询空运运单费用及出港信息
	 * @param waybillNo 运单号
	 * @return AirPickupbillEntity
	 * @author 099197-foss-zhoudejun
	 * @date 2013-1-15 下午2:38:23
	 */
	AirPickupbillEntity queryAirPickupbill(String waybillNo);
	
	/**
	 * 根据运单号查询空运运单合票信息  供综合查询显示内部轨迹
	 * @param waybillNo 运单号
	 * @return AirPickupbillEntity
	 * @author liuzhaowei
	 * @date 2013-6-13 下午2:38:23
	 */
	List<AirPickupbillEntity> queryAirPickupbillListForViewTrack(String waybillNo);
	
	/**
	 * 根据合大票id获取合大票清单明细 
	 * @param airPickupbillId 合大票Id
	 * @return  List<AirPickupbillDetailEntity> 返回合大票清单明细
	 * @author liuzhaowei
	 * @date 2013-06-25 上午8:58:58
	 */
	List<AirPickupbillDetailEntity> queryAirPickupbillDetailListByPrimaryId(String airPickupbillId);
	
	/**
	 *  根据运单号查询运单结算信息是否可以修改
	 * @param waybillNo 运单号
	 * @author liuzhaowei
	 * @date 2013-4-23 下午2:38:23
	 */
	void validateAirJointTicketDetail(String waybillNo);
	
	/**
	 *  接送货做更改单时同步修改合大票明细信息
	 * @param entity 空运合大票明细
	 * @author liuzhaowei
	 * @date 2013-7-1 下午2:38:23
	 */
	public void updatePickupDetailFromModifyWaybill(AirPickupbillDetailEntity entity);
	
	/**
	 * 删除合票清单
	 * @param entity 空运合大票明细
	 * @author foss-105795-wqh
	 * @date 2014-01-20
	 */
	public void deleteAirPickupAndDetailByAirPickupId(String id);
}
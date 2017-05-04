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
 *  FILE PATH          :/IAirQueryModifyPickupbillDao.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 *  
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.airfreight.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickupbillDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickupbillEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickupbillLogEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.SerialEntity;

/**
 * 查询修改合大票清单dao 
 * @author 099197-foss-zhoudejun
 * @date 2012-12-18 上午9:16:19
 * 
 */
public interface IAirQueryModifyPickupbillDao {

	/**
	 * 修改合大票
	 * @param airPickupbillEntity
	 * @return (0或1)
	 * @author foss-liuzhaowei
	 * @date 2013-05-20 上午9:41:51
	 */
	int modifyAirPickupbill(AirPickupbillEntity airPickupbillEntity);
	
	/**
	 * 批量修改合大票明细list
	 * @param airPickupbillDetailList
	 * @return (0或1)
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-18 上午9:41:51
	 */
	int modifyAirPickupbillDetailList (List<AirPickupbillDetailEntity> airPickupbillDetailList);
	
	/**
	 * 批量新增合大票明细list 
	 * @param addAirPickupbillDetailList
	 * @return (0或1)
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-18 上午9:42:58
	 */
	int addAirPickupbillDetailList (List<AirPickupbillDetailEntity> addAirPickupbillDetailList);
	
	/**
	 * 批量删除合大票明细
	 * @param delelePickupbillIds 
	 * @return (0或1)
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-18 上午9:45:10
	 */
	int deleteAirPickupbillDetailList (List<String> delelePickupbillIds);
	
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
	 * @return List<AirPickupbillEntity>
	 * @author liuzhaowei
	 * @date 2013-6-13 下午2:38:23
	 */
	List<AirPickupbillEntity> queryAirPickupbillListForViewTrack(String waybillNo);
	
	/**
	 * 根据合大票id查询合大票运单明细
	 * @param airPickupbillId 合大票id 
	 * @return List<AirPickupbillDetailEntity>
	 * @author liuzhaowei
	 * @date 2013-6-25  下午2:38:23
	 */
	List<AirPickupbillDetailEntity> queryAirPickupbillDetailListByPrimaryId(String airPickupbillId);
	
	/**
	 * 根据运单号list查询符合条件的运单原始送货费
	 * @param List<pickupDetailIdList> 合大票明细id
	 * @return List<AirPickupbillEntity>
	 * @author 099197-foss-zhoudejun
	 * @date 2013-1-15 下午2:38:23
	 */
	List<AirPickupbillDetailEntity> queryAirPickupbillDetailListByIds(List<String> pickupDetailIdList);

	/**
	 *  接送货做更改单时同步修改合大票明细信息
	 * @param entity 空运合大票明细
	 * @author liuzhaowei
	 * @date 2013-7-1 下午4:38:23
	 */
	int updatePickupDetailFromModifyWaybill(AirPickupbillDetailEntity entity);
	/**
	 *  根据航空正单删除合票单据
	 * @param airWaybillNo 正单号
	 * @author wqh
	 * @date 2013-08-28 
	 */
	int deleteAirPickupBillByAirWaybill(String airWaybillNo);
	
	
	/**
	 *  根据合票清单Id删除合票单据
	 * @param airPickupbillId id
	 * @author wqh
	 * @date 2013-08-28 
	 */
	int deleteAirPickupBillById(String airPickupbillId);
	
	
	/**
	 *  根据运单号查询合票清单List
	 * @param waybillNo 运单号
	 * @author wqh
	 * @date 2013-09-08 
	 */
	List<AirPickupbillEntity> queryAirPickupbillListByWaybillNo(String waybillNo);
	
	/**
	 *  添加合票日志
	 * @param airPickupbillLogEntity 合票日志
	 * @author foss-105795-wqh
	 * @date 2014-01-21 
	 */
	void addAirPickupbillLog(AirPickupbillLogEntity airPickupbillLogEntity);
	/**
	 * 
	* @description 批量删除合大票清单流水表信息
	* @param deletePickupDetailList
	* @return
	* @version 1.0
	* @author 269701-foss-lln
	* @update 2016年5月21日 上午11:09:53
	 */
	int deleteAirPickupbillSerialList(List<String> deletePickupDetailList);


	/**
	 * 
	* @description 批量新增合大票清单流水数据
	* @param serialList
	* @version 1.0
	* @author 269701-foss-lln
	* @update 2016年5月21日 下午5:16:00
	 */
	void addAirPickupbillSerialist(List<SerialEntity> serialList);
}
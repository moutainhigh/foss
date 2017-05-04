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
 *  FILE PATH          :/IAirQueryModifyTfrPickupBillDao.java
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
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirTransPickupDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirTransPickupbillEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirTransPickupBillDto;

/**
 * 查询_修改中转提货清单 dao接口
 * @author 099197-foss-zhoudejun
 * @date 2012-12-19 下午5:17:21
 * 
 */
public interface IAirQueryModifyTfrPickupBillDao {

	/**
	 * 根据空运总调、到达网点、制单时间、中转单号、目的站、中转航班
	 * 查询中转提货清单信息
	 * @param airTransPickupBillDto
	 * @return AirTransPickupbillEntity
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-19 下午5:15:31
	 */
	List<AirTransPickupbillEntity> queryTfrPickupBill (AirTransPickupBillDto airTransPickupBillDto,int limit ,int start);
	
	/**
	 * 根据中转提货清单id查询中转提货明细信息
	 * @param airTransferPickUpBillId 中转提货清单主键id
	 * @return AirTransPickupDetailEntity 中转提货明细list
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-20 下午1:11:33
	 */
	List<AirTransPickupDetailEntity> queryTfrPickupBillDetail (String airTransferPickUpBillId);
	
	/**
	 * 根据运单号查询同一(目的站、到达网点、中转航班、中转日期) 
	 * @param airTransPickupBillDto
	 * @return AirPickupbillDetailEntity
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-20 下午4:07:20
	 */
	List<AirPickupbillDetailEntity> queryToAddTfrPickupBillDetail(AirTransPickupBillDto airTransPickupBillDto);
	
	/**
	 * 新增中转提货清单明细list
	 * @param airPickupbillDetailList 中转提货清单明细list
	 * @return (0或1)
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-21 下午2:39:55
	 */
	int addAirTransPickupDetailList(List<AirTransPickupDetailEntity> addAirTransPickupDetailList);
	
	/**
	 * 更新中转提货清单明细
	 * @param modifyAirPickupbillDetailList 需要修改的明细list
	 * @return (0或1)
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-21 下午2:39:59
	 */
	int modifyAirTransPickupDetailList(List<AirTransPickupDetailEntity> modifyAirTransPickupDetailList);
	
	/**
	 * 批量删除中转提货清单明细
	 * @param ids
	 * @return (0或1)
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-21 下午3:34:12
	 */
	int delTransPickupDetailList(List<String> ids);
	
	/**
	 * 查询中转货清单  综合查询显示运单内部轨迹 
	 * @param waybillNo
	 * @return List<AirTransPickupbillEntity>
	 * @date 2013-6-17 下午5:15:31
	 */
	List<AirTransPickupbillEntity> queryTfrPickupBillListForViewTrack(String waybillNo);
	
	/**
	 * 根据中转提货清单id查询中转提过货清单
	 * @param airTransferPickUpBillId id
	 * @return AirTransPickupbillEntity 返回中转提货清单
	 * @author liuzhaowei
	 * @date 2013-07-09 上午9:40:31
	 */
	AirTransPickupbillEntity queryAirTransPickupbillEntityById(String airTransferPickUpBillId);
	
	/**
	 * 根据中转提货清单id删除中转提过货清单
	 * @param airTransferPickUpBillId id
	 * @return void
	 * @author 105795-foss-wqh
	 * @date 2014-01-13
	 */
	void deleteTfrAirPickupbillById(String airTransferPickUpBillId);
	
	/**
	 * 根据运单号查询所有中转提货清单
	 * @param airTransferPickUpBillId id
	 * @return void
	 * @author 105795-foss-wqh
	 * @date 2014-01-13
	 */
	List<AirTransPickupbillEntity> queryAirTransPickupbillByWaybillNo(String waybillNo);
}
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
 *  FILE PATH          :/IAirChangeInventoryDao.java
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

import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirChangeInventoryDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirChangeInventoryEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickupbillDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickupbillEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirRevisebillDetailEntity;

/**
 * 变更清单dao
 * @author 099197-foss-zhoudejun
 * @date 2012-12-12 下午4:35:20
 */
public interface IAirChangeInventoryDao {
	
	/**
	 * (空运)根据运单号查询合票明细
	 * @param waybillNO 运单号
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-12 下午4:40:50
	 */
	List<AirChangeInventoryEntity> queryChangeInventoryDetail (String waybillNO,String createOrgCode);
	
	/**
	 * 根据合大票明细id查询修改记录
	 * @param airPickupbillDetailId 合票明细id
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-12 下午7:38:38
	 */
	List<AirChangeInventoryDetailEntity> queryAirRevisebillDetailList (String waybillNO,String createOrgCode);
	
	/**
	 * 新增变更清单明细logger日志
	 * @param airRevisebillDetailEntity 变更清单明细
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-12 下午7:38:38
	 */
	int addAirRevisebillDetailEntity(AirRevisebillDetailEntity airRevisebillDetailEntity);
	
	/**
	 * 批量新增变更清单明细logger日志
	 * @param airRevisebillDetailList 变更清单明细list
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-13 下午1:01:35
	 */
	int addaddAirRevisebillDetailList (List<AirRevisebillDetailEntity> airRevisebillDetailList);
	
	/**
	 * 变更清单
	 * @param airChangeInventoryList 明细
	 * @param parameterMap 备注map
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-13 下午8:30:58
	 */
	int updateAirWaybillDetail(List<AirPickupbillDetailEntity> airPickupbillDetailList);
	
	/**
	 * 修改中转提货清单
	 * @param airPickupbillDetailList 明细
	 * @author 046130-foss-xuduowei
	 * @date 2013-03-05 下午8:30:58
	 */
	int updateTransPickupDetail(List<AirPickupbillDetailEntity> airPickupbillDetailList);
	
	/**
	 * 修改清单logger日志 
	 * @param airRevisebillDetailList 变更日志
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-18 下午7:24:18
	 */
	int updateAirRevisebillDetailLogger (List<AirRevisebillDetailEntity> airRevisebillDetailList);
	
	/**
	 * 返回合票清单
	 * @param airPickupbillId 合大票清单主键id
	 * @return AirPickupbillEntity 合大票清单实体
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-14 下午5:42:25
	 */
	AirPickupbillEntity queryOldAirPickupbillEntity(String airPickupbillId);
	
	/**
	 * 根据id查询合票明细
	 * @param listIds 主键id
	 * @return List<AirPickupbillDetailEntity> queryOldAirPickupbillDetails
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-15 上午11:10:08
	 */
	List<AirPickupbillDetailEntity> queryOldAirPickupbillDetails(List<String> listIds);
	
}
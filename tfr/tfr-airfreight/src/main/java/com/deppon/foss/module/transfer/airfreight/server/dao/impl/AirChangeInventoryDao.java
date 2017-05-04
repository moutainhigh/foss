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
 *  PROJECT NAME  : tfr-airfreight
 *  
 *  package_name  : 
 *  
 *  FILE PATH          :/AirChangeInventoryDao.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 *  
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.airfreight.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.airfreight.api.server.dao.IAirChangeInventoryDao;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirChangeInventoryDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirChangeInventoryEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickupbillDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickupbillEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirRevisebillDetailEntity;

/**
 * 变更清单dao实现
 * @author 099197-foss-zhoudejun
 * @date 2012-12-12 下午4:36:20
 */
public class AirChangeInventoryDao extends iBatis3DaoImpl implements
		IAirChangeInventoryDao {
	
	private static final String NAMESPACE = "foss.airfreight.";
	
	/**
	 * (空运)根据运单号查询合大票明细
	 * @param waybillNO 运单号
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-12 下午4:40:50
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AirChangeInventoryEntity> queryChangeInventoryDetail(
			String waybillNO,String createOrgCode) {
		Map<String,String> dataMap = new HashMap<String, String>();
		dataMap.put("waybillNo", waybillNO);
		dataMap.put("createOrgCode", createOrgCode);
		return this.getSqlSession().selectList(NAMESPACE + "queryChangeInventoryDetail",dataMap);
	}
	
	/**
	 * 根据合大票明细id查询修改记录
	 * @param airPickupbillDetailId 合大票明细id
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-12 下午7:38:38
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AirChangeInventoryDetailEntity> queryAirRevisebillDetailList(
			String waybillNO,String createOrgCode) {
		Map<String,String> dataMap = new HashMap<String, String>();
		dataMap.put("waybillNo", waybillNO);
		dataMap.put("createOrgCode", createOrgCode);
		return this.getSqlSession().selectList(NAMESPACE + "queryAirRevisebillDetailList", dataMap);
	}
	
	/**
	 * 新增变更清单明细logger日志
	 * @param airRevisebillDetailEntity 变更清单明细
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-12 下午7:38:38
	 */
	@Override
	public int addAirRevisebillDetailEntity(
			AirRevisebillDetailEntity airRevisebillDetailEntity) {
		return this.getSqlSession().insert(NAMESPACE + "addAirRevisebillDetailEntity",airRevisebillDetailEntity);
	}

	/**
	 * 批量新增变更清单明细logger日志
	 * @param airRevisebillDetailList 变更清单明细list
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-13 下午1:01:35
	 */
	@Override
	public int addaddAirRevisebillDetailList(
			List<AirRevisebillDetailEntity> airRevisebillDetailList) {
		return this.getSqlSession().insert(NAMESPACE + "addaddAirRevisebillDetailList",airRevisebillDetailList);
	}

	/**
	 * 变更清单,修改清单logger日志 
	 * @param airChangeInventoryList 明细
	 * @param parameterMap 备注map
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-13 下午8:30:58
	 */
	@Override
	public int updateAirWaybillDetail(List<AirPickupbillDetailEntity> airPickupbillDetailList) {
 		this.getSqlSession().update(NAMESPACE + "updateAirWaybillDetail",airPickupbillDetailList);
		return 0;
	}
	
	/**
	 * 修改中转提货清单
	 * @param airPickupbillDetailList 明细
	 * @author 046130-foss-xuduowei
	 * @date 2013-03-05 下午8:30:58
	 */
	@Override
	public int updateTransPickupDetail(
			List<AirPickupbillDetailEntity> airPickupbillDetailList) {
		return getSqlSession().update(NAMESPACE + "updateTransPickupDetail",airPickupbillDetailList);
	}

	/**
	 * 修改清单logger日志 
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-18 下午7:26:03
	 * @see com.deppon.foss.module.transfer.airfreight.api.server.dao.IAirChangeInventoryDao#updateAirRevisebillDetailLogger(java.util.List)
	 */
	public int updateAirRevisebillDetailLogger (List<AirRevisebillDetailEntity> airRevisebillDetailList){
		return this.getSqlSession().update(NAMESPACE + "updateAirWaybillLogger",airRevisebillDetailList);
	}
	/**
	 * 返回合大票清单
	 * @param airPickupbillId 合大票清单主键id
	 * @return AirPickupbillEntity 合大票清单实体
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-14 下午5:42:25
	 */
	@Override
	public AirPickupbillEntity queryOldAirPickupbillEntity(
			String airPickupbillId) {
		return (AirPickupbillEntity)this.getSqlSession().selectList(NAMESPACE + "queryOldAirPickupbillEntity",airPickupbillId).get(0);
	}

	/**
	 * 根据id查询合大票明细
	 * @param listIds 主键id
	 * @return List<AirPickupbillDetailEntity> queryOldAirPickupbillDetails
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-15 上午11:10:08
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AirPickupbillDetailEntity> queryOldAirPickupbillDetails(
			List<String> listIds) {
		return this.getSqlSession().selectList(NAMESPACE + "queryOldAirPickupbillDetails",listIds);
	}
}
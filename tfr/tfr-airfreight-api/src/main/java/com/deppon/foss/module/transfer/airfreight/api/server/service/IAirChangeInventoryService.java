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
 *  FILE PATH          :/IAirChangeInventoryService.java
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

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickupbillDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirRevisebillDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirTransPickupBillDto;

/**
 * 变更清单service 
 * @author 099197-foss-zhoudejun
 * @date 2012-12-12 下午4:33:07
 */
public interface IAirChangeInventoryService extends IService {
	
	/**
	 * (空运)根据运单号查询合大票明细
	 * @param waybillNO 运单号
	 * @param createOrgCode 部门编码
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-12 下午4:40:50
	 */
	AirTransPickupBillDto queryChangeInventoryDetail (String waybillNO,String createOrgCode);
	
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
	 * 变更清单,修改清单logger日志 
	 * @param airChangeInventoryList 明细
	 * @param parameterMap 备注map
	 * @param getStlWayBillNoMap 需要调用结算的map
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-13 下午8:30:58
	 */
	@SuppressWarnings("rawtypes")
	List<List> updateAirWaybillDetailOrLogger (List<AirPickupbillDetailEntity> airChangeInventoryList, Map<String,String> parameterMap,
			Map<String,String> stlWayBillNoMap,Map<String,String> delWayBillNoMap);
	
	/**
	 * 修改清单logger日志 
	 * @param airRevisebillDetailList 变更日志
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-18 下午7:24:18
	 */
	int updateAirRevisebillDetailLogger (List<AirRevisebillDetailEntity> airRevisebillDetailList);
	
	/**
	 * 上传变更清单给EDI 
	 * @param airWaybillNo 正单号
	 * @return InputStream 返回(明细)
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-19 下午2:36:34
	 */
	InputStream uploadChangeListCallEdi(List<String> idsList ,String callIsNotEdiFlag,String airWaybillNo);

}
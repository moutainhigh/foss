
/**
 *  initial comments.
 */
/*******************************************************************************
 * 
 * 
 * 
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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/airfreight/server/service/impl/AirQueryModifyPickupbillService.java
 *  
 *  FILE NAME          :AirPickupbillDetailService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 *  
 *  
 *  
 *  
 *  
 *  
 ******************************************************************************/
package com.deppon.foss.module.transfer.airfreight.server.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import com.deppon.foss.module.transfer.airfreight.api.server.dao.IAirQueryModifyPickupbillDao;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirPickupbillDetailService;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickupbillEntity;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;

public class AirPickupbillDetailService implements IAirPickupbillDetailService {

	private IAirQueryModifyPickupbillDao airQueryModifyPickupbillDao;
	
	
	/**
	 *  根据运单号查询合票清单List
	 * @param waybillNo 运单号
	 * @author wqh
	 * @date 2013-09-08 
	 */
   public List<AirPickupbillEntity> queryAirPickupbillListByWaybillNo(
			String waybillNo) {
		//判断参数合法性
	 	if(StringUtils.isEmpty(waybillNo)){
	 		throw new TfrBusinessException("运单号为空");
	 	}
		//调用接口
	 	List<AirPickupbillEntity> airPickupbillList=
	 			airQueryModifyPickupbillDao.
	 			queryAirPickupbillListByWaybillNo(waybillNo);
		/*if(CollectionUtils.isEmpty(airPickupbillList))
		{
			throw new TfrBusinessException("运单:"+waybillNo+" 没有做过合票");
		}*/
		return airPickupbillList;
	}


   //注入IAirQueryModifyPickupbillDao
	public void setAirQueryModifyPickupbillDao(
			IAirQueryModifyPickupbillDao airQueryModifyPickupbillDao) {
		this.airQueryModifyPickupbillDao = airQueryModifyPickupbillDao;
	}
	
}

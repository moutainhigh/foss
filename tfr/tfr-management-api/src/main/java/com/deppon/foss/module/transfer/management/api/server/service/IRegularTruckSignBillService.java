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
 *  PROJECT NAME  : tfr-management-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/management/api/server/service/IRegularTruckSignBillService.java
 *  
 *  FILE NAME          :IRegularTruckSignBillService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.management.api.server.service;

import java.io.InputStream;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.management.api.shared.domain.RegularTruckSignBillEntity;
import com.deppon.foss.module.transfer.management.api.shared.dto.RegularTruckSignBillDto;

/**
 *   专线对发签单Service接口
 * 
 * @author dp-liming
 * @date 2012-12-19 下午13:50:07
 */
public interface IRegularTruckSignBillService extends IService {

	/**
	 *   新增专线对发签单
	 * 
	 * @author dp-liming
	 * @date 2012-12-19 下午14:10:47
	 */
	 int addRegularTruckSignBill(RegularTruckSignBillEntity regularTruckSignBillEntity);

	/**
	 *   删除专线对发签单
	 * 
	 * @author dp-liming
	 * @date 2012-12-19 下午14:23:12
	 */
	 int deleteRegularTruckSignBill(String id);

	/**
	 *   修改专线对发签单
	 * 
	 * @author dp-liming
	 * @date 2012-12-19 下午14:25:46
	 */
	 int updateRegularTruckSignBill(RegularTruckSignBillEntity regularTruckSignBillEntity);

	/**
	 *   查询专线对发签单
	 * 
	 * @author dp-liming
	 * @date 2012-12-19 下午14:27:23
	 */
	 List<RegularTruckSignBillEntity> queryRegularTruckSignBill(RegularTruckSignBillDto regularTruckSignBillDto, int start, int limit);

	/**
	 *   查询专线对发签单总数
	 * 
	 * @author dp-liming
	 * @date 2012-12-19 下午14:28:24
	 */
	 Long queryCount(RegularTruckSignBillDto regularTruckSignBillDto);
	 
	/**
	 *   查询费用和提成
	 * 
	 * @author dp-liming
	 * @date 2012-12-19 下午14:30:47
	 */
	 RegularTruckSignBillDto queryRegularTruckSignBillByFee(RegularTruckSignBillDto regularTruckSignBillDto);

	/**
	 *   根据Id查询专线对发签单
	 * 
	 * @author dp-liming
	 * @date 2012-12-19 下午14:35:47
	 */
	 RegularTruckSignBillEntity queryRegularTruckSignBillById(String id);
	 
	/**
	 *  计算费用提成
	 * 
	 * @author dp-liming
	 * @date 2012-12-19 下午14:35:47
	 */
	 RegularTruckSignBillEntity calculateRegularTruckSignBillFee(RegularTruckSignBillDto regularTruckSignBillDto);
	 
	/**
	 *   导出excel
	 * 
	 * @author dp-liming
	 * @date 2012-11-29 上午10:50:47
	 */
	 InputStream queryExportRegularTruckSignBill(RegularTruckSignBillDto regularTruckSignBillDto);
	
	
	/**
	 *   根据车牌号查询专线对发签单
	 * 
	 * @author dp-liming
	 * @date 2012-12-19 下午14:35:47
	 */
	 RegularTruckSignBillEntity queryRegularTruckSignBillByVehicleNo(String vehicleNo);
 
}
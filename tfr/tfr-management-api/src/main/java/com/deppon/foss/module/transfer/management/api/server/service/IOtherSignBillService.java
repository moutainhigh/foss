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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/management/api/server/service/IOtherSignBillService.java
 *  
 *  FILE NAME          :IOtherSignBillService.java
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
import com.deppon.foss.module.transfer.management.api.shared.domain.OtherSignBillEntity;
import com.deppon.foss.module.transfer.management.api.shared.dto.OtherSignBillDto;
/**
 *   其他签单Service接口
 * 
 * @author dp-liming
 * @date 2012-11-29 上午10:50:47
 */
public interface IOtherSignBillService extends IService {
	/**
	 *   新增其他签单
	 * 
	 * @author dp-liming
	 * @date 2012-11-29 上午10:50:47
	 */
	 int addOtherSignBill(OtherSignBillEntity otherSignBillEntity);

	/**
	 *   删除其他签单
	 * 
	 * @author dp-liming
	 * @date 2012-11-29 上午10:50:47
	 */
	 int deleteOtherSignBill(String id);

	/**
	 *   修改其他签单
	 * 
	 * @author dp-liming
	 * @date 2012-11-29 上午10:50:47
	 */
	 int updateOtherSignBill(OtherSignBillEntity otherSignBillEntity);

	/**
	 *   查询其他签单
	 * 
	 * @author dp-liming
	 * @date 2012-11-29 上午10:50:47
	 */
	 List<OtherSignBillEntity> queryOtherSignBill(OtherSignBillDto otherSignBillDto, int start, int limit);

	/**
	 *   查询其他签单总数
	 * 
	 * @author dp-liming
	 * @date 2012-11-29 上午10:50:47
	 */
	 Long queryCount(OtherSignBillDto otherSignBillDto);
	 
	/**
	 *   查询其他签单费用和提成
	 * 
	 * @author dp-liming
	 * @date 2012-11-29 上午10:50:47
	 */
	OtherSignBillDto queryOtherSignBillByFee(OtherSignBillDto otherSignBillDto);


	/**
	 *   根据Id查询其他签单
	 * 
	 * @author dp-liming
	 * @date 2012-11-29 上午10:50:47
	 */
	 OtherSignBillEntity queryOtherSignBillById(String id);
	 
	/**
	 *   根据Id查询其他签单
	 * 
	 * @author dp-liming
	 * @date 2012-11-29 上午10:50:47
	 */
	 OtherSignBillEntity queryOtherSignBillByVehicleNo(String vehicleNo);
		 
	/**
	 *   计算其他签单费用和提成
	 * 
	 * @author dp-liming
	 * @date 2012-11-29 上午10:50:47
	 */
	 OtherSignBillEntity calculateOtherSignBillByFee(OtherSignBillDto otherSignBillDto); 

	/**
	 *   导出excel
	 * 
	 * @author dp-liming
	 * @date 2012-11-29 上午10:50:47
	 */
	 InputStream queryExportOtherSignBill(OtherSignBillDto otherSignBillDto); 
	 
}
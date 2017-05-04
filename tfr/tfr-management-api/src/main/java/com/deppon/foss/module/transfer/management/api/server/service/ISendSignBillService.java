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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/management/api/server/service/ISendSignBillService.java
 *  
 *  FILE NAME          :ISendSignBillService.java
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
import com.deppon.foss.module.transfer.management.api.shared.domain.SendSignBillEntity;
import com.deppon.foss.module.transfer.management.api.shared.dto.SendSignBillDto;
/**
 *   派送签单Service接口
 * 
 * @author dp-liming
 * @date 2012-11-29 上午10:32:47
 */
public interface ISendSignBillService extends IService {
	/**
	 *   新增派送签单
	 * 
	 * @author dp-liming
	 * @date 2012-11-29 上午10:50:47
	 */
	 int addSendSignBill(SendSignBillEntity SendSignBillEntity);

	/**
	 *   删除派送签单
	 * 
	 * @author dp-liming
	 * @date 2012-11-29 上午10:50:47
	 */
	 int deleteSendSignBill(String id);

	/**
	 *   修改派送签单
	 * 
	 * @author dp-liming
	 * @date 2012-11-29 上午10:50:47
	 */
	 int updateSendSignBill(SendSignBillEntity SendSignBillEntity);

	/**
	 *   查询派送签单
	 * 
	 * @author dp-liming
	 * @date 2012-11-29 上午10:50:47
	 */
	 List<SendSignBillEntity> querySendSignBill(SendSignBillDto sendSignBillDto, int start, int limit);

	/**
	 *   查询派送签单总数
	 * 
	 * @author dp-liming
	 * @date 2012-11-29 上午10:50:47
	 */
	 Long queryCount(SendSignBillDto sendSignBillDto);
	 
	 /**
	 *   查询费用和提成
	 * 
	 * @author dp-liming
	 * @date 2012-11-29 上午10:50:47
	 */
	 SendSignBillDto querySendSignBillByFee(SendSignBillDto sendSignBillDto);

	/**
	 *   根据Id查询派送签单
	 * 
	 * @author dp-liming
	 * @date 2012-11-29 上午10:50:47
	 */
	SendSignBillEntity  querySendSignBillById(String id);
	
	
	/**
	 *   导出excel
	 * 
	 * @author dp-liming
	 * @date 2012-11-29 上午10:50:47
	 */
	InputStream queryExportSendSignBill(SendSignBillDto sendSignBillDto); 
	 
	/**
	 *   查询费用和提成
	 * 
	 * @author dp-liming
	 * @date 2012-11-29 上午10:50:47
	 */
	 SendSignBillEntity calculateSendSignBillByFee(SendSignBillDto sendSignBillDto);
	
	/**
	 *   根据车牌号查询派送签单
	 * 
	 * @author dp-liming
	 * @date 2012-11-29 上午10:50:47
	 */
	SendSignBillEntity  querySendSignBillByVehicleNo(String vehicleNo);
	
	/**
	 *   根据派送单号查询派送签单信息
	 * 
	 * @author dp-liming
	 * @date 2012-11-29 上午10:50:47
	 */
	SendSignBillEntity queryDeliveryInfoByDeliverbillNo(String signBillNo);
}
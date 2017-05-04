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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/management/api/server/service/ITransferSignBillService.java
 *  
 *  FILE NAME          :ITransferSignBillService.java
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
import com.deppon.foss.module.transfer.management.api.shared.domain.TransferSignBillEntity;
import com.deppon.foss.module.transfer.management.api.shared.dto.TransferSignBillDto;

/**
 *  转货车签单Service接口
 * 
 * @author dp-liming
 * @date 2012-11-29 上午10:50:47
 */
public interface ITransferSignBillService extends IService {
	/**
	 *  新增转货车签单
	 * 
	 * @author dp-liming
	 * @date 2012-11-29 上午10:50:47
	 */
	 int addTransferSignBill(TransferSignBillEntity TransferSignBillEntity);

	/**
	 *  删除转货车签单
	 * 
	 * @author dp-liming
	 * @date 2012-11-29 上午10:50:47
	 */
	 int deleteTransferSignBill(String id);

	/**
	 *  修改转货车签单
	 * 
	 * @author dp-liming
	 * @date 2012-11-29 上午10:50:47
	 */
	 int updateTransferSignBill(TransferSignBillEntity TransferSignBillEntity);

	/**
	 *  查询转货车签单
	 * 
	 * @author dp-liming
	 * @date 2012-11-29 上午10:50:47
	 */
	 List<TransferSignBillEntity> queryTransferSignBill(TransferSignBillDto transferSignBillDto, int start, int limit);

	/**
	 *  查询转货车签单总数
	 * 
	 * @author dp-liming
	 * @date 2012-11-29 上午10:50:47
	 */
	 Long queryCount(TransferSignBillDto transferSignBillDto);
 
	/**
	 *  查询费用和提成
	 * 
	 * @author dp-liming
	 * @date 2012-11-29 上午10:50:47
	 */
	 TransferSignBillDto queryTransferSignBillByFee(TransferSignBillDto transferSignBillDto); 

	/**
	 *  根据Id查询转货车签单
	 * 
	 * @author dp-liming
	 * @date 2012-11-29 上午10:50:47
	 */
	 TransferSignBillEntity  queryTransferSignBillById(String id);

	/**
	 *   导出excel
	 * 
	 * @author dp-liming
	 * @date 2012-11-29 上午10:50:47
	 */
	 InputStream queryExportTransferSignBill(TransferSignBillDto transferSignBillDto);
	 
	 /**
	 *  根据车牌号查询转货车签单
	 * 
	 * @author dp-liming
	 * @date 2012-11-29 上午10:50:47
	 */
	 TransferSignBillEntity  queryTransferSignBillByVehicleNo(String vehicleNo);
	 
	/**
	 *  查询费用和提成
	 * 
	 * @author dp-liming
	 * @date 2012-11-29 上午10:50:47
	 */
	 TransferSignBillEntity calculateTransferSignBillByFee(TransferSignBillDto transferSignBillDto);
	 
}
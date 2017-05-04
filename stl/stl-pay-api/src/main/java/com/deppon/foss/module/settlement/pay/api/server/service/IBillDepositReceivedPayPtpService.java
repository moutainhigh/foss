/**
 * Copyright 2016 STL TEAM
 */
/*******************************************************************************
 * Copyright 2016 STL TEAM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: stl-consumer-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/settlement/pay/api/server/service/IBillDepositReceivedPayPtpService.java
 * 
 * FILE NAME        	: IBillDepositReceivedPayPtpService.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2016  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.pay.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillDepositReceivedPartnerDto;

/**
 * 对接合伙人预收单service接口
 * @author foss-Jiang Xun
 * @date 2016-01-07 下午02:37:00
 */
public interface IBillDepositReceivedPayPtpService extends IService{
	
	/**
	 * 新增合伙人预收单
	 * @author foss-Jiang Xun
	 * @date 2016-01-07 下午3:01:13
	 * @param 
	 * @param 
	 * @return 
	 * @see
	 */
	BillDepositReceivedPartnerDto addBillDepositReceivedPay(BillDepositReceivedPartnerDto billDepositReceivedPartnerDto);

}

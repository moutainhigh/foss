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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/settlement/consumer/api/server/service/IBillReceivablePtpService.java
 * 
 * FILE NAME        	: IBillReceivablePtpService.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2016  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.consumer.api.server.service;

import com.deppon.esb.core.exception.ESBBusinessException;
import com.deppon.esb.inteface.domain.receivable.add.PtpAddBillReceivableRequest;
import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillNewReceivablePtpDto;

/**
 * 对接合伙人生成应收单service接口
 * @author foss-Jiang Xun
 * @date 2016-01-07 上午11:21:13
 */

public interface IBillReceivablePtpService extends IService{
	
	/**
	 * 生成应收单
	 * @author foss-Jiang Xun
	 * @date 2016-01-07 上午11:23:13
	 */
	void generatReceivableBillController(BillNewReceivablePtpDto dto);
	
	/**
	 * 封装参数转成foss内部DTO
	 * @author foss-Jiang Xun
	 * @date 2016-01-14 下午8:16:00
	 */
	public BillNewReceivablePtpDto buildDto(PtpAddBillReceivableRequest request) throws ESBBusinessException ;

}

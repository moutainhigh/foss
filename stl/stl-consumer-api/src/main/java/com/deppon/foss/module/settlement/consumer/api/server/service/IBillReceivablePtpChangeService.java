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

import com.deppon.esb.inteface.domain.receivable.BillRecevivableChangedRequest;
import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillNewReceivablePtpDto;

/**
 * 对接合伙人更改生成应收单service接口
 * @author foss-hemingyu
 * @date 2016-01-07 上午11:21:13
 */

public interface IBillReceivablePtpChangeService extends IService{

	/**
	 * 获取操作人
	 * @author foss-hemingyu
	 * @date 2016-03-27 下午15:23:13
	 */
	CurrentInfo getPtpCurrentInfo(BillReceivableEntity entity);

	/**
	 * 红冲合伙人应收单.
	 * @author 黄乐为
	 * @date 2016-1-21 下午5:19:10
	 * @return
	 */
	public void writeBackBillReceivable(BillNewReceivablePtpDto dto);

	/**
	 * 根据运单号和应收类型生成合伙人应收单
	 * @author 黄乐为
	 * @date 2016-1-14 下午8:27:47
	 */
	public void addReceivableBillController(BillNewReceivablePtpDto dto);

	/**
	 * 封装参数转成foss内部DTO
	 * @author foss-hemingyu
	 * @date 2016-01-14 下午8:16:00
	 */
	public BillNewReceivablePtpDto buildDto(BillRecevivableChangedRequest request) throws SettlementException;
}

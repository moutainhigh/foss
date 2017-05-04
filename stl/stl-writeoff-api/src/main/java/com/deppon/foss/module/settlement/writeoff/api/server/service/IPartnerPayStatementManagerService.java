/**
 * Copyright 2013 STL TEAM
 */
/*******************************************************************************
 * Copyright 2013 STL TEAM
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
 * PROJECT NAME	: stl-writeoff-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/settlement/writeoff/api/server/service/IStatementModifyService.java
 * 
 * FILE NAME        	: IPayStatementManagerService.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.writeoff.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPaymentEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.PartnerPayStatementDEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.PartnerPayStatementEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.PartnerPayStatementDto;

/**
 * 付款对账单管理service接口类
 * @author 311396-foss-wangwenbo
 * @date 2016-02-22 下午 15:05:16
 */
public interface IPartnerPayStatementManagerService extends IService {

	/**
	 * 查询付款对账单信息(按合伙人)
	 * 
	 * @author 311396-foss-wangwenbo
	 * @date 2016-02-22 下午 15:05:16	
	 * @param queryDto
	 * 			付款对账单参数Dto,
	 *
	 */
	PartnerPayStatementDto queryStatement(PartnerPayStatementDto queryDto, int start, int limit);
	
	/**
	 * 查询对账单明细信息
	 * 
	 * @author 311396-foss-wangwenbo
	 * @date 2016-02-23 下午 5:11:16	
	 * @param queryDto
	 * 			付款对账单参数Dto,
	 */
	List<PartnerPayStatementDEntity> queryDetailByStatementBillNos(List<String> statementBillNos);
	/**
	 *付款
	 * 
	 * @author 311396-foss-wangwenbo
	 * @date 2016-02-28 下午 2:44:16	
	 */
	String toPayment(BillPaymentEntity paymentEntity, String[] billNos,	CurrentInfo currentInfo);
	/**
	 *按对账单号查询对账单集合
	 * 
	 * @author 311396-foss-wangwenbo
	 * @date 2016-02-28 下午 2:44:16	
	 */
	public List<PartnerPayStatementEntity> queryByStatementBillNos(List<String> statementBillNos);
	
	/**
	 * 确认对账单
	 * 
	 * @author 311396-foss-wangwenbo
	 * @date 2016-03-01 	
	 */
	PartnerPayStatementDto confirmStatement(PartnerPayStatementDto resultDto);
	/**
	 * 删除对账单明细接口
	 * 
	 * @author 311396-foss-wangwenbo
	 * @date 2016-03-03 	
	 */
	PartnerPayStatementDto deleteStatementDetail(PartnerPayStatementDto resultDto, CurrentInfo currentInfo);
	
	/**
	 * 查询可添加明细
	 * 
	 * @author 311396-foss-wangwenbo
	 * @date 2016-03-07 	
	 */
	PartnerPayStatementDto queryEntryForAdd(PartnerPayStatementDto resultDto, CurrentInfo currentInfo, int start, int limit);
	
	/**
	 * 添加明细
	 * 
	 * @author 311396-foss-wangwenbo
	 * @date 2016-03-09 	
	 */
	PartnerPayStatementDto addPartnerPayStatementDetail(PartnerPayStatementDto dto);
	
	/**
	 * 根据对账单号集合查询对账单
	 * 
	 * @author 311396-foss-wangwenbo
	 * @date 2016-03-09 	
	 */
	List<PartnerPayStatementEntity> queryPartnerPayStatementEntitesByBills(PartnerPayStatementDto dto);
	/**
	 * 根据对账单号集合查询对账单明细
	 * 
	 * @author 311396-foss-wangwenbo
	 * @date 2016-03-09 	
	 */
	List<PartnerPayStatementDEntity> queryPartnerPayStatementEntriesByBillNo(PartnerPayStatementDto dto);
	/**
	 * 付款失败回调更新对账单和明细
	 * 
	 * @author 311396-foss-wangwenbo
	 * @date 2016-03-10 	
	 */
	void updateBillAfterFailPay(List<String> statementBillNos, CurrentInfo currentInfo);
	
	/**
	 * 查询费用承担部门
	 * 
	 * @author 311396-foss-wangwenbo
	 * @date 2016-03-10 	
	 */
	PartnerPayStatementDto queryExpenseCenter(PartnerPayStatementDto dto);

	/**
	 * 批量删除合伙人付款对账单明细
	 * @author gpz
	 * @date 2016年12月6日
	 */
	PartnerPayStatementDto batchDeleteStatementEntry(
			List<PartnerPayStatementDEntity> detailList, CurrentInfo currentInfo);
}

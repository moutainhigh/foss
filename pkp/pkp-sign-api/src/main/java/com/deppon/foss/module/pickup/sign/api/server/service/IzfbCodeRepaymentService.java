/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-sign-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/server/service/IWaybillTransactionService.java
 * 
 * FILE NAME        	: IWaybillTransactionService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.server.service;

import java.math.BigDecimal;
import java.util.List;
import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.sign.api.shared.domain.RepaymentEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.WaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.TwoInOneWaybillDto;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.VerificationEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.CommonQueryParamDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.PdaRepaymentDto;

/**
 * 
 * 运单完结状态操作Service
 * @author 043258-foss-zhaobin
 * @date 2012-11-13 上午11:30:23
 */
public interface IzfbCodeRepaymentService extends IService 
{
	void zfbCodeWrietoff(VerificationEntity verificationEntity);
	
	List<PdaRepaymentDto> addPaymentInfo(List<CommonQueryParamDto> list);
	
	void addPaymentInfo(RepaymentEntity repaymentEntity, CurrentInfo currentInfo, WaybillDto waybilldto);
	
	CommonQueryParamDto isDriver(CommonQueryParamDto dto);
	
	String checkVerification(String waybill);
	
	BillReceivableEntity queryBillReceivable(String waybill);
	
	void addOriginalPaymentInfo(RepaymentEntity repaymentEntity, CurrentInfo currentInfo,
            String returnType, String waybillNo, TwoInOneWaybillDto twoInOneWaybillDto);
	
	PosCardEntity queryPosCard(String seriaNo, String deptCode, BigDecimal codAmount,
            BigDecimal actualFreight);
	
	void applyPosCardAndDetail(String waybillNo, BigDecimal codAmount, BigDecimal actualFreight,
            CurrentInfo currentInfo, PosCardEntity posCardEntity, String invoiceType);
}
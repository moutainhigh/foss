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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/server/service/ISignRfcService.java
 * 
 * FILE NAME        	: ISignRfcService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.RepaymentArrivesheetDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.SignResultDto;

/**
 * 查询签收变更结果
 * 
 * @author ibm-lizhiguo
 * @date 2012-10-18 上午9:44:11
 */
public interface ISignRfcService extends IService {

	/*
	 * 专线--setp1(反签收) 根据运单号,获得运到 相关联的，付款LIST和到达联LIST
	 * @author ibm-lizhiguo
	 * @date 2012-11-16 下午2:31:16
	 */
	RepaymentArrivesheetDto searchRepaymentArrivesheet(String waybillNo);
	/**
	 * 
	 *专线--setp2 保存
	 * @author ibm-lizhiguo
	 * @date 2012-11-16 下午4:21:40
	 */
	void saveChangeDedicated(SignResultDto dto, CurrentInfo currentInfo);
	/**
	 * 
	 * 根据运单号,获得空运和偏线的签收结果
	 * @author ibm-lizhiguo
	 * @date 2012-11-19 上午10:40:30
	 */	
	WaybillSignResultEntity searchWaybillSignResult(String waybillNo);
	/**
	 * 
	 * 保存空运偏线提出的变更申请
	 * @author ibm-lizhiguo
	 * @date 2012-11-19 上午11:34:49
	 */	
	void saveChangeAirliftPartialLine(SignResultDto dto, CurrentInfo currentInfo);
	 
	
	/***反签收***/
	/**
	 * 
	 * 在反签收查询中，只要有到达联的状态是修改中，将不能做付款的反签收
	 * @author ibm-lizhiguo
	 * @date 2012-11-19 下午2:53:51
	 */	
	RepaymentArrivesheetDto searchReverseSignInfo(String waybillNo);
	
	/**
	 * 
	 * 反签收保存
	 * @author ibm-lizhiguo
	 * @date 2012-11-19 下午7:11:35
	 */
	void saveReverseSignInfo(SignResultDto dto, CurrentInfo currentInfo);

}
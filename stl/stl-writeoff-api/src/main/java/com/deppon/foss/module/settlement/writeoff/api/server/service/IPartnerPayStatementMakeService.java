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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/settlement/writeoff/api/server/service/IStatementMakeService.java
 * 
 * FILE NAME        	: IStatementMakeService.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.writeoff.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.PartnerPayStatementDto;

/**
 * 制作合伙人付款对账单服务接口类
 * @author 黄乐为
 * @date 2016-1-29 下午2:04:09
 */
public interface IPartnerPayStatementMakeService extends IService {

	/**
	 * 合伙人付款对账单新增查询应付单
	 * @author 黄乐为
	 * @date 2016-1-29 下午5:36:16
	 * @param dto
	 * @param start
	 * @param limit
	 * @return
	 */
	public PartnerPayStatementDto queryPartnerPayStatementD(PartnerPayStatementDto dto,int start,int limit);
	
	/**
	 * 查询合伙人付款对账单明细包含的（应付）单据及合伙人付款对账单信息服务接口
	 * @author 黄乐为
	 * @date 2016-1-29 下午2:05:52
	 * @param dto  制作合伙人付款对账单参数Dto
	 * @param info 当前登录用户
	 * @return PartnerPayStatementMakeQueryResultDto
	 * 		        合伙人付款对账单及合伙人付款对账单明细Dto
	 */
	/*PartnerPayStatementDto queryForStatementMake(
			PartnerPayStatementDto dto,
			CurrentInfo info);*/

	/**
	 * 制作合伙人付款对账单时，保存合伙人付款对账单及合伙人付款对账单明细单据
	 * @author 黄乐为
	 * @date 2016-1-29 下午2:09:25
	 * @param dto 制作合伙人付款对账单参数Dto
	 * @param info 当前登录用户
	 * @return PartnerPayStatementMakeQueryResultDto 
	 * 			合伙人付款对账单及合伙人付款对账单明细Dto
	 */
	PartnerPayStatementDto addStatement(
			PartnerPayStatementDto dto,
			CurrentInfo info);
	
	/**
	 * 制作合伙人付款对账单时，保存合伙人付款对账单及合伙人付款对账单明细单据
	 * @author 黄乐为
	 * @date 2016-1-29 下午2:09:25
	 * @param dto 制作合伙人付款对账单参数Dto
	 * @param info 当前登录用户
	 * @return PartnerPayStatementMakeQueryResultDto 
	 * 			合伙人付款对账单及合伙人付款对账单明细Dto
	 */
	public PartnerPayStatementDto addStatement(PartnerPayStatementDto partnerPayStatementDto);

	/**
	 * 在保存合伙人付款对账单和新增合伙人付款对账单明细时,校验明细单据是否发生了变化
	 * @author 黄乐为
	 * @date 2016-1-29 下午2:11:39
	 * @param list 合伙人付款对账单明细集合
	 * @param statementNo 合伙人付款对账单号
	 * @param info 当前登录用户
	 * @return
	 */
	/*List<PartnerPayStatementDEntity> validateForAddDetail(
			List<PartnerPayStatementDEntity> list, String statementNo,
			CurrentInfo info);*/

	/**
	 * 新增合伙人付款对账单明细时，修改进入合伙人付款对账单的应付单明细的对账单号
	 * @author 黄乐为
	 * @date 2016-1-29 下午2:18:51
	 * @param payableEntityList 应付单集合
	 * @param statementNo 对账单号
	 * @param info 当前登录用户
	 */
	/*void updateDetailBillNoForAddDetail(
			List<BillPayableEntity> payableEntityList,
			String statementNo,CurrentInfo info);*/
}

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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/settlement/writeoff/api/server/dao/IStatementMakeDao.java
 * 
 * FILE NAME        	: IStatementMakeDao.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.consumer.api.server.dao;

import java.util.List;
import java.util.Map;

import com.deppon.foss.module.settlement.consumer.api.shared.dto.InvoiceManagementAddDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.InvoiceManagementAddListDto;


/**
 * 制作对账单DAO接口类
 * 
 * @author 218371-foss-zhaoyanjun
 * @date 2016-3-3 下午14:17:02
 */

public interface IStatementCreatDao {
	
	//根据客户信息查询应收单
	List<InvoiceManagementAddListDto> queryYSMakeSOAByCustomer(
			Map<String, Object> queryCondition);
	
	//查找最大的合伙人对账单号
	int queryMaxHhStatementNo();
	
	//生成对账单
	int makeHhStatement(InvoiceManagementAddDto resultDto);

	//按客户查询对账单
	List<InvoiceManagementAddDto> queryhhStatementForCustomer(Map<String, Object> map);
	
	//按单号查询对账单
	List<InvoiceManagementAddDto> queryhhStatementForWaybillNo(
			List<String> billDetailNos, List<String> allowDepts);
	
	//按对账单单号查询对账单
	List<InvoiceManagementAddDto> queryhhStatementForStatementNo(
			List<String> statementNos, List<String> allowDepts);
	
	//删除对账单号
	void deleteStatement(String statementNo);
	
	//删除应收单的合伙人对账单号
	void deleteStatementNoForReceivable(String statementNo);
	
	//修改对账单发票审核状态
	int updateInvoiceState(Map<String, Object> map);
	
	//修改应收单中相应的对账单号为新的单号
	int updateForDealWithIs(Map map);
	
	//按单号查询应收单
	List<InvoiceManagementAddListDto> queryYSMakeSOAByWaybillNumber(List<String> waybills, List<String> subsidiaryDept);
	
	//按应收单单号查询应收单
	List<InvoiceManagementAddListDto> queryYSMakeSOAByReceivableNumber(List<String> waybills, List<String> subsidiaryDept);
	
	//按对账单号查询应收单
	List<InvoiceManagementAddListDto> queryReceivablesByStatementBillNo(
			String statementBillNo);
	
	//按ID查询
	List<String> queryYSByIds(List<String> ids);
	//安数量 类型查询
	List<InvoiceManagementAddListDto> queryYSMakeSOAByReceivableNumber(List<String> waybills, List<String> subsidiaryDept,List<String> Manycheck);
    //按单号查询对账单
	List<InvoiceManagementAddListDto> queryYSMakeSOAByWaybillsNumber(
			List<String> waybills, List<String> subsidiaryDept,
			List<String> Manycheck);
}

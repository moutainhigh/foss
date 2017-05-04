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
 * PROJECT NAME	: stl-writeoff
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/settlement/writeoff/server/dao/impl/StatementMakeDao.java
 * 
 * FILE NAME        	: StatementMakeDao.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.consumer.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.consumer.api.server.dao.IStatementCreatDao;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.InvoiceManagementAddDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.InvoiceManagementAddListDto;

/**
 * 制作对账单DAO实现类
 * 
 * @author 088933-foss-zhangjiheng
 * @date 2012-10-24 下午8:27:29
 */
public class StatementCreatDao extends iBatis3DaoImpl implements IStatementCreatDao {
	//命名空间
	private static final String NAMESPACE = "foss.stl.StatementCreatDao.";
	
	//按单号查询应收单
	@Override
	public List<InvoiceManagementAddListDto> queryYSMakeSOAByWaybillNumber(
			List<String> waybills, List<String> subsidiaryDept) {
		// TODO Auto-generated method stub
		//执行查询操作
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("waybills",waybills);
		map.put("subsidiaryDept", subsidiaryDept);
		return getSqlSession().selectList(NAMESPACE + "queryReceivableByWaybillNumber",map);
	}
	
	//防止重复开单，按id查询
	@Override
	public List<String> queryYSByIds(
			List<String> list) {
		// TODO Auto-generated method stub
		return getSqlSession().selectList(NAMESPACE + "queryReceivableById",list);
	}
	
	// 按应收单单号查询应收单
	@Override
	public List<InvoiceManagementAddListDto> queryYSMakeSOAByReceivableNumber(
			List<String> waybills, List<String> subsidiaryDept) {
		// TODO Auto-generated method stub
		// 执行查询操作
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("waybills",waybills);
		map.put("subsidiaryDept", subsidiaryDept);
		return getSqlSession().selectList(NAMESPACE + "queryReceivableByReceivableNumber",map);
	}
	
	//根据客户信息查询应收单
	@Override
	public List<InvoiceManagementAddListDto> queryYSMakeSOAByCustomer(
			Map<String, Object> queryCondition) {
		// TODO Auto-generated method stub
		return getSqlSession().selectList(NAMESPACE + "queryReceivableByCustomer",queryCondition);
	}
	
	//查找最大合伙人对账单号
	@Override
	public int queryMaxHhStatementNo() {
		// TODO Auto-generated method stub
		Object queryMaxHhStatementNo=getSqlSession().selectOne(NAMESPACE + "queryMaxStatementNo");
		if(queryMaxHhStatementNo!=null){
			return (Integer)queryMaxHhStatementNo;
		}
		return 0;
	}
	
	//生成对账单
	@Override
	public int makeHhStatement(InvoiceManagementAddDto resultDto) {
		// TODO Auto-generated method stub
		return (Integer)getSqlSession().insert(NAMESPACE + "statementDSave",resultDto);
	}
	
	//修改应收单中相应的对账单号为新的单号
	@Override
	public int updateForDealWithIs(Map map) {
		// TODO Auto-generated method stub
		return (Integer)getSqlSession().update(NAMESPACE + "updateForBrDw",map);
	}
	
	//按客户查询对账单信息
	@Override
	public List<InvoiceManagementAddDto> queryhhStatementForCustomer(
			Map<String, Object> map) {
		// TODO Auto-generated method stub
		return getSqlSession().selectList(NAMESPACE + "queryHsByCustomer",map);
	}
	
	//按运单号查询对账单
	@Override
	public List<InvoiceManagementAddDto> queryhhStatementForWaybillNo(
			List<String> billDetailNos,List<String> allowDepts) {
		// TODO Auto-generated method stub
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("list", billDetailNos);
		map.put("allowDepts", allowDepts);
		return getSqlSession().selectList(NAMESPACE + "queryHsByWaybillNumber",map);
	}
	
	//按对账单号查询对账单
	@Override
	public List<InvoiceManagementAddDto> queryhhStatementForStatementNo(
			List<String> statementNos,List<String> allowDepts) {
		// TODO Auto-generated method stub
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("list", statementNos);
		map.put("allowDepts", allowDepts);
		return getSqlSession().selectList(NAMESPACE + "queryHsByStatementNo",map);
	}
	
	//删除对账单号
	public void deleteStatement(String statementNo){
		getSqlSession().update(NAMESPACE + "deleteForHsId",statementNo);
	}
		
	//删除应收单的合伙人对账单号
	public void deleteStatementNoForReceivable(String statementNo){
		getSqlSession().update(NAMESPACE + "deleteForBrDw",statementNo);
	}
	
	//修改合伙人对账单发票审核状态
	@Override
	public int updateInvoiceState(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return getSqlSession().update(NAMESPACE + "updateForInvoiceStatus",map);
	}

	@Override
	public List<InvoiceManagementAddListDto> queryReceivablesByStatementBillNo(
			String statementBillNo) {
		// TODO Auto-generated method stub
		return getSqlSession().selectList(NAMESPACE + "queryReceivablesByStatementBillNo",statementBillNo);
	}


	@Override
	public List<InvoiceManagementAddListDto> queryYSMakeSOAByReceivableNumber(
			List<String> waybills, List<String> subsidiaryDept,
			List<String> Manycheck) {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("waybills",waybills);
		map.put("subsidiaryDept", subsidiaryDept);
		map.put("Manycheck", Manycheck);
		return getSqlSession().selectList(NAMESPACE + "queryReceivableByReceivableNumber",map);
	}
	@Override
	public List<InvoiceManagementAddListDto> queryYSMakeSOAByWaybillsNumber(
			List<String> waybills, List<String> subsidiaryDept,List<String> Manycheck) {
		// TODO Auto-generated method stub
		//执行查询操作
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("waybills",waybills);
		map.put("subsidiaryDept", subsidiaryDept);
		map.put("Manycheck", Manycheck);
		return getSqlSession().selectList(NAMESPACE + "queryReceivableByWaybillNumbers",map);
	}
}

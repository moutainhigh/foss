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
 * PROJECT NAME	: stl-consumer
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/consumer/test/service/CashIncomeStatementsServiceTest.java
 * 
 * FILE NAME        	: CashIncomeStatementsServiceTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.consumer.test.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.apache.commons.collections.CollectionUtils;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICashIncomeStatementsService;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CashIncomeStatementsQueryDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CashIncomeStatementsResultDto;
import com.deppon.foss.module.settlement.consumer.test.BaseTestCase;
import com.deppon.foss.util.DateUtils;
import com.opensymphony.xwork2.interceptor.annotations.Before;

/**
 * 现金收入报表明细Service测试类
 * 
 * @author 099995-foss-wujiangtao
 * @date 2012-11-8 下午3:26:24
 * @since
 * @version
 */
public class CashIncomeStatementsServiceTest extends BaseTestCase {

	@Autowired
	ICashIncomeStatementsService cashIncomeStatementsService;

	@Before
	public void setUp() {

	}

	@After
	public void tearDown() {

	}

	/**
	 * 根据不同的查询条件查询所有的（实收单）现金收入明细记录
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-9 上午8:46:13
	 * @see
	 */
	@Test
	public void testQueryByCondition() {
		CashIncomeStatementsQueryDto dto = new CashIncomeStatementsQueryDto();
		Date startDate = DateUtils.convert("2012-10-00 18:00:00",
				DateUtils.DATE_TIME_FORMAT);
		Date endDate = DateUtils.convert("2013-01-25 00:00:00",
				DateUtils.DATE_TIME_FORMAT);
		
		//处理结束日期，在原结束日期上加1
		dto.setEndDate(DateUtils.convert(DateUtils.addDay(endDate, 1), DateUtils.DATE_FORMAT));
		dto.setAccountStartDate(startDate);
		dto.setAccountEndDate(dto.getEndDate());
		//dto.setCollectionOrgCode("ORG");
		
		List<String> collectionOrgCodes=new ArrayList<String>();
		collectionOrgCodes.add("W02010211");
		dto.setCollectionOrgCodes(collectionOrgCodes);
		dto.setEmpCode("000000");
		try {
			dto.setPaging(true);//需要分页
			CashIncomeStatementsResultDto result = cashIncomeStatementsService
					.queryByCondition(dto, 0, 20);
			if(result!=null&&CollectionUtils.isNotEmpty(result.getList())){
				Assert.assertTrue(result.getTotalAmount()!=null&&result.getTotalAmount().compareTo(BigDecimal.ZERO)>0);//总金额不为空
			}
		} catch (SettlementException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * 统计各种汇总金额
	 * 10375169.78   10375169.78
	 */
	@Test
	public void testQueryTotalAmountByCondition(){
		CashIncomeStatementsQueryDto dto = new CashIncomeStatementsQueryDto();
		Date startDate = DateUtils.convert("2012-10-00 18:00:00",
				DateUtils.DATE_TIME_FORMAT);
		Date endDate = DateUtils.convert("2012-11-10 00:00:00",
				DateUtils.DATE_TIME_FORMAT);
		
		//处理结束日期，在原结束日期上加1
		dto.setEndDate(DateUtils.convert(DateUtils.addDay(endDate, 1), DateUtils.DATE_FORMAT));
		dto.setAccountStartDate(startDate);
		dto.setAccountEndDate(dto.getEndDate());
		dto.setEmpCode("000000");
		try {
			CashIncomeStatementsResultDto result = cashIncomeStatementsService
					.queryTotalAmountByCondition(dto);
			
			if(result!=null&&result.getTotalAmount()!=null){
				logger.info(result.getTotalAmount()+" zong ");
				Assert.assertTrue(result.getTotalAmount().compareTo(BigDecimal.ZERO)>0);
			}
		} catch (SettlementException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 当选择单据类型为现金收款单时,查询符合条件的现金收款单记录
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-9 下午6:20:19
	 */
	@Test
	public void testQueryBillCashByCondition(){
		CashIncomeStatementsQueryDto dto = new CashIncomeStatementsQueryDto();
		Date startDate = DateUtils.convert("2012-11-09 18:00:00",
				DateUtils.DATE_TIME_FORMAT);
		Date endDate = DateUtils.convert("2012-11-09 00:00:00",
				DateUtils.DATE_TIME_FORMAT);
		
		//处理结束日期，在原结束日期上加1
		dto.setEndDate(DateUtils.convert(DateUtils.addDay(endDate, 1), DateUtils.DATE_FORMAT));

		dto.setAccountStartDate(startDate);
		dto.setAccountEndDate(dto.getEndDate());
		dto.setEmpCode("000000");
		try {
			dto.setPaging(true);//需要分页
			
			CashIncomeStatementsResultDto result = cashIncomeStatementsService
					.queryBillCashByCondition(dto, 0, 100);
			if(result!=null&&CollectionUtils.isNotEmpty(result.getList())){
				//总金额不为空和总金额大于0
				Assert.assertTrue(result.getTotalAmount()!=null&&result.getTotalAmount().compareTo(BigDecimal.ZERO)>0);
				//logger.info(result.getTotalAmount()+" counts: "+result.getTotalCount());
			}
			
		} catch (SettlementException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 当选择单据类型为现金收款单时,统计符合条件的现金收款单的总金额
	 *  Row: 144000, CH  144000
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-13 下午6:48:50
	 */
	@Test
	public void testqueryBillCashTotalAmountByCondition(){
		CashIncomeStatementsQueryDto dto = new CashIncomeStatementsQueryDto();
		Date startDate = DateUtils.convert("2012-11-09 18:00:00",
				DateUtils.DATE_TIME_FORMAT);
		Date endDate = DateUtils.convert("2012-11-09 00:00:00",
				DateUtils.DATE_TIME_FORMAT);
		
		//处理结束日期，在原结束日期上加1
		dto.setEndDate(DateUtils.convert(DateUtils.addDay(endDate, 1), DateUtils.DATE_FORMAT));

		dto.setAccountStartDate(startDate);
		dto.setAccountEndDate(dto.getEndDate());
		dto.setEmpCode("000000");
		try {
			dto.setPaging(true);//需要分页
			
			CashIncomeStatementsResultDto result = cashIncomeStatementsService
					.queryBillCashTotalAmountByCondition(dto);
			if(result!=null&&result.getTotalAmount()!=null){
				logger.info(result.getTotalAmount()+" zong ");
				Assert.assertTrue(result.getTotalAmount().compareTo(BigDecimal.ZERO)>0);
			}
			
		} catch (SettlementException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 *根据运单号查询所有的现金收款单信息
	 *
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-9 下午6:58:00
	 */
	@Test
	public void testQueryByWaybillNos(){
		CashIncomeStatementsQueryDto dto = new CashIncomeStatementsQueryDto();
		List<String> waybillNos=new ArrayList<String>();
		waybillNos.add("1352455786545");
		waybillNos.add("1352455787622");
		waybillNos.add("1352455788017");
		waybillNos.add("1352455788406");
		waybillNos.add("1352455788669");
		waybillNos.add("1352455789163");
		waybillNos.add("1352455789453");
		waybillNos.add("1352455789808");
		dto.setWaybillNos(waybillNos);
		dto.setEmpCode("000000");
		try{
			CashIncomeStatementsResultDto result =this.cashIncomeStatementsService.queryByWaybillNos(dto);
			if(result!=null&&CollectionUtils.isNotEmpty(result.getList())){
				//总金额不为空和总金额大于0
				Assert.assertTrue(result.getTotalAmount()!=null&&result.getTotalAmount().compareTo(BigDecimal.ZERO)>0);
				logger.info(result.getTotalAmount()+" counts: "+result.getTotalCount());
			}
		}catch(SettlementException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据运单号集合，统计所有现金收款单的金额信息
	 * 8000    8000 zong 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-13 下午6:53:45
	 */
	@Test
	public void testQueryTotalAmountByWaybillNos(){
		CashIncomeStatementsQueryDto dto = new CashIncomeStatementsQueryDto();
		List<String> waybillNos=new ArrayList<String>();
		waybillNos.add("1352455786545");
		waybillNos.add("1352455787622");
		waybillNos.add("1352455788017");
		waybillNos.add("1352455788406");
		waybillNos.add("1352455788669");
		waybillNos.add("1352455789163");
		waybillNos.add("1352455789453");
		waybillNos.add("1352455789808");
		dto.setWaybillNos(waybillNos);
		dto.setEmpCode("000000");
		try{
			CashIncomeStatementsResultDto result =this.cashIncomeStatementsService.queryTotalAmountByWaybillNos(dto);
			if(result!=null&&result.getTotalAmount()!=null){
				logger.info(result.getTotalAmount()+" zong ");
				Assert.assertTrue(result.getTotalAmount().compareTo(BigDecimal.ZERO)>0);
			}
		}catch(SettlementException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 选择的单据类型为还款单时，查询符合条件的还款单信息
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-9 下午7:12:48
	 */
	@Test
	public void testQueryBillRepaymentByCondition(){
		CashIncomeStatementsQueryDto dto = new CashIncomeStatementsQueryDto();
		Date startDate = DateUtils.convert("2012-11-09 18:00:00",
				DateUtils.DATE_TIME_FORMAT);
		Date endDate = DateUtils.convert("2012-11-09 00:00:00",
				DateUtils.DATE_TIME_FORMAT);
		
		//处理结束日期，在原结束日期上加1
		dto.setEndDate(DateUtils.convert(DateUtils.addDay(endDate, 1), DateUtils.DATE_FORMAT));

		dto.setAccountStartDate(startDate);
		dto.setAccountEndDate(dto.getEndDate());
		dto.setEmpCode("000000");
		try {
			CashIncomeStatementsResultDto result = cashIncomeStatementsService
					.queryBillRepaymentByCondition(dto, 0, 100);
			if(result!=null&&CollectionUtils.isNotEmpty(result.getList())){
				//总金额不为空和总金额大于0
				Assert.assertTrue(result.getTotalAmount()!=null&&result.getTotalAmount().compareTo(BigDecimal.ZERO)>0);
				logger.info(result.getTotalAmount()+" counts: "+result.getTotalCount());
			}
		} catch (SettlementException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 选择的单据类型为还款单时，查询符合条件的还款单总金额信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-13 下午6:56:40
	 * 
	 * 3000   3000 zong 
	 */
	@Test
	public void testQueryBillRepaymentTotalAmountByCondition(){
		CashIncomeStatementsQueryDto dto = new CashIncomeStatementsQueryDto();
		Date startDate = DateUtils.convert("2012-11-09 18:00:00",
				DateUtils.DATE_TIME_FORMAT);
		Date endDate = DateUtils.convert("2012-11-09 00:00:00",
				DateUtils.DATE_TIME_FORMAT);
		
		//处理结束日期，在原结束日期上加1
		dto.setEndDate(DateUtils.convert(DateUtils.addDay(endDate, 1), DateUtils.DATE_FORMAT));

		dto.setAccountStartDate(startDate);
		dto.setAccountEndDate(dto.getEndDate());
		dto.setEmpCode("000000");
		try {
			CashIncomeStatementsResultDto result = cashIncomeStatementsService
					.queryBillRepaymentTotalAmountByCondition(dto);
			if(result!=null&&result.getTotalAmount()!=null){
				logger.info(result.getTotalAmount()+" zong ");
				Assert.assertTrue(result.getTotalAmount().compareTo(BigDecimal.ZERO)>0);
			}
		} catch (SettlementException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据对账单号查询还款单信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-9 下午7:15:53
	 */
	@Test
	public void testQueryBillRepaymentByStatementBillNOs(){
		CashIncomeStatementsQueryDto dto = new CashIncomeStatementsQueryDto();
		List<String> statementBillNos=new ArrayList<String>();
		statementBillNos.add("DZ00000995");
		statementBillNos.add("DZ00000996");
		statementBillNos.add("DZ00000997");
		
		dto.setStatementBillNos(statementBillNos);
		dto.setEmpCode("000000");
		try{
			CashIncomeStatementsResultDto result =this.cashIncomeStatementsService.queryBillRepaymentByStatementBillNOs(dto);
			if(result!=null&&CollectionUtils.isNotEmpty(result.getList())){
				//总金额不为空和总金额大于0
				Assert.assertTrue(result.getTotalAmount()!=null&&result.getTotalAmount().compareTo(BigDecimal.ZERO)>0);
				logger.info(result.getTotalAmount()+" counts: "+result.getTotalCount());
			}
		}catch(SettlementException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据对账单号集合，统计还款单金额信息
	 *  
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-13 下午7:00:05
	 */
	@Test
	public  void testQueryBillRepaymentTotalAmountByStatementBillNOs(){
		CashIncomeStatementsQueryDto dto = new CashIncomeStatementsQueryDto();
		List<String> statementBillNos=new ArrayList<String>();
		statementBillNos.add("DZ00000995");
		statementBillNos.add("DZ00000996");
		statementBillNos.add("DZ00000997");
		
		dto.setStatementBillNos(statementBillNos);
		dto.setEmpCode("000000");
		try{
			CashIncomeStatementsResultDto result =this.cashIncomeStatementsService.queryBillRepaymentTotalAmountByStatementBillNOs(dto);
			if(result!=null&&result.getTotalAmount()!=null){
				logger.info(result.getTotalAmount()+" zong ");
				Assert.assertTrue(result.getTotalAmount().compareTo(BigDecimal.ZERO)>0);
			}
		}catch(SettlementException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据还款单号集合查询还款单信息
	 * 
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-9 下午7:27:00
	 */
	@Test
	public void testQueryBillRepaymentByNOs(){
		CashIncomeStatementsQueryDto dto = new CashIncomeStatementsQueryDto();
		List<String> repaymentNos=new ArrayList<String>();
		
		repaymentNos.add("HK200008091");
		repaymentNos.add("HK200008092");
//		repaymentNos.add("HK200007913");
//		repaymentNos.add("HK200007914");
		
		dto.setRepaymentNos(repaymentNos);
		dto.setEmpCode("000000");
		try{
			CashIncomeStatementsResultDto result =this.cashIncomeStatementsService.queryBillRepaymentByNOs(dto);
			if(result!=null&&CollectionUtils.isNotEmpty(result.getList())){
				//总金额不为空和总金额大于0
				Assert.assertTrue(result.getTotalAmount()!=null&&result.getTotalAmount().compareTo(BigDecimal.ZERO)>0);
				logger.info(result.getTotalAmount()+" counts: "+result.getTotalCount());
			}
		}catch(SettlementException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据还款单号集合，查询还款单的金额信息 
	 * 2000 counts: null  2000 zong 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-13 下午7:12:54
	 */
	@Test
	public void testQueryBillRepaymentTotalAmountByNOs(){
		CashIncomeStatementsQueryDto dto = new CashIncomeStatementsQueryDto();
		List<String> repaymentNos=new ArrayList<String>();
		
		repaymentNos.add("HK200008091");
		repaymentNos.add("HK200008092");
//		repaymentNos.add("HK200007913");
//		repaymentNos.add("HK200007914");
		
		dto.setRepaymentNos(repaymentNos);
		dto.setEmpCode("000000");
		try{
			CashIncomeStatementsResultDto result =this.cashIncomeStatementsService.queryBillRepaymentTotalAmountByNOs(dto);
			if(result!=null&&result.getTotalAmount()!=null){
				logger.info(result.getTotalAmount()+" zong ");
				Assert.assertTrue(result.getTotalAmount().compareTo(BigDecimal.ZERO)>0);
			}
		}catch(SettlementException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 选择的单据类型为预收单时，统计符合条件的预收单信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-9 下午7:30:53
	 */
	@Test
	public void testQueryBillDepositReceivedByCondition(){
		CashIncomeStatementsQueryDto dto = new CashIncomeStatementsQueryDto();
		Date startDate = DateUtils.convert("2012-10-01 18:00:00",
				DateUtils.DATE_TIME_FORMAT);
		Date endDate = DateUtils.convert("2012-11-09 00:00:00",
				DateUtils.DATE_TIME_FORMAT);
		
		//处理结束日期，在原结束日期上加1
		dto.setEndDate(DateUtils.convert(DateUtils.addDay(endDate, 1), DateUtils.DATE_FORMAT));

		dto.setAccountStartDate(startDate);
		dto.setAccountEndDate(dto.getEndDate());
		dto.setEmpCode("000000");
		try {
			CashIncomeStatementsResultDto result = cashIncomeStatementsService
					.queryBillDepositReceivedByCondition(dto, 0, 100);
			if(result!=null&&CollectionUtils.isNotEmpty(result.getList())){
				//总金额不为空和总金额大于0
				Assert.assertTrue(result.getTotalAmount()!=null&&result.getTotalAmount().compareTo(BigDecimal.ZERO)>0);
				logger.info(result.getTotalAmount()+" counts: "+result.getTotalCount());
			}
		} catch (SettlementException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 选择的单据类型为预收单时，统计符合条件的预收单金额信息
	 *  170 counts: null 170 zong 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-13 下午7:15:11
	 */
	@Test
	public void testQueryBillDepositReceivedTotalAmountByCondition(){
		CashIncomeStatementsQueryDto dto = new CashIncomeStatementsQueryDto();
		Date startDate = DateUtils.convert("2012-10-01 18:00:00",
				DateUtils.DATE_TIME_FORMAT);
		Date endDate = DateUtils.convert("2012-11-09 00:00:00",
				DateUtils.DATE_TIME_FORMAT);
		
		//处理结束日期，在原结束日期上加1
		dto.setEndDate(DateUtils.convert(DateUtils.addDay(endDate, 1), DateUtils.DATE_FORMAT));

		dto.setAccountStartDate(startDate);
		dto.setAccountEndDate(dto.getEndDate());
		dto.setEmpCode("000000");
		try {
			CashIncomeStatementsResultDto result = cashIncomeStatementsService
					.queryBillDepositReceivedTotalAmountByCondition(dto);
			if(result!=null&&result.getTotalAmount()!=null){
				logger.info(result.getTotalAmount()+" zong ");
				Assert.assertTrue(result.getTotalAmount().compareTo(BigDecimal.ZERO)>0);
			}
		} catch (SettlementException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据预收单号集合，查询预收单信息
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-9 下午7:47:51
	 */
	@Test
	public void testQueryBillDepositReceivedByNOs(){
		CashIncomeStatementsQueryDto dto = new CashIncomeStatementsQueryDto();
		List<String> depositReceivedNos=new ArrayList<String>();
		
		depositReceivedNos.add("US0000027");
		depositReceivedNos.add("10000001");
		depositReceivedNos.add("10000002");
		depositReceivedNos.add("10000003");
		depositReceivedNos.add("10000004");
		depositReceivedNos.add("10000005");
		depositReceivedNos.add("10000006");
		dto.setDepositReceivedNos(depositReceivedNos);
		dto.setEmpCode("000000");
		try{
			CashIncomeStatementsResultDto result =this.cashIncomeStatementsService.queryBillDepositReceivedByNOs(dto);
			if(result!=null&&CollectionUtils.isNotEmpty(result.getList())){
				//总金额不为空和总金额大于0
				Assert.assertTrue(result.getTotalAmount()!=null&&result.getTotalAmount().compareTo(BigDecimal.ZERO)>0);
				logger.info(result.getTotalAmount()+" counts: "+result.getTotalCount());
			}
		}catch(SettlementException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据预收单号集合，统计预收单金额信息
	 * 140 counts: null   140 zong
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-13 下午7:21:32
	 */
	@Test
	public void testQueryBillDepositReceivedTotalAmountByNOs(){
		CashIncomeStatementsQueryDto dto = new CashIncomeStatementsQueryDto();
		List<String> depositReceivedNos=new ArrayList<String>();
		
		depositReceivedNos.add("US0000027");
		depositReceivedNos.add("10000001");
		depositReceivedNos.add("10000002");
		depositReceivedNos.add("10000003");
		depositReceivedNos.add("10000004");
		depositReceivedNos.add("10000005");
		depositReceivedNos.add("10000006");
		dto.setDepositReceivedNos(depositReceivedNos);
		dto.setEmpCode("000000");
		try{
			CashIncomeStatementsResultDto result =this.cashIncomeStatementsService.queryBillDepositReceivedTotalAmountByNOs(dto);
			if(result!=null&&result.getTotalAmount()!=null){
				logger.info(result.getTotalAmount()+" zong ");
				Assert.assertTrue(result.getTotalAmount().compareTo(BigDecimal.ZERO)>0);
			}
		}catch(SettlementException e){
			e.printStackTrace();
		}
	}
	
}

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
 * PROJECT NAME	: stl-web
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/web/server/test/service/VehicleAgencyExternalServiceTest.java
 * 
 * FILE NAME        	: VehicleAgencyExternalServiceTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.web.server.test.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.settlement.agency.api.server.service.IVehicleAgencyExternalService;
import com.deppon.foss.module.settlement.agency.api.shared.dto.SettlementExternalBillDto;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPayableConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.web.test.BaseTestCase;
import com.deppon.foss.module.settlement.web.test.util.StlWebTestUtil;
import com.deppon.foss.util.define.FossConstants;

/**
 * 偏线外发单服务、录入/修改/审核/反审核/作废
 * @author 099995-foss-wujiangtao
 * @date 2012-11-21 下午8:26:23
 * @since
 * @version
 */
public class VehicleAgencyExternalServiceTest extends BaseTestCase{
	
	
	@Autowired
	private IVehicleAgencyExternalService vehicleAgencyExternalService;
	
	@Autowired
	private ISettlementCommonService settlementCommonService;
	
	@Autowired
	private IBillReceivableService billReceivableService;
	
	@Autowired
	private IBillPayableService billPayableService ;


	/**
	 * 保存外发单服务
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-27 下午5:29:30
	 * @param waybillNo
	 * @param externalNo
	 * @param agentCode
	 */
	private SettlementExternalBillDto addExternalBill(String waybillNo,String externalNo,String agentCode){
		//新增外发单
	
		init(waybillNo);		
		SettlementExternalBillDto dto=new SettlementExternalBillDto();
		dto.setWaybillNo(waybillNo);//运单号
		dto.setWaybillId(waybillNo);//运单号
		dto.setExternalBillNo(externalNo);//外发单号
		
		//付款方式
		dto.setPaidMethod(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH);//默认为现金
		dto.setWaifabumen("GS00002");//外发部门编码
		dto.setWaifabumenName("外发部门1");//外发部门名称
		
		dto.setExternalAgencyFee(new BigDecimal("1000"));//外发代理费
		dto.setDeliveryFee(new BigDecimal("1000"));//代理送货费
		dto.setCostAmount(new BigDecimal("2000"));//外发成本总额
		dto.setReceiveAgencyFee(new BigDecimal("1000"));//实收代理费
		dto.setPayAgencyFee(new BigDecimal("800"));//实付代理费
		dto.setAgentCompanyCode(agentCode);//外发代理编码
		dto.setAgentCompanyName("外发代理");//外发代理名称
		dto.setReceiveOrgCode("GS00002");
		dto.setLastLoadOrgCode("GS00002");
		dto.setDeliveryGoodsFee(new BigDecimal("100"));
		dto.setTotalFee(new BigDecimal("2000") );
		dto.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		Date date=new Date();
		dto.setBusinessDate(date);
		dto.setCreateTime(date);
		try{
			this.vehicleAgencyExternalService.addExternalBill(dto, StlWebTestUtil.getCurrentInfo());
			
			//查询是否存在偏线成本应付单
			List<BillPayableEntity> list=this.queryBillPayable(waybillNo, externalNo, agentCode);
			Assert.assertTrue(CollectionUtils.isNotEmpty(list));
		}catch(SettlementException e){
			logger.error(e.getErrorCode());
		}
		return dto;
	}
	
	/**
	 * 查询应付单数据
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-27 下午5:22:42
	 * @param waybillNo
	 * @param externalNo
	 * @param agenct
	 * @return
	 */
	private List<BillPayableEntity> queryBillPayable(String waybillNo,String externalNo,String agentCode){
		BillPayableConditionDto payableConDto = new BillPayableConditionDto(
				waybillNo);
		payableConDto.setSourceBillNo(externalNo);// 外发单号
		payableConDto.setPartailLineAgentCode(agentCode);
		payableConDto
				.setBillTypes(new String[] { SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTIAL_LINE // 偏线成本应付单
				});
		List<BillPayableEntity> payableList = this.billPayableService
				.queryBillPayableByCondition(payableConDto);
		return payableList;
	}
	
	/**
	 * 录入外发单服务
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-21 下午8:27:00
	 */
	@Test
	@Rollback(true)
	public void testAddExternalBill(){
		//运单号 有接送货给造的：waybillNo="849368904";//280454881,498836200
		//设置运单号
		String waybillNo="566565665";
		String externalNo=StlWebTestUtil.getExternalNO();//外发单号
		String agentCode="123456";
		addExternalBill(waybillNo,externalNo,agentCode);
	}
	
	
	
	
	/**
	 * 修改外发单服务
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-22 下午2:31:30
	 */
	@Test
	@Rollback(true)
	public void testModifyExternalBill(){
		//设置运单号
		String waybillNo=StlWebTestUtil.getWaybillNO();
		waybillNo="566565665";
		String externalNo=StlWebTestUtil.getExternalNO();//外发单号
		String agentCode="123456";
		addExternalBill(waybillNo,externalNo,agentCode);
		
		//审核外发单服务的外发单号为：WFD1354014212156 外发代理编码为： 123456 运单号为： YD1354014212156
		//modifyExternalBill("WFD1354014212156","YD1354014212156",agentCode);--外发单以及被审核了，不能进行修改操作
		
		modifyExternalBill(waybillNo,externalNo,agentCode);
	}
	
	/**
	 * 修改外发单
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-27 下午5:33:09
	 * @param waybillNo
	 * @param externalNo
	 * @param agentCode
	 */
	private void modifyExternalBill(String waybillNo,String externalNo,String agentCode){
		SettlementExternalBillDto dto=new SettlementExternalBillDto();
		dto.setWaybillId(waybillNo);//运单ID
		dto.setWaybillNo(waybillNo);//运单号
		dto.setExternalBillNo(externalNo);//外发单号
		
		//付款方式
		dto.setPaidMethod(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH);
		
		dto.setWaifabumen("GS00002");//外发部门编码
		dto.setWaifabumenName("外发部门1");//外发部门名称
	
		
		dto.setExternalAgencyFee(new BigDecimal("1000"));//外发代理费
		dto.setDeliveryFee(new BigDecimal("2000"));//代理送货费
		dto.setCostAmount(new BigDecimal("3000"));//外发成本总额 原来是2000后来改成3000
		dto.setReceiveAgencyFee(new BigDecimal("1000"));//实收代理费
		dto.setPayAgencyFee(new BigDecimal("801"));//实付代理费
		
		dto.setAgentCompanyCode(agentCode);//外发代理编码
		dto.setAgentCompanyName("外发代理");//外发代理名称
		
		dto.setReceiveOrgCode("GS00002");
		dto.setLastLoadOrgCode("GS00002");
		dto.setDeliveryGoodsFee(new BigDecimal("100"));
		dto.setTotalFee(new BigDecimal("3000") );
		
		dto.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		
		//dto.setTransferExternal(PartiallineConstants.IS_TRANSFER_EXTERNAL_YES);
		Date date=new Date();
		dto.setBusinessDate(date);
		dto.setCreateTime(date);
		try{
			this.vehicleAgencyExternalService.modifyExternalBill(dto, StlWebTestUtil.getCurrentInfo());
			List<BillPayableEntity> list=this.queryBillPayable(waybillNo, externalNo, agentCode);
			Assert.assertTrue(CollectionUtils.isNotEmpty(list));
			BillPayableEntity entity=list.get(0);
			if(entity!=null){
				Assert.assertTrue(entity.getAmount().compareTo(new BigDecimal("3000"))==0);
			}
		}catch(SettlementException e){
			logger.error(e.getErrorCode());
		}
	}
	
	
	/**
	 * 添加多条外发单记录
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-27 下午6:35:51
	 */
	private List<SettlementExternalBillDto> addManyExternalBills(){
		List<SettlementExternalBillDto> list=new ArrayList<SettlementExternalBillDto>();
		for(int i=0;i<1;i++){
			String waybillNo=StlWebTestUtil.getWaybillNO();
			String externalNo=StlWebTestUtil.getExternalNO();//外发单号
			String agentCode="123456";
			SettlementExternalBillDto dto=this.addExternalBill(waybillNo, externalNo, agentCode);
			list.add(dto);
		}
		return list;
	}
	
	/**
	 * 批量审核外发单
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-27 上午11:32:26
	 */
	@Test
	@Rollback(true)
	@Transactional
	public void testAuditExternalBill(){
		try{
			List<SettlementExternalBillDto> list=this.addManyExternalBills();
			this.vehicleAgencyExternalService.auditExternalBill(list,  StlWebTestUtil.getCurrentInfo());
		}catch(SettlementException e){
			logger.error(e.getErrorCode());
		}
	}
	
	
	
	/**
	 * 审核外发单传入的参数为空
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-27 下午7:17:08
	 */
	@Test
	public void testAuditExternalBillParamIsEmpty(){
		try{
			this.vehicleAgencyExternalService.auditExternalBill(null,  StlWebTestUtil.getCurrentInfo());
		}catch(SettlementException e){
			Assert.assertEquals("审核外发单传入参数为空", e.getErrorCode());
			logger.error(e.getErrorCode());
		}
	}
	
	
	/**
	 * 批量反审核外发单服务
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-27 下午1:35:32
	 */
	@Test
	@Rollback(true)
	public void testReverseAuditExternalBill(){
		try{
			List<SettlementExternalBillDto> list=this.addManyExternalBills();
			//审核外发单
			this.vehicleAgencyExternalService.auditExternalBill(list,  StlWebTestUtil.getCurrentInfo());
			
			//反审核外发单
			this.vehicleAgencyExternalService.reverseAuditExternalBill(list, StlWebTestUtil.getCurrentInfo());
		}catch(SettlementException e){
			logger.error(e.getErrorCode());
		}
	}
	
	/**
	 * 批量作废外发单服务
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-27 下午1:36:13
	 */
	@Test
	@Rollback(true)
	public void testDisableExternalBill(){
		try{
			List<SettlementExternalBillDto> list=this.addManyExternalBills();
			
			//审核外发单
			this.vehicleAgencyExternalService.auditExternalBill(list,  StlWebTestUtil.getCurrentInfo());
			
			//反审核外发单
			this.vehicleAgencyExternalService.reverseAuditExternalBill(list, StlWebTestUtil.getCurrentInfo());
			this.vehicleAgencyExternalService.disableExternalBill(list, StlWebTestUtil.getCurrentInfo());
		}catch(SettlementException e){
			logger.error(e.getErrorCode());
		}
	}
	
	/**
	 * 初始化数据
	 * 新增到付运费应收单
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-27 下午3:09:54
	 * @param waybillNo
	 */
	private void init(String waybillNo){
		//先新增一个到付运费应收单
		String destReceivableBillType = SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE;
		BillReceivableEntity destReceivable = StlWebTestUtil
				.getBillReceivableEntity(
						waybillNo,
						this.getReceivableNO(destReceivableBillType),
						destReceivableBillType,
						SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__FREIGHT_COLLECT);
		try {
			this.billReceivableService.addBillReceivable(destReceivable,
					StlWebTestUtil.getCurrentInfo());
		} catch (SettlementException e) {
			logger.error(e.getErrorCode());
		}		
	}
	
	/**
	 * 根据传入的单据类型获取应收单号
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-23 上午10:40:06
	 * @return
	 * @see
	 */
	public String getReceivableNO(String billType) {
		if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE
				.equals(billType)) {
			return settlementCommonService
					.getSettlementNoRule(SettlementNoRuleEnum.YS2);
		} else if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE
				.equals(billType)) {
			return settlementCommonService
					.getSettlementNoRule(SettlementNoRuleEnum.YS6);
		} else if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE
				.equals(billType)) {
			return settlementCommonService
					.getSettlementNoRule(SettlementNoRuleEnum.YS1);
		}
		return "";
	}
	
	
	/*******************************************************配合测试人员进行测试代码*****************************/
	
	/**
	 * 作废外发单---限制条件 对应的到付运费应收单已核销金额大于0
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-17 下午3:34:11
	 */
	@Test
	@Rollback(false)
	public void testDisableExternal(){
		String waybillNo="50002020";
		waybillNo="50002020";
		String sql=" UPDATE STL.T_STL_BILL_RECEIVABLE T  SET T.UNVERIFY_AMOUNT = T.UNVERIFY_AMOUNT - 10000,T.VERIFY_AMOUNT  =10000  WHERE T.WAYBILL_NO = '"+waybillNo+"' AND T.ACTIVE = 'Y' ";
		this.simpleJdbcTemplate.getJdbcOperations().execute(sql);
	}
	
	/**
	 * 作废外发单--  限制条件：外发单对应的付款单状态为：已付款
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-17 下午4:05:55
	 */
	@Test
	@Rollback(false)
	public void testDisableExternalTwo(){
		String externalNo="50002016";
		String code="e87c4bb4-3920-4b15-91bc-c6a568598123";
		String sql="  UPDATE STL.T_STL_BILL_PAYMENT T SET T.REMIT_STATUS = 'TD' WHERE T.SOURCE_BILL_NO = '"+externalNo+"' and t.CUSTOMER_CODE='"+code+"' AND T.SOURCE_BILL_TYPE = 'PL' AND T.ACTIVE = 'Y' ";
		this.simpleJdbcTemplate.getJdbcOperations().execute(sql);
	}
	
	
	
	/**
	 * 作废外发单--  限制条件：外发单对应的付款单状态为：汇款中
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-17 下午4:05:55
	 */
	@Test
	@Rollback(false)
	public void testDisableExternalThree(){
		String externalNo="50002016";
		String code="e87c4bb4-3920-4b15-91bc-c6a568598123";
		String sql="  UPDATE STL.T_STL_BILL_PAYMENT T SET T.REMIT_STATUS = 'TG' WHERE T.SOURCE_BILL_NO = '"+externalNo+"' and t.CUSTOMER_CODE='"+code+"' AND T.SOURCE_BILL_TYPE = 'PL' AND T.ACTIVE = 'Y' ";
		this.simpleJdbcTemplate.getJdbcOperations().execute(sql);
	}
	
	/**
	 * 修改外发单服务
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-17 下午4:17:48
	 */
	@Test
	@Rollback(true)
	public void testModifyExternalBillTwo(){
		//设置运单号
		String waybillNo=StlWebTestUtil.getWaybillNO();
		waybillNo="50002020";
		String externalNo="50002020-6";//外发单号
		String agentCode="e87c4bb4-3920-4b15-91bc-c6a568598123";
		modifyExternalBillTwo(waybillNo,externalNo,agentCode);
	}
	
	@Test
	@Rollback(true)
	public void testAddSame(){
		SettlementExternalBillDto dto=new SettlementExternalBillDto();
		dto.setWaybillId("50002016");//运单ID
		dto.setWaybillNo("50002016");//运单号
		dto.setExternalBillNo("50002016");//外发单号
		
		//付款方式
		dto.setPaidMethod(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH);
		
		dto.setWaifabumen("W3100020616");//外发部门编码
		dto.setWaifabumenName("1111");//外发部门名称
		dto.setExternalAgencyFee(new BigDecimal("100"));//外发代理费
		dto.setDeliveryFee(new BigDecimal("219"));//代理送货费
		dto.setCostAmount(new BigDecimal("319"));//外发成本总额
		dto.setPayAgencyFee(new BigDecimal("319") );
		
		
		dto.setAgentCompanyCode("e87c4bb4-3920-4b15-91bc-c6a568598123");//外发代理编码
		dto.setAgentCompanyName("ddd");//外发代理名称
		dto.setReceiveOrgCode("W3100020616");//偏线外发部门
		dto.setLastLoadOrgCode("W3100020616"); 
		dto.setDeliveryGoodsFee(new BigDecimal("100"));
		dto.setTotalFee(new BigDecimal("1000") );
		dto.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		Date date=new Date();
		dto.setBusinessDate(date);
		dto.setCreateTime(date);
		try{
			this.vehicleAgencyExternalService.addExternalBill(dto, StlWebTestUtil.getCurrentInfo());
		}catch(SettlementException e){
			logger.error(e.getErrorCode(),e);
		}
	}
	
	/**
	 * 修改外发单
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-27 下午5:33:09
	 * @param waybillNo
	 * @param externalNo
	 * @param agentCode
	 */
	private void modifyExternalBillTwo(String waybillNo,String externalNo,String agentCode){
		SettlementExternalBillDto dto=new SettlementExternalBillDto();
		dto.setWaybillId(waybillNo);//运单ID
		dto.setWaybillNo(waybillNo);//运单号
		dto.setExternalBillNo(externalNo);//外发单号
		
		//付款方式
		dto.setPaidMethod(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH);
		
		dto.setWaifabumen("W3100020616");//外发部门编码
		dto.setWaifabumenName("1111");//外发部门名称
		dto.setExternalAgencyFee(new BigDecimal("900"));//外发代理费
		dto.setDeliveryFee(new BigDecimal("600"));//代理送货费
		dto.setCostAmount(new BigDecimal("1500"));//外发成本总额 
		
		dto.setAgentCompanyCode(agentCode);//外发代理编码
		dto.setAgentCompanyName("ddd");//外发代理名称
		dto.setReceiveOrgCode("W3100020616");//偏线外发部门
		dto.setLastLoadOrgCode("W3100020616"); 
		dto.setDeliveryGoodsFee(new BigDecimal("100"));
		dto.setTotalFee(new BigDecimal("2000") );
		dto.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		Date date=new Date();
		dto.setBusinessDate(date);
		dto.setCreateTime(date);
		try{
			this.vehicleAgencyExternalService.modifyExternalBill(dto, StlWebTestUtil.getCurrentInfo());
		}catch(SettlementException e){
			logger.error(e.getErrorCode(),e);
		}
	}
	
	
}

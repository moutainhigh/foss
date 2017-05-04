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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/settlement/ptpitf/test/service/impl/BillPayableNewPtpProcessTest.java
 * 
 * FILE NAME        	: BillPayableNewPtpProcessTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2016  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.ptpitf.test.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.deppon.esb.core.exception.ESBBusinessException;
import com.deppon.esb.inteface.domain.payable.add.FeeDetails;
import com.deppon.esb.inteface.domain.payable.add.PayableBills;
import com.deppon.esb.inteface.domain.payable.add.PtpAddBillPayableRequest;
import com.deppon.esb.inteface.domain.payable.add.PtpAddBillPayableResponse;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementESBDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.pay.api.server.service.IBillPayablePtpService;
import com.deppon.foss.module.settlement.ptpitf.test.BaseTestCase;

/**
 * 对接合伙人生成应付单接口实现
 * @author foss-Jiang Xun
 * @date 2016-01-25 上午11:31:13
 */
public class BillPayableNewPtpProcessTest extends BaseTestCase {
	
	private static final Logger LOGGER = LogManager
			.getLogger(BillPayableNewPtpProcessTest.class);
	
	/**
	 * 应付单通用服务service
	 */
	@Autowired
	private IBillPayablePtpService billPayablePtpService;
	/**
	 * 结算通用服务service
	 */
	@Autowired
	private ISettlementCommonService settlementCommonService;
	/**
	 * 应付单据子类型控制方法
	 * @author foss-Jiang Xun
	 * @throws ESBBusinessException 
	 * @date 2016-01-25 上午11:31:13
	 */
	@Test
	@Rollback(false)
	public void generatPayableBillController() throws ESBBusinessException {
		LOGGER.info("测试生成合伙人应付单开始.................");
		//获取返回信息
		FeeDetails feeDetail1 = new FeeDetails();
		FeeDetails feeDetail2 = new FeeDetails();
		List<FeeDetails> feeDetails = new ArrayList<FeeDetails>();
		PayableBills payableBill1 = new PayableBills();
		PayableBills payableBill2 = new PayableBills();
		List<PayableBills> payableBills = new ArrayList<PayableBills>();
		
		feeDetail1.setAmount(BigDecimal.valueOf(50));
		feeDetail1.setPayableType("ABCDEFG");//从字典中取
		feeDetail2.setAmount(BigDecimal.valueOf(30));
		feeDetail2.setPayableType("HIJK");//从字典中取
//		feeDetails.add(feeDetail1);
//		feeDetails.add(feeDetail2);
		
		payableBill1.setWaybillNo("yingfu001");//运单号
		payableBill1.setWaybillId("yingfu001");//运单id
		payableBill1.setSourceBillType("小小酥");//来源单据类型
		payableBill1.setSourceBillNo("TTT");//来源单据号
		
		payableBill1.setBillType("PDFP");//单据子类型
		payableBill1.setPayableOrgCode("DP02076");//应付部门编码
		payableBill1.setOrigOrgCode("DP02076");//出发部门编码
		payableBill1.setDestOrgCode("DP02076");//到达部门编码
		payableBill1.setCustomerCode("guest001");//客户编码/应付代理编码
		payableBill1.setCustomerName("guest001");//客户名称/应付代理名称
		payableBill1.setAmount(BigDecimal.valueOf(80));//订单总金额
		payableBill1.setCurrencyCode("RMB");//币种
		payableBill1.setBusinessDate(new Date());//业务日期
		payableBill1.setProductCode(SettlementDictionaryConstants.BILL_DEPOSIT_RECEIVED__TRANSPORT_TYPE__LINE_CUSTOMER);//运输性质
		payableBill1.setProductId("ABC");//产品ID
		payableBill1.setCreateUserCode("user007");//制单人编码
		payableBill1.setCreateUserName("user007");//制单人名称
		payableBill1.setCreateOrgCode("user007");//制单人部门编码
		payableBill1.setCreateOrgName("user007");//制单人部门名称
		payableBill1.setContractOrgCode("合同007");//合同部门编码
		payableBill1.setContractOrgName("合同007");//合同部门名称
		
		payableBill1.setPayStatus("1");//支付状态
		payableBill1.setCustomerContact("customer007");//客户联系人编码
		payableBill1.setCustomerName("customer007");//客户联系人名称
		payableBill1.setCustomerPhone("18932331234");//客户联系人电话
		payableBill1.setWorkflowNo("wf007");//工作流号
//		payableBill1.setLgdriverCode("customer007");//外请车司机编码
//		payableBill1.setLgdriverName("customer007");//外请车司机名称
		payableBill1.setPayerType("customer007");//付款方
		
		payableBill1.setPayableType("MNMN");//应付类型??
		payableBill1.setDeliverFee(BigDecimal.valueOf(20));//送货费
		payableBill1.setOutgoingFee(BigDecimal.valueOf(20));//外发运费
		payableBill1.setCodAgencyFee(BigDecimal.valueOf(20));//代付货款手续费
		payableBill1.setExternalInsuranceFee(BigDecimal.valueOf(20));//外发保价费
		payableBill1.setNotes("付给小明");//备注
		payableBill1.setPayeeName("蒋迅");//开户人姓名
		payableBill1.setBankHqCode("BANK001");//开户行编码
		payableBill1.setBankHqName("中国银行");//开户行名称
		payableBill1.setAccountNo("320826");//银行账号
		payableBill1.setProvinceCode("p007");//省份编码
		payableBill1.setProvinceName("江苏省");//省份名称
		payableBill1.setCityCode("c007");//城市编码
		payableBill1.setCityName("苏州市");//城市名称
		payableBill1.setBankBranchCode("320826");//支行编码
		payableBill1.setBankBranchName("中国银行徐泾支行");//支行名称
		payableBill1.setPaymentNotes("请确认");//付款备注
		payableBill1.setPaymentAmount(BigDecimal.valueOf(20));//付款金额
		payableBill1.setPaymentCategories("现付");//支付类别
		payableBill1.setAccountType("储蓄卡");//账户类型
		payableBill1.setExpressOrigOrgCode("789");//出发部门映射快递点部编码
		payableBill1.setExpressOrigOrgName("789");//出发部门映射快递点部名称
		payableBill1.setExpressDestOrgCode("789");//到达部门映射快递点部编码
		payableBill1.setExpressDestOrgName("789");//到达部门映射快递点部名称
		payableBill1.setInvoiceMark("1");//发票标记
		
		payableBill1.setContractOrgCode("0005");//合同部门编码
		payableBill1.setContractOrgName("0005");//合同部门名称
		payableBill1.setExpenseBearCode("DP02076");//应付单费用承担部门
		
		
		payableBill1.getFeeDetails().add(feeDetail1);
		payableBill1.getFeeDetails().add(feeDetail2);
		
		payableBills.add(payableBill1);
//		payableBills.add(payableBill2);
		
		
		PtpAddBillPayableRequest request = new PtpAddBillPayableRequest();
		request.getPayableBills().add(payableBill1);
//		request.getPayableBills().add(payableBill2);
		// 返回Foss生成应付单响应
		PtpAddBillPayableResponse response = new PtpAddBillPayableResponse();
		//验证
		if(request == null||CollectionUtils.isEmpty( request.getPayableBills())|| request.getPayableBills().size()<1){
			logger.error("生成合伙人应付单错误，请求参数为空，调用接口失败");
			response.setResult(SettlementESBDictionaryConstants.INTERFACE_RESPONSE_FAILED);//处理失败标志
			response.setReason("请求参数为空，调用接口失败");//失败原因
			throw new ESBBusinessException("请求参数为空，调用接口失败");
		}

		// 获取传递信息
		String waybillNo = request.getPayableBills().get(0).getWaybillNo();//运单号
		response.setWaybillNo(waybillNo);//响应运单号
		response.setResult(SettlementESBDictionaryConstants.INTERFACE_RESPONSE_SUCCESS);//处理成功标志
		try{
			//foss内部service处理
			billPayablePtpService.generatPtpPayableBill(billPayablePtpService.buildDto(request,null));
			//异常处理
		}catch (SettlementException se) {
			logger.error("\n运单号:"+waybillNo+"生成合伙人应付单结算错误，" + se.getErrorCode(), se);
			response.setResult(SettlementESBDictionaryConstants.INTERFACE_RESPONSE_FAILED);//处理失败标志
			response.setReason("运单号:"+waybillNo+"。生成合伙人应付单结算错误，"+se.getErrorCode());//失败原因
		}catch (BusinessException ex) {
			logger.error("\n运单号:"+waybillNo+"生成合伙人应付单业务错误，" + ex.getErrorCode(), ex);
			response.setResult(SettlementESBDictionaryConstants.INTERFACE_RESPONSE_FAILED);//处理失败标志
			response.setReason("运单号:"+waybillNo+"。生成合伙人应付单结算错误，"+ex.getErrorCode());//失败原因
		} catch (Exception e) {
			logger.error("\n运单号:"+waybillNo+"生成合伙人应付单发生未知错误，" + e.getMessage(), e);
			response.setResult(SettlementESBDictionaryConstants.INTERFACE_RESPONSE_FAILED);//处理失败标志
			response.setReason("运单号:"+waybillNo+"。生成合伙人应付单结算错误，"+e.getMessage());//失败原因
		}
		//记录日志
		logger.info("FOSS接口生成应付单结束....响应结果。成功标志是："+response.getResult()+"，失败原因是："+response.getReason());	

		LOGGER.info("测试生成合伙人应付单结束......................");
		Assert.assertEquals(response.getResult(),1 );
		Assert.assertNotNull("nnnnnnnn");
	}
	
}

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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/settlement/ptpitf/test/service/impl/BillReceivableNewPtpProcessTest.java
 * 
 * FILE NAME        	: BillReceivableNewPtpProcessTest.java
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
import com.deppon.esb.inteface.domain.receivable.add.FeeDetails;
import com.deppon.esb.inteface.domain.receivable.add.PtpAddBillReceivableRequest;
import com.deppon.esb.inteface.domain.receivable.add.PtpAddBillReceivableResponse;
import com.deppon.esb.inteface.domain.receivable.add.ReceivableBills;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementESBDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.service.IBillReceivablePtpService;
import com.deppon.foss.module.settlement.ptpitf.server.esb.BillReceivableNewPtpProcess;
import com.deppon.foss.module.settlement.ptpitf.test.BaseTestCase;

/**
 * 对接合伙人生成应付单接口实现
 * @author foss-Jiang Xun
 * @date 2016-01-07 上午11:27:13
 */
public class BillReceivableNewPtpProcessTest extends BaseTestCase {
	
	private static final Logger logger = LogManager
			.getLogger(BillReceivableNewPtpProcessTest.class);
	
	/**
	 * 应收单通用服务service
	 */
	@Autowired
	private IBillReceivablePtpService billReceivablePtpService;
	/**
	 * 结算通用服务service
	 */
	@Autowired
	private ISettlementCommonService settlementCommonService;
	/**
	 * 应收单据子类型控制方法
	 * @author foss-Jiang Xun
	 * @throws ESBBusinessException 
	 * @date 2016-01-07 上午11:23:13
	 */
	@Test
	@Rollback(false)
	public void generatReceivableBillController() throws ESBBusinessException {
		logger.info("测试生成合伙人应收单开始.................");
		//获取返回信息
		FeeDetails feeDetail1 = new FeeDetails();
		FeeDetails feeDetail2 = new FeeDetails();
		List<FeeDetails> feeDetails = new ArrayList<FeeDetails>();
		ReceivableBills receivableBill1 = new ReceivableBills();
		ReceivableBills receivableBill2 = new ReceivableBills();
		List<ReceivableBills> receivableBills = new ArrayList<ReceivableBills>();
		
		feeDetail1.setAmount(BigDecimal.valueOf(50));
		feeDetail1.setReceivableType("送货上楼");
		feeDetail2.setAmount(BigDecimal.valueOf(40));
		feeDetail2.setReceivableType("送货费");
		feeDetails.add(feeDetail1);
		feeDetails.add(feeDetail2);
		
		receivableBill1.setWaybillNo("99999996");//运单号
		receivableBill1.setWaybillId("kkk5");//运单id
		receivableBill1.setSourceBillType("taobao1");//来源单据类型
		receivableBill1.setSourceBillNo("jjj");//来源单据号
		
		receivableBill1.setBillType("PDFR");//单据子类型
		receivableBill1.setReceivableOrgCode("DP02076");//应收部门编码
		receivableBill1.setGeneratingOrgCode("DP02076");//收入部门编码
		receivableBill1.setDunningOrgCode("DP02076");//催款部门编码
		receivableBill1.setOrigOrgCode("DP02076");//出发部门编码
		receivableBill1.setDestOrgCode("DP02076");//到达部门编码
		receivableBill1.setCustomerCode("guest001");//客户编码/应收代理编码
		receivableBill1.setCustomerName("guest001");//客户名称/应收代理名称
		receivableBill1.setDeliveryCustomerCode("guest002");//发货客户编码
		receivableBill1.setDeliveryCustomerName("guest002");//发货客户名称
		receivableBill1.setReceiveCustomerCode("guest003");//收货客户编码
		receivableBill1.setCurrencyCode("RMB");//币种
		receivableBill1.setBusinessDate(new Date());//业务日期
		receivableBill1.setPaymentType(SettlementDictionaryConstants.OTHER_REVENUE__PAYMENT_TYPE__CASH);//付款方式
		receivableBill1.setProductCode(SettlementDictionaryConstants.BILL_DEPOSIT_RECEIVED__TRANSPORT_TYPE__LINE_CUSTOMER);//运输性质
		receivableBill1.setProductId("ABC");//产品ID
		receivableBill1.setCreateUserCode("user001");//制单人编码
		receivableBill1.setCreateUserName("user001");//制单人名称
		receivableBill1.setCreateOrgCode("user001");//制单人部门编码
		receivableBill1.setCreateOrgName("user001");//制单人部门名称
		receivableBill1.setTransportFee(BigDecimal.valueOf(23.1));//公布价运费
		receivableBill1.setPickupFee(BigDecimal.valueOf(23.1));//接货费
		receivableBill1.setDeliveryGoodsFee(BigDecimal.valueOf(23.1));//送货费
		receivableBill1.setCodFee(BigDecimal.valueOf(23.1));//代收货款手续费
		receivableBill1.setInsuranceFee(BigDecimal.valueOf(23.1));//保价费
		receivableBill1.setOtherFee(BigDecimal.valueOf(23.1));//其他费用
		receivableBill1.setValueAddFee(BigDecimal.valueOf(23.1));//增值费用
		receivableBill1.setPromotionsFee(BigDecimal.valueOf(23.1));//优惠费用
		receivableBill1.setGoodsName("一箱手套");//货物名称
		receivableBill1.setGoodsVolumeTotal(BigDecimal.valueOf(23.1));//货物总体积
		receivableBill1.setBillWeight(BigDecimal.valueOf(23.1));//计费重量
		receivableBill1.setReceiveMethod("自己提货");//提货方式
		receivableBill1.setCustomerPickupOrgCode("DP02076");//提货网点
		receivableBill1.setGoodsQtyTotal(BigDecimal.valueOf(20));//货物总件数
		receivableBill1.setTargetOrgCode("DP02076");//目的站
		receivableBill1.setCollectionType("收款类别X");//收款类别
		receivableBill1.setCollectionName("收款类别Y");//收款名称
		receivableBill1.setUnitPrice(BigDecimal.valueOf(0.6));//运费计费费率
		receivableBill1.setInsuranceAmount(BigDecimal.valueOf(1000));//保险声明价值
		receivableBill1.setDeliveryCustomerContact("隔壁老张");//发货联系人
		receivableBill1.setUnifiedSettlement("1");//是否统一结算**DB中char(1)类型
		receivableBill1.setContractOrgCode("合同001");//合同部门编码
		receivableBill1.setContractOrgName("合同001");//合同部门名称
		receivableBill1.setIsDiscount("1");//是否折扣**DB中char(1)类型
		receivableBill1.setWithholdStatus(SettlementDictionaryConstants.STL__WITHHOLD_STATUS__UNWITHHOLDED);//扣款状态
		receivableBill1.setAmount(BigDecimal.valueOf(90));//订单总金额
		
		receivableBill2.setWaybillNo("99999996");//运单号
		receivableBill2.setWaybillId("kkk6");//运单id
		receivableBill2.setSourceBillType("jingdong");//来源单据类型
		receivableBill2.setSourceBillNo("MMM");//来源单据号
		
		receivableBill2.setBillType("POR");//单据子类型
		receivableBill2.setReceivableOrgCode("DP02076");//应收部门编码
		receivableBill2.setGeneratingOrgCode("DP02076");//收入部门编码
		receivableBill2.setDunningOrgCode("DP02076");//催款部门编码
		receivableBill2.setOrigOrgCode("DP02076");//出发部门编码
		receivableBill2.setDestOrgCode("DP02076");//到达部门编码
		receivableBill2.setCustomerCode("guest008");//客户编码/应收代理编码
		receivableBill2.setCustomerName("guest008");//客户名称/应收代理名称
		receivableBill2.setDeliveryCustomerCode("guest009");//发货客户编码
		receivableBill2.setDeliveryCustomerName("guest009");//发货客户名称
		receivableBill2.setReceiveCustomerCode("guest009");//收货客户编码
		receivableBill2.setCurrencyCode("RMB");//币种
		receivableBill2.setBusinessDate(new Date());//业务日期
		receivableBill2.setPaymentType(SettlementDictionaryConstants.OTHER_REVENUE__PAYMENT_TYPE__CASH);//付款方式
		receivableBill2.setProductCode(SettlementDictionaryConstants.BILL_DEPOSIT_RECEIVED__TRANSPORT_TYPE__LINE_CUSTOMER);//运输性质
		receivableBill2.setProductId("ABC");//产品ID
		receivableBill2.setCreateUserCode("user0010");//制单人编码
		receivableBill2.setCreateUserName("user0010");//制单人名称
		receivableBill2.setCreateOrgCode("user0010");//制单人部门编码
		receivableBill2.setCreateOrgName("user0010");//制单人部门名称
		receivableBill2.setTransportFee(BigDecimal.valueOf(56));//公布价运费
		receivableBill2.setPickupFee(BigDecimal.valueOf(56));//接货费
		receivableBill2.setDeliveryGoodsFee(BigDecimal.valueOf(56));//送货费
		receivableBill2.setCodFee(BigDecimal.valueOf(56));//代收货款手续费
		receivableBill2.setInsuranceFee(BigDecimal.valueOf(56));//保价费
		receivableBill2.setOtherFee(BigDecimal.valueOf(56));//其他费用
		receivableBill2.setValueAddFee(BigDecimal.valueOf(56));//增值费用
		receivableBill2.setPromotionsFee(BigDecimal.valueOf(56));//优惠费用
		receivableBill2.setGoodsName("半根香蕉");//货物名称
		receivableBill2.setGoodsVolumeTotal(BigDecimal.valueOf(56));//货物总体积
		receivableBill2.setBillWeight(BigDecimal.valueOf(56));//计费重量
		receivableBill2.setReceiveMethod("自己提货");//提货方式
		receivableBill2.setCustomerPickupOrgCode("DP02076");//提货网点
		receivableBill2.setGoodsQtyTotal(BigDecimal.valueOf(90));//货物总件数
		receivableBill2.setTargetOrgCode("DP02076");//目的站
		receivableBill2.setCollectionType("收款类别X");//收款类别
		receivableBill2.setCollectionName("收款类别Y");//收款名称
		receivableBill2.setUnitPrice(BigDecimal.valueOf(0.8));//运费计费费率
		receivableBill2.setInsuranceAmount(BigDecimal.valueOf(3000));//保险声明价值
		receivableBill2.setDeliveryCustomerContact("蜡笔小新");//发货联系人
		receivableBill2.setUnifiedSettlement("0");//是否统一结算**DB中char(1)类型
		receivableBill2.setContractOrgCode("合同007");//合同部门编码
		receivableBill2.setContractOrgName("合同007");//合同部门名称
		receivableBill2.setIsDiscount("0");//是否折扣**DB中char(1)类型
		receivableBill2.setWithholdStatus(SettlementDictionaryConstants.STL__WITHHOLD_STATUS__UNWITHHOLDED);//扣款状态
		receivableBill2.setAmount(BigDecimal.valueOf(70));//订单总金额
		
		receivableBill1.getFeeDetails().add(feeDetail1);
		receivableBill1.getFeeDetails().add(feeDetail2);
		
		receivableBills.add(receivableBill1);
		receivableBills.add(receivableBill2);
		
		
		PtpAddBillReceivableRequest request = new PtpAddBillReceivableRequest();
		request.getReceivableBills().add(receivableBill1);
		request.getReceivableBills().add(receivableBill2);
		// 返回Foss生成应收单响应
		PtpAddBillReceivableResponse response = new PtpAddBillReceivableResponse();
		//验证
		if(request == null||CollectionUtils.isEmpty( request.getReceivableBills())|| request.getReceivableBills().size()<1){
			logger.error("生成合伙人应收单错误，请求参数为空，调用接口失败");
			response.setResult(SettlementESBDictionaryConstants.INTERFACE_RESPONSE_FAILED);//处理失败标志
			response.setReason("请求参数为空，调用接口失败");//失败原因
			throw new ESBBusinessException("请求参数为空，调用接口失败");
		}

		// 获取传递信息
		String waybillNo = request.getReceivableBills().get(0).getWaybillNo();//运单号
		response.setWaybillNo(waybillNo);//响应运单号
		response.setResult(SettlementESBDictionaryConstants.INTERFACE_RESPONSE_SUCCESS);//处理成功标志
		try{
			//foss内部service处理
			billReceivablePtpService.generatReceivableBillController(billReceivablePtpService.buildDto(request));
			//异常处理
		}catch (SettlementException se) {
			logger.error("\n运单号:"+waybillNo+"1生成合伙人应收单结算错误，" + se.getErrorCode(), se);
			response.setResult(SettlementESBDictionaryConstants.INTERFACE_RESPONSE_FAILED);//处理失败标志
			response.setReason("运单号:"+waybillNo+"。生成合伙人应收单结算错误，"+se.getErrorCode());//失败原因
		}catch (BusinessException ex) {
			logger.error("\n运单号:"+waybillNo+"2生成合伙人应收单业务错误，" + ex.getErrorCode(), ex);
			response.setResult(SettlementESBDictionaryConstants.INTERFACE_RESPONSE_FAILED);//处理失败标志
			response.setReason("运单号:"+waybillNo+"。生成合伙人应收单结算错误，"+ex.getErrorCode());//失败原因
		} catch (Exception e) {
			logger.error("\n运单号:"+waybillNo+"3生成合伙人应收单发生未知错误，" + e.getMessage(), e);
			response.setResult(SettlementESBDictionaryConstants.INTERFACE_RESPONSE_FAILED);//处理失败标志
			response.setReason("运单号:"+waybillNo+"。生成合伙人应收单结算错误，"+e.getMessage());//失败原因
		}
		//记录日志
		logger.info("FOSS接口生成应收单结束....响应结果。成功标志是："+response.getResult()+"，失败原因是："+response.getReason());
		//记录日志
		logger.info("FOSS接口生成应收单结束....");
		Assert.assertEquals(response.getResult(),1 );
		Assert.assertNotNull("nnnnnnnn");
	}
	
	@Test
	@Rollback(false)
	public void testGeneratReceivableBillController() {
		// 获取返回信息
		FeeDetails feeDetail1 = new FeeDetails();
		FeeDetails feeDetail2 = new FeeDetails();
		List<FeeDetails> feeDetails = new ArrayList<FeeDetails>();
		ReceivableBills receivableBill1 = new ReceivableBills();
		//ReceivableBills receivableBill2 = new ReceivableBills();
		List<ReceivableBills> receivableBills = new ArrayList<ReceivableBills>();

		feeDetail1.setAmount(BigDecimal.valueOf(50));
		feeDetail1.setReceivableType("送货上楼");
		feeDetail2.setAmount(BigDecimal.valueOf(40));
		feeDetail2.setReceivableType("送货费");
		feeDetails.add(feeDetail1);
		feeDetails.add(feeDetail2);

		receivableBill1.setWaybillNo("819201175");// 运单号
		receivableBill1.setWaybillId("kkk5");// 运单id
		receivableBill1.setSourceBillType("taobao1");// 来源单据类型
		receivableBill1.setSourceBillNo("jjj");// 来源单据号

		receivableBill1.setBillType("PDFR");// 单据子类型
		receivableBill1.setReceivableOrgCode("DP02076");// 应收部门编码
		receivableBill1.setGeneratingOrgCode("DP02076");// 收入部门编码
		receivableBill1.setDunningOrgCode("DP02076");// 催款部门编码
		receivableBill1.setOrigOrgCode("DP02076");// 出发部门编码
		receivableBill1.setDestOrgCode("DP02076");// 到达部门编码
		receivableBill1.setCustomerCode("guest001");// 客户编码/应收代理编码
		receivableBill1.setCustomerName("guest001");// 客户名称/应收代理名称
		receivableBill1.setDeliveryCustomerCode("guest002");// 发货客户编码
		receivableBill1.setDeliveryCustomerName("guest002");// 发货客户名称
		receivableBill1.setReceiveCustomerCode("guest003");// 收货客户编码
		receivableBill1.setCurrencyCode("RMB");// 币种
		receivableBill1.setBusinessDate(new Date());// 业务日期
		receivableBill1
				.setPaymentType(SettlementDictionaryConstants.OTHER_REVENUE__PAYMENT_TYPE__CASH);// 付款方式
		receivableBill1
				.setProductCode(SettlementDictionaryConstants.BILL_DEPOSIT_RECEIVED__TRANSPORT_TYPE__LINE_CUSTOMER);// 运输性质
		receivableBill1.setProductId("ABC");// 产品ID
		receivableBill1.setCreateUserCode("user001");// 制单人编码
		receivableBill1.setCreateUserName("user001");// 制单人名称
		receivableBill1.setCreateOrgCode("user001");// 制单人部门编码
		receivableBill1.setCreateOrgName("user001");// 制单人部门名称
		receivableBill1.setTransportFee(BigDecimal.valueOf(23.1));// 公布价运费
		receivableBill1.setPickupFee(BigDecimal.valueOf(23.1));// 接货费
		receivableBill1.setDeliveryGoodsFee(BigDecimal.valueOf(23.1));// 送货费
		receivableBill1.setCodFee(BigDecimal.valueOf(23.1));// 代收货款手续费
		receivableBill1.setInsuranceFee(BigDecimal.valueOf(23.1));// 保价费
		receivableBill1.setOtherFee(BigDecimal.valueOf(23.1));// 其他费用
		receivableBill1.setValueAddFee(BigDecimal.valueOf(23.1));// 增值费用
		receivableBill1.setPromotionsFee(BigDecimal.valueOf(23.1));// 优惠费用
		receivableBill1.setGoodsName("一箱手套");// 货物名称
		receivableBill1.setGoodsVolumeTotal(BigDecimal.valueOf(23.1));// 货物总体积
		receivableBill1.setBillWeight(BigDecimal.valueOf(23.1));// 计费重量
		receivableBill1.setReceiveMethod("自己提货");// 提货方式
		receivableBill1.setCustomerPickupOrgCode("DP02076");// 提货网点
		receivableBill1.setGoodsQtyTotal(BigDecimal.valueOf(20));// 货物总件数
		receivableBill1.setTargetOrgCode("DP02076");// 目的站
		receivableBill1.setCollectionType("收款类别X");// 收款类别
		receivableBill1.setCollectionName("收款类别Y");// 收款名称
		receivableBill1.setUnitPrice(BigDecimal.valueOf(0.6));// 运费计费费率
		receivableBill1.setInsuranceAmount(BigDecimal.valueOf(1000));// 保险声明价值
		receivableBill1.setDeliveryCustomerContact("隔壁老张");// 发货联系人
		receivableBill1.setUnifiedSettlement("1");// 是否统一结算**DB中char(1)类型
		receivableBill1.setContractOrgCode("合同001");// 合同部门编码
		receivableBill1.setContractOrgName("合同001");// 合同部门名称
		receivableBill1.setIsDiscount("1");// 是否折扣**DB中char(1)类型
		receivableBill1
				.setWithholdStatus(SettlementDictionaryConstants.STL__WITHHOLD_STATUS__UNWITHHOLDED);// 扣款状态
		receivableBill1.setAmount(BigDecimal.valueOf(90));// 订单总金额

		/*receivableBill2.setWaybillNo("99999996");// 运单号
		receivableBill2.setWaybillId("kkk6");// 运单id
		receivableBill2.setSourceBillType("jingdong");// 来源单据类型
		receivableBill2.setSourceBillNo("MMM");// 来源单据号

		receivableBill2.setBillType("POR");// 单据子类型
		receivableBill2.setReceivableOrgCode("DP02076");// 应收部门编码
		receivableBill2.setGeneratingOrgCode("DP02076");// 收入部门编码
		receivableBill2.setDunningOrgCode("DP02076");// 催款部门编码
		receivableBill2.setOrigOrgCode("DP02076");// 出发部门编码
		receivableBill2.setDestOrgCode("DP02076");// 到达部门编码
		receivableBill2.setCustomerCode("guest008");// 客户编码/应收代理编码
		receivableBill2.setCustomerName("guest008");// 客户名称/应收代理名称
		receivableBill2.setDeliveryCustomerCode("guest009");// 发货客户编码
		receivableBill2.setDeliveryCustomerName("guest009");// 发货客户名称
		receivableBill2.setReceiveCustomerCode("guest009");// 收货客户编码
		receivableBill2.setCurrencyCode("RMB");// 币种
		receivableBill2.setBusinessDate(new Date());// 业务日期
		receivableBill2
				.setPaymentType(SettlementDictionaryConstants.OTHER_REVENUE__PAYMENT_TYPE__CASH);// 付款方式
		receivableBill2
				.setProductCode(SettlementDictionaryConstants.BILL_DEPOSIT_RECEIVED__TRANSPORT_TYPE__LINE_CUSTOMER);// 运输性质
		receivableBill2.setProductId("ABC");// 产品ID
		receivableBill2.setCreateUserCode("user0010");// 制单人编码
		receivableBill2.setCreateUserName("user0010");// 制单人名称
		receivableBill2.setCreateOrgCode("user0010");// 制单人部门编码
		receivableBill2.setCreateOrgName("user0010");// 制单人部门名称
		receivableBill2.setTransportFee(BigDecimal.valueOf(56));// 公布价运费
		receivableBill2.setPickupFee(BigDecimal.valueOf(56));// 接货费
		receivableBill2.setDeliveryGoodsFee(BigDecimal.valueOf(56));// 送货费
		receivableBill2.setCodFee(BigDecimal.valueOf(56));// 代收货款手续费
		receivableBill2.setInsuranceFee(BigDecimal.valueOf(56));// 保价费
		receivableBill2.setOtherFee(BigDecimal.valueOf(56));// 其他费用
		receivableBill2.setValueAddFee(BigDecimal.valueOf(56));// 增值费用
		receivableBill2.setPromotionsFee(BigDecimal.valueOf(56));// 优惠费用
		receivableBill2.setGoodsName("半根香蕉");// 货物名称
		receivableBill2.setGoodsVolumeTotal(BigDecimal.valueOf(56));// 货物总体积
		receivableBill2.setBillWeight(BigDecimal.valueOf(56));// 计费重量
		receivableBill2.setReceiveMethod("自己提货");// 提货方式
		receivableBill2.setCustomerPickupOrgCode("DP02076");// 提货网点
		receivableBill2.setGoodsQtyTotal(BigDecimal.valueOf(90));// 货物总件数
		receivableBill2.setTargetOrgCode("DP02076");// 目的站
		receivableBill2.setCollectionType("收款类别X");// 收款类别
		receivableBill2.setCollectionName("收款类别Y");// 收款名称
		receivableBill2.setUnitPrice(BigDecimal.valueOf(0.8));// 运费计费费率
		receivableBill2.setInsuranceAmount(BigDecimal.valueOf(3000));// 保险声明价值
		receivableBill2.setDeliveryCustomerContact("蜡笔小新");// 发货联系人
		receivableBill2.setUnifiedSettlement("0");// 是否统一结算**DB中char(1)类型
		receivableBill2.setContractOrgCode("合同007");// 合同部门编码
		receivableBill2.setContractOrgName("合同007");// 合同部门名称
		receivableBill2.setIsDiscount("0");// 是否折扣**DB中char(1)类型
		receivableBill2
				.setWithholdStatus(SettlementDictionaryConstants.STL__WITHHOLD_STATUS__UNWITHHOLDED);// 扣款状态
		receivableBill2.setAmount(BigDecimal.valueOf(70));// 订单总金额
*/
		receivableBill1.getFeeDetails().add(feeDetail1);
		receivableBill1.getFeeDetails().add(feeDetail2);

		receivableBills.add(receivableBill1);
		//receivableBills.add(receivableBill2);

		PtpAddBillReceivableRequest request = new PtpAddBillReceivableRequest();
		request.getReceivableBills().add(receivableBill1);
		//request.getReceivableBills().add(receivableBill2);
		
		try {
			BillReceivableNewPtpProcess bean = applicationContext.getBean(BillReceivableNewPtpProcess.class);
			bean.process(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}

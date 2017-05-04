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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/web/test/util/StlWebTestUtil.java
 * 
 * FILE NAME        	: StlWebTestUtil.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.web.test.util;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillCashCollectionEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CODEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

public class StlWebTestUtil {

	/**
	 * 
	 * 获取运单号测试类
	 * 
	 * @author dp-wujiangtao
	 * @date 2012-10-20 下午2:37:06
	 * @return
	 * @see
	 */
	public static String getWaybillNO() {
		return new Date().getTime()+"" ;
	}
	
	/**
	 * 获取外发单号
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-21 下午8:30:15
	 * @return
	 */
	public static String getExternalNO(){
		return "WFD"+new Date().getTime() ;
	}
	

	/**
	 * 设置测试应收单数据
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-23 上午10:34:09
	 * @param waybillNo
	 *            运单号
	 * @param receivableNo
	 *            应收单号
	 * @param billType
	 *            应收单类型
	 * @param paymentType
	 *            付款类型
	 * @return
	 * @see
	 */
	public static BillReceivableEntity getBillReceivableEntity(
			String waybillNo, String receivableNo, String billType,
			String paymentType) {
		BillReceivableEntity entity = new BillReceivableEntity();
		String uuid = UUIDUtils.getUUID();
		entity.setId(uuid);// 主键
		entity.setWaybillId(waybillNo);// 运单Id
		entity.setWaybillNo(waybillNo);// 运单号
		entity.setReceivableNo(receivableNo);// 应收单号
		entity.setCreateType("1");// 系统自动生成
		entity.setSourceBillNo(waybillNo);
		entity.setSourceBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL);// 来源单据类型
		entity.setBillType(billType);// 单据子类型 -到付应收单
		entity.setPaymentType(paymentType);// 付款方式-到付
		if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE
				.equals(billType)
				|| SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE
						.equals(billType)
				|| SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_PARTIAL_LINE
						.equals(billType)
				|| SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY
						.equals(billType)
				||SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY_COD.equals(billType)		
				) {
			entity.setReceivableOrgCode("W011302020106");// 应收部门编码
			entity.setReceivableOrgName("到达北京营业部");// 应收部门名称
			entity.setCustomerCode("");// 客户编码
			entity.setCustomerName("");// 客户名称
		} else {
			entity.setReceivableOrgCode("W011302020106");// 应收部门编码
			entity.setReceivableOrgName("出发上海徐泾营业部");// 应收部门名称
			entity.setCustomerCode("0005");// 客户编码
			entity.setCustomerName("张三");// 客户名称
		}

		entity.setGeneratingOrgCode("SR1");// 收入部门编码
		entity.setGeneratingOrgName("收入上海营业部");// 收入部门名称
		entity.setGeneratingComCode("SRCOM1");// 收入子公司编码
		entity.setGeneratingComName("收入公司上海德邦物流");// 收入子公司名称
		entity.setDunningOrgCode("CK1");// 催款部门编码
		entity.setDunningOrgName("催款上海青浦营业部");// 催款部门名称
		entity.setOrigOrgCode("W011302020106");// 出发部门编码
		entity.setOrigOrgName("出发上海徐泾营业部");// 出发部门名称
		entity.setDestOrgCode("W011302020106");// 到达部门编码
		entity.setDestOrgName("到达北京营业部");// 到达部门名称
		

		// 设置金额 部分
		entity.setAmount(new BigDecimal(1000));// 总金额
		entity.setUnverifyAmount(entity.getAmount());// 未核销金额
		entity.setVerifyAmount(new BigDecimal(0));// 已核销金额
		entity.setTransportFee(new BigDecimal(400));// 公布价运费
		entity.setPickupFee(new BigDecimal(100));// 接货费
		entity.setDeliveryGoodsFee(new BigDecimal(100));// 送货费
		entity.setPackagingFee(new BigDecimal(100));// 包装手续费
		entity.setCodFee(new BigDecimal(100));// 代收货款手续费
		entity.setInsuranceFee(new BigDecimal(100));// 保价费
		entity.setOtherFee(new BigDecimal(100));// 其他费用

		entity.setValueAddFee(new BigDecimal(600));// 增值费用
		entity.setPromotionsFee(new BigDecimal(0));// 优惠费用
		entity.setCurrencyCode("1");// 货币 1 人民币

		// 提货方式
		entity.setReceiveMethod("1");
		entity.setBusinessDate(new Date());// 设置业务日期
		entity.setAccountDate(new Date());// 记账日期
		entity.setProductCode("1");// 产品类型 1精准汽运
		entity.setActive("Y");// 是否有效 默认为Y
		entity.setIsRedBack("N");// 是否为红单 默认为否
		entity.setIsInit("N");// 是否初始化 否
		entity.setVersionNo(FossConstants.INIT_VERSION_NO);// 版本号 1
		
		return entity;
	}
	
	
	public static BillReceivableEntity getBillReceivableEntityThree(
			String waybillNo, String receivableNo, String billType,
			String paymentType) {
		BillReceivableEntity entity = new BillReceivableEntity();
		String uuid = UUIDUtils.getUUID();
		entity.setId(uuid);// 主键
		entity.setWaybillId(waybillNo);// 运单Id
		entity.setWaybillNo(waybillNo);// 运单号
		entity.setReceivableNo(receivableNo);// 应收单号
		entity.setCreateType("1");// 系统自动生成
		entity.setSourceBillNo(waybillNo);
		entity.setSourceBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL);// 来源单据类型
		entity.setBillType(billType);// 单据子类型 -到付应收单
		entity.setPaymentType(paymentType);// 付款方式-到付
		if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE
				.equals(billType)
				|| SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE
						.equals(billType)
				|| SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_PARTIAL_LINE
						.equals(billType)
				|| SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY
						.equals(billType)) {
			entity.setReceivableOrgCode("GS00002");// 应收部门编码
			entity.setReceivableOrgName("上海花木营业部");// 应收部门名称
			entity.setCustomerCode("");// 客户编码
			entity.setCustomerName("");// 客户名称
		} else {
			entity.setReceivableOrgCode("GS00002");// 应收部门编码
			entity.setReceivableOrgName("上海花木营业部");// 应收部门名称
			entity.setCustomerCode("0006");// 客户编码
			entity.setCustomerName("黄金用户");// 客户名称
		}

		entity.setGeneratingOrgCode("GS00002");// 收入部门编码
		entity.setGeneratingOrgName("收入上海营业部");// 收入部门名称
		entity.setGeneratingComCode("GS00002");// 收入子公司编码
		entity.setGeneratingComName("收入公司上海德邦物流");// 收入子公司名称
		entity.setDunningOrgCode("GS00002");// 催款部门编码
		entity.setDunningOrgName("催款上海青浦营业部");// 催款部门名称
		entity.setOrigOrgCode("GS00002");// 出发部门编码
		entity.setOrigOrgName("出发上海徐泾营业部");// 出发部门名称
		entity.setDestOrgCode("GS00002");// 到达部门编码
		entity.setDestOrgName("到达北京营业部");// 到达部门名称
		

		// 设置金额 部分
		entity.setAmount(new BigDecimal(1000));// 总金额
		entity.setUnverifyAmount(entity.getAmount());// 未核销金额
		entity.setVerifyAmount(new BigDecimal(0));// 已核销金额
		entity.setTransportFee(new BigDecimal(400));// 公布价运费
		entity.setPickupFee(new BigDecimal(100));// 接货费
		entity.setDeliveryGoodsFee(new BigDecimal(100));// 送货费
		entity.setPackagingFee(new BigDecimal(100));// 包装手续费
		entity.setCodFee(new BigDecimal(100));// 代收货款手续费
		entity.setInsuranceFee(new BigDecimal(100));// 保价费
		entity.setOtherFee(new BigDecimal(100));// 其他费用

		entity.setValueAddFee(new BigDecimal(600));// 增值费用
		entity.setPromotionsFee(new BigDecimal(0));// 优惠费用
		entity.setCurrencyCode("1");// 货币 1 人民币

		// 提货方式
		entity.setReceiveMethod("1");
		entity.setBusinessDate(new Date());// 设置业务日期
		entity.setAccountDate(new Date());// 记账日期
		entity.setProductCode("1");// 产品类型 1精准汽运
		entity.setActive("Y");// 是否有效 默认为Y
		entity.setIsRedBack("N");// 是否为红单 默认为否
		entity.setIsInit("N");// 是否初始化 否
		entity.setVersionNo(FossConstants.INIT_VERSION_NO);// 版本号 1
		// entity.setApproveStatus(SettlementDictionaryConstants.BILL_RECEIVABLE__APPROVE_STATUS__NOT_AUDIT);
		return entity;
	}

	public static WaybillEntity buildRandomWaybillDto() {

		WaybillEntity dto = new WaybillEntity();
		CODEntity entity = buildRandomCODEntity();

		// ID、运单号、运单ID、应付部门
		dto.setId(UUIDUtils.getUUID());
		dto.setWaybillNo(entity.getWaybillNo());
		dto.setReceiveOrgCode(entity.getPayableOrgCode());

		// 代收货款类型、收款人与发货人关系、银行、支行、收款人
		dto.setRefundType(entity.getCodType());
		dto.setAccountBank(entity.getBankHQName());
		dto.setDeliveryCustomerCode("TEST_DELIVERY_CUSTOMER_CODE");
		dto.setDeliveryCustomerName(entity.getPayeeName());

		// 收货人账户、对公对私标志、行号、收款人电话、省份、城市
		dto.setAccountCode(entity.getAccountNo());
		dto.setDeliveryCustomerPhone(entity.getPayeePhone());

		// 业务日期、创建人、创建部门
		dto.setCreateTime(entity.getBusinessDate());
		dto.setCreateUserCode(entity.getCreateUserCode());
		dto.setCreateOrgCode(entity.getCreateOrgCode());

		dto.setCodAmount(entity.getCodAmount());

		dto.setTargetOrgCode("1");
		dto.setReceiveCustomerCode("zhangsan");
		dto.setReceiveCustomerName("张三");
		dto.setProductCode("RD");
		dto.setAccountName("李四");

		return dto;
	}

	/**
	 * 
	 * 构建代收货款实体数据
	 * 
	 * @author dp-zengbinwen
	 * @date 2012-10-25 下午2:58:03
	 */
	public static CODEntity buildRandomCODEntity() {

		String waybillNo = String.valueOf(RandomUtils.nextInt(1000000000));
		waybillNo = ("00000000" + waybillNo).substring(waybillNo.length());

		CODEntity entity = new CODEntity();
		entity.setId(UUIDUtils.getUUID());
		entity.setWaybillNo(waybillNo);
		entity.setPayableOrgCode("1");
		entity.setPayableOrgName("德邦物流");
		entity.setCodType("R1");
		entity.setPayeeRelationship("父子");
		entity.setBankHQName("中国工商银行");
		entity.setBankBranchName("中国工商银行武汉洪山区支行");
		//entity.setPayeeCode("W0125-1458");
		entity.setPayeeName("李四");
		entity.setAccountNo("62210119840212312");
		entity.setPublicPrivateFlag(SettlementDictionaryConstants.COD__PUBLIC_PRIVATE_FLAG__COMPANY);
		entity.setBankBranchCode("BK001254");
		entity.setPayeePhone("13523456723");
		entity.setProvinceName("湖北");
		entity.setCityName("武汉");
		entity.setBusinessDate(new Date());
		entity.setBatchNumber(null);
		entity.setRefundSuccessTime(null);
		entity.setCreateUserName("zbw");
		entity.setCreateUserCode("046644");
		entity.setCreateOrgName("德邦物流");
		entity.setCreateOrgCode("1");
		entity.setIsInit("N");
		entity.setActive("Y");
		entity.setStatus("NR");
		entity.setAirStatus(null);
		entity.setOrgFreezeTime(null);
		entity.setOrgFreezeUserCode(null);
		entity.setOrgFreezeUserName(null);
		entity.setAccountModifyTime(null);
		entity.setAccountModifyUserCode(null);
		entity.setAccountModifyUserName(null);
		entity.setOrgAuditTime(null);
		entity.setOrgAuditUserCode(null);
		entity.setOrgAuditUserName(null);
		entity.setOrgManagerAuditTime(null);
		entity.setOrgManagerAuditName(null);
		entity.setOrgManagerAuditCode(null);
		entity.setRefundNotes(null);
		entity.setTusyorgFreezeTime(null);
		entity.setTusyorgFreezeUserCode(null);
		entity.setTusyorgFreezeUserName(null);
		entity.setTusyorgAuditTime(null);
		entity.setTusyorgAuditUserCode(null);
		entity.setTusyorgAuditUserName(null);
		entity.setTusyorgRfdApptime(null);
		entity.setTusyorgRfdAppUserCode(null);
		entity.setTusyorgRfdAppUserName(null);
		entity.setCodExportTime(null);
		entity.setCodExportName(null);
		entity.setCodExportCode(null);
		entity.setRemittanceFailNotes(null);
		entity.setCreateTime(null);
		entity.setModifyTime(null);
		entity.setModifyUserName(null);
		entity.setModifyUserCode(null);
		entity.setCodAmount(new BigDecimal(2000000));

		return entity;
	}

	/**
	 * 
	 * 构建代收货款实体数据
	 * 
	 * @author dp-zengbinwen
	 * @date 2012-10-25 下午2:58:03
	 */
	public static CODEntity buildRandomCODEntity(String waybillNo) {
		CODEntity entity = new CODEntity();
		entity.setId(UUIDUtils.getUUID());
		entity.setWaybillNo(waybillNo);
		entity.setPayableOrgCode("1");
		entity.setPayableOrgName("德邦物流");
		entity.setCodType("R1");
		entity.setPayeeRelationship("父子");
		entity.setBankHQName("中国工商银行");
		entity.setBankBranchName("中国工商银行武汉洪山区支行");
		//entity.setPayeeCode("W0125-1458");
		entity.setPayeeName("李四");
		entity.setAccountNo("62210119840212312");
		entity.setPublicPrivateFlag(SettlementDictionaryConstants.COD__PUBLIC_PRIVATE_FLAG__COMPANY);
		entity.setBankBranchCode("BK001254");
		entity.setPayeePhone("13523456723");
		entity.setProvinceName("湖北");
		entity.setCityName("武汉");
		entity.setBusinessDate(new Date());
		entity.setBatchNumber(null);
		entity.setRefundSuccessTime(null);
		entity.setCreateUserName("zbw");
		entity.setCreateUserCode("046644");
		entity.setCreateOrgName("德邦物流");
		entity.setCreateOrgCode("1");
		entity.setIsInit("N");
		entity.setActive("Y");
		entity.setStatus("NR");
		entity.setAirStatus(null);
		entity.setOrgFreezeTime(null);
		entity.setOrgFreezeUserCode(null);
		entity.setOrgFreezeUserName(null);
		entity.setAccountModifyTime(null);
		entity.setAccountModifyUserCode(null);
		entity.setAccountModifyUserName(null);
		entity.setOrgAuditTime(null);
		entity.setOrgAuditUserCode(null);
		entity.setOrgAuditUserName(null);
		entity.setOrgManagerAuditTime(null);
		entity.setOrgManagerAuditName(null);
		entity.setOrgManagerAuditCode(null);
		entity.setRefundNotes(null);
		entity.setTusyorgFreezeTime(null);
		entity.setTusyorgFreezeUserCode(null);
		entity.setTusyorgFreezeUserName(null);
		entity.setTusyorgAuditTime(null);
		entity.setTusyorgAuditUserCode(null);
		entity.setTusyorgAuditUserName(null);
		entity.setTusyorgRfdApptime(null);
		entity.setTusyorgRfdAppUserCode(null);
		entity.setTusyorgRfdAppUserName(null);
		entity.setCodExportTime(null);
		entity.setCodExportName(null);
		entity.setCodExportCode(null);
		entity.setRemittanceFailNotes(null);
		entity.setCreateTime(null);
		entity.setModifyTime(null);
		entity.setModifyUserName(null);
		entity.setModifyUserCode(null);
		entity.setCodAmount(new BigDecimal(2000000));

		return entity;
	}

	/**
	 * 获取应付单数据
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-31 下午5:51:37
	 * @param wayBillNo
	 * @return
	 * @see
	 */
	public static BillPayableEntity getBillPayableEntity(String waybillNo,
			String payableNo, String billType, Date date) {
		BillPayableEntity entity = new BillPayableEntity();
		entity.setId(UUIDUtils.getUUID());
		entity.setPayableNo(payableNo);
		entity.setWaybillNo(waybillNo);
		entity.setWaybillId(waybillNo);

		entity.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__AUTO);
		entity.setPayerType(SettlementDictionaryConstants.BILL_PAYABLE__PAYER_TYPE__ORIGIN);
		entity.setPayableType(null);

		// 单据子类型，来源单据编码，来源单据类型,应付部门编码，应付部门名称
		entity.setBillType(billType);
		entity.setSourceBillNo(waybillNo);
		entity.setSourceBillType(SettlementDictionaryConstants.BILL_PAYABLE__SOURCE_BILL_TYPE__WAYBILL);

		// 整车尾款应付单-到达部门来付钱
		if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_LAST
				.equals(billType)) {
			entity.setPayableOrgCode("W011302020106");
		} else {
			entity.setPayableOrgCode("W011302020106");
		}

		entity.setPayableOrgName("上海营业部"); // TODO

		entity.setPayableComCode("GS1"); // TODO
		entity.setPayableComName("上海德邦物流");

		// 设置出发部门编码、名称
		entity.setOrigOrgCode("CF1");
		entity.setOrigOrgName("上海青浦营业部"); // TODO

		// 到达部门名称、到达部门编码
		entity.setDestOrgCode("DEST1");
		entity.setDestOrgName("北京大营门营业部"); // TODO

		// 设置应付客户编码、名称
		entity.setCustomerCode("YFKH");
		entity.setCustomerName("张三");
		entity.setCustomerContact(""); // TODO
		entity.setCustomerContactName(""); // TODO
		entity.setCustomerPhone("10000");

		// 设置金额、已核销金额、未核销金额
		entity.setAmount(NumberUtils.createBigDecimal("1000"));
		entity.setVerifyAmount(BigDecimal.ZERO);
		entity.setUnverifyAmount(entity.getAmount());

		// 设置币种、会计日期、业务日期
		entity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		entity.setAccountDate(new Date());
		entity.setBusinessDate(date);

		// 设置生效日期、创建人、创建人部门
		entity.setEffectiveDate(null);
		entity.setCreateUserCode("CTUS"); // TODO
		entity.setCreateUserName("CTUN");
		entity.setCreateOrgCode("CTORG");
		entity.setCreateOrgName("CTORGN"); // TODO

		// 是否有效、是否红单、是否初始化、版本号
		entity.setActive(FossConstants.YES);
		entity.setIsRedBack(SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__NO);
		entity.setIsInit(FossConstants.NO);
		entity.setPayStatus(SettlementDictionaryConstants.BILL_PAYABLE__PAY_STATUS__NO);
		entity.setFrozenStatus(SettlementDictionaryConstants.BILL_PAYABLE__FROZEN_STATUS__NOT_FROZEN);
		entity.setVersionNo(FossConstants.INIT_VERSION_NO);

		// 生效状态、创建时间、修改时间、对方部门
		entity.setEffectiveStatus(SettlementDictionaryConstants.BILL_PAYABLE__EFFECTIVE_STATUS__NO);
		entity.setCreateTime(date);
		entity.setModifyTime(date);
		entity.setApproveStatus(SettlementDictionaryConstants.BILL_PAYABLE__APPROVE_STATUS__NOT_AUDIT);

		return entity;
	}

	/**
	 * 设置现金收款单的数据
	 */
	public static BillCashCollectionEntity getBillCashCollectionEntity(
			String waybillNo, String cashCollectionNo, Date date) {
		BillCashCollectionEntity entity = new BillCashCollectionEntity();

		entity.setId(UUIDUtils.getUUID());

		entity.setCashCollectionNo(cashCollectionNo);
		entity.setWaybillId(waybillNo);
		entity.setSourceBillNo(waybillNo);

		entity.setActive(FossConstants.ACTIVE);
		entity.setIsRedBack(SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__NO);
		entity.setVersionNo(FossConstants.INIT_VERSION_NO);
		entity.setCreateTime(date);
		entity.setModifyTime(date);
		entity.setAccountDate(new Date());
		entity.setBusinessDate(date);
		entity.setStatus("STATUS");
		entity.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH);
		entity.setIsInit(FossConstants.NO);
		entity.setBillType(SettlementDictionaryConstants.BILL_CASH_COLLECTION__BILL_TYPE__CASH_COLLECTION);
		entity.setSourceBillType(SettlementDictionaryConstants.BILL_CASH_COLLECTION__SOURCE_BILL_TYPE__WAYBILL);
		entity.setProductCode("PRODUCT");
		entity.setBillType(SettlementDictionaryConstants.BILL_CASH_COLLECTION__SOURCE_BILL_TYPE__WAYBILL);
		entity.setCustomerCode("CUST");
		entity.setCustomerName("客户");
		entity.setCreateOrgCode("ORG");
		entity.setCreateOrgName("部门");
		entity.setGeneratingOrgCode("ORG");
		entity.setGeneratingOrgName("部门");
		entity.setCollectionOrgCode("ORG");
		entity.setCollectionOrgName("部门");

		// 设置金额 部分
		entity.setAmount(NumberUtils.createBigDecimal("1000"));

		entity.setTransportFee(new BigDecimal(400));// 公布价运费
		entity.setPickupFee(new BigDecimal(100));// 接货费
		entity.setDeliveryGoodsFee(new BigDecimal(100));// 送货费
		entity.setPackagingFee(new BigDecimal(100));// 包装手续费
		entity.setCodFee(new BigDecimal(100));// 代收货款手续费
		entity.setInsuranceFee(new BigDecimal(100));// 保价费
		entity.setOtherFee(new BigDecimal(100));// 其他费用

		entity.setValueAddFee(new BigDecimal(600));// 增值费用
		entity.setPromotionsFee(new BigDecimal(0));// 优惠费用
		entity.setCurrencyCode("1");// 货币 1 人民币

		entity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);

		return entity;
	}

	public static CurrentInfo getCurrentInfo() {
		UserEntity user = new UserEntity();
		EmployeeEntity employee = new EmployeeEntity();
		employee.setEmpCode("000123");
		employee.setEmpName("张三");
		user.setEmployee(employee);
		user.setUserName("zhangsan");

		OrgAdministrativeInfoEntity dept = new OrgAdministrativeInfoEntity();
		dept.setCode("1");
		dept.setName("德邦物流");

		CurrentInfo currentInfo = new CurrentInfo(user, dept);
		return currentInfo;
	}

}

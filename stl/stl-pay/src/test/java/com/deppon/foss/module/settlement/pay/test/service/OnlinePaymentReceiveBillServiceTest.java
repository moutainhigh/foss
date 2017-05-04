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
 * PROJECT NAME	: stl-pay
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/pay/test/service/OnlinePaymentReceiveBillServiceTest.java
 * 
 * FILE NAME        	: OnlinePaymentReceiveBillServiceTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.pay.test.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.pay.api.server.service.IOnlinePaymentReceiveBillService;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillReceivableOnlineQueryDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillSOADOnlineResultDto;
import com.deppon.foss.module.settlement.pay.test.BaseTestCase;
import com.deppon.foss.module.settlement.pay.test.PayTestUtil;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * 网上支付：查询、锁定还款应收单
 * 
 * @author 088933-foss-zhangjiheng
 * @date 2012-11-26 下午4:55:15
 */
public class OnlinePaymentReceiveBillServiceTest extends BaseTestCase {
	@Autowired
	private IOnlinePaymentReceiveBillService onlinePaymentReceiveBillService;
	@Autowired
	private IBillReceivableService billReceivableService;

	/**
	 * 
	 * 新增应收单
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-8 下午4:43:21
	 */
	private BillReceivableEntity getBillReceivableEntity() {
		CurrentInfo info = PayTestUtil.getCurrentInfo();
		BillReceivableEntity entity = new BillReceivableEntity();
		String uuid = UUIDUtils.getUUID();
		entity.setId(uuid);// 主键
		entity.setWaybillId("2012123");// 运单Id
		entity.setWaybillNo("2012123testt");// 运单号
		entity.setReceivableNo("YS10000001");// 应收单号
		entity.setCreateType("1");// 系统自动生成
		entity.setSourceBillNo("12345678");
		entity.setSourceBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL);// 来源单据类型
		entity.setBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE);// 单据子类型
																												// -到付应收单
		entity.setPaymentType("1");// 付款方式-到付
		entity.setReceivableOrgCode("YS1");// 应收部门编码
		entity.setReceivableOrgName("应收上海营业部");// 应收部门名称
		entity.setGeneratingOrgCode("SR1");// 收入部门编码
		entity.setGeneratingOrgName("收入上海营业部");// 收入部门名称
		entity.setGeneratingComCode("SRCOM1");// 收入子公司编码
		entity.setGeneratingComName("收入公司上海德邦物流");// 收入子公司名称
		entity.setDunningOrgCode("CK1");// 催款部门编码
		entity.setDunningOrgName("催款上海青浦营业部");// 催款部门名称
		entity.setOrigOrgCode("CFORG1");// 出发部门编码
		entity.setOrigOrgName("出发上海徐泾营业部");// 出发部门名称
		entity.setDestOrgCode("DDORG1");// 到达部门编码
		entity.setDestOrgName("到达北京营业部");// 到达部门名称
		entity.setCustomerCode("0001test");// 客户编码
		entity.setCustomerName("客户1");// 客户名称

		// 设置金额 部分
		entity.setAmount(new BigDecimal("600"));// 总金额
		entity.setUnverifyAmount(new BigDecimal("600"));// 未核销金额
		entity.setVerifyAmount(new BigDecimal(0));// 已核销金额
		entity.setTransportFee(new BigDecimal(100));// 公布价运费
		entity.setPickupFee(new BigDecimal(100));// 接货费
		entity.setDeliveryGoodsFee(new BigDecimal(100));// 送货费
		entity.setPackagingFee(new BigDecimal(100));// 包装手续费
		entity.setCodFee(new BigDecimal(100));// 代收货款手续费
		entity.setInsuranceFee(new BigDecimal(100));// 保价费
		entity.setOtherFee(new BigDecimal(100));// 其他费用

		entity.setValueAddFee(new BigDecimal(600));// 增值费用
		entity.setPromotionsFee(new BigDecimal(100));// 优惠费用
		entity.setCurrencyCode("1");// 货币 1 人民币

		entity.setGoodsName("衣服");
		entity.setReceiveCustomerName("张继恒");

		Date now = DateUtils.truncate(Calendar.getInstance().getTime(),
				Calendar.SECOND);
		// 提货方式
		entity.setReceiveMethod("1");
		entity.setBusinessDate(now);// 设置业务日期
		entity.setAccountDate(now);// 记账日期
		entity.setProductCode("1");// 产品类型 1精准汽运
		entity.setActive(FossConstants.ACTIVE);// 是否有效 默认为Y
		entity.setIsRedBack(FossConstants.NO);// 是否为红单 默认为否
		entity.setIsInit("N");// 是否初始化 否
		entity.setVersionNo(FossConstants.INIT_VERSION_NO);// 版本号 1
		entity.setStatementBillNo(SettlementConstants.DEFAULT_BILL_NO);
		billReceivableService.addBillReceivable(entity, info);
		return entity;
	}

	/**
	 * 
	 * 查询应收单信息(按客户编码)
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-26 下午5:01:58
	 */
	@Test
	public void queryReceiveBillInfoByCustomerTest() {
		// 新增应收单信息
		BillReceivableEntity receivablEntity = getBillReceivableEntity();
		BillReceivableOnlineQueryDto queryDto = new BillReceivableOnlineQueryDto();
		queryDto.setActive(FossConstants.ACTIVE);
		queryDto.setIsRedBack(FossConstants.NO);
		queryDto.setPageNo(0);
		queryDto.setPageSize(10);
		queryDto.setPayWay("1");
		// 按客户编码查询
		queryDto.setQueryType(SettlementConstants.BHO_QUERY_TYPE_BY_CUSTOMER);
		queryDto.setCustomerCode(receivablEntity.getCustomerCode());
		BillSOADOnlineResultDto dto = onlinePaymentReceiveBillService
				.queryReceiveBillInfo(queryDto);
		Assert.assertEquals(1, dto.getBillReceivableEntityList().size());
		Assert.assertEquals(1, dto.getCountNum());
	}

	/**
	 * 
	 * 查询应收单信息（按运单号）
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-26 下午5:01:58
	 */
	// @Test
	// public void queryReceiveBillInfoByWaybillTest() {
	// //新增应收单信息
	// BillReceivableEntity receivablEntity=getBillReceivableEntity();
	// BillReceivableOnlineQueryDto queryDto=new BillReceivableOnlineQueryDto();
	// queryDto.setActive(FossConstants.ACTIVE);
	// queryDto.setIsRedBack(FossConstants.NO);
	// queryDto.setPageNo(0);
	// queryDto.setPageSize(10);
	// queryDto.setPayWay("1");
	// //按运单号和手机号码查询
	// queryDto.setQueryType(SettlementConstants.BHO_QUERY_TYPE_BY_WAYBILL);
	// List<String> waybillNOs=new ArrayList<String>();
	// waybillNOs.add(receivablEntity.getWaybillNo());
	// List<String> telphoneNOs=new ArrayList<String>();
	// telphoneNOs.add("13888888888");
	// queryDto.setWayBillNos(waybillNOs);
	// queryDto.setTelphoneNos(telphoneNOs);
	// BillSOADOnlineResultDto
	// dto2=onlinePaymentReceiveBillService.queryReceiveBillInfo(queryDto);
	// Assert.assertEquals(1, dto2.getBillReceivableEntityList().size());
	// Assert.assertEquals(1, dto2.getCountNum());
	//
	// }
	/**
	 * 
	 * 查询应收单信息（按日期）
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-26 下午5:01:58
	 */
	@Test
	public void queryReceiveBillInfoByDateTest() {
		// 新增应收单信息
		BillReceivableEntity receivablEntity = getBillReceivableEntity();
		BillReceivableOnlineQueryDto queryDto = new BillReceivableOnlineQueryDto();
		queryDto.setActive(FossConstants.ACTIVE);
		queryDto.setIsRedBack(FossConstants.NO);
		queryDto.setPageNo(0);
		queryDto.setPageSize(10);
		queryDto.setPayWay("1");
		// 按日期查询
		queryDto.setQueryType(SettlementConstants.BHO_QUERY_TYPE_BY_DATE);
		queryDto.setCustomerCode(receivablEntity.getCustomerCode());
		queryDto.setBeginDate(DateUtils.addDays(new Date(), -2));
		queryDto.setEndDate(new Date());
		queryDto.setCargoName("衣");
		queryDto.setConsigneeName("张");
		queryDto.setPayWay(receivablEntity.getPaymentType());
		BillSOADOnlineResultDto dto = onlinePaymentReceiveBillService
				.queryReceiveBillInfo(queryDto);
		Assert.assertEquals(1, dto.getBillReceivableEntityList().size());
		Assert.assertEquals(1, dto.getCountNum());

	}

	/**
	 * 
	 * 查询应收单信息（客户和运单号）
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-26 下午5:01:58
	 */
	@Test
	public void queryReceiveBillInfoByCustomerAndWaybillTest() {
		// 新增应收单信息
		BillReceivableEntity receivablEntity = getBillReceivableEntity();
		BillReceivableOnlineQueryDto queryDto = new BillReceivableOnlineQueryDto();
		queryDto.setActive(FossConstants.ACTIVE);
		queryDto.setIsRedBack(FossConstants.NO);
		queryDto.setPageNo(0);
		queryDto.setPageSize(10);
		queryDto.setPayWay("1");
		// 按日期查询
		queryDto.setQueryType(SettlementConstants.BHO_QUERY_TYPE_BY_WAYBILL_CUSTOMER);
		queryDto.setCustomerCode(receivablEntity.getCustomerCode());
		List<String> waybillNOs = new ArrayList<String>();
		waybillNOs.add(receivablEntity.getWaybillNo());
		queryDto.setWayBillNos(waybillNOs);
		BillSOADOnlineResultDto dto = onlinePaymentReceiveBillService
				.queryReceiveBillInfo(queryDto);
		Assert.assertEquals(1, dto.getBillReceivableEntityList().size());
		Assert.assertEquals(1, dto.getCountNum());

	}

	/**
	 * 锁定应收单
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-28 下午1:46:56
	 */
	@Test
	
	public void lockReceiveBillInfoTest() {
		// 新增应收单信息
		BillReceivableEntity receivablEntity = getBillReceivableEntity();
		BillReceivableOnlineQueryDto queryDto = new BillReceivableOnlineQueryDto();
		queryDto.setReceivableNo(receivablEntity.getReceivableNo());
		queryDto.setCustomerCode(receivablEntity.getCustomerCode());
		queryDto.setAccountDate(receivablEntity.getAccountDate());
		try {
			onlinePaymentReceiveBillService.lockReceiveBillInfo(queryDto);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			
		}

	}
	/**
	 * 按应收单还款
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-28 下午1:46:56
	 */
	@Test
	public void payReceiveBillInfoTest(){
		// 新增应收单信息
		BillReceivableEntity receivablEntity = getBillReceivableEntity();
		BillReceivableOnlineQueryDto queryDto = new BillReceivableOnlineQueryDto();
		queryDto.setAmount(receivablEntity.getUnverifyAmount());
		queryDto.setOnlineNo("123456");
		queryDto.setReceivableNo(receivablEntity.getReceivableNo());
		queryDto.setAccountDate(receivablEntity.getAccountDate());
		try {
			onlinePaymentReceiveBillService.paymentReceiveBillInfo(queryDto);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}
}

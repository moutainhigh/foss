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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/web/test/util/TestUtil.java
 * 
 * FILE NAME        	: TestUtil.java
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

import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.dto.LineSignDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.PaymentSettlementDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.WaybillPickupInfoDto;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;


public class TestUtil {
	
/***********************************************************获取开单的数据******************************/
	
	/**
	 * 设置(开单只有预付现金)运单的基本属性
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-31 上午9:26:55
	 * @return
	 */
	public static WaybillPickupInfoDto getWaybillPickupInfoDtoForCash(
			String waybillNo,String paideMethod,
			String origOrgCode,String destOrgCode
			){
		WaybillPickupInfoDto	waybill=new WaybillPickupInfoDto();
		waybill.setId(UUIDUtils.getUUID());
		waybill.setWaybillNo(waybillNo);
		waybill.setDeliveryCustomerId("a7ef7627-b4de-46fd-9507-1693f8922831");//发货客户ID
		waybill.setDeliveryCustomerCode("19443");//发货客户编码
		waybill.setDeliveryCustomerName("向日葵");//发货客户名称
		waybill.setReceiveCustomerId("a7ef7627-b4de-46fd-9537-1693f8923831");//收货客户ID
		waybill.setReceiveCustomerCode("19450");//收货客户编码
		waybill.setReceiveCustomerName("李学军");//收货客户名称
		waybill.setReceiveOrgCode(origOrgCode);//出发部门编码---广州花都区花山营业部
		waybill.setProductId("C30001");//精准卡航
		waybill.setProductCode(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT);//精准卡航
		waybill.setCustomerPickupOrgCode(destOrgCode);//武汉派送部：W011306040304
		waybill.setLoadOrgCode(destOrgCode);//配载部门   武汉派送部：W011306040304
		waybill.setLastLoadOrgCode(destOrgCode);//最终配载部门 武汉派送部：W011306040304
		
		//开单付款方式
		waybill.setPaidMethod(paideMethod);
		Date date=new Date();
		waybill.setBillTime(date);//开单时间
		waybill.setPrePayAmount(new BigDecimal("10000"));//预付金额
		waybill.setToPayAmount(BigDecimal.ZERO);//到付金额为0
		waybill.setTransportFee(new BigDecimal("10000"));//公布价运费
		waybill.setTotalFee(new BigDecimal("10000"));//总费用
		waybill.setCreateUserCode("044983");//创建操作者编码
		waybill.setCreateOrgCode("W011303070303");//创建部门编码
		waybill.setCodAmount(BigDecimal.ZERO);//代收货款
		waybill.setCodRate(BigDecimal.ZERO);//代收费率
		waybill.setCodFee(BigDecimal.ZERO);//代收货款手续费
		waybill.setValueAddFee(BigDecimal.ZERO);//保价声明价值
		waybill.setInsuranceRate(BigDecimal.ZERO);//保价费率
		waybill.setInsuranceFee(BigDecimal.ZERO);//保价费
		waybill.setActive(FossConstants.ACTIVE);
		waybill.setServiceFee(BigDecimal.ZERO);
		return waybill;
	}
	
	/**
	 * 开单现金，且存在代收货款金额
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-31 下午2:12:35
	 * @param waybillNo
	 * @param paideMethod
	 * @param origOrgCode
	 * @param destOrgCode
	 * @return
	 */
	public static WaybillPickupInfoDto getWaybillPickupInfoDtoForCashAndCod(
			String waybillNo,String paideMethod,
			String origOrgCode,String destOrgCode
			){
		WaybillPickupInfoDto	waybill=new WaybillPickupInfoDto();
		waybill.setId(UUIDUtils.getUUID());
		waybill.setWaybillNo(waybillNo);
		waybill.setDeliveryCustomerId("a7ef7627-b4de-46fd-9507-1693f8922831");//发货客户ID
		waybill.setDeliveryCustomerCode("19443");//发货客户编码
		waybill.setDeliveryCustomerName("向日葵");//发货客户名称
		waybill.setReceiveCustomerId("a7ef7627-b4de-46fd-9537-1693f8923831");//收货客户ID
		waybill.setReceiveCustomerCode("19450");//收货客户编码
		waybill.setReceiveCustomerName("李学军");//收货客户名称
		waybill.setReceiveOrgCode(origOrgCode);//出发部门编码---广州花都区花山营业部
		waybill.setProductId("C30001");//精准卡航
		waybill.setProductCode(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT);//精准卡航
		waybill.setCustomerPickupOrgCode(destOrgCode);//武汉派送部：W011306040304
		waybill.setLoadOrgCode(destOrgCode);//配载部门   武汉派送部：W011306040304
		waybill.setLastLoadOrgCode(destOrgCode);//最终配载部门 武汉派送部：W011306040304
		
		//开单付款方式
		waybill.setPaidMethod(paideMethod);
		Date date=new Date();
		waybill.setBillTime(date);//开单时间
		waybill.setPrePayAmount(new BigDecimal("10000"));//预付金额
		waybill.setToPayAmount(BigDecimal.ZERO);//到付金额为0
		waybill.setTransportFee(new BigDecimal("10000"));//公布价运费
		waybill.setTotalFee(new BigDecimal("20000"));//总费用
		waybill.setCreateUserCode("044983");//创建操作者编码
		waybill.setCreateOrgCode("W011303070303");//创建部门编码
		waybill.setCodAmount(new BigDecimal("10000"));//代收货款10000
		waybill.setCodRate(BigDecimal.ZERO);//代收费率
		waybill.setCodFee(BigDecimal.ZERO);//代收货款手续费
		waybill.setValueAddFee(BigDecimal.ZERO);//保价声明价值
		waybill.setInsuranceRate(BigDecimal.ZERO);//保价费率
		waybill.setInsuranceFee(BigDecimal.ZERO);//保价费
		waybill.setActive(FossConstants.ACTIVE);

		waybill.setServiceFee(BigDecimal.ZERO);//装卸费
		
		return getWaybillPickupInfoDtoForCodBank(waybill);
	}
	
	/**
	 * 设置运单代收货款银行账户信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-2 下午4:25:37
	 * @param waybill
	 * @return
	 */
	private static WaybillPickupInfoDto getWaybillPickupInfoDtoForCodBank(WaybillPickupInfoDto waybill){
		//关于代收货款的一些信息
		waybill.setRefundType(SettlementDictionaryConstants.COD__COD_TYPE__RETURN_3_DAY);//退款类型
		waybill.setAccountCode("6222601310019372234");//返款帐户开户账户
		waybill.setAccountName("向日浩");//返款帐户开户名称
		waybill.setAccountBank("交通银行");//返款帐户开户银行
		waybill.setBankHQCode("00002");//开户行编码
		waybill.setPayeePhone("13916067264");//收款人手机号码
		waybill.setPayeeRelationship("朋友");//收款人与发货人关系
		waybill.setBankBranchCode("0000113");//支行编码（行号）
		waybill.setBankBranchName("交通银行广州市花都支行");//支行名称
		waybill.setPublicPrivateFlag("PRIVATE_ACCOUNT");//对公对私标志
		waybill.setProvinceName("广东省");//省份名称
		waybill.setProvinceCode("00023");//省份编码
		waybill.setCityName("广州市");//城市名称
		waybill.setCityCode("00332");//城市编码		
		return waybill;
	}
	
	/**
	 * 开单现金，且有装卸费的
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-1 上午10:00:33
	 * @param waybillNo
	 * @param paideMethod
	 * @param origOrgCode
	 * @param destOrgCode
	 * @return
	 */
	public static WaybillPickupInfoDto getWaybillPickupInfoDtoForCashAndService(
			String waybillNo,String paideMethod,
			String origOrgCode,String destOrgCode
			){
		WaybillPickupInfoDto	waybill=new WaybillPickupInfoDto();
		waybill.setId(UUIDUtils.getUUID());
		waybill.setWaybillNo(waybillNo);
		waybill.setDeliveryCustomerId("a7ef7627-b4de-46fd-9507-1693f8922831");//发货客户ID
		waybill.setDeliveryCustomerCode("19443");//发货客户编码
		waybill.setDeliveryCustomerName("向日葵");//发货客户名称
		waybill.setReceiveCustomerId("a7ef7627-b4de-46fd-9537-1693f8923831");//收货客户ID
		waybill.setReceiveCustomerCode("19450");//收货客户编码
		waybill.setReceiveCustomerName("李学军");//收货客户名称
		waybill.setReceiveOrgCode(origOrgCode);//出发部门编码---广州花都区花山营业部
		waybill.setProductId("C30001");//精准卡航
		waybill.setProductCode(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT);//精准卡航
		waybill.setCustomerPickupOrgCode(destOrgCode);//武汉派送部：W011306040304
		waybill.setLoadOrgCode(destOrgCode);//配载部门   武汉派送部：W011306040304
		waybill.setLastLoadOrgCode(destOrgCode);//最终配载部门 武汉派送部：W011306040304
		
		//开单付款方式
		waybill.setPaidMethod(paideMethod);
		Date date=new Date();
		waybill.setBillTime(date);//开单时间
		waybill.setPrePayAmount(new BigDecimal("10000"));//预付金额
		waybill.setToPayAmount(BigDecimal.ZERO);//到付金额为0
		waybill.setTransportFee(new BigDecimal("10000"));//公布价运费
		waybill.setTotalFee(new BigDecimal("20000"));//总费用
		waybill.setCreateUserCode("044983");//创建操作者编码
		waybill.setCreateOrgCode("W011303070303");//创建部门编码
		waybill.setCodAmount(new BigDecimal("10000"));//代收货款10000
		waybill.setCodRate(BigDecimal.ZERO);//代收费率
		waybill.setCodFee(BigDecimal.ZERO);//代收货款手续费
		waybill.setValueAddFee(BigDecimal.ZERO);//保价声明价值
		waybill.setInsuranceRate(BigDecimal.ZERO);//保价费率
		waybill.setInsuranceFee(BigDecimal.ZERO);//保价费
		waybill.setActive(FossConstants.ACTIVE);
		waybill.setServiceFee(new BigDecimal("100"));//装卸费
		return waybill;
	}

	/**
	 * 开单现金，含有代收货款，且需要支付装卸费的
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-1 上午10:00:33
	 * @param waybillNo
	 * @param paideMethod
	 * @param origOrgCode
	 * @param destOrgCode
	 * @return
	 */
	public static WaybillPickupInfoDto getWaybillPickupInfoDtoForCashAndCodService(
			String waybillNo,String paideMethod,
			String origOrgCode,String destOrgCode
			){
		WaybillPickupInfoDto	waybill=new WaybillPickupInfoDto();
		waybill.setId(UUIDUtils.getUUID());
		waybill.setWaybillNo(waybillNo);
		waybill.setDeliveryCustomerId("a7ef7627-b4de-46fd-9507-1693f8922831");//发货客户ID
		waybill.setDeliveryCustomerCode("19443");//发货客户编码
		waybill.setDeliveryCustomerName("向日葵");//发货客户名称
		waybill.setReceiveCustomerId("a7ef7627-b4de-46fd-9537-1693f8923831");//收货客户ID
		waybill.setReceiveCustomerCode("19450");//收货客户编码
		waybill.setReceiveCustomerName("李学军");//收货客户名称
		waybill.setReceiveOrgCode(origOrgCode);//出发部门编码---广州花都区花山营业部
		waybill.setProductId("C30001");//精准卡航
		waybill.setProductCode(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT);//精准卡航
		waybill.setCustomerPickupOrgCode(destOrgCode);//武汉派送部：W011306040304
		waybill.setLoadOrgCode(destOrgCode);//配载部门   武汉派送部：W011306040304
		waybill.setLastLoadOrgCode(destOrgCode);//最终配载部门 武汉派送部：W011306040304
		
		//开单付款方式
		waybill.setPaidMethod(paideMethod);
		Date date=new Date();
		waybill.setBillTime(date);//开单时间
		waybill.setPrePayAmount(new BigDecimal("10000"));//预付金额
		waybill.setToPayAmount(BigDecimal.ZERO);//到付金额为0
		waybill.setTransportFee(new BigDecimal("10000"));//公布价运费
		waybill.setTotalFee(new BigDecimal("20000"));//总费用
		waybill.setCreateUserCode("044983");//创建操作者编码
		waybill.setCreateOrgCode("W011303070303");//创建部门编码
		waybill.setCodAmount(new BigDecimal("10000"));//代收货款10000
		waybill.setCodRate(BigDecimal.ZERO);//代收费率
		waybill.setCodFee(BigDecimal.ZERO);//代收货款手续费
		waybill.setValueAddFee(BigDecimal.ZERO);//保价声明价值
		waybill.setInsuranceRate(BigDecimal.ZERO);//保价费率
		waybill.setInsuranceFee(BigDecimal.ZERO);//保价费
		waybill.setActive(FossConstants.ACTIVE);
		waybill.setServiceFee(new BigDecimal("100"));//装卸费
		return  getWaybillPickupInfoDtoForCodBank(waybill);
	}
	
	
	/**
	 * 开单银行卡
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-31 上午9:26:55
	 * @return
	 */
	public static WaybillPickupInfoDto getWaybillPickupInfoDtoForCard(
			String waybillNo,String paideMethod,
			String origOrgCode,String destOrgCode
			){
		WaybillPickupInfoDto	waybill=new WaybillPickupInfoDto();
		waybill.setId(UUIDUtils.getUUID());
		waybill.setWaybillNo(waybillNo);
		waybill.setDeliveryCustomerId("a7ef7627-b4de-46fd-9507-1693f8922831");//发货客户ID
		waybill.setDeliveryCustomerCode("19443");//发货客户编码
		waybill.setDeliveryCustomerName("向日葵");//发货客户名称
		waybill.setReceiveCustomerId("a7ef7627-b4de-46fd-9537-1693f8923831");//收货客户ID
		waybill.setReceiveCustomerCode("19450");//收货客户编码
		waybill.setReceiveCustomerName("李学军");//收货客户名称
		waybill.setReceiveOrgCode(origOrgCode);//出发部门编码---广州花都区花山营业部
		waybill.setProductId("C30001");//精准卡航
		waybill.setProductCode(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT);//精准卡航
		
		waybill.setCustomerPickupOrgCode(destOrgCode);//武汉派送部：W011306040304
		waybill.setLoadOrgCode(destOrgCode);//配载部门   武汉派送部：W011306040304
		waybill.setLastLoadOrgCode(destOrgCode);//最终配载部门 武汉派送部：W011306040304
		
		//开单付款方式
		waybill.setPaidMethod(paideMethod);
		Date date=new Date();
		waybill.setBillTime(date);//开单时间
		waybill.setPrePayAmount(new BigDecimal("10000"));//预付金额
		waybill.setToPayAmount(BigDecimal.ZERO);//到付金额为0
		waybill.setTransportFee(new BigDecimal("10000"));//公布价运费
		waybill.setTotalFee(new BigDecimal("10000"));//总费用
		waybill.setCreateUserCode("044983");//创建操作者编码
		waybill.setCreateOrgCode("W011303070303");//创建部门编码
		waybill.setCodAmount(BigDecimal.ZERO);//代收货款
		waybill.setCodRate(BigDecimal.ZERO);//代收费率
		waybill.setCodFee(BigDecimal.ZERO);//代收货款手续费
		waybill.setValueAddFee(BigDecimal.ZERO);//保价声明价值
		waybill.setInsuranceRate(BigDecimal.ZERO);//保价费率
		waybill.setInsuranceFee(BigDecimal.ZERO);//保价费
		waybill.setActive(FossConstants.ACTIVE);
		waybill.setServiceFee(BigDecimal.ZERO);//装卸费
		return waybill;
	}
	
	/**
	 * 开单银行卡，且存在代收货款金额
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-31 下午2:12:35
	 * @param waybillNo
	 * @param paideMethod
	 * @param origOrgCode
	 * @param destOrgCode
	 * @return
	 */
	public static WaybillPickupInfoDto getWaybillPickupInfoDtoForCardAndCod(
			String waybillNo,String paideMethod,
			String origOrgCode,String destOrgCode
			){
		WaybillPickupInfoDto	waybill=new WaybillPickupInfoDto();
		waybill.setId(UUIDUtils.getUUID());
		waybill.setWaybillNo(waybillNo);
		waybill.setDeliveryCustomerId("a7ef7627-b4de-46fd-9507-1693f8922831");//发货客户ID
		waybill.setDeliveryCustomerCode("19443");//发货客户编码
		waybill.setDeliveryCustomerName("向日葵");//发货客户名称
		waybill.setReceiveCustomerId("a7ef7627-b4de-46fd-9537-1693f8923831");//收货客户ID
		waybill.setReceiveCustomerCode("19450");//收货客户编码
		waybill.setReceiveCustomerName("李学军");//收货客户名称
		waybill.setReceiveOrgCode(origOrgCode);//出发部门编码---广州花都区花山营业部
		waybill.setProductId("C30001");//精准卡航
		waybill.setProductCode(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT);//精准卡航
		waybill.setCustomerPickupOrgCode(destOrgCode);//武汉派送部：W011306040304
		waybill.setLoadOrgCode(destOrgCode);//配载部门   武汉派送部：W011306040304
		waybill.setLastLoadOrgCode(destOrgCode);//最终配载部门 武汉派送部：W011306040304
		
		//开单付款方式
		waybill.setPaidMethod(paideMethod);
		Date date=new Date();
		waybill.setBillTime(date);//开单时间
		waybill.setPrePayAmount(new BigDecimal("10000"));//预付金额
		waybill.setToPayAmount(BigDecimal.ZERO);//到付金额为0
		waybill.setTransportFee(new BigDecimal("10000"));//公布价运费
		waybill.setTotalFee(new BigDecimal("20000"));//总费用
		waybill.setCreateUserCode("044983");//创建操作者编码
		waybill.setCreateOrgCode("W011303070303");//创建部门编码
		waybill.setCodAmount(new BigDecimal("10000"));//代收货款10000
		waybill.setCodRate(BigDecimal.ZERO);//代收费率
		waybill.setCodFee(BigDecimal.ZERO);//代收货款手续费
		waybill.setValueAddFee(BigDecimal.ZERO);//保价声明价值
		waybill.setInsuranceRate(BigDecimal.ZERO);//保价费率
		waybill.setInsuranceFee(BigDecimal.ZERO);//保价费
		waybill.setActive(FossConstants.ACTIVE);
		waybill.setServiceFee(BigDecimal.ZERO);//装卸费
		
		return getWaybillPickupInfoDtoForCodBank(waybill);
	}
	
	/**
	 * 开单银行卡，且需要支付装卸费的
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-1 上午10:00:33
	 * @param waybillNo
	 * @param paideMethod
	 * @param origOrgCode
	 * @param destOrgCode
	 * @return
	 */
	public static WaybillPickupInfoDto getWaybillPickupInfoDtoForCardAndService(
			String waybillNo,String paideMethod,
			String origOrgCode,String destOrgCode
			){
		WaybillPickupInfoDto	waybill=new WaybillPickupInfoDto();
		waybill.setId(UUIDUtils.getUUID());
		waybill.setWaybillNo(waybillNo);
		waybill.setDeliveryCustomerId("a7ef7627-b4de-46fd-9507-1693f8922831");//发货客户ID
		waybill.setDeliveryCustomerCode("19443");//发货客户编码
		waybill.setDeliveryCustomerName("向日葵");//发货客户名称
		waybill.setReceiveCustomerId("a7ef7627-b4de-46fd-9537-1693f8923831");//收货客户ID
		waybill.setReceiveCustomerCode("19450");//收货客户编码
		waybill.setReceiveCustomerName("李学军");//收货客户名称
		waybill.setReceiveOrgCode(origOrgCode);//出发部门编码---广州花都区花山营业部
		waybill.setProductId("C30001");//精准卡航
		waybill.setProductCode(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT);//精准卡航
		waybill.setCustomerPickupOrgCode(destOrgCode);//武汉派送部：W011306040304
		waybill.setLoadOrgCode(destOrgCode);//配载部门   武汉派送部：W011306040304
		waybill.setLastLoadOrgCode(destOrgCode);//最终配载部门 武汉派送部：W011306040304
		
		//开单付款方式
		waybill.setPaidMethod(paideMethod);
		Date date=new Date();
		waybill.setBillTime(date);//开单时间
		waybill.setPrePayAmount(new BigDecimal("10000"));//预付金额
		waybill.setToPayAmount(BigDecimal.ZERO);//到付金额为0
		waybill.setTransportFee(new BigDecimal("10000"));//公布价运费
		waybill.setTotalFee(new BigDecimal("20000"));//总费用
		waybill.setCreateUserCode("044983");//创建操作者编码
		waybill.setCreateOrgCode("W011303070303");//创建部门编码
		waybill.setCodAmount(new BigDecimal("10000"));//代收货款10000
		waybill.setCodRate(BigDecimal.ZERO);//代收费率
		waybill.setCodFee(BigDecimal.ZERO);//代收货款手续费
		waybill.setValueAddFee(BigDecimal.ZERO);//保价声明价值
		waybill.setInsuranceRate(BigDecimal.ZERO);//保价费率
		waybill.setInsuranceFee(BigDecimal.ZERO);//保价费
		waybill.setActive(FossConstants.ACTIVE);
		waybill.setServiceFee(new BigDecimal("100"));//装卸费
		return waybill;
	}

	/**
	 * 开单银行卡，含有代收货款且需要支付装卸费的
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-1 上午10:00:33
	 * @param waybillNo
	 * @param paideMethod
	 * @param origOrgCode
	 * @param destOrgCode
	 * @return
	 */
	public static WaybillPickupInfoDto getWaybillPickupInfoDtoForCardAndCodService(
			String waybillNo,String paideMethod,
			String origOrgCode,String destOrgCode
			){
		WaybillPickupInfoDto	waybill=new WaybillPickupInfoDto();
		waybill.setId(UUIDUtils.getUUID());
		waybill.setWaybillNo(waybillNo);
		waybill.setDeliveryCustomerId("a7ef7627-b4de-46fd-9507-1693f8922831");//发货客户ID
		waybill.setDeliveryCustomerCode("19443");//发货客户编码
		waybill.setDeliveryCustomerName("向日葵");//发货客户名称
		waybill.setReceiveCustomerId("a7ef7627-b4de-46fd-9537-1693f8923831");//收货客户ID
		waybill.setReceiveCustomerCode("19450");//收货客户编码
		waybill.setReceiveCustomerName("李学军");//收货客户名称
		waybill.setReceiveOrgCode(origOrgCode);//出发部门编码---广州花都区花山营业部
		waybill.setProductId("C30001");//精准卡航
		waybill.setProductCode(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT);//精准卡航
		waybill.setCustomerPickupOrgCode(destOrgCode);//武汉派送部：W011306040304
		waybill.setLoadOrgCode(destOrgCode);//配载部门   武汉派送部：W011306040304
		waybill.setLastLoadOrgCode(destOrgCode);//最终配载部门 武汉派送部：W011306040304
		
		//开单付款方式
		waybill.setPaidMethod(paideMethod);
		Date date=new Date();
		waybill.setBillTime(date);//开单时间
		waybill.setPrePayAmount(new BigDecimal("10000"));//预付金额
		waybill.setToPayAmount(BigDecimal.ZERO);//到付金额为0
		waybill.setTransportFee(new BigDecimal("10000"));//公布价运费
		waybill.setTotalFee(new BigDecimal("20000"));//总费用
		waybill.setCreateUserCode("044983");//创建操作者编码
		waybill.setCreateOrgCode("W011303070303");//创建部门编码
		waybill.setCodAmount(new BigDecimal("10000"));//代收货款10000
		waybill.setCodRate(BigDecimal.ZERO);//代收费率
		waybill.setCodFee(BigDecimal.ZERO);//代收货款手续费
		waybill.setValueAddFee(BigDecimal.ZERO);//保价声明价值
		waybill.setInsuranceRate(BigDecimal.ZERO);//保价费率
		waybill.setInsuranceFee(BigDecimal.ZERO);//保价费
		waybill.setActive(FossConstants.ACTIVE);
		waybill.setServiceFee(new BigDecimal("100"));//装卸费
		return getWaybillPickupInfoDtoForCodBank(waybill);
	}

	
	
	
	/**
	 * 开单支付方式-临欠
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-31 下午4:42:31
	 * @param waybillNo
	 * @param paideMethod
	 * @param origOrgCode
	 * @param destOrgCode
	 * @return
	 */
	public static WaybillPickupInfoDto getWaybillPickupInfoDtoForDt(String waybillNo,String paideMethod,
			String origOrgCode,String destOrgCode){
		WaybillPickupInfoDto	waybill=new WaybillPickupInfoDto();
		waybill.setId(UUIDUtils.getUUID());
		waybill.setWaybillNo(waybillNo);
		waybill.setDeliveryCustomerId("a7ef7627-b4de-46fd-9507-1693f8922831");//发货客户ID
		waybill.setDeliveryCustomerCode("19443");//发货客户编码
		waybill.setDeliveryCustomerName("向日葵");//发货客户名称
		waybill.setReceiveCustomerId("a7ef7627-b4de-46fd-9537-1693f8923831");//收货客户ID
		waybill.setReceiveCustomerCode("19450");//收货客户编码
		waybill.setReceiveCustomerName("李学军");//收货客户名称
		waybill.setReceiveOrgCode(origOrgCode);//出发部门编码---广州花都区花山营业部
		waybill.setProductId("C30001");//精准卡航
		waybill.setProductCode(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT);//精准卡航
		waybill.setCustomerPickupOrgCode(destOrgCode);//武汉派送部：W011306040304
		waybill.setLoadOrgCode(destOrgCode);//配载部门   武汉派送部：W011306040304
		waybill.setLastLoadOrgCode(destOrgCode);//最终配载部门 武汉派送部：W011306040304
		
		//开单付款方式
		waybill.setPaidMethod(paideMethod);
		Date date=new Date();
		waybill.setBillTime(date);//开单时间
		waybill.setPrePayAmount(new BigDecimal("10000"));//预付金额
		waybill.setToPayAmount(BigDecimal.ZERO);//到付金额为0
		waybill.setTransportFee(new BigDecimal("10000"));//公布价运费
		waybill.setTotalFee(new BigDecimal("10000"));//总费用
		waybill.setCreateUserCode("044983");//创建操作者编码
		waybill.setCreateOrgCode("W011303070303");//创建部门编码
		waybill.setCodAmount(BigDecimal.ZERO);//代收货款
		waybill.setCodRate(BigDecimal.ZERO);//代收费率
		waybill.setCodFee(BigDecimal.ZERO);//代收货款手续费
		waybill.setValueAddFee(BigDecimal.ZERO);//保价声明价值
		waybill.setInsuranceRate(BigDecimal.ZERO);//保价费率
		waybill.setInsuranceFee(BigDecimal.ZERO);//保价费
		waybill.setActive(FossConstants.ACTIVE);
		waybill.setServiceFee(BigDecimal.ZERO);//装卸费
		return waybill;
	}
	
	/**
	 * 开单为临欠，且包含代收货款的
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-31 下午2:14:48
	 * @param waybillNo
	 * @param paideMethod
	 * @param origOrgCode
	 * @param destOrgCode
	 * @return
	 */
	public static WaybillPickupInfoDto getWaybillPickupInfoDtoForDtAndCod(String waybillNo,String paideMethod,
			String origOrgCode,String destOrgCode){
		WaybillPickupInfoDto	waybill=new WaybillPickupInfoDto();
		waybill.setId(UUIDUtils.getUUID());
		waybill.setWaybillNo(waybillNo);
		waybill.setDeliveryCustomerId("a7ef7627-b4de-46fd-9507-1693f8922831");//发货客户ID
		waybill.setDeliveryCustomerCode("19443");//发货客户编码
		waybill.setDeliveryCustomerName("向日葵");//发货客户名称
		waybill.setReceiveCustomerId("a7ef7627-b4de-46fd-9537-1693f8923831");//收货客户ID
		waybill.setReceiveCustomerCode("19450");//收货客户编码
		waybill.setReceiveCustomerName("李学军");//收货客户名称
		waybill.setReceiveOrgCode(origOrgCode);//出发部门编码---广州花都区花山营业部
		waybill.setProductId("C30001");//精准卡航
		waybill.setProductCode(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT);//精准卡航
		waybill.setCustomerPickupOrgCode(destOrgCode);//武汉派送部：W011306040304
		waybill.setLoadOrgCode(destOrgCode);//配载部门   武汉派送部：W011306040304
		waybill.setLastLoadOrgCode(destOrgCode);//最终配载部门 武汉派送部：W011306040304
		
		//开单付款方式
		waybill.setPaidMethod(paideMethod);
		Date date=new Date();
		waybill.setBillTime(date);//开单时间
		waybill.setPrePayAmount(new BigDecimal("10000"));//预付金额
		waybill.setToPayAmount(BigDecimal.ZERO);//到付金额为0
		waybill.setTransportFee(new BigDecimal("10000"));//公布价运费
		waybill.setTotalFee(new BigDecimal("20000"));//总费用
		waybill.setCreateUserCode("044983");//创建操作者编码
		waybill.setCreateOrgCode("W011303070303");//创建部门编码
		waybill.setCodAmount(new BigDecimal("10000"));//代收货款10000
		waybill.setCodRate(BigDecimal.ZERO);//代收费率
		waybill.setCodFee(BigDecimal.ZERO);//代收货款手续费
		waybill.setValueAddFee(BigDecimal.ZERO);//保价声明价值
		waybill.setInsuranceRate(BigDecimal.ZERO);//保价费率
		waybill.setInsuranceFee(BigDecimal.ZERO);//保价费
		waybill.setActive(FossConstants.ACTIVE);
		waybill.setServiceFee(BigDecimal.ZERO);//装卸费
		return getWaybillPickupInfoDtoForCodBank(waybill);
	}
	
	/**
	 * 开单为临欠，且有装卸费的
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-31 下午2:14:48
	 * @param waybillNo
	 * @param paideMethod
	 * @param origOrgCode
	 * @param destOrgCode
	 * @return
	 */
	public static WaybillPickupInfoDto getWaybillPickupInfoDtoForDtAndService(String waybillNo,String paideMethod,
			String origOrgCode,String destOrgCode){
		WaybillPickupInfoDto	waybill=new WaybillPickupInfoDto();
		waybill.setId(UUIDUtils.getUUID());
		waybill.setWaybillNo(waybillNo);
		waybill.setDeliveryCustomerId("a7ef7627-b4de-46fd-9507-1693f8922831");//发货客户ID
		waybill.setDeliveryCustomerCode("19443");//发货客户编码
		waybill.setDeliveryCustomerName("向日葵");//发货客户名称
		waybill.setReceiveCustomerId("a7ef7627-b4de-46fd-9537-1693f8923831");//收货客户ID
		waybill.setReceiveCustomerCode("19450");//收货客户编码
		waybill.setReceiveCustomerName("李学军");//收货客户名称
		waybill.setReceiveOrgCode(origOrgCode);//出发部门编码---广州花都区花山营业部
		waybill.setProductId("C30001");//精准卡航
		waybill.setProductCode(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT);//精准卡航
		waybill.setCustomerPickupOrgCode(destOrgCode);//武汉派送部：W011306040304
		waybill.setLoadOrgCode(destOrgCode);//配载部门   武汉派送部：W011306040304
		waybill.setLastLoadOrgCode(destOrgCode);//最终配载部门 武汉派送部：W011306040304
		
		//开单付款方式
		waybill.setPaidMethod(paideMethod);
		Date date=new Date();
		waybill.setBillTime(date);//开单时间
		waybill.setPrePayAmount(new BigDecimal("10000"));//预付金额
		waybill.setToPayAmount(BigDecimal.ZERO);//到付金额为0
		waybill.setTransportFee(new BigDecimal("10000"));//公布价运费
		waybill.setTotalFee(new BigDecimal("20000"));//总费用
		waybill.setCreateUserCode("044983");//创建操作者编码
		waybill.setCreateOrgCode("W011303070303");//创建部门编码
		waybill.setCodAmount(new BigDecimal("10000"));//代收货款10000
		waybill.setCodRate(BigDecimal.ZERO);//代收费率
		waybill.setCodFee(BigDecimal.ZERO);//代收货款手续费
		waybill.setValueAddFee(BigDecimal.ZERO);//保价声明价值
		waybill.setInsuranceRate(BigDecimal.ZERO);//保价费率
		waybill.setInsuranceFee(BigDecimal.ZERO);//保价费
		waybill.setActive(FossConstants.ACTIVE);
		waybill.setServiceFee(new BigDecimal("100"));//装卸费
		return waybill;
	}
	
	/**
	 * 开单为临欠，包含代收货款和装卸费
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-31 下午2:14:48
	 * @param waybillNo
	 * @param paideMethod
	 * @param origOrgCode
	 * @param destOrgCode
	 * @return
	 */
	public static WaybillPickupInfoDto getWaybillPickupInfoDtoForDtAndCodService(String waybillNo,String paideMethod,
			String origOrgCode,String destOrgCode){
		WaybillPickupInfoDto	waybill=new WaybillPickupInfoDto();
		waybill.setId(UUIDUtils.getUUID());
		waybill.setWaybillNo(waybillNo);
		waybill.setDeliveryCustomerId("a7ef7627-b4de-46fd-9507-1693f8922831");//发货客户ID
		waybill.setDeliveryCustomerCode("19443");//发货客户编码
		waybill.setDeliveryCustomerName("向日葵");//发货客户名称
		waybill.setReceiveCustomerId("a7ef7627-b4de-46fd-9537-1693f8923831");//收货客户ID
		waybill.setReceiveCustomerCode("19450");//收货客户编码
		waybill.setReceiveCustomerName("李学军");//收货客户名称
		waybill.setReceiveOrgCode(origOrgCode);//出发部门编码---广州花都区花山营业部
		waybill.setProductId("C30001");//精准卡航
		waybill.setProductCode(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT);//精准卡航
		waybill.setCustomerPickupOrgCode(destOrgCode);//武汉派送部：W011306040304
		waybill.setLoadOrgCode(destOrgCode);//配载部门   武汉派送部：W011306040304
		waybill.setLastLoadOrgCode(destOrgCode);//最终配载部门 武汉派送部：W011306040304
		
		//开单付款方式
		waybill.setPaidMethod(paideMethod);
		Date date=new Date();
		waybill.setBillTime(date);//开单时间
		waybill.setPrePayAmount(new BigDecimal("10000"));//预付金额
		waybill.setToPayAmount(BigDecimal.ZERO);//到付金额为0
		waybill.setTransportFee(new BigDecimal("10000"));//公布价运费
		waybill.setTotalFee(new BigDecimal("20000"));//总费用
		waybill.setCreateUserCode("044983");//创建操作者编码
		waybill.setCreateOrgCode("W011303070303");//创建部门编码
		waybill.setCodAmount(new BigDecimal("10000"));//代收货款10000
		waybill.setCodRate(BigDecimal.ZERO);//代收费率
		waybill.setCodFee(BigDecimal.ZERO);//代收货款手续费
		waybill.setValueAddFee(BigDecimal.ZERO);//保价声明价值
		waybill.setInsuranceRate(BigDecimal.ZERO);//保价费率
		waybill.setInsuranceFee(BigDecimal.ZERO);//保价费
		waybill.setActive(FossConstants.ACTIVE);
		waybill.setServiceFee(new BigDecimal("100"));//装卸费
		return getWaybillPickupInfoDtoForCodBank(waybill);
	}
	
	
	
	
	/**
	 * 开单付款方式为-月结
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-31           上午11:06:21
	 * @param waybillNo    运单号
	 * @param paideMethod  开单付款方式
	 * @param origOrgCode  始发部门编码
	 * @param destOrgCode  到达部门编码
	 * @return
	 */
	public static WaybillPickupInfoDto getWaybillPickupInfoDtoForCt(String waybillNo,String paideMethod,
			String origOrgCode,String destOrgCode){
		WaybillPickupInfoDto	waybill=new WaybillPickupInfoDto();
		waybill.setId(UUIDUtils.getUUID());
		waybill.setWaybillNo(waybillNo);
		waybill.setDeliveryCustomerId("a7ef7627-b4de-46fd-9507-1693f8922831");//发货客户ID
		waybill.setDeliveryCustomerCode("19443");//发货客户编码
		waybill.setDeliveryCustomerName("向日葵");//发货客户名称
		waybill.setReceiveCustomerId("a7ef7627-b4de-46fd-9537-1693f8923831");//收货客户ID
		waybill.setReceiveCustomerCode("19450");//收货客户编码
		waybill.setReceiveCustomerName("李学军");//收货客户名称
		waybill.setReceiveOrgCode(origOrgCode);//出发部门编码---广州花都区花山营业部
		waybill.setProductId("C30001");//精准卡航
		waybill.setProductCode(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT);//精准卡航
		waybill.setCustomerPickupOrgCode(destOrgCode);//武汉派送部：W011306040304
		waybill.setLoadOrgCode(destOrgCode);//配载部门   武汉派送部：W011306040304
		waybill.setLastLoadOrgCode(destOrgCode);//最终配载部门 武汉派送部：W011306040304
		
		//开单付款方式
		waybill.setPaidMethod(paideMethod);
		Date date=new Date();
		waybill.setBillTime(date);//开单时间
		waybill.setPrePayAmount(new BigDecimal("10000"));//预付金额
		waybill.setToPayAmount(BigDecimal.ZERO);//到付金额为0
		waybill.setTransportFee(new BigDecimal("10000"));//公布价运费
		waybill.setTotalFee(new BigDecimal("10000"));//总费用
		waybill.setCreateUserCode("044983");//创建操作者编码
		waybill.setCreateOrgCode("W011303070303");//创建部门编码
		waybill.setCodAmount(BigDecimal.ZERO);//代收货款
		waybill.setCodRate(BigDecimal.ZERO);//代收费率
		waybill.setCodFee(BigDecimal.ZERO);//代收货款手续费
		waybill.setValueAddFee(BigDecimal.ZERO);//保价声明价值
		waybill.setInsuranceRate(BigDecimal.ZERO);//保价费率
		waybill.setInsuranceFee(BigDecimal.ZERO);//保价费
		waybill.setActive(FossConstants.ACTIVE);
		waybill.setServiceFee(BigDecimal.ZERO);//装卸费
		return waybill;
	}
	
	/**
	 * 开单付款方式为-月结,且包含有代收货款
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-31           上午11:06:21
	 * @param waybillNo    运单号
	 * @param paideMethod  开单付款方式
	 * @param origOrgCode  始发部门编码
	 * @param destOrgCode  到达部门编码
	 * @return
	 */
	public static WaybillPickupInfoDto getWaybillPickupInfoDtoForCtAndCod(String waybillNo,String paideMethod,
			String origOrgCode,String destOrgCode){
		WaybillPickupInfoDto	waybill=new WaybillPickupInfoDto();
		waybill.setId(UUIDUtils.getUUID());
		waybill.setWaybillNo(waybillNo);
		waybill.setDeliveryCustomerId("a7ef7627-b4de-46fd-9507-1693f8922831");//发货客户ID
		waybill.setDeliveryCustomerCode("19443");//发货客户编码
		waybill.setDeliveryCustomerName("向日葵");//发货客户名称
		waybill.setReceiveCustomerId("a7ef7627-b4de-46fd-9537-1693f8923831");//收货客户ID
		waybill.setReceiveCustomerCode("19450");//收货客户编码
		waybill.setReceiveCustomerName("李学军");//收货客户名称
		waybill.setReceiveOrgCode(origOrgCode);//出发部门编码---广州花都区花山营业部
		waybill.setProductId("C30001");//精准卡航
		waybill.setProductCode(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT);//精准卡航
		waybill.setCustomerPickupOrgCode(destOrgCode);//武汉派送部：W011306040304
		waybill.setLoadOrgCode(destOrgCode);//配载部门   武汉派送部：W011306040304
		waybill.setLastLoadOrgCode(destOrgCode);//最终配载部门 武汉派送部：W011306040304
		
		//开单付款方式
		waybill.setPaidMethod(paideMethod);
		Date date=new Date();
		waybill.setBillTime(date);//开单时间
		waybill.setPrePayAmount(new BigDecimal("10000"));//预付金额
		waybill.setToPayAmount(BigDecimal.ZERO);//到付金额为0
		waybill.setTransportFee(new BigDecimal("10000"));//公布价运费
		waybill.setTotalFee(new BigDecimal("10000"));//总费用
		waybill.setCreateUserCode("044983");//创建操作者编码
		waybill.setCreateOrgCode("W011303070303");//创建部门编码
		waybill.setCodAmount(BigDecimal.ZERO);//代收货款
		waybill.setCodRate(BigDecimal.ZERO);//代收费率
		waybill.setCodFee(BigDecimal.ZERO);//代收货款手续费
		waybill.setValueAddFee(BigDecimal.ZERO);//保价声明价值
		waybill.setInsuranceRate(BigDecimal.ZERO);//保价费率
		waybill.setInsuranceFee(BigDecimal.ZERO);//保价费
		waybill.setActive(FossConstants.ACTIVE);
		waybill.setServiceFee(BigDecimal.ZERO);//装卸费
		return getWaybillPickupInfoDtoForCodBank(waybill);
	}
	
	/**
	 * 开单为月结，包含有装卸费的
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-1 上午10:10:38
	 * @param waybillNo
	 * @param paideMethod
	 * @param origOrgCode
	 * @param destOrgCode
	 * @return
	 */
	public static WaybillPickupInfoDto getWaybillPickupInfoDtoForCtAndService(String waybillNo,String paideMethod,
			String origOrgCode,String destOrgCode){
		WaybillPickupInfoDto	waybill=new WaybillPickupInfoDto();
		waybill.setId(UUIDUtils.getUUID());
		waybill.setWaybillNo(waybillNo);
		waybill.setDeliveryCustomerId("a7ef7627-b4de-46fd-9507-1693f8922831");//发货客户ID
		waybill.setDeliveryCustomerCode("19443");//发货客户编码
		waybill.setDeliveryCustomerName("向日葵");//发货客户名称
		waybill.setReceiveCustomerId("a7ef7627-b4de-46fd-9537-1693f8923831");//收货客户ID
		waybill.setReceiveCustomerCode("19450");//收货客户编码
		waybill.setReceiveCustomerName("李学军");//收货客户名称
		waybill.setReceiveOrgCode(origOrgCode);//出发部门编码---广州花都区花山营业部
		waybill.setProductId("C30001");//精准卡航
		waybill.setProductCode(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT);//精准卡航
		waybill.setCustomerPickupOrgCode(destOrgCode);//武汉派送部：W011306040304
		waybill.setLoadOrgCode(destOrgCode);//配载部门   武汉派送部：W011306040304
		waybill.setLastLoadOrgCode(destOrgCode);//最终配载部门 武汉派送部：W011306040304
		
		//开单付款方式
		waybill.setPaidMethod(paideMethod);
		Date date=new Date();
		waybill.setBillTime(date);//开单时间
		waybill.setPrePayAmount(new BigDecimal("10000"));//预付金额
		waybill.setToPayAmount(BigDecimal.ZERO);//到付金额为0
		waybill.setTransportFee(new BigDecimal("10000"));//公布价运费
		waybill.setTotalFee(new BigDecimal("10000"));//总费用
		waybill.setCreateUserCode("044983");//创建操作者编码
		waybill.setCreateOrgCode("W011303070303");//创建部门编码
		waybill.setCodAmount(BigDecimal.ZERO);//代收货款
		waybill.setCodRate(BigDecimal.ZERO);//代收费率
		waybill.setCodFee(BigDecimal.ZERO);//代收货款手续费
		waybill.setValueAddFee(BigDecimal.ZERO);//保价声明价值
		waybill.setInsuranceRate(BigDecimal.ZERO);//保价费率
		waybill.setInsuranceFee(BigDecimal.ZERO);//保价费
		waybill.setActive(FossConstants.ACTIVE);
		waybill.setServiceFee(new BigDecimal("100"));//装卸费
		return waybill;
	}
	
	/**
	 * 开单为月结，包含有代收货款和装卸费的
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-1 上午10:11:30
	 * @param waybillNo
	 * @param paideMethod
	 * @param origOrgCode
	 * @param destOrgCode
	 * @return
	 */
	public static WaybillPickupInfoDto getWaybillPickupInfoDtoForCtAndCodService(String waybillNo,String paideMethod,
			String origOrgCode,String destOrgCode){
		WaybillPickupInfoDto	waybill=new WaybillPickupInfoDto();
		waybill.setId(UUIDUtils.getUUID());
		waybill.setWaybillNo(waybillNo);
		waybill.setDeliveryCustomerId("a7ef7627-b4de-46fd-9507-1693f8922831");//发货客户ID
		waybill.setDeliveryCustomerCode("19443");//发货客户编码
		waybill.setDeliveryCustomerName("向日葵");//发货客户名称
		waybill.setReceiveCustomerId("a7ef7627-b4de-46fd-9537-1693f8923831");//收货客户ID
		waybill.setReceiveCustomerCode("19450");//收货客户编码
		waybill.setReceiveCustomerName("李学军");//收货客户名称
		waybill.setReceiveOrgCode(origOrgCode);//出发部门编码---广州花都区花山营业部
		waybill.setProductId("C30001");//精准卡航
		waybill.setProductCode(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT);//精准卡航
		waybill.setCustomerPickupOrgCode(destOrgCode);//武汉派送部：W011306040304
		waybill.setLoadOrgCode(destOrgCode);//配载部门   武汉派送部：W011306040304
		waybill.setLastLoadOrgCode(destOrgCode);//最终配载部门 武汉派送部：W011306040304
		
		//开单付款方式
		waybill.setPaidMethod(paideMethod);
		Date date=new Date();
		waybill.setBillTime(date);//开单时间
		waybill.setPrePayAmount(new BigDecimal("10000"));//预付金额
		waybill.setToPayAmount(BigDecimal.ZERO);//到付金额为0
		waybill.setTransportFee(new BigDecimal("10000"));//公布价运费
		waybill.setTotalFee(new BigDecimal("10000"));//总费用
		waybill.setCreateUserCode("044983");//创建操作者编码
		waybill.setCreateOrgCode("W011303070303");//创建部门编码
		waybill.setCodAmount(BigDecimal.ZERO);//代收货款
		waybill.setCodRate(BigDecimal.ZERO);//代收费率
		waybill.setCodFee(BigDecimal.ZERO);//代收货款手续费
		waybill.setValueAddFee(BigDecimal.ZERO);//保价声明价值
		waybill.setInsuranceRate(BigDecimal.ZERO);//保价费率
		waybill.setInsuranceFee(BigDecimal.ZERO);//保价费
		waybill.setActive(FossConstants.ACTIVE);
		waybill.setServiceFee(new BigDecimal("100"));//装卸费
		return getWaybillPickupInfoDtoForCodBank(waybill);
	}
	
	
	
	
	/**
	 * 开单支付方式-网上支付
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-31 下午4:43:43
	 * @param waybillNo
	 * @param paideMethod
	 * @param origOrgCode
	 * @param destOrgCode
	 * @return
	 */
	public static WaybillPickupInfoDto getWaybillPickupInfoDtoForOnLine(String waybillNo,String paideMethod,
			String origOrgCode,String destOrgCode){
		WaybillPickupInfoDto	waybill=new WaybillPickupInfoDto();
		waybill.setId(UUIDUtils.getUUID());
		waybill.setWaybillNo(waybillNo);
		waybill.setDeliveryCustomerId("a7ef7627-b4de-46fd-9507-1693f8922831");//发货客户ID
		waybill.setDeliveryCustomerCode("19443");//发货客户编码
		waybill.setDeliveryCustomerName("向日葵");//发货客户名称
		waybill.setReceiveCustomerId("a7ef7627-b4de-46fd-9537-1693f8923831");//收货客户ID
		waybill.setReceiveCustomerCode("19450");//收货客户编码
		waybill.setReceiveCustomerName("李学军");//收货客户名称
		waybill.setReceiveOrgCode(origOrgCode);//出发部门编码---广州花都区花山营业部
		waybill.setProductId("C30001");//精准卡航
		waybill.setProductCode(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT);//精准卡航
		waybill.setCustomerPickupOrgCode(destOrgCode);//武汉派送部：W011306040304
		waybill.setLoadOrgCode(destOrgCode);//配载部门   武汉派送部：W011306040304
		waybill.setLastLoadOrgCode(destOrgCode);//最终配载部门 武汉派送部：W011306040304
		
		//开单付款方式
		waybill.setPaidMethod(paideMethod);
		Date date=new Date();
		waybill.setBillTime(date);//开单时间
		waybill.setPrePayAmount(new BigDecimal("10000"));//预付金额
		waybill.setToPayAmount(BigDecimal.ZERO);//到付金额为0
		waybill.setTransportFee(new BigDecimal("10000"));//公布价运费
		waybill.setTotalFee(new BigDecimal("10000"));//总费用
		waybill.setCreateUserCode("044983");//创建操作者编码
		waybill.setCreateOrgCode("W011303070303");//创建部门编码
		waybill.setCodAmount(BigDecimal.ZERO);//代收货款
		waybill.setCodRate(BigDecimal.ZERO);//代收费率
		waybill.setCodFee(BigDecimal.ZERO);//代收货款手续费
		waybill.setValueAddFee(BigDecimal.ZERO);//保价声明价值
		waybill.setInsuranceRate(BigDecimal.ZERO);//保价费率
		waybill.setInsuranceFee(BigDecimal.ZERO);//保价费
		waybill.setActive(FossConstants.ACTIVE);
		waybill.setServiceFee(BigDecimal.ZERO);//装卸费
		return waybill;
	}
	
	/**
	 * 开单支付方式-网上支付,且包含有代收货款
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-31 下午4:43:43
	 * @param waybillNo
	 * @param paideMethod
	 * @param origOrgCode
	 * @param destOrgCode
	 * @return
	 */
	public static WaybillPickupInfoDto getWaybillPickupInfoDtoForOnLineAndCod(String waybillNo,String paideMethod,
			String origOrgCode,String destOrgCode){
		WaybillPickupInfoDto	waybill=new WaybillPickupInfoDto();
		waybill.setId(UUIDUtils.getUUID());
		waybill.setWaybillNo(waybillNo);
		waybill.setDeliveryCustomerId("a7ef7627-b4de-46fd-9507-1693f8922831");//发货客户ID
		waybill.setDeliveryCustomerCode("19443");//发货客户编码
		waybill.setDeliveryCustomerName("向日葵");//发货客户名称
		waybill.setReceiveCustomerId("a7ef7627-b4de-46fd-9537-1693f8923831");//收货客户ID
		waybill.setReceiveCustomerCode("19450");//收货客户编码
		waybill.setReceiveCustomerName("李学军");//收货客户名称
		waybill.setReceiveOrgCode(origOrgCode);//出发部门编码---广州花都区花山营业部
		waybill.setProductId("C30001");//精准卡航
		waybill.setProductCode(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT);//精准卡航
		waybill.setCustomerPickupOrgCode(destOrgCode);//武汉派送部：W011306040304
		waybill.setLoadOrgCode(destOrgCode);//配载部门   武汉派送部：W011306040304
		waybill.setLastLoadOrgCode(destOrgCode);//最终配载部门 武汉派送部：W011306040304
		
		//开单付款方式
		waybill.setPaidMethod(paideMethod);
		Date date=new Date();
		waybill.setBillTime(date);//开单时间
		waybill.setPrePayAmount(new BigDecimal("10000"));//预付金额
		waybill.setToPayAmount(BigDecimal.ZERO);//到付金额为0
		waybill.setTransportFee(new BigDecimal("10000"));//公布价运费
		waybill.setTotalFee(new BigDecimal("10000"));//总费用
		waybill.setCreateUserCode("044983");//创建操作者编码
		waybill.setCreateOrgCode("W011303070303");//创建部门编码
		waybill.setCodAmount(BigDecimal.ZERO);//代收货款
		waybill.setCodRate(BigDecimal.ZERO);//代收费率
		waybill.setCodFee(BigDecimal.ZERO);//代收货款手续费
		waybill.setValueAddFee(BigDecimal.ZERO);//保价声明价值
		waybill.setInsuranceRate(BigDecimal.ZERO);//保价费率
		waybill.setInsuranceFee(BigDecimal.ZERO);//保价费
		waybill.setActive(FossConstants.ACTIVE);
		waybill.setServiceFee(BigDecimal.ZERO);//装卸费
		return getWaybillPickupInfoDtoForCodBank(waybill);
	}
	
	/**
	 * 开单支付方式-网上支付,且包含有装卸费的
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-31 下午4:43:43
	 * @param waybillNo
	 * @param paideMethod
	 * @param origOrgCode
	 * @param destOrgCode
	 * @return
	 */
	public static WaybillPickupInfoDto getWaybillPickupInfoDtoForOnLineAndService(String waybillNo,String paideMethod,
			String origOrgCode,String destOrgCode){
		WaybillPickupInfoDto	waybill=new WaybillPickupInfoDto();
		waybill.setId(UUIDUtils.getUUID());
		waybill.setWaybillNo(waybillNo);
		waybill.setDeliveryCustomerId("a7ef7627-b4de-46fd-9507-1693f8922831");//发货客户ID
		waybill.setDeliveryCustomerCode("19443");//发货客户编码
		waybill.setDeliveryCustomerName("向日葵");//发货客户名称
		waybill.setReceiveCustomerId("a7ef7627-b4de-46fd-9537-1693f8923831");//收货客户ID
		waybill.setReceiveCustomerCode("19450");//收货客户编码
		waybill.setReceiveCustomerName("李学军");//收货客户名称
		waybill.setReceiveOrgCode(origOrgCode);//出发部门编码---广州花都区花山营业部
		waybill.setProductId("C30001");//精准卡航
		waybill.setProductCode(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT);//精准卡航
		
		waybill.setCustomerPickupOrgCode(destOrgCode);//武汉派送部：W011306040304
		waybill.setLoadOrgCode(destOrgCode);//配载部门   武汉派送部：W011306040304
		waybill.setLastLoadOrgCode(destOrgCode);//最终配载部门 武汉派送部：W011306040304
		
		//开单付款方式
		waybill.setPaidMethod(paideMethod);
		
		Date date=new Date();
		waybill.setBillTime(date);//开单时间
		waybill.setPrePayAmount(new BigDecimal("10000"));//预付金额
		waybill.setToPayAmount(BigDecimal.ZERO);//到付金额为0
		waybill.setTransportFee(new BigDecimal("10000"));//公布价运费
		waybill.setTotalFee(new BigDecimal("10000"));//总费用
		waybill.setCreateUserCode("044983");//创建操作者编码
		waybill.setCreateOrgCode("W011303070303");//创建部门编码
		waybill.setCodAmount(BigDecimal.ZERO);//代收货款
		waybill.setCodRate(BigDecimal.ZERO);//代收费率
		waybill.setCodFee(BigDecimal.ZERO);//代收货款手续费
		waybill.setValueAddFee(BigDecimal.ZERO);//保价声明价值
		waybill.setInsuranceRate(BigDecimal.ZERO);//保价费率
		waybill.setInsuranceFee(BigDecimal.ZERO);//保价费
		waybill.setActive(FossConstants.ACTIVE);
		waybill.setServiceFee(new BigDecimal("100"));//装卸费
		return waybill;
	}
	
	/**
	 * 开单支付方式-网上支付,且包含有代收货款和装卸费的
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-31 下午4:43:43
	 * @param waybillNo
	 * @param paideMethod
	 * @param origOrgCode
	 * @param destOrgCode
	 * @return
	 */
	public static WaybillPickupInfoDto getWaybillPickupInfoDtoForOnLineAndCodService(String waybillNo,String paideMethod,
			String origOrgCode,String destOrgCode){
		WaybillPickupInfoDto	waybill=new WaybillPickupInfoDto();
		waybill.setId(UUIDUtils.getUUID());
		waybill.setWaybillNo(waybillNo);
		waybill.setDeliveryCustomerId("a7ef7627-b4de-46fd-9507-1693f8922831");//发货客户ID
		waybill.setDeliveryCustomerCode("19443");//发货客户编码
		waybill.setDeliveryCustomerName("向日葵");//发货客户名称
		waybill.setReceiveCustomerId("a7ef7627-b4de-46fd-9537-1693f8923831");//收货客户ID
		waybill.setReceiveCustomerCode("19450");//收货客户编码
		waybill.setReceiveCustomerName("李学军");//收货客户名称
		waybill.setReceiveOrgCode(origOrgCode);//出发部门编码---广州花都区花山营业部
		waybill.setProductId("C30001");//精准卡航
		waybill.setProductCode(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT);//精准卡航
		waybill.setCustomerPickupOrgCode(destOrgCode);//武汉派送部：W011306040304
		waybill.setLoadOrgCode(destOrgCode);//配载部门   武汉派送部：W011306040304
		waybill.setLastLoadOrgCode(destOrgCode);//最终配载部门 武汉派送部：W011306040304
		
		//开单付款方式
		waybill.setPaidMethod(paideMethod);
		Date date=new Date();
		waybill.setBillTime(date);//开单时间
		waybill.setPrePayAmount(new BigDecimal("10000"));//预付金额
		waybill.setToPayAmount(BigDecimal.ZERO);//到付金额为0
		waybill.setTransportFee(new BigDecimal("10000"));//公布价运费
		waybill.setTotalFee(new BigDecimal("10000"));//总费用
		waybill.setCreateUserCode("044983");//创建操作者编码
		waybill.setCreateOrgCode("W011303070303");//创建部门编码
		waybill.setCodAmount(BigDecimal.ZERO);//代收货款
		waybill.setCodRate(BigDecimal.ZERO);//代收费率
		waybill.setCodFee(BigDecimal.ZERO);//代收货款手续费
		waybill.setValueAddFee(BigDecimal.ZERO);//保价声明价值
		waybill.setInsuranceRate(BigDecimal.ZERO);//保价费率
		waybill.setInsuranceFee(BigDecimal.ZERO);//保价费
		waybill.setActive(FossConstants.ACTIVE);
		waybill.setServiceFee(new BigDecimal("100"));//装卸费
		return getWaybillPickupInfoDtoForCodBank(waybill);
	}
	
	
	/**
	 * 获取开单付款方式为--全部为到付
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-31 上午11:42:46
	 * @param waybillNo
	 * @param paideMethod
	 * @param origOrgCode
	 * @param destOrgCode
	 * @return
	 */
	public static WaybillPickupInfoDto getWaybillPickupInfoDtoForFc(String waybillNo,String paideMethod,
			String origOrgCode,String destOrgCode){
		WaybillPickupInfoDto	waybill=new WaybillPickupInfoDto();
		waybill.setId(UUIDUtils.getUUID());
		waybill.setWaybillNo(waybillNo);
		waybill.setDeliveryCustomerId("a7ef7627-b4de-46fd-9507-1693f8922831");//发货客户ID
		waybill.setDeliveryCustomerCode("19443");//发货客户编码
		waybill.setDeliveryCustomerName("向日葵");//发货客户名称
		waybill.setReceiveCustomerId("a7ef7627-b4de-46fd-9537-1693f8923831");//收货客户ID
		waybill.setReceiveCustomerCode("19450");//收货客户编码
		waybill.setReceiveCustomerName("李学军");//收货客户名称
		waybill.setReceiveOrgCode(origOrgCode);//出发部门编码---广州花都区花山营业部
		waybill.setProductId("C30001");//精准卡航
		waybill.setProductCode(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT);//精准卡航
		waybill.setCustomerPickupOrgCode(destOrgCode);//武汉派送部：W011306040304
		waybill.setLoadOrgCode(destOrgCode);//配载部门   武汉派送部：W011306040304
		waybill.setLastLoadOrgCode(destOrgCode);//最终配载部门 武汉派送部：W011306040304
		
		//开单付款方式
		waybill.setPaidMethod(paideMethod);
		Date date=new Date();
		waybill.setBillTime(date);//开单时间
		waybill.setPrePayAmount(BigDecimal.ZERO);//预付金额
		waybill.setToPayAmount(new BigDecimal("10000"));//到付金额为10000
		waybill.setTransportFee(new BigDecimal("10000"));//公布价运费
		waybill.setTotalFee(new BigDecimal("10000"));//总费用
		waybill.setCreateUserCode("044983");//创建操作者编码
		waybill.setCreateOrgCode("W011303070303");//创建部门编码
		waybill.setCodAmount(BigDecimal.ZERO);//代收货款
		waybill.setCodRate(BigDecimal.ZERO);//代收费率
		waybill.setCodFee(BigDecimal.ZERO);//代收货款手续费
		waybill.setValueAddFee(BigDecimal.ZERO);//保价声明价值
		waybill.setInsuranceRate(BigDecimal.ZERO);//保价费率
		waybill.setInsuranceFee(BigDecimal.ZERO);//保价费
		waybill.setActive(FossConstants.ACTIVE);
		waybill.setServiceFee(BigDecimal.ZERO);//装卸费
		return waybill;
	}
	
	
	/**
	 * 开单付款方式为--全部为到付，且包含有代收货款
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-31 上午11:42:46
	 * @param waybillNo
	 * @param paideMethod
	 * @param origOrgCode
	 * @param destOrgCode
	 * @return
	 */
	public static WaybillPickupInfoDto getWaybillPickupInfoDtoForFcAndCod(String waybillNo,String paideMethod,
			String origOrgCode,String destOrgCode){
		WaybillPickupInfoDto	waybill=new WaybillPickupInfoDto();
		waybill.setId(UUIDUtils.getUUID());
		waybill.setWaybillNo(waybillNo);
		waybill.setDeliveryCustomerId("a7ef7627-b4de-46fd-9507-1693f8922831");//发货客户ID
		waybill.setDeliveryCustomerCode("19443");//发货客户编码
		waybill.setDeliveryCustomerName("向日葵");//发货客户名称
		waybill.setReceiveCustomerId("a7ef7627-b4de-46fd-9537-1693f8923831");//收货客户ID
		waybill.setReceiveCustomerCode("19450");//收货客户编码
		waybill.setReceiveCustomerName("李学军");//收货客户名称
		waybill.setReceiveOrgCode(origOrgCode);//出发部门编码---广州花都区花山营业部
		waybill.setProductId("C30001");//精准卡航
		waybill.setProductCode(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT);//精准卡航
		waybill.setCustomerPickupOrgCode(destOrgCode);//武汉派送部：W011306040304
		waybill.setLoadOrgCode(destOrgCode);//配载部门   武汉派送部：W011306040304
		waybill.setLastLoadOrgCode(destOrgCode);//最终配载部门 武汉派送部：W011306040304
		
		//开单付款方式
		waybill.setPaidMethod(paideMethod);
		Date date=new Date();
		waybill.setBillTime(date);//开单时间
		waybill.setPrePayAmount(BigDecimal.ZERO);//预付金额
		waybill.setToPayAmount(new BigDecimal("10000"));//到付金额为10000
		waybill.setTransportFee(new BigDecimal("10000"));//公布价运费
		waybill.setTotalFee(new BigDecimal("20000"));//总费用
		waybill.setCreateUserCode("044983");//创建操作者编码
		waybill.setCreateOrgCode("W011303070303");//创建部门编码
		waybill.setCodAmount(new BigDecimal("10000"));//代收货款10000
		waybill.setCodRate(BigDecimal.ZERO);//代收费率
		waybill.setCodFee(BigDecimal.ZERO);//代收货款手续费
		waybill.setValueAddFee(BigDecimal.ZERO);//保价声明价值
		waybill.setInsuranceRate(BigDecimal.ZERO);//保价费率
		waybill.setInsuranceFee(BigDecimal.ZERO);//保价费
		waybill.setActive(FossConstants.ACTIVE);
		waybill.setServiceFee(BigDecimal.ZERO);//装卸费
		return getWaybillPickupInfoDtoForCodBank(waybill);
	}
	
	
	/**
	 * 开单付款方式为--全部为到付，且有装卸费的
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-31 上午11:42:46
	 * @param waybillNo
	 * @param paideMethod
	 * @param origOrgCode
	 * @param destOrgCode
	 * @return
	 */
	public static WaybillPickupInfoDto getWaybillPickupInfoDtoForFcAndService(String waybillNo,String paideMethod,
			String origOrgCode,String destOrgCode){
		WaybillPickupInfoDto	waybill=new WaybillPickupInfoDto();
		waybill.setId(UUIDUtils.getUUID());
		waybill.setWaybillNo(waybillNo);
		waybill.setDeliveryCustomerId("a7ef7627-b4de-46fd-9507-1693f8922831");//发货客户ID
		waybill.setDeliveryCustomerCode("19443");//发货客户编码
		waybill.setDeliveryCustomerName("向日葵");//发货客户名称
		waybill.setReceiveCustomerId("a7ef7627-b4de-46fd-9537-1693f8923831");//收货客户ID
		waybill.setReceiveCustomerCode("19450");//收货客户编码
		waybill.setReceiveCustomerName("李学军");//收货客户名称
		waybill.setReceiveOrgCode(origOrgCode);//出发部门编码---广州花都区花山营业部
		waybill.setProductId("C30001");//精准卡航
		waybill.setProductCode(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT);//精准卡航
		waybill.setCustomerPickupOrgCode(destOrgCode);//武汉派送部：W011306040304
		waybill.setLoadOrgCode(destOrgCode);//配载部门   武汉派送部：W011306040304
		waybill.setLastLoadOrgCode(destOrgCode);//最终配载部门 武汉派送部：W011306040304
		
		//开单付款方式
		waybill.setPaidMethod(paideMethod);
		Date date=new Date();
		waybill.setBillTime(date);//开单时间
		waybill.setPrePayAmount(BigDecimal.ZERO);//预付金额
		waybill.setToPayAmount(new BigDecimal("10000"));//到付金额为10000
		waybill.setTransportFee(new BigDecimal("10000"));//公布价运费
		waybill.setTotalFee(new BigDecimal("20000"));//总费用
		waybill.setCreateUserCode("044983");//创建操作者编码
		waybill.setCreateOrgCode("W011303070303");//创建部门编码
		waybill.setCodAmount(new BigDecimal("10000"));//代收货款10000
		waybill.setCodRate(BigDecimal.ZERO);//代收费率
		waybill.setCodFee(BigDecimal.ZERO);//代收货款手续费
		waybill.setValueAddFee(BigDecimal.ZERO);//保价声明价值
		waybill.setInsuranceRate(BigDecimal.ZERO);//保价费率
		waybill.setInsuranceFee(BigDecimal.ZERO);//保价费
		waybill.setActive(FossConstants.ACTIVE);
		waybill.setServiceFee(new BigDecimal("100"));//装卸费
		return waybill;
	}
	
	/**
	 * 开单付款方式为--为到付，且包含有代收货款和装卸费的
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-31 上午11:42:46
	 * @param waybillNo
	 * @param paideMethod
	 * @param origOrgCode
	 * @param destOrgCode
	 * @return
	 */
	public static WaybillPickupInfoDto getWaybillPickupInfoDtoForFcAndCodService(String waybillNo,String paideMethod,
			String origOrgCode,String destOrgCode){
		WaybillPickupInfoDto	waybill=new WaybillPickupInfoDto();
		waybill.setId(UUIDUtils.getUUID());
		waybill.setWaybillNo(waybillNo);
		waybill.setDeliveryCustomerId("a7ef7627-b4de-46fd-9507-1693f8922831");//发货客户ID
		waybill.setDeliveryCustomerCode("19443");//发货客户编码
		waybill.setDeliveryCustomerName("向日葵");//发货客户名称
		waybill.setReceiveCustomerId("a7ef7627-b4de-46fd-9537-1693f8923831");//收货客户ID
		waybill.setReceiveCustomerCode("19450");//收货客户编码
		waybill.setReceiveCustomerName("李学军");//收货客户名称
		waybill.setReceiveOrgCode(origOrgCode);//出发部门编码---广州花都区花山营业部
		waybill.setProductId("C30001");//精准卡航
		waybill.setProductCode(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT);//精准卡航
		waybill.setCustomerPickupOrgCode(destOrgCode);//武汉派送部：W011306040304
		waybill.setLoadOrgCode(destOrgCode);//配载部门   武汉派送部：W011306040304
		waybill.setLastLoadOrgCode(destOrgCode);//最终配载部门 武汉派送部：W011306040304
		
		//开单付款方式
		waybill.setPaidMethod(paideMethod);
		Date date=new Date();
		waybill.setBillTime(date);//开单时间
		waybill.setPrePayAmount(BigDecimal.ZERO);//预付金额
		waybill.setToPayAmount(new BigDecimal("10000"));//到付金额为10000
		waybill.setTransportFee(new BigDecimal("10000"));//公布价运费
		waybill.setTotalFee(new BigDecimal("20000"));//总费用
		waybill.setCreateUserCode("044983");//创建操作者编码
		waybill.setCreateOrgCode("W011303070303");//创建部门编码
		waybill.setCodAmount(new BigDecimal("10000"));//代收货款10000
		waybill.setCodRate(BigDecimal.ZERO);//代收费率
		waybill.setCodFee(BigDecimal.ZERO);//代收货款手续费
		waybill.setValueAddFee(BigDecimal.ZERO);//保价声明价值
		waybill.setInsuranceRate(BigDecimal.ZERO);//保价费率
		waybill.setInsuranceFee(BigDecimal.ZERO);//保价费
		waybill.setActive(FossConstants.ACTIVE);
		waybill.setServiceFee(new BigDecimal("100"));//装卸费
		return getWaybillPickupInfoDtoForCodBank(waybill);
	}
	
	
	/**
	 * 开单付款方式为--到付，且始发预付款大于0(存在现金收款单)
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-31 上午11:42:46
	 * @param waybillNo
	 * @param paideMethod
	 * @param origOrgCode
	 * @param destOrgCode
	 * @return
	 */
	public static WaybillPickupInfoDto getWaybillPickupInfoDtoForFcAndCash(String waybillNo,String paideMethod,
			String origOrgCode,String destOrgCode){
		WaybillPickupInfoDto	waybill=new WaybillPickupInfoDto();
		waybill.setId(UUIDUtils.getUUID());
		waybill.setWaybillNo(waybillNo);
		waybill.setDeliveryCustomerId("a7ef7627-b4de-46fd-9507-1693f8922831");//发货客户ID
		waybill.setDeliveryCustomerCode("19443");//发货客户编码
		waybill.setDeliveryCustomerName("向日葵");//发货客户名称
		waybill.setReceiveCustomerId("a7ef7627-b4de-46fd-9537-1693f8923831");//收货客户ID
		waybill.setReceiveCustomerCode("19450");//收货客户编码
		waybill.setReceiveCustomerName("李学军");//收货客户名称
		waybill.setReceiveOrgCode(origOrgCode);//出发部门编码---广州花都区花山营业部
		waybill.setProductId("C30001");//精准卡航
		waybill.setProductCode(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT);//精准卡航
		waybill.setCustomerPickupOrgCode(destOrgCode);//武汉派送部：W011306040304
		waybill.setLoadOrgCode(destOrgCode);//配载部门   武汉派送部：W011306040304
		waybill.setLastLoadOrgCode(destOrgCode);//最终配载部门 武汉派送部：W011306040304
		
		//开单付款方式
		waybill.setPaidMethod(paideMethod);
		Date date=new Date();
		waybill.setBillTime(date);//开单时间
		waybill.setPrePayAmount(new BigDecimal("10000"));//预付金额 10000---
		waybill.setToPayAmount(new BigDecimal("10000"));//到付金额为10000
		waybill.setTransportFee(new BigDecimal("20000"));//公布价运费20000
		waybill.setTotalFee(new BigDecimal("30000"));//总费用
		waybill.setCreateUserCode("044983");//创建操作者编码
		waybill.setCreateOrgCode("W011303070303");//创建部门编码
		waybill.setCodAmount(new BigDecimal("10000"));//代收货款10000
		waybill.setCodRate(BigDecimal.ZERO);//代收费率
		waybill.setCodFee(BigDecimal.ZERO);//代收货款手续费
		waybill.setValueAddFee(BigDecimal.ZERO);//保价声明价值
		waybill.setInsuranceRate(BigDecimal.ZERO);//保价费率
		waybill.setInsuranceFee(BigDecimal.ZERO);//保价费
		waybill.setActive(FossConstants.ACTIVE);
		waybill.setServiceFee(BigDecimal.ZERO);//装卸费
		return waybill;
	}
	
	
	/**
	 * 开单付款方式为--到付，且始发预付款大于0(存在现金收款单)，且包含有代收货款
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-31 上午11:42:46
	 * @param waybillNo
	 * @param paideMethod
	 * @param origOrgCode
	 * @param destOrgCode
	 * @return
	 */
	public static WaybillPickupInfoDto getWaybillPickupInfoDtoForFcAndCashAndCod(String waybillNo,String paideMethod,
			String origOrgCode,String destOrgCode){
		WaybillPickupInfoDto	waybill=new WaybillPickupInfoDto();
		waybill.setId(UUIDUtils.getUUID());
		waybill.setWaybillNo(waybillNo);
		waybill.setDeliveryCustomerId("a7ef7627-b4de-46fd-9507-1693f8922831");//发货客户ID
		waybill.setDeliveryCustomerCode("19443");//发货客户编码
		waybill.setDeliveryCustomerName("向日葵");//发货客户名称
		
		
		waybill.setReceiveCustomerId("a7ef7627-b4de-46fd-9537-1693f8923831");//收货客户ID
		waybill.setReceiveCustomerCode("19450");//收货客户编码
		waybill.setReceiveCustomerName("李学军");//收货客户名称
		
		waybill.setReceiveOrgCode(origOrgCode);//出发部门编码---广州花都区花山营业部
		waybill.setProductId("C30001");//精准卡航
		waybill.setProductCode(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT);//精准卡航
		
		waybill.setCustomerPickupOrgCode(destOrgCode);//武汉派送部：W011306040304
		waybill.setLoadOrgCode(destOrgCode);//配载部门   武汉派送部：W011306040304
		waybill.setLastLoadOrgCode(destOrgCode);//最终配载部门 武汉派送部：W011306040304
		
		//开单付款方式
		waybill.setPaidMethod(paideMethod);
		
		Date date=new Date();
		waybill.setBillTime(date);//开单时间
		waybill.setPrePayAmount(new BigDecimal("10000"));//预付金额 10000---
		waybill.setToPayAmount(new BigDecimal("10000"));//到付金额为10000
		waybill.setTransportFee(new BigDecimal("20000"));//公布价运费20000
		waybill.setTotalFee(new BigDecimal("30000"));//总费用
		waybill.setCreateUserCode("044983");//创建操作者编码
		waybill.setCreateOrgCode("W011303070303");//创建部门编码
		waybill.setCodAmount(new BigDecimal("10000"));//代收货款10000
		waybill.setCodRate(BigDecimal.ZERO);//代收费率
		waybill.setCodFee(BigDecimal.ZERO);//代收货款手续费
		waybill.setValueAddFee(BigDecimal.ZERO);//保价声明价值
		waybill.setInsuranceRate(BigDecimal.ZERO);//保价费率
		waybill.setInsuranceFee(BigDecimal.ZERO);//保价费
		waybill.setActive(FossConstants.ACTIVE);
		waybill.setServiceFee(BigDecimal.ZERO);//装卸费
		return getWaybillPickupInfoDtoForCodBank(waybill);
	}
	
	/**
	 * 开单付款方式为--到付，且始发预付款大于0(存在现金收款单)，且包含有装卸费
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-31 上午11:42:46
	 * @param waybillNo
	 * @param paideMethod
	 * @param origOrgCode
	 * @param destOrgCode
	 * @return
	 */
	public static WaybillPickupInfoDto getWaybillPickupInfoDtoForFcAndCashService(String waybillNo,String paideMethod,
			String origOrgCode,String destOrgCode){
		WaybillPickupInfoDto	waybill=new WaybillPickupInfoDto();
		waybill.setId(UUIDUtils.getUUID());
		waybill.setWaybillNo(waybillNo);
		waybill.setDeliveryCustomerId("a7ef7627-b4de-46fd-9507-1693f8922831");//发货客户ID
		waybill.setDeliveryCustomerCode("19443");//发货客户编码
		waybill.setDeliveryCustomerName("向日葵");//发货客户名称
		
		
		waybill.setReceiveCustomerId("a7ef7627-b4de-46fd-9537-1693f8923831");//收货客户ID
		waybill.setReceiveCustomerCode("19450");//收货客户编码
		waybill.setReceiveCustomerName("李学军");//收货客户名称
		
		waybill.setReceiveOrgCode(origOrgCode);//出发部门编码---广州花都区花山营业部
		waybill.setProductId("C30001");//精准卡航
		waybill.setProductCode(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT);//精准卡航
		
		waybill.setCustomerPickupOrgCode(destOrgCode);//武汉派送部：W011306040304
		waybill.setLoadOrgCode(destOrgCode);//配载部门   武汉派送部：W011306040304
		waybill.setLastLoadOrgCode(destOrgCode);//最终配载部门 武汉派送部：W011306040304
		
		//开单付款方式
		waybill.setPaidMethod(paideMethod);
		
		Date date=new Date();
		waybill.setBillTime(date);//开单时间
		waybill.setPrePayAmount(new BigDecimal("10000"));//预付金额 10000---
		waybill.setToPayAmount(new BigDecimal("10000"));//到付金额为10000
		waybill.setTransportFee(new BigDecimal("20000"));//公布价运费20000
		waybill.setTotalFee(new BigDecimal("30000"));//总费用
		waybill.setCreateUserCode("044983");//创建操作者编码
		waybill.setCreateOrgCode("W011303070303");//创建部门编码
		waybill.setCodAmount(new BigDecimal("10000"));//代收货款10000
		waybill.setCodRate(BigDecimal.ZERO);//代收费率
		waybill.setCodFee(BigDecimal.ZERO);//代收货款手续费
		waybill.setValueAddFee(BigDecimal.ZERO);//保价声明价值
		waybill.setInsuranceRate(BigDecimal.ZERO);//保价费率
		waybill.setInsuranceFee(BigDecimal.ZERO);//保价费
		waybill.setActive(FossConstants.ACTIVE);
		waybill.setServiceFee(new BigDecimal("100"));//装卸费
		return waybill;
	}
	
	/**
	 * 开单付款方式为--到付，且始发预付款大于0(存在现金收款单)，且包含有代收货款和装卸费的
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-31 上午11:42:46
	 * @param waybillNo
	 * @param paideMethod
	 * @param origOrgCode
	 * @param destOrgCode
	 * @return
	 */
	public static WaybillPickupInfoDto getWaybillPickupInfoDtoForFcAndCashAndCodService(String waybillNo,String paideMethod,
			String origOrgCode,String destOrgCode){
		WaybillPickupInfoDto	waybill=new WaybillPickupInfoDto();
		waybill.setId(UUIDUtils.getUUID());
		waybill.setWaybillNo(waybillNo);
		waybill.setDeliveryCustomerId("a7ef7627-b4de-46fd-9507-1693f8922831");//发货客户ID
		waybill.setDeliveryCustomerCode("19443");//发货客户编码
		waybill.setDeliveryCustomerName("向日葵");//发货客户名称
		waybill.setReceiveCustomerId("a7ef7627-b4de-46fd-9537-1693f8923831");//收货客户ID
		waybill.setReceiveCustomerCode("19450");//收货客户编码
		waybill.setReceiveCustomerName("李学军");//收货客户名称
		waybill.setReceiveOrgCode(origOrgCode);//出发部门编码---广州花都区花山营业部
		waybill.setProductId("C30001");//精准卡航
		waybill.setProductCode(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT);//精准卡航
		waybill.setCustomerPickupOrgCode(destOrgCode);//武汉派送部：W011306040304
		waybill.setLoadOrgCode(destOrgCode);//配载部门   武汉派送部：W011306040304
		waybill.setLastLoadOrgCode(destOrgCode);//最终配载部门 武汉派送部：W011306040304
		
		//开单付款方式
		waybill.setPaidMethod(paideMethod);
		Date date=new Date();
		waybill.setBillTime(date);//开单时间
		waybill.setPrePayAmount(new BigDecimal("10000"));//预付金额 10000---
		waybill.setToPayAmount(new BigDecimal("10000"));//到付金额为10000
		waybill.setTransportFee(new BigDecimal("20000"));//公布价运费20000
		waybill.setTotalFee(new BigDecimal("30000"));//总费用
		waybill.setCreateUserCode("044983");//创建操作者编码
		waybill.setCreateOrgCode("W011303070303");//创建部门编码
		waybill.setCodAmount(new BigDecimal("10000"));//代收货款10000
		waybill.setCodRate(BigDecimal.ZERO);//代收费率
		waybill.setCodFee(BigDecimal.ZERO);//代收货款手续费
		waybill.setValueAddFee(BigDecimal.ZERO);//保价声明价值
		waybill.setInsuranceRate(BigDecimal.ZERO);//保价费率
		waybill.setInsuranceFee(BigDecimal.ZERO);//保价费
		waybill.setActive(FossConstants.ACTIVE);
		waybill.setServiceFee(new BigDecimal("100"));//装卸费
		return getWaybillPickupInfoDtoForCodBank(waybill);
	}
	
	/**
	 * 获取插入运单sql语句
	 *  
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-31 上午10:30:15
	 * @param waybillNo
	 * @param waybill
	 * @return
	 */
	public static String getInsertWaybillSql(String waybillNo,WaybillPickupInfoDto waybill){
		String dateTime=DateUtils.convert(waybill.getBillTime(),DateUtils.DATE_TIME_FORMAT);
		String sql="insert into pkp.t_srv_waybill (ID, WAYBILL_NO, ORDER_NO, " +
				"DELIVERY_CUSTOMER_ID, " +//deliveryCustomerId 发货客户ID
				"DELIVERY_CUSTOMER_CODE, " +//发货客户编码
				"DELIVERY_CUSTOMER_NAME," +//发货客户名称
				" DELIVERY_CUSTOMER_MOBILEPHONE," +
				" DELIVERY_CUSTOMER_PHONE, " +
				"DELIVERY_CUSTOMER_CONTACT," +
				" DELIVERY_CUSTOMER_NATION_CODE, " +
				"DELIVERY_CUSTOMER_PROV_CODE, " +
				"DELIVERY_CUSTOMER_CITY_CODE, " +
				"DELIVERY_CUSTOMER_DIST_CODE, " +
				"DELIVERY_CUSTOMER_ADDRESS," +
				" RECEIVE_CUSTOMER_ID, " +
				"RECEIVE_CUSTOMER_CODE," +
				" RECEIVE_CUSTOMER_NAME, " +
				"RECEIVE_CUSTOMER_MOBILEPHONE, " +
				"RECEIVE_CUSTOMER_PHONE," +
				" RECEIVE_CUSTOMER_CONTACT," +
				" RECEIVE_CUSTOMER_NATION_CODE, " +
				"RECEIVE_CUSTOMER_PROV_CODE, " +
				"RECEIVE_CUSTOMER_CITY_CODE, " +
				"RECEIVE_CUSTOMER_DIST_CODE, " +
				"RECEIVE_CUSTOMER_ADDRESS, " +
				"RECEIVE_ORG_CODE, " + //出发部门编码
				"PRODUCT_ID," + //产品ID
				" PRODUCT_CODE, " +//产品编码
				"RECEIVE_METHOD, " +
				"CUSTOMER_PICKUP_ORG_CODE, " +//提货网点
				"LOAD_METHOD," +
				" TARGET_ORG_CODE," +//目的站
				" PICKUP_TO_DOOR, DRIVER_CODE, PICKUP_CENTRALIZED, LOAD_LINE_CODE, LOAD_ORG_CODE, " +
				"LAST_LOAD_ORG_CODE, " +//最终配载部门
				"PRE_DEPARTURE_TIME, PRE_CUSTOMER_PICKUP_TIME," +
				" CAR_DIRECT_DELIVERY, " +
				"GOODS_NAME, " +
				"GOODS_QTY_TOTAL, " +
				"GOODS_WEIGHT_TOTAL, GOODS_VOLUME_TOTAL, GOODS_SIZE, GOODS_TYPE_CODE, PRECIOUS_GOODS, SPECIAL_SHAPED_GOODS, " +
				"OUTER_NOTES, INNER_NOTES," +
				" GOODS_PACKAGE, " +//货物包装
				"INSURANCE_AMOUNT," +//保价声明价值
				" COD_AMOUNT, " +//代收货款
				"COD_RATE, " +//代收费率
				"COD_FEE, " +//代收货款手续费
				"REFUND_TYPE, " +//退款类型
				"RETURN_BILL_TYPE, SECRET_PREPAID, " +
				"TO_PAY_AMOUNT, " +//到付金额
				"PRE_PAY_AMOUNT, " +//预付金额
				"DELIVERY_GOODS_FEE, " +//送货费
				"OTHER_FEE," +//其他费用
				" PACKAGE_FEE, " +//包装手续费
				"PROMOTIONS_FEE, " +//优惠费用
				"BILLING_TYPE, " +//运费计费类型
				"TRANSPORT_FEE, " +//公布价运费
				"VALUE_ADD_FEE, " +//增值费用
				"PAID_METHOD, " +//付款方式
				"ARRIVE_TYPE, ACTIVE, FORBIDDEN_LINE," +
				" FREIGHT_METHOD, FLIGHT_SHIFT, " +
				"TOTAL_FEE," +//总费用
				" PROMOTIONS_CODE, CREATE_TIME, MODIFY_TIME, BILL_TIME, " +
				"CREATE_USER_CODE, " +
				"MODIFY_USER_CODE, " +
				"CREATE_ORG_CODE, " +
				"MODIFY_ORG_CODE, " +
				"REF_ID, REF_CODE, CURRENCY_CODE, IS_WHOLE_VEHICLE, " +
				"WHOLE_VEHICLE_APPFEE, WHOLE_VEHICLE_ACTUALFEE, ACCOUNT_NAME, ACCOUNT_CODE, ACCOUNT_BANK, BILL_WEIGHT, " +
				"PICKUP_FEE, " +
				"SERVICE_FEE, " +//装卸费
				"PRE_ARRIVE_TIME, TRANSPORT_TYPE, ADD_TIME, INSURANCE_RATE, " +
				"INSURANCE_FEE, " +//报价费
				"UNIT_PRICE, CONTACT_ADDRESS_ID, FLIGHT_NUMBER_TYPE, COLLECTION_DEPARTMENT, " +
				"TRANSPORTATION_REMARK, IS_PASS_OWN_DEPARTMENT, PAPER_NUM, WOOD_NUM, FIBRE_NUM," +
				" SALVER_NUM, MEMBRANE_NUM, OTHER_PACKAGE, DELIVERY_CUSTOMER_CONTACT_ID, " +
				"RECEIVE_CUSTOMER_CONTACT_ID, PENDING_TYPE, LICENSE_PLATE_NUM, ORDER_VEHICLE_NUM)"+
				"values ('"+UUIDUtils.getUUID()+"', " +
				"'"+waybill.getWaybillNo()+"'," +
				" ''," +
    			" '"+waybill.getDeliveryCustomerId()+"'," +//发货客户ID
    			" '"+waybill.getDeliveryCustomerCode()+"'," +//发货客户编码
    		    " '"+waybill.getDeliveryCustomerName()+"'," +//发货客户名称
    			" '13817266332', '02156565656', '鹏哥', '', '', '', '', '', " +
    			" '"+waybill.getReceiveCustomerId()+"' , " + //收货客户ID
    			"  '"+waybill.getReceiveCustomerCode()+"', " +//收货客户编码
    			"'"+waybill.getReceiveCustomerName()+"', " +//收货客户名称
    			"'13235656788', '', '张士更', '', '', '', '', '', " +
    			"'"+waybill.getReceiveOrgCode()+"', " +//出发部门编码
    			"'"+waybill.getProductId()+"'," +//产品id
    			" '"+waybill.getProductCode()+"', " +//产品编码
					"'SELF_PICKUP_AIR'," +
					" '"+waybill.getCustomerPickupOrgCode()+"'," +//提货网点
					" '', '武汉'," +//目的站
					" 'N', '', 'N', '5fc6caa1-16b3-4d60-8128-516c0fe7d065'," +
					" '"+waybill.getLoadOrgCode()+"', " +//配载部门
					"'"+waybill.getLastLoadOrgCode()+"', " +//最终配载部门
					"null, to_date('"+dateTime+"', 'yyyy-MM-dd hh24:mi:ss')," +
							" 'N', " +
							"'键盘', " +
							"11, 121.000, 1.320, '55*66*33*11'," +
							" 'NORMAL', 'N', 'N', '', '', '11纸', " +
							"0," +//报价声明费
							" "+waybill.getCodAmount()+"*100," +//代收货款
							" 0, " +//代收费率
							"0, " +//代收货款手续费
							"'', " +//代收货款退款类型
							"'NONE'," +
							" 'N'," +waybill.getToPayAmount()+"*100, " +//到付金额
							(waybill.getPrePayAmount())+"*100, " +//预付金额
							"0," +//送货费
							" 0, " +//其他费用
							"0," +//包装手续费
							" 0, " +//优惠费用
							"'WEIGHT', " +
							waybill.getTransportFee()+"*100," +//公布价运费
							waybill.getValueAddFee()+"*100," +//增值费用
							" '"+waybill.getPaidMethod()+"'," +//付款方式
							" '', " +
							"'"+waybill.getActive()+"'," +//是否有效
							" 'Y', 'HDP', ''," +
							" "+waybill.getTotalFee()+"*100, " +//总费用
							"'', to_date('"+dateTime+"', 'yyyy-MM-dd hh24:mi:ss'), " +//创建时间
							"to_date('"+dateTime+"', 'yyyy-MM-dd hh24:mi:ss')," +//修改时间
							" to_date('"+dateTime+"', 'yyyy-MM-dd hh24:mi:ss')," +//开单时间
							" '"+waybill.getCreateUserCode()+"'," +//创建人编码
							" '"+waybill.getCreateUserCode()+"'," +//修改人编码
							" '"+waybill.getReceiveOrgCode()+"', " +//创建部门编码
							"'"+waybill.getReceiveOrgCode()+"'," +//修改部门编码
							" '', '', 'RMB', 'N', 0, 0, '', '', '', 220.000, 0, " +waybill.getServiceFee()+
							"*100, " +
							"to_date('"+dateTime+"', 'yyyy-MM-dd hh24:mi:ss'), 'TRANS_AIRCRAFT', to_date('"+dateTime+"', 'yyyy-MM-dd hh24:mi:ss'), " +
							"0.0040, " +
							""+waybill.getInsuranceFee() //保价费
							+"*100," +"5.30, '', '', '', '', '', 11, 0, 0, 0, 0, '', '', '', 'PC_ACTIVE', '', '')  ";
		return sql;
	}
	

/*********************************************获取（反）到付转临欠/月结，（反）实收货款的接口参数信息*****************/
	
	/**
	 * 获取(反)到付转临欠/月结接口参数
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-1 下午2:42:30
	 * @param waybillNo
	 * @param paymentType
	 * @param destOrgCode
	 * @param destOrgName
	 * @return
	 */
	public static PaymentSettlementDto getPaymentSettlementDtoToConfirmRece
	(String waybillNo,String paymentType,String destOrgCode,String destOrgName){
		PaymentSettlementDto dto=new PaymentSettlementDto();
		dto.setWaybillNo(waybillNo);//运单号
		dto.setPaymentType(paymentType);//付款方式
		dto.setDestOrgCode(destOrgCode);//到达部门编码
		dto.setDestOrgName(destOrgName);//到达部门名称
		dto.setCustomerCode("19450");//到达客户编码
		dto.setCustomerName("李学军");//到达客户名称
		dto.setBusinessDate(new Date());
		return dto;
	}
	
	/**
	 * 获取（反）实收货款接口参数
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-1 下午3:04:15
	 * @param waybillNo
	 * @param paymentType
	 * @param destOrgCode
	 * @param destOrgName
	 * @param codFee
	 * @param toPayFee
	 * @param paymentNo
	 * @param sourceBillNo
	 * @return
	 */
	public static PaymentSettlementDto getPaymentSettlementDto(String waybillNo,
			String paymentType,String destOrgCode,String destOrgName,
			BigDecimal codFee,BigDecimal toPayFee,String paymentNo,
			String sourceBillNo){
		PaymentSettlementDto dto=new PaymentSettlementDto();
		dto.setWaybillNo(waybillNo);//运单号
		dto.setPaymentType(paymentType);//付款方式
		dto.setDestOrgCode(destOrgCode);//到达部门编码
		dto.setDestOrgName(destOrgName);//到达部门名称
		dto.setBusinessDate(new Date());
		dto.setCodFee(codFee);
		dto.setToPayFee(toPayFee);
		dto.setPaymentNo(paymentNo);
		dto.setSourceBillNo(sourceBillNo);
		dto.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);//货币
		return dto;
	}
	
	/**
	 * 获取运单号
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-31 上午9:43:18
	 * @return
	 */
	public static String getWaybillNO() {
		return new Date().getTime()+"" ;
	}
	
	/**
	 * 获取始发开单操作者信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-29 下午7:17:06
	 * @return
	 */
	public static CurrentInfo getOrigCurrentInfo() {
		UserEntity user = new UserEntity();
		EmployeeEntity employee = new EmployeeEntity();
		employee.setEmpCode("044983");
		employee.setEmpName("王军");
		user.setEmployee(employee);
		user.setUserName("王军");

		OrgAdministrativeInfoEntity dept = new OrgAdministrativeInfoEntity();
		dept.setCode("W011303070303");
		dept.setName("广州花都区花山营业部");

		CurrentInfo currentInfo = new CurrentInfo(user, dept);
		return currentInfo;
	}
	
	/**
	 * 获取操作者信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-29 下午7:17:48
	 * @return
	 */
	public static CurrentInfo getCurrentInfo(String empCode,String empName,String destOrgCode,String destOrgName) {
		UserEntity user = new UserEntity();
		EmployeeEntity employee = new EmployeeEntity();
		employee.setEmpCode(empCode);
		employee.setEmpName(empName);
		user.setEmployee(employee);
		user.setUserName(empName);

		OrgAdministrativeInfoEntity dept = new OrgAdministrativeInfoEntity();
		dept.setCode(destOrgCode);
		dept.setName(destOrgName);
		CurrentInfo currentInfo = new CurrentInfo(user, dept);
		return currentInfo;
	}
	
	/**
	 * 获取签收dto
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-30 下午4:08:48
	 * @param dto
	 * @param waybillNo
	 * @param destOrgCode
	 * @param destOrgName
	 * @param signType
	 * @param isWholeVehicle
	 * @param isPdaSign
	 * @return
	 */
	public static LineSignDto getLineSignDto(LineSignDto dto,String waybillNo,String destOrgCode,String destOrgName,
			String signType,String isWholeVehicle,String isPdaSign
			){
		if(dto==null){
			dto=new LineSignDto();
		}
		dto.setWaybillNo(waybillNo);//运单号
		dto.setSignType(signType);//签收类型
		dto.setSignOrgCode(destOrgCode);//签收部门编码
		dto.setSignOrgName(destOrgName);//签收部门名称
		dto.setSignDate(new Date());
		dto.setIsWholeVehicle(isWholeVehicle);//是否整车运单
		dto.setIsPdaSign(isPdaSign);//是否PDA签收
		return dto;
	}
	
	
	

}

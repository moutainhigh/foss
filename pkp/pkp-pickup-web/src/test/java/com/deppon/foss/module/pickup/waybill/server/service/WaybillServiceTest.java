/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-pickup-web
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/pickup/waybill/server/service/WaybillServiceTest.java
 * 
 * FILE NAME        	: WaybillServiceTest.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.server.service;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.server.service.impl.AdministrativeRegionsService;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.order.api.server.dao.IDispatchOrderEntityDao;
import com.deppon.foss.module.pickup.order.api.server.dao.IEWaybillOrderEntityDao;
import com.deppon.foss.module.pickup.order.api.shared.define.DispatchOrderStatusConstants;
import com.deppon.foss.module.pickup.order.api.shared.domain.DispatchOrderEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.EWaybillOrderEntity;
import com.deppon.foss.module.pickup.order.server.dao.impl.DispatchOrderEntityDao;
import com.deppon.foss.module.pickup.order.server.dao.impl.EWaybillOrderEntityDao;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.server.service.impl.ProductService;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IActualFreightDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IPdaScanDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IPendingTodoDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillPendingDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillRfcBatchChangeDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IGisQueryService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ILabeledGoodService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillAcHisPdaService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillExpressService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillPendingService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcTodoJobService;
import com.deppon.foss.module.pickup.waybill.server.common.TestHelper;
import com.deppon.foss.module.pickup.waybill.server.dao.impl.ActualFreightDao;
import com.deppon.foss.module.pickup.waybill.server.dao.impl.PdaScanDao;
import com.deppon.foss.module.pickup.waybill.server.dao.impl.PendingTodoDao;
import com.deppon.foss.module.pickup.waybill.server.dao.impl.WaybillDao;
import com.deppon.foss.module.pickup.waybill.server.dao.impl.WaybillPendingDao;
import com.deppon.foss.module.pickup.waybill.server.dao.impl.WaybillRfcBatchChangeDao;
import com.deppon.foss.module.pickup.waybill.server.service.impl.LabeledGoodService;
import com.deppon.foss.module.pickup.waybill.server.service.impl.WaybillAcHisPdaService;
import com.deppon.foss.module.pickup.waybill.server.service.impl.WaybillExpressService;
import com.deppon.foss.module.pickup.waybill.server.service.impl.WaybillManagerService;
import com.deppon.foss.module.pickup.waybill.server.service.impl.WaybillPendingService;
import com.deppon.foss.module.pickup.waybill.server.service.impl.WaybillRfcTodoJobService;
import com.deppon.foss.module.pickup.waybill.server.service.impl.WaybillStockService;
import com.deppon.foss.module.pickup.waybill.shared.define.ExpWaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillRfcConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodPDAEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodTodoEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.PdaScanEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.PendingTodoEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillCHDtlPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillChargeDtlEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillDisDtlEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillDisDtlPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPaymentEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPaymentPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcBatchChangeEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WoodenRequirePendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WoodenRequirementsEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.ClientEWaybillConditionDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.EWaybillConditionDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.EWaybillSalesDepartDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.EffectiveDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.PdaScanQueryDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.QueryPickupPointDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillOtherChargeDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillPendingDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillSystemDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

public class WaybillServiceTest {

	private IWaybillManagerService waybillManagerService;

	private IWaybillStockService waybillStockService;
	
	private IWaybillPendingService waybillPendingService;
	
	private IWaybillAcHisPdaService waybillAcHisPdaService;
	
	private IGisQueryService gisQueryService;
	
	private ILabeledGoodService labeledGoodService;
	private IPendingTodoDao pendingTodoDao;
	
	private IWaybillRfcTodoJobService waybillRfcTodoJobService;
	
	private IProductService productService;
	
	private IPdaScanDao pdaScanDao;
	
	private IWaybillPendingDao waybillPendingDao;
	
	private IWaybillDao waybillDao;
	
	
	private IEWaybillOrderEntityDao ewaybillOrderEntityDao;
	
	private IDispatchOrderEntityDao dispatchOrderEntityDao;
	
	private IActualFreightDao actualFreightDao;
	
	private IWaybillRfcBatchChangeDao waybillRfcBatchChangeDao;
	
	private IAdministrativeRegionsService administrativeRegionsService;
	
	private IWaybillExpressService waybillExpressService;
	

	private String waybillNo = Math.round(Math.random() * 1000000000) + "";
	

	@Before
	public void setUp() throws Exception {
		waybillManagerService = TestHelper.get().getBeanByClass(WaybillManagerService.class);
		waybillStockService = TestHelper.get().getBeanByClass(WaybillStockService.class);
		waybillPendingService = TestHelper.get().getBeanByClass(WaybillPendingService.class);
		waybillAcHisPdaService = TestHelper.get().getBeanByClass(WaybillAcHisPdaService.class);
		labeledGoodService = TestHelper.get().getBeanByClass(LabeledGoodService.class);
		pendingTodoDao = TestHelper.get().getBeanByClass(PendingTodoDao.class);
		waybillRfcTodoJobService = TestHelper.get().getBeanByClass(WaybillRfcTodoJobService.class);
		productService = TestHelper.get().getBeanByClass(ProductService.class);
		pdaScanDao = TestHelper.get().getBeanByClass(PdaScanDao.class);
		waybillPendingDao = TestHelper.get().getBeanByClass(WaybillPendingDao.class);
		waybillDao = TestHelper.get().getBeanByClass(WaybillDao.class);
		ewaybillOrderEntityDao = TestHelper.get().getBeanByClass(EWaybillOrderEntityDao.class);
		dispatchOrderEntityDao = TestHelper.get().getBeanByClass(DispatchOrderEntityDao.class);
		actualFreightDao = TestHelper.get().getBeanByClass(ActualFreightDao.class);
		waybillRfcBatchChangeDao = TestHelper.get().getBeanByClass(WaybillRfcBatchChangeDao.class);
		administrativeRegionsService = TestHelper.get().getBeanByClass(AdministrativeRegionsService.class);
		waybillExpressService = TestHelper.get().getBeanByClass(WaybillExpressService.class);
	}

	// @Test
	public void testSubmitWaybill() {
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@" + waybillNo);
		WaybillDto waybillDto = new WaybillDto();
		WaybillEntity entity = new WaybillEntity();
		entity.setWaybillNo(waybillNo);
		entity.setDeliveryCustomerName("Name:" + Math.round(Math.random() * 1000));
		entity.setDeliveryCustomerMobilephone("139" + Math.round(Math.random() * 100000000));
		entity.setDeliveryCustomerPhone("021-" + Math.round(Math.random() * 1000000));
		entity.setDeliveryCustomerContact("码农:" + Math.round(Math.random() * 1000) + "号");
		entity.setReceiveCustomerId(UUIDUtils.getUUID());
		entity.setReceiveCustomerCode("客户编码：" + Math.round(Math.random() * 1000000));
		entity.setReceiveCustomerName("收货客户：" + Math.round(Math.random() * 1000000));
		entity.setReceiveCustomerMobilephone("158" + Math.round(Math.random() * 100000000));
		entity.setReceiveCustomerPhone("010-" + Math.round(Math.random() * 1000000));
		entity.setReceiveCustomerContact("码农:" + Math.round(Math.random() * 1000) + "号");
		entity.setReceiveCustomerNationCode(Math.round(Math.random() * 100) + "国 ");
		entity.setReceiveCustomerProvCode(Math.round(Math.random() * 10) + "省");
		entity.setReceiveCustomerCityCode(Math.round(Math.random() * 100) + "市");
		entity.setReceiveCustomerAddress("地址：" + Math.round(Math.random() * 1000));
		entity.setReceiveOrgCode("GS00002");
		entity.setProductId(UUIDUtils.getUUID());
		entity.setProductCode("C30001");
		entity.setReceiveMethod("提货方式：" + Math.round(Math.random() * 100));
		entity.setPickupToDoor(FossConstants.YES);
		entity.setPickupCentralized(FossConstants.NO);
		entity.setLoadOrgCode("W040002060401");
		entity.setLastLoadOrgCode("W06071401");
		entity.setPreCustomerPickupTime(new Date());
		entity.setCarDirectDelivery(FossConstants.NO);
		entity.setGoodsName("GoodsName " + Math.round(Math.random() * 1000));
		entity.setGoodsQtyTotal(Integer.valueOf(Math.round(Math.random() * 100) + ""));
		entity.setGoodsTypeCode("A" + Integer.valueOf(Math.round(Math.random() * 10) + ""));
		entity.setPreciousGoods(FossConstants.NO);
		entity.setSpecialShapedGoods(FossConstants.NO);
		entity.setSecretPrepaid(FossConstants.YES);
		entity.setActive(FossConstants.ACTIVE);
		entity.setForbiddenLine(FossConstants.NO);
		// TODO 总运费
		BigDecimal totalFee = BigDecimal.valueOf(Math.round(Math.random() * 1000000));
		entity.setTotalFee(totalFee);
		entity.setCreateTime(new Date());
		entity.setBillTime(new Date());
		entity.setCreateUserCode("李凤腾");
		entity.setCreateOrgCode("GS00002");
		entity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		entity.setIsWholeVehicle(FossConstants.NO);
		entity.setWholeVehicleAppfee(BigDecimal.valueOf(Math.round(Math.random() * 1000), 2));
		entity.setBillWeight(BigDecimal.valueOf(Math.round(Math.random() * 1000), 2));
		// TODO
		BigDecimal pickupFee = BigDecimal.valueOf(Math.round(Math.random() * 100));
		entity.setPickupFee(pickupFee);
		entity.setServiceFee(BigDecimal.valueOf(Math.random() * 1000));
		entity.setPreArriveTime(new Date());
		entity.setReceiveCustomerDistCode(" " + Integer.valueOf(Math.round(Math.random() * 1000) + ""));
		entity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);

		/**
		 * 可空字段
		 */
		entity.setOrderNo("DD" + Math.round(Math.random() * 100));
		entity.setDeliveryCustomerId(UUIDUtils.getUUID());
		entity.setDeliveryCustomerName(Math.round(Math.random() * 100) + "国 ");
		entity.setDeliveryCustomerProvCode(Math.round(Math.random() * 10) + "省");
		entity.setDeliveryCustomerCityCode(Math.round(Math.random() * 100) + "市");
		entity.setDeliveryCustomerAddress("地址：" + Math.round(Math.random() * 1000));
		entity.setCustomerPickupOrgCode("CPOC：" + Math.round(Math.random() * 100));
		entity.setLoadMethod("配载类型：" + Math.round(Math.random() * 10));
		entity.setTargetOrgCode("TOC：" + Math.round(Math.random() * 10));
		entity.setDriverCode("DC：" + Math.round(Math.random() * 1000));
		entity.setLoadLineCode(" " + Math.round(Math.random() * 10));
		entity.setPreDepartureTime(new Date());
		entity.setGoodsWeightTotal(BigDecimal.valueOf(Math.round(Math.random() * 100), 2));
		entity.setGoodsVolumeTotal(BigDecimal.valueOf(Math.round(Math.random() * 100), 2));
		entity.setGoodsSize("2000*10*111*1");
		entity.setOuterNotes("易碎品，勿压！");
		entity.setInnerNotes("该货请帮忙配载下！");
		entity.setGoodsPackage("1纸1木");
		entity.setInsuranceAmount(BigDecimal.valueOf(Math.random() * 100));
		entity.setInsuranceRate(BigDecimal.valueOf(Math.round(Math.random() * 100), 2));
		// TODO
		BigDecimal insuranceFee = BigDecimal.valueOf(Math.round(Math.random() * 100));
		entity.setInsuranceFee(insuranceFee);
		// TODO
		BigDecimal toPayAmount = BigDecimal.valueOf(Math.round(Math.random() * 1000));
		entity.setToPayAmount(toPayAmount);
		// TODO
		BigDecimal prePayAmount = totalFee.subtract(toPayAmount);
		entity.setPrePayAmount(prePayAmount);
		// TODO 代收货款
		BigDecimal codAmount = totalFee.subtract(prePayAmount).subtract(toPayAmount);
		entity.setCodAmount(codAmount);
		entity.setCodRate(BigDecimal.valueOf(Math.round(Math.random() * 10), 2));
		// TODO
		BigDecimal codFee = BigDecimal.valueOf(Math.round(Math.random() * 100));
		entity.setCodFee(codFee);
		entity.setRefundType(" " + Integer.valueOf(Math.round(Math.random() * 10) + ""));
		// TODO
		BigDecimal deliveryGoodsFee = BigDecimal.valueOf(Math.round(Math.random() * 100));
		entity.setDeliveryGoodsFee(deliveryGoodsFee);
		// TODO
		BigDecimal otherFee = BigDecimal.valueOf(Math.round(Math.random() * 100));
		entity.setOtherFee(otherFee);
		// TODO
		BigDecimal packageFee = BigDecimal.valueOf(Math.round(Math.random() * 100));
		entity.setPackageFee(packageFee);
		// TODO
		BigDecimal promotionsFee = BigDecimal.valueOf(Math.round(Math.random() * 100));
		entity.setPromotionsFee(promotionsFee);
		entity.setBillingType(" " + Integer.valueOf(Math.round(Math.random() * 10) + ""));
		entity.setUnitPrice(BigDecimal.valueOf(Math.round(Math.random() * 10000), 2));
		// TODO
		BigDecimal transport = totalFee.subtract(promotionsFee.negate()).subtract(packageFee).subtract(otherFee).subtract(deliveryGoodsFee)
				.subtract(codAmount).subtract(insuranceFee).subtract(pickupFee).subtract(codFee);
		entity.setTransportFee(transport);
		// TODO
		BigDecimal valueAddFee = pickupFee.add(deliveryGoodsFee).add(packageFee).add(codFee).add(otherFee).add(insuranceFee);
		entity.setValueAddFee(valueAddFee);
		entity.setPaidMethod(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH);
		entity.setArriveType(" " + Integer.valueOf(Math.round(Math.random() * 10) + ""));
		entity.setFreightMethod(" " + Integer.valueOf(Math.round(Math.random() * 10) + ""));
		entity.setFlightShift(new Date() + "");
		entity.setPromotionsCode("优惠编码：" + Integer.valueOf(Math.round(Math.random() * 1000) + ""));
		entity.setModifyTime(new Date());
		entity.setModifyUserCode("李凤腾");
		entity.setModifyOrgCode(" " + Integer.valueOf(Math.round(Math.random() * 1000) + ""));
		entity.setRefId(UUIDUtils.getUUID());
		entity.setRefCode(Math.round(Math.random() * 10) + "");
		entity.setWholeVehicleActualfee(BigDecimal.valueOf(Math.random() * 1000));
		entity.setAccountName(" " + Integer.valueOf(Math.round(Math.random() * 1000) + ""));
		entity.setAccountCode("ICBC:" + Integer.valueOf(Math.round(Math.random() * 1000) + ""));
		entity.setAccountBank("ICBC:" + Integer.valueOf(Math.round(Math.random() * 1000) + ""));
		entity.setTransportType(" " + Integer.valueOf(Math.round(Math.random() * 10) + ""));
		entity.setPrintTimes(Integer.valueOf(Math.round(Math.random() * 10) + ""));
		entity.setAddTime(new Date());
		//entity.setIsPdaBill(FossConstants.YES);
		entity.setDeliveryCustomerDistCode(" " + Integer.valueOf(Math.round(Math.random() * 100) + ""));
		entity.setDeliveryCustomerNationCode("DeliveryCustomerNationCode:" + Integer.valueOf(Math.round(Math.random() * 1000) + ""));
		entity.setReturnBillType("returnBillType:" + Integer.valueOf(Math.round(Math.random() * 10) + ""));
		entity.setContactAddressId(UUIDUtils.getUUID());
		// ======================WaybillChargeDtlEntity=============================

		List<WaybillChargeDtlEntity> chargeDtlEntityList = new ArrayList<WaybillChargeDtlEntity>();
		WaybillChargeDtlEntity chargeDtlEntity1 = new WaybillChargeDtlEntity();
		chargeDtlEntity1.setPricingEntryCode("pricingEntryCode" + Math.random());
		chargeDtlEntity1.setAmount(BigDecimal.valueOf(Math.random() * 1000));
		chargeDtlEntity1.setWaybillNo(waybillNo);
		chargeDtlEntity1.setPricingCriDetailId(UUIDUtils.getUUID());
		chargeDtlEntity1.setCreateTime(new Date());
		chargeDtlEntity1.setModifyTime(new Date());
		chargeDtlEntity1.setActive(FossConstants.ACTIVE);
		chargeDtlEntity1.setBillTime(new Date());
		chargeDtlEntity1.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		chargeDtlEntityList.add(chargeDtlEntity1);

		WaybillChargeDtlEntity chargeDtlEntity2 = new WaybillChargeDtlEntity();
		chargeDtlEntity2.setPricingEntryCode("pricingEntryCode" + Math.random());
		chargeDtlEntity2.setAmount(BigDecimal.valueOf(Math.random() * 1000));
		chargeDtlEntity2.setWaybillNo(waybillNo);
		chargeDtlEntity2.setPricingCriDetailId(UUIDUtils.getUUID());
		chargeDtlEntity2.setCreateTime(new Date());
		chargeDtlEntity2.setModifyTime(new Date());
		chargeDtlEntity2.setActive(FossConstants.ACTIVE);
		chargeDtlEntity2.setBillTime(new Date());
		chargeDtlEntity2.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		chargeDtlEntityList.add(chargeDtlEntity2);

		// =========================WaybillDisDtlEntity========================
		List<WaybillDisDtlEntity> disDtlEntityList = new ArrayList<WaybillDisDtlEntity>();

		WaybillDisDtlEntity disDtlEntity1 = new WaybillDisDtlEntity();
		disDtlEntity1.setPricingEntryCode("pricingEntryCode" + Math.random());
		disDtlEntity1.setType("优惠类型" + Math.random());
		disDtlEntity1.setRate(BigDecimal.valueOf(Math.random() * 10));
		disDtlEntity1.setAmount(BigDecimal.valueOf(Math.random() * 1000));
		disDtlEntity1.setWaybillNo(waybillNo);
		disDtlEntity1.setDicountId(UUIDUtils.getUUID());
		disDtlEntity1.setWaybillChargeDetailId(UUIDUtils.getUUID());
		disDtlEntity1.setCreateTime(new Date());
		disDtlEntity1.setModifyTime(new Date());
		disDtlEntity1.setActive(FossConstants.ACTIVE);
		disDtlEntity1.setBillTime(new Date());
		disDtlEntity1.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		disDtlEntityList.add(disDtlEntity1);

		WaybillDisDtlEntity disDtlEntity2 = new WaybillDisDtlEntity();
		disDtlEntity2.setPricingEntryCode("pricingEntryCode" + Math.random());
		disDtlEntity2.setType("优惠类型" + Math.random());
		disDtlEntity2.setRate(BigDecimal.valueOf(Math.random() * 10));
		disDtlEntity2.setAmount(BigDecimal.valueOf(Math.random() * 1000));
		disDtlEntity2.setWaybillNo(waybillNo);
		disDtlEntity2.setDicountId(UUIDUtils.getUUID());
		disDtlEntity2.setWaybillChargeDetailId(UUIDUtils.getUUID());
		disDtlEntity2.setCreateTime(new Date());
		disDtlEntity2.setModifyTime(new Date());
		disDtlEntity2.setActive(FossConstants.ACTIVE);
		disDtlEntity2.setBillTime(new Date());
		disDtlEntity2.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		disDtlEntityList.add(disDtlEntity2);

		// =================WoodenRequirementsEntity========================
		WoodenRequirementsEntity woodenRequirementsEntity = new WoodenRequirementsEntity();
		woodenRequirementsEntity.setWaybillNo(waybillNo);
		woodenRequirementsEntity.setPackageOrgCode(Math.random() * 10000 + "");
		woodenRequirementsEntity.setStandGoodsNum(Integer.valueOf(Math.round(Math.random() * 100) + ""));
		woodenRequirementsEntity.setStandRequirement("要求");
		woodenRequirementsEntity.setStandGoodsSize("200*100*100*1");
		woodenRequirementsEntity.setStandGoodsVolume(BigDecimal.valueOf(Math.random()));
		woodenRequirementsEntity.setBoxGoodsNum(Integer.valueOf(Math.round(Math.random() * 100) + ""));
		woodenRequirementsEntity.setBoxRequirement("boxRequirement");
		woodenRequirementsEntity.setBoxGoodsSize("boxGoodsSize:200*100*100*1");
		woodenRequirementsEntity.setBoxGoodsVolume(BigDecimal.valueOf(Math.random()));
		woodenRequirementsEntity.setActive(FossConstants.ACTIVE);
		woodenRequirementsEntity.setCreateTime(new Date());
		woodenRequirementsEntity.setModifyTime(new Date());

		// ======================waybillPaymentEntity=====================
		List<WaybillPaymentEntity> waybillPaymentEntityList = new ArrayList<WaybillPaymentEntity>();

		WaybillPaymentEntity waybillPaymentEntity1 = new WaybillPaymentEntity();
		waybillPaymentEntity1.setWaybillNo(waybillNo);
		waybillPaymentEntity1.setType("付款类型" + Math.random());
		waybillPaymentEntity1.setAmount(BigDecimal.valueOf(Math.random() * 100));
		waybillPaymentEntity1.setPaymentTime(new Date());
		waybillPaymentEntity1.setCreateTime(new Date());
		waybillPaymentEntity1.setModifyTime(new Date());
		waybillPaymentEntity1.setActive(FossConstants.ACTIVE);
		waybillPaymentEntity1.setBillTime(new Date());
		waybillPaymentEntity1.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		waybillPaymentEntityList.add(waybillPaymentEntity1);

		WaybillPaymentEntity waybillPaymentEntity2 = new WaybillPaymentEntity();
		waybillPaymentEntity2.setWaybillNo(waybillNo);
		waybillPaymentEntity2.setType("付款类型" + Math.random());
		waybillPaymentEntity2.setAmount(BigDecimal.valueOf(Math.random() * 100));
		waybillPaymentEntity2.setPaymentTime(new Date());
		waybillPaymentEntity2.setCreateTime(new Date());
		waybillPaymentEntity2.setModifyTime(new Date());
		waybillPaymentEntity2.setActive(FossConstants.ACTIVE);
		waybillPaymentEntity2.setBillTime(new Date());
		waybillPaymentEntity2.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		waybillPaymentEntityList.add(waybillPaymentEntity2);

		EmployeeEntity employeeEntity = new EmployeeEntity();
		employeeEntity.setEmpCode("" + BigDecimal.valueOf(Math.round(Math.random() * 100000)));
		employeeEntity.setEmpName("营业部" + BigDecimal.valueOf(Math.round(Math.random() * 100)));

		OrgAdministrativeInfoEntity dept = new OrgAdministrativeInfoEntity();
		dept.setCode("" + BigDecimal.valueOf(Math.round(Math.random() * 100000)));
		dept.setName("上海事业部" + BigDecimal.valueOf(Math.round(Math.random() * 10)));

		UserEntity user = new UserEntity();
		user.setEmployee(employeeEntity);
		user.setUserName("name:" + BigDecimal.valueOf(Math.round(Math.random() * 100000)));
		user.setActive(FossConstants.ACTIVE);

		CurrentInfo currentInfo = new CurrentInfo(user, dept);
		waybillDto.setCurrentInfo(currentInfo);
		waybillDto.setWaybillEntity(entity);
		waybillDto.setWaybillChargeDtlEntity(chargeDtlEntityList);
		waybillDto.setWaybillDisDtlEntity(disDtlEntityList);
		waybillDto.setWaybillPaymentEntity(waybillPaymentEntityList);
		waybillDto.setWoodenRequirementsEntity(woodenRequirementsEntity);
		waybillManagerService.submitWaybill(waybillDto);
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@" + waybillNo);
	}

	/**
	 * 测试 库存
	 */
	// @Test
	public void testWaybillStockService() {

		//String waybillNo = "165795575";
		List<String> serialNos = new ArrayList<String>();
		serialNos.add("0001");
		serialNos.add("0002");
		serialNos.add("0003");
		serialNos.add("0004");
		serialNos.add("0005");
		serialNos.add("0006");
		UserEntity userEntity = new UserEntity();
		userEntity.setEmpCode("000000");
		EmployeeEntity employeeEntity = new EmployeeEntity();
		employeeEntity.setEmpCode("000000");
		employeeEntity.setEmpName("linwens");
		userEntity.setEmployee(employeeEntity);
		// waybillStockService.saveWaybillStockService(waybillNo,
		// serialNos,,userEntity);
	}

	//@Test
	public void testSearchPreLeaveTime() {
		String departDeptCode = "W011302020106";
		String specialLine = "W040002060401";
		String productCode = ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT;
		Date createTime = new Date();
		Date time = waybillManagerService.searchPreLeaveTime(departDeptCode, specialLine, productCode, createTime);
		System.out.println("预计出发时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time));
	}

	//@Test
	public void testSearchPreSelfPickupTime() throws ParseException {
		String departDeptCode = "W011302020106";
		String specialLine = "C30001";
		String arriveDetpCode = "W011305080203";
		String productCode = WaybillConstants.TRUCK_FLIGHT;
		Date createTime = new Date();
		Date preLeaveTime = waybillManagerService.searchPreLeaveTime(departDeptCode, specialLine, productCode, createTime);
		System.out.println("预计出发时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(preLeaveTime));
		EffectiveDto time = waybillManagerService.searchPreSelfPickupTime(specialLine, arriveDetpCode, productCode, preLeaveTime, createTime);
		System.out.println("承认自提时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time.getSelfPickupTime()));
	}

	//@Test
	public void testSearchPreDeliveryTime() throws ParseException {
		String departDeptCode = "W040002060401";
		String arriveDetpCode = "W06071401";
		String productCode = WaybillConstants.TRUCK_FLIGHT;
		Date preLeaveTime = new SimpleDateFormat("yyyyMMddHHmmss").parse("20121120231200");
		Date createTime = new Date();
		EffectiveDto time = waybillManagerService.searchPreDeliveryTime(departDeptCode, arriveDetpCode, productCode, preLeaveTime, createTime);
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(preLeaveTime));
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time.getDeliveryDate()));
	}

	//@Test
	public void testQueryWaybillBasicByNoList() {
		List<String> waybillNoList = new ArrayList<String>();
		waybillNoList.add("2012123");
		waybillNoList.add("2012117");
		waybillNoList.add("2012007");
		waybillNoList.add("2000100");
		waybillNoList.add("2000101");

		List<WaybillEntity> waybillEntityList = waybillManagerService.queryWaybillBasicByNoList(waybillNoList);
		for (WaybillEntity waybillEntity : waybillEntityList) {
			System.out.println(waybillEntity.getWaybillNo());
			System.out.println(waybillEntity.getArriveType());
			System.out.println(waybillEntity.getCreateOrgCode());
		}
	}
	
	//@Test
	public void testQueryWaybillDtoById(){
		String waybillId = "b2bb1624-272e-4a12-a500-b7330f0d0cbd";
		WaybillDto dto = waybillManagerService.getWaybillDtoById(waybillId);
		Assert.assertNotNull(dto);
		Assert.assertNotNull(dto.getWaybillChargeDtlEntity());
	}
	
	//@Test
	public void testQueryPendingByNo(){
		String waybillNo = "000026123";
		WaybillPendingEntity entity = waybillPendingService.queryPendingByNo(waybillNo);
		System.out.println(entity.getInsuranceAmount());
		System.out.println(entity.getInsuranceRate());
		System.out.println(entity.getInsuranceFee());
		System.out.println(entity.getWaybillNo());
		System.out.println(entity.getDeliveryCustomerContact());
		System.out.println(entity.getReceiveCustomerContact());
	}
	
	//@Test
	public void testQueryPaymentPendingById(){
		String waybillNo = "123";
		List<WaybillPaymentPendingEntity> list = waybillPendingService.queryPaymentPendingByNo(waybillNo);
		for (WaybillPaymentPendingEntity entity : list) {
			System.out.println("---------------------");
			System.out.println(entity.getWaybillNo());
			System.out.println(entity.getAmount());
			System.out.println(entity.getCurrencyCode());
		}
	}
	
	//@Test
	public void testQueryDisDtlPendingByWaybillId(){
		String waybillNo = "43243243";
		List<WaybillDisDtlPendingEntity> list = waybillPendingService.queryDisDtlPendingByNo(waybillNo);
		for (WaybillDisDtlPendingEntity entity : list) {
			System.out.println("---------------------");
			System.out.println(entity.getWaybillNo());
			System.out.println(entity.getAmount());
			System.out.println(entity.getCurrencyCode());
		}
	}
	
	//@Test
	public void testQueryCHDtlPendingByWaybillId(){
		String waybillNo = "123";
		List<WaybillCHDtlPendingEntity> list = waybillPendingService.queryCHDtlPendingByNo(waybillNo);
		for (WaybillCHDtlPendingEntity entity : list) {
			System.out.println("---------------------");
			System.out.println(entity.getPricingEntryCode());
			System.out.println(entity.getAmount());
			System.out.println(entity.getCurrencyCode());
		}
	}
	
	//@Test
	public void testSavePendingWaybill(){
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@" + waybillNo);
		WaybillPendingDto waybillDto = new WaybillPendingDto();
		WaybillPendingEntity entity = new WaybillPendingEntity();
		entity.setWaybillNo(waybillNo);
		entity.setDeliveryCustomerName("Name:" + Math.round(Math.random() * 1000));
		entity.setDeliveryCustomerMobilephone("139" + Math.round(Math.random() * 100000000));
		entity.setDeliveryCustomerPhone("021-" + Math.round(Math.random() * 1000000));
		entity.setDeliveryCustomerContact("码农:" + Math.round(Math.random() * 1000) + "号");
		entity.setReceiveCustomerId(UUIDUtils.getUUID());
		entity.setReceiveCustomerCode("客户编码：" + Math.round(Math.random() * 1000000));
		entity.setReceiveCustomerName("收货客户：" + Math.round(Math.random() * 1000000));
		entity.setReceiveCustomerMobilephone("158" + Math.round(Math.random() * 100000000));
		entity.setReceiveCustomerPhone("010-" + Math.round(Math.random() * 1000000));
		entity.setReceiveCustomerContact("码农:" + Math.round(Math.random() * 1000) + "号");
		entity.setReceiveCustomerNationCode(Math.round(Math.random() * 100) + "国 ");
		entity.setReceiveCustomerProvCode(Math.round(Math.random() * 10) + "省");
		entity.setReceiveCustomerCityCode(Math.round(Math.random() * 100) + "市");
		entity.setReceiveCustomerAddress("地址：" + Math.round(Math.random() * 1000));
		entity.setReceiveOrgCode("GS00002");
		entity.setProductId(UUIDUtils.getUUID());
		entity.setProductCode("C30001");
		entity.setReceiveMethod("提货方式：" + Math.round(Math.random() * 100));
		entity.setPickupToDoor(FossConstants.YES);
		entity.setPickupCentralized(FossConstants.NO);
		entity.setLoadOrgCode("W040002060401");
		entity.setLastLoadOrgCode("W06071401");
		entity.setPreCustomerPickupTime(new Date());
		entity.setCarDirectDelivery(FossConstants.NO);
		entity.setGoodsName("GoodsName " + Math.round(Math.random() * 1000));
		entity.setGoodsQtyTotal(Integer.valueOf(Math.round(Math.random() * 100) + ""));
		entity.setGoodsTypeCode("A" + Integer.valueOf(Math.round(Math.random() * 10) + ""));
		entity.setPreciousGoods(FossConstants.NO);
		entity.setSpecialShapedGoods(FossConstants.NO);
		entity.setSecretPrepaid(FossConstants.YES);
		entity.setActive(FossConstants.ACTIVE);
		entity.setForbiddenLine(FossConstants.NO);
		// TODO 总运费
		BigDecimal totalFee = BigDecimal.valueOf(Math.round(Math.random() * 1000000));
		entity.setTotalFee(totalFee);
		entity.setCreateTime(new Date());
		entity.setBillTime(new Date());
		entity.setCreateUserCode("李凤腾");
		entity.setCreateOrgCode("GS00002");
		entity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		entity.setIsWholeVehicle(FossConstants.NO);
		entity.setWholeVehicleAppfee(BigDecimal.valueOf(Math.round(Math.random() * 1000), 2));
		entity.setBillWeight(BigDecimal.valueOf(Math.round(Math.random() * 1000), 2));
		// TODO
		BigDecimal pickupFee = BigDecimal.valueOf(Math.round(Math.random() * 100));
		entity.setPickupFee(pickupFee);
		entity.setServiceFee(BigDecimal.valueOf(Math.random() * 1000));
		entity.setPreArriveTime(new Date());
		entity.setReceiveCustomerDistCode(" " + Integer.valueOf(Math.round(Math.random() * 1000) + ""));
		entity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);

		/**
		 * 可空字段
		 */
		entity.setOrderNo("DD" + Math.round(Math.random() * 100));
		entity.setDeliveryCustomerId(UUIDUtils.getUUID());
		entity.setDeliveryCustomerName(Math.round(Math.random() * 100) + "国 ");
		entity.setDeliveryCustomerProvCode(Math.round(Math.random() * 10) + "省");
		entity.setDeliveryCustomerCityCode(Math.round(Math.random() * 100) + "市");
		entity.setDeliveryCustomerAddress("地址：" + Math.round(Math.random() * 1000));
		entity.setCustomerPickupOrgCode("CPOC：" + Math.round(Math.random() * 100));
		entity.setLoadMethod("配载类型：" + Math.round(Math.random() * 10));
		entity.setTargetOrgCode("TOC：" + Math.round(Math.random() * 10));
		entity.setDriverCode("DC：" + Math.round(Math.random() * 1000));
		entity.setLoadLineCode(" " + Math.round(Math.random() * 10));
		entity.setPreDepartureTime(new Date());
		entity.setGoodsWeightTotal(BigDecimal.valueOf(Math.round(Math.random() * 100), 2));
		entity.setGoodsVolumeTotal(BigDecimal.valueOf(Math.round(Math.random() * 100), 2));
		entity.setGoodsSize("2000*10*111*1");
		entity.setOuterNotes("易碎品，勿压！");
		entity.setInnerNotes("该货请帮忙配载下！");
		entity.setGoodsPackage("1纸1木");
		entity.setInsuranceAmount(BigDecimal.valueOf(Math.random() * 100));
		entity.setInsuranceRate(BigDecimal.valueOf(Math.round(Math.random() * 100), 2));
		// TODO
		BigDecimal insuranceFee = BigDecimal.valueOf(Math.round(Math.random() * 100));
		entity.setInsuranceFee(insuranceFee);
		// TODO
		BigDecimal toPayAmount = BigDecimal.valueOf(Math.round(Math.random() * 1000));
		entity.setToPayAmount(toPayAmount);
		// TODO
		BigDecimal prePayAmount = totalFee.subtract(toPayAmount);
		entity.setPrePayAmount(prePayAmount);
		// TODO 代收货款
		BigDecimal codAmount = totalFee.subtract(prePayAmount).subtract(toPayAmount);
		entity.setCodAmount(codAmount);
		entity.setCodRate(BigDecimal.valueOf(Math.round(Math.random() * 10), 2));
		// TODO
		BigDecimal codFee = BigDecimal.valueOf(Math.round(Math.random() * 100));
		entity.setCodFee(codFee);
		entity.setRefundType(" " + Integer.valueOf(Math.round(Math.random() * 10) + ""));
		// TODO
		BigDecimal deliveryGoodsFee = BigDecimal.valueOf(Math.round(Math.random() * 100));
		entity.setDeliveryGoodsFee(deliveryGoodsFee);
		// TODO
		BigDecimal otherFee = BigDecimal.valueOf(Math.round(Math.random() * 100));
		entity.setOtherFee(otherFee);
		// TODO
		BigDecimal packageFee = BigDecimal.valueOf(Math.round(Math.random() * 100));
		entity.setPackageFee(packageFee);
		// TODO
		BigDecimal promotionsFee = BigDecimal.valueOf(Math.round(Math.random() * 100));
		entity.setPromotionsFee(promotionsFee);
		entity.setBillingType(" " + Integer.valueOf(Math.round(Math.random() * 10) + ""));
		entity.setUnitPrice(BigDecimal.valueOf(Math.round(Math.random() * 10000), 2));
		// TODO
		BigDecimal transport = totalFee.subtract(promotionsFee.negate()).subtract(packageFee).subtract(otherFee).subtract(deliveryGoodsFee)
				.subtract(codAmount).subtract(insuranceFee).subtract(pickupFee).subtract(codFee);
		entity.setTransportFee(transport);
		// TODO
		BigDecimal valueAddFee = pickupFee.add(deliveryGoodsFee).add(packageFee).add(codFee).add(otherFee).add(insuranceFee);
		entity.setValueAddFee(valueAddFee);
		entity.setPaidMethod(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH);
		entity.setArriveType(" " + Integer.valueOf(Math.round(Math.random() * 10) + ""));
		entity.setFreightMethod(" " + Integer.valueOf(Math.round(Math.random() * 10) + ""));
		entity.setFlightShift(new Date() + "");
		entity.setPromotionsCode("优惠编码：" + Integer.valueOf(Math.round(Math.random() * 1000) + ""));
		entity.setModifyTime(new Date());
		entity.setModifyUserCode("李凤腾");
		entity.setModifyOrgCode(" " + Integer.valueOf(Math.round(Math.random() * 1000) + ""));
		entity.setRefId(UUIDUtils.getUUID());
		entity.setRefCode(Math.round(Math.random() * 10) + "");
		entity.setWholeVehicleActualfee(BigDecimal.valueOf(Math.random() * 1000));
		entity.setAccountName(" " + Integer.valueOf(Math.round(Math.random() * 1000) + ""));
		entity.setAccountCode("ICBC:" + Integer.valueOf(Math.round(Math.random() * 1000) + ""));
		entity.setAccountBank("ICBC:" + Integer.valueOf(Math.round(Math.random() * 1000) + ""));
		entity.setTransportType(" " + Integer.valueOf(Math.round(Math.random() * 10) + ""));
		entity.setAddTime(new Date());
		//entity.setIsPdaBill(FossConstants.YES);
		entity.setDeliveryCustomerDistCode(" " + Integer.valueOf(Math.round(Math.random() * 100) + ""));
		entity.setDeliveryCustomerNationCode("DeliveryCustomerNationCode:" + Integer.valueOf(Math.round(Math.random() * 1000) + ""));
		entity.setReturnBillType("returnBillType:" + Integer.valueOf(Math.round(Math.random() * 10) + ""));
		entity.setContactAddressId(UUIDUtils.getUUID());
		// ======================WaybillChargeDtlEntity=============================

		List<WaybillCHDtlPendingEntity> chargeDtlEntityList = new ArrayList<WaybillCHDtlPendingEntity>();
		WaybillCHDtlPendingEntity chargeDtlEntity1 = new WaybillCHDtlPendingEntity();
		chargeDtlEntity1.setPricingEntryCode("pricingEntryCode" + Math.random());
		chargeDtlEntity1.setAmount(BigDecimal.valueOf(Math.random() * 1000));
		chargeDtlEntity1.setWaybillNo(waybillNo);
		chargeDtlEntity1.setPricingCriDetailId(UUIDUtils.getUUID());
		chargeDtlEntity1.setCreateTime(new Date());
		chargeDtlEntity1.setModifyTime(new Date());
		chargeDtlEntity1.setActive(FossConstants.ACTIVE);
		chargeDtlEntity1.setBillTime(new Date());
		chargeDtlEntity1.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		chargeDtlEntityList.add(chargeDtlEntity1);

		WaybillCHDtlPendingEntity chargeDtlEntity2 = new WaybillCHDtlPendingEntity();
		chargeDtlEntity2.setPricingEntryCode("pricingEntryCode" + Math.random());
		chargeDtlEntity2.setAmount(BigDecimal.valueOf(Math.random() * 1000));
		chargeDtlEntity2.setWaybillNo(waybillNo);
		chargeDtlEntity2.setPricingCriDetailId(UUIDUtils.getUUID());
		chargeDtlEntity2.setCreateTime(new Date());
		chargeDtlEntity2.setModifyTime(new Date());
		chargeDtlEntity2.setActive(FossConstants.ACTIVE);
		chargeDtlEntity2.setBillTime(new Date());
		chargeDtlEntity2.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		chargeDtlEntityList.add(chargeDtlEntity2);

		// =========================WaybillDisDtlEntity========================
		List<WaybillDisDtlPendingEntity> disDtlEntityList = new ArrayList<WaybillDisDtlPendingEntity>();

		WaybillDisDtlPendingEntity disDtlEntity1 = new WaybillDisDtlPendingEntity();
		disDtlEntity1.setPricingEntryCode("pricingEntryCode" + Math.random());
		disDtlEntity1.setType("优惠类型" + Math.random());
		disDtlEntity1.setRate(BigDecimal.valueOf(Math.random() * 10));
		disDtlEntity1.setAmount(BigDecimal.valueOf(Math.random() * 1000));
		disDtlEntity1.setWaybillNo(waybillNo);
		disDtlEntity1.setDicountId(UUIDUtils.getUUID());
		disDtlEntity1.setWaybillChDePgId("64913985");
		disDtlEntity1.setCreateTime(new Date());
		disDtlEntity1.setModifyTime(new Date());
		disDtlEntity1.setActive(FossConstants.ACTIVE);
		disDtlEntity1.setBillTime(new Date());
		disDtlEntity1.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		disDtlEntityList.add(disDtlEntity1);

		WaybillDisDtlPendingEntity disDtlEntity2 = new WaybillDisDtlPendingEntity();
		disDtlEntity2.setPricingEntryCode("pricingEntryCode" + Math.random());
		disDtlEntity2.setType("优惠类型" + Math.random());
		disDtlEntity2.setRate(BigDecimal.valueOf(Math.random() * 10));
		disDtlEntity2.setAmount(BigDecimal.valueOf(Math.random() * 1000));
		disDtlEntity2.setWaybillNo(waybillNo);
		disDtlEntity2.setDicountId(UUIDUtils.getUUID());
		disDtlEntity2.setWaybillChDePgId(UUIDUtils.getUUID());
		disDtlEntity2.setCreateTime(new Date());
		disDtlEntity2.setModifyTime(new Date());
		disDtlEntity2.setActive(FossConstants.ACTIVE);
		disDtlEntity2.setBillTime(new Date());
		disDtlEntity2.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		disDtlEntityList.add(disDtlEntity2);

		// =================WoodenRequirementsEntity========================
		WoodenRequirePendingEntity woodenRequirementsEntity = new WoodenRequirePendingEntity();
		woodenRequirementsEntity.setWaybillNo(waybillNo);
		woodenRequirementsEntity.setPackageOrgCode(Math.random() * 10000 + "");
		woodenRequirementsEntity.setStandGoodsNum(Integer.valueOf(Math.round(Math.random() * 100) + ""));
		woodenRequirementsEntity.setStandRequirement("要求");
		woodenRequirementsEntity.setStandGoodsSize("200*100*100*1");
		woodenRequirementsEntity.setStandGoodsVolume(BigDecimal.valueOf(Math.random()));
		woodenRequirementsEntity.setBoxGoodsNum(Integer.valueOf(Math.round(Math.random() * 100) + ""));
		woodenRequirementsEntity.setBoxRequirement("boxRequirement");
		woodenRequirementsEntity.setBoxGoodsSize("boxGoodsSize:200*100*100*1");
		woodenRequirementsEntity.setBoxGoodsVolume(BigDecimal.valueOf(Math.random()));
		woodenRequirementsEntity.setActive(FossConstants.ACTIVE);
		woodenRequirementsEntity.setCreateTime(new Date());
		woodenRequirementsEntity.setModifyTime(new Date());

		// ======================waybillPaymentEntity=====================
		List<WaybillPaymentPendingEntity> waybillPaymentEntityList = new ArrayList<WaybillPaymentPendingEntity>();

		WaybillPaymentPendingEntity waybillPaymentEntity1 = new WaybillPaymentPendingEntity();
		waybillPaymentEntity1.setWaybillNo(waybillNo);
		waybillPaymentEntity1.setType("付款类型" + Math.random());
		waybillPaymentEntity1.setAmount(BigDecimal.valueOf(Math.random() * 100));
		waybillPaymentEntity1.setPaymentTime(new Date());
		waybillPaymentEntity1.setCreateTime(new Date());
		waybillPaymentEntity1.setModifyTime(new Date());
		waybillPaymentEntity1.setActive(FossConstants.ACTIVE);
		waybillPaymentEntity1.setBillTime(new Date());
		waybillPaymentEntity1.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		waybillPaymentEntityList.add(waybillPaymentEntity1);

		WaybillPaymentPendingEntity waybillPaymentEntity2 = new WaybillPaymentPendingEntity();
		waybillPaymentEntity2.setWaybillNo(waybillNo);
		waybillPaymentEntity2.setType("付款类型" + Math.random());
		waybillPaymentEntity2.setAmount(BigDecimal.valueOf(Math.random() * 100));
		waybillPaymentEntity2.setPaymentTime(new Date());
		waybillPaymentEntity2.setCreateTime(new Date());
		waybillPaymentEntity2.setModifyTime(new Date());
		waybillPaymentEntity2.setActive(FossConstants.ACTIVE);
		waybillPaymentEntity2.setBillTime(new Date());
		waybillPaymentEntity2.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		waybillPaymentEntityList.add(waybillPaymentEntity2);

		EmployeeEntity employeeEntity = new EmployeeEntity();
		employeeEntity.setEmpCode("" + BigDecimal.valueOf(Math.round(Math.random() * 100000)));
		employeeEntity.setEmpName("营业部" + BigDecimal.valueOf(Math.round(Math.random() * 100)));

		OrgAdministrativeInfoEntity dept = new OrgAdministrativeInfoEntity();
		dept.setCode("" + BigDecimal.valueOf(Math.round(Math.random() * 100000)));
		dept.setName("上海事业部" + BigDecimal.valueOf(Math.round(Math.random() * 10)));

		UserEntity user = new UserEntity();
		user.setEmployee(employeeEntity);
		user.setUserName("name:" + BigDecimal.valueOf(Math.round(Math.random() * 100000)));
		user.setActive(FossConstants.ACTIVE);

		CurrentInfo currentInfo = new CurrentInfo(user, dept);
		waybillDto.setCurrentInfo(currentInfo);
		waybillDto.setWaybillPending(entity);
		waybillDto.setWaybillChargeDtlPending(chargeDtlEntityList);
		waybillDto.setWaybillDisDtlPending(disDtlEntityList);
		waybillDto.setWaybillPaymentPending(waybillPaymentEntityList);
		waybillDto.setWoodenRequirePending(woodenRequirementsEntity);
		
		
		waybillPendingService.addPendingWaybill(waybillDto);
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@" + waybillNo);
	}

	//@Test
	public void testAddWaybillDisDtlPending(){
		List<WaybillDisDtlPendingEntity> disDtlEntityList = new ArrayList<WaybillDisDtlPendingEntity>();

		WaybillDisDtlPendingEntity disDtlEntity1 = new WaybillDisDtlPendingEntity();
		disDtlEntity1.setPricingEntryCode("pricingEntryCode" + Math.random());
		disDtlEntity1.setType("优惠类型" + Math.random());
		disDtlEntity1.setRate(BigDecimal.valueOf(Math.random() * 10));
		disDtlEntity1.setAmount(BigDecimal.valueOf(Math.random() * 1000));
		disDtlEntity1.setWaybillNo(waybillNo);
		disDtlEntity1.setDicountId(UUIDUtils.getUUID());
		disDtlEntity1.setWaybillChDePgId(UUIDUtils.getUUID());
		disDtlEntity1.setCreateTime(new Date());
		disDtlEntity1.setModifyTime(new Date());
		disDtlEntity1.setActive(FossConstants.ACTIVE);
		disDtlEntity1.setBillTime(new Date());
		disDtlEntity1.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		disDtlEntityList.add(disDtlEntity1);

		WaybillDisDtlPendingEntity disDtlEntity2 = new WaybillDisDtlPendingEntity();
		disDtlEntity2.setPricingEntryCode("pricingEntryCode" + Math.random());
		disDtlEntity2.setType("优惠类型" + Math.random());
		disDtlEntity2.setRate(BigDecimal.valueOf(Math.random() * 10));
		disDtlEntity2.setAmount(BigDecimal.valueOf(Math.random() * 1000));
		disDtlEntity2.setWaybillNo(waybillNo);
		disDtlEntity2.setDicountId(UUIDUtils.getUUID());
		disDtlEntity2.setWaybillChDePgId(UUIDUtils.getUUID());
		disDtlEntity2.setCreateTime(new Date());
		disDtlEntity2.setModifyTime(new Date());
		disDtlEntity2.setActive(FossConstants.ACTIVE);
		disDtlEntity2.setBillTime(new Date());
		disDtlEntity2.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		disDtlEntityList.add(disDtlEntity2);
			
		WaybillSystemDto systemDto = new WaybillSystemDto();
		systemDto.setCreateTime(new Date());
		systemDto.setModifyTime(new Date());
		systemDto.setBillTime(new Date());

		//waybillPendingService.addWaybillDisDtlPending(disDtlEntityList, systemDto);
	}
	
	//@Test
	public void testQueryPartWaybillByNo(){
		String waybillNo = "2012123";
		WaybillEntity waybillEntity = waybillManagerService.queryPartWaybillByNo(waybillNo);
		System.out.println(waybillEntity.getPaidMethod());
		System.out.println(waybillEntity.getCustomerPickupOrgCode());
		System.out.println(waybillEntity.getToPayAmount());
		System.out.println(waybillEntity.getCurrencyCode());
	}
	
	//@Test
	public void testQueryOtherChargeByNo(){
		String waybillNo = "20121402";
		List<WaybillOtherChargeDto> waybill = waybillPendingService.queryOtherChargeByNo(waybillNo);
		for (WaybillOtherChargeDto dto : waybill) {
			System.out.println(dto.getCode());
			System.out.println(dto.getChargeName());
			System.out.println(dto.getAmount());
		}
	}
	
//	@Test
//	public void testQueryChargeExceptOtherByNo(){
//		String waybillNo = "44449614";
//		Map<String,String> map = new HashMap<String,String>();
//		map.put("QT", "QT");
//		map.put("QS", "QS");
//		List<WaybillCHDtlPendingEntity> waybill = waybillPendingService.queryOtherChargeDtlByNo(waybillNo, map);
//		for (WaybillCHDtlPendingEntity entity : waybill) {
//			System.out.println(entity.getPricingEntryCode());
//			System.out.println(entity.getWaybillNo());
//			System.out.println(entity.getAmount());
//		}
//	}

	//@Test
	public void testAdd(){
		List<LabeledGoodPDAEntity> pdaList = new ArrayList<LabeledGoodPDAEntity>();
		LabeledGoodPDAEntity entity1 = new LabeledGoodPDAEntity();
		entity1.setActive(FossConstants.YES);
		entity1.setCreateDate(new Date());
		entity1.setBillTime(new Date());
		entity1.setCreateOrgCode("026123");
		entity1.setCreateTime(new Date());
		entity1.setCreateUser("026123");
		entity1.setCreateUserCode("026123");
		entity1.setModifyDate(new Date());
		entity1.setModifyOrgCode("026123");
		entity1.setModifyTime(new Date());
		entity1.setModifyUser("026123");
		entity1.setModifyUserCode("026123");
		entity1.setSerialNo("0001");
		entity1.setWaybillNo("201303170");
		entity1.setWaybillPDAId("0752a3d7-f115-401f-9f60-f8f9300080c2");
		pdaList.add(entity1);
		
		LabeledGoodPDAEntity entity2 = new LabeledGoodPDAEntity();
		entity2.setActive(FossConstants.YES);
		entity2.setCreateDate(new Date());
		entity2.setBillTime(new Date());
		entity2.setCreateOrgCode("026123");
		entity2.setCreateTime(new Date());
		entity2.setCreateUser("026123");
		entity2.setCreateUserCode("026123");
		entity2.setModifyDate(new Date());
		entity2.setModifyOrgCode("026123");
		entity2.setModifyTime(new Date());
		entity2.setModifyUser("026123");
		entity2.setModifyUserCode("026123");
		entity2.setSerialNo("0002");
		entity2.setWaybillNo("201303171");
		entity2.setWaybillPDAId("4a6c9738-ff6c-4d44-a1fc-59c7a0af13d1p ");
		pdaList.add(entity2);
		
		//labeledGoodService.add(pdaList);
	}
	
	//@Test
	public void testReduce(){
		List<LabeledGoodPDAEntity> pdaList = new ArrayList<LabeledGoodPDAEntity>();
		LabeledGoodPDAEntity entity1 = new LabeledGoodPDAEntity();
		entity1.setActive(FossConstants.YES);
		entity1.setSerialNo("0002");
		entity1.setWaybillNo("761603911");
		pdaList.add(entity1);
		
		LabeledGoodPDAEntity entity2 = new LabeledGoodPDAEntity();
		entity2.setActive(FossConstants.YES);
		entity2.setSerialNo("0003");
		entity2.setWaybillNo("139165288");
		pdaList.add(entity2);
		
		//labeledGoodService.reduce(pdaList);
	}
	
	@Test
	@Ignore
	public void testPendingTodo(){
		List<String> pendTodoIdList = new ArrayList<String>();
		pendTodoIdList.add("96966961-322f-41c7-9cd9-d21b898c180d");
		List<PendingTodoEntity> pend = pendingTodoDao.queryPendingTodoByIds(pendTodoIdList);
		if(pend.size() > 0){
			waybillRfcTodoJobService.sendSingleTodo(pend.get(0));
		}
	}
	
	@Test
	@Ignore
	public void testLabelTodo(){
		LabeledGoodTodoEntity todoEntity = new LabeledGoodTodoEntity();
		todoEntity.setSerialNo("0001");
		todoEntity.setWaybillRfcId("d08f7e85-f4be-4240-82ce-4ab2b08c78dc");//20000840
		waybillRfcTodoJobService.handleSingleTodo(todoEntity);
	}
	
	@Test
	@Ignore
	public void testProduct3(){
		ProductEntity ento = productService.getLevel3ProductInfo("FLF");
		System.out.println(ento.getName());
	}
	
	@Test
	@Ignore
	public void testwaybillManagerService(){
		waybillManagerService.updateGoodsNum("622111222", 1, "092444", "W3100020616");
	}
	
	@Test
	@Ignore
	public void testPdaScan(){
		/*for(long i=1;i<=205;i++){
			String waybillNo = 8000000+String.format("%03d", i);
			ActualFreightEntity actualFreightEntity = actualFreightDao.queryByPrimaryKey("4e8ae96f-7e02-45ec-8055-01911c88c97b");
			actualFreightEntity.setId(UUIDUtils.getUUID());
			actualFreightEntity.setCreateTime(new Date());
			actualFreightEntity.setModifyTime(new Date());
			actualFreightEntity.setWaybillNo(waybillNo);
			actualFreightEntity.setWaybillType(WaybillConstants.EWAYBILL);
			actualFreightEntity.setStatus(WaybillConstants.WAYBILL_STATUS_EWAYBILL_PENDING);
			actualFreightDao.insertSelective(actualFreightEntity);
		}*/
		for(int i=0;i<100;i++){
			//获取运单数据
			String waybillNo = waybillPendingDao.getNextEWaybillNo();
			//PDA扫描的数据
			PdaScanEntity entity = null;
			List<PdaScanEntity> pdaScanEntityList = new ArrayList<PdaScanEntity>();
			for(int j=0;j<3;j++){
				entity = new PdaScanEntity();
				entity.setId(UUIDUtils.getUUID());
				entity.setSerialNo(String.format("%04d", j+1));
				entity.setWaybillNo(waybillNo);
				entity.setOrderNo("WK"+waybillNo);
				entity.setActive(FossConstants.YES);
				entity.setScanTime(new Date());
				entity.setModifyTime(new Date());
				entity.setScanType("SCAN");
				entity.setTaskType("PICKUP");
				entity.setDriverCode("065545");
				entity.setOperateOrgCode("W3100020616");
				entity.setClerkCode("013662");
				entity.setTaskId(UUIDUtils.getUUID());
				entity.setWaybillType(WaybillConstants.EWAYBILL);
				entity.setWhetherComplete(FossConstants.YES);
				entity.setVolume(BigDecimal.valueOf(0.02));
				entity.setWeight(BigDecimal.valueOf(1));
				pdaScanEntityList.add(entity);
			}
			pdaScanDao.batchInsertSelectivePdaScanInfo(pdaScanEntityList);
			
			EWaybillOrderEntity eWaybillOrderEntity = ewaybillOrderEntityDao.queryEWaybillByOrderNo("WK150123741991");
			eWaybillOrderEntity.setOrderNO("WK"+waybillNo);
			eWaybillOrderEntity.setWaybillNO(waybillNo);
			eWaybillOrderEntity.setCreateTime(new Date());
			eWaybillOrderEntity.setModifyTime(new Date());
			eWaybillOrderEntity.setCreateUser("065545");
			eWaybillOrderEntity.setID(UUIDUtils.getUUID());
			eWaybillOrderEntity.setJobID("N/A");
			ewaybillOrderEntityDao.insertEWaybillOrder(eWaybillOrderEntity);
			
			DispatchOrderEntity dispatchOrderEntity = dispatchOrderEntityDao.queryBasicDispachOrderEntity("WK140520554479");
			dispatchOrderEntity.setId(UUIDUtils.getUUID());
			dispatchOrderEntity.setOrderTime(new Date());
			dispatchOrderEntity.setDriverCode("065545");
			dispatchOrderEntity.setSalesDepartmentCode("W0113080404");
			dispatchOrderEntity.setWaybillNo(waybillNo);
			dispatchOrderEntity.setOrderStatus(DispatchOrderStatusConstants.STATUS_WAYBILL);
			dispatchOrderEntity.setWaybillType(WaybillConstants.EWAYBILL);
			dispatchOrderEntityDao.insertSelective(dispatchOrderEntity);
			
			WaybillPendingEntity waybillPendingEntity = waybillPendingDao.getWaybillPendingEntityByWaybillNo("201350708");
			waybillPendingEntity.setWaybillNo(waybillNo);
			waybillPendingEntity.setPendingType(WaybillConstants.WAYBILL_STATUS_PDA_PENDING);
			waybillPendingEntity.setId(UUIDUtils.getUUID());
			waybillPendingEntity.setOrderNo("WK"+waybillNo);
			waybillPendingEntity.setDriverCode("065545");
			waybillPendingEntity.setCreateOrgCode("W0113080404");
			waybillPendingEntity.setReceiveOrgCode("W0113080404");
			waybillPendingEntity.setCreateTime(new Date());
			waybillPendingEntity.setModifyTime(new Date());
			waybillPendingEntity.setBillTime(new Date());
			waybillPendingEntity.setWaybillType(WaybillConstants.EWAYBILL);
			waybillPendingDao.insert(waybillPendingEntity);
			
			WaybillEntity waybillEntity = new WaybillEntity();
			try {
				BeanUtils.copyProperties(waybillEntity, waybillPendingEntity);
				waybillEntity.setId(UUIDUtils.getUUID());
				waybillEntity.setActive(FossConstants.YES);
				waybillEntity.setPendingType(WaybillConstants.WAYBILL_STATUS_PDA_PENDING);
				waybillDao.addWaybillEntity(waybillEntity);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			ActualFreightEntity actualFreightEntity = actualFreightDao.queryByPrimaryKey("4e8ae96f-7e02-45ec-8055-01911c88c97b");
			actualFreightEntity.setId(UUIDUtils.getUUID());
			actualFreightEntity.setCreateTime(new Date());
			actualFreightEntity.setModifyTime(new Date());
			actualFreightEntity.setWaybillNo(waybillNo);
			actualFreightEntity.setWaybillType(WaybillConstants.EWAYBILL);
			actualFreightEntity.setStatus(WaybillConstants.EWAYBILL_ACTIVE_FAIL);
			actualFreightDao.insertSelective(actualFreightEntity);
		}
	}
	
	@Test
	@Ignore
	public void pdaScanAdd(){
		for(int i=0;i<10;i++){
			//获取运单数据
			String waybillNo = waybillPendingDao.getNextEWaybillNo();
			//PDA扫描的数据
			PdaScanEntity entity = null;
			List<PdaScanEntity> pdaScanEntityList = new ArrayList<PdaScanEntity>();
			for(int j=0;j<3;j++){
				entity = new PdaScanEntity();
				entity.setId(UUIDUtils.getUUID());
				entity.setSerialNo(String.format("%04d", j+1));
				entity.setWaybillNo(waybillNo);
				entity.setOrderNo("WK"+waybillNo);
				entity.setActive(FossConstants.YES);
				entity.setScanTime(new Date());
				entity.setModifyTime(new Date());
				entity.setScanType("SCAN");
				entity.setTaskType("UNLOAD");
				entity.setDriverCode("065545");
				entity.setClerkCode("046788");
				entity.setOperateOrgCode("W0113080404");
				entity.setTaskId(UUIDUtils.getUUID());
				entity.setWaybillType(WaybillConstants.EWAYBILL);
				entity.setWhetherComplete(FossConstants.YES);
				entity.setVolume(BigDecimal.valueOf(0.02));
				entity.setWeight(BigDecimal.valueOf(1));
				pdaScanEntityList.add(entity);
			}
			pdaScanDao.batchInsertSelectivePdaScanInfo(pdaScanEntityList);
		}
	}
	
	@Test
	@Ignore
	public void waybillRfcBatchChangeDaoTest(){
		//获取运单数据
		WaybillRfcBatchChangeEntity record = new WaybillRfcBatchChangeEntity();
		record.setId(UUIDUtils.getUUID());
		record.setWaybillNo("8888888888");
		record.setJobId("N/A");
		record.setImportTime(new Date());
		record.setModifyTime(new Date());
		record.setCreateUserCode("046788");
		record.setModifyUserCode("046788");
		record.setFailReason("N");
		record.setDeliverCustomerCode("11111111");
		record.setTransportFeeChange(BigDecimal.ZERO);
		record.setWeightChange(BigDecimal.ZERO);
		record.setVolumnChange(BigDecimal.ZERO);
		record.setProductChange("RCP");
		record.setChangeNote("亲爱的");
		record.setChangeStatus("RFC_PENDING");
		waybillRfcBatchChangeDao.insertSelective(record);

		waybillRfcBatchChangeDao.deleteByPrimaryKey(record.getId());
		List<WaybillRfcBatchChangeEntity> list = new ArrayList<WaybillRfcBatchChangeEntity>();
		WaybillRfcBatchChangeEntity record1 = null;
		for(int i=0;i<10;i++){
			//获取运单数据
			record1 = new WaybillRfcBatchChangeEntity();
			record1.setId(UUIDUtils.getUUID());
			record1.setWaybillNo("8888888888");
			record1.setJobId("N/A");
			record1.setImportTime(new Date());
			record1.setModifyTime(new Date());
			record1.setCreateUserCode("046788");
			record1.setModifyUserCode("046788");
			record1.setFailReason("N");
			record1.setDeliverCustomerCode("11111111");
			record1.setTransportFeeChange(BigDecimal.ZERO);
			record1.setWeightChange(BigDecimal.ZERO);
			record1.setVolumnChange(BigDecimal.ZERO);
			record1.setProductChange("RCP");
			record1.setChangeNote("亲爱的");
			record1.setChangeStatus("RFC_PENDING");
			list.add(record1);
		}
		waybillRfcBatchChangeDao.updateByPrimaryKey(record);
		waybillRfcBatchChangeDao.updateByPrimaryKeySelective(record);
		int count = waybillRfcBatchChangeDao.batchInsertSelective(list);
		System.out.println(count);
	}
	
	@Test
	@Ignore
	public void testPdaScanDelete(){
		PdaScanQueryDto pdaScanQueryDto = new PdaScanQueryDto();
		pdaScanQueryDto.setId(UUIDUtils.getUUID());
		pdaScanQueryDto.setWaybillNo(waybillNo);
		pdaScanQueryDto.setOrderNo(waybillNo);
		pdaScanQueryDto.setActive(FossConstants.YES);
		pdaScanQueryDto.setDriverCode("046788");
		pdaScanQueryDto.setScanTime(new Date());
		pdaScanQueryDto.setScanType("SCAN");
		pdaScanQueryDto.setTaskId("PICKUP");
		pdaScanQueryDto.setWaybillType(WaybillConstants.EWAYBILL);
		pdaScanQueryDto.setWhetherComplete(FossConstants.YES);
		pdaScanDao.deletePdaScanEntityByIdOrNo(pdaScanQueryDto);
	}
	
	@Test
	@Ignore
	public void selectCustomerPickupOrgCode(){
		while(true){
			try{
				//获取目的站		
				QueryPickupPointDto queryPickupPointDto = new QueryPickupPointDto();
				
				// 网点类型标志
				ProductEntity productEntity = productService.getLevel3ProductInfo(WaybillConstants.PACKAGE);
				if(productEntity != null){
					queryPickupPointDto.setDestNetType(productEntity.getDestNetType());
				}
				// 提货方式
				queryPickupPointDto.setPickUpType(WaybillConstants.SELF_PICKUP);
				// 产品编码
				queryPickupPointDto.setTransType(WaybillConstants.PACKAGE);
				//将收货城市作为目的站简称
				if(StringUtils.isNotBlank("310000-1")){
					queryPickupPointDto.setTargetCityCode("310000-1");
				}
				//将收货人城市所在区县给弄出来
				String  simpleDistrictName = null;
				if(StringUtils.isNotBlank("310118")){
					queryPickupPointDto.setTargetCountyCode("310118");
				}
				// 出发营业部
				if(!WaybillRfcConstants.RETURN.equals(queryPickupPointDto.getSource())){
					queryPickupPointDto.setReceiveOrgCode(null);
				}
				// 自有营业部类型
				queryPickupPointDto.setSalesType(DictionaryValueConstants.ORG_ARRIVE);
				
				if (queryPickupPointDto.getPickUpPoint() != null) {
					queryPickupPointDto.setPickUpPoint(queryPickupPointDto.getPickUpPoint().trim());
					//如果提货网点有值那么需要清空目的站查询条件
					queryPickupPointDto.setOrgSimpleName("");
				}
				//是否自提
				if (waybillExpressService.verdictPickUpSelf(WaybillConstants.SELF_PICKUP)) {
					queryPickupPointDto.setPickUpSelf(FossConstants.YES);
				}
				//是否派送
				if (waybillExpressService.verdictPickUpDoor(WaybillConstants.SELF_PICKUP)) {
					queryPickupPointDto.setPickUpDoor(FossConstants.YES);
				}
				//当前时间(当前日期要大于等于开业日期)
				queryPickupPointDto.setCurDate(new Date());
				//是否有效
				queryPickupPointDto.setActive(FossConstants.ACTIVE);
				
				//进行数据的查询,这里只精确到市级单位，根据市级单位进行查询更靠谱点，而不是分割对应的名称进行数据的查询
				List<SaleDepartmentEntity> saleDepartmentEntityList = queryAllSuitableSalesDepartment(queryPickupPointDto);
				
				if(CollectionUtils.isEmpty(saleDepartmentEntityList)){
					//去除对应的区县，再次按照市区重新进行一次查询
					queryPickupPointDto.setTargetCountyCode(null);
					saleDepartmentEntityList = queryAllSuitableSalesDepartment(queryPickupPointDto);
				}
				//List<SaleDepartmentEntity> saleDepartmentEntityList = waybillManagerService.queryByDepartmentInfo(queryPickupPointDto);
				
				//对目的站及提货营业部（到达部门），到达部门根据收货人地址省市区信息匹配快递派送区域基础资料得出虚拟营业部，
				//优先选择公司的网点，其次选择快递代理；
				if(CollectionUtils.isEmpty(saleDepartmentEntityList)){
					throw new WaybillValidateException(WaybillValidateException.DESTINATION_NULL);
				}
				
				SaleDepartmentEntity saleDeprtTemp = null;
				//如果是普通营业部，直接赋值不解释
				for(SaleDepartmentEntity entity:saleDepartmentEntityList){		
					if(!(entity.getName().contains("出发")||entity.getName().contains("外发") || (entity.getName().contains("远郊")))){
						//普通营业部
						saleDeprtTemp = entity;
						break;
					}
				}
				//如果没有找到普通营业部，先判定是否出发快递营业部，没有再次选择外发的
				if(saleDeprtTemp == null){
					for(SaleDepartmentEntity entity:saleDepartmentEntityList){
						//再次选择出发快递营业部，如果还没有，就只能选择外发了
						if(entity.getName().contains("出发")||entity.getName().contains("外发")){
							//出发营业部
							saleDeprtTemp = entity;
							break;
						}else {
							//远郊营业部 
							if(saleDeprtTemp!=null && saleDeprtTemp.getName().contains("出发")||entity.getName().contains("外发")){
								continue;
							}else{
								saleDeprtTemp = entity;
							}
						}
					}
				}
				System.out.println(saleDeprtTemp.getName());
			}catch(Exception e){
				
			}
		}
	}
	
	private List<SaleDepartmentEntity> queryAllSuitableSalesDepartment(QueryPickupPointDto queryPickupPointDto) {
		if(FossConstants.YES.equals(queryPickupPointDto.getPickUpSelf())){
			if(WaybillConstants.PACKAGE.equals(queryPickupPointDto.getTransType())||ExpWaybillConstants.ROUND_COUPON_PACKAGE.equals(queryPickupPointDto.getTransType())){
				return waybillDao.queryPickupStationInforExpress(queryPickupPointDto);
			}else{
				return waybillDao.queryByDepartmentInfo(queryPickupPointDto);
			}
		}else{
			if(WaybillConstants.PACKAGE.equals(queryPickupPointDto.getTransType())||ExpWaybillConstants.ROUND_COUPON_PACKAGE.equals(queryPickupPointDto.getTransType())){
				//这段代码新加的 小件  查询虚拟部门
				return waybillDao.selectPickupStationInfoVirtualExpress(queryPickupPointDto);
			}else{
				return waybillDao.queryByDepartmentInfo(queryPickupPointDto);
			}
		}
	}
}
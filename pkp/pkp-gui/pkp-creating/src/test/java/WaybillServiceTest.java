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
 * PROJECT NAME	: pkp-creating
 * 
 * FILE PATH        	: src/test/java/WaybillServiceTest.java
 * 
 * FILE NAME        	: WaybillServiceTest.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.client.component.remote.DefaultRemoteServiceFactory;
import com.deppon.foss.framework.client.core.context.ApplicationContext;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.login.shared.domain.LoginInfo;
import com.deppon.foss.module.login.shared.hessian.ILoginHessianRemoting;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillChargeDtlEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillDisDtlEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPaymentEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WoodenRequirementsEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillHessianRemoting;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.util.UUIDUtils;

public class WaybillServiceTest {

	
	
	

	private static String waybillNo = Math.round(Math.random() * 1000000000) + "";


	public static void main(String[] args) {
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@" + waybillNo);

		ApplicationContext.setApplicationHome("/C:");
		
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
		entity.setReceiveOrgCode("W011302020515");
		entity.setProductId("C30001");
		entity.setProductCode("FLF");
		entity.setReceiveMethod("SELF_PICKUP");
		entity.setPickupToDoor("Y");
		entity.setPickupCentralized("N");
		entity.setLoadOrgCode("W3100020616");
		entity.setLastLoadOrgCode("W011306040303");
		entity.setPreCustomerPickupTime(new Date());
		entity.setCarDirectDelivery("N");
		entity.setGoodsName("GoodsName " + Math.round(Math.random() * 1000));
		entity.setGoodsQtyTotal(Integer.valueOf(Math.round(Math.random() * 100) + ""));
		entity.setGoodsTypeCode("A" + Integer.valueOf(Math.round(Math.random() * 10) + ""));
		entity.setPreciousGoods("N");
		entity.setSpecialShapedGoods("N");
		entity.setSecretPrepaid("Y");
		entity.setActive("Y");
		entity.setForbiddenLine("N");
		// TODO 总运费
		BigDecimal totalFee = BigDecimal.valueOf(Math.round(Math.random() * 1000000));
		entity.setTotalFee(totalFee);
		entity.setCreateTime(new Date());
		entity.setBillTime(new Date());
		entity.setCreateUserCode("092444");
		entity.setCreateOrgCode("W011302020515");
		entity.setCurrencyCode("RMB");
		entity.setIsWholeVehicle("N");
		entity.setWholeVehicleAppfee(BigDecimal.valueOf(Math.round(Math.random() * 1000), 2));
		entity.setBillWeight(BigDecimal.valueOf(Math.round(Math.random() * 1000), 2));
		// TODO
		BigDecimal pickupFee = BigDecimal.valueOf(Math.round(Math.random() * 100));
		entity.setPickupFee(pickupFee);
		entity.setServiceFee(BigDecimal.valueOf(Math.random() * 1000));
		entity.setPreArriveTime(new Date());
		entity.setReceiveCustomerDistCode(" " + Integer.valueOf(Math.round(Math.random() * 1000) + ""));
		entity.setCurrencyCode("RMB");

		/**
		 * 可空字段
		 */
		entity.setOrderNo(null);
		entity.setDeliveryCustomerId(UUIDUtils.getUUID());
		entity.setDeliveryCustomerName(Math.round(Math.random() * 100) + "国 ");
		entity.setDeliveryCustomerProvCode(Math.round(Math.random() * 10) + "省");
		entity.setDeliveryCustomerCityCode(Math.round(Math.random() * 100) + "市");
		entity.setDeliveryCustomerAddress("地址：" + Math.round(Math.random() * 1000));
		entity.setCustomerPickupOrgCode("W011306040304");
		entity.setLoadMethod("配载类型：" + Math.round(Math.random() * 10));
		entity.setTargetOrgCode("W06071401");
		entity.setDriverCode("044983");
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
		entity.setBillingType("VOLUME");
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
		entity.setModifyUserCode("044983");
		entity.setModifyOrgCode("W011303070303");
		entity.setRefId(UUIDUtils.getUUID());
		entity.setRefCode(Math.round(Math.random() * 10) + "");
		entity.setWholeVehicleActualfee(BigDecimal.valueOf(Math.random() * 1000));
		entity.setAccountName(" " + Integer.valueOf(Math.round(Math.random() * 1000) + ""));
		entity.setAccountCode("ICBC:" + Integer.valueOf(Math.round(Math.random() * 1000) + ""));
		entity.setAccountBank("ICBC:" + Integer.valueOf(Math.round(Math.random() * 1000) + ""));
		entity.setTransportType(" " + Integer.valueOf(Math.round(Math.random() * 10) + ""));
		entity.setPrintTimes(Integer.valueOf(Math.round(Math.random() * 10) + ""));
		entity.setAddTime(new Date());
		//entity.setIsPdaBill("Y");
		entity.setDeliveryCustomerDistCode(" " + Integer.valueOf(Math.round(Math.random() * 100) + ""));
		entity.setDeliveryCustomerNationCode("DeliveryCustomerNationCode:" + Integer.valueOf(Math.round(Math.random() * 1000) + ""));
		entity.setReturnBillType("NONE");
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
		chargeDtlEntity1.setActive("Y");
		chargeDtlEntity1.setBillTime(new Date());
		chargeDtlEntity1.setCurrencyCode("RMB");
		chargeDtlEntityList.add(chargeDtlEntity1);

		WaybillChargeDtlEntity chargeDtlEntity2 = new WaybillChargeDtlEntity();
		chargeDtlEntity2.setPricingEntryCode("pricingEntryCode" + Math.random());
		chargeDtlEntity2.setAmount(BigDecimal.valueOf(Math.random() * 1000));
		chargeDtlEntity2.setWaybillNo(waybillNo);
		chargeDtlEntity2.setPricingCriDetailId(UUIDUtils.getUUID());
		chargeDtlEntity2.setCreateTime(new Date());
		chargeDtlEntity2.setModifyTime(new Date());
		chargeDtlEntity2.setActive("Y");
		chargeDtlEntity2.setBillTime(new Date());
		chargeDtlEntity2.setCurrencyCode("RMB");
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
		disDtlEntity1.setActive("Y");
		disDtlEntity1.setBillTime(new Date());
		disDtlEntity1.setCurrencyCode("RMB");
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
		disDtlEntity2.setActive("Y");
		disDtlEntity2.setBillTime(new Date());
		disDtlEntity2.setCurrencyCode("RMB");
		disDtlEntityList.add(disDtlEntity2);

		// =================WoodenRequirementsEntity========================
		WoodenRequirementsEntity woodenRequirementsEntity = new WoodenRequirementsEntity();
		woodenRequirementsEntity.setWaybillNo(waybillNo);
		woodenRequirementsEntity.setPackageOrgCode("W01310305");//TODO
		woodenRequirementsEntity.setStandGoodsNum(Integer.valueOf(Math.round(Math.random() * 100) + ""));
		woodenRequirementsEntity.setStandRequirement("要求");
		woodenRequirementsEntity.setStandGoodsSize("200*100*100*1");
		woodenRequirementsEntity.setStandGoodsVolume(BigDecimal.valueOf(Math.random()));
		woodenRequirementsEntity.setBoxGoodsNum(Integer.valueOf(Math.round(Math.random() * 100) + ""));
		woodenRequirementsEntity.setBoxRequirement("boxRequirement");
		woodenRequirementsEntity.setBoxGoodsSize("boxGoodsSize:200*100*100*1");
		woodenRequirementsEntity.setBoxGoodsVolume(BigDecimal.valueOf(Math.random()));
		woodenRequirementsEntity.setActive("Y");
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
		waybillPaymentEntity1.setActive("Y");
		waybillPaymentEntity1.setBillTime(new Date());
		waybillPaymentEntity1.setCurrencyCode("RMB");
		waybillPaymentEntityList.add(waybillPaymentEntity1);

		WaybillPaymentEntity waybillPaymentEntity2 = new WaybillPaymentEntity();
		waybillPaymentEntity2.setWaybillNo(waybillNo);
		waybillPaymentEntity2.setType("付款类型" + Math.random());
		waybillPaymentEntity2.setAmount(BigDecimal.valueOf(Math.random() * 100));
		waybillPaymentEntity2.setPaymentTime(new Date());
		waybillPaymentEntity2.setCreateTime(new Date());
		waybillPaymentEntity2.setModifyTime(new Date());
		waybillPaymentEntity2.setActive("Y");
		waybillPaymentEntity2.setBillTime(new Date());
		waybillPaymentEntity2.setCurrencyCode("RMB");
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
		user.setActive("Y");

		CurrentInfo currentInfo = new CurrentInfo(user, dept);
		waybillDto.setCurrentInfo(currentInfo);
		waybillDto.setWaybillEntity(entity);
		waybillDto.setWaybillChargeDtlEntity(chargeDtlEntityList);
		waybillDto.setWaybillDisDtlEntity(disDtlEntityList);
		waybillDto.setWaybillPaymentEntity(waybillPaymentEntityList);
		waybillDto.setWoodenRequirementsEntity(woodenRequirementsEntity);
		//waybillManagerService.submitWaybill(waybillDto);
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@" + waybillNo);

		ILoginHessianRemoting logService = 
				DefaultRemoteServiceFactory.getService(ILoginHessianRemoting.class);
		
		//LoginInfo loginfo = logService.userLogin("044983", "qqqqqq");
		
		//System.out.println("User Login Token " + loginfo.getToken());

		
		
		ActualFreightEntity actualFreightEntity = new ActualFreightEntity();
		waybillDto.setActualFreightEntity(actualFreightEntity);
		//运单号
		actualFreightEntity.setWaybillNo(waybillNo);
		//货物名称
		actualFreightEntity.setGoodsName(entity.getGoodsName());
		//重量
		actualFreightEntity.setWeight(entity.getBillWeight());
		//体积
		actualFreightEntity.setVolume(entity.getGoodsVolumeTotal());
		//件数
		actualFreightEntity.setGoodsQty(entity.getGoodsQtyTotal());
		//尺寸
		actualFreightEntity.setDimension( "1*1*1*1");
		//保险声明价值
		actualFreightEntity.setInsuranceValue(entity.getInsuranceAmount());
		//包装费
		actualFreightEntity.setPackingFee( BigDecimal.valueOf(0));
		//送货费
		actualFreightEntity.setDeliverFee(  BigDecimal.valueOf(0));
		//装卸费
		actualFreightEntity.setLaborFee(   BigDecimal.valueOf(0));
		//代收货款
		actualFreightEntity.setCodAmount( BigDecimal.valueOf(0));
		//增值费
		actualFreightEntity.setValueaddFee(  BigDecimal.valueOf(0));
		//公布价运费
		actualFreightEntity.setFreight(  BigDecimal.valueOf(0));
		//结清状态 
		actualFreightEntity.setSettleStatus("N");
		//结清时间
		actualFreightEntity.setSettleTime(new Date());
		//通知状态
		//actualFreightEntity.setNotificationType(vo.get)
		//通知时间
		//actualFreightEntity.setNotificationTime(vo);
		//送货时间 
		//actualFreightEntity.setDeliverDate(vo.getPreCustomerPickupTime());
		//实际送货方式
		actualFreightEntity.setActualDeliverType(entity.getReceiveMethod());
		//运单状态
		actualFreightEntity.setStatus("EFFECTIVE");
		//库存天数
		//actualFreightEntity.setStorageDay(vo.getst)
		//库存费用
		//actualFreightEntity.setStorageCharge(vo.get);
		actualFreightEntity.setStartStockOrgCode(entity.getReceiveOrgCode());
		actualFreightEntity.setEndStockOrgCode(entity.getLastLoadOrgCode());
		//actualFreightEntity.setActualDeliverType(vo.getdelivery)
		// 已生效
		actualFreightEntity.setStatus("EFFECTIVE");
		actualFreightEntity.setArriveGoodsQty(new Integer(0));
		
		actualFreightEntity.setArriveNotoutGoodsQty(new Integer(0));
		
		//保存官网用户名
		IWaybillHessianRemoting waybillRemotingService = 
				DefaultRemoteServiceFactory.getService(IWaybillHessianRemoting.class);
		
		
		waybillRemotingService.submitWaybill(waybillDto);
		
		
		
	 }
	 
	 

	
}
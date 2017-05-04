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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/consumer/test/service/WaybillPickupServiceTest.java
 * 
 * FILE NAME        	: WaybillPickupServiceTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.consumer.test.service;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.math.NumberUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.service.IWaybillPickupService;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.WaybillPickupInfoDto;
import com.deppon.foss.module.settlement.consumer.test.BaseTestCase;
import com.deppon.foss.module.settlement.consumer.test.util.ConsumerTestUtil;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;


/**
 * 开单服务测试类
 * @author ibm-zhuwei
 * @date 2012-10-25 下午6:08:12
 */
public class WaybillPickupServiceTest extends BaseTestCase {

	@Autowired
	private IWaybillPickupService waybillPickupService;
	
	private static final String WAYBILL_NO = "12345678";
	private static final String CUST_CODE = "CUST_CODE";
	private static final String ORIG_ORG_CODE = "W011302020103";
	private static final String DEST_ORG_CODE = "W011302020103";
	
	@Before
	public void setUp() {
		this.simpleJdbcTemplate.update("delete from t_stl_bill_cash_collection where source_bill_no = ?", WAYBILL_NO);
		this.simpleJdbcTemplate.update("delete from t_stl_bill_receivable where source_bill_no = ?", WAYBILL_NO);
		this.simpleJdbcTemplate.update("delete from t_stl_bill_receivable where waybill_no = ?", WAYBILL_NO);
		this.simpleJdbcTemplate.update("delete from t_stl_bill_payable where source_bill_no = ?", WAYBILL_NO);
		this.simpleJdbcTemplate.update("delete from t_stl_cod where waybill_no = ?", WAYBILL_NO);
		this.simpleJdbcTemplate.update("update T_STL_CREDIT_CUSTOMER set used_amount = 0 where customer_code = ?", CUST_CODE);
		this.simpleJdbcTemplate.update("update T_STL_CREDIT_ORG set used_amount = 0 where org_code = ?", ORIG_ORG_CODE);
		
//		this.executeSqlScript("classpath:com/deppon/foss/module/settlement/common/test/META-INF/sql/bill_cash_collection.sql", true);
	}
	
	/**
	 * 开单新增运单(全额现金)
	 * @author ibm-zhuwei
	 * @date 2012-10-25 下午6:07:04
	 */
	@Test
	@Rollback(false)
	public void testAddWaybill1() {
		this.logger.info("...");
		// 只生成现金收款单
		WaybillPickupInfoDto item = this.getWaybillDto();

		item.setPaidMethod(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH);
		
		item.setTotalFee(NumberUtils.createBigDecimal("100.50"));// 现金总额
		item.setTransportFee(NumberUtils.createBigDecimal("100.50"));	// 公布价运费
		item.setPickupFee(BigDecimal.ZERO);	// 接货费
		item.setDeliveryGoodsFee(BigDecimal.ZERO);	// 送货费
		item.setPackageFee(BigDecimal.ZERO);	// 包装手续费
		item.setCodFee(BigDecimal.ZERO);	// 代收货款费
		item.setInsuranceFee(BigDecimal.ZERO);	// 保价费
		item.setOtherFee(BigDecimal.ZERO);	// 其他费用
		item.setValueAddFee(BigDecimal.ZERO);	// 增值费用
		item.setPromotionsFee(BigDecimal.ZERO);	// 优惠费用
		
		item.setPrePayAmount(NumberUtils.createBigDecimal("100.50"));	// 预收金额
		item.setToPayAmount(BigDecimal.ZERO);	// 到付金额
		item.setCodAmount(BigDecimal.ZERO);
		
		item.setServiceFee(NumberUtils.createBigDecimal("1.00"));	// 装卸费
		
		waybillPickupService.addWaybill(item,ConsumerTestUtil.getCurrentInfo());
	}

	/**
	 * 开单新增运单(全额到付)
	 * @author ibm-zhuwei
	 * @date 2012-10-25 下午6:07:04
	 */
	@Test()
//	@Test(timeout=5000,expected=SettlementException.class)
	@Rollback(false)
	public void testAddWaybill2() {
		this.logger.info("...");
		
		// 只生成到付应收单
		WaybillPickupInfoDto item = this.getWaybillDto();

		item.setPaidMethod(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__FREIGHT_COLLECT);
		
		item.setTotalFee(NumberUtils.createBigDecimal("100.50"));// 现金总额
		item.setTransportFee(NumberUtils.createBigDecimal("100.50"));	// 公布价运费
		item.setPickupFee(BigDecimal.ZERO);	// 接货费
		item.setDeliveryGoodsFee(BigDecimal.ZERO);	// 送货费
		item.setPackageFee(BigDecimal.ZERO);	// 包装手续费
		item.setCodFee(BigDecimal.ZERO);	// 代收货款费
		item.setInsuranceFee(BigDecimal.ZERO);	// 保价费
		item.setOtherFee(BigDecimal.ZERO);	// 其他费用
		item.setValueAddFee(BigDecimal.ZERO);	// 增值费用
		item.setPromotionsFee(BigDecimal.ZERO);	// 优惠费用
		
		item.setPrePayAmount(BigDecimal.ZERO);	// 预收金额
		item.setToPayAmount(NumberUtils.createBigDecimal("100.50"));	// 到付金额
		item.setCodAmount(BigDecimal.ZERO);
		
		waybillPickupService.addWaybill(item,ConsumerTestUtil.getCurrentInfo());
	}

	/**
	 * 开单新增运单(全额临欠)
	 * @author ibm-zhuwei
	 * @date 2012-10-25 下午6:07:04
	 */
	@Test()
	@Rollback(false)
	public void testAddWaybill3() {
		this.logger.info("...");
		
		// 只生成始发应收单
		WaybillPickupInfoDto item = this.getWaybillDto();

		// 临欠扣部门,月结扣客户
		item.setPaidMethod(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT);	// 临欠
		
		item.setTotalFee(NumberUtils.createBigDecimal("100.50"));// 现金总额
		item.setTransportFee(NumberUtils.createBigDecimal("100.50"));	// 公布价运费
		item.setPickupFee(BigDecimal.ZERO);	// 接货费
		item.setDeliveryGoodsFee(BigDecimal.ZERO);	// 送货费
		item.setPackageFee(BigDecimal.ZERO);	// 包装手续费
		item.setCodFee(BigDecimal.ZERO);	// 代收货款费
		item.setInsuranceFee(BigDecimal.ZERO);	// 保价费
		item.setOtherFee(BigDecimal.ZERO);	// 其他费用
		item.setValueAddFee(BigDecimal.ZERO);	// 增值费用
		item.setPromotionsFee(BigDecimal.ZERO);	// 优惠费用
		
		item.setPrePayAmount(NumberUtils.createBigDecimal("100.50"));	// 预收金额
		item.setToPayAmount(BigDecimal.ZERO);	// 到付金额
		item.setCodAmount(BigDecimal.ZERO);
		
		waybillPickupService.addWaybill(item,ConsumerTestUtil.getCurrentInfo());
	}

	/**
	 * 开单新增运单(全额月结)
	 * @author ibm-zhuwei
	 * @date 2012-10-25 下午6:07:04
	 */
	@Test()
	@Rollback(false)
	@Ignore
	public void testAddWaybill4() {
		this.logger.info("...");
		
		// 只生成始发应收单
		WaybillPickupInfoDto item = this.getWaybillDto();

		// 临欠扣部门,月结扣客户
		item.setPaidMethod(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT);	// 月结
		
		item.setTotalFee(NumberUtils.createBigDecimal("100.50"));// 现金总额
		item.setTransportFee(NumberUtils.createBigDecimal("100.50"));	// 公布价运费
		item.setPickupFee(BigDecimal.ZERO);	// 接货费
		item.setDeliveryGoodsFee(BigDecimal.ZERO);	// 送货费
		item.setPackageFee(BigDecimal.ZERO);	// 包装手续费
		item.setCodFee(BigDecimal.ZERO);	// 代收货款费
		item.setInsuranceFee(BigDecimal.ZERO);	// 保价费
		item.setOtherFee(BigDecimal.ZERO);	// 其他费用
		item.setValueAddFee(BigDecimal.ZERO);	// 增值费用
		item.setPromotionsFee(BigDecimal.ZERO);	// 优惠费用
		
		item.setPrePayAmount(NumberUtils.createBigDecimal("100.50"));	// 预收金额
		item.setToPayAmount(BigDecimal.ZERO);	// 到付金额
		item.setCodAmount(BigDecimal.ZERO);
		
		waybillPickupService.addWaybill(item,ConsumerTestUtil.getCurrentInfo());
	}

	/**
	 * 开单新增运单(始发现金+到付)
	 * @author ibm-zhuwei
	 * @date 2012-10-25 下午6:07:04
	 */
	@Test()
	@Rollback(false)
	public void testAddWaybill5() {
		this.logger.info("...");
		
		// 只生成始发应收单
		WaybillPickupInfoDto item = this.getWaybillDto();

		// 临欠扣部门,月结扣客户
		item.setPaidMethod(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__FREIGHT_COLLECT);	// 到付
		
		item.setTotalFee(NumberUtils.createBigDecimal("100.50"));// 现金总额
		item.setTransportFee(NumberUtils.createBigDecimal("85.50"));	// 公布价运费
		item.setPickupFee(NumberUtils.createBigDecimal("1.00"));	// 接货费
		item.setDeliveryGoodsFee(NumberUtils.createBigDecimal("2.00"));	// 送货费
		item.setPackageFee(NumberUtils.createBigDecimal("3.00"));	// 包装手续费
		item.setCodFee(BigDecimal.ZERO);	// 代收货款费
		item.setInsuranceFee(NumberUtils.createBigDecimal("4.00"));	// 保价费
		item.setOtherFee(NumberUtils.createBigDecimal("5.00"));	// 其他费用
		item.setValueAddFee(NumberUtils.createBigDecimal("15.00"));	// 增值费用
		item.setPromotionsFee(BigDecimal.ZERO);	// 优惠费用
		
		item.setPrePayAmount(NumberUtils.createBigDecimal("50.50"));	// 预收金额
		item.setToPayAmount(NumberUtils.createBigDecimal("50"));	// 到付金额
		item.setCodAmount(BigDecimal.ZERO);
		
		// 新增
		waybillPickupService.addWaybill(item,ConsumerTestUtil.getCurrentInfo());
		
		waybillPickupService.canChange(WAYBILL_NO);
		
		waybillPickupService.cancelWaybill(WAYBILL_NO,ConsumerTestUtil.getCurrentInfo());
	}

	/**
	 * 开单新增运单(始发临欠+到付)
	 * @author ibm-zhuwei
	 * @date 2012-10-25 下午6:07:04
	 */
	@Test()
	@Rollback(false)
	public void testAddWaybill6() {
		this.logger.info("...");
		
		// 只生成始发应收单
		WaybillPickupInfoDto item = this.getWaybillDto();

		// 临欠扣部门,月结扣客户
		item.setPaidMethod(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT);	// 临欠
		
		item.setTotalFee(NumberUtils.createBigDecimal("100.50"));// 现金总额
		item.setTransportFee(NumberUtils.createBigDecimal("85.50"));	// 公布价运费
		item.setPickupFee(NumberUtils.createBigDecimal("1.00"));	// 接货费
		item.setDeliveryGoodsFee(NumberUtils.createBigDecimal("2.00"));	// 送货费
		item.setPackageFee(NumberUtils.createBigDecimal("3.00"));	// 包装手续费
		item.setCodFee(BigDecimal.ZERO);	// 代收货款费
		item.setInsuranceFee(NumberUtils.createBigDecimal("4.00"));	// 保价费
		item.setOtherFee(NumberUtils.createBigDecimal("5.00"));	// 其他费用
		item.setValueAddFee(NumberUtils.createBigDecimal("15.00"));	// 增值费用
		item.setPromotionsFee(BigDecimal.ZERO);	// 优惠费用
		
		item.setPrePayAmount(NumberUtils.createBigDecimal("50.50"));	// 预收金额
		item.setToPayAmount(NumberUtils.createBigDecimal("50"));	// 到付金额
		item.setCodAmount(BigDecimal.ZERO);
		
		// 新增
		waybillPickupService.addWaybill(item,ConsumerTestUtil.getCurrentInfo());
		
		waybillPickupService.canChange(WAYBILL_NO);
		
		WaybillPickupInfoDto newItem = new WaybillPickupInfoDto();
		BeanUtils.copyProperties(item, newItem);
		
		BigDecimal promotionsFee = NumberUtils.createBigDecimal("1");
		newItem.setPromotionsFee(promotionsFee);
		newItem.setTotalFee(newItem.getTotalFee().subtract(promotionsFee));
		newItem.setPrePayAmount(newItem.getPrePayAmount().subtract(promotionsFee));
		
		waybillPickupService.modifyWaybill(item, newItem,ConsumerTestUtil.getCurrentInfo());
	}

	/**
	 * 开单新增运单(始发现金+到付+代收)
	 * @author ibm-zhuwei
	 * @date 2012-10-25 下午6:07:04
	 */
	@Test()
	@Rollback(false)
	public void testAddWaybill7() {
		this.logger.info("...");
		
		// 只生成始发应收单
		WaybillPickupInfoDto item = this.getWaybillDto();

		item.setRefundType(SettlementDictionaryConstants.COD__COD_TYPE__RETURN_APPROVE);
		
		// 临欠扣部门,月结扣客户
		item.setPaidMethod(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__FREIGHT_COLLECT);	// 到付
		
		item.setTotalFee(NumberUtils.createBigDecimal("2105.50"));// 现金总额
		item.setTransportFee(NumberUtils.createBigDecimal("85.50"));	// 公布价运费
		item.setPickupFee(NumberUtils.createBigDecimal("1.00"));	// 接货费
		item.setDeliveryGoodsFee(NumberUtils.createBigDecimal("2.00"));	// 送货费
		item.setPackageFee(NumberUtils.createBigDecimal("3.00"));	// 包装手续费
		item.setCodFee(NumberUtils.createBigDecimal("5.00"));	// 代收货款费
		item.setInsuranceFee(NumberUtils.createBigDecimal("4.00"));	// 保价费
		item.setOtherFee(NumberUtils.createBigDecimal("5.00"));	// 其他费用
		item.setValueAddFee(NumberUtils.createBigDecimal("20.00"));	// 增值费用
		item.setPromotionsFee(BigDecimal.ZERO);	// 优惠费用
		item.setCodAmount(NumberUtils.createBigDecimal("2000.00"));
		
		item.setPrePayAmount(NumberUtils.createBigDecimal("55.50"));	// 预收金额
		item.setToPayAmount(NumberUtils.createBigDecimal("2050"));	// 到付金额
		try{//TODO  加入异常捕获有点问题
			waybillPickupService.addWaybill(item,ConsumerTestUtil.getCurrentInfo());
		}catch(SettlementException e){
			e.printStackTrace();
		}
	}

	
	@Test()
	public void testScale() {
		BigDecimal fee = BigDecimal.ZERO;
		
		fee = new BigDecimal("8").divide(new BigDecimal("7"), 2, BigDecimal.ROUND_FLOOR);
		logger.info("fee : " + fee);
		
		fee = new BigDecimal("12").divide(new BigDecimal("7"), 2, BigDecimal.ROUND_FLOOR);
		logger.info("fee : " + fee);
	}
	
	private WaybillPickupInfoDto getWaybillDto() {
		
		Date now = new Date();
		WaybillPickupInfoDto item = new WaybillPickupInfoDto();
		
		item.setId(UUIDUtils.getUUID());
		item.setWaybillNo(WAYBILL_NO);
		item.setOrderNo("ORD12345678");
		item.setDeliveryCustomerId("cust_id1");
		item.setDeliveryCustomerCode("cust_id1");
		item.setDeliveryCustomerName("cust_name1");
		item.setDeliveryCustomerMobilephone("cust_mobile1");
		item.setDeliveryCustomerPhone("cust_phone1");
		item.setDeliveryCustomerContact("cust_contact1");
		item.setDeliveryCustomerNationCode(null);
		item.setDeliveryCustomerProvCode(null);
		item.setDeliveryCustomerCityCode(null);
//		item.setDeliveryCustomerCountyCode(null);
		item.setDeliveryCustomerAddress(null);
		item.setReceiveCustomerId("cust_id");
		item.setReceiveCustomerCode(CUST_CODE);
		item.setReceiveCustomerName("cust_name");
		item.setReceiveCustomerMobilephone(null);
		item.setReceiveCustomerPhone(null);
		item.setReceiveCustomerContact("cust_contact");
		item.setReceiveCustomerNationCode(null);
		item.setReceiveCustomerProvCode(null);
		item.setReceiveCustomerCityCode(null);
//		item.setReceiveCustomerCountyCode(null);
		item.setReceiveCustomerAddress(null);
		item.setReceiveOrgCode(ORIG_ORG_CODE);
		item.setProductId("prod_code");
		item.setProductCode("prod_code");
		item.setReceiveMethod(null);
		item.setCustomerPickupOrgCode("001");
		item.setLoadMethod(null);
		item.setTargetOrgCode("002");
		item.setPickupToDoor(null);
		item.setDriverCode(null);
		item.setPickupCentralized(null);
		item.setLoadLineCode(null);
		item.setLoadOrgCode(null);
		item.setLastLoadOrgCode(DEST_ORG_CODE);
		item.setPreDepartureTime(null);
		item.setPreCustomerPickupTime(null);
		item.setCarDirectDelivery(null);
		item.setGoodsName(null);
		item.setGoodsQtyTotal(null);
		item.setGoodsWeightTotal(null);
		item.setGoodsVolumeTotal(null);
		item.setGoodsSize(null);
		item.setGoodsTypeCode(null);
		item.setPreciousGoods(null);
		item.setSpecialShapedGoods(null);
		item.setOuterNotes(null);
		item.setInnerNotes(null);
		item.setGoodsPackage(null);
		item.setInsuranceAmount(null);
		item.setInsuranceRate(null);
		item.setInsuranceFee(null);
		item.setCodAmount(null);
		item.setCodRate(null);
		item.setCodFee(null);
		item.setRefundType(null);
		item.setReturnBillType(null);
		item.setSecretPrepaid(null);
		item.setToPayAmount(null);
		item.setPrePayAmount(null);
		item.setDeliveryGoodsFee(null);
		item.setOtherFee(null);
		item.setPromotionsFee(null);
		item.setBillingType(null);
		item.setUnitPrice(null);
		
		item.setArriveType(null);
		item.setActive(FossConstants.ACTIVE);
		item.setForbiddenLine(null);
		item.setFreightMethod(null);
		item.setFlightShift(null);
		
		item.setPromotionsCode(null);
//		item.setBeginTime(null);
//		item.setEndTime(null);
		item.setCreateTime(now);
		item.setModifyTime(now);
		item.setBillTime(now);
		item.setCreateUserCode(null);
		item.setModifyUserCode(null);
		item.setCreateOrgCode("create_org_code");
		item.setModifyOrgCode(null);
		item.setRefId(null);
		item.setRefCode(null);
		item.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		item.setIsWholeVehicle(null);
		item.setWholeVehicleAppfee(null);
		item.setWholeVehicleActualfee(null);
//		item.setPackageOrgCode(null);
//		item.setStandGoodsNum(null);
//		item.setStandRequirement(null);
//		item.setStandGoodsSize(null);
//		item.setStandGoodsVolume(null);
//		item.setBoxGoodsNum(null);
//		item.setBoxRequirement(null);
//		item.setBoxGoodsSize(null);
//		item.setBoxGoodsVolume(null);
		item.setAccountName(null);
		item.setAccountCode(null);
		item.setAccountBank("account_bank");
		item.setBillWeight(null);
		item.setServiceFee(null);
		item.setPreArriveTime(null);
		item.setTransportType("trans_type");
		item.setPrintTimes(null);
		item.setAddTime(null);

		return item;
	}
	
	
	
	@Test
	@Rollback(false)
	public void testAddWaybil20() {
		this.logger.info("...");
		
		// 只生成始发应收单
		WaybillPickupInfoDto item = this.getWaybillDto();

		// 临欠扣部门,月结扣客户
		item.setPaidMethod(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__FREIGHT_COLLECT);	// 到付
		
		item.setTotalFee(NumberUtils.createBigDecimal("120"));// 现金总额
		item.setTransportFee(NumberUtils.createBigDecimal("20"));	// 公布价运费
		item.setPickupFee(NumberUtils.createBigDecimal("10"));	// 接货费
		item.setDeliveryGoodsFee(NumberUtils.createBigDecimal("10"));	// 送货费
		item.setPackageFee(NumberUtils.createBigDecimal("20"));	// 包装手续费
		item.setCodFee(NumberUtils.createBigDecimal("20"));	// 代收货款费
		item.setInsuranceFee(NumberUtils.createBigDecimal("20"));	// 保价费
		item.setOtherFee(NumberUtils.createBigDecimal("20"));	// 其他费用
		item.setValueAddFee(NumberUtils.createBigDecimal("100"));	// 增值费用
		item.setPromotionsFee(NumberUtils.createBigDecimal("10"));	// 优惠费用
		
		item.setPrePayAmount(NumberUtils.createBigDecimal("110"));	// 预收金额
		item.setToPayAmount(new BigDecimal("10"));	// 到付金额
		item.setCodAmount(BigDecimal.ZERO);
		
		waybillPickupService.addWaybill(item,ConsumerTestUtil.getCurrentInfo());
	}
	
}

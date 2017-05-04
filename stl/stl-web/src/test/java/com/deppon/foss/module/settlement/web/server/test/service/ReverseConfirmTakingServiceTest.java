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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/web/server/test/service/ReverseConfirmTakingServiceTest.java
 * 
 * FILE NAME        	: ReverseConfirmTakingServiceTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.web.server.test.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.deppon.foss.module.settlement.agency.api.server.service.IVehicleAgencyExternalService;
import com.deppon.foss.module.settlement.agency.api.shared.dto.SettlementExternalBillDto;
import com.deppon.foss.module.settlement.common.api.server.service.IBillBadAccountService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillBadAccountEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillReceivableConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.LineSignDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.service.ILineSignService;
import com.deppon.foss.module.settlement.web.test.BaseTestCase;
import com.deppon.foss.module.settlement.web.test.util.StlWebTestUtil;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 反签收测试用例     结合测试人员要求进行编写测试用例，
 * 有测试人员通过运单录入页面进行开单，按照测试发给测试案例步骤进行测试
 * 
 * 
 * @author 099995-foss-wujiangtao
 * @date 2013-1-12 上午9:28:57
 * @since
 * @version
 */
public class ReverseConfirmTakingServiceTest extends BaseTestCase{
	
	/**
	 * 签收Service
	 */
	@Autowired
	private ILineSignService lineSignService;
	
	/**
	 * 应收单公用Service
	 */
	@Autowired
	private IBillReceivableService billReceivableService;
	
	@Autowired
	private IBillPayableService billPayableService;
	
	@Autowired
	private ISettlementCommonService settlementCommonService;
	
	@Autowired
	private IVehicleAgencyExternalService vehicleAgencyExternalService;
	
	@Autowired
	private IBillBadAccountService  billBadAccountService;
	
	/**
	 * 各种参数为空-签收测试
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-12 上午10:12:27
	 */
	public void pubConfirmTaking(){
		String waybillNo="";
		String signOrgCode="";
		String signOrgName="";
		String signType="";
		
		this.pubConfirmTaking(waybillNo, signOrgCode, signOrgName, signType);
	}
	
	
	/**
	 * 各种参数为空-反签收测试
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-12 上午10:10:07
	 */
	@Test
	@Rollback(true)
	public void testReverseConfirmTaking(){
		String waybillNo="";
		String signOrgCode="";
		String signOrgName="";
		String signType="";
		this.reverseConfirmTaking(waybillNo, signOrgCode,signOrgName,signType);
	}
	
/*********************************************************空运部分*******************************/
	
	/**
	 * 空运签收
	 * 
	 * 空运部门：W31000206090611，上海空运总调
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-12 上午11:53:16
	 */
	@Test
	@Rollback(true)
	public void testAirPubConfirmTaking(){
		//String waybillNo="50001951";
		String signOrgCode="W31000206090611";
		String signOrgName="上海空运总调";
		String signType=SettlementConstants.AIR_SIGN;//空运
	//	this.pubConfirmTaking(waybillNo, signOrgCode, signOrgName, signType);
		
		String[] waybillNos={"50001951","50001960","50001952","50001953","50001959","50001961","50001962","50001963","50001964","50001965"};
		for(int i=0;i<waybillNos.length;i++){
			this.pubConfirmTaking(waybillNos[i], signOrgCode, signOrgName, signType);
		}
	}
	
	
	/**
	 * 空运反签收测试通过
	 * 
	 * 50001951  反签收通过
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-12 上午10:10:07
	 */
	@Test
	@Rollback(true)
	public void testAirReverseConfirmTaking(){
		String waybillNo="50001963";
		String signOrgCode="W31000206090611";
		String signOrgName="上海空运总调";
		String signType=SettlementConstants.AIR_SIGN;//空运
		this.reverseConfirmTaking(waybillNo, signOrgCode, signOrgName, signType);
	}
	
	/**
	 * 空运反签收测试不通过---已经反签收了，再次调用反签收接口
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-12 下午3:34:15
	 */
	@Test
	@Rollback(true)
	public void testAirReverseConfirmTakingOne(){
		String waybillNo="50001951";
		String signOrgCode="W31000206090611";
		String signOrgName="上海空运总调";
		String signType=SettlementConstants.AIR_SIGN;//空运
		this.reverseConfirmTaking(waybillNo, signOrgCode, signOrgName, signType);
		this.reverseConfirmTaking(waybillNo, signOrgCode, signOrgName, signType);
	}
	
	
	/**
	 * 反签收 不通过--- 插入重复的现金收款单数据
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-12 下午3:39:27
	 */
	@Test
	@Rollback(true)
	public void testAirReverseConfirmTakingTwo(){
		//'50001962'
		String waybillNo="50001962";
		String signOrgCode="W31000206090611";
		String signOrgName="上海空运总调";
		String signType=SettlementConstants.AIR_SIGN;//空运
		
		//插入一条相同运单号的现金收款单记录 
		String sql=" insert into STL.T_STL_BILL_CASH_COLLECTION (ID, CASH_COLLECTION_NO, SOURCE_BILL_NO, WAYBILL_ID, SOURCE_BILL_TYPE, CUSTOMER_CODE, CUSTOMER_NAME, CREATE_ORG_CODE, CREATE_ORG_NAME, COLLECTION_ORG_CODE, COLLECTION_ORG_NAME, COLLECTION_COMPANY_CODE, COLLECTION_COMPANY_NAME, GENERATING_ORG_CODE, GENERATING_ORG_NAME, GENERATING_COMPANY_CODE, GENERATING_COMPANY_NAME, AMOUNT, CURRENCY_CODE, PRODUCT_CODE, PRODUCT_ID, TRANSPORT_FEE, PICKUP_FEE, DELIVERY_GOODS_FEE, PACKAGING_FEE, COD_FEE, INSURANCE_FEE, OTHER_FEE, VALUE_ADD_FEE, PROMOTIONS_FEE, SMALL_FEE, CONREVEN_DATE, BILL_TYPE, STATUS, ACTIVE, IS_RED_BACK, PAYMENT_TYPE, CREATE_USER_CODE, CREATE_USER_NAME, AUDIT_USER_CODE, AUDIT_USER_NAME, MODIFY_USER_CODE, MODIFY_USER_NAME, DISABLE_USER_CODE, DISABLE_USER_NAME, CASH_CONFIRM_USER_CODE, CASH_CONFIRM_USER_NAME, BUSINESS_DATE, ACCOUNT_DATE, CREATE_TIME, MODIFY_TIME, DISABLE_TIME, CASH_CONFIRM_TIME, IS_INIT, NOTES, VERSION_NO, WAYBILL_NO, COLLECTION_TYPE, ORIG_ORG_CODE, ORIG_ORG_NAME, DEST_ORG_CODE, DEST_ORG_NAME)"+
			    " values (sys_guId(), 'XS110018679', '50001962', '1d779495-8ca6-4b69-8a47-825c055f0025', 'W', '400233719', '吴华娜', 'W011302020106', '上海浦东花木营业部', 'W011302020106', '上海浦东花木营业部', 'ZGS001', '上海精准德邦物流责任有限公司', 'W011302020106', '上海浦东花木营业部', 'ZGS001', '上海精准德邦物流责任有限公司', 40000, 'RMB', 'C30001', 'C30001', 33111, 0, 0, 0, 3913, 939, 2037, 6889, 0, null, to_date('12-01-2013 14:28:04', 'dd-mm-yyyy hh24:mi:ss'), 'C', 'S', 'Y', 'N', 'CH', '999999', '倩倩', '', '', '000123', '张三', '', '', '', '', to_date('12-01-2013 12:39:46', 'dd-mm-yyyy hh24:mi:ss'), to_date('12-01-2013 12:39:49', 'dd-mm-yyyy hh24:mi:ss'), to_date('12-01-2013 12:39:50', 'dd-mm-yyyy hh24:mi:ss'), to_date('12-01-2013 14:28:04', 'dd-mm-yyyy hh24:mi:ss'), null, null, 'N', '', 2, '50001962', '', 'W011302020106', '上海浦东花木营业部', 'W31000206090611', '上海空运总调')";
		this.simpleJdbcTemplate.getJdbcOperations().execute(sql);
		
		//反签收
		this.reverseConfirmTaking(waybillNo, signOrgCode, signOrgName, signType);
	}
	
	/**
	 * 空运反签收不通过-插入一条重复的代收货款应收单
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-12 下午3:45:05
	 */
	@Test
	@Rollback(true)
	public void testAirReverseConfirmTakingThree(){
		String waybillNo="50001953";
		String signOrgCode="W31000206090611";
		String signOrgName="上海空运总调";
		String signType=SettlementConstants.AIR_SIGN;//空运
		
		//插入一条相同运单号的代收货款应收单记录 
		String sql=" insert into STL.t_stl_bill_receivable (ID, RECEIVABLE_NO, WAYBILL_NO, WAYBILL_ID, CREATE_TYPE, SOURCE_BILL_NO, SOURCE_BILL_TYPE, BILL_TYPE, RECEIVABLE_ORG_CODE, RECEIVABLE_ORG_NAME, GENERATING_ORG_CODE, GENERATING_ORG_NAME, GENERATING_COM_CODE, GENERATING_COM_NAME, DUNNING_ORG_CODE, DUNNING_ORG_NAME, ORIG_ORG_CODE, ORIG_ORG_NAME, DEST_ORG_CODE, DEST_ORG_NAME, CUSTOMER_CODE, CUSTOMER_NAME, DELIVERY_CUSTOMER_CODE, DELIVERY_CUSTOMER_NAME, RECEIVE_CUSTOMER_CODE, RECEIVE_CUSTOMER_NAME, AMOUNT, VERIFY_AMOUNT, UNVERIFY_AMOUNT, CURRENCY_CODE, BUSINESS_DATE, ACCOUNT_DATE, CONREVEN_DATE, PAYMENT_TYPE, PRODUCT_CODE, PRODUCT_ID, TO_PAY_AMOUNT, PRE_PAY_AMOUNT, TRANSPORT_FEE, PICKUP_FEE, DELIVERY_GOODS_FEE, PACKAGING_FEE, COD_FEE, INSURANCE_FEE, OTHER_FEE, VALUE_ADD_FEE, PROMOTIONS_FEE, GOODS_NAME, GOODS_VOLUME_TOTAL, BILL_WEIGHT, RECEIVE_METHOD, CUSTOMER_PICKUP_ORG_CODE, GOODS_QTY_TOTAL, TARGET_ORG_CODE, VERSION_NO, ACTIVE, IS_RED_BACK, IS_INIT, CREATE_USER_CODE, CREATE_USER_NAME, CREATE_ORG_CODE, CREATE_ORG_NAME, CREATE_TIME, MODIFY_TIME, MODIFY_USER_CODE, MODIFY_USER_NAME, STATEMENT_BILL_NO, UNLOCK_DATE_TIME, LOCK_CUSTOMER_CODE, LOCK_CUSTOMER_NAME, COLLECTION_TYPE, COLLECTION_NAME, AUDIT_USER_CODE, AUDIT_USER_NAME, APPROVE_STATUS, AUDIT_DATE, IS_DISABLE, DISABLE_USER_CODE, DISABLE_USER_NAME, DISABLE_TIME, NOTES) "+
		" values (sys_guId(), 'YS610038942', '50001953', '1de50f83-4c07-46c9-b9fc-47fd4e7f05f5', 'A', '50001953', 'W', 'CR', 'W31000206090611', '上海空运总调', '', '', '', '', '', '', 'W011302020106', '上海浦东花木营业部', 'W31000206090611', '上海空运总调', '', '', '400233719', '吴华娜', '', '', 400000, 0, 400000, 'RMB', to_date('12-01-2013 10:50:22', 'dd-mm-yyyy hh24:mi:ss'), to_date('12-01-2013 10:50:25', 'dd-mm-yyyy hh24:mi:ss'), to_date('12-01-2013 14:28:04', 'dd-mm-yyyy hh24:mi:ss'), 'FC', 'C30001', '', null, null, null, null, null, null, null, null, null, null, null, '', null, null, '', '', null, '北京西城区西站营业部', 2, 'Y', 'N', 'N', '999999', '倩倩', 'W011302020106', '上海浦东花木营业部', to_date('12-01-2013 10:50:25', 'dd-mm-yyyy hh24:mi:ss'), to_date('12-01-2013 14:28:04', 'dd-mm-yyyy hh24:mi:ss'), '000123', '张三', 'N/A', null, '', '', '', '', '', '', '', null, '', '', '', null, '')  ";
		this.simpleJdbcTemplate.getJdbcOperations().execute(sql);
		
		//反签收
		this.reverseConfirmTaking(waybillNo, signOrgCode, signOrgName, signType);
	}
	
	/**
	 * 空运反签收不通过-插入一条重复的到付运费应收单
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-12 下午3:45:05
	 */
	@Test
	@Rollback(true)
	public void testAirReverseConfirmTakingSeven(){
		String waybillNo="50001961";
		String signOrgCode="W31000206090611";
		String signOrgName="上海空运总调";
		String signType=SettlementConstants.AIR_SIGN;//空运
		
		//插入一条相同运单号的到付运费应收单
		String sql=" insert into STL.t_stl_bill_receivable (ID, RECEIVABLE_NO, WAYBILL_NO, WAYBILL_ID, CREATE_TYPE, SOURCE_BILL_NO, SOURCE_BILL_TYPE, BILL_TYPE, RECEIVABLE_ORG_CODE, RECEIVABLE_ORG_NAME, GENERATING_ORG_CODE, GENERATING_ORG_NAME, GENERATING_COM_CODE, GENERATING_COM_NAME, DUNNING_ORG_CODE, DUNNING_ORG_NAME, ORIG_ORG_CODE, ORIG_ORG_NAME, DEST_ORG_CODE, DEST_ORG_NAME, CUSTOMER_CODE, CUSTOMER_NAME, DELIVERY_CUSTOMER_CODE, DELIVERY_CUSTOMER_NAME, RECEIVE_CUSTOMER_CODE, RECEIVE_CUSTOMER_NAME, AMOUNT, VERIFY_AMOUNT, UNVERIFY_AMOUNT, CURRENCY_CODE, BUSINESS_DATE, ACCOUNT_DATE, CONREVEN_DATE, PAYMENT_TYPE, PRODUCT_CODE, PRODUCT_ID, TO_PAY_AMOUNT, PRE_PAY_AMOUNT, TRANSPORT_FEE, PICKUP_FEE, DELIVERY_GOODS_FEE, PACKAGING_FEE, COD_FEE, INSURANCE_FEE, OTHER_FEE, VALUE_ADD_FEE, PROMOTIONS_FEE, GOODS_NAME, GOODS_VOLUME_TOTAL, BILL_WEIGHT, RECEIVE_METHOD, CUSTOMER_PICKUP_ORG_CODE, GOODS_QTY_TOTAL, TARGET_ORG_CODE, VERSION_NO, ACTIVE, IS_RED_BACK, IS_INIT, CREATE_USER_CODE, CREATE_USER_NAME, CREATE_ORG_CODE, CREATE_ORG_NAME, CREATE_TIME, MODIFY_TIME, MODIFY_USER_CODE, MODIFY_USER_NAME, STATEMENT_BILL_NO, UNLOCK_DATE_TIME, LOCK_CUSTOMER_CODE, LOCK_CUSTOMER_NAME, COLLECTION_TYPE, COLLECTION_NAME, AUDIT_USER_CODE, AUDIT_USER_NAME, APPROVE_STATUS, AUDIT_DATE, IS_DISABLE, DISABLE_USER_CODE, DISABLE_USER_NAME, DISABLE_TIME, NOTES)"+"values (sys_guId(), 'YS210020265', '50001961', '99b0b0ba-eaf6-4e98-a86a-be8aec471bc2', 'A', '50001961', 'W', 'AA', 'W31000206090611', '上海空运总调', 'W011302020106', '上海浦东花木营业部', 'ZGS001', '上海精准德邦物流责任有限公司', 'W31000206090611', '上海空运总调', 'W011302020106', '上海浦东花木营业部', 'W31000206090611', '上海空运总调', '', '', '400233719', '吴华娜', '', '', 44600, 0, 44600, 'RMB', to_date('12-01-2013 12:37:36', 'dd-mm-yyyy hh24:mi:ss'), to_date('12-01-2013 12:37:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('12-01-2013 14:28:04', 'dd-mm-yyyy hh24:mi:ss'), 'FC', 'C30001', 'C30001', null, null, 37500, 0, 0, 0, 2500, 2000, 2600, 7100, 0, '键盘', 1.786, 0.000, 'SELF_PICKUP', 'W011305080203', 12, '北京西城区西站营业部', 2, 'Y', 'N', 'N', '999999', '倩倩', 'W011302020106', '上海浦东花木营业部', to_date('12-01-2013 12:37:39', 'dd-mm-yyyy hh24:mi:ss'), to_date('12-01-2013 14:28:04', 'dd-mm-yyyy hh24:mi:ss'), '000123', '张三', 'N/A', null, '', '', '', '', '', '', '', null, '', '', '', null, '')";
		this.simpleJdbcTemplate.getJdbcOperations().execute(sql);
	

		//反签收
		this.reverseConfirmTaking(waybillNo, signOrgCode, signOrgName, signType);
	}
	
	/**
	 * 空运反签收不通过-插入一条重复的始发应收单
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-12 下午3:45:05
	 */
	@Test
	@Rollback(true)
	public void testAirReverseConfirmTakingSix(){
		String waybillNo="50001953";
		String signOrgCode="W31000206090611";
		String signOrgName="上海空运总调";
		String signType=SettlementConstants.AIR_SIGN;//空运
		
		//插入一条相同运单号的始发应收单记录 
		String sql=" insert into STL.t_stl_bill_receivable (ID, RECEIVABLE_NO, WAYBILL_NO, WAYBILL_ID, CREATE_TYPE, SOURCE_BILL_NO, SOURCE_BILL_TYPE, BILL_TYPE, RECEIVABLE_ORG_CODE, RECEIVABLE_ORG_NAME, GENERATING_ORG_CODE, GENERATING_ORG_NAME, GENERATING_COM_CODE, GENERATING_COM_NAME, DUNNING_ORG_CODE, DUNNING_ORG_NAME, ORIG_ORG_CODE, ORIG_ORG_NAME, DEST_ORG_CODE, DEST_ORG_NAME, CUSTOMER_CODE, CUSTOMER_NAME, DELIVERY_CUSTOMER_CODE, DELIVERY_CUSTOMER_NAME, RECEIVE_CUSTOMER_CODE, RECEIVE_CUSTOMER_NAME, AMOUNT, VERIFY_AMOUNT, UNVERIFY_AMOUNT, CURRENCY_CODE, BUSINESS_DATE, ACCOUNT_DATE, CONREVEN_DATE, PAYMENT_TYPE, PRODUCT_CODE, PRODUCT_ID, TO_PAY_AMOUNT, PRE_PAY_AMOUNT, TRANSPORT_FEE, PICKUP_FEE, DELIVERY_GOODS_FEE, PACKAGING_FEE, COD_FEE, INSURANCE_FEE, OTHER_FEE, VALUE_ADD_FEE, PROMOTIONS_FEE, GOODS_NAME, GOODS_VOLUME_TOTAL, BILL_WEIGHT, RECEIVE_METHOD, CUSTOMER_PICKUP_ORG_CODE, GOODS_QTY_TOTAL, TARGET_ORG_CODE, VERSION_NO, ACTIVE, IS_RED_BACK, IS_INIT, CREATE_USER_CODE, CREATE_USER_NAME, CREATE_ORG_CODE, CREATE_ORG_NAME, CREATE_TIME, MODIFY_TIME, MODIFY_USER_CODE, MODIFY_USER_NAME, STATEMENT_BILL_NO, UNLOCK_DATE_TIME, LOCK_CUSTOMER_CODE, LOCK_CUSTOMER_NAME, COLLECTION_TYPE, COLLECTION_NAME, AUDIT_USER_CODE, AUDIT_USER_NAME, APPROVE_STATUS, AUDIT_DATE, IS_DISABLE, DISABLE_USER_CODE, DISABLE_USER_NAME, DISABLE_TIME, NOTES)"+
		"values (sys_guID(), 'YS110016584', '50001953', '1de50f83-4c07-46c9-b9fc-47fd4e7f05f5', 'A', '50001953', 'W', 'OR', 'W011302020106', '上海浦东花木营业部', 'W011302020106', '上海浦东花木营业部', 'ZGS001', '上海精准德邦物流责任有限公司', 'W011302020106', '上海浦东花木营业部', 'W011302020106', '上海浦东花木营业部', 'W31000206090611', '上海空运总调', '400233719', '吴华娜', '400233719', '吴华娜', '', '', 21300, 21300, 0, 'RMB', to_date('12-01-2013 10:50:22', 'dd-mm-yyyy hh24:mi:ss'), to_date('12-01-2013 10:50:24', 'dd-mm-yyyy hh24:mi:ss'), to_date('12-01-2013 14:28:04', 'dd-mm-yyyy hh24:mi:ss'), 'CT', 'C30001', 'C30001', null, null, 15100, 0, 0, 0, 2000, 1600, 2600, 6200, 0, '键盘', 0.719, 0.000, 'SELF_PICKUP', 'W011305080203', 6, '北京西城区西站营业部', 5, 'Y', 'N', 'N', '999999', '倩倩', 'W011302020106', '上海浦东花木营业部', to_date('12-01-2013 10:50:25', 'dd-mm-yyyy hh24:mi:ss'), to_date('12-01-2013 14:28:04', 'dd-mm-yyyy hh24:mi:ss'), '000123', '张三', 'DZ01001350', null, '', '', '', '', '', '', '', null, '', '', '', null, '')";

		this.simpleJdbcTemplate.getJdbcOperations().execute(sql);
		
		//反签收
		this.reverseConfirmTaking(waybillNo, signOrgCode, signOrgName, signType);
	}
	
	/**
	 * 空运反签收不通过-插入一条重复的代收货款应付单
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-12 下午3:48:42
	 */
	@Test
	@Rollback(true)
	public void testAirReverseConfirmTakingFour(){
		String waybillNo="50001962";
		String signOrgCode="W31000206090611";
		String signOrgName="上海空运总调";
		String signType=SettlementConstants.AIR_SIGN;//空运
		
		//插入一条相同运单号的代收货款应付单记录 
		String sql=" insert into STL.t_stl_bill_payable (ID, PAYABLE_NO, WAYBILL_NO, WAYBILL_ID, PAYMENT_NO, CREATE_TYPE, BILL_TYPE, SOURCE_BILL_NO, SOURCE_BILL_TYPE, PRODUCT_CODE, PAYABLE_ORG_CODE, PAYABLE_ORG_NAME, PAYABLE_COM_CODE, PAYABLE_COM_NAME, ORIG_ORG_CODE, ORIG_ORG_NAME, DEST_ORG_CODE, DEST_ORG_NAME, CUSTOMER_CODE, CUSTOMER_NAME, AMOUNT, VERIFY_AMOUNT, UNVERIFY_AMOUNT, CURRENCY_CODE, PRODUCT_ID, ACCOUNT_DATE, BUSINESS_DATE, SIGN_DATE, EFFECTIVE_DATE, CREATE_USER_NAME, CREATE_USER_CODE, CREATE_ORG_CODE, CREATE_ORG_NAME, ACTIVE, IS_RED_BACK, IS_INIT, VERSION_NO, EFFECTIVE_STATUS, EFFECTIVE_USER_NAME, EFFECTIVE_USER_CODE, FROZEN_STATUS, FROZEN_TIME, FROZEN_USER_NAME, FROZEN_USER_CODE, PAY_STATUS, PAYMENT_STATUS, STATEMENT_BILL_NO, CUSTOMER_CONTACT, CUSTOMER_CONTACT_NAME, CUSTOMER_PHONE, CREATE_TIME, MODIFY_TIME, MODIFY_USER_NAME, MODIFY_USER_CODE, WAYBILL_STATUS, OTHERSIDE_ORG_CODE, OTHERSIDE_ORG_NAME, WORKFLOW_NO, LGDRIVER_CODE, LGDRIVER_NAME, PAYER_TYPE, PAYABLE_TYPE, DELIVER_FEE, OUTGOING_FEE, AUDIT_USER_CODE, AUDIT_USER_NAME, AUDIT_DATE, APPROVE_STATUS, IS_DISABLE, DISABLE_USER_CODE, DISABLE_USER_NAME, DISABLE_TIME, NOTES, COD_TYPE, PAYEE_NAME, BANK_HQ_CODE, BANK_HQ_NAME, ACCOUNT_NO, PROVINCE_CODE, PROVINCE_NAME, CITY_CODE, CITY_NAME, BANK_BRANCH_CODE, BANK_BRANCH_NAME, PAYMENT_AMOUNT, PAYMENT_NOTES, LAST_PAYMENT_TIME) "+" values (sys_guid(), 'YF110042869', '50001962', '1d779495-8ca6-4b69-8a47-825c055f0025', 'N/A', 'A', 'APC', '50001962', 'W', '', 'W011302020106', '上海浦东花木营业部', 'ZGS001', '上海精准德邦物流责任有限公司', 'W011302020106', '上海浦东花木营业部', 'W31000206090611', '上海空运总调', '400233719', '吴华娜', 500000, 0, 500000, 'RMB', '', to_date('12-01-2013 12:39:50', 'dd-mm-yyyy hh24:mi:ss'), to_date('12-01-2013 12:39:46', 'dd-mm-yyyy hh24:mi:ss'), to_date('12-01-2013 14:28:04', 'dd-mm-yyyy hh24:mi:ss'), null, '倩倩', '999999', 'W011302020106', '上海浦东花木营业部', 'Y', 'N', 'N', 2, 'N', '', '', 'N', null, '', '', 'N', 'NP', 'N/A', '', '', '', to_date('12-01-2013 12:39:50', 'dd-mm-yyyy hh24:mi:ss'), to_date('12-01-2013 14:28:04', 'dd-mm-yyyy hh24:mi:ss'), '张三', '000123', '', '北京西城区西站营业部', '', '', '', '', '', '', null, null, '', '', null, 'AA', '', '', '', null, '', 'R1', '', '', '', '', '', '', '', '', '', '', null, '', null)";

		this.simpleJdbcTemplate.getJdbcOperations().execute(sql);
		
		//反签收
		this.reverseConfirmTaking(waybillNo, signOrgCode, signOrgName, signType);
	}
	
	/**
	 * 空运反签收不通过-插入一条重复的装卸费应付单
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-12 下午3:48:42
	 */
	@Test
	@Rollback(true)
	public void testAirReverseConfirmTakingEight(){
		String waybillNo="50001959";
		String signOrgCode="W31000206090611";
		String signOrgName="上海空运总调";
		String signType=SettlementConstants.AIR_SIGN;//空运
		
		//插入一条相同运单号的代收货款应付单记录 
		String sql=" insert into STL.t_stl_bill_payable (ID, PAYABLE_NO, WAYBILL_NO, WAYBILL_ID, PAYMENT_NO, CREATE_TYPE, BILL_TYPE, SOURCE_BILL_NO, SOURCE_BILL_TYPE, PRODUCT_CODE, PAYABLE_ORG_CODE, PAYABLE_ORG_NAME, PAYABLE_COM_CODE, PAYABLE_COM_NAME, ORIG_ORG_CODE, ORIG_ORG_NAME, DEST_ORG_CODE, DEST_ORG_NAME, CUSTOMER_CODE, CUSTOMER_NAME, AMOUNT, VERIFY_AMOUNT, UNVERIFY_AMOUNT, CURRENCY_CODE, PRODUCT_ID, ACCOUNT_DATE, BUSINESS_DATE, SIGN_DATE, EFFECTIVE_DATE, CREATE_USER_NAME, CREATE_USER_CODE, CREATE_ORG_CODE, CREATE_ORG_NAME, ACTIVE, IS_RED_BACK, IS_INIT, VERSION_NO, EFFECTIVE_STATUS, EFFECTIVE_USER_NAME, EFFECTIVE_USER_CODE, FROZEN_STATUS, FROZEN_TIME, FROZEN_USER_NAME, FROZEN_USER_CODE, PAY_STATUS, PAYMENT_STATUS, STATEMENT_BILL_NO, CUSTOMER_CONTACT, CUSTOMER_CONTACT_NAME, CUSTOMER_PHONE, CREATE_TIME, MODIFY_TIME, MODIFY_USER_NAME, MODIFY_USER_CODE, WAYBILL_STATUS, OTHERSIDE_ORG_CODE, OTHERSIDE_ORG_NAME, WORKFLOW_NO, LGDRIVER_CODE, LGDRIVER_NAME, PAYER_TYPE, PAYABLE_TYPE, DELIVER_FEE, OUTGOING_FEE, AUDIT_USER_CODE, AUDIT_USER_NAME, AUDIT_DATE, APPROVE_STATUS, IS_DISABLE, DISABLE_USER_CODE, DISABLE_USER_NAME, DISABLE_TIME, NOTES, COD_TYPE, PAYEE_NAME, BANK_HQ_CODE, BANK_HQ_NAME, ACCOUNT_NO, PROVINCE_CODE, PROVINCE_NAME, CITY_CODE, CITY_NAME, BANK_BRANCH_CODE, BANK_BRANCH_NAME, PAYMENT_AMOUNT, PAYMENT_NOTES, LAST_PAYMENT_TIME) "
		+" values (sys_guId(), 'YF210012629', '50001959', '177fd573-9e76-4672-a12f-94179d20c039', 'N/A', 'A', 'SF', '50001959', 'W', '', 'W011302020106', '上海浦东花木营业部', 'ZGS001', '上海精准德邦物流责任有限公司', 'W011302020106', '上海浦东花木营业部', 'W31000206090611', '上海空运总调', '400233719', '吴华娜', 6000, 0, 6000, 'RMB', '', to_date('12-01-2013 11:54:54', 'dd-mm-yyyy hh24:mi:ss'), to_date('12-01-2013 11:54:51', 'dd-mm-yyyy hh24:mi:ss'), to_date('12-01-2013 14:28:04', 'dd-mm-yyyy hh24:mi:ss'), null, '倩倩', '999999', 'W011302020106', '上海浦东花木营业部', 'Y', 'N', 'N', 2, 'N', '', '', 'N', null, '', '', 'N', 'NP', 'N/A', '郑立颖', '郑立颖', '13565687845', to_date('12-01-2013 11:54:54', 'dd-mm-yyyy hh24:mi:ss'), to_date('12-01-2013 14:28:04', 'dd-mm-yyyy hh24:mi:ss'), '张三', '000123', '', '', '', '', '', '', 'O', '', null, null, '', '', null, 'AA', '', '', '', null, '', '', '', '', '', '', '', '', '', '', '', '', null, '', null)";
		this.simpleJdbcTemplate.getJdbcOperations().execute(sql);
		
		//反签收
		this.reverseConfirmTaking(waybillNo, signOrgCode, signOrgName, signType);
	}
	
	/**
	 * 不存在有效的财务单据
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-12 下午4:54:51
	 */
	@Test
	@Rollback(true)
	public void testAirReverseConfirmTakingNine(){
		String waybillNo="50001959";
		String signOrgCode="W31000206090611";
		String signOrgName="上海空运总调";
		String signType=SettlementConstants.AIR_SIGN;//空运
		
		//作废所有
		String receivableSql=" update stl.t_stl_bill_receivable t set t.active='N',t.is_red_back='N' where t.waybill_no= '"+waybillNo+"'";;
		this.simpleJdbcTemplate.getJdbcOperations().execute(receivableSql);
		
		String payableSql=" update stl.t_stl_bill_payable t set t.active='N',t.is_red_back='N' where t.waybill_no= '"+waybillNo+"'";;
		this.simpleJdbcTemplate.getJdbcOperations().execute(payableSql);
		
		String cashSql=" update stl.T_STL_BILL_CASH_COLLECTION t set t.active='N',t.is_red_back='N' where t.waybill_no= '"+waybillNo+"'";;
		this.simpleJdbcTemplate.getJdbcOperations().execute(cashSql);
		
		String codSql=" update stl.t_stl_cod t set t.active='N' where t.waybill_no= '"+waybillNo+"'";
		this.simpleJdbcTemplate.getJdbcOperations().execute(codSql);
		
		//反签收
		this.reverseConfirmTaking(waybillNo, signOrgCode, signOrgName, signType);
	}
	
	/**
	 * 空运代收货款已经审核--不能进行反签收
	 * 
	 * 有测试人员在 操作界面进行审核操作，在这里就不写审核代码
	 * 
	 * 直接
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-12 下午5:07:54
	 */
	@Test
	//@Rollback(true)
	@Ignore
	public void testAirReverseConfirmTakingTen(){
		String waybillNo="50001952";
		String signOrgCode="W31000206090611";
		String signOrgName="上海空运总调";
		String signType=SettlementConstants.AIR_SIGN;//空运
		
		//反签收
		this.reverseConfirmTaking(waybillNo, signOrgCode, signOrgName, signType);
	}
	
	/**
	 * 反签收- 在数据库更改应付单的状态为已支付和已生效  不能进行反签收
	 *
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-12 下午5:30:22
	 */
	@Test
	@Rollback(true)
	public void testAirReverseConfirmTakingElevent(){
		String waybillNo="50001959";
		String signOrgCode="W31000206090611";
		String signOrgName="上海空运总调";
		String signType=SettlementConstants.AIR_SIGN;//空运
		
		//反签收
		this.reverseConfirmTaking(waybillNo, signOrgCode, signOrgName, signType);
	}
	
	/**
	 * 修改应付单的已核销金额，不能进行反签收操作
	 *  
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-12 下午5:36:44
	 */
	@Test
	@Rollback(true)
	public void testAirReverseConfirmTakingTw(){
		String waybillNo="50001953";
		String signOrgCode="W31000206090611";
		String signOrgName="上海空运总调";
		String signType=SettlementConstants.AIR_SIGN;//空运
		
		String sql=" update stl.t_stl_bill_payable t set t.verify_amount=10000,t.unverify_amount=(t.unverify_amount*100 -10000) where t.waybill_no ='"+waybillNo+"'  and t.bill_type='APC' ";
		this.simpleJdbcTemplate.getJdbcOperations().execute(sql);
		
		//反签收
		this.reverseConfirmTaking(waybillNo, signOrgCode, signOrgName, signType);
	}
	
	
	/**
	 * 空运反签收不通过-传入的运单号为空
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-12 下午2:33:51
	 */
	@Test
	@Rollback(true)
	public void testAirReverseConfirmTakingWaybillNoIsEmpty(){
		String waybillNo="";
		String signOrgCode="W31000206090611";
		String signOrgName="上海空运总调";
		String signType=SettlementConstants.AIR_SIGN;//空运
		
		//反签收
		this.reverseConfirmTaking(waybillNo, signOrgCode, signOrgName, signType);
	}
	
	/**
	 * 空运反签收不通过-输入的运单号不合法
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-12 下午2:34:50
	 */
	@Test
	@Rollback(true)
	public void testAirReverseConfirmTakingNoHeFa(){
		String waybillNo="z50001962";//真实数据为：50001962
		String signOrgCode="W31000206090611";
		String signOrgName="上海空运总调";
		String signType=SettlementConstants.AIR_SIGN;//空运
		
		//反签收
		this.reverseConfirmTaking(waybillNo, signOrgCode, signOrgName, signType);
	}
	
	/**
	 * 空运反签收不通过--运单号不存在
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-12 下午4:08:35
	 */
	@Test
	@Rollback(true)
	public void testAirReverseConfirmTakingWaybillNoNotExists(){
		String waybillNo="50001912362";//真实数据为：50001962
		String signOrgCode="W31000206090611";
		String signOrgName="上海空运总调";
		String signType=SettlementConstants.AIR_SIGN;//空运
		
		//反签收
		this.reverseConfirmTaking(waybillNo, signOrgCode, signOrgName, signType);
	}
	
	/**
	 * 空运反签收不通过--签收部门编码为空
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-12 下午2:37:25
	 */
	@Test
	@Rollback(true)
	public void testAirReverseConfirmSignOrgCodeIsNull(){
		String waybillNo="50001962";//真实数据为：50001962
		String signOrgCode="";//签收部门编码为空
		String signOrgName="上海空运总调";
		String signType=SettlementConstants.AIR_SIGN;//空运
		
		//反签收
		this.reverseConfirmTaking(waybillNo, signOrgCode, signOrgName, signType);
	}
	
	/**
	 * 空运反签收不通过-反签收部门名称为空
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-12 下午3:26:49
	 */
	@Test
	@Rollback(true)
	public void testAirReverseConfirmSignOrgNameIsNull(){
		String waybillNo="50001962";//真实数据为：50001962
		String signOrgCode="W31000206090611";
		String signOrgName="";//签收部门名称为空
		String signType=SettlementConstants.AIR_SIGN;//空运
		
		//反签收
		this.reverseConfirmTaking(waybillNo, signOrgCode, signOrgName, signType);
	}
	
	/**
	 * 空运反签收不通过-反签收部门不等于应收单的到达部门编码
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-12 下午4:18:34
	 */
	@Test
	@Rollback(true)
	public void testAirReverseConfirmFirve(){
		String waybillNo="50001962";//真实数据为：50001962
		String signOrgCode="W310002060906111000";
		String signOrgName="广州空运总调";//签收部门名称为空
		String signType=SettlementConstants.AIR_SIGN;//空运
		
		//反签收
		this.reverseConfirmTaking(waybillNo, signOrgCode, signOrgName, signType);
	}
	
	
/**********************************************偏线部分******************************************/
	/**
	 * 偏线空运签收
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-12 上午11:53:16
	 */
	public void testPlPubConfirmTaking(){
		String waybillNo="";
		String signOrgCode="";
		String signOrgName="";
		String signType="";
		this.pubConfirmTaking(waybillNo, signOrgCode, signOrgName, signType);
	}
	
	
	/**
	 * 偏线反签收测试
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-12 上午10:10:07
	 */
	@Test
	@Rollback(true)
	public void testPlReverseConfirmTaking(){
		String waybillNo="";
		String signOrgCode="";
		String signOrgName="";
		String signType="";
		this.reverseConfirmTaking(waybillNo, signOrgCode, signOrgName, signType);
	}

	
	/***********************************************专线反签收增加限制条件**************************************/
	
	/**
	 * 专线反签收-存在服务补救应付单
	 * 
	 * TODO 
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-14 下午3:45:44
	 */
	@Test
	@Rollback(true)
	public void testZxReverseConfirmTakingByCp(){
		
		String waybillNo="40000195";
		String billType=SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__COMPENSATION;
		Date date=new Date();
		String origOrgCode="W011302020106";
		String origOrgName="上海浦东花木营业部";
		String destOrgCode="W011305080203";
		String destOrgName="北京西城区西站营业部";
		
		BillPayableEntity payableEntity = StlWebTestUtil.getBillPayableEntity(
				waybillNo, this.getPayableNO(billType),
				billType, date);
		
		payableEntity.setWaybillNo(waybillNo);//运单号
		payableEntity.setOrigOrgCode(origOrgCode);//始发部门编码
		payableEntity.setOrigOrgName(origOrgName);//始发部门名称
		
		payableEntity.setDestOrgCode(destOrgCode);//到达部门编码
		payableEntity.setDestOrgName(destOrgName);//到达部门名称
		
		payableEntity.setPayableOrgCode(origOrgCode);//应付部门编码
		payableEntity.setPayableOrgName(origOrgName);//应付部门名称
		try {
			this.billPayableService.addBillPayable(payableEntity,
					StlWebTestUtil.getCurrentInfo());
			
			//反签收
			this.reverseConfirmTaking(waybillNo, destOrgCode, destOrgName, SettlementConstants.LINE_SIGN);
		} catch (SettlementException e) {
			logger.error(e.getMessage(),e);
		}
	}
	
	/**
	 * 专线反签收-存在理赔应付单
	 * 
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-14 下午3:52:59
	 */
	@Test
	@Rollback(true)
	public void testZxReverseConfirmTakingByLiPei(){
		
		String waybillNo="40000195";
		String billType=SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM;
		Date date=new Date();
		String origOrgCode="W011302020106";
		String origOrgName="上海浦东花木营业部";
		String destOrgCode="W011305080203";
		String destOrgName="北京西城区西站营业部";
		
		BillPayableEntity payableEntity = StlWebTestUtil.getBillPayableEntity(
				waybillNo, this.getPayableNO(billType),
				billType, date);
		
		payableEntity.setWaybillNo(waybillNo);//运单号
		payableEntity.setOrigOrgCode(origOrgCode);//始发部门编码
		payableEntity.setOrigOrgName(origOrgName);//始发部门名称
		
		payableEntity.setDestOrgCode(destOrgCode);//到达部门编码
		payableEntity.setDestOrgName(destOrgName);//到达部门名称
		
		payableEntity.setPayableOrgCode(origOrgCode);//应付部门编码
		payableEntity.setPayableOrgName(origOrgName);//应付部门名称
		try {
			this.billPayableService.addBillPayable(payableEntity,
					StlWebTestUtil.getCurrentInfo());
			
			//反签收
			this.reverseConfirmTaking(waybillNo, destOrgCode, destOrgName, SettlementConstants.LINE_SIGN);
		} catch (SettlementException e) {
			logger.info(e.getErrorCode());
			logger.error(e.getMessage(),e);
		}
	}
	
	/**
	 * 反签收-存在坏账申请记录
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-14 下午3:54:56
	 */
	@Test
	@Rollback(true)
	public void testZxReverseConfirmTakingByHuaiZhang(){
		String waybillNo="40000195";
		Date date=new Date();
		String destOrgCode="W011305080203";
		String destOrgName="北京西城区西站营业部";
		
		BillBadAccountEntity bad=new BillBadAccountEntity();
		try {
			bad.setId(UUIDUtils.getUUID());
			//获取坏账单号
			bad.setBadDebatBillNo(this.settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.HZ));
			bad.setApplyOrgCode(destOrgCode);//申请部门编码
			bad.setApplyOrgName(destOrgName);//申请部门名称
			bad.setReceivableNo("YS210021655");//应收单号
			bad.setWaybillNo(waybillNo);
			bad.setSourceBillType("W");
			bad.setCreateTime(date);
			bad.setBadAmount(new BigDecimal("1000"));
			bad.setCurrencyCode("RMB");
			billBadAccountService.add(bad);//保存坏账记录
			
			//反签收
			this.reverseConfirmTaking(waybillNo, destOrgCode, destOrgName, SettlementConstants.LINE_SIGN);
		} catch (SettlementException e) {
			logger.info(e.getErrorCode());
			logger.error(e.getMessage(),e);
		}
	}
	
	/**
	 * 反签收--代收货款资金部冻结
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-14 下午4:12:51
	 */
	@Test
	@Rollback(true)
	public void testZxReverseConfirmTakingByCod(){
		String waybillNo="40000196";
		String destOrgCode="W011305080203";
		String destOrgName="北京西城区西站营业部";
		try {
			String codSql=" update stl.t_stl_cod t set t.status='FF' where t.waybill_no= '"+waybillNo+"'";;
			this.simpleJdbcTemplate.getJdbcOperations().execute(codSql);
			
			//反签收
			this.reverseConfirmTaking(waybillNo, destOrgCode, destOrgName, SettlementConstants.LINE_SIGN);
		} catch (SettlementException e) {
			logger.info(e.getErrorCode());
			logger.error(e.getMessage(),e);
		}
	}
	
	/**
	 * 反签收--代收货款退款中
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-14 下午4:12:51
	 */
	@Test
	@Rollback(true)
	public void testZxReverseConfirmTakingByCodTwo(){
		String waybillNo="40000196";
		
		String destOrgCode="W011305080203";
		String destOrgName="北京西城区西站营业部";
		try {
			String codSql=" update stl.t_stl_cod t set t.status='RG' where t.waybill_no= '"+waybillNo+"'";;
			this.simpleJdbcTemplate.getJdbcOperations().execute(codSql);
			
			//反签收
			this.reverseConfirmTaking(waybillNo, destOrgCode, destOrgName, SettlementConstants.LINE_SIGN);
		} catch (SettlementException e) {
			logger.info(e.getErrorCode());
			logger.error(e.getMessage(),e);
		}
	}
	
	/**
	 * 反签收--代收货款付款成功
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-14 下午4:12:51
	 */
	@Test
	@Rollback(true)
	public void testZxReverseConfirmTakingByCodThree(){
		String waybillNo="40000196";
		
		String destOrgCode="W011305080203";
		String destOrgName="北京西城区西站营业部";
		try {
			String codSql=" update stl.t_stl_cod t set t.status='RD' where t.waybill_no= '"+waybillNo+"'";;
			this.simpleJdbcTemplate.getJdbcOperations().execute(codSql);
			
			//反签收
			this.reverseConfirmTaking(waybillNo, destOrgCode, destOrgName, SettlementConstants.LINE_SIGN);
		} catch (SettlementException e) {
			logger.info(e.getErrorCode());
			logger.error(e.getMessage(),e);
		}
	}

	
	
	/**
	 * 为运单手动插入一条已生效，已签收的整车尾款应付单
	 * 进行签收操作
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-14 下午3:52:59
	 */
	@Test
	@Rollback(true)
	public void testZxReverseConfirmTakingBy(){
		String waybillNo="40000196";
		String billType=SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_LAST;
		Date date=new Date();
		String origOrgCode="W011302020106";
		String origOrgName="上海浦东花木营业部";
		String destOrgCode="W011305080203";
		String destOrgName="北京西城区西站营业部";
		
		BillPayableEntity payableEntity = StlWebTestUtil.getBillPayableEntity(
				waybillNo, this.getPayableNO(billType),
				billType, date);
		
		payableEntity.setWaybillNo(waybillNo);//运单号
		payableEntity.setOrigOrgCode(origOrgCode);//始发部门编码
		payableEntity.setOrigOrgName(origOrgName);//始发部门名称
		payableEntity.setDestOrgCode(destOrgCode);//到达部门编码
		payableEntity.setDestOrgName(destOrgName);//到达部门名称
		payableEntity.setPayableOrgCode(origOrgCode);//应付部门编码
		payableEntity.setPayableOrgName(origOrgName);//应付部门名称
		payableEntity.setEffectiveDate(new Date());
		payableEntity.setEffectiveStatus(SettlementDictionaryConstants.BILL_PAYABLE__EFFECTIVE_STATUS__YES);
		payableEntity.setSignDate(new Date());
		try {
//			this.billPayableService.addBillPayable(payableEntity,
//					StlWebTestUtil.getCurrentInfo());
			
			//反签收
			this.reverseConfirmTaking(waybillNo, destOrgCode, destOrgName, SettlementConstants.LINE_SIGN);
		} catch (SettlementException e) {
			logger.info(e.getErrorCode());
			logger.error(e.getMessage(),e);
		}
	}
	
	
/**********************************辅助方法**********************************************/	
	
	/**
	 * 修改结算单据信息
	 * 
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-14 上午8:49:53
	 * @param waybillNo
	 * @param origOrgCode
	 * @param origOrgName
	 * @param destOrgCode
	 * @param destOrgName
	 */
	public void updateStlBill(String waybillNo,String origOrgCode,String  origOrgName,String destOrgCode,String destOrgName,
			String billType,String customerCode,String customerName
			){
		
		//修改应收单的到达部门
		StringBuffer updateReceSql=new StringBuffer(" update stl.t_stl_bill_receivable t set t.dest_org_code='"+destOrgCode+"',t.dest_org_name='"+destOrgName+"'  where t.waybill_no='"+waybillNo+"' and t.active='Y' ");
		this.simpleJdbcTemplate.getJdbcOperations().execute(updateReceSql.toString());
		
		//修改应收单的单据类型和客户名称、客户编码，以及催款部门等信息
		String sql=" UPDATE STL.T_STL_BILL_RECEIVABLE T SET T.BILL_TYPE = '"+billType+"', T.CUSTOMER_CODE = '"+customerCode+"', T.CUSTOMER_NAME = '"+customerName+"',t.DUNNING_ORG_CODE='"+destOrgCode+"',t.DUNNING_ORG_NAME='"+destOrgName+"'   WHERE T.WAYBILL_NO = '"+waybillNo+"' AND T.ACTIVE = 'Y'  AND T.BILL_TYPE='DR' ";
		this.simpleJdbcTemplate.getJdbcOperations().execute(sql);
		
		
		//修改应收单的催款部门信息
		sql="   UPDATE STL.T_STL_BILL_RECEIVABLE T SET t.DUNNING_ORG_CODE='"+destOrgCode+"',t.DUNNING_ORG_NAME='"+destOrgName+"'   WHERE T.WAYBILL_NO = '"+waybillNo+"' AND T.ACTIVE = 'Y' ";
		this.simpleJdbcTemplate.getJdbcOperations().execute(sql);
		
		
		//修改应付单的到达部门信息
		sql=" update stl.t_stl_bill_payable t set t.dest_org_code='"+destOrgCode+"',t.dest_org_name='"+destOrgName+"' where t.waybill_no='"+waybillNo+"' and t.active='Y' ";
		this.simpleJdbcTemplate.getJdbcOperations().execute(sql);
		
		//修改代收货款的到达部门信息
		sql=" update stl.T_STL_BILL_CASH_COLLECTION t set t.dest_org_code='"+destOrgCode+"',t.dest_org_name='"+destOrgName+"' where t where t.waybill_no='"+waybillNo+"' and t.active='Y' ";
		this.simpleJdbcTemplate.getJdbcOperations().execute(sql);
	}
	
	
	
	
	/**
	 * 签收记录
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-12 上午10:16:39
	 * @param waybillNo
	 * @param signOrgCode
	 * @param signOrgName
	 * @param signType
	 */
	private void pubConfirmTaking(String waybillNo,String signOrgCode,String signOrgName,String signType) {
		LineSignDto signDto = new LineSignDto();
		signDto.setWaybillNo(waybillNo);
		signDto.setSignDate(new Date());
		signDto.setSignOrgCode(signOrgCode);
		signDto.setSignOrgName(signOrgName);
		signDto.setIsWholeVehicle(FossConstants.NO);
		signDto.setSignType(signType);//SettlementConstants.LINE_SIGN);// 专线签收
		try {
			// 调用签收方法
			lineSignService.confirmTaking(signDto,StlWebTestUtil.getCurrentInfo());

			// 调用签收方法成功后校验对应的应收单的确认收入日期是否为空
			BillReceivableConditionDto billReceivableConDto = new BillReceivableConditionDto(waybillNo);
			billReceivableConDto
					.setSourceBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL);
			billReceivableConDto
					.setBillTypes(new String[] {
							SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE,
							SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE });
			List<BillReceivableEntity> list = this.billReceivableService
					.queryBillReceivableByCondition(billReceivableConDto);
			if (CollectionUtils.isNotEmpty(list)) {
				for (BillReceivableEntity entity : list) {
					Assert.assertTrue(entity.getConrevenDate() != null);
				}
			}
		} catch (SettlementException e) {
			logger.error(e.getMessage(),e);
		}
	}
	
	/**
	 * 反签收方法
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-12 上午9:43:11
	 * @param waybillNo
	 * @param date
	 */
	private void reverseConfirmTaking(String waybillNo,String signOrgCode,String signOrgName,String signType) {
		
		// 专线反签收
		LineSignDto signDto = new LineSignDto();
		signDto.setWaybillNo(waybillNo);
		signDto.setSignDate(new Date());
		signDto.setSignOrgCode(signOrgCode);
		signDto.setSignOrgName(signOrgName);
		signDto.setIsWholeVehicle(FossConstants.NO);
		signDto.setSignType(signType);

		// 专线反签收
		try {
			lineSignService.reverseConfirmTaking(signDto,
					StlWebTestUtil.getCurrentInfo());

			// 调用反签收方法成功后校验对应的应收单的确认收入日期是否为空
			BillReceivableConditionDto billReceivableConDto = new BillReceivableConditionDto(
					waybillNo);
			billReceivableConDto
					.setSourceBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL);
			billReceivableConDto
					.setBillTypes(new String[] {
							SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE,
							SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE });
			List<BillReceivableEntity> list = this.billReceivableService
					.queryBillReceivableByCondition(billReceivableConDto);
			if (CollectionUtils.isNotEmpty(list)) {
				for (BillReceivableEntity entity : list) {
					Assert.assertTrue(entity.getConrevenDate() == null);
				}
			}
		} catch (SettlementException e) {
			logger.error(e.getMessage(),e);
		}
	}
	
	
	
	
	
	
	/**
	 * 空运--红冲到付运费应收单，生成新的空运到达代理运费应收单
	 * 
	 * 
	 * @未使用的方法
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-12 上午10:31:57
	 */
	public void writeBackBillReceivableDrToAA(String waybillNo,String origOrgCode,
			String origOrgName,String destOrgCode,String destOrgName,String customerCode,String customerName){
		BillReceivableConditionDto conDto=new BillReceivableConditionDto(waybillNo);
		conDto.setBillTypes(new String[]{
				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE //到达运费应收单
		});
		
		List<BillReceivableEntity> list=this.billReceivableService.queryBillReceivableByCondition(conDto);
		Assert.assertTrue(CollectionUtils.isNotEmpty(list));
		BillReceivableEntity entity=list.get(0);
		
		//红冲应收单
		this.billReceivableService.writeBackBillReceivable(entity, StlWebTestUtil.getCurrentInfo());
		
		BillReceivableEntity blueEntity=new BillReceivableEntity();
	
		BeanUtils.copyProperties(entity, blueEntity);
		blueEntity.setId(UUIDUtils.getUUID());//新的单据ID
		
		if (SettlementConstants.BLUE_NEW_BILL_NO) {	// 生成新单据
			SettlementNoRuleEnum rule = SettlementNoRuleEnum.getByCode(entity.getReceivableNo());
			blueEntity.setReceivableNo(settlementCommonService.getSettlementNoRule(rule));
		}
		blueEntity.setBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY);//空运到达运费应收单
		blueEntity.setReceivableOrgCode(destOrgCode);//应收部门编码
		blueEntity.setReceivableOrgName(destOrgName);//应收部门名称
		blueEntity.setGeneratingOrgCode(origOrgCode);//收入部门编码
		blueEntity.setGeneratingOrgName(origOrgName);//收入部门编码
		blueEntity.setDunningOrgCode(destOrgCode);//催款部门编码
		blueEntity.setDunningOrgName(destOrgName);//催款部门编码
		blueEntity.setCustomerCode(customerCode);//客户编码
		blueEntity.setCustomerName(customerName);//客户名称
		blueEntity.setAccountDate(new Date());
		this.billReceivableService.addBillReceivable(blueEntity, StlWebTestUtil.getCurrentInfo());
	}
	
	/**
	 * 偏线外发单
	 * 
	 * @未使用的方法
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-12 上午11:19:30
	 */
	public void waybillToParintLine(String waybillNo,String externalNo,String agentCode,String agentName){
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
		dto.setAgentCompanyName(agentName);//外发代理名称
		
		
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
			
			
		}catch(SettlementException e){
			logger.error(e.getErrorCode());
		}
	}
	
	
	/**
	 * 获取应付单数据
	 * 
	 *  @未使用的方法
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-12 上午10:25:27
	 * @param waybillNo
	 * @param date
	 * @return
	 */
	public BillPayableEntity getBillPayableEntity(String waybillNo,Date date,String origOrgCode,
			String origOrgName,String destOrgCode,String destOrgName,String billType){
		// 添加装卸费应付单
		
		BillPayableEntity payableEntity = StlWebTestUtil.getBillPayableEntity(
				waybillNo, this.getPayableNO(billType),
				billType, date);
		
		payableEntity.setWaybillNo(waybillNo);//运单号
		payableEntity.setOrigOrgCode(origOrgCode);//始发部门编码
		payableEntity.setOrigOrgName(origOrgName);//始发部门名称
		
		payableEntity.setDestOrgCode(destOrgCode);//到达部门编码
		payableEntity.setDestOrgName(destOrgName);//到达部门名称
		
		payableEntity.setPayableOrgCode(origOrgCode);//应付部门编码
		payableEntity.setPayableOrgName(origOrgName);//应付部门名称
		try {
			this.billPayableService.addBillPayable(payableEntity,
					StlWebTestUtil.getCurrentInfo());
		} catch (SettlementException e) {
			logger.error(e.getMessage(),e);
		}
		return payableEntity;
	}
	
	
	
	/**
	 * 获取应付单号
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-3 上午11:08:38
	 * @param billType
	 * @return
	 * @see
	 */
	private String getPayableNO(String billType) {

		if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD // 代收货款应付单
				.equals(billType)) {
			return settlementCommonService
					.getSettlementNoRule(SettlementNoRuleEnum.YF1);
		} else if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__SERVICE_FEE // 装卸费应付单
				.equals(billType)) {
			return settlementCommonService
					.getSettlementNoRule(SettlementNoRuleEnum.YF2);
		} else if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_LAST // 整车尾款应付单
				.equals(billType)) {
			return settlementCommonService
					.getSettlementNoRule(SettlementNoRuleEnum.YF62);
		} else if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM
				.equals(billType)) {// 理赔应付
			return settlementCommonService
					.getSettlementNoRule(SettlementNoRuleEnum.YF3);
		} else if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__REFUND
				.equals(billType)) {// 退运费应付
			return settlementCommonService
					.getSettlementNoRule(SettlementNoRuleEnum.YF4);
		} else if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__COMPENSATION
				.equals(billType)) {// 服务补救应付
			return settlementCommonService
					.getSettlementNoRule(SettlementNoRuleEnum.YF5);
		}
		return "";
	}
	

}

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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/pickup/waybill/server/service/QueryCustServiceTest.java
 * 
 * FILE NAME        	: QueryCustServiceTest.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.server.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ContactEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusAccountEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerContactDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto;
import com.deppon.foss.module.pickup.waybill.api.server.service.IQueryCustomerService;
import com.deppon.foss.module.pickup.waybill.server.common.TestHelper;
import com.deppon.foss.module.pickup.waybill.server.common.TestHelperUtils;
import com.deppon.foss.module.pickup.waybill.server.service.impl.QueryCustomerService;
import com.deppon.foss.module.pickup.waybill.shared.domain.HisDeliveryCusEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.HisReceiveCusEntity;

public class QueryCustServiceTest {

    private IQueryCustomerService queryCustomerService;

    @Before
    public void setUp() throws Exception {
    	queryCustomerService = TestHelper.get().getBeanByClass(QueryCustomerService.class);
    }

    // @Test
    public void testQueryCustomerInfo() {
	// 生成4~6的随机整数
	Random r = new Random();
	String custCode = "000" + Math.abs(r.nextInt() % (3) + 4);
	System.out.println("随机数===================" + custCode);

	CustomerQueryConditionDto condition = new CustomerQueryConditionDto();
	condition.setCustCode(custCode);

	// 查询条件
	condition.setCustCode("0004");

	// 查询结果
	List<CustomerContactDto> list = queryCustomerService.queryCustomerInfo(condition);
	for (CustomerContactDto cust : list) {
	    System.out.println(cust.getCustCode());
	    System.out.println(cust.getCustName());
	    System.out.println(cust.getContactName());
	    System.out.println(cust.getOfficePhone());
	    System.out.println(cust.getMobilePhone());
	    System.out.println(cust.getIdCard());
	    System.out.println(cust.getAddress());
	}

    }

    // @Test
    public void testQueryCustInfoByMobile() {
	String mobilePhone = "13916510090";
	//当前部门编码
	String deptCode = "GS00002";//FossUserContext. getCurrentDeptCode();;

	CustomerDto custInfo = queryCustomerService.queryCustInfoByMobile(mobilePhone, deptCode);
	List<ContactEntity> contactList = custInfo.getContactList();
	List<CusAccountEntity> bankAccountList = custInfo.getBankAccountList();

	System.out.println("============客户信息===========");
	System.out.println(custInfo.getCusCode());
	System.out.println(custInfo.getName());
	System.out.println();

	System.out.println("============联系人信息===========");
	for (ContactEntity contactEntity : contactList) {
	    System.out.println(contactEntity.getContactName());
	    System.out.println(contactEntity.getMobilePhone());
	    System.out.println(contactEntity.getContactPhone());
	    System.out.println(contactEntity.getAddress());
	    System.out.println();
	}

	System.out.println("============开户行信息===========");
	for (CusAccountEntity cusAccountEntity : bankAccountList) {
	    System.out.println(cusAccountEntity.getAccountNo());
	    System.out.println(cusAccountEntity.getAccountName());
	    System.out.println(cusAccountEntity.getCustomer());
	    System.out.println();
	}
    }

    //@Test
    public void testQueryCustInfoByPhone() {
	List<String> phoneList = new ArrayList<String>();
	phoneList.add("020-88333326");
	phoneList.add("020-88333325");
	phoneList.add("020-88333322");
	phoneList.add("020-88333320");
	phoneList.add("020-88333323");
	phoneList.add("020-88333321");
	phoneList.add("020-88333324");

	String deptCode = "0004";
	List<CustomerDto> custList = queryCustomerService.queryCustInfoByPhone(phoneList, deptCode);
	for (CustomerDto customerDto : custList) {
	    System.out.println("**************查询出的客户列表***************");
	    List<ContactEntity> contactList = customerDto.getContactList();
	    List<CusAccountEntity> bankAccountList = customerDto.getBankAccountList();

	    System.out.println("======客户信息======");
	    System.out.println(customerDto.getCusCode());
	    System.out.println(customerDto.getName());
	    System.out.println();

	    System.out.println("======联系人信息====");
	    for (ContactEntity contactEntity : contactList) {
		System.out.println(contactEntity.getContactName());
		System.out.println(contactEntity.getMobilePhone());
		System.out.println(contactEntity.getContactPhone());
		System.out.println(contactEntity.getAddress());
		System.out.println();
	    }

	    System.out.println("======开户行信息====");
	    for (CusAccountEntity cusAccountEntity : bankAccountList) {
		System.out.println(cusAccountEntity.getAccountNo());
		System.out.println(cusAccountEntity.getAccountName());
		System.out.println(cusAccountEntity.getCustomer());
		System.out.println();
	    }
	}
    }
    
    //@Test
    public void testAddHisDeliveryCus(){
    	HisDeliveryCusEntity custEntity = new HisDeliveryCusEntity();
    	custEntity.setDeliveryCustomerAddress("随机汉字："+TestHelperUtils.randomChChar(10));
    	custEntity.setDeliveryCustomerCityCode("城市编码："+Math.round(Math.random() * 100000));
    	custEntity.setDeliveryCustomerContact(TestHelperUtils.randomChChar(3));
    	custEntity.setDeliveryCustomerDisCode("区域编码："+Math.round(Math.random() * 10000));
    	custEntity.setDeliveryCustomerMobilephone("139"+Math.round(Math.random() * 100000000));
    	custEntity.setDeliveryCustomerNationCode("国家编码："+Math.round(Math.random() * 100));
    	custEntity.setDeliveryCustomerPhone(Math.round(Math.random() * 100)+"-"+Math.round(Math.random() * 10000000));
    	custEntity.setDeliveryCustomerProvCode("省份编码："+Math.round(Math.random() * 1000));
    	//queryCustomerService.addHisDeliveryCustomer(custEntity);
    }
    
    //@Test
    public void testQueryHisDeliveryCusByPhone(){
    	CustomerQueryConditionDto conditionDto = new CustomerQueryConditionDto();
    	conditionDto.setMobilePhone("13916510000");
    	//conditionDto.setContactPhone("021-31350606");
    	List<HisDeliveryCusEntity> custList = null;//queryCustomerService.queryHisDeliveryCusByPhone(conditionDto);
    	if(custList == null || custList.size() == 0){
    	    System.out.println("没有查询出数据");
    	}
    	for (HisDeliveryCusEntity entity : custList) {
	    System.out.println(entity.getDeliveryCustomerContact());
	    System.out.println(entity.getDeliveryCustomerMobilephone());
	    System.out.println(entity.getDeliveryCustomerPhone());
	    System.out.println(entity.getDeliveryCustomerAddress());
	}
    }
    
    //@Test
    public void testAddHisReceiveCus(){
    	HisReceiveCusEntity custEntity = new HisReceiveCusEntity();
    	custEntity.setReceiveCustomerAddress("随机汉字："+TestHelperUtils.randomChChar(10));
    	custEntity.setReceiveCustomerCityCode("城市编码："+Math.round(Math.random() * 100000));
    	custEntity.setReceiveCustomerContact(TestHelperUtils.randomChChar(3));
    	custEntity.setReceiveCustomerDisCode("区域编码："+Math.round(Math.random() * 10000));
    	custEntity.setReceiveCustomerMobilephone("139"+Math.round(Math.random() * 100000000));
    	custEntity.setReceiveCustomerNationCode("国家编码："+Math.round(Math.random() * 100));
    	custEntity.setReceiveCustomerPhone(Math.round(Math.random() * 100)+"-"+Math.round(Math.random() * 10000000));
    	custEntity.setReceiveCustomerProvCode("省份编码："+Math.round(Math.random() * 1000));
    	//queryCustomerService.addHisReceiveCustomer(custEntity);
    }
    
	//@Test
	public void testQueryHisReceiveCusByPhone() {
		CustomerQueryConditionDto conditionDto = new CustomerQueryConditionDto();
		// conditionDto.setMobilePhone("13916510001");
		conditionDto.setContactPhone("021-31350606");
		List<HisReceiveCusEntity> custList = null;// queryCustomerService.queryHisReceiveCusByPhone(conditionDto);
		if (custList == null || custList.size() == 0) {
			System.out.println("没有查询出数据");
		}
		for (HisReceiveCusEntity entity : custList) {
			System.out.println(entity.getReceiveCustomerContact());
			System.out.println(entity.getReceiveCustomerMobilephone());
			System.out.println(entity.getReceiveCustomerPhone());
			System.out.println(entity.getReceiveCustomerAddress());
		}
    }
	
	//@Test
	public void testQueryHisDeliveryCusByPhoneList() {
		List<String> phoneList = new ArrayList<String>();
		phoneList.add("021-31350606");
		phoneList.add("33-3448474");
		phoneList.add("47-7667815");
		phoneList.add("28-7044464");
		List<HisDeliveryCusEntity> custList = queryCustomerService.queryHisDeliveryCusByPhoneList(phoneList);
		if (custList == null || custList.size() == 0) {
			System.out.println("没有查询出数据");
		}
		for (HisDeliveryCusEntity entity : custList) {
			System.out.println(entity.getDeliveryCustomerContact());
			System.out.println(entity.getDeliveryCustomerMobilephone());
			System.out.println(entity.getDeliveryCustomerPhone());
			System.out.println(entity.getDeliveryCustomerAddress());
		}
    }
	
	//@Test
	public void testQueryHisDeliveryCusByMobileList() {
		List<String> mobileList = new ArrayList<String>();
		mobileList.add("13961176523");
		mobileList.add("13916510000");
		mobileList.add("1395483876");
		mobileList.add("1398726691");
		List<HisDeliveryCusEntity> custList = queryCustomerService.queryHisDeliveryCusByMobileList(mobileList);
		if (custList == null || custList.size() == 0) {
			System.out.println("没有查询出数据");
		}
		for (HisDeliveryCusEntity entity : custList) {
			System.out.println(entity.getDeliveryCustomerContact());
			System.out.println(entity.getDeliveryCustomerMobilephone());
			System.out.println(entity.getDeliveryCustomerPhone());
			System.out.println(entity.getDeliveryCustomerAddress());
		}
    }
	
	@Test
	public void testQueryHisReceiveCusByPhoneList() {
		List<String> phoneList = new ArrayList<String>();
		phoneList.add("021-31350606");
		phoneList.add("35-5748093");
		phoneList.add("39-8006653");
		phoneList.add("9-3135664");
		List<HisReceiveCusEntity> custList = queryCustomerService.queryHisReceiveCusByPhoneList(phoneList);
		if (custList == null || custList.size() == 0) {
			System.out.println("没有查询出数据");
		}
		for (HisReceiveCusEntity entity : custList) {
			System.out.println(entity.getReceiveCustomerContact());
			System.out.println(entity.getReceiveCustomerMobilephone());
			System.out.println(entity.getReceiveCustomerPhone());
			System.out.println(entity.getReceiveCustomerAddress());
		}
    }
	
	@Test
	public void testQueryHisReceiveCusByMobileList() {
		List<String> mobileList = new ArrayList<String>();
		mobileList.add("13921546470");
		mobileList.add("13948878231");
		mobileList.add("13916510001");
		mobileList.add("13925100590");
		List<HisReceiveCusEntity> custList = queryCustomerService.queryHisReceiveCusByMobileList(mobileList);
		if (custList == null || custList.size() == 0) {
			System.out.println("没有查询出数据");
		}
		for (HisReceiveCusEntity entity : custList) {
			System.out.println(entity.getReceiveCustomerContact());
			System.out.println(entity.getReceiveCustomerMobilephone());
			System.out.println(entity.getReceiveCustomerPhone());
			System.out.println(entity.getReceiveCustomerAddress());
		}
    }
	
	@Test
	public void testQueryCustomerByCondition() {
		CustomerQueryConditionDto condition = new CustomerQueryConditionDto();
		condition.setCustCode("S20130126-00000181");
		
		CustomerQueryConditionDto condition2 = new CustomerQueryConditionDto();
		condition2.setCustCode("S20130124-00000161");
		
		List<CustomerQueryConditionDto> conditions = new ArrayList<CustomerQueryConditionDto>();
		conditions.add(condition);
		conditions.add(condition2);
		// 查询结果
		List<CustomerQueryConditionDto> list = queryCustomerService.queryCustomerByCondition(condition);
		for (CustomerQueryConditionDto cust : list) {
			System.out.println("=============================");
			System.out.println(cust.getCustCode());
			System.out.println(cust.getCustName());
			System.out.println(cust.getContactName());
			System.out.println(cust.getLinkmanCode());
			System.out.println(cust.getMobilePhone());
			System.out.println(cust.getIdCard());
			System.out.println(cust.getAddress());
			System.out.println(cust.getBargainCode());
			System.out.println(cust.getBeginTime());
			System.out.println(cust.getEndTime());
		}

	}
}
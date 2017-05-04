/*******************************************************************************
 * Copyright 2013 BSE TEAM
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
 * PROJECT NAME	: bse-baseinfo-web
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/server/dao/CustomerDaoTest.java
 * 
 * FILE NAME        	: CustomerDaoTest.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.base.baseinfo.server.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.deppon.foss.module.base.baseinfo.api.server.dao.ICustomerDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ContactAddressEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerContactDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 客户相关信息DAO单元测试类
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:094463-foss-xieyantao,date:2012-11-5
 * 下午2:03:02
 * </p>
 * 
 * @author 094463-foss-xieyantao
 * @date 2012-11-5 下午2:03:02
 * @since
 * @version
 */
public class CustomerDaoTest {

    private JdbcTemplate jdbc;

    // customerDao
    private ICustomerDao customerDao;
    
    private CustomerEntity entity = new CustomerEntity(); 

    // 虚拟编码
    // private String virtualCode;

    // Id
    private String id;

    @Before
    public void setUp() throws Exception {
	id = UUIDUtils.getUUID();
	// 插入客户联系人地址
	String sql = "insert into t_bas_cus_contact_address t (t.id,service_address_id,contact,t.other_demand,t.begin_time,"
		+ "t.end_time,t.active,t.create_time,t.modify_time)values ('"
		+ id
		+ "','0001','张三','其他要求',"
		+ "to_date('2012-11-05 13:00:00','yyyy-mm-dd hh24:mi:ss'),to_date('2012-11-05 17:30:00','yyyy-mm-dd hh24:mi:ss'),'Y',sysdate,sysdate)";

	String customerId = UUIDUtils.getUUID();
	// 插入客户信息
	String customerSql = "insert into T_BAS_CUSTOMER (ID, ADDRESS, PROPERTY, TYPE, "
		+ "CREDIT_AMOUNT, NAME, LICENSE, UNIFIED_CODE, CODE, ACTIVE_CUS, "
		+ "TOTAL_ARREARS, CHARGE_MODE, DEGREE, CRM_CUS_ID, CREATE_TIME, "
		+ "MODIFY_TIME, ACTIVE, CREATE_USER_CODE, MODIFY_USER_CODE, VIRTUAL_CODE, FISTRANGOODS) "
		+ "values ('"
		+ customerId
		+ "', '青浦区徐泾镇', '到达', '公司', 12000, '旺达科技', 'www232433', "
		+ "'000112', '0004', 'Y', 1000, '月结', '黄金客户', '0002333122', "
		+ "to_date('06-11-2012 00:30:00', 'dd-mm-yyyy hh24:mi:ss'), "
		+ "to_date('06-11-2012 04:01:00', 'dd-mm-yyyy hh24:mi:ss'), 'Y', "
		+ "'张三', '张三', '" + customerId + "', 'Y')";

	String contactId = UUIDUtils.getUUID();
	// 插入客户联系人信息
	String contactSql = "insert into T_BAS_CUS_CONTACT (ID, GENDER, CONTACT_PHONE, MOBILE_PHONE, "
		+ "FAX_NO, ADDRESS, EMAIL, ZIP_CODE, BIRTHDAY, ID_CARD, HOBBY, RECEIVE_EMAIL, "
		+ "RECEIVE_MESSAGE, RECEIVE_LETTER, WAY, NATION, HOMETOWN, TITLE, WORKING_DEPT, "
		+ "CONTACT_NAME, NOTES, CONTACT_TYPE, MAIN_CONTRACT, CREATE_TIME, MODIFY_TIME, "
		+ "ACTIVE, CREATE_USER_CODE, MODIFY_USER_CODE, CUSTOMER_CODE, VIRTUAL_CODE) "
		+ "values ('"
		+ contactId
		+ "', 'M', '020-88333321', '13929299922', null, '广州市海珠区赤沙', "
		+ "null, null, to_date('01-10-1988', 'dd-mm-yyyy'), '420688889399339933939', null, "
		+ "'Y', 'Y', 'Y', null, '汉', '湖南', '职员', 'ＩＴ部门', '张龙', null, '收货联系人', 'Y', "
		+ "to_date('06-11-2012 00:01:00', 'dd-mm-yyyy hh24:mi:ss'), to_date('06-11-2012 00:01:00', "
		+ "'dd-mm-yyyy hh24:mi:ss'), 'Y', '李菲', '李菲', '0004', '"
		+ contactId + "')";

	String accountId = UUIDUtils.getUUID();
	// 插入客户银行账户信息
	String accountSql = "insert into T_BAS_CUS_ACCOUNT (ID, OTHER_BRANCH_BANK_NAME, ACCOUNT_NO, "
		+ "ACCOUNT_NAME, CITY_CODE, PROV_CODE, BANK_CODE, MOBILE_PHONE, CUSTOMER, DEFAULT_ACCOUNT, "
		+ "BRANCH_BANK_CODE, NOTES, CREATE_TIME, MODIFY_TIME, ACTIVE, CREATE_USER_CODE, MODIFY_USER_CODE, "
		+ "VIRTUAL_CODE) values ('"
		+ accountId
		+ "', '其他支行名称', '62222999932929292', '张江', '00332', '00023', "
		+ "'00001', '13800099968', '0004', 'Y', '0000113', null, to_date('06-11-2012 00:01:00', "
		+ "'dd-mm-yyyy hh24:mi:ss'), to_date('06-11-2012 00:00:01', 'dd-mm-yyyy hh24:mi:ss'), 'Y', '张飞', '张飞', '"
		+ accountId + "')";
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(
		JdbcTemplate.class);
	customerDao = SpringTestHelper.get().getBeanByInterface(
		ICustomerDao.class);
	// 添加客户联系人地址
	// jdbc.execute(sql);

	// 添加客户信息
//	jdbc.execute(customerSql);

	// 添加客户联系人信息
//	jdbc.execute(contactSql);

	// 添加客户开户银行账户信息
//	jdbc.execute(accountSql);
    }

    /**
     * 清空测试数据
     * 
     * @author 094463-foss-xieyantao
     * @date 2012-11-5 下午2:03:02
     * @throws java.lang.Exception
     * @see
     */
    @After
    public void tearDown() throws Exception { 
	// 清空客户联系人地址表测试数据
//	jdbc.execute("delete from BSE.T_BAS_CUS_CONTACT_ADDRESS"); 
	// 清空客户信息表测试数据
//	jdbc.execute("delete from BSE.T_BAS_CUSTOMER"); 
	// 清空客户联系人信息表测试数据
//	jdbc.execute("delete from BSE.T_BAS_CUS_CONTACT");
	// 清空客户开户银行账户表测试数据
//	jdbc.execute("delete from BSE.T_BAS_CUS_ACCOUNT");
	
//	jdbc.execute("delete from BSE.T_BAS_CUSTOMER where id = '"+id+"'");
    }

    /**
     * Test method for
     * {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.CustomerDao#addCustomer(com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerEntity)}
     * .
     */
    @Ignore
    @Test
    public void testAddCustomer() {
	entity.setId(id);
	entity.setCreateUser("张三");
	entity.setCreateDate(new Date());
	entity.setModifyUser("张三");
	entity.setModifyDate(entity.getCreateDate());
	entity.setActive(FossConstants.ACTIVE);
	entity.setCrmCusId(new BigDecimal(321222));
	
	
	int result = customerDao.addCustomer(entity);
	
	Assert.assertTrue(result > 0);	
    }
    
    /**
     * <p>根据crmId,最后一次修改时间查询客户是否存在</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-1-5 上午10:35:04
     * @see
     */
    @Ignore
    @Test
    public void testQueryCustomerByCrmId(){
	boolean flag = customerDao.queryCustomerByCrmId(new BigDecimal(400233719), new Date());
	System.out.println(flag);
	Assert.assertTrue(flag);
    }

    /**
     * Test method for
     * {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.CustomerDao#deleteCustomerByCode(java.lang.String[], java.lang.String)}
     * .
     */
    @Ignore
    @Test
    public void testDeleteCustomerByCode() {
	//添加一条数据
	testAddCustomer();
	
	int result = customerDao.deleteCustomerByCode(entity.getCrmCusId(), "王辉");
	Assert.assertTrue(result > 0);		
    }

    /**
     * Test method for
     * {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.CustomerDao#updateCustomer(com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerEntity)}
     * .
     */
    @Ignore
    @Test
    public void testUpdateCustomer() {
	//添加一条数据
	testAddCustomer();
	entity.setId(id);
	entity.setModifyUser("李四");
	entity.setModifyDate(new Date());
	entity.setActive(FossConstants.ACTIVE);
	entity.setCrmCusId(new BigDecimal(321222));
	
	int result = customerDao.updateCustomer(entity);
	Assert.assertTrue(result > 0);	
    }

    /**
     * Test method for
     * {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.CustomerDao#queryCustomers(com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerEntity, int, int)}
     * .
     */
    @Ignore
    @Test
    public void testQueryCustomers() {
	testAddCustomer();
	entity.setActive(FossConstants.ACTIVE);
	entity.setName("旺达");
	List<CustomerEntity> list = customerDao.queryCustomers(entity, 10, 0);
	Assert.assertTrue(list.size()>0);
    }

    /**
     * Test method for
     * {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.CustomerDao#queryRecordCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerEntity)}
     * .
     */
    @Ignore
    @Test
    public void testQueryRecordCount() {
	
	testAddCustomer();
	entity.setActive(FossConstants.ACTIVE);
	entity.setName("旺达");
	Long records = customerDao.queryRecordCount(entity);
	Assert.assertTrue(records>0);
    }

    /**
     * Test method for
     * {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.CustomerDao#queryCustomerInfo(com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto)}
     * .
     */
    @Ignore
    @Test
    public void testQueryCustomerInfo() {
	
	CustomerQueryConditionDto dto = new CustomerQueryConditionDto();
//	dto.setContactName("张龙");
//	dto.setAddress("广州");
//	dto.setContactPhone("020-88333321");
	dto.setCustCode("400233719");
//	dto.setCustName("");
//	dto.setIdCard("");
	List<CustomerContactDto> list = customerDao.queryCustomerInfo(dto);
	
	Assert.assertTrue(list.size() > 0);
	
    }

    /**
     * 通过传入一个客户编码查询出对应的客户信息 Test method for
     * {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.CustomerDao#queryCustInfoByCode(java.lang.String)}
     * .
     */
    @Ignore
    @Test
    public void testQueryCustInfoByCode() {

	CustomerDto dto = customerDao.queryCustInfoByCode("400233719");

	Assert.assertNotNull(dto);
    }

    /**
     * Test method for
     * {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.CustomerDao#queryCustInfoByName(java.lang.String)}
     * .
     */
    @Ignore
    @Test
    public void testQueryCustInfoByName() {
	
	List<CustomerDto> list = customerDao.queryCustInfoByName("科技");
	
	Assert.assertTrue(list.size() > 0);
    }

    /**
     * Test method for
     * {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.CustomerDao#queryCustInfoByPhone(java.util.List, java.lang.String)}
     * .
     */
    @Ignore
    @Test
    public void testQueryCustInfoByPhone() {
	
	List<String> phoneList = new ArrayList<String>();
	phoneList.add("020-88333320");
	phoneList.add("020-88333323");
	List<CustomerContactDto> list = customerDao.queryCustInfoByPhone(phoneList, "000112");
        
	Assert.assertTrue(list.size() > 0);
    }

    /**
     * 通过传入手机号、部门Code，返回部门的客户信息
     * Test method for
     * {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.CustomerDao#queryCustInfoByMobile(java.lang.String, java.lang.String)}
     * .
     */
    @Ignore
    @Test
    public void testQueryCustInfoByMobile() {
	CustomerContactDto dto = customerDao.queryCustInfoByMobile("13929299922", "000112");
	Assert.assertNotNull(dto);
    }

    /**
     * 通过传入部门标杆编码查询该部门下所有客户信息：客户编码、客户名称 Test method for
     * {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.CustomerDao#queryCusInfosByDeptCode(java.lang.String)}
     * .
     */
    @Ignore
    @Test
    public void testQueryCusInfosByDeptCode() {

	List<CustomerEntity> list = customerDao
		.queryCusInfosByDeptCode("000112");
	Assert.assertTrue(list.size() > 0);
    }

    /**
     * 根据客户联系人地址ID，查询出客户的收货习惯 Test method for
     * {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.CustomerDao#queryContactAddressById(java.lang.String)}
     * .
     */
    @Ignore
    @Test
    public void testQueryContactAddressById() {
	ContactAddressEntity entity = customerDao
		.queryContactAddressById("0001");
	Assert.assertNotNull(entity);
    }
    
    /**
     * <p>根据客户编码查询客户信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-11-17 下午5:25:08
     * @see
     */
    @Ignore
    @Test
    public void testqueryCustInfoByCode(){
	CustomerDto dto = customerDao.queryCustInfoByCode("0004");
	Assert.assertNotNull(dto);
    }

}

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
 * PROJECT NAME	: stl-writeoff
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/BillDepAndBillRecOptionTest.java
 * 
 * FILE NAME        	: BillDepAndBillRecOptionTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module;

import junit.framework.TestCase;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BillDepAndBillRecOptionTest extends TestCase {

    private static final Logger log = LogManager
	    .getLogger(BillDepAndBillRecOptionTest.class);

    //private IBillDepositReceivedOptionDao billDepositReceivedOptionDao;

   // private IBillReceivableOptionDao billReceivableOptionDao;

    private static ApplicationContext ctx = null;

    String[] xmls = new String[] {
	    "com/deppon/foss/module/settlement/writeoff/server/META-INF/spring.xml",
	    "com/deppon/foss/module/settlement/writeoff/test/META-INF/spring.xml" };

    @Deprecated
    @Override
    protected void setUp() throws Exception {
	try {
	    if (ctx == null) {
		ctx = new ClassPathXmlApplicationContext(xmls);
	    }
	   // billDepositReceivedOptionDao = ctx
		//    .getBean(IBillDepositReceivedOptionDao.class);
	    //billReceivableOptionDao = ctx
		//    .getBean(IBillReceivableOptionDao.class);

	} catch (Exception e) {
		log.error(e.getMessage());
	}
    }
    
    public void testQueryDepositReceivedNOs() {}

    // 按输入的一到多个预收单号查询预收单
    // public void testQueryDepositReceivedNOs() {
    // log.trace("begin test");
    //
    // BillDepositReceivedDto billDepositReceivedDto = new
    // BillDepositReceivedDto();
    // List<String> precollectedNosList = new ArrayList<String>();
    // precollectedNosList.add("10000001");
    // billDepositReceivedDto.setPrecollectedNos(precollectedNosList);
    // billDepositReceivedOptionDao.queryByDepositReceivedNOs(billDepositReceivedDto);
    //
    // log.trace("end test");
    // }

    // 按输入的一到多个应收单号查询预收单
    // public void testQueryReceivedNOs() {
    // log.trace("begin test");
    //
    // BillReceivableDto billReceivableDto = new BillReceivableDto();
    // List<String> list = new ArrayList<String>();
    // list.add("10000001");
    // billReceivableDto.setReceivableNos(list);
    // billReceivableOptionDao.queryByReceivableNOs(billReceivableDto);
    //
    // log.trace("end test");
    // }

    // // 按输入的参数信息查询应收单
    // public void testqueryByReceivableParams() {
    //
    // log.trace("begin test");
    //
    // BillReceivableDto billReceivableDto = new BillReceivableDto();
    //
    // billReceivableDto.setMaxShowNum(1000);
    // billReceivableDto.setGeneratingOrgCode("SR1");
    // billReceivableDto.setCustomerCode("CUST1");
    //
    // String stratDate = "2012-10-10 00:00:00";
    // String endDate = "2012-10-20 00:00:00";
    // String path1 = "yyyy-MM-dd HH:mm:ss";
    // SimpleDateFormat sdf1 = new SimpleDateFormat(path1);
    //
    // try {
    // Date startAccountDate = sdf1.parse(stratDate);
    // Date endAccountDate = sdf1.parse(endDate);
    // Date startBusinessDate = sdf1.parse(stratDate);
    // Date endBusinessDate = sdf1.parse(endDate);
    //
    // billReceivableDto.setStartAccountDate(startAccountDate);
    // billReceivableDto.setStartBusinessDate(startBusinessDate);
    // billReceivableDto.setEndAccountDate(endAccountDate);
    // billReceivableDto.setEndBusinessDate(endBusinessDate);
    //
    // } catch (ParseException e) {
    //	log.error(e.getMessage());
    // }
    //
    // billReceivableOptionDao.queryByReceivableParams(billReceivableDto);
    //
    // log.trace("end test");
    // }

    // 按输入的参数信息查询应收单
    // public void testqueryByReceivableParams() {
    // log.trace("begin test");
    //
    // BillDepositReceivedDto billDepositReceivedDto = new
    // BillDepositReceivedDto();
    //
    // billDepositReceivedDto.setMaxShowNum(1000);
    // billDepositReceivedDto.setCollectionOrgCode("021020");
    // billDepositReceivedDto.setCustomerCode("1111");
    //
    // String stratDate="2012-10-10 00:00:00";
    // String endDate="2012-10-20 00:00:00";
    // String path1="yyyy-MM-dd HH:mm:ss";
    // SimpleDateFormat sdf1=new SimpleDateFormat(path1);
    //
    // try {
    // Date startAccountDate = sdf1.parse(stratDate);
    // Date endAccountDate = sdf1.parse(endDate);
    // Date startBusinessDate = sdf1.parse(stratDate);
    // Date endBusinessDate = sdf1.parse(endDate);
    //
    // billDepositReceivedDto.setStartAccountDate(startAccountDate);
    // billDepositReceivedDto.setStartBusinessDate(startBusinessDate);
    // billDepositReceivedDto.setEndAccountDate(endAccountDate);
    // billDepositReceivedDto.setEndBusinessDate(endBusinessDate);
    //
    // } catch (ParseException e) { 
    //	log.error(e.getMessage());
    // }
    //
    //
    // billDepositReceivedOptionDao.queryByDepositReceivedParams(billDepositReceivedDto);
    //
    // log.trace("end test");
    // }

    public void test1() {
	long seqNum = 1234l;
	int length = 5;
	String seqCode = String.format("%010d", seqNum);
	seqCode = "LK" + seqCode.substring(seqCode.length() - length);
	log.info(seqCode);
    }
}

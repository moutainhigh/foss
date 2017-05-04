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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/web/server/test/mvr/waybill/Testite.java
 * 
 * FILE NAME        	: Testite.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.web.server.test.mvr.waybill;

import junit.framework.TestCase;


public class Testite extends TestCase{
	
	/**
	 * 运单号
	 */
	public static  String waybillNo;
	
	public static String orgCode;
	
	public void testAdd(){
		junit.textui.TestRunner.run(MathToolTest.suite());
	}

	
	/**
	 * @return  the waybillNo
	 */
	public static String getWaybillNo() {
		return waybillNo;
	}

	
	/**
	 * @param waybillNo the waybillNo to set
	 */
	public static void setWaybillNo(String waybillNo) {
		Testite.waybillNo = waybillNo;
	}

	
	/**
	 * @return  the orgCode
	 */
	public static String getOrgCode() {
		return orgCode;
	}

	
	/**
	 * @param orgCode the orgCode to set
	 */
	public static void setOrgCode(String orgCode) {
		Testite.orgCode = orgCode;
	}


	/** 
	 * 初始化数据
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-29 下午5:30:44
	 * @throws Exception 
	 */
	@SuppressWarnings("static-access")
	@Override
	protected void setUp() throws Exception {
		this.setWaybillNo("YD12345YD ");
		this.setOrgCode("ORG12345 ");
		super.setUp();
	}


	/** 
	 * 销毁方法
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-29 下午5:30:44
	 * @throws Exception 
	 */
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

}

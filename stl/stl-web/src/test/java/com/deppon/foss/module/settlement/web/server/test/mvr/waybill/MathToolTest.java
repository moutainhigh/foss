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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/web/server/test/mvr/waybill/MathToolTest.java
 * 
 * FILE NAME        	: MathToolTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.web.server.test.mvr.waybill;



import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * 
 * @author 099995-foss-wujiangtao
 * @date 2013-1-29 下午4:15:55
 * @since
 * @version
 */
public class MathToolTest extends TestCase {

	public String waybillNo;
	
	public String orgCode;
	
	public MathToolTest(String testMethod) {
		super(testMethod);
	}

	public void testGcd() {
		System.out.println("1ererer");
	}
	public void testGcdTwo() {
		System.out.println(Testite.waybillNo+"    1ererer222222222");
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(suite());
	}
	
	/**
	 * 此方法，可以把所有的其他类的test加入进来
	 * 
	 * //  suite.addTestSuite(MathToolTest.class);  会调用这个测试类中，所有的Test方法
	 * 
	 *  suite.addTest(new MathToolTest("testGcd"));//也可以只添加一个测试类中，单独的一个测试方法
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-29 下午4:34:07
	 * @return
	 */
	public static Test suite() {
	     TestSuite suite = new TestSuite();
	     suite.addTestSuite(MathToolTest.class);  //会调用这个测试类中，所有的Test方法，
	    // suite.addTest(new MathToolTest("testGcd"));//也可以只添加一个测试类中，单独的一个测试方法
	    
	     suite.addTestSuite(MathToolTwoTest.class);
	     return suite;
	}

	/** 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-29 下午4:47:34
	 * @throws Exception 
	 */
	@Override
	protected void setUp() throws Exception {
		
	}

	/** 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-29 下午4:47:34
	 * @throws Exception 
	 */
	@Override
	protected void tearDown() throws Exception {
		
		super.tearDown();
	}

	
	/**
	 * @return  the waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	
	/**
	 * @param waybillNo the waybillNo to set
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	
	/**
	 * @return  the orgCode
	 */
	public String getOrgCode() {
		return orgCode;
	}

	
	/**
	 * @param orgCode the orgCode to set
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
}

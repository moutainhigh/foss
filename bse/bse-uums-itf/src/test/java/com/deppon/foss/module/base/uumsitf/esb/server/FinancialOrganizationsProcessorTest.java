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
 * PROJECT NAME	: bse-uums-itf
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/uumsitf/esb/server/FinancialOrganizationsProcessorTest.java
 * 
 * FILE NAME        	: FinancialOrganizationsProcessorTest.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.uumsitf.esb.server;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.deppon.esb.api.util.SendUtil;
import com.deppon.foss.framework.server.context.AppContext;
/**
 * LMS数据同步服务监听类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-11-22 下午1:37:41</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-11-22 下午1:37:41
 * @since
 * @version
 */
@Ignore
public class FinancialOrganizationsProcessorTest{

    private static final Logger logger = LoggerFactory.getLogger(FinancialOrganizationsProcessorTest.class);
    
    protected ApplicationContext appContext = null;
    
    @Before
    public void before(){
	AppContext.initAppContext("application", "http://192.168.17.167/poc","");
	appContext = new ClassPathXmlApplicationContext(
		"classpath*:com/deppon/foss/module/base/uumsitf/server/META-INF/springTest.xml",
		"classpath*:com/deppon/foss/module/base/uumsitf/server/META-INF/spring.xml",
		"classpath*:com/deppon/foss/module/base/dict/server/META-INF/spring.xml",
		"classpath*:com/deppon/foss/module/base/baseinfo/server/META-INF/spring.xml");
    }

    @SuppressWarnings("static-access")
    @Test
    public void testfinancialOrganizationsProcessor() throws Exception {

	logger.info("****************** ESB Monitor Service Start ******************");

	Thread.currentThread().sleep(60*60*10*1000);
    }
    
    @Test
    public void testfinancialOrganizationsProcessor2() throws Exception {
	SendUtil.send("TEST1", "body", 3, 3, null, body.length());

	logger.info("send msg over");
    }

    static 	String body="\n<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"+
	    "\n<SendAdminOrgRequest xmlns=\"http://www.deppon.com/uums/inteface/domain/usermanagement\">"+
	    "\n    <adminOrgInfo>"+
	    "\n        <orgChangeId>26126</orgChangeId>"+
	    "\n        <orgId>11085</orgId>"+
	    "\n        <orgCode>W0113050102</orgCode>"+
	    "\n        <parentOrgCode>W01130501</parentOrgCode>"+
	    "\n        <orgName>北京事业部后勤管理组</orgName>"+
	    "\n        <orgAttribute>经营</orgAttribute>"+
	    "\n        <orgBenchmarkCode>DP01195</orgBenchmarkCode>"+
	    "\n        <orgManager>003926</orgManager>"+
	    "\n        <orgPhone>010-59270331</orgPhone>"+
	    "\n        <orgFax>010-59270205</orgFax>"+
	    "\n        <parentOrgId>623</parentOrgId>"+
	    "\n        <parentOrgBenchmarkCode>DP00233</parentOrgBenchmarkCode>"+
	    "\n        <subCompName>北京德邦货运代理有限公司</subCompName>"+
	    "\n        <subCompCode>BJ</subCompCode>"+
	    "\n        <orgEmail>W0113050102@deppon.com</orgEmail>"+
	    "\n        <orgAddress>北京市朝阳区黑庄户乡大鲁店村东2号德邦物流.上班时间：08：00——17：30 </orgAddress>"+
	    "\n        <orgStatus>1</orgStatus>"+
	    "\n        <isCareerDept>false</isCareerDept>"+
	    "\n        <isBigArea>false</isBigArea>"+
	    "\n        <isSmallArea>false</isSmallArea>"+
	    "\n        <orgLevel>6</orgLevel>"+
	    "\n        <orgSeq>.103.104.464252.621.623.11085.</orgSeq>"+
	    "\n        <isLeaf>false</isLeaf>"+
	    "\n        <changeType>4</changeType>"+
	    "\n        <changeDate>2013-01-06T17:51:02+08:00</changeDate>"+
	    "\n        <deptAttribute>经营</deptAttribute>"+
	    "\n        <areaCode>北京事业部</areaCode>"+
	    "\n        <ehrDeptCode>O03030101</ehrDeptCode>"+
	    "\n    </adminOrgInfo>"+
	    "\n</SendAdminOrgRequest>";

    
    
}

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
 * PROJECT NAME	: bse-lms-itf
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/lmsitf/esb/client/TransferSiteInfoResultCallBackProcessorTest.java
 * 
 * FILE NAME        	: TransferSiteInfoResultCallBackProcessorTest.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.lmsitf.esb.client;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.deppon.esb.inteface.domain.vehicle.SiteInfo;
import com.deppon.foss.framework.server.context.AppContext;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.ISynchronousExternalSystemService;
/**
 * LMS数据同步服务发送类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-12-5 下午7:25:54</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-12-5 下午7:25:54
 * @since
 * @version
 */
public class TransferSiteInfoResultCallBackProcessorTest{

    private static final Logger LOGGER = LoggerFactory.getLogger(TransferSiteInfoResultCallBackProcessorTest.class);
    
    protected ApplicationContext appContext = null;
    
    //“同步FOSS数据信息”给“外围系统”的应数据Service
    private ISynchronousExternalSystemService synchronousExternalSystemService;
    
    @Before
    public void before(){
	AppContext.initAppContext("application", "http://192.168.17.167/poc", "");
	appContext = new ClassPathXmlApplicationContext(
		"classpath*:com/deppon/foss/module/base/lmsitf/server/META-INF/springTest.xml",
		"classpath*:com/deppon/foss/module/base/lmsitf/server/META-INF/spring.xml",
		"classpath*:com/deppon/foss/module/base/dict/server/META-INF/spring.xml",
		"classpath*:com/deppon/foss/module/base/baseinfo/api/server/META-INF/spring.xml",
		"classpath*:com/deppon/foss/module/base/baseinfo/server/META-INF/spring.xml",
		"classpath*:com/deppon/foss/module/transfer/departure/server/META-INF/spring.xml",
		"classpath*:com/deppon/foss/module/pickup/predeliver/server/META-INF/spring.xml");
	synchronousExternalSystemService = (ISynchronousExternalSystemService) appContext.getBean("synchronousExternalSystemService");
    }
    
    /**
     * <p>同步“外场信息”到LMS</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-12-21 下午3:03:39
     * @see
     */
    @Ignore
    @Test
    public void testTransferSiteInfoResultCallBackProcessor(){
	LOGGER.info("****************** ESB Monitor Service Start ******************");
	
	List<SiteInfo> siteInfoList = new ArrayList<SiteInfo>();
	SiteInfo siteInfo = new SiteInfo();
	siteInfo.setAcitonType(2);
	siteInfo.setBigPlatform(22);
	siteInfo.setFieldpoleNo("56");
	siteInfo.setLoadingArea(new BigDecimal("22"));
	siteInfo.setPlaceArea(new BigDecimal("22"));
	siteInfo.setPlatformNumber(22);
	siteInfo.setPlatformSjNumber(22);
	siteInfo.setSiteNo("EE222");
	siteInfo.setSmallPlatform(16);
	
	siteInfoList.add(siteInfo);
	
	siteInfo = new SiteInfo();
	siteInfo.setAcitonType(3);
	siteInfo.setBigPlatform(123);
	siteInfo.setFieldpoleNo("02");
	siteInfo.setLoadingArea(new BigDecimal("123"));
	siteInfo.setPlaceArea(new BigDecimal("123"));
	siteInfo.setPlatformNumber(11);
	siteInfo.setPlatformSjNumber(11);
	siteInfo.setSiteNo("DD123");
	siteInfo.setSmallPlatform(123);
	
	siteInfoList.add(siteInfo);
	
	synchronousExternalSystemService.synchronizeOutfieldDataToLms(siteInfoList);
	
	LOGGER.info("****************** ESB Monitor Service End ******************");
    }
}

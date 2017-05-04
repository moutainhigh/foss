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
 * PROJECT NAME	: bse-baseinfo
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/test/BaseTestCase.java
 * 
 * FILE NAME        	: BaseTestCase.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;

import com.deppon.foss.util.test.DaoDBUnitSupportUnitTests;

/**
 * Service测试基类
 * @author ibm-zhuwei
 * @date 2012-10-18 下午7:48:22
 */
@ContextConfiguration( 
        locations = {"classpath:com/deppon/foss/module/base/baseinfo/server/META-INF/springTest.xml"})

public abstract class BaseTestCase extends DaoDBUnitSupportUnitTests {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 
     */
   
    public BaseTestCase() {
    	
    }
    
}


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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/uumsitf/esb/server/OrgAdministrativeInfoProcessorTest.java
 * 
 * FILE NAME        	: OrgAdministrativeInfoProcessorTest.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.uumsitf.esb.server;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.deppon.foss.framework.server.context.AppContext;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.uums.inteface.domain.usermanagement.AdminOrgInfo;
import com.deppon.uums.inteface.domain.usermanagement.SendAdminOrgRequest;
/**
 * UUMS数据同步 单元测试
 * 
 * @author 100847-foss-GaoPeng
 * @date 2012-11-22 下午1:37:41
 * @since
 * @version
 */
//@Ignore
public class OrgAdministrativeInfoProcessorTest{

    private static final Logger logger = LoggerFactory.getLogger(OrgAdministrativeInfoProcessorTest.class);
    
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

    @Ignore
    @Test
    public void orgAdministrativeInfoProcessor() throws Exception {
	SendAdminOrgRequest entity =getEntity();
	OrgAdministrativeInfoProcessor proc = new OrgAdministrativeInfoProcessor();
	try{
	    proc.process(entity);
	}catch(Exception e){
	    logger.error(e.getMessage());
	}
    }
    
    public static SendAdminOrgRequest getEntity() {
	SendAdminOrgRequest request = (SendAdminOrgRequest) createObj(
		SendAdminOrgRequest.class.getName());
	AdminOrgInfo entity =(AdminOrgInfo) createObj(
		AdminOrgInfo.class.getName());
	entity = new AdminOrgInfo();
	entity.setSubCompCode("ZGS001");
	String id = UUIDUtils.getUUID();
	entity.setOrgId(id);
	entity.setOrgCode(id);
	entity.setChangeType("1");
	List<AdminOrgInfo> admins = request.getAdminOrgInfo();
	admins.add(entity);
	
	entity =(AdminOrgInfo) createObj(
		AdminOrgInfo.class.getName());
	entity.setSubCompCode("ZGS001");
	entity.setOrgId(id);
	entity.setOrgCode(id);
	entity.setChangeType("2");
	admins.add(entity);
	

	entity =(AdminOrgInfo) createObj(
		AdminOrgInfo.class.getName());
	entity.setSubCompCode("ZGS001");
	entity.setOrgId(id);
	entity.setOrgCode(id);
	entity.setChangeType("3");
	admins.add(entity);
	
	return request;
    }

    static int inLevel = 0;

    public static Object createObj(String cls, int level) {
	try {
	    ++inLevel;

	    Object o = Class.forName(cls).newInstance();
	    if (inLevel == 3) {
		return o;
	    }
	    if ("java.util.Date".equals(cls)) {
		return o;
	    }

	    @SuppressWarnings("rawtypes")
	    Set set = getSuperMethod(Object.class);
	    java.lang.reflect.Method[] ms = o.getClass().getMethods();
	    for (int i = 0, l = ms.length; i < l; i++) {
		java.lang.reflect.Method field = ms[i];
		String fieldName = field.getName();
		if (!set.contains(fieldName)) {
		    if (fieldName.startsWith("set")) {
			@SuppressWarnings("rawtypes")
			Class[] innerClasses = ms[i].getParameterTypes();
			String clsName = innerClasses[0].getName();
			Object o2 = null;
			if ("java.lang.String".equals(clsName)) {
			    o2 = "1";
			} else {
			    o2 = createObj(innerClasses[0].getName(), 2);
			}
			ms[i].invoke(o, o2);
		    }
		}
	    }
	    --inLevel;
	    return o;
	} catch (Exception e) {
	}
	return null;
    }
    

    @SuppressWarnings("rawtypes")
    public static Object createObj(String cls) {
	try {
	    ++inLevel;

	    Object o = Class.forName(cls).newInstance();
	    if (inLevel == 3) {
		return o;
	    }
	    if ("java.util.Date".equals(cls)) {
		return o;
	    }

	    Set set = getSuperMethod(Object.class);
	    java.lang.reflect.Method[] ms = o.getClass().getMethods();
	    for (int i = 0, l = ms.length; i < l; i++) {
		java.lang.reflect.Method field = ms[i];
		String fieldName = field.getName();
		if (!set.contains(fieldName)) {
		    if (fieldName.startsWith("set")) {
			Class[] innerClasses = ms[i].getParameterTypes();
			String clsName = innerClasses[0].getName();
			Object o2 = null;
			if ("java.lang.String".equals(clsName)) {
			    o2 = "Y";
			} else {
			    String paramClsName = innerClasses[0].getName();
			    if ("java.util.Collection".equals(paramClsName)) {
				o2 = new ArrayList<Object>();
			    } else if ("java.lang.Integer".equals(paramClsName)) {
				o2 = new Integer(1);
			    }else if ("java.util.List".equals(paramClsName)) {
				o2 = new ArrayList();
			    } else if ("java.lang.Byte".equals(paramClsName)) {
				o2 = new Byte("1");
			    } else if ("java.lang.Boolean".equals(paramClsName)) {
				o2 = new Boolean("True");
			    } else if ("java.math.BigDecimal".equals(paramClsName)) {
				o2 = new BigDecimal(123);
			    } else if ("boolean".equals(paramClsName)) {
				o2 = new Boolean("True");
			    } else {
				o2 = Class.forName(paramClsName).newInstance();
			    }
			}
			ms[i].invoke(o, o2);
		    }
		}
	    }
	    --inLevel;
	    return o;
	} catch (Exception e) {
	}
	return null;
    }

    @SuppressWarnings("rawtypes")
    public static Set getSuperMethod(Class cls) {
	Set<String> set = new HashSet<String>();
	Method[] m = cls.getMethods();
	for (int i = 0, l = m.length; i < l; i++) {
	    set.add(m[i].getName());
	}
	return set;
    }
}

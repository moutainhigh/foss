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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/server/service/org/PublicBankAccountServiceTest.java
 * 
 * FILE NAME        	: PublicBankAccountServiceTest.java
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
package com.deppon.foss.module.base.baseinfo.server.service.org;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;

import com.deppon.foss.base.util.define.SymbolConstants;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPublicBankAccountService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PublicBankAccountEntity;
import com.deppon.foss.module.base.baseinfo.server.service.impl.PublicBankAccountService;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 营业部适用产品-服务-测试类
 * 
 * @author foss-zhujunyong
 * @date Oct 30, 2012 10:53:20 AM
 * @version 1.0
 */
@Ignore
public class PublicBankAccountServiceTest {

    /**
     * 测试新增
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 下午2:05:11
     */
    @Test
    public void addPublicBankAccount() {
	PublicBankAccountEntity entity = getEntity();

	entity = publicBankAccountService.addPublicBankAccount(entity);
	Assert.assertNotNull(entity);
	
	deleteById(entity.getId());
    }

    /**
     * 测试作废
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 下午2:05:26
     */

    @Test
    public void deletePublicBankAccount() {
	PublicBankAccountEntity entity = getEntity();

	entity = publicBankAccountService.addPublicBankAccount(entity);
	entity = publicBankAccountService.deletePublicBankAccount(entity);
	Assert.assertNotNull(entity);

	deleteById(entity.getId());
    }

    /**
     * 测试批量作废
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 下午2:05:26
     */

    @Test
    public void deletePublicBankAccountMore() {
	PublicBankAccountEntity entity = getEntity();

	entity = publicBankAccountService.addPublicBankAccount(entity);
	entity = publicBankAccountService.deletePublicBankAccountMore(entity
		.getFid().split(SymbolConstants.EN_COMMA), entity
		.getCreateUser());
	Assert.assertNotNull(entity);

	deleteById(entity.getId());
    }

    /**
     * 测试更新
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 下午2:05:26
     */
    @Test
    public void updatePublicBankAccount() {
	// 准备测试数据
	PublicBankAccountEntity entity = getEntity();
	entity = publicBankAccountService.addPublicBankAccount(entity);

	entity = publicBankAccountService.updatePublicBankAccount(entity);
	Assert.assertNotNull(entity);

	deleteById(entity.getId());
    }

    /**
     * 以下全为查询
     */

    /**
     * 测试 精确查询 通过 BANK_ACC 查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 下午2:05:26
     */
    @Test
    public void queryPublicBankAccountByFid() {
	// 准备测试数据
	PublicBankAccountEntity entity = getEntity();
	entity = publicBankAccountService.addPublicBankAccount(entity);

	entity = publicBankAccountService
		.queryPublicBankAccountByFid(entity.getFid());
	Assert.assertNotNull(entity);

	deleteById(entity.getId());
    }

    /**
     * 测试 精确查询 根据多个编码批量查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 下午2:05:26
     */
    @Test
    public void queryPublicBankAccountBatchByFid() {
	// 准备测试数据
	PublicBankAccountEntity entity = getEntity();
	entity = publicBankAccountService.addPublicBankAccount(entity);

	List<PublicBankAccountEntity> entitys = publicBankAccountService
		.queryPublicBankAccountBatchByFid(entity.getFid()
			.split(SymbolConstants.EN_COMMA));
	Assert.assertNotNull(CollectionUtils.isEmpty(entitys));

	deleteById(entity.getId());
    }

    /**
     * 测试 精确查询 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 下午2:05:26
     */
    @Test
    public void queryPublicBankAccountExactByEntity() {
	// 准备测试数据
	PublicBankAccountEntity entity = getEntity();
	entity = publicBankAccountService.addPublicBankAccount(entity);

	List<PublicBankAccountEntity> entitys = publicBankAccountService
		.queryPublicBankAccountExactByEntity(
			new PublicBankAccountEntity(), 0, 1);
	Assert.assertNotNull(CollectionUtils.isEmpty(entitys));

	deleteById(entity.getId());
    }

    /**
     * 测试 精确查询-查询总条数，用于分页 动态的查询条件。
     * 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 下午2:05:26
     */
    @Test
    public void queryPublicBankAccountExactByEntityCount() {
	// 准备测试数据
	PublicBankAccountEntity entity = getEntity();
	entity = publicBankAccountService.addPublicBankAccount(entity);

	long result = publicBankAccountService
		.queryPublicBankAccountExactByEntityCount(new PublicBankAccountEntity());
	Assert.assertTrue(result > 0);

	deleteById(entity.getId());
    }

    /**
     * 测试 模糊查询 动态的查询条件。
     * 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为模糊查询的查询条件
     * 
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 下午2:05:26
     */
    @Test
    public void queryPublicBankAccountByEntity() {
	// 准备测试数据
	PublicBankAccountEntity entity = getEntity();
	entity = publicBankAccountService.addPublicBankAccount(entity);

	List<PublicBankAccountEntity> entitys = publicBankAccountService
		.queryPublicBankAccountByEntity(new PublicBankAccountEntity(),
			0, 1);
	Assert.assertNotNull(CollectionUtils.isEmpty(entitys));

	deleteById(entity.getId());
    }

    /**
     * 测试 动态的查询条件-查询总条数。
     * 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为模糊查询的查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 下午2:05:26
     */
    @Test
    public void queryPublicBankAccountByEntityCount() {
	// 准备测试数据
	PublicBankAccountEntity entity = getEntity();
	entity = publicBankAccountService.addPublicBankAccount(entity);

	long result = publicBankAccountService
		.queryPublicBankAccountByEntityCount(new PublicBankAccountEntity());
	Assert.assertTrue(result > 0);

	deleteById(entity.getId());
    }

    /**
     * 下面是特殊方法
     */

    /**
     * 测试
     * 
     * 通过MotorcadeCode车队编码 标识来删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 下午2:05:26
     */
    @Test
    public void deletePublicBankAccountByMotorcadeCode() {
	// 准备测试数据
	PublicBankAccountEntity entity = getEntity();
	entity = publicBankAccountService.addPublicBankAccount(entity);

	PublicBankAccountEntity result = publicBankAccountService
		.deletePublicBankAccount(entity);
	Assert.assertTrue(result != null);

	deleteById(entity.getId());
    }

    /**
     * 下面是测试所使用的工具
     */

    private JdbcTemplate jdbc;

    private IPublicBankAccountService publicBankAccountService;

    /**
     * 删除测试的实体
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 下午2:02:00
     */
    public void deleteById(String id) {
	jdbc.execute("delete from BSE.T_BAS_PUBLIC_BANK_ACCOUNT where id = '"
		+ id + "'");

    }

    @Before
    public void setUp() throws Exception {
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(
		JdbcTemplate.class);
	publicBankAccountService = (IPublicBankAccountService) SpringTestHelper
		.get().getBeanByClass(PublicBankAccountService.class);
    }

    @After
    public void tearDown() throws Exception {
    }

    public static PublicBankAccountEntity getEntity() {
	PublicBankAccountEntity entity = (PublicBankAccountEntity) createObj(PublicBankAccountEntity.class
		.getName());
	entity.setBankAcc(System.currentTimeMillis() + "");
	entity.setActive(FossConstants.ACTIVE);
	entity.setAccountStatus(FossConstants.ACTIVE);
	String id = UUIDUtils.getUUID();
	entity.setId(id);
	entity.setFid(id);
	entity.setCreateUser("087584-foss-lijun-needDelete");
	return entity;
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
			    o2 = "1";
			} else {
			    String paramClsName = innerClasses[0].getName();
			    if ("java.util.Collection".equals(paramClsName)) {
				o2 = new ArrayList<Object>();
			    } else if ("java.lang.Integer".equals(paramClsName)) {
				o2 = new Integer(1);
			    } else if ("java.util.List".equals(paramClsName)) {
				o2 = new ArrayList();
			    } else if ("java.lang.Byte".equals(paramClsName)) {
				o2 = new Byte("1");
			    } else if ("java.lang.Boolean".equals(paramClsName)) {
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

/*
 * PROJECT NAME: pkp-predeliver
 * PACKAGE NAME: com.deppon.foss.module
 * FILE    NAME: QueryGoodsDaoTest.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.departure.server.dao;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.deppon.foss.module.transfer.departure.api.server.dao.ISharedDao;
import com.deppon.foss.module.transfer.departure.server.dao.impl.SharedDao;

/**
 * 
 * 通知客户DAO测试类
 * 
 * @author ibm-wangfei
 * @date Oct 24, 2012 5:06:00 PM
 */
public class SharedDaoTest {

	private ISharedDao sharedDao = null;
	private static ApplicationContext ctx = null;

	String[] xmls = new String[] { "com/deppon/foss/module/departrue/test/META-INF/spring.xml" };

	@Before
	public void init() throws Exception {
		try {
			if (ctx == null || sharedDao == null) {
				ctx = new ClassPathXmlApplicationContext(xmls);
				sharedDao = ctx.getBean(SharedDao.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 *  
	 * 测试新增运单客户通知记录
	 * @author ibm-liubinbin
	 * @date Oct 24, 2012 5:29:55 PM
	 */
	@Test
	public void addNotificationEntity() {
		//sharedDao.autoCancle();
	}
	
	
}

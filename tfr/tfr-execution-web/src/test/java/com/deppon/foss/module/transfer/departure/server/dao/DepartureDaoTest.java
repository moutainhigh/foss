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

import com.deppon.foss.module.transfer.departure.api.server.dao.IDepartureDao;
import com.deppon.foss.module.transfer.departure.server.dao.impl.DepartureDao;

/**
 * 
 * 通知客户DAO测试类
 * 
 * @author ibm-wangfei
 * @date Oct 24, 2012 5:06:00 PM
 */
public class DepartureDaoTest
{

	private IDepartureDao departureDao = null;
	private static ApplicationContext ctx = null;

	String[] xmls = new String[]
	{ "com/deppon/foss/module/departrue/test/META-INF/spring.xml" };

	@Before
	public void init() throws Exception
	{
		try
		{
			if (ctx == null || departureDao == null)
			{
				ctx = new ClassPathXmlApplicationContext(xmls);
				departureDao = ctx.getBean(DepartureDao.class);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 测试新增运单客户通知记录
	 * 
	 * @author ibm-liubinbin
	 * @date Oct 24, 2012 5:29:55 PM
	 */
	@Test
	public void addNotificationEntity()
	{
		departureDao.getHandoverBillsByDepartId("123");
		System.out.println(departureDao.getPDADepartResult(
				"fb9a1ce3-4993-448e-85dd-1663006d511b").getDriverName());
		departureDao.getSealNosByDepartId("12321321");
	}

}

/**
 *  initial comments.
 */
/*
 * PROJECT NAME: pkp-predeliver
 * PACKAGE NAME: com.deppon.foss.module
 * FILE    NAME: QueryGoodsDaoTest.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.pickup.predeliver.server.dao;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.deppon.foss.module.pickup.predeliver.api.shared.define.ExceptionProcessConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ExceptionEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ExceptionProcessDetailEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ExceptionProcessConditionDto;
import com.deppon.foss.module.pickup.predeliver.server.dao.impl.ExceptionProcessDao;
import com.deppon.foss.util.define.FossConstants;

/**
 * 查询货量测试类
 * 
 * @author 043258-foss-zhaobin
 * @date 2012-10-23 下午2:33:17
 */
public class ExceptionProcessTest {

	private ExceptionProcessDao exceptionProcessDao = null;

	private static ApplicationContext ctx = null;

	String[] xmls = new String[] {
			"com/deppon/foss/module/pickup/predeliver/test/META-INF/spring.xml",
			"com/deppon/foss/module/pickup/predeliver/server/META-INF/spring.xml" };
		
	@Before
	public void init() throws Exception {
		try {
			if (ctx == null || exceptionProcessDao == null) {
				ctx = new ClassPathXmlApplicationContext(xmls);
				exceptionProcessDao = ctx.getBean(ExceptionProcessDao.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testAdd() {
		ExceptionEntity exceptionEntity = new ExceptionEntity();
		ExceptionProcessDetailEntity exceptionProcessDetailEntity = new ExceptionProcessDetailEntity();
		exceptionEntity.setId("2012002");
		exceptionEntity.setStatus("1");
		
		exceptionProcessDetailEntity.setId("010");
		exceptionProcessDetailEntity.settSrvExceptionId("2012002");
		exceptionProcessDetailEntity.setNotes("ABC");
		
		exceptionProcessDao.addExceptionProcessDetail(exceptionProcessDetailEntity);
		//exceptionProcessDao.updateExceptionProcessInfo(exceptionEntity);
	}
	@Test
	public void testQueryExceptionProcess() {
		ExceptionProcessConditionDto exceptionEntity = new ExceptionProcessConditionDto();
		exceptionEntity.setWaybillNo("90909801");
		exceptionEntity.setExceptionType(ExceptionProcessConstants.WAYBILL_EXCEPTION);
		exceptionEntity.setExceptionLink(ExceptionProcessConstants.CUSTOMER_NOTICE);
		exceptionEntity.setStatus(ExceptionProcessConstants.HANDLING);
		exceptionEntity.setActive(FossConstants.ACTIVE);
		exceptionProcessDao.queryExceptionProcess(exceptionEntity);
		//exceptionProcessDao.updateExceptionProcessInfo(exceptionEntity);
	}
	
	@Test
	public void testQueryExceptionProcessInfo() 
	{
		ExceptionProcessConditionDto exceptionEntity = new ExceptionProcessConditionDto();
		exceptionEntity.setWaybillNo("201301183");
		exceptionEntity.setActive(FossConstants.ACTIVE);
		exceptionProcessDao.queryExceptionProcessInfo(exceptionEntity, 0, 10);
	}

}
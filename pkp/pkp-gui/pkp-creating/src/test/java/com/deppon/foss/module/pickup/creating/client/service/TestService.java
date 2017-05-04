/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
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
 * PROJECT NAME	: pkp-creating
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/pickup/creating/client/service/TestService.java
 * 
 * FILE NAME        	: TestService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.creating.client.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.mybatis.guice.XMLMyBatisModule;
import org.mybatis.guice.datasource.helper.JdbcHelper;

import com.deppon.foss.framework.client.component.dataaccess.ClassPathFileScanner;
import com.deppon.foss.framework.client.component.dataaccess.DefaultTransactionFactory;
import com.deppon.foss.framework.client.component.dataaccess.GuiceContextFactroy;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OutfieldEntity;
import com.deppon.foss.module.pickup.common.client.guice.BaseInfoModule;
import com.deppon.foss.module.pickup.common.client.guice.CommonModule;
import com.deppon.foss.module.pickup.common.client.service.IBaseDataService;
import com.deppon.foss.module.pickup.common.client.service.IDataDictionaryValueService;
import com.deppon.foss.module.pickup.common.client.service.IWaybillFreightRouteService;
import com.deppon.foss.module.pickup.common.client.service.IWaybillStockService;
import com.deppon.foss.module.pickup.common.client.service.impl.WaybillFreightRouteService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillStoreException;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

public class TestService {

	private Injector injector;

	private IBaseDataService baseDataService;

	private IDataDictionaryValueService dataDictionaryValueService;

	private ILocalRuleVerifyService localRuleVerifyService;

	private IWaybillStockService waybillStockService;
	
	private IWaybillFreightRouteService waybillFreightRouteService;

	private String suffix = "Mapper.xml";

	@Before
	public void setupMyBatisGuice() throws Exception {

		// bindings
		List<Module> modules = this.createMyBatisModule();
		this.injector = Guice.createInjector(modules);

		List<URL> mappers = new ArrayList<URL>();
		try {
			mappers.addAll(ClassPathFileScanner.scanFile(this.getClass()
					.getClassLoader(), suffix));
		} catch (Throwable t) {
			t.printStackTrace();
		}
		buildStatement(mappers);

		/*
		 * this.baseDataService = this.injector
		 * .getInstance(IBaseDataService.class); this.dataDictionaryService =
		 * this.injector .getInstance(IDataDictionaryService.class);
		 * this.localRuleVerifyService = this.injector
		 * .getInstance(ILocalRuleVerifyService.class);
		 */

		this.waybillFreightRouteService = this.injector
				.getInstance(WaybillFreightRouteService.class);
		// this.waybillRemotingService =
		// this.injector.getInstance(IWaybillRemotingService.class);
	}

	protected List<Module> createMyBatisModule() {
		List<Module> modules = new ArrayList<Module>();
		modules.add(JdbcHelper.HSQLDB_IN_MEMORY_NAMED);
		modules.add(new XMLMyBatisModule() {
			@Override
			protected void initialize() {
				setEnvironmentId("development");
				setClassPathResource("com/deppon/foss/META-INF/mybatis.xml");
			}

		});

		modules.add(new BaseInfoModule());
		modules.add(new CommonModule());
		return modules;
	}

	private void buildStatement(List<URL> urls) {

		Configuration configuration2 = injector.getInstance(
				SqlSessionFactory.class).getConfiguration();
		try {
			for (URL url : urls) {
				try {
					String path = URLDecoder.decode(url.getPath(),
							System.getProperty("file.encoding"));
					InputStream inputStream2 = url.openStream();
					XMLMapperBuilder mapperBuilder1 = new XMLMapperBuilder(
							inputStream2, configuration2, path,
							configuration2.getSqlFragments());
					mapperBuilder1.parse();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	protected static Properties createTestProperties() {
		final Properties myBatisProperties = new Properties();
		myBatisProperties.setProperty("mybatis.environment.id", "development");
		myBatisProperties.setProperty("JDBC.username", "sa");
		myBatisProperties.setProperty("JDBC.password", "");
		myBatisProperties.setProperty("JDBC.autoCommit", "false");

		return myBatisProperties;
	}

/*	*//**
	 * 
	 * 测试始发库存和最终库存 * @author 026113-foss-linwensheng
	 * 
	 * @date 2012-11-28 上午10:53:21
	 *//*
	@Test
	public void testQueryStartStocksDepartmentService() {
		WaybillEntity waybillEntry = new WaybillEntity();
	
		*//**
		 * 测试集中接送货集中开单后，始发库存部门为配载部门
		 *//*
		waybillEntry.setCreateOrgCode("GS00002");
		waybillEntry.setLastLoadOrgCode("juh");
		try {

			String jsh = waybillStockService
					.queryStartStocksDepartmentService(waybillEntry);
			if (jsh != null) {
				System.out.println(jsh);
			}

		} catch (WaybillStoreException e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		
		
		
		
		*//**
		 * 测试集中接送货集中开单后，始发库存部门为配载部门
		 *//*
		waybillEntry.setCreateOrgCode("jsh");
		waybillEntry.setLoadOrgCode("PZBM");
		try {

			String jsh = waybillStockService
					.queryStartStocksDepartmentService(waybillEntry);
			if (jsh != null) {
				System.out.println(jsh);
			}

		} catch (WaybillStoreException e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}

		*//**
		 * 测试驻地营业部开单后，始发库存部门为驻地部门外场
		 *//*
		waybillEntry.setCreateOrgCode("W01060304");
		waybillEntry.setLoadOrgCode("PZBM");
		try {

			String zdbm = waybillStockService
					.queryStartStocksDepartmentService(waybillEntry);
			if (zdbm != null) {
				System.out.println(zdbm);
			}

		} catch (WaybillStoreException e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}

	}

	*//**
	 * 
	 * 测试最终到达部门
	 * 
	 * @author 026113-foss-linwensheng
	 * @date 2012-11-29 上午10:28:09
	 *//*
	@Test
	public void testQueryEndStocksDepartmentService() {
		WaybillEntity waybillEntry = new WaybillEntity();

		waybillEntry.setLastLoadOrgCode("W01060304");

		try {
			String zdbm = waybillStockService
					.queryEndStocksDepartmentService(waybillEntry);
			if (zdbm != null) {
				System.out.println(zdbm);
			}
		} catch (WaybillStoreException e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		
		
		waybillEntry.setLastLoadOrgCode("GS00002");

		try {
			String ZZPZ = waybillStockService
					.queryEndStocksDepartmentService(waybillEntry);
			if (ZZPZ != null) {
				System.out.println(ZZPZ);
			}
		} catch (WaybillStoreException e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		

	}
*/
	/*
	 * 
	 * @Test public void testFooService(){ List<DataDictionaryValueEntity>
	 * dataDicList = dataDictionaryService.queryProductType();
	 * assertNotNull(dataDicList); System.out.println(dataDicList.size());
	 * 
	 * List<DataDictionaryValueEntity> outerRemarkList =
	 * dataDictionaryService.queryOuterRemark(); assertNotNull(outerRemarkList);
	 * System.out.println(outerRemarkList.size());
	 * 
	 * //List<DataDictionaryValueEntity> pickupGoodsList =
	 * dataDictionaryService.queryPickUpGoods();
	 * //assertNotNull(pickupGoodsList);
	 * //System.out.println(pickupGoodsList.size());
	 * 
	 * boolean a = baseDataService.isInsurGoods("菜刀"); System.out.println(a);
	 * boolean b = baseDataService.isProhibitGoods("玻璃"); System.out.println(b);
	 * String c = baseDataService.queryLimitAmount("test");
	 * System.out.println(c); }
	 * 
	 * 
	 * 
	 * @Test public void testRemotingService(){ Result res =
	 * waybillRemotingService.checkWaybillNumber("21312321", "上海徐泾营业部");
	 * System.out.println(res.getCode()); System.out.println(res.getMsg()); }
	 * 
	 * 
	 * @Test public void testSysConfigService(){ //boolean a =
	 * dataDictionaryService.isVehicleDirect("9", "30"); boolean a =
	 * localRuleVerifyService.isValueGoods(10,"2","110000");
	 * Assert.assertTrue(a); //Assert.assertFalse(a); }
	 * 
	 * 
	 * @Test public void testBaseDataService(){ List<ProductEntity> list =
	 * baseDataService.queryTransType("10001");
	 * Assert.assertTrue(list.size()>0); //Assert.assertTrue(list.size()==0); }
	 * 
	 * 
	 * 
	 * @Test public void testRuleVerify(){ //boolean a =
	 * localRuleVerifyService.isWeightByVolume("5", "3");
	 * //Assert.assertTrue(a); //Assert.assertFalse(a);
	 * 
	 * Result res = localRuleVerifyService.verifyGoodsByDept("10002", 2, "2000",
	 * "1"); System.out.println(res.getCode());
	 * System.out.println(res.getMsg()); }
	 */
	
	//@Test 
	public void testWaybillFreightRouteService(){ 
		OutfieldEntity entity = waybillFreightRouteService.queryDefaultTransDept("W011304061105", "WHOLE_VEHICLE");
		System.out.println(entity.getName());
	}
}
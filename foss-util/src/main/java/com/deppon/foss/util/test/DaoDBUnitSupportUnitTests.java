package com.deppon.foss.util.test;

import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

/**
 * 结合dbunit的测试基类；
 * 使用DBUnitTestHelper来初始化测试数据；
 * 
 * 测试数据文件如果有待替换的值，则需要覆盖getDatasetReplacements方法；
 * 
 * 实例请参考：
 * 	bse/bse-baseinfo-api/src/test/java/com/deppon/foss/module/base/baseinfo/api/server/sample/SampleDaoTest.java
 * 用于该测试的数据文件为：
 * 	bse/bse-baseinfo-api/src/test/resources/com/deppon/foss/module/base/baseinfo/api/server/data
 *  请注意：该目录下必须有一个文件是table-ordering.txt文件，每一行是一个物理的表名；
 * 配置文件为：
 * 	bse/bse-baseinfo-api/src/test/resources/com/deppon/foss/module/base/baseinfo/api/server/spring-test.xml
 * 
 * @author zhengwl
 *
 */
public abstract class DaoDBUnitSupportUnitTests extends AbstractTransactionalJUnit4SpringContextTests {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired(required=false)
	private DBUnitTestHelper dbunitHelper;
	
	public void executeInitSql() {
		
	}
	
	public void executeDestorySql() {
		
	}
	
	@Before
	public void onSetUpInTransaction() throws Exception {
		executeInitSql();
		
		if ( dbunitHelper != null ) {
			dbunitHelper.setReplacements(this.getDatasetReplacements());
			dbunitHelper.setUp();
		}
	}

	@After
	public void onTearDownInTransaction() throws Exception {
		if ( dbunitHelper != null ) {
			dbunitHelper.tearDown();
		}
		executeDestorySql();
	}

	public DBUnitTestHelper getDbunitHelper() {
		return dbunitHelper;
	}

	public void setDbunitHelper(DBUnitTestHelper dbunitHelper) {
		this.dbunitHelper = dbunitHelper;
	}

	/**
	 * 返回用户测试数据文件中的替换值；可以由子类实现；
	 * 
	 * 返回一个map对象，其中的key是待替换的值，value是用于替换的值；
	 * @return
	 */
	protected Map getDatasetReplacements() {
		return null;
	}
}

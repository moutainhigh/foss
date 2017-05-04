package com.deppon.foss.module.base.baseinfo.server.service;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;

import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.server.service.impl.OrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.server.service.IDataDictionaryValueService;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.base.dict.server.service.impl.ConfigurationParamsService;
import com.deppon.foss.module.base.dict.server.service.impl.DataDictionaryValueService;
import com.deppon.foss.util.define.FossConstants;

/**
 * 数据字典server服务测试类
 * 
 * @author 088933-foss-zhangjiheng
 * @date 2013-4-16 下午7:19:52
 */
public class DataDictionaryServiceTest {

	private IDataDictionaryValueService dataDictionaryValueService;
	private IConfigurationParamsService configurationParamsService;
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	private JdbcTemplate jdbc;

	@Before
	public void setUp() throws Exception {
		jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(
				JdbcTemplate.class);
		dataDictionaryValueService = (IDataDictionaryValueService) SpringTestHelper.get().getBeanByClass(
				DataDictionaryValueService.class);
		configurationParamsService = (IConfigurationParamsService) SpringTestHelper.get().getBeanByClass(
				ConfigurationParamsService.class);
		orgAdministrativeInfoService = (IOrgAdministrativeInfoService) SpringTestHelper.get().getBeanByClass(OrgAdministrativeInfoService.class);
	}
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * 
	 * 新增测试类
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2013-4-16 下午7:23:03
	 */
	@Ignore
	@Test
	@Rollback(true)
	public void AddTest() {
		DataDictionaryValueEntity entity = add();
		entity=dataDictionaryValueService.addDataDictionaryValue(entity);
		Assert.assertNotNull(entity);
	}
	/**
	 * 
	 * 测试删除方法
	 * @author 088933-foss-zhangjiheng
	 * @date 2013-4-17 上午9:19:45
	 */
	@Ignore
	@Test
	public void delTest(){
		DataDictionaryValueEntity entity = add();
		entity=dataDictionaryValueService.addDataDictionaryValue(entity);
		entity=dataDictionaryValueService.deleteDataDictionaryValue(entity);
		Assert.assertNotNull(entity);
	}
    /**
     * 
     * 测试修改方法
     * @author 088933-foss-zhangjiheng
     * @date 2013-4-17 上午9:20:04
     */
	@Ignore
	@Test
	public void updateTest(){
		DataDictionaryValueEntity entity = add();
		entity=dataDictionaryValueService.addDataDictionaryValue(entity);
		entity=dataDictionaryValueService.updateDataDictionaryValue(entity);
		Assert.assertNotNull(entity);
	}
    
	/**
	 * 
	 * 根据词条编码查询
	 * @author 088933-foss-zhangjiheng
	 * @date 2013-4-17 上午9:21:01
	 */
	@Ignore
	@Test
	public void queryByTermsCode(){
		List list=dataDictionaryValueService.queryDataDictionaryValueByTermsCode("test1");
		Assert.assertEquals(1, list.size());
	}
	/**
	 * 
	 * 根据词条编码+值编码查询
	 * @author 088933-foss-zhangjiheng
	 * @date 2013-4-17 上午9:21:16
	 */
	@Ignore
	@Test
	public void queryByTermsCodeAndValueCode(){
		DataDictionaryValueEntity entity=dataDictionaryValueService.queryDataDictionaryValueByTermsCodeValueCode("test1", "test1");
	    Assert.assertNotNull(entity);
	}
	
	/**
	 * 
	 * 测试查询业务配置参数信息
	 * @author 088933-foss-zhangjiheng
	 * @date 2013-4-18 下午1:42:31
	 */
	@Test 
	public void queryConfigurationByOrgCode(){
		String parmMouudle="SYSTEM_CONFIG_PARM__TFR";
		String parmCode="FORECAST_START";
		OrgAdministrativeInfoEntity c = new OrgAdministrativeInfoEntity();
		c.setSalesDepartment(FossConstants.YES);
		List<OrgAdministrativeInfoEntity> orgAdministrativeInfoList = orgAdministrativeInfoService.queryOrgAdministrativeInfoExactByEntity(c, 0, Integer.MAX_VALUE);
		
		for (OrgAdministrativeInfoEntity entity : orgAdministrativeInfoList) {
		    System.out.println("entity = " + entity);
		    String orgCode = entity.getCode();
		    System.out.println("code = " + orgCode);
		    ConfigurationParamsEntity result=configurationParamsService.queryConfigurationParamsByOrgCode(parmMouudle,  parmCode,  orgCode);
		    Assert.assertNotNull(result);
		}
	}
	
	
	
	// 返回一个数据字典信息
	private DataDictionaryValueEntity add() {
		DataDictionaryValueEntity entity = new DataDictionaryValueEntity();
		entity.setId("test2");
		entity.setActive("Y");
		entity.setCreateDate(new Date());
		entity.setCreateUser("test2");
		entity.setTermsCode("test2");
		entity.setTermsName("test2");
		entity.setValueCode("test2");
		entity.setValueName("test2");
		entity.setVirtualCode("test2");
		return entity;

	}

}

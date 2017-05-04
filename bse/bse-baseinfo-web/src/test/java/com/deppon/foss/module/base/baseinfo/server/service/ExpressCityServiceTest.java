package com.deppon.foss.module.base.baseinfo.server.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressCityService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressCityEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressCityQueryDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressCityResultDto;
import com.deppon.foss.module.base.baseinfo.server.service.impl.ExpressCityService;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.util.define.FossConstants;

public class ExpressCityServiceTest {

	@SuppressWarnings("unused")
	private JdbcTemplate jdbc;

	private IExpressCityService expressCityService;

	private static final Logger log = Logger
			.getLogger(ExpressPartSalesDeptServiceTest.class);

	@Before
	public void setup() {
		jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(
				JdbcTemplate.class);
		expressCityService = (IExpressCityService) SpringTestHelper
				.get().getBeanByClass(ExpressCityService.class);
	}

	@After
	public void teardown() {
		jdbc = null;
	}
	
	/**
	 * 查询落地配/试点城市列表
	 * @author foss-qiaolifeng
	 * @date 2013-9-6 下午3:54:21
	 */
	@Ignore
	@Test
	public void testQueryExpressCityListByCondition(){
		ExpressCityQueryDto expressCityQueryDto = new ExpressCityQueryDto();
		expressCityQueryDto.setDistrictCode("310000-1");
		
		long count = expressCityService.queryExpressCityCountByCondition(expressCityQueryDto);
		if(count>0){
			List<ExpressCityResultDto> rtnList = expressCityService.queryExpressCityListByCondition(expressCityQueryDto, 0, 1);
			Assert.assertNull(rtnList);
		}
	}
	
	/**
	 * 根据条件查询一条落地配/试点城市
	 * @author foss-qiaolifeng
	 * @date 2013-9-6 下午3:54:21
	 */
	@Ignore
	@Test
	public void testQueryOneExpressCityByCondition(){
		ExpressCityQueryDto expressCityQueryDto = new ExpressCityQueryDto();
		expressCityQueryDto.setDistrictCode("310000-1");
		
		ExpressCityResultDto entity = expressCityService.queryOneExpressCityByCondition(expressCityQueryDto);
		Assert.assertNull(entity);
	}

	/**
	 * 新增
	 * @author foss-qiaolifeng
	 * @date 2013-9-6 下午3:54:21
	 */
	@Ignore
	@Test
	public void testAddExpressCityList(){
		
		List<ExpressCityEntity> expressCityEntityList = new ArrayList<ExpressCityEntity>();
		ExpressCityEntity expressCityEntity = new ExpressCityEntity();
		expressCityEntity.setDistrictCode("310000-1");
		expressCityEntity.setType(DictionaryValueConstants.EXPRESS_CITY_TYPE_LDP);
		expressCityEntityList.add(expressCityEntity);
		
		UserEntity user = new UserEntity();
		EmployeeEntity employee = new EmployeeEntity();
		employee.setEmpCode("000123");
		employee.setEmpName("张三");
		user.setEmployee(employee);
		user.setUserName("zhangsan");
		OrgAdministrativeInfoEntity dept = new OrgAdministrativeInfoEntity();
		dept.setCode("1");
		dept.setName("德邦物流");
		CurrentInfo currentInfo = new CurrentInfo(user, dept);
		
		expressCityService.addExpressCityList(expressCityEntityList, currentInfo);
	}
	
	/**
	 * 作废
	 * @author foss-qiaolifeng
	 * @date 2013-9-6 下午3:54:21
	 */
	@Ignore
	@Test
	public void testDisabledExpressCity(){
		
		ExpressCityQueryDto expressCityQueryDto = new ExpressCityQueryDto();
		expressCityQueryDto.setSelectedIds("123121233111,325464565565");
		
		UserEntity user = new UserEntity();
		EmployeeEntity employee = new EmployeeEntity();
		employee.setEmpCode("000123");
		employee.setEmpName("张三");
		user.setEmployee(employee);
		user.setUserName("zhangsan");
		OrgAdministrativeInfoEntity dept = new OrgAdministrativeInfoEntity();
		dept.setCode("1");
		dept.setName("德邦物流");
		CurrentInfo currentInfo = new CurrentInfo(user, dept);
		
		expressCityService.disabledExpressCity(expressCityQueryDto, currentInfo);
	}
	
	/**
	 * 修改
	 * @author foss-qiaolifeng
	 * @date 2013-9-6 下午3:54:21
	 */
	@Ignore
	@Test
	public void testUpdateExpressCity(){
		
		ExpressCityEntity oldExpressCityEntity = new ExpressCityEntity();
		oldExpressCityEntity.setDistrictCode("310000-1");
		oldExpressCityEntity.setType(DictionaryValueConstants.EXPRESS_CITY_TYPE_LDP);
		
		List<ExpressCityEntity> newExpressCityEntityList = new ArrayList<ExpressCityEntity>();
		ExpressCityEntity expressCityEntity = new ExpressCityEntity();
		expressCityEntity.setDistrictCode("110000-1");
		expressCityEntity.setType(DictionaryValueConstants.EXPRESS_CITY_TYPE_LDP);
		newExpressCityEntityList.add(expressCityEntity);
		
		UserEntity user = new UserEntity();
		EmployeeEntity employee = new EmployeeEntity();
		employee.setEmpCode("000123");
		employee.setEmpName("张三");
		user.setEmployee(employee);
		user.setUserName("zhangsan");
		OrgAdministrativeInfoEntity dept = new OrgAdministrativeInfoEntity();
		dept.setCode("1");
		dept.setName("德邦物流");
		CurrentInfo currentInfo = new CurrentInfo(user, dept);
		
		expressCityService.updateExpressCity(oldExpressCityEntity, newExpressCityEntityList, currentInfo);
	}
	
	/**
	 * 根据营业部网点编码获取该营业部所在城市的落地配/试点城市类型(优先试点城市)
	 * @author foss-qiaolifeng
	 * @date 2013-9-6 下午3:54:21
	 */
	@Ignore
	@Test
	public void testQueryExpressCityTypeByOrgCode(){
		
		String orgCode = "310000-1";
		ExpressCityResultDto entity = expressCityService.queryExpressCityTypeByOrgCode(orgCode);
		Assert.assertNull(entity);
	}
	
	/**
	 * 根据营业部网点编码获取该营业部所在城市的落地配/试点城市类型（优先落地配城市）
	 * @author foss-qiaolifeng
	 * @date 2013-9-6 下午3:54:21
	 */
	@Ignore
	@Test
	public void testQueryLdpExpressCityTypeByOrgCode(){
		
		String orgCode = "310000-1";
		ExpressCityResultDto entity = expressCityService.queryLdpExpressCityTypeByOrgCode(orgCode);
		Assert.assertNull(entity);
	}
	
	/**
	 * 根据营业部编码查询落地配/试点城市映射信息
	 * @author foss-qiaolifeng
	 * @date 2013-9-6 下午3:54:21
	 */
	@Ignore
	@Test
	public void testQueryExpressCityByOrgCode(){
		
		String orgCode = "310000-1";
		String active = FossConstants.ACTIVE;
		List<ExpressCityEntity> rtnList = expressCityService.queryExpressCityByOrgCode(orgCode, active);
		Assert.assertNull(rtnList);
	}
	
}

package com.deppon.foss.module.pickup.server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.module.pickup.pricing.api.server.service.IExternalPriceSchemeService;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ExternalPriceSchemeEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.ExternalPriceSchemeException;
import com.deppon.foss.util.DateUtils;

public class ExternalPriceSchemeServiceTest {
	private IExternalPriceSchemeService externalPriceSchemeService;
	@Before
	public void init(){
		String[] paths = {"classpath*:com/deppon/foss/module/pickup/pricing/server/META-INF/springTest.xml"};
		ApplicationContext context = new ClassPathXmlApplicationContext(paths);
		externalPriceSchemeService=(IExternalPriceSchemeService) context.getBean("externalPriceSchemeService");
	}
	/**
	 * @author 092020-lipengfei
	 * @description 根据参数查询偏线外发价格方案
	 * @param entity
	 * @return List<ExternalPriceSchemeEntity>
	 */
	 @Test
	public void queryExternalPriceSchemeByParamTest(){
		 ExternalPriceSchemeEntity entity=new ExternalPriceSchemeEntity();
		 entity.setAgentDeptCode("PX1026");
		 List<ExternalPriceSchemeEntity> entityList=externalPriceSchemeService.queryExternalPriceSchemeByParam(entity, 10, 0);
		 Assert.assertEquals(2, entityList.size());
	 }
	/**
	 * @author 092020-lipengfei
	 * @description 根据参数查询偏线外发价格方案总量
	 * @param entity
	 * @return Long
	 */
	 @Test
	public void queryRecordCountTest(){
		 ExternalPriceSchemeEntity entity=new ExternalPriceSchemeEntity();
		 long result = externalPriceSchemeService.queryRecordCount(entity);
		 Assert.assertEquals(2, result);
	 }
	/**
	 * @author 092020-lipengfei
	 * @description 根据ID查询偏线外发价格方案
	 * @param entity
	 * @return List<ExternalPriceSchemeEntity>
	 */
	 @Test
	public void queryExternalePriceSchemeByIdTest(){
		 String schemeId="ed21fcac-02c2-47e0-b88e-9acb5c7a223a";
		 ExternalPriceSchemeEntity entity=externalPriceSchemeService.queryExternalePriceSchemeById(schemeId);
		 System.out.println(entity.getSchemeName());
		 Assert.assertNotNull(entity);
	 }
	/**
	 * @author 092020-lipengfei
	 * @description 新增偏线外发价格方案
	 * @param entity
	 * @return Integer
	 */
	 @Test
	public void addExternalPriceSchemeTest(){
		 ExternalPriceSchemeEntity entity=new ExternalPriceSchemeEntity();
		 entity.setAgentDeptCode("PX1026");
		 entity.setAgentDeptName("广州转梧州金皇");
		 entity.setBeginTime(DateUtils.convert("2014-12-31 23:59:59", DateUtils.DATE_TIME_FORMAT));
		 entity.setProvCode("440000");
		 entity.setProvName("广东省");
		 entity.setCityCode("440100");
		 entity.setCityName("广州市");
		 entity.setCountyCode("440111");
		 entity.setCountyName("白云区");
		 entity.setExternalOrgCode("W02020111");
		 entity.setExternalOrgName("广州白云区新市运作部");
		 entity.setHeavyPrice(new BigDecimal(2.0));
		 entity.setLightPrice(new BigDecimal(3.0));
		 entity.setLowestPrice(new BigDecimal(20));
		 entity.setSchemeName("TEST1");
		 entity.setCreateUser("092020");
		 entity.setTransportType(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C1_C20001_ZH);
		 int result=0;
		 try {
			  result=externalPriceSchemeService.addExternalPriceScheme(entity);
		} catch (ExternalPriceSchemeException e) {
			System.out.println(e.getErrorCode());
		}
		 Assert.assertEquals(1, result);
	 }
	/**
	 * @author 092020-lipengfei
	 * @description 修改偏线外发价格方案
	 * @param entityList
	 * @return Integer
	 */
	 @Test
	public void updateExternalPriceSchemeTest(){
		 ExternalPriceSchemeEntity entity=new ExternalPriceSchemeEntity();
		 entity.setId("ab3f9653-51a9-46c2-a9a4-72817fa3d71d");
		 entity.setAgentDeptCode("PX1026");
		 entity.setAgentDeptName("广州转梧州金皇");
		 entity.setBeginTime(DateUtils.convert("2014-05-31 23:59:59", DateUtils.DATE_TIME_FORMAT));
		 entity.setProvCode("440000");
		 entity.setProvName("广东省");
		 entity.setCityCode("440100");
		 entity.setCityName("广州市");
		 entity.setCountyCode("440111");
		 entity.setCountyName("白云区");
		 entity.setExternalOrgCode("W02020111");
		 entity.setExternalOrgName("广州白云区新市运作部");
		 entity.setHeavyPrice(new BigDecimal(2.0));
		 entity.setLightPrice(new BigDecimal(3.0));
		 entity.setLowestPrice(new BigDecimal(20));
		 entity.setSchemeName("TEST2");
		 entity.setModifyUser("092020");
		 entity.setTransportType(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C1_C20003_ZH);
		 int result =externalPriceSchemeService.updateExternalPriceScheme(entity);
		 Assert.assertEquals(1, result);
	 }
	/**
	 * @author 092020-lipengfei
	 * @description 删除偏线外发价格方案
	 * @param idList
	 * @return Integer
	 */
	 @Test
	public void deleteExternalPriceSchemeByIdTest(){
		 List<String> idList =new ArrayList<String>();
		 idList.add("ab3f9653-51a9-46c2-a9a4-72817fa3d71d");
		 idList.add("ed21fcac-02c2-47e0-b88e-9acb5c7a223a");
		 int result =externalPriceSchemeService.deleteExternalPriceSchemeById(idList);
		 Assert.assertEquals(2, result);
	 }
	/**
	 * @author 092020-lipengfei
	 * @description 激活偏线外发价格方案
	 * @param idList
	 * @return Integer
	 */
	 @Test
	public void activateExternalPriceSchemeByIdTest(){
		 List<ExternalPriceSchemeEntity> entityList=new ArrayList<ExternalPriceSchemeEntity>();
		 ExternalPriceSchemeEntity entity=new ExternalPriceSchemeEntity();
		 entity.setId("c953dcdf-f458-4134-a2ff-9ee9dca7f637");
		 entity.setAgentDeptCode("PX1026");
		 entity.setExternalOrgCode("W02020111");
		 entity.setBeginTime(DateUtils.convert("2014-12-30 23:59:59", DateUtils.DATE_TIME_FORMAT));
		 entity.setModifyUser("092013");
		 entity.setTransportType(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C1_C20001_ZH);
		 entityList.add(entity);
		 int result=externalPriceSchemeService.activateExternalPriceSchemeById(entityList);
		 Assert.assertEquals(1, result);
	 }
	/**
	 * @author 092020-lipengfei
	 * @description 立即激活偏线外发价格方案
	 * @param entity
	 * @return Integer
	 */
	 @Test
	public void immediatelyActivateSchemeByIdTest(){
		 ExternalPriceSchemeEntity entity=new ExternalPriceSchemeEntity();
		 entity.setId("c953dcdf-f458-4134-a2ff-9ee9dca7f637");
		 entity.setAgentDeptCode("PX1026");
		 entity.setExternalOrgCode("W02020111");
		 entity.setBeginTime(DateUtils.convert("2014-6-30 23:59:59", DateUtils.DATE_TIME_FORMAT));
		 entity.setModifyUser("092013");
		 entity.setTransportType(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C1_C20001_ZH);
		 int result=0;
		 try {
			 result =externalPriceSchemeService.immediatelyActivateSchemeById(entity);	
		} catch (ExternalPriceSchemeException e) {
			System.out.println(e.getErrorCode());
		}
		 Assert.assertEquals(1, result);
	 }
	/**
	 * @author 092020-lipengfei
	 * @description 复制偏线外发价格方案
	 * @param entity
	 * @return ExternalPriceSchemeEntity
	 */
	 @Test
	public void copyExternalPriceSchemeTest(){
		 String id="c45fa7ae-b5e6-45b4-bc2e-72d942a424e2";
		 ExternalPriceSchemeEntity copyEntity=externalPriceSchemeService.copyExternalPriceScheme(id);
		 Assert.assertNotNull(copyEntity);
	 }
	/**
	 * @author 092020-lipengfei
	 * @description 立即中止偏线外发价格方案
	 * @param id
	 * @return Integer
	 */
	 @Test
	public void immediatelySuspendSchemeByIdTest(){
		 ExternalPriceSchemeEntity entity =new ExternalPriceSchemeEntity();
		 entity.setId("dc33f62a-4072-49b5-95c1-9bf0375722ad");
		 entity.setEndTime(DateUtils.convert("2015-12-30 23:59:59", DateUtils.DATE_TIME_FORMAT));
		 int result=0;
		 try {
			 result=externalPriceSchemeService.immediatelySuspendSchemeById(entity);
		} catch (ExternalPriceSchemeException e) {
			System.out.println(e.getErrorCode());
		}
		 Assert.assertEquals(1, result);
	 }
	/**
	 * @author 092020-lipengfei
	 * @description 导入偏线外发价格方案
	 * @param workbook
	 * @return Integer
	 */
	 @Test
	public void importExternalPriceSchemeTest(){
		 try {
			InputStream ips=new FileInputStream("d://test.xlsx");
			Workbook workbook=new XSSFWorkbook(ips);
			externalPriceSchemeService.importExternalPriceScheme(workbook);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	 }
	/**
	 * @author 092020-lipengfei
	 * @description 导出偏线外发价格方案 参考EffectiveExpressPlanService
	 * @param workbook
	 * @return Integer
	 */
	 @Test
	public void exportExternalPriceSchemeByParamsTest(){
		 ExternalPriceSchemeEntity entity=new ExternalPriceSchemeEntity();
		 ExportResource exportResource=externalPriceSchemeService.exportExternalPriceSchemeByParams(entity);
		 Assert.assertNotNull(exportResource);		 
	 }
	 @Test
	 public void queryAgentOutPriceInfoTest(){
		 ExternalPriceSchemeEntity entity= externalPriceSchemeService.queryAgentOutPriceInfo("PX1026", "W02020111", "TRANS_VEHICLE", DateUtils.convert("2014-7-30 23:59:59", DateUtils.DATE_TIME_FORMAT));
		 Assert.assertNotNull(entity);		
	}
}

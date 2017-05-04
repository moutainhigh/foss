package com.deppon.foss.module.pickup.server;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.deppon.foss.module.pickup.pricing.api.server.service.IAgentDeliveryFeeSchemeService;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.AgentDeliveryFeeEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.AgentDeliveryFeeSchemeEntity;

public class AgentDeliveryFeeSchemeServiceTest {
	private IAgentDeliveryFeeSchemeService agentDeliveryFeeSchemeService;
	@Before
	public void init(){
		String[] paths = {"classpath*:com/deppon/foss/module/pickup/pricing/server/META-INF/springTest.xml"};
		ApplicationContext context = new ClassPathXmlApplicationContext(paths);
		agentDeliveryFeeSchemeService=(IAgentDeliveryFeeSchemeService) context.getBean("agentDeliveryFeeSchemeService");
	}
	@Test
	public void queryAgentDeliveryFeeSchemeByParamsTest(){
		AgentDeliveryFeeSchemeEntity entity=new AgentDeliveryFeeSchemeEntity();
		entity.setAgentDeptName("广州转梧州金皇");
//		entity.setSchemeName("测试方案一");
		List<AgentDeliveryFeeSchemeEntity> entityList=agentDeliveryFeeSchemeService.queryAgentDeliveryFeeSchemeByParams(entity, 10, 0);
		Assert.assertEquals(1, entityList.size());
		
	}
	@Test
	public void queryRecordCountTest(){
		AgentDeliveryFeeSchemeEntity entity=new AgentDeliveryFeeSchemeEntity();
		long result =agentDeliveryFeeSchemeService.queryRecordCount(entity);
		Assert.assertEquals(2, result);
	}
	@Test
	public void addAgentDeliveryFeeSchemeTest(){
		AgentDeliveryFeeSchemeEntity entity=new AgentDeliveryFeeSchemeEntity();
		entity.setAgentDeptCode("PX1026");
		entity.setAgentDeptName("广州转梧州金皇");
		entity.setCreateUser("092020");
		entity.setRemark("this is a test");
		entity.setSchemeName("测试方案一");
		entity.setTransportType(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_ROAD_FREIGHT);
		AgentDeliveryFeeEntity feeEntity1=new AgentDeliveryFeeEntity();
		feeEntity1.setChargeStandard(new  BigDecimal(20.0));
		feeEntity1.setLowestPrice(20);
		feeEntity1.setPricingManner(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT);
		feeEntity1.setStartPoint(new  BigDecimal(0.0));
		feeEntity1.setTerminalPoint(new  BigDecimal(1.0));
		AgentDeliveryFeeEntity feeEntity2=new AgentDeliveryFeeEntity();
		feeEntity2.setChargeStandard(new  BigDecimal(15.0));
		feeEntity2.setLowestPrice(20);
		feeEntity2.setPricingManner(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME);
		feeEntity2.setStartPoint(new  BigDecimal(0.0));
		feeEntity2.setTerminalPoint(new  BigDecimal(2.0));
		List<AgentDeliveryFeeEntity> feeEntityList=new ArrayList<AgentDeliveryFeeEntity>();
		feeEntityList.add(feeEntity1);
		feeEntityList.add(feeEntity2);
		entity.setFeeEntityList(feeEntityList);
		int result =agentDeliveryFeeSchemeService.addAgentDeliveryFeeScheme(entity);
		Assert.assertEquals(1, result);
		
	}
	@Test
	public void deleteAgentDeliveryFeeSchemeByIdTest(){
		List<String> idList=new ArrayList<String>();
		idList.add("2cf17c45-917f-40d0-ba94-468246e7c9af");
		int result = agentDeliveryFeeSchemeService.deleteAgentDeliveryFeeSchemeById(idList);
		Assert.assertEquals(0, result);
	}
	@Test
	public void updateAgentDeliveryFeeSchemeTest(){
		AgentDeliveryFeeSchemeEntity entity=new AgentDeliveryFeeSchemeEntity();
		entity.setId("2cf17c45-917f-40d0-ba94-468246e7c9af");
		entity.setAgentDeptCode("PX1026");
		entity.setAgentDeptName("广州转梧州金皇");
		entity.setCreateUser("092013");
		entity.setRemark("test for update");
		entity.setSchemeName("测试方案一");
		entity.setTransportType(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_ROAD_FREIGHT);
		AgentDeliveryFeeEntity feeEntity1=new AgentDeliveryFeeEntity();
		feeEntity1.setChargeStandard(new  BigDecimal(21.0));
		feeEntity1.setLowestPrice(21);
		feeEntity1.setPricingManner(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT);
		feeEntity1.setStartPoint(new  BigDecimal(0.1));
		feeEntity1.setTerminalPoint(new  BigDecimal(1.1));
		AgentDeliveryFeeEntity feeEntity2=new AgentDeliveryFeeEntity();
		feeEntity2.setChargeStandard(new  BigDecimal(15.1));
		feeEntity2.setLowestPrice(21);
		feeEntity2.setPricingManner(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME);
		feeEntity2.setStartPoint(new  BigDecimal(0.1));
		feeEntity2.setTerminalPoint(new  BigDecimal(2.1));
		List<AgentDeliveryFeeEntity> feeEntityList=new ArrayList<AgentDeliveryFeeEntity>();
		feeEntityList.add(feeEntity1);
		feeEntityList.add(feeEntity2);
		entity.setFeeEntityList(feeEntityList);
		int result =agentDeliveryFeeSchemeService.updateAgentDeliveryFeeScheme(entity);
		Assert.assertEquals(0, result);
	}
	@Test
	public void queryAgentDeliveryFeeSchemeByIdTest(){
		AgentDeliveryFeeSchemeEntity entity=agentDeliveryFeeSchemeService.queryAgentDeliveryFeeSchemeById("1d026cb6-9591-4b57-b716-6bea7ebb1e20");
		Assert.assertNotNull(entity);
	}
	@Test
	public void queryAgentDeliveryChargeTest(){
		AgentDeliveryFeeSchemeEntity entity=agentDeliveryFeeSchemeService.queryAgentDeliveryCharge("LRF", "PX1026", null, new BigDecimal("1.5"));
		Assert.assertNotNull(entity);
		
	}
}

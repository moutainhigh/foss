package com.deppon.foss.module.pickup.pricing.server.dao.impl;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.deppon.foss.module.pickup.pricing.api.server.service.IPublishPriceService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PublishPriceEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PublicPriceDto;

/**
 * 公布价查询、网点价格查询单元测试用例
 * 
 * @author 219413-Luomengxiang
 * 
 * @category 2015.1.8
 *
 */
public class PublishPriceTest {

	private static IPublishPriceService publishPriceService;
	
	@Before
	public void init(){
		String[] config={"classpath:com/deppon/foss/module/pickup/pricing/server/META-INF/Pop-spring-test.xml"};
		ApplicationContext ac=new ClassPathXmlApplicationContext(config);
		publishPriceService=
				(IPublishPriceService) ac.getBean("publishPriceService");
	}
	
	/**
	 * 测试公布价查询
	 * 
	 * @author 219413-Luomengxiang
	 * 
	 * @category 2015.1.8
	 */
	@Test
	public void testQueryPublishPrice(){
		List<PublicPriceDto> publicPriceDtoList=
				publishPriceService.queryPublishPriceDetailByConditionWithFlight(
						"511500","100000","510000","510100","510124",null,null,null);
		try{
		     for(PublicPriceDto dto : publicPriceDtoList){
			    System.err.println(dto.getPopPublicPriceDtoList());
		  }
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 测试网点价格查询
	 * 
	 * @author 219413-Luomengxiang
	 * 
	 * @category 2015.1.8
	 */
	@Test
	public void testQueryWebPrice(){
		String startDepCode="W012031613";
		String arvReginCode="W012031310";
		try{
			List<PublishPriceEntity> publishPriceEntities=
		     publishPriceService.queryPublishPriceDetailForSalesAndPx(
		    		 startDepCode,arvReginCode);
		    for(PublishPriceEntity entity : publishPriceEntities){
			  System.out.println(entity.getPopPublicPriceDtoList());
		   }
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}

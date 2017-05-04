package com.deppon.foss.module.pickup.pricing.server.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.deppon.foss.module.pickup.pricing.api.server.dao.IPopPriceDetailSectionDao;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPricePlanService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PricePlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.pop.PopPriceDetailSectionEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PricePlanDetailDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryPricePlanDetailBean;
import com.deppon.foss.util.DateUtils;

public class PriceDetailSectionDaoTest {
	  private static IPricePlanService pricePlanService;
	  private static IPopPriceDetailSectionDao popPriceDetailSectionDao;
	  @Before
	   public void init(){
		   String[] config={"classpath:com/deppon/foss/module/pickup/pricing/server/META-INF/Pop-spring-test.xml"};
		   ApplicationContext ac=new ClassPathXmlApplicationContext(config);
		   pricePlanService= (IPricePlanService) ac.getBean("pricePlanService");
		   ApplicationContext ac2=new ClassPathXmlApplicationContext(config);
		   popPriceDetailSectionDao= (IPopPriceDetailSectionDao) ac2.getBean("priceDetailSectionDao");
	  }
	  /**
	   * 新增计价方案
	   * @author Pop-Team-Luomengxiang
	   * @date 2014.11.4
	   */
	@Test
	  public void testAddPricePlan(){
		   PricePlanEntity entity=new PricePlanEntity();
		   entity.setBeginTime(DateUtils.convert("2014-11-08"));
		   entity.setDescription("555");
		   entity.setName("pop321");
		   entity.setPriceRegionCode("guangzhoutianhe");
		   entity.setPriceRegionId("DB53DF63B1494A5BE0433565A8C090AF396");
		   entity.setPriceRegionName("广州天河");
		   pricePlanService.addPricePlan(entity);
	  }
	   /**
	    * 新增计价方式分段明细测试
	    * @author POP-Team-LuoMengxiang
	    */
       @Test
       public void testAddPricePlanDetail(){
			  List<PricePlanDetailDto> dtoList=new ArrayList<PricePlanDetailDto>();
			   // 第一段
			   PricePlanDetailDto dto=new PricePlanDetailDto();
			   dto.setArrvRegionId("DB53DF63B1494A5BE0433565A8C090AF100");
    	       dto.setCentralizePickup("Y");
    	       dto.setCreateTime(null);
    	       dto.setArrvRegionName("香港");
    	       dto.setHeavyPrice(new BigDecimal(180) );
    	       dto.setLightPrice(new BigDecimal(120));
    	       dto.setMinimumOneTicket(100L);
    	       dto.setPricePlanId("9091fd65-3aa0-489b-acab-f5b3cae9f470");
    	       dto.setProductItemCode("1361860098891");
    	       dto.setProductItemId("5a42ce92-7039-40c0-913d-662fe8470973");
    	       dto.setProductItemName("普车-通用");
    	       dto.setRemark("~~~~~~");
    	       dto.setSectionID("1");
    	       dtoList.add(dto);
    	       //第二段
    	       PricePlanDetailDto dto2=new PricePlanDetailDto();
    	       dto2.setArrvRegionId("DB53DF63B1494A5BE0433565A8C090AF100");
    	       dto2.setCentralizePickup("Y");
    	       dto2.setCreateTime(null);
    	       dto2.setArrvRegionName("香港");
    	       dto2.setHeavyPrice(new BigDecimal(180) );
    	       dto2.setHeavyCriticalVal(new BigDecimal(50));
    	       dto2.setLightPrice(new BigDecimal(120));
    	       dto2.setLightCriticalVal(new BigDecimal(30));
    	       dto2.setPricePlanId("9091fd65-3aa0-489b-acab-f5b3cae9f470");
    	       dto2.setProductItemCode("1361860098891");
    	       dto2.setProductItemId("5a42ce92-7039-40c0-913d-662fe8470973");
    	       dto2.setProductItemName("普车-通用");
    	       dto2.setRemark("~~~~~~~");
    	       dto2.setSectionID("2");
    	       dtoList.add(dto2);
    	       //第三段
    	       PricePlanDetailDto dto3=new PricePlanDetailDto();
    	       dto3.setArrvRegionId("DB53DF63B1494A5BE0433565A8C090AF100");
    	       dto3.setCentralizePickup("Y");
    	       dto3.setCreateTime(null);
    	       dto3.setArrvRegionName("香港");
    	       dto3.setHeavyPrice(new BigDecimal(180) );
    	       dto3.setHeavyCriticalVal(new BigDecimal(70));
    	       dto3.setLightPrice(new BigDecimal(120));
    	       dto3.setLightCriticalVal(new BigDecimal(50));
    	       dto3.setPricePlanId("9091fd65-3aa0-489b-acab-f5b3cae9f470");
    	       dto3.setProductItemCode("1361860098891");
    	       dto3.setProductItemId("5a42ce92-7039-40c0-913d-662fe8470973");
    	       dto3.setProductItemName("普车-通用");
    	       dto3.setRemark("~~~~~~");
    	       dto3.setSectionID("3");
    	       dtoList.add(dto3);
    	       //第四段
    	       PricePlanDetailDto dto4=new PricePlanDetailDto();
    	       dto4.setArrvRegionId("DB53DF63B1494A5BE0433565A8C090AF100");
    	       dto4.setCentralizePickup("Y");
    	       dto4.setCreateTime(null);
    	       dto4.setArrvRegionName("香港");
    	       dto4.setHeavyPrice(new BigDecimal(180) );
    	       dto4.setHeavyCriticalVal(new BigDecimal(90));
    	       dto4.setLightPrice(new BigDecimal(120));
    	       dto4.setLightCriticalVal(new BigDecimal(70));
    	       dto4.setPricePlanId("9091fd65-3aa0-489b-acab-f5b3cae9f470");
    	       dto4.setProductItemCode("1361860098891");
    	       dto4.setProductItemId("5a42ce92-7039-40c0-913d-662fe8470973");
    	       dto4.setProductItemName("普车-通用");
    	       dto4.setRemark("~~~~~~");
    	       dto4.setSectionID("4");
    	       dtoList.add(dto4);
    	       //第五段
    	       PricePlanDetailDto dto5=new PricePlanDetailDto();
    	       dto5.setArrvRegionId("DB53DF63B1494A5BE0433565A8C090AF100");
    	       dto5.setCentralizePickup("Y");
    	       dto5.setCreateTime(null);
    	       dto5.setArrvRegionName("香港");
    	       dto5.setHeavyPrice(new BigDecimal(180) );
    	       dto5.setHeavyCriticalVal(new BigDecimal(110));
    	       dto5.setLightPrice(new BigDecimal(120));
    	       dto5.setLightCriticalVal(new BigDecimal(90));
    	       dto5.setPricePlanId("9091fd65-3aa0-489b-acab-f5b3cae9f470");
    	       dto5.setProductItemCode("1361860098891");
    	       dto5.setProductItemId("5a42ce92-7039-40c0-913d-662fe8470973");
    	       dto5.setProductItemName("普车-通用");
    	       dto5.setRemark("~~~~~~");
    	       dto5.setSectionID("5");
    	       dtoList.add(dto5);
    	       //第六段
    	       PricePlanDetailDto dto6=new PricePlanDetailDto();
    	       dto6.setArrvRegionId("DB53DF63B1494A5BE0433565A8C090AF100");
    	       dto6.setCentralizePickup("Y");
    	       dto6.setCreateTime(null);
    	       dto6.setArrvRegionName("香港");
    	       dto6.setHeavyPrice(new BigDecimal(180) );
    	       dto6.setHeavyCriticalVal(new BigDecimal(130));
    	       dto6.setLightPrice(new BigDecimal(120));
    	       dto6.setLightCriticalVal(new BigDecimal(110));
    	       dto6.setPricePlanId("9091fd65-3aa0-489b-acab-f5b3cae9f470");
    	       dto6.setProductItemCode("1361860098891");
    	       dto6.setProductItemId("5a42ce92-7039-40c0-913d-662fe8470973");
    	       dto6.setProductItemName("普车-通用");
    	       dto6.setRemark("~~~~~~~");
    	       dto6.setSectionID("6");
    	       dtoList.add(dto6);
    	       pricePlanService.addPricePlanDetail(dtoList);
       }
       /**
        * 根据规则id查询明细分段信息测试
        */
       @Test
       public void testSelectByValuationId(){
//    	   PopPriceDetailSectionEntity entity=new PopPriceDetailSectionEntity();
//    	   entity.setValuationId("9debe8fb-fd89-4deb-bf9f-ebee47c0b990");
    	   String valuationId="91257967-34c1-46ed-88bd-9835072f7993";
    	   List<PopPriceDetailSectionEntity> entitys= popPriceDetailSectionDao.selectByValuationId(valuationId);
    	   for(PopPriceDetailSectionEntity ent : entitys){
              	System.out.println(ent.getSectionID()+","+ent.getCriticalValue()+","+
                    ent.getCaculateType()+","+ent.getFee()+","+ent.getId());
              }
       }
     
       /**
        * 查询明细
        */
       @Test
       public void testQueryPricePlanDetailInfo(){
    	   QueryPricePlanDetailBean bean=new QueryPricePlanDetailBean();
    	   //bean.setValuationId("91257967-34c1-46ed-88bd-9835072f7993");
    	   bean.setPricePlanId("8a8415f7-1f03-488a-8684-2c40b3da7bbb");
    	   List<PricePlanDetailDto> beanList=pricePlanService.queryPricePlanDetailInfo(bean, 0, 10);
    	   for(PricePlanDetailDto dto : beanList){
    		     System.out.println(dto.getCaculateType()+","+dto.getSectionID()+","+dto.getCentralizePickup());
    	   }
       }
       /**
        * 删除计价方式明细+分段
        */
   @Test
     public void testDeletePriceDetailPlan(){
	      String valuationId="4a4a3405-35ae-4140-a660-2621acb8b425";
	      List<String> valuationIdList=new ArrayList<String>();
	      valuationIdList.add(valuationId);
	      pricePlanService.deletePriceDetailPlan(valuationIdList);
   }
   /**
    * 删除方案
    */
   @Test
   public void testPricePlan(){
	     String pricePlanId="8a8415f7-1f03-488a-8684-2c40b3da7bbb";
	     List<String> pricePlanIdList=new ArrayList<String>();
	     pricePlanIdList.add(pricePlanId);
	     pricePlanService.deletePricePlan(pricePlanIdList);
   }
}


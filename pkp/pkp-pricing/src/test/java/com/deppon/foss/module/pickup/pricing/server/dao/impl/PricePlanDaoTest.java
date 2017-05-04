package com.deppon.foss.module.pickup.pricing.server.dao.impl;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.deppon.foss.module.pickup.pricing.api.server.dao.IPricePlanDao;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PricePlanEntity;
import com.deppon.foss.module.pickup.pricing.server.util.PricePlanUtil;
import com.deppon.foss.util.DateUtils;

public class PricePlanDaoTest {
	@Test
      public void testSelect(){
    	                PricePlanEntity pricePlanEntity=new PricePlanEntity(); 
    	                pricePlanEntity.setBeginTime(DateUtils.convert("2014-11-02 ",DateUtils.DATE_FORMAT));
    	                pricePlanEntity.setEndTime(DateUtils.convert("2014-11-05",DateUtils.DATE_FORMAT));
    	                IPricePlanDao pricePlanDao=PricePlanUtil.pricePlanDao;
			            List<PricePlanEntity> result=pricePlanDao.queryPricePlanBatchInfo(pricePlanEntity);
			           //Assert.assertEquals(11,result.size());
			            for(PricePlanEntity entity : result){
			            	System.out.println(entity.getName()+"           "+entity.getBeginTime());
			            }
    	  }
     }


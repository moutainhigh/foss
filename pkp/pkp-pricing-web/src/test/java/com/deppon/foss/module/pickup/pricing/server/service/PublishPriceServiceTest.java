package com.deppon.foss.module.pickup.pricing.server.service;
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */ 
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.aspectj.weaver.ast.And;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.deppon.foss.module.base.baseinfo.api.shared.dto.SaleDepartmentInfoDto;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IEffectivePlanDetailDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IPublishPriceDao;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPublishPriceService;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.EffectivePlanDetailEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PublishPriceEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PublicPriceDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PublishPriceDto;
import com.deppon.foss.module.pickup.pricing.server.dao.impl.EffectivePlanDetailDao;
import com.deppon.foss.module.pickup.pricing.server.dao.impl.PublishPriceDao;
import com.deppon.foss.module.pickup.pricing.server.service.impl.PublishPriceService;
import com.deppon.foss.module.pickup.pricing.server.util.SpringTestHelper;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;


/**
 * 月台测试类
 * @author foss-zhujunyong
 * @date Oct 19, 2012 10:58:19 AM
 * @version 1.0
 * @param <PriceDiscountServiceTest>
 */
public class PublishPriceServiceTest {
	
	Logger log = Logger.getLogger(PublishPriceServiceTest.class);

    private IPublishPriceService publishPriceService;
    private IPublishPriceDao publishPriceDao;
    private IEffectivePlanDetailDao effectivePlanDetailDao;
    @Before
    public void setup() {
    	publishPriceService = SpringTestHelper.get().getBeanByClass(PublishPriceService.class);
    	publishPriceDao = SpringTestHelper.get().getBeanByClass(PublishPriceDao.class);
    	effectivePlanDetailDao = SpringTestHelper.get().getBeanByClass(EffectivePlanDetailDao.class);
    }
    
    
    @Test
    @Ignore
    public void testSelectPriceRegion() {
    	
    	publishPriceService.queryPublishPriceDetail("W011303010608", "W011305080106", new Date());
    	PublishPriceDto publishPriceDto = new PublishPriceDto(); 
		publishPriceDto.setDeptRegionId("1111");
		publishPriceDto.setArrvRegionId("2222");
		publishPriceDto.setReceiveDate(new Date());
		publishPriceDto.setValuationType(PricingConstants.VALUATION_TYPE_PRICING);
		publishPriceDto.setActive(FossConstants.ACTIVE);
		// 根据最新时效方案版本查询相关时效方案详细信息
		List<PublishPriceDto> publishPriceDtos = publishPriceDao.queryPublishPriceValuationByCalculaCondition(publishPriceDto);
		log.debug("1、共"+publishPriceDtos.size()+"条");
		for (int i = 0; i < publishPriceDtos.size(); i++) {
			PublishPriceDto priceDto = (PublishPriceDto)publishPriceDtos.get(i);
			log.debug("计费规则ID:"+priceDto.getPricingValuationId());
			log.debug("产品CODE:"+priceDto.getProductCode());
			log.debug("产品NAME:"+priceDto.getProductCode());
			log.debug("货物类型CODE:"+priceDto.getGoodsTypeCode());
			log.debug("货物类型NAME:"+priceDto.getGoodsTypeName());
			log.debug("始发价格区域ID:"+priceDto.getDeptRegionId());
			log.debug("始发价格区域CODE:"+priceDto.getDeptRegionCode());
			log.debug("目的价格区域ID:"+priceDto.getArrvRegionId());
			log.debug("目的价格区域CODE:"+priceDto.getArrvRegionCode());
			log.debug("费用类别:"+priceDto.getCaculateType());
			log.debug("费用:"+priceDto.getFeeRate());
			log.debug("最低一票:"+priceDto.getMinFee());
			log.debug("---------------------------------------");
		}
    }
    
    @Test
    @Ignore
    public void testSelectEffectRegion() {
    	List<EffectivePlanDetailEntity> list = effectivePlanDetailDao.queryEffectivePlanDetailListByCondition("1111","2222",null, new Date());
    	log.debug("1、共"+list.size()+"条");
    	for (int i = 0; i < list.size(); i++) {
    		EffectivePlanDetailEntity detailEntity = list.get(i);
    		log.debug("计费规则ID:"+detailEntity.getEffectivePlanId());
			log.debug("产品CODE:"+detailEntity.getProductCode());
			log.debug("产品NAME:"+detailEntity.getProductCode());
			log.debug("始发价格区域ID:"+detailEntity.getDeptRegionId());
			log.debug("始发价格区域CODE:"+detailEntity.getDeptRegionCode());
			log.debug("目的价格区域ID:"+detailEntity.getArrvRegionId());
			log.debug("目的价格区域CODE:"+detailEntity.getArrvRegionCode());
			log.debug("计费规则ID:"+detailEntity.getLongOrShort());
			log.debug("承诺最短时间:"+detailEntity.getMinTime());
			log.debug("承诺最短时间单位:"+detailEntity.getMinTimeUnit());
			log.debug("承诺最长时间:"+detailEntity.getMaxTime());
			log.debug("承诺最长时间单位:"+detailEntity.getMaxTimeUnit());
			log.debug("承诺到达营业部时间:"+detailEntity.getArriveTime());
			log.debug("承诺派送时间:"+detailEntity.getDeliveryTime());
			log.debug("-----------------------------------------------------");
		}
    }
    
    @Test
    public void testSelect() {
    	log.debug("queryPublishPriceDetailByCity 2 start>>"+new Date());
    	List<PublicPriceDto> list = publishPriceService.queryPublishPriceDetailByCity("440100","310100", new Date());
    	log.debug("queryPublishPriceDetailByCity 2 end>>"+new Date());
    	log.debug("1、共"+list.size()+"条");
    	for (int i = 0; i < list.size(); i++) {
    		PublicPriceDto detailEntity = list.get(i);
    		if(detailEntity.getProductCode().equals("FLF") && detailEntity.getDeptRegionName().equals("广州") && detailEntity.getArrvRegionName().equals("上海")){
            		log.debug("计费规则ID:"+detailEntity.getId());
        		log.debug("产品CODE:"+detailEntity.getProductCode());
        		log.debug("产品NAME:"+detailEntity.getProductName());
        		log.debug("货物CODE:"+detailEntity.getGoodsTypeCode());
        		log.debug("货物NAME:"+detailEntity.getGoodsTypeName());
        		log.debug("始发价格区域ID:"+detailEntity.getDeptRegionId());
        		log.debug("始发价格区域CODE:"+detailEntity.getDeptRegionCode());
        		log.debug("目的价格区域ID:"+detailEntity.getArrvRegionId());
        		log.debug("目的价格区域CODE:"+detailEntity.getArrvRegionCode());
        		log.debug("长短途:"+detailEntity.getLongOrShort());
        		log.debug("承诺最短时间:"+detailEntity.getMinTime());
        		log.debug("承诺最短时间单位:"+detailEntity.getMinTimeUnit());
        		log.debug("承诺最长时间:"+detailEntity.getMaxTime());
        		log.debug("承诺最长时间单位:"+detailEntity.getMaxTimeUnit());
        		log.debug("承诺到达营业部时间:"+detailEntity.getArriveTime());
        		log.debug("承诺派送时间:"+detailEntity.getDeliveryTime());
        		log.debug("重货上:"+detailEntity.getHeavyFeeRatePickUpYes());
        		log.debug("重货非:"+detailEntity.getHeavyFeeRatePickUpNo());
        		log.debug("轻货上:"+detailEntity.getLightFeeRatePickUpYes());
        		log.debug("轻货非:"+detailEntity.getLightFeeRatePickUpNo());
        		log.debug("最低上:"+detailEntity.getMinFeePickUpYes());
        		log.debug("最低非:"+detailEntity.getMinFeePickUpNo());
        		log.debug("出发区域名称:"+detailEntity.getDeptRegionName());
        		log.debug("到达区域名称:"+detailEntity.getArrvRegionName());
        		log.debug("-----------------------------------------------------");
    			}
		}
    }
    
    @Test
    @Ignore
    public void testSelect1() {
    	List<PublishPriceEntity> list = publishPriceService.queryPublishPriceDetail("W011303010608", "W011305080106", new Date());
    	log.debug("1、共"+list.size()+"条");
    	for (int i = 0; i < list.size(); i++) {
    		PublishPriceEntity detailEntity = list.get(i);
    		log.debug("计费规则ID:"+detailEntity.getId());
			log.debug("产品CODE:"+detailEntity.getProductItemCode());
			log.debug("产品NAME:"+detailEntity.getProductItemName());
			log.debug("货物CODE:"+detailEntity.getGoodsTypeCode());
			log.debug("货物NAME:"+detailEntity.getGoodsTypeName());
			log.debug("始发价格区域ID:"+detailEntity.getStartDeptId());
			log.debug("始发价格区域CODE:"+detailEntity.getStartDeptCode());
			log.debug("目的价格区域ID:"+detailEntity.getArrvRegionId());
			log.debug("目的价格区域CODE:"+detailEntity.getArrvRegionCode());
			log.debug("长短途:"+detailEntity.getLongOrShort());
			log.debug("承诺最短时间:"+detailEntity.getMinEffectiveValue());
			log.debug("承诺最短时间单位:"+detailEntity.getEffectiveUnit());
			log.debug("承诺最长时间:"+detailEntity.getMaxEffectiveValue());
			log.debug("承诺最长时间单位:"+detailEntity.getMaxEffectiveValue());
			log.debug("承诺到达营业部时间:"+detailEntity.getArriveTime());
			log.debug("承诺派送时间:"+detailEntity.getPickupTime());
			log.debug("-----------------------------------------------------");
		}
    }

    @Test
    @Ignore
    public void testSelect2() {
    	List<PublicPriceDto> list = publishPriceService.queryPublishPriceDetailByCity("440100", "420100", new Date());
    	log.debug("1、共"+list.size()+"条");
    	for (int i = 0; i < list.size(); i++) {
    		PublicPriceDto detailEntity = list.get(i);
    		log.debug("计费规则ID:"+detailEntity.getArriveTime());
			log.debug("产品CODE:"+detailEntity.getArrvRegionCode());
			log.debug("产品NAME:"+detailEntity.getDeptRegionCode());
			log.debug("始发价格区域ID:"+detailEntity.getDeptRegionId());
			log.debug("始发价格区域CODE:"+detailEntity.getDeptRegionCode());
			log.debug("目的价格区域ID:"+detailEntity.getArrvRegionId());
			log.debug("目的价格区域CODE:"+detailEntity.getArrvRegionCode());
			log.debug("计费规则ID:"+detailEntity.getLongOrShort());
			log.debug("承诺最短时间:"+detailEntity.getMinTime());
			log.debug("承诺最短时间单位:"+detailEntity.getMinTimeUnit());
			log.debug("承诺最长时间:"+detailEntity.getMaxTime());
			log.debug("承诺最长时间单位:"+detailEntity.getMaxTimeUnit());
			log.debug("承诺到达营业部时间:"+detailEntity.getArriveTime());
			log.debug("承诺派送时间:"+detailEntity.getDeliveryTime());
			log.debug("重货上:"+detailEntity.getHeavyFeeRatePickUpYes());
			log.debug("重货非:"+detailEntity.getHeavyFeeRatePickUpNo());
			log.debug("轻货上:"+detailEntity.getLightFeeRatePickUpYes());
			log.debug("轻货非:"+detailEntity.getLightFeeRatePickUpNo());
			log.debug("最低上:"+detailEntity.getMinFeePickUpYes());
			log.debug("最低非:"+detailEntity.getMinFeePickUpNo());
			log.debug("产品:"+detailEntity.getProductCode());
			log.debug("产品:"+detailEntity.getProductName());
			log.debug("-----------------------------------------------------");
		}
    }
    @Test
    @Ignore
    public void testSelect3() {
    	List<String> codes = new ArrayList<String>();
    	codes.add("PXSH001");
    	codes.add("DL001001");
    	codes.add("WHJC001");
    	codes.add("W011302020106");
    	codes.add("W011305080203");
    	codes.add("W01000304020910");
    	List<SaleDepartmentInfoDto> list = publishPriceService.getOuterAndDepartment(codes);
		if(CollectionUtils.isNotEmpty(list)) {
			System.out.println("共"+list.size()+"条");
			for (int i = 0; i < list.size(); i++) {
				SaleDepartmentInfoDto dto = list.get(i);
				System.out.println("deptno>>"+dto.getCode());
				System.out.println("name>>"+dto.getName());
				System.out.println("simplename>>>>"+dto.getSimpleName());
				System.out.println("address>>"+dto.getAddress());
				System.out.println("telephone>>"+dto.getTelephone());
				System.out.println("orgtype>>"+dto.getOrgType());
				System.out.println("tihuo>>"+dto.getPickupSelf());
				System.out.println("songhuo>>"+dto.getDelivery());
			}
		}
    }
    
    @Test
    @Ignore
    public void testSelect5() {
    	List<PublicPriceDto> list = publishPriceService.
    			queryPublishPriceDetailByCondition("110100", "100000", "310000", "310100", "", "营业部",new Date());
    	log.debug("1、共"+list.size()+"条");
    	for (int i = 0; i < list.size(); i++) {
    		PublicPriceDto detailEntity = list.get(i);
    		log.debug("计费规则ID:"+detailEntity.getId());
			log.debug("产品CODE:"+detailEntity.getProductCode());
			log.debug("产品NAME:"+detailEntity.getProductName());
			log.debug("货物CODE:"+detailEntity.getGoodsTypeCode());
			log.debug("货物NAME:"+detailEntity.getGoodsTypeName());
			log.debug("始发价格区域ID:"+detailEntity.getDeptRegionId());
			log.debug("始发价格区域CODE:"+detailEntity.getDeptRegionCode());
			log.debug("目的价格区域ID:"+detailEntity.getArrvRegionId());
			log.debug("目的价格区域CODE:"+detailEntity.getArrvRegionCode());
			log.debug("长短途:"+detailEntity.getLongOrShort());
			log.debug("承诺最短时间:"+detailEntity.getMinTime());
			log.debug("承诺最短时间单位:"+detailEntity.getMinTimeUnit());
			log.debug("承诺最长时间:"+detailEntity.getMaxTime());
			log.debug("承诺最长时间单位:"+detailEntity.getMaxTimeUnit());
			log.debug("承诺到达营业部时间:"+detailEntity.getArriveTime());
			log.debug("承诺派送时间:"+detailEntity.getDeliveryTime());
			log.debug("重货上:"+detailEntity.getHeavyFeeRatePickUpYes());
			log.debug("重货非:"+detailEntity.getHeavyFeeRatePickUpNo());
			log.debug("轻货上:"+detailEntity.getLightFeeRatePickUpYes());
			log.debug("轻货非:"+detailEntity.getLightFeeRatePickUpNo());
			log.debug("最低上:"+detailEntity.getMinFeePickUpYes());
			log.debug("最低非:"+detailEntity.getMinFeePickUpNo());
			log.debug("-----------------------------------------------------");
		}
    }
}

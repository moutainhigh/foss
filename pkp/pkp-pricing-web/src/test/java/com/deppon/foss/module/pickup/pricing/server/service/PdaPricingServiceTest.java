/*******************************************************************************
 * Copyright 2013 BSE TEAM
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
 * PROJECT NAME	: bse-baseinfo-web
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/server/service/PdaPricingServiceTest.java
 * 
 * FILE NAME        	: PdaPricingServiceTest.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.deppon.foss.module.pickup.pricing.api.server.service.IBillCaculateService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IDistrictRegionService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPdaBillCaculateService;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PriceEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.DistrictRegionEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PdaQueryBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PdaQueryBillCalculateSubDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PdaResultBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PdaResultDiscountDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PriceDiscountDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ProductPriceDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateValueAddDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ValueAddDto;
import com.deppon.foss.module.pickup.pricing.server.service.impl.BillCaculateService;
import com.deppon.foss.module.pickup.pricing.server.service.impl.DistrictRegionService;
import com.deppon.foss.module.pickup.pricing.server.service.impl.PdaBillCaculateService;
import com.deppon.foss.module.pickup.pricing.server.util.SpringTestHelper;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.define.FossConstants;


/**
 * 月台测试类
 * @date Oct 19, 2012 10:58:19 AM
 * @version 1.0
 * @param <PriceDiscountServiceTest>
 */
public class PdaPricingServiceTest {

	Logger log = Logger.getLogger(PdaPricingServiceTest.class);
    private IPdaBillCaculateService pdaBillCaculateService;
    private IBillCaculateService billCaculateService;
    private IDistrictRegionService districtRegionService;
    
    @Before
    public void setup() {
    	pdaBillCaculateService = SpringTestHelper.get().getBeanByClass(PdaBillCaculateService.class);
//    	billCaculateService = SpringTestHelper.get().getBeanByClass(BillCaculateService.class);
//    	districtRegionService = SpringTestHelper.get().getBeanByClass(DistrictRegionService.class);
    }
    
    //@Test
    public void testSelectdistrictRegion() {
    	List<DistrictRegionEntity> list = districtRegionService.searchDistrictRegion();
    	for (DistrictRegionEntity districtRegionEntity : list) {
			System.out.println("districtRegionEntity>>"+districtRegionEntity.getEffectiveRegionNames());
		}
    }
    
    //@Test
    //@Ignore
    public void testSelectPdaPricing( ) {
    	PdaQueryBillCalculateDto queryBillCalculateDto = new PdaQueryBillCalculateDto();
    	queryBillCalculateDto.setOriginalOrgCode("W011302020515");
    	queryBillCalculateDto.setDestinationOrgCode("W011305080202");
    	
//    	queryBillCalculateDto.setOriginalOrgCode("W011305070302");
//    	queryBillCalculateDto.setDestinationOrgCode("W011305090304");
//    	queryBillCalculateDto.setCustomerCode("1221");
    	queryBillCalculateDto.setProductCode("FLF");
    	queryBillCalculateDto.setCurrencyCdoe(FossConstants.CURRENCY_CODE_RMB);
//    	queryBillCalculateDto.setIndustrulCode("ALL");
//    	queryBillCalculateDto.setReceiveDate(new Date());
    	queryBillCalculateDto.setVolume(new BigDecimal(300));
    	queryBillCalculateDto.setWeight(new BigDecimal(200));

    	List<PdaQueryBillCalculateSubDto> list = new ArrayList<PdaQueryBillCalculateSubDto>();
//    	PdaQueryBillCalculateSubDto subDto1 = new PdaQueryBillCalculateSubDto();
//    	subDto1.setOriginnalCost(new BigDecimal(10000));
//    	subDto1.setPriceEntityCode("BF");
//    	
//    	PdaQueryBillCalculateSubDto subDto2 = new PdaQueryBillCalculateSubDto();
//    	subDto2.setOriginnalCost(new BigDecimal(50000));
//    	subDto2.setPriceEntityCode("HK");
//    	subDto2.setSubType("R3");
//    	
//    	PdaQueryBillCalculateSubDto subDto3 = new PdaQueryBillCalculateSubDto();
//    	subDto3.setOriginnalCost(new BigDecimal(0));
//    	subDto3.setPriceEntityCode("SHSL");
//    	subDto3.setSubType("NONE");
//    	
    	PdaQueryBillCalculateSubDto subDto4 = new PdaQueryBillCalculateSubDto();
    	subDto4.setOriginnalCost(new BigDecimal(0));
    	subDto4.setPriceEntityCode("JH");
//    	
//    	PdaQueryBillCalculateSubDto subDto5 = new PdaQueryBillCalculateSubDto();
//    	subDto5.setOriginnalCost(new BigDecimal(0));
//    	subDto5.setPriceEntityCode("BZ");
//    	subDto5.setSubType("BOX");
//    	subDto5.setWoodenVolume(new BigDecimal(11.2));
//    	
//    	PdaQueryBillCalculateSubDto subDto6 = new PdaQueryBillCalculateSubDto();
//    	subDto6.setOriginnalCost(new BigDecimal(0));
//    	subDto6.setPriceEntityCode("SH");
    	
    	PdaQueryBillCalculateSubDto subDto7 = new PdaQueryBillCalculateSubDto();
    	subDto7.setOriginnalCost(new BigDecimal(0));
    	subDto7.setPriceEntityCode("FRT");
    	
//    	list.add(subDto1);
//    	list.add(subDto2);
//    	list.add(subDto3);
    	list.add(subDto4);
//    	list.add(subDto5);
//    	list.add(subDto6);
    	list.add(subDto7);
    	queryBillCalculateDto.setPriceEntities(list);
    	List<PdaResultBillCalculateDto> productPriceDtos = pdaBillCaculateService.queryPdaBillPrice(queryBillCalculateDto);
    	log.debug("productPriceDtos:"+productPriceDtos.size());
    	for (int i = 0; i < productPriceDtos.size(); i++) {
    		PdaResultBillCalculateDto discountDto = productPriceDtos.get(i);
    		log.debug("表达式:"+discountDto.getCaculateExpression());
    		log.debug("计价方式CODE:"+discountDto.getPriceEntityCode());
    		log.debug("计价方式NAME:"+discountDto.getPriceEntityName());
    		log.debug("实际FeeRATE:"+discountDto.getActualFeeRate());
    		log.debug("计算后的价格:"+discountDto.getCaculateFee());
    		log.debug("打折后的价格:"+discountDto.getDiscountFee());
    		log.debug("是否删除:"+discountDto.getCanDelete());
    		log.debug("是否修改:"+discountDto.getCanModify());
    		log.debug("最大值:"+discountDto.getMaxFee());
    		log.debug("最小值:"+discountDto.getMinFee());
    		List<PdaResultDiscountDto> resultDiscountDtos = discountDto.getDiscountPrograms();
    		if(CollectionUtils.isNotEmpty(resultDiscountDtos)) {
    			for (int j = 0; j < resultDiscountDtos.size(); j++) {
        			PdaResultDiscountDto priceDiscountDto = resultDiscountDtos.get(j);
        			log.debug("折折方案:"+priceDiscountDto.getMarketName());
        			log.debug("折扣类型:"+priceDiscountDto.getSaleChannelName());
        			log.debug("减免费:"+priceDiscountDto.getReduceFee());
        			log.debug("折扣率:"+priceDiscountDto.getDiscountRate());
    			}
    		}
    		log.debug("-------------------------------------------------");
		}
    }
    
    
    //@Test
    //@Ignore
    public void testSelectPdaPricing2( ) {
    	PdaQueryBillCalculateDto queryBillCalculateDto = new PdaQueryBillCalculateDto();
    	queryBillCalculateDto.setOriginalOrgCode("W011302020515");
    	queryBillCalculateDto.setDestinationOrgCode("W011305080202");
    	queryBillCalculateDto.setCustomerCode("111111111");
    	queryBillCalculateDto.setProductCode("FLF");
    	queryBillCalculateDto.setCurrencyCdoe(FossConstants.CURRENCY_CODE_RMB);
    	queryBillCalculateDto.setReceiveDate(new Date());
    	queryBillCalculateDto.setIndustrulCode("ALL");
    	queryBillCalculateDto.setVolume(new BigDecimal(0.57));
    	queryBillCalculateDto.setWeight(new BigDecimal(89));

    	List<PdaQueryBillCalculateSubDto> list = new ArrayList<PdaQueryBillCalculateSubDto>();
    	PdaQueryBillCalculateSubDto subDto1 = new PdaQueryBillCalculateSubDto();
    	subDto1.setOriginnalCost(new BigDecimal(20000));
    	subDto1.setPriceEntityCode("BF");
    	
    	PdaQueryBillCalculateSubDto subDto2 = new PdaQueryBillCalculateSubDto();
    	subDto2.setOriginnalCost(new BigDecimal(1000));
    	subDto2.setPriceEntityCode("HK");
    	subDto2.setSubType("RA");
    	
    	PdaQueryBillCalculateSubDto subDto3 = new PdaQueryBillCalculateSubDto();
    	subDto3.setOriginnalCost(new BigDecimal(0));
    	subDto3.setPriceEntityCode("QS");
    	subDto3.setSubType("ORIGINAL");
    	
    	PdaQueryBillCalculateSubDto subDto4 = new PdaQueryBillCalculateSubDto();
    	subDto4.setOriginnalCost(new BigDecimal(0));
    	subDto4.setPriceEntityCode("JH");
    	
    	PdaQueryBillCalculateSubDto subDto5 = new PdaQueryBillCalculateSubDto();
    	subDto5.setOriginnalCost(new BigDecimal(0));
    	subDto5.setPriceEntityCode("BZ");
    	subDto5.setWoodenVolume(new BigDecimal(0));
 	
    	PdaQueryBillCalculateSubDto subDto6 = new PdaQueryBillCalculateSubDto();
    	subDto6.setOriginnalCost(new BigDecimal(0));
    	subDto6.setPriceEntityCode("SHSL");
    	
    	PdaQueryBillCalculateSubDto subDto7 = new PdaQueryBillCalculateSubDto();
    	subDto7.setOriginnalCost(new BigDecimal(0));
    	subDto7.setPriceEntityCode("FRT");
    	
    	list.add(subDto1);
    	list.add(subDto2);
    	list.add(subDto3);
    	list.add(subDto4);
    	list.add(subDto5);
    	list.add(subDto6);
    	list.add(subDto7);
    	queryBillCalculateDto.setPriceEntities(list);
    	List<PdaResultBillCalculateDto> productPriceDtos = pdaBillCaculateService.queryPdaBillPrice(queryBillCalculateDto);
    	log.debug("productPriceDtos:"+productPriceDtos.size());
    	for (int i = 0; i < productPriceDtos.size(); i++) {
    		PdaResultBillCalculateDto discountDto = productPriceDtos.get(i);
    		log.debug("表达式:"+discountDto.getCaculateExpression());
    		log.debug("计价方式CODE:"+discountDto.getPriceEntityCode());
    		log.debug("计价方式NAME:"+discountDto.getPriceEntityName());
    		log.debug("实际FeeRATE:"+discountDto.getActualFeeRate());
    		log.debug("计算后的价格:"+discountDto.getCaculateFee());
    		log.debug("打折后的价格:"+discountDto.getDiscountFee());
    		log.debug("是否删除:"+discountDto.getCanDelete());
    		log.debug("是否修改:"+discountDto.getCanModify());
    		log.debug("最大值:"+discountDto.getMaxFee());
    		log.debug("最小值:"+discountDto.getMinFee());
    		List<PdaResultDiscountDto> resultDiscountDtos = discountDto.getDiscountPrograms();
    		if(CollectionUtils.isNotEmpty(resultDiscountDtos)) {
    			for (int j = 0; j < resultDiscountDtos.size(); j++) {
        			PdaResultDiscountDto priceDiscountDto = resultDiscountDtos.get(j);
        			log.debug("折折方案:"+priceDiscountDto.getMarketName());
        			log.debug("折扣类型:"+priceDiscountDto.getSaleChannelName());
        			log.debug("减免费:"+priceDiscountDto.getReduceFee());
        			log.debug("折扣率:"+priceDiscountDto.getDiscountRate());
    			}
    		}
    		log.debug("-------------------------------------------------");
		}
    }
    
    
    //@Test
    //@Ignore
    public void testSelectPdaPricing3( ) {
    	PdaQueryBillCalculateDto queryBillCalculateDto = new PdaQueryBillCalculateDto();
    	queryBillCalculateDto.setOriginalOrgCode("W011303070301");
    	queryBillCalculateDto.setDestinationOrgCode("W011303070309");
    	queryBillCalculateDto.setProductCode("FSF");
    	queryBillCalculateDto.setGoodsCode("H00001");
    	queryBillCalculateDto.setCurrencyCdoe(FossConstants.CURRENCY_CODE_RMB);
    	queryBillCalculateDto.setReceiveDate(new Date());
    	queryBillCalculateDto.setIndustrulCode("ALL");
    	queryBillCalculateDto.setVolume(new BigDecimal(2));
    	queryBillCalculateDto.setWeight(new BigDecimal(90));

    	List<PdaQueryBillCalculateSubDto> list = new ArrayList<PdaQueryBillCalculateSubDto>();
    	
    	PdaQueryBillCalculateSubDto subDto7 = new PdaQueryBillCalculateSubDto();
    	subDto7.setOriginnalCost(new BigDecimal(0));
    	subDto7.setPriceEntityCode("FRT");
    	
    	list.add(subDto7);
    	queryBillCalculateDto.setPriceEntities(list);
    	List<PdaResultBillCalculateDto> productPriceDtos = pdaBillCaculateService.queryPdaBillPrice(queryBillCalculateDto);
    	log.debug("productPriceDtos:"+productPriceDtos.size());
    	for (int i = 0; i < productPriceDtos.size(); i++) {
    		PdaResultBillCalculateDto discountDto = productPriceDtos.get(i);
    		log.debug("表达式:"+discountDto.getCaculateExpression());
    		log.debug("计价方式CODE:"+discountDto.getPriceEntityCode());
    		log.debug("计价方式NAME:"+discountDto.getPriceEntityName());
    		log.debug("实际FeeRATE:"+discountDto.getActualFeeRate());
    		log.debug("计算后的价格:"+discountDto.getCaculateFee());
    		log.debug("打折后的价格:"+discountDto.getDiscountFee());
    		log.debug("是否删除:"+discountDto.getCanDelete());
    		log.debug("是否修改:"+discountDto.getCanModify());
    		log.debug("最大值:"+discountDto.getMaxFee());
    		log.debug("最小值:"+discountDto.getMinFee());
    		List<PdaResultDiscountDto> resultDiscountDtos = discountDto.getDiscountPrograms();
    		if(CollectionUtils.isNotEmpty(resultDiscountDtos)) {
    			for (int j = 0; j < resultDiscountDtos.size(); j++) {
        			PdaResultDiscountDto priceDiscountDto = resultDiscountDtos.get(j);
        			log.debug("折折方案:"+priceDiscountDto.getMarketName());
        			log.debug("折扣类型:"+priceDiscountDto.getSaleChannelName());
        			log.debug("减免费:"+priceDiscountDto.getReduceFee());
        			log.debug("折扣率:"+priceDiscountDto.getDiscountRate());
    			}
    		}
    		log.debug("-------------------------------------------------");
		}
    }
    
    //@Test
    public void testSelectTransportPricing( ) {
    	
    	PdaQueryBillCalculateDto queryBillCalculateDto = new PdaQueryBillCalculateDto();
    	queryBillCalculateDto.setOriginalOrgCode("W011302020515");
    	queryBillCalculateDto.setDestinationOrgCode("W011306040201");
    	queryBillCalculateDto.setReceiveDate(new Date());
    	queryBillCalculateDto.setIsReceiveGoods("Y");
    	queryBillCalculateDto.setProductCode("FLF");
    	queryBillCalculateDto.setWeight(new BigDecimal(300));
    	queryBillCalculateDto.setVolume(new BigDecimal(1));
    	queryBillCalculateDto.setChannelCode("ALIBABACXT");
    	queryBillCalculateDto.setCustomerCode("065545");
    	
    	List<PdaQueryBillCalculateSubDto> list1 = new ArrayList<PdaQueryBillCalculateSubDto>();
    	
    	//运费
    	PdaQueryBillCalculateSubDto subDto1 = new PdaQueryBillCalculateSubDto();
    	subDto1.setPriceEntityCode("FRT");
    	
    	//增值服务-保费
//    	PdaQueryBillCalculateSubDto subDto2 = new PdaQueryBillCalculateSubDto();
//    	subDto2.setPriceEntityCode("BF");
    	
    	list1.add(subDto1);
//    	list.add(subDto2);
    	queryBillCalculateDto.setPriceEntities(list1);
    	
    	

    	List<PdaResultBillCalculateDto> productPriceDtos = pdaBillCaculateService.queryPdaTransportBillPrice(queryBillCalculateDto);
    	for (int i = 0; i < productPriceDtos.size(); i++) {
    		PdaResultBillCalculateDto discountDto = productPriceDtos.get(i);
    		log.debug("表达式:"+discountDto.getCaculateExpression());
    		log.debug("计价方式CODE:"+discountDto.getPriceEntityCode());
    		log.debug("计价方式NAME:"+discountDto.getPriceEntityName());
    		log.debug("实际FeeRATE:"+discountDto.getActualFeeRate());
    		log.debug("计算后的价格:"+discountDto.getCaculateFee());
    		log.debug("打折后的价格:"+discountDto.getDiscountFee());
    		log.debug("是否删除:"+discountDto.getCanDelete());
    		log.debug("是否修改:"+discountDto.getCanModify());
    		log.debug("最大值:"+discountDto.getMaxFee());
    		log.debug("最小值:"+discountDto.getMinFee());
    		List<PdaResultDiscountDto> list = discountDto.getDiscountPrograms();
    		for (int j = 0; j < list.size(); j++) {
    			PdaResultDiscountDto priceDiscountDto = list.get(j);
    			log.debug("折折方案:"+priceDiscountDto.getMarketName());
    			log.debug("折扣类型:"+priceDiscountDto.getSaleChannelName());
    			log.debug("减免费:"+priceDiscountDto.getReduceFee());
    			log.debug("折扣率:"+priceDiscountDto.getDiscountRate());
			}
    		log.debug("-------------------------------------------------");
//    		log.debug("明细类型:"+discountDto.get);
//    		log.debug("是否接货:"+discountDto.getCentralizePickup());
//    		log.debug("航班班次:"+discountDto.getFlightShiftNo());
//    		log.debug("货物类型code:"+discountDto.getGoodsTypeCode());
//    		log.debug("货物类型name:"+discountDto.getGoodsTypeName());
//    		log.debug("ID:"+discountDto.getId());
//    		log.debug("长短途:"+discountDto.getLongOrShort());
//    		log.debug("计价方式CODE:"+discountDto.getPriceEntityCode());
//    		log.debug("计价方式NAME:"+discountDto.getPriceEntityName());
//    		log.debug("产品CODE:"+discountDto.getProductCode());
//    		log.debug("产品NAME:"+discountDto.getProductName());
//    		log.debug("实际FeeRATE:"+discountDto.getActualFeeRate());
//    		log.debug("计算后的价格:"+discountDto.getCaculateFee());
//    		log.debug("打折后的价格:"+discountDto.getDiscountFee());
//    		log.debug("固定费用:"+discountDto.getFee());
//    		log.debug("单价:"+discountDto.getFeeRate());
//    		log.debug("重货单价:"+discountDto.getHeavyFeeRate());
//    		log.debug("轻货单价:"+discountDto.getLightFeeRate());
//    		log.debug("最大值:"+discountDto.getMaxFee());
//    		log.debug("最小值:"+discountDto.getMinFee());
		}
    }
    
    
    //@Test
    //@Ignore
    public void testSelectValueAddPricing( ) {
    	PdaQueryBillCalculateDto queryBillCalculateDto = new PdaQueryBillCalculateDto();
    	queryBillCalculateDto.setOriginalOrgCode("W110709602");
    	queryBillCalculateDto.setDestinationOrgCode("GZSD001");
    	queryBillCalculateDto.setProductCode("FLF");
    	queryBillCalculateDto.setIsReceiveGoods("Y");
    	queryBillCalculateDto.setReceiveDate(new Date());
    	queryBillCalculateDto.setWeight(new BigDecimal(12));
    	queryBillCalculateDto.setVolume(new BigDecimal(1.34));
    	queryBillCalculateDto.setCurrencyCdoe("RMB");
    	queryBillCalculateDto.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_SH);
		List<PdaResultBillCalculateDto> productPriceDtos = pdaBillCaculateService
				.queryPdaValueAddBillPrice(queryBillCalculateDto);
		
    	for (int i = 0; i < productPriceDtos.size(); i++) {
    		PdaResultBillCalculateDto discountDto = productPriceDtos.get(i);
    		log.debug("表达式:"+discountDto.getCaculateExpression());
    		log.debug("计价方式CODE:"+discountDto.getPriceEntityCode());
    		log.debug("计价方式NAME:"+discountDto.getPriceEntityName());
    		log.debug("实际FeeRATE:"+discountDto.getActualFeeRate());
    		log.debug("计算后的价格:"+discountDto.getCaculateFee());
    		log.debug("打折后的价格:"+discountDto.getDiscountFee());
    		log.debug("是否删除:"+discountDto.getCanDelete());
    		log.debug("是否修改:"+discountDto.getCanModify());
    		log.debug("最大值:"+discountDto.getMaxFee());
    		log.debug("最小值:"+discountDto.getMinFee());
    		List<PdaResultDiscountDto> list = discountDto.getDiscountPrograms();
    		for (int j = 0; j < list.size(); j++) {
    			PdaResultDiscountDto priceDiscountDto = list.get(j);
    			log.debug("折折方案:"+priceDiscountDto.getMarketName());
    			log.debug("折扣类型:"+priceDiscountDto.getSaleChannelName());
    			log.debug("减免费:"+priceDiscountDto.getReduceFee());
    			log.debug("折扣率:"+priceDiscountDto.getDiscountRate());
			}
    		log.debug("-------------------------------------------------");
		}
    }
    //@Test
    //@Ignore
    public void testyunfei() {
    	QueryBillCacilateDto queryBillCacilateDto = new QueryBillCacilateDto();
    	queryBillCacilateDto.setOriginalOrgCode("W011302020515");
    	queryBillCacilateDto.setDestinationOrgCode("W011305080202");
    	queryBillCacilateDto.setProductCode("FLF");
    	queryBillCacilateDto.setCurrencyCdoe(FossConstants.CURRENCY_CODE_RMB);
//    	queryBillCacilateDto.setGoodsCode("H00001");
    	queryBillCacilateDto.setVolume(new BigDecimal(300));
    	queryBillCacilateDto.setWeight(new BigDecimal(200));
//    	queryBillCacilateDto.setCustomerCode("400233719");
    	
    	List<ProductPriceDto> productPriceDtos =  billCaculateService.searchProductPriceList(queryBillCacilateDto);

    	log.debug("productPriceDtos:"+productPriceDtos.size());
    	for (int i = 0; i < productPriceDtos.size(); i++) {
    		ProductPriceDto discountDto = productPriceDtos.get(i);
    		log.debug("表达式:"+discountDto.getCaculateExpression());
    		log.debug("计价方式CODE:"+discountDto.getPriceEntityCode());
    		log.debug("计价方式NAME:"+discountDto.getPriceEntityName());
    		log.debug("实际FeeRATE:"+discountDto.getActualFeeRate());
    		log.debug("计算后的价格:"+discountDto.getCaculateFee());
    		log.debug("打折后的价格:"+discountDto.getDiscountFee());
    		log.debug("计算方式:"+discountDto.getCaculateType());
    		log.debug("最大值:"+discountDto.getMaxFee());
    		log.debug("最小值:"+discountDto.getMinFee());
    		List<PriceDiscountDto> resultDiscountDtos = discountDto.getDiscountPrograms();
    		if(CollectionUtils.isNotEmpty(resultDiscountDtos)) {
    			for (int j = 0; j < resultDiscountDtos.size(); j++) {
    				PriceDiscountDto priceDiscountDto = resultDiscountDtos.get(j);
        			log.debug("折折方案:"+priceDiscountDto.getMarketName());
        			log.debug("折扣类型:"+priceDiscountDto.getSaleChannelName());
        			log.debug("减免费:"+priceDiscountDto.getReduceFee());
        			log.debug("折扣率:"+priceDiscountDto.getDiscountRate());
        			
        			log.debug("计价类型:"+priceDiscountDto.getPriceEntryCode());
        			log.debug("计费ID:"+priceDiscountDto.getChargeDetailId());
        			log.debug("折扣ID:"+priceDiscountDto.getDiscountId());
        			log.debug("折扣类型:"+priceDiscountDto.getType());
    			}
    		}
    		log.debug("-------------------------------------------------");
		}
    }
    
    //@Test
    //@Ignore
    public void testvaluadd1() {
    	QueryBillCacilateValueAddDto queryBillCacilateDto = new QueryBillCacilateValueAddDto();
    	queryBillCacilateDto.setOriginalOrgCode("W011302020515");
    	queryBillCacilateDto.setDestinationOrgCode("W011305080202");
    	queryBillCacilateDto.setProductCode("FLF");
    	queryBillCacilateDto.setCurrencyCdoe(FossConstants.CURRENCY_CODE_RMB);
    	queryBillCacilateDto.setPricingEntryCode("SH");
//    	queryBillCacilateDto.setSubType("R1");
//    	queryBillCacilateDto.setVolume(new BigDecimal(2.5));
//    	queryBillCacilateDto.setWeight(new BigDecimal(300));
//    	queryBillCacilateDto.setOriginnalCost(new BigDecimal(3000));
//    	queryBillCacilateDto.setCustomerCode("400233719");
    	
    	queryBillCacilateDto.setPricingEntryCode("SH");
//    	queryBillCacilateDto.setSubType("SHJCF");
    	queryBillCacilateDto.setVolume(new BigDecimal(400));
    	queryBillCacilateDto.setWeight(new BigDecimal(0.5));
//    	queryBillCacilateDto.setOriginnalCost(new BigDecimal(120));
//    	queryBillCacilateDto.setCustomerCode("400233719");
    	
    	List<ValueAddDto> productPriceDtos =  billCaculateService.searchValueAddPriceList(queryBillCacilateDto);

    	log.debug("productPriceDtos:"+productPriceDtos.size());
    	for (int i = 0; i < productPriceDtos.size(); i++) {
    		ValueAddDto discountDto = productPriceDtos.get(i);
    		log.debug("表达式:"+discountDto.getCaculateExpression());
    		log.debug("计价方式CODE:"+discountDto.getPriceEntityCode());
    		log.debug("计价方式NAME:"+discountDto.getPriceEntityName());
    		log.debug("实际FeeRATE:"+discountDto.getActualFeeRate());
    		log.debug("计算后的价格:"+discountDto.getCaculateFee());
    		log.debug("打折后的价格:"+discountDto.getDiscountFee());
    		log.debug("最大值:"+discountDto.getMaxFee());
    		log.debug("最小值:"+discountDto.getMinFee());
    		List<PriceDiscountDto> resultDiscountDtos = discountDto.getDiscountPrograms();
    		if(CollectionUtils.isNotEmpty(resultDiscountDtos)) {
    			for (int j = 0; j < resultDiscountDtos.size(); j++) {
    				PriceDiscountDto priceDiscountDto = resultDiscountDtos.get(j);
        			log.debug("折折方案:"+priceDiscountDto.getMarketName());
        			log.debug("折扣类型:"+priceDiscountDto.getSaleChannelName());
        			log.debug("减免费:"+priceDiscountDto.getReduceFee());
        			log.debug("折扣率:"+priceDiscountDto.getDiscountRate());
    			}
    		}
    		log.debug("-------------------------------------------------");
		}
    }
    
//    public static void main(String[] args) {
////		BigDecimal a = new BigDecimal(10);
////		BigDecimal b = new BigDecimal(6);
////		System.out.println(a.divide(b));
////		System.out.println(a.divide(b, 1, RoundingMode.HALF_DOWN));
//	}
    
    @Test
    public void testQueryBillCalculate()
	{
		PdaQueryBillCalculateDto billCalculateDto=new PdaQueryBillCalculateDto();
		billCalculateDto.setCurrencyCdoe("RMB");		
		billCalculateDto.setDestinationOrgCode("W011303070204");
		billCalculateDto.setIsReceiveGoods("Y");
		billCalculateDto.setOriginalOrgCode("W12073309");		
		List<PdaQueryBillCalculateSubDto> priceEntities=new ArrayList<PdaQueryBillCalculateSubDto>();
		PdaQueryBillCalculateSubDto subDto=new PdaQueryBillCalculateSubDto();
		subDto.setOriginnalCost(new BigDecimal("0"));
		subDto.setPriceEntityCode("BZ");
		subDto.setSubType("FRAME");
		subDto.setWoodenVolume(new BigDecimal("0.490"));
		priceEntities.add(subDto);
		PdaQueryBillCalculateSubDto subDto2=new PdaQueryBillCalculateSubDto();
		subDto2.setOriginnalCost(new BigDecimal("0"));
		subDto2.setPriceEntityCode("SH");
		subDto2.setSubType("");
		subDto2.setWoodenVolume(new BigDecimal("0"));
		priceEntities.add(subDto2);
		PdaQueryBillCalculateSubDto subDto3=new PdaQueryBillCalculateSubDto();
		subDto3.setOriginnalCost(new BigDecimal("0"));
		subDto3.setPriceEntityCode("FRT");
		subDto3.setSubType("");
		subDto3.setWoodenVolume(new BigDecimal("0"));	
		priceEntities.add(subDto3);
		PdaQueryBillCalculateSubDto subDto4=new PdaQueryBillCalculateSubDto();
		subDto4.setOriginnalCost(new BigDecimal("0"));
		subDto4.setPriceEntityCode("QT");
		subDto4.setSubType("RYFJ");
		subDto4.setWoodenVolume(new BigDecimal("0"));
		priceEntities.add(subDto4);
		PdaQueryBillCalculateSubDto subDto5=new PdaQueryBillCalculateSubDto();
		subDto5.setOriginnalCost(new BigDecimal("0"));
		subDto5.setPriceEntityCode("QT");
		subDto5.setSubType("ZHXX");
		subDto5.setWoodenVolume(new BigDecimal("0"));
		priceEntities.add(subDto5);
		billCalculateDto.setPriceEntities(priceEntities);		
		billCalculateDto.setProductCode("FLF");
		billCalculateDto.setReceiveDate(DateUtils.convert("2013-10-13 17:21:58"));
		billCalculateDto.setVolume(new BigDecimal("0.66"));
		billCalculateDto.setWeight(new BigDecimal("95.40"));
		
		List<PdaResultBillCalculateDto> list = pdaBillCaculateService.queryPdaBillPrice(billCalculateDto);
		if(list!=null && list.size()>0){
			System.out.println("列表SIZE============================"+list.size());
			
		}
		
	}
}

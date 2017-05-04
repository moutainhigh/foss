/**
 *  initial comments.
 */
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
 * PROJECT NAME	: pkp-pricing
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/service/impl/PdaBillCaculateService.java
 * 
 * FILE NAME        	: PdaBillCaculateService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.pickup.pricing.api.server.service.IBillCaculateService;
import com.deppon.foss.module.pickup.pricing.api.server.service.ICityMarketPlanService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IEffectivePlanDetailService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPdaBillCaculateService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPriceValuationService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IRegionAirService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IRegionArriveService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IRegionExpressService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IRegionService;
import com.deppon.foss.module.pickup.pricing.api.server.util.PriceUtil;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PriceEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.CityMarketPlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.EffectivePlanDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PdaQueryBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PdaQueryBillCalculateSubDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PdaResultBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PdaResultDiscountDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PriceDiscountDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ProductPriceDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateValueAddDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryProductPriceDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ResultProductPriceDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ValueAddDto;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.BillCaculateServiceException;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class PdaBillCaculateService.
 *
 * @Description: PDA计价类
 * PdaBillCaculateService.java Create on 2013-3-18 下午2:02:45
 * Company:IBM
 * @author FOSSDP-Administrator
 * Copyright (c) 2013 Company,Inc. All Rights Reserved
 * @version V1.0
 */
public class PdaBillCaculateService implements IPdaBillCaculateService {
	
	/** 计价明细 Service. */
	@Inject
	private IEffectivePlanDetailService effectivePlanDetailService;
	
	/** 出发区域 Service. */
	@Inject
	private IRegionService regionService;
	
	/** 到达区域. */
	@Inject
	IRegionArriveService regionArriveService;
	/**
	 * 区域 Service
	 */
	@Inject
	private IRegionExpressService regionExpressService;
	
	/** 计费规则 Service. */
	@Inject
	private IPriceValuationService priceValuationService;
	
	/** 产品 Service. */
	@Inject
	private IProductService productService;

	/**
	 * 城市优惠活动
	 */
	@Inject
	private ICityMarketPlanService cityMarketPlanService;
	
	public void setProductService(IProductService productService) {
		this.productService = productService;
	}
	
	public void setPriceValuationService(
			IPriceValuationService priceValuationService) {
		this.priceValuationService = priceValuationService;
	}
	
	public void setRegionExpressService(IRegionExpressService regionExpressService) {
	    this.regionExpressService = regionExpressService;
	}
	
	/**
	 * Sets the region arrive service.
	 *
	 * @param regionArriveService the new region arrive service
	 */
	public void setRegionArriveService(IRegionArriveService regionArriveService) {
		this.regionArriveService = regionArriveService;
	}
	
	
	/** 空运区域 Service. */
	@Inject
	private IRegionAirService regionAirService;
	
	/** 价格计算 SERVICE. */
	@Inject
	private IBillCaculateService billCaculateService;
	/**
	 * 设置 价格计算 SERVICE.
	 *
	 * @param billCaculateService the new 价格计算 SERVICE
	 */
	public void setBillCaculateService(IBillCaculateService billCaculateService) {
		this.billCaculateService = billCaculateService;
	}
	/**
	 * 设置 计价明细 Service.
	 *
	 * @param effectivePlanDetailService the new 计价明细 Service
	 */
	public void setEffectivePlanDetailService(IEffectivePlanDetailService effectivePlanDetailService) {
		this.effectivePlanDetailService = effectivePlanDetailService;
	}
	/**
	 * 设置 区域 Service.
	 *
	 * @param regionService the new 区域 Service
	 */
	public void setRegionService(IRegionService regionService) {
		this.regionService = regionService;
	}
	/**
	 * 设置 空运区域 Service.
	 *
	 * @param regionAirService the new 空运区域 Service
	 */
	public void setRegionAirService(IRegionAirService regionAirService) {
		this.regionAirService = regionAirService;
	}
	
	/**
	 * Query pda transport bill price.
	 *
	 * @param billCalculateDto the bill calculate dto
	 * @return the list
	 * @Description: 计算运费
	 * 
	 * Company:IBM
	 * @author FOSSDP-sz
	 * @date 2013-1-14 上午10:33:35
	 * @version V1.0
	 */
	@Override
	public List<PdaResultBillCalculateDto> queryPdaTransportBillPrice(
			PdaQueryBillCalculateDto billCalculateDto) {
		QueryBillCacilateDto queryBillCacilateDto = new QueryBillCacilateDto();
		queryBillCacilateDto.setOriginalOrgCode(billCalculateDto.getOriginalOrgCode());
		queryBillCacilateDto.setDestinationOrgCode(billCalculateDto.getDestinationOrgCode());
		queryBillCacilateDto.setProductCode(billCalculateDto.getProductCode());
		queryBillCacilateDto.setCurrencyCdoe(billCalculateDto.getCurrencyCdoe());
		queryBillCacilateDto.setDeptRegionId(billCalculateDto.getDeptRegionId());
		queryBillCacilateDto.setArrvRegionId(billCalculateDto.getArrvRegionId());
		queryBillCacilateDto.setFlightShift(billCalculateDto.getFlightShift());
		queryBillCacilateDto.setGoodsCode(billCalculateDto.getGoodsCode());
		queryBillCacilateDto.setReceiveDate(billCalculateDto.getReceiveDate());
		queryBillCacilateDto.setIsReceiveGoods(billCalculateDto.getIsReceiveGoods());
		queryBillCacilateDto.setWeight(billCalculateDto.getWeight());
		queryBillCacilateDto.setVolume(billCalculateDto.getVolume());
		queryBillCacilateDto.setDeptRegionId(billCalculateDto.getDeptRegionId());
		queryBillCacilateDto.setArrvRegionId(billCalculateDto.getArrvRegionId());
		queryBillCacilateDto.setLongOrShort(billCalculateDto.getLongOrShort());
		queryBillCacilateDto.setChannelCode(billCalculateDto.getChannelCode());
		queryBillCacilateDto.setCustomerCode(billCalculateDto.getCustomerCode());
		queryBillCacilateDto.setEconomySince(billCalculateDto.getEconomySince());
		/*************************市场营销活动****************************/
		queryBillCacilateDto.setActiveDto(billCalculateDto.getActiveDto());
		queryBillCacilateDto.setCalActiveDiscount(billCalculateDto.isCalActiveDiscount());	
		//出发外场
		queryBillCacilateDto.setLoadOrgCode(billCalculateDto.getStartOutFieldCode());
		//到达外场
		queryBillCacilateDto.setLastOutLoadOrgCode(billCalculateDto.getArriveOutFieldCode());		
		//PDA计算运费
		queryBillCacilateDto.setPda(true);
		List<ProductPriceDto> productPriceDtos = billCaculateService.searchProductPriceList(queryBillCacilateDto);
		//设置纯运费，为了计算增值推广活动的校验
		billCalculateDto.setBilllingAmount(queryBillCacilateDto.getTransportFee());
		if(CollectionUtils.isNotEmpty(productPriceDtos)) {
			List<PdaResultBillCalculateDto> resultBillCalculateDtos = new ArrayList<PdaResultBillCalculateDto>(); 
			for (int i = 0; i < productPriceDtos.size(); i++) {
				ProductPriceDto productPriceDto = productPriceDtos.get(i);
				PdaResultBillCalculateDto resultBillCalculateDto =  new PdaResultBillCalculateDto();
				resultBillCalculateDto.setPriceEntityCode(productPriceDto.getPriceEntityCode());
				resultBillCalculateDto.setPriceEntityName(productPriceDto.getPriceEntityName());
				resultBillCalculateDto.setActualFeeRate(productPriceDto.getActualFeeRate());
				resultBillCalculateDto.setCaculateFee(productPriceDto.getCaculateFee());
				resultBillCalculateDto.setDiscountFee(productPriceDto.getDiscountFee());
				resultBillCalculateDto.setCaculateExpression(productPriceDto.getCaculateExpression());
				resultBillCalculateDto.setMinFee(productPriceDto.getMinFee());
				resultBillCalculateDto.setMaxFee(productPriceDto.getMaxFee());
				resultBillCalculateDto.setCentralizePickup(queryBillCacilateDto.getIsReceiveGoods());
				if(CollectionUtils.isNotEmpty(productPriceDto.getDiscountPrograms())) {
					List<PdaResultDiscountDto> resultDiscountDtos = new ArrayList<PdaResultDiscountDto>();
					for (int k = 0; k < productPriceDto.getDiscountPrograms().size(); k++) {
						PriceDiscountDto discountDto =  productPriceDto.getDiscountPrograms().get(k);
						PdaResultDiscountDto pdaResultDiscountDto = new PdaResultDiscountDto();
						pdaResultDiscountDto.setDiscountRate(discountDto.getDiscountRate());
						pdaResultDiscountDto.setMarketName(discountDto.getMarketName());
						pdaResultDiscountDto.setReduceFee(discountDto.getReduceFee());
						pdaResultDiscountDto.setSaleChannelName(discountDto.getSaleChannelName());
						//添加推广活动折扣
						pdaResultDiscountDto.setActiveCode(discountDto.getActiveCode());
						pdaResultDiscountDto.setActiveName(discountDto.getActiveName());
						pdaResultDiscountDto.setActiveStartTime(discountDto.getActiveStartTime());
						pdaResultDiscountDto.setActiveEndTime(discountDto.getActiveEndTime());
						pdaResultDiscountDto.setOptionsCrmId(discountDto.getOptionsCrmId());
						resultDiscountDtos.add(pdaResultDiscountDto);
					}
					resultBillCalculateDto.setDiscountPrograms(resultDiscountDtos);
				}
				resultBillCalculateDtos.add(resultBillCalculateDto);
			}
			return resultBillCalculateDtos;
		} else { 
			return null;
		}
	}
	
	/**
	 * Query pda value add bill price.
	 *
	 * @param billCalculateDto the bill calculate dto
	 * @return the list
	 * @Description: 计算增值服务
	 * 
	 * Company:IBM
	 * @author FOSSDP-sz
	 * @date 2013-1-14 下午4:48:15
	 * @version V1.0
	 */
	@Override
	public List<PdaResultBillCalculateDto> queryPdaValueAddBillPrice(
			PdaQueryBillCalculateDto billCalculateDto) {
		QueryBillCacilateValueAddDto queryBillCacilateValueAddDto = new QueryBillCacilateValueAddDto();
		queryBillCacilateValueAddDto.setOriginalOrgCode(billCalculateDto.getOriginalOrgCode());
		queryBillCacilateValueAddDto.setDestinationOrgCode(billCalculateDto.getDestinationOrgCode());
		queryBillCacilateValueAddDto.setReceiveDate(billCalculateDto.getReceiveDate());
		queryBillCacilateValueAddDto.setProductCode(billCalculateDto.getProductCode());
		queryBillCacilateValueAddDto.setFlightShift(billCalculateDto.getFlightShift());
		queryBillCacilateValueAddDto.setWeight(billCalculateDto.getWeight());
		queryBillCacilateValueAddDto.setVolume(billCalculateDto.getVolume());
		queryBillCacilateValueAddDto.setSubType(billCalculateDto.getSubType());
		queryBillCacilateValueAddDto.setOriginnalCost(billCalculateDto.getOriginnalCost());
		queryBillCacilateValueAddDto.setPricingEntryCode(billCalculateDto.getPricingEntryCode());
		queryBillCacilateValueAddDto.setDeptRegionId(billCalculateDto.getDeptRegionId());
		queryBillCacilateValueAddDto.setArrvRegionId(billCalculateDto.getArrvRegionId());
		queryBillCacilateValueAddDto.setLongOrShort(billCalculateDto.getLongOrShort());
		queryBillCacilateValueAddDto.setChannelCode(billCalculateDto.getChannelCode());
		queryBillCacilateValueAddDto.setCustomerCode(billCalculateDto.getCustomerCode());
		/*************************市场营销活动****************************/
		queryBillCacilateValueAddDto.setActiveDto(billCalculateDto.getActiveDto());
		queryBillCacilateValueAddDto.setCalActiveDiscount(billCalculateDto.isCalActiveDiscount());	
		//出发外场
		queryBillCacilateValueAddDto.setLoadOrgCode(billCalculateDto.getStartOutFieldCode());
		//到达外场
		queryBillCacilateValueAddDto.setLastOutLoadOrgCode(billCalculateDto.getArriveOutFieldCode());		
		//PDA计算运费
		queryBillCacilateValueAddDto.setPda(true);
		//纯运费
		queryBillCacilateValueAddDto.setTransportFee(billCalculateDto.getBilllingAmount());
		List<ValueAddDto> valueAddDtos = billCaculateService.searchValueAddPriceList(queryBillCacilateValueAddDto);
		if(CollectionUtils.isNotEmpty(valueAddDtos)) {
			List<PdaResultBillCalculateDto> resultBillCalculateDtos = new ArrayList<PdaResultBillCalculateDto>();
			for (int i = 0; i < valueAddDtos.size(); i++) {
				ValueAddDto valueAddDto = valueAddDtos.get(i);
				PdaResultBillCalculateDto resultBillCalculateDto =  new PdaResultBillCalculateDto();
				resultBillCalculateDto.setPriceEntityCode(valueAddDto.getPriceEntityCode());
				resultBillCalculateDto.setPriceEntityName(valueAddDto.getPriceEntityName());
				resultBillCalculateDto.setActualFeeRate(valueAddDto.getActualFeeRate());
				resultBillCalculateDto.setCaculateFee(valueAddDto.getCaculateFee());
				resultBillCalculateDto.setDiscountFee(valueAddDto.getDiscountFee());
				resultBillCalculateDto.setCaculateExpression(valueAddDto.getCaculateExpression());
				resultBillCalculateDto.setMinFee(valueAddDto.getMinFee());
				resultBillCalculateDto.setMaxFee(valueAddDto.getMaxFee());
				resultBillCalculateDto.setSubType(valueAddDto.getSubType());
				resultBillCalculateDto.setSubTypeName(valueAddDto.getSubTypeName());
				if(CollectionUtils.isNotEmpty(valueAddDto.getDiscountPrograms())) {
					List<PdaResultDiscountDto> resultDiscountDtos = new ArrayList<PdaResultDiscountDto>();
					for (int k = 0; k < valueAddDto.getDiscountPrograms().size(); k++) {
						PriceDiscountDto discountDto =  valueAddDto.getDiscountPrograms().get(k);
						PdaResultDiscountDto pdaResultDiscountDto = new PdaResultDiscountDto();
						pdaResultDiscountDto.setDiscountRate(discountDto.getDiscountRate());
						pdaResultDiscountDto.setMarketName(discountDto.getMarketName());
						pdaResultDiscountDto.setReduceFee(discountDto.getReduceFee());
						pdaResultDiscountDto.setSaleChannelName(discountDto.getSaleChannelName());
						//添加推广活动折扣
						pdaResultDiscountDto.setActiveCode(discountDto.getActiveCode());
						pdaResultDiscountDto.setActiveName(discountDto.getActiveName());
						pdaResultDiscountDto.setActiveStartTime(discountDto.getActiveStartTime());
						pdaResultDiscountDto.setActiveEndTime(discountDto.getActiveEndTime());
						pdaResultDiscountDto.setOptionsCrmId(discountDto.getOptionsCrmId());
						resultDiscountDtos.add(pdaResultDiscountDto);
					}
					resultBillCalculateDto.setDiscountPrograms(resultDiscountDtos);
				}
				resultBillCalculateDtos.add(resultBillCalculateDto);
			}
			return resultBillCalculateDtos;
		} else { 
			return null;
		}
	}
	
	/**
	 * Query pda bill price.
	 *
	 * @param billCalculateDto the bill calculate dto
	 * @return the list
	 * @Description: 计算物流费用
	 * 
	 * Company:IBM
	 * @author FOSSDP-sz
	 * @date 2013-1-14 下午3:05:21
	 * @version V1.0
	 */
	@Override
	public List<PdaResultBillCalculateDto> queryPdaBillPrice(
			PdaQueryBillCalculateDto billCalculateDto) {
		filterParamert(billCalculateDto);
		List<PdaResultBillCalculateDto> resultBillCalculateDtos = new ArrayList<PdaResultBillCalculateDto>(); 
		List<PdaQueryBillCalculateSubDto> subDtos = billCalculateDto.getPriceEntities();
		/*
		 * 在进行业务计算之前，将billCalculateDto中的运费计算需要的体积信息存入运费所在的sub条目中，
		 * 防止先计算增值费后计算运费时包装体积覆盖掉运费体积
		 */
		for(PdaQueryBillCalculateSubDto temp : subDtos){
			if(StringUtil.isNotBlank(temp.getPriceEntityCode())) {
				String entryCode = temp.getPriceEntityCode();
				String parentEntryCode = PriceUtil.getFirstLevelEntryCode(entryCode);
				if(StringUtil.equals(PriceEntityConstants.PRICING_CODE_FRT, parentEntryCode)) {
					temp.setWoodenVolume(billCalculateDto.getVolume());
					break;
				}
			}
		}
		for (int i = 0; i < subDtos.size(); i++) {
			List<PdaResultBillCalculateDto> billCalculateDtos = null;
			PdaQueryBillCalculateSubDto queryBillCalculateSubDto = subDtos.get(i);
			if(StringUtil.isNotBlank(queryBillCalculateSubDto.getPriceEntityCode())) {
				String entryCode = queryBillCalculateSubDto.getPriceEntityCode();
				String parentEntryCode = PriceUtil.getFirstLevelEntryCode(entryCode);
				//运费
				if(StringUtil.equals(PriceEntityConstants.PRICING_CODE_FRT, parentEntryCode)) {
					// 从sub条目中恢复可能被包装费计算时覆盖掉的体积
					billCalculateDto.setVolume(queryBillCalculateSubDto.getWoodenVolume());
					billCalculateDtos = queryPdaTransportBillPrice(billCalculateDto);
					if(FossConstants.NO.equals(billCalculateDto.getIsReceiveGoods()) && CollectionUtils.isEmpty(billCalculateDtos)){
					    throw new BillCaculateServiceException("没有找到运费!");
					}
					if(FossConstants.YES.equals(billCalculateDto.getIsReceiveGoods()) && CollectionUtils.isEmpty(billCalculateDtos)){
					    billCalculateDto.setIsReceiveGoods(FossConstants.NO);
					    billCalculateDtos = queryPdaTransportBillPrice(billCalculateDto);
					    if(CollectionUtils.isNotEmpty(billCalculateDtos)){
						    PdaQueryBillCalculateSubDto subDtoJhf = new PdaQueryBillCalculateSubDto();
							subDtoJhf.setPriceEntityCode(PricingConstants.PriceEntityConstants.PRICING_CODE_JH);
							subDtos.add(subDtoJhf);
					    }else{
					    	throw new BillCaculateServiceException("没有找到运费!");  
					    }
					}
				//增值费
				} else if(StringUtil.equals(PriceEntityConstants.PRICING_CODE_VALUEADDED, parentEntryCode)) {
					billCalculateDto.setPricingEntryCode(queryBillCalculateSubDto.getPriceEntityCode());
					billCalculateDto.setSubType(queryBillCalculateSubDto.getSubType());
					billCalculateDto.setOriginnalCost(queryBillCalculateSubDto.getOriginnalCost());
					if(StringUtil.equals(PriceEntityConstants.PRICING_CODE_BZ, entryCode)) {
						billCalculateDto.setVolume(queryBillCalculateSubDto.getWoodenVolume());
					}
					billCalculateDtos = queryPdaValueAddBillPrice(billCalculateDto);
				}
				
				if(CollectionUtils.isNotEmpty(billCalculateDtos)) {
					resultBillCalculateDtos.addAll(billCalculateDtos);
				}
			} else {
				throw new BillCaculateServiceException("计价条目CODE为空");
			}
		}
		return resultBillCalculateDtos;
	}
	
	/**
	 * 根据出发始发区域ID,目的地区域ID,产品编码,营业日期,确定获得唯一时效明细信息返回长短途标识.
	 *
	 * @param originalOrgCode the original org code
	 * @param destinationOrgCode the destination org code
	 * @param productCode 产品编码
	 * @param receiveDate 收货日期
	 * @return the long or short
	 * @author DP-Foss-YueHongJie
	 * @date 2012-11-9 下午2:37:08
	 */
	private String getLongOrShort(String originalOrgCode, String destinationOrgCode,String productCode,Date receiveDate){
	    List<EffectivePlanDto> effPlanDetailList = effectivePlanDetailService.queryEffectivePlanDetailListByOrgCode(originalOrgCode, destinationOrgCode, productCode,receiveDate);
	    if(CollectionUtils.isNotEmpty(effPlanDetailList)){
	    	return effPlanDetailList.get(0).getLongOrShort();    
	    }
	    return null;
	}
	
	/**
	 * 设置计算参数
	 * @param billCalculateDto
	 * @param originalId
	 * @param destinationId
	 */
	private void filterParamert(PdaQueryBillCalculateDto billCalculateDto){
		//数据准备
		String originalId = null;
		String destinationId = null;
		if (StringUtils.equals(billCalculateDto.getProductCode(), ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT)
				|| StringUtils.equals(billCalculateDto.getProductCode(), ProductEntityConstants.PRICING_PRODUCT_C2_C20004)) {
			/* 始发部门code 定位价格区域信息 */
			originalId = regionAirService.findRegionOrgByDeptNo(
					billCalculateDto.getOriginalOrgCode(),
					billCalculateDto.getReceiveDate(), null,
					PricingConstants.PRICING_REGION);
		} else {
			/* 始发部门code 定位价格区域信息 */
			originalId = regionService.findRegionOrgByDeptNo(
					billCalculateDto.getOriginalOrgCode(),
					billCalculateDto.getReceiveDate(), null,
					PricingConstants.PRICING_REGION);
		}
		
		if (StringUtils.equals(billCalculateDto.getProductCode(), ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT)
				|| StringUtils.equals(billCalculateDto.getProductCode(), ProductEntityConstants.PRICING_PRODUCT_C2_C20004)) {
			/* 到达部门 code 定位价格区域信息 */
			destinationId = regionAirService.findRegionOrgByDeptNo(
					billCalculateDto.getDestinationOrgCode(),
					billCalculateDto.getReceiveDate(), null,
					PricingConstants.PRICING_REGION);
		} else {
			/* 到达部门 code 定位价格区域信息 */
			destinationId = regionArriveService.findRegionOrgByDeptNo(
					billCalculateDto.getDestinationOrgCode(),
					billCalculateDto.getReceiveDate(), null,
					PricingConstants.ARRIVE_REGION);
		}
		//长短途
				String longOrShort = getLongOrShort(
						billCalculateDto.getOriginalOrgCode(), billCalculateDto.getDestinationOrgCode(), 
						billCalculateDto.getProductCode(), billCalculateDto.getReceiveDate());/* 查询长短途 */
				billCalculateDto.setDeptRegionId(originalId);
				billCalculateDto.setArrvRegionId(destinationId);
				billCalculateDto.setLongOrShort(longOrShort);
				billCalculateDto.setIsReceiveGoods(FossConstants.YES);
	}
	
	/*
	 * 
	 * @Description: 计算快遞物流费用
	 * 
	 * Company:IBM
	 * 
	 * @author FOSSDP-zdp
	 * 
	 * @date 2013-1-14 下午3:05:21
	 * 
	 * @param billCalculateDto
	 * 
	 * @return
	 * 
	 * @version V1.0
	 */
	@Override
	public List<PdaResultBillCalculateDto> queryPdaExpressBillPrice(
		PdaQueryBillCalculateDto billCalculateDto)
		{

		//数据准备
	        String originalId = null;
		String destinationId = null;
		originalId = regionExpressService.findRegionOrgByDeptNo(billCalculateDto.getOriginalOrgCode(),
			billCalculateDto.getReceiveDate(), null,
			PricingConstants.PRICING_REGION);
		destinationId = regionExpressService.findRegionOrgByDeptNo(billCalculateDto.getDestinationOrgCode(), billCalculateDto.getReceiveDate(), null,
			PricingConstants.PRICING_REGION);  
		
  		//------ qiaolf 根据第一次获得的始发、到达区域ID查询运费，如果为空，重新获取行政区域id -------------
  		// 运费查询Bean
//		QueryProductPriceDto queryProductPriceDto = new QueryProductPriceDto();
//		// 设置货币、始发区域、到达区域、航班类别、货物、是否接货、计费规则类型、状态
//		String productCode = billCalculateDto.getProductCode();
//		ProductEntity productEntity = productService.getProductByCache(
//				productCode, billCalculateDto.getReceiveDate());
//		if (productEntity == null) {
//			return null;
//		}
//		// 根据客户端传入的三级产品得到二级产品
//		productCode = productEntity.getParentCode();
//		queryProductPriceDto.setProductCode(productCode);	
//		String currencyCode = billCalculateDto.getCurrencyCdoe();
//		if (null == currencyCode) {
//			currencyCode = FossConstants.CURRENCY_CODE_RMB;
//		}
//		queryProductPriceDto.setCurrencyCode(currencyCode);
//		queryProductPriceDto.setOriginalOrgId(originalId);
//		queryProductPriceDto.setDestinationId(destinationId);
//		queryProductPriceDto.setGoodsTypeCode(billCalculateDto.getGoodsCode());
//		queryProductPriceDto.setReceiveDate(billCalculateDto.getReceiveDate());
//		String isSelfPickUp = billCalculateDto.getIsSelfPickUp();
//		// 默认是否接货为否
//		if (StringUtil.isEmpty(isSelfPickUp)) {
//			isSelfPickUp = FossConstants.NO;
//		}
//		queryProductPriceDto.setIsSelfPickUp(isSelfPickUp);
//		queryProductPriceDto.setType(PricingConstants.VALUATION_TYPE_PRICING);// 价格定义
//		queryProductPriceDto.setActive(FossConstants.ACTIVE);
//  		// 根据三级产品查询计算费用
//		List<ResultProductPriceDto> resultList = priceValuationService
//				.queryPriceValuationByCalculaCondition(queryProductPriceDto);
//  		//如果查询费用为空
//		if(CollectionUtils.isEmpty(resultList)){
//				//重新查询始发行政区域ID
//  				originalId = regionExpressService.findOrgAdministrativeInfoByDeptNo(billCalculateDto.getOriginalOrgCode(),
//  					billCalculateDto.getReceiveDate(), null,
//  					PricingConstants.PRICING_REGION);
//  		  		
//  		  		if(originalId!=null){
//  		  			originalId = originalId.trim();
//  		  		}
//  		  		//重新查询到达行政区域ID
//  		  		destinationId = regionExpressService.findOrgAdministrativeInfoByDeptNo(billCalculateDto.getDestinationOrgCode(), billCalculateDto.getReceiveDate(), null,
//  					PricingConstants.PRICING_REGION); 
//  		  		if(destinationId!=null){
//  		  			destinationId = destinationId.trim();
//  		  		}
//  		}
  		//-----------------------------------------------------------------------------------
		
		
		billCalculateDto.setDeptRegionId(originalId);
		billCalculateDto.setArrvRegionId(destinationId);		 
		billCalculateDto.setIsReceiveGoods(FossConstants.YES);
		
		List<PdaResultBillCalculateDto> resultBillCalculateDtos = new ArrayList<PdaResultBillCalculateDto>(); 
		List<PdaQueryBillCalculateSubDto> subDtos = billCalculateDto.getPriceEntities();
		
  		//校验优惠活动编码是否存在
		String cityMarketCode = billCalculateDto.getCityMarketCode();
		if(StringUtil.isNotEmpty(cityMarketCode)){
			CityMarketPlanEntity cityMarketPlanEntity = cityMarketPlanService.getCityMarketPlanEntityCode(cityMarketCode, 
														billCalculateDto.getOriginalOrgCode(), billCalculateDto.getReceiveDate());
			if(null==cityMarketPlanEntity){
				throw new BillCaculateServiceException("没有找到快递优惠活动编码!");
			}
		}
		
		/*
		 * 在进行业务计算之前，将billCalculateDto中的运费计算需要的体积信息存入运费所在的sub条目中，
		 * 防止先计算增值费后计算运费时包装体积覆盖掉运费体积
		 */
		for(PdaQueryBillCalculateSubDto temp : subDtos){
			if(StringUtil.isNotBlank(temp.getPriceEntityCode())) {
				String entryCode = temp.getPriceEntityCode();
				String parentEntryCode = PriceUtil.getFirstLevelEntryCode(entryCode);
				if(StringUtil.equals(PriceEntityConstants.PRICING_CODE_FRT, parentEntryCode)) {
					temp.setWoodenVolume(billCalculateDto.getVolume());
					break;
				}
			}
		}
		for (int i = 0; i < subDtos.size(); i++) {
			List<PdaResultBillCalculateDto> billCalculateDtos = null;
			PdaQueryBillCalculateSubDto queryBillCalculateSubDto = subDtos.get(i);
			if(StringUtil.isNotBlank(queryBillCalculateSubDto.getPriceEntityCode())) {
				String entryCode = queryBillCalculateSubDto.getPriceEntityCode();
				String parentEntryCode = PriceUtil.getFirstLevelEntryCode(entryCode);
				//运费
				if(StringUtil.equals(PriceEntityConstants.PRICING_CODE_FRT, parentEntryCode)) {
					// 从sub条目中恢复可能被包装费计算时覆盖掉的体积
					billCalculateDto.setVolume(queryBillCalculateSubDto.getWoodenVolume());
					billCalculateDtos = queryExpressPdaTransportBillPrice(billCalculateDto);
				//增值费
				} else if(StringUtil.equals(PriceEntityConstants.PRICING_CODE_VALUEADDED, parentEntryCode)) {
					billCalculateDto.setPricingEntryCode(queryBillCalculateSubDto.getPriceEntityCode());
					billCalculateDto.setSubType(queryBillCalculateSubDto.getSubType());
					billCalculateDto.setOriginnalCost(queryBillCalculateSubDto.getOriginnalCost());
					if(StringUtil.equals(PriceEntityConstants.PRICING_CODE_BZ, entryCode)) {
						billCalculateDto.setVolume(queryBillCalculateSubDto.getWoodenVolume());
					}
					billCalculateDtos = queryExpressPdaValueAddBillPrice(billCalculateDto);
				} 
				if(CollectionUtils.isNotEmpty(billCalculateDtos)) {
					resultBillCalculateDtos.addAll(billCalculateDtos);
				}
			} else {
				throw new BillCaculateServiceException("计价条目CODE为空");
			}
		}
		return resultBillCalculateDtos;
	
		}
	
	/**
	 * 
	 * @Description: 计算运费
	 * 
	 * Company:IBM
	 * 
	 * @author FOSSDP-sz
	 * 
	 * @date 2013-1-14 上午10:33:35
	 * 
	 * @param billCalculateDto
	 * 
	 * @return
	 * 
	 * @version V1.0
	 */
	 
	private List<PdaResultBillCalculateDto> queryExpressPdaTransportBillPrice(
			PdaQueryBillCalculateDto billCalculateDto) {
		QueryBillCacilateDto queryBillCacilateDto = new QueryBillCacilateDto();
		queryBillCacilateDto.setOriginalOrgCode(billCalculateDto.getOriginalOrgCode());
		queryBillCacilateDto.setDestinationOrgCode(billCalculateDto.getDestinationOrgCode());
		queryBillCacilateDto.setProductCode(billCalculateDto.getProductCode());
		queryBillCacilateDto.setCurrencyCdoe(billCalculateDto.getCurrencyCdoe());
		queryBillCacilateDto.setDeptRegionId(billCalculateDto.getDeptRegionId());
		queryBillCacilateDto.setArrvRegionId(billCalculateDto.getArrvRegionId());
		queryBillCacilateDto.setFlightShift(billCalculateDto.getFlightShift());
		queryBillCacilateDto.setGoodsCode(billCalculateDto.getGoodsCode());
		queryBillCacilateDto.setReceiveDate(billCalculateDto.getReceiveDate());
		queryBillCacilateDto.setIsReceiveGoods(billCalculateDto.getIsReceiveGoods());
		queryBillCacilateDto.setWeight(billCalculateDto.getWeight());
		queryBillCacilateDto.setVolume(billCalculateDto.getVolume());
		queryBillCacilateDto.setDeptRegionId(billCalculateDto.getDeptRegionId());
		queryBillCacilateDto.setArrvRegionId(billCalculateDto.getArrvRegionId());
		//queryBillCacilateDto.setLongOrShort(billCalculateDto.getLongOrShort());
		queryBillCacilateDto.setChannelCode(billCalculateDto.getChannelCode());
		queryBillCacilateDto.setCustomerCode(billCalculateDto.getCustomerCode());
		//KDTE-3042
		queryBillCacilateDto.setIsSelfPickUp(billCalculateDto.getIsSelfPickUp());		
		/*************************市场营销活动****************************/
		queryBillCacilateDto.setActiveDto(billCalculateDto.getActiveDto());
		queryBillCacilateDto.setCalActiveDiscount(billCalculateDto.isCalActiveDiscount());	
		//出发外场
		queryBillCacilateDto.setLoadOrgCode(billCalculateDto.getStartOutFieldCode());
		//到达外场
		queryBillCacilateDto.setLastOutLoadOrgCode(billCalculateDto.getArriveOutFieldCode());		
		//是否PDA计算运费
		queryBillCacilateDto.setPda(true);
		queryBillCacilateDto.setIsSelfPickUp(billCalculateDto.getIsSelfPickUp());
		queryBillCacilateDto.setCityMarketCode(billCalculateDto.getCityMarketCode());
		//PDA是否展会默认值为N,151211,2016-3-10
		//解决PDA提交市场营销活动计算运费报错问题
		queryBillCacilateDto.setIsExhibitCargo(false);
		List<ProductPriceDto> productPriceDtos = billCaculateService.searchExpressProductPriceList(queryBillCacilateDto);
		//纯运费，为了校验增值推广活动
		billCalculateDto.setBilllingAmount(queryBillCacilateDto.getTransportFee());
		
		if(CollectionUtils.isNotEmpty(productPriceDtos)) {
			List<PdaResultBillCalculateDto> resultBillCalculateDtos = new ArrayList<PdaResultBillCalculateDto>(); 
			for (int i = 0; i < productPriceDtos.size(); i++) {
				ProductPriceDto productPriceDto = productPriceDtos.get(i);
				PdaResultBillCalculateDto resultBillCalculateDto =  new PdaResultBillCalculateDto();
				resultBillCalculateDto.setPriceEntityCode(productPriceDto.getPriceEntityCode());
				resultBillCalculateDto.setPriceEntityName(productPriceDto.getPriceEntityName());
				resultBillCalculateDto.setActualFeeRate(productPriceDto.getActualFeeRate());
				resultBillCalculateDto.setCaculateFee(productPriceDto.getCaculateFee());
				resultBillCalculateDto.setDiscountFee(productPriceDto.getDiscountFee());
				resultBillCalculateDto.setCaculateExpression(productPriceDto.getCaculateExpression());
				resultBillCalculateDto.setMinFee(productPriceDto.getMinFee());
				resultBillCalculateDto.setMaxFee(productPriceDto.getMaxFee());
				if(CollectionUtils.isNotEmpty(productPriceDto.getDiscountPrograms())) {
					List<PdaResultDiscountDto> resultDiscountDtos = new ArrayList<PdaResultDiscountDto>();
					for (int k = 0; k < productPriceDto.getDiscountPrograms().size(); k++) {
						PriceDiscountDto discountDto =  productPriceDto.getDiscountPrograms().get(k);
						PdaResultDiscountDto pdaResultDiscountDto = new PdaResultDiscountDto();
						pdaResultDiscountDto.setDiscountRate(discountDto.getDiscountRate());
						pdaResultDiscountDto.setMarketName(discountDto.getMarketName());
						pdaResultDiscountDto.setReduceFee(discountDto.getReduceFee());
						pdaResultDiscountDto.setSaleChannelName(discountDto.getSaleChannelName());
						//添加推广活动折扣
						pdaResultDiscountDto.setActiveCode(discountDto.getActiveCode());
						pdaResultDiscountDto.setActiveName(discountDto.getActiveName());
						pdaResultDiscountDto.setActiveStartTime(discountDto.getActiveStartTime());
						pdaResultDiscountDto.setActiveEndTime(discountDto.getActiveEndTime());
						pdaResultDiscountDto.setOptionsCrmId(discountDto.getOptionsCrmId());
						resultDiscountDtos.add(pdaResultDiscountDto);
					}
					resultBillCalculateDto.setDiscountPrograms(resultDiscountDtos);
				}
				resultBillCalculateDtos.add(resultBillCalculateDto);
			}
			return resultBillCalculateDtos;
		} else { 
			return null;
		}
	}
	
	
	/**
	 * 
	 * @Description: 计算增值服务
	 * 
	 * Company:IBM
	 * 
	 * @author FOSSDP-zdp
	 * 
	 * @date 2013-8-14 下午4:48:15
	 * 
	 * @param billCalculateDto
	 * 
	 * @return
	 * 
	 * @version V1.0
	 */
	 
	private List<PdaResultBillCalculateDto> queryExpressPdaValueAddBillPrice(
			PdaQueryBillCalculateDto billCalculateDto) {
		QueryBillCacilateValueAddDto queryBillCacilateValueAddDto = new QueryBillCacilateValueAddDto();
		queryBillCacilateValueAddDto.setOriginalOrgCode(billCalculateDto.getOriginalOrgCode());
		queryBillCacilateValueAddDto.setDestinationOrgCode(billCalculateDto.getDestinationOrgCode());
		queryBillCacilateValueAddDto.setReceiveDate(billCalculateDto.getReceiveDate());
		queryBillCacilateValueAddDto.setProductCode(billCalculateDto.getProductCode());
		queryBillCacilateValueAddDto.setFlightShift(billCalculateDto.getFlightShift());
		queryBillCacilateValueAddDto.setWeight(billCalculateDto.getWeight());
		queryBillCacilateValueAddDto.setVolume(billCalculateDto.getVolume());
		queryBillCacilateValueAddDto.setSubType(billCalculateDto.getSubType());
		queryBillCacilateValueAddDto.setOriginnalCost(billCalculateDto.getOriginnalCost());
		queryBillCacilateValueAddDto.setPricingEntryCode(billCalculateDto.getPricingEntryCode());
		queryBillCacilateValueAddDto.setDeptRegionId(billCalculateDto.getDeptRegionId());
		queryBillCacilateValueAddDto.setArrvRegionId(billCalculateDto.getArrvRegionId());
		//queryBillCacilateValueAddDto.setLongOrShort(billCalculateDto.getLongOrShort());
		queryBillCacilateValueAddDto.setChannelCode(billCalculateDto.getChannelCode());
		queryBillCacilateValueAddDto.setCustomerCode(billCalculateDto.getCustomerCode());
		/*************************市场营销活动****************************/
		queryBillCacilateValueAddDto.setActiveDto(billCalculateDto.getActiveDto());
		queryBillCacilateValueAddDto.setCalActiveDiscount(billCalculateDto.isCalActiveDiscount());	
		//出发外场
		queryBillCacilateValueAddDto.setLoadOrgCode(billCalculateDto.getStartOutFieldCode());
		//到达外场
		queryBillCacilateValueAddDto.setLastOutLoadOrgCode(billCalculateDto.getArriveOutFieldCode());		
		//PDA计算运费
		queryBillCacilateValueAddDto.setPda(true);
		queryBillCacilateValueAddDto.setCityMarketCode(billCalculateDto.getCityMarketCode());
		//设置开单金额
		queryBillCacilateValueAddDto.setTransportFee(billCalculateDto.getBilllingAmount());
		List<ValueAddDto> valueAddDtos = billCaculateService.searchExpressValueAddPriceList(queryBillCacilateValueAddDto);
		if(CollectionUtils.isNotEmpty(valueAddDtos)) {
			List<PdaResultBillCalculateDto> resultBillCalculateDtos = new ArrayList<PdaResultBillCalculateDto>();
			for (int i = 0; i < valueAddDtos.size(); i++) {
				ValueAddDto valueAddDto = valueAddDtos.get(i);
				PdaResultBillCalculateDto resultBillCalculateDto =  new PdaResultBillCalculateDto();
				resultBillCalculateDto.setPriceEntityCode(valueAddDto.getPriceEntityCode());
				
				if(PriceEntityConstants.PRICING_CODE_HK.endsWith(valueAddDto.getPriceEntityCode())){
					resultBillCalculateDto.setPriceEntityName("代收费");
				}else{
					resultBillCalculateDto.setPriceEntityName(valueAddDto.getPriceEntityName());
				}
				resultBillCalculateDto.setActualFeeRate(valueAddDto.getActualFeeRate());
				resultBillCalculateDto.setCaculateFee(valueAddDto.getCaculateFee());
				resultBillCalculateDto.setDiscountFee(valueAddDto.getDiscountFee());
				resultBillCalculateDto.setCaculateExpression(valueAddDto.getCaculateExpression());
				resultBillCalculateDto.setMinFee(valueAddDto.getMinFee());
				resultBillCalculateDto.setMaxFee(valueAddDto.getMaxFee());
				resultBillCalculateDto.setSubType(valueAddDto.getSubType());
				resultBillCalculateDto.setSubTypeName(valueAddDto.getSubTypeName());
				if(CollectionUtils.isNotEmpty(valueAddDto.getDiscountPrograms())) {
					List<PdaResultDiscountDto> resultDiscountDtos = new ArrayList<PdaResultDiscountDto>();
					for (int k = 0; k < valueAddDto.getDiscountPrograms().size(); k++) {
						PriceDiscountDto discountDto =  valueAddDto.getDiscountPrograms().get(k);
						PdaResultDiscountDto pdaResultDiscountDto = new PdaResultDiscountDto();
						pdaResultDiscountDto.setDiscountRate(discountDto.getDiscountRate());
						pdaResultDiscountDto.setMarketName(discountDto.getMarketName());
						pdaResultDiscountDto.setReduceFee(discountDto.getReduceFee());
						pdaResultDiscountDto.setSaleChannelName(discountDto.getSaleChannelName());
						//添加推广活动折扣
						pdaResultDiscountDto.setActiveCode(discountDto.getActiveCode());
						pdaResultDiscountDto.setActiveName(discountDto.getActiveName());
						pdaResultDiscountDto.setActiveStartTime(discountDto.getActiveStartTime());
						pdaResultDiscountDto.setActiveEndTime(discountDto.getActiveEndTime());
						pdaResultDiscountDto.setOptionsCrmId(discountDto.getOptionsCrmId());
						resultDiscountDtos.add(pdaResultDiscountDto);
					}
					resultBillCalculateDto.setDiscountPrograms(resultDiscountDtos);
				}
				resultBillCalculateDtos.add(resultBillCalculateDto);
			}
			return resultBillCalculateDtos;
		} else { 
			return null;
		}
	}

	public void setCityMarketPlanService(
			ICityMarketPlanService cityMarketPlanService) {
		this.cityMarketPlanService = cityMarketPlanService;
	}
	
	/**
	 *  PDA不传产品类型查询物流费用接口
	 * 
	 * @author foss-206860
	 * 
	 * RFOSS2015041729
	 * 
	 * */	
	@Override
	public HashMap<String, List<PdaResultBillCalculateDto>> queryPdaExpressBillPriceNoProduct(
			PdaQueryBillCalculateDto billCalculateDto) {
		HashMap<String, List<PdaResultBillCalculateDto>> resultBillCalculateDtosMap = new HashMap<String, List<PdaResultBillCalculateDto>>();
		//通过快递一级产品查询数据库中所有的快递三级产品
		List<ProductEntity> productEntityList = productService.getLevel3ForExpress();
		if(CollectionUtils.isNotEmpty(productEntityList)){
			//循环设置产品，查询物流费用
			for (int i = 0; i < productEntityList.size(); i++) {
				//将获取到的产品设置到查询实体类
				billCalculateDto.setProductCode(productEntityList.get(i).getCode());
				List<PdaResultBillCalculateDto> queryPdaExpressBillPrice = queryPdaExpressBillPrice(billCalculateDto);
				if(CollectionUtils.isNotEmpty(queryPdaExpressBillPrice)){
					//将查询到的运费信息封装至返回实体中
					resultBillCalculateDtosMap.put(productEntityList.get(i).getCode(), queryPdaExpressBillPrice);
				}
			}
			return resultBillCalculateDtosMap;
		}else{
			return null;
		}
	}
	
}
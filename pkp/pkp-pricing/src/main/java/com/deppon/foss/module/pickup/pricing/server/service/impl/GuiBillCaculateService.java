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

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.pickup.pricing.api.server.service.*;
import com.deppon.foss.module.pickup.pricing.api.server.util.PriceUtil;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PriceEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.CityMarketPlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.*;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.BillCaculateServiceException;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Inject;

import net.sf.cglib.beans.BeanCopier;

import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.*;

import static com.deppon.foss.base.util.define.NumberConstants.NUMBER_2000;

/**
 * The Class GuiBillCaculateService.
 *
 * @Description: GUI计价类
 * PdaBillCaculateService.java Create on 2013-3-18 下午2:02:45
 * Company:IBM
 * @author FOSSDP-Administrator
 * Copyright (c) 2013 Company,Inc. All Rights Reserved
 * @version V1.0
 */
public class GuiBillCaculateService implements IGuiBillCaculateService {
	
	/** 计价明细 Service. */
	@Inject
	private IEffectivePlanDetailService effectivePlanDetailService;
	
	/** 区域 Service. */
	@Inject
	private IRegionService regionService;
	
	/** 空运区域 Service. */
	@Inject
	private IRegionAirService regionAirService;
	
	/** 价格计算 SERVICE. */
	@Inject
	private IBillCaculateService billCaculateService;
	
	
	/** 到达区域  */
	@Inject
	IRegionArriveService regionArriveService;
	
	/**
	 *偏线代理费用 
	 */
	IOuterPriceCaculateService outerPriceCaculateService;
	
	/**
	 * 区域 Service
	 */
	@Inject
	private IRegionExpressService regionExpressService;

	/**
	 * 城市优惠活动
	 */
	@Inject
	private ICityMarketPlanService cityMarketPlanService;
	
	public void setRegionExpressService(IRegionExpressService regionExpressService) {
	    this.regionExpressService = regionExpressService;
	}
	
	public void setOuterPriceCaculateService(
			IOuterPriceCaculateService outerPriceCaculateService) {
		this.outerPriceCaculateService = outerPriceCaculateService;
	}
	/**
	 * Sets the region arrive service.
	 *
	 * @param regionArriveService the new region arrive service
	 */
	public void setRegionArriveService(IRegionArriveService regionArriveService) {
		this.regionArriveService = regionArriveService;
	}
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
	 * Query gui transport bill price.
	 *
	 * @param billCalculateDto the bill calculate dto
	 * @return the list
	 * @Description
	 * <p>计算运费</p>
	 * @author DP-Foss-YueHongJie
	 * @date 2013-4-20 上午11:14:59
	 * @see com.deppon.foss.module.pickup.pricing.api.server.service.IGuiBillCaculateService#queryGuiTransportBillPrice(com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiQueryBillCalculateDto)
	 */
	@Override
	public List<GuiResultBillCalculateDto> queryGuiTransportBillPrice(
		GuiQueryBillCalculateDto billCalculateDto) {
		QueryBillCacilateDto queryBillCacilateDto = new QueryBillCacilateDto();
		queryBillCacilateDto.setWaybillNo(billCalculateDto.getWaybillNo());//设置运单号
		//展会货
		queryBillCacilateDto.setIsExhibitCargo(billCalculateDto.getIsExhibitCargo());
		//伙伴开单
		queryBillCacilateDto.setPartnerBillingLogo(billCalculateDto.getPartnerBillingLogo());
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
		//是否接货是否变更
		queryBillCacilateDto.setIsReceiveGoodsChange(billCalculateDto.getIsReceiveGoodsChange());
		queryBillCacilateDto.setChannelCode(billCalculateDto.getChannelCode());
		queryBillCacilateDto.setWeight(billCalculateDto.getWeight());
		queryBillCacilateDto.setVolume(billCalculateDto.getVolume());
		queryBillCacilateDto.setDeptRegionId(billCalculateDto.getDeptRegionId());
		queryBillCacilateDto.setArrvRegionId(billCalculateDto.getArrvRegionId());
		queryBillCacilateDto.setLongOrShort(billCalculateDto.getLongOrShort());
		queryBillCacilateDto.setCustomerCode(billCalculateDto.getCustomerCode());
		queryBillCacilateDto.setEconomySince(billCalculateDto.getEconomySince());
		queryBillCacilateDto.setLastOrgCode(billCalculateDto.getLastOrgCode());
		//添加行业类型
		queryBillCacilateDto.setIndustrulCode(billCalculateDto.getIndustrulCode());
		//zxy 20140522 DEFECT-2949 MANA-1253 start 新增
		if (StringUtils.equals(billCalculateDto.getProductCode(), ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT)
				|| StringUtils.equals(billCalculateDto.getProductCode(), ProductEntityConstants.PRICING_PRODUCT_C2_C20004)) 
			queryBillCacilateDto.setCombBillTypeCode(billCalculateDto.getCombBillTypeCode());	//zxy 20140507 MANA-1253 新增
		//zxy 20140522 DEFECT-2949 MANA-1253 end 新增
		//市场营销活动
		queryBillCacilateDto.setActiveDto(billCalculateDto.getActiveDto());
		queryBillCacilateDto.setCalActiveDiscount(billCalculateDto.isCalActiveDiscount());
		queryBillCacilateDto.setActiveCode(billCalculateDto.getActiveCode());
		queryBillCacilateDto.setGoodsName(billCalculateDto.getGoodsName());
		queryBillCacilateDto.setDeliveryCustomerCode(billCalculateDto.getDeliveryCustomerCode());		
		queryBillCacilateDto.setOrderChannel(billCalculateDto.getOrderChannel());
		queryBillCacilateDto.setReceiveOrgCode(billCalculateDto.getReceiveOrgCode());
		queryBillCacilateDto.setLoadOrgCode(billCalculateDto.getLoadOrgCode());
		queryBillCacilateDto.setLastOutLoadOrgCode(billCalculateDto.getLastOutLoadOrgCode());
		queryBillCacilateDto.setCustomerPickupOrgCode(billCalculateDto.getCustomerPickupOrgCode());
		queryBillCacilateDto.setBillTime(billCalculateDto.getBillTime());
		queryBillCacilateDto.setTransportFee(billCalculateDto.getTransportFee());
		queryBillCacilateDto.setGoodsWeightTotal(billCalculateDto.getGoodsWeightTotal());
		queryBillCacilateDto.setGoodsVolumeTotal(billCalculateDto.getGoodsVolumeTotal());
		//降价返券需求：为了再次计算获取当前价格版本的折前运费
		//封装内部发货条件
		queryBillCacilateDto.setInternalDeliveryType(billCalculateDto.getInternalDeliveryType());
		queryBillCacilateDto.setEmployeeNo(billCalculateDto.getEmployeeNo());
		queryBillCacilateDto.setAmount(billCalculateDto.getAmount());
		queryBillCacilateDto.setIsCurrContract(billCalculateDto.getIsCurrContract());
		queryBillCacilateDto.setTotalAmount(billCalculateDto.getTotalAmount());
		List<ProductPriceDto> productPriceDtos = billCaculateService.searchProductPriceList(queryBillCacilateDto);
		//设置纯运费，为了计算增值推广活动的校验
		billCalculateDto.setTransportFee(queryBillCacilateDto.getTransportFee());
		billCalculateDto.setCalActiveDiscount(queryBillCacilateDto.isCalActiveDiscount());
		if(CollectionUtils.isNotEmpty(productPriceDtos)) {
			List<GuiResultBillCalculateDto> resultBillCalculateDtos = new ArrayList<GuiResultBillCalculateDto>(); 
			for (int i = 0; i < productPriceDtos.size(); i++) {
				ProductPriceDto productPriceDto = productPriceDtos.get(i);
				GuiResultBillCalculateDto resultBillCalculateDto =  new GuiResultBillCalculateDto();
				resultBillCalculateDto.setPriceEntryCode(productPriceDto.getPriceEntityCode());
				resultBillCalculateDto.setId(productPriceDto.getId());
				resultBillCalculateDto.setPriceEntryName(productPriceDto.getPriceEntityName());
				resultBillCalculateDto.setActualFeeRate(productPriceDto.getActualFeeRate());
				resultBillCalculateDto.setCaculateFee(productPriceDto.getCaculateFee());
				resultBillCalculateDto.setDiscountFee(productPriceDto.getDiscountFee());
				resultBillCalculateDto.setCaculateExpression(productPriceDto.getCaculateExpression());
				resultBillCalculateDto.setMinFee(productPriceDto.getMinFee());
				resultBillCalculateDto.setMaxFee(productPriceDto.getMaxFee());
				resultBillCalculateDto.setCentralizePickup(productPriceDto.getCentralizePickup());
				resultBillCalculateDto.setHeavyFeeRatePickUpNo(productPriceDto.getHeavyFeeRatePickUpNo());
				resultBillCalculateDto.setHeavyFeeRatePickUpYes(productPriceDto.getHeavyFeeRatePickUpYes());
				resultBillCalculateDto.setMinFeePickUpNo(productPriceDto.getMinFeePickUpNo());
				resultBillCalculateDto.setMinFeePickUpYes(productPriceDto.getMinFeePickUpYes());
				resultBillCalculateDto.setVolumeWeight(productPriceDto.getVolumeWeight());
				resultBillCalculateDto.setFlightShiftNo(productPriceDto.getFlightShiftNo());
				resultBillCalculateDto.setLongOrShort(productPriceDto.getLongOrShort());
				resultBillCalculateDto.setCentralizePickup(billCalculateDto.getIsReceiveGoods());
				resultBillCalculateDto.setCaculateType(productPriceDto.getCaculateType());
				resultBillCalculateDto.setResultOuterPriceCaccilateDto(productPriceDto.getResultOuterPriceCaccilateDto());
				resultBillCalculateDto.setCentralizePickupResult(productPriceDto.getCentralizePickup());
				resultBillCalculateDto.setCentralizeDeliveryResult(productPriceDto.getCentralizeDelivery());
				//将偏线费用赋给GuiResultBillCalculateDto 352676
				resultBillCalculateDto.setPartialTransportFee(productPriceDto.getPartialTransportFee());
				//精准包裹产生的productPriceDto已经封装好是否接送货，将其调整一致即可
				resultBillCalculateDto.setCentralizePickup(productPriceDto.getCentralizePickup()!=null?productPriceDto.getCentralizePickup():resultBillCalculateDto.getCentralizePickup());
				if(CollectionUtils.isNotEmpty(productPriceDto.getDiscountPrograms())) {
					List<GuiResultDiscountDto> resultDiscountDtos = new ArrayList<GuiResultDiscountDto>();
					for (int k = 0; k < productPriceDto.getDiscountPrograms().size(); k++) {
						PriceDiscountDto discountDto =  productPriceDto.getDiscountPrograms().get(k);
						GuiResultDiscountDto guiResultDiscountDto = new GuiResultDiscountDto();
						guiResultDiscountDto.setDiscountRate(discountDto.getDiscountRate());
						guiResultDiscountDto.setMarketName(discountDto.getMarketName());
						guiResultDiscountDto.setReduceFee(discountDto.getReduceFee());
						guiResultDiscountDto.setPriceEntryCode(discountDto.getPriceEntryCode());
						guiResultDiscountDto.setPriceEntryName(discountDto.getPriceEntryName());
						guiResultDiscountDto.setSaleChannelCode(discountDto.getSaleChannelCode());
						guiResultDiscountDto.setSaleChannelName(discountDto.getSaleChannelName());
						guiResultDiscountDto.setDiscountId(discountDto.getDiscountId());
						guiResultDiscountDto.setDiscountType(discountDto.getType());
						guiResultDiscountDto.setDiscountTypeName(discountDto.getTypeName());
						guiResultDiscountDto.setChargeDetailId(discountDto.getChargeDetailId());
						//CRM营销活动
						guiResultDiscountDto.setActiveCode(discountDto.getActiveCode());
						guiResultDiscountDto.setActiveName(discountDto.getActiveName());
						guiResultDiscountDto.setActiveStartTime(discountDto.getActiveStartTime());
						guiResultDiscountDto.setActiveEndTime(discountDto.getActiveEndTime());
						guiResultDiscountDto.setOptionsCrmId(discountDto.getOptionsCrmId());
						resultDiscountDtos.add(guiResultDiscountDto);
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
	 * Query gui value add bill price.
	 *
	 * @param billCalculateDto the bill calculate dto
	 * @return the list
	 * @Description：
	 * <p>计算增值服务</p>
	 * @author DP-Foss-YueHongJie
	 * @date 2013-4-20 上午11:15:30
	 * @see com.deppon.foss.module.pickup.pricing.api.server.service.IGuiBillCaculateService#queryGuiValueAddBillPrice(com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiQueryBillCalculateDto)
	 */
	@Override
	public List<GuiResultBillCalculateDto> queryGuiValueAddBillPrice(
		GuiQueryBillCalculateDto billCalculateDto) {
		QueryBillCacilateValueAddDto queryBillCacilateValueAddDto = new QueryBillCacilateValueAddDto();
		queryBillCacilateValueAddDto.setWaybillNo(billCalculateDto.getWaybillNo());
		queryBillCacilateValueAddDto.setOriginalOrgCode(billCalculateDto.getOriginalOrgCode());
		queryBillCacilateValueAddDto.setDestinationOrgCode(billCalculateDto.getDestinationOrgCode());
		queryBillCacilateValueAddDto.setReceiveDate(billCalculateDto.getReceiveDate());
		queryBillCacilateValueAddDto.setProductCode(billCalculateDto.getProductCode());
		queryBillCacilateValueAddDto.setFlightShift(billCalculateDto.getFlightShift());
		queryBillCacilateValueAddDto.setWeight(billCalculateDto.getWeight());
		queryBillCacilateValueAddDto.setVolume(billCalculateDto.getVolume());
		queryBillCacilateValueAddDto.setSubType(billCalculateDto.getSubType());
		queryBillCacilateValueAddDto.setKilom(billCalculateDto.getKilom());
		queryBillCacilateValueAddDto.setOriginnalCost(billCalculateDto.getOriginnalCost());
		queryBillCacilateValueAddDto.setPricingEntryCode(billCalculateDto.getPricingEntryCode());
		queryBillCacilateValueAddDto.setDeptRegionId(billCalculateDto.getDeptRegionId());
		queryBillCacilateValueAddDto.setArrvRegionId(billCalculateDto.getArrvRegionId());
		queryBillCacilateValueAddDto.setLongOrShort(billCalculateDto.getLongOrShort());
		queryBillCacilateValueAddDto.setChannelCode(billCalculateDto.getChannelCode());
		queryBillCacilateValueAddDto.setCustomerCode(billCalculateDto.getCustomerCode());
		queryBillCacilateValueAddDto.setFeeRate(billCalculateDto.getFeeRate());
		//定价项目POP-407foss没有将接货金额、保费金额5个字段传给规则引擎
		queryBillCacilateValueAddDto.setValuedtos(billCalculateDto.getValuedtos());
		//添加行业类型
		queryBillCacilateValueAddDto.setIndustrulCode(billCalculateDto.getIndustrulCode());
		queryBillCacilateValueAddDto.setGoodsTypeCode(billCalculateDto.getGoodsCode());
		//liyongfei 新增计费方式
		queryBillCacilateValueAddDto.setCaculateType(billCalculateDto.getCaculateType());
		
		//市场营销活动
		queryBillCacilateValueAddDto.setActiveDto(billCalculateDto.getActiveDto());
		queryBillCacilateValueAddDto.setCalActiveDiscount(billCalculateDto.isCalActiveDiscount());
		queryBillCacilateValueAddDto.setActiveCode(billCalculateDto.getActiveCode());
		queryBillCacilateValueAddDto.setGoodsName(billCalculateDto.getGoodsName());
		queryBillCacilateValueAddDto.setDeliveryCustomerCode(billCalculateDto.getDeliveryCustomerCode());		
		queryBillCacilateValueAddDto.setOrderChannel(billCalculateDto.getOrderChannel());
		queryBillCacilateValueAddDto.setReceiveOrgCode(billCalculateDto.getReceiveOrgCode());
		queryBillCacilateValueAddDto.setLoadOrgCode(billCalculateDto.getLoadOrgCode());
		queryBillCacilateValueAddDto.setLastOutLoadOrgCode(billCalculateDto.getLastOutLoadOrgCode());
		queryBillCacilateValueAddDto.setCustomerPickupOrgCode(billCalculateDto.getCustomerPickupOrgCode());
		queryBillCacilateValueAddDto.setBillTime(billCalculateDto.getBillTime());
		queryBillCacilateValueAddDto.setTransportFee(billCalculateDto.getTransportFee());
		queryBillCacilateValueAddDto.setGoodsWeightTotal(billCalculateDto.getGoodsWeightTotal());
		queryBillCacilateValueAddDto.setGoodsVolumeTotal(billCalculateDto.getGoodsVolumeTotal());
		queryBillCacilateValueAddDto.setIsReceiveGoods(billCalculateDto.getIsReceiveGoods());
		// 整车保价费需要GUI端传递费率
		if(StringUtil.equals(queryBillCacilateValueAddDto.getProductCode(),
				PricingConstants.ProductEntityConstants.PRICING_PRODUCT_FULL_VEHICLE)
				&& StringUtil.equals(queryBillCacilateValueAddDto.getPricingEntryCode(),
						PriceEntityConstants.PRICING_CODE_BF)){
			if(queryBillCacilateValueAddDto.getFeeRate() == null){
				throw new BillCaculateServiceException("GUI端传入整车保价费率不能为空！");
			}
		}
		//封装内部发货条件
		//封装内部发货条件
		queryBillCacilateValueAddDto.setInternalDeliveryType(billCalculateDto.getInternalDeliveryType());
		queryBillCacilateValueAddDto.setEmployeeNo(billCalculateDto.getEmployeeNo());
		//封装是否获取保价费率---206860
		queryBillCacilateValueAddDto.setIsGetInsuranceRate(false);
		queryBillCacilateValueAddDto.setCalDiscount(billCalculateDto.isCalDiscount());
		//封装最低费率和最高费率
		queryBillCacilateValueAddDto.setMinFeeRate(billCalculateDto.getMinFeeRate());
		queryBillCacilateValueAddDto.setMaxFeeRate(billCalculateDto.getMaxFeeRate());

		List<ValueAddDto> valueAddDtos = billCaculateService.searchValueAddPriceList(queryBillCacilateValueAddDto);
		
		billCalculateDto.setCalActiveDiscount(queryBillCacilateValueAddDto.isCalActiveDiscount());
		if(CollectionUtils.isNotEmpty(valueAddDtos)) {
			List<GuiResultBillCalculateDto> resultBillCalculateDtos = new ArrayList<GuiResultBillCalculateDto>();
			for (int i = 0; i < valueAddDtos.size(); i++) {
				ValueAddDto valueAddDto = valueAddDtos.get(i);
				GuiResultBillCalculateDto resultBillCalculateDto =  new GuiResultBillCalculateDto();
				resultBillCalculateDto.setId(valueAddDto.getId());
				resultBillCalculateDto.setCaculateExpression(valueAddDto.getCaculateExpression());
				resultBillCalculateDto.setActualFeeRate(valueAddDto.getActualFeeRate());
				resultBillCalculateDto.setCaculateFee(valueAddDto.getCaculateFee());
				resultBillCalculateDto.setDiscountFee(valueAddDto.getDiscountFee());
				resultBillCalculateDto.setFee(valueAddDto.getFee());
				resultBillCalculateDto.setFeeRate(valueAddDto.getFeeRate());
				resultBillCalculateDto.setMinFee(valueAddDto.getMinFee());
				resultBillCalculateDto.setMaxFee(valueAddDto.getMaxFee());
				resultBillCalculateDto.setSubType(valueAddDto.getSubType());
				resultBillCalculateDto.setSubTypeName(valueAddDto.getSubTypeName());
				resultBillCalculateDto.setCaculateType(valueAddDto.getCaculateType());
				resultBillCalculateDto.setPriceEntryCode(valueAddDto.getPriceEntityCode());
				resultBillCalculateDto.setPriceEntryName(valueAddDto.getPriceEntityName());
				resultBillCalculateDto.setProductCode(valueAddDto.getProductCode());
				resultBillCalculateDto.setProductName(valueAddDto.getProductName());
				resultBillCalculateDto.setGoodsTypeCode(valueAddDto.getGoodsTypeCode());
				resultBillCalculateDto.setGoodsTypeName(valueAddDto.getGoodsTypeName());
				resultBillCalculateDto.setDefaultBF(valueAddDto.getDefaultBF());
				resultBillCalculateDto.setCandelete(valueAddDto.getCandelete());
				resultBillCalculateDto.setCanmodify(valueAddDto.getCanmodify());
				resultBillCalculateDto.setHeavyFeeRate(valueAddDto.getHeavyFeeRate());
				resultBillCalculateDto.setLightFeeRate(valueAddDto.getActualFeeRate());
				resultBillCalculateDto.setCaculateType(valueAddDto.getCaculateType());
				resultBillCalculateDto.setCentralizePickup(billCalculateDto.getIsReceiveGoods());
				if(CollectionUtils.isNotEmpty(valueAddDto.getDiscountPrograms())) {
					List<GuiResultDiscountDto> resultDiscountDtos = new ArrayList<GuiResultDiscountDto>();
					for (int k = 0; k < valueAddDto.getDiscountPrograms().size(); k++) {
						PriceDiscountDto discountDto =  valueAddDto.getDiscountPrograms().get(k);
						GuiResultDiscountDto guiResultDiscountDto = new GuiResultDiscountDto();
						guiResultDiscountDto.setDiscountRate(discountDto.getDiscountRate());
						guiResultDiscountDto.setMarketName(discountDto.getMarketName());
						guiResultDiscountDto.setReduceFee(discountDto.getReduceFee());
						guiResultDiscountDto.setPriceEntryCode(discountDto.getPriceEntryCode());
						guiResultDiscountDto.setPriceEntryName(discountDto.getPriceEntryName());
						//为了后期获取折扣子费用---定价体系优化项目POP-423
						guiResultDiscountDto.setSubType(discountDto.getSubType());
						guiResultDiscountDto.setSubTypeName(discountDto.getSubTypeName());
						guiResultDiscountDto.setSaleChannelCode(discountDto.getSaleChannelCode());
						guiResultDiscountDto.setSaleChannelName(discountDto.getSaleChannelName());
						guiResultDiscountDto.setDiscountId(discountDto.getDiscountId());
						guiResultDiscountDto.setDiscountType(discountDto.getType());
						guiResultDiscountDto.setDiscountTypeName(discountDto.getTypeName());
						guiResultDiscountDto.setChargeDetailId((discountDto.getChargeDetailId()));
						//CRM营销活动
						guiResultDiscountDto.setActiveCode(discountDto.getActiveCode());
						guiResultDiscountDto.setActiveName(discountDto.getActiveName());
						guiResultDiscountDto.setActiveStartTime(discountDto.getActiveStartTime());
						guiResultDiscountDto.setActiveEndTime(discountDto.getActiveEndTime());
						guiResultDiscountDto.setOptionsCrmId(discountDto.getOptionsCrmId());
						resultDiscountDtos.add(guiResultDiscountDto);
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
	 * Query gui bill price.
	 *
	 * @param billCalculateDto the bill calculate dto
	 * @return the list
	 * @Description:
	 * <p>计算物流费用</p>
	 * @author DP-Foss-YueHongJie
	 * @date 2013-4-20 上午11:15:54
	 * @see com.deppon.foss.module.pickup.pricing.api.server.service.IGuiBillCaculateService#queryGuiBillPrice(com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiQueryBillCalculateDto)
	 */
	@Override
	public List<GuiResultBillCalculateDto> queryGuiBillPrice(
		GuiQueryBillCalculateDto billCalculateDto) {
		if(!StringUtil.equals(ProductEntityConstants.PRICING_PRODUCT_PCP,billCalculateDto.getProductCode())){
			filterParamert(billCalculateDto);
		}
		List<GuiResultBillCalculateDto> resultBillCalculateDtos = new ArrayList<GuiResultBillCalculateDto>();
		List<GuiQueryBillCalculateSubDto> subDtos = billCalculateDto.getPriceEntities();
		/*
		 * 
		 * 在进行业务计算之前，将billCalculateDto中的运费计算需要的体积信息存入运费所在的sub条目中，
		 * 防止先计算增值费后计算运费时包装体积覆盖掉运费体积
		 */
		for(GuiQueryBillCalculateSubDto temp : subDtos){
			if(StringUtil.isNotBlank(temp.getPriceEntityCode())) {
				String entryCode = temp.getPriceEntityCode();
				String parentEntryCode = PriceUtil.getFirstLevelEntryCode(entryCode);
				if(StringUtil.equals(PriceEntityConstants.PRICING_CODE_FRT, parentEntryCode)) {
					temp.setWoodenVolume(billCalculateDto.getVolume());
					break;
				}
			}
		}
		//定价项目POP-407foss没有将接货金额、保费金额5个字段传给规则引擎
		 Map<String,GuiQueryBillCalculateSubDto> valuedtos=new HashMap<String,GuiQueryBillCalculateSubDto>();
		for(GuiQueryBillCalculateSubDto valuedto : subDtos){
			if(StringUtil.isNotBlank(valuedto.getPriceEntityCode())) {
				String entryCode = valuedto.getPriceEntityCode();
				if(StringUtil.equals(PriceEntityConstants.PRICING_CODE_BF, entryCode)) {
					  valuedtos.put(PriceEntityConstants.PRICING_CODE_BF, valuedto);
				}
				if(StringUtil.equals(PriceEntityConstants.PRICING_CODE_HK, entryCode)) {
					  valuedtos.put(PriceEntityConstants.PRICING_CODE_HK, valuedto);
				}
			}
		}
		billCalculateDto.setValuedtos(valuedtos);
		//新增属性，货物按照什么方式计费 重量还是体积 ①
		String caculateType =null;
		for (int i = 0; i < subDtos.size(); i++) {
			List<GuiResultBillCalculateDto> billCalculateDtos = null;
			GuiQueryBillCalculateSubDto queryBillCalculateSubDto = subDtos.get(i);
			if(StringUtil.isNotBlank(queryBillCalculateSubDto.getPriceEntityCode())) {
				String entryCode = queryBillCalculateSubDto.getPriceEntityCode();
				String parentEntryCode = PriceUtil.getFirstLevelEntryCode(entryCode);
				String productCode = billCalculateDto.getProductCode();
				//GUI端整车产品不算运费
				if(!PricingConstants.ProductEntityConstants.PRICING_PRODUCT_FULL_VEHICLE.equals(productCode)){
				    //运费
				    if(StringUtil.equals(PriceEntityConstants.PRICING_CODE_FRT, parentEntryCode)) {
				    	// 从sub条目中恢复可能被包装费计算时覆盖掉的体积
						billCalculateDto.setVolume(queryBillCalculateSubDto.getWoodenVolume());
				    	billCalculateDtos = queryGuiTransportBillPrice(billCalculateDto);
				    	//逻辑 ①赋值
				    	if(billCalculateDtos!=null && billCalculateDtos.size()>0){
				    		caculateType = billCalculateDtos.get(0).getCaculateType();
				    	}
						//如果三级产品是精准包裹
						if (StringUtil.equals(billCalculateDto.getProductCode(), ProductEntityConstants.PRICING_PRODUCT_PCP)) {
							//结果集为空就抛出异常
							if (CollectionUtils.isEmpty(billCalculateDtos)) {
								throw new BillCaculateServiceException("没有找到运费!");
							} else {
								//页面选择了接货，并且计价条目为不接货时,增加接货条目
								if (StringUtil.equals(billCalculateDto.getIsReceiveGoods(), PricingConstants.CENTRALIZE_PICKUP_YES)
										&& StringUtil.equals(billCalculateDtos.get(0).getCentralizePickupResult(), PricingConstants.CENTRALIZE_PICKUP_NO)) {
									GuiQueryBillCalculateSubDto subDtoJhf = new GuiQueryBillCalculateSubDto();
									subDtoJhf.setPriceEntityCode(PricingConstants.PriceEntityConstants.PRICING_CODE_JH);
									subDtos.add(subDtoJhf);
								}
							}
						}

						if(!StringUtil.equals(billCalculateDto.getProductCode(),ProductEntityConstants.PRICING_PRODUCT_PCP) && FossConstants.NO.equals(billCalculateDto.getIsReceiveGoods()) && CollectionUtils.isEmpty(billCalculateDtos)){
						    throw new BillCaculateServiceException("没有找到运费!");
						}
						if(!StringUtil.equals(billCalculateDto.getProductCode(),ProductEntityConstants.PRICING_PRODUCT_PCP) && FossConstants.YES.equals(billCalculateDto.getIsReceiveGoods()) && CollectionUtils.isEmpty(billCalculateDtos)){
						    billCalculateDto.setIsReceiveGoods(FossConstants.NO);
						    //是否接货做了变更
						    billCalculateDto.setIsReceiveGoodsChange(FossConstants.YES);
						    billCalculateDtos = queryGuiTransportBillPrice(billCalculateDto);
							    if(CollectionUtils.isNotEmpty(billCalculateDtos)){
									GuiQueryBillCalculateSubDto subDtoJhf = new GuiQueryBillCalculateSubDto();
									subDtoJhf.setPriceEntityCode(PricingConstants.PriceEntityConstants.PRICING_CODE_JH);
									caculateType = billCalculateDtos.get(0).getCaculateType();
									subDtos.add(subDtoJhf);
							    }else{
							    	throw new BillCaculateServiceException("没有找到运费!");  
							    }
							//修改为原始是否接货类型
							billCalculateDto.setIsReceiveGoods(FossConstants.YES);
						}
				    }
				}
				//增值费
				if(StringUtil.equals(PriceEntityConstants.PRICING_CODE_VALUEADDED, parentEntryCode)) {
					GuiQueryBillCalculateDto dto2 = new GuiQueryBillCalculateDto();
					BeanCopier copy = BeanCopier.create(billCalculateDto.getClass(), dto2.getClass(), false);
					copy.copy(billCalculateDto, dto2, null);
					//逻辑 ①，零担汽运新规则---增值服务
					/**
					 * 同时配置按重量收费和按体积收费，开单时，该票货物若按重量计费，当开单重量满足“起点＜开单重量≤终点，
					 * 则开单时读取该增值收费方案；该票货物若按体积计费，当开单体积满足“起点＜开单体积≤终点，则开单时读取该增值收费方案；
					 * 若只配置了按重量收费，当开单时，无论按照重量计费还是体积计费，该票货物开单重量都须满足“起点＜开单重量≤终点，开单时才读取该增值收费方案；
					 * 若只配置了按体积收费，当开单时，无论按照重量计费还是体积计费，该票货物开单体积都须满足“起点＜开单体积≤终点，开单时才读取该增值收费方案；
					 */
					dto2.setCaculateType(caculateType);//本行是设计计费方式
					dto2.setCaculateType(billCalculateDto.getCaculateType() != null ? billCalculateDto.getCaculateType() : dto2.getCaculateType());
					dto2.setPricingEntryCode(queryBillCalculateSubDto.getPriceEntityCode());
					dto2.setSubType(queryBillCalculateSubDto.getSubType());
					dto2.setOriginnalCost(queryBillCalculateSubDto.getOriginnalCost());
					dto2.setPriceEntities(billCalculateDto.getPriceEntities());
					if(StringUtil.equals(PriceEntityConstants.PRICING_CODE_BZ, entryCode)) {
						dto2.setVolume(queryBillCalculateSubDto.getWoodenVolume());
					}
					if(StringUtil.equals(PriceEntityConstants.PRICING_CODE_BF, entryCode)) {
						dto2.setMinFeeRate(queryBillCalculateSubDto.getMinFeeRate());//最低费率
						dto2.setMaxFeeRate(queryBillCalculateSubDto.getMaxFeeRate());//最高费率
					}
					//添加是否接货至计算参数中
					dto2.setIsReceiveGoods(billCalculateDto.getIsReceiveGoods());

					//以下为增值
					//如果产品是精准包裹
					if (StringUtil.equals(dto2.getProductCode(), ProductEntityConstants.PRICING_PRODUCT_PCP)) {
						//项目是保费，则减免2000参与计算，并且没有最低一票，低于2000不计算
						if (StringUtil.equals(dto2.getPricingEntryCode(), PriceEntityConstants.PRICING_CODE_BF)) {
							if (dto2.getOriginnalCost().compareTo(new BigDecimal(NUMBER_2000)) <= 0) {
								continue;
							} else {
								dto2.setOriginnalCost(dto2.getOriginnalCost().subtract(new BigDecimal(NUMBER_2000)));
							}
						}




						//价格条目中包含送货,项目是送货上楼等不用参与计算
						if (StringUtil.equals(dto2.getCentralizeDeliveryResult(), PricingConstants.CONSTANTS_YES) &&
								(StringUtil.equals(dto2.getPricingEntryCode(),PriceEntityConstants.PRICING_CODE_SH)||
										StringUtil.equals(dto2.getPricingEntryCode(),PriceEntityConstants.PRICING_CODE_SHSL)||
										StringUtil.equals(dto2.getPricingEntryCode(),PriceEntityConstants.PRICING_CODE_DJSL)) ) {
								continue;

						}
					}


					billCalculateDtos = queryGuiValueAddBillPrice(dto2);
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
	 * 获取整车的保费相关信息
	 * @param billCalculateDto
	 * @return
	 */
	@Override
	public GuiResultBillCalculateDto getProductPriceDtoOfWVHAndBF(GuiQueryBillCalculateDto billCalculateDto){
		filterParamert(billCalculateDto);
		List<GuiQueryBillCalculateSubDto> subDtos = billCalculateDto.getPriceEntities();
		// 遍历PriceEntities，获取整车+保费等相关数据
		for (int i = 0; i < subDtos.size(); i++) {
			GuiQueryBillCalculateSubDto queryBillCalculateSubDto = subDtos.get(i);
			if(StringUtil.equals(PriceEntityConstants.PRICING_CODE_BF,queryBillCalculateSubDto.getPriceEntityCode())) {	
				billCalculateDto.setPricingEntryCode(queryBillCalculateSubDto.getPriceEntityCode());
				billCalculateDto.setSubType(queryBillCalculateSubDto.getSubType());
				billCalculateDto.setOriginnalCost(queryBillCalculateSubDto.getOriginnalCost());
				break;
			}
		}
		// 从GUI端获取参数信息
		QueryBillCacilateValueAddDto queryBillCacilateValueAddDto = new QueryBillCacilateValueAddDto();
		queryBillCacilateValueAddDto.setWaybillNo(billCalculateDto.getWaybillNo());
		queryBillCacilateValueAddDto.setOriginalOrgCode(billCalculateDto.getOriginalOrgCode());
		queryBillCacilateValueAddDto.setDestinationOrgCode(billCalculateDto.getDestinationOrgCode());
		queryBillCacilateValueAddDto.setReceiveDate(billCalculateDto.getReceiveDate());
		queryBillCacilateValueAddDto.setProductCode(billCalculateDto.getProductCode());
		queryBillCacilateValueAddDto.setFlightShift(billCalculateDto.getFlightShift());
		queryBillCacilateValueAddDto.setWeight(billCalculateDto.getWeight());
		queryBillCacilateValueAddDto.setVolume(billCalculateDto.getVolume());
		queryBillCacilateValueAddDto.setSubType(billCalculateDto.getSubType());
		queryBillCacilateValueAddDto.setKilom(billCalculateDto.getKilom());
		queryBillCacilateValueAddDto.setOriginnalCost(billCalculateDto.getOriginnalCost());
		queryBillCacilateValueAddDto.setPricingEntryCode(billCalculateDto.getPricingEntryCode());
		queryBillCacilateValueAddDto.setDeptRegionId(billCalculateDto.getDeptRegionId());
		queryBillCacilateValueAddDto.setArrvRegionId(billCalculateDto.getArrvRegionId());
		queryBillCacilateValueAddDto.setLongOrShort(billCalculateDto.getLongOrShort());
		queryBillCacilateValueAddDto.setChannelCode(billCalculateDto.getChannelCode());
		queryBillCacilateValueAddDto.setCustomerCode(billCalculateDto.getCustomerCode());
		queryBillCacilateValueAddDto.setFeeRate(billCalculateDto.getFeeRate());
		//（FOSS20150818）RFOSS2015052602 保价阶梯费率---206860
		queryBillCacilateValueAddDto.setCalDiscount(billCalculateDto.isCalDiscount());
		// 查询方案信息
		ValueAddDto valueAddDto = billCaculateService.getProductPriceDtoOfWVHAndBF(queryBillCacilateValueAddDto);
		// 拼装返回对象
		GuiResultBillCalculateDto resultBillCalculateDto = null;
		if(valueAddDto != null){
			resultBillCalculateDto = new GuiResultBillCalculateDto();
			resultBillCalculateDto.setId(valueAddDto.getId());
			resultBillCalculateDto.setCaculateExpression(valueAddDto.getCaculateExpression());
			resultBillCalculateDto.setActualFeeRate(valueAddDto.getActualFeeRate());
			resultBillCalculateDto.setCaculateFee(valueAddDto.getCaculateFee());
			resultBillCalculateDto.setDiscountFee(valueAddDto.getDiscountFee());
			resultBillCalculateDto.setFee(valueAddDto.getFee());
			resultBillCalculateDto.setFeeRate(valueAddDto.getFeeRate());
			resultBillCalculateDto.setMinFee(valueAddDto.getMinFee());
			resultBillCalculateDto.setMaxFee(valueAddDto.getMaxFee());
			resultBillCalculateDto.setSubType(valueAddDto.getSubType());
			resultBillCalculateDto.setSubTypeName(valueAddDto.getSubTypeName());
			resultBillCalculateDto.setCaculateType(valueAddDto.getCaculateType());
			resultBillCalculateDto.setPriceEntryCode(valueAddDto.getPriceEntityCode());
			resultBillCalculateDto.setPriceEntryName(valueAddDto.getPriceEntityName());
			resultBillCalculateDto.setProductCode(valueAddDto.getProductCode());
			resultBillCalculateDto.setProductName(valueAddDto.getProductName());
			resultBillCalculateDto.setGoodsTypeCode(valueAddDto.getGoodsTypeCode());
			resultBillCalculateDto.setGoodsTypeName(valueAddDto.getGoodsTypeName());
			resultBillCalculateDto.setDefaultBF(valueAddDto.getDefaultBF());
			resultBillCalculateDto.setCandelete(valueAddDto.getCandelete());
			resultBillCalculateDto.setCanmodify(valueAddDto.getCanmodify());
			resultBillCalculateDto.setHeavyFeeRate(valueAddDto.getHeavyFeeRate());
			resultBillCalculateDto.setLightFeeRate(valueAddDto.getActualFeeRate());
			resultBillCalculateDto.setCaculateType(valueAddDto.getCaculateType());
			resultBillCalculateDto.setCentralizePickup(billCalculateDto.getIsReceiveGoods());
			resultBillCalculateDto.setMinFeeRate(valueAddDto.getMinFeeRate());
			resultBillCalculateDto.setMaxFeeRate(valueAddDto.getMaxFeeRate());
			if(CollectionUtils.isNotEmpty(valueAddDto.getDiscountPrograms())) {
				List<GuiResultDiscountDto> resultDiscountDtos = new ArrayList<GuiResultDiscountDto>();
				for (int k = 0; k < valueAddDto.getDiscountPrograms().size(); k++) {
					PriceDiscountDto discountDto =  valueAddDto.getDiscountPrograms().get(k);
					GuiResultDiscountDto guiResultDiscountDto = new GuiResultDiscountDto();
					guiResultDiscountDto.setDiscountRate(discountDto.getDiscountRate());
					guiResultDiscountDto.setRenewalDiscountRate(discountDto.getRenewalDiscountRate());
					guiResultDiscountDto.setDiscountRate(discountDto.getDiscountRate());
					guiResultDiscountDto.setMarketName(discountDto.getMarketName());
					guiResultDiscountDto.setReduceFee(discountDto.getReduceFee());
					guiResultDiscountDto.setPriceEntryCode(discountDto.getPriceEntryCode());
					guiResultDiscountDto.setPriceEntryName(discountDto.getPriceEntryName());
					guiResultDiscountDto.setSaleChannelCode(discountDto.getSaleChannelCode());
					guiResultDiscountDto.setSaleChannelName(discountDto.getSaleChannelName());
					guiResultDiscountDto.setDiscountId(discountDto.getDiscountId());
					guiResultDiscountDto.setDiscountType(discountDto.getType());
					guiResultDiscountDto.setDiscountTypeName(discountDto.getTypeName());
					guiResultDiscountDto.setChargeDetailId(discountDto.getChargeDetailId());
					//添加推广活动信息
					guiResultDiscountDto.setActiveCode(discountDto.getActiveCode());
					guiResultDiscountDto.setActiveName(discountDto.getActiveName());
					guiResultDiscountDto.setActiveStartTime(discountDto.getActiveStartTime());
					guiResultDiscountDto.setActiveEndTime(discountDto.getActiveEndTime());
					guiResultDiscountDto.setOptionsCrmId(discountDto.getOptionsCrmId());
					resultDiscountDtos.add(guiResultDiscountDto);
				}
				resultBillCalculateDto.setDiscountPrograms(resultDiscountDtos);
			}
		}		
		return resultBillCalculateDto;
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
	 * 设置计算参数.
	 *
	 * @param billCalculateDto the bill calculate dto
	 */
	private void filterParamert(GuiQueryBillCalculateDto billCalculateDto){
		//长短途
				String longOrShort = getLongOrShort(
						billCalculateDto.getOriginalOrgCode(), billCalculateDto.getDestinationOrgCode(), 
						billCalculateDto.getProductCode(), billCalculateDto.getReceiveDate());/* 查询长短途 */
				billCalculateDto.setLongOrShort(longOrShort);
	}
	
	/**
	 * 
	 * @Description 
	 * <p>计算运费</p> 
	 * @author DP-Foss-zdp
	 * @date 2013-4-20 上午11:14:59
	 * @param billCalculateDto
	 * @return 
	 * @see com.deppon.foss.module.pickup.pricing.api.server.service.IGuiBillCaculateService#queryGuiTransportBillPrice(com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiQueryBillCalculateDto)
	 */
	 
	private List<GuiResultBillCalculateDto> queryGuiExpressTransportBillPrice(
		GuiQueryBillCalculateDto billCalculateDto) {
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
		queryBillCacilateDto.setChannelCode(billCalculateDto.getChannelCode());
		queryBillCacilateDto.setWeight(billCalculateDto.getWeight());
		queryBillCacilateDto.setVolume(billCalculateDto.getVolume());
		queryBillCacilateDto.setDeptRegionId(billCalculateDto.getDeptRegionId());
		queryBillCacilateDto.setArrvRegionId(billCalculateDto.getArrvRegionId());
		//queryBillCacilateDto.setLongOrShort(billCalculateDto.getLongOrShort());
		queryBillCacilateDto.setCustomerCode(billCalculateDto.getCustomerCode());
		queryBillCacilateDto.setIsSelfPickUp(billCalculateDto.getIsSelfPickUp());
		//市场营销活动
		queryBillCacilateDto.setActiveDto(billCalculateDto.getActiveDto());
		//快递集中录单
		if(null!=billCalculateDto.getActiveDto()&& null!=billCalculateDto.getActiveDto().getIsExpressFous()){
			queryBillCacilateDto.getActiveDto().setIsExpressFous("Y");
		}
		queryBillCacilateDto.setCalActiveDiscount(billCalculateDto.isCalActiveDiscount());
		queryBillCacilateDto.setActiveCode(billCalculateDto.getActiveCode());
		queryBillCacilateDto.setGoodsName(billCalculateDto.getGoodsName());
		queryBillCacilateDto.setDeliveryCustomerCode(billCalculateDto.getDeliveryCustomerCode());		
		queryBillCacilateDto.setOrderChannel(billCalculateDto.getOrderChannel());
		queryBillCacilateDto.setReceiveOrgCode(billCalculateDto.getReceiveOrgCode());
		queryBillCacilateDto.setLoadOrgCode(billCalculateDto.getLoadOrgCode());
		queryBillCacilateDto.setLastOutLoadOrgCode(billCalculateDto.getLastOutLoadOrgCode());
		queryBillCacilateDto.setCustomerPickupOrgCode(billCalculateDto.getCustomerPickupOrgCode());
		queryBillCacilateDto.setBillTime(billCalculateDto.getBillTime());
		queryBillCacilateDto.setTransportFee(billCalculateDto.getTransportFee());
		queryBillCacilateDto.setGoodsWeightTotal(billCalculateDto.getGoodsWeightTotal());
		queryBillCacilateDto.setGoodsVolumeTotal(billCalculateDto.getGoodsVolumeTotal());
		queryBillCacilateDto.setGoodsCode(billCalculateDto.getGoodsCode());
		queryBillCacilateDto.setCityMarketCode(billCalculateDto.getCityMarketCode());
		//菜鸟新需求返货折扣
		queryBillCacilateDto.setIsCainiao(billCalculateDto.getIsCainiao());
		queryBillCacilateDto.setReturnWaybillNo(billCalculateDto.getReturnWaybillNo());
		queryBillCacilateDto.setOldreceiveCustomerCode(billCalculateDto.getOldreceiveCustomerCode());
		queryBillCacilateDto.setReturnbilltime(billCalculateDto.getReturnbilltime());
		queryBillCacilateDto.setReturnInsuranceFee(billCalculateDto.getReturnInsuranceFee());
		queryBillCacilateDto.setReturnTransportFee(billCalculateDto.getReturnTransportFee());
		queryBillCacilateDto.setOriginalReceiveOrgCode(billCalculateDto.getOriginalReceiveOrgCode());
		//内部发货折扣
		queryBillCacilateDto.setInternalDeliveryType(billCalculateDto.getInternalDeliveryType());
		queryBillCacilateDto.setEmployeeNo(billCalculateDto.getEmployeeNo());
		queryBillCacilateDto.setAmount(billCalculateDto.getAmount());
		queryBillCacilateDto.setTotalAmount(billCalculateDto.getTotalAmount());
		//伙伴开单
		queryBillCacilateDto.setPartnerBillingLogo(billCalculateDto.getPartnerBillingLogo());
		//封装上门发货
		queryBillCacilateDto.setHomeDelivery(billCalculateDto.isHomeDelivery());
		//2014-04-18 queryBillCacilateDto.set();
		List<ProductPriceDto> productPriceDtos = billCaculateService.searchExpressProductPriceList(queryBillCacilateDto);
		//获得纯运费，并且传会前台开单action
		BigDecimal standardExpFirstFee = BigDecimal.ZERO;
		ExpressDiscountDto expressDiscountDto = null;
		BigDecimal purefreight = BigDecimal.ZERO;
		if(productPriceDtos!=null&&productPriceDtos.size()!=0){
		    for(ProductPriceDto p:productPriceDtos){
		    	if(PriceEntityConstants.PRICING_CODE_FRT.equals(p.getPriceEntityCode())){
		    		purefreight = p.getPurefreight();
		    		standardExpFirstFee = p.getStandardExpFirstFee();
		    		expressDiscountDto = p.getExpressDiscountDto();
		    	}
		     }
		 }
		//纯运费，为了校验增值推广活动
		billCalculateDto.setTransportFee(queryBillCacilateDto.getTransportFee());
		
		if(CollectionUtils.isNotEmpty(productPriceDtos)) {
			List<GuiResultBillCalculateDto> resultBillCalculateDtos = new ArrayList<GuiResultBillCalculateDto>(); 
			for (int i = 0; i < productPriceDtos.size(); i++) {
				ProductPriceDto productPriceDto = productPriceDtos.get(i);
				GuiResultBillCalculateDto resultBillCalculateDto =  new GuiResultBillCalculateDto();
				resultBillCalculateDto.setPriceEntryCode(productPriceDto.getPriceEntityCode());
				resultBillCalculateDto.setId(productPriceDto.getId());
				resultBillCalculateDto.setPriceEntryName(productPriceDto.getPriceEntityName());
				resultBillCalculateDto.setActualFeeRate(productPriceDto.getActualFeeRate());
				resultBillCalculateDto.setCaculateFee(productPriceDto.getCaculateFee());
				resultBillCalculateDto.setDiscountFee(productPriceDto.getDiscountFee());
				resultBillCalculateDto.setCaculateExpression(productPriceDto.getCaculateExpression());
				resultBillCalculateDto.setMinFee(productPriceDto.getMinFee());
				resultBillCalculateDto.setMaxFee(productPriceDto.getMaxFee());
				resultBillCalculateDto.setCentralizePickup(productPriceDto.getCentralizePickup());
				resultBillCalculateDto.setHeavyFeeRatePickUpNo(productPriceDto.getHeavyFeeRatePickUpNo());
				resultBillCalculateDto.setHeavyFeeRatePickUpYes(productPriceDto.getHeavyFeeRatePickUpYes());
				resultBillCalculateDto.setMinFeePickUpNo(productPriceDto.getMinFeePickUpNo());
				resultBillCalculateDto.setMinFeePickUpYes(productPriceDto.getMinFeePickUpYes());
				resultBillCalculateDto.setVolumeWeight(productPriceDto.getVolumeWeight());
				resultBillCalculateDto.setFlightShiftNo(productPriceDto.getFlightShiftNo());
				resultBillCalculateDto.setLongOrShort(productPriceDto.getLongOrShort());
				resultBillCalculateDto.setCentralizePickup(billCalculateDto.getIsReceiveGoods());
				resultBillCalculateDto.setCaculateType(productPriceDto.getCaculateType());
				if(CollectionUtils.isNotEmpty(productPriceDto.getDiscountPrograms())) {
					List<GuiResultDiscountDto> resultDiscountDtos = new ArrayList<GuiResultDiscountDto>();
					for (int k = 0; k < productPriceDto.getDiscountPrograms().size(); k++) {
						PriceDiscountDto discountDto =  productPriceDto.getDiscountPrograms().get(k);
						GuiResultDiscountDto guiResultDiscountDto = new GuiResultDiscountDto();
						guiResultDiscountDto.setDiscountRate(discountDto.getDiscountRate());
						//设置快递运费续重费率
						guiResultDiscountDto.setRenewalDiscountRate(discountDto.getRenewalDiscountRate());
						guiResultDiscountDto.setDiscountRate(discountDto.getDiscountRate());
						guiResultDiscountDto.setMarketName(discountDto.getMarketName());
						guiResultDiscountDto.setReduceFee(discountDto.getReduceFee());
						guiResultDiscountDto.setPriceEntryCode(discountDto.getPriceEntryCode());
						guiResultDiscountDto.setPriceEntryName(discountDto.getPriceEntryName());
						guiResultDiscountDto.setSaleChannelCode(discountDto.getSaleChannelCode());
						guiResultDiscountDto.setSaleChannelName(discountDto.getSaleChannelName());
						guiResultDiscountDto.setDiscountId(discountDto.getDiscountId());
						guiResultDiscountDto.setDiscountType(discountDto.getType());
						guiResultDiscountDto.setDiscountTypeName(discountDto.getTypeName());
						guiResultDiscountDto.setChargeDetailId(discountDto.getChargeDetailId());
						//添加推广活动信息
						guiResultDiscountDto.setActiveCode(discountDto.getActiveCode());
						guiResultDiscountDto.setActiveName(discountDto.getActiveName());
						guiResultDiscountDto.setActiveStartTime(discountDto.getActiveStartTime());
						guiResultDiscountDto.setActiveEndTime(discountDto.getActiveEndTime());
						guiResultDiscountDto.setOptionsCrmId(discountDto.getOptionsCrmId());
						resultDiscountDtos.add(guiResultDiscountDto);
					}
					resultBillCalculateDto.setDiscountPrograms(resultDiscountDtos);
				}
				resultBillCalculateDtos.add(resultBillCalculateDto);
			}
			if(resultBillCalculateDtos!=null&&resultBillCalculateDtos.size()!=0){
			for(GuiResultBillCalculateDto g:resultBillCalculateDtos){
				if(PriceEntityConstants.PRICING_CODE_FRT.equals(g.getPriceEntryCode())){
					g.setPurefreight(purefreight);
					g.setStandExpFirstFee(standardExpFirstFee);
					g.setExpressDiscountDto(expressDiscountDto);
				}
			}
			}
			return resultBillCalculateDtos;
		} else { 
			return null;
		}
	}
	
	
	/**
	 * @Description:
	 * <p>计算快递物流费用</p> 
	 * @author DP-Foss-YueHongJie
	 * @date 2013-4-20 上午11:15:54
	 * @param billCalculateDto
	 * @return 
	 * @see com.deppon.foss.module.pickup.pricing.api.server.service.IGuiBillCaculateService#queryGuiExpressBillPrice(com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiQueryBillCalculateDto)
	 */
	@Override
	public List<GuiResultBillCalculateDto> queryGuiExpressBillPrice(
		GuiQueryBillCalculateDto billCalculateDto) {
	  //数据准备
		String users = billCalculateDto.getUsers();
		String startCountyCode = billCalculateDto.getStartCountyCode();
		String startCityCode = billCalculateDto.getStartCityCode();
		String startProvCode = billCalculateDto.getStartProvCode();
		String arriveCountyCode = billCalculateDto.getArriveCountyCode();
		String arriveCityCode = billCalculateDto.getArriveCityCode();
		String arriveProvCode = billCalculateDto.getArriveProvCode();
  		String originalId = null;
  		String destinationId = null;
  		if("OWS".equalsIgnoreCase(users)){
  			originalId = regionExpressService.findRegionOrgByCode(startCountyCode,startCityCode,startProvCode,
  					billCalculateDto.getReceiveDate(), null,
  					PricingConstants.PRICING_REGION);
  			destinationId = regionExpressService.findRegionOrgByCode(
  					arriveCountyCode,arriveCityCode,arriveProvCode,
					billCalculateDto.getReceiveDate(), null,
					PricingConstants.PRICING_REGION);
  		}else{
  			originalId = regionExpressService.findRegionOrgByDeptNo(billCalculateDto.getOriginalOrgCode(),
  					billCalculateDto.getReceiveDate(), null,
  					PricingConstants.PRICING_REGION);
			destinationId = regionExpressService.findRegionOrgByDeptNo(
					billCalculateDto.getDestinationOrgCode(),
					billCalculateDto.getReceiveDate(), null,
					PricingConstants.PRICING_REGION);
  		}
  		if(originalId!=null){
  			originalId = originalId.trim();
  		}
  		if(destinationId!=null){
  			destinationId = destinationId.trim();
  		}
	  		
	  		
	  		billCalculateDto.setDeptRegionId(originalId);
	  		billCalculateDto.setArrvRegionId(destinationId);	  		 
	  		
	  		List<GuiResultBillCalculateDto> resultBillCalculateDtos = new ArrayList<GuiResultBillCalculateDto>(); 
	  		List<GuiQueryBillCalculateSubDto> subDtos = billCalculateDto.getPriceEntities();
	  		//校验优惠活动编码是否存在
			String cityMarketCode = billCalculateDto.getCityMarketCode();
			if(StringUtil.isNotEmpty(cityMarketCode)){
				CityMarketPlanEntity cityMarketPlanEntity = cityMarketPlanService.getCityMarketPlanEntityCode(cityMarketCode, 
															billCalculateDto.getOriginalOrgCode(), billCalculateDto.getReceiveDate());
				if(null==cityMarketPlanEntity){
					throw new BillCaculateServiceException("没有找到快递优惠活动编码!");
				}
			}
	  		for (int i = 0; i < subDtos.size(); i++) {
	  			List<GuiResultBillCalculateDto> billCalculateDtos = null;
	  			GuiQueryBillCalculateSubDto queryBillCalculateSubDto = subDtos.get(i);
	  			if(StringUtil.isNotBlank(queryBillCalculateSubDto.getPriceEntityCode())) {
	  				String entryCode = queryBillCalculateSubDto.getPriceEntityCode();
	  				String parentEntryCode = PriceUtil.getFirstLevelEntryCode(entryCode);
	  				     //运费
	  				    if(StringUtil.equals(PriceEntityConstants.PRICING_CODE_FRT, parentEntryCode)) {
	  					billCalculateDtos = queryGuiExpressTransportBillPrice(billCalculateDto);
	  					if(CollectionUtils.isEmpty(billCalculateDtos)){
	  					    throw new BillCaculateServiceException("没有找到运费!");
	  					} 
	  				    }
	  				 
	  				//增值费
	  				if(StringUtil.equals(PriceEntityConstants.PRICING_CODE_VALUEADDED, parentEntryCode)) {
	  					billCalculateDto.setPricingEntryCode(queryBillCalculateSubDto.getPriceEntityCode());
	  					billCalculateDto.setSubType(queryBillCalculateSubDto.getSubType());
	  					billCalculateDto.setOriginnalCost(queryBillCalculateSubDto.getOriginnalCost());	  					
	  					billCalculateDtos = queryGuiExpressValueAddBillPrice(billCalculateDto);
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
	 * @Description：
	 * <p>计算增值服务</p> 
	 * @author DP-Foss-zdp
	 * @date 2013-4-20 上午11:15:30
	 * @param billCalculateDto
	 * @return 
	 * @see com.deppon.foss.module.pickup.pricing.api.server.service.IGuiBillCaculateService#queryGuiValueAddBillPrice(com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiQueryBillCalculateDto)
	 */
	 
	private List<GuiResultBillCalculateDto> queryGuiExpressValueAddBillPrice(
		GuiQueryBillCalculateDto billCalculateDto) {
		QueryBillCacilateValueAddDto queryBillCacilateValueAddDto = new QueryBillCacilateValueAddDto();
		queryBillCacilateValueAddDto.setOriginalOrgCode(billCalculateDto.getOriginalOrgCode());
		queryBillCacilateValueAddDto.setDestinationOrgCode(billCalculateDto.getDestinationOrgCode());
		queryBillCacilateValueAddDto.setReceiveDate(billCalculateDto.getReceiveDate());
		queryBillCacilateValueAddDto.setProductCode(billCalculateDto.getProductCode());
		queryBillCacilateValueAddDto.setFlightShift(billCalculateDto.getFlightShift());
		queryBillCacilateValueAddDto.setWeight(billCalculateDto.getWeight());
		queryBillCacilateValueAddDto.setVolume(billCalculateDto.getVolume());
		queryBillCacilateValueAddDto.setSubType(billCalculateDto.getSubType());
		queryBillCacilateValueAddDto.setKilom(billCalculateDto.getKilom());
		queryBillCacilateValueAddDto.setOriginnalCost(billCalculateDto.getOriginnalCost());
		queryBillCacilateValueAddDto.setPricingEntryCode(billCalculateDto.getPricingEntryCode());
		queryBillCacilateValueAddDto.setDeptRegionId(billCalculateDto.getDeptRegionId());
		queryBillCacilateValueAddDto.setArrvRegionId(billCalculateDto.getArrvRegionId());
		//queryBillCacilateValueAddDto.setLongOrShort(billCalculateDto.getLongOrShort());
		queryBillCacilateValueAddDto.setChannelCode(billCalculateDto.getChannelCode());
		queryBillCacilateValueAddDto.setCustomerCode(billCalculateDto.getCustomerCode());
		//市场营销活动
		queryBillCacilateValueAddDto.setActiveDto(billCalculateDto.getActiveDto());
		queryBillCacilateValueAddDto.setCalActiveDiscount(billCalculateDto.isCalActiveDiscount());
		queryBillCacilateValueAddDto.setActiveCode(billCalculateDto.getActiveCode());
		queryBillCacilateValueAddDto.setGoodsName(billCalculateDto.getGoodsName());
		queryBillCacilateValueAddDto.setDeliveryCustomerCode(billCalculateDto.getDeliveryCustomerCode());		
		queryBillCacilateValueAddDto.setOrderChannel(billCalculateDto.getOrderChannel());
		queryBillCacilateValueAddDto.setReceiveOrgCode(billCalculateDto.getReceiveOrgCode());
		queryBillCacilateValueAddDto.setLoadOrgCode(billCalculateDto.getLoadOrgCode());
		queryBillCacilateValueAddDto.setLastOutLoadOrgCode(billCalculateDto.getLastOutLoadOrgCode());
		queryBillCacilateValueAddDto.setCustomerPickupOrgCode(billCalculateDto.getCustomerPickupOrgCode());
		queryBillCacilateValueAddDto.setBillTime(billCalculateDto.getBillTime());
		queryBillCacilateValueAddDto.setTransportFee(billCalculateDto.getTransportFee());
		queryBillCacilateValueAddDto.setGoodsWeightTotal(billCalculateDto.getGoodsWeightTotal());
		queryBillCacilateValueAddDto.setGoodsVolumeTotal(billCalculateDto.getGoodsVolumeTotal());
		//纯运费，为了校验增值推广活动
		queryBillCacilateValueAddDto.setTransportFee(billCalculateDto.getTransportFee());
		queryBillCacilateValueAddDto.setCityMarketCode(billCalculateDto.getCityMarketCode());
		//菜鸟新需求返货折扣
		queryBillCacilateValueAddDto.setIsCainiao(billCalculateDto.getIsCainiao());
		queryBillCacilateValueAddDto.setReturnWaybillNo(billCalculateDto.getReturnWaybillNo());
		queryBillCacilateValueAddDto.setOldreceiveCustomerCode(billCalculateDto.getOldreceiveCustomerCode());
		queryBillCacilateValueAddDto.setReturnbilltime(billCalculateDto.getReturnbilltime());
		queryBillCacilateValueAddDto.setReturnInsuranceFee(billCalculateDto.getReturnInsuranceFee());
		queryBillCacilateValueAddDto.setReturnTransportFee(billCalculateDto.getReturnTransportFee());
		queryBillCacilateValueAddDto.setOriginalReceiveOrgCode(billCalculateDto.getOriginalReceiveOrgCode());
		/**
		 * 内部员工折扣方案
		 */
		queryBillCacilateValueAddDto.setInternalDeliveryType(billCalculateDto.getInternalDeliveryType());
		queryBillCacilateValueAddDto.setEmployeeNo(billCalculateDto.getEmployeeNo());
		List<ValueAddDto> valueAddDtos = billCaculateService.searchExpressValueAddPriceList(queryBillCacilateValueAddDto);
		
		if(CollectionUtils.isNotEmpty(valueAddDtos)) {
			List<GuiResultBillCalculateDto> resultBillCalculateDtos = new ArrayList<GuiResultBillCalculateDto>();
			for (int i = 0; i < valueAddDtos.size(); i++) {
				ValueAddDto valueAddDto = valueAddDtos.get(i);
				GuiResultBillCalculateDto resultBillCalculateDto =  new GuiResultBillCalculateDto();
				resultBillCalculateDto.setId(valueAddDto.getId());
				resultBillCalculateDto.setCaculateExpression(valueAddDto.getCaculateExpression());
				resultBillCalculateDto.setActualFeeRate(valueAddDto.getActualFeeRate());
				resultBillCalculateDto.setCaculateFee(valueAddDto.getCaculateFee());
				resultBillCalculateDto.setDiscountFee(valueAddDto.getDiscountFee());
				resultBillCalculateDto.setFee(valueAddDto.getFee());
				resultBillCalculateDto.setFeeRate(valueAddDto.getFeeRate());
				resultBillCalculateDto.setMinFee(valueAddDto.getMinFee());
				resultBillCalculateDto.setMaxFee(valueAddDto.getMaxFee());
				resultBillCalculateDto.setSubType(valueAddDto.getSubType());
				resultBillCalculateDto.setSubTypeName(valueAddDto.getSubTypeName());
				resultBillCalculateDto.setCaculateType(valueAddDto.getCaculateType());
				resultBillCalculateDto.setPriceEntryCode(valueAddDto.getPriceEntityCode());
				resultBillCalculateDto.setPriceEntryName(valueAddDto.getPriceEntityName());
				resultBillCalculateDto.setProductCode(valueAddDto.getProductCode());
				resultBillCalculateDto.setProductName(valueAddDto.getProductName());
				resultBillCalculateDto.setGoodsTypeCode(valueAddDto.getGoodsTypeCode());
				resultBillCalculateDto.setGoodsTypeName(valueAddDto.getGoodsTypeName());
				resultBillCalculateDto.setDefaultBF(valueAddDto.getDefaultBF());
				resultBillCalculateDto.setCandelete(valueAddDto.getCandelete());
				resultBillCalculateDto.setCanmodify(valueAddDto.getCanmodify());
				resultBillCalculateDto.setHeavyFeeRate(valueAddDto.getHeavyFeeRate());
				resultBillCalculateDto.setLightFeeRate(valueAddDto.getActualFeeRate());
				resultBillCalculateDto.setCaculateType(valueAddDto.getCaculateType());
				resultBillCalculateDto.setCentralizePickup(billCalculateDto.getIsReceiveGoods());
				if(CollectionUtils.isNotEmpty(valueAddDto.getDiscountPrograms())) {
					List<GuiResultDiscountDto> resultDiscountDtos = new ArrayList<GuiResultDiscountDto>();
					for (int k = 0; k < valueAddDto.getDiscountPrograms().size(); k++) {
						PriceDiscountDto discountDto =  valueAddDto.getDiscountPrograms().get(k);
						GuiResultDiscountDto guiResultDiscountDto = new GuiResultDiscountDto();
						guiResultDiscountDto.setDiscountRate(discountDto.getDiscountRate());
						guiResultDiscountDto.setMarketName(discountDto.getMarketName());
						guiResultDiscountDto.setReduceFee(discountDto.getReduceFee());
						guiResultDiscountDto.setPriceEntryCode(discountDto.getPriceEntryCode());
						guiResultDiscountDto.setPriceEntryName(discountDto.getPriceEntryName());
						guiResultDiscountDto.setSaleChannelCode(discountDto.getSaleChannelCode());
						guiResultDiscountDto.setSaleChannelName(discountDto.getSaleChannelName());
						guiResultDiscountDto.setDiscountId(discountDto.getDiscountId());
						guiResultDiscountDto.setDiscountType(discountDto.getType());
						guiResultDiscountDto.setDiscountTypeName(discountDto.getTypeName());
						guiResultDiscountDto.setChargeDetailId((discountDto.getChargeDetailId()));
						//添加推广活动折扣
						guiResultDiscountDto.setActiveCode(discountDto.getActiveCode());
						guiResultDiscountDto.setActiveName(discountDto.getActiveName());
						guiResultDiscountDto.setActiveStartTime(discountDto.getActiveStartTime());
						guiResultDiscountDto.setActiveEndTime(discountDto.getActiveEndTime());
						guiResultDiscountDto.setOptionsCrmId(discountDto.getOptionsCrmId());
						resultDiscountDtos.add(guiResultDiscountDto);
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
}
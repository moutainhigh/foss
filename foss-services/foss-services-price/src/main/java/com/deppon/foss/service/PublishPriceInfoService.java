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
package com.deppon.foss.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.xml.ws.Holder;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.header.ESBHeader;
import com.deppon.esb.inteface.domain.productservice.ExpressPrice;
import com.deppon.esb.inteface.domain.productservice.GeneralPrice;
import com.deppon.esb.inteface.domain.productservice.LttRate;
import com.deppon.esb.inteface.domain.productservice.ProductAging;
import com.deppon.esb.inteface.domain.productservice.PublishPriceInfo;
import com.deppon.esb.inteface.domain.productservice.QueryAgingRequest;
import com.deppon.esb.inteface.domain.productservice.QueryAgingResponse;
import com.deppon.esb.inteface.domain.productservice.QueryPriceRequest;
import com.deppon.esb.inteface.domain.productservice.QueryPriceResponse;
import com.deppon.esb.inteface.domain.productservice.QueryPublishPriceRequest;
import com.deppon.esb.inteface.domain.productservice.QueryPublishPriceResponse;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPublishPriceExpressService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPublishPriceService;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PublishPriceExpressEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PublicPriceDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryProductPriceDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ResultPriceDto;
import com.deppon.foss.productservice.ProductService;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * @Description: 查询公布价
 * PublicPriceInfoService.java Create on 2012-12-25 下午5:48:34
 * Company:IBM
 * @author FOSSDP-Administrator
 * Copyright (c) 2012 Company,Inc. All Rights Reserved
 * @version V1.0
 */
public class PublishPriceInfoService implements ProductService {

    /**
     * 日志
     */
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    // 客户信息Service接口
    private IPublishPriceService publishPriceService;
    
    /** 产品 Service. */
	private IProductService productService;

	public void setPublishPriceService(IPublishPriceService publishPriceService) {
		this.publishPriceService = publishPriceService;
	}
	
	private IPublishPriceExpressService publishPriceExpressService;

	public void setPublishPriceExpressService(
			IPublishPriceExpressService publishPriceExpressService) {
		this.publishPriceExpressService = publishPriceExpressService;
	}

	
	public IProductService getProductService() {
		return productService;
	}


	public void setProductService(IProductService productService) {
		this.productService = productService;
	}


	@Override
	public QueryPublishPriceResponse queryPublishPrice(
			Holder<ESBHeader> esbHeader, QueryPublishPriceRequest request)
			throws com.deppon.foss.productservice.CommonException {
		String startCityCode = request.getDepartureCity();
		String destinationCityCode = request.getDestinationCity();
		try {
			//判断出发城市编码和到达城市编码是否为省，如果为省，就返回null
			if(publishPriceService.queryPublishPriceDetailisProvince(startCityCode, destinationCityCode)){
				return null;
			}
			List<PublicPriceDto> publicPriceDtos = publishPriceService.queryPublishPriceDetailByCity(startCityCode, destinationCityCode, new Date());
			List<PublishPriceExpressEntity> publishPriceExpressEntitys = publishPriceExpressService.queryPublishPriceByCityCode(startCityCode, destinationCityCode, new Date());
			
			QueryPublishPriceResponse response = new QueryPublishPriceResponse();
			
			
			//快递价格
			if(CollectionUtils.isNotEmpty(publishPriceExpressEntitys)) {
				for (int i = 0; i < publishPriceExpressEntitys.size(); i++) {
					PublishPriceExpressEntity expressEntity = publishPriceExpressEntitys.get(i);
					PublishPriceInfo info = new PublishPriceInfo();
					info.setProductCode(expressEntity.getProductCode());
					info.setProductName(expressEntity.getProductName());
					//快递价格
					ExpressPrice expressPrice = new ExpressPrice();
					expressPrice.setGroundPrice(expressEntity.getFirstWeight());
					expressPrice.setLowerGround(expressEntity.getWeightLowLimit());
					expressPrice.setLowerOfStage1(expressEntity.getWeightOffline1());
					expressPrice.setLowerOfStage2(expressEntity.getWeightOffline2());
					expressPrice.setRateOfStage1(expressEntity.getFeeRate1());
					expressPrice.setRateOfStage2(expressEntity.getFeeRate2());
					expressPrice.setUpperGround(expressEntity.getWeightHighLimit());
					expressPrice.setUpperOfStage1(expressEntity.getWeightOnline1());
					expressPrice.setUpperOfStage2(expressEntity.getWeightOnline2());

					
					info.setExpressRate(expressPrice);

					
					ProductAging value=new ProductAging() ;
	                value.setDeliveryAddDays(expressEntity.getAddDay());				
					value.setGoodTypeCode(expressEntity.getGoodsTypeCode());
					value.setGoodTypeName(expressEntity.getGoodsTypeName());
					value.setLongShort(expressEntity.getLongOrShort());
					value.setPromiseArriveTime(expressEntity.getArriveTime());
					value.setPromiseDeliveryTime(expressEntity.getDeliveryTime());
					value.setPromiseQuickTime(expressEntity.getMinTime());
					value.setPromiseSlowTime(expressEntity.getMaxTime());
					value.setUnitOfPromiseQuickTime(expressEntity.getMinTimeUnit());
					value.setUnitPromiseOfSlowTime(expressEntity.getMaxTimeUnit());
					info.setSpeed(value); 				
					response.getPublishPriceInfos().add(info);
				}
			}
			
			//普通公布价
			if(CollectionUtils.isNotEmpty(publicPriceDtos)) {
				for (int i = 0; i < publicPriceDtos.size(); i++) {
					PublicPriceDto dto = publicPriceDtos.get(i);
					PublishPriceInfo info = new PublishPriceInfo();
					info.setProductCode(dto.getProductCode());
					info.setProductName(dto.getProductName());
					String productCode = dto.getProductCode();
					//根据三级产品code 获取二级产品code
					ProductEntity productEntity = productService.getProductByCache(productCode, new Date());
					if (productEntity == null) {
						return null;
					}
					//根据客户端传入的三级产品得到二级产品
					productCode = productEntity.getParentCode();
					
					//如果是零担汽运，则查询分段价格
					if(productCode!=null && (StringUtil.equalsIgnoreCase(productCode,
							ProductEntityConstants.PRICING_PRODUCT_C2_C20002)
							|| StringUtil.equalsIgnoreCase(productCode,
									ProductEntityConstants.PRICING_PRODUCT_C2_C20001))){
						//查询分段信息
						QueryProductPriceDto queryProductPriceDto = new QueryProductPriceDto();
						queryProductPriceDto.setOriginalOrgId(dto.getDeptRegionId());
						queryProductPriceDto.setDestinationId(dto.getArrvRegionId());
						queryProductPriceDto.setProductCode(productCode);
						queryProductPriceDto.setType(PricingConstants.VALUATION_TYPE_PRICING);//价格定义 
						queryProductPriceDto.setActive(FossConstants.ACTIVE); 
						queryProductPriceDto.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
						queryProductPriceDto.setReceiveDate(new Date());
						queryProductPriceDto.setGoodsTypeCode("H00001");
						List<ResultPriceDto> list = publishPriceService.queryResultPrice(queryProductPriceDto);
						List<LttRate> rateList = new ArrayList<LttRate>();
						LttRate[] arrays =null;
						if(list!=null && list.size()>0){
							
							Map<String,LttRate> map = new HashMap<String,LttRate>();
							for(ResultPriceDto result : list){
								String sectionId = result.getSectionId();
								String centralizePickup = result.getCentralizePickup();
								String caculateType = result.getCaculateType();
								LttRate lttRate = null;
								GeneralPrice valuePrice =null;
								if(map.containsKey(sectionId)){
									lttRate = map.get(sectionId);
									valuePrice = lttRate.getGenerateRate();
									
								}else{
									lttRate = new LttRate();
									valuePrice = new GeneralPrice();
								}
								//非上门接货
								//由于不管是上门接货还是非上门接货，重量范围和体积费范围都是一样的，因此该段的起点都一样
								if("N".equals(centralizePickup)){
									if("WEIGHT".equalsIgnoreCase(caculateType)){
										//设置重货价格（不上门接货）
										valuePrice.setHeavyRate(result.getFee());
										//设置重量范围起点
										lttRate.setWeightStart(new BigDecimal(result.getCriticalValue()));
									}else{
										//设置轻货价格（不上门接货）
										valuePrice.setLightRate(result.getFee());
										//设置体积范围起点
										lttRate.setVolumeStart(new BigDecimal(result.getCriticalValue()));
									}
									//设置最低一票（不上门接货）
									valuePrice.setLowestPrice(new BigDecimal(result.getMinFee()));
								}else{
									if("WEIGHT".equalsIgnoreCase(caculateType)){
										//设置重货价格（上门接货）
										valuePrice.setReceiveHeavyRate(result.getFee());
										//设置重量范围起点
										lttRate.setWeightStart(new BigDecimal(result.getCriticalValue()));
									}else{
										//设置轻货价格（上门接货）
										valuePrice.setReceiveLightRate(result.getFee());
										//设置体积范围起点
										lttRate.setVolumeStart(new BigDecimal(result.getCriticalValue()));
									}
									//设置最低一票（上门接货）
									valuePrice.setReceiveLowestPrice(new BigDecimal(result.getMinFee()));
								}
								
								valuePrice.setDestinationArea(dto.getArrvRegionName());
								valuePrice.setOriginatingArea(dto.getDeptRegionName());	
								lttRate.setGenerateRate(valuePrice);
								lttRate.setSectionId(sectionId);
								map.put(sectionId, lttRate);
							}
							
							//遍历map，设置范围终点
							if(!map.isEmpty()){//如果map不为空
								arrays = new LttRate[map.size()];
								Set<Map.Entry<String, LttRate>> set=map.entrySet();
								for (Iterator<Map.Entry<String, LttRate>> it = set.iterator(); it.hasNext();){
									 Map.Entry<String, LttRate> entry = (Map.Entry<String, LttRate>) it.next();
									 int sectionId = Integer.valueOf(entry.getKey());
									 if(sectionId>0){
										 LttRate lttRate = entry.getValue();
										 LttRate nextLttRate = map.get(sectionId+1+"");
										 if(nextLttRate !=null){//如果存在下一个阶段
											 //下一个分段的重量范围起点设置为本阶段重量范围的终点
											 lttRate.setWeightEnd(nextLttRate.getWeightStart());
											//下一个分段的体积范围起点设置为本阶段体积范围的终点
											 lttRate.setVolumeEnd(nextLttRate.getVolumeStart());
										 }else{
											 lttRate.setWeightEnd(null);
											 lttRate.setVolumeEnd(null);
										 }
										 arrays[sectionId-1] = lttRate;
									 }
								}
							}
						}
						if(arrays!=null && arrays.length>0){
							for(LttRate ltr: arrays){
								rateList.add(ltr);
							}
							info.getLttRates().addAll(rateList);
						}
						
					}
					
					GeneralPrice valuePrice=new GeneralPrice();				
					valuePrice.setReceiveHeavyRate(dto.getHeavyFeeRatePickUpYes());
					valuePrice.setReceiveLightRate(dto.getLightFeeRatePickUpYes());				
					valuePrice.setHeavyRate(dto.getHeavyFeeRatePickUpNo());
					valuePrice.setLightRate(dto.getLightFeeRatePickUpNo());
					valuePrice.setLowestPrice(dto.getMinFeePickUpNo());				 
					valuePrice.setReceiveLowestPrice(dto.getMinFeePickUpYes());
					valuePrice.setDestinationArea(dto.getArrvRegionName());
					valuePrice.setOriginatingArea(dto.getDeptRegionName());			
					
					info.setGenerateRate(valuePrice);
					
					ProductAging value=new ProductAging() ;
	                value.setDeliveryAddDays(dto.getAddDay());				
					value.setGoodTypeCode(dto.getGoodsTypeCode());
					value.setGoodTypeName(dto.getGoodsTypeName());
					value.setLongShort(dto.getLongOrShort());
					value.setPromiseArriveTime(dto.getArriveTime());
					value.setPromiseDeliveryTime(dto.getDeliveryTime());
					value.setPromiseQuickTime(dto.getMinTime());
					value.setPromiseSlowTime(dto.getMaxTime());
					value.setUnitOfPromiseQuickTime(dto.getMinTimeUnit());
					value.setUnitPromiseOfSlowTime(dto.getMaxTimeUnit());
					info.setSpeed(value); 				
					response.getPublishPriceInfos().add(info);
				}
			}
			esbHeader.value.setResponseId(UUID.randomUUID().toString());
			esbHeader.value.setResultCode(1);
			return response;
		} catch (Exception e) {

			logger.info(startCityCode+":"+destinationCityCode+":"+"CC官网调用价格时效接口"+e.getMessage()+"栈信息"+ExceptionUtils.getFullStackTrace(e));
 
		}
		return null;
		
	}

	@Override
	public QueryPriceResponse queryPrice(Holder<ESBHeader> esbHeader,
			QueryPriceRequest request)
			throws com.deppon.foss.productservice.CommonException {
		return null;
	}

	@Override
	public QueryAgingResponse queryAging(Holder<ESBHeader> esbHeader,
			QueryAgingRequest request)
			throws com.deppon.foss.productservice.CommonException {
		return null;
	}

}
